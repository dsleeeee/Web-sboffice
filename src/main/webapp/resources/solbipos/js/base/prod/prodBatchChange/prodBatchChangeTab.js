/****************************************************************
 *
 * 파일명 : prodBatchChangeTab.js
 * 설  명 : 상품정보일괄변경 Tab JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.16     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('prodBatchChangeTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#prodBatchChangeTab").addClass("on");
        $("#prodBatchChange2Tab").removeClass("on");
    };
    
    // 판매상품여부, 포인트적립여부, 매핑상품코드, 가격관리구분 변경 탭 보이기
    $scope.prodBatchChangeShow = function () {
        $("#prodBatchChangeTab").addClass("on");
        $("#prodBatchChange2Tab").removeClass("on");

        $("#prodBatchChangeVeiw").show();
        $("#prodBatchChange2Veiw").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodBatchChangeCtrl");
        scope.flex.refresh();
    };

    // 브랜드, 상품분류 변경 레이어 탭 보이기
    $scope.prodBatchChange2Show = function () {
        $("#prodBatchChangeTab").removeClass("on");
        $("#prodBatchChange2Tab").addClass("on");

        $("#prodBatchChangeVeiw").hide();
        $("#prodBatchChange2Veiw").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodBatchChange2Ctrl");
        scope.flex.refresh();
    };

}]);