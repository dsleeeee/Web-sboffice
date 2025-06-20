/****************************************************************
 *
 * 파일명 : RemainAmtChk2.js
 * 설  명 : 잔여금액확인 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.10     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**
 * 가상로그인 그리드 생성
 */
app.controller('remainAmtChkCtrl',  ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('remainAmtChkCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#remainAmtChkStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#remainAmtChkEndDate", gvEndDate);


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
        dataItem.orgnCd             = messages["remainAmtChk.orgnCd"];
        dataItem.orgnNm             = messages["remainAmtChk.orgnNm"];
        dataItem.preRemainAmt       = messages["remainAmtChk.preRemainAmt"];
        dataItem.smsChargeAmt       = messages["remainAmtChk.chargeAmt"];
        dataItem.smsChargeCnt       = messages["remainAmtChk.chargeCnt"];
        dataItem.totSendQty         = messages["remainAmtChk.all"];
        dataItem.totWaitQty         = messages["remainAmtChk.all"];
        dataItem.totSuccessQty      = messages["remainAmtChk.all"];
        dataItem.totFailQty         = messages["remainAmtChk.all"];
        dataItem.totSaleAmt         = messages["remainAmtChk.all"];
        dataItem.smsSendQty         = messages["remainAmtChk.sms"];
        dataItem.smsWaitQty         = messages["remainAmtChk.sms"];
        dataItem.smsSuccessQty      = messages["remainAmtChk.sms"];
        dataItem.smsFailQty         = messages["remainAmtChk.sms"];
        dataItem.smsSaleAmt         = messages["remainAmtChk.sms"];
        dataItem.lmsSendQty         = messages["remainAmtChk.lms"];
        dataItem.lmsWaitQty         = messages["remainAmtChk.lms"];
        dataItem.lmsSuccessQty      = messages["remainAmtChk.lms"];
        dataItem.lmsFailQty         = messages["remainAmtChk.lms"];
        dataItem.lmsSaleAmt         = messages["remainAmtChk.lms"];
        dataItem.mmsSendQty         = messages["remainAmtChk.mms"];
        dataItem.mmsWaitQty         = messages["remainAmtChk.mms"];
        dataItem.mmsSuccessQty      = messages["remainAmtChk.mms"];
        dataItem.mmsFailQty         = messages["remainAmtChk.mms"];
        dataItem.mmsSaleAmt         = messages["remainAmtChk.mms"];
        dataItem.alkSendQty         = messages["remainAmtChk.alimtalk"];
        dataItem.alkWaitQty         = messages["remainAmtChk.alimtalk"];
        dataItem.alkSuccessQty      = messages["remainAmtChk.alimtalk"];
        dataItem.alkFailQty         = messages["remainAmtChk.alimtalk"];
        dataItem.alkSaleAmt         = messages["remainAmtChk.alimtalk"];
        dataItem.alkSmsSendQty      = messages["remainAmtChk.alimtalkSms"];
        dataItem.alkSmsWaitQty      = messages["remainAmtChk.alimtalkSms"];
        dataItem.alkSmsSuccessQty   = messages["remainAmtChk.alimtalkSms"];
        dataItem.alkSmsFailQty      = messages["remainAmtChk.alimtalkSms"];
        dataItem.alkSmsSaleAmt      = messages["remainAmtChk.alimtalkSms"];
        dataItem.alkLmsSendQty      = messages["remainAmtChk.alimtalkLms"];
        dataItem.alkLmsWaitQty      = messages["remainAmtChk.alimtalkLms"];
        dataItem.alkLmsSuccessQty   = messages["remainAmtChk.alimtalkLms"];
        dataItem.alkLmsFailQty      = messages["remainAmtChk.alimtalkLms"];
        dataItem.alkLmsSaleAmt      = messages["remainAmtChk.alimtalkLms"];
        dataItem.alkMmsSendQty      = messages["remainAmtChk.alimtalkMms"];
        dataItem.alkMmsWaitQty      = messages["remainAmtChk.alimtalkMms"];
        dataItem.alkMmsSuccessQty   = messages["remainAmtChk.alimtalkMms"];
        dataItem.alkMmsFailQty      = messages["remainAmtChk.alimtalkMms"];
        dataItem.alkMmsSaleAmt      = messages["remainAmtChk.alimtalkMms"];
        dataItem.calcRemainAmt      = messages["remainAmtChk.calcRemainAmt"];
        dataItem.remainAmt          = messages["remainAmtChk.remainAmt"];
        dataItem.calcCurrRemainAmt  = messages["remainAmtChk.calcCurrRemainAmt"];
        dataItem.currRemainAmt      = messages["remainAmtChk.currRemainAmt"];
        dataItem.remainAmtFg        = messages["remainAmtChk.remainAmtFg"];
        dataItem.currRemainAmtFg    = messages["remainAmtChk.currRemainAmtFg"];

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

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "orgnCd") { // 전표번호
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "orgnCd") {
                    var params     = {};
                    params.srchOrgnCd   = selectedRow.orgnCd;
                    params.srchOrgnNm   = selectedRow.orgnNm;
                    params.startDate    = $scope.startDate;
                    params.endDate      = $scope.endDate;
                    $scope.remainAmtHistLayer.show(true);
                    $scope._broadcast('remainAmtHistCtrl', params);
                }
            }
        });

    };
    // 가상로그인 그리드 조회
    $scope.$on("remainAmtChkCtrl", function(event, data) {
        // 파라미터
        $scope.getRemainAmtChkList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.getRemainAmtChkList = function() {

        // 조회기간
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM'));

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        var params = {};

        params.startDate    = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate      = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.srchOrgnCd   = $scope.srchOrgnCd;
        params.srchOrgnNm   = $scope.srchOrgnNm;

        $scope.startDate    = params.startDate;
        $scope.endDate      = params.endDate;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/adi/sms/remainAmtChk/remainAmtChk/getRemainAmtChkList.sb", params, function() {

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
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                '잔여금액확인' + $scope.startDate + '~' + $scope.endDate + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
