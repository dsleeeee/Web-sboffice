/****************************************************************
 *
 * 파일명 : smsAnalysisTab.js
 * 설  명 : SMS전송분석(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('smsAnalysisTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#smsSendHistView").show();
    };

    // SMS전송이력 탭 보이기
    $scope.smsSendHistShow = function () {
        $("#smsSendHistTab").addClass("on");

        $("#smsSendHistView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("smsSendHistCtrl");
        scope.flex.refresh();
    };

}]);