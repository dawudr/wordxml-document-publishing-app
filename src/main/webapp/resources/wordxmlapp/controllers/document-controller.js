var wordxmlDocumentApp = angular.module('wordxmlApp.document.controller', ['ngResource', 'ui.codemirror', 'ui.bootstrap', 'ngSanitize', 'wordxmlApp.transformation.service', 'wordxmlApp.download.service']);

wordxmlDocumentApp.controller('DocumentCtrl', function ($scope, $http, $rootScope, TransformationsFactory, TransformationFactory, PreviewJSONFactory, PreviewXMLFactory, $stateParams, $sanitize) {
    /**
     * DOCUMENT DETAIL STUFF
     */

    console.log("DocumentCtrl");
    console.log($stateParams);

    console.log('Calling service- showRecord');
    TransformationFactory.show({id: $stateParams.transformationId}).$promise.then(
        function (data) {
            console.log("Transformation Record as JSON data:");
            console.log(data);
            $scope.transformation = data || [];

        },
        function () {
            // Broadcast the event for a server error.
            $scope.transformation = [];
            $rootScope.$broadcast('error');
        }
    )

    PreviewJSONFactory.show({id: $stateParams.transformationId}).$promise.then(
        function (data) {
            console.log("Transformation Content as JSON data:");
            console.log(data);
            $scope.unit = data;
        },
        function () {
            // Broadcast the event for a server error.
            $scope.unit = [];
            $rootScope.$broadcast('error');
        }
    )


    $http.get('/transformation/xml/' + $stateParams.transformationId).success(function (data) {
        $scope.xmlUnit = data;
    });


    // this method filters, formats and prints the previews the data
    $scope.filterContentField = function (items) {
        var result = {};
        var regex = new RegExp('@.*');
        var title = '';

        // remove items with keys prefixed with '@' and remove the key field where it is same as title.
        angular.forEach(items, function (value, key) {
            if (!regex.test(key + '')) {
                // If title is repeated don't output again
                if (key.replace(/ /g, '').toLowerCase() == title) {
                    result[''] = value;
                    /*                } else if (key.toLowerCase() == 'table') {
                     result[''] = value;*/
                } else {
                    // Set first letter to uppercase to use as label
                    result[key.charAt(0).toUpperCase() + key.slice(1)] = value;
                }
            } else if (key.toLowerCase() == '@title') {
                title = value.replace(/ /g, '').toLowerCase();
            }
        });
        return result;
    }

    $scope.tabulateContents = function(table) {
        var result = [];
        $scope.testvar = table;
        // Get the rows
        angular.forEach(table, function(tablevalue) {

            if (angular.isArray(tablevalue)) {
                //console.log('tablevalue is array:');
                //console.log(tablevalue);
                // Single Column tables
                angular.forEach(tablevalue, function(rowsvalue_single) {
                    if (angular.isArray(rowsvalue_single)) {
                        //console.log('all_rowsvalue_single is array');
                        //console.log(rowsvalue_single);

                    } else if (angular.isObject(rowsvalue_single)) {
                        //console.log('all_rowsvalue_single is other:' + rowsvalue_single);
                        var row_single = rowsvalue_single['row'];
                        //console.log(row_single);

                        if (angular.isArray(row_single)) {
                            //console.log('row_single is array:==>');
                            //console.log(row_single);
                            angular.forEach(row_single, function(cells_single) {
                                if (angular.isArray(cells_single)) {
                                    //console.log('cells_single is array');
                                    //console.log(cells_single);

                                } else if (angular.isObject(cells_single)) {

                                    // Get all cells
                                    var cell_single = cells_single['cells'];
                                    //console.log('cells_single:');
                                    //console.log(cell_single);

                                    // Get cell
                                    var cell_singlevalue = cell_single['cell'];
                                    //console.log('cells_singlevalue:-->');
                                    //console.log(cell_singlevalue);

                                    // Get Rows data
                                    if(angular.isArray(cell_singlevalue)) {
                                        // Array of Maps
                                        result.push(cell_singlevalue);
                                    } else if (angular.isObject(cell_singlevalue)) {
                                        // Map of items
                                        //result.push(cell_singlevalue);
                                        result.push($scope.filterContentMap(cell_singlevalue));
                                    }
                                }
                            })
                        } else {
                            //console.log('rows_single is other:-' + rows_single);
                            //console.log(rows_single);
                        }
                    }
                }) // End of loop rowsvalue_single

            } else if (angular.isObject(tablevalue)) {
                //console.log('tablevalue:');
                //console.log(tablevalue['row']);
                var rows = tablevalue['row'];

                if (angular.isArray(rows)) {
                    //console.log('rows is array:');

                    angular.forEach(rows, function(rowsvalue) {
                        //console.log('Looping rows is array:');
                        //console.log(rowsvalue);

                        if (angular.isArray(rowsvalue)) {
                            //console.log('rowsvalue is array');
                            //console.log(rowsvalue);

                        } else if (angular.isObject(rowsvalue)) {
                            var cells = rowsvalue['cells'];
                            //console.log('cells:');
                            //console.log(cells);
                            if (angular.isArray(cells)) {
                                //console.log('cells is array');
                                //console.log(cells);

                            } else if (angular.isObject(cells)) {
                                var cell = cells['cell'];
                                //console.log(cell);

                                // Get Rows data
                                if(angular.isArray(cell)) {
                                    // Array of Maps
                                    result.push(cell);
                                } else if (angular.isObject(cell)) {
                                    // Map of items
                                    //result.push(cell);
                                    result.push($scope.filterContentMap(cell));
                                }
                            }
                        }
                    }) // End of loop rows
                    //console.log('rows:-->');
                    //console.log(rows);

                } else {
                    //console.log('rows is other:' + rows);
                }

                //result.push(data_rows);
            } // End of isObject(tablevalue)
        }); // End of Table
        return result;
    }

    $scope.filterContentMap = function(key) {
        var data_cell = {};

        for(var k in key) {
            if($scope.isContentField(k)) {
                data_cell[k] = key[k];
            }
        }
        return data_cell;
    }


    $scope.isContentField = function(key) {
        // remove items with keys prefixed with '@'
        var regex = new RegExp('@.*');
        return angular.isString(key) && !regex.test(key + '')
    }

    var urlRegEx = /^https?:\/\//
    $scope.type = function (thing) {
        switch (typeof thing) {
            case "object":
                if (Object.prototype.toString.call(thing) === "[object Array]") {
                    return 'array'
                } else if (thing == null) {
                    return 'null'
                } else {
                    return 'hash'
                }
            case "string":
                if (urlRegEx.test(thing)) {
                    return "url"
                } else {
                    return "string"
                }
            default:
                return typeof thing
        }
    }


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

    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.transformations = null;
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };


});

