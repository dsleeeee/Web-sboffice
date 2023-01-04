/****************************************************************
 *
 * 파일명 : prodInfoSearch.js
 * 설  명 : 기초관리 > 상품관리 > 상품정보조회 JavaScript
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
    var chk = 0;

    $scope.init = function () {
        $("#prodClassView").show();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();
    };

    // 상품분류 탭 보이기
    $scope.prodClassShow = function () {
        $("#prodClassTab").addClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");

        $("#prodClassView").show();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodClassCtrl");
        scope.flex.refresh();
    };

    // 사이드-속성 탭 보이기
    $scope.sideAttrShow = function () {
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").addClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");

        $("#prodClassView").hide();
        $("#sideAttrView").show();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideAttrCtrl");
        scope.flex.refresh();
    };

    // 사이드-선택메뉴 탭 보이기
    $scope.sideMenuShow = function () {
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").addClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");

        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").show();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuCtrl");
        scope.flex.refresh();
    };

    // 옵션 탭 보이기
    $scope.optionShow = function () {
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").addClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");

        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").show();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("optionCtrl");
        scope.flex.refresh();
    };

    // 상품-속성/선택메뉴/옵션 탭 보이기
    $scope.prodInfoShow = function () {
        if(chk == 0){
            $scope.prodClass();
        }
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").addClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").removeClass("on");

        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").show();
        $("#orgplceView").hide();
        $("#allergyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodInfoCtrl");
        scope.flex.refresh();
    };

    // 원산지 탭 보이기
    $scope.orgplceShow = function () {
        if(chk == 0){
            $scope.prodClass();
        }
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").addClass("on");
        $("#allergyTab").removeClass("on");

        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").show();
        $("#allergyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orgplceCtrl");
        scope.flex.refresh();
    };

    // 알레르기 탭 보이기
    $scope.allergyShow = function () {
        if(chk == 0){
            $scope.prodClass();
        }
        $("#prodClassTab").removeClass("on");
        $("#sideAttrTab").removeClass("on");
        $("#sideMenuTab").removeClass("on");
        $("#optionTab").removeClass("on");
        $("#prodInfoTab").removeClass("on");
        $("#orgplceTab").removeClass("on");
        $("#allergyTab").addClass("on");

        $("#prodClassView").hide();
        $("#sideAttrView").hide();
        $("#sideMenuView").hide();
        $("#optionView").hide();
        $("#prodInfoView").hide();
        $("#orgplceView").hide();
        $("#allergyView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("allergyCtrl");
        scope.flex.refresh();
    };

    // 분류조회팝업 한번씩 열었다 닫아야 정상동작함 딱 한번만 반복하도록
    $scope.prodClass = function (){
        var prodInfoScope = agrid.getScope("prodInfoCtrl");
        prodInfoScope.prodClassPopUpLayer.show();
        prodInfoScope.prodClassPopUpLayer.hide();

        var orgplceScope = agrid.getScope("orgplceCtrl");
        orgplceScope.prodClassPopUpLayer.show();
        orgplceScope.prodClassPopUpLayer.hide();

        var allergyScope = agrid.getScope("allergyCtrl");
        allergyScope.prodClassPopUpLayer.show();
        allergyScope.prodClassPopUpLayer.hide();

        chk++;
    };

}]);