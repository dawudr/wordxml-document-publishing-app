var wordxmlUserApp = angular.module('wordxmlApp.users.controller',  ['ngResource',  'ui.bootstrap', 'wordxmlApp.users.service']);

wordxmlUserApp.controller('UserManageCtrl', function ($scope,  $rootScope, UsersFactory, UserFactory) {

        /**
         * USER STUFF
         */

    // Initialise Users Management page
    $scope.users = [];
    $scope.userroles = [
        {id:'INACTIVE', name:'INACTIVE'},
        {id:'ROLE_ADMIN', name:'ADMIN'},
        {id:'ROLE_EDITOR', name:'EDITOR'},
        {id:'ROLE_AUTHOR', name:'AUTHOR'},
        {id:'ROLE_VIEWER', name:'VIEWER'}
    ];

    // Modal Dialog for settings
    $scope.showUserModal = false;
    $scope.toggleModal = function(){
        $scope.showUserModal = !$scope.showUserModal;
    };


    // Set Modal width
    $(".modal-short").on("show.bs.modal", function() {
        var height = $(window).height() - 400;
        $(this).find(".modal-body").css("max-height", height);
    });

    $("#userModal").on("hidden.bs.modal", function () {
        // Broadcast the event to refresh the grid.
        //$rootScope.$broadcast('refreshGrid');
    });

    // Set the default value of inputType
    $scope.inputType = 'password';
    $scope.passwordCheckbox = false;

    // Hide & show password function
    $scope.hideShowPassword = function(){
        $scope.passwordCheckbox=!$scope.passwordCheckbox;
        if ($scope.inputType == 'password')
            $scope.inputType = 'text';
        else
            $scope.inputType = 'password';
    };


    // Initialise User Management page
    $scope.myUser = [];


    UsersFactory.query().$promise.then(
        function(data) {
            console.log(data);
            $scope.users = data;
        }
    );



    // callback for ng-click 'createNewUser':
    $scope.createNewUser = function (myUser) {
        console.log('Calling service- createNewUser')
        UsersFactory.create(myUser).$promise.then(
            function () {
                // Update the current $scope.users if success
                $scope.users.push(myUser);
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('recordCreated');
            },
            function (response) {
                console.log(response);
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    // callback for ng-click 'updateUser':
    $scope.updateUser = function (myUser) {
        console.log('Calling service- updateUser');
        console.log(myUser);

        UserFactory.update(myUser).$promise.then(
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
        $scope.changepasswordbutton=true;
        $scope.editbutton=true;
    };


    // callback for ng-click 'addNewUser':
    $scope.addNewUser = function () {
        console.log('Calling service- addNewUser')
        $scope.myUser = {
            firstname: "",
            lastname: "",
            username: "",
            password: "",
            email: "",
            role: "UNASSIGNED"
        };
        //$scope.templates.push($scope.myTemplate);
        $scope.toggleModal();
    }

    $scope.saveForm = function() {
        console.log('Calling service- saveForm')
        console.log($scope.myUser);
        if($scope.myUser != null && $scope.myUser.id == null) {
            $scope.createNewUser($scope.myUser);
        } else {
            $scope.updateTemplate($scope.myUser);
        }
        $scope.editbutton=true;
        $rootScope.$broadcast('recordCreated');
        $scope.toggleModal();
    }


    $scope.deleteUser = function (user) {
        console.log('Calling service- deleteUser');
        console.log(user);

        swal({
            title: "Confirm Delete",
            text: "Are you sure you want to delete user: " + user.username + " (Id: " + user.id + ") ?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes",
            closeOnConfirm: false },
            function(){

                // get index of user record to remove
                var index = $scope.users.indexOf(user);

                UserFactory.delete({id: user.id}).$promise.then(
                    function () {
                        // remove user from list so the page updates
                        $scope.users.splice(index, 1);

                        swal("Deleted!", "User has been deleted.", "success");

                        // Broadcast the event to display a delete message.
                        $rootScope.$broadcast('recordDeleted');
                    },
                    function () {
                        // Broadcast the event for a server error.
                        $rootScope.$broadcast('error');
                    });
            });

    }




});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
wordxmlUserApp.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('recordSaved', function () {
        $scope.alerts = [
            { type: 'success', icon: 'fa fa-2x fa-check', msg: 'Record updated successfully!' }
        ];
    });
    // Picks up the event to display a saved message.
    $scope.$on('recordCreated', function () {
        $scope.alerts = [
            { type: 'success', icon: 'fa fa-2x fa-check', msg: 'Record created successfully!' }
        ];
    });
    // Picks up the event to display a deleted message.
    $scope.$on('recordDeleted', function () {
        $scope.alerts = [
            { type: 'success', icon: 'fa fa-2x fa-check', msg: 'Record deleted successfully!' }
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