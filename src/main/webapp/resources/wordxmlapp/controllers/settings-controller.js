var wordxmlSettingsApp = angular.module('wordxmlApp.settings.controller',  ['ngResource', 'ngGrid', 'ui.bootstrap','wordxmlApp.settings.service']);

/*wordxmlSettingsApp.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});*/

wordxmlSettingsApp.controller('SettingsCtrl', function ($scope,  $rootScope, TemplatesFactory, TemplateFactory) {

    /**
     * SETTING STUFF
     */

    // Modal Dialog for settings
    $scope.showTemplateModal = false;
    $scope.toggleModal = function(){
        $scope.showTemplateModal = !$scope.showTemplateModal;
    };

    // Set Modal width
    $(".modal-wide").on("show.bs.modal", function() {
        var height = $(window).height() - 200;
        $(this).find(".modal-body").css("max-height", height);
    });

    $("#templateModal").on("hidden.bs.modal", function () {
        // Broadcast the event to refresh the grid.
        //$rootScope.$broadcast('refreshGrid');
    });

    // Initialise Templates setting page
    $scope.myTemplate = [];

    TemplatesFactory.query().$promise.then(
        function(data) {
            console.log(data);
            $scope.templates = data;
        }
    );

    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};

    // Bind to the grid templates
    $scope.gridOptions = {
        data: 'templates',
        //useExternalSorting: true,
        //sortInfo: $scope.sortInfo,
        enableCellSelection: true,
        enableRowSelection: true,
        //enableCellEditOnFocus: true,
        columnDefs: [{field: 'id', width: 35, displayName: 'ID', enableCellEdit: true},
            {field:'templateName', displayName:'Template Name', enableCellEdit: true},
            {field:'description', displayName:'Description', enableCellEdit: true},
            {field:'revision', width: 72, displayName:'Revision', enableCellEdit: true},
            {displayName:'Action', width: 160,  enableCellEdit: true, cellTemplate:
                '<button type="button" ng-click="deleteTemplate(row)" class="btn btn-sm btn-danger">' +
                '<span class="glyphicon glyphicon-remove remove"></span> Delete' +
                '</button> ' +
                '<button type="button" ng-click="toggleModal()" class="btn btn-sm btn-primary">' +
                '<span class="glyphicon glyphicon-edit"></span> Edit' +
                '</button>'
            }
        ],
        multiSelect: false,
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        //TODO: Fix Bug when deleting, error deleting a delete template id
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('templateSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

    // Bind to the grid sections
/*
  TODO: To fix. Use grid instead
    $scope.gridSections = {
        data: 'myTemplate.templatesection',
        enableCellSelection: true,
        enableRowSelection: false,
        enableCellEditOnFocus: true,
        columnDefs: [{field: 'id', width: 30, displayName: 'ID', enableCellEdit: false},
            {field:'sectionName', displayName:'Section Name', enableCellEdit: true},
            {field:'sectionType', displayName:'Filter type', enableCellEdit: true},
            {field:'sectionValue', displayName:'Filter text', enableCellEdit: true},
            {field:'sectionStyle', displayName:'Filter style', enableCellEdit: true},
            {field:'validateInWordDoc', displayName:'Verify in Word', enableCellEdit: true},
            {field:'showSection', displayName:'Print XML field', enableCellEdit: true},
            {displayName:'Action', width: 140, cellTemplate:
            '<span class="btn-group">' +
            '<button type="button" ng-click="deleteTemplate(row)" class="btn btn-sm btn-danger">' +
            '<span class="glyphicon glyphicon-remove remove"></span> Delete' +
            '</button> ' +
            '<button type="button" ng-click="toggleModal()" class="btn btn-sm btn-primary">' +
            '<span class="glyphicon glyphicon-edit"></span> Edit' +
            '</button>' +
            '</span>'
            }
        ]
    };
*/


    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        $scope.templates = TemplatesFactory.query(); //data;
    };

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo.fields[0]', function () {
        $scope.refreshGrid();
    }, true);

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });

    // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
    // the appropiate rest service.
    $scope.$on('templateSelected', function (event, id) {
        console.log('templateSelected');
        console.log(id);
        if(id != null) {
            $scope.showTemplate(id);
        }
    });

    // Picks us the event broadcasted when the person is deleted from the grid and perform the actual person delete by
    // calling the appropiate rest service.
