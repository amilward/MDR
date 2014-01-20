angular.module('appng.toolbar', ['security', 'ui.bootstrap'])

// The loginToolbar directive is a reusable widget that can show login or logout buttons
// and information the current authenticated user
    .directive('appngToolbar', ['security', function (security) {
        security.requestCurrentUser();
        var directive = {
            templateUrl: 'js/appng/toolbar.tpl.html',
            restrict: 'E',
            replace: true,
            scope: true,
            link: function ($scope, $element, $attrs, $controller) {
                $scope.isAuthenticated = security.isAuthenticated;
                $scope.isAdmin = security.isAdmin;
                $scope.login = security.showLogin;
                $scope.logout = security.logout;
                $scope.$watch(function () {
                    return security.currentUser;
                }, function (currentUser) {
                    $scope.currentUser = currentUser;
                });
            }
        };
        return directive;
    }])

    // Use route-link attribute directive if you want to keep the navigation bar item highlighted based on
    // current route used. The highlighting happen when the route is resolved so if the navigation doesn't succeed
    // the route remain unchanged.
    //
    // If needed we can consider similar but more generic function which would watch $location.path() instead of
    // relying on $routeChangeSuccess event.
    .directive('routeLink', ['$location', '$rootScope', function ($location, $rootScope) {
        var directive = {
            link: function ($scope, $element, $attrs) {
                var updateActive = function () {
                    var listItem = $element.parent('li')
                    var dropdown = listItem.parents('li.dropdown')
                    var href = $element.attr('href');
                    if (!href) {
                        return;
                    }
                    if (href.indexOf('#') === 0 && href.length > 1) {
                        href = href.substring(1);
                    }
                    if (href.indexOf('?') > 0 && href.length > 1) {
                        href = href.substring(href.indexOf('?'))
                    }
                    if ($location.path() === href) {
                        listItem.addClass('active')
                        dropdown.addClass('active')
                    } else {
                        listItem.removeClass('active');
                        dropdown.removeClass('active');
                    }
                }
                $rootScope.$on('$routeChangeSuccess', updateActive);
            }
        };
        return directive;
    }])

;