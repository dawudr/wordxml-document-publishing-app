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

var services = angular.module('wordxmlApp.settings.service', ['ngResource']);

services.factory('TemplatesFactory', function ($resource) {
    console.log('Calling TemplatesFactory- query');
    return $resource('/template', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST' }
    })
});

services.factory('TemplateFactory', function ($resource) {
    console.log('Calling TemplateFactory- show, update, delete');
    return $resource('/template/:id', {}, {
        show: { method: 'GET', params: {id: '@id'} },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});