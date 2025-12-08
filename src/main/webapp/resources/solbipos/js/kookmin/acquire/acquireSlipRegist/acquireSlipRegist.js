/****************************************************************
 *
 * 파일명 : acquireSlipRegist.js
 * 설  명 : 국민대 > 매입처관리 > 매입전표 등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.11.19     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 매입전표 그리드 controller */
app.controller('acquireSlipRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('acquireSlipRegistCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 조회조건 조회일자구분
    $scope._setComboData("srchDateFg", [
        {"name": messages["vendrInstock.regDate"], "value": "reg"},
        {"name": messages["vendrInstock.instockRtnDate"], "value": "in"}
    ]);

    // 조회조건 전표구분
    $scope._setComboData("srchSlipFg", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["vendrInstock.slipFgIn"], "value": "1"},
        {"name": messages["vendrInstock.slipFgRtn"], "value": "-1"}
    ]);

    // 조회조건 진행구분
    $scope._setComboData("srchProcFg", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["vendrInstock.procFg0"], "value": "0"},
        {"name": messages["vendrInstock.procFg1"], "value": "1"}
    ]);

    // 그리드 전표구분
    $scope.slipFgMap = new wijmo.grid.DataMap([
        {id: "1", name: messages["vendrInstock.slipFgIn"]},
        {id: "-1", name: messages["vendrInstock.slipFgRtn"]}
    ], 'id', 'name');

    // 그리드 진행구분
    $scope.procFgMap = new wijmo.grid.DataMap([
        {id: "0", name: messages["vendrInstock.procFg0"]},
        {id: "1", name: messages["vendrInstock.procFg1"]}
    ], 'id', 'name');

    // 그리드 발주/무발주 구분
    $scope.instockTypeMap = new wijmo.grid.DataMap([
        {id: "Y", name: messages["vendrInstock.orderInstock"]},
        {id: "N", name: messages["vendrInstock.notOrderInstock"]}
    ], 'id', 'name');


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("acquireSlipRegistCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "slipNo") { // 전표번호
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }

                // 전표구분이 반출이면 글씨색을 red 로 변경한다.
                if (col.binding === "slipFg") {
                    var item = s.rows[e.row].dataItem;
                    if (item.slipFg === -1) {
                        wijmo.addClass(e.cell, 'red');
                    }
                }
                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "slipNo") { // 전표번호 클릭
                    var params     = {};
                    params.slipNo  = selectedRow.slipNo;
                    params.slipFg  = selectedRow.slipFg;
                    params.vendrCd = selectedRow.vendrCd;
                    $scope._broadcast('inOutStockRegistCtrl', params);
                }
            }
        });
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("acquireSlipRegistCtrl", function (event, data) {
        $scope.getSearchInOutStockList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 입고/반출 리스트 조회
    $scope.getSearchInOutStockList = function () {
        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.vendrCd   = $("#acquireSlipRegistSelectVendrCd").val();
        params.storeCd   = $("#acquireSlipRegistStoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/acquire/acquireSlipRegist/acquireSlipRegist/getSearchInOutStockList.sb", params);
    };

    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.acquireSlipRegistSelectVendrShow = function () {
        $scope._broadcast('acquireSlipRegistSelectVendrCtrl');
    };


    // 입고/반출 신규등록 (입고 - slipFg : 1, 반출 - slipFg : -1)
    $scope.inOutstockRegist = function (slipFg) {
        var params     = {};
        params.slipNo  = '';
        params.slipFg  = slipFg;
        params.vendrCd = '';
        $scope._broadcast('inOutStockRegistCtrl', params);
    };

}]);
