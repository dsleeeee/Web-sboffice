/****************************************************************
 *
 * 파일명 : smsChargeStatus.js
 * 설  명 : SMS 충전 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.07     이다솜      1.0
 *
 * **************************************************************/
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

app.controller('smsChargeStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('smsChargeStatusCtrl', $scope, $http, $timeout, true));

    // 충전일자 셋팅
    var startDate = wcombo.genDateVal("#startDate3", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate3", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("smsUseRegFg", smsUseRegFgData); // SMS사용등록 구분

    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.successYnDataMap = new wijmo.grid.DataMap(successYnComboData, 'value', 'name'); // 성공여부
        $scope.pgresourceDataMap = new wijmo.grid.DataMap(gpgresourceComboData, 'value', 'name'); // 결제수단

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 승인번호
                if (col.binding === "controlno") {
                    // 값이 있으면 링크 효과
                    if (item[("controlno")] !== '*') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }

                // 결제수단
                if (col.binding === "pgresource") {
                    // 값이 있으면 링크 효과
                    if (item[("pgresource")] !== '*') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }

                // 비고(결과메시지)
                if (col.binding === "resultmessage") {
                    if (item[("controlno")] == '*') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                var selectedRow = s.rows[ht.row].dataItem;

                // 승인번호 클릭시 상세정보 조회
                if (col.binding === "controlno") {
                    if (selectedRow[("controlno")] !== '*') {
                        $scope.setSelectedSmsChargeHist(selectedRow);
                        $scope.wjSmsChargeDtlLayer.show(true);
                        event.preventDefault();
                    }
                }

                // 결제수단 클릭시 상세정보 조회
                if (col.binding === "pgresource") {
                    if (selectedRow[("pgresource")] !== '*') {
                        // SMS충전영수증
                        $scope.smsChargeBill(selectedRow);
                    }
                }

                // 비고(결과메시지) 수정 탭
                if (col.binding === "resultmessage") {
                    if (selectedRow[("controlno")] == '*') {
                        $scope.setSelectedSmsChargeHist(selectedRow);
                        $scope.wjResultmessageEditLayer.show(true);
                        $("#resultmessage").val(selectedRow[("resultmessage")]);
                        $("#orgnCd").val(selectedRow[("orgnCd")]);
                        $("#chargeDate").val(selectedRow[("chargeDate")].replaceAll("-", ""));
                        $("#chargeTime").val(selectedRow[("chargeTime")].replaceAll(":", ""));
                        $("#callPage").val("smsChargeStatus");
                        event.preventDefault();;
                    }
                }
            }
        });

        // <-- 그리드 합치기 -->
        s.allowMerging = 'Cells';
        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.Cell) {

                // 컬럼 병합(그리드 합치기)
                if(panel.columns[c].binding == "orgn") {
                    panel.columns[c].allowMerging = true;
                }

                // 합쳐진 컬럼 데이터 가운데 정렬
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div>' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });

                // readOnly 배경색 표시
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }

                // 오른쪽 정렬
                if (col.binding === "baseChargeAmt" || col.binding === "chargeAmt" || col.binding === "vatAmt" || col.binding === "chargeTot") {
                    wijmo.setCss(cell.children[0], {
                        display      : 'table-cell',
                        verticalAlign: 'middle',
                        textAlign    : 'right'
                    });
                }
            }
        }
        // <-- //그리드 합치기 -->
    };

    $scope.$on("smsChargeStatusCtrl", function (event, data) {
        // 조회
        $scope.searchSmsChargeStatus();
        event.preventDefault();
    });

    // 조회
    $scope.searchSmsChargeStatus = function () {

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

        $scope._inquiryMain("/adi/sms/smsUserStatus/getSmsChargeStatusList.sb", params, function () {}, false);
    };

    // SMS임의충전
    /*$scope.smsChargeRegist = function () {
        $scope.wjSmsChargeRegistLayer.show(true);
        event.preventDefault();
    };*/

    // SMS충전영수증
    $scope.smsChargeBill = function (data) {
        var cmd = "";
        var height = "";
        // 신용카드
        if(data.pgresource = '11') {
            cmd = "card_bill";
            height = "815";
        } else {
            cmd = "vcnt_bill";
            height = "695";
        }
        var tno = data.controlno; // KCP거래번호
        var order_no = data.approvalnum; // 주문번호 ordr_idxx
        var trade_mony = data.chargeTot; // 거래금액 amount

        var url = siteUrl + "/assist/bill.BillActionNew.do?cmd=" + cmd + "&tno=" + tno + "&order_no=" + order_no + "&trade_mony=" + trade_mony;
        window.open(url, "", "width=470,height=" + height + ",resizable=yes,scrollbars=yes");
    };

    // 현재잔여금액
    /*$scope.restSmsAmtPopup = function(){
        $scope.wjRestSmsAmtLayer.show(true);
    };*/

    // 선택
    $scope.selectedSmsChargeHist;
    $scope.setSelectedSmsChargeHist = function(store) {
        $scope.selectedSmsChargeHist = store;
    };
    $scope.getSelectedSmsChargeHist = function() {
        return $scope.selectedSmsChargeHist;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // SMS임의충전 팝업 핸들러 추가
        /*$scope.wjSmsChargeRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsChargeRegistCtrl', null);
            }, 50)
        });*/

        // SMS결제상세 팝업 핸들러 추가
        $scope.wjSmsChargeDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsChargeDtlCtrl', $scope.getSelectedSmsChargeHist());
            }, 50)
        });

        // 현재잔여금액 팝업 핸들러 추가
        /*$scope.wjRestSmsAmtLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('restSmsAmtCtrl', null);
            }, 50)
        });*/
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
            }, 'SMS사용현황_충전현황_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };
}]);
