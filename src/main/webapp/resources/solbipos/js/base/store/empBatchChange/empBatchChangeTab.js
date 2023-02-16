/****************************************************************
 *
 * 파일명 : empBatchChangeTab.js
 * 설  명 : 기초관리 > 사원관리 > 사원정보일괄변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.01.17     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('empBatchChangeTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#empBatchChangeView").show();
        $("#empBatchChangeExcelUploadView").hide();
    };

    // 일괄변경 탭 보이기
    $scope.empBatchChangeShow = function () {
        $("#empBatchChangeTab").addClass("on");
        $("#empBatchChangeExcelUploadTab").removeClass("on");

        $("#empBatchChangeView").show();
        $("#empBatchChangeExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empBatchChangeCtrl");
    };

    // 엑셀업로드 탭 보이기
    $scope.empBatchChangeExcelUploadShow = function () {
        $("#empBatchChangeTab").removeClass("on");
        $("#empBatchChangeExcelUploadTab").addClass("on");

        $("#empBatchChangeView").hide();
        $("#empBatchChangeExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empBatchChangeExcelUploadCtrl");
        scope.excelFlex.refresh();
        var scope2 = agrid.getScope("storeExcelUploadCtrl");
        scope2.flex.refresh();
    };

}]);