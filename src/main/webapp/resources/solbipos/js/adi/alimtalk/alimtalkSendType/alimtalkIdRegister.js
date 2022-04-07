/****************************************************************
 *
 * 파일명 : alimtalkIdRegister.js
 * 설  명 : 알림톡 계정등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사업자 카테고리
var categoryCodeComboLData = [
    {"name":"대분류","value":""}
];
var categoryCodeComboMData = [
    {"name":"중분류","value":""}
];
var categoryCodeComboSData = [
    {"name":"소분류","value":""}
];

/**
 *  계정등록 조회 그리드 생성
 */
app.controller('alimtalkIdRegisterCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkIdRegisterCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("categoryCodeLCombo", categoryCodeComboLData); // 사업자 카테고리(대분류)
    $scope._setComboData("categoryCodeMCombo", categoryCodeComboMData); // 사업자 카테고리(중분류)
    $scope._setComboData("categoryCodeSCombo", categoryCodeComboSData); // 사업자 카테고리(소분류)

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkIdRegisterCtrl", function(event, data) {
        $scope.searchAlimtalkIdRegister();
        event.preventDefault();
    });

    $scope.searchAlimtalkIdRegister = function() {
        // 사업자 카테고리 조회
        $scope.categoryCode("L", "");
    };
    // <-- //검색 호출 -->

    // 사업자 카테고리 조회
    $scope.categoryCode = function(gubun, value) {
        var params = {};
        params.gubun = gubun;
        params.categoryCode = value;

        $scope._postJSONQuery.withOutPopUp('/adi/alimtalk/alimtalkSendType/alimtalkIdRegister/getCategoryCodeComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var categoryCodeList = response.data.data.list;
                if(gubun == "L") {
                    $scope._setComboData("categoryCodeLCombo", categoryCodeList); // 사업자 카테고리(대분류)
                } else if(gubun == "M") {
                    $scope._setComboData("categoryCodeMCombo", categoryCodeList); // 사업자 카테고리(중분류)
                } else if(gubun == "S") {
                    $scope._setComboData("categoryCodeSCombo", categoryCodeList); // 사업자 카테고리(소분류)
                }
            } else {
                if(gubun == "L") {
                    $scope._setComboData("categoryCodeLCombo", categoryCodeComboLData); // 사업자 카테고리(대분류)
                } else if(gubun == "M") {
                    $scope._setComboData("categoryCodeMCombo", categoryCodeComboMData); // 사업자 카테고리(중분류)
                } else if(gubun == "S") {
                    $scope._setComboData("categoryCodeSCombo", categoryCodeComboSData); // 사업자 카테고리(소분류)
                }
            }
        });
    };

    // 사업자 카테고리 선택 이벤트
    $scope.setCategoryCodeComboL = function (s){
        // 사업자 카테고리 조회
        $scope.categoryCode("M", s.selectedValue);
    };
    $scope.setCategoryCodeComboM = function (s){
        // 사업자 카테고리 조회
        $scope.categoryCode("S", s.selectedValue);
    };

    // 인증요청
    $scope.registerRequestSave = function() {
        if ($scope.plusFriendId === "" || $scope.plusFriendId === undefined) {
            $scope._popMsg(messages["alimtalkIdRegister.plusFriendIdAlert"]); // 카카오계정ID를 입력해주세요.
            return;
        }

        if ($scope.phoneNo === "" || $scope.phoneNo === undefined) {
            $scope._popMsg(messages["alimtalkIdRegister.phoneNoAlert"]); // 휴대폰번호를 입력해주세요.
            return;
        } else {
            // 숫자만 입력
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test($scope.phoneNo)) {
                $scope._popMsg(messages["alimtalkIdRegister.phoneNoInChk"]); // 휴대폰번호 숫자만 입력해주세요.
                return false;
            }
        }

        var params = {};
        params.orgnCd = orgnCd;
        params.regId = userId;
        params.modId = userId;
        params.appKey = appKey;
        params.secretKey = secretKey;
        params.apiUrl = apiUrl;
        params.plusFriendId = $scope.plusFriendId;
        params.categoryCode = $scope.categoryCodeLCombo + $scope.categoryCodeMCombo + $scope.categoryCodeSCombo;
        params.phoneNo = $scope.phoneNo;

        // 알림톡 계정등록 체크
        $scope._postJSONQuery.withOutPopUp( "/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkIdRegisterAllChk.sb", params, function(response){
            var alimtalkIdInfo = response.data.data.result;
            $scope.alimtalkIdInfo = alimtalkIdInfo;

            if(response.data.data.result != null) {
                $scope._popMsg(messages["alimtalkSendType.alimtalkIdRegisterAlert"]); // 계정이 등록되어 있습니다.
                return false;
            } else {
                // 인증요청 API호출 및 저장
                $scope.registerRequestApi(params);
            }
        });
    };

    // 인증요청 (발신프로필 등록 API 호출 및 저장)
    $scope.registerRequestApi = function(params) {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        $.ajax({
            type: "POST",
            url: "/adi/alimtalk/alimtalkSendType/alimtalkIdRegister/getAlimtalkSenderApiSave.sb",
            data: JSON.stringify(params),
            cache: false,
            dataType: "json",
            contentType : 'application/json',
            success: function(result){
                // alert(result.status);
                // alert(result.data);
                // alert(result.data.resultCode);
                // alert(result.data.resultMessage);
                if (result.data.resultCode.toString() === "0") {
                    $scope._popMsg("휴대폰번호로 인증번호 발송 되었습니다.");
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');

                    $scope.tokenYn = "Y";
                    $("#srchPlusFriendId").attr("disabled",true);
                    $("#srchCategoryCodeLCombo").attr("disabled",true);
                    $("#srchCategoryCodeMCombo").attr("disabled",true);
                    $("#srchCategoryCodeSCombo").attr("disabled",true);
                    $("#srchPhoneNo").attr("disabled",true);
                }
                else if (result.data.resultCode.toString() !== "0") {
                    $scope._popMsg(result.data.resultMessage.toString());
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
                // if (result.status === "OK") {
                //     $scope._popMsg("저장되었습니다.");
                //     $scope.close();
                // }
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
            }
        });
    };

    // 계정등록 (토큰인증 API 호출 및 저장)
    $scope.registerTokenSave = function() {
        if ($scope.tokenYn !== "Y") {
            $scope._popMsg(messages["alimtalkIdRegister.tokenYnAlert"]); // 인증요청을 해주세요.
            return;
        }

        if ($scope.token === "" || $scope.token === undefined) {
            $scope._popMsg(messages["alimtalkIdRegister.tokenAlert"]); // 인증번호를 입력해주세요.
            return;
        }

        var params = {};
        params.orgnCd = orgnCd;
        params.regId = userId;
        params.modId = userId;
        params.groupSenderKey = groupSenderKey;
        params.groupSenderKeyNm = groupSenderKeyNm;
        params.appKey = appKey;
        params.secretKey = secretKey;
        params.apiUrl = apiUrl;
        params.plusFriendId = $scope.plusFriendId;
        params.token = $scope.token;
        params.phoneNo = $scope.phoneNo;

        // 로딩바 show
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        $.ajax({
            type: "POST",
            url: "/adi/alimtalk/alimtalkSendType/alimtalkIdRegister/getAlimtalkSenderTokenApiSave.sb",
            data: JSON.stringify(params),
            cache: false,
            dataType: "json",
            contentType : 'application/json',
            success: function(result){
                // alert(result.status);
                // alert(result.data);
                if (result.data.resultCode.toString() === "0") {
                    params.senderKey = result.data.senderKey.toString();

                    // 그룹-계정등록 체크
                    $scope.registerGroupChk(params);
                }
                else if (result.data.resultCode.toString() !== "0") {
                    $scope._popMsg(result.data.resultMessage.toString());
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
                // if (result.status === "OK") {
                //     $scope._popMsg("저장되었습니다.");
                //     $scope.close();
                // }
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
            }
        });
    };

    // 그룹-계정등록 체크
    $scope.registerGroupChk = function(params) {
        $scope._postJSONQuery.withOutPopUp( "/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkRegisterGroupChk.sb", params, function(response){
            var alimtalkGroupInfo = response.data.data.result;
            $scope.alimtalkGroupInfo = alimtalkGroupInfo;

            if(response.data.data.result != null) {
                $scope._popMsg("계정등록 되었습니다.");
                // 로딩바 hide
                $scope.$broadcast('loadingPopupInactive');
                // 팝업 닫기
                $scope.close();
            } else {
                // 그룹-계정등록 (그룹에 발신프로필 추가 API 호출 및 저장)
                $scope.registerGroupApi(params);
            }
        });
    };

    // 그룹-계정등록 (그룹에 발신프로필 추가 API 호출 및 저장)
    $scope.registerGroupApi = function(params) {
        $.ajax({
            type: "POST",
            url: "/adi/alimtalk/alimtalkSendType/alimtalkIdRegister/getAlimtalkSenderGroupApiSave.sb",
            data: JSON.stringify(params),
            cache: false,
            dataType: "json",
            contentType : 'application/json',
            success: function(result){
                // alert(result.status);
                // alert(result.data);
                if (result.data.resultCode.toString() === "0") {
                    $scope._popMsg("계정등록 되었습니다.");
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                    // 팝업 닫기
                    $scope.close();
                }
                else if (result.data.resultCode.toString() !== "0") {
                    // 해당 그룹-계정정보가 이미 NHN 사이트에 등록되있을때(-1018 This is a plusFriend that has already been added)
                    if(result.data.resultCode.toString() === "-1018") {
                        $scope._popMsg("계정등록 되었습니다.");
                        // 팝업 닫기
                        $scope.close();
                    } else {
                        $scope._popMsg(result.data.resultMessage.toString());
                    }

                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
                // if (result.status === "OK") {
                //     $scope._popMsg("저장되었습니다.");
                //     $scope.close();
                // }
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
            }
        });
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.plusFriendId = "";
        $scope.phoneNo = "";
        $scope.token = "";
        $scope.tokenYn = "";

        $scope._setComboData("categoryCodeLCombo", categoryCodeComboLData); // 사업자 카테고리(대분류)
        $scope._setComboData("categoryCodeMCombo", categoryCodeComboMData); // 사업자 카테고리(중분류)
        $scope._setComboData("categoryCodeSCombo", categoryCodeComboSData); // 사업자 카테고리(소분류)

        $("#srchPlusFriendId").attr("disabled",false);
        $("#srchCategoryCodeLCombo").attr("disabled",false);
        $("#srchCategoryCodeMCombo").attr("disabled",false);
        $("#srchCategoryCodeSCombo").attr("disabled",false);
        $("#srchPhoneNo").attr("disabled",false);

        $scope.wjAlimtalkIdRegisterLayer.hide();
    };
}]);