/**
 * get application
 */
var app = agrid.getApp();

var vSortFg = [
//	{"name":"매장형태","value":"1"}
    {"name":"매장용도","value":"2"}
];

/** 할인구분별(매출리스트) controller */
app.controller('storeFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeFgCtrl', $scope, $http, $timeout, true));

  //groupRow 접고 펼치기 flag 변수
  $scope.setCollapsed = false;
  
  $scope.srchStoreFgStartDate = wcombo.genDateVal("#srchStoreFgStartDate", getToday());
  $scope.srchStoreFgEndDate   = wcombo.genDateVal("#srchStoreFgEndDate", getToday());

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// 콤보박스 데이터 Set
	$scope._setComboData('storeFglistScaleBox', gvListScaleBoxData);
	$scope._setComboData("srchStoreFgDisplay", vSortFg);
	
	// picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeFgCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.storeFg  = messages["store.storeFg"];
    dataItem.storeNm  = messages["store.storeNm"];
    dataItem.totSaleAmt       = messages["store.totSaleAmt"];
    dataItem.totDcAmt      = messages["store.totDcAmt"];
    dataItem.realSaleAmt   = messages["store.realSaleAmt"];
    dataItem.saleCnt   = messages["store.saleCnt"];
    dataItem.ratRealSaleAmt   = messages["store.rat"];
    dataItem.ratCnt   = messages["store.rat"];

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
    
    // 그리드 클릭 이벤트
	s.addEventListener(s.hostElement, 'mousedown', function (e) {
    	var ht = s.hitTest(e);

    	/* 머지된 헤더 셀 클릭시 정렬 비활성화
    	 * 헤더 cellType: 2 && 머지된 row 인덱스: 0 && 머지된 column 인덱스 4 초과
    	 * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
    	 */
    	if(ht.cellType == 2 && ht.row < 1 && ht.col > 4) {
    		s.allowSorting = false;
		} else {
			s.allowSorting = true;
		}
	});
  }

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeFgCtrl", function (event, data) {
    $scope.searchStoreFgList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("storeFgCtrlSrch", function (event, data) {
    $scope.searchStoreFgList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 매장형태별 매출 리스트 조회
  $scope.searchStoreFgList = function (isPageChk) {

  $scope.setCollapsed = false;
    // 파라미터
    var params       = {};
  //  params.startDate = wijmo.Globalize.format($scope.srchStoreFgStartDate.value, 'yyyyMMdd');
  //  params.endDate = wijmo.Globalize.format($scope.srchStoreFgEndDate.value, 'yyyyMMdd');
    params.prodCd   = $("#srchStoreFgProdCd").val();
    params.storeFg = $scope.storeFg;
    params.storeCd   = $("#storeFgSelectStoreCd").val();
    params.listScale = $scope.listScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    params.orgnFg    = $scope.orgnFg;

    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStoreFgStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchStoreFgEndDate.value, 'yyyyMMdd');
    }

    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
    
//    if ($("#srchStoreFgProdCd").val() === "") {
//        $scope._popMsg(messages["storeManage.require.select.prod"]); // 상품을 선택해 주세요.
//        return false;
//    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/store/fg/list.sb", params);
    
    //create a group to show the grand totals
    var grpLv1 = new wijmo.collections.PropertyGroupDescription('전체');
    var grpLv2 = new wijmo.collections.PropertyGroupDescription('clsFg');

    var theGrid = new wijmo.Control.getControl('#storeFgGrid');

    theGrid.itemsSource = new wijmo.collections.CollectionView();
    
    // custom cell calculation
    theGrid.formatItem.addHandler(function(s, e) {

    	var lengthTemp = s.collectionView.groupDescriptions.length;

    	if (lengthTemp < 2) {
    		s.collectionView.groupDescriptions.push(grpLv1);
        	s.collectionView.groupDescriptions.push(grpLv2);
    	}

    	s.rows.forEach(function(row) {
    		if(row instanceof wijmo.grid.GroupRow){
    			var groupProp=row.dataItem.groupDescription.propertyName;
    			var className=null;
    			switch(groupProp){
    				case "전체":className="grp-lv-1";break;
    				case "clsFg":className="grp-lv-2";break;
    			}

    			if(className){
    				row.cssClass=className;
    			}
    			
    			if(row.level == 1) { 
					if(!$scope.setCollapsed){
						row.isCollapsed = true;
					}
				}
    		}
    	});

    });
    
	// 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
    theGrid.addEventListener(theGrid.hostElement, 'mousedown', function (e) {
      var ht = theGrid.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        if (theGrid.rows[ht.row].level == 1) { // 2단계 분류
        	$scope.setCollapsed = true;
        	var isCollapsed = theGrid.rows[ht.row].isCollapsed;
        	theGrid.rows[ht.row].isCollapsed ? false : true;
        }
      }
    });

    // start collapsed
    theGrid.collapseGroupsToLevel(1);
    theGrid.collectionView.refresh();
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStoreFgStartDate.isReadOnly = $scope.isChecked;
    $scope.srchStoreFgEndDate.isReadOnly = $scope.isChecked;
  };

  // 상품분류정보 팝업
  $scope.popUpProd = function() {
    var params = {};
    params.storeCd   = $("#storeFgSelectStoreCd").val();
    params.gubun = "StoreFg";
    //조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._broadcast('prodStrl', params);
  };

  // 상품분류정보 선택취소
  $scope.delProd = function(){
    $scope.prodCd = "";
    $scope.prodCdNm = "";
    $scope.prodCalssCd = "";
  }

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeFgSelectStoreShow = function () {
    $scope._broadcast('storeFgSelectStoreCtrl');
  };
  
  //엑셀 다운로드
  $scope.excelDownloadStoreFg = function () {
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
      }, '매출분석_매장별매출분석_매장형태별매출_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);