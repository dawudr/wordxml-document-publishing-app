/**
 * INSPINIA - Responsive Admin Theme
 * Copyright 2014 Webapplayers.com
 *
 * Inspinia theme use AngularUI Router to manage routing and views
 * Each view are defined as state.
 * Initial there are written stat for all view in theme.
 *
 */
function config($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/dashboard");
    $stateProvider
        .state('dashboard', {
            url: "/dashboard",
            templateUrl: "wordxmlapp/views/dashboard-view.html",
            controller: 'DashboardCtrl',
            data: { pageTitle: 'Dashboard View' }
        })
        .state('downloads', {
            url: "/downloads",
            templateUrl: "wordxmlapp/views/downloads-view.html",
            controller: 'DownloadsCtrl',
            data: { pageTitle: 'Downloads View' }
        })
        .state('wizards', {
            url: "/wizards",
            templateUrl: "/processtransform/start",
            controller: 'DocumentTransformsCtrl',
            //controller: wizardCtrl,
            data: { pageTitle: 'Wizards' }
        })
        .state('fileupload', {
            url: "/fileupload",
            templateUrl: "wordxmlapp/views/fileupload-view.html",
            data: { pageTitle: 'File Upload' }
        })
        .state('document', {
            url: "/document/:transformationId",
            templateUrl: "wordxmlapp/views/document-view.html",
            controller: 'DocumentCtrl',
            data: { pageTitle: 'Document' }
        })
        .state('settings', {
            url: "/settings",
            templateUrl: "wordxmlapp/views/settings-view.html",
            controller: 'SettingsCtrl',
            data: { pageTitle: 'Settings' }
        })
        .state('userprofile', {
            url: "/userprofile/:userId",
            controller: 'UserProfileCtrl',
            templateUrl: "wordxmlapp/views/userprofile-view.html",
            data: { pageTitle: 'User profile' }
        })
        .state('usermanagement', {
            url: "/usermanagement",
            controller: 'UserManageCtrl',
            templateUrl: "wordxmlapp/views/users-view.html",
            data: { pageTitle: 'User Management' }
        });
}
angular
    .module('wordxmlplus')
    .config(config)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    })
    .config([
        '$httpProvider', 'fileUploadProvider',
        function ($httpProvider, fileUploadProvider) {
            delete $httpProvider.defaults.headers.common['X-Requested-With'];
            fileUploadProvider.defaults.redirect = window.location.href.replace(
                /\/[^\/]*$/,
                '/cors/result.html?%s'
            );
        }
    ])
;