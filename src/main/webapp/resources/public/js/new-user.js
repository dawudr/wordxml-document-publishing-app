angular.module('newUserApp', ['common', 'spring-security-csrf-token-interceptor', 'editableTableWidgets'])
    .controller('NewUserCtrl', ['$scope', '$http', function ($scope, $http) {

        $scope.vm.success = false;

        $scope.createUser = function () {
            console.log('Creating user: ' + $scope.vm.firstname + ' ' + $scope.vm.lastname + ' with username ' + $scope.vm.username + ' and password ' + $scope.vm.password);

            $scope.vm.submitted = true;

            if ($scope.form.$invalid) {
                $scope.vm.appReady = true;
                return;
            }

            var postData = {
                firstname: $scope.vm.firstname,
                lastname: $scope.vm.lastname,
                username: $scope.vm.username,
                plainTextPassword: $scope.vm.password,
                email: $scope.vm.email
            };

            $http({
                method: 'POST',
                url: '/user',
                data: postData,
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "text/plain"
                }
            })
            .then(function (response) {
                if (response.status == 200) {
                    //$scope.login($scope.vm.userName, $scope.vm.password);
                    $scope.vm.success = true;
                    $scope.vm.errorMessages = [];
                    console.log("Successful user creation: " + response.data);
                }
                else {
                    $scope.vm.errorMessages.push({description: response.data});
                    console.log("failed user creation: " + response.data);
                }

                $scope.vm.appReady = true;

            });
        }
    }]);