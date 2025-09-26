/****************************************************************
 *
 * 파일명 : storeProdLangTab.js
 * 설  명 : 다국어관리(상품)(매장) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.25     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeProdLangTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {

        $("#storeProdNmView").show();
        $("#storeProdInfoView").hide();
    };

    // 상품명 탭 보이기
    $scope.storeProdNmShow = function () {
        $("#storeProdNmTab").addClass("on");
        $("#storeProdInfoTab").removeClass("on");

        $("#storeProdNmView").show();
        $("#storeProdInfoView").hide();

        //
        $scope._broadcast("storeProdNmCtrl");
    };

    //  상품정보 탭 보이기
    $scope.storeProdInfoShow = function () {
        $("#storeProdNmTab").removeClass("on");
        $("#storeProdInfoTab").addClass("on");

        $("#storeProdNmView").hide();
        $("#storeProdInfoView").show();

        //
        $scope._broadcast("storeProdInfoCtrl");
    };

}]);