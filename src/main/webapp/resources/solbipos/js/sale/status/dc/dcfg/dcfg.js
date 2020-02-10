/**
 * get application
 */
var app = agrid.getApp();

/** 할인구분별(매출리스트) controller */
app.controller('dcDcfgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dcDcfgCtrl', $scope, $http, $timeout, true));

  $scope.srchDcDcfgStartDate = wcombo.genDateVal("#srchDcDcfgStartDate", gvStartDate);
  $scope.srchDcDcfgEndDate   = wcombo.genDateVal("#srchDcDcfgEndDate", gvEndDate);

  // 리스트 콤보박스 데이터 Set
  $scope._setComboData("dcDcfgListScaleBox", gvListScaleBoxData);
  $scope._setComboData("dcDcfgDtlListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 조회조건 '코너 표시'
	$scope.getDcNmList();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dcDcfgCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "realSaleAmt") { // 실매출
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
        	params.dcCd   = selectedRow.dcCd;
        	params.storeCd   = $("#dcDcfgSelectStoreCd").val();
        	params.startDate   = selectedRow.saleDate;
    	    params.endDate   = selectedRow.saleDate;
        if (col.binding === "realSaleAmt") { // 실매출
          $scope._broadcast('dcDcfgDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  }

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dcDcfgCtrl", function (event, data) {
    $scope.searchDcDcfgList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 할인구분별 리스트 조회
  $scope.searchDcDcfgList = function () {
  if ($("#dcDcfgSelectStoreCd").val() === '') {
      $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
      return false;
    }

    // 파라미터
    var params       = {};
    params.storeCd   = $("#dcDcfgSelectStoreCd").val();
    params.dcCd = $scope.dcCd;
    params.listScale = $scope.dcDcfgListScale; //-페이지 스케일 갯수

    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchDcDcfgStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchDcDcfgEndDate.value, 'yyyyMMdd');
    }
    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
    // 할인유형 전체선택에 따른 params
    if(!$scope.isAll){
      params.dcCd = $scope.dcCd;
      console.log($scope.dcCd);
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/status/dc/dcfg/list.sb", params);

    //메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){

		var rows = sender.rows;
		var params		 = {};

		if(rows.length != 0) {
			 params.startDate   = rows[0].dataItem.saleDate;
		     params.endDate   = rows[0].dataItem.saleDate;
			 params.dcCd   = rows[0].dataItem.dcCd;
			 params.storeCd   = $("#dcDcfgSelectStoreCd").val();
	    }
		else {
			params.dcCd   = -1;
		}

    	// 코너별 매출현황 상세조회.
    	$scope._broadcast("dcDcfgDtlCtrl", params);

	}
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchDcDcfgStartDate.isReadOnly = $scope.isChecked;
    $scope.srchDcDcfgEndDate.isReadOnly = $scope.isChecked;
  };
  // 전체 할인유형 체크박스 클릭이벤트
  $scope.totalDcDcfg = function() {
	  var grid = wijmo.Control.getControl("#srchDcDcfgDisplay");
	  grid.isReadOnly = $scope.isAll;;
  };

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dcDcfgSelectStoreShow = function () {
    $scope._broadcast('dcDcfgSelectStoreCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadDcDcfg = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, 'dcDcfg.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

//조회조건 코너표시(corner) 리스트 조회
  $scope.getDcNmList = function () {
    var url             = '/sale/status/dc/dcfg/dcNmList.sb';
    var comboParams     = {};
    comboParams.storeCd = $("#dcDcfgSelectStoreCd").val();
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchDcDcfgDisplay", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
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


/** 할인구분별매출(매출상세) controller */
app.controller('dcDcfgDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('dcDcfgDtlCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("dcDcfgDtlCtrl");
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("dcDcfgDtlCtrl", function (event, data) {
		$scope.startDate = data.startDate;
		$scope.endDate   = data.endDate;
		$scope.storeCd   = data.storeCd;
		$scope.dcCd   = data.dcCd;

	    $scope.searchDcfgDtlList();
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 코너별매출일자별 리스트 조회
	  $scope.searchDcfgDtlList = function () {
	  if ($("#dcDcfgSelectStoreCd").val() === '') {
	      $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
	      return false;
	    }

	    // 파라미터
	    var params       = {};
	    params.startDate = $scope.startDate;
	    params.endDate   = $scope.endDate;
	    params.storeCd   = $scope.storeCd;
	    params.dcCd   = $scope.dcCd;
	    params.listScale = $scope.dcDcfgDtlListScale;

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/status/dc/dcfg/dtl.sb", params);
	    $scope.flex.refresh();
	  };

	//엑셀 다운로드
	  $scope.excelDownloadDcDcfgDtl = function () {
	    if ($scope.flex.rows.length <= 0) {
	      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
	      return false;
	    }

	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
	    $timeout(function () {
	      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
	        includeColumnHeaders: true,
	        includeCellStyles   : false,
	        includeColumns      : function (column) {
	          return column.visible;
	        }
	      }, 'dcDcfgDtl.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };
}]);
