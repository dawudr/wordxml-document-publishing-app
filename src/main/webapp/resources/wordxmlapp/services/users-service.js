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

var servicesUsers = angular.module('wordxmlApp.users.service', ['ngResource']);

servicesUsers.factory('UsersFactory', function ($resource) {
    console.log('Calling UsersFactory- query');
    return $resource('/users', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST' }
    })
});

servicesUsers.factory('UserFactory', function ($resource) {
    console.log('Calling UserFactory- show, update, delete');
    return $resource('/user/:id', {}, {
        show: { method: 'GET', params: {id: '@id'} },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});