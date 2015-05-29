var wordxmlFileUploadApp = angular.module('wordxmlApp.fileupload.controller', ['ngResource','wordxmlApp.fileupload.service', 'wordxmlApp.transformation.service']);

wordxmlFileUploadApp.controller('FileUploadCtrl', function($scope,  $rootScope, $http, $filter, $window, TemplatesFactory, TemplateFactory, FileUploadsFactory, TransformationsFactory) {

    // Initialise Files page

    /**
     * FILE UPLOAD STUFF
     */
    var url = '/upload';
    $scope.myTransforms = [];

    $scope.options = {
        url: url,
        maxFileSize: 5000000,
        acceptFileTypes: /(\.|\/)(docx|doc|rtf)$/i
    };
    $scope.loadingFiles = true;

    // Retrieve list of files uploaded
    FileUploadsFactory.query().$promise.then(
        function (data) {
            $scope.loadingFiles = false;
            $scope.queue = data.files || [];
            $scope.updateTransformsList();
        },
        function () {
            $scope.loadingFiles = false;
        }
    );

    // Update Transform list by copying files list to transforms
    $scope.updateTransformsList = function() {
        $scope.myTransforms = [];
        angular.forEach($scope.queue, function(value, key) {
            // New Image files will be uploaded with lastModified date being null
            if(value.lastUpdated == null) {
                $scope.myTransforms.push({
                    file: value,
                    openxmlfilename: replaceSpaces(value.name) + '.xml',
                    iqsxmlfilename: replaceSpaces(value.name) + '-pqs.xml'
                });
            }
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


    // callback for ng-click 'createTransformation':
    $scope.createTransformation = function (item) {
        console.log('Calling service- createTransformation');
        //console.log(item);
        $scope.myTransformation =  {
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
            "wordfilename": item.file.name,
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
            "imageId": item.file.id
        }
        console.log($scope.myTransformation);

        TransformationsFactory.create($scope.myTransformation).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                //$rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('recordCreated');
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            }
        );
        // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
        //$scope.updateTemplate($scope.myTemplate);
        //$rootScope.$broadcast('recordDeleted');
    };


/*
    $http.get(url)
        .then(
        function (response) {
            $scope.loadingFiles = false;
            $scope.queue = response.data.files || [];
            //console.log($scope.queue);
        },
        function () {
            $scope.loadingFiles = false;
        }
    );
*/

        // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
        // This also works for the first page access, since we assign the initial sorting in the initialize section.
/*        $scope.$watch('queue', function () {
            console.log('Watching');
            $scope.updateTransformsList();
        }, true);*/
    })
    .controller('FileDestroyController', [
        '$scope', '$http',
        function ($scope, $http) {
            var file = $scope.file,
                state;
            if (file.url) {
                file.$state = function () {
                    return state;
                };
                file.$destroy = function () {
                    state = 'pending';
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
                    $scope.clear(file);
                };
            }
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
            { type: 'success', msg: 'Transformation validated successfully! Review Updates in Dashboard' }
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
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});