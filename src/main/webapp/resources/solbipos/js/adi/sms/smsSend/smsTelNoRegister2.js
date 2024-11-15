/****************************************************************
 *
 * 파일명 : smsTelNoRegister2.js
 * 설  명 : 발신번호 사전등록2 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  발신번호 사전등록2 팝업 조회 그리드 생성
 */
app.controller('smsTelNoRegister2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsTelNoRegister2Ctrl', $scope, $http, false));

    // 처리 동의 안내 체크
    $scope.contentYn = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("smsTelNoRegister2Ctrl", function(event, data) {
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

    // 휴대폰 본인인증
    $scope.vfTelNo = function(){
        if($("#srchCertId").val() == "" || $("#srchCertId").val() == null) {
            // 본인인증
            $scope.verify();

        } else {
            // 본인인증 체크
            var params = {};
            params.certId = $("#srchCertId").val();

            $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsTelNoRegister2/getVerifyChk2.sb', params, function (response) {
                if (response.data.data.list !== 0) {
                    $scope._popMsg(messages["smsTelNoRegister2.verifyChk"]); // 이미 본인인증이 완료되었습니다.
                    return false;

                } else {
                    // 본인인증
                    $scope.verify();
                }
            });
        }
    };

    // 본인인증
    $scope.verify = function(){
        $.postJSON("/adi/sms/marketingSmsSend/marketingSmsSend/getVerifyVal.sb", null, function(result) {
            var data = result.data;
            console.log(data);

            var auth_form = document.form_auth;

            var return_gubun;
            var width = 410;
            var height = 500;

            var leftpos = screen.width / 2 - (width / 2);
            var toppos = screen.height / 2 - (height / 2);

            var winopts = "width=" + width + ", height=" + height + ", toolbar=no,status=no,statusbar=no,menubar=no,scrollbars=no,resizable=no";
            var position = ",left=" + leftpos + ", top=" + toppos;

            var url = data.gwUrl + '?' +                        // KCP 인증창
                'site_cd=' + data.siteCd + '&' +                // 상점코드
                'ordr_idxx=' + data.ordrIdxx + '&' +            // 상점관리요청번호
                'req_tx=cert' + '&' +                                   // 요청의 종류를 구분하는 변수
                'cert_method=01' + '&' +                                // 01-휴대폰인증 02-공인인증(추후제공)
                'up_hash=' + data.upHash + '&' +                // 요청 hash data
                'Ret_URL=' + data.retUrl + '?sid=' + data.sessionId + '&' +                // 본인인증 결과 리턴페이지
                'cert_otp_use=Y' + '&' +                                // 인요청시 OTP승인 여부
                'cert_enc_use_ext=Y'
            ;

            console.log("JH");
            console.log("site_cd : " + data.siteCd);
            console.log("web_siteid : " + data.webSiteid);
            console.log("gw_url : " + data.gwUrl);
            console.log("Ret_URL : " + data.retUrl);
            console.log("ordr_idxx : " + data.ordrIdxx);
            console.log("up_hash : " + data.upHash);
            console.log("sessionID : " + data.sessionId);
            console.log("url : " + url);

            $("#srchCertId").val(data.ordrIdxx);

            // 저장기능 수행
            var params = {};
            params.certId = data.ordrIdxx;

            $.postJSONArray("/adi/sms/marketingSmsSend/marketingSmsSend/saveVerify.sb", params, function (result) {
                    console.log("JH : 결과");
                    var AUTH_POP =  window.open(url, 'auth_popup', winopts + position);
                    console.log('1111');
                },
                function (result) {
                    s_alert.pop("JH : 결과msg" + result.message);
                    s_alert.pop(result.message);
                });
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
            if (response.data.data.list !== 0) {
                // 첨부파일 체크
                $scope.fileChk();

            } else {
                $scope._popMsg(messages["smsTelNoRegister2.vfTelNoBlankAlert"]); // 휴대폰 본인인증을 해주세요.
                return false;
            }
        });
    });

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
                // console.log('save result', result);
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

    // 팝업 닫기
    $scope.close = function() {
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