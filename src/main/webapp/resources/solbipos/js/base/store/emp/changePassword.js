/****************************************************************
 *
 * 파일명 : changePassword.js
 * 설  명 : 비밀번호 변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.29     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 사원 비밀번호 변경
 */
app.controller('changePwdCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('changePwdCtrl', $scope, $http, false));

  // 선택된 사원
  $scope.selectedEmp;

  // 사원정보
  $scope.emp;
  $scope.emp = function(emp){
    $scope.emp = emp;
  };
  $scope.emp = function(){
    return $scope.emp;
  };

  // 해당 scope 호출
  $scope.$on("changePwdCtrl", function(event, data) {

    console.log('>> data',data);
    $scope.emp = data;

    event.preventDefault();
  });

  // 비밀번호 변경
  $scope.change = function(){
    // 현재 비밀번호, 변경 비밀번호 확인 체크
    if($scope.emp.currentPwd === $scope.emp.changePwd) {
      $scope._popMsg(messages["emp.passwordNotChanged.msg"] );
      return false;
    }

    // 비밀번호, 비밀번호 확인 체크
    if($scope.emp.userPwd !== $scope.emp.userPwdCfm) {
      $scope._popMsg(messages["emp.result.fail.pwdconfirm"] );
      return false;
    }

    var params      = $scope.emp;
    params.pwdChgFg = true; // 비밀번호 변경여부

    var saveUrl = "";

    console.log('$scope.emp.empFg', $scope.emp.empFg);

    if($scope.emp.empFg === 'S') { // 시스템
      saveUrl = "/base/store/emp/system/modifyPassword.sb";
    } else if($scope.emp.empFg === 'H') { // 본사
      saveUrl = "/base/store/emp/hq/modifyPassword.sb";
    } else if($scope.emp.empFg === 'S') { // 매장
      saveUrl = "/base/store/emp/store/modifyPassword.sb";
    }

    $scope._postJSONSave.withOutPopUp( saveUrl , params, function(response){

      console.log('response', response.data.data);

      if(response.data.data == 'SUCCESS') {
        $scope._popMsg(messages["cmm.registSucc"]);
        $scope.changePwdLayer.hide();
      } else if(response.data.data === 'PASSWORD_NOT_CHANGED'){
        $scope._popMsg(messages["emp.passwordNotChanged.msg"]);
        return false;
      } else if(response.data.data === 'PASSWORD_REGEXP') {
        $scope._popMsg(messages["login.pw.not.match.char"]);
        return false;
      } else {
        $scope._popMsg(messages["emp.result.fail.pwd"]);
        return false;
      }
    });
  };

  // 닫기버튼 클릭
  $scope.close = function(){
    $scope.changePwdLayer.hide();
  };
}]);
