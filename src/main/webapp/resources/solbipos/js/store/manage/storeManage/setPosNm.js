/****************************************************************
 *
 * 파일명 : setPosNm.js
 * 설  명 : 포스명칭관리 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.26     김지은      1.0
 *
 * **************************************************************/

app.controller('posNmCtrl', ['$scope', '$http', function ($scope, $http) {

  angular.extend(this, new RootController('posNmCtrl', $scope, $http, false));

  // 팝업 오픈시 테이블 그룹정보 조회
  $scope.$on("posNmCtrl", function(event, data) {
    $scope.getPosNmList();
    event.preventDefault();
  });

  // 포스명칭 목록 조회
  $scope.getPosNmList = function(){

    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');
    var posEnvScope   = agrid.getScope('posEnvCtrl');
    var posList       = posEnvScope.getPosList();

    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;

    $scope.$broadcast('loadingPopupActive');

    $scope._postJSONQuery.withOutPopUp( '/store/manage/storeManage/storeManage/getPosList.sb', params, function(response){

      if($.isEmptyObject(response.data) ) {
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.tableGroupLayer.hide();
        return false;
      }
      $scope.$broadcast('loadingPopupInactive');

      var list = response.data.data.list;

      var posEnvScope = agrid.getScope('posEnvCtrl');
      posEnvScope.setPosList(list);

      var innerHtml = "";

      for(var i=0; i<posList.length; i++) {

        innerHtml += "<tr>";
        innerHtml += "<td class='tc'>"+list[i].rownum+"</td>";
        innerHtml += "<td class='tc'>"+list[i].posNo+"</td>";
        innerHtml += "<td class='tc'>";
        innerHtml += "<input name='pos'id='pos"+list[i].posNo+"' value='"+list[i].posNm+"'>";
        innerHtml += "</td>";
        innerHtml += "</tr>";
      }

      $("#posNmContent").html(innerHtml);
    });
  };

  // 저장
  $scope.savePosNm = function(){

    var params     = new Array();
    var storeScope = agrid.getScope('storeManageCtrl');

    $("#posNmContent input").each(function(index){

      var obj     = {};
      var id      = $(this).attr("id");

      obj.storeCd = storeScope.getSelectedStore().storeCd;
      obj.posNo   = id.substring(3, id.length);
      obj.posNm   = $("#"+ id).val();

      params.push(obj);
    });

    // console.log(params);

    $scope.$broadcast('loadingPopupActive');
    $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/savePosNm.sb", params, function(response){
      $scope.$broadcast('loadingPopupInactive');
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.posNmLayer.hide();
    });
  };

}]);
