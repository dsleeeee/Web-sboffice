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
        $("#recpOriginInfoView").hide();

        if(orgnFg === "HQ" && (hqOfficeCd === 'H0393' || hqOfficeCd === 'DS021' || hqOfficeCd === 'DS034' || hqOfficeCd === 'H0514' || hqOfficeCd === 'H0614' || hqOfficeCd === 'H0616')){
            $("#recpOriginInfoTab").css("display",'block');
        }
    };

    // 원산지 탭 보이기
    $scope.recpOriginShow = function () {
        $("#prodRecpOriginTab").removeClass("on");
        $("#recpOriginTab").addClass("on");
        $("#recpOriginInfoTab").removeClass("on");

        $("#prodRecpOriginView").hide();
        $("#recpOriginView").show();
        $("#recpOriginInfoView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("recpOriginCtrl");
        scope.flex.refresh();
    };

    // 상품-원산지관리 탭 보이기
    $scope.prodRecpOriginShow = function () {
        $("#prodRecpOriginTab").addClass("on");
        $("#recpOriginTab").removeClass("on");
        $("#recpOriginInfoTab").removeClass("on");

        $("#prodRecpOriginView").show();
        $("#recpOriginView").hide();
        $("#recpOriginInfoView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodRecpOriginCtrl");
        scope.flex.refresh();
        var scope1 = agrid.getScope("prodRecpOriginDetailCtrl");
        scope1.flex.refresh();
    };

    // 원산지관리 - 정보입력 탭 보이기
    $scope.recpOriginInfoShow = function () {
        $("#prodRecpOriginTab").removeClass("on");
        $("#recpOriginTab").removeClass("on");
        $("#recpOriginInfoTab").addClass("on");

        $("#prodRecpOriginView").hide();
        $("#recpOriginView").hide();
        $("#recpOriginInfoView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("recpOriginInfoCtrl");
        scope.flex.refresh();
    };

}]);