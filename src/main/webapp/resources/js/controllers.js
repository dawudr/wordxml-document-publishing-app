/**
 * MainCtrl - controller
 * Contains severals global data used in diferent view
 *
 */
function MainCtrl() {

    /**
     * slideInterval - Interval for bootstrap Carousel, in milliseconds:
     */
    this.slideInterval = 5000;

    /**
     * Variables used for Ui Elements view
     */
    this.bigTotalItems = 175;
    this.bigCurrentPage = 1;
    this.maxSize = 5;
    this.singleModel = 1;
    this.radioModel = 'Middle';
    this.checkModel = {
        left: false,
        middle: true,
        right: false
    };

    /**
     * groups - used for Collapse panels in Tabs and Panels view
     */
    this.groups = [
        {
            title: 'Dynamic Group Header - 1',
            content: 'Dynamic Group Body - 1'
        },
        {
            title: 'Dynamic Group Header - 2',
            content: 'Dynamic Group Body - 2'
        }
    ];

    /**
     * alerts - used for dynamic alerts in Notifications and Tooltips view
     */
    this.alerts = [
        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
        { type: 'success', msg: 'Well done! You successfully read this important alert message.' },
        { type: 'info', msg: 'OK, You are done a great job man.' },
    ];

    /**
     * addAlert, closeAlert  - used to manage alerts in Notifications and Tooltips view
     */
    this.addAlert = function() {
        this.alerts.push({msg: 'Another alert!'});
    };

    this.closeAlert = function(index) {
        this.alerts.splice(index, 1);
    };

    /**
     * randomStacked - used for progress bar (stacked type) in Badges adn Labels view
     */
    this.randomStacked = function() {
        this.stacked = [];
        var types = ['success', 'info', 'warning', 'danger'];

        for (var i = 0, n = Math.floor((Math.random() * 4) + 1); i < n; i++) {
            var index = Math.floor((Math.random() * 4));
            this.stacked.push({
                value: Math.floor((Math.random() * 30) + 1),
                type: types[index]
            });
        }
    };
    /**
     * initial run for random stacked value
     */
    this.randomStacked();

    /**
     * summernoteText - used for Summernote plugin
     */
    this.summernoteText = ['<h3>Hello Jonathan! </h3>',
        '<p>dummy text of the printing and typesetting industry. <strong>Lorem Ipsum has been the dustrys</strong> standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more',
        'recently with</p>'].join('');

};


function HeaderCtrl($scope, $http) {
    var headerVars = this;

    $http.get('/user').success(function (data) {
        headerVars.user = data;
    }).then(function (response) {
            $scope.vm = {
                appReady: true
            }
        }
    );


    $scope.logout = function () {
        $http({
            method: 'POST',
            url: '/logout'
        })
        .then(function (response) {
            if (response.status == 200) {
                window.location.reload();
            }
            else {
                console.log("Logout failed!");
            }
        });
    }
};


/**
 * modalDemoCtrl - Controller used to run modal view
 * used in Basic form view
 */
function modalDemoCtrl($scope, $modal) {

    $scope.open = function () {

        var modalInstance = $modal.open({
            templateUrl: 'views/modal_example.html',
            controller: ModalInstanceCtrl,
        });
    };

    $scope.open1 = function () {
        var modalInstance = $modal.open({
            templateUrl: 'views/modal_example1.html',
            controller: ModalInstanceCtrl
        });
    };

    $scope.open2 = function () {
        var modalInstance = $modal.open({
            templateUrl: 'views/modal_example2.html',
            controller: ModalInstanceCtrl,
            windowClass: "animated fadeIn"
        });
    };

    $scope.open3 = function (size) {
        var modalInstance = $modal.open({
            templateUrl: 'views/modal_example3.html',
            size: size,
            controller: ModalInstanceCtrl,
        });
    };

    $scope.open4 = function () {
        var modalInstance = $modal.open({
            templateUrl: 'views/modal_example2.html',
            controller: ModalInstanceCtrl,
            windowClass: "animated flipInY"
        });
    };
};

