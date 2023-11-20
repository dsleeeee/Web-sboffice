/****************************************************************
 *
 * 파일명 : captionMsgTab.js
 * 설  명 : 다국어관리(기능키/메시지) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.11.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('captionMsgTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#captionMsgView").show();
        $("#captionMsgGrpView").hide();
    };

    // 기능키/메시지 탭 보이기
    $scope.captionMsgShow = function () {
        $("#captionMsgTab").addClass("on");
        $("#captionMsgGrpTab").removeClass("on");

        $("#captionMsgView").show();
        $("#captionMsgGrpView").hide();

        //
        $scope._broadcast("captionMsgCtrl");
    };

    // 화면구분등록 탭 보이기
    $scope.captionMsgGrpShow = function () {
        $("#captionMsgTab").removeClass("on");
        $("#captionMsgGrpTab").addClass("on");

        $("#captionMsgView").hide();
        $("#captionMsgGrpView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("captionMsgGrpCtrl");
        scope.flex.refresh();
    };

}]);