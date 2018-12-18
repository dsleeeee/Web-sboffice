/****************************************************************
 *
 * 파일명 : systemEmpPwdChange.js
 * 설  명 : 비밀번호 임의변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.22     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  사원목록 그리드
 **********************************************************************/
app.controller('pwdChangeCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('pwdChangeCtrl', $scope, $http, true));

  $scope.pwdChange;

  // 팝업 오픈 클릭
  $scope.$on("pwdChangeCtrl", function(event, data) {
    $scope.$apply(function() {
      $scope.pwdChange = data;
    });
    event.preventDefault();
  });

  // 비밀번호 변경
  $scope.modifyPwd = function(){

    var params = $scope.pwdChange;

    $scope._postJSONSave.withPopUp( "/base/store/emp/pwdManage/systemPwdModify.sb", params, function(response){
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
      } else { // 저장실패
        $scope._popMsg(messages["cmm.registFail"]);
        return false;
      }
    });
  };

  // 닫을때 초기화 로직 추가
  $scope.closePopup = function(){
    $scope.pwdChange = null;
    $scope.pwdChangePopupLayer.hide();
  };

}]);
