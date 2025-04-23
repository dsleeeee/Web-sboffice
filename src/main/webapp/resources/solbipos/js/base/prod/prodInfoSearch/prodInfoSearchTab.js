/****************************************************************
 *
 * 파일명 : prodInfoSearch.js
 * 설  명 : 기초관리 > 상품관리 > 상품구성정보조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.23     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('prodInfoSearchCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        if(momsEnvstVal === "1") {
            $("#prodInfo2Tab").addClass("on");
            $("#prodClassTab").removeClass("on");
            $("#sideAttrTab").removeClass("on");
            $("#sideMenuTab").removeClass("on");
            $("#optionTab").removeClass("on");
            $("#prodInfoTab").removeClass("on");
            $("#orgplceTab").removeClass("on");
            $("#allergyTab").removeClass("on");
            $("#touchKeyTab").removeClass("on");
            $("#kioskKeyMapTab").removeClass("on");
            
            $("#prodClassView").hide();
            $("#sideAttrView").hide();
            $("#sideMenuView").hide();
            $("#optionView").hide();
            $("#prodInfoView").hide();
            $("#orgplceView").hide();
            $("#allergyView").hide();
            $("#touchKeyView").hide();
            $("#kioskKeyMapView").hide();
            $("#prodInfo2View").show();
        } else {
            $("#prodClassView").show();
            $("#sideAttrView").hide();
            $("#sideMenuView").hide();
            $("#optionView").hide();
            $("#prodInfoView").hide();
            $("#orgplceView").hide();
            $("#allergyView").hide();
            $("#touchKeyView").hide();
            $("#kioskKeyMapView").hide();
        }
    };

    // 상품정보 탭 보이기
    $scope.prodInfo2Show = function () {
        $("#prodInfo2Tab").addClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").show();
        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodInfo2Ctrl");
        scope.flex.refresh();
    };

    // 상품분류 탭 보이기
    $scope.prodClassShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").addClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").show();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodClassCtrl");
        scope.flex.refresh();
    };

    // 사이드-속성 탭 보이기
    $scope.sideAttrShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").addClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").hide();
        $("#sideAttrView").show();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideAttrCtrl");
        scope.flex.refresh();
    };

    // 사이드-선택메뉴 탭 보이기
    $scope.sideMenuShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").addClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").show();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuCtrl");
        scope.flex.refresh();
    };

    // 옵션 탭 보이기
    $scope.optionShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").addClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").show();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("optionCtrl");
        scope.flex.refresh();
    };

    // 상품-속성/선택메뉴/옵션 탭 보이기
    $scope.prodInfoShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").addClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").show();
        $("#orgplceView").hide();
        $("#allergyView").hide();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodInfoCtrl");
        scope.flex.refresh();
    };

    // 원산지 탭 보이기
    $scope.orgplceShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").addClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").show();
        $("#allergyView").hide();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orgplceCtrl");
        scope.flex.refresh();
    };

    // 알레르기 탭 보이기
    $scope.allergyShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").addClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").show();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("allergyCtrl");
        scope.flex.refresh();
    };

    // 판매터치키 탭 보이기
    $scope.touchKeyShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").addClass("on");
        $("#kioskKeyMapTab").removeClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();
        $("#touchKeyView").show();
        $("#kioskKeyMapView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("touchKeyCtrl");
        scope.flex.refresh();
    };

    // 키오스크키맵 탭 보이기
    $scope.kioskKeyMapShow = function () {
        $("#prodInfo2Tab").removeClass("on");
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");
        $("#touchKeyTab").removeClass("on");
        $("#kioskKeyMapTab").addClass("on");

        $("#prodInfo2View").hide();
        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();
        $("#touchKeyView").hide();
        $("#kioskKeyMapView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("kioskKeyMapCtrl");
        scope.flex.refresh();
    };

}]);