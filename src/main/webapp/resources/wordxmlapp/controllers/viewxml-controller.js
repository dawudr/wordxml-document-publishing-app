var wordxmlDocumentApp = angular.module('wordxmlApp.viewxml', ['ngResource', 'ui.bootstrap', 'ngSanitize', 'wordxmlApp.download.service']);

wordxmlDocumentApp.controller('ViewXMLCtrl', function ($scope, $modal, $http, PreviewXMLFactory, $sanitize) {
    /**
     * VIEW XML STUFF
     */

    /* MODAL CODE */
    $scope.showModal = function(transformation) {

        $scope.transformationDetails = transformation;

        $scope.opts = {
            backdrop: true,
            backdropClick: true,
            dialogFade: false,
            keyboard: true,
            templateUrl : 'wordxmlapp/views/viewxml-view.html',
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

        $http.get('/transformation/xml/' + $scope.item.transformation.id).success(function (data) {
            $scope.xmlUnit = data;
        });

    }

});

