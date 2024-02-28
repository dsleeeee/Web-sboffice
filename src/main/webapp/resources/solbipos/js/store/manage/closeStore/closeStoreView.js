/****************************************************************
 *
 * 파일명 : closeStoreView.js
 * 설  명 : 폐점예정매장 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.02.16     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('closeStoreViewCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#closeStore").show();
        $("#storeCloseExcept").hide();
    };

    // 본사환경 탭 보이기
    $scope.closeShow = function () {
        $("#closeTab").addClass("on");
        $("#exceptTab").removeClass("on");


        $("#closeStore").show();
        $("#storeCloseExcept").hide();


        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("closeStoreCtrl");
        scope.flex.refresh();
    };

    // 매장환경 탭 보이기
    $scope.exceptShow = function () {
        $("#closeTab").removeClass("on");
        $("#exceptTab").addClass("on");


        $("#closeStore").hide();
        $("#storeCloseExcept").show();


        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeCloseExceptCtrl");
        scope.flex.refresh();
    };

}]);