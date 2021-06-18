/****************************************************************
 *
 * 파일명 : verInfoDtl.js
 * 설  명 : 미디어관리 > 버전 상세보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.09     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('verDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verDetailCtrl', $scope, $http, true));

  // 콤보박스 데이터
  $scope._setComboData("useYnCombo", useYn);
  $scope._setComboData("fileTypeCombo", fileType);

  // 버전정보
  $scope.version;

  // 조회 버튼 클릭
  $scope.$on("verDetailCtrl", function(event, data) {
    $scope.getVersionInfo();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionInfo = function(){
    var params = {};

    var scope = agrid.getScope('mediaCtrl');
    params    = scope.getSelectVersion();

    $scope._postJSONQuery.withOutPopUp( "/base/store/media/verInfo/dtlInfo.sb", params, function(response){
      var data = response.data.data;
      $scope.version = data;

      if(orgnFg === "STORE" && $scope.version.verSerNo < 8000){
        $("#btnMod").hide();
      } else {
        $("#btnMod").show();
      }
    });
  };

  // 수정
  $scope.modify = function(){
    $scope.versionRegistLayer.show(true, function(){
      var scope = agrid.getScope('verRegistCtrl');
      console.log('scope.version', scope.version);
      scope.version = null;

      $scope.getVersionInfo();
    });
  };

  // 닫기
  $scope.close = function(){
    $scope.versionInfoDetailLayer.hide();
  };

  // 탭변경
  $scope.changeTab = function(){
    $scope.versionInfoDetailLayer.hide();
    $scope.storeAddLayer.show(true, function(){
      // 탭 닫을때 그리드 초기화
      var sScope = agrid.getScope("addStoreCtrl");
      sScope._gridDataInit()
      var nScope = agrid.getScope("allStoreCtrl");
      nScope._gridDataInit();

      $("#srchHqOfficeCd").val("");
      $("#srchHqOfficeNm").val("");
      $("#srchStoreCd").val("");
      $("#srchStoreNm").val("");
    });
  };

}]);

