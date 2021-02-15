/**
 * get application
 */
var app = agrid.getApp();

app.controller('todaySaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#todayGnrlzView").show();
        $("#todayProdView").show();
        $("#todayDtlView").hide();
        $("#todayDtlView2").hide();
        $("#todayBillSaleDtlView").hide();
        // $("#nonSalelView").hide();
    };

    // 당일매출종합 탭 보이기
    $scope.todayGnrlzShow = function () {
        $("#todayGnrlzTab").addClass("on");
        $("#todayDtlTab").removeClass("on");
        $("#todayBillSaleDtlTab").removeClass("on");
        //$("#noneSaleTab").removeClass("on");

        $("#todayGnrlzView").show();
        $("#todayProdView").show();
        $("#todayDtlView").hide();
        $("#todayDtlView2").hide();
        $("#todayBillSaleDtlView").hide();
        //$("#nonSalelView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("todayGnrlzCtrl");
        scope.flex.refresh();

        scope = agrid.getScope("todayGnrlzPayCtrl");
        scope.flex.refresh();

        scope = agrid.getScope("todayGnrlzMemberCtrl");
        scope.flex.refresh();

        scope = agrid.getScope("todayGnrlzProdCtrl");
        scope.flex.refresh();
    };


    // 당일매출상세 탭 보이기
    $scope.todayDtlShow = function () {
        $("#todayGnrlzTab").removeClass("on");
        $("#todayDtlTab").addClass("on");
        $("#todayBillSaleDtlTab").removeClass("on");
        //$("#noneSaleTab").removeClass("on");

        $("#todayGnrlzView").hide();
        $("#todayProdView").hide();
        $("#todayDtlView").show();
        $("#todayDtlView2").show();
        $("#todayBillSaleDtlView").hide();
        //$("#nonSalelView").hide();


        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("todayDtlCtrl");
        scope.flex.refresh();

        scope = agrid.getScope("todayDtlDetailCtrl");
        scope.flex.refresh();
    };


    // 영수증별매출 탭 보이기
    $scope.todayBillSaleDtlShow = function () {
        $("#todayGnrlzTab").removeClass("on");
        $("#todayDtlTab").removeClass("on");
        $("#todayBillSaleDtlTab").addClass("on");
        //$("#noneSaleTab").removeClass("on");

        $("#todayGnrlzView").hide();
        $("#todayProdView").hide();
        $("#todayDtlView").hide();
        $("#todayDtlView2").hide();
        $("#todayBillSaleDtlView").show();
        //$("#nonSalelView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("todayBillSaleDtlCtrl");
        scope.flex.refresh();
    };


    // 비매출내역 탭 보이기
    /*$scope.nonSaleShow = function () {
        $("#todayGnrlzTab").removeClass("on");
        $("#todayDtlTab").removeClass("on");
        $("#todayBillSaleDtlTab").removeClass("on");
        $("#noneSaleTab").addClass("on");

        $("#todayGnrlzView").hide();
        $("#todayDtlView").hide();
        $("#todayBillSaleDtlView").hide();
        $("#nonSalelView").show();


        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("statusPosInstallCtrl");
        scope.flex.refresh();
    };
*/
}]);