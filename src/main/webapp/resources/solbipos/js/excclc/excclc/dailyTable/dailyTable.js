/****************************************************************
 *
 * 파일명 : dailyTable.js
 * 설  명 : 일일일계표 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.15     권지현      1.0
 *
 * **************************************************************/

var app 		= agrid.getApp();	//get application

var startDate 	= wcombo.genDateVal("#startDate", getToday()	);

var arrDispSeq	 = new Array();		//[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
arrDispSeq[0] = new Array('sl',1,'Y');
arrDispSeq[1] = new Array('prodClass',2,'Y');
arrDispSeq[2] = new Array('pay',3,'Y');
arrDispSeq[3] = new Array('rtn',4,'Y');

arrDispSeq[4] = new Array('courseStatus',5,'Y');
arrDispSeq[5] = new Array('courseType',6,'Y');
arrDispSeq[6] = new Array('tuition1',7,'Y');
arrDispSeq[7] = new Array('tuition2',8,'Y');
arrDispSeq[8] = new Array('groupCourse',9,'Y');

arrDispSeq[9] = new Array('paymentStatus1',10,'Y');
arrDispSeq[10] = new Array('paymentStatus2',11,'Y');
arrDispSeq[11] = new Array('paymentStatus3',12,'Y');


var flex_payline = {};
/* 전역으로 선언해도 인식못함.
//매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------
//함수명         : 모듈에 넘기는 파라미터의 targetId + 'Show'
//_broadcast: 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
dailyTableSelectStoreShow = function () {
	$scope._broadcast('dailyTableSelectStoreCtrl');
};
//매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------
*/

