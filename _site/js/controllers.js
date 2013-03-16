'use strict';

/* Controllers */
function LogDetailCtrl($scope, $timeout, $routeParams, LogDetail, Log) {
    $scope.log = LogDetail.get({name: $routeParams.name, type: $scope.type, line: $scope.line});
    $scope.logs = Log.query();
    $scope.tick = 2000;
    $scope.type = 'tail';

    (function tick() {
        $scope.log = LogDetail.get({name: $routeParams.name, type: $scope.type, line: $scope.line});
        $timeout(tick, $scope.tick);
    })();
}