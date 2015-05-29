var wordxmlDownloadsApp = angular.module('wordxmlApp.downloads.controller',  ['ngResource', 'ngGrid', 'ui.bootstrap','wordxmlApp.transformation.service']);

/*wordxmlSettingsApp.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});*/

wordxmlDownloadsApp.controller('DownloadsCtrl', function ($scope,  $rootScope, TransformationsFactory, TransformationFactory) {

    /**
     * SETTING STUFF
     */

        // Modal Dialog for settings
    $scope.showTemplateModal = false;
    $scope.toggleModal = function () {
        $scope.showTemplateModal = !$scope.showTemplateModal;
    };

    // Set Modal width
    $(".modal-wide").on("show.bs.modal", function () {
        var height = $(window).height() - 200;
        $(this).find(".modal-body").css("max-height", height);
    });

    $("#templateModal").on("hidden.bs.modal", function () {
        // Broadcast the event to refresh the grid.
        //$rootScope.$broadcast('refreshGrid');
    });

    // Initialise Transformations page
    $scope.transformation = [];
    $scope.transformations = [];

    TransformationsFactory.query().$promise.then(
        function (data) {
            // success handler
            $scope.transformations = data || [];
        }
    );

    $scope.gridOptions = {
        data: 'transformations',
        enableCellSelection: false,
        enableRowSelection: true,
        enableCellEdit: false,
        enableCellEditOnFocus: false,
        columnDefs: [
            {name: 'id', field: 'id', width: 30, displayName: '#', enableCellEdit: false},
            {field:'qanNo', displayName:'Qan', enableCellEdit: true},
            {field:'unitNo', displayName:'Unit No', enableCellEdit: true},
            {field:'unitTitle', displayName:'Title:', enableCellEdit: true},
            {field:'wordfilename', displayName:'File:', enableCellEdit: true},
            {field:'lastmodified', displayName:'Created', enableCellEdit: true},
            //{field:'transformStatus', displayName:'Transform result', enableCellEdit: true},
            {displayName:'Download', minWidth: 120, width: 120, enableCellEdit: false, cellTemplate:
            '<a alt="Word file" href="/download/{{row.entity.imageId}}" class="btn btn-primary btn-sm">' +
            '<i class="fa fa-file-word-o"></i>' +
            '</a> ' +
            '<a alt="Open XML file" href="/transformation/xml/{{row.entity.id}}/download/" class="btn btn-primary btn-sm">' +
            '<i class="fa fa-file-code-o"></i>' +
            '</a> ' +
            '<a alt="PQS XML file" href="/transformation/pqs/{{row.entity.id}}/download/" class="btn btn-primary btn-sm">' +
            '<i class="fa fa-file-text-o"></i>' +
            '</a> '
            },
            {displayName:'Action', minWidth: 80, width: 80, enableCellEdit: false, cellTemplate:
            '<button type="button" ng-click="deleteRecord(row)" class="btn btn-sm btn-danger">' +
            '<span class="glyphicon glyphicon-remove remove"></span>' +
            '</button> ' +
            '<a href="#/document/{{row.entity.id}}" class="btn btn-primary btn-sm">' +
            '<span class="glyphicon glyphicon-edit"></span>' +
            '</a> '
            }
        ],
        enablePaging: true,
        showFooter: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions,
        multiSelect: false,
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        //TODO: Fix Bug when deleting, error deleting a delete template id
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                console.log(rowItem);
                $rootScope.$broadcast('recordSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };


    // Calls the rest method to save a transformation.
    $scope.updateRecord = function () {
        TransformationFactory.update($scope.transformation).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('recordSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };



    // Picks up the event broadcasted when the transformation is selected from the grid and perform the transformation load by calling
    // the appropiate rest service.
    $scope.$on('recordSelected', function (event, id) {
        console.log('recordSelected');
        console.log(id);
        if(id != null) {
            $scope.showRecord(id);
        }
    });

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRecord = function (row) {
        console.log('Calling - deleteRecord');

        TransformationFactory.delete({id: row.entity.id}).$promise.then(
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
            }
        );
    };




    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        TransformationsFactory.query().$promise.then(
            function (data) {
                // success handler
                $scope.transformations = data || [];
            }
        );
    };

    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });


    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.transformations = null;
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };


    // callback for ng-click 'showRecord':
    $scope.showRecord = function (transformId) {
        console.log('Calling service- showRecord');
        TransformationFactory.show({id: transformId}).$promise.then(
            function(data) {
                console.log(data);
                $scope.transformation = data || [];
            },
            function () {
                // Broadcast the event for a server error.
                $scope.transformation = [];
                $rootScope.$broadcast('error');
            }
        )
    };


    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [25, 50, 100],
        pageSize: 25,
        currentPage: 1
    };
    $scope.setPagingData = function (data, page, pageSize) {
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.myData = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data;
            if (searchText) {
                var ft = searchText.toLowerCase();

                TransformationsFactory.query().$promise.then(
                    function (largeLoad) {
                        // success handler
                        data = largeLoad.filter(function (item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data, page, pageSize);
                    }
                );

            } else {
                TransformationsFactory.query().$promise.then(
                    function (largeLoad) {
                        // success handler
                        $scope.setPagingData(largeLoad, page, pageSize);
                    }
                );
            }
        }, 100);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
wordxmlDownloadsApp.controller('alertMessagesController', function ($scope) {
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