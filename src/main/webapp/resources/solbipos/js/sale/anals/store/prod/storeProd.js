/**
 * get application
 */
var app = agrid.getApp();

var vRowNum = [
    {"name":"30위","value":"30"},
    {"name":"50위","value":"50"},
    {"name":"100위","value":"100"}
];

/** 매장상품순위 상세현황 controller */
app.controller('storeProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('storeProdCtrl', $scope, $http, $timeout, true));
    
	// 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchRowNumCombo", vRowNum);
	
    // 콤보박스 데이터 Set
    $scope._setComboData('storeProdlistScaleBox', gvListScaleBoxData);
    
	// 조회일자 세팅
	$scope.srchStartDate = wcombo.genDateVal("#srchProdStartDate", getToday());
	$scope.srchEndDate   = wcombo.genDateVal("#srchProdEndDate", getToday());
	$scope.orgnFg = gvOrgnFg;
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
					
		//  picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("storeProdCtrl");
		    	    
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
	      
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeProdCtrl", function (event, data) {
 
	 $scope.searchStoreProdList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };
  
  // 매장순위 리스트 조회
  $scope.searchStoreProdList = function () {

    // 파라미터
    var params       = {};
		params.storeCd   = $("#storeProdSelectStoreCd").val();
		params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    if(!$scope.isChecked){
    	 params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
         params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
	if($scope.isCheckedProdAll){
		params.chkProdAll = "Y";
	}else{
		params.chkProdAll = "N";
	}
	params.hqOfficeCd = $("#hqOfficeCd").val();
	params.prodCd	 =  $("#srchStoreProdProdCd").val();
	params.prodCalssCd =  $("#srchStoreProdProdClassCd").val();
    params.orgnFg    = $scope.orgnFg;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/store/prod/list.sb", params, function() {});

  }; 
   
  // 상품분류정보 팝업
  $scope.popUpProd = function() {
    var params = {};
    params.gubun = "StoreProd";
    params.storeCd   = $("#storeProdSelectStoreCd").val();
    //조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._broadcast('prodStrl', params);
  };
  
  // 상품분류정보 선택취소
  $scope.delProd = function(){
    $scope.prodCd = "";
    $scope.prodCdNm = "";
    $scope.prodCalssCd = "";
  }
  
  // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  var columns = $scope.flex.columns;

	  for(var i=0; i<columns.length; i++){
		  if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
			  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
		  }
	  }
  }
  
  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeProdSelectStoreShow = function () {
    $scope._broadcast('storeProdSelectStoreCtrl');
  };
  
  // 엑셀 다운로드
  $scope.excelDownloadStoreProd = function () {
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
      }, '매출분석_매장별매출분석_매장상품순위_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
  
}]);
