/** 입고/반출 상품 추가/변경 그리드 controller */
app.controller('vendrInstockProdRegCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrInstockProdRegCtrl', $scope, $http, true));

  $scope._setComboData("safeStockFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["vendrInstock.reg.safeStockY"], "value": "Y"}
  ]);

  $scope._setComboData("addQtyFg", [
    {"name": messages["vendrInstock.reg.addQtyFgApply"], "value": "apply"},
    {"name": messages["vendrInstock.reg.addQtyFgAdd"], "value": "add"}
  ]);
  
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
        if (col.binding === "inEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용
            e.cell.outerHTML = e.cell.outerHTML;
          }
        }
      }
    });
    
    /*
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 원가 및 수량 수정시 합계 계산하여 보여준다.
        if (col.binding === "costUprc" || col.binding === "inUnitQty" || col.binding === "inEtcQty") {
          var item = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
        }
      }

      s.collectionView.commitEdit();
    });
	*/
    s.cellEditEnded.addHandler(function (s, e) {
        if (e.panel === s.cells) {
            var col = s.columns[e.col];
            var str = col.binding;
            var idx = 0;

            if(str.indexOf('arr') != -1){	//입고수량 수정시
            	idx = str.lastIndexOf('_');
            	/*
                console.log('col: ' + col);
                console.log('col: ' + col.binding);
                console.log('col: ' + col.binding.indexOf('arr'));
            	console.log('lastIndexOf: ' + idx );
            	console.log(str.substring(0,idx) + ' & ' + str.substring(idx+1) );
				*/
                var item = s.rows[e.row].dataItem;
                $scope.calcAmt(item, str.substring(idx+1));
            }
        }
        s.collectionView.commitEdit();
    });
    
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
//    s.allowMerging  = 2;
//    s.itemFormatter = function (panel, r, c, cell) {
//      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
//        //align in center horizontally and vertically
//        panel.rows[r].allowMerging    = true;
//        panel.columns[c].allowMerging = true;
//        wijmo.setCss(cell, {
//          display    : 'table',
//          tableLayout: 'fixed'
//        });
//        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
//        wijmo.setCss(cell.children[0], {
//          display      : 'table-cell',
//          verticalAlign: 'middle',
//          textAlign    : 'center'
//        });
//      }
//      // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
//      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
//        // GroupRow 인 경우에는 표시하지 않는다.
//        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
//          cell.textContent = '';
//        } else {
//          if (!isEmpty(panel._rows[r]._data.rnum)) {
//            cell.textContent = (panel._rows[r]._data.rnum).toString();
//          } else {
//            cell.textContent = (r + 1).toString();
//          }
//        }
//      }
//      // readOnly 배경색 표시
//      else if (panel.cellType === wijmo.grid.CellType.Cell) {
//        var col = panel.columns[c];
//        if (col.isReadOnly || panel.grid.isReadOnly) {
//          wijmo.addClass(cell, 'wj-custom-readonly');
//        }
//      }
//    }
    
    
  //Grid Header 2줄 - START	----------------------------------------------------------------
    //s.allowMerging = 'ColumnHeaders';
      s.allowMerging = 2;	//2:ColumnHeaders, 6:AllHeaders
      s.columnHeaders.rows.push(new wijmo.grid.Row());
      
      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.prodCd         = messages["vendrInstock.reg.prodCd"         ];	//상품코드
          dataItem.prodNm         = messages["vendrInstock.reg.prodNm"         ];	//상품명
          dataItem.lastCostUprc   = messages["vendrInstock.reg.lastCostUprc"   ];	//최종원가
          dataItem.poUnitFg       = messages["vendrInstock.reg.poUnitFg"       ];   //주문단위
          dataItem.poUnitQty      = messages["vendrInstock.reg.poUnitQty"      ];   //입수
          dataItem.prevInUnitQty  = messages["vendrInstock.reg.prevInUnitQty"  ];   //기수량
          dataItem.prevInEtcQty   = messages["vendrInstock.reg.prevInUnitQty"  ];   //기수량          
          dataItem.costUprc       = messages["vendrInstock.reg.costUprc"       ];	//원가단가(VAT포함)
          dataItem.inUnitQty      = messages["vendrInstock.reg.inUnitQty"      ];	//수량   
          dataItem.inEtcQty       = messages["vendrInstock.reg.inUnitQty"      ];	//수량   
          dataItem.prevInTotQty   = messages["vendrInstock.reg.prevInTotQty"   ];	//이전총수량
          dataItem.inTotQty       = messages["vendrInstock.reg.inTotQty"       ];	//총수량
          dataItem.inAmt          = messages["vendrInstock.reg.inAmt"          ];	//금액
          dataItem.inVat          = messages["vendrInstock.reg.inVat"          ];	//VAT
          dataItem.inTot          = messages["vendrInstock.reg.inTot"          ];	//합계(VAT포함)
          dataItem.vatFg          = messages["vendrInstock.reg.vatFg"          ];	//상품부가세구분
          dataItem.vendrVatFg01   = messages["vendrInstock.reg.vendrVatFg01"   ];	//부가세포함여부
          dataItem.storeSplyUprc  = messages["vendrInstock.reg.storeSplyUprc"  ];	//매장공급가

      s.columnHeaders.rows[0].dataItem = dataItem;
  //Grid Header 2줄 - END		----------------------------------------------------------------

  };


  $scope.calcAmt_OLD = function (item) {
    /** 수량이 없는 경우 계산하지 않음.
        null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 */
    if (nvl(item.inUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.inEtcQty, null) === null)) return false;

    var costUprc     = parseFloat(item.costUprc);
    var poUnitQty    = parseInt(item.poUnitQty);
    var vat01        = parseInt(item.vatFg01);
    var vendrVatFg01 = parseInt(item.vendrVatFg01);

    var unitQty = (parseInt(nvl(item.prevOrderUnitQty, 0)) + parseInt(nvl(item.inUnitQty, 0))) * parseInt(item.poUnitQty);
    var etcQty  = parseInt(nvl(item.prevOrderEtcQty, 0)) + parseInt(nvl(item.inEtcQty, 0));
    var totQty  = parseInt(unitQty + etcQty);
    var tempAmt = Math.round(totQty * costUprc / poUnitQty);
    var inAmt   = tempAmt - Math.round(tempAmt * vat01 * vendrVatFg01 / 11);
    var inVat   = Math.round(tempAmt * vat01 / (10 + vendrVatFg01));
    var inTot   = parseInt(inAmt + inVat);

    item.inTotQty = totQty;   // 총주문수량
    item.inAmt    = inAmt; // 금액
    item.inVat    = inVat; // VAT
    item.inTot    = inTot; // 합계
  };

  $scope.calcAmt = function (item, idx) {
	  
	  var costUprc     = parseFloat(item.costUprc);
	  var poUnitQty    = parseInt(item.poUnitQty);
	  var vat01        = parseInt(item.vatFg01);
	  var vendrVatFg01 = parseInt(item.vendrVatFg01);
	    
//      var outSplyUprc = parseInt(item.outSplyUprc	);
//      var poUnitQty   = parseInt(item.poUnitQty	);
//      var vat01       = parseInt(item.vatFg01		);
//      var envst0011   = parseInt(item.envst0011	);
	  
//	  var unitQty = (parseInt(nvl(item.prevOrderUnitQty, 0)) + parseInt(nvl(item.inUnitQty, 0))) * parseInt(item.poUnitQty);
//	    var etcQty  = parseInt(nvl(item.prevOrderEtcQty, 0)) + parseInt(nvl(item.inEtcQty, 0));
//	    var totQty  = parseInt(unitQty + etcQty);
//	    var tempAmt = Math.round(totQty * costUprc / poUnitQty);
//	    var inAmt   = tempAmt - Math.round(tempAmt * vat01 * vendrVatFg01 / 11);
//	    var inVat   = Math.round(tempAmt * vat01 / (10 + vendrVatFg01));
//	    var inTot   = parseInt(inAmt + inVat);	  
	  
//      var unitQty     = (parseInt(nvl(item.prevInUnitQty, 0))	+ parseInt(nvl(eval('item.arrInUnitQty_' + idx), 0))) * parseInt(item.poUnitQty);
//      var etcQty      = parseInt(nvl(item.prevInEtcQty, 0))		+ parseInt(nvl(eval('item.arrInEtcQty_'  + idx), 0));
      
      var unitQty     = parseInt(nvl(eval('item.arrInUnitQty_' + idx), 0)) * parseInt(item.poUnitQty);
      var etcQty      = parseInt(nvl(eval('item.arrInEtcQty_'  + idx), 0));      
      console.log('unitQty: ' + unitQty);
      console.log('etcQty : ' + etcQty );
      var totQty      = parseInt(unitQty + etcQty);
      var tempAmt     = Math.round(totQty * costUprc / poUnitQty);
      var inAmt       = tempAmt - Math.round(tempAmt * vat01 * vendrVatFg01 / 11);
      var inVat       = Math.round(tempAmt * vat01 / (10 + vendrVatFg01));
      var inTot       = parseInt(inAmt + inVat);
//      console.log('inAmt: ' + inAmt);
//      console.log('inVat : ' + inVat );
//      console.log('inTot : ' + inTot );
      eval('item.arrInTotQty_'+ idx + ' = totQty;');	//총입고수량
      eval('item.arrInAmt_' 	+ idx + ' = inAmt;'	); 	//금액
      eval('item.arrInVat_' 	+ idx + ' = inVat;'	); 	//VAT
      eval('item.arrInTot_'	+ idx + ' = inTot;'	);	//합계

      //전체합계 setting - Header명 '입고수량' 부분 (입고수량, 금액, VAT, 합계) - START
	        //console.log('global_storage_cnt:' + global_storage_cnt);
	        var arrInUnitQty= 0;
	        var arrInEtcQty	= 0;
	        var arrInTotQty = 0;
	        var arrInAmt	= 0;
	        var arrInVat	= 0;
	        var arrInTot	= 0;

	        for(var i=0; i<global_storage_cnt; i++){
	        	eval('arrInUnitQty	+= parseInt(nvl(item.arrInUnitQty_'	+ i + ',0));');
	        	eval('arrInEtcQty	+= parseInt(nvl(item.arrInEtcQty_'	+ i + ',0));');
	            eval('arrInTotQty	+= parseInt(nvl(item.arrInTotQty_'	+ i + ',0));');
	        	eval('arrInAmt		+= parseInt(nvl(item.arrInAmt_'		+ i + ',0));');
	        	eval('arrInVat		+= parseInt(nvl(item.arrInVat_'		+ i + ',0));');
	            eval('arrInTot		+= parseInt(nvl(item.arrInTot_'		+ i + ',0));');			
	        }
	        console.log("calcamt arrInUnitQty::"+arrInUnitQty);
	        console.log("calcamt arrInEtcQty::"+arrInEtcQty);
	        console.log("calcamt arrInTotQty::"+arrInTotQty);
	        item.inUnitQty	= arrInUnitQty; //입고수량 - 단위
	        item.inEtcQty   = arrInEtcQty;  //입고수량 - 나머지
	        item.inTotQty   = arrInTotQty;  //총입고수량
	        item.inAmt      = arrInAmt;		//금액
	        item.inVat      = arrInVat;    	//VAT
	        item.inTot      = arrInTot;    	//합계

	        //console.log(idx + ': ' +  item.outTotQty  + ' & ' +  item.inTotQty);
	    //전체합계 setting - Header명 '입고수량' 부분 (입고수량, 금액, VAT, 합계) - END

	    //$scope.flex.collectionView.commitEdit();
  };
  //$scope.calcAmt		--------------------------------------------------------------------------------------------------------------------------
  

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrInstockProdRegCtrl", function (event, data) {

    if (!$.isEmptyObject(data)) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.slipNo  = data.slipNo;
      $scope.slipFg  = data.slipFg;
      $scope.vendrCd = data.vendrCd;
      
      // 값 초기화
      $scope.prodClassCdNm = messages["cmm.all"];
      $scope.prodClassCd   = '';
      $scope.storeSplyChk  = false;

      $scope.wjVendrInstockProdRegLayer.show(true);
    }
    // 페이징처리에서 broadcast 호출시
    else {
      $scope.searchVendrInstockRegList();
    }
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 조회버튼 클릭으로 조회시
  $scope.fnSearch = function () {
    $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
    $scope.searchVendrInstockRegList();
  };


  // 상품 리스트 조회
  $scope.searchVendrInstockRegList = function () {
    // 파라미터
    var params       = {};
    params.slipNo    = $scope.slipNo;
    params.slipFg    = $scope.slipFg;
    params.vendrCd   = $scope.vendrCd;
    params.listScale = 50;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/vendr/vendrInstock/vendrInstockProdReg/list.sb", params, function () {
    	global_storage_cnt	= 0;	//매장의 창고 갯수

    	var arrProdCd		= new Array( new Array(), new Array() );
        var arrStorageCd	= new Array( new Array(), new Array() );
        var arrStorageNm	= new Array( new Array(), new Array() );
        var arrInUnitQty	= new Array( new Array(), new Array() );
        var arrInEtcQty		= new Array( new Array(), new Array() );
        var arrInTotQty		= new Array( new Array(), new Array() );
        var arrInAmt		= new Array( new Array(), new Array() );
        var arrInVat		= new Array( new Array(), new Array() );
        var arrInTot		= new Array( new Array(), new Array() );

        var grid 			= $scope.flex;
        var item;
    	for(var i=0; i<grid.collectionView.items.length; i++){
    		
            item 			= grid.collectionView.items[i];

            arrProdCd   [i] = item.prodCd;
            arrStorageCd[i] = item.arrStorageCd .split("^");
            arrStorageNm[i] = item.arrStorageNm .split("^");
            arrInUnitQty[i] = item.arrInUnitQty .split("^");	//입고수량 - 주문딘위
            arrInEtcQty	[i] = item.arrInEtcQty  .split("^");	//입고수량 - 나머지
            arrInTotQty	[i] = item.arrInTotQty  .split("^");	//입고수량 - 합계
            arrInAmt    [i] = item.arrInAmt     .split("^");	//입고금액
            arrInVat	[i] = item.arrInVat	    .split("^");	//입고금액 - 부가세
            arrInTot	[i] = item.arrInTot	    .split("^");	//입고금액 - 합계
    	}

    	global_storage_cnt	= arrStorageCd[0].length;
    	/*
		  	while(grid.columns.length > 14){	//'비고'가 14번째	-> 숨겨져 있는 column도 포함해야 함. -> 아래처럼 변경
		  		grid.columns.removeAt(grid.columns.length-1);
		  	}
		  	*/
		  	while(grid.columns.length > 18){	//이 상세화면이 다시 열리는 경우를 대비하여, 추가된 칼럼 삭제해야 함. ('arrInTot'이 28번재)
		  		grid.columns.removeAt(grid.columns.length-1);
		  	}

        for(var i=0; i<arrStorageCd.length; i++){
        	for(var j=0; j<arrStorageCd[i].length; j++){
        		/*
            	console.log(i + '-' + j +
            			' Prod:'		+ arrProdCd   	[i]		+
            			' & Cd:'    	+ arrStorageCd	[i][j]	+
                        ' & Nm:' 		+ arrStorageNm	[i][j]  +
                        ' & UnitQty:' 	+ arrInUnitQty	[i][j]  +
                        ' & EtcQty:'  	+ arrInEtcQty	[i][j]  +
                        ' & TotQty:'  	+ arrInTotQty	[i][j]  +
                        ' & Amt:'     	+ arrInAmt		[i][j]  +
                        ' & Vat:'     	+ arrInVat		[i][j]  +
                        ' & Tot:'     	+ arrInTot		[i][j]  );
				*/
            	if(i == 0){
            		//입고수량, 금액, VAT, 합계
            		grid.columns.push( new wijmo.grid.Column({header:messages["vendrInstock.reg.inEtcQty"],binding:"arrInUnitQty_"	+ j,	width:50,    align:"right",    isReadOnly:false,	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 주문딘위
                	grid.columns.push( new wijmo.grid.Column({header:messages["vendrInstock.reg.inEtcQty"],binding:"arrInEtcQty_"	+ j,    width:50,    align:"right",    isReadOnly:false,  	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 나머지
                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrInTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum"}) );					//입고수량 - 합계
                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrInTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", visible:"false"}) );	//입고수량 - 합계
                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inAmt"],	binding:"arrInAmt_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액
                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inVat"],    binding:"arrInVat_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 부가세
                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTot"],	binding:"arrInTot_"		+ j,    width:70,    align:"right",    isReadOnly:true,		aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 합계
            	}

            	grid.columnHeaders.setCellData(0, 'arrInUnitQty_'	+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrInEtcQty_'	+ j, arrStorageNm[i][j]);
              //grid.columnHeaders.setCellData(0, 'arrInTotQty_'	+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrInAmt_'		+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrInVat_'		+ j, arrStorageNm[i][j]);
                grid.columnHeaders.setCellData(0, 'arrInTot_'		+ j, arrStorageNm[i][j]);

//                grid.setCellData(i, 'arrInUnitQty_'	+ j,	arrInUnitQty[i][j]);
//                grid.setCellData(i, 'arrInEtcQty_'	+ j,	arrInEtcQty	[i][j]);
//                grid.setCellData(i, 'arrInAmt_'		+ j,	arrInAmt	[i][j]);
//                grid.setCellData(i, 'arrInVat_'		+ j,	arrInVat	[i][j]);
//                grid.setCellData(i, 'arrInTot_'		+ j,	arrInTot	[i][j]);

        	}	//for(var j=0; j<arrStorageCd[i].length; j++){

            	/*
            	[저장]을 한 후 [확정]을 함께 하는 경우에는 상관이 없으나,
            	[저장]이후 화면을 닫은 후 다시 화면을 열어 [확정]만 하는 경우에는  'inTotQty' 값이 setting이 되지않아서 문제가 생길 수 있음.
            	 고로 상세페이지 열리는 경우에 금액계산하는 부분 호출함.	==> 2020.04.14: for문에서 제외하고  아래로 옮김(Grid의 행갯수만큼 $scope.calcAmt 실행)

				$scope.calcAmt($scope.flex.collectionView.items[i], i);
            	*/
        }	//for(var i=0; i<arrStorageCd.length; i++){

        for(var i=0; i<grid.collectionView.items.length; i++){
    		//console.log('$scope.calcAmt - Before: ' + i);
//        	$scope.calcAmt($scope.flex.collectionView.items[i], i);
        	//console.log('$scope.calcAmt - After : ' + i);
        }
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


  // 상품 저장
  $scope.save = function () {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      // 이전 주문수량이 없으면서 주문수량 0인 경우 저장하지 않는다.
      if (item.prevInTotQty === null && item.inTotQty === 0) {
        continue;
      }
      if (item.inEtcQty !== null && (parseInt(item.inEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["vendrInstock.reg.not.inEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
        $scope._popMsg(messages["vendrInstock.reg.not.overInTot"]); // 주문금액이 너무 큽니다.
        return false;
      }

      item.status      = "U";
      item.slipNo      = $scope.slipNo;
      item.slipFg      = $scope.slipFg;
      item.storageCd   = "999";			//001 -> 999
      item.hqBrandCd   = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.storeSplyFg = ($scope.storeSplyChk === true ? 'Y' : 'N');
      
    //TB_PO_HQ_STORE_OUTSTOCK_PROD - START
  	//console.log('params.push(item)Before : ' + JSON.stringify(item) );
		var arrInUnitQty	= "";	//입고수량 주문단위
		var arrInEtcQty		= "";	//입고수량 나머지
		var arrInTotQty 	= "";	//입고수량 합계
		var arrInAmt		= "";	//입고금액
		var arrInVat		= "";	//입고금액 부가세
		var arrInTot		= "";	//입고금액 합계

		for(var k=0; k<global_storage_cnt; k++){
			if(k==0){
				eval('arrInUnitQty	= parseInt(nvl(item.arrInUnitQty_'	+ k + ',0));');
				eval('arrInEtcQty	= parseInt(nvl(item.arrInEtcQty_'	+ k + ',0));');
				eval('arrInTotQty	= parseInt(nvl(item.arrInTotQty_'	+ k + ',0));');
				eval('arrInAmt		= parseInt(nvl(item.arrInAmt_'		+ k + ',0));');
				eval('arrInVat		= parseInt(nvl(item.arrInVat_'		+ k + ',0));');
				eval('arrInTot		= parseInt(nvl(item.arrInTot_'		+ k + ',0));');
			}else{
				eval('arrInUnitQty 	+= "^" + parseInt(nvl(item.arrInUnitQty_'	+ k + ',0));');
				eval('arrInEtcQty	+= "^" + parseInt(nvl(item.arrInEtcQty_'	+ k + ',0));');
				eval('arrInTotQty	+= "^" + parseInt(nvl(item.arrInTotQty_'	+ k + ',0));');
				eval('arrInAmt		+= "^" + parseInt(nvl(item.arrInAmt_'		+ k + ',0));');
				eval('arrInVat		+= "^" + parseInt(nvl(item.arrInVat_'		+ k + ',0));');
				eval('arrInTot		+= "^" + parseInt(nvl(item.arrInTot_'		+ k + ',0));');
			}
		}
		item.arrInUnitQty 	= arrInUnitQty;
		item.arrInEtcQty 	= arrInEtcQty;
		item.arrInTotQty 	= arrInTotQty;
		item.arrInAmt 		= arrInAmt;
		item.arrInVat		= arrInVat;
		item.arrInTot 		= arrInTot;
		//console.log('params.push(item)After  : ' + JSON.stringify(item) );
	//TB_PO_HQ_STORE_OUTSTOCK_PROD - END
		
      params.push(item);
    }

    $scope._save("/iostock/vendr/vendrInstock/vendrInstockProdReg/save.sb", params, function () {
      $scope.saveRegistCallback()
    });
  };


  // 저장 후 콜백 서치 함수
  $scope.saveRegistCallback = function () {
    $scope.searchVendrInstockRegList();

    var vendrInstockProdScope = agrid.getScope('vendrInstockProdCtrl');
    vendrInstockProdScope.procFgCheck();
  };


  $scope.setLastCostToCostUprc = function () {
    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
    // 데이터 처리중 팝업 띄우기위해 $timeout 사용.
    $timeout(function () {
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.lastCostUprc !== null) {
          $scope.flex.collectionView.editItem(item);

          if (nvl(item.lastCostUprc, 0) > 0) {
            item.costUprc = parseInt(item.lastCostUprc);
          }
          $scope.calcAmt(item);
          $scope.flex.collectionView.commitEdit();
        }
      }
      $scope.$broadcast('loadingPopupInactive');
    }, 100);
  };


  // 상품분류정보 팝업
  $scope.popUpProdClass = function () {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd    = scope.getSelectedClass();
        var params         = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function (response) {
            $scope.prodClassCd   = prodClassCd;
            $scope.prodClassCdNm = response.data.data;
          }
        );
      }
    });
  };


  // 엑셀 다운로드
  $scope.excelDownload = function () {
    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, 'excel.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };


  /** 엑셀업로드 관련 공통 함수 */
  $scope.excelTextUpload = function (prcsFg) {
    var excelUploadScope = agrid.getScope('excelUploadMPSCtrl');
    /** 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐. */
    var uploadFg = 'vendr';

    // 엑셀 양식다운로드
    if (prcsFg === 'excelFormDown') {
      excelUploadScope.excelFormDownload(uploadFg);
    }
    else{
      var msg = messages["excelUploadMPS.confmMsg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
      s_alert.popConf(msg, function () {
        excelUploadScope.uploadFg   = uploadFg;
        /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
        excelUploadScope.parentCtrl = 'vendrInstockProdRegCtrl';
        // 엑셀 업로드
        if (prcsFg === 'excelUp') {
          $("#excelUpFile").val('');
          $("#excelUpFile").trigger('click');
        }
        // 텍스트 업로드
        else if (prcsFg === 'textUp') {
          $("#textUpFile").val('');
          $("#textUpFile").trigger('click');
        }
      });
    }
  };


  /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
  $scope.uploadCallBack = function () {
    var params      = {};
    params.slipNo   = $scope.slipNo;
    params.slipFg   = $scope.slipFg;
    params.vendrCd  = $scope.vendrCd;
    params.addQtyFg = $scope.addQtyFg;

    var excelUploadScope = agrid.getScope('excelUploadMPSCtrl');

    $http({
      method : 'POST', //방식
      url    : '/iostock/vendr/vendrInstock/vendrInstockProdReg/excelUpload.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 엑셀 에러내역 팝업 호출
        $scope.excelUploadErrInfo();

        // 등록 그리드, 부모 그리드 조회
        $scope.saveRegistCallback();
      }
    }, function errorCallback(response) {
      $scope._popMsg(response.data.message);
      return false;
    }).then(function () {
      excelUploadScope.excelUploadingPopup(false); // 업로딩 팝업 닫기
    });
  };


  // 에러내역 팝업 호출
  $scope.excelUploadErrInfo = function () {
    var params      = {};
    params.uploadFg = 'vendr';
    params.vendrCd  = $scope.vendrCd;
    $scope._broadcast('excelUploadMPSErrInfoCtrl', params);
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
