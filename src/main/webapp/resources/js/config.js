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
            templateUrl: "views/dashboard_view.html",
            controller: 'DashboardCtrl',
            data: { pageTitle: 'Dashboard View' }
        })
        .state('downloads', {
            url: "/downloads",
            templateUrl: "views/downloads_view.html",
            controller: 'DashboardCtrl',
            data: { pageTitle: 'Downloads View' }
        })
        .state('wizards', {
            url: "/wizards",
            templateUrl: "views/form_wizard_single.html",
            controller: 'DocumentTransformSingleCtrl',
            //controller: wizardCtrl,
            data: { pageTitle: 'Wizards' }
        })
/*        .state('forms.wizard', {
            url: "/wizard",
            templateUrl: "views/form_wizard_single.html",
            controller: wizardCtrl,
            data: { pageTitle: 'Wizard form' },
            resolve: {
                loadPlugin: function ($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        {
                            files: ['css/plugins/steps/jquery.steps.css']
                        }
                    ]);
                }
            }
        })*/
/*        .state('wizard', {
            url: "/wizard",
            templateUrl: "views/form_wizard_single.html",
            controller: wizardCtrl,
            data: { pageTitle: 'Wizard form' }
        })*/
        .state('wizard.step_one', {
            url: '/step_one',
            templateUrl: 'views/wizard/step_one.html',
            data: { pageTitle: 'Wizard form' }
        })
        .state('wizard.step_two', {
            url: '/step_two',
            templateUrl: 'views/wizard/step_two.html',
            data: { pageTitle: 'Wizard form' }
        })
        .state('wizard.step_three', {
            url: '/step_three',
            templateUrl: 'views/wizard/step_three.html',
            data: { pageTitle: 'Wizard form' }
        })
        .state('batchrun', {
            url: "/batchrun",
            templateUrl: "views/form_wizard_multiple.html",
            data: { pageTitle: 'Batch Conversion' }
        })
        .state('document', {
            url: "/document/:transformationId",
            templateUrl: "views/document_detail.html",
            controller: 'DocumentDetailCtrl',
            data: { pageTitle: 'Document detail' }
        })
        .state('settings', {
            url: "/settings",
            templateUrl: "views/form_settings.html",
            data: { pageTitle: 'Settings' }
        })
        .state('userprofile', {
            url: "/userprofile/:userId",
            controller: 'UserProfileCtrl',
            templateUrl: "views/user_profile.html",
            data: { pageTitle: 'User profile' }
        })
        .state('usermanagement', {
            url: "/usermanagement",
            controller: 'UserManageCtrl',
            templateUrl: "views/user_management.html",
            data: { pageTitle: 'User Management' }
        })
        .state('testpage', {
        url: "/test",
        controller: 'TestCtrl',
        templateUrl: "views/test_page.html",
        data: { pageTitle: 'Test Page' }
    });
}
angular
    .module('inspinia')
    .config(config)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    });