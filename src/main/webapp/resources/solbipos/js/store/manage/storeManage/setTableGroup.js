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
    var posEnvScope      = agrid.getScope('posEnvCtrl');

    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;
    params.posNo      = posEnvScope.getSelectedPosNo();
    params.envstFg    = "03"; // 포스환경

    console.log(params)

    $scope.$broadcast('loadingPopupActive');

    $http({
      method : 'POST',
      url    : '/store/manage/storeManage/storeManage/getPosConfigList.sb',
      params : params,
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function successCallback(response) {

      if($.isEmptyObject(response.data) ) {
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.tableGroupLayer.hide();
        return false;
      }
      var posList = posEnvScope.getPosList();
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
    var posEnvScope = agrid.getScope('posEnvCtrl');
    var posLength   = posEnvScope.getPosList().length;

    var params      = new Array();

    $("#tabGrpContent select").each(function(index){

      var id = $(this).attr("id");

      var obj = {};
      obj.storeCd   = storeScope.getSelectedStore().storeCd;
      obj.posNo     = id.substring(3,id.length);
      obj.envstVal  = $("#"+ id).val();

      params.push(obj);
    });

    // console.log(params);

    $scope.$broadcast('loadingPopupActive');

    // ajax 통신 설정
    $http({
      method: 'POST', //방식
      url: "/store/manage/storeManage/storeManage/savePosTabGrp.sb",
      data: params,
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function successCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if(response.data.status === "OK") {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.tableGroupLayer.hide();
      }
      else if(response.data.status === "FAIL") {
        $scope._popMsg("Ajax Fail By HTTP Request");
      }
      else if(response.data.status === "SESSION_EXFIRE") {
        $scope._popMsg(response.data.message, function() {
          location.href = response.data.url;
        });
      }
      else if(response.data.status === "SERVER_ERROR") {
        $scope._popMsg(response.data.message);
      }
      else {
        var msg = response.data.status + " : " + response.data.message;
        $scope._popMsg(msg);
      }
    }, function errorCallback(response) {
      $scope.$broadcast('loadingPopupInactive');
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      if (typeof callback === 'function') {
        setTimeout(function () {
          callback();
        }, 10);
      }
    });
  };
}]);
