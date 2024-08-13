/****************************************************************
 *
 * 파일명 : smsChargeHist.js
 * 설  명 : SMS충전내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.19     김설아      1.0
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

/**
 *  SMS충전내역 조회 그리드 생성
 */
app.controller('smsChargeHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsChargeHistCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("successYnCombo", successYnComboData); // 성공여부
    $scope._setComboData("pgresourceCombo", gpgresourceComboData); // 결제수단

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.successYnDataMap = new wijmo.grid.DataMap(successYnComboData, 'value', 'name'); // 성공여부
        $scope.pgresourceDataMap = new wijmo.grid.DataMap(gpgresourceComboData, 'value', 'name'); // 결제수단

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // if (col.format === "date") {
                //     e.cell.innerHTML = getFormatDate(e.cell.innerText);
                // } else if (col.format === "dateTime") {
                //     e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                // } else if (col.format === "time") {
                //     e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                // }

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

    // <-- 검색 호출 -->
    $scope.$on("smsChargeHistCtrl", function(event, data) {
        $scope.searchSmsChargeHist();
        event.preventDefault();
    });

    $scope.searchSmsChargeHist = function() {
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
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.pgresource = $scope.pgresource;
        params.successYn = $scope.successYn;
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/adi/sms/smsChargeHist/smsChargeHist/getSmsChargeHistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // SMS임의충전
    $scope.smsChargeRegist = function () {
        $scope.wjSmsChargeRegistLayer.show(true);
        event.preventDefault();
    };

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
    $scope.restSmsAmtPopup = function(){
        $scope.wjRestSmsAmtLayer.show(true);
    };

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
        $scope.wjSmsChargeRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsChargeRegistCtrl', null);
            }, 50)
        });

        // SMS결제상세 팝업 핸들러 추가
        $scope.wjSmsChargeDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsChargeDtlCtrl', $scope.getSelectedSmsChargeHist());
            }, 50)
        });

        // 현재잔여금액 팝업 핸들러 추가
        $scope.wjRestSmsAmtLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('restSmsAmtCtrl', null);
            }, 50)
        });
    });

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.pgresource = $scope.pgresource;
        params.successYn = $scope.successYn;

        $scope._broadcast('smsChargeHistExcelCtrl', params);
    };
    // <-- //엑셀다운로드 -->
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('smsChargeHistExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsChargeHistExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.successYnDataMap = new wijmo.grid.DataMap(successYnComboData, 'value', 'name'); // 성공여부
        $scope.pgresourceDataMap = new wijmo.grid.DataMap(gpgresourceComboData, 'value', 'name'); // 결제수단

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // if (col.format === "date") {
                //     e.cell.innerHTML = getFormatDate(e.cell.innerText);
                // } else if (col.format === "dateTime") {
                //     e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                // } else if (col.format === "time") {
                //     e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                // }
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

    // <-- 검색 호출 -->
    $scope.$on("smsChargeHistExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/adi/sms/smsChargeHist/smsChargeHist/getSmsChargeHistExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "충전내역_" + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->
}]);