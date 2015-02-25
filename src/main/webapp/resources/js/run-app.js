var dashboardApp = angular.module('dashboardApp', ['editableTableWidgets', 'frontendServices']);

dashboardApp.controller('DashboardCtrl','UserService', '$timeout',
    function ($scope, $http, $filter, UserService, $timeout) {

    $scope.vm = {
        currentPage: 1,
        totalPages: 0,
        isSelectionEmpty: true,
        errorMessages: [],
        infoMessages: []
    };

    function showErrorMessage(errorMessage) {
        clearMessages();
        $scope.vm.errorMessages.push({description: errorMessage});
    }

    function updateUserInfo() {
        UserService.getUserInfo()
            .then(function (userInfo) {
                $scope.vm.userName = userInfo.userName;
                $scope.vm.email = userInfo.email;
                $scope.vm.firstname = userInfo.firstname;
                $scope.vm.lastname = userInfo.lastname;
                $scope.vm.role = userInfo.role;
            },
            function (errorMessage) {
                showErrorMessage(errorMessage);
            });
    }


    $http.get('/transformation/list').success(function (data) {
        $scope.transformations = data;

        var sortedArray = $filter('orderBy')($scope.transformations, '-date');
        var lastDate = sortedArray[sortedArray.length - 1].date;
        $scope.lastUpdateDate = lastDate;
    });


    // Today
    var d = new Date();
    var curr_date = d.getDate();
    var curr_month = d.getMonth()+1;
    var curr_year = d.getFullYear();

    $scope.dateToday = moment().format('YYYY-MM-DD HH:mm:ss');  //Date.parse(curr_month + "/" + curr_date + "/" + curr_year);
    $scope.dateTomorrow = moment().add(1, 'days').format('YYYY-MM-DD HH:mm:ss'); //Date.parse(moment().add(1, 'days'));
    $scope.dateLastWeek = moment().subtract(1, 'week').format('YYYY-MM-DD HH:mm:ss'); //Date.parse(moment().subtract(1, 'week'));
    $scope.dateLastMonth = moment().subtract(1, 'month').format('YYYY-MM-DD HH:mm:ss'); //Date.parse(moment().subtract(1, 'month'));
    $scope.dateRange = "";

    $scope.eventDateFilter = function(column) {
        if(column === 'today') {
            $scope.dateRange = $scope.dateToday;
            console.log($scope.dateToday)
        } else if (column === 'pastWeek') {
            $scope.dateRange = $scope.dateLastWeek;
            console.log($scope.dateLastWeek)
        } else if (column === 'pastMonth') {
            $scope.dateRange = $scope.dateLastMonth;
            console.log($scope.dateLastMonth)

        } else if (column === 'future') {
            $scope.dateRange = $scope.dateTomorrow;
            console.log($scope.dateTomorrow)

        } else {
            $scope.dateRange = "";
        }
    };

/*
    var sortedArray = $filter('orderBy')($scope.transformations, '-date');
    var lastDate = sortedArray[sortedArray.length - 1].date;
    $scope.lastUpdateDate = lastDate;

     $scope.recentTransformationsCount = $filter("filter")($scope.transformations, {generalStatus : 0 }).length;

     $scope.recentAlertsCount = ($scope.transformations.length) - ($filter("filter")($scope.transformations, {transformStatus : 1 }).length);

*/




    $scope.recentTransformations = $filter("filter")($scope.transformations, {generalStatus : 0 })

    $scope.recentAlertsCount = $filter("filter")($scope.transformations, {transformStatus : 1 });

    $scope.logout = function () {
        UserService.logout();
    }

});
