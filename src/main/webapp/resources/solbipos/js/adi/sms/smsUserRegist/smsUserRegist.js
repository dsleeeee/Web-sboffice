/****************************************************************
 *
 * 파일명 : smsUserRegist.js
 * 설  명 : SMS 사용 등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.31     김유승      1.0
 * 2026.08.13     김유승      2.0            KCP 본인확인 신규 연동방식(V2) 전환
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/*
 * [SMS 사용등록 - KCP 처리 순서]
 * getVerifyVal.sb가 KCP 거래를 등록하고, updateVerify.sb가 결과를 S2S 조회해 콜백을 호출한다.
 */

// KCP 본인인증 결과를 인증 상태와 SMS 사용등록 화면에 반영한다.
// Angular 컨텍스트 안·밖에서 모두 호출될 수 있어 digest 상태를 확인한다.
// result: success, fail, duplicate, error(저장오류) / data: 상태별 부가정보
window.smsUserRegistVerifyCallback = function (result, data) {
    var scope = angular.element(document.getElementById('smsUserRegistView')).scope();
    data = data || {};

    if (scope.finishKcpAuthPopup) {
        // 콜백을 마친 KCP 팝업 상태·감시 타이머·인증 폼을 정리한다.
        scope.finishKcpAuthPopup(false);
    }

    var applyFn = function () {
        if (result === 'success') {
            scope.verifyState = 'success';
            scope.verifiedInfo = {
                userNm: data.userNm || '',
                telNo: data.telNo || '',
                verifyDt: data.verifyDt || getCurDateTime()
            };
            scope.goStep(3);
        } else if (result === 'duplicate') {
            scope.verifyState = 'duplicate';
            scope.dupUserId = data.dupUserId || '';
        } else {
            // fail, error 공통 - 본인인증 실패 화면으로
            scope.verifyState = 'fail';
            scope.verifyFailReason = data.reason || '';
        }
    };

    if (scope.$root.$$phase) {
        // 콜백 결과를 현재 Angular 화면에 반영한다.
        applyFn();
    } else {
        // Angular digest를 시작하며 콜백 결과를 화면에 반영한다.
        scope.$apply(applyFn);
    }
};

