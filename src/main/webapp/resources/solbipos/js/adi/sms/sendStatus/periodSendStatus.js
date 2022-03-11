/****************************************************************
 *
 * 파일명 : periodSendStatus.js
 * 설  명 : 기간별 전송현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.08     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일자별 전송현황 조회 그리드 생성
 */
app.controller('periodSendStatusCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('periodSendStatusCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#periodSendStatusStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#periodSendStatusEndDate", gvEndDate);

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
        dataItem.ognCd = messages["periodSendStatus.orgnCd"];
        dataItem.orgnNm = messages["periodSendStatus.orgnNm"];
        dataItem.smsChargeAmt = messages["periodSendStatus.smsChargeAmt"];
        dataItem.smsChargeCnt = messages["periodSendStatus.smsChargeCnt"];
        dataItem.totSendQty = messages["periodSendStatus.tot"];
        dataItem.totWaitQty = messages["periodSendStatus.tot"];
        dataItem.totSuccessQty = messages["periodSendStatus.tot"];
        dataItem.totFailQty = messages["periodSendStatus.tot"];
        dataItem.totSaleAmt = messages["periodSendStatus.tot"];
        dataItem.smsSendQty = messages["periodSendStatus.sms"];
        dataItem.smsWaitQty = messages["periodSendStatus.sms"];
        dataItem.smsSuccessQty = messages["periodSendStatus.sms"];
        dataItem.smsFailQty = messages["periodSendStatus.sms"];
        dataItem.smsSaleAmt = messages["periodSendStatus.sms"];
        dataItem.lmsSendQty = messages["periodSendStatus.lms"];
        dataItem.lmsWaitQty = messages["periodSendStatus.lms"];
        dataItem.lmsSuccessQty = messages["periodSendStatus.lms"];
        dataItem.lmsFailQty = messages["periodSendStatus.lms"];
        dataItem.lmsSaleAmt = messages["periodSendStatus.lms"];
        dataItem.mmsSendQty = messages["periodSendStatus.mms"];
        dataItem.mmsWaitQty = messages["periodSendStatus.mms"];
        dataItem.mmsSuccessQty = messages["periodSendStatus.mms"];
        dataItem.mmsFailQty = messages["periodSendStatus.mms"];
        dataItem.mmsSaleAmt = messages["periodSendStatus.mms"];

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
    $scope.$on("periodSendStatusCtrl", function(event, data) {
        $scope.searchPeriodSendStatus();
        event.preventDefault();
    });

    $scope.searchPeriodSendStatus = function() {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
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

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.listScale = $scope.periodSendStatusListScale;

        $scope._inquiryMain("/adi/sms/sendStatus/periodSendStatus/getPeriodSendStatusList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);