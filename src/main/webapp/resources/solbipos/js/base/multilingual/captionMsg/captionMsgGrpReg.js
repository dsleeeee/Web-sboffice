/****************************************************************
 *
 * 파일명 : captionMsgGrp.js
 * 설  명 : 다국어관리(기능키/메시지) - 화면구분등록 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.11.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('captionMsgGrpRegCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('captionMsgGrpRegCtrl', $scope, $http, true));

    // 신규 or 수정 구분
    $scope.isEdit = false;

    // 입력한 화면구분 정보
    $scope.version = {};

    // 수정모드시, 선택한 화면구분 정보 갖고있기 위해
    $scope.selCaptionMsgGrp = {};

    // 화면 진입
    $scope.$on("captionMsgGrpRegCtrl", function(event, data) {

        if(!isEmptyObject(data)) {
            $("#lblTitle").text(messages["captionMsg.captionMsgMod"]);
            $scope.isEdit = true;
            $scope.selCaptionMsgGrp = data;
            $scope.dtlInfo($scope.selCaptionMsgGrp);
        } else {
            $("#lblTitle").text(messages["captionMsg.captionMsgGrp"]);
            $scope.isEdit = false;
            $scope.selCaptionMsgGrp = {};
        }
        event.preventDefault();
    });

    // 조회
    $scope.dtlInfo = function (data) {
        var params = data;

        $scope._postJSONQuery.withOutPopUp( "/base/multilingual/captionMsg/getCaptionMsgGrpDtl.sb", params, function(response){
            $scope.version = response.data.data;

            // 파일사이즈 변환하여 표기
            $scope.version.fileSize = getfileSize($scope.version.fileSize);
        });
    };

    // 저장
    $scope.save = function(){

        // 입력양식 체크 성공시
        if($scope.chkForm()){

            var formData = new FormData($("#regForm")[0]);

            formData.append("orgnCd", orgnCd);
            if (orgnFg === "HQ") {
                formData.append("hqOfficeCd", hqOfficeCd);
            } else if (orgnFg === "STORE") {
                formData.append("hqOfficeCd", "");
                formData.append("storeCd", storeCd);
            }
            formData.append("userId", userId);
            formData.append("captionImgCd", $scope.version.captionImgCd);
            formData.append("captionImgNm", $scope.version.captionImgNm);
            formData.append("fileSize", $scope.version.fileSize);

            if($scope.version.fileDesc === undefined || $scope.version.fileDesc === null || $scope.version.fileDesc === ""){
                formData.append("fileDesc", "");
            }else {
                formData.append("fileDesc", $scope.version.fileDesc);
            }

            var url = '';

            if (!$scope.isEdit && isEmptyObject($scope.version.captionImgCd)) {
                url = '/base/multilingual/captionMsg/saveCaptionMsgGrp.sb';
            } else {
                url = '/base/multilingual/captionMsg/updateCaptionMsgGrp.sb';
            }

            $scope.$broadcast('loadingPopupActive');

            $.ajax({
                url: url,
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                cache: false,
                success: function (result) {
                    if (result.status === "OK") {
                        $scope._popMsg("저장되었습니다.");
                        $scope.$broadcast('loadingPopupInactive');

                        if($scope.isEdit){ // 수정모드시, 상세 팝업 재조회
                            $scope._broadcast('captionMsgGrpDtlCtrl',  $scope.selCaptionMsgGrp);
                        }

                        $scope.close();

                    } else if (result.status === "FAIL") {
                        if(result.data !== null){
                            var msg = result.status + " : " + result.data.msg;
                            $scope._popMsg(msg);
                        }else{
                            $scope._popMsg('Ajax Fail By HTTP Request');
                        }
                        $scope.$broadcast('loadingPopupInactive');
                    } else if (result.status === "SERVER_ERROR") {
                        $scope._popMsg(result.message);
                        $scope.$broadcast('loadingPopupInactive');
                    } else {
                        var msg = result.status + " : " + result.message;
                        $scope._popMsg(msg);
                        $scope.$broadcast('loadingPopupInactive');
                    }
                },
                error: function (result) {
                    $scope._popMsg("error");
                    $scope.$broadcast('loadingPopupInactive');
                }
            }, function () {
                $scope._popMsg("Ajax Fail By HTTP Request");
                $scope.$broadcast('loadingPopupInactive');
            });
        }
    };

    // 입력양식 체크
    $scope.chkForm = function(){
        // 화면구분코드 체크
        if($scope.isEdit) { // 수정모드 시, 체크
            if ($("#regCaptionImgCd").val() === null || $("#regCaptionImgCd").val() === undefined || $("#regCaptionImgCd").val() === "") {
                $scope._popMsg(messages["captionMsg.captionImgCd"] + " " + messages["cmm.require.text"]);
                return false;
            }
        }

        // 화면구분명 체크
        if ($("#regCaptionImgNm").val() === null || $("#regCaptionImgNm").val() === undefined || $("#regCaptionImgNm").val() === "") {
            $scope._popMsg(messages["captionMsg.captionImgNm"] + " " + messages["cmm.require.text"]);
            return false;
        }

        // 첨부파일 체크
        if(!$scope.isEdit) { // 신규등록시, 체크
            if ($("#file").val() === null || $("#file").val() === undefined || $("#file").val() === "") {
                $scope._popMsg(messages["captionMsg.file"] + " " + messages["cmm.require.text"]);
                return;
            }
        }

        return true;
    };

    // 파일업로드시 파일사이즈 변경
    $scope.uploadChange = function () {
        $scope.$apply(function () {
            var maxSize = 20 * 1024 * 1024;
            var fileSize = document.getElementById("file").files[0].size;

            if (fileSize > maxSize) {
                alert("첨부파일 사이즈는 20MB 이내로 등록 가능합니다.    ");

                // 첨부파일 리셋
                var agent = navigator.userAgent.toLowerCase();
                if ((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1)) {
                    // ie 일때
                    $("#file").replaceWith($("#file").clone(true));
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

    // 닫기
    $scope.close = function(){

        // 첨부파일 리셋
        var agent = navigator.userAgent.toLowerCase();
        if ((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1)) {
            // ie 일때
            $("#file").replaceWith($("#file").clone(true));
            $scope.version.uploadFile = "";
        } else {
        // other browser 일때
            $("#file").val("");
            $scope.version.uploadFile = "";
        }
        $scope.version.fileSize = "";

        $("#fileIn").attr("colspan", 3);
        $("#fileOrgH").hide();
        $("#fileOrgD").hide();

        // 화면구분등록 리스트 재조회
        $scope._pageView('captionMsgGrpCtrl', 1);

        // 기능키/메시지 탭 화면구분 검색조건 콤보박스 재조회
        var vScope = agrid.getScope('captionMsgCtrl');
        vScope.setCaptionMsgGrpCombo();

        // 신규등록 팝업 hide
        $scope.captionMsgGrpRegLayer.hide();
    };

}]);