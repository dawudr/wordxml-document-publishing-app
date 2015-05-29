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

//TODO: Unfinished

var servicesFiles = angular.module('wordxmlApp.fileupload.service', ['ngResource']);

servicesFiles.factory('FileUploadsFactory', function ($resource) {
    console.log('Calling FileUploadsFactory- query');
    return $resource('/upload', {}, {
        query: { method: 'GET' , isArray: false },
        create: { method: 'POST' }
    })
});
/*
servicesFiles.factory('FileUploadFactory', function ($resource) {
    console.log('Calling FileUploadFactory- show, update, delete');
    return $resource('/delete/:id', {}, {
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});
*/
