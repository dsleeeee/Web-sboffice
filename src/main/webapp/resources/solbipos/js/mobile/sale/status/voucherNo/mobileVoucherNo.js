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

// 조회해온 최종교환권번호(세션 안끊기고 제대로 조회했는지 파악)
var resNo = "";

/**
 *  최종교환권번호 view 화면 생성
 */
app.controller('mobileVoucherNoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileVoucherNoCtrl', $scope, $http, false));

    // <-- 검색 호출 -->
    $scope.$on("mobileVoucherNoCtrl", function(event, data) {

        // 초기화
        resNo = "";
        
        // 최종교환권번호 조회
        $scope.getVoucherNo();

        // 2초 내에 조회한 최종교환권번호가 없으면 화면 새로고침
        setTimeout(function() {

            console.log("result : " + resNo);

            if(resNo === null || resNo === ""){
                // 다시 화면 리로드 하여, 세션 연결 후 조회되도록 처리
                location.reload(true);
            }else{
                console.log("get VoucherNo : " + resNo);
            }
        }, 2000);

        event.preventDefault();
    });


    // 최종교환권번호 조회
    $scope.getVoucherNo = function () {

        var params = {};

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/mobileVoucherNo/getVoucherNo.sb", params, function(result) {

                if (result.data.status === "OK") {

                    // 조회한 최종교환권번호 변수에 셋팅
                    resNo = result.data.data;

                    // 화면에 표시
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