//dailyTableCtrl		START	############################################################################################################################################################################
app.controller('dailyTableCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	//상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('dailyTableCtrl', $scope, $http, true));

	$scope.orgnFg = gvOrgnFg;

	$scope.init            = function () {
		$scope.configCtrlList();
        event.preventDefault();	//기능수행 종료 (반드시 추가해야 함)
	};

    //[조회] - START			--------------------------------------------------------------------------------------------------------------------------
    $scope.$on("dailyTableCtrl", function(event, data) {	//판매추이분석 Grid 조회

    	$scope.dailyTableCtrlList();

        event.preventDefault();	//기능수행 종료 (반드시 추가해야 함)

	});

    $scope.configCtrlList = function(data){

            ///*
    	    var params = {};
            	params.storeCd   	= $("#dailyTableSelectStoreCd").val();

            //$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.
    	    $scope._postJSONQuery.withOutPopUp	(	"/excclc/excclc/dailyTable/dailyTable/list.sb",	//영업일보 구성 조회
    					        					params,
    					        					function(response)	{
    															            //'영업일보(0000-00-00 ~ 0000-00-00)' 문구 setting
    															            var dailyTableCtrl_excel = agrid.getScope('dailyTableCtrl_excel');
    															            dailyTableCtrl_excel.span_startDate	= wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd');

    															            //2020.02.24 - '본사'인 경우에도 [결제라인] [영업일보] button 보이기
    															            if($scope.orgnFg == "H") {	//H:본사, S:가맹점, M:?? -> 매장이 'M'으로 보이는 경우도 있음
    																    	    $("#btnAdd"		).show();
    																    	    $("#btnDel"		).show();
    																    	    $("#btnSave"	).show();

    																    		$("#btnSave2"	).show();
    																    		$("#btnUp"	 	).show();
    																    		$("#btnDown"	).show();
    															    		}

    															            sortByDataItem("dailyTableView");	//화면의 Grid 정렬 변경

    															            //$scope.$broadcast('loadingPopupInactive');
    																	},	//callBack function
    												false);
    };	//$scope.dailyTableCtrlList = function(data){
    //[조회] - END			--------------------------------------------------------------------------------------------------------------------------


    $scope.dailyTableCtrlList = function(data){
    	console.log("scope.orgnFg : " + $scope.orgnFg					);
    	console.log("매장                 : " + $("#dailyTableSelectStoreCd").val()	);

    	//'본부'인 경우, 매장 선택여부 확인
      //if($scope.orgnFg == "H") {	--> 'M'으로 조회되는 경우도 있다. 고로 매장이 아닌경우로 if조건 변경함.
    	if($scope.orgnFg != "S") {
    		if( $("#dailyTableSelectStoreCd").val() == "" ){
    			s_alert.pop( messages["dailyReport.alert.selectStore"] );	//선택된 매장이 없습니다. 매장을 선택해 주십시오.
    			return;
    		}
    	}

        var params = {};
        	params.startDate 		= wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
	        params.storeCd   	= $("#dailyTableSelectStoreCd").val();

	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//'데이터 처리 중입니다.' 시작	//cmm.progress=데이터 처리 중입니다.

	    $scope._postJSONQuery.withOutPopUp	(	"/excclc/excclc/dailyTable/dailyTable/list.sb",	//영업일보 구성 조회
					        					params,
					        					function(response)	{
																		//데이터 setting > 영업일보
																    	var dailyTableCtrl_sl       	= agrid.getScope("dailyTableCtrl_sl");      		dailyTableCtrl_sl.flex.itemsSource				= response.data.data.sl;      //매출종합
																    	var dailyTableCtrl_prodClass	= agrid.getScope("dailyTableCtrl_prodClass");		dailyTableCtrl_prodClass.flex.itemsSource		= response.data.data.prodClass;     //결제수단
																    	var dailyTableCtrl_pay      	= agrid.getScope("dailyTableCtrl_pay");     		dailyTableCtrl_pay.flex.itemsSource				= response.data.data.pay;     //비매출종합
																    	var dailyTableCtrl_rtn     		= agrid.getScope("dailyTableCtrl_rtn");    		dailyTableCtrl_rtn.flex.itemsSource				= response.data.data.rtn;    //비매출결제수단

																    	var configCtrl_1				= agrid.getScope("configCtrl_1");    				configCtrl_1.flex.itemsSource					= response.data.data.payline;    //결제라인

																    	var dailyTableCtrl_courseStatus	= agrid.getScope("dailyTableCtrl_courseStatus");	dailyTableCtrl_courseStatus.flex.itemsSource	= response.data.data.courseStatus;	//수강현황
																    	var dailyTableCtrl_courseType	= agrid.getScope("dailyTableCtrl_courseType");		dailyTableCtrl_courseType.flex.itemsSource		= response.data.data.courseType;    //수강유형
																    	var dailyTableCtrl_tuition1		= agrid.getScope("dailyTableCtrl_tuition1");		dailyTableCtrl_tuition1.flex.itemsSource		= response.data.data.tuition1;    	//수강료현황
																    	var dailyTableCtrl_tuition2		= agrid.getScope("dailyTableCtrl_tuition2");		dailyTableCtrl_tuition2.flex.itemsSource		= response.data.data.tuition2;    	//수강료현황
																    	var dailyTableCtrl_groupCourse	= agrid.getScope("dailyTableCtrl_groupCourse");	dailyTableCtrl_groupCourse.flex.itemsSource		= response.data.data.groupCourse;	//단체수강내역

																    	var dailyTableCtrl_paymentStatus1	= agrid.getScope("dailyTableCtrl_paymentStatus1");	dailyTableCtrl_paymentStatus1.flex.itemsSource	= response.data.data.paymentStatus1;	//단체수강내역
																    	var dailyTableCtrl_paymentStatus2	= agrid.getScope("dailyTableCtrl_paymentStatus2");	dailyTableCtrl_paymentStatus2.flex.itemsSource	= response.data.data.paymentStatus2;	//단체수강내역
																    	var dailyTableCtrl_paymentStatus3	= agrid.getScope("dailyTableCtrl_paymentStatus3");	dailyTableCtrl_paymentStatus3.flex.itemsSource	= response.data.data.paymentStatus3;	//단체수강내역

															            //'영업일보(0000-00-00 ~ 0000-00-00)' 문구 setting
															            var dailyTableCtrl_excel = agrid.getScope('dailyTableCtrl_excel');
															            dailyTableCtrl_excel.span_startDate	= wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd');

															            //2020.02.24 - '본사'인 경우에도 [결제라인] [영업일보] button 보이기
															            if($scope.orgnFg == "H") {	//H:본사, S:가맹점, M:?? -> 매장이 'M'으로 보이는 경우도 있음
																    	    $("#btnAdd"		).show();
																    	    $("#btnDel"		).show();
																    	    $("#btnSave"	).show();

																    		$("#btnSave2"	).show();
																    		$("#btnUp"	 	).show();
																    		$("#btnDown"	).show();
															    		}

															            sortByDataItem("dailyTableView");

//----------------------------------------------------------------------------------------------------------------------------------------------------


															            $scope.$broadcast('loadingPopupInactive');	//'데이터 처리 중입니다.' 중지
																	},	//callBack function
												false);
    };	//$scope.dailyTableCtrlList = function(data){
    //[조회] - END			--------------------------------------------------------------------------------------------------------------------------

}]);
//dailyTableCtrl		END		############################################################################################################################################################################

