/****************************************************************
 *
 * 파일명 : smsTelNoRegister.js
 * 설  명 : 발신번호 사전등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/*
 * [발신번호 등록 팝업의 KCP 처리]
 * $scope.getVal()이 KCP 거래를 등록하고 telNoRequest()가 인증 폼을 제출한다.
 * 일반번호 CERT_ID 채번은 getVal.sb, 휴대폰 KCP 거래등록은 getKcpVerifyVal.sb가 담당한다.
 */

/**
 *  발신번호 사전등록 팝업 조회 그리드 생성
 */
app.controller('smsTelNoRegisterCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsTelNoRegisterCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("smsTelNoRegisterCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function() {
        // 발신번호 등록됬는지 확인
        var smsTelNoRegisterScope = agrid.getScope('smsSendCtrl');
        smsTelNoRegisterScope.tellNumChkPop();

        $scope.wjSmsTelNoRegisterLayer.hide();
        event.preventDefault();
    };

    // KCP 본인확인 거래를 등록하고 인증 폼에 필요한 값을 설정한다.
    $scope.getVal = function() {
        var authForm = document.getElementById("smsTelNoRegisterKcpAuthForm");
        if (!authForm) {
            $scope._popMsg("본인확인 요청 화면을 초기화하지 못했습니다. 화면을 새로고침 후 다시 시도해주세요.");
            return;
        }

        // $scope.getVal() 재호출 전 #smsTelNoRegisterKcpAuthForm과 #smsTelNoRegisterOrdrIdxx를 초기화한다.
        authForm.reset();
        authForm.removeAttribute("action");
        authForm.removeAttribute("target");
        delete authForm.dataset.callUrl;
        $("#smsTelNoRegisterOrdrIdxx").val("");

        var requestHandled = false;
        // KCP 거래를 등록하고 인증 폼 제출값을 요청한다.
        var request = $.postJSON("/adi/sms/smsTelNoManage/smsTelNoManage/getKcpVerifyVal.sb", null, function(result) {
                requestHandled = true;
                var data = result.data;

                if (!data || data.error || !data.callUrl || !data.regCertKey || !data.ordrIdxx) {
                    authForm.reset();
                    authForm.removeAttribute("action");
                    authForm.removeAttribute("target");
                    delete authForm.dataset.callUrl;
                    $("#smsTelNoRegisterOrdrIdxx").val("");
                    $scope._popMsg((data && data.error) || "본인확인 요청 준비 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
                    return;
                }

                // callUrl은 dataset에 보관하고 인증 폼에는 KCP 제출 필드만 채운다.
                authForm.dataset.callUrl = data.callUrl;
                authForm.elements["reg_cert_key"].value = data.regCertKey;
                authForm.elements["kcp_page_submit_yn"].value = data.kcpPageSubmitYn || "N";
                // ordrIdxx는 getSmsTelNoManageSave.sb의 CERT_ID로만 사용한다.
                $("#smsTelNoRegisterOrdrIdxx").val(data.ordrIdxx);
            },
            function (result) {
                requestHandled = true;
                authForm.reset();
                authForm.removeAttribute("action");
                authForm.removeAttribute("target");
                $("#smsTelNoRegisterOrdrIdxx").val("");
                $scope._popMsg(result.message);
            }
        );

        request.always(function () {
            if (!requestHandled) {
                authForm.reset();
                authForm.removeAttribute("action");
                authForm.removeAttribute("target");
                $("#smsTelNoRegisterOrdrIdxx").val("");
            }
        });
    };

    // 일반번호 서류인증 신청 팝업을 연다.
    $scope.smsGeneralNoRequest = function() {
        $scope.wjSmsGeneralNoRegisterLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 일반번호 인증요청 팝업 핸들러 추가
        $scope.wjSmsGeneralNoRegisterLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsGeneralNoRegisterCtrl', null);
            }, 50)
        });
    });
}]);
