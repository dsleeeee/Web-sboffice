/**
 * get application
 */
var app = agrid.getApp();

/** 할인구분별(매출리스트) controller */
app.controller('storeBrandCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeBrandCtrl', $scope, $http, $timeout, true));

  //groupRow 접고 펼치기 flag 변수
  $scope.setCollapsed = false;
  
  $scope.srchStoreBrandStartDate = wcombo.genDateVal("#srchStoreBrandStartDate", getToday());
  $scope.srchStoreBrandEndDate   = wcombo.genDateVal("#srchStoreBrandEndDate", getToday());
  	
  // 리스트 콤보박스 데이터 Set
  $scope._setComboData("storeBrandListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 조회조건 '정렬구분 표시'
	$scope.getSortFgComboList();

	// 콤보박스 데이터 Set
	$scope._setComboData('storeBrandlistScaleBox', gvListScaleBoxData);

	// picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeBrandCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.hqBrandNm  = messages["store.hqBrandNm"];
    dataItem.indexNo  = messages["store.indexNo"];
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
  $scope.$on("storeBrandCtrl", function (event, data) {
    $scope.searchStoreBrandList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.$on("storeBrandCtrlSrch", function (event, data) {
	    $scope.searchStoreBrandList(false);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


  // 브랜드별 매출 리스트 조회
  $scope.searchStoreBrandList = function (isPageChk) {

    var startDt = new Date(wijmo.Globalize.format($scope.srchStoreBrandStartDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.srchStoreBrandEndDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      $("div.storeRankLayer").hide();
      return false;
    }

    // 조회일자 최대 1년(365일) 제한
    if (diffDay > 365) {
      $scope._popMsg(messages['cmm.dateOver.1year.error']);
      $("div.storeRankLayer").hide();
      return false;
    }

  $scope.setCollapsed = false;
    // 파라미터
    var params       = {};
  //  params.startDate = wijmo.Globalize.format($scope.srchStoreBrandStartDate.value, 'yyyyMMdd');
  //  params.endDate = wijmo.Globalize.format($scope.srchStoreBrandEndDate.value, 'yyyyMMdd');
    params.storeCd   = $("#storeBrandSelectStoreCd").val();
    params.sortFg = $scope.sortFg;
    params.listScale = $scope.listScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStoreBrandStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchStoreBrandEndDate.value, 'yyyyMMdd');
    }

    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/store/brand/list.sb", params);
    
  //create a group to show the grand totals
    var grpLv1 = new wijmo.collections.PropertyGroupDescription('전체');
    var grpLv2 = new wijmo.collections.PropertyGroupDescription('hqBrandNm');

    var theGrid = new wijmo.Control.getControl('#storeBrandGrid');

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
    				case "hqBrandNm":className="grp-lv-2";break;
    			}

    			if(className){
    				row.cssClass=className;
    			}
    			
    			// groupRow 접기
    			if(row.level == 1) {  // 2단계 분류
					if(!$scope.setCollapsed){
						row.isCollapsed = true;
					}
				}
    		}
    	});

    });
    
    // 그리드 클릭 이벤트 groupRow 펼치기-------------------------------------------------------------------------------------------------
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
    $scope.srchStoreBrandStartDate.isReadOnly = $scope.isChecked;
    $scope.srchStoreBrandEndDate.isReadOnly = $scope.isChecked;
  };

  //엑셀 다운로드
  $scope.excelDownloadStoreBrand = function () {
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
      }, '매출분석_매장별매출분석_브랜드별매출_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

  //조회조건 정렬구분 리스트 조회
  $scope.getSortFgComboList = function () {
    var url             = '/sale/anals/store/brand/sortFgComboList.sb';
    var comboParams     = {};
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchStoreBrandDisplay", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
  };


  // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
  // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
  // comboId : combo 생성할 ID
  // gridMapId : grid 에서 사용할 Map ID
  // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
  // params : 데이터 조회할 url에 보낼 파라미터
  // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
  // callback : queryCombo 후 callback 할 함수
  $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
    var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
    if (url) {
      comboUrl = url;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : comboUrl, /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data.list)) {
          var list       = response.data.data.list;
          var comboArray = [];
          var comboData  = {};

          if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
            comboArray = [];
            if (option === "A") {
              comboData.name  = messages["cmm.all"];
              comboData.value = "";
              comboArray.push(comboData);
            } else if (option === "S") {
              comboData.name  = messages["cmm.select"];
              comboData.value = "";
              comboArray.push(comboData);
            }

            for (var i = 0; i < list.length; i++) {
              comboData       = {};
              comboData.name  = list[i].nmcodeNm;
              comboData.value = list[i].nmcodeCd;
              comboArray.push(comboData);
            }
            $scope._setComboData(comboId, comboArray);
          }

          if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
            comboArray = [];
            for (var i = 0; i < list.length; i++) {
              comboData      = {};
              comboData.id   = list[i].nmcodeCd;
              comboData.name = list[i].nmcodeNm;
              comboArray.push(comboData);
            }
            $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
          }
        }
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.error"]);
      return false;
    }).then(function () {
      if (typeof callback === 'function') {
        $timeout(function () {
          callback();
        }, 10);
      }
    });
  };
}]);