//dailyTableCtrl_sl    START   ############################################################################################################################################################################
app.controller('dailyTableCtrl_sl', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_sl', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {

        $scope._makePickColumns("dailyTableCtrl_sl");   	        //picker 사용시 호출

		//Grid Header 2줄 - START	----------------------------------------------------------------
		s.allowMerging = 2;
		s.columnHeaders.rows.push(new wijmo.grid.Row());

		//첫째줄 Header 생성
		var dataItem = {};
		dataItem.saleAmt      		= messages["dailyTable.dailySale"];
		dataItem.dcAmt				= messages["dailyTable.dailySale"];
		dataItem.netSaleAmt			= messages["dailyTable.dailySale"];
		dataItem.membrSaleAmt		= messages["dailyTable.dailySale"];
		dataItem.monthSaleAmt	   	= messages["dailyTable.monthlySale"];
		dataItem.monthDcAmt	   		= messages["dailyTable.monthlySale"];
		dataItem.monthNetSaleAmt	= messages["dailyTable.monthlySale"];
		dataItem.monthMembrSaleAmt	= messages["dailyTable.monthlySale"];
		dataItem.guestCnt			= messages["dailyTable.dailyGuest"];
		dataItem.guestUprc			= messages["dailyTable.dailyGuest"];
		dataItem.monthGuestCnt		= messages["dailyTable.monthlyGuest"];
		dataItem.monthGuestUprc		= messages["dailyTable.monthlyGuest"];
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

	};
}]);
//dailyTableCtrl_sl    	END		############################################################################################################################################################################

//dailyTableCtrl_prodClass    START   ############################################################################################################################################################################
app.controller('dailyTableCtrl_prodClass', ['$scope', '$http', function ($scope, $http) {
    angular.extend(this, new RootController('dailyTableCtrl_prodClass', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

    $scope.initGrid = function (s, e) {
        $scope._makePickColumns("dailyTableCtrl_prodClass");   	        //picker 사용시 호출

        s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
        s.bottomLeftCells.setCellData(0, 0, '합계');			//add a sigma to the header to show that this is a summary row

        //Grid Header 2줄 - START	----------------------------------------------------------------
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        //첫째줄 Header 생성
        var dataItem = {};
            dataItem.prodClassCd        = messages["dailyTable.prodClass"];
            dataItem.prodClassNm	    = messages["dailyTable.prodClass"];
            dataItem.saleQty		    = messages["dailyTable.daily"];
            dataItem.cashAmt		    = messages["dailyTable.daily"];
            dataItem.cardAmt			= messages["dailyTable.daily"];
            dataItem.etcSaleAmt 		= messages["dailyTable.daily"];
            dataItem.realSaleAmt		= messages["dailyTable.daily"];
            dataItem.monthSaleQty		= messages["dailyTable.monthly"];
            dataItem.monthCashAmt		= messages["dailyTable.monthly"];
            dataItem.monthCardAmt		= messages["dailyTable.monthly"];
            dataItem.monthEtcSaleAmt	= messages["dailyTable.monthly"];
            dataItem.monthRealSaleAmt	= messages["dailyTable.monthly"];
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
        }
    }
}]);
//dailyTableCtrl_prodClass    END		############################################################################################################################################################################

//dailyTableCtrl_pay 	START   ############################################################################################################################################################################
app.controller('dailyTableCtrl_pay', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('dailyTableCtrl_pay', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("dailyTableCtrl_pay");   	        //picker 사용시 호출

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
      }
  }
}]);
//dailyTableCtrl_pay    END		############################################################################################################################################################################

