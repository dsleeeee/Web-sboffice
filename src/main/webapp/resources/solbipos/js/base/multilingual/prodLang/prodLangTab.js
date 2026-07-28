/****************************************************************
 *
 * 파일명 : prodLangTab.js
 * 설  명 : 다국어관리(상품) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.12.28     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('prodLangTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {

        $("#prodNmView").show();
        $("#prodInfoView").hide();
        $("#prodPlatformView").hide();
    };

    // 상품명 탭 보이기
    $scope.prodNmShow = function () {
        $("#prodNmTab").addClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#prodPlatformTab").removeClass("on");

        $("#prodNmView").show();
        $("#prodInfoView").hide();
        $("#prodPlatformView").hide();

        //
        $scope._broadcast("prodNmCtrl");
    };

    //  상품정보 탭 보이기
    $scope.prodInfoShow = function (){
        $("#prodNmTab").removeClass("on");
        $("#prodInfoTab").addClass("on");
        $("#prodPlatformTab").removeClass("on");

        $("#prodNmView").hide();
        $("#prodInfoView").show();
        $("#prodPlatformView").hide();

        //
        $scope._broadcast("prodInfoCtrl");
    };

    // 플랫폼 상품명 탭 보이기
    $scope.prodPlatformShow = function (){
        $("#prodNmTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#prodPlatformTab").addClass("on");

        $("#prodNmView").hide();
        $("#prodInfoView").hide();
        $("#prodPlatformView").show();

        //
        $scope._broadcast("prodPlatformCtrl");
    };

}]);