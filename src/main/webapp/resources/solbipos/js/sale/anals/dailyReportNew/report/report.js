/****************************************************************
 *
 * 파일명 : report.js
 * 설  명 : 영업일보 JavaScript (매출관리 > 매출분석 > 영업일보 > [영업일보] Tab)
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.29     조현수      1.0
 *
 * **************************************************************/

var app 		= agrid.getApp();	//get application

var startDate 	= wcombo.genDateVal("#startDate", getToday()	);
var endDate 	= wcombo.genDateVal("#endDate",   getToday()	);

var arrDispSeq	 = new Array();		//[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)

/* 전역으로 선언해도 인식못함.
//매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------
//함수명         : 모듈에 넘기는 파라미터의 targetId + 'Show'
//_broadcast: 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
reportSelectStoreShow = function () {
	$scope._broadcast('reportSelectStoreCtrl');
};
//매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------
*/

//reportCtrl		START	############################################################################################################################################################################
app.controller('reportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
/*
app.controller('reportCtrl', ['$scope', '$http',             function ($scope, $http) {
app.controller('reportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
*/	
		
	//상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('reportCtrl', $scope, $http, true));

	$scope.orgnFg = gvOrgnFg;
	
	$scope.init            = function () {
		$scope.configCtrlList();        
        event.preventDefault();	//기능수행 종료 (반드시 추가해야 함)
	};
	
	
    //검색조건에 조회기간
		/*
		[안됨]
		$scope.startDate	= wcombo.genDateVal("#startDate", 	gvStartDate	);
		$scope.endDate		= wcombo.genDateVal("#endDate", 	gvEndDate	);

		wcombo.genDateVal("#startDate", gvStartDate	);
		wcombo.genDateVal("#endDate",   gvEndDate	);

		console.log("startDate     :" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd')	);	//OK
		console.log("endDate       :" + wijmo.Globalize.format(endDate  .value, 'yyyyMMdd')	);	//OK
		console.log("searchStoreCd :" + $("#reportSelectStoreCd").val()						);	//OK
		console.log("startDate     :" + $("#startDate").val()								);	//X
		console.log("endDate       :" + $("#endDate"  ).val()							 	);	//X

		[OK]
		var startDate 	= wcombo.genDateVal("#startDate", gvStartDate	);
		var endDate 	= wcombo.genDateVal("#endDate",   gvEndDate		);
		*/
	
    //[조회] - START			--------------------------------------------------------------------------------------------------------------------------
    $scope.$on("reportCtrl", function(event, data) {	//판매추이분석 Grid 조회
        
    	$scope.reportCtrlList();
        
        event.preventDefault();	//기능수행 종료 (반드시 추가해야 함)
        
	});	//$scope.$on("reportCtrl", function(event, data) {

    $scope.configCtrlList = function(data){

            ///*
    	    var params = {};
            	params.searchStoreCd   	= $("#reportSelectStoreCd").val();

            //$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.
    	    $scope._postJSONQuery.withOutPopUp	(	"/sale/anals/dailyReportNew/config/list.sb",	//영업일보 구성 조회
    					        					params,
    					        					function(response)	{
    																		//데이터 setting
    															            var configCtrl_1	= agrid.getScope('configCtrl_1');
    															            var configCtrl_2 	= agrid.getScope('configCtrl_2');
    															            configCtrl_1.flex.itemsSource = response.data.data.payline;	//결재라인
    															            configCtrl_2.flex.itemsSource = response.data.data.cfg;		//영업일보 구성

    															          //[영업일보 구성] check-box setting
    															            for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){
    															                var item = configCtrl_2.flex.collectionView.items[i];															                
    															                //console.log('item:' + item);

    															                if(item.cfgCd    == "SL"){
    															                	item.cfgSelYn	= "Y";	//[매출종합]은 무조건 'checked' (처음 접속 or '영업일보 구성'을 저장하지 않은 매장의 경우 '영업일보 구성'에서 선택된 것이 없기에, [매출종합]은 선택되어 보여지게 함)
//    																				item.cfgDispSeq = 1;
    															                }
//    															                //item.gChkDispSeq = false;	//순번 check-box는 모두 미선택 상태로
    															                
    															            	if(item.cfgSelYn == "Y"){
    															            		item.gChk = true;
//    															            		console.log("item.cfgCd")
                                                                                  //eval( '$(".div_'  + item.cfgCd + '").show();' );
                                                                                    eval( '$("#div_'  + item.cfgCd + '").show();' );
                                                                                    eval( '$("#span_' + item.cfgCd + '").show();' );

    															            	}else{
                                                                                  //eval( '$(".div_'  + item.cfgCd + '").hide();' );	//[영업일보 구성]에 없으면 숨기기
                                                                                    eval( '$("#div_'  + item.cfgCd + '").hide();' );	//[영업일보 구성]에 없으면 숨기기
                                                                                    eval( '$("#span_' + item.cfgCd + '").hide();' );
                                                                                }
    															            	
    															            	arrDispSeq[i] = new Array(item.cfgCd, item.cfgDispSeq, item.cfgSelYn);	//[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
    															            	
    															            	//console.log('Before: ' + item.cfgCd + ' & ' +  item.cfgDispSeq +   ' & ' + eval( '$("#div_sort_id_' + item.cfgCd + '").data("sort");' )   );
    															            	//eval( '$("#div_sort_id_' + item.cfgCd + '").data(' + '"sort",' + item.cfgDispSeq + ');' );
    															            	//console.log('After : ' + item.cfgCd + ' & ' +  item.cfgDispSeq +   ' & ' + eval( '$("#div_sort_id_' + item.cfgCd + '").data("sort");' )   );
    															            	eval( '$("#div_sort_id_' + item.cfgCd + '").data(' + '"sort",' + item.cfgDispSeq + ');' );

    															            }	//for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){

    															            //'영업일보(0000-00-00 ~ 0000-00-00)' 문구 setting
    															            var reportCtrl_excel = agrid.getScope('reportCtrl_excel');
    															            reportCtrl_excel.span_startDate	= wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd');
    															            reportCtrl_excel.span_endDate	= wijmo.Globalize.format(endDate  .value, 'yyyy-MM-dd');

    															            //2020.02.24 - '본사'인 경우에도 [결제라인] [영업일보] button 보이기
    															            if($scope.orgnFg == "H") {	//H:본사, S:가맹점, M:?? -> 매장이 'M'으로 보이는 경우도 있음
    																    	    $("#btnAdd"		).show();
    																    	    $("#btnDel"		).show();
    																    	    $("#btnSave"	).show();

    																    		$("#btnSave2"	).show();
    																    		$("#btnUp"	 	).show();
    																    		$("#btnDown"	).show();																    		
    															    		}

    															            sortByDataItem("reportView");	//화면의 Grid 정렬 변경

    															            //$scope.$broadcast('loadingPopupInactive');
    																	},	//callBack function
    												false);    		
    };	//$scope.reportCtrlList = function(data){
    //[조회] - END			--------------------------------------------------------------------------------------------------------------------------

    
    $scope.reportCtrlList = function(data){

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1년(365일) 제한
        if (diffDay > 365) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }

    	console.log("scope.orgnFg : " + $scope.orgnFg					);
    	console.log("매장                 : " + $("#reportSelectStoreCd").val()	);

    	//'본부'인 경우, 매장 선택여부 확인
      //if($scope.orgnFg == "H") {	--> 'M'으로 조회되는 경우도 있다. 고로 매장이 아닌경우로 if조건 변경함.
    	if($scope.orgnFg != "S") {
    		if( $("#reportSelectStoreCd").val() == "" ){
    			s_alert.pop( messages["dailyReport.alert.selectStore"] );	//선택된 매장이 없습니다. 매장을 선택해 주십시오.
    			return;
    		}
    	}

        var params = {};
        	params.startDate 		= wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
	        params.endDate 			= wijmo.Globalize.format(endDate  .value, 'yyyyMMdd');
	        params.searchStoreCd   	= $("#reportSelectStoreCd").val();