//dailyTableCtrl_rtn 	START   ############################################################################################################################################################################
app.controller('dailyTableCtrl_rtn', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('dailyTableCtrl_rtn', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("dailyTableCtrl_rtn");   	        //picker 사용시 호출

      s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

      //Grid Header 2줄 - START	----------------------------------------------------------------
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());

      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.rtnSaleCnt	= messages["dailyTable.rtnStatus"];
          dataItem.rtnSaleAmt	= messages["dailyTable.rtnStatus"];
          dataItem.totAmt     	= messages["dailyTable.totStatus"];
          dataItem.outAmt     	= messages["dailyTable.totStatus"];
          dataItem.inAmt		= messages["dailyTable.totStatus"];
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
//dailyTableCtrl_rtn   END		############################################################################################################################################################################

//dailyTableCtrl_excel	START	############################################################################################################################################################################
app.controller('dailyTableCtrl_excel', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	//'영업일보(0000-00-00 ~ 0000-00-00)' 문구
	$scope.span_startDate 	= wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd');

	//excelDownload		START	----------------------------------------------------------------------------------------------------------------------
	$scope.excelDownload = function(){
		var dailyTableCtrl_sl = agrid.getScope("dailyTableCtrl_sl");      //매출종합
		if (dailyTableCtrl_sl.flex.rows.length <= 0) {
			  //$scope._popMsg( messages["excelUpload.not.downloadData"] );	//다운로드 할 데이터가 없습니다.
				s_alert.pop( messages["excelUpload.not.downloadData"] );	//다운로드 할 데이터가 없습니다.
				return false;
		}

		var dateFrom		= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
		var excelFileName = '일일일계표(' + dateFrom + ')' + getCurDateTime() +'.xlsx';

	    //$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
		var dailyTableCtrl = agrid.getScope("dailyTableCtrl");
			dailyTableCtrl.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.

		$timeout(function()	{
						      //var dailyTableCtrl_sl       = agrid.getScope("dailyTableCtrl_sl");      //매출종합
						    	var dailyTableCtrl_prodClass      = agrid.getScope("dailyTableCtrl_prodClass");     //결제수단
						    	var dailyTableCtrl_pay      = agrid.getScope("dailyTableCtrl_pay");     //비매출종합
						    	var dailyTableCtrl_rtn     = agrid.getScope("dailyTableCtrl_rtn");    //비매출결제수단

								var	sl			= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_sl.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTable.sl"]});  //매출종합
								var prodClass	= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_prodClass.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTable.prodClass"]});  //분류
								var pay 		= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_pay.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTable.pay"]});  //결제수단
							    var rtn    		= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_rtn.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTable.rtn"]});  //반품출납

							    var firstSheetYN = "N";
							    var firstSheet;

								for(var i=0; i<4; i++){
									if(arrDispSeq[i][2] == "Y"   &&   firstSheetYN == "Y"){
										eval( 'firstSheet.sheets.push('+ arrDispSeq[i][0] + '.sheets[0]);' );
									}
									if(arrDispSeq[i][2] == "Y"   &&   firstSheetYN == "N"){
										firstSheetYN 	= "Y";
										firstSheet 		= eval('wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_' + arrDispSeq[i][0] + '.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTable.' + arrDispSeq[i][0] + '"]});');
									}
								}
					            firstSheet.saveAsync(excelFileName);

								dailyTableCtrl.$broadcast('loadingPopupInactive');
							}, 1000);	//건수가 많아서 1000으로 했음 (현재 1년치 정도가 500ms 미만임)
	};
	//excelDownload		END		----------------------------------------------------------------------------------------------------------------------

	//print				STATT	----------------------------------------------------------------------------------------------------------------------
	$scope.print = function(){
		var dailyTableCtrl_courseStatus = agrid.getScope("dailyTableCtrl_courseStatus");
		if (dailyTableCtrl_courseStatus.flex.rows.length <= 0) {
				s_alert.pop( messages["dailyTable.alert.noDataToPrint"] );	//출력할 자료가 없습니다.
				return false;
		}

		var dateFrom	= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
		var printTitle	= '일일일계표' ; //+ getCurDateTime();	//파일명의 '~'  -->  '_'로 자동 치환됨.

        let doc = new wijmo.PrintDocument	({
                                              //title	: printTitle,   --> 아래의 제목스타일 '영업일보 기간'으로 대체
                                                title	: '',
								            	copyCss	: true //false가 낫다 -> To prevent cross-origin issues
        									});


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
			arr_cfgPayLineSeq.push(item.cfgPayLineSeq);
			arr_cfgPayLineNm .push(item.cfgPayLineNm );
		}

		tmp_table += '<span class="fl"> 일자 : ' + dateFrom.replaceAll(".","-") + '</span>';
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

        var dailyTableCtrl_courseStatus	= agrid.getScope("dailyTableCtrl_courseStatus");
        var dailyTableCtrl_courseType 	= agrid.getScope("dailyTableCtrl_courseType");
        var dailyTableCtrl_tuition1   	= agrid.getScope("dailyTableCtrl_tuition1");
        var dailyTableCtrl_tuition2   	= agrid.getScope("dailyTableCtrl_tuition2");
        var dailyTableCtrl_groupCourse	= agrid.getScope("dailyTableCtrl_groupCourse");

        //[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
        for(var i=4; i<9; i++){
        	//console.log('Print: ' + arrDispSeq[i][0] + ', ' + arrDispSeq[i][1] + ', ' + arrDispSeq[i][2]);
        	if(arrDispSeq[i][2] == "Y"){
        		if(i != 7){
					if(i == 4){
						eval( 'doc.append("<h3 class=\'mt10 mb5\'>" + messages["dailyTable.' + arrDispSeq[i][0] + '"] + "<span class=\'fr\'>" + messages["dailyTable.vatIncld"] +"</span></h3>");');
					} else {
						eval( 'doc.append("<h3 class=\'mt10 mb5\'>" + messages["dailyTable.' + arrDispSeq[i][0] + '"] + "</h3>");');
					}
				}
        		eval( 'doc.append( this._renderTable(dailyTableCtrl_'  + arrDispSeq[i][0] + '.flex, "'+ arrDispSeq[i][0] + '") );'	);	//추가한 parameter 용도: [결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
				if(i == 7){
					eval( 'doc.append("<span class=\'red\'>" + messages["dailyTable.tuitionInfo"] + "</span>")');
				}
        	}
        }

		var date = new Date();
		var year   = date.getFullYear();
		var month  = date.getMonth() + 1;
		var day    = date.getDate();
		var hour   = date.getHours();
		var minute = date.getMinutes();

		if( (""+month).length  == 1 ) month  = "0"+month;
		if( (""+day).length    == 1 ) day    = "0"+day;
		if( (""+hour).length   == 1 ) hour   = "0"+hour;
		if( (""+minute).length == 1 ) minute = "0"+minute;

		eval( 'doc.append("<br><br><span class=\\"fl bk\\"> Date : " + year + "-" + month + "-" + day + "  " + hour + ":" + minute +  "</span>")');

        doc.print();
	};

























	//print2				STATT	----------------------------------------------------------------------------------------------------------------------
	$scope.print2 = function(){
		var dailyTableCtrl_paymentStatus1 = agrid.getScope("dailyTableCtrl_paymentStatus1");
		if (dailyTableCtrl_paymentStatus1.flex.rows.length <= 0) {
				s_alert.pop( messages["dailyTable.alert.noDataToPrint"] );	//출력할 자료가 없습니다.
				return false;
		}

		var dateFrom	= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
		var printTitle	= '일일일계표' ; //+ getCurDateTime();	//파일명의 '~'  -->  '_'로 자동 치환됨.

        let doc = new wijmo.PrintDocument	({
                                              //title	: printTitle,   --> 아래의 제목스타일 '영업일보 기간'으로 대체
                                                title	: '',
								            	copyCss	: true //false가 낫다 -> To prevent cross-origin issues
        									});


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
			arr_cfgPayLineSeq.push(item.cfgPayLineSeq);
			arr_cfgPayLineNm .push(item.cfgPayLineNm );
		}

		tmp_table += '<span class="fl"> 일자 : ' + dateFrom.replaceAll(".","-") + '</span>';
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

        var dailyTableCtrl_paymentStatus1	= agrid.getScope("dailyTableCtrl_paymentStatus1");
        var dailyTableCtrl_paymentStatus2	= agrid.getScope("dailyTableCtrl_paymentStatus2");
        var dailyTableCtrl_paymentStatus3	= agrid.getScope("dailyTableCtrl_paymentStatus3");

        //[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
        for(var i=9; i<arrDispSeq.length; i++){
        	if(arrDispSeq[i][2] == "Y"){
        		if(i < 11){
					doc.append("<br>");
				}
        		if(i < 10){
					eval( 'doc.append("<h3 class=\'mt10 mb5\'>" + messages["dailyTable.' + arrDispSeq[i][0] + '"] + "</h3>");');
				}
        		eval( 'doc.append( this._renderTable(dailyTableCtrl_'  + arrDispSeq[i][0] + '.flex, "'+ arrDispSeq[i][0] + '") );'	);
        	}
        }

		var date = new Date();
		var year   = date.getFullYear();
		var month  = date.getMonth() + 1;
		var day    = date.getDate();
		var hour   = date.getHours();
		var minute = date.getMinutes();

		if( (""+month).length  == 1 ) month  = "0"+month;
		if( (""+day).length    == 1 ) day    = "0"+day;
		if( (""+hour).length   == 1 ) hour   = "0"+hour;
		if( (""+minute).length == 1 ) minute = "0"+minute;

		eval( 'doc.append("<br><br><span class=\\"fl bk\\"> Date : " + year + "-" + month + "-" + day + "  " + hour + ":" + minute +  "</span>")');

        doc.print();
	};

    //Grid를 Table로 rendering 처리
	$scope._renderTable = function(flex, name){
		//Table - START
			let tbl = '<table class="reportPrint" style="width: 100%">';

        //Headers
	        if(flex.headersVisibility & wijmo.grid.HeadersVisibility.Column){
	            tbl += '<thead>';
	            for(let r=0; r<flex.columnHeaders.rows.length; r++){
                    tbl += this._renderRow_Header(flex.columnHeaders, r, name);
	            }
	            tbl += '</thead>';
	        }

        //Body
	        tbl += '<tbody>';
	        for(let r=0; r<flex.rows.length; r++){
                tbl += this._renderRow(flex.cells, r, name);
	        }
	        tbl += '</tbody>';

        //Table - End
            tbl += '</table>';
	        return tbl;
    }

	$scope._renderRow_Header = function(panel, r, name){
        let tr 		= '',
            row 	= panel.rows[r];

        let header_rowspan 	= 1;
        let header_colspan 	= 1;
        let header_colsize 	= 0;	//Header size는 의미없다 !!! (해당 데이터의 길이에 따라 가변적이기에)

        if(row.renderSize > 0){
            tr += '<tr>';

            for(let c=0; c<panel.columns.length; c++){

                let col 			= panel.columns[c];
                let col_next		= panel.columns[c+1];

                if(col.renderSize > 0){
                    //Cell style & content 구하기
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

                    	if(content == content_prev_row)	continue;	//이전행 Header값과 같으면 skip

                    	if(content == content_next_row){
                    		header_rowspan++;
                    	}

                    	if(content == content_next_col){
                    		header_colspan++;
                    		header_colsize += col_next.renderSize;
                    	}
                    	else{

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

	                    if(!row.isContentHtml && !col.isContentHtml){
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

}]);
//dailyTableCtrl_excel	END		############################################################################################################################################################################

//dailyTableCtrl_store	START	############################################################################################################################################################################
app.controller('dailyTableCtrl_store', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    //매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------
		//함수명         : 모듈에 넘기는 파라미터의 targetId + 'Show'
		//_broadcast: 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.dailyTableSelectStoreShow = function () {
		$scope._broadcast('dailyTableSelectStoreCtrl');
	};
	//매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------

}]);
//dailyTableCtrl_store	END		############################################################################################################################################################################

