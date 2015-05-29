var wordxmlUserApp = angular.module('wordxmlApp.users.controller', []);

wordxmlUserApp.controller('UserManageCtrl', function ($scope, $http) {

        /**
         * USER STUFF
         */

        $http.get('/user/list').success(function (data) {
            $scope.users = data;
        });

    })
    .controller('UserProfileCtrl', function ($scope, $http, $stateParams) {
        /**
         * USER PROFILE STUFF
         */

        $http.get('/user/' + $stateParams.userId).success(function (data) {
            $scope.user = data;
        });

    });