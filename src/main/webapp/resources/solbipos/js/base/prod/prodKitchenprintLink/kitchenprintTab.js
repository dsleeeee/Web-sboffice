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
        $("#storeProdKitchenprintLinkView").hide();
        if(hqOfficeCd === 'H0614' || hqOfficeCd === 'H0616' || hqOfficeCd === 'DS053'){
            $("#prodKitchenprintLinkView").hide();
            $("#storeProdKitchenprintLinkView").show();
            $("#prodKitchenprintLinkTab").hide();
            $("#storeProdKitchenprintLinkTab").addClass("on");
        }
    };

    // 상품-매장주방프린터 연결 탭 보이기
    $scope.prodKitchenprintLinkShow = function () {
        $("#prodKitchenprintLinkTab").addClass("on");
        $("#storeProdKitchenprintLinkTab").removeClass("on");

        $("#prodKitchenprintLinkView").show();
        $("#storeProdKitchenprintLinkView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodKitchenprintLinkCtrl");
        scope.flex.refresh();
    };

    // 매장-상품 탭 보이기
    $scope.storeProdKitchenprintLinkShow = function () {
        $("#prodKitchenprintLinkTab").removeClass("on");
        $("#storeProdKitchenprintLinkTab").addClass("on");

        $("#prodKitchenprintLinkView").hide();
        $("#storeProdKitchenprintLinkView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeProdKitchenprintLinkCtrl");
        scope.flex.refresh();
    };

}]);