app.controller('configCtrl_1', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('configCtrl_1', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {
		s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding === "nmcodeCd") {
					var item = s.rows[e.row].dataItem;
					if (item.status !== "I") {
						wijmo.addClass(e.cell, 'wijLink');
						wijmo.addClass(e.cell, 'wj-custom-readonly');
					} else {
						wijmo.removeClass(e.cell, 'wj-custom-readonly');
					}
				}
			}
		});
	}
}]);


app.controller('dailyTableCtrl_courseStatus', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_courseStatus', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {

		$scope._makePickColumns("dailyTableCtrl_courseStatus");   	        //picker 사용시 호출

		//Grid Header 2줄 - START	----------------------------------------------------------------
		s.allowMerging = 2;
		s.columnHeaders.rows.push(new wijmo.grid.Row());

		//첫째줄 Header 생성
		var dataItem = {};
		dataItem.fg        	= messages["dailyTable.fg"];
		dataItem.totTuition	= messages["dailyTable.courseStatus"];
		dataItem.studentCnt	= messages["dailyTable.courseStatus"];
		dataItem.dcTuition	= messages["dailyTable.courseStatus"];
		dataItem.netTuition	= messages["dailyTable.courseStatus"];
		dataItem.tuition	= messages["dailyTable.courseStatus"];
		s.columnHeaders.rows[0].dataItem = dataItem;
		//Grid Header 2줄 - END		----------------------------------------------------------------

		s.itemFormatter = function (panel, r, c, cell) {
			if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
				//align in center horizontally and vertically
				panel.allowMerging = 'All';
				panel.rows[r].allowMerging = true;
				panel.columns[c].allowMerging = true;
				wijmo.setCss(cell, {
					display: 'table',
					tableLayout: 'fixed'
				});
				cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
				wijmo.setCss(cell.children[0], {
					display: 'table-cell',
					verticalAlign: 'middle',
					textAlign: 'center'
				});
			}
			// 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
			else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
				// GroupRow 인 경우에는 표시하지 않는다.
				if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
					cell.textContent = '';
				} else {
					if (!isEmpty(panel._rows[r]._data.rnum)) {
						cell.textContent = (panel._rows[r]._data.rnum).toString();
					} else {
						cell.textContent = (r + 1).toString();
					}
				}
			}
			// readOnly 배경색 표시
			else if (panel.cellType === wijmo.grid.CellType.Cell) {
				var col = panel.columns[c];
				if (col.isReadOnly) {
					wijmo.addClass(cell, 'wj-custom-readonly');
				}
			}
		}
	}
}]);

