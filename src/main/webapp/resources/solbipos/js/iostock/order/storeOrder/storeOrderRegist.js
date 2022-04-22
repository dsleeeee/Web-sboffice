//var app = agrid.getApp();

/** 주문등록 상세 그리드 controller */
app.controller('storeOrderRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeOrderRegistCtrl', $scope, $http, true));

  // 옵션1
  $scope._setComboData("option1", [
    {"name": messages["storeOrder.dtl.option1All"], "value": ""},
    {"name": messages["storeOrder.dtl.option1SafeStock"], "value": "S"}
  ]);

  // 옵션2
  $scope._setComboData("option2", [
    {"name": messages["storeOrder.dtl.option2All"], "value": ""},
    {"name": messages["storeOrder.dtl.option2Order"], "value": "ORD"},
    {"name": messages["storeOrder.dtl.option2Outstock"], "value": "OUT"},
    {"name": messages["storeOrder.dtl.option2Sale"], "value": "SALE"}
  ]);

  // 엑셀업로드 수량적용/수량추가
  $scope._setComboData("addQtyFg", [
    {"name": messages["storeOrder.dtl.addQtyFgApply"], "value": "apply"},
    {"name": messages["storeOrder.dtl.addQtyFgAdd"], "value": "add"}
  ]);

  $scope.srchRegStartDate = wcombo.genDate("#srchRegStartDate");
  $scope.srchRegEndDate   = wcombo.genDate("#srchRegEndDate");
  
  $scope.reset = function() {
  	$scope.searchStoreLoan('Y');
  }
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "093";
    var url                 = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // s.allowMerging = wijmo.grid.AllowMerging.AllHeaders;
    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "orderUnitQty") {
          $scope.calcAmt(item);
        } else if (col.binding === "orderEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용.
            e.cell.outerHTML = e.cell.outerHTML;
          } else {
            $scope.calcAmt(item);
          }
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 주문수량 수정시 금액,VAT,합계 계산하여 보여준다.
        if (col.binding === "orderUnitQty" || col.binding === "orderEtcQty") {
          var item = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
        }
      }

      s.collectionView.commitEdit();
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    // s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    // s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging  = 2;
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
    if (nvl(item.orderUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.orderEtcQty, null) === null)) return false;

    var orderSplyUprc = parseInt(item.orderSplyUprc);
    var poUnitQty     = parseInt(item.poUnitQty);
    var vat01         = parseInt(item.vatFg01);
    var envst0011     = parseInt(item.envst0011);

    var unitQty  = (parseInt(nvl(item.prevOrderUnitQty, 0)) + parseInt(nvl(item.orderUnitQty, 0))) * parseInt(item.poUnitQty);
    var etcQty   = parseInt(nvl(item.prevOrderEtcQty, 0)) + parseInt(nvl(item.orderEtcQty, 0));
    var totQty   = parseInt(unitQty + etcQty);
    var tempAmt  = Math.round(totQty * orderSplyUprc / poUnitQty);
    var orderAmt = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
    var orderVat = Math.round(tempAmt * vat01 / (10 + envst0011));
    var orderTot = parseInt(orderAmt + orderVat);

    item.orderTotQty = totQty;   // 총수량
    item.orderAmt    = orderAmt; // 금액
    item.orderVat    = orderVat; // VAT
    item.orderTot    = orderTot; // 합계
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeOrderRegistCtrl", function (event, data) {

	  $scope.reset();
    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    if (!$.isEmptyObject(data)) {
      $scope.reqDate     = data.reqDate;
      $scope.slipFg      = data.slipFg;
      $scope.callParent  = data.callParent;
      $scope.regHdRemark = data.hdRemark;

      // 값 초기화
      $scope.prodClassCdNm = messages["cmm.all"];
      $scope.prodClassCd   = '';

      // 신규 요청등록인 경우
      if ($scope.callParent === "storeOrder") {
        // 당일보다 이전일자 요청등록 불가
        var today = getCurDate();
        if (parseInt(today) > parseInt($scope.reqDate)) {
          $scope._popMsg(messages["storeOrder.dtl.not.prevDateOrder"]);
          return false;
        }
        $scope.storeOrderDateCheck(); // 출고요청가능일인지 여부 체크
      }
      // 주문 상품상세내역 페이지에서 호출한 경우
      else if ($scope.callParent === "storeOrderDtl") {
        $scope.storeCloseCheck(); // 주문진행구분 체크
      }
    } else { // 페이징처리에서 broadcast 호출시
      $scope.searchStoreOrderRegistList();
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 출고요청가능일인지 여부 체크
  $scope.storeOrderDateCheck = function () {
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;

    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/orderDateCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          if (response.data.data.orderFg > 0) {
            $scope._popMsg(messages["storeOrder.dtl.not.orderDate"]);
            return false;
          }
        }
        $scope.storeCloseCheck();
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  // 매장마감여부 체크
  $scope.storeCloseCheck = function () {
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;

    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/storeCloseCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          if (response.data.data.orderCloseFg === "Y") {
            $scope._popMsg(messages["storeOrder.dtl.orderClose"]);
            return false;
          }
        }
        $scope.orderProcFgCheck(); // 주문진행구분 체크
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  // 주문진행구분 체크 및 HD 비고 조회
  $scope.orderProcFgCheck = function () {
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;

    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/orderProcFgCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 진행구분이 주문등록이 아니면 상품추가/변경 불가
        if (!$.isEmptyObject(response.data.data)) {
          if (response.data.data.procFg != "00") {
            $scope._popMsg(messages["storeOrder.dtl.not.orderProcEnd"]);
            return false;
          }
          $scope.regHdRemark = response.data.data.remark;
        }

        $scope.searchStoreLoan("Y");
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  // 매장여신 조회
  $scope.searchStoreLoan = function (popShowFg) {
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;

    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/storeLoan.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          // 발주중지 상태이면 상품추가/변경 불가
          if (response.data.data.orderCloseYn === "Y") {
            $scope._popMsg(messages["storeOrder.dtl.orderClose"]);
            return false;
          } else {
            // 주문가능금액이 있으면
            if (response.data.data.availableOrderAmt !== null) {
              $scope.prevOrderTot      = response.data.data.prevOrderTot;      //이전 주문금액
              $scope.limitLoanAmt      = response.data.data.limitLoanAmt;      //여신 한도액
              $scope.currLoanAmt       = response.data.data.currLoanAmt;       //여신잔액
              $scope.maxOrderAmt       = response.data.data.maxOrderAmt;       //1회주문한도
              $scope.noOutstockAmtFg   = response.data.data.noOutstockAmtFg;   //미출고금액고려여부
              $scope.availableOrderAmt = response.data.data.availableOrderAmt; //주문가능액

              //미출고금액 고려여부 사용인 경우
              if ($scope.noOutstockAmtFg === "Y") {
                if (parseInt($scope.availableOrderAmt) <= (parseInt($scope.currLoanAmt) - parseInt($scope.prevOrderTot))) {
                  // 해당 조건에는 조회해 온 주문가능액 그대로 사용
                } else if (parseInt($scope.availableOrderAmt) >= (parseInt($scope.currLoanAmt) - parseInt($scope.prevOrderTot)) && parseInt($scope.maxOrderAmt) != 0) {
                  $scope.availableOrderAmt = parseInt($scope.currLoanAmt) - parseInt($scope.prevOrderTot);
                } else {
                  $scope.availableOrderAmt = parseInt($scope.availableOrderAmt) - parseInt($scope.prevOrderTot);
                }
              }

              $("#registStoreLoanInfo").html("1회주문한도액 : " + addComma($scope.maxOrderAmt) + " 여신잔액 : " + addComma($scope.currLoanAmt) + " 미출고액 : " + addComma($scope.prevOrderTot) + " 주문가능액 : " + addComma($scope.availableOrderAmt));
            } else {
              $("#registStoreLoanInfo").html('');
            }
          }
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
      if (popShowFg === "Y") {
        $scope.wjStoreOrderRegistLayer.show(true);
        $("#registSubTitle").html(' (' + messages["storeOrder.reqDate"] + ' : ' + getFormatDate($scope.reqDate, '-') + ')');
      }
    });
  };

  // 주문가능상품 리스트 조회
  $scope.searchStoreOrderRegistList = function () {
    // 파라미터
    var params       = {};
    params.reqDate   = $scope.reqDate;
    params.slipFg    = $scope.slipFg;
    params.startDate = wijmo.Globalize.format($scope.srchRegStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchRegEndDate.value,   'yyyyMMdd');
    params.listScale = 50;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/storeOrder/storeOrderRegist/list.sb", params);
  };

  // 주문 상품 저장 전 출고요청일자에 등록한 주문 총 합계 금액 조회
  $scope.getOrderTotAmt = function(){

    var params       = {};
    params.reqDate   = $scope.reqDate;
    $scope._postJSONQuery.withOutPopUp( "/iostock/order/storeOrder/storeOrder/getOrderTotAmt.sb", params, function(response) {

      if(response.data.data !== null) {
        $scope.saveStoreOrderRegist(response.data.data);
      }

    });
  };

  // 주문 상품 저장
  $scope.saveStoreOrderRegist = function (orderTotAmt) {

    var params   = [];
    var orderTot = 0;
    var grid 			= $scope.flex;
    
    for (var i=0; i<$scope.flex.collectionView.items.length; i++) {
    	var item =  $scope.flex.collectionView.items[i]; 
    	if (item.orderTotQty !== null && item.orderTotQty !== 0 && (parseInt(item.orderTotQty) < parseInt(item.poMinQty))) {
            $scope._popMsg(messages["storeOrder.dtl.prodCd"] +"["+item.prodCd+"]" +" "+ messages["storeOrder.dtl.not.minOrderQty"]); // 주문수량은 최소주문수량 이상 입력하셔야 합니다.
            grid.select(i,6,i,6 );
            return false;
        }    	
    }
    
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      // 이전 주문수량이 없으면서 주문수량 0인 경우 저장하지 않는다.
      if (item.prevOrderTotQty === null && item.orderTotQty === 0) {
        continue;
      }

//      if (item.orderTotQty !== null && item.orderTotQty !== 0 && (parseInt(item.orderTotQty) < parseInt(item.poMinQty))) {
//        $scope._popMsg(messages["storeOrder.dtl.not.minOrderQty"]); // 주문수량은 최소주문수량 이상 입력하셔야 합니다.
//        grid.select(i,6,i,6 );
//        return false;
//      }
      if (item.orderEtcQty !== null && (parseInt(item.orderEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["storeOrder.dtl.not.orderEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
    //if (item.orderTot !== null && (parseInt(item.orderTot) > 9999999999)) {
      if (item.orderTot !== null && (parseInt(item.orderTot) > 999999999 )) {
        $scope._popMsg(messages["storeOrder.dtl.not.overOrderTot"]); // 주문금액이 너무 큽니다.
        return false;
      }

      item.status    = "U";
      item.reqDate   = $scope.reqDate;
      item.slipFg    = $scope.slipFg;
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.hdRemark  = $scope.regHdRemark;

      if(item.prevOrderTot !== null){
          orderTot += parseInt(item.orderTot - item.prevOrderTot); //  합계 - 기주문수량금액 = 추가한 주문수량의 금액 합계
      }else{
          orderTot += parseInt(item.orderTot);
      }

      params.push(item);
    }

    // 상품 주문 합계 + 이전에 등록한 주문 총 합계
    orderTot += parseInt(orderTotAmt);

    // 1회주문한도액과 주문가능금액 중 적은금액을 기준으로 주문가능금액 체크
    var approvalAmt = $scope.maxOrderAmt > $scope.availableOrderAmt ? $scope.availableOrderAmt : $scope.maxOrderAmt;

    // 주문가능액 체크
    if (approvalAmt != null) {
      if (parseInt(approvalAmt) < parseInt(orderTot)) {
        $scope._popMsg(messages["storeOrder.dtl.orderTotOver"]);
        return false;
      }
    }

    $scope._save("/iostock/order/storeOrder/storeOrderRegist/save.sb", params, function () {
      $scope.saveRegistCallback()
    });
  };


  // 저장 후 콜백 서치 함수
  $scope.saveRegistCallback = function () {
    $scope.searchStoreOrderRegistList();
    $scope.searchStoreLoan("N");

    // 신규 요청등록인 경우
    if ($scope.callParent === "storeOrder") {
      var storeOrderScope = agrid.getScope('storeOrderCtrl');
      storeOrderScope.searchStoreOrderList();
    }
    // 주문 상품상세내역 페이지에서 호출한 경우
    else if ($scope.callParent === "storeOrderDtl") {
      var storeOrderScope = agrid.getScope('storeOrderCtrl');
      storeOrderScope.searchStoreOrderList();

      var storeOrderDtlScope = agrid.getScope('storeOrderDtlCtrl');
      storeOrderDtlScope.searchStoreOrderDtlList();
    }
  };


  // 안전재고 수량적용.
  $scope.setSafeToOrder = function () {
    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
    // 데이터 처리중 팝업 띄우기위해 $timeout 사용.
    $timeout(function () {
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.safeStockUnitQty !== null || item.safeStockEtcQty !== null) {
          $scope.flex.collectionView.editItem(item);

          if (nvl(item.safeStockUnitQty, 0) > 0) {
            item.orderUnitQty = parseInt(item.safeStockUnitQty) - parseInt(nvl(item.storeCurUnitQty, 0));
          }
          if (nvl(item.safeStockEtcQty, 0) > 0) {
            item.orderEtcQty = parseInt(item.safeStockEtcQty) - parseInt(nvl(item.storeCurEtcQty, 0));
          }
          $scope.calcAmt(item);
          $scope.flex.collectionView.commitEdit();
        }
      }
      $scope.$broadcast('loadingPopupInactive');
    }, 100);
  };

  // 옵션2 값 변경 이벤트 함수
  $scope.selectedIndexChanged = function (s, e) {
    if (s.selectedValue === "") {
      $scope.option2LayerHide();
    } else {
      $scope.option2LayerHide();
      $("#option2DateLayer").show();

      if (s.selectedValue === "ORD") {
        $("#option2OrdLayer").show();
        $("#option2OrdLayer2").show();
      } else if (s.selectedValue === "OUT") {
        $("#option2OutLayer").show();
        $("#option2OutLayer2").show();
      } else if (s.selectedValue === "SALE") {
        $("#option2SaleLayer").show();
        $("#option2SaleLayer2").show();
      }
    }
  };


  $scope.option2LayerHide = function () {
    $("#option2DateLayer").hide();
    $("#option2OrdLayer").hide();
    $("#option2OrdLayer2").hide();
    $("#option2OutLayer").hide();
    $("#option2OutLayer2").hide();
    $("#option2SaleLayer").hide();
    $("#option2SaleLayer2").hide();
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
    if ($scope.flex.rows.length <= 0) {
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
    /** 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐 */
    var uploadFg         = 'order';

    // 엑셀 양식다운로드
    if (prcsFg === 'excelFormDown') {
      excelUploadScope.excelFormDownload(uploadFg);
    } else {
      var msg = messages["excelUploadMPS.confmMsg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
      s_alert.popConf(msg, function () {
        excelUploadScope.uploadFg = uploadFg;
        /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
        excelUploadScope.parentCtrl = 'storeOrderRegistCtrl';
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


  /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성 */
  $scope.uploadCallBack = function () {
    var params      = {};
    params.date     = $scope.reqDate;
    params.slipFg   = $scope.slipFg;
    params.hdRemark = $scope.regHdRemark;
    params.addQtyFg = $scope.addQtyFg;

    var excelUploadScope = agrid.getScope('excelUploadMPSCtrl');

    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }

    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/excelUpload.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 엑셀 에러내역 팝업 호출
        $scope.excelUploadErrInfo();

        // 등록 그리드 및 여신, 부모 그리드 조회
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
    params.uploadFg = 'order';
    $scope._broadcast('excelUploadMPSErrInfoCtrl', params);
  };

}]);
