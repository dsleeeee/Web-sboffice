/****************************************************************
 *
 * 파일명 : side.js
 * 설  명 : 기초관리 > 상품관리 > 상품-매장주방프린터 연결 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.10     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('kitchenprintCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#prodKitchenprintLinkView").show();
        $("#printerGroupView").hide();
    };

    // 상품-매장주방프린터 연결 탭 보이기
    $scope.prodKitchenprintLinkShow = function () {
        $("#prodKitchenprintLinkTab").addClass("on");
        $("#printerGroupTab").removeClass("on");

        $("#prodKitchenprintLinkView").show();
        $("#printerGroupView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodKitchenprintLinkCtrl");
        scope.flex.refresh();
    };


    // 상품-매장주방프린터 연결 탭 보이기
    $scope.printerGroupShow = function () {
        $("#prodKitchenprintLinkTab").removeClass("on");
        $("#printerGroupTab").addClass("on");

        $("#prodKitchenprintLinkView").hide();
        $("#printerGroupView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("printerGroupCtrl");
        scope.flex.refresh();
    };

}]);