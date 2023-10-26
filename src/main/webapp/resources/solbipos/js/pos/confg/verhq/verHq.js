/****************************************************************
 *
 * 파일명 : verHq.js
 * 설  명 : 버전관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.06     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  버전관리 그리드
 **********************************************************************/
app.controller('verHqCtrl', ['$scope', '$http', function ($scope, $http) {
  $scope.init = function () {

    // 환경설정값 1014[포스프로그램구분]에 따른 탭표시
    if(posVerEnvstVal === "1"){
        // tab
        $("#verManageTab").css("display", "");
        $("#verManageV2Tab").css("display", "none");
        $("#verRecvTab").css("display", "");
        $("#storeRecvTab").css("display", "");
        $("#verStoreTab").css("display", "");

        // 화면
        $scope.verManageShow();

    }else{
        // tab
        $("#verManageTab").css("display", "none");
        $("#verManageV2Tab").css("display", "");
        $("#verRecvTab").css("display", "none");
        $("#storeRecvTab").css("display", "none");
        $("#verStoreTab").css("display", "none");

        // 화면
        $scope.verManageV2Show();
    }
  };

  // POS버전관리 탭 보이기
  $scope.verManageShow = function () {
    $("#verManageTab").addClass("on");
    $("#verManageV2Tab").removeClass("on");
    $("#verRecvTab").removeClass("on");
    $("#storeRecvTab").removeClass("on");
    $("#verStoreTab").removeClass("on");

    $("#verManageView").show();
    $("#verManageV2View").hide();
    $("#verRecvView").hide();
    $("#storeRecvView").hide();
    $("#verStoreView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("verManageCtrl");
    scope.flex.refresh();
  };

  // POS 버전 관리(V2 버전) 탭 보이기
    $scope.verManageV2Show = function () {
      $("#verManageTab").removeClass("on");
      $("#verManageV2Tab").addClass("on");
      $("#verRecvTab").removeClass("on");
      $("#storeRecvTab").removeClass("on");
      $("#verStoreTab").removeClass("on");

      $("#verManageView").hide();
      $("#verManageV2View").show();
      $("#verRecvView").hide();
      $("#storeRecvView").hide();
      $("#verStoreView").hide();

      // angular 그리드 hide 시 깨지므로 refresh()
      var scope = agrid.getScope("verManageV2Ctrl");
      scope.flex.refresh();
    };

  // 버전별수신현황 탭 보이기
  $scope.verRecvShow = function () {
    $("#verManageTab").removeClass("on");
    $("#verManageV2Tab").removeClass("on");
    $("#verRecvTab").addClass("on");
    $("#storeRecvTab").removeClass("on");
    $("#verStoreTab").removeClass("on");

    $("#verManageView").hide();
    $("#verManageV2View").hide();
    $("#verRecvView").show();
    $("#storeRecvView").hide();
    $("#verStoreView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("verRecvCtrl");
    scope.flex.refresh();
    var scope2 = agrid.getScope("verRecvStoreCtrl");
    scope2.flex.refresh();
    var scope3 = agrid.getScope("verRecvStoreExcelCtrl");
  };

  // 매장별수신현황 탭 보이기
  $scope.storeRecvShow = function () {
    $("#verManageTab").removeClass("on");
    $("#verManageV2Tab").removeClass("on");
    $("#verRecvTab").removeClass("on");
    $("#storeRecvTab").addClass("on");
    $("#verStoreTab").removeClass("on");

    $("#verManageView").hide();
    $("#verManageV2View").hide();
    $("#verRecvView").hide();
    $("#storeRecvView").show();
    $("#verStoreView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("storeRecvCtrl");
    scope.flex.refresh();
  };

  // 버전별매장현황 탭 보이기
  $scope.verStoreShow = function () {
    $("#verManageTab").removeClass("on");
    $("#verManageV2Tab").removeClass("on");
    $("#verRecvTab").removeClass("on");
    $("#storeRecvTab").removeClass("on");
    $("#verStoreTab").addClass("on");

    $("#verManageView").hide();
    $("#verManageV2View").hide();
    $("#verRecvView").hide();
    $("#storeRecvView").hide();
    $("#verStoreView").show();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("verInfoCtrl");
    scope.flex.refresh();
    var scope2 = agrid.getScope("verInfoDtlCtrl");
    scope2.flex.refresh();
  };

}]);

