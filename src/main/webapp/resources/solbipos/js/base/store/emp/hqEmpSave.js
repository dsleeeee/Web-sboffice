/****************************************************************
 *
 * 파일명 : hqEmpSave.js
 * 설  명 : 본사사원정보 등록 및 수정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.21     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnComboData = [
  {"name": "사용", "value": "Y"},
  {"name": "미사용", "value": "N"}
];
var mainSaleFgComboData = [
  {"name": "사용", "value": "0"},
  {"name": "미사용", "value": "1"}
];

/**
 * 본사사원 등록 및 수정
 */
app.controller('hqEmpRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqEmpRegistCtrl', $scope, $http, false));

  // 웹 사용여부
  $scope._setComboData('hqEmpWebUseYnComboData', useYnComboData);
  // 사용여부
  $scope._setComboData('hqEmpUseYnFgComboData', useYnComboData);
  // 사용여부
  $scope._setComboData('hqEmpMainSaleFgComboData', mainSaleFgComboData);
  // SMS수신여부
  $scope._getComboDataQuery('072', 'hqEmpSmsRecvYnComboData');
  // 판매상품여부 콤보박스
  $scope._getComboDataQuery('007', 'hqEmpServiceFgComboData');
  // 본사 거래처 콤보박스
  $scope._setComboData('hqEmpVendrCdComboData', vendrList);
  // 그룹 콤보박스
  $scope._setComboData('hqEmpBranchCdComboData', branchList);

  // [1250 맘스터치]
  // 콤보박스 데이터
  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 추가정보-팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // 추가정보-AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 추가정보-지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 추가정보-상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 추가정보-점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 추가정보-매장관리타입
  $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 추가정보-매장그룹
  $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 추가정보-매장그룹2
  $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 추가정보-매장그룹3
  $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 추가정보-매장그룹4
  $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 추가정보-매장그룹5

  // 선택 사원 (사원 수정시)
  $scope.selectedHqEmp;

  // 신규 수정 여부
  $scope.newEmpYn = 1; // 1: 신규등록, 2: 수정(WEB 사용), 3: 수정(WEB 미사용)

  // 웹사용자 아이디 중복체크 여부
  $scope.duplicationChkFg = "";

  // 사원정보
  $scope.hqEmpRegistInfo;
  $scope.setHqEmpRegistInfo = function(emp){
    $scope.hqEmpRegistInfo = emp;
  };
  $scope.getHqEmpRegistInfo = function(){
    return $scope.hqEmpRegistInfo;
  };

  // 브랜드사용여부 가 1일때만
  if(userHqBrandYn == "1") {
    $("#trUserHqBrandYn").css("display", "")
  } else {
    $("#trUserHqBrandYn").css("display", "none")
  }

  // 해당 scope 호출
  $scope.$on("hqEmpRegistCtrl", function(event, data) {

    $scope.selectedHqEmp = data;

    if( isEmptyObject(data)) {

      $scope.hqEmpRegistInfo            = {};
      $scope.hqEmpRegistInfo.webUseYn   = 'Y';
      $scope.hqEmpRegistInfo.smsRecvYn  = 'N';
      $scope.hqEmpRegistInfo.serviceFg  = '1';
      $scope.hqEmpRegistInfo.useYn      = 'Y';
      $scope.newEmpYn                   = 1;
      $scope.hqEmpRegistInfo.mainSaleFg = '0';

      // [1250 맘스터치]
      if(momsEnvstVal === "1") {
        $scope.srchMomsTeamCombo.selectedIndex = 0;
        $scope.srchMomsAcShopCombo.selectedIndex = 0;
        $scope.srchMomsAreaFgCombo.selectedIndex = 0;
        $scope.srchMomsCommercialCombo.selectedIndex = 0;
        $scope.srchMomsShopTypeCombo.selectedIndex = 0;
        $scope.srchMomsStoreManageTypeCombo.selectedIndex = 0;
        $scope.srchMomsStoreFg01Combo.selectedIndex = 0;
        $scope.srchMomsStoreFg02Combo.selectedIndex = 0;
        $scope.srchMomsStoreFg03Combo.selectedIndex = 0;
        $scope.srchMomsStoreFg04Combo.selectedIndex = 0;
        $scope.srchMomsStoreFg05Combo.selectedIndex = 0;
      }

    } else {

      $scope.getHqEmpList();
    }

    event.preventDefault();
  });

  // 본사사원정보관리 그리드 조회
  $scope.getHqEmpList = function(){

    var params = $scope.selectedHqEmp;

    $scope._postJSONQuery.withOutPopUp( "/base/store/emp/hq/detail.sb", params, function(response){
      $scope.hqEmpRegistInfo                    = response.data.data;
      $scope.hqEmpRegistInfo.empInfo            = ' [' + response.data.data.empNo + ']' + response.data.data.empNm;
      $scope.hqEmpRegistInfo.originalWebUserId  = response.data.data.userId;
      $scope.duplicationChkFg                   = response.data.data.userId;

      if (response.data.data.userId != null && response.data.data.userId != undefined && response.data.data.userId != "") {
          $scope.newEmpYn = 2; // 수정(WEB 사용)
      } else {
          $scope.newEmpYn = 3; // 수정(WEB 미사용)
      }

      // [1250 맘스터치]
      $scope.hqEmpRegistInfo.momsTeam = nvl(response.data.data.momsTeam, "");
      $scope.hqEmpRegistInfo.momsAcShop = nvl(response.data.data.momsAcShop, "");
      $scope.hqEmpRegistInfo.momsAreaFg = nvl(response.data.data.momsAreaFg, "");
      $scope.hqEmpRegistInfo.momsCommercial = nvl(response.data.data.momsCommercial, "");
      $scope.hqEmpRegistInfo.momsShopType = nvl(response.data.data.momsShopType, "");
      $scope.hqEmpRegistInfo.momsStoreManageType = nvl(response.data.data.momsStoreManageType, "");
      $scope.hqEmpRegistInfo.momsStoreFg01 = nvl(response.data.data.momsStoreFg01, "");
      $scope.hqEmpRegistInfo.momsStoreFg02 = nvl(response.data.data.momsStoreFg02, "");
      $scope.hqEmpRegistInfo.momsStoreFg03 = nvl(response.data.data.momsStoreFg03, "");
      $scope.hqEmpRegistInfo.momsStoreFg04 = nvl(response.data.data.momsStoreFg04, "");
      $scope.hqEmpRegistInfo.momsStoreFg05 = nvl(response.data.data.momsStoreFg05, "");
    });
  };

  // 아이디 정책 및 중복 체크
  $scope.checkDuplicate = function(){

    if( isEmptyObject($scope.hqEmpRegistInfo.userId)) {
      $scope._popMsg(messages["hqEmp.userId"] + messages["cmm.require.text"] );
      return false;
    }

    var params    = {};
    params.userId = $scope.hqEmpRegistInfo.userId;

    $scope._postJSONQuery.withPopUp( "/base/store/emp/hq/chkHqUserId.sb", params, function(response){

      var result = response.data.data;

      console.log('chk duplicate result', result);

      if(result == "SUCCESS"){
        $scope.duplicationChkFg = $scope.hqEmpRegistInfo.userId;
        $scope._popMsg(messages["hqEmp.notDuplicate.msg"]);
      } else if(result === "USER_ID_REGEXP"){
        $scope._popMsg(messages["hqEmp.userIdRegexp.msg"]);
      } else if(result === "USER_ID_LENGHTH_REGEXP"){
        $scope._popMsg(messages["hqEmp.userIdLengthRegexp.msg"]);
      } else if(result === "USER_ID_CANNOT_USE_HANGEUL"){
        $scope._popMsg(messages["hqEmp.userIdNotUseHangeul.msg"]);
      } else if(result === "USER_ID_MUST_CONTAIN_ENG_CAHR"){
        $scope._popMsg(messages["hqEmp.userIdContainEngChar.msg"]);
      } else if(result === "USER_ID_ONLY_ENG_NUM_CHAR"){
        $scope._popMsg(messages["hqEmp.userIdOnlyEnvNumChar.msg"]);
      } else if(result === "USER_ID_DUPLICATE"){
        $scope._popMsg(messages["hqEmp.userId.duplicate.msg"]);
      } else {
        $scope._popMsg(messages["hqEmp.userId.notDuplicate.msg"]);
      }
    });
  };

  // 비밀번호 변경
  $scope.changePassword = function(){
    $scope.changePwdLayer.show(true);
  };

  // 신규등록
  $scope.regist = function(){

    if($scope.hqEmpRegistInfo.webUseYn === 'Y') {

        /*웹사용자ID 중복체크*/
        if ($scope.duplicationChkFg === "") {
            $scope._popMsg(messages["hqEmp.require.chk.userId"]);
            return false;
        }

        /*웹사용자ID 중복체크2*/
        if ($scope.hqEmpRegistInfo.userId !== $scope.duplicationChkFg) {
            $scope._popMsg(messages["hqEmp.require.chk.userId"]);
            return false;
        }

        // 비밀번호, 비밀번호 확인 체크
        if ($scope.hqEmpRegistInfo.userPwd !== $scope.hqEmpRegistInfo.userPwdCfm) {
            $scope._popMsg(messages["hqEmp.passwordNotMatch.msg"]);
            return false;
        }
    } else {
        $scope.hqEmpRegistInfo.userId = "";
        $scope.hqEmpRegistInfo.userPwd = "";
        $scope.hqEmpRegistInfo.userPwdCfm = "";
    }

    var params      = $scope.hqEmpRegistInfo;
    params.pwdChgFg = false; // 비밀번호 변경여부
    params.chkHqBrandCd = $scope.hqEmpRegistInfo.hqBrandCd;

    $scope._postJSONSave.withOutPopUp( "/base/store/emp/hq/regist.sb", params, function(response){

      if(response.data.data == 'SUCCESS') {
        $scope._popMsg(messages["cmm.registSucc"]);
        $scope.close();
      } else if(response.data.data === 'USER_ID_REGEXP') {
        $scope._popMsg(messages["hqEmp.userIdRegexp.msg"]);
        return false;
      } else if(response.data.data === 'PASSWORD_REGEXP') {
        $scope._popMsg(messages["login.pw.cannot"]);
        return false;
      } else {
        $scope._popMsg(messages["cmm.registFail"]);
        return false;
      }
    });
  };

  // 저장
  $scope.save = function(){

    // 한번도 웹 사용한적 없는경우
    if($scope.hqEmpRegistInfo.originalWebUserId == '' || $scope.hqEmpRegistInfo.originalWebUserId == undefined || $scope.hqEmpRegistInfo.originalWebUserId == null){
        // 웹 사용여부 'Y'로 변경시
        if($scope.hqEmpRegistInfo.webUseYn === 'Y') {

            /*웹사용자ID 중복체크*/
            if ($scope.duplicationChkFg === "") {
                $scope._popMsg(messages["hqEmp.require.chk.userId"]);
                return false;
            }

            /*웹사용자ID 중복체크2*/
            if ($scope.hqEmpRegistInfo.userId !== $scope.duplicationChkFg) {
                $scope._popMsg(messages["hqEmp.require.chk.userId"]);
                return false;
            }

            // 비밀번호, 비밀번호 확인 체크
            if ($scope.hqEmpRegistInfo.userPwd !== $scope.hqEmpRegistInfo.userPwdCfm) {
                $scope._popMsg(messages["hqEmp.passwordNotMatch.msg"]);
                return false;
            }
        } else { // 웹 사용여부 'N'로 변경시
            $scope.hqEmpRegistInfo.userId = "";
            $scope.hqEmpRegistInfo.userPwd = "";
            $scope.hqEmpRegistInfo.userPwdCfm = "";
        }
    }

    var params = $scope.hqEmpRegistInfo;
    params.pwdChgFg = false; // 비밀번호 변경여부
    params.chkHqBrandCd = $scope.hqEmpRegistInfo.hqBrandCd;

    $scope._postJSONSave.withOutPopUp( "/base/store/emp/hq/save.sb", params, function(response){

      if(response.data.data == 'SUCCESS') {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.close();
      } else if(response.data.data === 'PASSWORD_REGEXP') {
        $scope._popMsg(messages["login.pw.cannot"]);
        return false;
      } else if(response.data.data === 'PASSWORD_NOT_CHANGED') {
        $scope._popMsg(messages["hqEmp.passwordNotChanged.msg"]);
        return false;
      } else {
        $scope._popMsg(messages["cmm.saveFail"]);
        return false;
      }
    });
  };

  // 닫기버튼 클릭
  $scope.close = function(){
    $scope.hqEmpWebUseYnCombo.selectedIndex = 0;
    $scope.hqEmpSmsRecvYnCombo.selectedIndex = 0;
    $scope.hqEmpServiceFgCombo.selectedIndex = 0;
    $scope.hqEmpUseYnFgCombo.selectedIndex = 0;
    $scope.hqEmpMainSaleFgCombo.selectedIndex = 0;
    $scope.hqEmpVendrCdCombo.selectedIndex = 0;
    $scope.hqEmpBranchCdCombo.selectedIndex = 0;

    $scope.hqEmpRegistLayer.hide();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 비밀번호 변경 팝업 핸들러 추가
    $scope.changePwdLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        var params = $scope.hqEmpRegistInfo;
        params.empFg = 'H'; // 본사 사원
        $scope._broadcast('changePwdCtrl', params);
      }, 50);
    });
  });

  // 탭변경
  $scope.changeTab = function() {
    s_alert.pop(messages["hqEmp.request.regist.hqEmpInfo"]);
    return;
  };

  // 관리브랜드 팝업
  $scope.popUpHqBrandCd = function() {
    var params = $scope.hqEmpRegistInfo;
    $scope.setHqEmpRegistInfo(params);

    $scope.wjSearchUserHqBrandLayer.show(true);
    event.preventDefault();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 관리브랜드 조회 팝업 핸들러 추가
    $scope.wjSearchUserHqBrandLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('searchNoUserHqBrandTotalCtrl', $scope.getHqEmpRegistInfo());
      }, 50)
    });
  });

}]);
