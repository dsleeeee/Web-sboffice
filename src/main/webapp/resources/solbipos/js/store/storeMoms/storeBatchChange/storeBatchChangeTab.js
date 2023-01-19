/****************************************************************
 *
 * 파일명 : storeBatchChangeTab.js
 * 설  명 : 맘스터치 > 매장관리 > 매장정보일괄변경 JavaScript
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

app.controller('storeBatchChangeTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storeBatchChangeView").show();
        // $("#storeBatchChangeExcelUploadView").hide();
    };

    // 일괄변경 탭 보이기
    $scope.storeBatchChangeShow = function () {
        $("#storeBatchChangeTab").addClass("on");
        $("#storeBatchChangeExcelUploadTab").removeClass("on");

        $("#storeBatchChangeView").show();
        $("#storeBatchChangeExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeBatchChangeCtrl");
    };

    // 엑셀업로드 탭 보이기
    $scope.storeBatchChangeExcelUploadShow = function () {
        $("#storeBatchChangeTab").removeClass("on");
        $("#storeBatchChangeExcelUploadTab").addClass("on");

        $("#storeBatchChangeView").hide();
        $("#storeBatchChangeExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeBatchChangeExcelUploadCtrl");
        scope.excelFlex.refresh();
        var scope2 = agrid.getScope("storeExcelUploadCtrl");
        scope2.flex.refresh();
    };

}]);