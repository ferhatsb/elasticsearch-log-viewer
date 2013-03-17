'use strict';

/* Controllers */
function LogDetailCtrl($scope, $timeout, $routeParams, LogDetail, Log) {
    $scope.log = LogDetail.get({name: $routeParams.name, type: $scope.type, line: $scope.line});
    $scope.logs = Log.query();
    $scope.tick = 'none';
    $scope.type = 'tail';
    $scope.line = 1;

    function tick() {
        if ($scope.tick != 'none') {
            $scope.log = LogDetail.get({name: $routeParams.name, type: $scope.type, line: $scope.line});
            $timeout(tick, $scope.tick);
        } else {
            $timeout.cancel();
        }
    }

    $scope.ticker = function () {
        $timeout(tick, $scope.tick);
    };

    $scope.fetch = function () {
        $scope.log = LogDetail.get({name: $routeParams.name, type: $scope.type, line: $scope.line});
    };

    $scope.navClass = function (page) {
        return page === $routeParams.name ? 'active' : '';
    };
}