/**
 * get application
 */
var app = agrid.getApp();

app.controller('todayBest3Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todayBest3Ctrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("todayBest3Ctrl", function (event, data) {
        $scope.getTodayBest3();
        event.preventDefault();
    });

    $scope.getTodayBest3 = function () {
        var params = {};
        params.storeCd = $("#mobileProdSaleStoreCd").val();
        params.todayBest3Fg = "Y";

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/mobile/sale/status/prod/prodSale/prodSaleList.sb", params, function() {}, false);
    }

}]);

app.controller('mobileProdSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileProdSaleCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // grid 합계
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    }

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mobileProdSaleCtrl", function (event, data) {

        //
        gridOpen("todayBest3");
        gridOpen("prodSale");

        //
        $scope.getProdSaleList();

        // 상품별 매출현황 조회 후,
        var todayBest3Grid = agrid.getScope("todayBest3Ctrl");
        todayBest3Grid._pageView('todayBest3Ctrl', 1);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    
    $scope.getProdSaleList = function () {
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#mobileProdSaleStoreCd").val();
        params.todayBest3Fg = "N";

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/mobile/sale/status/prod/prodSale/prodSaleList.sb", params, function() {}, false);
    }

    $scope.mobileProdSaleStoreShow = function () {
        $scope._broadcast('mobileProdSaleStoreCtrl');
    };

}]);