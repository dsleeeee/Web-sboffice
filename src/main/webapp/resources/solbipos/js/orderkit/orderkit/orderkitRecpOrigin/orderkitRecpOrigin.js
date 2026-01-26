/****************************************************************
 *
 * 파일명 : orderkitRecpOrigin.js
 * 설  명 : 오더킷 > 오더킷 > 원산지 정보 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.11.05     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** 원산지 정보 controller */
app.controller('orderkitRecpOriginCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderkitRecpOriginCtrl', $scope, $http, false));
    
    $scope.$on("orderkitRecpOriginCtrl", function(event, data) {

        // 원산지 정보 조회
        $scope.searchRecpOrigin();
        event.preventDefault();
    });
    
    // 원산지 정보 조회
    $scope.searchRecpOrigin = function () {

        var params = {};
        params.prtCd = '{원산지관리-정보입력}';

        $scope._postJSONQuery.withPopUp("/orderkit/orderkit/orderkitRecpOrigin/getOrderkitRecpOrigin.sb", params, function (response) {
            var result = response.data.data.list;

            var msg = '';
            msg = nvl(result[0].originTxt1, '') + nvl(result[0].originTxt2, '') + nvl(result[0].originTxt3, '')
                + nvl(result[0].originTxt4, '') + nvl(result[0].originTxt5, '');

            $("#recpOriginInfoDetail").val(msg);

        }, false);
    };

    // 오더킷 바로가기
    $scope.orderkitGoto = function () {

        var params = {};
        params.apiStoreYn = "N"; // 연동상태 저장여부

        var redirectUrl = "";
        var url = "https://test.orderkit.co.kr"; // 개발
        //var url = "https://orderkit.co.kr"; // 운영

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getOmsUserStatus.sb", params, function (response) {

            var data = response.data.data.list;

            if (data.status === "success" && data.status_code === 200) {

                if (data.data.subscriptionStatus == "EXPIRED" || data.data.subscriptionStatus == "CANCELLED") { // 만료, 해지완료
                    redirectUrl = "/app/dashboard";
                } else if (data.data.subscriptionStatus == "UNPAID") { // 결제 이전
                    redirectUrl = "/app/payment/pay";
                } else if (data.data.subscriptionStatus == "ACTIVE" || data.data.subscriptionStatus == "GRACE" || data.data.subscriptionStatus == "REQ_CANCEL") { // 활성화, 유예, 해지요청
                    if (data.data.base_platform_info.platform === null) { // 배달앱 미연동
                        redirectUrl = "/app/setting/platform";
                    }
                    if (data.data.base_platform_info.platform !== null) { // 정상 연동
                        redirectUrl = "/app/dashboard";
                    }
                } else {
                    redirectUrl = ""
                }

            } else { // data.status === "error" && data.status_code === 500 인 상태
                redirectUrl = ""
            }

            params.redirectUrl = redirectUrl;
            $scope._postJSONQuery.withOutPopUp('/orderkit/orderkit/orderkitRecpOrigin/orderkitGoto.sb', params, function (response) {

                // jwtToken
                var jwtToken = response.data.data;

                if (redirectUrl !== "") {
                    console.log("url : " + url + "/auth/pos/url?token=" + jwtToken);
                    window.open(url + "/auth/pos/url?token=" + jwtToken, 'newWindow');
                } else {
                    console.log("url : " + url + "/auth/pos?token=" + jwtToken);
                    window.open(url + "/auth/pos?token=" + jwtToken, 'newWindow');
                }

            });

        });
    };

}]);