/****************************************************************
 *
 * 파일명 : storeEmpSave.js
 * 설  명 : 매장사원정보 등록 및 수정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.23     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

var mainSaleFgComboData = [
    {"name": "사용", "value": "0"},
    {"name": "미사용", "value": "1"}
];

/**
 * 매장사원 등록 및 수정
 */
app.controller('storeEmpRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeEmpRegistCtrl', $scope, $http, false));

    // 웹 사용여부
    $scope._setComboData('storeEmpWebUseYnComboData', useYnComboData);
    // 사용여부
    $scope._setComboData('storeEmpUseYnFgComboData', useYnComboData);
    // 메인화면매출표시
    $scope._setComboData('storeEmpMainSaleFgComboData', mainSaleFgComboData);
    // SMS수신여부
    $scope._setComboData('storeEmpSmsRecvYnComboData', smsRecvYn2);
    // 판매상품여부 콤보박스
    $scope._setComboData('storeEmpServiceFgComboData', serviceFg2);

    // 선택 사원 (사원 수정시)
    $scope.selectedHqEmp;

    // 신규 수정 여부
    $scope.newEmpYn = 1; // 1: 신규등록, 2: 수정(WEB 사용), 3: 수정(WEB 미사용)

    // 웹사용자 아이디 중복체크 여부
    $scope.duplicationChkFg = "";

    // 사원정보
    $scope.storeEmpRegistInfo;
    $scope.setStoreEmpRegistInfo = function (emp) {
        $scope.storeEmpRegistInfo = emp;
    };
    $scope.getStoreEmpRegistInfo = function () {
        return $scope.storeEmpRegistInfo;
    };

    // 해당 scope 호출
    $scope.$on("storeEmpRegistCtrl", function (event, data) {

        $scope.selectedStoreEmp = data;

        if (isEmptyObject(data)) {

            $scope.storeEmpRegistInfo = {};
            $scope.storeEmpRegistInfo.webUseYn = 'Y';
            $scope.storeEmpRegistInfo.smsRecvYn = 'N';
            $scope.storeEmpRegistInfo.serviceFg = '1';
            $scope.storeEmpRegistInfo.useYn = 'Y';
            $scope.storeEmpRegistInfo.mainSaleFg = '0';
            $scope.newEmpYn = 1; // 신규등록

        } else {

          $scope.getStoreEmpList();
        }

        event.preventDefault();
    });

    // 매장사원정보관리 그리드 조회
    $scope.getStoreEmpList = function(){

        var params = $scope.selectedStoreEmp;

        $scope._postJSONQuery.withOutPopUp("/base/store/emp/store/detail.sb", params, function (response) {
            $scope.storeEmpRegistInfo = response.data.data;
            $scope.storeEmpRegistInfo.empInfo = ' [' + response.data.data.empNo + ']' + response.data.data.empNm;
            $scope.storeEmpRegistInfo.originalWebUserId = response.data.data.userId;
            $scope.duplicationChkFg                     = response.data.data.userId;

            if (response.data.data.userId != null && response.data.data.userId != undefined && response.data.data.userId != "") {
                $scope.newEmpYn = 2; // 수정(WEB 사용)
            } else {
                $scope.newEmpYn = 3; // 수정(WEB 미사용)
            }
        });
    };

    // 아이디 정책 및 중복 체크
    $scope.checkDuplicate = function () {

        if (isEmptyObject($scope.storeEmpRegistInfo.userId)) {
            $scope._popMsg(messages["storeEmp.userId"] + messages["cmm.require.text"]);
            return false;
        }

        var params = {};
        params.userId = $scope.storeEmpRegistInfo.userId;

        $scope._postJSONQuery.withPopUp("/base/store/emp/store/chkStoreUserId.sb", params, function (response) {

            var result = response.data.data;

            if (result == "SUCCESS") {
                $scope.duplicationChkFg = $scope.storeEmpRegistInfo.userId;
                $scope._popMsg(messages["storeEmp.notDuplicate.msg"]);
            } else if (result === "USER_ID_REGEXP") {
                $scope._popMsg(messages["storeEmp.userIdRegexp.msg"]);
            } else if (result === "USER_ID_LENGHTH_REGEXP") {
                $scope._popMsg(messages["storeEmp.userIdLengthRegexp.msg"]);
            } else if (result === "USER_ID_CANNOT_USE_HANGEUL") {
                $scope._popMsg(messages["storeEmp.userIdNotUseHangeul.msg"]);
            } else if (result === "USER_ID_MUST_CONTAIN_ENG_CAHR") {
                $scope._popMsg(messages["storeEmp.userIdContainEngChar.msg"]);
            } else if (result === "USER_ID_ONLY_ENG_NUM_CHAR") {
                $scope._popMsg(messages["storeEmp.userIdOnlyEnvNumChar.msg"]);
            } else if (result === "USER_ID_DUPLICATE") {
                $scope._popMsg(messages["storeEmp.userId.duplicate.msg"]);
            } else {
                $scope._popMsg(messages["storeEmp.userId.notDuplicate.msg"]);
            }
        });
    };

    // 비밀번호 변경
    $scope.changePassword = function () {
        $scope.changePwdLayer.show(true);
    };

    // 신규등록
    $scope.regist = function () {

        if ($scope.storeEmpRegistInfo.webUseYn === 'Y') {

            /*웹사용자ID 중복체크*/
            if ($scope.duplicationChkFg === "") {
                $scope._popMsg(messages["storeEmp.require.chk.userId"]);
                return false;
            }

            /*웹사용자ID 중복체크2*/
            if ($scope.storeEmpRegistInfo.userId !== $scope.duplicationChkFg) {
                $scope._popMsg(messages["storeEmp.require.chk.userId"]);
                return false;
            }

            // 비밀번호, 비밀번호 확인 체크
            if ($scope.storeEmpRegistInfo.userPwd !== $scope.storeEmpRegistInfo.userPwdCfm) {
                $scope._popMsg(messages["storeEmp.passwordNotMatch.msg"]);
                return false;
            }
        } else {
            $scope.storeEmpRegistInfo.userId = "";
            $scope.storeEmpRegistInfo.userPwd = "";
            $scope.storeEmpRegistInfo.userPwdCfm = "";
        }

        var params = $scope.storeEmpRegistInfo;
        params.pwdChgFg = false; // 비밀번호 변경여부

        $scope._postJSONSave.withOutPopUp("/base/store/emp/store/regist.sb", params, function (response) {

            if (response.data.data == 'SUCCESS') {
                $scope._popMsg(messages["cmm.registSucc"]);
                $scope.storeEmpRegistLayer.hide();
            } else if (response.data.data === 'USER_ID_REGEXP') {
                $scope._popMsg(messages["storeEmp.userIdRegexp.msg"]);
                return false;
            } else if (response.data.data === 'PASSWORD_REGEXP') {
                $scope._popMsg(messages["login.pw.cannot"]);
                return false;
            } else {
                $scope._popMsg(messages["cmm.registFail"]);
                return false;
            }
        });
    };

    // 저장
    $scope.save = function () {

        // 한번도 웹 사용한적 없는경우
        if ($scope.storeEmpRegistInfo.originalWebUserId == '' || $scope.storeEmpRegistInfo.originalWebUserId == undefined || $scope.storeEmpRegistInfo.originalWebUserId == null) {
            // 웹 사용여부 'Y'로 변경시
            if ($scope.storeEmpRegistInfo.webUseYn === 'Y') {

                /*웹사용자ID 중복체크*/
                if ($scope.duplicationChkFg === "") {
                    $scope._popMsg(messages["storeEmp.require.chk.userId"]);
                    return false;
                }

                /*웹사용자ID 중복체크2*/
                if ($scope.storeEmpRegistInfo.userId !== $scope.duplicationChkFg) {
                    $scope._popMsg(messages["storeEmp.require.chk.userId"]);
                    return false;
                }

                // 비밀번호, 비밀번호 확인 체크
                if ($scope.storeEmpRegistInfo.userPwd !== $scope.storeEmpRegistInfo.userPwdCfm) {
                    $scope._popMsg(messages["storeEmp.passwordNotMatch.msg"]);
                    return false;
                }
            } else { // 웹 사용여부 'N'로 변경시
                $scope.storeEmpRegistInfo.userId = "";
                $scope.storeEmpRegistInfo.userPwd = "";
                $scope.storeEmpRegistInfo.userPwdCfm = "";
            }
        }

        var params = $scope.storeEmpRegistInfo;
        params.pwdChgFg = false; // 비밀번호 변경여부

        $scope._postJSONSave.withOutPopUp("/base/store/emp/store/save.sb", params, function (response) {

            if (response.data.data == 'SUCCESS') {
                $scope._popMsg(messages["cmm.saveSucc"]);
                $scope.storeEmpRegistLayer.hide();
            } else if (response.data.data === 'PASSWORD_REGEXP') {
                $scope._popMsg(messages["login.pw.cannot"]);
                return false;
            } else if (response.data.data === 'PASSWORD_NOT_CHANGED') {
                $scope._popMsg(messages["storeEmp.passwordNotChanged.msg"]);
                return false;
            } else {
                $scope._popMsg(messages["cmm.saveFail"]);
                return false;
            }
        });
    };

    // 닫기버튼 클릭
    $scope.close = function () {
        $scope.storeEmpRegistLayer.hide();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 비밀번호 변경 팝업 핸들러 추가
        $scope.changePwdLayer.shown.addHandler(function (s) {
            setTimeout(function () {
                var params = $scope.storeEmpRegistInfo;
                params.empFg = 'S'; // 매장 사원
                $scope._broadcast('changePwdCtrl', params);
            }, 50);
        });
    });

    // 탭변경
    $scope.changeTab = function () {
        s_alert.pop(messages["storeEmp.request.regist.storeEmpInfo"]);
        return;
    };

}]);
