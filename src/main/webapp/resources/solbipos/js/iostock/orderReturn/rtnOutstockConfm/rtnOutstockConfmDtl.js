/** 출고확정 상세 그리드 controller */
app.controller('rtnOutstockConfmDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnOutstockConfmDtlCtrl', $scope, $http, true));
  
  var global_storage_cnt = 0;	//매장의 창고 갯수
  $scope.dtlOutDate = wcombo.genDate("#dtlOutDate");

  $scope._setComboData("stmtAcctFg", [
    {"name": messages["rtnOutstockConfm.dtl.stmtAcctAll"], "value": ""},
    {"name": messages["rtnOutstockConfm.dtl.stmtAcctSplr"], "value": "1"},
    {"name": messages["rtnOutstockConfm.dtl.stmtAcctSplrRcpnt"], "value": "2"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 배송기사
    var comboParams             = {};
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	comboParams.sid = document.getElementsByName('sessionId')[0].value;
    }
    var url = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchDtlDlvrCd", null, url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    comboParams         = {};
    comboParams.nmcodeGrpCd = "097";
    url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "outEtcQty") { // 입수에 따라 출고수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용.
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
        
        if (col.binding === "outSplyUprc" || col.binding === "outUnitQty" || col.binding === "outEtcQty") { // 출고수량 수정시
          var item = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
        }
        
        if(str.indexOf('arr') != -1){	//입고수량 수정시
          	idx = str.lastIndexOf('_');
              var item = s.rows[e.row].dataItem;
              $scope.newCalcAmt(item, str.substring(idx+1));
          }
      }

      s.collectionView.commitEdit();
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging  = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    
    //첫째줄 Header 생성
    var dataItem = {};
        dataItem.slipNo         	= messages["rtnOutstockConfm.dtl.slipNo"         ];	
        dataItem.slipFg         	= messages["rtnOutstockConfm.dtl.slipFg"         ];	
        dataItem.seq  			    = messages["rtnOutstockConfm.dtl.seq" 			 ];	
        
        dataItem.storeCd   			= messages["rtnOutstockConfm.dtl.storeCd"		 ]; 
        dataItem.prodCd    			= messages["rtnOutstockConfm.dtl.prodCd"		 ]; 
        dataItem.prodNm    			= messages["rtnOutstockConfm.dtl.prodNm"		 ]; 
        dataItem.barcdCd    		= messages["rtnOutstockConfm.dtl.barcdCd"		 ]; 
             
        dataItem.poUnitFg     		= messages["rtnOutstockConfm.dtl.poUnitFg"		 ];
        dataItem.poUnitQty      	= messages["rtnOutstockConfm.dtl.poUnitQty"		 ]; 
        
        dataItem.outSplyUprc     	= messages["rtnOutstockConfm.dtl.outSplyUprc"	 ];
        dataItem.outUnitQty      	= messages["rtnOutstockConfm.dtl.outUnitQty"	 ]; 
        dataItem.outEtcQty      	= messages["rtnOutstockConfm.dtl.outUnitQty"	 ]; 
        dataItem.outTotQty      	= messages["rtnOutstockConfm.dtl.outUnitQty"	 ]; 
        
        dataItem.outAmt        		= messages["rtnOutstockConfm.dtl.outAmt"      	 ];	
        dataItem.outVat         	= messages["rtnOutstockConfm.dtl.outVat"      	 ];	
        dataItem.outTot         	= messages["rtnOutstockConfm.dtl.outTot"      	 ];	
        dataItem.remark        		= messages["rtnOutstockConfm.dtl.remark"      	 ];	
        dataItem.vatFg01       		= messages["rtnOutstockConfm.dtl.vatFg"      	 ]; 
        dataItem.envst0011      	= messages["rtnOutstockConfm.dtl.envst0011"      ]; 

    s.columnHeaders.rows[0].dataItem = dataItem;
  };

  // 금액 계산
  $scope.calcAmt = function (item) {

    var outSplyUprc = parseInt(item.outSplyUprc);
    var poUnitQty   = parseInt(item.poUnitQty);
    var vat01       = parseInt(item.vatFg01);
    var envst0011   = parseInt(item.envst0011);

    var unitQty = parseInt(nvl(item.outUnitQty, 0)) * parseInt(item.poUnitQty);
    var etcQty  = parseInt(nvl(item.outEtcQty, 0));
    var totQty  = parseInt(unitQty + etcQty);
    var tempAmt = Math.round(totQty * outSplyUprc / poUnitQty);
    var outAmt  = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
    var outVat  = Math.round(tempAmt * vat01 / (10 + envst0011));
    var outTot  = parseInt(outAmt + outVat);

    item.outTotQty = totQty; // 총출고수량
    item.outAmt    = outAmt; // 금액
    item.outVat    = outVat; // VAT
    item.outTot    = outTot; // 합계
  };
  
  $scope.newCalcAmt = function (item, idx) {
  	//$scope.flex.collectionView.editItem(item);

	  var orderSplyUprc = parseInt(item.outSplyUprc);
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
  $scope.$on("rtnOutstockConfmDtlCtrl", function (event, data) {
	$scope.startDate = data.startDate;
	$scope.endDate = data.endDate;
    $scope.slipNo 	= data.slipNo;
    $scope.reqDate 	= data.reqDate;
    $scope.storeCd 	= data.storeCd;
    $scope.wjRtnOutstockConfmDtlLayer.show(true);

    $scope.getSlipNoInfo();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 전표상세 조회
  $scope.getSlipNoInfo = function () {
    var params    = {};
    params.slipNo = $scope.slipNo;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }	
    
    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/orderReturn/rtnOutstockConfm/rtnOutstockConfmDtl/getSlipNoInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {

          $scope.dtlOutDate.value = new Date(getFormatDate(response.data.data.outDate, "-"));
          $scope.outDate          = response.data.data.outDate;
          $scope.inDate           = response.data.data.inDate;
          $scope.slipFg           = response.data.data.slipFg;
          $scope.slipKind         = response.data.data.slipKind;
          $scope.slipKindNm       = response.data.data.slipKindNm;
          $scope.procFg           = response.data.data.procFg;
          $scope.storeCd          = response.data.data.storeCd;
          $scope.storeNm          = response.data.data.storeNm;
          $scope.hdRemark         = response.data.data.remark;
          $scope.hqRemark         = response.data.data.hqRemark;
          $scope.dlvrCd           = nvl(response.data.data.dlvrCd, '');
          $scope.dlvrNm           = response.data.data.dlvrNm;

          // 수주확정
          if ($scope.procFg === "10") {
            $("#spanDtlTitle").html(messages["rtnOutstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnOutstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnOutstockConfm.dtl.reqDate"]+' : ' + getFormatDate($scope.outDate));
            $("#outstockBtnLayer").show();
            $scope.spanOutstockConfirmFg = true;
            $scope.btnDtlSave = true;
            $scope.btnSetOutToIn = true;
            $scope.btnOutstockAfterDtlSave = false;
            $scope.flex.isReadOnly = false;
          }
          // 출고확정 또는 입고확정
          else if ($scope.procFg === "20" || $scope.procFg === "30") {
            $("#outstockBtnLayer").hide();
            $scope.spanOutstockConfirmFg = false;
            $scope.btnDtlSave = false;
            $scope.btnSetOutToIn = false;
            $scope.btnOutstockAfterDtlSave = true;
            $scope.flex.isReadOnly = true;

            // 출고확정
            if ($scope.procFg === "20") {
              $("#spanDtlTitle").html(messages["rtnOutstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnOutstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnOutstockConfm.dtl.outDate"]+' : ' + getFormatDate($scope.outDate));
            }
            // 입고확정
            else if ($scope.procFg === "30") {
              $("#spanDtlTitle").html(messages["rtnOutstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnOutstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnOutstockConfm.dtl.outDate"]+' : ' + getFormatDate($scope.outDate) + ', '+messages["rtnOutstockConfm.dtl.inDate"]+' : ' + getFormatDate($scope.inDate));
            }
          }

          $scope.searchRtnOutstockConfmDtlList();
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  // 출고확정 상세내역 리스트 조회
  $scope.searchRtnOutstockConfmDtlList = function () {
    // 파라미터
    var params    = {};
    params.slipNo = $scope.slipNo;
    params.reqDate = $scope.reqDate;
    params.storeCd = $scope.storeCd;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/orderReturn/rtnOutstockConfm/rtnOutstockConfmDtl/list.sb", params, function () {
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
//        var arrCurrQty		= new Array( new Array(), new Array() );
      
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
//            arrCurrQty[i] 		= item.arrCurrQty.split("^");		//재고수량
    	}

    	global_storage_cnt	= arrStorageCd[0].length;
    	/*
		  	while(grid.columns.length > 14){	//'비고'가 14번째	-> 숨겨져 있는 column도 포함해야 함. -> 아래처럼 변경
		  		grid.columns.removeAt(grid.columns.length-1);
		  	}
		  	*/
		  	while(grid.columns.length > 19){	//이 상세화면이 다시 열리는 경우를 대비하여, 추가된 칼럼 삭제해야 함. ('arrInTot'이 28번재)
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
//            		grid.columns.push( new wijmo.grid.Column({header:messages["rtnInstockConfm.dtl.currQty"],	binding:"arrCurrQty_"		+ j,	width:80,    align:"right",    isReadOnly:true,		aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//재고수량 - 주문딘위
            		grid.columns.push( new wijmo.grid.Column({header:messages["rtnInstockConfm.dtl.outUnitQty"],binding:"arrOrderUnitQty_"	+ j,	width:50,    align:"right",    isReadOnly:false,	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 주문딘위
                	grid.columns.push( new wijmo.grid.Column({header:messages["rtnInstockConfm.dtl.outUnitQty"],binding:"arrOrderEtcQty_"	+ j,    width:50,    align:"right",    isReadOnly:false,  	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 나머지
                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrOrderTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum"}) );					//입고수량 - 합계
                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrOrderTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", visible:"false"}) );	//입고수량 - 합계
                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inAmt"],	binding:"arrOrderAmt_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액
                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inVat"],    binding:"arrOrderVat_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 부가세
                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTot"],	binding:"arrOrderTot_"		+ j,    width:70,    align:"right",    isReadOnly:true,		aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 합계
            	}
            	
//            	grid.columnHeaders.setCellData(0, 'arrCurrQty_'			+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrOrderUnitQty_'	+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrOrderEtcQty_'		+ j, arrStorageNm[i][j]);
              //grid.columnHeaders.setCellData(0, 'arrOrderTotQty_'	+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrOrderAmt_'		+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrOrderVat_'		+ j, arrStorageNm[i][j]);
                grid.columnHeaders.setCellData(0, 'arrOrderTot_'		+ j, arrStorageNm[i][j]);
                
//                grid.setCellData(i, 'arrCurrQty_'		+ j,	arrCurrQty[i][j]);
                grid.setCellData(i, 'arrOrderUnitQty_'	+ j,	arrOrderUnitQty[i][j]);
                grid.setCellData(i, 'arrOrderEtcQty_'	+ j,	arrOrderEtcQty	[i][j]);
                grid.setCellData(i, 'arrOrderAmt_'		+ j,	arrOrderAmt	[i][j]);
                grid.setCellData(i, 'arrOrderVat_'		+ j,	arrOrderVat	[i][j]);
                grid.setCellData(i, 'arrOrderTot_'		+ j,	arrOrderTot	[i][j]);

        	}	//for(var j=0; j<arrStorageCd[i].length; j++){
        }		//for(var i=0; i<arrStorageCd.length; i++){

        /*
		console.log('panel.cellType: ' + wijmo.grid.CellType.None			);	//0
		console.log('panel.cellType: ' + wijmo.grid.CellType.Cell			);	//1
		console.log('panel.cellType: ' + wijmo.grid.CellType.ColumnHeader	);	//2
		console.log('panel.cellType: ' + wijmo.grid.CellType.RowHeader		);	//3
		console.log('panel.cellType: ' + wijmo.grid.CellType.TopLeft		);	//4
		console.log('panel.cellType: ' + wijmo.grid.CellType.ColumnFooter	);	//5
		console.log('panel.cellType: ' + wijmo.grid.CellType.BottomLeft		);	//6

		//console.log('panel.cellType: ' + r + ' - ' + c + ' - ' + panel.cellType);
		if (panel.cellType === wijmo.grid.CellType.ColumnFooter) {
			console.log('### ColumnFooter:' + panel.getCellData(r,c) );
		}

		s.columnHeaders.rows[0].allowMerging    = true;
		*/

        ///*
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

    });		//$scope._inquirySub("/iostock/order/instockConfm/instockConfmDtl/list.sb", params, function () {
  };	//$scope.searchInstockConfmDtlList	------------------------------------------

  // 저장
  $scope.save = function () {
    var params = [];

    // 확정처리가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
    if ($("#outstockConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
      var item = $scope.flex.collectionView.items[0];
      if (item === null) return false;

      $scope.flex.collectionView.editItem(item);
      item.status = "U";
      $scope.flex.collectionView.commitEdit();
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];
      var k = i+1;

      if (nvl(item.orderTotQty,0) !== item.outTotQty) {
          $scope._popMsg(k+"번째 행의 주문수량과 창고출고수량이 맞지 않습니다."); // 출고수량을 입력해주세요.
          return false;
      }
      if (item.outUnitQty === null && item.outEtcQty === null) {
        $scope._popMsg(messages["rtnOutstockConfm.dtl.require.outQty"]); // 출고수량을 입력해주세요.
        return false;
      }
      if (item.outEtcQty !== null && (parseInt(item.outEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["rtnOutstockConfm.dtl.not.outEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.outTot !== null && (parseInt(item.outTot) > 9999999999)) {
        $scope._popMsg(messages["rtnOutstockConfm.dtl.not.overOutTot"]); // 출고금액이 너무 큽니다.
        return false;
      }

      item.status    = "U";
      item.outDate   = wijmo.Globalize.format($scope.dtlOutDate.value, 'yyyyMMdd');
      item.hdRemark  = $scope.hdRemark;
      item.hqRemark  = $scope.hqRemark;
      item.dlvrCd    = $scope.dlvrCd;
      item.confirmFg = ($("#outstockConfirmFg").is(":checked") ? $("#outstockConfirmFg").val() : "");
      item.reqDate   = $scope.reqDate;
      item.slipFg    = $scope.slipFg;
      item.storeCd   = $scope.storeCd;
      item.empNo     = "0000";
      item.storageCd = "999";
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      
      var arrOrderUnitQty	= "";	//입고수량 주문단위
      var arrOrderEtcQty	= "";	//입고수량 나머지
      var arrOrderTotQty 	= "";	//입고수량 합계
      var arrOrderAmt		= "";	//입고금액
      var arrOrderVat		= "";	//입고금액 부가세
      var arrOrderTot		= "";	//입고금액 합계
      
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
    $scope._save("/iostock/orderReturn/rtnOutstockConfm/rtnOutstockConfmDtl/save.sb", params, function () {
      $scope.saveRtnOutstockConfmDtlCallback()
    });
  };

  // 저장 후 콜백 함수
  $scope.saveRtnOutstockConfmDtlCallback = function () {
    var rtnOutstockConfmScope = agrid.getScope('rtnOutstockConfmCtrl');
    rtnOutstockConfmScope.searchRtnOutstockConfmList();

    $scope.wjRtnOutstockConfmDtlLayer.hide(true);
  };

  // 출고확정 이후 저장. 비고, 본사비고, 배송기사를 저장한다.
  $scope.saveOutstockAfter = function () {
    // 파라미터
    var params      = {};
    params.slipNo   = $scope.slipNo;
    params.hdRemark = $scope.hdRemark;
    params.hqRemark = $scope.hqRemark;
    params.dlvrCd   = $scope.dlvrCd;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }	
    
    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/orderReturn/rtnOutstockConfm/rtnOutstockConfmDtl/saveOutstockAfter.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.flex.collectionView.clearChanges();
        $scope.saveRtnOutstockConfmDtlCallback();
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  $scope.fnConfirmChk = function () {
    if ($("#outstockConfirmFg").prop("checked")) {
      $("#divDtlOutDate").show();
    }
    else {
      $("#divDtlOutDate").hide();
    }
  };
  
  // 출고내역으로 입고내역 세팅
  $scope.setOutToIn = function () {
      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

      //데이터 처리중 팝업 띄우기위해 $timeout 사용.
      $timeout(function () {
          for (var i=0; i<$scope.flex.collectionView.items.length; i++) {
          	var item =  $scope.flex.collectionView.items[i];
          	var orderUnitQty 	= item.outUnitQty;
          	var orderEtcQty 	= item.outEtcQty;
          	var orderTotQty 	= item.outTotQty;
          	var orderAmt 		= item.outAmt;
          	var orderVat 		= item.outVat;
          	var orderTot 		= item.outTot;
          	item.orderTotQty = orderTotQty;
            $scope.flex.collectionView.editItem(item);
            
          	//창고부분 모두 0으로 setting
  			for(var k=0; k<global_storage_cnt; k++){
  				eval('item.arrOrderUnitQty_'	+ 0 + ' = orderUnitQty;');
  				eval('item.arrOrderEtcQty_'		+ 0 + ' = orderEtcQty;');
  				eval('item.arrOrderTotQty_'		+ 0 + ' = orderTotQty;');
  				eval('item.arrOrderAmt_'		+ 0 + ' = orderAmt;');
  				eval('item.arrOrderVat_'		+ 0 + ' = orderVat;');
  				eval('item.arrOrderTot_'		+ 0 + ' = orderTot;');
  				
  			}

  			//첫번째 창고의 [입고수량]을 [출고수량] 값으로 setting
              item.arrInUnitQty_0	= item.outUnitQty;
              item.arrInEtcQty_0	= item.outEtcQty;

              $scope.calcAmt(item, 0);

              $scope.flex.collectionView.commitEdit();
          }

          $scope.$broadcast('loadingPopupInactive');
      }, 100);
  };	//$scope.setOutToIn	--------------------------------------------------------------------------------------------------------------------------
  
  // 거래명세표
  $scope.reportTrans = function () {
    var params        = {};
    params.startDate  = $scope.startDate;
    params.endDate    = $scope.endDate;
    params.slipFg     = $scope.slipFg;
    params.strSlipNo  = $scope.slipNo;
    params.stmtAcctFg = $scope.stmtAcctFg;
    $scope._broadcast('transReportCtrl', params);
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
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
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
