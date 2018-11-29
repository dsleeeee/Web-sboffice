/****************************************************************
 *
 * 파일명 : storeEmpSave.js
 * 설  명 : 매장사원정보 등록 및 수정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.23     김지은      1.0
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
app.controller('storeEmpRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeEmpRegistCtrl', $scope, $http, false));

  // 웹 사용여부
  $scope._setComboData('storeEmpWebUseYnComboData', useYnComboData);
  // 사용여부
  $scope._setComboData('storeEmpUseYnFgComboData', useYnComboData);
  // SMS수신여부
  $scope._getComboDataQuery('072', 'storeEmpSmsRecvYnComboData');
  // 판매상품여부 콤보박스
  $scope._getComboDataQuery('007', 'storeEmpServiceFgComboData');

  // 선택 사원 (사원 수정시)
  $scope.selectedHqEmp;

  // 신규 수정 여부
  $scope.newEmpYn = true;

  // 웹사용자 아이디 중복체크 여부
  $scope.duplicationChkFg = false;

  // 사원정보
  $scope.storeEmpRegistInfo;
  $scope.setStoreEmpRegistInfo = function(emp){
    $scope.storeEmpRegistInfo = emp;
  };
  $scope.getStoreEmpRegistInfo = function(){
    return $scope.storeEmpRegistInfo;
  };

  // 해당 scope 호출
  $scope.$on("storeEmpRegistCtrl", function(event, data) {

    $scope.selectedStoreEmp = data;

    if( isEmptyObject(data)) {

      $scope.storeEmpRegistInfo            = {};
      $scope.storeEmpRegistInfo.webUseYn   = 'Y';
      $scope.storeEmpRegistInfo.smsRecvYn  = 'N';
      $scope.storeEmpRegistInfo.serviceFg  = '1';
      $scope.storeEmpRegistInfo.useYn      = 'Y';
      $scope.newEmpYn                   = true;

    } else {

      $scope.getStoreEmpList();
      $scope.newEmpYn = false;
    }

    event.preventDefault();
  });

  // 매장사원정보관리 그리드 조회
  $scope.getStoreEmpList = function(){

    var params = $scope.selectedStoreEmp;

    $scope._postJSONQuery.withOutPopUp( "/base/store/emp/store/detail.sb", params, function(response){
      $scope.storeEmpRegistInfo                    = response.data.data;
      $scope.storeEmpRegistInfo.empInfo            = ' [' + response.data.data.empNo + ']' + response.data.data.empNm;
      $scope.storeEmpRegistInfo.originalWebUserId  = response.data.data.userId;
    });
  };

  // 아이디 정책 및 중복 체크
  $scope.checkDuplicate = function(){

    if( isEmptyObject($scope.storeEmpRegistInfo.userId)) {
      $scope._popMsg(messages["storeEmp.userId"] + messages["cmm.require.text"] );
      return false;
    }

    var params    = {};
    params.userId = $scope.storeEmpRegistInfo.userId;

    $scope._postJSONQuery.withPopUp( "/base/store/emp/store/chkStoreUserId.sb", params, function(response){

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

  // 비밀번호 변경
  $scope.changePassword = function(){
    $scope.changePwdLayer.show(true);
  };

  // 신규등록
  $scope.regist = function(){

    if($scope.storeEmpRegistInfo.webUseYn === 'Y') {
      // 웹 사용자 아이디 중복체크
      if(!$scope.duplicationChkFg) {
        $scope._popMsg(messages["storeEmp.require.chk.userId"] );
        return false;
      }
      // 비밀번호, 비밀번호 확인 체크
      if($scope.storeEmpRegistInfo.userPwd !== $scope.storeEmpRegistInfo.userPwdCfm) {
        $scope._popMsg(messages["storeEmp.passwordNotMatch.msg"] );
        return false;
      }
    }

    var params = $scope.storeEmpRegistInfo;
    params.pwdChgFg = false; // 비밀번호 변경여부

    $scope._postJSONSave.withOutPopUp( "/base/store/emp/store/regist.sb", params, function(response){

      if(response.data.data == 'SUCCESS') {
        $scope._popMsg(messages["cmm.registSucc"]);
        $scope.storeEmpRegistLayer.hide();
      } else if(response.data.data === 'USER_ID_REGEXP') {
        $scope._popMsg(messages["storeEmp.userIdRegexp.msg"]);
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
    if(($scope.storeEmpRegistInfo.originalWebUserId == '' || $scope.storeEmpRegistInfo.originalWebUserId == undefined)
        && $scope.storeEmpRegistInfo.webUseYn === 'Y') {
      // 웹 사용자 아이디 중복체크
      if(!$scope.duplicationChkFg) {
        $scope._popMsg(messages["storeEmp.require.chk.userId"] );
        return false;
      }
    }

    var params = $scope.storeEmpRegistInfo;

    $scope._postJSONSave.withOutPopUp( "/base/store/emp/store/save.sb", params, function(response){

      if(response.data.data == 'SUCCESS') {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.storeEmpRegistLayer.hide();
      } else if(response.data.data === 'PASSWORD_REGEXP') {
        $scope._popMsg(messages["login.pw.not.match.char"]);
        return false;
      } else if(response.data.data === 'PASSWORD_NOT_CHANGED') {
        $scope._popMsg(messages["storeEmp.passwordNotChanged.msg"]);
        return false;
      } else {
        $scope._popMsg(messages["cmm.saveFail"]);
        return false;
      }
    });
  };

  // 닫기버튼 클릭
  $scope.close = function(){
    $scope.storeEmpRegistLayer.hide();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 비밀번호 변경 팝업 핸들러 추가
    $scope.changePwdLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        var params = $scope.storeEmpRegistInfo;
        params.empFg = 'S'; // 매장 사원
        $scope._broadcast('changePwdCtrl', params);
      }, 50);
    });
  });


}]);
