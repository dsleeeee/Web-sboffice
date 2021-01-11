/****************************************************************
 *
 * 파일명 : prodPayFg.js
 * 설  명 : 상품별 >결제수단별 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.06     김진        1.0
 * 2021.01.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 결제수단별 상세현황 controller */
app.controller('prodPayFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodPayFgCtrl', $scope, $http, true));

  // 조회일자 세팅
  var startDate = wcombo.genDateVal("#startDatePayFg", gvStartDate);
  var endDate = wcombo.genDateVal("#endDatePayFg", gvEndDate);

  // 콤보박스 데이터 Set
  $scope._setComboData('prodPayFglistScaleBox', gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("prodPayFgCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if ((col.binding.substr(0, 3) === "pay") && col.binding !== "totPayAmt") { // 결제수단
        	// var item = s.rows[e.row].dataItem;
          	wijmo.addClass(e.cell, 'wijLink');
        } else if (col.binding === "totPayAmt"){ // 실매출액
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
        	//params.chkPop	= "tablePop";
        	params.storeCd   = $("#pordPayFgSelectStoreCd").val();
        	params.prodCd = selectedRow.prodCd;
        	params.dialogHd  = col.header;
            params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
            params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

        if ((col.binding.substr(0, 3) === "pay") && col.binding !== "totPayAmt") { // 결제수단
          params.payCd = col.binding.substr(3,2); // pay01 : 끝에 2자리 숫자만 가져옴
          $scope._broadcast('saleComPayFgCtrl', params);
        } else if (col.binding === "totPayAmt") { // 실매출액
          $scope._broadcast('saleComPayFgCtrl', params);
        }

      }
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodPayFgCtrl", function (event, data) {
    $scope.searchProdPayFgList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 결제수단별 리스트 조회
  $scope.searchProdPayFgList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $("#pordPayFgSelectStoreCd").val();
    params.prodCd    = $("#srchPayFgProdCd").val();
    params.prodNm    = $("#srchPayFgProdNm").val();
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
    params.payCol = payCol;
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/prod/payFg/getProdPayFgList.sb", params, function() {});
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.pordPayFgSelectStoreShow = function () {
    $scope._broadcast('pordPayFgSelectStoreCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadPayFg = function () {
    var params = {};
    params.storeCd   = $("#pordPayFgSelectStoreCd").val();
    params.prodCd    = $("#srchPayFgProdCd").val();
    params.prodNm    = $("#srchPayFgProdNm").val();
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
    params.payCol = payCol;

    $scope._broadcast('prodPayFgExcelCtrl', params);
  };

  // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
      var columns = $scope.flex.columns;

      for(var i=0; i<columns.length; i++){
          if(columns[i].binding === 'pathNm'){
              $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
          }
      }
  };

}]);


/** 결제수단별 상세현황 controller */
app.controller('prodPayFgExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodPayFgExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodPayFgExcelCtrl", function (event, data) {
    $scope.searchProdPayFgExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품매출순위 리스트 조회
  $scope.searchProdPayFgExcelList = function (data) {
    // 파라미터
    var params     = {};
    params.storeCd = data.storeCd;
    params.prodCd = data.prodCd;
    params.prodNm = data.prodNm;
    params.startDate = data.startDate;
    params.endDate = data.endDate;
    params.payCol = data.payCol;

    $scope.isChkProdClassDisplay();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/prod/payFg/getProdPayFgExcelList.sb", params, function() {
    	if ($scope.excelFlexTrd.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
    	}

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlexTrd, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, '매출현황_상품별_결제수단별_'+getToday()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
      });
  };

  // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  var columns = $scope.excelFlexTrd.columns;

	  for(var i=0; i<columns.length; i++){
		  if(columns[i].binding === 'pathNm'){
			  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
		  }
	  }
  };
  
}]);