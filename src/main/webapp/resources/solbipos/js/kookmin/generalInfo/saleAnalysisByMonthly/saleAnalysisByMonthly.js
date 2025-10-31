/****************************************************************
 *
 * 파일명 : saleAnalysisByMonthly.js
 * 설  명 : 국민대 > 총괄정보 > 월별매출분석 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.23     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** 월별매출분석 그리드 controller */
app.controller('saleAnalysisByMonthlyCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleAnalysisByMonthlyCtrl', $scope, $http, false));

    // 조회월
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("saleAnalysisByMonthlyCtrl");

        //Grid Header 2줄 - START	----------------------------------------------------------------
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        //첫째줄 Header 생성
        var dataItem = {};
        dataItem.branchNm = messages["saleAnalysisByMonthly.type"];
        dataItem.storeNm = messages["saleAnalysisByMonthly.storeNm"];
        dataItem.totSaleAmt = messages["saleAnalysisByMonthly.sale"];
        dataItem.bTotSaleAmt = messages["saleAnalysisByMonthly.sale"];
        dataItem.totSaleAmtContrast = messages["saleAnalysisByMonthly.sale"];
        dataItem.totSaleAmtPercent = messages["saleAnalysisByMonthly.sale"];
        dataItem.saleQty = messages["saleAnalysisByMonthly.saleQty"];
        dataItem.bSaleQty = messages["saleAnalysisByMonthly.saleQty"];
        dataItem.saleQtyContrast = messages["saleAnalysisByMonthly.saleQty"];
        dataItem.saleQtyPercent = messages["saleAnalysisByMonthly.saleQty"];
        dataItem.totGuestCnt = messages["saleAnalysisByMonthly.guestCnt"];
        dataItem.bTotGuestCnt = messages["saleAnalysisByMonthly.guestCnt"];
        dataItem.totGuestCntContrast = messages["saleAnalysisByMonthly.guestCnt"];
        dataItem.totGuestCntPercent = messages["saleAnalysisByMonthly.guestCnt"];
        dataItem.weekdays = messages["saleAnalysisByMonthly.openDay"];
        dataItem.bWeekdays = messages["saleAnalysisByMonthly.openDay"];
        dataItem.weekdaysContrast = messages["saleAnalysisByMonthly.openDay"];
        dataItem.weekend = messages["saleAnalysisByMonthly.openDay"];
        dataItem.bWeekend = messages["saleAnalysisByMonthly.openDay"];
        dataItem.weekendContrast = messages["saleAnalysisByMonthly.openDay"];


        s.columnHeaders.rows[0].dataItem = dataItem;
        //Grid Header 2줄 - END		----------------------------------------------------------------

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
                panel.rows   [r].allowMerging = true;
                panel.columns[c].allowMerging = true;

                wijmo.setCss(cell, {
                        display: 'table',
                        tableLayout: 'fixed'
                    }
                );

                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

                wijmo.setCss(cell.children[0], {
                        display: 'table-cell',
                        verticalAlign: 'middle',
                        textAlign: 'center'
                    }
                );
            } else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            } else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }	//s.itemFormatter = function (panel, r, c, cell) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleAnalysisByMonthlyCtrl", function (event, data) {

        // 월별매출분석 리스트 조회
        $scope.getSaleAnalysisByMonthlyList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 월별매출분석 리스트 조회
    $scope.getSaleAnalysisByMonthlyList = function () {

        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            s_alert.pop(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 한달(31 일) 제한
        if (diffDay > 0) {
            s_alert.pop(messages['cmm.dateOver.1month.error']);
            return false;
        }

        startDt = wijmo.Globalize.format(startMonth.value, 'yyyyMM01');
        endDt = wijmo.Globalize.format(endMonth.value, 'yyyyMM31');
        var lastStartDt = (Number(startDt.substr(0, 4)) - 1).toString() + startDt.substr(4);
        var lastEndDt = (Number(endDt.substr(0, 4)) - 1).toString() + endDt.substr(4);

        // 파라미터
        var params = {};
        params.startDate = startDt;
        params.endDate = endDt;
        params.lastStartDate = lastStartDt;
        params.lastEndDate = lastEndDt;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/generalInfo/saleAnalysisByMonthly/saleAnalysisByMonthly/getSaleAnalysisByMonthlyList.sb", params, function () {

            var grid = wijmo.Control.getControl("#wjGrid");

            var tot_bTotSaleAmt = 0;
            var tot_totSaleAmtContrast = 0;
            var tot_bSaleQty = 0;
            var tot_saleQtyContrast = 0;
            var tot_bTotGuestCnt = 0;
            var tot_totGuestCntContrast = 0;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var item = $scope.flex.collectionView.items[i];

                tot_bTotSaleAmt += item.bTotSaleAmt;
                tot_totSaleAmtContrast += item.totSaleAmtContrast;
                tot_bSaleQty += item.bSaleQty;
                tot_saleQtyContrast += item.saleQtyContrast;
                tot_bTotGuestCnt += item.bTotGuestCnt ;
                tot_totGuestCntContrast += item.totGuestCntContrast ;

                if (!isNull(item.totSaleAmtPercent)) {
                    item.totSaleAmtPercent = item.totSaleAmtPercent + '%';
                }

                if (!isNull(item.saleQtyPercent)) {
                    item.saleQtyPercent = item.saleQtyPercent + '%';
                }

                if (!isNull(item.totGuestCntPercent)) {
                    item.totGuestCntPercent = item.totGuestCntPercent + '%';
                }
            }

            if (tot_bTotSaleAmt != 0) {
                grid.columnFooters.setCellData(0, 5, Math.round((tot_totSaleAmtContrast / tot_bTotSaleAmt) * 100 * 10) / 10 + '%');
            } else {
                grid.columnFooters.setCellData(0, 5, '');
            }

            if (tot_bSaleQty != 0) {
                grid.columnFooters.setCellData(0, 9, Math.round((tot_saleQtyContrast / tot_bSaleQty) * 100 * 10) / 10 + '%');
            } else {
                grid.columnFooters.setCellData(0, 9, '');
            }

            if (tot_bTotGuestCnt != 0) {
                grid.columnFooters.setCellData(0, 13, Math.round((tot_totGuestCntContrast / tot_bTotGuestCnt) * 100 * 10) / 10 + '%');
            } else {
                grid.columnFooters.setCellData(0, 13, '');
            }

        }, false);
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, '월별매출분석'+ '_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 리포트
    $scope.report = function () {

        if($scope.flex.rows.length <= 0){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            s_alert.pop(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 한달(31 일) 제한
        if (diffDay > 0) {
            s_alert.pop(messages['cmm.dateOver.1month.error']);
            return false;
        }

        startDt = wijmo.Globalize.format(startMonth.value, 'yyyyMM01');
        endDt = wijmo.Globalize.format(endMonth.value, 'yyyyMM31');
        var lastStartDt = (Number(startDt.substr(0, 4)) - 1).toString() + startDt.substr(4);
        var lastEndDt = (Number(endDt.substr(0, 4)) - 1).toString() + endDt.substr(4);

        // 파라미터
        var params = {};
        params.startDate = startDt;
        params.endDate = endDt;
        params.lastStartDate = lastStartDt;
        params.lastEndDate = lastEndDt;
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyy-MM')
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyy-MM');

        $scope._broadcast('saleAnalysisByMonthlyReportCtrl', params);
    };

}]);