/****************************************************************
 *
 * 파일명 : daySendStatus.js
 * 설  명 : 일자별 전송현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일자별 전송현황 조회 그리드 생성
 */
app.controller('daySendStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySendStatusCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#daySendStatusStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#daySendStatusEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

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

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.orgnCd = messages["daySendStatus.orgnCd"];
        dataItem.orgnNm = messages["daySendStatus.orgnNm"];
        dataItem.smsDate = messages["daySendStatus.smsDate"];
        dataItem.smsChargeAmt = messages["daySendStatus.smsChargeAmt"];
        dataItem.smsChargeCnt = messages["daySendStatus.smsChargeCnt"];
        dataItem.totSendQty = messages["daySendStatus.tot"];
        dataItem.totWaitQty = messages["daySendStatus.tot"];
        dataItem.totSuccessQty = messages["daySendStatus.tot"];
        dataItem.totFailQty = messages["daySendStatus.tot"];
        dataItem.smsSendQty = messages["daySendStatus.sms"];
        dataItem.smsWaitQty = messages["daySendStatus.sms"];
        dataItem.smsSuccessQty = messages["daySendStatus.sms"];
        dataItem.smsFailQty = messages["daySendStatus.sms"];
        dataItem.lmsSendQty = messages["daySendStatus.lms"];
        dataItem.lmsWaitQty = messages["daySendStatus.lms"];
        dataItem.lmsSuccessQty = messages["daySendStatus.lms"];
        dataItem.lmsFailQty = messages["daySendStatus.lms"];
        dataItem.mmsSendQty = messages["daySendStatus.mms"];
        dataItem.mmsWaitQty = messages["daySendStatus.mms"];
        dataItem.mmsSuccessQty = messages["daySendStatus.mms"];
        dataItem.mmsFailQty = messages["daySendStatus.mms"];

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

    // <-- 검색 호출 -->
    $scope.$on("daySendStatusCtrl", function(event, data) {
        $scope.searchDaySendStatus();
        event.preventDefault();
    });

    $scope.searchDaySendStatus = function() {
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
        params.srchOrgnCd = $scope.srchOrgnCd;
        params.srchOrgnNm = $scope.srchOrgnNm;
        params.listScale = $scope.daySendStatusListScale;

        $scope._inquiryMain("/adi/sms/sendStatus/daySendStatus/getDaySendStatusList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.srchOrgnCd = $scope.srchOrgnCd;
        params.srchOrgnNm = $scope.srchOrgnNm;

        $scope._broadcast('daySendStatusExcelCtrl', params);
    };
    // <-- //엑셀다운로드 -->
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('daySendStatusExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySendStatusExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

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

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.orgnCd = messages["daySendStatus.orgnCd"];
        dataItem.orgnNm = messages["daySendStatus.orgnNm"];
        dataItem.smsDate = messages["daySendStatus.smsDate"];
        dataItem.smsChargeAmt = messages["daySendStatus.smsChargeAmt"];
        dataItem.smsChargeCnt = messages["daySendStatus.smsChargeCnt"];
        dataItem.totSendQty = messages["daySendStatus.tot"];
        dataItem.totWaitQty = messages["daySendStatus.tot"];
        dataItem.totSuccessQty = messages["daySendStatus.tot"];
        dataItem.totFailQty = messages["daySendStatus.tot"];
        dataItem.smsSendQty = messages["daySendStatus.sms"];
        dataItem.smsWaitQty = messages["daySendStatus.sms"];
        dataItem.smsSuccessQty = messages["daySendStatus.sms"];
        dataItem.smsFailQty = messages["daySendStatus.sms"];
        dataItem.lmsSendQty = messages["daySendStatus.lms"];
        dataItem.lmsWaitQty = messages["daySendStatus.lms"];
        dataItem.lmsSuccessQty = messages["daySendStatus.lms"];
        dataItem.lmsFailQty = messages["daySendStatus.lms"];
        dataItem.mmsSendQty = messages["daySendStatus.mms"];
        dataItem.mmsWaitQty = messages["daySendStatus.mms"];
        dataItem.mmsSuccessQty = messages["daySendStatus.mms"];
        dataItem.mmsFailQty = messages["daySendStatus.mms"];

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

    // <-- 검색 호출 -->
    $scope.$on("daySendStatusExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/adi/sms/sendStatus/daySendStatus/getDaySendStatusExcelList.sb", params, function() {
            if ($scope.daySendStatusExcelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.daySendStatusExcelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "일자별 전송현황_" + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->
}]);