/****************************************************************
 *
 * 파일명 : verInfoDtl.js
 * 설  명 : (관리자) 듀얼모니터 영상관리 탭 - 등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.03.07     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var langFgReg = [
    {"name":"국문","value":"0"},
    {"name":"영문","value":"1"},
    {"name":"중문","value":"2"},
    {"name":"일문","value":"3"}
];

app.controller('verRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('verRegistCtrl', $scope, $http, true));

    // 콤보박스 데이터
    $scope._setComboData("useYnCombo", useYnData);
    $scope._setComboData("fileTypeCombo", fileTypeComboList);
    $scope._setComboData("versionLangFgCombo", langFgReg);

    // 등록일자 셋팅
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    $scope.isEdit = false;

    // 버전정보
    $scope.version;

    $scope.selectVersion;
    $scope.setSelectVersion = function(ver){
        $scope.selectVersion = ver;
    };
    $scope.getSelectVersion = function(){
        return $scope.selectVersion;
    };

    // 파일타입
    $scope.fileType;
    $scope.setFileType = function(ver){
        $scope.fileType = ver;
    };
    $scope.getFileType = function(){
        return $scope.fileType;
    };

    // 파일업로드시 파일사이즈 변경
    $scope.maxDate = function(){
        endDate.value = new Date('2099-12-31');
    };

    // 조회 버튼 클릭
    $scope.$on("verRegistCtrl", function(event, data) {

        $scope.setSelectVersion(data);

        if( !isEmptyObject($scope.getSelectVersion()) ) {
            $scope.isEdit = true;
            $scope.getVersionInfo();
        } else {
            // 파일등록시, 사용여부 '사용'으로 기본 셋팅
            $scope.versionUseYnCombo.selectedValue = "Y";
            // 파일등록시, 언어 '국문'으로 기본 셋팅
            $scope.versionLangFgCombo.selectedValue = "0";
        }
        event.preventDefault();
    });

    // 파일업로드시 파일사이즈 변경
    $scope.uploadChange = function(){
        $scope.$apply(function() {
            var maxSize = 20 * 1024 * 1024;
            var fileSize = document.getElementById("file").files[0].size;

            if(fileSize > maxSize) {
                alert("첨부파일 사이즈는 20MB 이내로 등록 가능합니다.    ");

                // 첨부파일 리셋
                var agent = navigator.userAgent.toLowerCase();
                if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
                    // ie 일때
                    $("#file").replaceWith( $("#file").clone(true) );
                    $scope.version.uploadFile = "";
                } else {
                    // other browser 일때
                    $("#file").val("");
                    $scope.version.uploadFile = "";
                }
                $scope.version.fileSize = "";

                return false;
            } else {
                $scope.version.fileSize = getfileSize(fileSize);
            }
        });
    };

    // 입력 값 체크
    $scope.mediaSave = function (){
        if(Number(wijmo.Globalize.format(startDate.value, 'yyyyMMdd')) > Number(wijmo.Globalize.format(endDate.value, 'yyyyMMdd'))){
            $scope._popMsg(messages["media.date.msg"]);
            return;
        }

        // 버전적용명 체크
        if($("#verSerNm").val() === null || $("#verSerNm").val() === undefined || $("#verSerNm").val() === "") {
            $scope._popMsg(messages["media.verSerNm"] + " " + messages["cmm.require.text"]);
            return;
        }

        // 이미지출력시간
        if($("#dispTime").val() === null || $("#dispTime").val() === undefined || $("#dispTime").val() === "") {
        } else {
            // 숫자만 입력
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test($("#dispTime").val())) {
                $scope._popMsg(messages["media.dispTimeInChk"]); // 이미지출력시간은 숫자만 입력해주세요.
                return;
            }

            // 길이체크
            if (nvl($("#dispTime").val(), '').length > 2) {
                $scope._popMsg(messages["media.dispTimeLengthChk"]); // 이미지출력시간 길이가 너무 깁니다. 1~99 입력가능합니다.
                return;
            }

            // 숫자입력 제한체크
            if (parseInt($("#dispTime").val(), 0) < 1 || parseInt($("#dispTime").val(), 0) > 99) {
                $scope._popMsg(messages["media.dispTimeLengthChk2"]); // 이미지출력시간은 1~99 입력가능합니다.
                return;
            }
        }

        if( isEmptyObject($scope.getSelectVersion()) ) { // 신규등록
            // 파일 체크
            if($("#file").val() === null || $("#file").val() === undefined || $("#file").val() === "") {
                $scope._popMsg(messages["media.file"] + " " + messages["cmm.require.text"]);
                return;
            }
            // else {
            //   var type = '(.*?)\\.(' + $scope.getFileType().replace(",","|").replace(".","") + ')$';
            //   console.log(type);
            //   // 확장자 체크
            //   var reg = new RegExp(type);
            //   if(!$("#file").val().match(reg)) {
            //     $scope._popMsg(messages["media.fileChk.msg"] + $scope.getFileType() + messages["media.fileChk.msg2"]);
            //     return;
            //   }
            // }
        } else{ // 수정
            if($("#file").val() === null || $("#file").val() === undefined || $("#file").val() === "") {
            }
            // else {
            //   var type = '(.*?)\\.(' + $scope.getFileType().replace(",","|").replace(".","") + ')$';
            //   // 확장자 체크
            //   var reg = new RegExp(type);
            //   if(!$("#file").val().match(reg)) {
            //     $scope._popMsg(messages["media.fileChk.msg"] + $scope.getFileType() + messages["media.fileChk.msg2"]);
            //     return;
            //   }
            // }
        }
        $scope.chkRegist();
    }

    // 날짜 중복여부 체크
    $scope.chkRegist = function(){

        // 날짜 체크
        var param = {};
        param.storeCd = storeCd;
        param.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        param.endDate =  wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        if( isEmptyObject($scope.getSelectVersion()) ) {
            param.verSerNo = '0';
        } else {
            param.verSerNo = $scope.version.verSerNo;
        }
        param.fileType = $scope.versionFileTypeCombo.selectedValue;
        param.useYn = $scope.versionUseYnCombo.selectedValue;

        $.postJSON("/sys/admin/adminMedia/chkDate.sb", param, function(result) {
                $scope.$broadcast('loadingPopupActive');
                if(result.status === 'OK') {
                    $scope.chkDup();
                }
            },
            function(result) {
                $scope._popMsg($scope.versionFileTypeCombo.text + messages["media.chkDate.msg"]);
            });
    };

    // 중복 체크
    $scope.chkDup = function (){
        // 날짜 체크
        var param = {};
        param.storeCd = storeCd;
        param.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        param.endDate =  wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        if( isEmptyObject($scope.getSelectVersion()) ) {
            param.verSerNo = '0';
        } else {
            param.verSerNo = $scope.version.verSerNo;
        }
        param.fileType = $scope.versionFileTypeCombo.selectedValue;
        param.useYn = $scope.versionUseYnCombo.selectedValue;

        $.postJSON("/sys/admin/adminMedia/chkDup.sb", param, function(result) {
                $scope.$broadcast('loadingPopupActive');
                if(result.status === 'OK') {
                    $scope.regist();
                }
            },
            function(result) {
                $scope._popMsg($scope.versionFileTypeCombo.text + "광고 이미지 배너는 " + result.data + "개까지 등록 가능합니다.");
                $scope.$broadcast('loadingPopupInactive');
            });
    };

    // 파일 타입 변경 시 첨부파일 확장자 변경
    $scope.fileTypeChg = function(s){
        // 키오스크(인트로)
        if(s.selectedValue === "003") {
            $("#lblFileSizeMax").text(messages["media.fileSize.max2"]);
        } else if(s.selectedValue === "010") {
            $("#lblFileSizeMax").text(messages["media.fileSize.max3"]);
        } else if(s.selectedValue === "014") {
            $("#lblFileSizeMax").text(messages["media.fileSize.msg"]);
        } else if(s.selectedValue === "015") {
            $("#lblFileSizeMax").text(messages["media.fileSize.msg2"]);
        } else if(s.selectedValue === "016") {
            $("#lblFileSizeMax").text(messages["media.fileSize.max4"]);
        } else if(s.selectedValue === "018") {
            $("#lblFileSizeMax").text(messages["media.fileSize.max5"]);
        } else {
            $("#lblFileSizeMax").text("");
        }

        if($("#file").val() === null || $("#file").val() === undefined || $("#file").val() === "") {
        } else {
            // 첨부파일 리셋
            var agent = navigator.userAgent.toLowerCase();
            if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
                // ie 일때
                $("#file").replaceWith( $("#file").clone(true) );
                // media.fileSize.max
                $scope.version.uploadFile = "";
            } else {
                // other browser 일때
                $("#file").val("");
                $scope.version.uploadFile = "";
            }
        }

        var param = {};
        param.fileType = $scope.versionFileTypeCombo.selectedValue;
        $.ajax({
            url:"/sys/admin/adminMedia/getFileType.sb",
            type:"POST",
            data:param,
            success:function(result){
                $scope.setFileType(result);
                $("#file").attr("accept",result);
            }
        });

    }

    // 저장
    $scope.regist = function(){
        var formData = new FormData($("#regForm")[0]);

        formData.append("orgnCd", orgnCd);
        if(orgnFg === "HQ"){
            formData.append("hqOfficeCd", hqOfficeCd);
        } else if(orgnFg === "STORE") {
            formData.append("hqOfficeCd", "");
            formData.append("storeCd", storeCd);
        }
        formData.append("userId", userId);
        formData.append("verSerNo", $scope.version.verSerNo);
        formData.append("verSerNm", $scope.version.verSerNm);
        formData.append("startDate", wijmo.Globalize.format(startDate.value, 'yyyyMMdd'));
        formData.append("endDate", wijmo.Globalize.format(endDate.value, 'yyyyMMdd'));
        formData.append("fileType", $scope.versionFileTypeCombo.selectedValue);
        formData.append("fileSize", $scope.version.fileSize);
        formData.append("useYn", $scope.versionUseYnCombo.selectedValue);
        formData.append("dispTime", nvl($scope.version.dispTime, 3));
        formData.append("langFg", $scope.versionLangFgCombo.selectedValue);
        formData.append("adverUrl", nvl($scope.version.adverUrl, ''));

        var url = '';

        if( isEmptyObject($scope.getSelectVersion()) ) {
            url = '/sys/admin/adminMedia/verInfo/getRegistMedia.sb';
        } else {
            url = '/sys/admin/adminMedia/verInfo/getModifyMedia.sb';
        }

        $scope.$broadcast('loadingPopupActive');

        $.ajax({
            url: url,
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            success: function(result) {
                if (result.status === "OK") {
                    if( isEmptyObject($scope.getSelectVersion()) ) {
                        $scope._popMsg("등록되었습니다.");
                    } else {
                        $scope._popMsg("저장되었습니다.");
                    }

                    $scope.$broadcast('loadingPopupInactive');
                    $scope.close();
                }
                else if (result.status === "FAIL") {
                    $scope._popMsg(messages["media.fileChk.msg"] + $scope.getFileType() + messages["media.fileChk.msg2"]);
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }

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
        },function(){
            $scope._popMsg("Ajax Fail By HTTP Request");
            $scope.$broadcast('loadingPopupInactive');
        });
    };

    // 버전 목록 조회
    $scope.getVersionInfo = function(){
        var params = {};
        params    = $scope.getSelectVersion();

        console.log('params' , params);

        $scope._postJSONQuery.withOutPopUp( "/sys/admin/adminMedia/verInfo/dtlInfo.sb", params, function(response){

            console.log('response',response);

            var data = response.data.data;
            $scope.version = data;

            startDate.value = getFormatDate($scope.version.startDate, '-');
            endDate.value = getFormatDate($scope.version.endDate, '-');

            $("#fileIn").attr("colspan", 1);
            $("#fileOrgH").show();
            $("#fileOrgD").show();

            // 파일사이즈 변환하여 표기
            $scope.version.fileSize = getfileSize($scope.version.fileSize);

            // 언어구분 값이 없는경우, '국문'으로 기본 셋팅
            if($scope.version.langFg === null || $scope.version.langFg === undefined || $scope.version.langFg === ""){
                $scope.version.langFg = "0";
                $scope.versionLangFgCombo.selectedValue = "0";
            }
        });
    };

    // 닫기
    $scope.close = function(){
        startDate.value = new Date(Date.now());
        endDate.value = new Date(Date.now());

        // 첨부파일 리셋
        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
            // ie 일때
            $("#file").replaceWith( $("#file").clone(true) );
            $scope.version.uploadFile = "";
        } else {
            // other browser 일때
            $("#file").val("");
            $scope.version.uploadFile = "";
        }

        $("#fileIn").attr("colspan", 3);
        $("#fileOrgH").hide();
        $("#fileOrgD").hide();

        var scope  = agrid.getScope('adminMediaCtrl');
        scope._broadcast('adminMediaCtrl');
        $scope.versionRegistLayer.hide();
    };

}]);

