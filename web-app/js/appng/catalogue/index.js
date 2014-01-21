// Based loosely around work by Witold Szczerba - https://github.com/witoldsz/angular-http-auth
angular.module('catalogue', [
    //'catalogue.dashboard' FIXME , catalogue.valueDomain etc.
    ]);

catalogue.controller('PhoneListCtrl', function ($scope) {
    $scope.phones = [
        {'name': 'Nexus S',
            'snippet': 'Fast just got faster with Nexus S.'},
        {'name': 'Motorola XOOM™ with Wi-Fi',
            'snippet': 'The Next, Next Generation tablet.'},
        {'name': 'MOTOROLA XOOM™',
            'snippet': 'The Next, Next Generation tablet.'}
    ];
});