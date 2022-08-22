/****************************************************************
 *
 * 파일명 : verInfoDtl.js
 * 설  명 : 포스버전관리 > 버전 상세보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.31     김지은      1.0           Angular방식으로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
var scope = null;
var manageVer = "";

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('verDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verDetailCtrl', $scope, $http, true));

  // 콤보박스 데이터
  $scope._setComboData("progFgCombo", progFg);
  $scope._setComboData("useYnCombo", useYn);

  // 버전정보
  $scope.version;

  // 조회 버튼 클릭
  $scope.$on("verDetailCtrl", function(event, data) {

    if(window.location.href.indexOf("verHq/list.sb") > 0){ // 본사 화면에서 접속한 경우
      // 활성화된 탭에 따라 모화면 ctrl 값 셋팅
      if($("#verManageTab").hasClass("on")){
        scope = agrid.getScope('verManageCtrl');
        manageVer = "1"; // [1] NXPOS_V1;
      }else{
        scope = agrid.getScope('verManageV2Ctrl');
        manageVer = "2"; // [2] NXPOS_V2;
      }
    }else{                                                // 관리자 화면에서 접속한 경우
      // 유입화면(버전)에 따라 모화면 ctrl 값 셋팅
      if(data.progFg === "1"){
        scope = agrid.getScope('verManageCtrl');
        manageVer = "1"; // [1] NXPOS_V1;
      }else{
        scope = agrid.getScope('verManageV2Ctrl');
        manageVer = "2"; // [2] NXPOS_V2;
      }
    }

    $scope.getVersionInfo();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionInfo = function(){
    var params = {};
    params    = scope.getSelectVersion();

    // console.log('params' , params);

    $scope._postJSONQuery.withOutPopUp( "/pos/confg/verManage/verInfo/dtlInfo.sb", params, function(response){

      // console.log('response',response);

      var data = response.data.data;
      $scope.version = data;

      console.log('data.pgmYn', data.pgmYn);
      console.log('data.dbYn', data.dbYn);
      console.log('data.imgYn', data.imgYn);

      // 포함내역
      var incldDtlsStr = '';
      if(data.pgmYn === 'Y') incldDtlsStr += 'PGM';
      if(data.dbYn === 'Y') {
        if(incldDtlsStr === '') {
          incldDtlsStr += 'DB';
        } else {
          incldDtlsStr += ' / DB';
        }
      }
      if(data.imgYn === 'Y') {
        if(incldDtlsStr === '') {
          incldDtlsStr += 'IMAGE';
        } else {
          incldDtlsStr += ' / IMAGE';
        }
      }
      $scope.version.incldDtls = incldDtlsStr;
    });
  };

  // 수정
  $scope.modify = function(){
    $scope.versionRegistLayer.show(true, function(){
      var scope = agrid.getScope('verRegistCtrl');
      console.log('scope.version', scope.version);
      scope.version = null;
      scope.progFg = '1';
      scope.useYn = 'Y';

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
      sScope._gridDataInit();
      var nScope = agrid.getScope("allStoreCtrl");
      nScope._gridDataInit();

      $("#srchHqOfficeCd").val("");
      $("#srchHqOfficeNm").val("");
      $("#srchStoreCd").val("");
      $("#srchStoreNm").val("");
    });
  };

}]);

