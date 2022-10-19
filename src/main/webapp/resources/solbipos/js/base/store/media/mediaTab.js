/****************************************************************
 *
 * 파일명 : mediaTab.js
 * 설  명 : 듀얼모니터영상관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.10.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('mediaTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#mediaView").show();
        $("#mediaPlaySeqView").hide();
    };

    // 미디어관리 탭 보이기
    $scope.mediaShow = function () {
        $("#mediaTab").addClass("on");
        $("#mediaPlaySeqTab").removeClass("on");

        $("#mediaView").show();
        $("#mediaPlaySeqView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("mediaCtrl");
        scope.flex.refresh();
    };

    // 재생순서관리 탭 보이기
    $scope.mediaPlaySeqShow = function () {
        $("#mediaTab").removeClass("on");
        $("#mediaPlaySeqTab").addClass("on");

        $("#mediaView").hide();
        $("#mediaPlaySeqView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("mediaPlaySeqCtrl");
        scope.flex.refresh();
    };

}]);