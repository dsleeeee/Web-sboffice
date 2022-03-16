/****************************************************************
 *
 * 파일명 : offAddProd.js
 * 설  명 : 오프라인추가매출현황 > 상품별 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.14     권지현        1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var orderAddFgData = [
  {"name":"전체","value":""},
  {"name":"오프라인","value":"1"},
  {"name":"온라인","value":"2"}
]

/** 결제수단별 상세현황 controller */
app.controller('offAddProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('offAddProdCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  $scope.startDateProd = wcombo.genDateVal("#startDateProd", gvStartDate);
  $scope.endDateProd = wcombo.genDateVal("#endDateProd", gvEndDate);

  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);
  $scope._setComboData('orderAddFg', orderAddFgData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("offAddProdCtrl");
    $scope.orderAddFgDataMap = new wijmo.grid.DataMap(orderAddFgData, 'value', 'name'); //승인여부


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
        	params.storeCd   = $("#offAddProdStoreCd").val();
        	params.prodCd = selectedRow.prodCd;
        	params.dialogHd  = col.header;
            params.startDate = wijmo.Globalize.format($scope.startDateProd.value, 'yyyyMMdd'); //조회기간
            params.endDate = wijmo.Globalize.format($scope.endDateProd.value, 'yyyyMMdd'); //조회기간

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
  $scope.$on("offAddProdCtrl", function (event, data) {
    $scope.searchOffAddProdList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 결제수단별 리스트 조회
  $scope.searchOffAddProdList = function () {

    var startDt = new Date(wijmo.Globalize.format($scope.startDateProd.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.endDateProd.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      s_alert.pop(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 한달(31 일) 제한
    if (diffDay > 30) {
      s_alert.pop(messages['cmm.dateOver.1month.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.storeCd   = $("#offAddProdStoreCd").val();
    params.prodCd    = $("#srchPayFgProdCd").val();
    params.prodNm    = $("#srchPayFgProdNm").val();
    params.startDate = wijmo.Globalize.format($scope.startDateProd.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format($scope.endDateProd.value, 'yyyyMMdd'); //조회기간
    params.payCol = payCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/offAdd/offAdd/getOffAddProdList.sb", params, function() {});
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.offAddProdStoreShow = function () {
    $scope._broadcast('offAddProdStoreCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadPayFg = function () {
    var params = {};
    params.storeCd   = $("#offAddProdStoreCd").val();
    params.prodCd    = $("#srchPayFgProdCd").val();
    params.prodNm    = $("#srchPayFgProdNm").val();
    params.startDate = wijmo.Globalize.format($scope.startDateProd.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format($scope.endDateProd.value, 'yyyyMMdd'); //조회기간
    params.payCol = payCol;

    $scope._broadcast('offAddProdExcelCtrl', params);
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


  // 상품분류정보 팝업
  $scope.popUpProdClass = function () {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd = scope.getSelectedClass();
        var params = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
            function (response) {
              $scope.prodClassCd = prodClassCd;
              $scope.prodClassCdNm = response.data.data;
            }
        );
      }
    });
  };

  // 상품분류정보 선택취소
  $scope.delProdClass = function () {
    $scope.prodClassCd = "";
    $scope.prodClassCdNm = "";
  };

}]);


/** 상품별 엑셀 다운로드 controller */
app.controller('offAddProdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('offAddProdExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("offAddProdExcelCtrl", function (event, data) {
    $scope.searchoffAddProdExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 리스트 조회
  $scope.searchoffAddProdExcelList = function (data) {
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
    $scope._inquiryMain("/sale/status/offAdd/offAdd/getOffAddProdExcelList.sb", params, function() {
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
        }, messages["offAdd.offAdd"] + '_' + messages["offAdd.month"] + getToday() + '.xlsx', function () {
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