/** 입고확정 상세 그리드 controller */
app.controller('rtnInstockConfmDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnInstockConfmDtlCtrl', $scope, $http, $timeout, true));

  $scope.dtlInDate = wcombo.genDate("#dtlInDate");

  $scope._setComboData("stmtAcctFg", [
    {"name": messages["rtnInstockConfm.dtl.stmtAcctAll"], "value": ""},
    {"name": messages["rtnInstockConfm.dtl.stmtAcctSplr"], "value": "1"},
    {"name": messages["rtnInstockConfm.dtl.stmtAcctSplrRcpnt"], "value": "2"}
  ]);

  var global_storage_cnt = 0;	//매장의 창고 갯수

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 배송기사
    var comboParams = {};
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	comboParams.sid = document.getElementsByName('sessionId')[0].value;
    }
    var url         = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';

    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchDtlDlvrCd", null, url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 출고창고
    url = '/iostock/order/outstockConfm/outstockConfm/getOutStorageCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "saveDtlOutStorageCd", null, url, comboParams, null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    comboParams             = {};
    comboParams.nmcodeGrpCd = "093";
    url                     = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "inEtcQty") { // 입수에 따라 출고수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용.
            e.cell.outerHTML = e.cell.outerHTML;
          }
        }
      }
    });

    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "inEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
        var dataItem = s.rows[elements.row].dataItem;
        if (dataItem.poUnitQty === 1) {
          elements.cancel = true;
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "inUnitQty" || col.binding === "inEtcQty") { // 입고수량 수정시
          var item = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
        }
      }

      s.collectionView.commitEdit();
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

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
			s.columnHeaders.setCellData(i, "inUnitQty" , messages["instockConfm.dtl.inUnitQty"    ] );
			s.columnHeaders.setCellData(i, "inEtcQty"  , messages["instockConfm.dtl.inUnitQty"	] );

			s.columnHeaders.setCellData(i, "inTotQty"   , messages["instockConfm.dtl.inTotQty"      ] );
			s.columnHeaders.setCellData(i, "inAmt"      , messages["instockConfm.dtl.inAmt"         ] );
			s.columnHeaders.setCellData(i, "inVat"      , messages["instockConfm.dtl.inVat"         ] );
			s.columnHeaders.setCellData(i, "inTot"      , messages["instockConfm.dtl.inTot"         ] );
			s.columnHeaders.setCellData(i, "remark"     , messages["instockConfm.dtl.remark"        ] );
			s.columnHeaders.setCellData(i, "vatFg01"    , messages["instockConfm.dtl.vatFg"         ] );
		}
    //Header - END

        for(var i=0; i<19; i++){
        	s.columnHeaders.columns[i].allowMerging = true;
        }



      $scope.selectedIndexChanged = function (s) {
        	var comboParams             = {};
        	comboParams.storageCd 	= $scope.save.dtl.outStorageCd;
            // 배송기사
            url = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';

            // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
            $scope._queryCombo("combo", "saveDtlDlvrCd", null, url, comboParams, ""); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

        };
  };

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

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnInstockConfmDtlCtrl", function (event, data) {
	$scope.startDate = data.startDate;
	$scope.endDate = data.endDate;
    $scope.slipFg = data.slipFg;
    $scope.slipNo = data.slipNo;
    $scope.vendrCd = data.vendrCd;
    $scope.wjRtnInstockConfmDtlLayer.show(true);

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
      url    : '/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl/getSlipNoInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {

          $scope.dtlInDate.value = new Date(getFormatDate(response.data.data.outDate, "-"));
          $scope.outDate         = response.data.data.outDate;
          $scope.inDate          = response.data.data.inDate;
          $scope.slipFg          = response.data.data.slipFg;
          $scope.slipKind        = response.data.data.slipKind;
          $scope.slipKindNm      = response.data.data.slipKindNm;
          $scope.procFg          = response.data.data.procFg;
          $scope.storeCd         = response.data.data.storeCd;
          $scope.storeNm         = response.data.data.storeNm;
          $scope.hdRemark        = response.data.data.remark;
          $scope.dlvrCd          = nvl(response.data.data.dlvrCd, '');
          $scope.dlvrNm          = response.data.data.dlvrNm;

          // 출고확정
          if ($scope.procFg === "20") {
            $("#lblTitle").text(messages["rtnInstockConfm.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["rtnInstockConfm.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["rtnInstockConfm.dtl.outDate"] + ' : ' + getFormatDate($scope.outDate));
            $scope.instockBtnLayerDisplay(true);
          }
          // 수주확정 또는 입고확정
          else if ($scope.procFg === "10" || $scope.procFg === "30") {
            $scope.instockBtnLayerDisplay(false);
            $scope.flex.isReadOnly = true;

            // 수주확정
            if ($scope.procFg === "10") {
              $("#lblTitle").text(messages["rtnInstockConfm.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["rtnInstockConfm.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["rtnInstockConfm.dtl.reqDate"] + ' : ' + getFormatDate($scope.outDate));
            }
            // 입고확정
            else if ($scope.procFg === "30") {
              $("#lblTitle").text(messages["rtnInstockConfm.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["rtnInstockConfm.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["rtnInstockConfm.dtl.outDate"] + ' : ' + getFormatDate($scope.outDate) + ', ' + messages["rtnInstockConfm.dtl.inDate"] + ' : ' + getFormatDate($scope.inDate));
            }
          }

          $scope.searchRtnInstockConfmDtlList();
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


  // 입고확정 상세내역 리스트 조회
  $scope.searchRtnInstockConfmDtlList = function () {
    // 파라미터
    var params    = {};
    params.slipNo = $scope.slipNo;

 // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl/list.sb", params, function () {
    });

  };


  // 저장
  $scope.save = function () {
    var params = [];

    // 확정처리가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
//    if ($("#instockConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
//      var item = $scope.flex.collectionView.items[0];
//      if (item === null) return false;
//
//      $scope.flex.collectionView.editItem(item);
//      item.status = "U";
//      $scope.flex.collectionView.commitEdit();
//    }

//    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
//      var item = $scope.flex.collectionView.itemsEdited[i];

      for (var i=0; i<$scope.flex.collectionView.items.length; i++) {
      	var item =  $scope.flex.collectionView.items[i];

      if (item.inUnitQty === null && item.inEtcQty === null) {
        $scope._popMsg(messages["rtnInstockConfm.dtl.require.inQty"]); // 입고수량을 입력해주세요.
        return false;
      }
      if (item.inEtcQty !== null && (parseInt(item.inEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["rtnInstockConfm.dtl.not.inEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
        $scope._popMsg(messages["rtnInstockConfm.dtl.not.overInTot"]); // 입고금액이 너무 큽니다.
        return false;
      }

      //입고내역 세팅등으로 인한 작업은 위즈모에서 이벤트 처리못함. 저장시 재계산
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

      item.status    = "U";
      item.inDate    = wijmo.Globalize.format($scope.dtlInDate.value, 'yyyyMMdd');
      item.hdRemark  = $scope.hdRemark;
      item.confirmFg = ($("#instockConfirmFg").is(":checked") ? $("#instockConfirmFg").val() : "");
      item.inStorageCd	= $scope.save.dtl.outStorageCd;
      item.dlvrCd    = $scope.save.dtl.dlvrCd;

      params.push(item);
    }
    console.log(params);
    $scope._save("/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl/save.sb", params, function () {
      $scope.saveRtnInstockConfmDtlCallback()
    });
  };


  // 저장 후 콜백 함수
  $scope.saveRtnInstockConfmDtlCallback = function () {
    var rtnInstockConfmScope = agrid.getScope('rtnInstockConfmCtrl');
    rtnInstockConfmScope.searchRtnInstockConfmList();

    $scope.wjRtnInstockConfmDtlLayer.hide(true);
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