//For Test	params.startDate 		= wijmo.Globalize.format('20190101', 	  'yyyyMMdd'); //조회기간

	        /*
			console.log("startDate     :" + params.startDate 		);
			console.log("endDate       :" + params.endDate			);
			console.log("searchStoreCd :" + params.searchStoreCd	);
			*/
        /*
        $scope._inquiryMain					(	"/sale/anals/dailyReportNew/report/list.sb",		function _inquiry(url, params, callback, isView, isMaster) {
        $scope._inquirySub					(	"/sale/anals/dailyReportNew/report/list.sb",
        $scope._postJSONQuery.withOutPopUp	(	"/sale/anals/dailyReportNew/report/list.sb",
	    $scope._postJSONQuery.withPopUp		(	"/sale/anals/dailyReportNew/report/list.sb",
        */
	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//'데이터 처리 중입니다.' 시작	//cmm.progress=데이터 처리 중입니다.

	    $scope._postJSONQuery.withOutPopUp	(	"/sale/anals/dailyReportNew/report/list.sb",
					        					params,
					        					function(response)	{
																		//데이터 setting > 영업일보
																    	var reportCtrl_sl       = agrid.getScope("reportCtrl_sl");      reportCtrl_sl  	.flex.itemsSource = response.data.data.sl;      //매출종합
																    	var reportCtrl_pay      = agrid.getScope("reportCtrl_pay");     reportCtrl_pay  .flex.itemsSource = response.data.data.pay;     //결제수단
																    	var reportCtrl_nsl      = agrid.getScope("reportCtrl_nsl");     reportCtrl_nsl  .flex.itemsSource = response.data.data.nsl;     //비매출종합
																    	var reportCtrl_npay     = agrid.getScope("reportCtrl_npay");    reportCtrl_npay .flex.itemsSource = response.data.data.npay;    //비매출결제수단
																    	var reportCtrl_pos      = agrid.getScope("reportCtrl_pos");     reportCtrl_pos  .flex.itemsSource = response.data.data.pos;     //포스정산
																    	var reportCtrl_emp      = agrid.getScope("reportCtrl_emp");     reportCtrl_emp  .flex.itemsSource = response.data.data.emp;     //판매원별 매출
																    	var reportCtrl_dc       = agrid.getScope("reportCtrl_dc");      reportCtrl_dc   .flex.itemsSource = response.data.data.dc;      //할인내역
																    	var reportCtrl_dcdtl    = agrid.getScope("reportCtrl_dcdtl");   reportCtrl_dcdtl.flex.itemsSource = response.data.data.dcdtl;   //할인상세내역
																    	var reportCtrl_gift     = agrid.getScope("reportCtrl_gift");    reportCtrl_gift .flex.itemsSource = response.data.data.gift;    //상품권 판매 및 회수내역
																    	var reportCtrl_order    = agrid.getScope("reportCtrl_order");   reportCtrl_order.flex.itemsSource = response.data.data.order;   //수발주내역
																    	var reportCtrl_lv1      = agrid.getScope("reportCtrl_lv1");     reportCtrl_lv1  .flex.itemsSource = response.data.data.lv1;     //대분류별 매출
																    	var reportCtrl_lv2      = agrid.getScope("reportCtrl_lv2");     reportCtrl_lv2  .flex.itemsSource = response.data.data.lv2;     //중분류별 매출
																    	var reportCtrl_lv3      = agrid.getScope("reportCtrl_lv3");     reportCtrl_lv3  .flex.itemsSource = response.data.data.lv3;     //소분류별 매출
																    	var reportCtrl_prod     = agrid.getScope("reportCtrl_prod");    reportCtrl_prod .flex.itemsSource = response.data.data.prod;    //상품별 매출
																    	var reportCtrl_compt    = agrid.getScope("reportCtrl_compt");   reportCtrl_compt.flex.itemsSource = response.data.data.compt;   //경쟁사매출
																    	var reportCtrl_appr     = agrid.getScope("reportCtrl_appr");    reportCtrl_appr .flex.itemsSource = response.data.data.appr;    //승인현황
																    	var reportCtrl_membr    = agrid.getScope("reportCtrl_membr");   reportCtrl_membr.flex.itemsSource = response.data.data.membr;   //회원
																    	var reportCtrl_work     = agrid.getScope("reportCtrl_work");    reportCtrl_work .flex.itemsSource = response.data.data.work;    //근태관리

															            //데이터 setting > 영업일보 구성
															            var configCtrl_1 = agrid.getScope('configCtrl_1');	configCtrl_1.flex.itemsSource = response.data.data.payline;	//결재라인
															            var configCtrl_2 = agrid.getScope('configCtrl_2');	configCtrl_2.flex.itemsSource = response.data.data.cfg;		//영업일보 구성



															            //[영업일보 구성] check-box setting
															            for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){
															                var item = configCtrl_2.flex.collectionView.items[i];															                
															                //console.log('item:' + item);

															                if(item.cfgCd    == "SL"){
															                	item.cfgSelYn	= "Y";	//[매출종합]은 무조건 'checked' (처음 접속 or '영업일보 구성'을 저장하지 않은 매장의 경우 '영업일보 구성'에서 선택된 것이 없기에, [매출종합]은 선택되어 보여지게 함)
//																				item.cfgDispSeq = 1;
															                }
//															                //item.gChkDispSeq = false;	//순번 check-box는 모두 미선택 상태로
															                
															            	if(item.cfgSelYn == "Y"){
															            		item.gChk = true;
															            		console.log("item.cfgCd")
                                                                              //eval( '$(".div_'  + item.cfgCd + '").show();' );
                                                                                eval( '$("#div_'  + item.cfgCd + '").show();' );
                                                                                eval( '$("#span_' + item.cfgCd + '").show();' );

															            	}else{
                                                                              //eval( '$(".div_'  + item.cfgCd + '").hide();' );	//[영업일보 구성]에 없으면 숨기기
                                                                                eval( '$("#div_'  + item.cfgCd + '").hide();' );	//[영업일보 구성]에 없으면 숨기기
                                                                                eval( '$("#span_' + item.cfgCd + '").hide();' );
                                                                            }
															            	
															            	arrDispSeq[i] = new Array(item.cfgCd, item.cfgDispSeq, item.cfgSelYn);	//[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
															            	
															            	//console.log('Before: ' + item.cfgCd + ' & ' +  item.cfgDispSeq +   ' & ' + eval( '$("#div_sort_id_' + item.cfgCd + '").data("sort");' )   );
															            	//eval( '$("#div_sort_id_' + item.cfgCd + '").data(' + '"sort",' + item.cfgDispSeq + ');' );
															            	//console.log('After : ' + item.cfgCd + ' & ' +  item.cfgDispSeq +   ' & ' + eval( '$("#div_sort_id_' + item.cfgCd + '").data("sort");' )   );
															            	eval( '$("#div_sort_id_' + item.cfgCd + '").data(' + '"sort",' + item.cfgDispSeq + ');' );

															            }	//for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){

															            //'영업일보(0000-00-00 ~ 0000-00-00)' 문구 setting
															            var reportCtrl_excel = agrid.getScope('reportCtrl_excel');
															            reportCtrl_excel.span_startDate	= wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd');
															            reportCtrl_excel.span_endDate	= wijmo.Globalize.format(endDate  .value, 'yyyy-MM-dd');

															            //2020.02.24 - '본사'인 경우에도 [결제라인] [영업일보] button 보이기
															            if($scope.orgnFg == "H") {	//H:본사, S:가맹점, M:?? -> 매장이 'M'으로 보이는 경우도 있음
																    	    $("#btnAdd"		).show();
																    	    $("#btnDel"		).show();
																    	    $("#btnSave"	).show();

																    		$("#btnSave2"	).show();
																    		$("#btnUp"	 	).show();
																    		$("#btnDown"	).show();																    		
															    		}
															            
//----------------------------------------------------------------------------------------------------------------------------------------------------
															            /*
															            //2차원 배열의 요소들을 하나씩 출력
															            for(var i=0; i<arrDispSeq.length; i++){
															            	console.log('Before: ' + arrDispSeq[i][0] + ', ' + arrDispSeq[i][1]);
															            }															            
															            //[영업일보 구성] 정렬 순서
															            arrDispSeq.sort(function (name, seq){
															                var idx = 1;    //0:구성내역명칭, 1:정렬순서

															                switch(idx){
															                    case 0: //문자 - 문자열 정렬 (문자열 정렬이 기본이기에 다른작업 안해도 됨)
															                            name[idx] = name[idx].toString().toLowerCase();
															                            seq [idx] = seq [idx].toString().toLowerCase();
															                            break;

															                    case 1: //정렬순서 - 정수변환
															                            name[idx] = parseInt(name[idx]);
															                            seq [idx] = parseInt(seq [idx]);
															                            break;
															                }
															                return (name[idx] < seq[idx])? -1 : ((name[idx] > seq[idx])? 1 : 0);
															            });
															            
															            //2차원 배열의 요소들을 하나씩 출력
															            for(var i=0; i<arrDispSeq.length; i++){
															            	console.log('After : ' + arrDispSeq[i][0] + ', ' + arrDispSeq[i][1]);
															            }
															            */

															            /*
															    	    var values = [];
															    	    $("#reportView.div_sort_class").each(function(index) {
															    	        var item        = {};
															    	            item.index  = index;
															    	            item.obj    = this;
															    	            item.value  = $(this).data("sort");
															    	        values.push(item);
															    	    });
															    	    
															    	    values.sort( function(a, b){return(b.value - a.value);} );	    
															    	    var container = $("#reportView");
															    	    for (var i=0; i<values.length; i++) {
															    	        var self = $( values[i].obj );
															    	            self.detach();
															    	        container.prepend(self);
															    	    }
															    	    //return;
															            */

															            sortByDataItem("reportView");

//----------------------------------------------------------------------------------------------------------------------------------------------------															        	
															    	    
															            
															            $scope.$broadcast('loadingPopupInactive');	//'데이터 처리 중입니다.' 중지
																	},	//callBack function
												false);
    };	//$scope.reportCtrlList = function(data){
    //[조회] - END			--------------------------------------------------------------------------------------------------------------------------



	//접기 & 펴기 - START			----------------------------------------------------------------------------------------------------------------------
    /*
	$(".flddUnfld_sl"  	).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_SL"	   ).toggle();  });  //매출종합
    $(".flddUnfld_pay"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_PAY"   ).toggle();  });  //결제수단
    $(".flddUnfld_nsl"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_NSL"   ).toggle();  });  //비매출종합
    $(".flddUnfld_npay" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_NPAY"  ).toggle();  });  //비매출결제수단
    $(".flddUnfld_pos"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_POS"   ).toggle();  });  //포스정산
    $(".flddUnfld_emp"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_EMP"   ).toggle();  });  //판매원별 매출
    $(".flddUnfld_dc"   ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_DC"    ).toggle();  });  //할인내역
    $(".flddUnfld_dcdtl").click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_DCDTL" ).toggle();  });  //할인상세내역
    $(".flddUnfld_gift" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_GIFT"  ).toggle();  });  //상품권 판매 및 회수내역
    $(".flddUnfld_order").click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_ORDER" ).toggle();  });  //수발주내역
    $(".flddUnfld_lv1"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_LV1"   ).toggle();  });  //대분류별 매출
    $(".flddUnfld_lv2"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_LV2"   ).toggle();  });  //중분류별 매출
    $(".flddUnfld_lv3"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_LV3"   ).toggle();  });  //소분류별 매출
    $(".flddUnfld_prod" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_PROD"  ).toggle();  });  //상품별 매출
    $(".flddUnfld_compt").click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_COMPT" ).toggle();  });  //경쟁사매출
    $(".flddUnfld_appr" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_APPR"  ).toggle();  });  //승인현황
    $(".flddUnfld_membr").click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_MEMBR" ).toggle();  });  //회원
    $(".flddUnfld_work" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_WORK"  ).toggle();  });  //근태관리
    */
    //접기 & 펴기 - END				----------------------------------------------------------------------------------------------------------------------

}]);
//reportCtrl		END		############################################################################################################################################################################






