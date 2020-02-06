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

var app = agrid.getApp();	//get application

app.controller('reportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
/*
app.controller('reportCtrl', ['$scope', '$http', function ($scope, $http) {
app.controller('reportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
*/

	//상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('reportCtrl', $scope, $http, true));

	$scope.orgnFg = gvOrgnFg;

    //검색조건에 조회기간
		var startDate 	= wcombo.genDateVal("#startDate", gvStartDate	);
		var endDate 	= wcombo.genDateVal("#endDate",   gvEndDate		);
		/* 안됨
		$scope.startDate	= wcombo.genDateVal("#startDate", 	gvStartDate	);
		$scope.endDate		= wcombo.genDateVal("#endDate", 	gvEndDate	);

		wcombo.genDateVal("#startDate", gvStartDate	);
		wcombo.genDateVal("#endDate",   gvEndDate	);

		console.log("startDate     :" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd')	);	//OK
		console.log("endDate       :" + wijmo.Globalize.format(endDate  .value, 'yyyyMMdd')	);	//OK
		console.log("searchStoreCd :" + $("#reportSelectStoreCd").val()						);	//OK
		console.log("startDate     :" + $("#startDate").val()								);	//X
		console.log("endDate       :" + $("#endDate"  ).val()							 	);	//X
		*/


    //Grid 초기화 : 생성되기전 초기화되면서 생성됨		--------------------------------------------------------------------------------------------------------------
    $scope.initGrid_sl = function (s, e) {
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

    };
    //$scope.initGrid_sl		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_pay		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_pay = function (s, e) {
    	//s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
    	//s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

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
    };
    //$scope.initGrid_pay		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_nsl		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_nsl = function (s, e) {
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
    };
    //$scope.initGrid_nsl		----------------------------------------------------------------------------------------------------------------------




    //$scope.initGrid_npay		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_npay = function (s, e) {
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
    };
    //$scope.initGrid_npay		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_pos		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_pos = function (s, e) {
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
    };
    //$scope.initGrid_pos		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_emp		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_emp = function (s, e) {
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
    };
    //$scope.initGrid_emp		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_dc		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_dc = function (s, e) {
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
    };
    //$scope.initGrid_dc		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_dcdtl		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_dcdtl = function (s, e) {
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
    };
    //$scope.initGrid_dcdtl		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_gift		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_gift = function (s, e) {
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
    };
    //$scope.initGrid_gift		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_order		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_order = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
        s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

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
    };
    //$scope.initGrid_order		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_lv1		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_lv1 = function (s, e) {
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
    };
    //$scope.initGrid_lv1		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_lv2		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_lv2 = function (s, e) {
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
    };
    //$scope.initGrid_lv2		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_lv3		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_lv3 = function (s, e) {
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
    };
    //$scope.initGrid_lv3		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_prod		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_prod = function (s, e) {
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
    };
    //$scope.initGrid_prod		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_compt		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_compt = function (s, e) {
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
    };
    //$scope.initGrid_compt		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_appr		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_appr = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//합계 - add the new GroupRow to the grid's 'columnFooters' panel
        s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

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

            dataItem.apprCntPartne      = messages["dailyReport.apprPartner"];
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
    };
    //$scope.initGrid_appr		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_membr		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_membr = function (s, e) {
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
    };
    //$scope.initGrid_membr		----------------------------------------------------------------------------------------------------------------------



    //$scope.initGrid_work		----------------------------------------------------------------------------------------------------------------------
    $scope.initGrid_work = function (s, e) {
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
    };
    //$scope.initGrid_work		----------------------------------------------------------------------------------------------------------------------




    //[조회] - START			--------------------------------------------------------------------------------------------------------------------------
    $scope.$on("reportCtrl", function(event, data) {	//판매추이분석 Grid 조회
    	//console.log("$scope.orgnFg      : " + $scope.orgnFg						);
    	//console.log("reportSelectStoreCd: " + $("#reportSelectStoreCd").val()	);

    	//'본부'인 경우, 매장 선택여부 확인
    	if($scope.orgnFg == "H") {
    		if( $("#reportSelectStoreCd").val() == "" ){
    			s_alert.pop( messages["dailyReport.alert.selectStore"] );	//선택된 매장이 없습니다. 매장을 선택해 주십시오.
    			return;
    		}
    	}

        var params = {};
	        params.startDate 		= wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
	        params.endDate 			= wijmo.Globalize.format(endDate  .value, 'yyyyMMdd');
	        params.searchStoreCd   	= $("#reportSelectStoreCd").val();
	        /*
			console.log("startDate     :" + params.startDate 		);
			console.log("endDate       :" + params.endDate			);
			console.log("searchStoreCd :" + params.searchStoreCd	);
			*/
        /*
        $scope._inquiryMain					(	"/sale/anals/dailyReport/report/list.sb",		function _inquiry(url, params, callback, isView, isMaster) {
        $scope._inquirySub					(	"/sale/anals/dailyReport/report/list.sb",
        $scope._postJSONQuery.withOutPopUp	(	"/sale/anals/dailyReport/report/list.sb",
	    $scope._postJSONQuery.withPopUp		(	"/sale/anals/dailyReport/report/list.sb",
        */
	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.
	    $scope._postJSONQuery.withOutPopUp	(	"/sale/anals/dailyReport/report/list.sb",
					        					params,
					        					function(response)	{
																		//데이터 setting
																		$scope.sl   .itemsSource = response.data.data.sl;			        //매출종합
															            $scope.pay  .itemsSource = response.data.data.pay;			        //결제수단
															            $scope.nsl  .itemsSource = response.data.data.nsl;			        //비매출종합
															            $scope.npay .itemsSource = response.data.data.npay;			        //비매출결제수단
															            $scope.pos  .itemsSource = response.data.data.pos;			        //포스정산
															            $scope.emp  .itemsSource = response.data.data.emp;			        //판매원별 매출
															            $scope.dc   .itemsSource = response.data.data.dc;			        //할인내역
															            $scope.dcdtl.itemsSource = response.data.data.dcdtl;		        //할인상세내역
															            $scope.gift .itemsSource = response.data.data.gift;			        //상품권 판매 및 회수내역
															          //$scope.order.itemsSource = response.data.data.order;		        //수발주내역
															            $scope.lv1  .itemsSource = response.data.data.lv1;			        //대분류별 매출
															            $scope.lv2  .itemsSource = response.data.data.lv2;			        //중분류별 매출
															            $scope.lv3  .itemsSource = response.data.data.lv3;			        //소분류별 매출
															            $scope.prod .itemsSource = response.data.data.prod;			        //상품별 매출
																      //$scope.compt.itemsSource = response.data.data.compt;		        //경쟁사매출
															            $scope.appr .itemsSource = response.data.data.appr;			        //승인현황
																	    $scope.membr.itemsSource = response.data.data.membr;		        //회원
															            $scope.work .itemsSource = response.data.data.work;			        //근태관리

															            var configCtrl_1	= agrid.getScope('configCtrl_1');
															            var configCtrl_2 	= agrid.getScope('configCtrl_2');

															            configCtrl_1.flex.itemsSource = response.data.data.payline;			//결재라인
															            configCtrl_2.flex.itemsSource = response.data.data.cfg;				//영업일보 구성

															            //[영업일보 구성] check-box setting
															            for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){
															                var item = configCtrl_2.flex.collectionView.items[i];
															            	if(item.cfgSelYn == "Y")	item.gChk = true;
															            }
															            /*
															            configCtrl  .itemsSource = response.data.data.payline;	console.log("1");
															            configCtrl_2.itemsSource = response.data.data.cfg;		console.log("2");

															            configCtrl  .flex = response.data.data.payline;	console.log("1");
															            configCtrl_2.flex = response.data.data.cfg;		console.log("2");

															            configCtrl  .flex.itemsSource = response.data.data.payline;	console.log("1");
															            configCtrl_2.flex.itemsSource = response.data.data.cfg;		console.log("2");
															            */



															            //데이터 없으면 [접기]
															            if($scope.sl   .rows.length == 0)   $(".div_sl"     ).toggle();     //매출종합
															            if($scope.pay  .rows.length == 0)   $(".div_pay"    ).toggle();     //결제수단
															            if($scope.nsl  .rows.length == 0)   $(".div_nsl"    ).toggle();     //비매출종합
															            if($scope.npay .rows.length == 0)   $(".div_npay"   ).toggle();     //비매출결제수단
															            if($scope.pos  .rows.length == 0)   $(".div_pos"    ).toggle();     //포스정산
															            if($scope.emp  .rows.length == 0)   $(".div_emp"    ).toggle();     //판매원별 매출
															            if($scope.dc   .rows.length == 0)   $(".div_dc"     ).toggle();     //할인내역
															            if($scope.dcdtl.rows.length == 0)   $(".div_dcdtl"  ).toggle();     //할인상세내역
															            if($scope.gift .rows.length == 0)   $(".div_gift"   ).toggle();     //상품권 판매 및 회수내역
															            if($scope.order.rows.length == 0)   $(".div_order"  ).toggle();     //수발주내역
															            if($scope.lv1  .rows.length == 0)   $(".div_lv1"    ).toggle();     //대분류별 매출
															            if($scope.lv2  .rows.length == 0)   $(".div_lv2"    ).toggle();     //중분류별 매출
															            if($scope.lv3  .rows.length == 0)   $(".div_lv3"    ).toggle();     //소분류별 매출
															            if($scope.prod .rows.length == 0)   $(".div_prod"   ).toggle();     //상품별 매출
															            if($scope.compt.rows.length == 0)   $(".div_compt"  ).toggle();     //경쟁사매출
															            if($scope.appr .rows.length == 0)   $(".div_appr"   ).toggle();     //승인현황
															            if($scope.membr.rows.length == 0)   $(".div_membr"  ).toggle();     //회원
															            if($scope.work .rows.length == 0)   $(".div_work"   ).toggle();     //근태관리

															            //영업일보 구성에 없으면 [숨기기]


							        									/* For TEST
							        									if(response.data.data.order ==  undefined )	console.log("response.data.data.order undefined 2");	--> undefined 로 check해도 되지만, 위에서 처럼 rows.length로 했다
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
								     									console.log("response.data.data.work	:" + JSON.stringify(response.data.data.work) );
								     									*/
															            $scope.$broadcast('loadingPopupInactive');
																	},	//callBack function
												false);

        event.preventDefault();	//기능수행 종료 (반드시 추가해야 함)

    });	//$scope.$on("reportCtrl", function(event, data) {
    //[조회] - END			--------------------------------------------------------------------------------------------------------------------------



	//[엑셀 다운로드] - START	------------------------------------------------------------------------------------------------------------------------------
	$scope.excelDownload = function(){
		if ($scope.sl.rows.length <= 0) {
		  //$scope._popMsg( messages["excelUpload.not.downloadData"] );	//다운로드 할 데이터가 없습니다.
			s_alert.pop( messages["excelUpload.not.downloadData"] );	//다운로드 할 데이터가 없습니다.
			return false;
		}

		var dateFrom		= wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
		var dateTo			= wijmo.Globalize.format(endDate  .value, 'yyyy.MM.dd');
	  //var excelFileName = '영업일보(' + dateFrom + ' ~ ' + dateTo + ').xlsx';	//파일명의 '~'  -->  '_'로 자동 치환됨.
		var excelFileName = '영업일보(' + dateFrom + ' - ' + dateTo + ').xlsx';

	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.
		$timeout(function()	{
								var	sl		= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.sl,	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.sl"		]}	);  //매출종합
								var pay		= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.pay, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.pay"		]}	);  //결제수단
								var nsl 	= wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.nsl, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.nsl"		]}	);  //비매출종합
							    var npay    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.npay, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.npay" 	]}	);  //비매출결제수단
							    var pos     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.pos, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.pos"  	]}	);  //포스정산
							    var emp     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.emp, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.emp"  	]}	);  //판매원별 매출
							    var dc      = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.dc, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.dc"		]}	);  //할인내역
							    var dcdtl   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.dcdtl, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.dcdtl"	]}	);  //할인상세내역
							    var gift    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.gift, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.gift" 	]}	);  //상품권 판매 및 회수내역
							    var order   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.order, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.order"	]}	);  //수발주내역
							    var lv1     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.lv1, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.lv1"		]}	);  //대분류별 매출
							    var lv2     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.lv2, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.lv2"  	]}	);  //중분류별 매출
							    var lv3     = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.lv3, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.lv3"  	]}	);  //소분류별 매출
							    var prod    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.prod, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.prod" 	]}	);  //상품별 매출
							    var compt   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.compt, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.compt"	]}	);  //경쟁사매출
							    var appr    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.appr, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.appr" 	]}	);  //승인현황
							    var membr   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.membr, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.membr"	]}	);  //회원
							    var work    = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.work, 	{includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyReport.work" 	]}	);  //근태관리

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

								sl.saveAsync(excelFileName);

								$scope.$broadcast('loadingPopupInactive');
							}, 1000);	//건수가 많아서 1000으로 했음 (현재 1년치 정도가 500ms 미만임)

	};
	//[엑셀 다운로드] - END	------------------------------------------------------------------------------------------------------------------------------



	//매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------
		//함수명         : 모듈에 넘기는 파라미터의 targetId + 'Show'
		//_broadcast: 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.reportSelectStoreShow = function () {
		$scope._broadcast('reportSelectStoreCtrl');
	};
	//매장선택 모듈 팝업 사용시 정의			----------------------------------------------------------------------------------------------------------------------



	//접기 & 펴기 - START			----------------------------------------------------------------------------------------------------------------------
	$(".flddUnfld_sl"  	).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_sl"	   ).toggle();  });  //매출종합
    $(".flddUnfld_pay"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_pay"   ).toggle();  });  //결제수단
    $(".flddUnfld_nsl"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_nsl"   ).toggle();  });  //비매출종합
    $(".flddUnfld_npay" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_npay"  ).toggle();  });  //비매출결제수단
    $(".flddUnfld_pos"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_pos"   ).toggle();  });  //포스정산
    $(".flddUnfld_emp"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_emp"   ).toggle();  });  //판매원별 매출
    $(".flddUnfld_dc"   ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_dc"    ).toggle();  });  //할인내역
    $(".flddUnfld_dcdtl").click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_dcdtl" ).toggle();  });  //할인상세내역
    $(".flddUnfld_gift" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_gift"  ).toggle();  });  //상품권 판매 및 회수내역
    $(".flddUnfld_order").click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_order" ).toggle();  });  //수발주내역
    $(".flddUnfld_lv1"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_lv1"   ).toggle();  });  //대분류별 매출
    $(".flddUnfld_lv2"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_lv2"   ).toggle();  });  //중분류별 매출
    $(".flddUnfld_lv3"  ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_lv3"   ).toggle();  });  //소분류별 매출
    $(".flddUnfld_prod" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_prod"  ).toggle();  });  //상품별 매출
    $(".flddUnfld_compt").click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_compt" ).toggle();  });  //경쟁사매출
    $(".flddUnfld_appr" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_appr"  ).toggle();  });  //승인현황
    $(".flddUnfld_membr").click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_membr" ).toggle();  });  //회원
    $(".flddUnfld_work" ).click(    function(e){    $(this).children("a").toggleClass("open");  $(this).children("a").toggleClass("close"); $(".div_work"  ).toggle();  });  //근태관리
    //접기 & 펴기 - END				----------------------------------------------------------------------------------------------------------------------



}]);	//app.controller('reportCtrl', ['$scope', '$http', function ($scope, $http) {
