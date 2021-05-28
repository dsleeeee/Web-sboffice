/****************************************************************
 *
 * 파일명 : recpOriginTab.js
 * 설  명 : 원산지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.03.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('recpOriginTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#prodRecpOriginView").show();
        $("#recpOriginView").hide();
    };

    // 원산지 탭 보이기
    $scope.recpOriginShow = function () {
        $("#prodRecpOriginTab").removeClass("on");
        $("#recpOriginTab").addClass("on");

        $("#prodRecpOriginView").hide();
        $("#recpOriginView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("recpOriginCtrl");
        scope.flex.refresh();
    };

    // 상품-원산지관리 탭 보이기
    $scope.prodRecpOriginShow = function () {
        $("#prodRecpOriginTab").addClass("on");
        $("#recpOriginTab").removeClass("on");

        $("#prodRecpOriginView").show();
        $("#recpOriginView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodRecpOriginCtrl");
        scope.flex.refresh();
        var scope1 = agrid.getScope("prodRecpOriginDetailCtrl");
        scope1.flex.refresh();
    };

}]);