/****************************************************************
 *
 * 파일명 : marketingSmsSendVfc.js
 * 설  명 : 마케팅 SMS 전송 전 추가인증 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.10     김유승      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('marketingSmsSendVfcCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('marketingSmsSendVfcCtrl', $scope, $http, false));

    var reserveYn = null;            // 인증 성공 후 이어서 실행할 구분(0: 즉시전송, 1: 예약전송)
    var sendInProgress = false;      // C20 중복 요청을 막는 처리 상태
    var verifyInProgress = false;    // C21 중복 요청을 막는 처리 상태
    var codeSent = false;            // 최초 발송과 재발송의 버튼 문구를 구분하는 발송 여부
    var codeExpired = false;         // 발송 후 3분이 지나 C21 요청을 차단하는 상태
    var authLocked = false;          // C20/C21 코드 03 발생 후 팝업 내 추가 요청을 차단하는 상태
    var expireTimer = null;          // 인증번호 유효시간 3분 표시용 타이머
    var resendTimer = null;          // 인증번호 재전송 제한시간 1분 표시용 타이머

    // 메인 마케팅 SMS 컨트롤러의 전송/예약 클릭 시 팝업 열기 이벤트
    $scope.$on('openMarketingSmsSendVfc', function(event, value) {
        $scope.open(value);
        event.preventDefault();
    });

    /** 전송/예약 클릭 시 추가인증 팝업을 초기화하여 표시한다. */
    $scope.open = function(value) {
        if (value !== "0" && value !== "1") {
            $scope._popMsg("SMS 전송구분을 확인할 수 없습니다.");
            return;
        }

        reserveYn = value;
        // 이전 인증번호·타이머·버튼 상태 초기화 후 새 전송구분으로 팝업 표시
        resetPopup();
        $scope.wjMarketingSmsSendVfcLayer.show(true);
        setTimeout(function() {
            $("#marketingSmsSendVfcNo").focus();
        }, 50);
    };

    /** C20 함수로 추가 인증번호를 요청한다. */
    $scope.requestCode = function() {
        if (sendInProgress || authLocked) {
            return;
        }

        sendInProgress = true;
        $("#marketingSmsSendVfcSendBtn").prop("disabled", true);
        // 새 C20 요청 전 이전 안내 메시지 제거
        clearMessage();

        var request = $.postJSON("/adi/sms/smsSend/smsSend/requestMarketingSmsVfcCode.sb", {reserveYn: reserveYn}, function(result) {
            if (!result || result.status !== "OK" || !result.data) {
                // 정상 응답 데이터 누락 시 공통 발송 오류 메시지 표시
                showMessage("추가 인증번호 전송 중 오류가 발생했습니다.", true);
                return;
            }

            var data = result.data;
            // C20 결과 메시지를 실제 발송 여부에 맞는 성공·오류 스타일로 표시
            showMessage(data.message, !data.sent);

            // C20 코드 00·02 발송 시 서버 발송시각 기준 타이머 시작
            if (data.requested) {
                codeSent = true;
                codeExpired = false;
                $("#marketingSmsSendVfcNo").val("").removeClass("error").focus();
                // 서버 반환 인증 유효시간·재전송 제한시간으로 두 타이머 시작
                startTimers(data.expireSeconds, data.resendSeconds);
            } else if ((parseInt(data.resendSeconds, 10) || 0) > 0) {
                // 다른 전송구분의 인증번호 사용 차단 및 DB 재전송 제한시간 유지
                // 현재 용도의 인증번호가 없어도 남은 재전송 제한시간을 버튼에 표시
                startResendTimer(data.resendSeconds);
            } else {
                // 재전송 제한이 없으면 인증번호 발송 버튼 활성화
                resetSendButton();
            }

            if (data.code === "03") {
                // 요청 제한 코드이면 현재 팝업의 발송·검증 버튼 잠금
                lockPopup();
            }
        }, function() {
            // C20 통신 실패 메시지 표시 및 발송 버튼 복구
            showMessage("추가 인증번호 전송 중 오류가 발생했습니다.", true);
            resetSendButton();
        });

        request.always(function() {
            sendInProgress = false;
            if (resendTimer === null && !authLocked) {
                // 재전송 타이머·잠금이 없으면 C20 요청 종료 후 발송 버튼 복구
                resetSendButton();
            }
        });
    };

    /** C21 함수로 사용자가 입력한 추가 인증번호를 검증한다. */
    $scope.verifyCode = function() {
        var smsVfcNo = $.trim($("#marketingSmsSendVfcNo").val());
        if (codeExpired) {
            // 화면 타이머 만료 시 C21 호출 차단 및 재요청 안내
            showMessage("추가 인증번호 입력시간이 초과되었습니다. 인증번호를 다시 요청해주세요.", true);
            return;
        }
        if (!/^\d{6}$/.test(smsVfcNo)) {
            // 숫자 6자리 형식 오류 시 C21 호출 차단 및 입력 오류 표시
            showMessage("추가 인증번호 6자리를 입력해주세요.", true);
            $("#marketingSmsSendVfcNo").addClass("error").focus();
            return;
        }
        if (verifyInProgress || authLocked) {
            return;
        }

        verifyInProgress = true;
        $("#marketingSmsSendVfcConfirmBtn").prop("disabled", true);
        // 새 C21 요청 전 이전 안내 메시지 제거
        clearMessage();

        var request = $.postJSON("/adi/sms/smsSend/smsSend/verifyMarketingSmsVfcCode.sb", {
            smsVfcNo: smsVfcNo,
            reserveYn: reserveYn
        }, function(result) {
            if (!result || result.status !== "OK" || !result.data) {
                // 정상 응답 데이터 누락 시 공통 검증 오류 메시지 표시
                showMessage("추가 인증번호 확인 중 오류가 발생했습니다.", true);
                return;
            }

            var data = result.data;
            if (data.verified && data.smsVfcToken) {
                var verifiedReserveYn = reserveYn;
                var smsVfcToken = data.smsVfcToken;
                $scope.close();

                // 인증 완료 후 기존 사용자·잔액·내용·수신자 검증 시작
                var marketingSmsSendScope = agrid.getScope('marketingSmsSendCtrl');
                marketingSmsSendScope.smsSendAfterVfc(verifiedReserveYn, smsVfcToken);
                return;
            }

            if (data.code === "04") {
                codeExpired = true;
            } else if (data.code === "03") {
                // 입력 제한 코드이면 현재 팝업의 발송·검증 버튼 잠금
                lockPopup();
            }
            // C21 실패 사유를 오류 스타일로 표시
            showMessage(data.message, true);
            $("#marketingSmsSendVfcNo").addClass("error").focus().select();
        }, function() {
            // C21 통신 실패 시 공통 검증 오류 메시지 표시
            showMessage("추가 인증번호 확인 중 오류가 발생했습니다.", true);
        });

        request.always(function() {
            verifyInProgress = false;
            if (!authLocked) {
                $("#marketingSmsSendVfcConfirmBtn").prop("disabled", false);
            }
        });
    };

    /** 팝업을 닫고 인증번호를 포함한 화면 상태와 임시 전송구분을 제거한다. */
    $scope.close = function() {
        // 닫힌 팝업의 인증번호·타이머·버튼 상태 초기화
        resetPopup();
        reserveYn = null;
        $scope.wjMarketingSmsSendVfcLayer.hide();
    };

    /** 인증번호 유효시간과 재전송 제한시간을 서버가 반환한 남은 초부터 시작한다. */
    function startTimers(expireSeconds, resendSeconds) {
        // 새 서버 시간 적용 전 기존 인증·재전송 타이머 해제
        clearTimers();

        expireSeconds = Math.max(0, parseInt(expireSeconds, 10) || 0);
        resendSeconds = Math.max(0, parseInt(resendSeconds, 10) || 0);
        codeExpired = expireSeconds <= 0;
        // 보정된 인증 유효시간을 MM:SS 형식으로 표시
        updateRemainTime(expireSeconds);

        if (resendSeconds > 0) {
            // 재전송 제한시간이 남으면 별도 버튼 타이머 시작
            startResendTimer(resendSeconds);
        } else {
            // 재전송 제한 종료 상태이면 발송 버튼 즉시 활성화
            resetSendButton();
        }

        if (expireSeconds > 0) {
            expireTimer = setInterval(function() {
                expireSeconds--;
                // 1초마다 감소한 인증 유효시간 화면 반영
                updateRemainTime(expireSeconds);
                if (expireSeconds <= 0) {
                    codeExpired = true;
                    clearInterval(expireTimer);
                    expireTimer = null;
                }
            }, 1000);
        }

    }

    /** 유효한 현재 용도의 번호가 없어도 DB의 재전송 제한시간은 버튼에 표시한다. */
    function startResendTimer(resendSeconds) {
        resendSeconds = Math.max(0, parseInt(resendSeconds, 10) || 0);
        if (resendTimer !== null) {
            clearInterval(resendTimer);
            resendTimer = null;
        }
        if (resendSeconds <= 0) {
            // 제한시간이 없으면 별도 타이머 없이 발송 버튼 복구
            resetSendButton();
            return;
        }

        // 최초 재전송 제한시간을 발송 버튼에 표시
        updateResendButton(resendSeconds);
        resendTimer = setInterval(function() {
            resendSeconds--;
            if (resendSeconds <= 0) {
                clearInterval(resendTimer);
                resendTimer = null;
                // 재전송 제한 종료 시 발송 버튼 활성화
                resetSendButton();
                return;
            }
            // 1초마다 감소한 재전송 제한시간을 발송 버튼에 반영
            updateResendButton(resendSeconds);
        }, 1000);
    }

    function updateRemainTime(seconds) {
        var minutes = Math.floor(seconds / 60);
        var remainSeconds = seconds % 60;
        $("#marketingSmsSendVfcRemainTime").text((minutes < 10 ? "0" : "") + minutes + ":" + (remainSeconds < 10 ? "0" : "") + remainSeconds);
    }

    function updateResendButton(seconds) {
        $("#marketingSmsSendVfcSendBtn").prop("disabled", true).text("재전송 (" + seconds + "초)");
    }

    function resetSendButton() {
        var buttonText = codeSent ? "인증번호 다시 받기" : "인증번호 받기";
        $("#marketingSmsSendVfcSendBtn").prop("disabled", authLocked).text(buttonText);
    }

    function lockPopup() {
        authLocked = true;
        // 잠금 상태에서 두 타이머 해제
        clearTimers();
        $("#marketingSmsSendVfcSendBtn, #marketingSmsSendVfcConfirmBtn").prop("disabled", true);
    }

    function clearTimers() {
        if (expireTimer !== null) {
            clearInterval(expireTimer);
            expireTimer = null;
        }
        if (resendTimer !== null) {
            clearInterval(resendTimer);
            resendTimer = null;
        }
    }

    function resetPopup() {
        // 팝업 재오픈 전 이전 인증·재전송 타이머 해제
        clearTimers();
        sendInProgress = false;
        verifyInProgress = false;
        codeSent = false;
        codeExpired = false;
        authLocked = false;
        $("#marketingSmsSendVfcNo").val("").removeClass("error");
        $("#marketingSmsSendVfcRemainTime").text("03:00");
        $("#marketingSmsSendVfcConfirmBtn").prop("disabled", false);
        // 이전 C20/C21 안내 메시지 제거
        clearMessage();
        // 초기 발송 이력·잠금 상태에 맞게 발송 버튼 복구
        resetSendButton();
    }

    function showMessage(message, isError) {
        $("#marketingSmsSendVfcMessage")
            .toggleClass("error", isError === true)
            .addClass("show")
            // DB 메시지의 문자열 개행을 실제 개행으로 변환 후 표시
            .text(normalizeMessage(message));
    }

    function clearMessage() {
        $("#marketingSmsSendVfcMessage").removeClass("show error").text("");
    }

    function normalizeMessage(message) {
        return (message || "").replace(/\\n/g, "\n");
    }

    $("#marketingSmsSendVfcNo").on("input", function() {
        this.value = this.value.replace(/\D/g, "");
        $(this).removeClass("error");
    }).on("keydown", function(event) {
        if (event.keyCode === 13) {
            $scope.verifyCode();
        }
    });
}]);
