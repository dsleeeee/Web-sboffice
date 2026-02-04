/****************************************************************
 *
 * 파일명 : naverPlaceLink.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.01.27     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** 네이버플레이스 연동 controller */
app.controller('naverPlaceLinkCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceLinkCtrl', $scope, $http, false));

    $scope.$on("naverPlaceLinkCtrl", function (event, data) {

    });

    // 인증 API Access Token 조회
    $scope.btn1 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/getAccessToken.sb", params, function (response) {
            var data = response.data.data.list;
            $scope._popMsg(data.token);
        });
    };

    // 동의여부확인 API 호출
    $scope.btn3 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/getAgreeYn.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

    // 업체목록조회 API 호출
    $scope.btn5 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/getPlaceList.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

    // 업체 등록/수정 API 호출
    $scope.btn7 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/savePlace.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

    // 연동 추가 API
    $scope.btn8 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/mappingPlace.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

    // 연동 해지 API
    $scope.btn9 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/unMappingPlace.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

}]);