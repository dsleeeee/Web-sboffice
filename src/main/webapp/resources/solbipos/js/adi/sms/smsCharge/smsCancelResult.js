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
            if(data.resultcode == "0000") {
                $("#lblContentSmsCancelResult").text("취소요청이 완료되었습니다.");
                $("#resultcodeSmsCancelResult").val(data.resultcode);
                $("#resultmessageSmsCancelResult").val(data.resultmessage);
            } else {
                $("#lblContentSmsCancelResult").text("취소요청이 처리 되지 못하였습니다.");
                $("#resultcodeSmsCancelResult").val(data.resultcode);
                $("#resultmessageSmsCancelResult").val(data.resultmessage);
            }
        } else {
            $("#lblContentSmsCancelResult").text("잘못된 경로 입니다.");
            $("#resultcodeSmsCancelResult").val("");
            $("#resultmessageSmsCancelResult").val("");
        }
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $("#lblContentSmsCancelResult").text("");
        $("#resultcodeSmsCancelResult").val("");
        $("#resultmessageSmsCancelResult").val("");

        var smsChargeDtlScope = agrid.getScope('smsChargeDtlCtrl');
        smsChargeDtlScope.close();

        $scope.wjSmsCancelResultLayer.hide();
    };
}]);