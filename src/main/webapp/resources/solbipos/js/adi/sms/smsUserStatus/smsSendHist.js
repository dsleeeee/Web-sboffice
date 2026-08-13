/****************************************************************
 *
 * 파일명 : smsSendHist.js
 * 설  명 : SMS 발송 이력 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.07     이다솜      1.0
 *
 * **************************************************************/
var app = agrid.getApp();

// 메세지타입
var msgTypeDataMapData = [
    {"name":"SMS","value":"1"},
    {"name":"LMS","value":"2"},
    {"name":"MMS","value":"3"}
];
// 예약여부
var reserveYnDataMapData = [
    {"name":"예약","value":"1"},
    {"name":"즉시","value":"0"}
];
// 조회구분
var gubunComboData = [
    {"name":"전체","value":""},
    {"name":"본사","value":"1"},
    {"name":"매장","value":"2"}
];

app.controller('smsSendHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('smsSendHistCtrl', $scope, $http, $timeout, true));

    // 충전일자 셋팅
    var startDate = wcombo.genDateVal("#startDate4", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate4", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("smsUseRegFg", smsUseRegFgData); // SMS사용등록 구분

    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(msgTypeDataMapData, 'value', 'name'); // 메세지타입
        $scope.reserveYnDataMap = new wijmo.grid.DataMap(reserveYnDataMapData, 'value', 'name'); // 예약여부
        $scope.sendFg = "0";

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 메세지
                if (col.binding === "msgContent") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 건수
                if (col.binding === "smsSendCount") {
                    wijmo.addClass(e.cell, 'wijLink');
                }

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

                // 메세지 클릭시 상세정보 조회
                if ( col.binding === "msgContent") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedSmsSendHist(selectedRow);
                    $scope.wjMessageDtlLayer.show(true);
                    event.preventDefault();
                }

                // 건수 클릭시 상세정보 조회
                if ( col.binding === "smsSendCount") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedSmsSendHist(selectedRow);
                    $scope.wjAddresseeeDtlLayer.show(true);
                    event.preventDefault();
                }
            }
        });
    };

    $scope.$on("smsSendHistCtrl", function (event, data) {
        // 조회
        $scope.searchSmsSendHist();
        event.preventDefault();
    });

    // 조회
    $scope.searchSmsSendHist = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 6달(186일) 제한
        if (diffDay > 186) {
            $scope._popMsg(messages['cmm.dateOver.6month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.orgnCd = $scope.orgnCd;
        params.orgnNm = $scope.orgnNm;
        params.userId = $scope.userId;
        params.userNm = $scope.userNm;
        params.smsUseRegFg = $scope.smsUseRegFgCombo.selectedValue;

        $scope._inquiryMain("/adi/sms/smsUserStatus/getSmsSendHistList.sb", params, function () {}, false);
    };

    // 선택
    $scope.selectedSmsSendHist;
    $scope.setSelectedSmsSendHist = function(store) {
        $scope.selectedSmsSendHist = store;
    };
    $scope.getSelectedSmsSendHist = function() {
        return $scope.selectedSmsSendHist;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 메세지 팝업 핸들러 추가
        $scope.wjMessageDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('messageDtlCtrl', $scope.getSelectedSmsSendHist());
            }, 50)
        });

        // 수신자정보 팝업 핸들러 추가
        $scope.wjAddresseeeDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('addresseeDtlCtrl', $scope.getSelectedSmsSendHist());
            }, 50)
        });
    });

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, 'SMS사용현황_발송이력_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };
}]);
