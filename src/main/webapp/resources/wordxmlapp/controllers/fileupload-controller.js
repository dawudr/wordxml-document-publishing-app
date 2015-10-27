var wordxmlFileUploadApp = angular.module('wordxmlApp.fileupload.controller', ['ngResource', 'wordxmlApp.fileupload.service', 'wordxmlApp.transformation.service']);

wordxmlFileUploadApp.controller('FileUploadCtrl', function($scope, $rootScope, $http, $filter, $window, $q, TemplatesFactory, TemplateFactory, FileUploadsFactory, FileUploadFactory, TransformationsFactory) {

    // Initialise Files page

    /**
     * FILE UPLOAD STUFF
     */
    var url = '/upload';
    $scope.myTransforms = [];
    $scope.defaultTemplate = {};
    $scope.vm = {
        appLoaded: false
    };

    $scope.options = {
        url: url,
        maxFileSize: 5000000,
        acceptFileTypes: /(\.|\/)(docx|doc|rtf)$/i,
        singleFileUploads: true,
        limitMultiFileUploads: 1
    };
    $scope.loadingFiles = true;

    $scope.log = [
    ];


    // Retrieve list of files uploaded
    FileUploadsFactory.query().$promise.then(
        function (data) {
            if($scope.loadingFiles) {
                $scope.loadingFiles = false;
                console.log('In FileUploadsFactory Reload.....');
                $scope.queue = data.files || [];
                console.log($scope.queue);
            }
            $scope.vm.appLoaded = true;
            console.log('In FileUploadsFactory.query()');
        },
        function () {
            $scope.loadingFiles = false;
            $scope.vm.appLoaded = true;
        }
    );

    // Upload button task to fill in Tranform form
    $scope.prePopulateUploadForm = function() {
        console.log('In prePopulateUploadForm');
        console.log($scope.queue);
        $scope.myTransforms = [];
        angular.forEach($scope.queue, function(queue) {
            // New Image files will be uploaded with lastModified date being null
            queue.qanNo = '';
            queue.openxmlfilename = replaceSpaces(queue.name) + '.xml';
            queue.iqsxmlfilename = replaceSpaces(queue.name) + '-pqs.xml';
            queue.template = '';
            queue.deleteUrl = '/delete/' + queue.id;
            queue.url = '/download/' + queue.id;
            queue.transforming = false;
        });
    }

    $scope.prePopulateUploadFormTemplate = function() {
        console.log('In prePopulateUploadFormTemplate');
        console.log($scope.defaultTemplate);
        console.log($scope.queue);
        $scope.myTransforms = [];
        angular.forEach($scope.queue, function(queue) {
            queue.template = $scope.defaultTemplate;
        });
    }


    function replaceSpaces(str) {
        return str.slice(0, str.lastIndexOf('.')).replace(/([^a-z0-9]+)/gi, '_');
    };

    // Populates list of template for select
    TemplatesFactory.query().$promise.then(
        function(data) {
            $scope.templates = data;
        }
    );

    $scope.refresh = function() {
        $window.location.reload();
    }

    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refresh();
    });

    // callback for ng-click 'delete' for CANCEL button: Cancel Pending upload files or delete uploaded files
    $scope.delete = function() {
        angular.forEach($scope.queue, function(file) {
            console.log('Calling delete...')

            if (file instanceof File){
                console.log('Removing non persisted file upload object from Queue List...')
                $scope.queue.splice(0,1)
            } else if(angular.isObject(file)) {
                console.log('Removing Persisted file object from Queue List...')
                // Delete files already uploaded
                FileUploadFactory.delete({ id: file.id }).$promise.then(
                    function () {
                        // Broadcast the event to display a delete message.
                        console.log('Splicing...')
                        console.log($scope.queue)
                        $scope.queue.splice(0,1)
                        $rootScope.$broadcast('recordDeleted');
                    },
                    function () {
                        // Broadcast the event for a server error.
                        $rootScope.$broadcast('error');
                    }
                );
            }
        })
    }

    $scope.hasFilesPendingTransform = function () {
        for (var i = 0, len = $scope.queue.length; i < len; i++) {
            var file = $scope.queue[i]
            if(angular.isObject(file) && !(file instanceof File)) {
                //console.log('Found pending transform file...')
                //console.log(file)
                return true
                break;
            }
        }
        return false;
    }

    // callback for ng-click 'createTransformations' for Transform ALL button:
    $scope.createTransformations = function (queue) {
        console.log('Calling service- createTransformations');
        $scope.log.length = 0;
        console.log($scope.log);

        function next() {
            // process the first item on the queue and remove it so queue list becomes empty
            if (queue.length > 0) {
                console.log('Running createTransformation for:');
                console.log(queue[0]);
                $scope.queue[0].transforming = true;
                $scope.createTransformation(queue[0]).then( function (){
                        next();
                    }
                );
            }
        }
        next();

    };


    // Transform button task to post Transform form data
    // callback for ng-click 'createTransformation' for the Transform button:
    $scope.createTransformation = function (item) {
        console.log('Calling service- createTransformation');
        // get index of file record to remove
        var index = $scope.queue.indexOf(item);

        //console.log(item);
        //-$scope.vm.appLoaded = false;
        $scope.queue[index].transforming = true;
        $scope.myTransformation = {
            //"id":1,
/*            "user": {
                "id":2,
                "username":"btectest1",
                "password":"Password123",
                "email":"test@email.com",
                "firstname":"Btec",
                "lastname":"Test",
                "role":"ROLE_ADMIN"
            },*/
            "date": item.date,
            "qanNo": item.qanNo,
            "wordfilename": item.name,
            "openxmlfilename": item.openxmlfilename,
            "iqsxmlfilename": item.iqsxmlfilename,
            //"unitNo":"44",
            //"unitTitle":"Manufacturing Secondary Machining Processes",
            //"author":"Paul Winser",
            //"lastmodified": item.file.lastUpdated,
            //"template":null,
            "templateId": item.template.id,
            "transformStatus":'TRANSFORM_STATUS_FILE_UPLOAD_IN_PROGRESS',
            //"message":"No errors were found",
            //"generalStatus":"GENERAL_STATUS_UNREAD",
            //"specunit":1,
            "imageId": item.id,
            "transforming" : false
        }
        console.log($scope.myTransformation);

        var deferred = $q.defer();
        TransformationsFactory.create($scope.myTransformation).$promise.then(
            function () {
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('recordCreated');
                //- $scope.vm.appLoaded = true;
                // remove file from queue list so the page updates
                deferred.resolve()
                $scope.queue.splice(index, 1);
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
                //- $scope.vm.appLoaded = true;
                $scope.queue[index].transforming = false;
                $scope.queue[index].error = true;
                deferred.reject(error);
            }
        )
        return deferred.promise;
    };
    })
    .controller('FileDestroyController', [
        '$scope', '$http', '$rootScope',
        function ($scope, $http, $rootScope) {
            var file = $scope.file,
                state;
            if (file.url) {
                file.$state = function () {
                    return state;
                };
                file.$destroy = function () {
                    state = 'pending';
                    console.log('Deleting file...')
                    console.log(file)
                    return $http({
                        url: file.deleteUrl,
                        method: file.deleteType
                    }).then(
                        function () {
                            state = 'resolved';
                            $scope.clear(file);
                        },
                        function () {
                            state = 'rejected';
                        }
                    );
                };
            } else if (!file.$cancel && !file._index) {
                file.$cancel = function () {
                    console.log('Cancelling uploads...')
                    $scope.clear(file);
                };
            }
            $scope.prePopulateUploadForm($scope.queue);
        }

    ]);


// Create a controller with name alertMessagesController to bind to the feedback messages section.
wordxmlFileUploadApp.controller('alertMessagesFileUploadController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('recordSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });
    // Picks up the event to display a saved message.
    $scope.$on('recordCreated', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Document transformed successfully! Please click on Dashboard or Downloads to view XML document' }
        ];
    });
    // Picks up the event to display a deleted message.
    $scope.$on('recordDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });
    // Picks up the event to display a selected message.
    $scope.$on('recordSelected', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record selected' }
        ];
    });
    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'The document has errors and does not match the Template type. Please check the document and try again.' }
        ];
    });

    $scope.$on('cancel', function () {
        console.log('Upload cancelled');
        $scope.alerts = [
            { type: 'success', msg: 'Upload cancelled' }
        ];
    });

    $scope.$on('fileuploaded', function () {
        console.log('Fileuploaded ok!');
        $scope.alerts = [
            { type: 'success', msg: 'File upload success. Now check details and click on Transform' }
        ];
    });


    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});