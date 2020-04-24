/**
 * get application
 */
var app = agrid.getApp();
var prodNoEnvFg = "";
/** 매장별 입출고내역 controller */
app.controller('frnchsStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('frnchsStoreCtrl', $scope, $http, true));
  $scope.slipFg = 1;
  $scope.excelFg = false;
  // 조회일자 세팅
  $scope.srchStartDate = wcombo.genDateVal("#srchClassStartDate", getToday());
  $scope.srchEndDate   = wcombo.genDateVal("#srchClassEndDate", getToday());
  var writtenDate   = wcombo.genDate("#writtenDate");
  $scope.orgnFg = gvOrgnFg;

  // 콤보박스 데이터 Set
  $scope._setComboData('frnchsStorelistScaleBox', gvListScaleBoxData);

  //전표구분 grid data-map
  $scope.slipFgMap = new wijmo.grid.DataMap([
    {id: "1", name: messages["dstmn.orderSlipFg"]},
    {id: "-1", name: messages["dstmn.rtnSlipFg"]},
  ], 'id', 'name');

  //거래명세표
  $scope._setComboData("stmtAcctFg", [
    {"name": messages["dstmn.stmtAcctAll"], "value": ""},
    {"name": messages["dstmn.stmtAcctSplr"], "value": "1"},
    {"name": messages["dstmn.stmtAcctSplrRcpnt"], "value": "2"}
  ]);

  //세금계산서 청구, 영수 구분
  $scope._setComboData("billFg", [
    {"name": messages["dstmn.billFg0"], "value": "0"},
    {"name": messages["dstmn.billFg1"], "value": "1"}
  ]);

  // 세금계산서 일반, 과세/면세
  $scope._setComboData("taxFg", [
    {"name": messages["dstmn.taxFg0"], "value": "0"},
    {"name": messages["dstmn.taxFg1"], "value": "1"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// 전표종류
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "087";
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo,map", "srchSlipKind", "slipKindMap", null, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("frnchsStoreCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());

    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 그리드 링크 효과
	s.formatItem.addHandler(function (s, e) {
		if (e.panel === s.cells) {
			var col = s.columns[e.col];
			if (col.binding === "storeNm"){ // 매장명
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
            var params       = {};
            	//params.chkPop	= "tablePop";
            	params.prodClassCd = $scope.prodClassCd;
            	params.storeCd   = selectedRow.storeCd;
        	    params.prodCd    = $("#srchProdCd").val();
        	    params.prodNm    = $("#srchProdNm").val();
        	    params.slipNo 	 = selectedRow.slipNo;
        	    params.orgnFg    = $scope.orgnFg;

            	// 등록일자 '전체기간' 선택에 따른 params
                if(!$scope.isChecked){
                  params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
                  params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
                }

            if (col.binding === "storeNm") { // 매장명
              $scope._broadcast('frnchsStoreDtlCtrl', params);
            }

          }
	});

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("frnchsStoreCtrl", function (event, data) {
    $scope.searchFrnchsStoreList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("frnchsStoreCtrlSrch", function (event, data) {
    $scope.searchFrnchsStoreList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장-상품별입출고내역 리스트 조회
  $scope.searchFrnchsStoreList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.storeCd   = $("#frnchsStoreSelectStoreCd").val();
    params.storeNm    = $("#storeNm").val();
    params.orgnFg    = $scope.orgnFg;
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    $scope.storeCdForExcel   = params.storeCd;
    $scope.storeNmForExcel   = params.storeNm;
    
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
      
      $scope.startDateForExcel = params.startDate;
      $scope.endDateForExcel   = params.endDate;
    }
    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
     $scope._inquiryMain("/iostock/frnchs/store/store/frnchsStoreList.sb", params, function() {});
    //$scope._inquiryMain("/sale/status/table/dayperiod/list.sb", params, function() {});

     $scope.excelFg = true;
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.frnchsStoreSelectStoreShow = function () {
    $scope._broadcast('frnchsStoreSelectStoreCtrl');
  };

  //거래명세표 리스트 조회
  $scope.searchDstmnList = function () {
    // 파라미터
    var params       = {};
    params.slipFg    = $scope.slipFg;
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/dstmn/dstmn/list.sb", params);
  };

  //리포트
  $scope.report = function (reportFg) {
    var strSlipNo = '';
    var strDlvrCd = '';
    if (!$scope.flex.collectionView) {
      $scope.flex.itemsSource = new wijmo.collections.CollectionView();
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];
      if (item.gChk === true) {
    	  strSlipNo += (strSlipNo === '' ? '' : ',') + item.slipNo;
        if (nvl(item.dlvrCd, '') !== '' && strDlvrCd.indexOf(item.dlvrCd) < 0) {
          strDlvrCd += (strDlvrCd === '' ? '' : ',') + item.dlvrCd;
        }
      }
    }

    if (strSlipNo === '') {
      $scope._popMsg(messages['dstmn.require.slipNo']);
      return false;
    }

    var params       = {};
    params.slipFg    = $scope.slipFg;
    params.strSlipNo = strSlipNo;

    // 상품
    if (reportFg === 'prod') {
      $scope._broadcast('dstbProdReportCtrl', params);
    }
    // 상품-매장
    else if (reportFg === 'prodStore') {
      $scope._broadcast('dstbProdStoreReportCtrl', params);
    }
    // 매장-상품
    else if (reportFg === 'storeProd') {
      $scope._broadcast('dstbStoreProdReportCtrl', params);
    }
    // 기사별
    else if (reportFg === 'dlvr') {
      if (strDlvrCd === '') {
        $scope._popMsg(messages['dstmn.require.dlvr']);
        return false;
      }
      params.strDlvrCd = strDlvrCd;
      $scope._broadcast('dstbDlvrCtrl', params);
    }
    // 거래명세표
    else if (reportFg === 'trans') {
      params.stmtAcctFg = $scope.stmtAcctFg;
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd');
      $scope._broadcast('transReportCtrl', params);
    }
    // 세금계산서
    else if (reportFg === 'tax') {
      params.writtenDate = wijmo.Globalize.format(writtenDate.value, 'yyyyMMdd');
      params.billFg      = $scope.billFg;
      params.taxFg       = $scope.taxFg;
      $scope._broadcast('taxReportCtrl', params);
    }
    // 거래명세표 엑셀다운
    else if (reportFg === 'transExcelDown') {
      params.stmtAcctFg = $scope.stmtAcctFg;
      $scope._broadcast('transReportExcelDownCtrl', params);
    }

  };

  // 엑셀 다운로드
  $scope.excelDownloadClass = function () {
	// 파라미터
	var params = {};
	params.storeCd   = $scope.storeCdForExcel;
    params.storeNm   = $scope.storeNmForExcel;
    params.startDate = $scope.startDateForExcel;
  	params.endDate 	 = $scope.endDateForExcel;
  	params.orgnFg    = $scope.orgnFg;
  	params.excelFg   = $scope.excelFg;

	$scope._broadcast('frnchsStoreExcelCtrl',params);
  };

//DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
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


/** 매장별 입출고내역 엑셀리스트 controller */
app.controller('frnchsStoreExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('frnchsStoreExcelCtrl', $scope, $http, true));
//  $scope.slipFg = 1;
  $scope.orgnFg = gvOrgnFg;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());

    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 그리드 링크 효과
	s.formatItem.addHandler(function (s, e) {
		if (e.panel === s.cells) {
			var col = s.columns[e.col];
			if (col.binding === "storeNm"){ // 매장명
				wijmo.addClass(e.cell, 'wijLink');
			}
		}
	});
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("frnchsStoreExcelCtrl", function (event, data) {
	  if(data != undefined && data.excelFg) {
			if(data.startDate > data.endDate){
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			 	return false;
			}
			
			$scope.storeCd   = data.storeCd;
			$scope.storeNm   = data.storeNm;
			$scope.startDate = data.startDate;
			$scope.endDate   = data.endDate;
			
			$scope.searchFrnchsStoreList(false);
		}else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장별 입출고내역 리스트 조회
  $scope.searchFrnchsStoreList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.storeCd   = $scope.storeCd;
    params.storeNm   = $scope.storeNm;
    params.orgnFg    = $scope.orgnFg;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.isPageChk = isPageChk;
    
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
     $scope._inquirySub("/iostock/frnchs/store/store/frnchsStoreExcelList.sb", params, function() {

 		var flex = $scope.excelFlex;
 		//row수가 0이면
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
 			}, '본사매장간입출고내역_매장별입출고내역_'+getToday()+'.xlsx', function () {
 				$timeout(function () {
 					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
 				}, 10);
 			});
 		}, 10);
     });
  };
}]);
