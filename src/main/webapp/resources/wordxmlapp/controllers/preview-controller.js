var wordxmlDocumentApp = angular.module('wordxmlApp.preview', ['ngResource', 'ui.bootstrap', 'ngSanitize', 'ngJSONPath', 'wordxmlApp.download.service']);

wordxmlDocumentApp.controller('PreviewCtrl', function ($scope, $modal, $http, PreviewJSONFactory, $sanitize, jsonPath) {
    /**
     * PREVIEW STUFF
     */

    /* MODAL CODE */
    $scope.showModal = function(transformation) {

        $scope.transformationDetails = transformation;

        $scope.opts = {
            backdrop: true,
            backdropClick: true,
            dialogFade: false,
            keyboard: true,
            templateUrl : 'wordxmlapp/views/preview-view.html',
            controller : ModalInstanceCtrl,
            size: 'lg',
            resolve: {} // empty storage
        };


        $scope.opts.resolve.item = function() {
            return angular.copy({transformation:$scope.transformationDetails}); // pass name to Dialog
        }

        var modalInstance = $modal.open($scope.opts);

        modalInstance.result.then(function(){
            //on ok button press
        },function(){
            //on cancel button press
            console.log("Modal Closed");
        });
    };

    var ModalInstanceCtrl = function($scope, $modalInstance, $modal, item) {
        console.log("Modal opening");
        $scope.item = item;
        console.log(item);

        $scope.ok = function () {
            $modalInstance.close();
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };


        PreviewJSONFactory.show({id: $scope.item.transformation.id}).$promise.then(
            function (data) {
                console.log("Transformation Content as JSON data:");
                console.log(data);
                $scope.unit = data;
            },
            function () {
                // Broadcast the event for a server error.
                $scope.unit = [];
            }
        )

    }


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



    // collect data and colspan as collection of rows celldata
    $scope.tabulateContents = function(table) {
        var result = [];
        rows = jsonPath(table, '$..cells')

        // Get count cell in header row for colspan
        var numberOfHeadColumns = jsonPath(table, '$..cells.@count')[0]
        //console.log('numberOfHeadColumns=' + numberOfHeadColumns)

        // Get Rows
        angular.forEach(rows, function (rowHead, i) {
            //result.push(row)
            //console.log('rowHead:' + i)
            //console.log(rowHead)

            // Get count cell in row for colspan
            var numberOfColumns = jsonPath(rowHead, '$.@count')[0]

            // Get cells
            var cells = jsonPath(rowHead, '$..cell')
            var resultRow = []
            angular.forEach(cells, function (cell, j) {

                if(angular.isArray(cell)) {
                    angular.forEach(cell, function (cellContent) {
                        console.log('cellContent...')
                        console.log(cellContent)

                        var resultcell = $scope.getContentAsHtml(cellContent)
                        //console.log(resultcell)
                        resultRow.push(resultcell)
                    })
                } else {
                    console.log('cell...')
                    console.log(cell)
                    var resultcell = $scope.getContentAsHtml(cell)
                    //console.log(resultcell)
                    resultRow.push(resultcell)
                }

            })
            result.push(resultRow);

        })
        return result;
    }


    // process Cell and output cells as HTML
    $scope.getContentAsHtml = function(cellContent) {

        // Get cell
        var resultcell = {}
        var content = ''
        var contentHeader = jsonPath(cellContent, '$.#text')[0]
        var contentBodyList = jsonPath(cellContent, '$.list')[0]

        resultcell.colspan = jsonPath(cellContent, '$.@colspan')[0]
        resultcell.rowspan = jsonPath(cellContent, '$.@rowspan')[0]

        /* CONTENTS OF CELL*/

        /* Header row of Assessment Criteria */
        if (angular.isString(contentHeader)) {
            content += contentHeader

            /* Assessment Criteria */
        } else if (angular.isObject(contentBodyList)) {
            content += $scope.formatCellAsHtml(contentBodyList)

            /* Learning Aims and UnitContent */
        } else if (angular.isObject(cellContent)) {
            content += $scope.formatCellAsHtml(cellContent)

            /* Learning Aims and UnitContent - UnitContent */
        } else if (angular.isString(cellContent)) {
            // Bug hack to ignore '1' in first column
            if (cellContent != '1') {
                content += cellContent
            } else {
                content += ''
            }
        }

        // add content from Arrays, ignore text outside
        //if(!angular.isString(cell)) {
        resultcell.content = content

        return resultcell
    }




    // Convert cell with object into key value pairs to HTML
    $scope.formatCellAsHtml = function (cell) {
        var contentbuilder = ''
        angular.forEach(cell, function(value, key) {
            if($scope.isContentField(key) && angular.isString(value)) {
                // Collect content as HTML
                //contentbuilder += ('<p><strong>' + key + '</strong> ' + value + ' <p/>')
                if((key != '') && (value != '')) {
                    contentbuilder += ('<div>' + value + '<span class="pull-right label label-default">' + key + '</span></div>')
                }
            } else if(angular.isObject(value)) {
                /* SAO Assignment or Assessment Criteria */
                contentbuilder += $scope.flattenNestedObjects(value)

            } else {
                // Add meta data as a child
                //resultcell[key] = value
                if($scope.isContentField(key)) {
                    console.log('WARN! - Unmapped data- key=' + key + ' value=' + value)
                }

            }
        })
        return contentbuilder
    }



    // Flattens objects and outputs key value pair items as html
    $scope.flattenNestedObjects = function (object) {
        var contentbuilder = ''
        angular.forEach(object, function(valueItem, keyItem) {
            // Another nested item
            if($scope.isContentField(keyItem) && angular.isString(valueItem)) {
                //contentbuilder += ('<p><strong>' + keyItem + '</strong> ' + valueItem + ' <p/>')
                if((keyItem != '') && (valueItem != '')) {
                    contentbuilder += ('<div>' + valueItem + '<span class="pull-right label label-default">' + keyItem + '</span></div>')
                }
            }
        })
        return contentbuilder
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

});

