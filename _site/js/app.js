'use strict';

/* App Module */

angular.module('logviewer', [   'logviewerServices']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/logs', {templateUrl: 'partials/log-list.html',   controller: LogListCtrl}).
      when('/logs/:name', {templateUrl: 'partials/log-detail.html', controller: LogDetailCtrl}).
      otherwise({redirectTo: '/logs'});
}]);
