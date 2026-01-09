/****************************************************************
 *
 * 파일명 : orderkit.js
 * 설  명 : 오더킷 > 오더킷 > 오더킷 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.30     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** 오더킷 controller */
app.controller('orderkitCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderkitCtrl', $scope, $http, false));

    $scope.$on("orderkitCtrl", function (event, data) {

    });

    // 오더킷(Order kit) 관리자 웹 이동
    $scope.openWeb = function () {

        var params = {};
        var url = "https://test.orderkit.co.kr/"; // 개발
        //var url = "https://orderkit.co.kr/"; // 운영

        if (jwtToken.length > 0) {

            // 유저 상태 조회(구독여부, 주문 중개 서비스 사용여부, 배달앱 연동 정보)
            $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getOmsUserStatus.sb", params, function (response) {

                var data = response.data.data.list;

                if (data.status === "success" && data.status_code === 200) {

                    if (data.data.paymentStatus == "SUSPENDED" || data.data.paymentStatus == "GRACE" || data.data.paymentStatus == "WITHDRAW") { // 사용중지, 유예, 탈퇴
                        console.log("url : " + url + "auth/pos?token=" + jwtToken);
                        window.open(url + "auth/pos?token=" + jwtToken, 'newWindow');
                    }

                    if (data.data.paymentStatus == "UNPAID") { // 미결제
                        console.log("url : " + url + "app/payment/pay?token=" + jwtToken);
                        window.open(url + "app/payment/pay?token=" + jwtToken, 'newWindow');
                    }

                    if (data.data.paymentStatus == "ACTIVE") { // 결제 완료

                        if (data.data.base_platform_info === null) { // 배달앱 미연동
                            console.log("url : " + url + "app/menu/collect?token=" + jwtToken);
                            window.open(url + "app/menu/collect?token=" + jwtToken, 'newWindow');
                        }

                        if (data.data.base_platform_info !== null) { // 정상 연동
                            console.log("url : " + url + "app/dashboard?token=" + jwtToken);
                            window.open(url + "app/dashboard?token=" + jwtToken, 'newWindow');
                        }
                    }

                } else { // data.status === "error" && data.status_code === 500 인 상태, 비회원
                    console.log("url : " + url + "auth/pos?token=" + jwtToken);
                    window.open(url + "auth/pos?token=" + jwtToken, 'newWindow');
                }
            });
        }
    }

}]);