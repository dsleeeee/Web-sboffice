/** 매대이동관리 신규등록 그리드 controller */
app.controller('standMoveRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('standMoveRegistCtrl', $scope, $http, true));

  $scope.moveDate = wcombo.genDate("#regMoveDate");
  
  //이동구분
  $scope._setComboData("srchRegDlvrFg", [
	    {"name": messages["standMove.standMove"],  	"value": "-1"},
	    {"name": messages["standMove.storageMove"], "value": "1"}
  ]);
  
  var global_storage_cnt = 0;	//매장의 창고 갯수
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	      
      //Grid format handler
      s.formatItem.addHandler(function (s, e) {
          if (e.panel === s.cells) {
              var col = s.columns[e.col];
              var item = s.rows[e.row].dataItem;
              if (col.binding === "inEtcQty") {   //입수에 따라 출고수량 컬럼 readOnly 컨트롤
                  if (item.poUnitQty === 1) {
                      wijmo.addClass(e.cell, 'wj-custom-readonly');
                      wijmo.setAttribute(e.cell, 'aria-readonly', true);

                      //Attribute 의 변경사항을 적용
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
              }
          }

          s.collectionView.commitEdit();
       }); 	  
      
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      s.bottomLeftCells.setCellData(0, 0, '합계');
      
    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging  = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    
    //첫째줄 Header 생성
    var dataItem = {};
        dataItem.prodCd         	= messages["storeMove.reg.prodCd"        	 	];	//상품코드
        dataItem.prodNm         	= messages["storeMove.reg.prodNm"        	 	];	//상품명        
        dataItem.poUnitFg  			= messages["storeMove.reg.poUnitFg" 		 	];	//단위
        dataItem.poUnitQty  		= messages["storeMove.reg.poUnitQty" 		 	];	//입수
        
        dataItem.safeStockQty  		= messages["standMove.safeStockQty" 		 	];	//안전재고
        dataItem.totCurrQty  		= messages["standMove.standCurrQty" 		 	];	//총재고
        dataItem.standTotQty  		= messages["standMove.standQty" 		 		];	//총수량
        dataItem.standCurrUnitQty  	= messages["standMove.standQty" 		 		];	//주문단위
        dataItem.standCurrEtcQty  	= messages["standMove.standQty" 		 		];	//나머지
        
    s.columnHeaders.rows[0].dataItem = dataItem;
    //Grid Header 2줄 - END		----------------------------------------------------------------    
  };

  $scope.calcAmt = function (item, idx) {
	  
	    var poUnitQty  = parseInt(item.poUnitQty);
//	    var unitQty    = parseInt(nvl(item.standCurrUnitQty, 0)) * parseInt(item.poUnitQty);
//	    var etcQty     = parseInt(nvl(item.standCurrEtcQty, 0));
	    var unitQty     = parseInt(nvl(eval('item.arrUnitQty_' + idx), 0)) * parseInt(item.poUnitQty);
	    var etcQty      = parseInt(nvl(eval('item.arrEtcQty_'  + idx), 0));	    
	    var totQty     = parseInt(unitQty + etcQty);

	    eval('item.arrTotQty_'+ idx + ' = totQty;');	//총입고수량
//	    item.standTotQty 		= totQty;   // 총수량
	    //전체합계 setting - Header명 '입고수량' 부분 (입고수량, 금액, VAT, 합계) - START

	        var arrUnitQty  = 0;
	        var arrEtcQty	= 0;
	        var arrTotQty   = 0;
        
	        for(var i=0; i<global_storage_cnt; i++){
	        	eval('arrUnitQty	+= parseInt(nvl(item.arrUnitQty_'	+ i + ',0));');
	        	eval('arrEtcQty		+= parseInt(nvl(item.arrEtcQty_'	+ i + ',0));');
	        	eval('arrTotQty		+= parseInt(nvl(item.arrTotQty_'	+ i + ',0));');
	        }
	        
//	        console.log(totQty);
	        item.standCurrUnitQty	= arrUnitQty;  //입고수량 - 단위
	        item.standCurrEtcQty    = arrEtcQty;   //입고수량 - 나머지
	        item.standTotQty 		= arrTotQty;   // 총수량
//	        item.standTotQty   		= arrTotQty;   //총입고수량     
    //전체합계 setting - Header명 '입고수량' 부분 (입고수량, 금액, VAT, 합계) - END
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("standMoveRegistCtrl", function (event, data) {
    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    $scope.wjStandMoveRegistLayer.show(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매대이동관리 신규등록 상품 리스트 조회
  $scope.searchStoreMoveRegistList = function () {

    // 파라미터
    var params        = {};
    params.storeCd    = gvStoreCd;
    params.prodCd     = $scope.prodCd;
    params.prodNm     = $scope.prodNm;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid 	 = document.getElementsByName('sessionId')[0].value;
    }
   
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/move/standMove/standMoveRegist/list.sb", params, function () {
    	
    	global_storage_cnt	= 0;	//매장의 창고 갯수

    	var arrProdCd		= new Array( new Array(), new Array() );
        var arrStorageCd	= new Array( new Array(), new Array() );
        var arrStorageNm	= new Array( new Array(), new Array() );
        var arrUnitQty		= new Array( new Array(), new Array() );
        var arrEtcQty		= new Array( new Array(), new Array() );
        var arrTotQty		= new Array( new Array(), new Array() );
//        var arrAmt		= new Array( new Array(), new Array() );
//        var arrVat		= new Array( new Array(), new Array() );
//        var arrTot		= new Array( new Array(), new Array() );
//        var arrCurrQty		= new Array( new Array(), new Array() );
      
        var grid 			= $scope.flex;
        var item;
    	for(var i=0; i<grid.collectionView.items.length; i++){
            item 				= grid.collectionView.items[i];

            arrProdCd[i] 		= item.prodCd;
            arrStorageCd[i] 	= item.arrStorageCd.split("^");
            arrStorageNm[i] 	= item.arrStorageNm.split("^");
            arrUnitQty[i] 		= item.arrUnitQty.split("^");	//입고수량 - 주문딘위
            arrEtcQty[i] 		= item.arrEtcQty.split("^");	//입고수량 - 나머지
            arrTotQty[i] 		= item.arrTotQty.split("^");	//입고수량 - 합계
//            arrAmt[i] 		= item.arrAmt.split("^");		//입고금액
//            arrVat[i] 		= item.arrVat.split("^");		//입고금액 - 부가세
//            arrTot[i] 		= item.arrTot.split("^");		//입고금액 - 합계
//            arrCurrQty[i] 		= item.arrCurrQty.split("^");		//재고수량
    	}

    	global_storage_cnt	= arrStorageCd[0].length;
    	/*
		  	while(grid.columns.length > 14){	//'비고'가 14번째	-> 숨겨져 있는 column도 포함해야 함. -> 아래처럼 변경
		  		grid.columns.removeAt(grid.columns.length-1);
		  	}
		  	*/
		  	while(grid.columns.length > 30){	//이 상세화면이 다시 열리는 경우를 대비하여, 추가된 칼럼 삭제해야 함. ('arrInTot'이 28번재)
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
            		grid.columns.push( new wijmo.grid.Column({header:messages["rtnInstockConfm.dtl.outUnitQty"],binding:"arrUnitQty_"	+ j,	width:50,    align:"right",    isReadOnly:false,	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 주문딘위
                	grid.columns.push( new wijmo.grid.Column({header:messages["rtnInstockConfm.dtl.outUnitQty"],binding:"arrEtcQty_"	+ j,    width:50,    align:"right",    isReadOnly:false,  	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 나머지
                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum"}) );					//입고수량 - 합계
                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", visible:"false"}) );	//입고수량 - 합계
//                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inAmt"],	binding:"arrAmt_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액
//                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inVat"],    binding:"arrVat_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 부가세
//                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTot"],	binding:"arrTot_"		+ j,    width:70,    align:"right",    isReadOnly:true,		aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 합계
            	}
            	
//            	grid.columnHeaders.setCellData(0, 'arrCurrQty_'			+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrUnitQty_'	+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrEtcQty_'		+ j, arrStorageNm[i][j]);
              //grid.columnHeaders.setCellData(0, 'arrTotQty_'	+ j, arrStorageNm[i][j]);
//            	grid.columnHeaders.setCellData(0, 'arrAmt_'		+ j, arrStorageNm[i][j]);
//            	grid.columnHeaders.setCellData(0, 'arrVat_'		+ j, arrStorageNm[i][j]);
//                grid.columnHeaders.setCellData(0, 'arrTot_'		+ j, arrStorageNm[i][j]);
                
//                grid.setCellData(i, 'arrCurrQty_'		+ j,	arrCurrQty[i][j]);
                grid.setCellData(i, 'arrUnitQty_'	+ j,	arrUnitQty[i][j]);
                grid.setCellData(i, 'arrEtcQty_'	+ j,	arrEtcQty	[i][j]);
//                grid.setCellData(i, 'arrAmt_'		+ j,	arrAmt	[i][j]);
//                grid.setCellData(i, 'arrVat_'		+ j,	arrVat	[i][j]);
//                grid.setCellData(i, 'arrTot_'		+ j,	arrTot	[i][j]);

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
    });
  };


  // 저장
  $scope.save = function (confirmFg) {
    var params = [];
    
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.outEtcQty !== null && (parseInt(item.outEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["storeMove.reg.not.etcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }

      item.moveDate   = wijmo.Globalize.format($scope.moveDate.value, 'yyyyMMdd');
      item.slipFg     = $scope.regDlvrFg;
      item.storeCd    = gvStoreCd;
      item.storageCd  = "999";
      item.hqBrandCd  = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.confirmFg  = confirmFg;
      
      var arrUnitQty	= "";	//입고수량 주문단위
      var arrEtcQty		= "";	//입고수량 나머지
      var arrTotQty 	= "";	//입고수량 합계

      for(var k=0; k<global_storage_cnt; k++){
    	  if(k==0){
    		  eval('arrUnitQty	= parseInt(nvl(item.arrUnitQty_'	+ k + ',0));');
    		  eval('arrEtcQty	= parseInt(nvl(item.arrEtcQty_'	+ k + ',0));');
    		  eval('arrTotQty	= parseInt(nvl(item.arrTotQty_'	+ k + ',0));');
    	  }else{
    		  eval('arrUnitQty	+= "^" + parseInt(nvl(item.arrUnitQty_'		+ k + ',0));');
    		  eval('arrEtcQty	+= "^" + parseInt(nvl(item.arrEtcQty_'		+ k + ',0));');
    		  eval('arrTotQty	+= "^" + parseInt(nvl(item.arrTotQty_'		+ k + ',0));');
    	  }
      }
      item.arrUnitQty 	= arrUnitQty;
      item.arrEtcQty 	= arrEtcQty;
      item.arrTotQty 	= arrTotQty;
      
      params.push(item);
    }
    console.log(params);
    $scope._save("/iostock/move/standMove/standMoveRegist/save.sb", params, function () {
      $scope.saveStoreMoveRegistCallback()
    });
  };


  $scope.confirm = function () {
    var msg = messages["storeMove.reg.confirmMsg"]; // 현전표를 확정하시겠습니까?
    s_alert.popConf(msg, function () {
      $scope.save('Y');
    });
  };


  $scope.saveStoreMoveRegistCallback = function () {
//    $scope.wjStoreMoveRegistLayer.hide(true);

//    var storeMoveScope = agrid.getScope('standMoveCtrl');
//    standMoveScope.searchStandMoveList();
  };

}]);
