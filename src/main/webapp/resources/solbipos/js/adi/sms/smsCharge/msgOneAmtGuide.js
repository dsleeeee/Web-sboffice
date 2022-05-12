/****************************************************************
 *
 * 파일명 : msgOneAmtGuide.js
 * 설  명 : 메세지 건당 가격안내 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.03     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  메세지 건당 가격안내 팝업 조회 그리드 생성
 */
app.controller('msgOneAmtGuideCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('msgOneAmtGuideCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("msgOneAmtGuideCtrl", function(event, data) {
        $scope.searchMsgOneAmtGuide();

        // if(pageGubunVal == "sms") {
        //     $("#divMsgOneAmtGuideSms").css("display", "");
        //     $("#divMsgOneAmtGuideAlk").css("display", "none");
        // }
        // else if (pageGubunVal == "alimtalk") {
        //     $("#divMsgOneAmtGuideSms").css("display", "none");
        //     $("#divMsgOneAmtGuideAlk").css("display", "");
        // }
        event.preventDefault();
    });

    $scope.searchMsgOneAmtGuide = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp( "/adi/sms/smsCharge/msgOneAmtGuide/getMsgOneAmtGuideList.sb", params, function(response){
            var msgOneAmtGuide = response.data.data.result;
            $scope.msgOneAmtGuide = msgOneAmtGuide;

            $("#msgOneAmtGuideSmsOneAmt").val($scope.msgOneAmtGuide.smsOneAmt);
            $("#msgOneAmtGuideLmsOneAmt").val($scope.msgOneAmtGuide.lmsOneAmt);
            $("#msgOneAmtGuideMmsOneAmt").val($scope.msgOneAmtGuide.mmsOneAmt);
            $("#msgOneAmtGuideAlkOneAmt").val($scope.msgOneAmtGuide.alkOneAmt);
            // $("#msgOneAmtGuideAlkSmsOneAmt").val($scope.msgOneAmtGuide.alkSmsOneAmt);
            $("#msgOneAmtGuideAlkLmsOneAmt").val($scope.msgOneAmtGuide.alkLmsOneAmt);
            // $("#msgOneAmtGuideAlkMmsOneAmt").val($scope.msgOneAmtGuide.alkMmsOneAmt);
        });
    };
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $("#msgOneAmtGuideSmsOneAmt").val("");
        $("#msgOneAmtGuideLmsOneAmt").val("");
        $("#msgOneAmtGuideMmsOneAmt").val("");
        $("#msgOneAmtGuideAlkOneAmt").val("");
        // $("#msgOneAmtGuideAlkSmsOneAmt").val("");
        $("#msgOneAmtGuideAlkLmsOneAmt").val("");
        // $("#msgOneAmtGuideAlkMmsOneAmt").val("");

        $scope.wjMsgOneAmtGuideLayer.hide();
    };
}]);