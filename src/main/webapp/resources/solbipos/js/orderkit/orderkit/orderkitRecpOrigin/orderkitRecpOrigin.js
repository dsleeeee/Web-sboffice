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
        $scope._postJSONQuery.withOutPopUp('/orderkit/orderkit/orderkitRecpOrigin/orderkitGoto.sb', params, function (response) {

            // jwtToken
            var jwtToken = response.data.data;

            // OMS웹뷰 새창 open
            var url = "https://kcp.onesell.co.kr/auth/pos?token=" + jwtToken; // 개발
            //var url = "https://orderkit.co.kr/auth/pos?token=" + jwtToken; // 운영
            window.open(url, 'newWindow');

        });

    }

}]);