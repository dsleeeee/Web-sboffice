/****************************************************************
 *
 * 파일명 : userWebSessionDelPw.js
 * 설  명 : 세션 삭제 비밀번호 확인 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.03.31     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  비밀번호 확인 팝업
 */
app.controller('userWebSessionDelPwCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('userWebSessionDelPwCtrl', $scope, $http, true));

    // 선택 사용자
    $scope.selectedUser;
    $scope.setSelectedUser = function(user) {
        $scope.selectedUser = user;
    };
    $scope.getSelectedUser = function(){
        return $scope.selectedUser;
    };

    $scope.$on("userWebSessionDelPwCtrl", function(event, data) {
        event.preventDefault();
    });

    // 확인
    $scope.confirm = function () {

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        var params = $scope.selectedUser;
        params.password = $("#addDelPw").val();

        if($scope.selectedUser.procFg === "sessionId"){
            $scope.deleteSession(params);
        }else if($scope.selectedUser.procFg === "pause"){
            $scope.pauseUserId(params);
        }else if($scope.selectedUser.procFg === "resume"){
            $scope.resumeUserId(params);
        }

    };
    
    // 세션 삭제
    $scope.deleteSession = function (params) {
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/sys/stats/userWebHist/userWebHist/getDeleteSession.sb", params, function (response) {
            var result = response.data.data;
            $("#addDelPw").val('');

            if(result === "true"){
                $scope._popMsg(messages["userWebHist.msg.succDelSession"]); // 세션ID가 삭제되었습니다.
                $scope.userWebSessionDelPwLayer.hide(true);
            }else if(result === "chkPw"){
                $scope._popMsg(messages["userWebHist.msg.chkDelPw"]); // 비밀번호를 다시 확인해주세요.
            }else if(result === "delFail"){
                $scope._popMsg(messages["userWebHist.msg.failDelSession"]); // 세션 삭제를 실패하였습니다.
            }else if(result === "delPrev"){
                $scope._popMsg(messages["userWebHist.msg.prevDelSession"]); // 이미 삭제된 세션입니다.
            }

            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        });
    }

    // 사용자 일시정지
    $scope.pauseUserId = function (params) {

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/sys/stats/userWebHist/userWebHist/getPauseUserId.sb", params, function (response) {
            var result = response.data.data;
            $("#addDelPw").val('');

            if(result > 0){
                $scope._popMsg(messages["userWebHist.msg.succPauseId"]); // 일시정지 처리되었습니다.
                $scope.userWebSessionDelPwLayer.hide(true);
            }else if(result < 0) {
                $scope._popMsg(messages["userWebHist.msg.chkDelPw"]); // 비밀번호를 다시 확인해주세요.
            }else {
                $scope._popMsg(messages["userWebHist.msg.failPauseId"]); // 일시정지 처리에 실패하였습니다.
            }

            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        });
        
    }

    // 사용자 일시정지 해제
    $scope.resumeUserId = function (params) {

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/sys/stats/userWebHist/userWebHist/getResumeUserId.sb", params, function (response) {
            var result = response.data.data;
            $("#addDelPw").val('');

            if(result > 0){
                $scope._popMsg(messages["userWebHist.msg.succResumeId"]); // 일시정지 처리되었습니다.
                $scope.userWebSessionDelPwLayer.hide(true);
            }else if(result < 0) {
                $scope._popMsg(messages["userWebHist.msg.chkDelPw"]); // 비밀번호를 다시 확인해주세요.
            }else {
                $scope._popMsg(messages["userWebHist.msg.failResumeId"]); // 일시정지 처리에 실패하였습니다.
            }

            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        });
    }

    // 팝업 close
    $scope.cancel = function () {
        $scope.userWebSessionDelPwLayer.hide(true);
        $("#addDelPw").val('');
    };

    // 팝업 close
    $scope.closeUserWebSessionDelPw = function (){
        $scope.userWebSessionDelPwLayer.hide(true);
        $("#addDelPw").val('');
    }

}]);