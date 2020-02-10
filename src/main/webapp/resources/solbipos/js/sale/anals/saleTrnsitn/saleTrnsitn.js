/****************************************************************
 *
 * 파일명 : saleTrnsitn.js
 * 설  명 : 매출추이분석 JavaScript (매출관리 > 매출분석 > 판매추이분석)
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.14     조현수      1.0
 *
 * **************************************************************/

var app = agrid.getApp();	//get application

app.controller('saleTrnsitnCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
/*
app.controller('saleTrnsitnCtrl', ['$scope', '$http', function ($scope, $http) {
app.controller('saleTrnsitnCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
*/
	//상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('saleTrnsitnCtrl', $scope, $http, true));

    //검색조건에 조회기간
    var startDate 	= wcombo.genDateVal("#startDate", gvStartDate);
  //var endDate 	= wcombo.genDateVal("#endDate",   gvEndDate  );

	//추후 칼럼이 추가나 삭제되는 경우를 대비해 변수값으로 사용
	var global_idxDateHeaderRow		=  1;	//날짜Header row        index
	var global_idxDateHeaderFrom 	=  3;	//날짜Header column 시작 index
	var global_idxDateHeaderTo		= 16;	//날짜Header column 종료 index
	var global_idxDateVariableLast	= 13;	//날짜Header에 setting할 hidden 날짜값 변수명의 마지막index (13일이전 ~ 금일: dateBefore13 ~ dateBefore0) -> 보여주는 일수가 14일에서 변경되는 경우에만 바꾸면 됨

    //Grid 초기화 : 생성되기전 초기화되면서 생성됨		--------------------------------------------------------------------------------------------------------------
    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
        s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

        /*
		grid.columns.push(new wijmo.grid.Column({header: messages["empday.realSaleAmt"], binding: 'realSaleAmt'+i, align: "right" ,  isReadOnly: "true", aggregate: "Sum"}));
		grid.columns.push(new wijmo.grid.Column({header: messages["empday.billCnt"],     binding: 'billCnt'    +i, align: "center" , isReadOnly: "true", aggregate: "Sum"}));
		grid.columnHeaders.setCellData(0, 3+i+k, response.data.data.list[i].nmcodeNm);
		grid.columnHeaders.setCellData(0, 4+i+k, response.data.data.list[i].nmcodeNm);
   		*/
        /*
        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "saleAmt" ||  col.binding === "membrNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 구매액 클릭시 상세정보 조회
                if ( col.binding === "saleAmt") {
                    $scope.setSelectedStore(s.rows[ht.row].dataItem);
                    $scope.dayMembrPurchsViewLayer.show(true);
                    event.preventDefault();
                }

                // 회원명 클릭시 상세정보 조회
                if ( col.binding === "membrNm") {
                    $scope.setSelectedStore(s.rows[ht.row].dataItem);
                    $scope.dayMembrDetailViewLayer.show(true);
                    event.preventDefault();
                }
            }
        });
		*/


        //Grid Header 2줄 - START	----------------------------------------------------------------
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        //첫째줄 Header 생성
        var dataItem         = {};
	        dataItem.prodCd    			= messages["saleTrnsitn.prodCd"];
	        dataItem.prodNm    			= messages["saleTrnsitn.prodNm"];
	        dataItem.splyUprc			= messages["saleTrnsitn.splyUprc"];

	        dataItem.dateBefore0Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore1Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore2Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore3Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore4Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore5Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore6Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore7Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore8Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore9Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore10Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore11Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore12Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];
	        dataItem.dateBefore13Qty 	= messages["saleTrnsitn.currTwoWeekDailySale"];

	        dataItem.twoWeekTot			= messages["saleTrnsitn.twoWeekTot"];
	        dataItem.twoWeekAvr			= messages["saleTrnsitn.twoWeekAvr"];

	        dataItem.inWhCarryOut		= messages["saleTrnsitn.sum"];
			dataItem.outWhCarryIn		= messages["saleTrnsitn.sum"];
			dataItem.hqCurrentStk		= messages["saleTrnsitn.sum"];
			dataItem.sale				= messages["saleTrnsitn.sum"];
			dataItem.storeTotStk		= messages["saleTrnsitn.sum"];

			dataItem.saleRatio			= messages["saleTrnsitn.saleRatio"];
			dataItem.firstSaleDate		= messages["saleTrnsitn.firstSaleDate"];
			dataItem.lastSaleDate		= messages["saleTrnsitn.lastSaleDate"];
			dataItem.saleNumberOfDays	= messages["saleTrnsitn.saleNumberOfDays"];
			dataItem.dayAvrSale			= messages["saleTrnsitn.dayAvrSale"];
			dataItem.exhaustionOrg		= messages["saleTrnsitn.exhaustionOrg"];
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


        //Header에 날짜 setting - START	------------------------------------------------------------
	        var flex		= $scope.flex;
	        var today		= new Date();
	        var prevDate	= new Date();
	        var tempDate;
	        var yyyy;
	        var mm;
	        var dd;
	        var idxTemp = global_idxDateHeaderTo;	//var global_idxDateHeaderTo		= 16;	//날짜Header column 종료 index

	        for(var n=0; n<=global_idxDateVariableLast; n++){
	        	tempDate = today.getTime() - (n * 24 * 60 * 60 * 1000);	//n일 이전
	        	prevDate.setTime(tempDate);

		        yyyy 	= prevDate.getFullYear();
		        mm 		= prevDate.getMonth() + 1; //cf> 0: January
		        dd 		= prevDate.getDate();

		        if(mm < 10)	mm = '0' + mm;
		        if(dd < 10)	dd = '0' + dd;

		        flex.columnHeaders.setCellData(global_idxDateHeaderRow, idxTemp, yyyy + "." + mm + "." + dd);
		        idxTemp--;
	        }
		//Header에 날짜 setting - END		------------------------------------------------------------

    };	//$scope.initGrid	--------------------------------------------------------------------------------------------------------------------------



    //[조회] - START			--------------------------------------------------------------------------------------------------------------------------
    $scope.$on("saleTrnsitnCtrl", function(event, data) {	//판매추이분석 Grid 조회
        var params = {};
	    	//params.srchProdCd   = srchProdCd.value;
	    	//params.srchProdNm   = srchProdNm.value;
	        params.startDate 	= wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
	      //params.endDate 		= wijmo.Globalize.format(endDate.value,   'yyyyMMdd');
	        //console.log("startDate:" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') );

        $scope._inquiryMain("/sale/anals/saletrnsitn/list.sb",
        					params,
        					function()	{
        									var flex = $scope.flex;

        									/* For TEST
        									for (var r=0; r<flex.itemsSource.itemCount; r++){
        										console.log("[" + r + "] : " + flex.rows[r]._data.prodCd);
        									}
        									console.log("Header: " + flex.columnHeaders.getCellData(1,3) );
        									console.log("Header: " + flex.columnHeaders.getCellData(1,4) );
        									*/

        									/* _data 는 배열기능이 없다. 칼럼명을 지정해 주어야 함.
	     									   dataItem ??
	     									for(var i=global_idxDateHeaderFrom; i<=global_idxDateHeaderTo; i++){
	     										flex.columnHeaders.setCellData(global_idxDateHeaderRow,i, flex.rows[0]._data.dateBeforeThirteen);
	     										...
	     									}
	     									*/
        									//console.log("$scope.flex.rows.length: " + $scope.flex.rows.length);
        									if ($scope.flex.rows.length > 0) {	//데이터가 있는 경우에만
	        									var idxTemp = global_idxDateVariableLast;
	        									for(var i=global_idxDateHeaderFrom; i<=global_idxDateHeaderTo; i++){
//        											console.log(global_idxDateVariableLast + ": " + eval('flex.rows[0]._data.dateBefore' + idxTemp) );
	        										flex.columnHeaders.setCellData(global_idxDateHeaderRow, i, eval('flex.rows[0]._data.dateBefore' + idxTemp) );

	        										idxTemp--;
	        									}
        									}
        								},	//callBack function
        					false);

        event.preventDefault();
    });	//$scope.$on("saleTrnsitnCtrl", function(event, data) {
    //[조회] - END			--------------------------------------------------------------------------------------------------------------------------


	//[엑셀 다운로드] - START	------------------------------------------------------------------------------------------------------------------------------
	$scope.excelDownload = function(){
		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
			return false;
		}

		var dateFrom 	= $scope.flex.columnHeaders.getCellData(global_idxDateHeaderRow, global_idxDateHeaderFrom);
		var dateTo		= $scope.flex.columnHeaders.getCellData(global_idxDateHeaderRow, global_idxDateHeaderTo  );
		//console.log("dateFrom: " + dateFrom);
		//console.log("dateTo  : " + dateTo  );

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
		$timeout(function()	{
								wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
																					{
																						includeColumnHeaders: 	true,
																						includeCellStyles	: 	true,
																						includeColumns      :	function (column) {
																													return column.visible;
																												}
																					},
																				  //'saleTrnsitn(매출추이분석).xlsx',
																				  //'매출추이분석(' + dateFrom + ' ~ ' + dateTo + ').xlsx',	//파일명의 '~'  -->  '_'로 자동 치환됨.
																					'매출추이분석(' + dateFrom + ' - ' + dateTo + ').xlsx',
																					function () {
																									$timeout(function () {
																										$scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
																									}, 10);
																					}
																				);
							}, 10);
	};
	//[엑셀 다운로드] - END	------------------------------------------------------------------------------------------------------------------------------



    //상품분류 Pop-up	- START	--------------------------------------------------------------------------------------------------------------------------
    $scope.popUpProdClass = function() {
        $scope.prodClassPopUpLayer.show	(   true,
					                        function(s) {
					                                        // 선택 버튼 눌렀을때만
					                                        if (s.dialogResult === "wj-hide-apply") {
					                                            var scope       = agrid.getScope('prodClassPopUpCtrl');
					                                            var prodClassCd = scope.getSelectedClass();

					                                            var params = {};
					                                                params.prodClassCd = prodClassCd;

					                                            //상품정보 분류 플랫 조회
					                                            $scope._postJSONQuery.withPopUp(    "/popup/getProdClassCdNm.sb",
					                                                                                params,
					                                                                                function(response){
					                                                                                    $scope.prodClassCd      = prodClassCd;
					                                                                                    $scope.prodClassCdNm    = response.data.data;
					                                                                                }
					                                                                            );
					                                        }
					                                    }
        								);
    };

    //상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd		= "";
        $scope.prodClassCdNm 	= "";
    }
    //상품분류 Pop-up	- END	--------------------------------------------------------------------------------------------------------------------------


}]);	//app.controller('saleTrnsitnCtrl', ['$scope', '$http', function ($scope, $http) {