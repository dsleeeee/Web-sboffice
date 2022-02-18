/****************************************************************
 *
 * 파일명 : memberDelete.js
 * 설  명 : 회원삭제 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.02.09     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원삭제 팝업생성
 */
app.controller('memberDeleteCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberDeleteCtrl', $scope, $http, true));

    // 강제삭제 셋팅
    $scope.isCheckedForcedDelete = false;
    $("input:checkbox[id='chkForcedDelete']").prop("checked", false);
    $("#forcedDeletePwd").val("");
    $("#forcedDeletePwd").attr("disabled", true);
    $("#forcedDeletePwd").css('background-color', '#F0F0F0');
    $("#trForcedDeletePwd").css("display", "none");

    // 선택회원
    $scope.selectedMember;
    $scope.setSelectedMember = function(member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function(){
        return $scope.selectedMember;
    };

    $scope.$on("memberDeleteCtrl", function(event, data) {

        // 부모창에서 선택한 회원정보 변수에 셋팅
        $scope.setSelectedMember(data);
        event.preventDefault();
    });

    // 선택회원삭제
    $scope.selectMembrDelete = function () {

        if($scope.getSelectedMember().length <= 0) {
            $scope._popMsg(messages["regist.membr.unSelected.msg"]); // 선택한 회원이 없습니다.
            return false;
        }

        // 강제삭제시 비밀번호 체크
        if ($("#chkForcedDelete").is(":checked")) {
            if(document.getElementsByName('sessionId')[0]) {

                if($("#forcedDeletePwd").val() === ""){
                    $scope._popMsg(messages["regist.membr.forcedDeletePwd.msg"]); // 비밀번호를 입력하세요.
                    return false;
                }

                if($("#forcedDeletePwd").val() !== forcedDeleteChkPwd){
                    $scope._popMsg(messages["regist.membr.forcedDeleteChkPwd.msg"]); // 비밀번호가 일치하지 않습니다. 다시 입력하세요.
                    return false;
                }
            }
        }

        // 선택한 회원을 삭제하시겠습니까?
        $scope._popConfirm(messages["regist.membr.selectDelete.msg"], function() {

            var params = $scope.getSelectedMember();

            for (var i = 0; i < params.length; i++) {
                params[i].forcedDeleteYn = $("#chkForcedDelete").is(":checked") ? "Y": "N";
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/membr/info/view/base/selectMemberDelete.sb", params, function () {

                // 부모창 재조회 및 팝업닫기
                var scope = agrid.getScope('memberCtrl');
                scope.getMemberList();
                $scope.wjMemberDeleteLayer.hide(true);
                $scope.closeMemberDelete();
            });
        });
    };

    // 전체회원삭제
    $scope.allMembrDelete = function () {

        // 강제삭제시 비밀번호 체크
        if ($("#chkForcedDelete").is(":checked")) {
            if(document.getElementsByName('sessionId')[0]) {

                if($("#forcedDeletePwd").val() === ""){
                    $scope._popMsg(messages["regist.membr.forcedDeletePwd.msg"]); // 비밀번호를 입력하세요.
                    return false;
                }

                if($("#forcedDeletePwd").val() !== forcedDeleteChkPwd){
                    $scope._popMsg(messages["regist.membr.forcedDeleteChkPwd.msg"]); // 비밀번호가 일치하지 않습니다. 다시 입력하세요.
                    return false;
                }
            }
        }

        // 모든 회원을 삭제하시겠습니까?
        $scope._popConfirm(messages["regist.membr.allDelete.msg"], function() {

            var params = {};
            params.forcedDeleteYn = $("#chkForcedDelete").is(":checked") ? "Y": "N";

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/membr/info/view/base/allMemberDelete.sb", params, function () {

                // 부모창 재조회 및 팝업닫기
                var scope = agrid.getScope('memberCtrl');
                scope.getMemberList();
                $scope.wjMemberDeleteLayer.hide(true);
                $scope.closeMemberDelete();
            });
        });
    };

    // 강제삭제 체크박스
    $scope.isChkForcedDelete = function () {
        if($scope.isCheckedForcedDelete){
            $("#forcedDeletePwd").attr("disabled", false);
            $("#forcedDeletePwd").css('background-color', '#FFFFFF');
            $("#trForcedDeletePwd").css("display", "");
        }else{
            $("#forcedDeletePwd").attr("disabled", true);
            $("#forcedDeletePwd").css('background-color', '#F0F0F0');
            $("#trForcedDeletePwd").css("display", "none");
        }
    };

    // 닫기
    $scope.closeMemberDelete = function () {
        $scope.isCheckedForcedDelete = false;
        $("input:checkbox[id='chkForcedDelete']").prop("checked", false);
        $("#forcedDeletePwd").val("");
        $("#forcedDeletePwd").attr("disabled", true);
        $("#forcedDeletePwd").css('background-color', '#F0F0F0');
        $("#trForcedDeletePwd").css("display", "none");
    };

}]);