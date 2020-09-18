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

        // 입고창고
        var url = '/iostock/order/instockConfm/instockConfm/getInStorageCombo.sb';

        // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
        $scope._queryCombo("combo", "saveDtlInStorageCd", null, url, comboParams, null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

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



        s.columnFooters.rows.push(new wijmo.grid.GroupRow());	//add the new GroupRow to the grid's 'columnFooters' panel
        s.bottomLeftCells.setCellData(0, 0, '합계');				//add a sigma to the header to show that this is a summary row


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

	            s.columnHeaders.setCellData(i, "outUnitQty" , messages["instockConfm.dtl.outUnitQty"    ] );
	            s.columnHeaders.setCellData(i, "outEtcQty"  , messages["instockConfm.dtl.outUnitQty"	] );
	            s.columnHeaders.setCellData(i, "inUnitQty"  , messages["instockConfm.dtl.inUnitQty"     ] );
	            s.columnHeaders.setCellData(i, "inEtcQty"   , messages["instockConfm.dtl.inUnitQty"     ] );

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

    };	//$scope.initGrid	--------------------------------------------------------------------------------------------------------------------------



    //금액 계산					--------------------------------------------------------------------------------------------------------------------------
    $scope.calcAmt = function (item) {
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
    //$scope.calcAmt		--------------------------------------------------------------------------------------------------------------------------



    //다른 컨트롤러의 broadcast 받기
    $scope.$on("instockConfmDtlCtrl", function (event, data) {
        $scope.slipFg = data.slipFg;
        $scope.slipNo = data.slipNo;
        $scope.startDate = data.startDate;
        $scope.endDate = data.endDate;
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


                var grid 			= $scope.flex;
                var item;

                for(var i=0; i<grid.collectionView.items.length; i++){
            		//console.log('$scope.calcAmt - Before: ' + i);
                	$scope.calcAmt($scope.flex.collectionView.items[i], i);
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
//        if ($("#instockConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
//            var item = $scope.flex.collectionView.items[0];
//            if (item === null) return false;
//
//            $scope.flex.collectionView.editItem(item);
//            item.status = "U";
//            $scope.flex.collectionView.commitEdit();
//        }
//
//        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
//            var item = $scope.flex.collectionView.itemsEdited[i];

        for (var i=0; i<$scope.flex.collectionView.items.length; i++) {
        	var item =  $scope.flex.collectionView.items[i];

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

            item.status     = "U";
            item.inDate     = wijmo.Globalize.format($scope.dtlInDate.value, 'yyyyMMdd');
            item.hdRemark   = $scope.hdRemark;
            item.inStorageCd	= $scope.save.dtl.inStorageCd;
            item.confirmFg  = ($("#instockConfirmFg").is(":checked") ? $("#instockConfirmFg").val() : "");

            params.push(item);

        }

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
    $scope.setOutToIn = function () {
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
    //$scope.setOutToIn	--------------------------------------------------------------------------------------------------------------------------



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
        params.startDate 	= $scope.startDate;
        params.endDate 		= $scope.endDate;
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
        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
        	params.sid = document.getElementsByName('sessionId')[0].value;
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
