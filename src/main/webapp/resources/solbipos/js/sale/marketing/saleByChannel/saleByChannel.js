/****************************************************************
 *
 * 파일명 : saleByChannel.js
 * 설  명 : 미스터피자 > 마케팅조회 > 채널별매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.25     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  채널별 매출 그리드 생성
 */
app.controller('saleByChannelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleByChannelCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // // add the new GroupRow to the grid's 'columnFooters' panel
        // s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // // add a sigma to the header to show that this is a summary row
        // s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.saleDate   = messages["saleByChannel.saleDate"];
        dataItem.yoil       = messages["saleByChannel.yoil"];
        dataItem.storeCd    = messages["saleByChannel.storeCd"];
        dataItem.storeNm    = messages["saleByChannel.storeNm"];

        // 주문채널별 header 셋팅
        for (var i = 0; i < dlvrColList.length; i++) {
            dataItem['realSaleCnt' + dlvrColList[i].dlvrFg + dlvrColList[i].dlvrCd] = messages["saleByChannel.realSaleCnt"];
            if(i === dlvrColList.length-1){
                dataItem['realSaleCntEtc'] = messages["saleByChannel.realSaleCnt"];
                dataItem['realSaleCntSum'] = messages["saleByChannel.realSaleCnt"];
            }
        }
        // 주문채널별 header 셋팅
        for (var i = 0; i < dlvrColList.length; i++) {
            dataItem['totSaleAmt' + dlvrColList[i].dlvrFg + dlvrColList[i].dlvrCd] = messages["saleByChannel.totSaleAmt"];
            if(i === dlvrColList.length-1){
                dataItem['totSaleAmtEtc'] = messages["saleByChannel.totSaleAmt"];
                dataItem['totSaleAmtSum'] = messages["saleByChannel.totSaleAmt"];
            }
        }
        // 주문채널별 header 셋팅
        for (var i = 0; i < dlvrColList.length; i++) {
            dataItem['totDcAmt' + dlvrColList[i].dlvrFg + dlvrColList[i].dlvrCd] = messages["saleByChannel.totDcAmt"];
            if(i === dlvrColList.length-1){
                dataItem['totDcAmtEtc'] = messages["saleByChannel.totDcAmt"];
                dataItem['totDcAmtSum'] = messages["saleByChannel.totDcAmt"];
            }
        }
        // 주문채널별 header 셋팅
        for (var i = 0; i < dlvrColList.length; i++) {
            dataItem['realSaleAmt' + dlvrColList[i].dlvrFg + dlvrColList[i].dlvrCd] = messages["saleByChannel.realSaleAmt"];
            if(i === dlvrColList.length-1){
                dataItem['realSaleAmtEtc'] = messages["saleByChannel.realSaleAmt"];
                dataItem['realSaleAmtSum'] = messages["saleByChannel.realSaleAmt"];
            }
        }

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
    $scope.$on("saleByChannelCtrl", function (event, data) {
        $scope.searchSaleByChannelList();
        event.preventDefault();
    });

    $scope.searchSaleByChannelList = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        var chkStoreCnt = [];
        if($("#saleByChannelSelectStoreCd").val() !== null && $("#saleByChannelSelectStoreCd").val() !== undefined && $("#saleByChannelSelectStoreCd").val() !== ''){
            chkStoreCnt = $("#saleByChannelSelectStoreCd").val().split(',');
        }

        if(chkStoreCnt.length === 1){
            // 조회일자 최대 6달(186일) 제한
            if (diffDay > 186) {
                $scope._popMsg(messages['cmm.dateOver.6month.error']);
                return false;
            }
        }else {
            // 조회일자 최대 한달(31일) 제한
            if (diffDay > 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }
        }

        // 파라미터
        var params = {};
        params.startDate    = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate      = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCd      = $("#saleByChannelSelectStoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/marketing/saleByChannel/saleByChannel/getSaleByChannelList.sb", params, function (){});
    };
    // <-- //검색 호출 -->

    // 현재화면 엑셀다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "채널별매출_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