//reportCtrl_sl    START   ############################################################################################################################################################################
app.controller('reportCtrl_sl', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('reportCtrl_sl', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {
		
        $scope._makePickColumns("reportCtrl_sl");   	        //picker 사용시 호출

	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
	    s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

	    s.itemFormatter = function (panel, r, c, cell) {
	        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
	            panel.rows   [r].allowMerging	= true;
	            panel.columns[c].allowMerging	= true;

	            wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
	            );

	            cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

	            wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
	            );
	        }
	        else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
	            if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
	                cell.textContent = '';
	            } else {
	                if (!isEmpty(panel._rows[r]._data.rnum)) {
	                    cell.textContent = (panel._rows[r]._data.rnum).toString();
	                } else {
	                    cell.textContent = (r + 1).toString();
	                }
	            }
	        }
	        else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
	            var col = panel.columns[c];
	            if (col.isReadOnly) {
	                wijmo.addClass(cell, 'wj-custom-readonly');
	            }
	        }
	    }	//s.itemFormatter = function (panel, r, c, cell) {

	    // 그리드 클릭 이벤트
    	s.addEventListener(s.hostElement, 'mousedown', function (e) {
	    	var ht = s.hitTest(e);

	    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
        		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
        		if (rng && rng.columnSpan > 1) {
        			e.preventDefault();
        		}
        	}
    	}, true);

	};
}]);
//reportCtrl_sl    	END		############################################################################################################################################################################



//reportCtrl_pay    START   ############################################################################################################################################################################
app.controller('reportCtrl_pay', ['$scope', '$http', function ($scope, $http) {
    angular.extend(this, new RootController('reportCtrl_pay', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

    $scope.initGrid = function (s, e) {
        $scope._makePickColumns("reportCtrl_pay");   	        //picker 사용시 호출

        //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
        //s.bottomLeftCells.setCellData(0, 0, '합계');			//add a sigma to the header to show that this is a summary row

        //Grid Header 2줄 - START	----------------------------------------------------------------
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        //첫째줄 Header 생성
        var dataItem = {};
            dataItem.payRealSaleAmt     = messages["dailyReport.payRealSaleAmt"];
            dataItem.payTotTipAmt       = messages["dailyReport.payTotTipAmt"  ];
            dataItem.payTotEtcAmt       = messages["dailyReport.payTotEtcAmt"  ];
            dataItem.payCardAmt         = messages["dailyReport.pay"];
            dataItem.payCashAmt     	= messages["dailyReport.pay"];
            dataItem.payPaycoAmt    	= messages["dailyReport.pay"];
            dataItem.payVpointAmt   	= messages["dailyReport.pay"];
            dataItem.payVcoupnAmt   	= messages["dailyReport.pay"];
            dataItem.payVchargeAmt  	= messages["dailyReport.pay"];
            dataItem.payMpayAmt     	= messages["dailyReport.pay"];
            dataItem.payMcoupnAmt   	= messages["dailyReport.pay"];
            dataItem.payMembrAmt    	= messages["dailyReport.pay"];
            dataItem.payPrepaidAmt  	= messages["dailyReport.pay"];
            dataItem.payPostpaidAmt 	= messages["dailyReport.pay"];
            dataItem.payCoupnAmt    	= messages["dailyReport.pay"];
            dataItem.payGiftAmt     	= messages["dailyReport.pay"];
            dataItem.payFstmpAmt    	= messages["dailyReport.pay"];
            dataItem.payPartnerAmt  	= messages["dailyReport.pay"];
            dataItem.payOkcsbAmt    	= messages["dailyReport.pay"];
            dataItem.payEmpCardAmt  	= messages["dailyReport.pay"];
            dataItem.payTemporaryAmt 	= messages["dailyReport.pay"];
            dataItem.paySmartOrderAmt 	= messages["dailyReport.pay"];
        s.columnHeaders.rows[0].dataItem = dataItem;
        //Grid Header 2줄 - END		----------------------------------------------------------------

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
                panel.rows   [r].allowMerging	= true;
                panel.columns[c].allowMerging	= true;

                wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
                );

                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

                wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
			  				                    textAlign    : 'center'
		  					                	}
                );
            }
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }	//s.itemFormatter = function (panel, r, c, cell) {

        // 그리드 클릭 이벤트
    	s.addEventListener(s.hostElement, 'mousedown', function (e) {
	    	var ht = s.hitTest(e);

	    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
        		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
        		if (rng && rng.columnSpan > 1) {
        			e.preventDefault();
        		}
        	}
    	}, true);

    }
}]);
//reportCtrl_pay    END		############################################################################################################################################################################



//reportCtrl_nsl 	START   ############################################################################################################################################################################
app.controller('reportCtrl_nsl', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_nsl', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_nsl");   	        //picker 사용시 호출

      s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      s.bottomLeftCells.setCellData(0, 0, '합계');			//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);

  }
}]);
//reportCtrl_nsl    END		############################################################################################################################################################################



//reportCtrl_npay 	START   ############################################################################################################################################################################
app.controller('reportCtrl_npay', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_npay', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_npay");   	        //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      //Grid Header 2줄 - START	----------------------------------------------------------------
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());

      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.npayRealSaleAmt    = messages["dailyReport.npayRealSaleAmt"];
          dataItem.npayTotTipAmt      = messages["dailyReport.npayTotTipAmt"  ];
          dataItem.npayTotEtcAmt      = messages["dailyReport.npayTotEtcAmt"  ];
          dataItem.npayCardAmt        = messages["dailyReport.npay"];
          dataItem.npayCashAmt   	    = messages["dailyReport.npay"];
      s.columnHeaders.rows[0].dataItem = dataItem;
      //Grid Header 2줄 - END		----------------------------------------------------------------

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);

  }
}]);
//reportCtrl_npay   END		############################################################################################################################################################################




