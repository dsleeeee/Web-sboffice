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

  // 선택 사원 (사원 수정시)
  $scope.selectedHqEmp;

  // 신규 수정 여부
  $scope.newEmpYn = true;

  // 웹사용자 아이디 중복체크 여부
  $scope.duplicationChkFg = false;

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
      $scope.newEmpYn                   = true;
      $scope.hqEmpRegistInfo.mainSaleFg = '0';

    } else {

      $scope.getHqEmpList();
      $scope.newEmpYn = false;
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
        $scope.duplicationChkFg = true;
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
      // 웹 사용자 아이디 중복체크
      if(!$scope.duplicationChkFg) {
        $scope._popMsg(messages["hqEmp.require.chk.userId"] );
        return false;
      }
      // 비밀번호, 비밀번호 확인 체크
      if($scope.hqEmpRegistInfo.userPwd !== $scope.hqEmpRegistInfo.userPwdCfm) {
        $scope._popMsg(messages["hqEmp.passwordNotMatch.msg"] );
        return false;
      }
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
        $scope._popMsg(messages["login.pw.not.match.char"]);
        return false;
      } else {
        $scope._popMsg(messages["cmm.registFail"]);
        return false;
      }
    });
  };

  // 저장
  $scope.save = function(){

    // 원래 웹 사용여부 'N'이었는데, 사용여부 'Y'로 변경한 경우
    if(($scope.hqEmpRegistInfo.originalWebUserId == '' || $scope.hqEmpRegistInfo.originalWebUserId == undefined)
       && $scope.hqEmpRegistInfo.webUseYn === 'Y') {
      // 웹 사용자 아이디 중복체크
      if(!$scope.duplicationChkFg) {
        $scope._popMsg(messages["hqEmp.require.chk.userId"] );
        return false;
      }
    }

    var params = $scope.hqEmpRegistInfo;
    params.chkHqBrandCd = $scope.hqEmpRegistInfo.hqBrandCd;

    $scope._postJSONSave.withOutPopUp( "/base/store/emp/hq/save.sb", params, function(response){

      if(response.data.data == 'SUCCESS') {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.close();
      } else if(response.data.data === 'PASSWORD_REGEXP') {
        $scope._popMsg(messages["login.pw.not.match.char"]);
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
