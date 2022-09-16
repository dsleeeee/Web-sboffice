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
            	params.searchStoreCd   	= $("#dailyTableSelectStoreCd").val();

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
	        params.searchStoreCd   	= $("#dailyTableSelectStoreCd").val();

	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//'데이터 처리 중입니다.' 시작	//cmm.progress=데이터 처리 중입니다.

	    $scope._postJSONQuery.withOutPopUp	(	"/excclc/excclc/dailyTable/dailyTable/list.sb",	//영업일보 구성 조회
					        					params,
					        					function(response)	{
																		//데이터 setting > 영업일보
																    	var dailyTableCtrl_sl       = agrid.getScope("dailyTableCtrl_sl");      dailyTableCtrl_sl  	.flex.itemsSource = response.data.data.sl;      //매출종합
																    	var dailyTableCtrl_prodClass      = agrid.getScope("dailyTableCtrl_prodClass");     dailyTableCtrl_prodClass  .flex.itemsSource = response.data.data.prodClass;     //결제수단
																    	var dailyTableCtrl_pay      = agrid.getScope("dailyTableCtrl_pay");     dailyTableCtrl_pay  .flex.itemsSource = response.data.data.pay;     //비매출종합
																    	var dailyTableCtrl_rtn     = agrid.getScope("dailyTableCtrl_rtn");    dailyTableCtrl_rtn .flex.itemsSource = response.data.data.rtn;    //비매출결제수단

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

	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
	    s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

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
//dailyTableCtrl_sl    	END		############################################################################################################################################################################

//dailyTableCtrl_prodClass    START   ############################################################################################################################################################################
app.controller('dailyTableCtrl_prodClass', ['$scope', '$http', function ($scope, $http) {
    angular.extend(this, new RootController('dailyTableCtrl_prodClass', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

    $scope.initGrid = function (s, e) {
        $scope._makePickColumns("dailyTableCtrl_prodClass");   	        //picker 사용시 호출

        //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
        //s.bottomLeftCells.setCellData(0, 0, '합계');			//add a sigma to the header to show that this is a summary row

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
//dailyTableCtrl_pay    END		############################################################################################################################################################################

//dailyTableCtrl_rtn 	START   ############################################################################################################################################################################
app.controller('dailyTableCtrl_rtn', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('dailyTableCtrl_rtn', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

  $scope.initGrid = function (s, e) {
      $scope._makePickColumns("dailyTableCtrl_rtn");   	        //picker 사용시 호출

      //s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
      //s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

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

								var	sl			= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_sl   .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.sl"		]}	);  //매출종합
								var prodClass	= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_prodClass  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.prodClass"		]}	);  //결제수단
								var pay 		= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_pay  .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.pay"		]}	);  //비매출종합
							    var rtn    		= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_rtn .flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.rtn" 	]}	);  //비매출결제수단

							    var firstSheetYN = "N";
							    var firstSheet;

							    arrDispSeq[0] = new Array(item.cfgCd, item.cfgDispSeq, item.cfgSelYn);
							    
					            for(var i=0; i<arrDispSeq.length; i++){
					            	if(arrDispSeq[i][2] == "Y"   &&   firstSheetYN == "Y"){	
					            		eval( 'firstSheet.sheets.push('+ arrDispSeq[i][0].toLowerCase() + '.sheets[0]);' );
					            	}
					            	if(arrDispSeq[i][2] == "Y"   &&   firstSheetYN == "N"){	
					            		firstSheetYN 	= "Y";
					            		firstSheet 		= eval('wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableCtrl_' + arrDispSeq[i][0].toLowerCase() + '.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.' + arrDispSeq[i][0].toLowerCase() + '"]});');
					            	}					            	
					            }
					            firstSheet.saveAsync(excelFileName);

								dailyTableCtrl.$broadcast('loadingPopupInactive');
							}, 1000);	//건수가 많아서 1000으로 했음 (현재 1년치 정도가 500ms 미만임)
	};
	//excelDownload		END		----------------------------------------------------------------------------------------------------------------------



	//print				START	----------------------------------------------------------------------------------------------------------------------
	$scope.print_OLD_XXX = function(){
		var dailyTableCtrl_sl = agrid.getScope("dailyTableCtrl_sl");      //매출종합
		if (dailyTableCtrl_sl.flex.rows.length <= 0) {
				s_alert.pop( messages["dailyReport.alert.noDataToPrint"] );	//출력할 자료가 없습니다.
				return false;
		}

		var dateFrom	= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
	  	var printTitle	= '일일일계표(' + dateFrom + ')' + getCurDateTime();

	    var doc 	= new wijmo.PrintDocument( {title : printTitle} );

	    var browser = navigator.userAgent.toLowerCase();	//브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
	    if (-1 != browser.indexOf('chrome')) {
	    }

	    doc.append( document.querySelector('#div_print') );

	    doc.print();
	};
	//print				END		----------------------------------------------------------------------------------------------------------------------


	//print				STATT	----------------------------------------------------------------------------------------------------------------------
	$scope.print = function(){
		var dailyTableCtrl_sl = agrid.getScope("dailyTableCtrl_sl");      //매출종합
		if (dailyTableCtrl_sl.flex.rows.length <= 0) {
				s_alert.pop( messages["dailyReport.alert.noDataToPrint"] );	//출력할 자료가 없습니다.
				return false;
		}

		var dateFrom	= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
		var printTitle	= '일일일계표(' + dateFrom + ')' + getCurDateTime();	//파일명의 '~'  -->  '_'로 자동 치환됨.

        let doc = new wijmo.PrintDocument	({
                                              //title	: printTitle,   --> 아래의 제목스타일 '영업일보 기간'으로 대체
                                                title	: '',
								            	copyCss	: true //false가 낫다 -> To prevent cross-origin issues
        									});


		doc.append('<link rel="stylesheet" type="text/css" href="/resource/solbipos/css/cmm/style.css"	/>');

        //영업일보 기간
            doc.append('<h3 align="center">' + printTitle + '</h3><br><br><br>');

		//결제라인	START	----------------------------------------------------------------------------

		var params = {};
		params.searchStoreCd   	= $("#reportSelectStoreCd").val();

		$scope._postJSONQuery.withOutPopUp	(	"/sale/anals/dailyReportNew/config/list.sb",	//영업일보 구성 조회
			params,
			function(response)	{
			console.log(response);
		var flex_payline        = response.data.data.payline; //결제라인 Grid
		var arr_cfgPayLineSeq   = new Array();
		var arr_cfgPayLineNm    = new Array();
		var tmp_table           = "";

		for(var i=0; i<flex_payline.length; i++){
			// var item = flex_payline.collectionView.items[i];
			arr_cfgPayLineSeq.push(flex_payline.cfgPayLineSeq);
			arr_cfgPayLineNm .push(flex_payline.cfgPayLineNm );
		}

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
			}, false);
		event.preventDefault();
		//결제라인	END		----------------------------------------------------------------------------

        var dailyTableCtrl_prodClass      = agrid.getScope("dailyTableCtrl_prodClass"   );
        var dailyTableCtrl_pay      = agrid.getScope("dailyTableCtrl_pay"   );
        var dailyTableCtrl_rtn     = agrid.getScope("dailyTableCtrl_rtn"  );

        //[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
        for(var i=0; i<arrDispSeq.length; i++){
        	//console.log('Print: ' + arrDispSeq[i][0] + ', ' + arrDispSeq[i][1] + ', ' + arrDispSeq[i][2]);
        	if(arrDispSeq[i][2] == "Y"){
        		doc.append("<br><br><br>");
        		eval( 'doc.append("<h3>" + messages["dailyReport.' + arrDispSeq[i][0].toLowerCase() + '"] + "</h3><br>");');
        		eval( 'doc.append( this._renderTable(dailyTableCtrl_'  + arrDispSeq[i][0].toLowerCase() + '.flex, "'+ arrDispSeq[i][0].toLowerCase() + '") );'	);	//추가한 parameter 용도: [결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
        	}
        }

        doc.print();
	};

    //Grid를 Table로 rendering 처리
	$scope._renderTable = function(flex, name){	//function(flex: wjcGrid.FlexGrid){
		//Table - START
			let tbl = '<table class="dailyTablePrint">';

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

        var tmpFlex 		= agrid.getScope("dailyTableCtrl_prodClass").flex;	//용도 : [결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)

        if(row.renderSize > 0){
            tr += '<tr>';

            for(let c=0; c<panel.columns.length; c++){
            	//[결제수단]의 값이 '0'이면 인쇄에서 제외(신용카드 ~ 스마트오더)
	            	if(name=="prodClass"   &&   r==1   &&   c >= 3){	//c >= 3 --> 0:실매출, 1:봉사료, 2:에누리
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
	                    	if(name=="prodClass"   &&   c >= 3   &&   content == 0)	continue;	//c >= 3 --> 0:실매출, 1:봉사료, 2:에누리
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
