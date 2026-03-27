/****************************************************************
 *
 * 파일명 : mCoupnHistory.js
 * 설  명 : 모바일쿠폰이력조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.03.24     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매출구분
var saleYnDataMapData = [
    {"name":"매출","value":"Y"},
    {"name":"반품","value":"N"}
];
// 모바일쿠폰구분
var mcoupnTypeFgData = [
    {"name":"교환권","value":"1"},
    {"name":"금액권","value":"2"}
];

// 현금영수증구분
var cashBillApprProcFgData = [
    {"name":"POS","value":"1"},
    {"name":"CAT","value":"2"},
    {"name":"일반","value":"3"}
];

// 모바일쿠폰승인구분
var apprProcFgData = [
    {"name":"POS","value":"1"},
    {"name":"CAT","value":"2"}
];

// 영수타입구분
var billTypeData = [
    {"name":"전체","value":""},
    {"name":"예약","value":"1"},
    {"name":"판매","value":"2"}
];

/**
 *  모바일쿠폰이력조회 그리드 생성
 */
app.controller('mCoupnHistoryCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mCoupnHistoryCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    $scope._setComboData("mCoupnCd", mCoupnCd);
    $scope._setComboData("billType", billTypeData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.saleYnDataMap = new wijmo.grid.DataMap(saleYnDataMapData, 'value', 'name'); // 구분
        $scope.mcoupnCdDataMap = new wijmo.grid.DataMap(mCoupnCd, 'value', 'name'); // 모바일쿠폰사
        $scope.mcoupnTypeFgDataMap = new wijmo.grid.DataMap(mcoupnTypeFgData, 'value', 'name'); // 모바일쿠폰구분
        $scope.cashBillApprProcFgDataMap = new wijmo.grid.DataMap(cashBillApprProcFgData, 'value', 'name'); // 현금영수증구분
        $scope.apprProcFgDataMap = new wijmo.grid.DataMap(apprProcFgData, 'value', 'name'); // 모바일쿠폰승인구분
        $scope.billTypeDataMap = new wijmo.grid.DataMap(billTypeData, 'value', 'name'); // 모바일쿠폰승인구분

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("mCoupnHistoryCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "billNo") { // 영수증번호
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var scope = agrid.getScope('mCoupnHistoryCtrl');
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                var params = {};
                params.storeCd = selectedRow.storeCd;
                params.saleDate = selectedRow.saleDate.replaceAll("-","");
                params.posNo = selectedRow.posNo;
                params.billNo = selectedRow.billNo;
                params.saleYn = selectedRow.saleYn;
                params.webReg = selectedRow.webReg;

                if (col.binding === "billNo") { // 영수증번호 클릭
                    $scope._broadcast('billInfoCtrl', params);
                }
            }
        });
    };

    $scope.$on("mCoupnHistoryCtrl", function (event, data) {

        // 조회
        $scope.searchMCoupnHistory();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchMCoupnHistory = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        // 모바일쿠폰바코드번호 또는 승인번호를 입력 후 조회하세요.
        /*if(isNull($scope.mCoupnBarcdNo) && isNull($scope.apprNo)){
            $scope._popMsg(messages['mCoupnHistory.search.chk.msg']);
            return false;
        }*/

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#mCoupnHistoryStoreCd").val();
        params.mCoupnBarcdNo = $scope.mCoupnBarcdNo;
        params.apprNo = $scope.apprNo;
        params.mCoupnCd = $scope.mCoupnCdCombo.selectedValue;
        params.billType = $scope.billTypeCombo.selectedValue;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/anals/mCoupnHistory/getMCoupnHistory.sb", params, function () {

        });
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex,
                {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    formatItem: saveFormatItem,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                },
                messages["mCoupnHistory.mCoupnHistory"] + '_' + getCurDateTime() + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

}]);

function saveFormatItem(args) {
    let p = args.panel, row = args.row, col = args.col, xlsxCell = args.xlsxCell, cell, color;
    if (p.cellType === wijmo.grid.CellType.Cell) {
        if (p.columns[col].binding === 'mcoupnBarcdNo' || p.columns[col].binding === 'apprUniqueNo' || p.columns[col].binding === 'apprNo') {
            if (xlsxCell.value) {
                xlsxCell.style.format = "@"
            }
        }
    }
};