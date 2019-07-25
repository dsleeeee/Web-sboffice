/****************************************************************
 *
 * 파일명 : copyStoreEnv.js
 * 설  명 : 매장환경복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.28     김지은      1.0
 *
 * **************************************************************/

app.controller('copyStoreEnvCtrl', ['$scope', '$http', function ($scope, $http) {

  angular.extend(this, new RootController('copyStoreEnvCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 팝업 오픈시 테이블 그룹정보 조회
  $scope.$on("copyStoreEnvCtrl", function(event, data) {
    $scope.searchEnvList();
    event.preventDefault();
  });

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.originalStoreShow = function () {
    $scope._pageView('originalStoreCtrl', 1);
  };

  $scope.targetStoreShow = function () {
    $scope._pageView('targetStoreCtrl', 1);
  };

  // 콤보박스 리셋
  $scope.resetCombobox = function(){
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      $scope.flex.collectionView.items[i].gChk = false;
    }
  };

  // 복사할 환경목록 조회
  $scope.searchEnvList = function(){

    var params             = {};
    params.originalStoreCd = $("#originalStoreCd").val();
    params.targetStoreCd   = $("#targetStoreCd").val();

    $scope._postJSONQuery.withOutPopUp( "/base/store/view/copyStoreEnv/getStoreEnvInfo.sb", params,
      function(response){

        var data            = response.data.data;
        var envList         = data.envList;

        $scope.data = new wijmo.collections.CollectionView(envList);
      });
  };

  // 복사
  $scope.copy = function(){


    if( isEmptyObject($("#originalStoreCd").val()) ) {
      $scope._popMsg("원매장을 선택해주세요.");
      return false;
    }

    if( isEmptyObject($("#targetStoreCd").val()) ) {
      $scope._popMsg("복사대상매장을 선택해주세요.");
      return false;
    }

    var params                  = new Array();
    var storeParams             = {};
    storeParams.originalStoreCd = $("#originalStoreCd").val();
    storeParams.targetStoreCd   = $("#targetStoreCd").val();


    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        params.push($scope.flex.collectionView.items[i]);
      }
    }

console.log('params',params);
console.log('storeParams',storeParams);

    if(params.length == 0) {
      $scope._popMsg("복사할 환경을 선택해주세요.");
      return false;
    }

    $scope.$broadcast('loadingPopupActive');

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
          $scope.searchEnvList();
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
  };

  // 팝업 닫기
  $scope.close = function(){
    // 초기화
    $("#originalStoreCd").val("");
    $("#originalStoreNm").val("선택");
    $("#targetStoreCd").val("");
    $("#targetStoreNm").val("선택");

    $scope.copyStoreEnvLayer.hide();
  };
}]);
