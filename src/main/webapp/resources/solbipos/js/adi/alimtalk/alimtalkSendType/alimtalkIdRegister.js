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

    // 인증요청하기
    $scope.registerRequest = function() {
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
        params.plusFriendId = $scope.plusFriendId;
        params.categoryCode = $scope.categoryCodeLCombo + $scope.categoryCodeMCombo + $scope.categoryCodeSCombo;
        params.phoneNo = $scope.phoneNo;

        // alert(params.plusFriendId);
        // alert(params.categoryCode);
        // alert(params.phoneNo);

        alert("준비중");
    };

    // 저장
    $scope.registerSave = function() {
        alert("준비중");
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.plusFriendId = "";
        $scope.phoneNo = "";
        $scope.token = "";

        $scope._setComboData("categoryCodeLCombo", categoryCodeComboLData); // 사업자 카테고리(대분류)
        $scope._setComboData("categoryCodeMCombo", categoryCodeComboMData); // 사업자 카테고리(중분류)
        $scope._setComboData("categoryCodeSCombo", categoryCodeComboSData); // 사업자 카테고리(소분류)

        $scope.wjAlimtalkIdRegisterLayer.hide();
    };
}]);