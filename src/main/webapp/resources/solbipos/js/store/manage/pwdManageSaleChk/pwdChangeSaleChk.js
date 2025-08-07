/****************************************************************
 *
 * 파일명 : pwdManageSaleChk.js
 * 설  명 : 매출조회 비밀번호 관리 > 매출조회 설정 변경 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.05     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  매출조회 설정 변경 그리드
 **********************************************************************/
app.controller('pwdChangeSaleChkCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pwdChangeSaleChkCtrl', $scope, $http, true));

    $scope.pwdChange;

    // 팝업 오픈 클릭
    $scope.$on("pwdChangeSaleChkCtrl", function(event, data) {
        $scope.$apply(function() {
            $scope.pwdChange = data;

            // 입력값 초기화
            $scope.pwdChange.password = "";
            $scope.pwdChange.newPassword = "";
            $scope.pwdChange.confirmPassword = "";
            if(data.salePwdYn !== null && data.salePwdYn === 'Y'){
                $('#salePwdYnY').prop('checked', true);
            }else{
                $('#salePwdYnN').prop('checked', true);
            }
        });
        event.preventDefault();
    });

    // 비밀번호 변경
    $scope.modifySalePwd = function(){

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

        var params = $scope.pwdChange;
        if($scope.pwdChange.salePwd === null || $scope.pwdChange.salePwd === undefined){
            $scope.pwdChange.salePwd = '';
        }

        $scope._postJSONSave.withPopUp( "/store/manage/pwdManageSaleChk/pwdManageSaleChk/getModifySalePwd.sb", params, function(response){
            // console.log("response", response);
            var result = response.data.data;

            if(result === 'SUCCESS' || result === 'CHECK_OK') { // 성공
                $scope._popMsg(messages["cmm.registSucc"]);
                $scope.closePopup();
            } else if(result === 'PASSWORD_NOT_MATCH') { // 기존 비밀번호가 일치하지 않습니다.
                $scope._popMsg(messages["pwdManageSaleChk.layer.not.match"]);
                return false;
            } else if(result === 'NEW_PASSWORD_NOT_MATCH') { // 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
                $scope._popMsg(messages["login.pw.find.not.match"]);
                return false;
            } else if(result === 'PASSWORD_NEW_OLD_MATH') { // 변경 패스워드가 기존 비밀번호가 같은지 체크
                $scope._popMsg(messages["login.layer.pwchg.current"]);
                return false;
            } else if(result === 'ID_IS_NULL') { // 사용자 ID가 없는경우
                $scope._popMsg(messages["login.pw.cannot.be.used.id.null"]);
                return false;
            } else if(result === 'PASSWORD_NOT_MATCH_LENGTH') { // 변경비밀번호 4자리 아닌경우
                $scope._popMsg(messages["pwdManageSaleChk.layer.not.match.length"]);
                return false;
            } else { // 저장실패
                $scope._popMsg(messages["cmm.registFail"] + "<br/>" + messages["login.pw.cannot"]);
                return false;
            }
        });
    };

    // 닫을때 초기화 로직 추가
    $scope.closePopup = function(){
        // $scope.$apply(function() {
        $scope._pageView('pwdManageSaleChkCtrl', $scope.pwdChange.currPage);
        $scope.pwdChange = null;
        // });
        $scope.pwdChangeSaleChkPopupLayer.hide();
    };

    // 패스워드초기화
    $scope.clearSalePwd = function(){
        // 매출조회 비밀번호를 초기화합니다. 진행하시겠습니까?
        var msg = messages["pwdManageSaleChk.pwdManageSaleChk"] + " " + messages["login.pw.msgClearPwd"];
        $scope._popConfirm(msg, function() {

            var params = $scope.pwdChange;
            $scope._postJSONSave.withPopUp( "/store/manage/pwdManageSaleChk/pwdManageSaleChk/getClearSalePwd.sb", params, function(response){
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

        });
    };

}]);
