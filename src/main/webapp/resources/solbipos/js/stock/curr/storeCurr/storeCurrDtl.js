/****************************************************************
 *
 * 파일명 : storeCurrDtl.js
 * 설  명 : 재고관리 - 매장재고현황 - 매장현재고 - 현재고 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.01.08     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 현재고 상세 팝업 controller */
app.controller('storeCurrDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeCurrDtlCtrl', $scope, $http, true));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    $scope._setComboData("srchUnitFgDtl", [
        {"name": messages["storeCurr.unitStockFg"], "value": "0"},
        {"name": messages["storeCurr.unitOrderFg"], "value": "1"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeCurrDtlCtrl", function (event, data) {

        $scope.storeCd = data.storeCd;
        $scope.prodCd = data.prodCd;
        $scope.storeCurrDtlLayer.show(true);
        $("#dtlStoreCd").text(" [" +data.storeCd +"]");
        $("#dtlStoreNm").text(data.storeNm);
        $("#dtlProdCd").text(" - [" +data.prodCd +"]");
        $("#dtlProdNm").text(data.prodNm);

        $scope.getSearchStoreCurrDtlList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    //재고현황- 재고현황 리스트 조회
    $scope.getSearchStoreCurrDtlList = function () {
        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 6달(186일) 제한
        // if (diffDay > 186) {
        //     $scope._popMsg(messages['cmm.dateOver.6month.error']);
        //     return false;
        // }

        // 파라미터
        var params      = {};
        params.startDate    = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate      = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd      = $scope.storeCd;
        params.prodCd		= $scope.prodCd;
        params.unitFg       = $scope.unitFgDtl;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/stock/curr/storeCurr/storeCurrDtl/getSearchStoreCurrDtlList.sb", params);
    };
}]);

