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
	  
	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("saleComTableCtrl");  
	
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.binding === "billNo") { // 결제수단
        	var item = s.rows[e.row].dataItem;
          	wijmo.addClass(e.cell, 'wijLink');
        } 
      }
    });
    
    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);

      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
        	params.storeCd   = selectedRow.storeCd;
        	params.posNo   	 = selectedRow.posNo;
        	params.saleDate  = selectedRow.saleDate;
        	params.billNo	 = selectedRow.billNo;

        if (col.binding === "billNo") { // 영수증번호
        	//판매여부 
        	if(selectedRow.saleFg === "판매"){
        		$scope._broadcast('billSalePopCtrl', params);
        	}else if(selectedRow.saleFg === "반품"){
        		$scope._broadcast('billRtnPopCtrl', params);
        	}
        } 
      }
    });
   
 // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         	= {};
    dataItem.storeCd      	= messages["saleComPopup.storeCd"];
    dataItem.storeNm      	= messages["saleComPopup.storeNm"];
    dataItem.saleDate      	= messages["saleComPopup.saleDate"];    
    dataItem.posNo      	= messages["saleComPopup.posNo"];
    dataItem.billNo  		= messages["saleComPopup.billNo"];
    dataItem.billDt    		= messages["saleComPopup.billDt"];
    dataItem.saleFg 		= messages["saleComPopup.saleFg"];
    dataItem.totSaleAmt     = messages["saleComPopup.totSaleAmt"];
    dataItem.realSaleAmt    = messages["saleComPopup.realSaleAmt"];
    dataItem.netSaleAmt   	= messages["saleComPopup.netSaleAmt"];
    dataItem.vatAmt   		= messages["saleComPopup.vatAmt"];
    dataItem.payTot     	= messages["saleComPopup.payChoice"];
    dataItem.pay01     		= messages["saleComPopup.payChoice"];
    dataItem.pay02     		= messages["saleComPopup.payChoice"];
    dataItem.pay03     		= messages["saleComPopup.payChoice"];
    dataItem.guestTot    	= messages["saleComPopup.guestTot"];
    dataItem.guestAmt   	= messages["saleComPopup.guestAmt"];

    // 객수 헤더머지 컬럼 생성
    for (var i = 0; i < arrGuestCol.length; i++) {
      dataItem['guest' + arrGuestCol[i]] = messages["saleComPopup.guestCnt"];
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
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleComTableCtrl", function (event, data) {
	  	  
    $scope.storeCd  	= data.storeCd;
    $scope.startDate 	= data.startDate;
    $scope.endDate		= data.endDate;
    $scope.tblCd    	= data.tblCd;
    $scope.saleDate     = data.saleDate;
    $scope.saleDay 		= data.saleDay;
    $scope.saleYm     	= data.saleYm;
    $scope.gubun     	= data.gubun;
    
    $scope.tableLayer.show(true);

    $scope.searchSaleComTableList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 테이블별 리스트 조회
  $scope.searchSaleComTableList = function () {
    // 파라미터
    var params      	= {};

    if($scope.gubun == "month"){
    	params.storeCd  	= $scope.storeCd;
    	params.tblCd    	= $scope.tblCd;
    	params.saleYm     	= $scope.saleYm;
    }else{
        params.storeCd  	= $scope.storeCd;
        params.startDate 	= $scope.startDate;
        params.endDate 		= $scope.endDate;
        params.tblCd    	= $scope.tblCd;
        params.saleDate 	= $scope.saleDate;
        params.saleDay     	= $scope.saleDay;
    }
        
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/com/popup/table/view.sb", params, function () {
          // <-- 그리드 visible -->
          // 선택한 테이블에 따른 리스트 항목 visible
          var grid = wijmo.Control.getControl("#wjGridSaleComTableList");
          var columns = grid.columns;

          // 컬럼 총갯수
          var columnsCnt = 17 + 6;

          // 합계가 0이면 해당 컬럼 숨기기
          for (var j = 0; j < columnsCnt; j++) {
              if(columns[j].binding == "guest01" || columns[j].binding == "guest02" || columns[j].binding == "guest03" || columns[j].binding == "guest04" || columns[j].binding == "guest05" || columns[j].binding == "guest06") {
                  // 합계행 값 가져오기
                  if($scope.flex.columnFooters.getCellData(0, j, true) == 0) {
                      columns[j].visible = false;
                  } else {
                      columns[j].visible = true;
                  }
              }
          }
          // <-- //그리드 visible -->
      });
  };

}]);
