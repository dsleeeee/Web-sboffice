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

  // 팝업 오픈시 테이블 그룹정보 조회
  $scope.$on("copyStoreEnvCtrl", function(event, data) {
    // $scope.getPosNmList();
    event.preventDefault();
  });

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.originalStoreShow = function () {
    console.log('searchStoreShow...');
    $scope._pageView('originalStoreCtrl', 1);
  };

  $scope.targetStoreShow = function () {
    console.log('targetStoreShow...')
    $scope._pageView('targetStoreCtrl', 1);
  };

  //
  // // 포스명칭 목록 조회
  // $scope.getPosNmList = function(){
  //
  //   var params        = {};
  //   var storeScope    = agrid.getScope('storeManageCtrl');
  //   var posEnvScope   = agrid.getScope('posEnvCtrl');
  //   var posList       = posEnvScope.getPosList();
  //
  //   params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
  //   params.storeCd    = storeScope.getSelectedStore().storeCd;
  //
  //   $scope.$broadcast('loadingPopupActive');
  //
  //   $scope._postJSONQuery.withOutPopUp( '/store/manage/storeManage/storeManage/getPosList.sb', params, function(response){
  //
  //     if($.isEmptyObject(response.data) ) {
  //       $scope._popMsg(messages["cmm.empty.data"]);
  //       $scope.tableGroupLayer.hide();
  //       return false;
  //     }
  //     $scope.$broadcast('loadingPopupInactive');
  //
  //     var list = response.data.data.list;
  //
  //     var posEnvScope = agrid.getScope('posEnvCtrl');
  //     posEnvScope.setPosList(list);
  //
  //     var innerHtml = "";
  //
  //     for(var i=0; i<posList.length; i++) {
  //
  //       innerHtml += "<tr>";
  //       innerHtml += "<td class='tc'>"+list[i].rownum+"</td>";
  //       innerHtml += "<td class='tc'>"+list[i].posNo+"</td>";
  //       innerHtml += "<td class='tc'>";
  //       innerHtml += "<input name='pos'id='pos"+list[i].posNo+"' value='"+list[i].posNm+"'>";
  //       innerHtml += "</td>";
  //       innerHtml += "</tr>";
  //     }
  //
  //     $("#posNmContent").html(innerHtml);
  //   });
  // };

  // // 저장
  // $scope.savePosNm = function(){
  //
  //   var params     = new Array();
  //   var storeScope = agrid.getScope('storeManageCtrl');
  //
  //   $("#posNmContent input").each(function(index){
  //
  //     var obj     = {};
  //     var id      = $(this).attr("id");
  //
  //     obj.storeCd = storeScope.getSelectedStore().storeCd;
  //     obj.posNo   = id.substring(3, id.length);
  //     obj.posNm   = $("#"+ id).val();
  //
  //     params.push(obj);
  //   });
  //
  //   // console.log(params);
  //
  //   $scope.$broadcast('loadingPopupActive');
  //   $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/savePosNm.sb", params, function(response){
  //     $scope.$broadcast('loadingPopupInactive');
  //     $scope._popMsg(messages["cmm.saveSucc"]);
  //     $scope.posNmLayer.hide();
  //   });
  // };

}]);
