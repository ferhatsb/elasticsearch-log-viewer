'use strict';

/* Services */
angular.module('logviewerServices', ['ngResource']).
    factory('Log',function ($resource) {
        return $resource('/_logviewer/logs', {}, {
            query: {method: 'GET', params: {}, isArray: true}
        });
    }).

    factory('LogDetail', function ($resource) {
        return $resource('/_logviewer/:name', {}, {
            query: {method: 'GET', params: {name: 'elasticsearch.log', type: 'tail', line: '1'}}
        });
    });
