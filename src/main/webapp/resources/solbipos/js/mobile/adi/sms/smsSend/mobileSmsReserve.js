/****************************************************************
 *
 * 파일명 : mobileSmsReserve.js
 * 설  명 : (모바일) SMS예약 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 시 VALUE
// var Hh = [24];
// for(i =0 ; i < 24; i++){
//     var timeVal = i.toString();
//     if(i>=0 && i<=9){
//         timeVal = "0" + timeVal;
//     }
//     Hh[i] = {"name":timeVal,"value":timeVal}
// }

// 전송가능 시간 체크(09~21시)
// 시 VALUE
var Hh = [13];
for(i =9 ; i < 22; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    Hh[i-9] = {"name":timeVal,"value":timeVal}
}

// 분 VALUE
var Dd = [60];
for(i =0 ; i < 60; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    Dd[i] = {"name":timeVal,"value":timeVal}
}

/**
 *  SMS예약 팝업 조회 그리드 생성
 */
app.controller('mobileSmsReserveCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileSmsReserveCtrl', $scope, $http, false));

    // 예약시간
    var startDateSmsReserve = wcombo.genDateVal("#startDateSmsReserve", getToday());
    $scope._setComboData("startTimeSmsReserveCombo", Hh);
    $scope._setComboData("startMinuteSmsReserveCombo", Dd);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileSmsReserveCtrl", function(event, data) {
        $("#lblSmsReserveReserveYn").text(data.reserveYn);
        $("#lblSmsReserveGubun").text(data.gubun);
        $("#lblSmsReserveMsgType").text(data.msgType);
        $("#lblSmsReserveMsgOneAmt").text(data.msgOneAmt);
        $("#lblSmsReserveSmsSendListCnt").text(data.smsSendListCnt);

        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 예약전송
    $scope.smsReserveSave = function() {
        var startDate = wijmo.Globalize.format(startDateSmsReserve.value, 'yyyyMMdd');
        var reserveDate = startDate + $scope.startTimeSmsReserveCombo + $scope.startMinuteSmsReserveCombo + "00";
        var reserveYn = $("#lblSmsReserveReserveYn").text();
        var gubun = $("#lblSmsReserveGubun").text();
        var msgType = $("#lblSmsReserveMsgType").text();
        var msgOneAmt = $("#lblSmsReserveMsgOneAmt").text();
        var smsSendListCnt = $("#lblSmsReserveSmsSendListCnt").text();

        if(parseInt(reserveDate) < parseInt(getCurDateTime())) {
            $scope._popMsg(messages["mobile.smsReserve.reserveTimeAlert"]); // 예약시간은 현재시간 이후로 가능합니다.
            return false;
        }

        // 전송 저장
        // if(gubun == "smsSend") {
        //     // SMS전송 팝업
        //     var smsReserveScope = agrid.getScope('smsSendCtrl');
        //
        // } else
        if(gubun == "marketingSmsSend") {
            // 마케팅용 SMS전송
            var smsReserveScope = agrid.getScope('mobileMarketingSmsSendCtrl');
        }
        smsReserveScope.smsSendSave(reserveYn, reserveDate, msgType, msgOneAmt, smsSendListCnt);

        // 팝업 닫기
        $scope.close();
    };

    // 팝업 닫기
    $scope.close = function() {
        $("#lblSmsReserveReserveYn").text("");
        $("#lblSmsReserveGubun").text("");
        $("#lblSmsReserveMsgType").text("");
        $("#lblSmsReserveMsgOneAmt").text("");
        $("#lblSmsReserveSmsSendListCnt").text("");

        $scope.wjMobileSmsReserveLayer.hide();
        event.preventDefault();
    };
}]);