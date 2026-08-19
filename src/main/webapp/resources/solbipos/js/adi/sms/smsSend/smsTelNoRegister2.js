/****************************************************************
 *
 * 파일명 : smsTelNoRegister2.js
 * 설  명 : 발신번호 사전등록2 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.06     김설아      1.0
 * 2026.08.13     김설아      2.0            KCP 본인확인 신규 연동방식(V2) 전환
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/*
 * [발신번호추가2 - 휴대폰 본인인증 처리 순서]
 * getVerifyVal2.sb가 KCP 거래를 등록하고, updateVerify2.sb가 결과를 S2S 조회해 콜백을 호출한다.
 */

// KCP 본인인증 결과 메시지를 발신번호 등록 화면에 표시한다.
// Angular 컨텍스트 밖에서 호출되므로 digest 상태를 확인한다.
window.smsTelNoRegister2VerifyCallback = function (message) {
    var scope = angular.element(document.querySelector('[ng-controller="smsTelNoRegister2Ctrl"]')).scope();

    var applyFn = function () {
        if (scope.finishKcpAuthPopup) {
            // 콜백을 마친 KCP 팝업 상태·감시 타이머·인증 폼을 정리한다.
            scope.finishKcpAuthPopup(false);
        }
        scope.verifyInProgress = false;
        scope._popMsg(message);
    };

    if (scope.$root.$$phase) {
        // 콜백 메시지를 현재 Angular 화면에 반영한다.
        applyFn();
    } else {
        // Angular digest를 시작하며 콜백 메시지를 화면에 반영한다.
        scope.$apply(applyFn);
    }
};

/**
 *  발신번호 사전등록2 팝업 조회 그리드 생성
 */
