/****************************************************************
 *
 * 파일명 : pwdManage.js
 * 설  명 : 비밀번호 임의변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 * 2018.12.22     김지은      1.0           Angular 로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var pwdChgFgDataMap = [
  {"name":"웹(WEB) 비밀번호","value":"WEB"},
  {"name":"포스(POS) 비밀번호","value":"POS"}
  ];

/**********************************************************************
 *  사원목록 그리드
 **********************************************************************/
app.controller('pwdChangeCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('pwdChangeCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData("pwdChgFgDataMap", pwdChgFgDataMap);

  $scope.pwdChange;

  // 팝업 오픈 클릭
  $scope.$on("pwdChangeCtrl", function(event, data) {
    $scope.$apply(function() {
      $scope.pwdChange = data;

      // 입력값 초기화
      $scope.layerPwdChgFgCombo.selectedIndex = 0;
      $scope.pwdChange.newPassword = "";
      $scope.pwdChange.confirmPassword = "";

      // 매장 ID만 WEB/포스 비번 변경 가능, 나머지 권한은 WEB 비번만 변경가능
      if($scope.pwdChange.empOrgnFg === 'S' ) {
        $scope.layerPwdChgFgCombo.isReadOnly = false;
      }else{
        $scope.layerPwdChgFgCombo.isReadOnly = true;
      }
    });
    event.preventDefault();
  });

  // 비밀번호 구분 변경 이벤트
  $scope.changePwdChgFg = function(s, e){
    if(s.selectedValue === 'WEB') {
      $('#layerNewPassword, #layerConfirmPassword').attr('maxlength','16');
      /*$('#layerNewPassword, #layerConfirmPassword').removeAttr('keyup');*/
    } else {
      $('#layerNewPassword, #layerConfirmPassword').attr('maxlength','4');
      /*$('#layerNewPassword, #layerConfirmPassword').on('keyup', function() {
        $(this).val($(this).val().replace(/[^0-9]/g,''));
      });*/
    }
    $('#layerNewPassword').val('');
    $('#layerConfirmPassword').val('');
  };

  // 비밀번호 변경
  $scope.modifyPwd = function(){

    // 새 비밀번호를 입력하세요.
    if($("#layerNewPassword").val() === ""){
      $scope._popMsg(messages["pwdManage.layer.newPassword"] + messages["cmm.require.text"]);
      return;
    }

    // 새 비밀번호 확인을 입력하세요.
    if($("#layerConfirmPassword").val() === ""){
      $scope._popMsg(messages["pwdManage.layer.confirmPassword"] + messages["cmm.require.text"]);
      return;
    }

    // 포스(POS) 비밀번호는 숫자 4자리만 허용됩니다.
    if($scope.pwdChange.pwdChgFg === "POS"){
      var msg = messages["pwdManage.layer.msg"];
      var numChkregexp = /[^0-9]/g;
      if(numChkregexp.test( $scope.pwdChange.newPassword)) {
        $scope._popMsg(msg);
        return false;
      }
    }

    var params = $scope.pwdChange;

    $scope._postJSONSave.withPopUp( "/store/manage/pwdManage/pwdManage/modify.sb", params, function(response){
      // console.log("response", response);
      var result = response.data.data;

      if(result === 'SUCCESS' || result === 'CHECK_OK') { // 성공
        $scope._popMsg(messages["cmm.registSucc"]);
        $scope.closePopup();
      } else if(result === 'NEW_PASSWORD_NOT_MATCH') { // 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
        $scope._popMsg(messages["login.pw.find.not.match"]);
        return false;
      } else if(result === 'PASSWORD_NEW_OLD_MATH') { // 변경 패스워드가 기존 비밀번호가 같은지 체크
        $scope._popMsg(messages["login.layer.pwchg.current"]);
        return false;
      } else if(result === 'PASSWORD_NOT_MATCH_LENGTH') { // 비밀번호는 최소 6자 이상 20자 이하만 가능
        $scope._popMsg(messages["login.pw.not.match.length"]);
        return false;
      } else if(result === 'PASSWORD_NOT_MATCH_CHAR') { // 비밀번호는 숫자와 영문, 특수문자(!,@,$,~)만 사용 가능
        $scope._popMsg(messages["login.pw.not.match.char"]);
        return false;
      } else if(result === 'PASSWORD_NOT_CONTAIN_NUMBER') { // 비밀번호는 반드시 숫자가 포함
        $scope._popMsg(messages["login.pw.not.contain.number"]);
        return false;
      } else if(result === 'PASSWORD_NOT_CONTAIN_ENG_CHAR') { // 비밀번호는 영문자가 반드시 포함
        $scope._popMsg(messages["login.pw.not.contain.char"]);
        return false;
      } else if(result === 'PASSWORD_CONTINUED_CHAR') { // 숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용할 수 없습니다.
        $scope._popMsg(messages["login.pw.cannot.be.used.continued.char"]);
        return false;
      } else if(result === 'PASSWORD_CAN_NOT_BE_USED_CONTINUED_CHAR') { // 숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용할 수 없습니다.
        $scope._popMsg(messages["login.pw.cannot.be.used.continued.char"]);
        return false;
      } else if(result === 'PASSWORD_SAME_CHAR') { // 동일한 문자 또는 숫자를 3자 이상 사용할 수 없습니다.
        $scope._popMsg(messages["login.pw.cannot.be.used.same.char"]);
        return false;
      } else if(result === 'ID_IS_NULL') { // 사용자 ID가 없는경우
        $scope._popMsg(messages["login.pw.cannot.be.used.id.null"]);
        return false;
      } else { // 저장실패
        $scope._popMsg(messages["cmm.registFail"]);
        return false;
      }
    });
  };

  // 닫을때 초기화 로직 추가
  $scope.closePopup = function(){
      // $scope.$apply(function() {
        $scope.pwdChange = null;
      // });
    $scope.pwdChangePopupLayer.hide();
  };

}]);
