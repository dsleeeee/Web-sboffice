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

var agencyDispYnData = [
  {"name":"노출","value":"Y"},
  {"name":"비노출","value":"N"}
];

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

      if(data.progDetailFg == "1")          $scope.version.progDetailFg = "POS&KIOSK";
      else if(data.progDetailFg == "2")     $scope.version.progDetailFg = "KDS";
      else if(data.progDetailFg == "3")     $scope.version.progDetailFg = "DID";
      else if(data.progDetailFg == "4")     $scope.version.progDetailFg = "테이블오더";

      if(data.systemTypeFg == "0")          $scope.version.systemTypeFg = "공통";
      else if(data.systemTypeFg == "1")     $scope.version.systemTypeFg = "32bit";
      else if(data.systemTypeFg == "2")     $scope.version.systemTypeFg = "64bit";

      // 파일사이즈 변환하여 표기
      $scope.version.fileSize = getfileSize($scope.version.fileSize);

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
      $("#srchAddr").val("");

      sScope.hqOfficeCombo.selectedIndex = 0;
      sScope.sysStatFgCombo.selectedIndex = 0;
      $("input:checkbox[id='chkMulti']").prop("checked", false);

      // 대리점관리매장 초기화
      if (orgnFg == "AGENCY") {
          if(orgnCd === "00607" || orgnCd === "00608"){
              sScope.agencyStoreYnCombo.selectedValue = "Y";
          }else{
              sScope.agencyStoreYnCombo.selectedValue = "N";
          }
      } else {
          sScope.agencyStoreYnCombo.selectedValue = "N";
      }
    });
    var scope = agrid.getScope("allStoreCtrl");
    scope.progDetailFg = $scope.version.progDetailFg;
  };
  
  // 삭제
  $scope.delete = function(){
    $scope.wjVerDelInfoLayer.show(true);
    event.preventDefault();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 버전관리 삭제정보 팝업 핸들러 추가
    $scope.wjVerDelInfoLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('verDelInfoCtrl', scope.getSelectVersion());
      }, 50)
    });
  });

}]);