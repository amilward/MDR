var appng = angular.module('appng', ['ngRoute', 'security', 'appng.toolbar'])

    .controller('HomeCtrl', function ($scope, $http, $log, security) {
        security.requestCurrentUser();
        $scope.login = function () {
            security.showLogin();
        }
        $scope.fetch = function () {
            $http.get("collectionBasket/dataElementsAsJSON")

                .success(function (data, status, headers, config) {
                    $log.info(data);
                    $log.info(status);
                    $log.info(headers);
                    $log.info(config);
                    $scope.dataElements = data;
                })

                .error(function (data, status, headers, config) {
                    $log.info(data);
                    $log.info(status);
                    $log.info(headers);
                    $log.info(config);
                });
        };
    })

    .config(['securityAuthorizationProvider', '$routeProvider', function (securityAuthorizationProvider, $routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'js/appng/partials/dashboard.tpl.html',
                controller: 'DashboardCtrl'
            }).
            when('/:controller/', {
                templateUrl: 'js/appng/partials/dummy.tpl.html',
                controller: 'DummyCtrl',
                resolve: {
                    user: securityAuthorizationProvider.requireAuthenticatedUser
                }
            }).
            when('/:controller/:action/', {
                templateUrl: 'js/appng/partials/dummy.tpl.html',
                controller: 'DummyCtrl',
                resolve: {
                    user: securityAuthorizationProvider.requireAuthenticatedUser
                }
            }).
            otherwise({
                redirectTo: '/'
            });
    }])

// Dasboard controller, as for now it only exposes security service
    .controller('DashboardCtrl', ['$scope', 'security', function ($scope, security) {
        $scope.security = security;
    }])

// Dummy controller, as for now it only exposes params service
    .controller('DummyCtrl', ['$scope', '$routeParams', function ($scope, $routeParams) {
        $scope.params = $routeParams;
    }]);