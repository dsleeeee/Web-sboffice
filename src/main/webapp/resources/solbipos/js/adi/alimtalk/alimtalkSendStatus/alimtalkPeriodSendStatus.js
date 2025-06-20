/****************************************************************
 *
 * 파일명 : alimtalkPeriodSendStatus.js
 * 설  명 : 알림톡 기간별 전송현황 JavaScript
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
app.controller('alimtalkPeriodSendStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkPeriodSendStatusCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#alimtalkPeriodSendStatusStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#alimtalkPeriodSendStatusEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
        dataItem.orgnCd             = messages["alimtalkPeriodSendStatus.orgnCd"];
        dataItem.orgnNm             = messages["alimtalkPeriodSendStatus.orgnNm"];
        dataItem.smsChargeAmt       = messages["alimtalkPeriodSendStatus.smsChargeAmt"];
        dataItem.smsChargeCnt       = messages["alimtalkPeriodSendStatus.smsChargeCnt"];
        dataItem.alkSendQty         = messages["alimtalkPeriodSendStatus.alk"];
        dataItem.alkWaitQty         = messages["alimtalkPeriodSendStatus.alk"];
        dataItem.alkSuccessQty      = messages["alimtalkPeriodSendStatus.alk"];
        dataItem.alkFailQty         = messages["alimtalkPeriodSendStatus.alk"];
        dataItem.alkSaleAmt         = messages["alimtalkPeriodSendStatus.alk"];
        dataItem.alkSmsSendQty      = messages["alimtalkPeriodSendStatus.alkSms"];
        dataItem.alkSmsWaitQty      = messages["alimtalkPeriodSendStatus.alkSms"];
        dataItem.alkSmsSuccessQty   = messages["alimtalkPeriodSendStatus.alkSms"];
        dataItem.alkSmsFailQty      = messages["alimtalkPeriodSendStatus.alkSms"];
        dataItem.alkSmsSaleAmt      = messages["alimtalkPeriodSendStatus.alkSms"];
        dataItem.alkLmsSendQty      = messages["alimtalkPeriodSendStatus.alkLms"];
        dataItem.alkLmsWaitQty      = messages["alimtalkPeriodSendStatus.alkLms"];
        dataItem.alkLmsSuccessQty   = messages["alimtalkPeriodSendStatus.alkLms"];
        dataItem.alkLmsFailQty      = messages["alimtalkPeriodSendStatus.alkLms"];
        dataItem.alkLmsSaleAmt      = messages["alimtalkPeriodSendStatus.alkLms"];
        dataItem.alkMmsSendQty      = messages["alimtalkPeriodSendStatus.alkMms"];
        dataItem.alkMmsWaitQty      = messages["alimtalkPeriodSendStatus.alkMms"];
        dataItem.alkMmsSuccessQty   = messages["alimtalkPeriodSendStatus.alkMms"];
        dataItem.alkMmsFailQty      = messages["alimtalkPeriodSendStatus.alkMms"];
        dataItem.alkMmsSaleAmt      = messages["alimtalkPeriodSendStatus.alkMms"];

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
    $scope.$on("alimtalkPeriodSendStatusCtrl", function(event, data) {
        $scope.searchAlimtalkPeriodSendStatus();
        event.preventDefault();
    });

    $scope.searchAlimtalkPeriodSendStatus = function() {
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

        $scope._inquiryMain("/adi/alimtalk/alimtalkSendStatus/alimtalkPeriodSendStatus/getAlimtalkPeriodSendStatusList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.alimtalkPeriodSendStatusFlex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.alimtalkPeriodSendStatusFlex, {
                includeColumnHeaders: true,
                includeCellStyles   : false,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, "알림톡 기간별 전송현황_" + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };
    // <-- //엑셀다운로드 -->
}]);