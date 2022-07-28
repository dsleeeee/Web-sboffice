/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('frnchsStoreProdDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('frnchsStoreProdDtlCtrl', $scope, $http, true));

  //$("#totRadio").prop("checked",true);

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
    dataItem.outDtFm      	= messages["frnchsStoreProd.outDate"];
    dataItem.inDtFm  		= messages["frnchsStoreProd.inDate"];
    dataItem.storeNm    		= messages["frnchsStoreProd.storeNm"];
    dataItem.slipFgNm 		= messages["frnchsStoreProd.fg"];
    dataItem.slipKindNm     = messages["frnchsStoreProd.slipKind"];
    dataItem.procFgNm    = messages["frnchsStoreProd.proc"];
    dataItem.splyUprc   	= messages["frnchsStoreProd.splyUprc"];
    dataItem.outTotQty   	= messages["frnchsStoreProd.out"];
    dataItem.outAmt     	= messages["frnchsStoreProd.out"];
    dataItem.outVat     		= messages["frnchsStoreProd.out"];
    dataItem.outTot     		= messages["frnchsStoreProd.out"];
    dataItem.inTotQty     		= messages["frnchsStoreProd.in"];
    dataItem.inAmt    	= messages["frnchsStoreProd.in"];
    dataItem.inVat    	= messages["frnchsStoreProd.in"];
    dataItem.inTot    	= messages["frnchsStoreProd.in"];
    dataItem.penaltyAmt    	= messages["frnchsStoreProd.penaltyAmt"];
    dataItem.remark    	= messages["frnchsStoreProd.remark"];

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
  $scope.$on("frnchsStoreProdDtlCtrl", function (event, data) {

    $scope.startDate 		= data.startDate;
    $scope.endDate			= data.endDate;
    $scope.prodClassCd    	= data.prodClassCd;
    $scope.storeCd  		= data.storeCd;
    $scope.prodCd    		= data.prodCd;
    $scope.prodNm 			= data.prodNm;
    $scope.orgnFg     		= data.orgnFg;
    $scope.vendrCd     		= data.vendrCd;

    $scope.frnchsStoreProdDtlLayer.show(true);

    $scope.searchFrnchsStoreProdDtlList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 테이블별 리스트 조회
  $scope.searchFrnchsStoreProdDtlList = function () {
    // 파라미터
    var params      	= {};
        params.storeCd  	= $scope.storeCd;
        params.startDate 	= $scope.startDate;
        params.endDate 		= $scope.endDate;
        params.tblCd    	= $scope.tblCd;
        params.saleDate 	= $scope.saleDate;
        params.saleDay     	= $scope.saleDay;
        params.prodCd     	= $scope.prodCd;
        params.vendrCd     	= $scope.vendrCd;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/frnchs/storeprod/storeprod/frnchsStoreProdDtlList.sb", params);
  };

  //전체 or 수량합계 라디오 클릭이벤트
  $scope.totalOrNot = function() {
	 for(var item = 0; item < $scope.flex.columns.length; item++) {
		 var col = $scope.flex.columns[item];
		 if(col.binding === 'outAmt' || col.binding === 'outVat' || col.binding === 'inAmt' || col.binding === 'inVat') {
			 var flag = $('input[name=isCheckedSort]:checked').val();
			 $scope.flex.columns[item].visible = flag == 1 ? true: false;
		 }
	 }
	 $scope.flex.refresh();
  };

  //엑셀 다운로드
  $scope.excelDownloadClass = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : true,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, '본사매장간입출고내역_매장별입출고내역_상품입출고현황'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
