/****************************************************************
 *
 * 파일명 : mobileDirectVoucherNo.js
 * 설  명 : (모바일_QR) 매출현황 > 최종교환권번호 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.02     권지현      1.0
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

        $scope._postJSONQuery.withOutPopUp( "/mobile/direct/getSessionInfo.sb", params, function (result) {});
        
        // 최종교환권번호 조회
        $scope.getVoucherNo();

        event.preventDefault();
    });


    // 최종교환권번호 조회
    $scope.getVoucherNo = function () {
        var params = {};
        var url = unescape(location.href);
        //파라미터만 자르고, 다시 &그분자를 잘라서 배열에 넣는다.
        var paramArr = (url.substring(url.indexOf("?")+1,url.length)).split("&");

        for(var i = 0 ; i < paramArr.length ; i++){
            var temp = paramArr[i].split("="); //파라미터 변수명을 담음

            if(temp[0].toUpperCase() == "ACCESS_CD"){
                // 변수명과 일치할 경우 데이터 삽입
                params.accessCd = paramArr[i].split("=")[1];
                break;
            }
        }

        $scope._postJSONQuery.withOutPopUp("/mobile/sale/status/mobileVoucherNo/getVoucherNo.sb", params, function (result) {

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