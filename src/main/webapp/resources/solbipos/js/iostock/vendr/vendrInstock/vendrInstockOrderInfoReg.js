/** 입고/반출 상품 추가/변경 그리드 controller */
app.controller('vendrInstockOrderInfoRegCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrInstockOrderInfoRegCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    
	var comboParams         = {};
    
	// 출고창고
//    var url = '/iostock/order/outstockConfm/outstockConfm/getOutStorageCombo.sb';    
//    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
//    $scope._queryCombo("combo", "saveDtlOutStorageCd", null, url, comboParams, null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
//    
//    comboParams         = {};
    comboParams.nmcodeGrpCd = "093";
    url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "orderEtcQty" || col.binding === "inEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용
            e.cell.outerHTML = e.cell.outerHTML;
          }
        }
      }
    });

    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "orderEtcQty" || col.binding === "inEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
        var dataItem = s.rows[elements.row].dataItem;
        if (dataItem.poUnitQty === 1) {
          elements.cancel = true;
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var str = col.binding;
        var idx = 0;
        // 원가 및 수량 수정시 합계 계산하여 보여준다.
        if (col.binding === "costUprc" || col.binding === "inUnitQty" || col.binding === "inEtcQty") {
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

    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging  = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    
    //첫째줄 Header 생성
    var dataItem = {};
        dataItem.prodCd         = messages["vendrInstock.ord.prodCd"        ];	//상품코드
        dataItem.prodNm         = messages["vendrInstock.ord.prodNm"        ];	//상품명
        dataItem.lastCostUprc   = messages["vendrInstock.ord.lastCostUprc"  ];	//최종원가
        dataItem.poUnitFg       = messages["vendrInstock.ord.poUnitFg"      ];	//주문단위
        dataItem.poUnitQty      = messages["vendrInstock.ord.poUnitQty" 	];	//입수
        dataItem.orderCostUprc  = messages["vendrInstock.ord.orderCostUprc" ];	//발주원가
        
        dataItem.orderUnitQty   = messages["vendrInstock.ord.orderUnitQty"  ];  //발주수량
        dataItem.orderEtcQty    = messages["vendrInstock.ord.orderUnitQty"  ];  //발주수량
                     
        dataItem.costUprc       = messages["vendrInstock.ord.inCostUprc"    ];	//발주원가
        
        dataItem.inUnitQty   	= messages["vendrInstock.ord.inUnitQty"     ];  //입고수량
        dataItem.inEtcQty    	= messages["vendrInstock.ord.inUnitQty"     ];  //입고수량
        dataItem.inTotQty    	= messages["vendrInstock.ord.inTotQty"   	];  //합계

        dataItem.prevInTotQty   = messages["vendrInstock.ord.prevInTotQty"  ];  //이전총입고수량
        dataItem.inAmt      	= messages["vendrInstock.ord.inAmt"    		];  //금액
        dataItem.inVat      	= messages["vendrInstock.ord.inVat"     	];  //Vat
        dataItem.inTot       	= messages["vendrInstock.ord.inTot"     	];  //합계(VAT포함)
        dataItem.vatFg01       	= messages["vendrInstock.ord.vatFg"      	];  //상품부가세구분
        dataItem.vendrVatFg01   = messages["vendrInstock.ord.vendrVatFg01"  ];  //부가세포함여부
        
    s.columnHeaders.rows[0].dataItem = dataItem;
    
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
        if (col.isReadOnly || panel.grid.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }
  };


  $scope.calcAmt = function (item) {
    /** 수량이 없는 경우 계산하지 않음.
        null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 */	
    if (nvl(item.inUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.inEtcQty, null) === null)) return false;

    //
    if(item.poUnitQty === 1){ // 주문단위가 낱개일 때
     if(nvl(item.inUnitQty, null) === null || nvl(item.costUprc, null) === null){ // 주문단위수량이나 발주원가가 없으면 계산 X
        return false;
     }
    }else{ // 주문단위가 박스일 때
      if((nvl(item.inUnitQty, null) === null && nvl(item.inEtcQty, null) === null) || nvl(item.costUprc, null) === null){ // 주문단위수량이나 나머지수량 둘다 없으면서 발주원가도 없으면 계산 X)
        return false;
      }
    }

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
  
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrInstockOrderInfoRegCtrl", function (event, data) {
    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    $scope.slipNo  = data.slipNo;
    $scope.slipFg  = data.slipFg;
    $scope.vendrCd = data.vendrCd;

    // 발주번호 가져오기.
    var vendrInstockDtlScope = agrid.getScope('vendrInstockDtlCtrl');
    $scope.orderSlipNo       = vendrInstockDtlScope.slipInfo.orderSlipNo;

    $scope.wjVendrInstockOrderInfoRegLayer.show(true);

    $scope.searchVendrInstockOrderInfoRegList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 상품 리스트 조회
  $scope.searchVendrInstockOrderInfoRegList = function () {
    // 파라미터
    var params         = {};
    params.slipNo      = $scope.slipNo;
    params.slipFg      = $scope.slipFg;
    params.orderSlipNo = $scope.orderSlipNo;
    params.vendrCd     = $scope.vendrCd;
    params.listScale   = 50;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/vendr/vendrInstock/vendrInstockOrderInfoReg/list.sb", params, function () {	//조회 : URL, parameter, callBack Function

    });		
    
    
  };


  // 상품 저장
  $scope.save = function () {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];
      
      // 총 주문수량 0인 경우 저장하지 않는다.
      if (item.inTotQty === null || item.inTotQty === 0) {
        continue;
      }
      if (item.inUnitQty !== null && (0 > parseInt(item.inUnitQty))) {
        $scope._popMsg(messages['vendrInstock.reg.above.zero.inUnitQty']); // 주문단위수량은 0이상 입력합니다.
        return false;
      }
      if (item.inEtcQty !== null && (0 > parseInt(item.inEtcQty))) {
        $scope._popMsg(messages['vendrInstock.reg.above.zero.inEtcQty']); //낱개수량은 0이상 입력합니다.
        return false;
      }
      if (item.inEtcQty !== null && (parseInt(item.inEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["vendrInstock.ord.not.inEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
        $scope._popMsg(messages["vendrInstock.ord.not.overInTot"]); // 주문금액이 너무 큽니다.
        return false;
      }

      if(item.poUnitQty === 1){ // 주문단위가 낱개일 때

          if(item.costUprc !== null && (parseInt(item.costUprc) >= 0)){
              if(item.inUnitQty === null || 1 > parseInt(item.inUnitQty)){
                  $scope._popMsg(messages['vendrInstock.reg.above.zero.inUnitQty']); // 주문단위수량은 0이상 입력합니다.
                  return false;
              }
          }else{
              $scope._popMsg(messages['vendrInstock.reg.inCostUprc']); // 발주원가를 입력합니다.
              return false;
          }

      }else{ // 주문단위가 박스일 때

          if(item.costUprc !== null && (parseInt(item.costUprc) >= 0)){
              if((item.inUnitQty === null || 1 > parseInt(item.inUnitQty)) && (item.inEtcQty === null || 1 > parseInt(item.inEtcQty))){
                  $scope._popMsg(messages['vendrInstock.reg.above.zero.inUnitQty']); // 주문단위수량은 0이상 입력합니다.
                  return false;
              }
          }else{
              $scope._popMsg(messages['vendrInstock.reg.inCostUprc']); // 발주원가를 입력합니다.
              return false;
          }
      }


      var orderTot = (parseInt(item.orderUnitQty)*item.poUnitQty+(item.orderEtcQty === null ? null : item.orderEtcQty) );
      if(orderTot < item.inTotQty ) {
    	  $scope._popMsg(messages["vendrInstock.ord.not.orderTotQty"]); 
    	  return false;
      }
      

      item.status    = "U";
      item.slipNo    = $scope.slipNo;
      item.slipFg    = $scope.slipFg;
      item.storageCd = "999";			//001 -> 999      
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.prodRegFg = "3"; // 발주내역으로등록에서 상품등록
//      item.outStorageCd	= $scope.save.dtl.outStorageCd;
      
      params.push(item);
    }

    $scope._save("/iostock/vendr/vendrInstock/vendrInstockProdReg/save.sb", params, function () {
      $scope.saveRegistCallback()
    });
  };


  // 저장 후 콜백 서치 함수
  $scope.saveRegistCallback = function () {
    var vendrInstockProdScope = agrid.getScope('vendrInstockProdCtrl');
    vendrInstockProdScope.procFgCheck();

    $scope.wjVendrInstockOrderInfoRegLayer.hide(true);
  };


  // 초기화
  $scope.setDefault = function () {
    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
    // 데이터 처리중 팝업 띄우기위해 $timeout 사용.
    $timeout(function () {
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        $scope.flex.collectionView.editItem(item);
        item.costUprc  = 0;
        item.inUnitQty = 0;
        item.inEtcQty  = (item.poUnitQty === 1 ? null : 0);

        $scope.calcAmt(item);
        $scope.flex.collectionView.commitEdit();
      }
      $scope.$broadcast('loadingPopupInactive');
    }, 100);
  };


  // 발주내역으로 세팅
  $scope.setOrderToInstock = function () {
    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
    // 데이터 처리중 팝업 띄우기위해 $timeout 사용.
    $timeout(function () {
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        $scope.flex.collectionView.editItem(item);
        item.costUprc  = item.orderCostUprc;
        item.inUnitQty = item.orderUnitQty;
        item.inEtcQty  = item.orderEtcQty;
        
      	$scope.calcAmt(item);
        $scope.flex.collectionView.commitEdit();
      }
      $scope.$broadcast('loadingPopupInactive');
    }, 100);
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
    	params.sid = document.getElementsByName('sessionId')[0].value;
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
