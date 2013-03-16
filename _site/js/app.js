'use strict';

/* App Module */

angular.module('logviewer', ['logviewerServices', 'logviwerFilters']).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            when('/logs/:name', {templateUrl: 'partials/log-detail.html', controller: LogDetailCtrl}).
            otherwise({redirectTo: '/logs/elasticsearch.log'});
    }]);
