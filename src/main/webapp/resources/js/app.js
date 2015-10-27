(function () {
    angular.module('wordxmlplus', [
        'ui.router',                    // Routing
        'ui.bootstrap',                 // Bootstrap
        'ui.checkbox',                  // Custom checkbox
        'summernote',                   // Text editor
        //'datePicker',                   // Datapicker
        //'datatables',                   // Dynamic tables
        'localytics.directives',        // Chosen select
        //-'ngGrid',                       // ngGrid
        'ui.codemirror',                // Code editor
        'ui.tree',                       // Nestable list
        'wordxmlApp.dashboard.controller', // custom dashboard controller
        'blueimp.fileupload',         // jquery file upload
        'wordxmlApp.settings.service', // Settings Service
        'wordxmlApp.fileupload.service', // FileUpload Service
        'wordxmlApp.download.service', // Download Service
        'wordxmlApp.users.service', // Users Service
        'wordxmlApp.transformation.service', // Transformation Service
        'wordxmlApp.fileupload.controller', // FileUpload Controller
        'wordxmlApp.settings.controller', // Settings Controller
        'wordxmlApp.downloads.controller', // Downloads Controller
        'wordxmlApp.document.controller', // Document Controller
        'wordxmlApp.transformations.controller', // Transformations Controller
        'wordxmlApp.users.controller', // Users Controller
        'wordxmlApp.preview', // Preview Controller
        'wordxmlApp.viewxml' // View XML Controller

    ])
})();