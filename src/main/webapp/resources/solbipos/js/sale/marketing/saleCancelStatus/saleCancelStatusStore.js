/****************************************************************
 *
 * 파일명 : saleCancelStatusAll.js
 * 설  명 : 미스터피자 > 마케팅조회 > 취소현황 > 선택점포 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.05     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 취소현황 선택점포 탭 controller */
app.controller('saleCancelStatusStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleCancelStatusStoreCtrl', $scope, $http, $timeout, true));

    $scope.srchStartDateStore = wcombo.genDateVal("#srchStartDateStore", getToday());
    $scope.srchEndDateStore   = wcombo.genDateVal("#srchEndDateStore", getToday());

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // picker 사용시 호출 : 미사용시 호출안함
        s.refresh();
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("saleCancelStatusStoreCtrl");

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.storeCd		= '';
        dataItem.storeNm      	= '';
        dataItem.saleCnt  	    = '';
        dataItem.realSaleAmt    = '';
        dataItem.rtnCnt  	    = '';
        dataItem.rtnAmt      	= '';
        dataItem.cancelCnt  	= '';
        dataItem.cancelAmt  	= '';

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


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleCancelStatusStoreCtrl", function (event, data) {
        $scope.searchSaleCancelStatusStoreList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 코너별매출일자별 리스트 조회
    $scope.searchSaleCancelStatusStoreList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDateStore.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDateStore.value, 'yyyy-MM-dd'));
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

        // 파라미터
        var params          = {};
        params.storeCd      = $("#saleCancelStatusStoreSelctStoreCd").val();
        params.startDate    = wijmo.Globalize.format($scope.srchStartDateStore.value, 'yyyyMMdd');
        params.endDate      = wijmo.Globalize.format($scope.srchEndDateStore.value, 'yyyyMMdd');
        if(params.startDate > params.endDate){
            $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
            return false;
        }
        if(isEmptyObject($("#saleCancelStatusStoreSelctStoreCd").val())){
            $scope._popMsg(messages["cmm.require.selectStore"]);
            return false;
        }
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/marketing/saleCancelStatus/saleCancelStatus/getSaleCancelStatusStoreList.sb", params, function (){
            var grid = wijmo.Control.getControl("#saleCancelStatusStoreMainGrid");
            var dataItem         = {};
            dataItem.storeCd   	= params.startDate + '~' + params.endDate;
            dataItem.storeNm    = params.startDate + '~' + params.endDate;
            dataItem.saleDate   = params.startDate + '~' + params.endDate;
            dataItem.posNo      = params.startDate + '~' + params.endDate;
            dataItem.billNo     = params.startDate + '~' + params.endDate;
            dataItem.cancelDt   = params.startDate + '~' + params.endDate;
            dataItem.cancelFg  	= params.startDate + '~' + params.endDate;
            dataItem.totSaleAmt	= params.startDate + '~' + params.endDate;
            dataItem.totDcAmt   = params.startDate + '~' + params.endDate;
            dataItem.cancelAmt 	= params.startDate + '~' + params.endDate;
            dataItem.orderFg   	= params.startDate + '~' + params.endDate;
            grid.columnHeaders.rows[0].dataItem = dataItem;

        });
    };

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
                "취소현황(선택점포)_" + wijmo.Globalize.format($scope.srchStartDateStore.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format($scope.srchEndDateStore.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);