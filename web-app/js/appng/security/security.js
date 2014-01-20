// Based loosely around work by Witold Szczerba - https://github.com/witoldsz/angular-http-auth
angular.module('security.service', [
        'security.retryQueue',    // Keeps track of failed requests that need to be retried once the user logs in
        'security.login',         // Contains the login form template and controller
        'ui.bootstrap'            // Used to display the login form as a modal dialog.
    ])

    .factory('security', ['$http', '$q', '$location', 'securityRetryQueue', '$modal', function ($http, $q, $location, queue, $dialog) {

        // Redirect to the given url (defaults to '/')
        function redirect(url) {
            url = url || '/';
            $location.path(url);
        }

        // Login form dialog stuff
        var loginDialog = null;

        function openLoginDialog() {
            if (!loginDialog) {
                loginDialog = $dialog.open({
                    templateUrl: 'js/appng/security/login/form.tpl.html',
                    controller: 'LoginFormController'})
                loginDialog.result.then(onLoginDialogClose);
            }
        }

        function closeLoginDialog(success) {
            if (loginDialog) {
                loginDialog.close(success);
                loginDialog = null;
            }
        }

        function onLoginDialogClose(success) {
            if (success) {
                queue.retryAll();
            } else {
                queue.cancelAll();
                redirect();
            }
        }

        // Register a handler for when an item is added to the retry queue
        queue.onItemAddedCallbacks.push(function (retryItem) {
            if (queue.hasMore()) {
                service.showLogin();
            }
        });

        // The public API of the service
        var service = {

            // Get the first reason for needing a login
            getLoginReason: function () {
                return queue.retryReason();
            },

            // Show the modal login dialog
            showLogin: function () {
                openLoginDialog();
            },

            // Attempt to authenticate a user by the given email and password
            login: function (username, password, remember) {
                var options = {
                    url: sec.loginUrl,
                    method: "POST",
                    params: {
                        j_username: username,
                        j_password: password,
                        ajax: true
                    }
                }
                if (remember) {
                    options[sec.rememberMe] = true
                }
                var request = $http(options);
                return request.then(function (response) {
                    service.currentUser = response.data.success ? response.data : null;
                    if (service.isAuthenticated()) {
                        closeLoginDialog(true);
                    }
                });
            },

            // Give up trying to login and clear the retry queue
            cancelLogin: function () {
                closeLoginDialog(false);
                redirect();
            },

            // Logout the current user and redirect
            logout: function (redirectTo) {
                $http.post('logout').then(function () {
                    service.currentUser = null;
                    redirect(redirectTo);
                });
            },

            // Ask the backend to see if a user is already authenticated - this may be from a previous session.
            requestCurrentUser: function () {
                if (service.isAuthenticated()) {
                    return $q.when(service.currentUser);
                } else {
                    return $http.get('login/ajaxSuccess').then(function (response) {
                        if (!response.data.success) {
                            service.currentUser = null;
                            return null;
                        }
                        service.currentUser = response.data;
                        return service.currentUser;
                    });
                }
            },

            // Information about the current user
            currentUser: null,

            // Is the current user authenticated?
            isAuthenticated: function () {
                return !!service.currentUser;
            },

            // Is the current user an adminstrator?
            isAdmin: function () {
                return !!(service.currentUser && service.currentUser.admin);
            }
        };

        return service;
    }]);