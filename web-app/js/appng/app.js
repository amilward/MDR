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
           /* when('/:controller/', {
                templateUrl: 'js/appng/partials/dummy.tpl.html',
                controller: 'DummyCtrl',
                resolve: {
                    user: securityAuthorizationProvider.requireAuthenticatedUser
                }
            }).*/
            when('/dataElement', {
                templateUrl: 'js/appng/partials/dataElement.tpl.html',
                controller: 'DataElement',
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
    .controller('DashboardCtrl', ['$scope', '$http', 'security', function ($scope, $http, security) {
        $scope.security = security;
        $scope.fetch = function () {
            $http.get("collectionBasket/dataElementsAsJSON")
                .success(function (data, status, headers, config) {
                    $scope.dataElements = data;
                })
        };
    }])


 // Dummy controller, as for now it only exposes params service
    .controller('DataElement', ['$scope', '$http','ngTableParams', 'security', function ($scope, ngTableParams, $http, security) {

        $scope.security = security;

        var data = [
            {name: "Moroni", age: 50},
            {name: "Tiancum", age: 43},
            {name: "Jacob", age: 27},
            {name: "Nephi", age: 29},
            {name: "Enos", age: 34},
            {name: "Tiancum", age: 43},
            {name: "Jacob", age: 27},
            {name: "Nephi", age: 29},
            {name: "Enos", age: 34},
            {name: "Tiancum", age: 43},
            {name: "Jacob", age: 27},
            {name: "Nephi", age: 29},
            {name: "Enos", age: 34},
            {name: "Tiancum", age: 43},
            {name: "Jacob", age: 27},
            {name: "Nephi", age: 29},
            {name: "Enos", age: 34}
        ];
        $scope.data = data;
        $scope.tableParams = new ngTableParams({
            page: 1,            // show first page
            count: 10,          // count per page
            filter: {
                //name: 'M'       // initial filter
            },
            sorting: {
                //name: 'asc'     // initial sorting
            }
        }, {
            total: data.length, // length of data
            getData: function ($defer, params) {
                // use build-in angular filter
                var filteredData = params.filter() ?
                    $filter('filter')(data, params.filter()) :
                    data;
                var orderedData = params.sorting() ?
                    $filter('orderBy')(filteredData, params.orderBy()) :
                    data;
                params.total(orderedData.length); // set total for recalc pagination
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });
        $scope.changeSelection = function(user) {
            // console.info(user);
        }


    }])

// Dummy controller, as for now it only exposes params service
    .controller('DummyCtrl', ['$scope', '$routeParams', function ($scope, $routeParams) {
        $scope.params = $routeParams;
    }]);