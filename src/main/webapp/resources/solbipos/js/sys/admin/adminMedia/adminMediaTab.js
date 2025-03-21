/****************************************************************
 *
 * 파일명 : mediaTab.js
 * 설  명 : (관리자)듀얼모니터영상관리  JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.03.07     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('adminMediaTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#adminMediaView").show();
        $("#adminMediaPlaySeqView").hide();

    };

    // 미디어관리 탭 보이기
    $scope.adminMediaShow = function () {
        $("#adminMediaTab").addClass("on");
        $("#adminMediaPlaySeqTab").removeClass("on");

        $("#adminMediaView").show();
        $("#adminMediaPlaySeqView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("adminMediaCtrl");
        scope.flex.refresh();
    };

    // 재생순서관리 탭 보이기
    $scope.adminMediaPlaySeqShow = function () {
        $("#adminMediaTab").removeClass("on");
        $("#adminMediaPlaySeqTab").addClass("on");

        $("#adminMediaView").hide();
        $("#adminMediaPlaySeqView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("adminMediaPlaySeqCtrl");
        scope.flex.refresh();
    };


}]);