/****************************************************************
 *
 * 파일명 : envConfgBatchChange.js
 * 설  명 : 환경변수일괄변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.02.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('envConfgBatchChangeCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#envConfgBatchChangeHqView").show();
        $("#envConfgBatchChangeStoreView").hide();
        $("#envConfgBatchChangeStorePosView").hide();
        $("#envConfgBatchChangeFnkeyView").hide();
    };

    // 본사환경 탭 보이기
    $scope.hqShow = function () {
        $("#hqTab").addClass("on");
        $("#storeTab").removeClass("on");
        $("#storePosTab").removeClass("on");
        $("#fnkeyTab").removeClass("on");

        $("#envConfgBatchChangeHqView").show();
        $("#envConfgBatchChangeStoreView").hide();
        $("#envConfgBatchChangeStorePosView").hide();
        $("#envConfgBatchChangeFnkeyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("envConfgBatchChangeHqCtrl");
        scope.flex.refresh();
    };

    // 매장환경 탭 보이기
    $scope.storeShow = function () {
        $("#hqTab").removeClass("on");
        $("#storeTab").addClass("on");
        $("#storePosTab").removeClass("on");
        $("#fnkeyTab").removeClass("on");

        $("#envConfgBatchChangeHqView").hide();
        $("#envConfgBatchChangeStoreView").show();
        $("#envConfgBatchChangeStorePosView").hide();
        $("#envConfgBatchChangeFnkeyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("envConfgBatchChangeStoreCtrl");
        scope.flex.refresh();
    };

    // 매장포스환경 탭 보이기
    $scope.storePosShow = function () {
        $("#hqTab").removeClass("on");
        $("#storeTab").removeClass("on");
        $("#storePosTab").addClass("on");
        $("#fnkeyTab").removeClass("on");

        $("#envConfgBatchChangeHqView").hide();
        $("#envConfgBatchChangeStoreView").hide();
        $("#envConfgBatchChangeStorePosView").show();
        $("#envConfgBatchChangeFnkeyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("envConfgBatchChangeStorePosCtrl");
        scope.flex.refresh();
    };

    // 기능키명칭 탭 보이기
    $scope.fnkeyShow = function () {
        $("#hqTab").removeClass("on");
        $("#storeTab").removeClass("on");
        $("#storePosTab").removeClass("on");
        $("#fnkeyTab").addClass("on");

        $("#envConfgBatchChangeHqView").hide();
        $("#envConfgBatchChangeStoreView").hide();
        $("#envConfgBatchChangeStorePosView").hide();
        $("#envConfgBatchChangeFnkeyView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("envConfgBatchChangeFnkeyCtrl");
        scope.flex.refresh();
    };

}]);