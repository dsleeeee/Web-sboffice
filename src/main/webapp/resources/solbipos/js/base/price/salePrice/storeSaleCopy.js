/****************************************************************
 *
 * 파일명 : storeSaleCopy.js
 * 설  명 : 매장판매가복사 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.16     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 상품별 판매가관리 그리드 생성
 */
app.controller('storeSaleCopyCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeSaleCopyCtrl', $scope, $http, false));

  // <-- 검색 호출 -->
  // 상품정보관리 그리드 조회
  $scope.$on("storeSaleCopyCtrl", function(event, data) {
    $scope.searchSalePriceList();
    event.preventDefault();
  });

  // 판매가 그리드 조회
  $scope.searchSalePriceList = function(){
      if( isEmptyObject($("#originalStoreCd").val()) ) {
          $scope._popMsg("기준매장을 선택해주세요.");
          return false;
      }

      if( isEmptyObject($("#targetStoreCd").val()) ) {
          $scope._popMsg("적용대상매장을 선택해주세요.");
          return false;
      }
      console.log($("#originalStoreCd").val());
      console.log($("#targetStoreCd").val());

      if( $("#originalStoreCd").val() == $("#targetStoreCd").val() ) {
          $scope._popMsg("기준매장과 적용대상매장이 같을 수 없습니다.");
          return false;
      }

    var params = [{nmcodeCd: '05'}];
    var storeParams = {};
    storeParams.originalStoreCd = $("#originalStoreCd").val();
    storeParams.targetStoreCd = $("#targetStoreCd").val();

      // 가상로그인 대응
      // 가상로그인으로 들어왔을때 저장시 Controller에서 sid값을 못읽는 현상 때문에 추가. (2020.03.26_이다솜)
      if (document.getElementsByName('sessionId')[0]) {
          storeParams.sid = document.getElementsByName('sessionId')[0].value;
      }

    console.log('params', params);
    console.log('storeParams', storeParams);

      var msg = "(" + $("#originalStoreNm").val() + ")의 매장판매가를 ("  + $("#targetStoreNm").val() + ")에 적용하시겠습니까?";

      $scope._popConfirm(msg, function () {

          $http({
              method : 'POST', //방식
              url    : '/base/store/view/copyStoreEnv/copyStoreEnvInfo.sb', /* 통신할 URL */
              data   : params, /* 파라메터로 보낼 데이터 */
              params : storeParams,
              headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
          }).then(function successCallback(response) {
              if ($scope._httpStatusCheck(response, true)) {

                  console.log('response', response);
                  if(response.data.status == 'OK'){

                      $scope._popMsg("복사되었습니다");
                      $scope.$broadcast('loadingPopupInactive');
                  }
              }
          }, function errorCallback(response) {
              $scope.$broadcast('loadingPopupInactive');
              $scope._popMsg(response.data.message);
              return false;
          }).then(function () {
              $scope.$broadcast('loadingPopupInactive');
          });

      });
  };

  // <-- //검색 호출 -->
    // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.originalStoreShow = function () {
        $scope._pageView('originalStoreCtrl', 1);
    };

    $scope.targetStoreShow = function () {
        $scope._pageView('targetStoreCtrl', 1);
    };

}]);