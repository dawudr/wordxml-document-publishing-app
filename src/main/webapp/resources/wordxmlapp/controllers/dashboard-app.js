var dashboardApp = angular.module('dashboardApp', []);

dashboardApp.service('DashboardServicex', function ($http) {
    var dashboardData = [];

    var promise = $http.get('/transformation/list').success(function (data) {
        dashboardData = data;
    });

    return {
        promise: promise,
        setTransformationsData: function (data) {
            dashboardData = data;
        },
        getTransformationsData: function () {
            return dashboardData;//.getSomeData();
        }
    };
});

dashboardApp.controller('DashboardCtrlx', function ($scope, $http, $filter, $location, TransformationFactory, TransformationsFactory) {

    /**
     * TRANSFORMATIONS STUFF
     */

    $http.get('/transformation/list').success(function (data, filterFilter) {
        $scope.transformations = data;
        if ($scope.transformations == null) {
            $scope.transformations = [];
        }

        if ($scope.transformations.length > 0) {
            var sortedArray = $filter('orderBy')($scope.transformations, '-date');
            var lastDate = sortedArray[sortedArray.length - 1].date;
            $scope.lastUpdateDate = lastDate;

            $scope.totalDocumentsCount = $scope.transformations.length;
            $scope.recentNewDocumentsCount = $filter("filter")($scope.transformations, {generalStatus: 'GENERAL_STATUS_UNREAD'}).length;
            $scope.recentAlertsCount = $filter("filter")($scope.transformations, {transformStatus: 'TRANSFORM_STATUS_FAIL'}).length;
            $scope.recentTransformations = $filter("filter")($scope.transformations, {generalStatus: 'GENERAL_STATUS_UNREAD'});
        } else {
            $scope.lastUpdateDate = Date();
            $scope.totalDocumentsCount = 0;
            $scope.recentNewDocumentsCount = 0;
            $scope.recentAlertsCount = 0;
            $scope.recentTransformations = [];
        }

        // Pagination stuff
        //$scope.transformationsTestData = [{"id":1,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-23 01:04:06","qanNo":"U/111/0001","wordfilename":"Unit 44_FBC.docx","openxmlfilename":"Unit 44_FBC.xml","iqsxmlfilename":"Unit 44_FBC-iqs.xml","unitNo":"44","unitTitle":"Manufacturing Secondary Machining Processes","author":"Paul Winser","lastmodified":"2015-02-23 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_UNREAD"},{"id":2,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-23 01:04:06","qanNo":"U/222/0001","wordfilename":"Unit 45_FBC.docx","openxmlfilename":"Unit 45_FBC.xml","iqsxmlfilename":"Unit 45_FBC-iqs.xml","unitNo":"45","unitTitle":"Big Data","author":"Paul Winser","lastmodified":"2015-02-23 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_FAIL","message":"Fatal error, please try again","generalStatus":"GENERAL_STATUS_UNREAD"},{"id":3,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-23 01:04:06","qanNo":"R/111/0002","wordfilename":"Unit 46_FBC.docx","openxmlfilename":"Unit 46_FBC.xml","iqsxmlfilename":"Unit 46_FBC-iqs.xml","unitNo":"46","unitTitle":"Business Application of Social Media","author":"Andres Vergara","lastmodified":"2015-02-23 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":4,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-23 01:04:06","qanNo":"R/222/0002","wordfilename":"Unit 47_FBC.docx","openxmlfilename":"Unit 47_FBC.xml","iqsxmlfilename":"Unit 47_FBC-iqs.xml","unitNo":"47","unitTitle":"Business Process Modelling Tools","author":"Andres Vergara","lastmodified":"2015-02-23 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_FAIL_TRANSFORM_XML_TO_IQSXML_XSLT","message":"Iqs transform errors","generalStatus":"GENERAL_STATUS_READ"},{"id":5,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-16 01:04:06","qanNo":"M/111/0003","wordfilename":"Unit 48_FBC.docx","openxmlfilename":"Unit 48_FBC.xml","iqsxmlfilename":"Unit 48_FBC-iqs.xml","unitNo":"48","unitTitle":"Communication Technologies","author":"Andres Vergara","lastmodified":"2015-02-23 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_MODIFIED"},{"id":6,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-16 01:04:06","qanNo":"M/222/0003","wordfilename":"Unit 49_FBC.docx","openxmlfilename":"Unit 49_FBC.xml","iqsxmlfilename":"Unit 49_FBC-iqs.xml","unitNo":"49","unitTitle":"Computer Forensics","author":"Paul Winser","lastmodified":"2015-02-16 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_FAIL_IQS_VALIDATION","message":"IQS Schema Validation failed","generalStatus":"GENERAL_STATUS_READ"},{"id":7,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-16 01:04:06","qanNo":"M/333/0001","wordfilename":"Unit 50_FBC.docx","openxmlfilename":"Unit 50_FBC.xml","iqsxmlfilename":"Unit 50_FBC-iqs.xml","unitNo":"50","unitTitle":"Computer Games Development","author":"Andres Vergara","lastmodified":"2015-02-16 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_FAIL_VALIDATE_WORD","message":"Word Document Validation failed","generalStatus":"GENERAL_STATUS_READ"},{"id":8,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-16 01:04:06","qanNo":"M/333/0002","wordfilename":"Unit 51_FBC.docx","openxmlfilename":"Unit 51_FBC.xml","iqsxmlfilename":"Unit 51_FBC-iqs.xml","unitNo":"51","unitTitle":"Database Development","author":"Andres Vergara","lastmodified":"2015-02-16 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_FAIL_TRANSFORM","message":"Transformation failed","generalStatus":"GENERAL_STATUS_READ"},{"id":9,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-01-23 01:04:06","qanNo":"M/333/0003","wordfilename":"Unit_52.docx","openxmlfilename":"Unit_52.xml","iqsxmlfilename":"Unit_52-iqs.xml","unitNo":"52","unitTitle":"Digital Audio","author":"Andres Vergara","lastmodified":"2015-02-16 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":10,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-01-23 01:04:06","qanNo":"M/444/0001","wordfilename":"Unit_53.docx","openxmlfilename":"Unit_53.xml","iqsxmlfilename":"Unit_53-iqs.xml","unitNo":"53","unitTitle":"Digital Graphics and Animation","author":"Paul Winser","lastmodified":"2015-02-16 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":11,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-01-23 01:04:06","qanNo":"M/444/0002","wordfilename":"Unit_54.docx","openxmlfilename":"Unit_54.xml","iqsxmlfilename":"Unit_54-iqs.xml","unitNo":"52","unitTitle":"Principles and Applications of Electronic Devices and Circuits","author":"Paul Winser","lastmodified":"2015-02-16 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":12,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-01-23 01:04:06","qanNo":"M/444/0003","wordfilename":"Unit_55.docx","openxmlfilename":"Unit_55.xml","iqsxmlfilename":"Unit_55-iqs.xml","unitNo":"55","unitTitle":"Principles of Telecommunications","author":"Paul Winser","lastmodified":"2015-02-16 01:04:06","templatename":"btecnationals","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":13,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-23 01:04:06","qanNo":"U/123/0001","wordfilename":"Unit_18.docx","openxmlfilename":"Unit_18.xml","iqsxmlfilename":"Unit_18-iqs.xml","unitNo":"18","unitTitle":"Computational Thinking","author":"Paul Winser","lastmodified":"2015-02-23 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_UNREAD"},{"id":14,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-23 01:04:06","qanNo":"U/123/0002","wordfilename":"Unit_19.docx","openxmlfilename":"Unit_19.xml","iqsxmlfilename":"Unit_19-iqs.xml","unitNo":"19","unitTitle":"Digital Product Analysis","author":"Andres Vergara","lastmodified":"2015-02-23 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_UNREAD"},{"id":15,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-23 01:04:06","qanNo":"U/123/0003","wordfilename":"Unit_20.docx","openxmlfilename":"Unit_20.xml","iqsxmlfilename":"Unit_20-iqs.xml","unitNo":"20","unitTitle":"Human Computer Interaction","author":"Paul Winser","lastmodified":"2015-02-23 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_FAIL_VALIDATE_WORD","message":"Word Document Validation failed","generalStatus":"GENERAL_STATUS_UNREAD"},{"id":16,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-23 01:04:06","qanNo":"U/123/0004","wordfilename":"Unit_21.docx","openxmlfilename":"Unit_21.xml","iqsxmlfilename":"Unit_21-iqs.xml","unitNo":"21","unitTitle":"IT Security","author":"Paul Winser","lastmodified":"2015-02-23 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_FAIL","message":"Transformation failed","generalStatus":"GENERAL_STATUS_UNREAD"},{"id":17,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-16 01:04:06","qanNo":"R/123/0005","wordfilename":"Unit_22.docx","openxmlfilename":"Unit_22.xml","iqsxmlfilename":"Unit_22-iqs.xml","unitNo":"22","unitTitle":"Managing and Supporting Systems","author":"Andres Vergara","lastmodified":"2015-02-16 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":18,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-16 01:04:06","qanNo":"R/123/0006","wordfilename":"Unit_23.docx","openxmlfilename":"Unit_23.xml","iqsxmlfilename":"Unit_23-iqs.xml","unitNo":"23","unitTitle":"Mobile Apps Development","author":"Andres Vergara","lastmodified":"2015-02-16 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":19,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-16 01:04:06","qanNo":"M/123/0007","wordfilename":"Unit_24.docx","openxmlfilename":"Unit_24.xml","iqsxmlfilename":"Unit_24-iqs.xml","unitNo":"24","unitTitle":"Computational Thinking 8","author":"Paul Winser","lastmodified":"2015-02-23 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_MODIFIED"},{"id":20,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-02-16 01:04:06","qanNo":"M/123/0008","wordfilename":"Unit_25.docx","openxmlfilename":"Unit_25.xml","iqsxmlfilename":"Unit_25-iqs.xml","unitNo":"25","unitTitle":"Multimedia Technologies and Design","author":"Andres Vergara","lastmodified":"2015-02-23 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_MODIFIED"},{"id":21,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-01-23 01:04:06","qanNo":"R/123/0009","wordfilename":"Unit_26.docx","openxmlfilename":"Unit_26.xml","iqsxmlfilename":"Unit_26-iqs.xml","unitNo":"26","unitTitle":"Network Analysis","author":"Andres Vergara","lastmodified":"2015-02-16 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":22,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-01-23 01:04:06","qanNo":"R/123/0010","wordfilename":"Unit_27.docx","openxmlfilename":"Unit_27.xml","iqsxmlfilename":"Unit_27-iqs.xml","unitNo":"27","unitTitle":"Network Operating Systems","author":"Andres Vergara","lastmodified":"2015-02-16 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_READ"},{"id":23,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-01-23 01:04:06","qanNo":"M/123/0011","wordfilename":"Unit_28.docx","openxmlfilename":"Unit_28.xml","iqsxmlfilename":"Unit_28-iqs.xml","unitNo":"28","unitTitle":"Network Solutions and Security","author":"Paul Winser","lastmodified":"2015-02-23 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_MODIFIED"},{"id":24,"user":{"id":1,"username":"test123","password":"$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS","email":"test@email.com","firstname":"test","lastname":"123","role":"ROLE_ADMIN"},"date":"2015-01-23 01:04:06","qanNo":"M/123/0012","wordfilename":"Unit_29.docx","openxmlfilename":"Unit_29.xml","iqsxmlfilename":"Unit_29-iqs.xml","unitNo":"29","unitTitle":"Object Oriented Programming","author":"Andres Vergara","lastmodified":"2015-02-23 01:04:06","templatename":"bteclevel2","transformStatus":"TRANSFORM_STATUS_SUCCESS","message":"No errors were found","generalStatus":"GENERAL_STATUS_MODIFIED"}];
        $scope.filteredTransformations = [];
        $scope.filteredPagnTransformations = [];
        $scope.filteredRecentTransformations = [];
        $scope.totalResults = $scope.transformations.length;
        $scope.numPerPage = 5;
        $scope.numOfPages = Math.ceil($scope.totalResults / $scope.numPerPage);
        //console.log("numOfPages=" + $scope.numOfPages )
        $scope.currentPage = 1; //current page
        $scope.maxSize = 5; //pagination max size
        $scope.entryLimit = 5; //max rows for data table

        $scope.$watch('search', function (term) {
            // Create filtered
            $scope.filteredTransformations = $filter('filter')($scope.transformations, term);
            $scope.filteredRecentTransformations = $filter("filter")($scope.filteredTransformations, {generalStatus: 'GENERAL_STATUS_UNREAD'});
            // Then calculate numPages
            $scope.numOfPages = Math.ceil($scope.filteredTransformations.length / $scope.numPerPage);
            $scope.numOfPagesRecent = Math.ceil($scope.filteredRecentTransformations.length / $scope.numPerPage);
            // then update total items
            $scope.totalResults = $scope.filteredTransformations.length;
            $scope.totalResultsRecent = $scope.filteredRecentTransformations.length;

        })

        $scope.$watch('currentPage + noOfPages', function () {
            var begin = (($scope.currentPage - 1) * $scope.numOfPages)
                , end = begin + $scope.numPerPage;
            //console.log("currentPage=" + $scope.currentPage + " begin=" + $scope.currentPage + " end=" + end);
            $scope.filteredPagnTransformations = $scope.filteredTransformations.slice(begin, end);

            var beginRecent = (($scope.currentPage - 1) * $scope.numOfPagesRecent)
                , endRecent = beginRecent + $scope.numPerPage;
            $scope.filteredPagnRecentTransformations = $scope.filteredRecentTransformations.slice(beginRecent, endRecent);

        });
    })
        .error(function (data, status, headers, config) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log("No data from request" + status);

        });

    // Today
    var d = new Date();
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1;
    var curr_year = d.getFullYear();

    $scope.dateToday = moment().format('YYYY-MM-DD HH:mm:ss');  //Date.parse(curr_month + "/" + curr_date + "/" + curr_year);
    $scope.dateTomorrow = moment().add(1, 'days').format('YYYY-MM-DD HH:mm:ss'); //Date.parse(moment().add(1, 'days'));
    $scope.dateLastWeek = moment().subtract(1, 'week').format('YYYY-MM-DD HH:mm:ss'); //Date.parse(moment().subtract(1, 'week'));
    $scope.dateLastMonth = moment().subtract(1, 'month').format('YYYY-MM-DD HH:mm:ss'); //Date.parse(moment().subtract(1, 'month'));
    $scope.dateRange = "";

    $scope.eventDateFilter = function (column) {
        if (column === 'today') {
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

    $scope.statusIconClass = "";
    $scope.generalStatusIcon = function (status) {
        if (status === 'GENERAL_STATUS_UNREAD') {
            $scope.statusIconClass = 'fa-envelope';
        } else if (status === 'GENERAL_STATUS_READ') {
            $scope.statusIconClass = 'fa-envelope-o';
        } else if (status === 'GENERAL_STATUS_MODIFIED') {
            $scope.statusIconClass = 'fa-pencil-square-o';
        } else if (status === 'GENERAL_STATUS_DELETED') {
            $scope.statusIconClass = 'fa-trash-o';
        } else {
            $scope.statusIconClass = 'fa-question-circle';
        }
    };

    // callback for ng-click 'deleteUser':
    $scope.deleteTransformation = function (id) {
        TransformationFactory.delete({ id: id });
        $scope.transformations = TransformationsFactory.query();
        $location.path('#/downloads');
    };

})
    .filter('startFrom', function () {
        return function (input, start) {
            if (input) {
                start = +start; //parse to int
                return input.slice(start);
            }
            return [];
        }
    })
    .filter('generalStatus', function () {

        var generalStatusDict = {
            GENERAL_STATUS_UNREAD: "New",
            GENERAL_STATUS_READ: "Viewed",
            GENERAL_STATUS_MODIFIED: "Modified",
            GENERAL_STATUS_DELETED: "Deleted"
        };
        return function (generalStatus) {
            return generalStatusDict[generalStatus] || 'Unknown';
        };
    })
    .filter('transformStatus', function () {

        var transformStatusDict = {
            TRANSFORM_STATUS_FILE_UPLOAD_IN_PROGRESS: "Word document upload in progress",
            TRANSFORM_STATUS_EXTRACTION_IN_PROGRESS: "Openxml extraction in progress",
            TRANSFORM_STATUS_TRANSFORM_IN_PROGRESS: "PQS transform in progress",
            TRANSFORM_STATUS_SUCCESS: "Completion with: 100%",
            TRANSFORM_STATUS_FAIL: 'Transform failed with: 0%',
            TRANSFORM_STATUS_FAIL_VALIDATE_WORD: 'Word validation failed',
            TRANSFORM_STATUS_FAIL_EXTRACT_WORD_TO_XML: 'Word -> OpenXml failed',
            TRANSFORM_STATUS_FAIL_TRANSFORM: 'Transform failed',
            TRANSFORM_STATUS_FAIL_TRANSFORM_XML_TO_IQSXML_XSLT: 'Transform to PQS failed',
            TRANSFORM_STATUS_FAIL_XML_TO_IQSXML_XQUERY: 'Transform to PQS tables failed',
            TRANSFORM_STATUS_FAIL_IQS_VALIDATION: 'PQS XML validation failed',
            TRANSFORM_STATUS_FAIL_FILE_WRITE: 'Transformed file could not be saved',
            TRANSFORM_STATUS_SUCCESS2: "Completion with: 100%"
        };
        return function (transformStatus) {
            return transformStatusDict[transformStatus] || 'Error';
        };
    })
    .controller('UserManageCtrl', function ($scope, $http) {
        /**
         * USER STUFF
         */

        $http.get('/user/list').success(function (data) {
            $scope.users = data;
        });

    })
    .controller('DocumentDetailCtrl', function ($scope, $http, $stateParams) {
        /**
         * DOCUMENT DETAIL STUFF
         */

        $http.get('/transformation/' + $stateParams.transformationId).success(function (data) {
            $scope.transformation = data;
        });

        $http.get('/transformation/json/' + $stateParams.transformationId).success(function (data) {
            $scope.specunit = data;
        });

    })
    .controller('UserProfileCtrl', function ($scope, $http, $stateParams) {
        /**
         * USER PROFILE STUFF
         */

        $http.get('/user/' + $stateParams.userId).success(function (data) {
            $scope.user = data;
        });

    })
    .controller('DocumentTransformsCtrl', function ($scope, $http, TemplatesFactory) {
        /**
         * DOCUMENT TRANSFORM STUFF
         */

        $http.get('/transformation/listrecent').success(function (data) {
            $scope.recentTransformations = data;
        });

        $scope.templates = TemplatesFactory.query();
        $scope.myTemplate = $scope.templates[0];

    })
    .controller('SettingsCtrl', function ($scope, TemplatesFactory, TemplateFactory, $location) {
        /**
         * SETTING STUFF
         */
        $scope.templates = [];
        $scope.defaultSelected = "BTEC NATIONALS";

        //$http.get('/template/list').success(function (data) {
            $scope.templates = TemplatesFactory.query(); //data;
            $scope.myTemplate = $scope.templates[0];
            $scope.sectionTypes = [
                {name: '-- Select --', type: null},
                {name: 'Meta data', type: 'META'},
                {name: 'Section title', type: 'HEADER'},
                {name: 'Section content', type: 'SECTION'},
                {name: 'Paragraph text', type: 'PARAGRAPH'},
                {name: 'Table', type: 'TABLE'},
                {name: 'Table row', type: 'ROW'},
                {name: 'Table cell', type: 'CELL'}];
        //});

        // callback for ng-click 'cancel':
        $scope.cancel = function () {
            $location.path('/settings');
        };

        // callback for ng-click 'deleteTemplate':
        $scope.deleteTemplate = function (templateId) {
            TemplateFactory.delete({ id: templateId });
            $scope.templates = TemplatesFactory.query();
        };

        // callback for ng-click 'updateTemplate':
        $scope.updateTemplate = function (myTemplate) {
            $scope.myTemplate = myTemplate;
            TemplateFactory.update($scope.myTemplate);
            $location.path('/settings');
        };

        // callback for ng-click 'createNewTemplate':
        $scope.createNewTemplate = function (myTemplate) {
            console.log(myTemplate);
            TemplateFactory.create(myTemplate);
            $location.path('/settings');
        }

        $scope.addNewTemplate = function () {
            $scope.myTemplate = {"templateName": "Insert template name","description":null,"xsltScriptLocation":null,"xsdScriptLocation":null,"xQueryScriptLocation":null,"templatesection":[]};
            $scope.templates.push($scope.myTemplate);
        }


    })
    .controller('DocumentFileUploadCtrl', function($scope, $http, $filter, $window) {
        /**
         * DOCUMENT UPLOAD STUFF
         */

        var url = '/upload';

        $scope.options = {
            url: url,
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(docx|doc|rtf)$/i
        };
        $scope.loadingFiles = true;
        $http.get(url)
            .then(
            function (response) {
                $scope.loadingFiles = false;
                $scope.queue = response.data.files || [];
            },
            function () {
                $scope.loadingFiles = false;
            }
        );
    })
    .controller('FileDestroyController', [
        '$scope', '$http',
        function ($scope, $http) {
            var file = $scope.file,
                state;
            if (file.url) {
                file.$state = function () {
                    return state;
                };
                file.$destroy = function () {
                    state = 'pending';
                    return $http({
                        url: file.deleteUrl,
                        method: file.deleteType
                    }).then(
                        function () {
                            state = 'resolved';
                            $scope.clear(file);
                        },
                        function () {
                            state = 'rejected';
                        }
                    );
                };
            } else if (!file.$cancel && !file._index) {
                file.$cancel = function () {
                    $scope.clear(file);
                };
            }
        }
    ]);