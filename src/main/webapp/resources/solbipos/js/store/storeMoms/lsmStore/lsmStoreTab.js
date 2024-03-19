/****************************************************************
 *
 * 파일명 : lsmStoreTab.js
 * 설  명 : 맘스터치 > 매장관리 > LSM사용매장조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.03.18     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('lsmStoreTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#lsmStoreView").show();

    };

    // 터치키 탭 보이기
    $scope.tukeyStoreChangeShow = function () {
        $("#tukeyStoreTab").addClass("on");
        $("#kioskStoreTab").removeClass("on");

        $("#lsmStoreView").show();
        $("#lsmKioskStoreView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("lsmStoreCtrl");
        scope.flex.refresh();
    };

    // 키오스크 탭 보이기
    $scope.kioskStoreChangeShow = function () {
        $("#tukeyStoreTab").removeClass("on");
        $("#kioskStoreTab").addClass("on");

        $("#lsmStoreView").hide();
        $("#lsmKioskStoreView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("lsmKioskStoreCtrl");
        scope.flex.refresh();
    };

}]);