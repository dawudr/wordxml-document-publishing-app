angular.module('loginApp', ['common', 'spring-security-csrf-token-interceptor', 'editableTableWidgets'])
    .controller('LoginCtrl', ['$scope', '$http', function ($scope, $http) {

        $scope.onLogin = function () {
            console.log('Attempting login with username ' + $scope.vm.username + ' and password ' + $scope.vm.password);

            $scope.vm.submitted = true;

            if ($scope.form.$invalid) {
                $scope.vm.appReady = true;
                return;
            }
            $scope.login($scope.vm.username, $scope.vm.password);
        };

    }]);