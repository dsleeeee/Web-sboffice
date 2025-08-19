/****************************************************************
 *
 * 파일명 : storeMrpizzaBatchChangeTab.js
 * 설  명 : 미스터피자 > 매장관리 > 매장정보일괄변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.18     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeMrpizzaBatchChangeTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storeMrpizzaBatchChangeView").show();
        // $("#storeMrpizzaBatchChangeExcelUploadView").hide();
    };

    // 일괄변경 탭 보이기
    $scope.storeMrpizzaBatchChangeShow = function () {
        $("#storeMrpizzaBatchChangeTab").addClass("on");
        $("#storeMrpizzaBatchChangeExcelUploadTab").removeClass("on");

        $("#storeMrpizzaBatchChangeView").show();
        $("#storeMrpizzaBatchChangeExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeMrpizzaBatchChangeCtrl");
    };

    // 엑셀업로드 탭 보이기
    $scope.storeMrpizzaBatchChangeExcelUploadShow = function () {
        $("#storeMrpizzaBatchChangeTab").removeClass("on");
        $("#storeMrpizzaBatchChangeExcelUploadTab").addClass("on");

        $("#storeMrpizzaBatchChangeView").hide();
        $("#storeMrpizzaBatchChangeExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeMrpizzaBatchChangeExcelUploadCtrl");
        scope.excelFlex.refresh();
        var scope2 = agrid.getScope("storeExcelUploadCtrl");
        scope2.flex.refresh();
    };

}]);