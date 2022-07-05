/****************************************************************
 *
 * 파일명 : alimtalkTemplateTab.js
 * 설  명 : 알림톡템플릿(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.08     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('alimtalkTemplateTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#alimtalkTemplateView").show();
    };

    // 알림톡 템플릿관리 탭 보이기
    $scope.alimtalkTemplateShow = function () {
        $("#alimtalkTemplateView").addClass("on");

        $("#alimtalkTemplateView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("alimtalkTemplateCtrl");
        scope.flex.refresh();
    };

}]);