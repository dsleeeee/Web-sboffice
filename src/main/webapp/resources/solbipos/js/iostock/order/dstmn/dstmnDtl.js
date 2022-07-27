/** 거래명세표 상세 그리드 controller */
app.controller('dstmnDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dstmnDtlCtrl', $scope, $http, true));

  $scope.dtlOutDate = wcombo.genDate("#dtlOutDate");

  $scope._setComboData("stmtAcctFg", [
    {"name": messages["outstockConfm.dtl.stmtAcctAll"], "value": ""},
    {"name": messages["outstockConfm.dtl.stmtAcctSplr"], "value": "1"},
    {"name": messages["outstockConfm.dtl.stmtAcctSplrRcpnt"], "value": "2"}
  ]);

  var global_storage_cnt = 0;	//매장의 창고 갯수

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    // 배송기사
    var comboParams             = {};
    var url = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchDtlDlvrCd", null, url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    comboParams         = {};
    comboParams.nmcodeGrpCd = "093";
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
    /*
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "outSplyUprc" || col.binding === "outUnitQty" || col.binding === "outEtcQty") { // 출고수량 수정시
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

  //Grid Header 2줄 - START	----------------------------------------------------------------
    //s.allowMerging = 'ColumnHeaders';
      s.allowMerging = 2;	//2:ColumnHeaders, 6:AllHeaders
      s.columnHeaders.rows.push(new wijmo.grid.Row());

      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.slipNo         = messages["outstockConfm.dtl.slipNo"        ];	//전표번호
          dataItem.slipFg         = messages["outstockConfm.dtl.slipFg"        ];	//전표구분
          dataItem.slipKind       = messages["outstockConfm.dtl.slipKind"      ];	//전표구분
          dataItem.seq            = messages["outstockConfm.dtl.seq"           ];	//순번
          dataItem.storeCd        = messages["outstockConfm.dtl.storeCd"       ];	//매장코드
          dataItem.prodCd         = messages["outstockConfm.dtl.prodCd"        ];	//상품코드
          dataItem.prodNm         = messages["outstockConfm.dtl.prodNm"        ];	//상품명
          dataItem.barcdCd        = messages["outstockConfm.dtl.barcdCd"       ];	//바코드
          dataItem.poUnitFg       = messages["outstockConfm.dtl.poUnitFg"      ];  //주문단위
          dataItem.poUnitQty      = messages["outstockConfm.dtl.poUnitQty"     ];  //입수
          dataItem.outSplyUprc    = messages["outstockConfm.dtl.outSplyUprc"   ];  //공급가

          dataItem.outUnitQty     = messages["outstockConfm.dtl.outUnitQty"    ];  //출고수량
          dataItem.outEtcQty      = messages["outstockConfm.dtl.outUnitQty"    ];  //출고수량
          dataItem.outTotQty      = messages["outstockConfm.dtl.outUnitQty"    ];  //출고수량

          dataItem.outAmt         = messages["outstockConfm.dtl.outAmt"        ];  //금액
          dataItem.outVat         = messages["outstockConfm.dtl.outVat"        ];  //VAT
          dataItem.outTot         = messages["outstockConfm.dtl.outTot"        ];  //합계
          dataItem.remark         = messages["outstockConfm.dtl.remark"        ];  //비고
          dataItem.vatFg01        = messages["outstockConfm.dtl.vatFg"         ];  //상품부가세구분
          dataItem.envst0011      = messages["outstockConfm.dtl.envst0011"     ];  //출고가-부가세포함여부

          dataItem.arrStorageCd   = "";
          dataItem.arrStorageNm   = "";
          dataItem.arrCurrQty     = "";
          dataItem.arrInUnitQty   = "";
          dataItem.arrInEtcQty    = "";
          dataItem.arrInTotQty    = "";
          dataItem.arrInAmt       = "";
          dataItem.arrInVat       = "";
          dataItem.arrInTot       = "";



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

  // 금액 계산
  $scope.calcAmt_OLD = function (item) {
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
  $scope.calcAmt = function (item, idx) {
  	//$scope.flex.collectionView.editItem(item);

      var outSplyUprc = parseInt(item.outSplyUprc	);
      var poUnitQty   = parseInt(item.poUnitQty	);
      var vat01       = parseInt(item.vatFg01		);
      var envst0011   = parseInt(item.envst0011	);

      var unitQty     = parseInt(nvl(eval('item.arrInUnitQty_' + idx), 0)) * parseInt(item.poUnitQty);
      var etcQty      = parseInt(nvl(eval('item.arrInEtcQty_'  + idx), 0));
      //console.log('etcQty : ' + etcQty );
      var totQty      = parseInt(unitQty + etcQty);
      var tempAmt     = Math.round(totQty * outSplyUprc / poUnitQty);
//      var inAmt       = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
//      var inVat       = Math.round(tempAmt * vat01 / (10 + envst0011));
//      var inTot       = parseInt(inAmt + inVat);
      var outAmt  		= tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
      var outVat  		= Math.round(tempAmt * vat01 / (10 + envst0011));
      var outTot  		= parseInt(outAmt + outVat);

      eval('item.arrInTotQty_'+ idx + ' = totQty;');	//총입고수량
      eval('item.arrInAmt_' 	+ idx + ' = outAmt;'	); 	//금액
      eval('item.arrInVat_' 	+ idx + ' = outVat;'	); 	//VAT
      eval('item.arrInTot_'	+ idx + ' = outTot;'	);	//합계

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
				/*
              console.log(item.prodCd + '[' + i + '] ' + eval('parseInt(nvl(item.arrInUnitQty_'	+ i + ',0))')	);
              console.log(item.prodCd + '[' + i + '] ' + eval('parseInt(nvl(item.arrInEtcQty_'	+ i + ',0))')	);
              console.log(item.prodCd + '[' + i + '] ' + eval('parseInt(nvl(item.arrInTotQty_'	+ i + ',0))')	);
              console.log(item.prodCd + '[' + i + '] ' + eval('parseInt(nvl(item.arrInAmt_'		+ i + ',0))')	);
              console.log(item.prodCd + '[' + i + '] ' + eval('parseInt(nvl(item.arrInVat_'		+ i + ',0))')	);
              console.log(item.prodCd + '[' + i + '] ' + eval('parseInt(nvl(item.arrInTot_'		+ i + ',0))')	);
				*/
	        }
	        /*
	        var unitQty     = parseInt(nvl(item.inUnitQty, 0)) * parseInt(item.poUnitQty);
	        var etcQty      = parseInt(nvl(item.inEtcQty,  0));
	        var totQty      = parseInt(unitQty + etcQty);

	        var tempAmt     = Math.round(totQty * outSplyUprc / poUnitQty);
	        var inAmt       = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
	        var inVat       = Math.round(tempAmt * vat01 / (10 + envst0011));
	        var inTot       = parseInt(inAmt + inVat);

	        item.inTotQty   = totQty;   //총입고수량
	        item.inAmt      = inAmt;    //금액
	        item.inVat      = inVat;    //VAT
	        item.inTot      = inTot;    //합계
	        ----------------------------------------------------------------------------------------
	        item.inUnitQty	= arrInUnitQty; //입고수량 - 단위
	        item.inEtcQty   = arrInEtcQty;  //입고수량 - 나머지
	        item.inTotQty   = arrInTotQty;  //총입고수량
	        item.inAmt      = arrInAmt;		//금액
	        item.inVat      = arrInVat;    	//VAT
	        item.inTot      = arrInTot;    	//합계
	        ----------------------------------------------------------------------------------------
	        item.inUnitQty	= arrInUnitQty * parseInt(item.poUnitQty);					//입고수량 - 단위
	        item.inEtcQty   = arrInEtcQty;  											//입고수량 - 나머지
	        item.inTotQty   = parseInt(arrInUnitQty + arrInEtcQty);  					//총입고수량

	        tempAmt     	= Math.round( parseInt(arrInUnitQty + arrInEtcQty) * outSplyUprc / poUnitQty);

	        item.inAmt      = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);	//금액
	        item.inVat      = Math.round(          tempAmt * vat01 / (10 + envst0011));	//VAT
	        item.inTot      = parseInt(item.inAmt + item.inVat);    					//합계
	        */
	        item.outUnitQty	 = arrInUnitQty; //입고수량 - 단위
	        item.outEtcQty   = arrInEtcQty;  //입고수량 - 나머지
	        item.outTotQty   = arrInTotQty;  //총입고수량
	        item.outAmt      = arrInAmt;		//금액
	        item.outVat      = arrInVat;    	//VAT
	        item.outTot      = arrInTot;    	//합계

	        //console.log(idx + ': ' +  item.outTotQty  + ' & ' +  item.inTotQty);
	    //전체합계 setting - Header명 '입고수량' 부분 (입고수량, 금액, VAT, 합계) - END

	    //$scope.flex.collectionView.commitEdit();
  };
  //$scope.calcAmt		--------------------------------------------------------------------------------------------------------------------------

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dstmnDtlCtrl", function (event, data) {
	// 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;
    $scope.startDate = data.startDate;
	$scope.endDate = data.endDate;
    $scope.slipNo = data.slipNo;
    $scope.slipFg = data.slipFg;
    $scope.vendrCd = data.vendrCd;
    $scope.wjDstmnDtlLayer.show(true);


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
      url    : '/iostock/order/outstockConfm/outstockConfmDtl/getSlipNoInfo.sb', /* 통신할 URL */
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
          $scope.dlvrCd           = nvl(response.data.data.dlvrCd, ''); // 값이 null 인 경우 선택이 select 될수 있도록 nvl 처리함.
          $scope.dlvrNm           = response.data.data.dlvrNm;

          // 수주확정
          if ($scope.procFg === "10") {
            $("#infoSlipNo").html($scope.slipNo);
            $("#infoStoreNm").html($scope.storeNm);
            $("#thOutDate").html(messages["outstockConfm.dtl.reqDate"]);
            $("#infoOutDate").html($scope.outDate !== null ? getFormatDate($scope.outDate) : '');
            $("#infoInDate").html('');

            $("#lblTitle").text(messages["outstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["outstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["outstockConfm.dtl.reqDate"]+' : ' + getFormatDate($scope.outDate));
            $("#outstockBtnLayer").show();
            $scope.spanOutstockConfirmFg   = false;
            $scope.btnSetOutToIn		   = false;
            $scope.btnDtlSave              = false;
            $scope.btnOutstockAfterDtlSave = false;
            $scope.flex.isReadOnly         = true;
          }
          // 출고확정 또는 입고확정
          else if ($scope.procFg === "20" || $scope.procFg === "30") {
            $("#outstockBtnLayer").hide();
            $scope.spanOutstockConfirmFg   = false;
            $scope.btnSetOutToIn		   = false;
            $scope.btnDtlSave              = false;
            $scope.btnOutstockAfterDtlSave = false;
            $scope.flex.isReadOnly         = true;

            // 출고확정
            if ($scope.procFg === "20") {
              $("#infoSlipNo").html($scope.slipNo);
              $("#infoStoreNm").html($scope.storeNm);
              $("#thOutDate").html(messages["outstockConfm.dtl.outDate"]);
              $("#infoOutDate").html($scope.outDate !== null ? getFormatDate($scope.outDate) : '');
              $("#infoInDate").html('');

              $("#lblTitle").text(messages["outstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["outstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["outstockConfm.dtl.outDate"]+' : ' + getFormatDate($scope.outDate));
            }
            // 입고확정
            else if ($scope.procFg === "30") {
              $("#infoSlipNo").html($scope.slipNo);
              $("#infoStoreNm").html($scope.storeNm);
              $("#thOutDate").html(messages["outstockConfm.dtl.outDate"]);
              $("#infoOutDate").html($scope.outDate !== null ? getFormatDate($scope.outDate) : '');
              $("#infoInDate").html($scope.inDate !== null ? getFormatDate($scope.inDate) : '');

              $("#lblTitle").text(messages["outstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["outstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["outstockConfm.dtl.outDate"]+' : ' + getFormatDate($scope.outDate) + ', '+messages["outstockConfm.dtl.inDate"]+' : ' + getFormatDate($scope.inDate));
            }
          }

          $scope.searchOutstockConfmDtlList();
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

  var arrProdCd		= new Array( new Array(), new Array() );
  var arrStorageCd	= new Array( new Array(), new Array() );
  var arrStorageNm	= new Array( new Array(), new Array() );
//  var arrCurrQty		= new Array( new Array(), new Array() );
  var arrInUnitQty	= new Array( new Array(), new Array() );
  var arrInEtcQty		= new Array( new Array(), new Array() );
  var arrInTotQty		= new Array( new Array(), new Array() );
  var arrInAmt		= new Array( new Array(), new Array() );
  var arrInVat		= new Array( new Array(), new Array() );
  var arrInTot		= new Array( new Array(), new Array() );

  // 출고확정 상세내역 리스트 조회
  $scope.searchOutstockConfmDtlList = function () {
    // 파라미터
    var params    = {};
    params.slipNo = $scope.slipNo;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/order/outstockConfm/outstockConfmDtl/list.sb", params, function () {

    	global_storage_cnt	= 0;	//매장의 창고 갯수


        var grid 			= $scope.flex;
        var item;

        var outUnitQty 	= new Array();
        var outEtcQty	= new Array();

        for(var i=0; i<grid.collectionView.items.length; i++){
    		item 			= grid.collectionView.items[i];

    		outUnitQty	[i] = item.outUnitQty;
            outEtcQty	[i] = item.outEtcQty;

    		arrProdCd   [i] = item.prodCd;
            arrStorageCd[i] = item.arrStorageCd .split("^");
            arrStorageNm[i] = item.arrStorageNm .split("^");
//            arrCurrQty[i] 	= item.arrCurrQty   .split("^");	//현재고수량 - 주문딘위
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
		  	while(grid.columns.length > 29){	//이 상세화면이 다시 열리는 경우를 대비하여, 추가된 칼럼 삭제해야 함. ('arrInTot'이 28번재)
		  		grid.columns.removeAt(grid.columns.length-1);
		  	}

        for(var i=0; i<arrStorageCd.length; i++){
        	$scope.flex.collectionView.editItem(item);

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

//            		grid.columns.push( new wijmo.grid.Column({header:messages["outstockConfm.dtl.currQty"],binding:"arrCurrQty_"	+ j,	width:80,    align:"right",    isReadOnly:true,	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//현재고수량
            		//입고수량, 금액, VAT, 합계
            		grid.columns.push( new wijmo.grid.Column({header:messages["outstockConfm.dtl.outUnitQty"],binding:"arrInUnitQty_"	+ j,	width:70,    align:"right",    isReadOnly:false,	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 주문딘위
                	grid.columns.push( new wijmo.grid.Column({header:messages["outstockConfm.dtl.outUnitQty"],binding:"arrInEtcQty_"	+ j,    width:70,    align:"right",    isReadOnly:false,  	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 나머지
                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrInTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum"}) );					//입고수량 - 합계
                  //grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inTotQty"], binding:"arrInTotQty_"	+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", visible:"false"}) );	//입고수량 - 합계
                	grid.columns.push( new wijmo.grid.Column({header:messages["outstockConfm.dtl.outAmt"],	binding:"arrInAmt_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액
                	grid.columns.push( new wijmo.grid.Column({header:messages["outstockConfm.dtl.outVat"],  binding:"arrInVat_"		+ j,    width:70,    align:"right",    isReadOnly:true,   	aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 부가세
                	grid.columns.push( new wijmo.grid.Column({header:messages["outstockConfm.dtl.outTot"],	binding:"arrInTot_"		+ j,    width:70,    align:"right",    isReadOnly:true,		aggregate:"Sum", dataType:"Number", format:"n0"}) );					//입고금액 - 합계
            	}

//            	grid.columnHeaders.setCellData(0, 'arrCurrQty_'		+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrInUnitQty_'	+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrInEtcQty_'	+ j, arrStorageNm[i][j]);
              //grid.columnHeaders.setCellData(0, 'arrInTotQty_'	+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrInAmt_'		+ j, arrStorageNm[i][j]);
            	grid.columnHeaders.setCellData(0, 'arrInVat_'		+ j, arrStorageNm[i][j]);
                grid.columnHeaders.setCellData(0, 'arrInTot_'		+ j, arrStorageNm[i][j]);

//                grid.setCellData(i, 'arrCurrQty_'	+ j,	arrCurrQty[i][j]);

                if(j == 0){
                	if(arrInUnitQty[i][j] == null || arrInUnitQty[i][j] == 0) { arrInUnitQty[i][j] = outUnitQty[i]; }
                	if(arrInEtcQty[i][j] == null || arrInEtcQty[i][j] == 0) { arrInEtcQty[i][j] = outEtcQty[i]; }
                }

//            	grid.setCellData(i, 'arrInUnitQty_'		+ j,	arrInUnitQty	[i][j]);
//            	grid.setCellData(i, 'arrInEtcQty_'		+ j,	arrInEtcQty	[i][j]);
//
//                grid.setCellData(i, 'arrInAmt_'		+ j,	arrInAmt	[i][j]);
//                grid.setCellData(i, 'arrInVat_'		+ j,	arrInVat	[i][j]);
//                grid.setCellData(i, 'arrInTot_'		+ j,	arrInTot	[i][j]);


        	}	//for(var j=0; j<arrStorageCd[i].length; j++){

            	/*
            	[저장]을 한 후 [확정]을 함께 하는 경우에는 상관이 없으나,
            	[저장]이후 화면을 닫은 후 다시 화면을 열어 [확정]만 하는 경우에는  'inTotQty' 값이 setting이 되지않아서 문제가 생길 수 있음.
            	 고로 상세페이지 열리는 경우에 금액계산하는 부분 호출함.
            	*/
            	//$scope.calcAmt($scope.flex.collectionView.items[i], i);

        }	//for(var i=0; i<arrStorageCd.length; i++){

        $scope.callDataSetting();

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

      if (item.outUnitQty === null && item.outEtcQty === null) {
        $scope._popMsg(messages["outstockConfm.dtl.require.outQty"]); // 출고수량을 입력해주세요.
        return false;
      }
      if (item.outEtcQty !== null && (parseInt(item.outEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["outstockConfm.dtl.not.outEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.outTot !== null && (parseInt(item.outTot) > 9999999999)) {
        $scope._popMsg(messages["outstockConfm.dtl.not.overOutTot"]); // 출고금액이 너무 큽니다.
        return false;
      }

      item.status    = "U";
      item.outDate   = wijmo.Globalize.format($scope.dtlOutDate.value, 'yyyyMMdd');
      item.hdRemark  = $scope.hdRemark;
      item.hqRemark  = $scope.hqRemark;
      item.dlvrCd    = $scope.dlvrCd;
      item.confirmFg = ($("#outstockConfirmFg").is(":checked") ? $("#outstockConfirmFg").val() : "");

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

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    //For TEST
    //console.log('params : ' + JSON.stringify(params) );	return false;

    $scope._save("/iostock/order/outstockConfm/outstockConfmDtl/save.sb", params, function () {
      $scope.saveOutstockConfmDtlCallback()
    });
  };

  // 저장 후 콜백 함수
  $scope.saveOutstockConfmDtlCallback = function () {
    var outstockConfmScope = agrid.getScope('dstmnCtrl');
    outstockConfmScope.searchDstmnList();

    $scope.wjDstmnDtlLayer.hide(true);
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
      url    : '/iostock/order/outstockConfm/outstockConfmDtl/saveOutstockAfter.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.flex.collectionView.clearChanges();
        $scope.saveOutstockConfmDtlCallback();
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
    } else {
      $("#divDtlOutDate").hide();
    }
  };

  $scope.callDataSetting = function(){
	  var grid 			= $scope.flex;
	  for(var i=0; i<arrStorageCd.length; i++){
		  var item =  $scope.flex.collectionView.items[i];
		  $scope.flex.collectionView.editItem(item);

		  if(item != undefined){
  	  	for(var j=0; j<arrStorageCd[i].length; j++){
	    	  	grid.setCellData(i, 'arrInUnitQty_'		+ j,	arrInUnitQty	[i][j]);
	    	  	grid.setCellData(i, 'arrInEtcQty_'		+ j,	arrInEtcQty	[i][j]);

		    	grid.setCellData(i, 'arrInAmt_'		+ j,	arrInAmt	[i][j]);
		    	grid.setCellData(i, 'arrInVat_'		+ j,	arrInVat	[i][j]);
		    	grid.setCellData(i, 'arrInTot_'		+ j,	arrInTot	[i][j]);
  	  	}

        $scope.calcAmt(item, 0);
     	}
        $scope.flex.collectionView.commitEdit();
  	  }
  }

//출고내역으로 세팅		--------------------------------------------------------------------------------------------------------------------------
  $scope.setOutToIn = function () {
      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

      //데이터 처리중 팝업 띄우기위해 $timeout 사용.
      $timeout(function () {
    	  $scope.callDataSetting();


//          for (var i=0; i<$scope.flex.collectionView.items.length; i++) {
//          	var item =  $scope.flex.collectionView.items[i];
//
//              $scope.flex.collectionView.editItem(item);
//
//          	//창고부분 모두 0으로 setting
//  			for(var k=0; k<global_storage_cnt; k++){
//  				eval('item.arrInUnitQty_'	+ k + ' = 0;');
//  				eval('item.arrInEtcQty_'	+ k + ' = 0;');
//  				eval('item.arrInTotQty_'	+ k + ' = 0;');
//  				eval('item.arrInAmt_'		+ k + ' = 0;');
//  				eval('item.arrInVat_'		+ k + ' = 0;');
//  				eval('item.arrInTot_'		+ k + ' = 0;');
//  			}
//
//  			//첫번째 창고의 [입고수량]을 [출고수량] 값으로 setting
////              item.arrInUnitQty_0	= item.outUnitQty;
////              item.arrInEtcQty_0	= item.outEtcQty;
//
//             $scope.calcAmt(item, 0);
//
//              $scope.flex.collectionView.commitEdit();
//          }

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
