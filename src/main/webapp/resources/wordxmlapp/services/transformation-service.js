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

var services = angular.module('wordxmlApp.transformation.service', ['ngResource']);

services.factory('TransformationsFactory', function ($resource) {
    console.log('Calling TransformationsFactory- query, create');

    return $resource('/transformation', {}, {
        query: {method: 'GET', isArray: true},
        create: { method: 'POST' }
    })
});

services.factory('TransformationFactory', function ($resource) {
    console.log('Calling TransformationFactory- show, update, delete');

    return $resource('/transformation/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});