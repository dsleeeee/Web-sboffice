/****************************************************************
 *
 * 파일명 : mobileVoucherNo.js
 * 설  명 : (모바일) 매출현황 > 최종교환권번호 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.11.16     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  최종교환권번호 view 화면 생성
 */
app.controller('mobileVoucherNoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileVoucherNoCtrl', $scope, $http, false));

    // <-- 검색 호출 -->
    $scope.$on("mobileVoucherNoCtrl", function(event, data) {

        // 최종교환권번호 조회
        $scope.getVoucherNo();
        event.preventDefault();
    });
    
    // 최종교환권번호 조회
    $scope.getVoucherNo = function () {

        var params = {};

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/mobileVoucherNo/getVoucherNo.sb", params, function(result) {

                if (result.data.status === "OK") {
                    $("#lblVoucherNo").text(result.data.data);
                }
            }
        );
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 최종교환권번호 조회
        $scope.getVoucherNo();
    });

}]);