var appng = angular.module('appng', ['ngRoute', 'security', 'appng.toolbar','xeditable', 'ngTable', 'catalogueControllers', 'dataElementServices', 'relationshipTypeServices', 'catalogueServices'])

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
                templateUrl: 'js/appng/partials/dataElementList.tpl.html',
                controller: 'DataElementList',
                resolve: {
                    user: securityAuthorizationProvider.requireAuthenticatedUser
                }
            }).
            when('/dataElement/show/:dataElementId', {
                templateUrl: 'js/appng/partials/dataElementShow.tpl.html',
                controller: 'DataElementShow',
                resolve: {
                    user: securityAuthorizationProvider.requireAuthenticatedUser
                }
            }).
            when('/dataElement/create', {
                templateUrl: 'js/appng/partials/dataElementCreate.tpl.html',
                controller: 'DataElementCreate',
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

