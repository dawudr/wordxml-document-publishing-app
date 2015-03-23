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

var services = angular.module('dashboardApp.services', ['ngResource']);

services.factory('TemplatesFactory', function ($resource) {
    return $resource('/template/list', {}, {
        query: { method: 'GET', isArray: true }
    })
});

services.factory('TemplateFactory', function ($resource) {
    return $resource('/template', {}, {
        create: { method: 'POST' }
    })
});

services.factory('TemplateFactory', function ($resource) {
    return $resource('/template/:id', {}, {
        show: { method: 'GET' },
        delete: { method: 'DELETE', params: {id: '@id'} },
        update: { method: 'PUT', params: {id: '@id'} }
    })
});

services.factory('TransformationsFactory', function ($resource) {
    return $resource('/transformation/list', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST' }
    })
});

services.factory('TransformationFactory', function ($resource) {
    return $resource('/transformation/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});