/****************************************************************
 *
 * 파일명 : mobileTodaySale.js
 * 설  명 : (모바일) 매출현황 > 당일매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.02     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  당일매출종합 그리드 생성
 */
app.controller('mobileTodaySaleTotalCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleTotalCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleTotalCtrl", function(event, data) {
        $scope.searchMobileTodaySaleTotal(data);
        event.preventDefault();
    });

    $scope.searchMobileTodaySaleTotal = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/todaySale/todaySale/getMobileTodaySaleTotalList.sb", params, function(response){
            var mobileTodaySale = response.data.data.result;
            $scope.mobileTodaySale = mobileTodaySale;

            if(response.data.data.result != null) {
                $("#lblSaleCnt").text($scope.mobileTodaySale.saleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblReturnSaleCnt").text($scope.mobileTodaySale.returnSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillCnt").text($scope.mobileTodaySale.billCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotSaleAmt").text($scope.mobileTodaySale.totSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotDcAmt").text($scope.mobileTodaySale.totDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblRealSaleAmt").text($scope.mobileTodaySale.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotGuestCnt").text($scope.mobileTodaySale.totGuestCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblGuestUprc").text($scope.mobileTodaySale.guestUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillUprc").text($scope.mobileTodaySale.billUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCardAmt").text($scope.mobileTodaySale.cardAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCashAmt").text($scope.mobileTodaySale.cashAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblEtcAmt").text($scope.mobileTodaySale.etcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblDcAmt").text($scope.mobileTodaySale.dcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCoupnDcAmt").text($scope.mobileTodaySale.coupnDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotalDcAmt").text($scope.mobileTodaySale.totalDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.mobileTodaySale;
    $scope.setMobileTodaySale = function(store) {
        $scope.mobileTodaySale = store;
    };
    $scope.getMobileTodaySale = function(){
        return $scope.mobileTodaySale;
    };
}]);


/**
 *  결제수단 그리드 생성
 */
app.controller('mobileTodaySaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", getToday());

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 조회
        $scope.searchMobileTodaySalePay();
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleCtrl", function(event, data) {
        gridOpen("mobileTodaySaleTotal");
        gridOpen("mobileTodaySalePay");
        gridOpen("mobileTodaySaleDc");
        gridOpen("mobileTodaySaleDlvr");
        gridOpen("mobileTodaySaleTime");

        $scope.searchMobileTodaySalePay();
        event.preventDefault();
    });

    $scope.searchMobileTodaySalePay = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileTodaySaleStoreCd").val();

        $scope._inquirySub("/mobile/sale/status/todaySale/todaySale/getMobileTodaySalePayList.sb", params, function() {
            // 매출종합현황
            $scope._broadcast("mobileTodaySaleTotalCtrl", params);
            // 할인내역
            $scope._broadcast("mobileTodaySaleDcCtrl", params);
            // 내점/배달/포장
            $scope._broadcast("mobileTodaySaleDlvrCtrl", params);
            // 시간대별
            $scope._broadcast("mobileTodaySaleTimeCtrl", params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileTodaySaleStoreShow = function () {
        $scope._broadcast('mobileTodaySaleStoreCtrl');
    };
}]);


/**
 *  할인내역 그리드 생성
 */
app.controller('mobileTodaySaleDcCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleDcCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleDcCtrl", function(event, data) {
        $scope.searchMobileTodaySaleDc(data);
        event.preventDefault();
    });

    $scope.searchMobileTodaySaleDc = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/todaySale/todaySale/getMobileTodaySaleDcList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  내점/배달/포장 그리드 생성
 */
app.controller('mobileTodaySaleDlvrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleDlvrCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleDlvrCtrl", function(event, data) {
        $scope.searchMobileTodaySaleDlvr(data);
        event.preventDefault();
    });

    $scope.searchMobileTodaySaleDlvr = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/todaySale/todaySale/getMobileTodaySaleDlvrList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  시간대별 그리드 생성
 */
app.controller('mobileTodaySaleTimeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleTimeCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleTimeCtrl", function(event, data) {
        $scope.searchMobileTodaySaleTime(data);
        event.preventDefault();
    });

    $scope.searchMobileTodaySaleTime = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/todaySale/todaySale/getMobileTodaySaleTimeList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);