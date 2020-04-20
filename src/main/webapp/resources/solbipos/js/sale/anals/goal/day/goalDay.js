/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('goalDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('goalDayCtrl', $scope, $http, $timeout, true));

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("goalDayListScaleBox", gvListScaleBoxData);
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("goalDayCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
//        if (col.binding.substring(0, 10) === "totSaleQty" || col.binding.substring(0, 7) === "saleQty") { // 수량합계
//        	var item = s.rows[e.row].dataItem;
//          	wijmo.addClass(e.cell, 'wijLink');
//          	wijmo.addClass(e.cell, 'wj-custom-readonly');
//        }
      }
    });
    
    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      
      /* 머지된 헤더 셀 클릭시 정렬 비활성화
  	   * 헤더 cellType: 2 && 머지된 row 인덱스: 0, 1 && 동적 생성된 column 인덱스 4 초과
  	   * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
  	   */
	  	if(ht.cellType == 2 && ht.row < 1 && ht.col > 1) {
  			s.allowSorting = false;
		} else {
			s.allowSorting = true;
		}
  	
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
        	params.chkPop   = "tablePop";
        	params.storeCd	 = $("#goalDaySelectStoreCd").val();
        	params.startDate = selectedRow.saleDate;
        	params.endDate   = selectedRow.saleDate;
//        	if (col.binding.substring(0, 10) === "totSaleQty") { // 수량
//            	$scope._broadcast('saleComProdCtrl', params);
//            }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 'ColumnHeaders';
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    for(var i = 0; i < s.columnHeaders.rows.length-1; i++) {
    	s.columnHeaders.setCellData(i, "saleGoalDate", messages["goal.goalDay.saleDate"]);
    	s.columnHeaders.setCellData(i, "saleGoalDy", messages["goal.goalDay.yoil"]);
    }

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
  $scope.$on("goalDayCtrl", function (event, data) {
    $scope.searchGoalDayList(true);
  });
  
  //다른 컨트롤러의 broadcast 받기(페이징 초기화)
  $scope.$on("goalDayCtrlSrch", function (event, data) {
    $scope.searchGoalDayList(false);
  });


  // 코너별매출일자별 리스트 조회
  $scope.searchGoalDayList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.storeCd   = $("#goalDaySelectStoreCd").val();
    params.isPageChk = isPageChk;
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    params.startDate = wijmo.Globalize.format($scope.goalDayStartDateCombo.value, 'yyyyMM');
    
	// 동적 그리드 생성.
	$scope._postJSONQuery.withOutPopUp("/sale/anals/goal/day/dayList.sb", params, function(response) {
	 var length = response.data.data.list.length;
	 var list	= response.data.data.list;
	 var grid = wijmo.Control.getControl("#goalDayGrid");
	 
	 
	 if (length != "" || length != null) {
		 $scope.paramsStoreCd = [];
		  while(grid.columns.length > 2){
	           grid.columns.removeAt(grid.columns.length-1);
		  }
		  for(var i = 0; i < length; i++){
			  $scope.paramsStoreCd.push(list[i].storeCd);
			  var storeNm = list[i].storeNm;
			  var saleGoalAmt = list[i].saleGoalAmt;
			  grid.columns.push(new wijmo.grid.Column({header: messages["goal.goalDay.dayGoalAmt"], binding: 'saleGoalAmt'+i, width: 100, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
	          grid.columns.push(new wijmo.grid.Column({header: messages["goal.goalDay.daySale"], binding: 'totSaleAmt'+i, width: 80, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
	          grid.columns.push(new wijmo.grid.Column({header: messages["goal.goalDay.totSale"], binding: 'acc'+i, width: 100, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
	          grid.columns.push(new wijmo.grid.Column({header: messages["goal.goalDay.goalAchv"], binding: 'goalAchi'+i, width: 100, align: 'right', isReadOnly: 'true', dataType: 'Number', format: 'p1', aggregate: 'Sum'}));
	          
	          grid.columnHeaders.setCellData(0, 'saleGoalAmt'+i, "<" + storeNm + ">" +  messages["goal.goalDay.monthGoalAmt"] + " : " + String(saleGoalAmt).replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,'));
	          grid.columnHeaders.setCellData(0, 'totSaleAmt'+i, "<" + storeNm + ">" +  messages["goal.goalDay.monthGoalAmt"] + " : " + String(saleGoalAmt).replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,'));
			  grid.columnHeaders.setCellData(0, 'acc'+i, "<" + storeNm + ">" +  messages["goal.goalDay.monthGoalAmt"] + " : " + String(saleGoalAmt).replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,'));
	          grid.columnHeaders.setCellData(0, 'goalAchi'+i, "<" + storeNm + ">" +  messages["goal.goalDay.monthGoalAmt"] + " : " + String(saleGoalAmt).replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,'));
		  }
		  params.storeCd = $scope.paramsStoreCd;
	  }
	  $scope.flex.refresh();
	  // 기능수행 종료 : 반드시 추가
	  event.preventDefault();
	  
		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/anals/goal/day/list.sb", params, function() {
			var flex = $scope.flex;
			//row수가 0이면
			if(flex.rows.length === 0){
				
				var grid = wijmo.Control.getControl("#goalDayGrid");
				//컬럼 삭제
				while(grid.columns.length > 2){
			          grid.columns.removeAt(grid.columns.length-1);
			    }
			}
		});
	});
  };

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.goalDaySelectStoreShow = function () {
    $scope._broadcast('goalDaySelectStoreCtrl');
  };
  
  //매출목표 등록 모듈 팝업 사용시 정의
  $scope.saleGoalReg = function () {
	  $scope._broadcast('goalPopCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadGoalDay = function () {
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
      },  '매출목표관리_일자별 목표대비 매출_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
  
}]);