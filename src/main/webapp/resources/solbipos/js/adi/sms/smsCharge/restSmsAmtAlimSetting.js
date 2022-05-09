/****************************************************************
 *
 * 파일명 : restSmsAmtAlimSetting.js
 * 설  명 : 잔여금액 알림 설정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.09     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 잔여금액 알림값
var rmSmsAmtDataMapData = [
    {"name":"미사용","value":-99999},
    {"name":"5,000","value":5000},
    {"name":"10,000","value":10000},
    {"name":"20,000","value":20000},
    {"name":"50,000","value":50000},
    {"name":"100,000","value":100000}
];

/**
 *  잔여금액 알림 설정 팝업 조회 그리드 생성
 */
app.controller('restSmsAmtAlimSettingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('restSmsAmtAlimSettingCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("rmSmsAmtCombo", rmSmsAmtDataMapData); // 잔여금액 알림값

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("restSmsAmtAlimSettingCtrl", function(event, data) {
        $scope.searchRestSmsAmtAlimSetting();
        event.preventDefault();
    });

    $scope.searchRestSmsAmtAlimSetting = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp( "/adi/sms/smsCharge/restSmsAmtAlimSetting/getRestSmsAmtAlimSettingList.sb", params, function(response){
            var restSmsAmtAlimSetting = response.data.data.result;
            $scope.restSmsAmtAlimSetting = restSmsAmtAlimSetting;

            $scope.rmSmsAmt = $scope.restSmsAmtAlimSetting.rmSmsAmt;
        });
    };
    // <-- //검색 호출 -->

    // 저장
    $("#funcSave").click(function(e){
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var params = {};
            params.rmSmsAmt = $scope.rmSmsAmt;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/smsCharge/restSmsAmtAlimSetting/getRestSmsAmtAlimSettingSave.sb", params, function(){ $scope.close() });
        });
    });

    // 팝업 닫기
    $scope.close = function(){
        $scope.srchRmSmsAmtCombo.selectedIndex = 0;

        $scope.wjRestSmsAmtAlimSettingLayer.hide();
    };
}]);