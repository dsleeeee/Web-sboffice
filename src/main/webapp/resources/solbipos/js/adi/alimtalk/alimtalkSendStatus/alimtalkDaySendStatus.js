/****************************************************************
 *
 * 파일명 : alimtalkSendStatus.js
 * 설  명 : 알림톡 일자별 전송현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.01     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  알림톡 일자별 전송현황 조회 그리드 생성
 */
app.controller('alimtalkDaySendStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkDaySendStatusCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#alimtalkDaySendStatusStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#alimtalkDaySendStatusEndDate", gvEndDate);

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
        dataItem.orgnCd             = messages["alimtalkDaySendStatus.orgnCd"];
        dataItem.orgnNm             = messages["alimtalkDaySendStatus.orgnNm"];
        dataItem.smsDate            = messages["alimtalkDaySendStatus.smsDate"];
        dataItem.smsChargeAmt       = messages["alimtalkDaySendStatus.smsChargeAmt"];
        dataItem.smsChargeCnt       = messages["alimtalkDaySendStatus.smsChargeCnt"];
        dataItem.alkSendQty         = messages["alimtalkDaySendStatus.alk"];
        dataItem.alkWaitQty         = messages["alimtalkDaySendStatus.alk"];
        dataItem.alkSuccessQty      = messages["alimtalkDaySendStatus.alk"];
        dataItem.alkFailQty         = messages["alimtalkDaySendStatus.alk"];
        dataItem.alkSmsSendQty      = messages["alimtalkDaySendStatus.alkSms"];
        dataItem.alkSmsWaitQty      = messages["alimtalkDaySendStatus.alkSms"];
        dataItem.alkSmsSuccessQty   = messages["alimtalkDaySendStatus.alkSms"];
        dataItem.alkSmsFailQty      = messages["alimtalkDaySendStatus.alkSms"];
        dataItem.alkLmsSendQty      = messages["alimtalkDaySendStatus.alkLms"];
        dataItem.alkLmsWaitQty      = messages["alimtalkDaySendStatus.alkLms"];
        dataItem.alkLmsSuccessQty   = messages["alimtalkDaySendStatus.alkLms"];
        dataItem.alkLmsFailQty      = messages["alimtalkDaySendStatus.alkLms"];
        dataItem.alkMmsSendQty      = messages["alimtalkDaySendStatus.alkMms"];
        dataItem.alkMmsWaitQty      = messages["alimtalkDaySendStatus.alkMms"];
        dataItem.alkMmsSuccessQty   = messages["alimtalkDaySendStatus.alkMms"];
        dataItem.alkMmsFailQty      = messages["alimtalkDaySendStatus.alkMms"];

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
    $scope.$on("alimtalkDaySendStatusCtrl", function(event, data) {
        $scope.searchAlimtalkDaySendStatus();
        event.preventDefault();
    });

    $scope.searchAlimtalkDaySendStatus = function() {
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
        params.listScale = $scope.alimtalkDaySendStatusListScale;

        $scope._inquiryMain("/adi/alimtalk/alimtalkSendStatus/alimtalkDaySendStatus/getAlimtalkDaySendStatusList.sb", params, function() {}, false);
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

        $scope._broadcast('alimtalkDaySendStatusExcelCtrl', params);
    };
    // <-- //엑셀다운로드 -->
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('alimtalkDaySendStatusExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkDaySendStatusExcelCtrl', $scope, $http, false));

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
        dataItem.orgnCd             = messages["alimtalkDaySendStatus.orgnCd"];
        dataItem.orgnNm             = messages["alimtalkDaySendStatus.orgnNm"];
        dataItem.smsDate            = messages["alimtalkDaySendStatus.smsDate"];
        dataItem.smsChargeAmt       = messages["alimtalkDaySendStatus.smsChargeAmt"];
        dataItem.smsChargeCnt       = messages["alimtalkDaySendStatus.smsChargeCnt"];
        dataItem.alkSendQty         = messages["alimtalkDaySendStatus.alk"];
        dataItem.alkWaitQty         = messages["alimtalkDaySendStatus.alk"];
        dataItem.alkSuccessQty      = messages["alimtalkDaySendStatus.alk"];
        dataItem.alkFailQty         = messages["alimtalkDaySendStatus.alk"];
        dataItem.alkSmsSendQty      = messages["alimtalkDaySendStatus.alkSms"];
        dataItem.alkSmsWaitQty      = messages["alimtalkDaySendStatus.alkSms"];
        dataItem.alkSmsSuccessQty   = messages["alimtalkDaySendStatus.alkSms"];
        dataItem.alkSmsFailQty      = messages["alimtalkDaySendStatus.alkSms"];
        dataItem.alkLmsSendQty      = messages["alimtalkDaySendStatus.alkLms"];
        dataItem.alkLmsWaitQty      = messages["alimtalkDaySendStatus.alkLms"];
        dataItem.alkLmsSuccessQty   = messages["alimtalkDaySendStatus.alkLms"];
        dataItem.alkLmsFailQty      = messages["alimtalkDaySendStatus.alkLms"];
        dataItem.alkMmsSendQty      = messages["alimtalkDaySendStatus.alkMms"];
        dataItem.alkMmsWaitQty      = messages["alimtalkDaySendStatus.alkMms"];
        dataItem.alkMmsSuccessQty   = messages["alimtalkDaySendStatus.alkMms"];
        dataItem.alkMmsFailQty      = messages["alimtalkDaySendStatus.alkMms"];

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
    $scope.$on("alimtalkDaySendStatusExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/adi/alimtalk/alimtalkSendStatus/alimtalkDaySendStatus/getAlimtalkDaySendStatusExcelList.sb", params, function() {
            if ($scope.alimtalkDaySendStatusExcelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.alimtalkDaySendStatusExcelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "알림톡 일자별 전송현황_" + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->
}]);