//reportCtrl_pos  	START   ############################################################################################################################################################################
app.controller('reportCtrl_pos', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_pos', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_pos");   	            //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_pos    END		############################################################################################################################################################################




//reportCtrl_emp  	START   ############################################################################################################################################################################
app.controller('reportCtrl_emp', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_emp', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_emp");   	            //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      //Grid Header 2줄 - START	----------------------------------------------------------------
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());

      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.empNm              = messages["dailyReport.empNm"];
          dataItem.npayTotTipAmt      = messages["dailyReport.npayTotTipAmt"];
          dataItem.npayTotEtcAmt      = messages["dailyReport.npayTotEtcAmt"];

          dataItem.empSalCnt          = messages["dailyReport.empSal"];
          dataItem.empSalTotalCard    = messages["dailyReport.empSal"];
          dataItem.empSalTotalCash    = messages["dailyReport.empSal"];
          dataItem.empSalTotalEtc     = messages["dailyReport.empSal"];

          dataItem.empRtnCnt          = messages["dailyReport.empRtn"];
          dataItem.empRtnTotalCard    = messages["dailyReport.empRtn"];
          dataItem.empRtnTotalCash    = messages["dailyReport.empRtn"];
          dataItem.empRtnTotalEtc     = messages["dailyReport.empRtn"];

          dataItem.empCancelCnt       = messages["dailyReport.empCancelCnt"];
      s.columnHeaders.rows[0].dataItem = dataItem;
      //Grid Header 2줄 - END		----------------------------------------------------------------

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_emp    END		############################################################################################################################################################################




//reportCtrl_dc   	START   ############################################################################################################################################################################
app.controller('reportCtrl_dc', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_dc', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_dc");   	            //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_dc     END		############################################################################################################################################################################




//reportCtrl_dcdtl	START   ############################################################################################################################################################################
app.controller('reportCtrl_dcdtl', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_dcdtl', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_dcdtl");   	        //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_dcdtl  END		############################################################################################################################################################################




//reportCtrl_gift 	START   ############################################################################################################################################################################
app.controller('reportCtrl_gift', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_gift', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_gift");   	        //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      //Grid Header 2줄 - START	----------------------------------------------------------------
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());

      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.giftCd             = messages["dailyReport.giftCd"];
          dataItem.giftNm             = messages["dailyReport.giftNm"];
          dataItem.giftUprc           = messages["dailyReport.giftUprc"];

          dataItem.giftOutQty         = messages["dailyReport.giftOut"];
          dataItem.giftOutAmt         = messages["dailyReport.giftOut"];

          dataItem.giftSaleQty        = messages["dailyReport.giftSale"];
          dataItem.giftSaleAmt        = messages["dailyReport.giftSale"];

          dataItem.giftRtnQty         = messages["dailyReport.giftRtn"];
          dataItem.giftRtnAmt         = messages["dailyReport.giftRtn"];

          dataItem.giftRtnCarryInQty  = messages["dailyReport.giftRtnCarryIn"];
          dataItem.giftRtnCarryInAmt  = messages["dailyReport.giftRtnCarryIn"];
      s.columnHeaders.rows[0].dataItem = dataItem;
	//Grid Header 2줄 - END		----------------------------------------------------------------

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_gift   END		############################################################################################################################################################################




//reportCtrl_order	START   ############################################################################################################################################################################
app.controller('reportCtrl_order', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_order', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_order");          //picker 사용시 호출

      s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      s.bottomLeftCells.setCellData(0, 0, '합계');			//add a sigma to the header to show that this is a summary row

      //Grid Header 2줄 - START	----------------------------------------------------------------
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());

      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.orderOutHqOut      = messages["dailyReport.orderOut"];
          dataItem.orderOutStoreCfm   = messages["dailyReport.orderOut"];
          dataItem.orderOutError      = messages["dailyReport.orderOut"];

          dataItem.orderRtnHqOut      = messages["dailyReport.orderRtn"];
          dataItem.orderRtnStoreCfm   = messages["dailyReport.orderRtn"];
          dataItem.orderRtnError      = messages["dailyReport.orderRtn"];
          dataItem.orderRtnPenalty    = messages["dailyReport.orderRtn"];
      s.columnHeaders.rows[0].dataItem = dataItem;
      //Grid Header 2줄 - END		----------------------------------------------------------------

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_order  END		############################################################################################################################################################################




//reportCtrl_lv1  	START   ############################################################################################################################################################################
app.controller('reportCtrl_lv1', ['$scope', '$http', function ($scope, $http) {
angular.extend(this, new RootController('reportCtrl_lv1', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

$scope.initGrid = function (s, e) {
    $scope._makePickColumns("reportCtrl_lv1");              //picker 사용시 호출

    s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
    s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_lv1    END		############################################################################################################################################################################




//reportCtrl_lv2  	START   ############################################################################################################################################################################
app.controller('reportCtrl_lv2', ['$scope', '$http', function ($scope, $http) {
angular.extend(this, new RootController('reportCtrl_lv2', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

$scope.initGrid = function (s, e) {
    $scope._makePickColumns("reportCtrl_lv2");              //picker 사용시 호출

    s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
    s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_lv2    END		############################################################################################################################################################################




//reportCtrl_lv3  	START   ############################################################################################################################################################################
app.controller('reportCtrl_lv3', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_lv3', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_lv3");            //picker 사용시 호출

      s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      s.bottomLeftCells.setCellData(0, 0, '합계');          //add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_lv3    END		############################################################################################################################################################################




//reportCtrl_prod 	START   ############################################################################################################################################################################
app.controller('reportCtrl_prod', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_prod', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_prod");           //picker 사용시 호출

      s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      s.bottomLeftCells.setCellData(0, 0, '합계');          //add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_prod   END		############################################################################################################################################################################




//reportCtrl_compt	START   ############################################################################################################################################################################
app.controller('reportCtrl_compt', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_compt', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_compt");              //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_compt  END		############################################################################################################################################################################




//reportCtrl_appr 	START   ############################################################################################################################################################################
app.controller('reportCtrl_appr', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_appr', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_appr");           //picker 사용시 호출

      s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      s.bottomLeftCells.setCellData(0, 0, '합계');		    //add a sigma to the header to show that this is a summary row

      //Grid Header 2줄 - START	----------------------------------------------------------------
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());

      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.apprNm        		= messages["dailyReport.emptySpace"];

          dataItem.apprCntCard        = messages["dailyReport.apprCard"];
          dataItem.apprApCard         = messages["dailyReport.apprCard"];
          dataItem.apprDcCard         = messages["dailyReport.apprCard"];

          dataItem.apprCntCash        = messages["dailyReport.apprCash"];
          dataItem.apprApCash         = messages["dailyReport.apprCash"];
          dataItem.apprDcCash         = messages["dailyReport.apprCash"];

          dataItem.apprCntPayco       = messages["dailyReport.apprPayco"];
          dataItem.apprApPayco        = messages["dailyReport.apprPayco"];
          dataItem.apprDcPayco        = messages["dailyReport.apprPayco"];

          dataItem.apprCntMpay        = messages["dailyReport.apprMpay"];
          dataItem.apprApMpay         = messages["dailyReport.apprMpay"];
          dataItem.apprDcMpay         = messages["dailyReport.apprMpay"];

          dataItem.apprCntMcoupn      = messages["dailyReport.apprMcoupn"];
          dataItem.apprApMcoupn       = messages["dailyReport.apprMcoupn"];
          dataItem.apprDcMcoupn       = messages["dailyReport.apprMcoupn"];

          dataItem.apprCntPartner     = messages["dailyReport.apprPartner"];
          dataItem.apprApPartner      = messages["dailyReport.apprPartner"];
          dataItem.apprDcPartner      = messages["dailyReport.apprPartner"];

          dataItem.apprCntNcard       = messages["dailyReport.apprNcard"];
          dataItem.apprApNcard        = messages["dailyReport.apprNcard"];
          dataItem.apprDcNcard        = messages["dailyReport.apprNcard"];

          dataItem.apprCntNcash       = messages["dailyReport.apprNcash"];
          dataItem.apprApNcash        = messages["dailyReport.apprNcash"];
          dataItem.apprDcNcash        = messages["dailyReport.apprNcash"];
      s.columnHeaders.rows[0].dataItem = dataItem;
      //Grid Header 2줄 - END		----------------------------------------------------------------

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_appr   END		############################################################################################################################################################################




//reportCtrl_membr	START   ############################################################################################################################################################################
app.controller('reportCtrl_membr', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_membr', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_membr");              //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_membr  END		############################################################################################################################################################################




//reportCtrl_work   START   ############################################################################################################################################################################
app.controller('reportCtrl_work', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('reportCtrl_work', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("reportCtrl_work");                //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
              panel.rows   [r].allowMerging	= true;
              panel.columns[c].allowMerging	= true;

              wijmo.setCss(cell, {
				                    display    : 'table',
				                    tableLayout: 'fixed'
				                	}
              );

              cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

              wijmo.setCss(cell.children[0], {
							                    display      : 'table-cell',
							                    verticalAlign: 'middle',
							                    textAlign    : 'center'
							                	}
              );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
              if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
                  cell.textContent = '';
              } else {
                  if (!isEmpty(panel._rows[r]._data.rnum)) {
                      cell.textContent = (panel._rows[r]._data.rnum).toString();
                  } else {
                      cell.textContent = (r + 1).toString();
                  }
              }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
              var col = panel.columns[c];
              if (col.isReadOnly) {
                  wijmo.addClass(cell, 'wj-custom-readonly');
              }
          }
      }	//s.itemFormatter = function (panel, r, c, cell) {

	   // 그리드 클릭 이벤트
	  	s.addEventListener(s.hostElement, 'mousedown', function (e) {
		    	var ht = s.hitTest(e);

		    	if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	      		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	      		if (rng && rng.columnSpan > 1) {
	      			e.preventDefault();
	      		}
	      	}
	  	}, true);
  }
}]);
//reportCtrl_work   END		############################################################################################################################################################################



//reportCtrl_excel	START	############################################################################################################################################################################
app.controller('reportCtrl_excel', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	//'영업일보(0000-00-00 ~ 0000-00-00)' 문구
	$scope.span_startDate 	= wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd');
	$scope.span_endDate		= wijmo.Globalize.format(endDate  .value, 'yyyy-MM-dd');

	//excelDownload		START	----------------------------------------------------------------------------------------------------------------------
	$scope.excelDownload = function(){

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1년(365일) 제한
        if (diffDay > 365) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }

		var reportCtrl_sl = agrid.getScope("reportCtrl_sl");      //매출종합
		if (reportCtrl_sl.flex.rows.length <= 0) {
			  //$scope._popMsg( messages["excelUpload.not.downloadData"] );	//다운로드 할 데이터가 없습니다.
				s_alert.pop( messages["excelUpload.not.downloadData"] );	//다운로드 할 데이터가 없습니다.
				return false;
		}

		var dateFrom		= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
		var dateTo			= wijmo.Globalize.format(endDate  .value, 'yyyy.MM.dd');
	  //var excelFileName = '영업일보(' + dateFrom + ' ~ ' + dateTo + ').xlsx';	//파일명의 '~'  -->  '_'로 자동 치환됨.
		var excelFileName = '영업일보(' + dateFrom + ' - ' + dateTo + ').xlsx';

	    //$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
		var reportCtrl = agrid.getScope("reportCtrl");
			reportCtrl.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.

		$timeout(function()	{
						      //var reportCtrl_sl       = agrid.getScope("reportCtrl_sl");      //매출종합
						    	var reportCtrl_pay      = agrid.getScope("reportCtrl_pay");     //결제수단
						    	var reportCtrl_nsl      = agrid.getScope("reportCtrl_nsl");     //비매출종합
						    	var reportCtrl_npay     = agrid.getScope("reportCtrl_npay");    //비매출결제수단
						    	var reportCtrl_pos      = agrid.getScope("reportCtrl_pos");     //포스정산
						    	var reportCtrl_emp      = agrid.getScope("reportCtrl_emp");     //판매원별 매출
						    	var reportCtrl_dc       = agrid.getScope("reportCtrl_dc");      //할인내역
						    	var reportCtrl_dcdtl    = agrid.getScope("reportCtrl_dcdtl");   //할인상세내역
						    	var reportCtrl_gift     = agrid.getScope("reportCtrl_gift");    //상품권 판매 및 회수내역
						    	var reportCtrl_order    = agrid.getScope("reportCtrl_order");   //수발주내역
						    	var reportCtrl_lv1      = agrid.getScope("reportCtrl_lv1");     //대분류별 매출
						    	var reportCtrl_lv2      = agrid.getScope("reportCtrl_lv2");     //중분류별 매출
						    	var reportCtrl_lv3      = agrid.getScope("reportCtrl_lv3");     //소분류별 매출
						    	var reportCtrl_prod     = agrid.getScope("reportCtrl_prod");    //상품별 매출
						    	var reportCtrl_compt    = agrid.getScope("reportCtrl_compt");   //경쟁사매출
						    	var reportCtrl_appr     = agrid.getScope("reportCtrl_appr");    //승인현황
						    	var reportCtrl_membr    = agrid.getScope("reportCtrl_membr");   //회원
						    	var reportCtrl_work     = agrid.getScope("reportCtrl_work");    //근태관리

								var	sl		= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_sl   .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.sl"		]}	);  //매출종합
								var pay		= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_pay  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.pay"		]}	);  //결제수단
								var nsl 	= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_nsl  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.nsl"		]}	);  //비매출종합
							    var npay    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_npay .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.npay" 	]}	);  //비매출결제수단
							    var pos     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_pos  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.pos"  	]}	);  //포스정산
							    var emp     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_emp  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.emp"  	]}	);  //판매원별 매출
							    var dc      = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_dc   .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.dc"		]}	);  //할인내역
							    var dcdtl   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_dcdtl.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.dcdtl"	]}	);  //할인상세내역
							    var gift    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_gift .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.gift" 	]}	);  //상품권 판매 및 회수내역
							    var order   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_order.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.order"	]}	);  //수발주내역
							    var lv1     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_lv1  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.lv1"		]}	);  //대분류별 매출
							    var lv2     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_lv2  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.lv2"  	]}	);  //중분류별 매출
							    var lv3     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_lv3  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.lv3"  	]}	);  //소분류별 매출
							    var prod    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_prod .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.prod" 	]}	);  //상품별 매출
							    var compt   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_compt.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.compt"	]}	);  //경쟁사매출
							    var appr    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_appr .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.appr" 	]}	);  //승인현황
							    var membr   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_membr.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.membr"	]}	);  //회원
							    var work    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_work .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.work" 	]}	);  //근태관리

							    /*
								sl.sheets.push(pay	.sheets[0]);    //결제수단
								sl.sheets.push(nsl	.sheets[0]);    //비매출종합
							    sl.sheets.push(npay .sheets[0]);    //비매출결제수단
							    sl.sheets.push(pos  .sheets[0]);    //포스정산
							    sl.sheets.push(emp  .sheets[0]);    //판매원별 매출
							    sl.sheets.push(dc   .sheets[0]);    //할인내역
							    sl.sheets.push(dcdtl.sheets[0]);    //할인상세내역
							    sl.sheets.push(gift .sheets[0]);    //상품권 판매 및 회수내역
							    sl.sheets.push(order.sheets[0]);    //수발주내역
							    sl.sheets.push(lv1  .sheets[0]);    //대분류별 매출
							    sl.sheets.push(lv2  .sheets[0]);    //중분류별 매출
							    sl.sheets.push(lv3  .sheets[0]);    //소분류별 매출
							    sl.sheets.push(prod .sheets[0]);    //상품별 매출
							    sl.sheets.push(compt.sheets[0]);    //경쟁사매출
							    sl.sheets.push(appr .sheets[0]);    //승인현황
							    sl.sheets.push(membr.sheets[0]);    //회원
							    sl.sheets.push(work .sheets[0]);    //근태관리
								*/

							/* 2020.04: [영업일보 구성]에서의 정렬 순서로 출력							    
							    //[영업일보 구성]에서 선택된 부분만 엑셀 출력
                                var configCtrl_2 = agrid.getScope('configCtrl_2');	//영업일보 구성
					            for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){
					                var item = configCtrl_2.flex.collectionView.items[i];
					                if(item.cfgCd == 'SL')	continue;
					            	if(item.cfgSelYn == "Y"){
                                        eval( 'sl.sheets.push(' + item.cfgCd.toLowerCase() + '.sheets[0]);' );
					            	}
					            }
							 */							    
							    //arrDispSeq -> [영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
							    var firstSheetYN = "N";
							    var firstSheet;

					            for(var i=0; i<arrDispSeq.length; i++){
					            	if(arrDispSeq[i][2] == "Y"   &&   firstSheetYN == "Y"){	
					            		eval( 'firstSheet.sheets.push('+ arrDispSeq[i][0].toLowerCase() + '.sheets[0]);' );
					            	}
					            	if(arrDispSeq[i][2] == "Y"   &&   firstSheetYN == "N"){	
					            		firstSheetYN 	= "Y";
					            		firstSheet 		= eval('wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(reportCtrl_' + arrDispSeq[i][0].toLowerCase() + '.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.' + arrDispSeq[i][0].toLowerCase() + '"]});');
					            	}					            	
					            }
					            firstSheet.saveAsync(excelFileName);

								//$scope.$broadcast('loadingPopupInactive');
								reportCtrl.$broadcast('loadingPopupInactive');
							}, 1000);	//건수가 많아서 1000으로 했음 (현재 1년치 정도가 500ms 미만임)
	};
	//excelDownload		END		----------------------------------------------------------------------------------------------------------------------



	//print				START	----------------------------------------------------------------------------------------------------------------------
	$scope.print_OLD_XXX = function(){
		var reportCtrl_sl = agrid.getScope("reportCtrl_sl");      //매출종합
		if (reportCtrl_sl.flex.rows.length <= 0) {
				s_alert.pop( messages["dailyReport.alert.noDataToPrint"] );	//출력할 자료가 없습니다.
				return false;
		}

		var dateFrom	= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
		var dateTo		= wijmo.Globalize.format(endDate  .value, 'yyyy.MM.dd');
	  //var printTitle	= '영업일보(' + dateFrom + ' ~ ' + dateTo + ')';	//파일명의 '~'  -->  '_'로 자동 치환됨.
		var printTitle	= '영업일보(' + dateFrom + ' - ' + dateTo + ')';

	    var doc 	= new wijmo.PrintDocument( {title : printTitle} );

	    var browser = navigator.userAgent.toLowerCase();	//브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
	    if (-1 != browser.indexOf('chrome')) {
	    	//doc.append('<div style="height: 9mm;"></div>');
	    }
	    /*
	    cf> <div id="reportView" class="subCon">

	    var view = document.querySelector('#reportView');
	    doc.append(view);

	    doc.append( document.querySelector('#reportView') );	OK

	    doc.append( document.querySelector('#subCon') );		X

	    doc.append( document.querySelector('#div_print') );		<div id="div_print"> 추가 -> 현재 지웠음
		*/
	    doc.append( document.querySelector('#div_print') );

	    doc.print();
	};
	//print				END		----------------------------------------------------------------------------------------------------------------------


	//print				STATT	----------------------------------------------------------------------------------------------------------------------
	$scope.print = function(){
		var reportCtrl_sl = agrid.getScope("reportCtrl_sl");      //매출종합
		if (reportCtrl_sl.flex.rows.length <= 0) {
				s_alert.pop( messages["dailyReport.alert.noDataToPrint"] );	//출력할 자료가 없습니다.
				return false;
		}

		var dateFrom	= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
		var dateTo		= wijmo.Globalize.format(endDate  .value, 'yyyy.MM.dd');
		var printTitle	= '영업일보(' + dateFrom + ' - ' + dateTo + ')';	//파일명의 '~'  -->  '_'로 자동 치환됨.

	  //let doc = new wjcCore.PrintDocument	({
        let doc = new wijmo.PrintDocument	({
                                              //title	: printTitle,   --> 아래의 제목스타일 '영업일보 기간'으로 대체
                                                title	: '',
								            	copyCss	: true //false가 낫다 -> To prevent cross-origin issues
        									});

        //https://www.grapecity.com/wijmo/api/classes/wijmo.printdocument.html
        //add CSS explicitly (since we can't use copyCss in jsfiddle)
        //doc.append('<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">');
        //doc.append('<link href="https://cdn.grapecity.com/wijmo/5.latest/styles/wijmo.min.css"         rel="stylesheet">');
        /*
        doc.addCSS("/resource/solbipos/css/cmm/style.css");

        doc.append('<link rel="stylesheet" type="text/css" href="/resource/vendor/wijmo/css/app.css"	/>');
        doc.append('<link rel="stylesheet" type="text/css" href="/resource/vendor/wijmo/css/wijmo.css"	/>');
        doc.append('<link rel="stylesheet" type="text/css" href="/resource/solbipos/css/cmm/wijmo.solbi.custom.css"	/>');
        doc.append('<link rel="stylesheet" type="text/css" href="/resource/solbipos/css/cmm/style.css"	/>');
		*/
		doc.append('<link rel="stylesheet" type="text/css" href="/resource/solbipos/css/cmm/style.css"	/>');

        //영업일보 기간
            doc.append('<h3 align="center">' + printTitle + '</h3><br><br><br>');

        //결제라인	START	----------------------------------------------------------------------------
            var flex_payline        = agrid.getScope('configCtrl_1').flex; //결제라인 Grid
            var arr_cfgPayLineSeq   = new Array();
            var arr_cfgPayLineNm    = new Array();
            var tmp_table           = "";

            for(var i=0; i<flex_payline.collectionView.items.length; i++){
                var item = flex_payline.collectionView.items[i];
                //console.log('arr_cfgPayLineSeq:' + item.cfgPayLineSeq);
                //console.log('arr_cfgPayLineNm :' + item.cfgPayLineNm );
                arr_cfgPayLineSeq.push(item.cfgPayLineSeq);
                arr_cfgPayLineNm .push(item.cfgPayLineNm );
            }
            /*
            tmp_table += '<table class="reportPrint" align="right">';
            tmp_table += '    <thead>';
            tmp_table += '        <tr>';
            for(var i=0; i<flex_payline.rows.length; i++){
                tmp_table += '        <th colspan="2" style="width:120px;text-align:center;padding-right: 6px">' + arr_cfgPayLineSeq[i] + '</th>';
            }
            tmp_table += '        </tr>';
            tmp_table += '    </thead>';
            tmp_table += '    <tbody>';
            tmp_table += '        <tr>';
            for(var i=0; i<flex_payline.rows.length; i++){
                tmp_table += '        <td style="width:80px;text-align:center;padding-right: 6px">' + arr_cfgPayLineNm[i] + '</td>';
                tmp_table += '        <td style="width:40px;text-align:center;padding-right: 6px"></td>';
            }
            tmp_table += '        </tr>';
            tmp_table += '    </tbody>';
            tmp_table += '</table><br><br><br><br><br><br>';
            doc.append(tmp_table);
            */
            tmp_table += '<table class="reportPrint" align="right">';
            tmp_table += '    <thead>';
            tmp_table += '        <tr>';
            for(var i=0; i<flex_payline.rows.length; i++){
                tmp_table += '        <th style="width:100px;text-align:center;padding-right: 6px">' + arr_cfgPayLineNm[i] + '</th>';
            }
            tmp_table += '        </tr>';
            tmp_table += '    </thead>';
            tmp_table += '    <tbody>';
            tmp_table += '        <tr>';
            for(var i=0; i<flex_payline.rows.length; i++){
                tmp_table += '        <td height="60px"></td>';	//참고: .reportPrint tr {height: 30px;} -> '서명'부분 2배크기로 설정함
            }
            tmp_table += '        </tr>';
            tmp_table += '    </tbody>';
            tmp_table += '</table><br><br><br><br><br><br>';

          //doc.append('<h3 align="right">' + messages["dailyReport.cfgPayLine"] + '</h3><br>');
            doc.append(tmp_table);
        //결제라인	END		----------------------------------------------------------------------------

    /* 2020.04: [영업일보 구성]에서의 정렬 순서로 출력
        //매출종합
            doc.append('<h3>' + messages["dailyReport.sl"] + '</h3><br>');
            doc.append( this._renderTable(reportCtrl_sl.flex) );
    */

        var reportCtrl_pay      = agrid.getScope("reportCtrl_pay"   );
        var reportCtrl_nsl      = agrid.getScope("reportCtrl_nsl"   );
        var reportCtrl_npay     = agrid.getScope("reportCtrl_npay"  );
        var reportCtrl_pos      = agrid.getScope("reportCtrl_pos"   );
        var reportCtrl_emp      = agrid.getScope("reportCtrl_emp"   );
        var reportCtrl_dc       = agrid.getScope("reportCtrl_dc"    );
        var reportCtrl_dcdtl    = agrid.getScope("reportCtrl_dcdtl" );
        var reportCtrl_gift     = agrid.getScope("reportCtrl_gift"  );
        var reportCtrl_order    = agrid.getScope("reportCtrl_order" );
        var reportCtrl_lv1      = agrid.getScope("reportCtrl_lv1"   );
        var reportCtrl_lv2      = agrid.getScope("reportCtrl_lv2"   );
        var reportCtrl_lv3      = agrid.getScope("reportCtrl_lv3"   );
        var reportCtrl_prod     = agrid.getScope("reportCtrl_prod"  );
        var reportCtrl_compt    = agrid.getScope("reportCtrl_compt" );
        var reportCtrl_appr     = agrid.getScope("reportCtrl_appr"  );
        var reportCtrl_membr    = agrid.getScope("reportCtrl_membr" );
        var reportCtrl_work     = agrid.getScope("reportCtrl_work"  );

    /* 2020.04: [영업일보 구성]에서의 정렬 순서로 출력
	    //[영업일보 구성]에서 선택된 부분만 출력
        var configCtrl_2 = agrid.getScope('configCtrl_2');	//영업일보 구성
        for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){
            var item = configCtrl_2.flex.collectionView.items[i];
            if(item.cfgCd == 'SL')	continue;
        	if(item.cfgSelYn == "Y"){
        		doc.append("<br><br><br>");
        		eval( 'doc.append("<h3>" + messages["dailyReport.' + item.cfgCd.toLowerCase() + '"] + "</h3><br>");');
        		eval( 'doc.append( this._renderTable(reportCtrl_'  + item.cfgCd.toLowerCase() + '.flex, "'+ item.cfgCd.toLowerCase() + '") );'	);	//추가한 parameter 용도: [결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
        	}
        }
    */


        //[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
        for(var i=0; i<arrDispSeq.length; i++){
        	//console.log('Print: ' + arrDispSeq[i][0] + ', ' + arrDispSeq[i][1] + ', ' + arrDispSeq[i][2]);
        	if(arrDispSeq[i][2] == "Y"){
        		doc.append("<br><br><br>");
        		eval( 'doc.append("<h3>" + messages["dailyReport.' + arrDispSeq[i][0].toLowerCase() + '"] + "</h3><br>");');
        		eval( 'doc.append( this._renderTable(reportCtrl_'  + arrDispSeq[i][0].toLowerCase() + '.flex, "'+ arrDispSeq[i][0].toLowerCase() + '") );'	);	//추가한 parameter 용도: [결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
        	}
        }
        
        /*
        //[영업일보 구성] 정렬 순서
        arrDispSeq.sort(function (name, seq){
            var idx = 1;    //0:구성내역명칭, 1:정렬순서

            switch(idx){
                case 0: //문자 - 문자열 정렬 (문자열 정렬이 기본이기에 다른작업 안해도 됨)
                        name[idx] = name[idx].toString().toLowerCase();
                        seq [idx] = seq [idx].toString().toLowerCase();
                        break;

                case 1: //정렬순서 - 정수변환
                        name[idx] = parseInt(name[idx]);
                        seq [idx] = parseInt(seq [idx]);
                        break;
            }
            return (name[idx] < seq[idx])? -1 : ((name[idx] > seq[idx])? 1 : 0);
        });

        for(var i=0; i<arrDispSeq.length; i++){
        	console.log('Before: ' + arrDispSeq[i][0] + ', ' + arrDispSeq[i][1]);
        	
            var item =  configCtrl_2.flex.collectionView.items[i];
            if(item.cfgCd == 'SL')	continue;
        	if(item.cfgSelYn == "Y"){
        		doc.append("<br><br><br>");
        		eval( 'doc.append("<h3>" + messages["dailyReport.' + item.cfgCd.toLowerCase() + '"] + "</h3><br>");');
        		eval( 'doc.append( this._renderTable(reportCtrl_'  + item.cfgCd.toLowerCase() + '.flex, "'+ item.cfgCd.toLowerCase() + '") );'	);	//추가한 parameter 용도: [결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
        	}        	
        	
        }	
        */
        
        doc.print();
	};

    //Grid를 Table로 rendering 처리
	$scope._renderTable = function(flex, name){	//function(flex: wjcGrid.FlexGrid){
		//Table - START
			/*
 	        let tbl = '<table>';
	        let tbl = '<table class="tblType01">';
	        let tbl = '<table class="searchTbl">';
			let tbl = '<table class="sam-tbl">';
			let tbl = '<table class="reportPrint">';
			*/
			let tbl = '<table class="reportPrint">';

        //Headers
	      //if(flex.headersVisibility & wjcGrid.HeadersVisibility.Column){
	        if(flex.headersVisibility & wijmo.grid.HeadersVisibility.Column){
	            tbl += '<thead>';
	            for(let r=0; r<flex.columnHeaders.rows.length; r++){
////////////////////tbl += this._renderRow       (flex.columnHeaders, r);
                    tbl += this._renderRow_Header(flex.columnHeaders, r, name);
                    //console.log('Headers > tbl: ' + this._renderRow_Header(flex.columnHeaders, r));
	            }
	            tbl += '</thead>';
	        }

        //Body
	        tbl += '<tbody>';
	        for(let r=0; r<flex.rows.length; r++){
                tbl += this._renderRow(flex.cells, r, name);
                //console.log('Body    > tbl: ' + this._renderRow(flex.cells, r));
	        }
	        tbl += '</tbody>';

        //Table - End
            tbl += '</table>';
            //console.log('tbl: ' + tbl);
	        return tbl;
    }

	$scope._renderRow_Header = function(panel, r, name){
        let tr 		= '',
            row 	= panel.rows[r];
          //row_next= panel.rows[r+1];

        let header_rowspan 	= 1;
        let header_colspan 	= 1;
        let header_colsize 	= 0;	//Header size는 의미없다 !!! (해당 데이터의 길이에 따라 가변적이기에)

        var tmpFlex 		= agrid.getScope("reportCtrl_pay").flex;	//용도 : [결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)

        if(row.renderSize > 0){
            tr += '<tr>';

            for(let c=0; c<panel.columns.length; c++){
            	//[결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
	            	if(name=="pay"   &&   r==1   &&   c >= 3){	//c >= 3 --> 0:실매출, 1:봉사료, 2:에누리
	            		/*
	            		console.log('----------------------------0');
	            		console.log('r: ' + r + ' & c:' + c);
	            		console.log(tmpFlex.getCellData(0,c) );
	            		if( tmpFlex.getCellData(0,c) == 0 )	continue;
	            		console.log('----------------------------1');
	            		*/
	            		if( tmpFlex.getCellData(0,c) == 0 )	continue;
	            	}

                let col 			= panel.columns[c];
                let col_next		= panel.columns[c+1];

                if(col.renderSize > 0){
                    //Cell style & content 구하기
	                  //let style 				= 'width:' + col.renderSize + 'px;' + 'text-align:' + col.getAlignment() + ';' + 'padding-right: 6px';
	                    let style 				= 'width:' + col.renderSize + 'px;' + 'text-align:center;'                     + 'padding-right: 6px';

	                    let content 			= panel.getCellData(r, 	c,	true);
	                    let content_prev_row	= "";																		//Previous 	row    값
	                    let content_next_row	= "";																		//Next 		row    값
	                    let content_next_col 	= "";																		//Next 		column 값

	                    if(0                    <  r   )	content_prev_row 	= panel.getCellData(r-1,	c,  	true);	//Previous 	row    값
	                    if(panel.rows   .length > (r+1))	content_next_row 	= panel.getCellData(r+1,	c,  	true);	//Next 		row    값
	                    if(panel.columns.length > (c+1))	content_next_col 	= panel.getCellData(r,  	c+1,	true);	//Next 		column 값

	                    if(header_colsize == 0)				header_colsize 		= col.renderSize;

	                    if(!row.isContentHtml && !col.isContentHtml){
	                        content = wijmo.escapeHtml(content);
	                    }

                    //Cell을  row에 추가
                    	/*
                    	console.log('----------------------------------------------------------');
                    	console.log('content_prev_row     : ' + content_prev_row				);
                    	console.log('content              : ' + content							);
                    	console.log('content_next_row     : ' + content_next_row				);
                    	console.log('content_next_col     : ' + content_next_col				);
                    	console.log('panel.columns.length : ' + panel.columns.length + ' & ' + c);
						*/

                    	/*
                		if(panel.columns.length == (c+1)   &&   content == content_prev_row){
                    		header_colspan++;
                    		header_colsize += col_next.renderSize;
                		}
                		*/
                    	//[수발주내역] -> 특이한 CASE이기에 hard-coding으로 해결 - START
                    	if(content_prev_row == ''   &&   content == '본사출고'){
                    		if(content_next_row == '물량오류'){
                    			tr += '<th rowspan="' + 1 + '" colspan="' + 3 + '" style="text-align:center;width:' + header_colsize + 'px">' + content + '</th>';
                    		}
                    		continue;
                    	}
                    	if(content_prev_row == '본사출고'   &&   content == '본사출고'){
                   				tr += '<th rowspan="' + 1 + '" colspan="' + 1 + '" style="text-align:center;width:' + header_colsize + 'px">' + content + '</th>';
                   			continue;
                    	}
                    	//[수발주내역] -> 특이한 CASE이기에 hard-coding으로 해결 - END

                    	if(content == content_prev_row)	continue;	//이전행 Header값과 같으면 skip

                    	if(content == content_next_row){
                    		header_rowspan++;
                    	}

                    	if(content == content_next_col){
                    		header_colspan++;
                    		header_colsize += col_next.renderSize;
                    		//console.log("content == content_next_col: " + content + " == " + content_next_col + " & " + header_colspan + " & " + header_colsize);
                    	}
                    	else{

                    		//console.log('header_rowspan       : ' + header_rowspan + ' & header_colspan: ' + header_colspan);

                    		if		(header_rowspan >  1    &&    header_colspan == 1){
                    			tr += '<th rowspan="' + header_rowspan + '" colspan="' + header_colspan + '" style="text-align:center;width:' + header_colsize + 'px">' + content + '</th>';
                    			header_rowspan = 1;

                    		}else if(header_rowspan == 1    &&    header_colspan >  1){
                    			tr += '<th rowspan="' + header_rowspan + '" colspan="' + header_colspan + '" style="text-align:center;width:' + header_colsize + 'px">' + content + '</th>';
                    			header_colspan = 1;
                    			header_colsize = 0;

                    		}else if(header_rowspan >  1    &&    header_colspan >  1){
                    			tr += '<th rowspan="' + header_rowspan + '" colspan="' + header_colspan + '" style="text-align:center;width:' + header_colsize + 'px">' + content + '</th>';
                    			header_rowspan = 1;
                    			header_colspan = 1;
                    			header_colsize = 0;

                    		}else{
                    			tr += '<th style="' + style + '">' + content + '</th>';
                    		}
                    	}
                    	//header_prev = panel.getCellData(r, c, true);
                }	//if(col.renderSize > 0){
            }		//for(let c=0; c<panel.columns.length; c++){
            tr += '</tr>';
        }
        return tr;
    }



	$scope._renderRow = function(panel, r, name){	//function(panel: wjcGrid.GridPanel, r: number){
        let tr 	= '',
        row 	= panel.rows[r];

        if(row.renderSize > 0){
            tr += '<tr>';

            for(let c=0; c<panel.columns.length; c++){
                let col = panel.columns[c];

                if(col.renderSize > 0){
                    //Cell style & content 구하기
	                    let style 	= 'width:' + col.renderSize + 'px;' + 'text-align:' + col.getAlignment() + ';' + 'padding-right: 6px';
	                    let content = panel.getCellData(r, c, true);

	                	//[결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
	                    	if(name=="pay"   &&   c >= 3   &&   content == 0)	continue;	//c >= 3 --> 0:실매출, 1:봉사료, 2:에누리
		                    //console.log('----------------------------------------------------------');
		                    //console.log('content              : ' + content			);

	                    if(!row.isContentHtml && !col.isContentHtml){
	                      //content = wjcCore.escapeHtml(content);
	                        content = wijmo.escapeHtml(content);
	                    }

                    //Cell을  row에 추가
                        //'check-box'있으면 true/false 구분해서 색깔 다르게
                        let raw = panel.getCellData(r, c, false);
                        if		(raw === true)	content = '&#9745;';
                        else if	(raw === false)	content = '&#9744;';

                        tr += '<td style="' + style + '">' + content + '</td>';
                }
            }
            tr += '</tr>';
        }
        return tr;
    }



	$scope._renderRow_OLD = function(panel, r){	//function(panel: wjcGrid.GridPanel, r: number){
        let tr 	= '',
        row 	= panel.rows[r];

        if(row.renderSize > 0){
            tr += '<tr>';

            for(let c=0; c<panel.columns.length; c++){
                let col = panel.columns[c];

                if(col.renderSize > 0){
                    //Cell style & content 구하기
	                    let style 	= 'width:' + col.renderSize + 'px;' + 'text-align:' + col.getAlignment() + ';' + 'padding-right: 6px';
	                    let content = panel.getCellData(r, c, true);

	                    if(!row.isContentHtml && !col.isContentHtml){
	                      //content = wjcCore.escapeHtml(content);
	                        content = wijmo.escapeHtml(content);
	                    }

                    //Cell을  row에 추가
	                  //if(panel.cellType == wjcGrid.CellType.ColumnHeader){
	                    if(panel.cellType == wijmo.grid.CellType.ColumnHeader){
	                        tr += '<th style="' + style + '">' + content + '</th>';

	                    }else {
	                        //'check-box'있으면 true/false 구분해서 색깔 다르게
	                        let raw = panel.getCellData(r, c, false);
	                        if		(raw === true)	content = '&#9745;';
	                        else if	(raw === false)	content = '&#9744;';

	                        tr += '<td style="' + style + '">' + content + '</td>';
	                    }
                }
            }
            tr += '</tr>';
        }
        return tr;
    }


/*
	//결제수단용 (금액이 '0'인 결제수단은 인쇄시 제외)	START	--------------------------------------------------------
    //Grid를 Table로 rendering 처리
	$scope._renderTable_PAY = function(flex){	//function(flex: wjcGrid.FlexGrid){
		//Table - START
			let tbl = '<table class="reportPrint">';

        //Headers
	        if(flex.headersVisibility & wijmo.grid.HeadersVisibility.Column){
	            tbl += '<thead>';
	            for(let r=0; r<flex.columnHeaders.rows.length; r++){
                    tbl += this._renderRow_Header_PAY(flex.columnHeaders, r);
                    //console.log('Headers > tbl: ' + this._renderRow_Header(flex.columnHeaders, r));
	            }
	            tbl += '</thead>';
	        }

        //Body
	        tbl += '<tbody>';
	        for(let r=0; r<flex.rows.length; r++){
                tbl += this._renderRow_PAY(flex.cells, r);
                //console.log('Body    > tbl: ' + this._renderRow(flex.cells, r));
	        }
	        tbl += '</tbody>';

        //Table - End
            tbl += '</table>';
            //console.log('tbl: ' + tbl);
	        return tbl;
    }

	$scope._renderRow_Header_PAY = function(panel, r){
		var tmpFlex = agrid.getScope("reportCtrl_pay").flex;
        let tr 		= '',
            row 	= panel.rows[r];

        let header_rowspan 	= 1;
        let header_colspan 	= 1;
        let header_colsize 	= 0;	//Header size는 의미없다 !!! (해당 데이터의 길이에 따라 가변적이기에)

        if(row.renderSize > 0){
            tr += '<tr>';

            for(let c=0; c<panel.columns.length; c++){

            	//[결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
	            	if(r==1   &&   c >= 3){	//c >= 3 --> 0:실매출, 1:봉사료, 2:에누리
	            		if( tmpFlex.getCellData(0,c) == 0 )	continue;
	            	}

                let col 			= panel.columns[c];
                let col_next		= panel.columns[c+1];

                if(col.renderSize > 0){
                    //Cell style & content 구하기
	                    let style 				= 'width:' + col.renderSize + 'px;' + 'text-align:' + col.getAlignment() + ';' + 'padding-right: 6px';

	                    let content 			= panel.getCellData(r, 	c,	true);
	                    let content_prev_row	= "";																		//Previous 	row    값
	                    let content_next_row	= "";																		//Next 		row    값
	                    let content_next_col 	= "";																		//Next 		column 값

	                    if(0                    <  r   )	content_prev_row 	= panel.getCellData(r-1,	c,  	true);	//Previous 	row    값
	                    if(panel.rows   .length > (r+1))	content_next_row 	= panel.getCellData(r+1,	c,  	true);	//Next 		row    값
	                    if(panel.columns.length > (c+1))	content_next_col 	= panel.getCellData(r,  	c+1,	true);	//Next 		column 값

	                    if(header_colsize == 0)				header_colsize 		= col.renderSize;

	                    if(!row.isContentHtml && !col.isContentHtml){
	                        content = wijmo.escapeHtml(content);
	                    }

                    //Cell을  row에 추가
                    	if(content == content_prev_row)	continue;	//이전행 Header값과 같으면 skip

                    	if(content == content_next_row){
                    		header_rowspan++;
                    	}

                    	if(content == content_next_col){
                    		header_colspan++;
                    		header_colsize += col_next.renderSize;
                    		//console.log("content == content_next_col: " + content + " == " + content_next_col + " & " + header_colspan + " & " + header_colsize);
                    	}
                    	else{

                    		//console.log('header_rowspan       : ' + header_rowspan + ' & header_colspan: ' + header_colspan);

                    		if		(header_rowspan >  1    &&    header_colspan == 1){
                    			tr += '<th rowspan="' + header_rowspan + '" colspan="' + header_colspan + '" style="text-align:center;width:' + header_colsize + 'px">' + content + '</th>';
                    			header_rowspan = 1;

                    		}else if(header_rowspan == 1    &&    header_colspan >  1){
                    			tr += '<th rowspan="' + header_rowspan + '" colspan="' + header_colspan + '" style="text-align:center;width:' + header_colsize + 'px">' + content + '</th>';
                    			header_colspan = 1;
                    			header_colsize = 0;

                    		}else if(header_rowspan >  1    &&    header_colspan >  1){
                    			tr += '<th rowspan="' + header_rowspan + '" colspan="' + header_colspan + '" style="text-align:center;width:' + header_colsize + 'px">' + content + '</th>';
                    			header_rowspan = 1;
                    			header_colspan = 1;
                    			header_colsize = 0;

                    		}else{
                    			tr += '<th style="' + style + '">' + content + '</th>';
                    		}
                    	}
                    	//header_prev = panel.getCellData(r, c, true);
                }	//if(col.renderSize > 0){
            }		//for(let c=0; c<panel.columns.length; c++){
            tr += '</tr>';
        }
        return tr;
    }



	$scope._renderRow_PAY = function(panel, r){	//function(panel: wjcGrid.GridPanel, r: number){
        let tr 	= '',
        row 	= panel.rows[r];

        if(row.renderSize > 0){
            tr += '<tr>';

            for(let c=0; c<panel.columns.length; c++){
                let col = panel.columns[c];

                if(col.renderSize > 0){
                    //Cell style & content 구하기
	                    let style 	= 'width:' + col.renderSize + 'px;' + 'text-align:' + col.getAlignment() + ';' + 'padding-right: 6px';
	                    let content = panel.getCellData(r, c, true);

	                	//[결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
	                    	if(c >= 3   &&   content == 0)	continue;	//c >= 3 --> 0:실매출, 1:봉사료, 2:에누리
		                    //console.log('----------------------------------------------------------');
		                    //console.log('content              : ' + content			);

	                    if(!row.isContentHtml && !col.isContentHtml){
	                      //content = wjcCore.escapeHtml(content);
	                        content = wijmo.escapeHtml(content);
	                    }

                    //Cell을  row에 추가
                        //'check-box'있으면 true/false 구분해서 색깔 다르게
                        let raw = panel.getCellData(r, c, false);
                        if		(raw === true)	content = '&#9745;';
                        else if	(raw === false)	content = '&#9744;';

                        tr += '<td style="' + style + '">' + content + '</td>';
                }
            }
            tr += '</tr>';
        }
        return tr;
    }
	//결제수단용 (금액이 '0'인 결제수단은 인쇄시 제외)	END		--------------------------------------------------------
	*/
	//print				END		----------------------------------------------------------------------------------------------------------------------



}]);
//reportCtrl_excel	END		############################################################################################################################################################################



//reportCtrl_store	START	############################################################################################################################################################################
app.controller('reportCtrl_store', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    //매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------
		//함수명         : 모듈에 넘기는 파라미터의 targetId + 'Show'
		//_broadcast: 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.reportSelectStoreShow = function () {
		$scope._broadcast('reportSelectStoreCtrl');
	};
	//매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------

}]);
//reportCtrl_store	END		############################################################################################################################################################################
