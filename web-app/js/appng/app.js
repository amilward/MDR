var appng = angular.module('appng', ['security']);

appng.controller('HomeCtrl', function ($scope, $http, $log, security) {
    $scope.phones = [
        {'name': 'Nexus S',
            'snippet': 'Fast just got faster with Nexus S.'},
        {'name': 'Motorola XOOM™ with Wi-Fi',
            'snippet': 'The Next, Next Generation tablet.'},
        {'name': 'MOTOROLA XOOM™',
            'snippet': 'The Next, Next Generation tablet.'}
    ];
    $scope.login = function () {
        security.showLogin();
    }
    $scope.fetch = function () {
        $http.get("collectionBasket/dataElementsAsJSON", {responseType: "json"})

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
});