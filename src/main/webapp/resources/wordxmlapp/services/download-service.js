'use strict';

/* Services */

/*
 http://docs.angularjs.org/api/ngResource.$resource

 Default ngResources are defined as

 'get':    {method:'GET'},
 'save':   {method:'POST'},
 'query':  {method:'GET', isArray:true},
 'remove': {method:'DELETE'},
 'delete': {method:'DELETE'}

 */
var servicesDownload = angular.module('wordxmlApp.download.service',['ngResource']);

servicesDownload.factory('DataSource', ['$http',function($http){
    return {
        get: function(file,callback,transform){
            $http.get(
                file,
                {transformResponse:transform}
            ).
                success(function(data, status) {
                    console.log("Request succeeded");
                    callback(data);
                }).
                error(function(data, status) {
                    console.log("Request failed " + status);
                });
        }
    };
}]);

servicesDownload.factory('PreviewXMLFactory', ['$http',function($http){
    console.log('Calling PreviewXMLFactory- query');
    var obj = {};
    obj.fetchXML = function() {
        return $http.get('/transformation/xml/3');
    }
    return obj;
}]);

servicesDownload.factory('PreviewJSONFactory', function ($resource) {
    console.log('Calling PreviewJSONFactory- show, update, delete');

    return $resource('/transformation/json/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});
