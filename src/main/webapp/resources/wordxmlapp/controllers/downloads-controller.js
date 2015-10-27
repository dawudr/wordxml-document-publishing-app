var wordxmlDownloadsApp = angular.module('wordxmlApp.downloads.controller',  ['ngResource', 'ngGrid', 'ui.bootstrap','ngSanitize','wordxmlApp.transformation.service','wordxmlApp.download.service']);


wordxmlDownloadsApp.controller('DownloadsCtrl', function ($scope, $http, $rootScope, TransformationsFactory, TransformationFactory, TemplatesFactory, $sanitize) {

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

    // Populates list of template for select
    TemplatesFactory.query().$promise.then(
        function(data) {
            $scope.templates = data;
        }
    );

    // Populates transforms list for ng-grid
    TransformationsFactory.query().$promise.then(
        function (data) {
            // success handler
            $scope.transformations = data || [];
        }
    );

    $scope.pagingOptions = { pageSizes: [25, 50, 100], pageSize: 25, totalServerItems: 0, currentPage: 1 }

    $scope.mySelections = [];
    // Button delete instead of checkbox
    var ct_nocheck='<button type="button" ng-click="deleteRecord(row.entity)" class="btn btn-sm btn-danger" title="Delete"><span class="fa fa-1x fa-times"></span></button>';
    // Hidden checkbox
    // var ct_nocheck="<div class=\"ngSelectionCell\"><input style=\"display:none\" tabindex=\"-1\" class=\"ngSelectionCheckbox\" type=\"checkbox\" ng-checked=\"row.selected\" /></div>";

    $scope.gridOptions = {
        data: 'transformations',
        checkboxHeaderTemplate: '<input class="ngSelectionHeader" type="checkbox" ng-model="allSelected" ng-change="toggleSelectAll(allSelected)"/>',
        showSelectionCheckbox: true,
        selectWithCheckboxOnly: false,
        //checkboxCellTemplate:ct_nocheck,
        jqueryUITheme: true,
        enableCellSelection: false,
        enableRowSelection: true,
        enableCellEdit: false,
        enableCellEditOnFocus: false,
        rowHeight: 40,
        columnDefs: [
            {name: 'id', field: 'id', width: 40, displayName: '#', enableCellEdit: false},
            {field:'qanNo', width: 100, displayName:'Qan', enableCellEdit: true},
            {field:'unitNo', width: 60, displayName:'Unit #', enableCellEdit: true},
            {field:'unitTitle', displayName:'Title:', enableCellEdit: true},
            {field:'wordfilename', displayName:'File:', enableCellEdit: true},
            {field:'lastmodified', width: 140, displayName:'Created', enableCellEdit: true},
            //{field:'transformStatus', displayName:'Transform result', enableCellEdit: true},
            {displayName:'Download', minWidth: 150, width: 150, enableCellEdit: false, cellTemplate:
            '<a href="/download/{{row.entity.imageId}}" class="btn btn-success btn-sm" title="Download Word file">' +
            '<i class="fa fa-2x fa-file-word-o"></i>' +
            '</a> ' +
            '<a href="/transformation/xml/{{row.entity.id}}/download/" class="btn btn-success btn-sm" title="Download Word XML file">' +
            '<i class="fa fa-2x fa-file-code-o"></i>' +
            '</a> ' +
            '<a href="/transformation/pqs/{{row.entity.id}}/download/" class="btn btn-success btn-sm" title="Download PQS XML file">' +
            '<i class="fa fa-2x fa-file-text-o"></i>' +
            '</a> '
            },
            {displayName:'View', minWidth: 50, width: 50, enableCellEdit: false, cellTemplate:
            //'<button type="button" ng-click="deleteRecord(row.entity)" class="btn btn-sm btn-danger" title="Delete">' +
            //'<span class="fa fa-2x fa-times"></span>' +
            //'</button> ' +
            //'<a href="#/document/{{row.entity.id}}" class="btn btn-primary btn-sm" title="Preview">' +
            '<button ng-controller="PreviewCtrl" type="button" ng-click="showModal(row.entity)" class="btn btn-primary btn-sm" title="Preview">' +
            '<span class="fa fa-2x fa-desktop"></span>' +
            '</button> '
            }
        ],
        enablePaging: true,
        showFooter: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions,
        multiSelect: false,
        selectedItems: $scope.mySelections,
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        //TODO: Fix Bug when deleting, error deleting a delete template id
        afterSelectionChange: function (rowItem) {
            //console.log('SelectedItem...')
            //console.log($scope.gridOptions.selectedItems)
            if (rowItem.selected) {
                //console.log('RowItem...')
                //console.log(rowItem);
                //$rootScope.$broadcast('recordSelected', $scope.gridOptions.selectedItems[0].id);
                $rootScope.$broadcast('recordSelected', rowItem.entity.id);
            }
        }
    };


    // Calls the rest method to save a transformation.
    $scope.updateRecord = function (transformation) {
        TransformationFactory.update(transformation).$promise.then(
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

    $scope.deleteAll = function(mySelections) {
        console.log('Calling - deleteAll');

        swal({
                title: "Confirm Delete",
                text: "Are you sure you want to delete transformations ?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes",
                closeOnConfirm: true },
            function() {
                angular.forEach(mySelections, function (transformation) {
                    $scope.deleteRecord(transformation)
                })
            }
        )
        //swal("Deleted!", "Transformation has been deleted.", "success");
    }

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRecord = function (transformation) {
        console.log('Calling - deleteRecord');
        console.log(transformation);

                TransformationFactory.delete({id: transformation.id}).$promise.then(
                    function () {
                        $scope.transformation = null;
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
                console.log("Transformation Record as JSON data:");
                console.log(data);
                $scope.transformation = data || [];

/*                $http.get('/transformation/xml/' + transformId).success(function (data) {
                    $scope.xmlUnit = data;
                });*/

            },
            function () {
                // Broadcast the event for a server error.
                $scope.transformation = [];
                $rootScope.$broadcast('xmlloaderror');
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

    // this method filters, formats and prints the previews the data
    $scope.filterContentField = function(items) {
        var result = {};
        var regex = new RegExp('@.*');
        var title = '';

        // remove items with keys prefixed with '@' and remove the key field where it is same as title.
        // remove items with keys prefixed with '@' and remove the key field where it is same as title.
        angular.forEach(items, function(value, key) {
            if(!regex.test(key + '')) {
                if (key.replace(/ /g, '').toLowerCase() == title) {
                    result[''] = value;
                    /*                } else if (key.toLowerCase() == 'table') {
                     result[''] = value;*/
                } else {
                    // Set first letter to uppercase to use as label
                    result[key.charAt(0).toUpperCase()+ key.slice(1)] = value;
                }
            } else if(key.toLowerCase() == '@title') {
                title = value.replace(/ /g,'').toLowerCase();
            }
        });
        return result;
    }

    // this method filters content fields
    $scope.isContentField = function (key) {
        // remove items with keys prefixed with '@'
        var regex = new RegExp('@.*');
        return !regex.test(key + '')
    }

    var urlRegEx = /^https?:\/\//
    $scope.type = function (thing) {
        switch(typeof thing){
            case "object":
                if(Object.prototype.toString.call(thing) === "[object Array]"){
                    return 'array'
                } else if (thing == null) {
                    return 'null'
                } else {
                    return 'hash'
                }
            case "string":
                if(urlRegEx.test(thing)){
                    return "url"
                } else {
                    return "string"
                }
            default:
                return typeof thing
        }
    }

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

    /* Angular-UI modal for preview and xml view */
    $scope.openPreview = function (id) {
        console.log('openPreview');
        console.log(id);
        var modalInstance = $modal.open({
            templateUrl: '/preview.html?id=' + id,
            //templateUrl: 'myModalContent.html',
            controller: 'DocumentCtrl'
        });
    };


});


wordxmlDownloadsApp.directive('htmlText', function(){
    return {
        'restrict': 'A',
        'require': 'ngModel',
        'link': function(scope,element,attrs,model) {
            model.$formatters.push(function(val){
                return val.htmlField;
            });

            model.$parsers.push(function(val){
                model.$modelValue.htmlField = val;
            });
        }
    };
});


// Create a controller with name alertMessagesController to bind to the feedback messages section.
wordxmlDownloadsApp.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('recordSaved', function () {
        $scope.alerts = [
            { type: 'success', icon: 'fa fa-2x fa-check', msg: 'Record saved successfully!' }
        ];
    });
    // Picks up the event to display a saved message.
    $scope.$on('recordCreated', function () {
        $scope.alerts = [
            { type: 'success', icon: 'fa fa-2x fa-check',  msg: 'Record created successfully!' }
        ];
    });
    // Picks up the event to display a deleted message.
    $scope.$on('recordDeleted', function () {
        $scope.alerts = [
            { type: 'success', icon: 'fa fa-2x fa-check', msg: 'Record(s) deleted successfully!' }
        ];
    });
    // Picks up the event to display a selected message.
    $scope.$on('recordSelected', function () {
        $scope.alerts = [
            { type: 'success', icon: 'fa fa-2x fa-check', msg: 'Record selected' }
        ];
    });
    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', icon: 'fa fa-2x fa-exclamation-circle', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

