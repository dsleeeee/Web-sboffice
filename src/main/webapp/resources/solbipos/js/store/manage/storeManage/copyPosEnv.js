/****************************************************************
 *
 * 파일명 : copyPosEnvCtrl.js
 * 설  명 : 포스 환경설정 복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.26     김지은      1.0
 *
 * **************************************************************/

app.controller('copyPosEnvCtrl', ['$scope', '$http', function ($scope, $http) {

  angular.extend(this, new RootController('copyPosEnvCtrl', $scope, $http, false));

  // 기준포스
  $scope.copyPos;
  $scope.setPos = function(s) {
    $scope.copyPos = s.selectedItem.posNo;
  };
  $scope.getPos = function(){
    return $scope.copyPos;
  };

  // 타겟포스
  $scope.targetPos;
  $scope.setTargetPos = function(s){
    $scope.targetPos = s.selectedItem.posNo;
  };
  $scope.getTargetPos = function(){
    return $scope.targetPos;
  };

  // 팝업 오픈시 테이블 그룹정보 조회
  $scope.$on("copyPosEnvCtrl", function(event, data) {
    $scope.getPosList();
    event.preventDefault();
  });

  // 포스명칭 목록 조회
  $scope.getPosList = function(){

    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');

    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;

    $scope.$broadcast('loadingPopupActive');
    $scope._postJSONQuery.withPopUp( '/store/manage/storeManage/storeManage/getPosList.sb', params, function(response){

      if($.isEmptyObject(response.data) ) {
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.tableGroupLayer.hide();
        return false;
      }
      $scope.$broadcast('loadingPopupInactive');

      var list = response.data.data.list;

      $scope._setComboData("posNo", list);
      $scope._setComboData("tPosNo", list);
    });
  };


  // 복사
  $scope.copy = function(){

    var originalPos = $scope.getPos();
    var targetPos   = $scope.getTargetPos();

    // 기존 포스번호와 타겟 포스번호는 서로 다른 값이 되어야 합니다.
    if(originalPos === targetPos){
      $scope._popMsg(messages["storeManage.require.diff.posNo"]);
      return false;
    }

    var params          = {};
    var storeScope      = agrid.getScope('storeManageCtrl');

    params.hqOfficeCd   = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd      = storeScope.getSelectedStore().storeCd;
    params.posNo        = originalPos;
    params.targetPosNo  = targetPos;

    // console.log('params', params);

    $scope._postJSONSave.withPopUp("/store/manage/storeManage/storeManage/copyPosSetting.sb", params, function(response){
      console.log('response',response);
      $scope._popMsg(messages["cmm.saveSucc"]);
      $scope.copyPosEnvLayer.hide();
    });
  };

}]);
