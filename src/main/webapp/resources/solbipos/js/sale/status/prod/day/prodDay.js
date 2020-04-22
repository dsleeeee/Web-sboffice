/**
 * get application
 */
var app = agrid.getApp();

/** 일자별상품 상세현황 controller */
app.controller('prodDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodDayCtrl', $scope, $http, true));

  // 조회일자 세팅
  $scope.srchStartDate = wcombo.genDateVal("#srchDayStartDate", getToday());
  $scope.srchEndDate   = wcombo.genDateVal("#srchDayEndDate", getToday());
  $scope.orgnFg = gvOrgnFg;

  // 콤보박스 데이터 Set
  $scope._setComboData('prodDaylistScaleBox', gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("prodDayCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodDayCtrl", function (event, data) {
    $scope.searchProdDayList(true);
    // 기능수행 종료 : 반드시 추가
//    event.preventDefault();
  });

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodDayCtrlSrch", function (event, data) {
    $scope.searchProdDayList(false);
    // 기능수행 종료 : 반드시 추가
//    event.preventDefault();
  });

  // 상품매출순위 리스트 조회
  $scope.searchProdDayList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.storeCd   = $("#pordDaySelectStoreCd").val();
    params.prodCd    = $("#srchDayProdCd").val();
    params.prodNm    = $("#srchDayProdNm").val();
    params.orgnFg    = $scope.orgnFg;
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }

    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/prod/day/list.sb", params, function() {});

  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

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
  $scope.pordDaySelectStoreShow = function () {
    $scope._broadcast('pordDaySelectStoreCtrl');
  };

  // 상품분류정보 팝업
  $scope.popUpProdDay = function() {
    var popUp = $scope.prodClassPopUpLayer.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope = agrid.getScope('prodClassPopUpCtrl');
        var prodDayCd = scope.getSelectedClass();
        var params = {};
        params.prodClassCd = prodDayCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function(response){
            $scope.prodDayCd = prodDayCd;
            $scope.prodDayCdNm = response.data.data;
          }
        );
      }
    });
  };

  // 상품분류정보 선택취소
  $scope.delProdDay = function(){
    $scope.prodDayCd = "";
    $scope.prodDayCdNm = "";
  }

  // 엑셀 다운로드
  $scope.excelDownloadDay = function () {
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
      }, '매출현황_상품별_일자별_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);
