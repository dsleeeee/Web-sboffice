/****************************************************************
 *
 * 파일명 : mobileProdSale.js
 * 설  명 : (모바일) 매출현황 > 상품별매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.03.31     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('todayBest3Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todayBest3Ctrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("todayBest3Ctrl", function (event, data) {

        // 당일매출 (Best3) 조회
        $scope.getTodayBest3();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 당일매출 (Best3) 조회
    $scope.getTodayBest3 = function () {
        var params = {};
        params.srchStoreCd = $("#mobileProdSaleStoreCd").val();
        params.todayBest3Fg = "Y";

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/mobile/sale/status/prod/prodSale/prodSaleList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("todayBest3", $scope.flexTodayBest3, "Y");
            // if ($scope.flexTodayBest3.rows.length <= 0) {
            //     gridShowMsg("todayBest3", "Y");
            // }else{
            //     gridShowMsg("todayBest3", "N");
            // }
        }, false);
    }

}]);

app.controller('mobileProdSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileProdSaleCtrl', $scope, $http, false));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // grid 합계
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mobileProdSaleCtrl", function (event, data) {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (1000 * 60 * 60 * 24);

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['mobile.cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 한달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['mobile.cmm.dateOver.1month.error']);
            return false;
        }

        // 접힌 grid 기본 open
        gridOpen("todayBest3");
        gridOpen("prodSale");

        // 상품별 매출현황 조회
        $scope.getProdSaleList();

        // 상품별 매출현황 조회 후, 당일매출 (Best3) 조회
        var todayBest3Grid = agrid.getScope("todayBest3Ctrl");
        todayBest3Grid._pageView('todayBest3Ctrl', 1);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상품별 매출현황 조회
    $scope.getProdSaleList = function () {
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.srchStoreCd = $("#mobileProdSaleStoreCd").val();
        params.todayBest3Fg = "N";

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/mobile/sale/status/prod/prodSale/prodSaleList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("prodSale", $scope.flexProdSale, "Y");
            // if ($scope.flexProdSale.rows.length <= 0) {
            //     gridShowMsg("prodSale", "Y");
            // }else{
            //     gridShowMsg("prodSale", "N");
            // }
        }, false);
    };

    // 다중매장 선택팝업
    $scope.mobileProdSaleStoreShow = function () {
        $scope._broadcast('mobileProdSaleStoreCtrl');
    };

}]);