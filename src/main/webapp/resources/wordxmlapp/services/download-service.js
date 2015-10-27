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

servicesDownload.factory('PreviewXMLFactory', function ($resource) {
    console.log('Calling PreviewXMLFactory-  show, update, delete');
    return $resource('/transformation/xml/:id', {}, {
        show: { method: 'GET', headers:{
            'Content-Type':'application/xml; charset=UTF-8'
        } },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});

servicesDownload.factory('PreviewJSONFactory', function ($resource) {
    console.log('Calling PreviewJSONFactory- show, update, delete');

    return $resource('/transformation/json/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});
