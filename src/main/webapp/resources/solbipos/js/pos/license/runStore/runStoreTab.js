/****************************************************************
 *
 * 파일명 : runStoreTab.js
 * 설  명 : 런닝매장현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.11     김유승      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('runStoreCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#runStoreListView").show();
        $("#runCopyCntView").hide();
        $("#runStoreTrnsitnView").hide();
    };

    // 런닝매장현황 탭 보이기
    $scope.runStoreListShow = function () {
        $("#runStoreListTab").addClass("on");
        $("#runCopyCntTab").removeClass("on");
        $("#runStoreTrnsitnTab").removeClass("on");

        $("#runStoreListView").show();
        $("#runCopyCntView").hide();
        $("#runStoreTrnsitnView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("runStoreListCtrl");
        scope.flex.refresh();
    };

    // 런닝COPY수 탭 보이기
    $scope.runCopyCntShow = function () {
        $("#runStoreListTab").removeClass("on");
        $("#runCopyCntTab").addClass("on");
        $("#runStoreTrnsitnTab").removeClass("on");

        $("#runStoreListView").hide();
        $("#runCopyCntView").show();
        $("#runStoreTrnsitnView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("runCopyCntCtrl");
        scope.flex.refresh();
    };

    // 런닝매장추이 탭 보이기
    $scope.runStoreTrnsitnShow = function () {
        $("#runStoreListTab").removeClass("on");
        $("#runCopyCntTab").removeClass("on");
        $("#runStoreTrnsitnTab").addClass("on");

        $("#runStoreListView").hide();
        $("#runCopyCntView").hide();
        $("#runStoreTrnsitnView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("runStoreTrnsitnCtrl");
        scope.flex.refresh();
    };

}]);