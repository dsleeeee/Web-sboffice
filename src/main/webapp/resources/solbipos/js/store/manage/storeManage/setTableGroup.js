/****************************************************************
 *
 * 파일명 : setTableGroup.js
 * 설  명 : 테이블 그룹 관리 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.25     김지은      1.0
 *
 * **************************************************************/

app.controller('tableGroupCtrl', ['$scope', '$http', function ($scope, $http) {

  angular.extend(this, new RootController('tableGroupCtrl', $scope, $http, false));

  // 팝업 오픈시 테이블 그룹정보 조회
  $scope.$on("tableGroupCtrl", function(event, data) {
    $scope.getTableGroupList();
    event.preventDefault();
  });

  // 테이블 그룹 목록 조회
  $scope.getTableGroupList = function(){

    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');
    var envScope      = agrid.getScope('storeEnvCtrl');

    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;
    params.posNo      = envScope.getSelectedPosNo();
    params.envstFg    = "03"; // 포스환경

    $scope.$broadcast('loadingPopupActive');

    $http({
      method : 'POST',
      url    : '/store/manage/storeManage/storeManage/getPosConfigList.sb',
      params : params,
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function successCallback(response) {

      // console.log(response.data);

      if($.isEmptyObject(response.data) ) {
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.tableGroupLayer.hide();
        return false;
      }
      var posList = envScope.getPosList();
      var grpList = response.data.data.groupList;

      var innerHtml = "";
      for(var i=0; i<posList.length; i++) {

        innerHtml += "<tr>";
        innerHtml += "<td class='tc'>"+posList[i].rownum+"</td>";
        innerHtml += "<td class='tc'>"+posList[i].posNo+"</td>";
        innerHtml += "<td class='tc'>";
        innerHtml += "<select name='pos'id='pos"+posList[i].posNo+"'>";

        for(var j=0; j<grpList.length; j++){
          innerHtml += "<option value='"+grpList[j].tblGrpCd+"'>"+grpList[j].tblGrpNm+"</option>";
        }

        innerHtml += "</select>";
        innerHtml += "</td>";
        innerHtml += "</tr>";
      }

      $("#tabGrpContent").html(innerHtml);

      $scope.$broadcast('loadingPopupInactive');

    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
    });
  };

  // 저장
  $scope.save = function(){

    var storeScope  = agrid.getScope('storeManageCtrl');
    var envScope    = agrid.getScope('storeEnvCtrl');
    var posLength   = envScope.getPosList().length;

    var params      = new Array();

    $("#tabGrpContent select").each(function(index){

      var id = $(this).attr("id");

      var obj = {};
      obj.storeCd   = storeScope.getSelectedStore().storeCd;
      obj.posNo     = id.substring(3,id.length);
      obj.envstVal  = $("#"+ id).val();

      params.push(obj);

    });

    console.log(params)

    $scope._save("/store/manage/storeManage/storeManage/savePosTabGrp.sb", params, function () {

      console.log(">>>>>>>>>>>")
      $scope._popMsg(messages["cmm.saveSucc"]);
      $scope.tableGroupLayer.hide();
    });
  };

}]);
