/****************************************************************
 *
 * 파일명 : restSmsAmt.js
 * 설  명 : 현재잔여금액 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.15     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  현재잔여금액 팝업 조회 그리드 생성
 */
app.controller('restSmsAmtCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('restSmsAmtCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("restSmsAmtCtrl", function(event, data) {
        $scope.searchRestSmsAmt();
        event.preventDefault();
    });

    $scope.searchRestSmsAmt = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp( "/adi/sms/smsCharge/msgOneAmtGuide/getMsgOneAmtGuideList.sb", params, function(response){
            var msgOneAmtGuide = response.data.data.result;
            $scope.msgOneAmtGuide = msgOneAmtGuide;

            $("#restSmsAmtRestSmsAmt").val($scope.msgOneAmtGuide.smsAmt);
        });
    };
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $("#restSmsAmtRestSmsAmt").val("");

        $scope.wjRestSmsAmtLayer.hide();
    };
}]);