app.controller('dailyTableCtrl_courseType', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_courseType', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {
		s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding === "nmcodeCd") {
					var item = s.rows[e.row].dataItem;
					if (item.status !== "I") {
						wijmo.addClass(e.cell, 'wijLink');
						wijmo.addClass(e.cell, 'wj-custom-readonly');
					} else {
						wijmo.removeClass(e.cell, 'wj-custom-readonly');
					}
				}
			}
		});
	}
}]);

app.controller('dailyTableCtrl_tuition1', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_tuition1', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {
		s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding === "nmcodeCd") {
					var item = s.rows[e.row].dataItem;
					if (item.status !== "I") {
						wijmo.addClass(e.cell, 'wijLink');
						wijmo.addClass(e.cell, 'wj-custom-readonly');
					} else {
						wijmo.removeClass(e.cell, 'wj-custom-readonly');
					}
				}
			}
		});
	}
}]);

app.controller('dailyTableCtrl_tuition2', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_tuition2', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {
		s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding === "nmcodeCd") {
					var item = s.rows[e.row].dataItem;
					if (item.status !== "I") {
						wijmo.addClass(e.cell, 'wijLink');
						wijmo.addClass(e.cell, 'wj-custom-readonly');
					} else {
						wijmo.removeClass(e.cell, 'wj-custom-readonly');
					}
				}
			}
		});
	}
}]);