/*    $scope.$on('deleteTemplate', function (event, row) {
        console.log('Calling service- deleteTemplate');
        if(row.entity.id != null) {
            TemplateFactory.delete({id: row.entity.id}).$promise.then(
                function () {
                    // Broadcast the event to refresh the grid.
                    $rootScope.$broadcast('refreshGrid');
                    // Broadcast the event to display a delete message.
                    $rootScope.$broadcast('recordDeleted');
                    $scope.clearForm();
                },
                function () {
                    // Broadcast the event for a server error.
                    $rootScope.$broadcast('error');
                });
        } else {
            //$scope.templates.push($scope.myTemplate);
            console.log($scope.templates.pop());
            // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
            $rootScope.$broadcast('deleteTemplate', row.entity.id);
        }
    });*/

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteTemplate = function (row) {
        console.log('Calling service- deleteTemplate');
        console.log(row);
        if(row.entity.id != null) {
            TemplateFactory.delete({id: row.entity.id}).$promise.then(
                function () {
                    // Broadcast the event to refresh the grid.
                    $rootScope.$broadcast('refreshGrid');
                    // Broadcast the event to display a delete message.
                    $rootScope.$broadcast('recordDeleted');
                },
                function () {
                    // Broadcast the event for a server error.
                    $rootScope.$broadcast('error');
                });
        } else {
            $scope.templates.pop($scope.myTemplate);
            console.log($scope.myTemplate);
            // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
            $rootScope.$broadcast('deleteTemplate', row.entity.id);
        }
    };

    $scope.deleteSection = function (sectionListIndex) {
        console.log('Calling service- deleteSection');
        console.log(sectionListIndex);
        $scope.myTemplate.templatesection.splice(sectionListIndex, 1);
        // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
        //$scope.updateTemplate($scope.myTemplate);
        //$rootScope.$broadcast('recordDeleted');
    };


    $scope.saveForm = function() {
        console.log('Calling service- saveForm')
        console.log($scope.myTemplate);
        if($scope.myTemplate != null && $scope.myTemplate.id == null) {
            $scope.createNewTemplate($scope.myTemplate);
        } else {
            $scope.updateTemplate($scope.myTemplate);
        }
        $rootScope.$broadcast('refreshGrid');
        $scope.toggleModal();
    }

    // callback for ng-click 'cancel':
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.myTemplate = null;
        // For some reason, I was unable to clear field values with type 'url' if the value is invalid.
        // This is a workaroud. Needs proper investigation.
        document.getElementById('imageUrl').value = null;
        // Resets the form validation state.
        $scope.templateForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };

    // callback for ng-click 'createNewTemplate':
    $scope.createNewTemplate = function () {
        console.log('Calling service- createNewTemplate')
        TemplatesFactory.create($scope.myTemplate).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('recordCreated');
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    // callback for ng-click 'updateTemplate':
    $scope.updateTemplate = function () {
        console.log('Calling service- updateTemplate');
        TemplateFactory.update($scope.myTemplate).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('recordSaved');
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };


    $scope.sectionTypes = [
        {name: '-- Select --', type: null},
        {name: 'Meta data', type: 'META'},
        {name: 'Section title', type: 'HEADER'},
        {name: 'Section content', type: 'SECTION'},
        {name: 'Paragraph text', type: 'PARAGRAPH'},
        {name: 'Table', type: 'TABLE'},
        {name: 'Table row', type: 'ROW'},
        {name: 'Table cell', type: 'CELL'}];

    // callback for ng-click 'showTemplate':
    $scope.showTemplate = function (templateId) {
        console.log('Calling service- showTemplate');
        TemplateFactory.show({id: templateId}).$promise.then(
            function(data) {
                console.log(data);
                $scope.myTemplate = data;
            })
    };


    // callback for ng-click 'addNewTemplate':
    $scope.addNewTemplate = function () {
        console.log('Calling service- addNewTemplate')
        console.log($scope.myTemplate);
        $scope.myTemplate = {
            "templateName": "",
            "description": null,
            "revision" : null,
            "xsltScriptLocation": null,
            "xsdScriptLocation": null,
            "xQueryScriptLocation": null,
            "templatesection": [
                {
                    "sectionType": null,
                    "sectionStyle": "",
                    "sectionName": "",
                    "sectionValue": "",
                    "showSection": false,
                    "validateInWordDoc": false
                }
            ]
        };
        //$scope.templates.push($scope.myTemplate);
        $scope.toggleModal();
    }
});

wordxmlSettingsApp.directive('modal', function () {
    return {
        template: '<div class="modal modal-wide fade">' +
        '<div class="modal-dialog">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
        '<h4 class="modal-title">{{ title }}</h4>' +
        '</div>' +
        '<div class="modal-body" ng-transclude></div>' +
        '<div class="modal-footer">' +
        '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>' +
        '<button type="button" class="btn btn-primary" ng-click="saveForm()">Save changes</button>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>',
        restrict: 'E',
        transclude: true,
        replace:true,
        scope:true,
        link: function postLink(scope, element, attrs) {
            scope.title = attrs.title;

            scope.$watch(attrs.visible, function(value){
                if(value == true)
                    $(element).modal('show');
                else
                    $(element).modal('hide');
            });

            $(element).on('shown.bs.modal', function(){
                scope.$apply(function(){
                    scope.$parent[attrs.visible] = true;
                });
            });

            // Nice explanations http://www.sitepoint.com/understanding-bootstrap-modals/
            $(element).on('hidden.bs.modal', function(){
                scope.$apply(function(){
                    scope.$parent[attrs.visible] = false;
                });
            });
        }
    };
});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
wordxmlSettingsApp.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('recordSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });
    // Picks up the event to display a saved message.
    $scope.$on('recordCreated', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record created successfully!' }
        ];
    });
    // Picks up the event to display a deleted message.
    $scope.$on('recordDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
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