app.controller('smsTelNoRegister2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsTelNoRegister2Ctrl', $scope, $http, false));

    // 처리 동의 안내 체크
    $scope.contentYn = true;
    $scope.verifyInProgress = false;
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

        var authForm = document.getElementById("smsTelNoRegister2KcpAuthForm");
        if (authForm) {
            authForm.reset();
            authForm.removeAttribute("action");
            authForm.removeAttribute("target");
        }
    };

    // Angular digest 상태에 맞춰 본인인증 진행 여부를 반영한다.
    function setVerifyInProgress(value) {
        if ($scope.$root.$$phase) {
            $scope.verifyInProgress = value;
        } else {
            $scope.$evalAsync(function () {
                $scope.verifyInProgress = value;
            });
        }
    }

    // KCP 인증 실패 시 자원과 진행 상태를 초기화하고 안내 메시지를 표시한다.
    function failKcpAuth(message) {
        // 실패한 KCP 팝업·감시 타이머·인증 폼을 정리한다.
        $scope.finishKcpAuthPopup(true);
        setVerifyInProgress(false);
        if (message) {
            $scope._popMsg(message);
        }
    }

    // 팝업 차단을 피하기 위해 빈 KCP 인증창을 미리 열고 닫힘을 감시한다.
    function openKcpAuthPopup() {
        var width = 410;
        var height = 500;
        var leftpos = screen.width / 2 - (width / 2);
        var toppos = screen.height / 2 - (height / 2);
        var winopts = "width=" + width + ", height=" + height + ", toolbar=no,status=no,statusbar=no,menubar=no,scrollbars=no,resizable=no";
        var position = ",left=" + leftpos + ", top=" + toppos;

        // 새 인증창을 열기 전에 남아 있는 KCP 인증 자원을 정리한다.
        $scope.finishKcpAuthPopup(true);
        $scope.kcpAuthPopupName = "smsTelNoRegister2KcpAuth_" + new Date().getTime();
        $scope.kcpAuthPopup = window.open("about:blank", $scope.kcpAuthPopupName, winopts + position);

        if (!$scope.kcpAuthPopup) {
            $scope.kcpAuthPopupName = "";
            return false;
        }

        // smsTelNoRegister2VerifyCallback() 없이 창이 닫히면 kcpAuthPopupTimer가 finishKcpAuthPopup()과 잠금을 정리한다.
        $scope.kcpAuthPopupTimer = setInterval(function () {
            if (!$scope.kcpAuthPopup || $scope.kcpAuthPopup.closed) {
                // 콜백 없이 닫힌 팝업의 감시 타이머와 인증 폼을 정리한다.
                $scope.finishKcpAuthPopup(false);
                setVerifyInProgress(false);
            }
        }, 500);
        return true;
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("smsTelNoRegister2Ctrl", function(event, data) {
        // orgnFg가 HQ 또는 STORE이면 발신번호 유형을 유선번호로 기본 선택
        if(orgnFg == "HQ" || orgnFg == "STORE") {
            $('input[name=radioTelFg][value="1"]').prop('checked', true);
        } else {
            $('input[name=radioTelFg][value="0"]').prop('checked', true);
        }
        $scope.radioTelNoChange();
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 내용보기 (개인정보취급방침)
    $scope.contentPop1 = function() {
        $scope.wjContentPop1Layer.show(true);
        event.preventDefault();
    };

    // 내용보기 (광고 및 스팸 문자 정책)
    $scope.contentPop2 = function() {
        $scope.wjContentPop2Layer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // SMS 개인정보취급방침 팝업 핸들러 추가
        $scope.wjContentPop1Layer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('contentPop1Ctrl', null);
            }, 50)
        });

        // SMS 광고 및 스팸 문자 정책 팝업 핸들러 추가
        $scope.wjContentPop2Layer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('contentPop2Ctrl', null);
            }, 50)
        });
    });

    // 휴대폰번호 (발신번호유형)
    $scope.radioTelNoChange = function() {
        // 서류첨부 오픈
        var telFg = $('input[name=radioTelFg]:checked').val(); // 발신번호 유형 (0:대표자본인, 1:기업명의)
        var addSmsFg = $('input[name=radioAddSmsFg]:checked').val(); // 발신번호 명의자 (0:휴대폰번호, 1:유선번호)

        if(telFg == 0 && addSmsFg == 0) {
            $("#trFile1").css("display", "none");
            $("#trFile2").css("display", "none");
            $("#trFile3").css("display", "none");
            // 첨부파일 초기화
            $scope.clearFile("A");

        } else if(telFg == 0 && addSmsFg == 1) {
            $("#trFile1").css("display", "");
            $("#trFile2").css("display", "");
            $("#trFile3").css("display", "");

        } else if(telFg == 1 && addSmsFg == 0) {
            $("#trFile1").css("display", "");
            $("#trFile2").css("display", "none");
            $("#trFile3").css("display", "none");
            // 첨부파일 초기화
            $scope.clearFile("23");

        } else if(telFg == 1 && addSmsFg == 1) {
            $("#trFile1").css("display", "");
            $("#trFile2").css("display", "");
            $("#trFile3").css("display", "");
        }
    };

    // SMS 사용등록·기존 인증 여부를 확인한 뒤 휴대폰 본인인증을 시작한다.
    $scope.vfTelNo = function(){
        if ($scope.verifyInProgress) {
            return;
        }

        $scope.verifyInProgress = true;
        // 사용자 클릭 시점에 KCP 인증창을 선오픈한다.
        if (!openKcpAuthPopup()) {
            // 선오픈 실패를 KCP 인증 실패 상태로 초기화한다.
            setVerifyInProgress(false);
            $scope._popMsg("팝업이 차단되었습니다. 팝업 허용 후 다시 시도해주세요.");
            return;
        }

        // getUserRegistInfo.sb/getVerifyChk2.sb가 차단하면 failKcpAuth()가 선오픈 창도 닫는다.
        var userInfoHandled = false;
        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsUserRegist/smsUserRegist/getUserRegistInfo.sb', {}, function (response0) {
            userInfoHandled = true;
            var registData = response0.data.data;
            if (!registData || !registData.userId) {
                // SMS 사용 미등록 상태를 KCP 인증 실패 상태로 초기화한다.
                failKcpAuth(messages["smsUserRegist.notRegistAlert"]);
                return;
            }

            if($("#srchCertId").val() == "" || $("#srchCertId").val() == null) {
                // 본인인증
                $scope.verify();

            } else {
                // 본인인증 체크
                var params = {};
                params.certId = $("#srchCertId").val();

                var verifyCheckHandled = false;
                $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsTelNoRegister2/getVerifyChk2.sb', params, function (response) {
                    verifyCheckHandled = true;
                    if (response.data.data.list !== 0) {
                        // 이미 인증된 건의 KCP 팝업·타이머·폼을 정리한다.
                        failKcpAuth(messages["smsTelNoRegister2.verifyChk"]); // 이미 본인인증이 완료되었습니다.
                        return false;

                    } else {
                        // 본인인증
                        $scope.verify();
                    }
                }, function () {
                    verifyCheckHandled = true;
                    // 인증 상태 조회 오류를 KCP 인증 실패 상태로 초기화한다.
                    failKcpAuth("본인확인 상태 조회 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
                }, function () {
                    if (!verifyCheckHandled) {
                        // 미처리 상태 조회 종료를 KCP 인증 실패 상태로 초기화한다.
                        failKcpAuth("본인확인 상태 조회 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
                    }
                });
            }
        }, function () {
            userInfoHandled = true;
            // SMS 사용 등록정보 조회 오류를 KCP 인증 실패 상태로 초기화한다.
            failKcpAuth("SMS 사용 등록정보 조회 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
        }, function () {
            if (!userInfoHandled) {
                // 미처리 등록정보 조회 종료를 KCP 인증 실패 상태로 초기화한다.
                failKcpAuth("SMS 사용 등록정보 조회 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    };

    // KCP 본인인증 거래와 대기 데이터를 등록한 뒤 선오픈한 팝업에 인증 폼을 제출한다.
    $scope.verify = function(){
        if (!$scope.kcpAuthPopup || $scope.kcpAuthPopup.closed) {
            // 닫힌 팝업을 KCP 인증 실패 상태로 초기화한다.
            failKcpAuth(null);
            return;
        }

        var registerHandled = false;
        // MARKETING_VERIFY2 목적의 KCP 거래를 등록하고 인증 폼 제출값을 요청한다.
        var registerRequest = $.postJSON("/adi/sms/marketingSmsSend/marketingSmsSend/getVerifyVal2.sb", null, function(result) {
            registerHandled = true;
            var data = result.data;

            if (!data || data.error || !data.callUrl || !data.regCertKey || !data.ordrIdxx) {
                // 거래등록 응답 오류를 KCP 인증 실패 상태로 초기화한다.
                failKcpAuth((data && data.error) || "본인확인 요청 준비 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
                return;
            }

            if (!$scope.kcpAuthPopup || $scope.kcpAuthPopup.closed) {
                // 닫힌 팝업을 KCP 인증 실패 상태로 초기화한다.
                failKcpAuth(null);
                return;
            }

            var authForm = document.getElementById("smsTelNoRegister2KcpAuthForm");
            if (!authForm) {
                // 인증 폼 초기화 오류를 KCP 인증 실패 상태로 처리한다.
                failKcpAuth("본인확인 요청 화면을 초기화하지 못했습니다. 화면을 새로고침 후 다시 시도해주세요.");
                return;
            }

            // #smsTelNoRegister2KcpAuthForm에는 reg_cert_key/kcp_page_submit_yn만 넣고 ordrIdxx는 saveVerify.sb의 CERT_ID로만 쓴다.
            authForm.elements["reg_cert_key"].value = data.regCertKey;
            authForm.elements["kcp_page_submit_yn"].value = data.kcpPageSubmitYn || "N";

            $("#srchCertId").val(data.ordrIdxx);

            // 저장기능 수행
            var params = {};
            params.certId = data.ordrIdxx;

            var saveHandled = false;
            // KCP 콜백이 갱신할 CERT_ID 대기 데이터를 저장한다.
            var saveRequest = $.postJSONArray("/adi/sms/marketingSmsSend/marketingSmsSend/saveVerify.sb", params, function () {
                    saveHandled = true;
                    if (!$scope.kcpAuthPopup || $scope.kcpAuthPopup.closed) {
                        // 대기 데이터 저장 중 닫힌 팝업을 인증 실패 상태로 초기화한다.
                        failKcpAuth(null);
                        return;
                    }

                    authForm.action = data.callUrl;
                    authForm.target = $scope.kcpAuthPopupName;
                    // 선오픈한 팝업을 KCP 인증창으로 이동시킨다.
                    authForm.submit();
                },
                function (result) {
                    saveHandled = true;
                    // CERT_ID 대기 데이터 저장 실패를 인증 전 상태로 초기화한다.
                    failKcpAuth(result.message);
                });

            saveRequest.always(function () {
                if (!saveHandled) {
                    // 미처리 저장 종료를 KCP 인증 실패 상태로 초기화한다.
                    failKcpAuth(null);
                }
            });
        }, function (result) {
            registerHandled = true;
            // KCP 거래등록 요청 실패를 인증 전 상태로 초기화한다.
            failKcpAuth(result.message);
        });

        registerRequest.always(function () {
            if (!registerHandled) {
                // 미처리 요청 종료를 KCP 인증 실패 상태로 초기화한다.
                failKcpAuth(null);
            }
        });
    };

    // <-- 저장 -->
    // 서류인증신청
    $("#funcSaveSmsTelNoRegister2").click(function(e) {
        // 처리 동의 안내 체크
        if($scope.contentYn == false) {
            $scope._popMsg(messages["smsTelNoRegister2.contentYnChkAlert"]); // '스팸 규제 및 개인정보 처리 동의 안내' 체크를 해주세요.
            return false;
        }

        // 신청자 이름
        if($scope.addSmsUserNm == "" || $scope.addSmsUserNm == null) {
            $scope._popMsg(messages["smsTelNoRegister2.addSmsUserNmBlankAlert"]); // 신청자 이름을 입력해주세요.
            return false;
        }

        // 신청자 연락처
        if($scope.addSmsTelNo == "" || $scope.addSmsTelNo == null) {
            $scope._popMsg(messages["smsTelNoRegister2.addSmsTelNoBlankAlert"]); // 신청자 연락처를 입력해주세요.
            return false;
        }

        // 본인인증 체크
        var params = {};
        params.certId = $("#srchCertId").val();

        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsTelNoRegister2/getVerifyChk2.sb', params, function (response) {
            // if (response.data.data.list !== 0) {
                // SMS사용등록 여부 + DI 재확인
                $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsTelNoRegister2/getUserRegistDiChk.sb', { certId: params.certId }, function (response2) {
                    var chkResult = response2.data.data;

                    if (chkResult === 'OK') {
                        // 번호 수량 체크
                        $scope.chkRegInfoCnt();
                    } else if (chkResult === 'NOT_REGIST') {
                        $scope._popMsg(messages["smsUserRegist.notRegistAlert"]);
                    } else {
                        $scope._popMsg(messages["smsUserRegist.diMismatchAlert"]);
                    }
                });

            // } else {
            //     $scope._popMsg(messages["smsTelNoRegister2.vfTelNoBlankAlert"]); // 휴대폰 본인인증을 해주세요.
            //     return false;
            // }
        });
    });

    // 번호 수량 체크
    $scope.chkRegInfoCnt = function () {

        var params = {};
        params.telFg = $('input[name=radioTelFg]:checked').val(); // 발신번호 유형 (0:휴대폰번호, 1:유선번호)

        $scope._postJSONQuery.withPopUp('/adi/sms/smsSend/smsTelNoRegister2/getChkRegInfoCnt.sb', params, function (response) {
            // 첨부파일 체크
            $scope.fileChk();
        });
    }

    // 첨부파일 체크
    $scope.fileChk = function() {
        var telFg = $('input[name=radioTelFg]:checked').val(); // 발신번호 유형 (0:대표자본인, 1:기업명의)
        var addSmsFg = $('input[name=radioAddSmsFg]:checked').val(); // 발신번호 명의자 (0:휴대폰번호, 1:유선번호)

        if( (telFg == 0 && addSmsFg == 1) || (telFg == 1 && addSmsFg == 0) || (telFg == 1 && addSmsFg == 1) ) {
            // 첨부파일 1
            if (!isNull($("#smsTelNoFileTel1")[0].files[0])) {
                // 크기제한 체크
                var maxSize = 30 * 1024 * 1024;
                var fileSize = $("#smsTelNoFileTel1")[0].files[0].size;
                if (fileSize > maxSize) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileSizeChk.30.msg"]); // 첨부파일은 30MB 이내로 등록 가능합니다.
                    return;
                }
                // 파일명 형식 체크
                var imgFullNm = $("#smsTelNoFileTel1").val().substring($("#smsTelNoFileTel1").val().lastIndexOf('\\') + 1);
                if (1 > imgFullNm.lastIndexOf('.')) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileNmChk.msg"]); // 파일명 또는 확장자가 올바르지 않습니다. 다시 확인해주세요.
                    return;
                }
                // 확장자 체크
                var reg = /(.*?)\.(jpg|JPG|png|PNG|gif|GIF)$/;
                if (!$("#smsTelNoFileTel1").val().match(reg)) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileExtensionChk.msg"]); // 확장자가 .jpg .JPG .png .PNG .gif .GIF 인 파일만 등록가능합니다.
                    return;
                }
            } else {
                $scope._popMsg("서류 첨부 ① " + messages["smsTelNoRegister2.fileChk.msg"]); // 파일을 선택해주세요.
                return;
            }
        }

        if( (telFg == 0 && addSmsFg == 1) || (telFg == 1 && addSmsFg == 1) ) {
            // 첨부파일 2
            if (!isNull($("#smsTelNoFileTel2")[0].files[0])) {
                // 크기제한 체크
                var maxSize = 30 * 1024 * 1024;
                var fileSize = $("#smsTelNoFileTel2")[0].files[0].size;
                if (fileSize > maxSize) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileSizeChk.30.msg"]); // 첨부파일은 30MB 이내로 등록 가능합니다.
                    return;
                }
                // 파일명 형식 체크
                var imgFullNm = $("#smsTelNoFileTel2").val().substring($("#smsTelNoFileTel2").val().lastIndexOf('\\') + 1);
                if (1 > imgFullNm.lastIndexOf('.')) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileNmChk.msg"]); // 파일명 또는 확장자가 올바르지 않습니다. 다시 확인해주세요.
                    return;
                }
                // 확장자 체크
                var reg = /(.*?)\.(jpg|JPG|png|PNG|gif|GIF)$/;
                if (!$("#smsTelNoFileTel2").val().match(reg)) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileExtensionChk.msg"]); // 확장자가 .jpg .JPG .png .PNG .gif .GIF 인 파일만 등록가능합니다.
                    return;
                }
            } else {
                $scope._popMsg("서류 첨부 ② " + messages["smsTelNoRegister2.fileChk.msg"]); // 파일을 선택해주세요.
                return;
            }

            // 첨부파일 3
            if (!isNull($("#smsTelNoFileTel3")[0].files[0])) {
                // 크기제한 체크
                var maxSize = 30 * 1024 * 1024;
                var fileSize = $("#smsTelNoFileTel3")[0].files[0].size;
                if (fileSize > maxSize) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileSizeChk.30.msg"]); // 첨부파일은 30MB 이내로 등록 가능합니다.
                    return;
                }
                // 파일명 형식 체크
                var imgFullNm = $("#smsTelNoFileTel3").val().substring($("#smsTelNoFileTel3").val().lastIndexOf('\\') + 1);
                if (1 > imgFullNm.lastIndexOf('.')) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileNmChk.msg"]); // 파일명 또는 확장자가 올바르지 않습니다. 다시 확인해주세요.
                    return;
                }
                // 확장자 체크
                var reg = /(.*?)\.(jpg|JPG|png|PNG|gif|GIF)$/;
                if (!$("#smsTelNoFileTel3").val().match(reg)) {
                    $scope._popMsg(messages["smsTelNoRegister2.fileExtensionChk.msg"]); // 확장자가 .jpg .JPG .png .PNG .gif .GIF 인 파일만 등록가능합니다.
                    return;
                }
            } else {
                $scope._popMsg("서류 첨부 ③ " + messages["smsTelNoRegister2.fileChk.msg"]); // 파일을 선택해주세요.
                return;
            }
        }

        // 저장하시겠습니까?
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            if(telFg == 0 && addSmsFg == 0) {
                // 저장
                $scope.save("");
            } else {
                // 첨부파일 저장
                $scope.fileSave();
            }
        });
    };

    // 첨부파일 저장
    $scope.fileSave = function() {
        var formData = new FormData($("#smsTelNoFileTelForm")[0]);
        // formData.append("orgnCd", orgnCd);
        formData.append("pageGubun", "smsTelNoFileTel");

        var url = '/adi/sms/smsSend/smsTelNoRegister2/getSmsTelNoRegister2FileSave.sb';
        $.ajax({
            url: url,
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            // async:false,
            success: function(result) {
                // alert(result.status);
                // alert(result.data);
                if (result.status === "OK") {
                    // $scope._popMsg("저장되었습니다.");
                    $scope.$broadcast('loadingPopupInactive');

                    // 저장할 컨텐츠(파일경로^파일명^원본파일명)
                    var contentData = result.data;
                    contentData = contentData.substring(0, contentData.length-1);

                    // 저장
                    $scope.save(contentData);
                }
                else if (result.status === "FAIL") {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }
                /*else if(result.status === undefined) {
                    location.href = "/";
                }*/
                else {
                    var msg = result.status + " : " + result.message;
                    $scope._popMsg(msg);
                    $scope.$broadcast('loadingPopupInactive');
                }
            },
            error : function(result){
                $scope._popMsg("error");
                $scope.$broadcast('loadingPopupInactive');
            }
        },function() {
            $scope._popMsg("Ajax Fail By HTTP Request");
            $scope.$broadcast('loadingPopupInactive');
        });
    };

    // 저장
    $scope.save = function(contentData) {
        var params = {};
        params.certId = $("#srchCertId").val();
        params.telFg = $('input[name=radioTelFg]:checked').val();
        params.addSmsFg = $('input[name=radioAddSmsFg]:checked').val();
        params.addSmsUserNm = $scope.addSmsUserNm;
        params.addSmsTelNo = $scope.addSmsTelNo.replaceAll("-", "");
        // 첨부파일 1
        params.fileUrl1 = "";
        params.fileNm1 = "";
        params.fileOrgNm1 = "";
        // 첨부파일 2
        params.fileUrl2 = "";
        params.fileNm2 = "";
        params.fileOrgNm2 = "";
        // 첨부파일 3
        params.fileUrl3 = "";
        params.fileNm3 = "";
        params.fileOrgNm3 = "";

        if (contentData != null) {
            var arrRowContentData = contentData.split("|");

            for(var i=0; i < arrRowContentData.length; i++) {
                var arrContentData = arrRowContentData[i].split("^");
                var contentData_fileUrl = arrContentData[0];
                var contentData_fileNm = arrContentData[1];
                var contentData_fileOrgNm = arrContentData[2];
                var contentData_fileNum = arrContentData[3];

                if(contentData_fileNum == "1") {
                    params.fileUrl1 = contentData_fileUrl;
                    params.fileNm1 = contentData_fileNm;
                    params.fileOrgNm1 = contentData_fileOrgNm;
                }
                if(contentData_fileNum == "2") {
                    params.fileUrl2 = contentData_fileUrl;
                    params.fileNm2 = contentData_fileNm;
                    params.fileOrgNm2 = contentData_fileOrgNm;
                }
                if(contentData_fileNum == "3") {
                    params.fileUrl3 = contentData_fileUrl;
                    params.fileNm3 = contentData_fileNm;
                    params.fileOrgNm3 = contentData_fileOrgNm;
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/sms/smsSend/smsTelNoRegister2/getSmsTelNoRegister2Save.sb", params, function(){
            // 팝업 닫기
            $scope.close();
        });
    };
    // <-- //저장 -->

    // 첨부파일 초기화
    $scope.clearFile = function (gubun) {
        if(gubun == "A") {
            // 첨부파일 리셋
            var agent = navigator.userAgent.toLowerCase();
            if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
                // ie 일때
                // $("#smsTelNoFileTel1").replaceWith( $("#smsTelNoFileTel1").clone(true) );
                // $("#smsTelNoFileTel2").replaceWith( $("#smsTelNoFileTel2").clone(true) );
                // $("#smsTelNoFileTel3").replaceWith( $("#smsTelNoFileTel3").clone(true) );
                $("#smsTelNoFileTel1").val("");
                $("#smsTelNoFileTel2").val("");
                $("#smsTelNoFileTel3").val("");
            } else {
                // other browser 일때
                $("#smsTelNoFileTel1").val("");
                $("#smsTelNoFileTel2").val("");
                $("#smsTelNoFileTel3").val("");
            }
            $("#smsTelNoFileTelForm")[0].reset();

        } else if (gubun == "23") {
            // 첨부파일 리셋
            var agent = navigator.userAgent.toLowerCase();
            if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
                // ie 일때
                // $("#smsTelNoFileTel2").replaceWith( $("#smsTelNoFileTel2").clone(true) );
                // $("#smsTelNoFileTel3").replaceWith( $("#smsTelNoFileTel3").clone(true) );
                $("#smsTelNoFileTel2").val("");
                $("#smsTelNoFileTel3").val("");
            } else {
                // other browser 일때
                $("#smsTelNoFileTel2").val("");
                $("#smsTelNoFileTel3").val("");
            }
        }
    };

    // KCP 인증 자원과 입력값을 초기화하고 등록 팝업을 닫는다.
    $scope.close = function() {
        // 등록 팝업을 닫기 전에 KCP 팝업·감시 타이머·인증 폼을 정리한다.
        $scope.finishKcpAuthPopup(true);
        $scope.verifyInProgress = false;

        // 처리 동의 안내 체크
        $scope.contentYn = true;

        $("#srchCertId").val("");
        $scope.addSmsUserNm = "";
        $scope.addSmsTelNo = "";

        // 첨부파일 초기화
        $scope.clearFile("A");

        $scope.wjSmsTelNoRegister2Layer.hide();
        event.preventDefault();
    };

}]);
