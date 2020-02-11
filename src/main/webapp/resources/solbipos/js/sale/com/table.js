/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('saleComTableCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleComTableCtrl', $scope, $http, true));


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
    dataItem.posNo      	= messages["saleComPopup.posNo"];
    dataItem.billNo  		= messages["saleComPopup.billNo"];
    dataItem.billDt    		= messages["saleComPopup.billDt"];
    dataItem.saleFg 		= messages["saleComPopup.saleFg"];
    dataItem.totSaleAmt     = messages["saleComPopup.totSaleAmt"];
    dataItem.realSaleAmt    = messages["saleComPopup.realSaleAmt"];
    dataItem.netSaleAmt   	= messages["saleComPopup.netSaleAmt"];
    dataItem.taxSaleAmt   	= messages["saleComPopup.taxSaleAmt"];
    dataItem.payTot     	= messages["saleComPopup.payChoice"];
    dataItem.pay01     		= messages["saleComPopup.payChoice"];
    dataItem.pay02     		= messages["saleComPopup.payChoice"];
    dataItem.pay03     		= messages["saleComPopup.payChoice"];
    dataItem.guestTot    	= messages["saleComPopup.guestTot"];
    dataItem.guestCnt1    	= messages["saleComPopup.guestCnt"];
    dataItem.guestCnt2    	= messages["saleComPopup.guestCnt"];
    dataItem.guestCnt3    	= messages["saleComPopup.guestCnt"];
    dataItem.guestCnt4    	= messages["saleComPopup.guestCnt"];
    dataItem.guestAmt   	= messages["saleComPopup.guestAmt"];

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
  $scope.$on("saleComTableCtrl", function (event, data) {

    $scope.storeCd  	= data.storeCd;
    $scope.startDate 	= data.startDate;
    $scope.endDate		= data.endDate;
    $scope.tblCd    	= data.tblCd;
    $scope.saleDate     = data.saleDate;
    $scope.saleDay = data.saleDay;

    $scope.tableLayer.show(true);

    $scope.searchSaleComTableList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 테이블별 리스트 조회
  $scope.searchSaleComTableList = function () {
    // 파라미터
    var params      = {};
    params.storeCd  = $scope.storeCd;
    params.startDate = $scope.startDate;
    params.endDate = $scope.endDate;
    params.tblCd    = $scope.tblCd;
    params.saleDate = $scope.saleDate;
    params.saleDay     = $scope.saleDay;
    params.saleYm     = $scope.saleYm;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/com/popup/table/view.sb", params);
  };

}]);
