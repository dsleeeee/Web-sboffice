/** 반품등록 상세 그리드 controller */
app.controller('rtnStoreOrderDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnStoreOrderDtlCtrl', $scope, $http, true));
  
  var global_storage_cnt = 0;	//매장의 창고 갯수
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "097";
    var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "orderEtcQty") { // 입수에 따라 반품수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용
            e.cell.outerHTML = e.cell.outerHTML;
          }
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        
      var str = col.binding;
      var idx = 0;

      if(str.indexOf('arr') != -1){	//입고수량 수정시
      	idx = str.lastIndexOf('_');

      var item = s.rows[e.row].dataItem;
      $scope.calcAmt(item, str.substring(idx+1));
        
//        if (col.binding === "orderUnitQty" || col.binding === "orderEtcQty") { // 반품수량 수정시
//          var item          = s.rows[e.row].dataItem;
//          var orderSplyUprc = parseInt(item.orderSplyUprc);
//          var poUnitQty     = parseInt(item.poUnitQty);
//          var vat01         = parseInt(item.vatFg01);
//          var envst0011     = parseInt(item.envst0011);
//
//          var unitQty  = parseInt(nvl(item.orderUnitQty, 0)) * parseInt(item.poUnitQty);
//          var etcQty   = parseInt(nvl(item.orderEtcQty, 0));
//          var totQty   = parseInt(unitQty + etcQty);
//          var tempAmt  = Math.round(totQty * orderSplyUprc / poUnitQty);
//          var orderAmt = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
//          var orderVat = Math.round(tempAmt * vat01 / (10 + envst0011));
//          var orderTot = parseInt(orderAmt + orderVat);
//
//          item.orderTotQty = totQty;   // 총반품수량
//          item.orderAmt    = orderAmt; // 금액
//          item.orderVat    = orderVat; // VAT
//          item.orderTot    = orderTot; // 합계
        }
      }

      s.collectionView.commitEdit();
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging  = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    
    //첫째줄 Header 생성
    var dataItem = {};
        dataItem.prodCd         	= messages["rtnStoreOrder.dtl.prodCd"        		];	//상품코드
        dataItem.prodNm         	= messages["rtnStoreOrder.dtl.prodNm"        		];	//상품명
        dataItem.orderSplyUprc  	= messages["rtnStoreOrder.dtl.orderSplyUprc" 		];	//공급단가       
        dataItem.prevOrderUnitQty   = messages["rtnStoreOrder.dtl.prevOrderUnitQty"		]; //기반품수량
             
        dataItem.orderUnitQty     	= messages["rtnStoreOrder.dtl.orderUnitQty"		 	]; //반품수량
        dataItem.orderEtcQty      	= messages["rtnStoreOrder.dtl.orderUnitQty"		 	]; //반품수량
        dataItem.orderTotQty      	= messages["rtnStoreOrder.dtl.orderUnitQty"		 	]; //반품수량

        dataItem.orderAmt        	= messages["rtnStoreOrder.dtl.orderAmt"      ];	//금액
        dataItem.orderVat         	= messages["rtnStoreOrder.dtl.orderVat"      ];	//VAT
        dataItem.orderTot         	= messages["rtnStoreOrder.dtl.orderTot"      ];	//합계
        
        dataItem.poUnitFg       	= messages["rtnStoreOrder.dtl.poUnitFg"      ]; //반품단위
        dataItem.poUnitQty      	= messages["rtnStoreOrder.dtl.poUnitQty"     ]; //입수
        
        dataItem.remark         	= messages["rtnStoreOrder.dtl.remark"        ]; //비고
        dataItem.poMinQty        	= messages["rtnStoreOrder.dtl.poMinQty"      ]; //발주최소수량
        dataItem.vatFg01        	= messages["rtnStoreOrder.dtl.vatFg"         ]; //상품부가세구분
        dataItem.envst0011        	= messages["rtnStoreOrder.dtl.envst0011"     ]; //출고가-부가세포함여부
    s.columnHeaders.rows[0].dataItem = dataItem;
    //Grid Header 2줄 - END		----------------------------------------------------------------
    
    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
        panel.rows[r].allowMerging    = true;
        panel.columns[c].allowMerging = true;
        wijmo.setCss(cell, {
          display    : 'table',
          tableLayout: 'fixed'
        });
        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
        wijmo.setCss(cell.children[0], {
          display      : 'table-cell',
          verticalAlign: 'middle',
          textAlign    : 'center'
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
  };

  $scope.calcAmt = function (item, idx) {
	  	//$scope.flex.collectionView.editItem(item);

		  var orderSplyUprc = parseInt(item.orderSplyUprc);
		  var poUnitQty     = parseInt(item.poUnitQty);
		  var vat01         = parseInt(item.vatFg01);
		  var envst0011     = parseInt(item.envst0011);
		  
	      var unitQty     = parseInt(nvl(eval('item.arrOrderUnitQty_' + idx), 0)) * parseInt(item.poUnitQty);
	      var etcQty      = parseInt(nvl(eval('item.arrOrderEtcQty_'  + idx), 0));
	      var totQty      = parseInt(unitQty + etcQty);
	      var tempAmt  	  = Math.round(totQty * orderSplyUprc / poUnitQty);
	      var orderAmt 	  = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
	      var orderVat 	  = Math.round(tempAmt * vat01 / (10 + envst0011));
	      var orderTot    = parseInt(orderAmt + orderVat);

	      eval('item.arrOrderTotQty_'	+ idx + ' = totQty;');		//총입고수량
	      eval('item.arrOrderAmt_' 		+ idx + ' = orderAmt;'	); 	//금액
	      eval('item.arrOrderVat_' 		+ idx + ' = orderVat;'	); 	//VAT
	      eval('item.arrOrderTot_'		+ idx + ' = orderTot;'	);	//합계

	      //전체합계 setting - Header명 '입고수량' 부분 (입고수량, 금액, VAT, 합계) - START
		        //console.log('global_storage_cnt:' + global_storage_cnt);
		        var arrOrderUnitQty= 0;
		        var arrOrderEtcQty	= 0;
		        var arrOrderTotQty = 0;
		        var arrOrderAmt	= 0;
		        var arrOrderVat	= 0;
		        var arrOrderTot	= 0;

		        for(var i=0; i<global_storage_cnt; i++){
		        	eval('arrOrderUnitQty	+= parseInt(nvl(item.arrOrderUnitQty_'	+ i + ',0));');
		        	eval('arrOrderEtcQty	+= parseInt(nvl(item.arrOrderEtcQty_'	+ i + ',0));');
		        	eval('arrOrderTotQty	+= parseInt(nvl(item.arrOrderTotQty_'	+ i + ',0));');
		        	eval('arrOrderAmt		+= parseInt(nvl(item.arrOrderAmt_'		+ i + ',0));');
		        	eval('arrOrderVat		+= parseInt(nvl(item.arrOrderVat_'		+ i + ',0));');
		        	eval('arrOrderTot		+= parseInt(nvl(item.arrOrderTot_'		+ i + ',0));');
		        }
		        item.orderUnitQty	= arrOrderUnitQty;  //입고수량 - 단위
		        item.orderEtcQty    = arrOrderEtcQty;   //입고수량 - 나머지
		        		        
		        item.orderTotQty = totQty;   // 총수량
		        item.orderAmt    = orderAmt; // 금액
		        item.orderVat    = orderVat; // VAT
		        item.orderTot    = orderTot; // 합계
		        
		    //전체합계 setting - Header명 '입고수량' 부분 (입고수량, 금액, VAT, 합계) - END

		    //$scope.flex.collectionView.commitEdit();
	  };	//$scope.calcAmt	--------------------------------------------------------------------------------------------------------------------------

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnStoreOrderDtlCtrl", function (event, data) {
	  
	    // 그리드 초기화
	    var cv          = new wijmo.collections.CollectionView([]);
	    cv.trackChanges = true;
	    $scope.data     = cv;
	  
	    $scope.reqDate     = data.reqDate;
	    $scope.slipFg      = data.slipFg;
	    $scope.procFg      = data.procFg;
	    $scope.dtlHdRemark = data.hdRemark;
	    $scope.storeCd     = data.storeCd;

	    $scope.wjRtnStoreOrderDtlLayer.show(true);
	    if ($scope.procFg === "00") {
	      $scope.btnAddProd = true;
	      $scope.btnDtlSave = true;
//	      $scope.btnConfirm = true;
	      $scope.flex.isReadOnly = false;
	      
	      if (gEnvst1042 === "1" || gEnvst1042 === "2") {
	          $scope.btnConfirm = true;
	        } else {
	          $scope.btnConfirm = false;
	        }
	    }
	    else {
	      $scope.btnAddProd = false;
	      $scope.btnDtlSave = false;
	      $scope.btnConfirm = false;
	      $scope.flex.isReadOnly = true;
	    }
	
	    $("#spanDtlTitle").html(messages["rtnStoreOrder.reqDate"]+' : ' + getFormatDate($scope.reqDate, '-'));
	    $scope.wjRtnStoreOrderDtlLayer.show(true);
	    $scope.searchRtnStoreOrderDtlList();
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
  });


  // 반품등록 상세내역 리스트 조회
  $scope.searchRtnStoreOrderDtlList = function () {
    // 파라미터
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;
    params.storeCd = $scope.storeCd;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
//    $scope._inquirySub("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/list.sb", params);
    params.listScale = 50;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/list.sb", params, function () {	//조회 : URL, parameter, callBack Function
    	if (gEnvst1044 === "N") {		//1043: 매장입고시수량변경

        } else {
        	global_storage_cnt	= 0;	//매장의 창고 갯수

        	var arrProdCd		= new Array( new Array(), new Array() );
            var arrStorageCd	= new Array( new Array(), new Array() );
            var arrStorageNm	= new Array( new Array(), new Array() );
            var arrOrderUnitQty	= new Array( new Array(), new Array() );
            var arrOrderEtcQty	= new Array( new Array(), new Array() );
            var arrOrderTotQty	= new Array( new Array(), new Array() );
            var arrOrderAmt		= new Array( new Array(), new Array() );
            var arrOrderVat		= new Array( new Array(), new Array() );
            var arrOrderTot		= new Array( new Array(), new Array() );
//            var arrCurrQty		= new Array( new Array(), new Array() );
          
            var grid 			= $scope.flex;
            var item;
        	for(var i=0; i<grid.collectionView.items.length; i++){
                item 				= grid.collectionView.items[i];

                arrProdCd[i] 		= item.prodCd;
                arrStorageCd[i] 	= item.arrStorageCd.split("^");
                arrStorageNm[i] 	= item.arrStorageNm.split("^");
                arrOrderUnitQty[i] 	= item.arrOrderUnitQty.split("^");	//입고수량 - 주문딘위
                arrOrderEtcQty[i] 	= item.arrOrderEtcQty.split("^");	//입고수량 - 나머지
                arrOrderTotQty[i] 	= item.arrOrderTotQty.split("^");	//입고수량 - 합계
                arrOrderAmt[i] 		= item.arrOrderAmt.split("^");		//입고금액
                arrOrderVat[i] 		= item.arrOrderVat.split("^");		//입고금액 - 부가세
                arrOrderTot[i] 		= item.arrOrderTot.split("^");		//입고금액 - 합계
//                arrCurrQty[i] 		= item.arrCurrQty.split("^");		//재고수량
        	}

        	global_storage_cnt	= arrStorageCd[0].length;
        	/*
  		  	while(grid.columns.length > 14){	//'비고'가 14번째	-> 숨겨져 있는 column도 포함해야 함. -> 아래처럼 변경
  		  		grid.columns.removeAt(grid.columns.length-1);
  		  	}
  		  	*/
  		  	while(grid.columns.length > 23){	//이 상세화면이 다시 열리는 경우를 대비하여, 추가된 칼럼 삭제해야 함. ('arrInTot'이 28번재)
  		  		grid.columns.removeAt(grid.columns.length-1);
  		  	}

            for(var i=0; i<arrStorageCd.length; i++){
            	for(var j=0; j<arrStorageCd[i].length; j++){
            		/*
                	console.log(i + '-' + j +
                			' Prod:'		+ arrProdCd   	[i]		+
                			' & Cd:'    	+ arrStorageCd	[i][j]	+
                            ' & Nm:' 		+ arrStorageNm	[i][j]  +
                            ' & UnitQty:' 	+ arrOrderUnitQty	[i][j]  +
                            ' & EtcQty:'  	+ arrOrderEtcQty	[i][j]  +
                            ' & TotQty:'  	+ arrOrderTotQty	[i][j]  +
                            ' & Amt:'     	+ arrOrderAmt		[i][j]  +
                            ' & Vat:'     	+ arrOrderVat		[i][j]  +
                            ' & Tot:'     	+ arrOrderTot		[i][j]  );
					*/
                	if(i == 0){
                		//입고수량, 금액, VAT, 합계
//                		grid.columns.push( new wijmo.grid.Column({header:messages["rtnInstockConfm.dtl.currQty"],	binding:"arrCurrQty_"		+ j,	width:80,    align:"right",    isReadOnly:true,		aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//재고수량 - 주문딘위
                		grid.columns.push( new wijmo.grid.Column({header:messages["rtnInstockConfm.dtl.outUnitQty"],binding:"arrOrderUnitQty_"	+ j,	width:50,    align:"right",    isReadOnly:false,	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 주문딘위
	                	grid.columns.push( new wijmo.grid.Column({header:messages["rtnInstockConfm.dtl.outUnitQty"],binding:"arrOrderEtcQty_"	+ j,    width:50,    align:"right",    isReadOnly:false,  	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 나머지
	                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrOrderTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum"}) );					//입고수량 - 합계
	                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrOrderTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", visible:"false"}) );	//입고수량 - 합계
	                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inAmt"],	binding:"arrOrderAmt_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액
	                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inVat"],    binding:"arrOrderVat_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 부가세
	                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTot"],	binding:"arrOrderTot_"		+ j,    width:70,    align:"right",    isReadOnly:true,		aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 합계
                	}
                	
//                	grid.columnHeaders.setCellData(0, 'arrCurrQty_'			+ j, arrStorageNm[i][j]);
                	grid.columnHeaders.setCellData(0, 'arrOrderUnitQty_'	+ j, arrStorageNm[i][j]);
                	grid.columnHeaders.setCellData(0, 'arrOrderEtcQty_'		+ j, arrStorageNm[i][j]);
                  //grid.columnHeaders.setCellData(0, 'arrOrderTotQty_'	+ j, arrStorageNm[i][j]);
                	grid.columnHeaders.setCellData(0, 'arrOrderAmt_'		+ j, arrStorageNm[i][j]);
                	grid.columnHeaders.setCellData(0, 'arrOrderVat_'		+ j, arrStorageNm[i][j]);
                    grid.columnHeaders.setCellData(0, 'arrOrderTot_'		+ j, arrStorageNm[i][j]);
                    
//                    grid.setCellData(i, 'arrCurrQty_'		+ j,	arrCurrQty[i][j]);
                    grid.setCellData(i, 'arrOrderUnitQty_'	+ j,	arrOrderUnitQty[i][j]);
                    grid.setCellData(i, 'arrOrderEtcQty_'	+ j,	arrOrderEtcQty	[i][j]);
                    grid.setCellData(i, 'arrOrderAmt_'		+ j,	arrOrderAmt	[i][j]);
                    grid.setCellData(i, 'arrOrderVat_'		+ j,	arrOrderVat	[i][j]);
                    grid.setCellData(i, 'arrOrderTot_'		+ j,	arrOrderTot	[i][j]);

            	}	
            }		

            grid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                    //align in center horizontally and vertically
                    panel.rows   [r].allowMerging = true;
                    panel.columns[c].allowMerging = true;

                    wijmo.setCss(cell,  {
                                            display		: 'table',
                                            tableLayout : 'fixed'
                                        });

                    cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

                    wijmo.setCss(cell.children[0],	{
					  									display 		: 'table-cell',
					  									verticalAlign 	: 'middle',
					  									textAlign		: 'center'
				  									});

                } else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
                    if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우에는 표시하지 않음
                        cell.textContent = '';
                    } else {
                        if (!isEmpty(panel._rows[r]._data.rnum)) {
                            cell.textContent = (panel._rows[r]._data.rnum).toString();
                        } else {
                            cell.textContent = (r + 1).toString();
                        }
                    }

                } else if (panel.cellType === wijmo.grid.CellType.Cell) {	//readOnly 배경색 표시
                    var col = panel.columns[c];
                    if (col.isReadOnly) {
                        wijmo.addClass(cell, 'wj-custom-readonly');
                    }
                }
            }	//grid.itemFormatter = function (panel, r, c, cell) {
            //*/

            //[합계]란에 새로 추가한 column들의 '합계'가 계산되지 않아 추가해 보았으나, 원인은 'dataType'등을 넣어주면 되는 것이었음.
			//grid.columnFooters.rows.push(new wijmo.grid.GroupRow());	//add the new GroupRow to the grid's 'columnFooters' panel
			//grid.bottomLeftCells.setCellData(0, 0, '합계');			//add a sigma to the header to show that this is a summary row

            $scope.flex.refresh();

        }	//else
    		//console.log('response:\n' + JSON.stringify(response.data.data) );
    });		//$scope._inquirySub("/iostock/order/instockConfm/instockConfmDtl/list.sb", params, function () {
};	//$scope.searchInstockConfmDtlList	----------------------------


  // 반품 상세 저장
  $scope.saveRtnStoreOrderDtl = function (saveFg) {
    var params   = [];
    var orderTot = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item       = $scope.flex.collectionView.itemsEdited[i];
      item.status    = "U";
      item.reqDate   = $scope.reqDate;
      item.slipFg    = $scope.slipFg;
      item.storageCd  = "999";
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리.
      item.hdRemark  = $scope.dtlHdRemark;
      item.storeCd   = $scope.storeCd;
      orderTot += parseInt(item.orderTot);
           
      for(var k=0; k<global_storage_cnt; k++){
    	  if(k==0){
    		  eval('arrOrderUnitQty	= parseInt(nvl(item.arrOrderUnitQty_'	+ k + ',0));');
    		  eval('arrOrderEtcQty	= parseInt(nvl(item.arrOrderEtcQty_'	+ k + ',0));');
    		  eval('arrOrderTotQty	= parseInt(nvl(item.arrOrderTotQty_'	+ k + ',0));');
    		  eval('arrOrderAmt		= parseInt(nvl(item.arrOrderAmt_'		+ k + ',0));');
    		  eval('arrOrderVat		= parseInt(nvl(item.arrOrderVat_'		+ k + ',0));');
    		  eval('arrOrderTot		= parseInt(nvl(item.arrOrderTot_'		+ k + ',0));');
    	  }else{
    		  eval('arrOrderUnitQty += "^" + parseInt(nvl(item.arrOrderUnitQty_'	+ k + ',0));');
    		  eval('arrOrderEtcQty	+= "^" + parseInt(nvl(item.arrOrderEtcQty_'		+ k + ',0));');
    		  eval('arrOrderTotQty	+= "^" + parseInt(nvl(item.arrOrderTotQty_'		+ k + ',0));');
    		  eval('arrOrderAmt		+= "^" + parseInt(nvl(item.arrOrderAmt_'		+ k + ',0));');
    		  eval('arrOrderVat		+= "^" + parseInt(nvl(item.arrOrderVat_'		+ k + ',0));');
    		  eval('arrOrderTot		+= "^" + parseInt(nvl(item.arrOrderTot_'		+ k + ',0));');
    	  }
      }
      item.arrOrderUnitQty 	= arrOrderUnitQty;
      item.arrOrderEtcQty 	= arrOrderEtcQty;
      item.arrOrderTotQty 	= arrOrderTotQty;
      item.arrOrderAmt 		= arrOrderAmt;
      item.arrOrderVat		= arrOrderVat;
      item.arrOrderTot 		= arrOrderTot;
      
      params.push(item);
    }


    if (saveFg === "confirm") {
    	$scope.confirm();
    }
      
    $scope._save("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/save.sb", params, function () {
        $scope.saveOrderDtlCallback();
    });
   
  };


  // 반품확정
  $scope.confirm = function () {

    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;
    params.remark  = $scope.dtlHdRemark;
    params.storeCd = $scope.storeCd;
    params.envst1042= gEnvst1042;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid = document.getElementsByName('sessionId')[0].value;
    }	

    $scope._save("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/confirm.sb", params, function () {
      $scope.saveOrderDtlCallback()
    });
  };


  // 저장 후 콜백 함수
  $scope.saveOrderDtlCallback = function () {
    $scope.searchRtnStoreOrderDtlList();
    $scope.wjRtnStoreOrderDtlLayer.hide(true);
    var rtnStoreOrderScope = agrid.getScope('rtnStoreOrderCtrl');
    rtnStoreOrderScope.searchRtnStoreOrderList();
  };


  // 상품추가/변경
  $scope.addProd = function () {
    var params        = {};
    params.callParent = "rtnStoreOrderDtl";
    params.reqDate    = $scope.reqDate;
    params.slipFg     = $scope.slipFg;
    params.hdRemark   = $scope.dtlHdRemark;
    params.storeCd    = $scope.storeCd;
    $scope._broadcast("rtnStoreOrderRegistCtrl", params);
  };


  // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
  // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
  // comboId : combo 생성할 ID
  // gridMapId : grid 에서 사용할 Map ID
  // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
  // params : 데이터 조회할 url에 보낼 파라미터
  // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
  // callback : queryCombo 후 callback 할 함수
  $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
    var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
    if (url) {
      comboUrl = url;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : comboUrl, /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data.list)) {
          var list       = response.data.data.list;
          var comboArray = [];
          var comboData  = {};

          if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
            comboArray = [];
            if (option === "A") {
              comboData.name  = messages["cmm.all"];
              comboData.value = "";
              comboArray.push(comboData);
            } else if (option === "S") {
              comboData.name  = messages["cmm.select"];
              comboData.value = "";
              comboArray.push(comboData);
            }

            for (var i = 0; i < list.length; i++) {
              comboData       = {};
              comboData.name  = list[i].nmcodeNm;
              comboData.value = list[i].nmcodeCd;
              comboArray.push(comboData);
            }
            $scope._setComboData(comboId, comboArray);
          }

          if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
            comboArray = [];
            for (var i = 0; i < list.length; i++) {
              comboData      = {};
              comboData.id   = list[i].nmcodeCd;
              comboData.name = list[i].nmcodeNm;
              comboArray.push(comboData);
            }
            $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
          }
        }
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.error"]);
      return false;
    }).then(function () {
      if (typeof callback === 'function') {
        $timeout(function () {
          callback();
        }, 10);
      }
    });
  };

}]);
