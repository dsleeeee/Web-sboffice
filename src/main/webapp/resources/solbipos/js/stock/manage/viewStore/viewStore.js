/**
 * get application
 */
var app = agrid.getApp();

/** 매장 - 실사/조정/폐기 조회 그리드 controller */
app.controller('stockViewStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('stockViewStoreCtrl', $scope, $http, true));

  $scope.startDate = wcombo.genDateVal("#srchStartDate", getToday());
  $scope.endDate   = wcombo.genDateVal("#srchEndDate", getToday());
  $scope.orgnFg = gvOrgnFg;

  $scope._setComboData("srchStatusFg", [
	{"name": messages["cmm.all"], "value": ""},
    {"name": "실사", "value": "1"},
    {"name": "조정", "value": "2"},
    {"name": "폐기", "value": "3"}
  ]);

  $scope._setComboData("srchProcFg", [
	{"name": messages["cmm.all"], "value": ""},
    {"name": "등록", "value": "0"},
    {"name": "확정", "value": "1"}
  ]);

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("stockViewStoreListScaleBox", gvListScaleBoxData);

  $scope.isChecked = true;
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("stockViewStoreCtrl");
    
    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.binding === "totDate") { // 일자
        	var item = s.rows[e.row].dataItem;
          	wijmo.addClass(e.cell, 'wijLink');
          	wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });
    
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
    	var ht = s.hitTest(e);
    	
    	if (ht.cellType === wijmo.grid.CellType.Cell) {
    		var col         = ht.panel.columns[ht.col];
    		var selectedRow = s.rows[ht.row].dataItem;
    		var params       = {};
			
    		if (col.binding === "totDate") { // 일자
    			params.orgnFg = $scope.orgnFg;
    			params.totDate		= selectedRow.totDate; // 날짜
    			params.seqNo		= selectedRow.seqNo; // 차수
    			params.hqGbn		= selectedRow.hqGbn; // 상태
    		    params.title		= selectedRow.title; // 제목
    		    params.storeCd		= selectedRow.storeCd; // 매장코드
    		    
    			$scope._broadcast('viewDtlCtrl', params);
    		}
    	}
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("stockViewStoreCtrl", function (event, data) {
    $scope.searchCurrUnityList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장 - 실사/조정/폐기 조회 리스트 조회
  $scope.searchCurrUnityList = function () {
    // 파라미터
    var params     = {};
    params.startDate = $scope.startDate.text.replace(/-/g, '');
    params.endDate = $scope.endDate.text.replace(/-/g, '');
    params.statusFg = $scope.statusFg;
    params.procFg = $scope.procFg;
    params.storeCd = $("#stockViewStoreSelectStoreCd").val();

    if(params.startDate > params.endDate){
		$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		return false;
	}

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/manage/viewStore/viewStore/getStockManageViewStoreList.sb", params);
  };

	//매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.stockViewStoreSelectStoreShow = function () {
		$scope._broadcast('stockViewStoreSelectStoreCtrl');
	};


  //엑셀 다운로드
  $scope.excelDownload = function () {
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
      }, '재고현황_매장-실사/조정/폐기 조회_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