function ModalInstanceCtrl ($scope, $modalInstance) {

    $scope.ok = function () {
        $modalInstance.close();
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};

/**
 * wizardCtrl - Controller for wizard functions
 * used in Wizard view
 */
function wizardCtrl($scope) {
    // All data will be store in this object
    $scope.formData = {};

    // After process wizard
    $scope.processForm = function() {
        alert('Wizard completed');
    };
}

/**
 * nestableCtrl - Controller for nestable list
 */
function nestableCtrl($scope) {
    $scope.remove = function(scope) {
        scope.remove();
    };
    $scope.toggle = function(scope) {
        scope.toggle();
    };
    $scope.moveLastToTheBeginning = function () {
        var a = $scope.data.pop();
        $scope.data.splice(0,0, a);
    };
    $scope.newSubItem = function(scope) {
        var nodeData = scope.$modelValue;
        nodeData.nodes.push({
            id: nodeData.id * 10 + nodeData.nodes.length,
            title: nodeData.title + '.' + (nodeData.nodes.length + 1),
            nodes: []
        });
    };
    $scope.collapseAll = function() {
        $scope.$broadcast('collapseAll');
    };
    $scope.expandAll = function() {
        $scope.$broadcast('expandAll');
    };
    $scope.data = [{
        "id": 1,
        "title": "node1",
        "nodes": [
            {
                "id": 11,
                "title": "node1.1",
                "nodes": [
                    {
                        "id": 111,
                        "title": "node1.1.1",
                        "nodes": []
                    }
                ]
            },
            {
                "id": 12,
                "title": "node1.2",
                "nodes": []
            }
        ],
    }, {
        "id": 2,
        "title": "node2",
        "nodes": [
            {
                "id": 21,
                "title": "node2.1",
                "nodes": []
            },
            {
                "id": 22,
                "title": "node2.2",
                "nodes": []
            }
        ],
    }, {
        "id": 3,
        "title": "node3",
        "nodes": [
            {
                "id": 31,
                "title": "node3.1",
                "nodes": []
            }
        ],
    }];
}

/**
 * codeEditorCtrl - Controller for code editor - Code Mirror
 */
function codeEditorCtrl($scope) {
    $scope.editorOptions = {
        lineNumbers: true,
        matchBrackets: true,
        styleActiveLine: true,
        theme:"ambiance"
    };

    $scope.editorOptions2 = {
        lineNumbers: true,
        matchBrackets: true,
        styleActiveLine: true
    };

}

/**
 * ngGridCtrl - Controller for code ngGrid
 */
function ngGridCtrl($scope) {
    $scope.ngOptions = { data: 'ngData' };
    $scope.ngOptions2 = {
        data: 'ngData',
        showGroupPanel: true,
        jqueryUIDraggable: true
    };
}


/**
 *
 * Pass all functions into module
 */
angular
    .module('wordxmlplus')
    .controller('MainCtrl', MainCtrl)
    .controller('HeaderCtrl', HeaderCtrl)
    //.controller('dashboardFlotOne', dashboardFlotOne)
    //.controller('dashboardFlotTwo', dashboardFlotTwo)
    //.controller('dashboardMap', dashboardMap)
    //.controller('flotChartCtrl', flotChartCtrl)
    //.controller('morrisChartCtrl', morrisChartCtrl)
    //.controller('rickshawChartCtrl', rickshawChartCtrl)
    //.controller('widgetFlotChart', widgetFlotChart)
    //.controller('modalDemoCtrl', modalDemoCtrl)
    //.controller('ionSlider', ionSlider)
    .controller('wizardCtrl', wizardCtrl)
    //.controller('CalendarCtrl', CalendarCtrl)
    //.controller('chartJsCtrl', chartJsCtrl)
    //.controller('GoogleMaps', GoogleMaps)
    .controller('ngGridCtrl', ngGridCtrl)
    .controller('codeEditorCtrl', codeEditorCtrl)
    .controller('nestableCtrl', nestableCtrl)
