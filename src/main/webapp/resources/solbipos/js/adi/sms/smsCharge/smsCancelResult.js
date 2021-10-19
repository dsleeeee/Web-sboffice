/****************************************************************
 *
 * 파일명 : smsCancelResult.js
 * 설  명 : SMS결제취소 결과 팝업 JavaScript
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

/**
 *  SMS결제취소 결과 팝업 조회 그리드 생성
 */
app.controller('smsCancelResultCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsCancelResultCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("smsCancelResultCtrl", function(event, data) {
        if(data != undefined) {
            if(data.resCd == "0000") {
                $("#lblContentSmsCancelResult").text("취소요청이 완료되었습니다.");
                $("#lblContentSmsCancelResult2").text("결과코드 : " + data.resCd);
                $("#lblContentSmsCancelResult3").text("결과메세지 : " + data.resMsg);

                $("#lblContentSmsCancelResult").css("color", "blue");
            } else {

                $("#lblContentSmsCancelResult").text("취소요청이 처리 되지 못하였습니다.");
                $("#lblContentSmsCancelResult2").text("결과코드 : " + data.resCd);
                $("#lblContentSmsCancelResult3").text("결과메세지 : " + data.resMsg);

                $("#lblContentSmsCancelResult").css("color", "red");
            }
        } else {
            $("#lblContentSmsCancelResult").text("잘못된 경로 입니다.");
            $("#lblContentSmsCancelResult2").text("");
            $("#lblContentSmsCancelResult3").text("");

            $("#lblContentSmsCancelResult").css("color", "black");
        }
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $("#lblContentSmsCancelResult").text("");
        $("#lblContentSmsCancelResult2").text("");
        $("#lblContentSmsCancelResult3").text("");

        $("#lblContentSmsCancelResult").css("color", "black");

        var smsChargeDtlScope = agrid.getScope('smsChargeDtlCtrl');
        smsChargeDtlScope.close();

        $scope.wjSmsCancelResultLayer.hide();
    };
}]);