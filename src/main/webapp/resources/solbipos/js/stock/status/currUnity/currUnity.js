/**
 * get application
 */
var app = agrid.getApp();

/** 본사매장통합현재고 그리드 controller */
app.controller('currUnityCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('currUnityCtrl', $scope, $http, true));


  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("currUnityListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("currUnityCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if ((col.binding === "hCurrQty" || col.binding === "mCurrQty") && s.cells.getCellData(e.row,e.col,false) != null ) { // 본사수량 클릭
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
		if (col.binding === "hCurrQty") { // 본사수량 클릭
			$("#currUnityHqDtl").show();
			$("#currUnityStoreDtl").hide();
			var params    = {};
			params.prodCd   = selectedRow.prodCd;
			$scope._broadcast('currUnityHqDtlSrchCtrl', params);

		} else if (col.binding === "mCurrQty") { // 매장수량 클릭
			$("#currUnityStoreDtl").show();
			$("#currUnityHqDtl").hide();
			var params    = {};
			params.prodCd   = selectedRow.prodCd;
			$scope._broadcast('currUnityStoreDtlSrchCtrl', params);
		}
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("currUnityCtrl", function (event, data) {
    $scope.searchCurrUnityList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

//다른 컨트롤러의 broadcast 받기
  $scope.$on("currUnitySrchCtrl", function (event, data) {
    $scope.searchCurrUnityList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 본사매장통합현재고 리스트 조회
  $scope.searchCurrUnityList = function (isPageChk) {
    // 파라미터
    var params     = {};
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.barcdCd = $scope.barcdCd;
    params.vendrCd = $("#currUnitySelectVendrCd").val();
    params.prodClassCd = $scope.prodClassCd;
    params.isPageChk   = isPageChk;
    params.listScale = $scope.listScaleCombo.text;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/status/currUnity/prod/getCurrUnityList.sb", params, function () {
    	$scope.isChkDt($scope.isChecked);
    });

    //메인그리드 조회후 상세그리드 조회.
    $scope.loadedRows = function(sender, args){
        var rows = sender.rows;

        var params       = {};
        if(rows.length > 0){
			params.prodCd   = rows[0].dataItem.prodCd;
			// 본사수량 상세조회.
	        $scope._broadcast("currUnityHqDtlSrchCtrl", params);
        } else{
        	// 메인그리드 조회 후 본사수량 상세조회 그리드 초기화
            var orderDtlScope = agrid.getScope('currUnityHqDtlCtrl');
            orderDtlScope.dtlGridDefault();
        }
    }
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

  //상품분류정보 선택취소
  $scope.delProdClass = function(){
    $scope.prodClassCd = "";
    $scope.prodClassCdNm = "";
  }

  //상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  var columns = $scope.flex.columns;

	  for(var i=0; i<columns.length; i++){
		  if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
			  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
		  }
	  }
  }

  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.currUnitySelectVendrShow = function () {
    $scope._broadcast('currUnitySelectVendrCtrl');
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
      }, '재고현황_본사매장통합현재고_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);

/** 본사매장통합현재고 우측 본사 수불수량 그리드 controller */
app.controller('currUnityHqDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('currUnityHqDtlCtrl', $scope, $http, true));

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("currUnityHqDtlListScaleBox", gvListScaleBoxData);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("currUnityHqDtlCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("currUnityHqDtlCtrl", function (event, data) {
    $scope.searchCurrUnityHqDtlList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("currUnityHqDtlSrchCtrl", function (event, data) {
	$scope.srchProdCd   = data.prodCd;

    $scope.searchCurrUnityHqDtlList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 본사매장통합현재고 리스트 조회
  $scope.searchCurrUnityHqDtlList = function (isPageChk) {
    // 파라미터
    var params     = {};
    params.isPageChk = isPageChk;
    params.listScale = $scope.listScaleCombo.text;
    params.prodCd      =	$scope.srchProdCd

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/stock/status/currUnity/prod/getCurrUnityHqDtlList.sb", params);
  };

  //상세 그리드 초기화
  $scope.dtlGridDefault = function () {
    $timeout(function () {
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;
      $scope.flex.refresh();
    }, 10);
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
      }, '재고현황_본사매장통합현재고_본사재고수량_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);

/** 본사매장통합현재고 우측 매장 수불수량 그리드 controller */
app.controller('currUnityStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('currUnityStoreDtlCtrl', $scope, $http, true));

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("currUnityStoreDtlListScaleBox", gvListScaleBoxData);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("currUnityStoreDtlCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("currUnityStoreDtlCtrl", function (event, data) {
    $scope.searchCurrUnityStoreDtlList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("currUnityStoreDtlSrchCtrl", function (event, data) {
	$scope.srchProdCd   = data.prodCd;

    $scope.searchCurrUnityStoreDtlList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 본사매장통합현재고 리스트 조회
  $scope.searchCurrUnityStoreDtlList = function (isPageChk) {
    // 파라미터
    var params     = {};
    params.isPageChk = isPageChk;
    params.listScale = $scope.listScale;
    params.prodCd      =	$scope.srchProdCd

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/stock/status/currUnity/prod/getCurrUnityStoreDtlList.sb", params);
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
      }, '재고현황_본사매장통합현재고_매장재고수량'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
