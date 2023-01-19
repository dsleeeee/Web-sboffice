/****************************************************************
 *
 * 파일명 : saleDtlChannelTab.js
 * 설  명 : 기초관리 > 상품관리 > 상품정보조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.01.06     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('saleDtlChannelTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        if(orgnFg === "HQ"){
            $("#saleDtlChannelExcelView").show();
            $("#saleDtlChannelView").hide();
        } else if (orgnFg === "STORE"){
            $("#saleDtlChannelExcelView").hide();
            $("#saleDtlChannelView").show();
        }

    };

    // 엑셀다운로드 탭 보이기
    $scope.saleDtlChannelExcelShow = function () {
        $("#saleDtlChannelExcelTab").addClass("on");
        $("#saleDtlChannelTab").removeClass("on");

        $("#saleDtlChannelExcelView").show();
        $("#saleDtlChannelView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("saleDtlChannelExcelCtrl");
    };

    // 조회 탭 보이기
    $scope.saleDtlChannelShow = function () {
        $("#saleDtlChannelExcelTab").removeClass("on");
        $("#saleDtlChannelTab").addClass("on");

        $("#saleDtlChannelExcelView").hide();
        $("#saleDtlChannelView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("saleDtlChannelCtrl");
        scope.flex.refresh();
    };

}]);