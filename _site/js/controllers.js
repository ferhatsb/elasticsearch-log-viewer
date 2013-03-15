'use strict';

/* Controllers */

function LogListCtrl($scope, Log) {
    $scope.logs = Log.query();
}

//PhoneListCtrl.$inject = ['$scope', 'Phone'];


function LogDetailCtrl($scope, $routeParams, LogDetail, Log) {
    $scope.log = LogDetail.get({name: $routeParams.name}, function (logdetail) {

    });
    $scope.logs = Log.query();
}

//PhoneDetailCtrl.$inject = ['$scope', '$routeParams', 'Phone'];
