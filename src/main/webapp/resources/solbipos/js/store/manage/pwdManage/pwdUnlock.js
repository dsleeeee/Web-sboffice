/****************************************************************
 *
 * 파일명 : pwdUnlock.js
 * 설  명 : 로그인 잠금해제 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.03.05     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  사원목록 그리드
 **********************************************************************/
app.controller('pwdUnlockCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pwdUnlockCtrl', $scope, $http, true));


    $scope.pwdUnlock;

    // 팝업 오픈 클릭
    $scope.$on("pwdUnlockCtrl", function(event, data) {
        $scope.$apply(function() {
            $scope.pwdUnlock = data;
        });
        event.preventDefault();
    });

    //
    // 비밀번호 변경
    $scope.unlockPwd = function(){

        var params = $scope.pwdUnlock;

        $scope._postJSONSave.withPopUp( "/store/manage/pwdManage/pwdManage/updatePasswordUnLock.sb", params, function(response){
            // console.log("response", response);
            var result = response.data.data;

            if(result === 'SUCCESS' || result === 'CHECK_OK') { // 성공
                $scope._popMsg(messages["cmm.registSucc"]);
                $scope.closePopup();
            } else { // 저장실패
                $scope._popMsg(messages["cmm.registFail"]);
                return false;
            }
        });
    };

    // 닫을때 초기화 로직 추가
    $scope.closePopup = function(){
        $scope.pwdUnlockPopupLayer.hide();
    };

}]);
