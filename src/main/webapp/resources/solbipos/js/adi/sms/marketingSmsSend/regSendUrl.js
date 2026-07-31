/****************************************************************
 *
 * 파일명 : regSendUrl.js
 * 설  명 : 전송 URL 관리(화이트리스트 등록요청) 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.23     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 승인여부
var regSendUrlApprFg = [
    {"name":"요청","value":"0"},
    {"name":"승인","value":"1"},
    {"name":"반려","value":"2"}
];

// 발신번호 (조회 결과가 없을 때 대체)
var regSendUrlTelNoComboData = [
    {"name":"선택","value":""}
];

// URL 형식 체크
var regSendUrlPattern = /^(https?:\/\/|www\.)[^\s]+\.[a-z]{2,}(\/[^\s]*)?$/i;

app.controller('regSendUrlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regSendUrlCtrl', $scope, $http, $timeout, false));

    // 사용기간
    var usePeriod = wcombo.genDateVal("#regSendUrlUsePeriod", gvStartDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.apprFgDataMap = new wijmo.grid.DataMap(regSendUrlApprFg, 'value', 'name'); // 승인여부
    };

    // 발신번호 콤보 조회
    $scope.getTelNoCombo = function () {
        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsSend/getSmsTelNoComboList.sb', {}, function (response) {
            if (response.data.data.list.length > 0) {
                $scope._setComboData("telNoCombo", response.data.data.list);
            } else {
                $scope._setComboData("telNoCombo", regSendUrlTelNoComboData);
            }
        });
    };

    // 조회
    $scope.searchRegSendUrl = function () {
        var params = {};

        $scope._inquirySub("/adi/sms/marketingSmsSend/marketingSmsSend/getRegSendUrlList.sb", params, function () {}, false);
    };

    // 요청
    $scope.reqSendUrl = function () {

        if (isEmpty($scope.telNoCombo.selectedValue)) {
            $scope._popMsg(messages["regSendUrl.telNoBlank"]); // 발신번호를 선택하세요.
            return false;
        }

        if (isEmpty($("#sendUrl").val())) {
            $scope._popMsg(messages["regSendUrl.sendUrlBlank"]); // 전송URL을 입력하세요.
            return false;
        }

        if (isEmpty(usePeriod.value)) {
            $scope._popMsg(messages["regSendUrl.usePeriodBlank"]); // 사용기간을 선택하세요.
            return false;
        }

        if (!regSendUrlPattern.test($("#sendUrl").val())) {
            $scope._popMsg(messages["regSendUrl.sendUrlFormat"]); // URL 형식이 올바르지 않습니다.
            return false;
        }

        // 등록된 사용자(TB_CM_ADD_SMS_NO) 여부 체크
        var chkParams = {};
        chkParams.callback = $scope.telNoCombo.selectedValue;

        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsSend/getChkRegUserInfo.sb', chkParams, function (response) {
            if (response.data.data <= 0) {
                $scope._popMsg(messages["marketingSmsSend.chkRegUserInfo"]); // 전송/예약은 등록된 사용자 및 발신번호 만 가능합니다
                return false;
            }

            $scope._popConfirm(messages["regSendUrl.choo.req"], function() {

                var params = {};
                params.telNo = $scope.telNoCombo.selectedValue;
                params.sendUrl = $("#sendUrl").val();
                params.useStartDate = wijmo.Globalize.format(usePeriod.value, 'yyyyMMdd');

                $scope._postJSONSave.withPopUp("/adi/sms/marketingSmsSend/marketingSmsSend/saveRegSendUrl.sb", params, function () {
                    // 재조회
                    $scope.searchRegSendUrl();
                });
            });
        });
    };

    // 팝업 열릴 때 초기화
    $scope.init = function () {
        $scope.getTelNoCombo();
        $scope.searchRegSendUrl();
    };

    // 팝업 닫기
    $scope.close = function () {
        $scope.wjRegSendUrlLayer.hide();
        event.preventDefault();
    };
}]);
