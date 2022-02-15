/****************************************************************
 *
 * 파일명 : smsGeneralNoRegister.js
 * 설  명 : 일반번호 인증요청 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.26     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일반번호 인증요청 팝업 조회 그리드 생성
 */
app.controller('smsGeneralNoRegisterCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsGeneralNoRegisterCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("smsGeneralNoRegisterCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 팩스로 접수
    $scope.radioFaxChange = function() {
        $("#divFax").css("display", "");
        $("#divFile").css("display", "none");

        // 첨부파일 초기화
        $scope.clearFile();
    };

    // 파일로 첨부
    $scope.radioFileChange = function() {
        $("#divFax").css("display", "none");
        $("#divFile").css("display", "");
    };

    // 서류인증신청
    $scope.smsGeneralNoSave = function() {
        var addFg = $('input[name=faxFile]:checked').val();

        // 파일로 첨부
        if(addFg === "1") {
            // 첨부파일
            if (!isNull($("#fileTel")[0].files[0])) {
                // 크기제한 체크
                var maxSize = 30 * 1024 * 1024;
                var fileSize = $("#fileTel")[0].files[0].size;
                if (fileSize > maxSize) {
                    $scope._popMsg(messages["smsGeneralNoRegister.fileSizeChk.30.msg"]); // 첨부파일은 30MB 이내로 등록 가능합니다.
                    return;
                }
                // 파일명 형식 체크
                var imgFullNm = $("#fileTel").val().substring($("#fileTel").val().lastIndexOf('\\') + 1);
                if(1 > imgFullNm.lastIndexOf('.')){
                    $scope._popMsg(messages["smsGeneralNoRegister.fileNmChk.msg"]); // 파일명 또는 확장자가 올바르지 않습니다. 다시 확인해주세요.
                    return;
                }
                // 확장자 체크
                var reg = /(.*?)\.(jpg|JPG|png|PNG)$/;
                if(! $("#fileTel").val().match(reg)) {
                    $scope._popMsg(messages["smsGeneralNoRegister.fileExtensionChk.msg"]); // 확장자가 .jpg .JPG .png .PNG 인 파일만 등록가능합니다.
                    return;
                }
            } else {
                $scope._popMsg(messages["smsGeneralNoRegister.fileChk.msg"]); // 파일을 선택해주세요.
                return;
            }
        }

        // 하여 서류인증신청을 하시겠습니까?
        var msg = "";
        // 파일로 첨부
        if(addFg === "1") {
            msg = messages["smsGeneralNoRegister.file"] + " " + messages["smsGeneralNoRegister.smsGeneralNoConfirm"];
            // 팩스로 접수
        } else {
            msg = messages["smsGeneralNoRegister.fax"] + " " + messages["smsGeneralNoRegister.smsGeneralNoConfirm"];
        }
        if (confirm(msg)) {
            // 파일로 첨부
            if(addFg === "1") {
                // 첨부파일 저장
                $scope.smsGeneralNoFileSave(addFg);
                // 팩스로 접수
            } else {
                // 서류인증신청 저장 전 관리요청번호(CERT_ID) 채번
                $scope.getValSmsGeneralNo(addFg, "", "");
            }
        }
    };

    // 서류인증신청 저장 전 관리요청번호(CERT_ID) 채번
    // 값가져오기
    $scope.getValSmsGeneralNo = function(addFg, fileUrl, fileNm) {
        $.postJSON("/adi/sms/smsTelNoManage/smsTelNoManage/getVal.sb", null, function(result) {
                var data = result.data;
                console.log(data);
                var ordrIdxx = data.ordrIdxx;

                // 서류인증신청 저장
                $scope.smsGeneralNoRealSave(addFg, fileUrl, fileNm, ordrIdxx);
            }
        );
    };

    // 서류인증신청 저장
    $scope.smsGeneralNoRealSave = function(addFg, fileUrl, fileNm, ordrIdxx) {
        var params = {};
        params.addFg = addFg;
        params.fileUrl = fileUrl;
        params.fileNm = fileNm;
        params.certId = ordrIdxx;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/sms/smsSend/smsGeneralNoRegister/getSmsGeneralNoRegisterSave.sb", params, function(){
            $scope._popMsg(messages["smsGeneralNoRegister.smsGeneralNoAlert"]); // 인증 요청하신 내역은 "부가서비스 > SMS관리 > SMS전송 > 발신번호관리 탭"에서 확인 가능합니다.

            // 팝업 닫기
            $scope.close();
        });
    };

    // 첨부파일 저장
    $scope.smsGeneralNoFileSave = function(addFg) {
        var formData = new FormData($("#telForm")[0]);

        var url = '/adi/sms/smsSend/smsGeneralNoRegister/getSmsGeneralNoFileSave.sb';
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

                    // 저장할 컨텐츠(파일경로^파일명)
                    var contentData = result.data;

                    var arrContentData = contentData.split("^");
                    var fileUrl = arrContentData[0];
                    var fileNm = arrContentData[1];

                    // 서류인증신청 저장 전 관리요청번호(CERT_ID) 채번
                    $scope.getValSmsGeneralNo(addFg, fileUrl, fileNm);
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

    // 첨부파일 초기화
    $scope.clearFile = function () {
        // 첨부파일 리셋
        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
            // ie 일때
            // $("#fileTel").replaceWith( $("#fileTel").clone(true) );
            $("#fileTel").val("");
        } else {
            // other browser 일때
            $("#fileTel").val("");
        }
        $("#telForm")[0].reset();
    };

    // 팝업 닫기
    $scope.close = function() {
        // 첨부파일 초기화
        $scope.clearFile();

        $scope.wjSmsGeneralNoRegisterLayer.hide();
        event.preventDefault();
    };
}]);