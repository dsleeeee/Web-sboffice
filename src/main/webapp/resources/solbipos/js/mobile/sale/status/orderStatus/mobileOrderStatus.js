/****************************************************************
 *
 * 파일명 : mobileOrderStatus.js
 * 설  명 : (모바일) 매출현황 > 주문현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.05     권지현      1.0
 *
 * **************************************************************/

/**
 * get application
 */
// 배달여부
var dlvrYnData = [
    {"name": "배달", "value": "Y"},
    {"name": "배달외", "value": "N"}
];
// 회원여부
var memberYnData = [
    {"name": "회원", "value": "Y"},
    {"name": "비회원", "value": "N"}
];
// 예약여부
var resveYnData = [
    {"name": "예약", "value": "Y"},
    {"name": "즉시", "value": "N"}
];
// 환급여부
var refundYnData = [
    {"name": "환급", "value": "Y"},
    {"name": "미환급", "value": "N"}
];
// 전송여부
var sendYnData = [
    {"name": "전송", "value": "Y"},
    {"name": "미전송", "value": "N"}
];
// 주문구분
var orderFgData = [
    {"name": "주문", "value": "1"},
    {"name": "취소", "value": "2"},
    {"name": "결제", "value": "3"}
];
// 배달주문구분
var dlvrOrderFgData = [
    {"name": "일반", "value": "1"},
    {"name": "배달", "value": "2"},
    {"name": "포장", "value": "3"}
];
var app = agrid.getApp();

/**
 *  주문현황 그리드 생성
 */
app.controller('mobileOrderStatusCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileOrderStatusCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.dlvrYnDataMap = new wijmo.grid.DataMap(dlvrYnData, 'value', 'name');           // 배달여부
        $scope.memberYnDataMap = new wijmo.grid.DataMap(memberYnData, 'value', 'name');       // 회원여부
        $scope.resveYnDataMap = new wijmo.grid.DataMap(resveYnData, 'value', 'name');         // 예약여부
        $scope.refundYnDataMap = new wijmo.grid.DataMap(refundYnData, 'value', 'name');       // 환급여부
        $scope.sendYnDataMap = new wijmo.grid.DataMap(sendYnData, 'value', 'name');           // 전송여부
        $scope.orderFgDataMap = new wijmo.grid.DataMap(orderFgData, 'value', 'name');         // 주문구분
        $scope.dlvrOrderFgDataMap = new wijmo.grid.DataMap(dlvrOrderFgData, 'value', 'name'); // 배달주문구분

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }

                if (col.binding === "orderNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "orderNo") { // 주문번호 클릭
                    var params    = {};
                    params.srchStoreCd = $("#mobileOrderStatusStoreCd").val();
                    params.saleDate = selectedRow.saleDate.replaceAll("-","");
                    params.orderNo = selectedRow.orderNo;
                    $scope.wjOrderStatusDtlLayer.show(true);
                    $scope._broadcast('orderStatusDtlCtrl', params);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileOrderStatusCtrl", function(event, data) {
        gridOpen("mobileOrderStatus");

        $scope.searchMobileOrderStatus(data);
        event.preventDefault();
    });

    $scope.searchMobileOrderStatus = function(data){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

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

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.srchStoreCd = $("#mobileOrderStatusStoreCd").val();

        $scope._inquirySub("/mobile/sale/status/orderStatus/orderStatus/getMobileOrderStatusList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileOrderStatus", $scope.flexMobileOrderStatus, "Y");
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileOrderStatusStoreShow = function () {
        $scope._broadcast('mobileOrderStatusStoreCtrl');
    };
}]);
