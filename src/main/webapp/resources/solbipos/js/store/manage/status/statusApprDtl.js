/****************************************************************
 *
 * 파일명 : statusApprDtl.js
 * 설  명 : 기초관리 > 매장정보관리 > 매장현황 > 관리매장 승인내역 > 카드승인현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.24     이다솜      1.0
 *
 * **************************************************************/

// 승인구분
var vApprFg = [
    {"name":"승인","value":"1"},
    {"name":"취소","value":"2"}
];

// 승인처리
var vApprProcFg = [
    {"name":"POS","value":"1"},
    {"name":"CAT","value":"2"}
];

app.controller('statusApprDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusApprDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.apprFgDataMap = new wijmo.grid.DataMap(vApprFg, 'value', 'name');
        $scope.apprProcFgDataMap = new wijmo.grid.DataMap(vApprProcFg, 'value', 'name');

        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if(col.binding === "billNo"){
                    //wijmo.addClass(e.cell, 'wj-custom-readonly');
                    wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
                }
            }
        });

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "billNo") {
                    var params      = {};
                    params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.hqBrandCd  = selectedRow.hqBrandCd;
                    params.storeCd  = selectedRow.storeCd;
                    params.saleDate = selectedRow.saleDate;
                    params.posNo = selectedRow.posNo;
                    params.billNo = selectedRow.billNo;
                    params.payCol = $scope.payCol;
                    params.payApprType = $scope.payApprType;

                    $scope._broadcast('statusApprInfoCtrl', params);
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("statusApprDtlCtrl", function (event, data) {

        $scope.payCol = data.payCol;
        $scope.payApprType = data.payApprType;

        $scope.statusApprDtlLayer.show(true);

        // 조회
        $scope.cardOrCashAppr(data);

        // Set Title & Column Control
        var grid = wijmo.Control.getControl("#wjApprGridList");
        var columns = grid.columns;

        if(data.payApprType === "CARD"){
            $("#spanDtlTitle").text(messages["storeStatus.cardApprDtl"]);
            columns[8].visible = true;
            columns[9].visible = true;
            columns[13].visible = true;
        }
        else if(data.payApprType === "CASH"){
            $("#spanDtlTitle").text(messages["storeStatus.cashApprDtl"]);
            columns[8].visible = false;
            columns[9].visible = false;
            columns[13].visible = false;
        }

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 일자 매장별 매출현황 리스트 조회
    $scope.cardOrCashAppr = function (data) {
        // 파라미터
        var params      = {};
        params.hqOfficeCd  = data.hqOfficeCd;
        params.storeCd = data.storeCd;
        params.startDate  = data.startDate;
        params.endDate = data.endDate;
        params.payApprType = data.payApprType;
        //params.payCol = data.payCol;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/store/manage/status/storeAppr/cardOrCashApprList.sb", params);

    };

    // 닫기버튼 클릭
    $scope.close = function(){
        $scope.statusApprDtlLayer.hide();
    };

}]);