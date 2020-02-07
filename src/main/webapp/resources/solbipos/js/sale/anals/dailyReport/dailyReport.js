/****************************************************************
 *
 * 파일명 : dailyReport.js
 * 설  명 : 영업일보 JavaScript (매출관리 > 매출분석 > 영업일보)
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.29     조현수      1.0
 *
 * **************************************************************/

var app = agrid.getApp();	//get application

app.controller('dailyReportCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#reportView"	).show();
        $("#configView"	).hide();
    };

    //[영업일보] Tab 보이기
    $scope.reportShow = function () {
        $("#reportTab"	).addClass		("on");
        $("#configTab"	).removeClass	("on");

        $("#reportView"	).show();
        $("#configView"	).hide();

        //'angular grid' hide시 문제생기므로(깨짐) refresh()
        /*
        var scope = agrid.getScope("reportCtrl");
	        scope.sl    .refresh();
	        scope.pay   .refresh();
	        scope.nsl 	.refresh();
	        scope.npay  .refresh();
	        scope.pos   .refresh();
	        scope.emp   .refresh();
	        scope.dc    .refresh();
	        scope.dcdtl .refresh();
	        scope.gift  .refresh();
	        scope.order .refresh();
	        scope.lv1   .refresh();
	        scope.lv2   .refresh();
	        scope.lv3   .refresh();
	        scope.prod  .refresh();
	        scope.compt .refresh();
	        scope.appr  .refresh();
	        scope.membr .refresh();
	        scope.work  .refresh();
	    */
        var scope;
        scope = agrid.getScope("reportCtrl_sl");       	scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_pay");       scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_nsl");       scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_npay");      scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_pos");       scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_emp");       scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_dc");        scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_dcdtl");     scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_gift");      scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_order");     scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_lv1");       scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_lv2");       scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_lv3");       scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_prod");      scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_compt");     scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_appr");      scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_membr");     scope.flex.refresh();
        scope = agrid.getScope("reportCtrl_work");      scope.flex.refresh();
    };


    //[영업일보 구성] Tab 보이기
    $scope.configShow = function () {
        $("#reportTab"	).removeClass	("on");
        $("#configTab"	).addClass		("on");

        $("#reportView"	).hide();
        $("#configView"	).show();

        //'angular grid' hide시 문제생기므로(깨짐) refresh()
        var scope;
        scope = agrid.getScope("configCtrl_1");	scope.flex.refresh();
        scope = agrid.getScope("configCtrl_2");	scope.flex.refresh();
    };

}]);