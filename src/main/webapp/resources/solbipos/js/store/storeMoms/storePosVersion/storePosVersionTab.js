/****************************************************************
 *
 * 파일명 : storePosVersionTab.js
 * 설  명 : 맘스터치 > 매장관리 > 매장포스버전현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.03.13     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storePosVersionTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storePosVersionView").show();

        if(hqOfficeCd === "DS034" || hqOfficeCd === "H0393" || hqOfficeCd === "DS021") {
            document.getElementById('posTab').style.display = '';
        }
    };

    // 매장포스버전현황 탭 보이기
    $scope.storePosVersionChangeShow = function () {
        $("#storePosVersionTab").addClass("on");
        $("#posPatchLogTab").removeClass("on");

        $("#storePosVersionView").show();
        $("#posPatchLogView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storePosVersionCtrl");
        scope.flex.refresh();
    };

    // 포스패치로그 탭 보이기
    $scope.posPatchLogChangeShow = function () {
        $("#storePosVersionTab").removeClass("on");
        $("#posPatchLogTab").addClass("on");

        $("#storePosVersionView").hide();
        $("#posPatchLogView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("posPatchLogCtrl");
        scope.flex.refresh();
    };

}]);