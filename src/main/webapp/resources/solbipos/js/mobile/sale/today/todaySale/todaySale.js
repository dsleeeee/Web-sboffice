/****************************************************************
 *
 * 파일명 : todaySale.js
 * 설  명 : (모바일) 당일매출현황 JavaScript
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
app.controller('todaySaleTotalCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todaySaleTotalCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("todaySaleTotalCtrl", function(event, data) {
        $scope.searchTodaySale(data);
        event.preventDefault();
    });

    $scope.searchTodaySale = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.storeCd = data.storeCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/today/todaySale/todaySale/getTodaySaleList.sb", params, function(response){
            var todaySale = response.data.data.result;
            $scope.todaySale = todaySale;

            if(response.data.data.result != null) {
                $scope.todaySale.saleCnt = $scope.todaySale.saleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.returnSaleCnt = $scope.todaySale.returnSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.billCnt = $scope.todaySale.billCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.totSaleAmt = $scope.todaySale.totSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.totDcAmt = $scope.todaySale.totDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.realSaleAmt = $scope.todaySale.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.totGuestCnt = $scope.todaySale.totGuestCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.guestUprc = $scope.todaySale.guestUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.billUprc = $scope.todaySale.billUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.tblCnt = $scope.todaySale.tblCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.tblUprc = $scope.todaySale.tblUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.cardAmt = $scope.todaySale.cardAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.cashAmt = $scope.todaySale.cashAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.etcAmt = $scope.todaySale.etcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.dcAmt = $scope.todaySale.dcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.coupnDcAmt = $scope.todaySale.coupnDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $scope.todaySale.totalDcAmt = $scope.todaySale.totalDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.todaySale;
    $scope.setTodaySale = function(store) {
        $scope.todaySale = store;
    };
    $scope.getTodaySale = function(){
        return $scope.todaySale;
    };
}]);


/**
 *  결제수단 그리드 생성
 */
app.controller('todaySaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todaySaleCtrl', $scope, $http, false));

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
        $scope.searchTodaySalePay();
    };

    // <-- 검색 호출 -->
    $scope.$on("todaySaleCtrl", function(event, data) {
        gridOpen("todaySaleTotal");
        gridOpen("todaySalePay");
        gridOpen("todaySaleDc");
        gridOpen("todaySaleDlvr");
        gridOpen("todaySaleTime");

        $scope.searchTodaySalePay();
        event.preventDefault();
    });

    $scope.searchTodaySalePay = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.storeCd = $("#mobileTodaySaleStoreCd").val();

        $scope._inquirySub("/mobile/sale/today/todaySale/todaySale/getTodaySalePayList.sb", params, function() {
            // 매출종합현황
            var todaySaleTotalScope = agrid.getScope('todaySaleTotalCtrl');
            todaySaleTotalScope._broadcast('todaySaleTotalCtrl', params);

            // 할인내역
            var todaySaleDcScope = agrid.getScope('todaySaleDcCtrl');
            todaySaleDcScope._broadcast('todaySaleDcCtrl', params);

            // 매장/배달/포장
            var todaySaleDlvrScope = agrid.getScope('todaySaleDlvrCtrl');
            todaySaleDlvrScope._broadcast('todaySaleDlvrCtrl', params);

            // 시간대별
            var todaySaleTimeScope = agrid.getScope('todaySaleTimeCtrl');
            todaySaleTimeScope._broadcast('todaySaleTimeCtrl', params);

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
app.controller('todaySaleDcCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todaySaleDcCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("todaySaleDcCtrl", function(event, data) {
        $scope.searchTodaySaleDc(data);
        event.preventDefault();
    });

    $scope.searchTodaySaleDc = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.storeCd = data.storeCd;

        $scope._inquirySub("/mobile/sale/today/todaySale/todaySale/getTodaySaleDcList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  매장/배달/포장 그리드 생성
 */
app.controller('todaySaleDlvrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todaySaleDlvrCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("todaySaleDlvrCtrl", function(event, data) {
        $scope.searchTodaySaleDlvr(data);
        event.preventDefault();
    });

    $scope.searchTodaySaleDlvr = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.storeCd = data.storeCd;

        $scope._inquirySub("/mobile/sale/today/todaySale/todaySale/getTodaySaleDlvrList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  시간대별 그리드 생성
 */
app.controller('todaySaleTimeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todaySaleTimeCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("todaySaleTimeCtrl", function(event, data) {
        $scope.searchTodaySaleTime(data);
        event.preventDefault();
    });

    $scope.searchTodaySaleTime = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.storeCd = data.storeCd;

        $scope._inquirySub("/mobile/sale/today/todaySale/todaySale/getTodaySaleTimeList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);