/****************************************************************
 *
 * 파일명 : authorInExRegistTab.js
 * 설  명 : 시스템관리 > 관리자기능2 > 메뉴권한임의등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.04.17     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


// 권한그룹여부
var authGrpFgComboData = [
    {"name":"전체","value":""},
    {"name":"포함","value":"Y"},
    {"name":"미포함","value":"N"}
];

// 권한처리구분
var authorProdcFgComboData = [
    {"name":"본사만","value":"H"},
    {"name":"매장만","value":"S"},
    {"name":"본사+해당본사의프랜매장","value":"C"}
];

// 사용여부
var useYnComboData = [
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

// 포함제외구분
var incldExcldFgComboData = [
    {"name":"포함","value":"I"},
    {"name":"제외","value":"E"}
];


/**
 * 메뉴권한임의등록 탭 생성
 */
app.controller('authorInExRegistTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#userInExRegistView").show();
        // $("#recpOriginView").hide();

    };

    // 사용자기준 탭 보이기
    $scope.userInExShow = function () {
        $("#orgnInExTab").removeClass("on");
        $("#userInExTab").addClass("on");

        $("#orgnInExRegistView").hide();
        $("#userInExRegistView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("userInExRegistCtrl");
        scope.flex.refresh();
    };

    // 소속기준 탭 보이기
    $scope.orgnInExShow = function () {
        $("#userInExTab").removeClass("on");
        $("#orgnInExTab").addClass("on");

        $("#orgnInExRegistView").show();
        $("#userInExRegistView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orgnInExRegistCtrl");
        scope.flex.refresh();
    };

}]);