app.controller('smsUserRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsUserRegistCtrl', $scope, $http, $timeout, false));

    // 화면(단계) 상태 : 1-동의, 2-본인인증/등록, 3-완료
    $scope.step = 1;

    // 계정 정보
    $scope.userId = suUserId;
    $scope.userNm = suUserNm;

    // 약관 동의 상태
    $scope.consent = { c1: false, c2: false };
    $scope.detailOpen = { c1: false, c2: false };

    // 본인인증 상태 : before, loading, success, fail, duplicate
    $scope.verifyState = 'before';
    $scope.verifyFailReason = "";
    $scope.dupUserId = "";
    $scope.verifiedInfo = { userNm: "", telNo: "", verifyDt: "" };
    $scope.kcpAuthPopup = null;
    $scope.kcpAuthPopupName = "";
    $scope.kcpAuthPopupTimer = null;

    // KCP 인증 팝업·감시 타이머·인증 폼을 정리한다.
    // closePopup이 true이면 열린 인증 팝업도 닫는다.
    $scope.finishKcpAuthPopup = function (closePopup) {
        if ($scope.kcpAuthPopupTimer) {
            clearInterval($scope.kcpAuthPopupTimer);
            $scope.kcpAuthPopupTimer = null;
        }

        if (closePopup && $scope.kcpAuthPopup && !$scope.kcpAuthPopup.closed) {
            $scope.kcpAuthPopup.close();
        }

        $scope.kcpAuthPopup = null;
        $scope.kcpAuthPopupName = "";

        var authForm = document.getElementById("smsUserRegistKcpAuthForm");
        if (authForm) {
            authForm.reset();
            authForm.removeAttribute("action");
            authForm.removeAttribute("target");
        }
    };

    // KCP 인증 준비 실패 시 자원과 화면 상태를 초기화하고 안내 메시지를 표시한다.
    function failKcpAuth(message) {
        // 실패한 KCP 팝업·감시 타이머·인증 폼을 정리한다.
        $scope.finishKcpAuthPopup(true);
        $scope.verifyState = 'before';
        if (message) {
            $scope._popMsg(message);
        }
    }

    // 약관 동의 토글
    $scope.toggleConsent = function (key) {
        $scope.consent[key] = !$scope.consent[key];
    };

    // 전체 동의 토글
    $scope.toggleAllConsent = function () {
        var next = !$scope.isAllAgreed();
        $scope.consent.c1 = next;
        $scope.consent.c2 = next;
    };

    // 필수 약관 모두 동의했는지
    $scope.isAllAgreed = function () {
        return $scope.consent.c1 && $scope.consent.c2;
    };

    // 약관 상세 펼치기/접기
    $scope.toggleDetail = function (key) {
        $scope.detailOpen[key] = !$scope.detailOpen[key];
    };

    // 단계 이동
    $scope.goStep = function (n) {
        $scope.step = n;
    };

    // 약관 동의를 확인한 뒤 KCP 본인인증 거래를 등록하고 인증 팝업을 시작한다.
    // 인증 결과는 smsUserRegistVerifyCallback()이 화면에 반영한다.
    $scope.runVerify = function () {
        if(!$scope.consent.c1 || !$scope.consent.c2){
            $scope._popMsg(messages["smsUserRegist.chkAgreeYn"]); // 약관 동의여부를 다시 확인해 주십시오.
            $scope.verifyState = 'before';
            return false;
        }

        $scope.verifyState = 'loading';

        var width = 410;
        var height = 500;
        var leftpos = screen.width / 2 - (width / 2);
        var toppos = screen.height / 2 - (height / 2);
        var winopts = "width=" + width + ", height=" + height + ", toolbar=no,status=no,statusbar=no,menubar=no,scrollbars=no,resizable=no";
        var position = ",left=" + leftpos + ", top=" + toppos;

        // 새 인증 요청 전에 남아 있는 KCP 인증 자원을 정리한다.
        $scope.finishKcpAuthPopup(true);
        $scope.kcpAuthPopupName = "smsUserRegistKcpAuth_" + new Date().getTime();
        $scope.kcpAuthPopup = window.open("about:blank", $scope.kcpAuthPopupName, winopts + position);

        if (!$scope.kcpAuthPopup) {
            // 선오픈 팝업 실패를 처리하고 인증 상태를 되돌린다.
            failKcpAuth("팝업이 차단되었습니다. 팝업 허용 후 다시 시도해주세요.");
            return false;
        }

        // 인증창을 X로 닫아 updateVerify.sb가 smsUserRegistVerifyCallback()을 호출하지 못하면 kcpAuthPopupTimer가 로딩 상태와 폼을 원복한다.
        $scope.kcpAuthPopupTimer = setInterval(function () {
            if (!$scope.kcpAuthPopup || $scope.kcpAuthPopup.closed) {
                // 콜백 없이 닫힌 팝업의 감시 타이머와 인증 폼을 정리한다.
                $scope.finishKcpAuthPopup(false);
                if ($scope.verifyState === 'loading') {
                    $scope.$evalAsync(function () {
                        $scope.verifyState = 'before';
                    });
                }
            }
        }, 500);

        var params = {};
        params.agree1Yn = $scope.consent.c1 ? 'Y' : 'N';
        params.agree2Yn = $scope.consent.c2 ? 'Y' : 'N';

        var verifyRequestHandled = false;
        // KCP 거래를 등록하고 인증 폼 제출값을 요청한다.
        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsUserRegist/smsUserRegist/getVerifyVal.sb', params, function (response) {
            verifyRequestHandled = true;
            var data = response.data.data;

            if (!data || data.error || !data.callUrl || !data.regCertKey) {
                // 거래등록 응답 오류를 KCP 인증 실패 상태로 초기화한다.
                failKcpAuth((data && data.error) || "본인확인 요청 준비 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
                return;
            }

            if (!$scope.kcpAuthPopup || $scope.kcpAuthPopup.closed) {
                // 닫힌 팝업을 KCP 인증 실패 상태로 초기화한다.
                failKcpAuth(null);
                return;
            }

            var authForm = document.getElementById("smsUserRegistKcpAuthForm");
            if (!authForm) {
                // 인증 폼 초기화 오류를 KCP 인증 실패 상태로 처리한다.
                failKcpAuth("본인확인 요청 화면을 초기화하지 못했습니다. 화면을 새로고침 후 다시 시도해주세요.");
                return;
            }

            // #smsUserRegistKcpAuthForm에는 reg_cert_key/kcp_page_submit_yn만 넣고 Ret_URL·ordr_idxx·enc_cert_data2는 넣지 않는다.
            authForm.elements["reg_cert_key"].value = data.regCertKey;
            authForm.elements["kcp_page_submit_yn"].value = data.kcpPageSubmitYn || "N";
            // getVerifyVal.sb의 callUrl은 #smsUserRegistKcpAuthForm.action에만 지정한다.
            authForm.action = data.callUrl;
            authForm.target = $scope.kcpAuthPopupName;
            // KCP 인증 폼을 선오픈한 팝업으로 제출한다.
            authForm.submit();
        }, function () {
            verifyRequestHandled = true;
            // KCP 거래등록 요청 실패를 인증 전 상태로 초기화한다.
            failKcpAuth("본인확인 요청 준비 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
        }, function () {
            if (!verifyRequestHandled) {
                // 미처리 요청 종료를 KCP 인증 실패 상태로 초기화한다.
                failKcpAuth("본인확인 요청 준비 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    };

    // 인증 자원을 정리하고 본인인증 전 상태로 돌린다.
    $scope.resetVerify = function () {
        // 재시도 전에 KCP 팝업·감시 타이머·인증 폼을 정리한다.
        $scope.finishKcpAuthPopup(true);
        $scope.verifyState = 'before';
    };

    // SMS 사용 등록 삭제
    $scope.deleteRegist = function () {

        var params = {};

        $scope._popConfirm("SMS 사용 등록을 삭제할까요?\n삭제하시면 현재 등록되어 있는 발신번호도 함께 모두 삭제됩니다.", function () {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/smsUserRegist/smsUserRegist/deleteUserRegist.sb", params, function(){
                $scope._popMsg("삭제되었습니다.");
                $scope.verifyState = 'before';
                $scope.verifiedInfo = { userNm: "", telNo: "", verifyDt: "" };
                $scope.goStep(1);
            });
        });
    };

    // 진입시 체크 - 등록정보 있으면 바로 완료화면
    $scope._postJSONQuery.withOutPopUp('/adi/sms/smsUserRegist/smsUserRegist/getUserRegistInfo.sb', {}, function (response) {
        var data = response.data.data;

        if (data && data.userId) {
            $scope.verifyState = 'success';
            $scope.verifiedInfo = {
                userNm: $scope.userNm,
                telNo: data.telNo || '',
                verifyDt: data.regDt || ''
            };
            $scope.goStep(3);
        }
    });

}]);