app.controller('dailyTableCtrl_groupCourse', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_groupCourse', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {
		s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding === "nmcodeCd") {
					var item = s.rows[e.row].dataItem;
					if (item.status !== "I") {
						wijmo.addClass(e.cell, 'wijLink');
						wijmo.addClass(e.cell, 'wj-custom-readonly');
					} else {
						wijmo.removeClass(e.cell, 'wj-custom-readonly');
					}
				}
			}
		});
	}
}]);

app.controller('dailyTableCtrl_paymentStatus1', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_paymentStatus1', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {
		s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding === "nmcodeCd") {
					var item = s.rows[e.row].dataItem;
					if (item.status !== "I") {
						wijmo.addClass(e.cell, 'wijLink');
						wijmo.addClass(e.cell, 'wj-custom-readonly');
					} else {
						wijmo.removeClass(e.cell, 'wj-custom-readonly');
					}
				}
			}
		});
	}
}]);

app.controller('dailyTableCtrl_paymentStatus2', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_paymentStatus2', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {

		$scope._makePickColumns("dailyTableCtrl_paymentStatus2");   	        //picker 사용시 호출

		//Grid Header 2줄 - START	----------------------------------------------------------------
		s.allowMerging = 2;
		s.columnHeaders.rows.push(new wijmo.grid.Row());

		//첫째줄 Header 생성
		var dataItem = {};
		dataItem.inInfo 	= messages["dailyTable.inAmt"];
		dataItem.inAmt		= messages["dailyTable.inAmt"];
		dataItem.outInfo	= messages["dailyTable.outAmt"];
		dataItem.outAmt		= messages["dailyTable.outAmt"];
		dataItem.remainAmt	= messages["dailyTable.remainAmt"];
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
		}
	}
}]);

