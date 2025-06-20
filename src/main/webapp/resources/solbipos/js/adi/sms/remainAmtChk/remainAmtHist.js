/****************************************************************
 *
 * 파일명 : remainAmtHist.js
 * 설  명 : 잔여금액확인 - 충전/사용내역 팝업
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.12     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 성공여부
var successYnComboData = [
    {"name":"전체","value":""},
    {"name":"성공","value":"Y"},
    {"name":"실패","value":"N"},
    {"name":"결제취소","value":"R"}
];
// 결제수단
var gpgresourceComboData = [
    {"name":"전체","value":""},
    {"name":"신용카드","value":"11"},
    // {"name":"계좌이체","value":"21"},
    // {"name":"휴대폰결제","value":"31"},
    // {"name":"보너스","value":"99"},
    {"name":"임의등록","value":"*"}
];

var msgTypeDataMapData = [
    {"name":"SMS","value":"1"},
    {"name":"LMS","value":"2"},
    {"name":"MMS","value":"3"},
    {"name":"알림톡","value":"-4"},
    {"name":"알림톡_대체발송","value":"-5"}
];
// 결과
var sendStatusFgData = [
    {"name":"전체","value":""},
    {"name":"전송대기","value":"0"}, // _ENC 발송대기
    {"name":"서버등록중","value":"1"}, // _ENC 서버등록중
    {"name":"서버등록완료","value":"2"}, // _ENC 서버등록완료
    {"name":"발송완료","value":"3"}, // _ENC 발송완료
    {"name":"실패","value":"-1"}, // _ENC 발송실패
    {"name":"전송","value":"4"}, // _REPORT 대기
    {"name":"전송성공","value":"5"}, // _REPORT 성공
    {"name":"전송실패","value":"6"} // _REPORT 실패
    // {"name":"","value":"7"} // _REPORT 오류
];
// 예약여부
var reserveYnDataMapData = [
    {"name":"전체","value":""},
    {"name":"예약","value":"1"},
    {"name":"즉시","value":"0"}
];
/**
 *  충전/사용내역 팝업 생성
 */
app.controller('remainAmtHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('remainAmtHistCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.successYnDataMap = new wijmo.grid.DataMap(successYnComboData, 'value', 'name'); // 성공여부
        $scope.pgresourceDataMap = new wijmo.grid.DataMap(gpgresourceComboData, 'value', 'name'); // 결제수단
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(msgTypeDataMapData, 'value', 'name'); // 메세지타입
        $scope.sendStatusFgDataMap = new wijmo.grid.DataMap(sendStatusFgData, 'value', 'name'); // 결과
        $scope.reserveYnDataMap = new wijmo.grid.DataMap(reserveYnDataMapData, 'value', 'name'); // 예약여부

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.useFg              = messages["remainAmtChk.useFg"];
        dataItem.useAmt             = messages["remainAmtChk.useAmt"];

        dataItem.orgn               = messages["remainAmtChk.charge"];
        dataItem.chargeDate         = messages["remainAmtChk.charge"];
        dataItem.chargeTime         = messages["remainAmtChk.charge"];
        dataItem.pgresource         = messages["remainAmtChk.charge"];
        dataItem.controlno          = messages["remainAmtChk.charge"];
        dataItem.baseChargeAmt      = messages["remainAmtChk.charge"];
        dataItem.chargeAmt          = messages["remainAmtChk.charge"];
        dataItem.vatAmt             = messages["remainAmtChk.charge"];
        dataItem.chargeTot          = messages["remainAmtChk.charge"];
        dataItem.successYn          = messages["remainAmtChk.charge"];
        dataItem.resultmessage      = messages["remainAmtChk.charge"];

        dataItem.chargeIdNm         = messages["remainAmtChk.use"];
        dataItem.orgnCd             = messages["remainAmtChk.use"];
        dataItem.orgnNm             = messages["remainAmtChk.use"];
        dataItem.approvalnum        = messages["remainAmtChk.use"];
        dataItem.regDt              = messages["remainAmtChk.use"];
        dataItem.sOgnCd             = messages["remainAmtChk.use"];
        dataItem.sOgnNm             = messages["remainAmtChk.use"];
        dataItem.sUserNm            = messages["remainAmtChk.use"];
        dataItem.sPhoneNumber       = messages["remainAmtChk.use"];
        dataItem.rOgnCd             = messages["remainAmtChk.use"];
        dataItem.rOgnNm             = messages["remainAmtChk.use"];
        dataItem.rPhoneNumber       = messages["remainAmtChk.use"];
        dataItem.msgType            = messages["remainAmtChk.use"];
        dataItem.reserveYn          = messages["remainAmtChk.use"];
        dataItem.sendDate           = messages["remainAmtChk.use"];
        dataItem.readDate           = messages["remainAmtChk.use"];
        dataItem.sendStatus         = messages["remainAmtChk.use"];
        dataItem.resultNm           = messages["remainAmtChk.use"];
        dataItem.company            = messages["remainAmtChk.use"];
        dataItem.msgContent         = messages["remainAmtChk.use"];
        dataItem.msgId              = messages["remainAmtChk.use"];
        dataItem.gubun              = messages["remainAmtChk.use"];
        dataItem.smsSendSeq         = messages["remainAmtChk.use"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더2줄 -->
    };

    $scope.$on("remainAmtHistCtrl", function(event, data) {

        $scope.srchOrgnCd   = data.srchOrgnCd;
        $scope.srchOrgnNm   = data.srchOrgnNm;
        $scope.startDate    = data.startDate;
        $scope.endDate      = data.endDate;
        // 상품가격정보 조회
        $scope.getRemainAmtHistList(data);
        event.preventDefault();
    });

    // 상품가격정보 조회
    $scope.getRemainAmtHistList = function (data) {

        var params = {};
        params= data;

        $scope._inquirySub("/adi/sms/remainAmtChk/remainAmtHist/getRemainAmtHistList.sb", params, function() {}, false);
    }

    // 엑셀 다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                '충전/사용내역' + $scope.startDate + '~' + $scope.endDate + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);