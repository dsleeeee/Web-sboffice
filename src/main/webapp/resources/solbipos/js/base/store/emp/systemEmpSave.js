/****************************************************************
 *
 * 파일명 : systemEmpSave.js
 * 설  명 : 사원정보 등록 및 수정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.26     김지은      1.0
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

/**
 * 매장사원 등록 및 수정
 */
app.controller('systemEmpRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('systemEmpRegistCtrl', $scope, $http, false));

  // 웹 사용여부
  $scope._setComboData('systemEmpWebUseYnComboData', useYnComboData);
  // 사용여부
  $scope._setComboData('systemEmpUseYnFgComboData', useYnComboData);
  // SMS수신여부
  $scope._getComboDataQuery('072', 'systemEmpSmsRecvYnComboData');
  // 판매상품여부 콤보박스
  $scope._getComboDataQuery('007', 'systemEmpServiceFgComboData');
  // 관리자구분 콤보박스
  $scope._getComboDataQuery('097', 'systemEmpAdminFgComboData');

  // 선택 사원 (사원 수정시)
  $scope.selectedSystemEmp;

  // 신규 수정 여부
  $scope.newEmpYn = true;

  // 웹사용자 아이디 중복체크 여부
  $scope.duplicationChkFg = false;

  // 비밀번호 변경여부
  $scope.pwdChgFg = false;


  // 사원정보
  $scope.systemEmpRegistInfo;
  $scope.setSystemEmpRegistInfo = function(emp){
    $scope.systemEmpRegistInfo = emp;
  };
  $scope.getSystemEmpRegistInfo = function(){
    return $scope.systemEmpRegistInfo;
  };

  // 해당 scope 호출
  $scope.$on("systemEmpRegistCtrl", function(event, data) {

    $scope.selectedSystemEmp = data;

    if( isEmptyObject(data)) {

      $scope.systemEmpRegistInfo            = {};
      $scope.systemEmpRegistInfo.webUseYn   = 'Y';
      $scope.systemEmpRegistInfo.smsRecvYn  = 'N';
      $scope.systemEmpRegistInfo.serviceFg  = '1';
      $scope.systemEmpRegistInfo.useYn      = 'Y';
      $scope.systemEmpRegistInfo.adminFg    = 'A';
      $scope.newEmpYn                       = true;

    } else {

      $scope.getSystemEmpList();
      $scope.newEmpYn = false;
    }

    // $scope.adminFgCombo.isReadOnly= !$scope.newEmpYn;

    event.preventDefault();
  });

  // 매장사원정보관리 그리드 조회
  $scope.getSystemEmpList = function(){

    var params = $scope.selectedSystemEmp;

    $scope._postJSONQuery.withOutPopUp( "/base/store/emp/system/detail.sb", params, function(response){
      $scope.systemEmpRegistInfo                    = response.data.data;
      $scope.systemEmpRegistInfo.empInfo            = ' [' + response.data.data.empNo + ']' + response.data.data.empNm;
      $scope.pwdChgFg                               = false;
      $scope.systemEmpRegistInfo.originalWebUserId  = response.data.data.userId;
    });
  };

  // 아이디 정책 및 중복 체크
  $scope.checkDuplicate = function(){

    if( isEmptyObject($scope.systemEmpRegistInfo.userId)) {
      $scope._popMsg(messages["systemEmp.userId"] + messages["cmm.require.text"] );
      return false;
    }

    var params    = {};
    params.userId = $scope.systemEmpRegistInfo.userId;

    // console.log('params ', params);

    $scope._postJSONQuery.withPopUp( "/base/store/emp/system/chkSystemUserId.sb", params, function(response){

      var result = response.data.data;

      if(result == "SUCCESS"){
        $scope.duplicationChkFg = true;
        $scope._popMsg(messages["storeEmp.notDuplicate.msg"]);
      } else if(result === "USER_ID_REGEXP"){
        $scope._popMsg(messages["storeEmp.userIdRegexp.msg"]);
      } else if(result === "USER_ID_LENGHTH_REGEXP"){
        $scope._popMsg(messages["storeEmp.userIdLengthRegexp.msg"]);
      } else if(result === "USER_ID_CANNOT_USE_HANGEUL"){
        $scope._popMsg(messages["storeEmp.userIdNotUseHangeul.msg"]);
      } else if(result === "USER_ID_MUST_CONTAIN_ENG_CAHR"){
        $scope._popMsg(messages["storeEmp.userIdContainEngChar.msg"]);
      } else if(result === "USER_ID_ONLY_ENG_NUM_CHAR"){
        $scope._popMsg(messages["storeEmp.userIdOnlyEnvNumChar.msg"]);
      } else if(result === "USER_ID_DUPLICATE"){
        $scope._popMsg(messages["storeEmp.userId.duplicate.msg"]);
      } else {
        $scope._popMsg(messages["storeEmp.userId.notDuplicate.msg"]);
      }
    });
  };

  // // 비밀번호 변경
  // $scope.changePassword = function(){
  //   $scope.changePwdLayer.show(true);
  // };

  // 대리점 조회
  $scope.searchAgency = function(){

    // $scope.agencyLayer.show();
    $scope.agencyLayer.show(true, function(s){
      var agencyScope = agrid.getScope('searchAgencyCtrl');
      // console.log('agencyResult ', agencyScope.getAgency())

      $scope.$apply(function() {
        if( !$.isEmptyObject(agencyScope.getAgency())  ){
          $scope.systemEmpRegistInfo.agencyCd = agencyScope.getAgency().agencyCd;
          $scope.systemEmpRegistInfo.agencyNm = agencyScope.getAgency().agencyNm;
        }
      });
    });
  };

  // 신규등록
  $scope.regist = function(){

    if($scope.systemEmpRegistInfo.webUseYn === 'Y') {
      // 웹 사용자 아이디 중복체크
      if(!$scope.duplicationChkFg) {
        $scope._popMsg(messages["systemEmp.require.chk.userId"] );
        return false;
      }
      // 비밀번호, 비밀번호 확인 체크
      if($scope.systemEmpRegistInfo.userPwd !== $scope.systemEmpRegistInfo.userPwdCfm) {
        $scope._popMsg(messages["systemEmp.passwordNotMatch.msg"] );
        return false;
      }
    }

    // 대리점(관리업체) 선택 필수
    if(isEmptyObject($scope.systemEmpRegistInfo.agencyNm)){
      $scope._popMsg(messages["systemEmp.require.agencyCd"] );
      return false;
    }

    var params = $scope.systemEmpRegistInfo;
    params.pwdChgFg = false;

    $scope._postJSONSave.withOutPopUp( "/base/store/emp/system/regist.sb", params, function(response){

      if(response.data.data == 'SUCCESS') {
        $scope._popMsg(messages["cmm.registSucc"]);
        $scope.systemEmpRegistLayer.hide();
      } else if(response.data.data === 'USER_ID_REGEXP') {
        $scope._popMsg(messages["systemEmp.userIdRegexp.msg"]);
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
    if(($scope.systemEmpRegistInfo.originalWebUserId == '' || $scope.systemEmpRegistInfo.originalWebUserId == undefined)
        && $scope.systemEmpRegistInfo.webUseYn === 'Y') {
      // 웹 사용자 아이디 중복체크
      if(!$scope.duplicationChkFg) {
        $scope._popMsg(messages["systemEmp.require.chk.userId"] );
        return false;
      }
    }

    // 웹사용여부 'Y'면서 비밀번호 변경시
    if($scope.systemEmpRegistInfo.webUseYn === 'Y' &&  !isEmptyObject($scope.systemEmpRegistInfo.userPwd)) {

      $scope.pwdChgFg = true;
      if($scope.systemEmpRegistInfo.userPwd !== $scope.systemEmpRegistInfo.userPwdCfm) { // 비밀번호, 비밀번호 확인 체크
        $scope._popMsg(messages["systemEmp.passwordNotMatch.msg"] );
        return false;
      }
    } else {
      $scope.pwdChgFg = false;
    }

    // 대리점(관리업체) 선택 필수
    if(isEmptyObject($scope.systemEmpRegistInfo.agencyNm)){
      $scope._popMsg(messages["systemEmp.require.agencyCd"] );
      return false;
    }

    var params      = $scope.systemEmpRegistInfo;
    params.pwdChgFg = $scope.pwdChgFg;

    // console.log('save params' , params);

    $scope._postJSONSave.withOutPopUp( "/base/store/emp/system/save.sb", params, function(response){
      // console.log('save result', response);

      if(response.data.data == 'SUCCESS') {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.systemEmpRegistLayer.hide();
      } else if(response.data.data === 'PASSWORD_REGEXP') {
        $scope._popMsg(messages["login.pw.not.match.char"]);
        return false;
      } else if(response.data.data === 'PASSWORD_NOT_CHANGED') {
        $scope._popMsg(messages["systemEmp.passwordNotChanged.msg"]);
        return false;
      } else {
        $scope._popMsg(messages["cmm.saveFail"]);
        return false;
      }
    });
  };

  // 닫기버튼 클릭
  $scope.close = function(){
    $scope.systemEmpRegistLayer.hide();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 대리점 팝업 핸들러 추가
    $scope.agencyLayer.shown.addHandler(function (s) {
    });
    // // 비밀번호 변경 팝업 핸들러 추가
    // $scope.changePwdLayer.shown.addHandler(function (s) {
    //   setTimeout(function() {
    //     var params = $scope.systemEmpRegistInfo;
    //     params.empFg = 'S'; // 시스템 사원
    //     $scope._broadcast('changePwdCtrl', params);
    //   }, 50);
    // });
  });


}]);