app.controller('dailyTableCtrl_paymentStatus3', ['$scope', '$http', function ($scope, $http) {
	angular.extend(this, new RootController('dailyTableCtrl_paymentStatus3', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

	$scope.initGrid = function (s, e) {
		s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding === "nmcodeCd") {
					var item = s.rows[e.row].dataItem;
					if (item.status !== "I") {
						wijmo.addClass(e.cell, 'wijLink');
						wijmo.addClass(e.cell, 'wj-custom-readonly');
					} else {
						wijmo.removeClass(e.cell, 'wj-custom-readonly');
					}
				}
			}
		});




	// <-- 그리드 합치기 -->
		s.allowMerging = 'Cells';
		s.itemFormatter = function (panel, r, c, cell) {
			if (panel.cellType === wijmo.grid.CellType.Cell) {

				// 컬럼 병합(그리드 합치기)
				if(panel.columns[c].binding == "remark") {
					panel.columns[c].allowMerging = true;
				}

				// 합쳐진 컬럼 데이터 가운데 정렬
				wijmo.setCss(cell, {
					display    : 'table',
					tableLayout: 'fixed'
				});
				cell.innerHTML = '<div>' + cell.innerHTML + '</div>';
				wijmo.setCss(cell.children[0], {
					display      : 'table-cell',
					verticalAlign: 'middle',
					textAlign    : 'center'
				});

				// readOnly 배경색 표시
				var col = panel.columns[c];
				if (col.isReadOnly) {
					wijmo.addClass(cell, 'wj-custom-readonly');
				}
			}
		}
		// <-- //그리드 합치기 -->



	}
}]);
