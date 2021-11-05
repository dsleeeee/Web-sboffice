/****************************************************************
 *
 * 파일명 : smsChargeResult.js
 * 설  명 : SMS충전결제 결과 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.19     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 결제수단
var gpgresourceComboData = [
    {"name":"전체","value":""},
    {"name":"신용카드","value":"11"},
    {"name":"계좌이체","value":"21"},
    {"name":"휴대폰결제","value":"31"},
    {"name":"보너스","value":"99"},
    {"name":"임의등록","value":"*"}
];

/**
 *  SMS충전결제 결과 팝업 조회 그리드 생성
 */
app.controller('smsChargeResultCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsChargeResultCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("pgresourceSmsChargeResultCombo", gpgresourceComboData); // 결제수단

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("smsChargeResultCtrl", function(event, data) {
        if(data != undefined) {
            if(data.resultcode == "0000") {
                $("#lblContentSmsChargeResult").text("충전결제가 완료되었습니다.");
                $scope.pgresourceSmsChargeResultCombo.selectedValue = data.pgresource;
                $("#chargeTotSmsChargeResult").val(data.chargeTot.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#chargeDateSmsChargeResult").val(data.chargeDate.substring(0,4) + "-" + data.chargeDate.substring(4,6) + "-" + data.chargeDate.substring(6,8));
                $("#chargeTimeSmsChargeResult").val(data.chargeTime.substring(0,2) + ":" + data.chargeTime.substring(2,4) + ":" + data.chargeTime.substring(4,6));
                $("#chargeAmtSmsChargeResult").val(data.chargeAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#controlnoSmsChargeResult").val(data.controlno);
                $("#resultcodeSmsChargeResult").val(data.resultcode);
                $("#resultmessageSmsChargeResult").val(data.resultmessage);
            } else {
                $("#lblContentSmsChargeResult").text("충전결제가 처리 되지 못하였습니다.");
                $("#pgresourceSmsChargeResult").val("");
                $("#chargeTotSmsChargeResult").val("");
                $("#chargeDateSmsChargeResult").val("");
                $("#chargeTimeSmsChargeResult").val("");
                $("#chargeAmtSmsChargeResult").val("");
                $("#controlnoSmsChargeResult").val("");
                $("#resultcodeSmsChargeResult").val(data.resultcode);
                $("#resultmessageSmsChargeResult").val(data.resultmessage);
            }
        } else {
            $("#lblContentSmsChargeResult").text("잘못된 경로 입니다.");
            $("#pgresourceSmsChargeResult").val("");
            $("#chargeTotSmsChargeResult").val("");
            $("#chargeDateSmsChargeResult").val("");
            $("#chargeTimeSmsChargeResult").val("");
            $("#chargeAmtSmsChargeResult").val("");
            $("#controlnoSmsChargeResult").val("");
            $("#resultcodeSmsChargeResult").val("");
            $("#resultmessageSmsChargeResult").val("");
        }
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $("#lblContentSmsChargeResult").text("");
        $("#pgresourceSmsChargeResult").val("");
        $("#chargeTotSmsChargeResult").val("");
        $("#chargeDateSmsChargeResult").val("");
        $("#chargeTimeSmsChargeResult").val("");
        $("#chargeAmtSmsChargeResult").val("");
        $("#controlnoSmsChargeResult").val("");
        $("#resultcodeSmsChargeResult").val("");
        $("#resultmessageSmsChargeResult").val("");

        $scope.wjSmsChargeResultLayer.hide();

        var vTabVal = '5';
        // 가상로그인
        if(document.getElementsByName('sessionId')[0]) {
            var vSessionId = document.getElementsByName('sessionId')[0].value;
            location.href = '/adi/sms/smsChargeTab/smsChargeTab/list.sb?sid='+ vSessionId + '&tabVal='+ vTabVal;
        // 로그인
        } else {
            location.href = '/adi/sms/smsChargeTab/smsChargeTab/list.sb?tabVal='+ vTabVal;
        }
    };
}]);