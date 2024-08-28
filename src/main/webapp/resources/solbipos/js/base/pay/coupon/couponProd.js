/****************************************************************
 *
 * 파일명 : couponProd.js
 * 설  명 : 쿠폰 상품등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.17     김지은      1.0
 *
 * **************************************************************/


/**
 *  쿠폰 등록 상품 그리드 생성
 */
app.controller('regProdCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('regProdCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 쿠폰등록 상품 그리드 조회
  $scope.$on("regProdCtrl", function(event, data) {
    if ($("#couponProdTitle").text().substr(1,3) < 800 && orgnFg == "STORE") { // 매장이 본사에서 등록한 쿠폰에 상품등록X
      $("#couponProdLayer .btn_grayS2").hide();
    } else {
      $("#couponProdLayer .btn_grayS2").show();
    }

    $scope.searchRegProd();
    // 등록상품 조회 후, 미등록상품 조회
    var noRegCouponGrid = agrid.getScope("noRegProdCtrl");
    noRegCouponGrid._pageView('noRegProdCtrl', 1);

    event.preventDefault();
  });

  // 등록된 상품 조회
  $scope.searchRegProd = function(){
    if(selectedCoupon != null && selectedCoupon != ""){
      var params = {};

      // params.prodCd = $("#srchProdCd").val();
      // params.prodNm = $("#srchProdNm").val();
      params.prodCd = '';
      params.prodNm = '';

      params.payClassCd = selectedCouponClass.payClassCd;
      params.coupnCd = selectedCoupon.coupnCd;
      params.coupnEnvstVal = coupnEnvstVal;
      params.prodRegFg = "Y";

      // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
      $scope._inquirySub(baseUrl + "prod/getProdList.sb", params, function() {}, false);
    }
  };

  // 등록 상품 삭제
  $scope.delete = function(){

    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].payClassCd = selectedCouponClass.payClassCd;
        $scope.flex.collectionView.items[i].coupnCd = selectedCoupon.coupnCd;
        $scope.flex.collectionView.items[i].coupnEnvstVal = coupnEnvstVal;
        params.push($scope.flex.collectionView.items[i]);
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "prod/deleteCouponProd.sb", params, function(){ $scope.allSearch() });
  };

  // 상품 삭제 완료 후처리
  $scope.allSearch = function () {
    $scope.searchRegProd();
    var noRegProdGrid = agrid.getScope("noRegProdCtrl");
    noRegProdGrid._pageView('noRegProdCtrl', 1);
  };

}]);

/**
 *  쿠폰 미등록 상품 그리드 생성
 */
app.controller('noRegProdCtrl', ['$scope', '$http', function ($scope, $http ) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegProdCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 미등록 상품 그리드 조회
  $scope.$on("noRegProdCtrl", function(event, data) {
    $scope.searchNoRegProd();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 미등록 상품
  $scope.searchNoRegProd = function(){
    if(selectedCoupon != null && selectedCoupon != ""){
        // 파라미터
      var params = {};

      // params.listScale = "10";
      params.prodCd = $("#srchApplyProdCd").val();
      params.prodNm = $("#srchApplyProdNm").val();
      params.payClassCd = selectedCouponClass.payClassCd;
      params.coupnCd = selectedCoupon.coupnCd;
      params.coupnEnvstVal = coupnEnvstVal;
      params.prodRegFg = "N";

      // console.log(params);
      // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
      $scope._inquirySub(baseUrl + "prod/getProdList.sb", params, function() {}, false);
    }
  };

  // 상품 등록
  $scope.regist = function() {
    var couponClassGrid = agrid.getScope("couponClassCtrl");
    var couponGrid = agrid.getScope("couponCtrl");
    var selectedRow = couponGrid.flex.selectedRows[0]._data;

    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      $scope.flex.collectionView.items[i].payClassCd = couponClassGrid.flex.selectedItems[0].payClassCd;
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].coupnCd = selectedRow.coupnCd;
        $scope.flex.collectionView.items[i].coupnEnvstVal = coupnEnvstVal;
        params.push($scope.flex.collectionView.items[i]);
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "prod/registCouponProd.sb", params, function(){
      $scope.allSearch()
    });
  };

  // 상품 등록 완료 후처리 (상품수량 변화)
  $scope.allSearch = function () {
    $scope.searchNoRegProd();
    var regProdGrid = agrid.getScope("regProdCtrl");
    regProdGrid._pageView('regProdCtrl',1);
  };


}]);

app.controller('noRegProdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegProdExcelCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  $("#btnExcelDownload").click(function() {
    $scope.sampleDownload();
  });

  // <-- 양식다운로드 -->
  $scope.sampleDownload = function(){

    var params = {};
    params.payClassCd = selectedCouponClass.payClassCd;
    params.coupnCd = selectedCoupon.coupnCd;
    params.coupnEnvstVal = coupnEnvstVal;
    params.prodRegFg = "N";

    $scope._inquiryMain(baseUrl + "prod/getExcelProdList.sb", params, function (){
      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기

      if ($scope.flex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
        return false;
      }

      $timeout(function()	{
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
            {
              includeColumnHeaders: 	true,
              includeCellStyles	: 	false,
              includeColumns      :	function (column) {
                return column.visible;
              }
            },
            '쿠폰적용상품엑셀업로드'+getCurDateTime()+'.xlsx',
            function () {
              $timeout(function () {
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
              }, 10);
            }
        );
      }, 10);
      $scope._broadcast('regProdCtrl');
    });
  };
// <-- //양식다운로드 -->

  $("#btnExcelUpload").click(function() {
    $scope.excelUpload();
  });

  // 엑셀업로드
  $scope.excelUpload = function () {
    var msg = messages["coupon.excelUpload.confmMsg"];  // 정상상품코드만 등록됩니다.

    $scope._popConfirm(msg, function() {
      $("#excelUpFile").val('');
      $("#excelUpFile").trigger('click');
    });
  };

}]);

