/**
 * get application
 */
var app = agrid.getApp();

// 콤보박스 아무것도 선택하지 않았을 때 사용
var defaultComboData = [
    {"name": "전체", "value": ""}
];


/** 매장 - 실사/조정/폐기 조회 그리드 controller */
app.controller('stockViewStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('stockViewStoreCtrl', $scope, $http, true));

  $scope.excelFg = false;

    $scope._setComboData("srchReason", defaultComboData);
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
                params.reasonNm		= selectedRow.reasonNm; // 사유

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
    params.statusFg = $scope.statusFgModel;
    params.procFg = $scope.procFgModel;
    params.storeCd = $("#stockViewStoreSelectStoreCd").val();
    params.listScale = $scope.listScaleCombo.text;
    
    $scope.excelStartDate = params.startDate;
    $scope.excelEndDate   = params.endDate;
    $scope.excelStatusFg  = params.statusFg;
    $scope.excelProcFg    = params.procFg;
    $scope.excelStoreCd   = params.storeCd;
    

    if(params.startDate > params.endDate){
		$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		return false;
	}

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/manage/viewStore/viewStore/getStockManageViewStoreList.sb", params);
    
    $scope.excelFg = true;
  };

	//매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.stockViewStoreSelectStoreShow = function () {
		$scope._broadcast('stockViewStoreSelectStoreCtrl');
	};


  //엑셀 다운로드
  $scope.excelDownloadViewStore = function () {
	    // 파라미터
	    var params     = {};
		$scope._broadcast('stockViewStoreExcelCtrl',params);
  };

    // 상태에 따른 사유 조회
    $scope.setReason = function (s) {

        var params = {};

        if(s.selectedValue === "") {
            $scope._setComboData("srchReason", defaultComboData);

        }else {

            params.hqGbn = s.selectedValue;

            // 계정조회
            $scope._postJSONQuery.withOutPopUp("/stock/manage/view/view/getReason.sb", params, function (response) {
                if (response.data.data.list.length > 0) {
                    var reasonList = response.data.data.list;
                    $scope._setComboData("srchReason", reasonList);
                } else {
                    $scope._setComboData("srchReason", defaultComboData);
                }
            });
        }
    };
}]);


/** 매장 - 실사/조정/폐기 조회(엑셀) 그리드 controller */
app.controller('stockViewStoreExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('stockViewStoreExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("stockViewStoreExcelCtrl", function (event, data) {
	  if(data != undefined && $scope.excelFg) {
			if($scope.excelStartDate > $scope.excelEndDate){
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
				return false;
			}
			$scope.searchCurrUnityExcelList();
	    
		}else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}
	  
	  // 기능수행 종료 : 반드시 추가
	  event.preventDefault();
  });

  // 매장 - 실사/조정/폐기 조회 리스트 조회
  $scope.searchCurrUnityExcelList = function () {
    // 파라미터
    var params     = {};
    
    params.startDate = $scope.excelStartDate;
    params.endDate   = $scope.excelEndDate;
    params.statusFg  = $scope.excelStatusFg;
    params.procFg    = $scope.excelProcFg;
    params.storeCd   = $scope.excelStoreCd;
//    params.listScale = $scope.excelListScale;


    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/manage/viewStore/viewStore/getStockManageViewStoreExcelList.sb", params, function(){
    	
		var flex = $scope.excelFlex;
		//row수가 0이면
		if(flex.rows.length === 0){

			 var grid = wijmo.Control.getControl("#stockViewStoreExcelGrid")
			//컬럼 삭제
			while(grid.columns.length > 7){
		          grid.columns.removeAt(grid.columns.length-1);
		    }
		}

		if (flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
		$timeout(function () {
			wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(flex, {
				includeColumnHeaders: true,
				includeCellStyles   : true,
				includeColumns      : function (column) {
					return column.visible;
				}
			}, messages["cmmStockStatus.stockStatus"]+'-'+messages["currUnity.storeCd"]+'_'+messages["stockManageView.stockManageView"]+'_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);

    	
    });
  };


}]);

