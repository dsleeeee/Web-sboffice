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

/**
 *  잔여금액 알림 설정 팝업 조회 그리드 생성
 */
app.controller('restSmsAmtAlimSettingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('restSmsAmtAlimSettingCtrl', $scope, $http, false));

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

            // 잔여금액 설정값
            $scope.rmSmsAmtChange($scope.restSmsAmtAlimSetting.rmSmsAmt);
            $scope.restSmsAmtAlimSettingTelNo = $scope.restSmsAmtAlimSetting.telNo;
        });
    };
    // <-- //검색 호출 -->

    // 잔여금액 설정값
    $scope.rmSmsAmtChange = function(rmSmsAmt){
        $("#lblRmSmsAmt").text(rmSmsAmt);

        if(rmSmsAmt == null || rmSmsAmt == "") {
            $("#btRmSmsAmt1").css("background","#1e88e5");
            $("#btRmSmsAmt2").css("background","#aeaeae");
            $("#btRmSmsAmt3").css("background","#aeaeae");
            $("#btRmSmsAmt4").css("background","#aeaeae");
            $("#btRmSmsAmt5").css("background","#aeaeae");
            $("#btRmSmsAmt6").css("background","#aeaeae");
        } else if(rmSmsAmt == 5000) {
            $("#btRmSmsAmt1").css("background","#aeaeae");
            $("#btRmSmsAmt2").css("background","#1e88e5");
            $("#btRmSmsAmt3").css("background","#aeaeae");
            $("#btRmSmsAmt4").css("background","#aeaeae");
            $("#btRmSmsAmt5").css("background","#aeaeae");
            $("#btRmSmsAmt6").css("background","#aeaeae");
        } else if(rmSmsAmt == 10000) {
            $("#btRmSmsAmt1").css("background","#aeaeae");
            $("#btRmSmsAmt2").css("background","#aeaeae");
            $("#btRmSmsAmt3").css("background","#1e88e5");
            $("#btRmSmsAmt4").css("background","#aeaeae");
            $("#btRmSmsAmt5").css("background","#aeaeae");
            $("#btRmSmsAmt6").css("background","#aeaeae");
        } else if(rmSmsAmt == 20000) {
            $("#btRmSmsAmt1").css("background","#aeaeae");
            $("#btRmSmsAmt2").css("background","#aeaeae");
            $("#btRmSmsAmt3").css("background","#aeaeae");
            $("#btRmSmsAmt4").css("background","#1e88e5");
            $("#btRmSmsAmt5").css("background","#aeaeae");
            $("#btRmSmsAmt6").css("background","#aeaeae");
        } else if(rmSmsAmt == 50000) {
            $("#btRmSmsAmt1").css("background","#aeaeae");
            $("#btRmSmsAmt2").css("background","#aeaeae");
            $("#btRmSmsAmt3").css("background","#aeaeae");
            $("#btRmSmsAmt4").css("background","#aeaeae");
            $("#btRmSmsAmt5").css("background","#1e88e5");
            $("#btRmSmsAmt6").css("background","#aeaeae");
        } else if(rmSmsAmt == 100000) {
            $("#btRmSmsAmt1").css("background","#aeaeae");
            $("#btRmSmsAmt2").css("background","#aeaeae");
            $("#btRmSmsAmt3").css("background","#aeaeae");
            $("#btRmSmsAmt4").css("background","#aeaeae");
            $("#btRmSmsAmt5").css("background","#aeaeae");
            $("#btRmSmsAmt6").css("background","#1e88e5");
        }
    };

    // 저장
    $("#funcSave").click(function(e){
        // 잔여금액 설정값
        var rmSmsAmt = $("#lblRmSmsAmt").text();

        // 잔여금액 설정값이 미사용이 아니면
        if(rmSmsAmt !== null && rmSmsAmt !== "") {
            if ($scope.restSmsAmtAlimSettingTelNo === "" || $scope.restSmsAmtAlimSettingTelNo === undefined || $scope.restSmsAmtAlimSettingTelNo === null) {
                $scope._popMsg(messages["restSmsAmtAlimSetting.telNoAlert"]); // 알림받을번호를 입력해주세요.
                return false;
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.restSmsAmtAlimSettingTelNo)) {
                    $scope._popMsg(messages["restSmsAmtAlimSetting.telNoInChk"]); // 알림받을번호는 숫자만 입력해주세요.
                    return false;
                }
            }
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var params = {};
            params.rmSmsAmt = rmSmsAmt;
            params.telNo = $scope.restSmsAmtAlimSettingTelNo;
            // 잔여금액 설정값이 미사용이 아니면
            if(rmSmsAmt !== null && rmSmsAmt !== "") {
                params.rmSmsAmtYn = "Y";
                params.zeroSmsAmtYn = "Y";
            } else {
                params.rmSmsAmtYn = "N";
                params.zeroSmsAmtYn = "N";
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/smsCharge/restSmsAmtAlimSetting/getRestSmsAmtAlimSettingSave.sb", params, function(){ $scope.close() });
        });
    });

    // 팝업 닫기
    $scope.close = function(){
        // 잔여금액 설정값
        $scope.rmSmsAmtChange(null);
        $scope.restSmsAmtAlimSettingTelNo = "";

        $scope.wjRestSmsAmtAlimSettingLayer.hide();
    };
}]);