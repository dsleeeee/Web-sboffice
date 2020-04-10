/** 입고확정 상세 그리드 controller */
app.controller('instockConfmDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    //상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('instockConfmDtlCtrl', $scope, $http, true));

    $scope.dtlInDate = wcombo.genDate("#dtlInDate");

    $scope._setComboData("stmtAcctFg", [
        {"name": messages["instockConfm.dtl.stmtAcctAll"], 		"value": ""},
        {"name": messages["instockConfm.dtl.stmtAcctSplr"], 	"value": "1"},
        {"name": messages["instockConfm.dtl.stmtAcctSplrRcpnt"],"value": "2"}
    ]);

    var global_storage_cnt = 0;	//매장의 창고 갯수

	//grid 초기화				--------------------------------------------------------------------------------------------------------------------------
    $scope.initGrid = function (s, e) {
        //배송기사
        var comboParams = {};
        var url         = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';
        $scope._queryCombo("combo", "srchDtlDlvrCd", null, url, comboParams, "S");  //명칭관리 조회시 URL 없이 그룹코드만 넘긴다.		//파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)

        comboParams             = {};
        comboParams.nmcodeGrpCd = "097";
        url                     = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
        $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A");  	//명칭관리 조회시 URL 없이 그룹코드만 넘긴다.		//파라미터 (comboFg, comboId, gridMapId, url, params, option)

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

        /*
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "inUnitQty" || col.binding === "inEtcQty") {    //입고수량 수정시
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

        s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//add the new GroupRow to the grid's 'columnFooters' panel
        s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row

        /*
        //Header column merge (출고수량, 입고수량)
        s.allowMerging                          = 'ColumnHeaders';
        s.columnHeaders.rows[0].allowMerging    = true;

        //Header - START
			//헤더 생성
			s.columnHeaders.rows.push(new wijmo.grid.Row());

			for(var i=0; i<s.columnHeaders.rows.length; i++) {
	            s.columnHeaders.setCellData(i, "slipNo"     , messages["instockConfm.dtl.slipNo"        ] );
	            s.columnHeaders.setCellData(i, "slipFg"     , messages["instockConfm.dtl.slipFg"        ] );
	            s.columnHeaders.setCellData(i, "seq"        , messages["instockConfm.dtl.seq"           ] );
	            s.columnHeaders.setCellData(i, "storeCd"    , messages["instockConfm.dtl.storeCd"       ] );
	            s.columnHeaders.setCellData(i, "prodCd"     , messages["instockConfm.dtl.prodCd"        ] );
	            s.columnHeaders.setCellData(i, "prodNm"     , messages["instockConfm.dtl.prodNm"        ] );
	            s.columnHeaders.setCellData(i, "barcdCd"    , messages["instockConfm.dtl.barcdCd"       ] );
	            s.columnHeaders.setCellData(i, "poUnitFg"   , messages["instockConfm.dtl.poUnitFg"      ] );
	            s.columnHeaders.setCellData(i, "poUnitQty"  , messages["instockConfm.dtl.poUnitQty"     ] );
	            s.columnHeaders.setCellData(i, "outSplyUprc", messages["instockConfm.dtl.outSplyUprc"   ] );

	            //s.columnHeaders.setCellData(i, "outUnitQty" , messages["instockConfm.dtl.outUnitQty"    ] );
	            //s.columnHeaders.setCellData(i, "outEtcQty"  , messages["instockConfm.dtl.outUnitQty"	] );
	            //s.columnHeaders.setCellData(i, "inUnitQty"  , messages["instockConfm.dtl.inUnitQty"     ] );
	            //s.columnHeaders.setCellData(i, "inEtcQty"   , messages["instockConfm.dtl.inUnitQty"     ] );

	            s.columnHeaders.setCellData(i, "inTotQty"   , messages["instockConfm.dtl.inTotQty"      ] );
	            s.columnHeaders.setCellData(i, "inAmt"      , messages["instockConfm.dtl.inAmt"         ] );
	            s.columnHeaders.setCellData(i, "inVat"      , messages["instockConfm.dtl.inVat"         ] );
	            s.columnHeaders.setCellData(i, "inTot"      , messages["instockConfm.dtl.inTot"         ] );
	            s.columnHeaders.setCellData(i, "remark"     , messages["instockConfm.dtl.remark"        ] );
	            s.columnHeaders.setCellData(i, "vatFg01"    , messages["instockConfm.dtl.vatFg"         ] );
			}
	    //Header - END

	        for(var i=0; i<28; i++){
	        	s.columnHeaders.columns[i].allowMerging = true;
	        }

	        //s.columnHeaders.rows[0].allowMerging    = true;
	        //s.columnHeaders.rows[1].allowMerging    = true;
 		*/

        //Grid Header 2줄 - START	----------------------------------------------------------------
          //s.allowMerging = 'ColumnHeaders';
	        s.allowMerging = 2;	//2:ColumnHeaders, 6:AllHeaders
	        s.columnHeaders.rows.push(new wijmo.grid.Row());

	        //첫째줄 Header 생성
	        var dataItem = {};
	            dataItem.slipNo         = messages["instockConfm.dtl.slipNo"        ];	//전표번호
	            dataItem.slipFg         = messages["instockConfm.dtl.slipFg"        ];	//전표구분
	            dataItem.seq            = messages["instockConfm.dtl.seq"           ];	//순번
	            dataItem.storeCd        = messages["instockConfm.dtl.storeCd"       ];	//매장코드
	            dataItem.prodCd         = messages["instockConfm.dtl.prodCd"        ];	//상품코드
	            dataItem.prodNm         = messages["instockConfm.dtl.prodNm"        ];	//상품명
	            dataItem.barcdCd        = messages["instockConfm.dtl.barcdCd"       ];	//바코드
	            dataItem.poUnitFg       = messages["instockConfm.dtl.poUnitFg"      ];  //주문단위
	            dataItem.poUnitQty      = messages["instockConfm.dtl.poUnitQty"     ];  //입수
	            dataItem.outSplyUprc    = messages["instockConfm.dtl.outSplyUprc"   ];  //공급가

	            dataItem.outUnitQty     = messages["instockConfm.dtl.outUnitQty"    ];  //출고수량
	            dataItem.outEtcQty      = messages["instockConfm.dtl.outUnitQty"    ];  //출고수량
	            dataItem.inUnitQty      = messages["instockConfm.dtl.inUnitQty"     ];  //입고수량
	            dataItem.inEtcQty       = messages["instockConfm.dtl.inUnitQty"     ];  //입고수량

	            dataItem.inTotQty       = messages["instockConfm.dtl.inTotQty"      ];  //입고수량
	            dataItem.inAmt          = messages["instockConfm.dtl.inAmt"         ];  //금액
	            dataItem.inVat          = messages["instockConfm.dtl.inVat"         ];  //VAT
	            dataItem.inTot          = messages["instockConfm.dtl.inTot"         ];  //합계
	            dataItem.remark         = messages["instockConfm.dtl.remark"        ];  //비고
	            dataItem.vatFg01        = messages["instockConfm.dtl.vatFg"         ];  //상품부가세구분
	        s.columnHeaders.rows[0].dataItem = dataItem;
        //Grid Header 2줄 - END		----------------------------------------------------------------

    };	//$scope.initGrid	--------------------------------------------------------------------------------------------------------------------------



    //금액 계산					--------------------------------------------------------------------------------------------------------------------------
    $scope.calcAmt_OLD = function (item) {
        var outSplyUprc = parseInt(item.outSplyUprc);
        var poUnitQty   = parseInt(item.poUnitQty);
        var vat01       = parseInt(item.vatFg01);
        var envst0011   = parseInt(item.envst0011);

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
    };
    $scope.calcAmt = function (item, idx) {
    	//$scope.flex.collectionView.editItem(item);

        var outSplyUprc = parseInt(item.outSplyUprc	);
        var poUnitQty   = parseInt(item.poUnitQty	);
        var vat01       = parseInt(item.vatFg01		);
        var envst0011   = parseInt(item.envst0011	);

        var unitQty     = parseInt(nvl(eval('item.arrInUnitQty_' + idx), 0)) * parseInt(item.poUnitQty);
        var etcQty      = parseInt(nvl(eval('item.arrInEtcQty_'  + idx), 0));
        //console.log('unitQty: ' + unitQty);
        //console.log('etcQty : ' + etcQty );
        var totQty      = parseInt(unitQty + etcQty);
        var tempAmt     = Math.round(totQty * outSplyUprc / poUnitQty);
        var inAmt       = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
        var inVat       = Math.round(tempAmt * vat01 / (10 + envst0011));
        var inTot       = parseInt(inAmt + inVat);

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



    //다른 컨트롤러의 broadcast 받기
    $scope.$on("instockConfmDtlCtrl", function (event, data) {
        $scope.slipFg = data.slipFg;
        $scope.slipNo = data.slipNo;
        $scope.wjInstockConfmDtlLayer.show(true);

        $scope.getSlipNoInfo();
        //기능수행 종료 : 반드시 추가
        event.preventDefault();
    });



    //전표상세 조회				--------------------------------------------------------------------------------------------------------------------------
    $scope.getSlipNoInfo = function () {
        var params      = {};
        params.slipNo   = $scope.slipNo;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        //ajax 통신 설정
        $http({
            method  : 'POST',
            url     : '/iostock/order/instockConfm/instockConfmDtl/getSlipNoInfo.sb',
            params  : params,
            headers : {'Content-Type': 'application/json; charset=utf-8'}
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {

                    $scope.dtlInDate.value = new Date(getFormatDate(response.data.data.outDate, "-"));
                    $scope.outDate      = response.data.data.outDate;
                    $scope.inDate       = response.data.data.inDate;
                    $scope.slipFg       = response.data.data.slipFg;
                    $scope.slipKind     = response.data.data.slipKind;
                    $scope.slipKindNm   = response.data.data.slipKindNm;
                    $scope.procFg       = response.data.data.procFg;
                    $scope.storeCd      = response.data.data.storeCd;
                    $scope.storeNm      = response.data.data.storeNm;
                    $scope.hdRemark     = response.data.data.remark;
                    $scope.dlvrCd       = nvl(response.data.data.dlvrCd, '');
                    $scope.dlvrNm       = response.data.data.dlvrNm;

                    if($scope.procFg === "20"){										//출고확정 (10:수주확정, 20:출고확정, 30:입고확정)
                        $("#spanDtlTitle").html(messages["instockConfm.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["instockConfm.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["instockConfm.dtl.outDate"] + ' : ' + getFormatDate($scope.outDate));
                        $scope.instockBtnLayerDisplay(true);

                        if (gEnvst1043 === "N") {	//1043: 매장입고시수량변경
                            $scope.flex.isReadOnly = true;

                        } else {
                            $scope.flex.isReadOnly = false;
                        }
                    }
                    else if ($scope.procFg === "10" || $scope.procFg === "30"){		//수주확정 또는 입고확정 (10:수주확정, 20:출고확정, 30:입고확정)
                        $scope.instockBtnLayerDisplay(false);
                        $scope.flex.isReadOnly = true;

                        if 		($scope.procFg === "10") {	//수주확정
                            $("#spanDtlTitle").html(messages["instockConfm.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["instockConfm.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["instockConfm.dtl.reqDate"] + ' : ' + getFormatDate($scope.outDate));
                        }
                        else if ($scope.procFg === "30") {	//입고확정
                            $("#spanDtlTitle").html(messages["instockConfm.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["instockConfm.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["instockConfm.dtl.outDate"] + ' : ' + getFormatDate($scope.outDate) + ', ' + messages["instockConfm.dtl.inDate"] + ' : ' + getFormatDate($scope.inDate));
                        }
                    }

                    $scope.searchInstockConfmDtlList();
                }
            }
        }, function errorCallback(response) {
            //called asynchronously if an error occurs
            //or server returns response with an error status.
            $scope._popMsg(messages["cmm.saveFail"]);
            return false;
        }).then(function () {
            //"complete" code here
        });
    };	//$scope.getSlipNoInfo	----------------------------------------------------------------------------------------------------------------------



    //입고확정 상세내역 리스트 조회		--------------------------------------------------------------------------------------------------------------------------
    $scope.searchInstockConfmDtlList = function () {

        var params          = {};
            params.slipNo   = $scope.slipNo;

        $scope._inquirySub("/iostock/order/instockConfm/instockConfmDtl/list.sb", params, function () {	//조회 : URL, parameter, callBack Function

        	if (gEnvst1043 === "N") {		//1043: 매장입고시수량변경

            } else {
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
      		  	while(grid.columns.length > 28){	//이 상세화면이 다시 열리는 경우를 대비하여, 추가된 칼럼 삭제해야 함. ('arrInTot'이 28번재)
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
                    		grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inUnitQty"],binding:"arrInUnitQty_"	+ j,	width:50,    align:"right",    isReadOnly:false,	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 주문딘위
		                	grid.columns.push( new wijmo.grid.Column({header:messages["instockConfm.dtl.inUnitQty"],binding:"arrInEtcQty_"	+ j,    width:50,    align:"right",    isReadOnly:false,  	aggregate:"Sum", dataType:"Number", format:"n0", maxLength:5}) );					//입고수량 - 나머지
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

                        grid.setCellData(i, 'arrInUnitQty_'	+ j,	arrInUnitQty[i][j]);
                        grid.setCellData(i, 'arrInEtcQty_'	+ j,	arrInEtcQty	[i][j]);
                        grid.setCellData(i, 'arrInAmt_'		+ j,	arrInAmt	[i][j]);
                        grid.setCellData(i, 'arrInVat_'		+ j,	arrInVat	[i][j]);
                        grid.setCellData(i, 'arrInTot_'		+ j,	arrInTot	[i][j]);

                	}	//for(var j=0; j<arrStorageCd[i].length; j++){

	                	/*
	                	[저장]을 한 후 [확정]을 함께 하는 경우에는 상관이 없으나,
	                	[저장]이후 화면을 닫은 후 다시 화면을 열어 [확정]만 하는 경우에는  'inTotQty' 값이 setting이 되지않아서 문제가 생길 수 있음.
	                	 고로 상세페이지 열리는 경우에 금액계산하는 부분 호출함.
	                	*/
	                	$scope.calcAmt($scope.flex.collectionView.items[i], i);
                }	//for(var i=0; i<arrStorageCd.length; i++){

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

            }	//else
        		//console.log('response:\n' + JSON.stringify(response.data.data) );
        });		//$scope._inquirySub("/iostock/order/instockConfm/instockConfmDtl/list.sb", params, function () {
    };	//$scope.searchInstockConfmDtlList	----------------------------------------------------------------------------------------------------------



    //저장					--------------------------------------------------------------------------------------------------------------------------
    $scope.save = function () {
        var params = [];

        //확정처리가 체크 되어지만 Grid의 수정된 내역은 없는 경우, 저장로직을 수행하기 위해 first row의 상태값을 '수정'으로 변경.
        if ($("#instockConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
            var item = $scope.flex.collectionView.items[0];
            if (item === null) return false;

            $scope.flex.collectionView.editItem(item);
            item.status = "U";
            $scope.flex.collectionView.commitEdit();
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            var item = $scope.flex.collectionView.itemsEdited[i];

            if (item.inUnitQty === null && item.inEtcQty === null) {
                $scope._popMsg(messages["instockConfm.dtl.require.inQty"]); //입고수량을 입력해주세요.
                return false;
            }
            if (item.inEtcQty !== null && (parseInt(item.inEtcQty) >= parseInt(item.poUnitQty))) {
                $scope._popMsg(messages["instockConfm.dtl.not.inEtcQty"]);  //낱개수량은 입수량보다 작아야 합니다.
                return false;
            }
            if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
                $scope._popMsg(messages["instockConfm.dtl.not.overInTot"]); //입고금액이 너무 큽니다.
                return false;
            }

            item.status     = "U";
            item.inDate     = wijmo.Globalize.format($scope.dtlInDate.value, 'yyyyMMdd');
            item.hdRemark   = $scope.hdRemark;
            item.confirmFg  = ($("#instockConfirmFg").is(":checked") ? $("#instockConfirmFg").val() : "");

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
        //For TEST
	        //console.log('params : ' + JSON.stringify(params) );	return false;

        $scope._save("/iostock/order/instockConfm/instockConfmDtl/save.sb", params, function () {
            $scope.saveInstockConfmDtlCallback()
        });
    };	//$scope.save		--------------------------------------------------------------------------------------------------------------------------



    //저장 후의 callBack
    $scope.saveInstockConfmDtlCallback = function () {
        var instockConfmScope = agrid.getScope('instockConfmCtrl');
        instockConfmScope.searchInstockConfmList();

        $scope.wjInstockConfmDtlLayer.hide(true);
    };



    $scope.fnConfirmChk = function () {
        if ($("#instockConfirmFg").prop("checked")) {
            $("#divDtlInDate").show();
        } else {
            $("#divDtlInDate").hide();
        }
    };



    //출고내역으로 입고내역 세팅		--------------------------------------------------------------------------------------------------------------------------
    $scope.setOutToIn_OLD = function () {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
        //데이터 처리중 팝업 띄우기위해 $timeout 사용.
        $timeout(function () {
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                $scope.flex.collectionView.editItem(item);

                item.inUnitQty  = item.outUnitQty;
                item.inEtcQty   = item.outEtcQty;
                $scope.calcAmt(item);

                $scope.flex.collectionView.commitEdit();
            }
            $scope.$broadcast('loadingPopupInactive');
        }, 100);
    };
    $scope.setOutToIn = function () {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        //데이터 처리중 팝업 띄우기위해 $timeout 사용.
        $timeout(function () {
            for (var i=0; i<$scope.flex.collectionView.items.length; i++) {
            	var item =  $scope.flex.collectionView.items[i];

                $scope.flex.collectionView.editItem(item);

            	//창고부분 모두 0으로 setting
    			for(var k=0; k<global_storage_cnt; k++){
    				eval('item.arrInUnitQty_'	+ k + ' = 0;');
    				eval('item.arrInEtcQty_'	+ k + ' = 0;');
    				eval('item.arrInTotQty_'	+ k + ' = 0;');
    				eval('item.arrInAmt_'		+ k + ' = 0;');
    				eval('item.arrInVat_'		+ k + ' = 0;');
    				eval('item.arrInTot_'		+ k + ' = 0;');
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



    $scope.instockBtnLayerDisplay = function (isVisible) {
        if (isVisible) {
            $("#instockBtnLayer").show();
        } else {
            $("#instockBtnLayer").hide();
        }

        $scope.spanInstockConfirmFg = isVisible;
        $scope.btnSetOutToIn        = isVisible;
        $scope.btnDtlSave           = isVisible;
    };



    //거래명세표
    $scope.reportTrans = function () {
        var params          = {};
        params.slipFg       = $scope.slipFg;
        params.strSlipNo    = $scope.slipNo;
        params.stmtAcctFg   = $scope.stmtAcctFg;
        $scope._broadcast('transReportCtrl', params);
    };



    //DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
    //comboFg   : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
    //comboId   : combo 생성할 ID
    //gridMapId : grid 에서 사용할 Map ID
    //url       : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
    //params    : 데이터 조회할 url에 보낼 파라미터
    //option    : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
    //callback  : queryCombo 후 callback 할 함수
    $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
        var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
        if (url) {
            comboUrl = url;
        }

        //ajax 통신 설정
        $http({
            method  : 'POST',
            url     : comboUrl,
            params  : params,
            headers : {'Content-Type': 'application/json; charset=utf-8'}
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list        = response.data.data.list;
                    var comboArray  = [];
                    var comboData   = {};

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
                            comboData       = {};
                            comboData.id    = list[i].nmcodeCd;
                            comboData.name  = list[i].nmcodeNm;
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
    };	//$scope._queryCombo



	//[엑셀 다운로드] - START	------------------------------------------------------------------------------------------------------------------------------
	$scope.excelDownload = function(){
		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
		$timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(
                $scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles   : 	true,
                    includeColumns      :   function (column) {
                                                return column.visible;
                                            }
                },
              //'입고확정_상세_' + getToday() + '.xlsx',
                '입고확정_상세_' + getCurDate('-') + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
	};
    //[엑셀 다운로드] - END	------------------------------------------------------------------------------------------------------------------------------


}]);
