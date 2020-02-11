/**
 * get application
 */
var app = agrid.getApp();

/** 결제수단별 상세현황 controller */
app.controller('prodPayFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodPayFgCtrl', $scope, $http, true));
     
  // 조회일자 세팅
  $scope.srchStartDate = wcombo.genDateVal("#srchPayFgStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchPayFgEndDate", gvEndDate);
  
  // 콤보박스 데이터 Set
  $scope._setComboData('prodPayFglistScaleBox', gvListScaleBoxData);
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("prodClassCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodPayFgCtrl", function (event, data) {
    $scope.searchProdPayFgList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodPayFgCtrlSrch", function (event, data) {
    $scope.searchProdPayFgList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  }); 
  
  // 상품매출순위 리스트 조회
  $scope.searchProdPayFgList = function (isPageChk) {

    if ($("#pordPayFgSelectStoreCd").val() === '') {
      $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
      return false;
    }

    // 파라미터
    var params       = {};
    params.storeCd   = $("#pordPayFgSelectStoreCd").val();
    params.prodCd    = $("#srchPayFgProdCd").val();
    params.prodNm    = $("#srchPayFgProdNm").val();
    params.listScale = $scope.prodPayFglistScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/prod/payFg/list.sb", params, function() {});

  };
  
  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };
  
  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.pordPayFgSelectStoreShow = function () {
    $scope._broadcast('pordPayFgSelectStoreCtrl');
  };
   
  // 엑셀 다운로드
  $scope.excelDownloadPayFg = function () {
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
      }, 'excel.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);
