/**
 * get application
 */
var app = agrid.getApp();

/** 매장현황 controller */
app.controller('saleApprNcashCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleApprNcashCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    
    // 첫째줄 헤더 생성
    var dataItem         	= {};
    dataItem.saleDate      	= messages["saleComPopup.ncash.saleDate"];
    dataItem.posNo  		= messages["saleComPopup.ncash.posNo"];
    dataItem.billNo  		= messages["saleComPopup.ncash.billNo"];
    dataItem.apprProc      	= messages["saleComPopup.ncash.apprSaleAmt"];
    dataItem.saleAmt      	= messages["saleComPopup.ncash.apprSaleAmt"];
    dataItem.tipAmt    		= messages["saleComPopup.ncash.apprSaleAmt"];   
    dataItem.vatAmt   		= messages["saleComPopup.ncash.apprSaleAmt"];    
    dataItem.cashBillCardNo   		= messages["saleComPopup.ncash.cashBill"];
    dataItem.cashBillCardTypeFg   	= messages["saleComPopup.ncash.cashBill"];    
    dataItem.apprDt     	= messages["saleComPopup.ncard.apprDt"];
    dataItem.apprNo     	= messages["saleComPopup.ncard.apprNo"];
    dataItem.apprAmt    	= messages["saleComPopup.ncard.apprAmt"];
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
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleApprNcashCtrl", function (event, data) {
    $scope.storeCd  	= data.storeCd;
    $scope.arrStorePos	= data.posNo;
    $scope.saleYn 		= data.saleYn;
    $scope.apprProcFg 	= data.apprProcFg;
    $scope.startDate 	= data.startDate;
    $scope.endDate		= data.endDate;
    $scope.chkPop 		= data.chkPop;

    $scope.apprNcashLayer.show(true);

    $scope.searchSaleComNcashList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 테이블별 리스트 조회
  $scope.searchSaleComNcashList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $scope.storeCd;
    params.arrStorePos	= $scope.arrStorePos;
    params.saleYn 		= $scope.saleYn;
    params.apprProcFg 	= $scope.apprProcFg;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.chkPop 	 = $scope.chkPop;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/com/popup/appr/view.sb", params);
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
            }, '승인현황_매장현황_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);