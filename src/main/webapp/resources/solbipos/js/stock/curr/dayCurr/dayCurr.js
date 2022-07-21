/**
 * get application
 */
var app = agrid.getApp();

/** 일재고현황 그리드 controller */
app.controller('dayCurrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayCurrCtrl', $scope, $http, true));

    var srchStartDate = wcombo.genDateVal("#srchStartDate", getToday());

  $scope._setComboData("srchUnitFg", [
    {"name": messages["dayCurr.unitStockFg"], "value": "0"},
    {"name": messages["dayCurr.unitOrderFg"], "value": "1"}
  ]);

  $scope._setComboData("srchWeightFg", [
    {"name": messages["dayCurr.weightFg0"], "value": "0"},
    {"name": messages["dayCurr.weightFg1"], "value": "1"}
  ]);

  $scope._setComboData("srchSafeStockFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["dayCurr.safeStockFg0"], "value": "0"}
  ]);

  //조회조건 콤보박스 listScale 세팅
  $scope._setComboData("dayCurrListScaleBox", gvListScaleBoxData);
  $scope.isSearch = false;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	$scope.ChkProdClassDisplay = false;

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dayCurrCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dayCurrCtrl", function (event, data) {
    $scope.searchDayCurrList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 현재고현황 리스트 조회
  $scope.searchDayCurrList = function (isPageChk) {
    // 파라미터
    var params     = {};
    params.searchDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.prodCd = $scope.prodCdModel;
    params.prodNm = $scope.prodNmModel;
    params.barcdCd = $scope.barcdCdModel;
    params.unitFg = $scope.unitFgModel;
    params.prodClassCd = $scope.prodClassCd;
    params.vendrCd = $("#dayCurrSelectVendrCd").val();
    params.isPageChk = isPageChk;
    params.listScale = $scope.listScaleCombo.text;
    params.storageCd = $("#dayCurrSelectStorageCd").val();
    params.safeStockFg		 = $scope.safeStockFgModel;
    
    $scope.excelProdCd		= params.prodCd;
    $scope.excelProdNm		= params.prodNm;
    $scope.excelBarcdCd		= params.barcdCd;
    $scope.excelUnitFg		= params.unitFg;
    $scope.excelProdClassCd	= params.prodClassCd;
    $scope.excelVendrCd		= params.vendrCd;
    $scope.excelListScale	= params.listScale;
    $scope.excelStorageCd	= params.storageCd;
    $scope.excelSafeStockFg	= params.safeStockFg;
    $scope.isSearch 		= true;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/curr/dayCurr/dayCurr/list.sb", params);
  };

  // 상품분류정보 팝업
  $scope.popUpProdClass = function () {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd    = scope.getSelectedClass();
        var params         = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function (response) {
            $scope.prodClassCd   = prodClassCd;
            $scope.prodClassCdNm = response.data.data;
          }
        );
      }
    });
  };

  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dayCurrSelectVendrShow = function () {
    $scope._broadcast('dayCurrSelectVendrCtrl');
  };

  //상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  var columns = $scope.flex.columns;

	  for(var i=0; i<columns.length; i++){
		  if(columns[i].binding === 'prodClassNm'){
			  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
		  }
	  }
  };

  //창고선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dayCurrSelectStorageShow = function () {
    $scope._broadcast('dayCurrSelectStorageCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownload = function () {
	  // 파라미터
	  var params     = {};
	  params.searchDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd')
	  
	  $scope._broadcast('dayCurrExcelCtrl',params);
  };

}]);

app.controller('dayCurrExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('dayCurrExcelCtrl', $scope, $http, $timeout, true));

	var checkInt = true;

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("dayCurrExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isSearch) {
			$scope.searchPeriodIostockExcelList(data);
			// 기능수행 종료 : 반드시 추가
			event.preventDefault();
		} else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}
	});

	//상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
	$scope.isChkProdClassDisplay = function(){
		var columns = $scope.excelFlex.columns;

		for(var i=0; i<columns.length; i++){
			if(columns[i].binding === 'prodClassNm'){
				$scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			}
		}
	};

	// 전체 엑셀 리스트 조회
	$scope.searchPeriodIostockExcelList = function (data) {// 파라미터
		
		// 파라미터
	    var params     = {};
        params.searchDate = data.searchDate;
        params.prodCd = $scope.excelProdCd;
	    params.prodNm = $scope.excelProdNm;
	    params.barcdCd = $scope.excelBarcdCd;
	    params.unitFg = $scope.excelUnitFg;
	    params.prodClassCd = $scope.excelProdClassCd;
	    params.vendrCd = $scope.excelVendrCd;
	    params.listScale = $scope.excelListScale;
	    params.storageCd = $scope.excelStorageCd;
	    params.safeStockFg = $scope.excelSafeStockFg;

		$scope.isChkProdClassDisplay();

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/stock/curr/dayCurr/dayCurr/excelList.sb", params, function(){
			if ($scope.excelFlex.rows.length <= 0) {
			      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			      return false;
			    }

			    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
			    $timeout(function () {
			      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
			        includeColumnHeaders: true,
			        includeCellStyles   : true,
			        includeColumns      : function (column) {
			          return column.visible;
			        }
			      }, '재고현황_' + $(menuNm).selector + '_'+getToday()+'.xlsx', function () {
			        $timeout(function () {
			          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
			        }, 10);
			      });
			    }, 10);
		});
	};

}]);