/**
 * get application
 */
var app = agrid.getApp();

app.controller('saleByPeriodCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#dayView").show();
        $("#dayOfWeekView").hide();
        $("#monthView").hide();
        $("#dayPeriodView").hide();
    };

    // 일자별 탭 보이기
    $scope.dayShow = function () {
        $("#dayTab").addClass("on");
        $("#dayOfWeekTab").removeClass("on");
        $("#monthTab").removeClass("on");
        $("#dayPeriodTab").removeClass("on");

        $("#dayView").show();
        $("#dayOfWeekView").hide();
        $("#monthView").hide();
        $("#dayPeriodView").hide();
        
        // 맨 처음탭 셋팅
        $scope.setTab("day");

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayCtrl");
        scope.flex.refresh();
    };

    // 요일별 탭 보이기
    $scope.dayOfWeekShow = function () {
        $("#dayTab").removeClass("on");
        $("#dayOfWeekTab").addClass("on");
        $("#monthTab").removeClass("on");
        $("#dayPeriodTab").removeClass("on");

        $("#dayView").hide();
        $("#dayOfWeekView").show();
        $("#monthView").hide();
        $("#dayPeriodView").hide();

        $('[name="dayView"]').hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekCtrl");
        scope.flex.refresh();
    };

    // 월별 탭 보이기
    $scope.monthShow = function () {
        $("#dayTab").removeClass("on");
        $("#dayOfWeekTab").removeClass("on");
        $("#monthTab").addClass("on");
        $("#dayPeriodTab").removeClass("on");

        $("#dayView").hide();
        $("#dayOfWeekView").hide();
        $("#monthView").show();
        $("#dayPeriodView").hide();

        $('[name="dayView"]').hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthCtrl");
        scope.flex.refresh();
    };

    // 설정기간별 탭 보이기
    $scope.dayPeriodShow = function () {
        $("#dayTab").removeClass("on");
        $("#dayOfWeekTab").removeClass("on");
        $("#monthTab").removeClass("on");
        $("#dayPeriodTab").addClass("on");

        $("#dayView").hide();
        $("#dayOfWeekView").hide();
        $("#monthView").hide();
        $("#dayPeriodView").show();

        $('[name="dayView"]').hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayPeriodCtrl");
        scope.flex.refresh();
    };

    // 각각 상위 탭 클릭 시 하위 맨 처음 탭이 나오도록
    $scope.setTab = function(pageName){

        $("#" + pageName + "TotalTab").addClass("on");
        $("#" + pageName + "DcTab").removeClass("on");
        $("#" + pageName + "TaxTab").removeClass("on");
        $("#" + pageName + "TimeTab").removeClass("on");
        $("#" + pageName + "ProdClassTab").removeClass("on");
        $("#" + pageName + "CornerTab").removeClass("on");
        $("#" + pageName + "PosTab").removeClass("on");

        $("#" + pageName + "TotalView").show();
        $("#" + pageName + "DcView").hide();
        $("#" + pageName + "TaxView").hide();
        $("#" + pageName + "TimeView").hide();
        $("#" + pageName + "ProdClassView").hide();
        $("#" + pageName + "CornerView").hide();
        $("#" + pageName + "PosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope(pageName + "TotalCtrl");
        scope.flex.refresh();
    }


}]);