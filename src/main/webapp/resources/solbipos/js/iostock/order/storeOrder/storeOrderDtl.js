/****************************************************************
 *
 * 파일명 : storeOrderDtl.js
 * 설  명 : 주문등록 상품 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.10     안동관      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 주문등록 상세 그리드 controller */
app.controller('storeOrderDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeOrderDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "093";
    var url                 = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "orderEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
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
      if (col.binding === "orderEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
        var dataItem = s.rows[elements.row].dataItem;
        if (dataItem.poUnitQty === 1) {
          elements.cancel = true;
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "orderUnitQty" || col.binding === "orderEtcQty") { // 주문수량 수정시
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
        if (col.isReadOnly) {
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

    var unitQty  = parseInt(nvl(item.orderUnitQty, 0)) * parseInt(item.poUnitQty);
    var etcQty   = parseInt(nvl(item.orderEtcQty, 0));
    var totQty   = parseInt(unitQty + etcQty);
    var tempAmt  = Math.round(totQty * orderSplyUprc / poUnitQty);
    var orderAmt = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
    var orderVat = Math.round(tempAmt * vat01 / (10 + envst0011));
    var orderTot = parseInt(orderAmt + orderVat);

    item.orderTotQty = totQty;   // 총주문수량
    item.orderAmt    = orderAmt; // 금액
    item.orderVat    = orderVat; // VAT
    item.orderTot    = orderTot; // 합계
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeOrderDtlCtrl", function (event, data) {
    $scope.reqDate     = data.reqDate;
    $scope.slipFg      = data.slipFg;
    $scope.procFg      = data.procFg;
    $scope.dtlHdRemark = data.hdRemark;
    $scope.vendrCd     = data.vendrCd;
    $scope.orderSlipNo = data.orderSlipNo;

    // 당일보다 이전일자 요청등록 불가
    var today = getCurDate();
    if (parseInt(today) > parseInt($scope.reqDate)) {

        if ($scope.procFg === "00") { // 진행구분이 '등록' 인 경우
            $scope.btnAddProd = false;
            $scope.btnDtlSave = true;
            $scope.btnConfirm = false;
            $scope.flex.isReadOnly = false;
        }else{
            $scope._popMsg(messages["storeOrder.dtl.not.prevDateOrder"]); // 당일보다 이전일자로 요청등록을 하실 수 없습니다.
            $scope.btnAddProd = false;
            $scope.btnDtlSave = false;
            $scope.btnConfirm = false;
            $scope.flex.isReadOnly = true;
            //return false;
        }
    } else {
      if ($scope.procFg === "00") {
        $scope.btnAddProd      = true;
        $scope.btnDtlSave      = true;
        $scope.flex.isReadOnly = false;

        if (gEnvst1042 === "1" || gEnvst1042 === "2") {
          $scope.btnConfirm = true;
        } else {
          $scope.btnConfirm = false;
        }
      } else {
        $scope.btnAddProd      = false;
        $scope.btnDtlSave      = false;
        $scope.btnConfirm      = false;
        $scope.flex.isReadOnly = true;
      }
    }

    $scope.wjStoreOrderDtlLayer.show(true);

    $("#spanDtlTitle").html(messages["storeOrder.reqDate"] + ' : ' + getFormatDate($scope.reqDate, '-') + ' / ' + messages["storeOrder.orderSlipNo"] + ' : ' + nvl($scope.orderSlipNo, ''));
    $scope.searchStoreLoan("Y");

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장여신 조회
  $scope.searchStoreLoan = function (popShowFg) {
    var params     = {};
    params.orderSlipNo = $scope.orderSlipNo; // 주문전표번호

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
          if (response.data.data.orderCloseYn === "Y") {
            $scope.flex.isReadOnly = true;
            $scope._popMsg(messages["storeOrder.dtl.orderClose"]);
          } else {
            $scope.flex.isReadOnly = false;

              // 주문가능액
              $scope.availableOrderAmt = null;

              // 신규등록일 때
              if(params.orderSlipNo === null || params.orderSlipNo === undefined || params.orderSlipNo === ""){
                  if (response.data.data.availableOrderAmt !== null) {
                      $scope.availableOrderAmt = response.data.data.availableOrderAmt;        // 주문가능액
                  }
              }else{ // 전표수정일 때
                  if (response.data.data.availableOrderAmtSave !== null) {
                      $scope.availableOrderAmt = response.data.data.availableOrderAmtSave;    // 주문가능액
                  }
              }

            // 주문가능금액이 있으면
            if ($scope.availableOrderAmt !== null) {
              $scope.prevOrderTot      = response.data.data.prevOrderTot;
              $scope.limitLoanAmt      = response.data.data.limitLoanAmt;
              $scope.currLoanAmt       = response.data.data.currLoanAmt;
              $scope.maxOrderAmt       = response.data.data.maxOrderAmt;
              $scope.noOutstockAmtFg   = response.data.data.noOutstockAmtFg;

              $("#dtlStoreLoanInfo").html("1회주문한도액 : " + addComma($scope.maxOrderAmt) + " 여신한도액 : " + addComma($scope.limitLoanAmt) + " 미출고액 : " + addComma($scope.prevOrderTot) + " 주문가능액 : " + addComma($scope.availableOrderAmt));
            } else {
              $("#dtlStoreLoanInfo").html('');
            }
          }
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
      if (popShowFg === "Y") {
        $scope.wjStoreOrderDtlLayer.show(true);
        $scope.searchStoreOrderDtlList();
      }
    });
  };

  // 주문등록 상세내역 리스트 조회
  $scope.searchStoreOrderDtlList = function () {
    // 파라미터
    var params     = {};
    params.slipFg  = $scope.slipFg;
    params.orderSlipNo = $scope.orderSlipNo;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/order/storeOrder/storeOrderDtl/list.sb", params);
  };

  // 저장, 주문확정 클릭 (주문가능한지 체크(저장, 확전 전에 체크))
  $scope.storeCloseCheck = function (saveFg, orderTotAmt){

    var params     = {};
    params.reqDate = $scope.reqDate;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
      params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // 출고요청가능일인지 여부 체크
    // ajax 통신 설정
    $http({
        method: 'POST', //방식
        url: '/iostock/order/storeOrder/storeOrderRegist/orderDateCheck.sb', /* 통신할 URL */
        params: params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
          if (!$.isEmptyObject(response.data.data)) {
              if (response.data.data.chkResult !== 'Y') {
                  $scope._popMsg(response.data.data.chkResult);
                  return false;
              }
          }
        if(saveFg === '' && orderTotAmt === ''){          // 저장버튼

          // 주문 수정 시, 해당 주문전표의 현재 주문 총 합계 금액 조회
          $scope.getOrderTotAmt();
        }
        if(saveFg === 'confirm' && orderTotAmt === '0'){  // 확정버튼
          $scope.saveStoreOrderDtl(saveFg, orderTotAmt);
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
    });
  };

  // 주문 수정 시, 해당 주문전표의 현재 주문 총 합계 금액 조회
  $scope.getOrderTotAmt = function(){

    var params = {};
    params.vendrCd = $scope.vendrCd;
    params.orderSlipNo = $scope.orderSlipNo;

    $scope._postJSONQuery.withOutPopUp( "/iostock/order/storeOrder/storeOrder/getOrderTotAmt.sb", params, function(response) {

      if(response.data.data !== null) {
        $scope.saveStoreOrderDtl('save', response.data.data)
      }

    });
  };

  // 주문 상세 저장
  $scope.saveStoreOrderDtl = function (saveFg, orderTotAmt) {
    var params   = [];
    var orderTot = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item       = $scope.flex.collectionView.itemsEdited[i];
      item.status    = "U";
      item.reqDate   = $scope.reqDate;
      item.slipFg    = $scope.slipFg;
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리.
      item.hdRemark  = $scope.dtlHdRemark;
      item.vendrCd   = $scope.vendrCd;
      item.orderSlipNo = $scope.orderSlipNo;

      if (item.orderTotQty !== null && item.orderTotQty !== 0 && (parseInt(item.orderTotQty) < parseInt(item.poMinQty))) {
          $scope._popMsg(messages["storeOrder.dtl.prodCd"] +"["+item.prodCd+"]" +" "+ messages["storeOrder.dtl.not.minOrderQty"]); // 주문수량은 최소주문수량 이상 입력하셔야 합니다.
          grid.select(i,6,i,6 );
          return false;
      }

      if(item.prevOrderTot !== null){
        orderTot += parseInt(item.orderTot - item.prevOrderTot); //  합계 - 기주문수량금액 = 추가한 주문수량의 금액 합계
      }else{
        orderTot += parseInt(item.orderTot);
      }

      params.push(item);
    }

    // 상품 주문 합계 + 이전에 등록한 주문 총 합계
    orderTot += parseInt(orderTotAmt);

    // 파라미터 길이체크
    if (params.length <= 0) {
      // 저장시, 변경사항이 없으면 return
      if(saveFg === "save") {
          $scope._popMsg(messages["cmm.not.modify"]); //cmm.not.modify=변경 사항이 없습니다.
          return false;
      }
    } else {
      params = JSON.stringify(params);
    }

    // 매장여신 조회해 주문 가능여부 다시한번 체크
    var params2 = {};
    params2.orderSlipNo = $scope.orderSlipNo; // 주문전표번호, 신규 요청등록인 경우 null, 주문 상품상세내역 페이지에서 호출한 경우 값 존재

    //가상로그인 session 설정
    if (document.getElementsByName('sessionId')[0]) {
      params2['sid'] = document.getElementsByName('sessionId')[0].value;
    }

      // ajax 통신 설정
      $http({
          method: 'POST', //방식
          url: '/iostock/order/storeOrder/storeOrderRegist/storeLoan.sb', /* 통신할 URL */
          params: params2, /* 파라메터로 보낼 데이터 */
          headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
          if ($scope._httpStatusCheck(response, true)) {
              if (!$.isEmptyObject(response.data.data)) {
                  // 발주중지 상태이면 상품추가/변경 불가
                  if (response.data.data.orderCloseYn === "Y") {
                      $scope.flex.isReadOnly = true;
                      $scope._popMsg(messages["storeOrder.dtl.orderClose"]);
                  } else {
                      $scope.flex.isReadOnly = false;

                      // 주문가능액
                      $scope.availableOrderAmt = null;

                      // 신규등록일 때
                      if(params2.orderSlipNo === null || params2.orderSlipNo === undefined || params2.orderSlipNo === ""){
                          if (response.data.data.availableOrderAmt !== null) {
                              $scope.availableOrderAmt = response.data.data.availableOrderAmt;        // 주문가능액
                          }
                      }else{ // 전표수정일 때
                          if (response.data.data.availableOrderAmtSave !== null) {
                              $scope.availableOrderAmt = response.data.data.availableOrderAmtSave;    // 주문가능액
                          }
                      }

                      // 주문가능금액이 있으면
                      if ($scope.availableOrderAmt !== null) {
                          $scope.prevOrderTot = response.data.data.prevOrderTot;
                          $scope.limitLoanAmt = response.data.data.limitLoanAmt;
                          $scope.currLoanAmt = response.data.data.currLoanAmt;
                          $scope.maxOrderAmt = response.data.data.maxOrderAmt;
                          $scope.noOutstockAmtFg = response.data.data.noOutstockAmtFg;

                          $("#dtlStoreLoanInfo").html("1회주문한도액 : " + addComma($scope.maxOrderAmt) + " 여신한도액 : " + addComma($scope.limitLoanAmt) + " 미출고액 : " + addComma($scope.prevOrderTot) + " 주문가능액 : " + addComma($scope.availableOrderAmt));
                      } else {
                          $("#dtlStoreLoanInfo").html('');
                      }

                      // 총 주문금액이 주문가능금액보다 이하면 주문가능
                      if ($scope.availableOrderAmt >= (parseInt(orderTot) -  parseInt(orderTotAmt))) { // 주문수량에 기주문도 포함될 수 있기 때문에, 기주문 제거하고 현재 주문하려는 금액으로만 비교한다.

                          // 수정한 값 없이 확정하는 경우, 바로 확정 진행
                          if (params.length <= 0) {
                              if (saveFg === "confirm") {
                                  $scope.confirm();
                              }
                          } else {

                              // 주문 저장
                              $scope._postJSONSave.withPopUp("/iostock/order/storeOrder/storeOrderRegist/save.sb", params, function (response) {
                                  if (response.data.data !== null) {
                                      $scope.flex.collectionView.clearChanges();

                                      // 확정버튼 클릭했다면, 주문 저장 후, 확정 진행
                                      if (saveFg === "confirm") {
                                          $scope.confirm();
                                      } else if (saveFg === "save") {
                                          $scope.saveOrderDtlCallback();
                                      }
                                  }
                              });
                          }

                      } else {
                          $scope._popMsg(messages["storeOrder.dtl.orderTotOver"]);
                          return false;
                      }
                  }
              }else{ // 여신 미사용인 경우, 여신과 상관없이 주문
                    // 수정한 값 없이 확정하는 경우, 바로 확정 진행
                    if (params.length <= 0) {
                        if (saveFg === "confirm") {
                            $scope.confirm();
                        }
                    } else {

                        // 주문 저장
                        $scope._postJSONSave.withPopUp("/iostock/order/storeOrder/storeOrderRegist/save.sb", params, function (response) {
                            if (response.data.data !== null) {
                                $scope.flex.collectionView.clearChanges();

                                // 확정버튼 클릭했다면, 주문 저장 후, 확정 진행
                                if (saveFg === "confirm") {
                                    $scope.confirm();
                                } else if (saveFg === "save") {
                                    $scope.saveOrderDtlCallback();
                                }
                            }
                        });
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

      });




    //가상로그인 session 설정
   /* var sParam = {};
    if(document.getElementsByName('sessionId')[0]){
        sParam['sid'] = document.getElementsByName('sessionId')[0].value;
    }*/

    // ajax 통신 설정
    /*$http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/save.sb', /!* 통신할 URL *!/
      data   : params, /!* 파라메터로 보낼 데이터 *!/
      params : sParam,
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.flex.collectionView.clearChanges();

        // 확정버튼 클릭시에도 변경사항 저장 후에 확정 로직을 진행해야하므로 저장 후에 확정로직 다시 실행.
        if (saveFg === "confirm") {
          $scope.confirm();
        } else if (saveFg === "save") {
          $scope.saveOrderDtlCallback();
        }
      }else{
        $scope.saveOrderDtlCallback();
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });*/
  };

  // 주문확정
  $scope.confirm = function () {
      var params = {};
      params.reqDate = $scope.reqDate;
      params.slipFg = $scope.slipFg;
      params.remark = $scope.dtlHdRemark;
      params.envst1042 = gEnvst1042;
      params.vendrCd = $scope.vendrCd;
      params.orderSlipNo = $scope.orderSlipNo;

      //여신잔액 check를 위한 추가 - START
      var orderTot = 0;
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
          var item = $scope.flex.collectionView.items[i];

          orderTot += parseInt(item.orderTot, 0);
      }
      params.orderTot = orderTot;
      //여신잔액 check를 위한 추가 - END

      //console.log('params:' + JSON.stringify(params));
      $scope._save("/iostock/order/storeOrder/storeOrderDtl/confirm.sb", params, function () {
          $scope.saveOrderDtlCallback()
      });
  };

  // 저장 후 콜백 함수
  $scope.saveOrderDtlCallback = function () {
    $scope.searchStoreOrderDtlList();

    // 매장여신 조회 재조회
    $scope.searchStoreLoan("N");

    // 주문진행구분을 체크하여, 버튼 show/hidden 처리
    $scope.orderProcFgCheck();

    var storeOrderScope = agrid.getScope('storeOrderCtrl');
    storeOrderScope.searchStoreOrderList();
  };

  // 상품추가/변경
  $scope.addProd = function () {
    var params        = {};
    params.callParent = "storeOrderDtl";
    params.reqDate    = $scope.reqDate;
    params.slipFg     = $scope.slipFg;
    params.hdRemark   = $scope.dtlHdRemark;
    params.vendrCd    = $scope.vendrCd;
    params.orderSlipNo = $scope.orderSlipNo;
    $scope._broadcast("storeOrderRegistCtrl", params);
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

  // 주문진행구분을 체크하여, 버튼 show/hidden 처리
  $scope.orderProcFgCheck = function () {
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.slipFg  = $scope.slipFg;
      params.orderSlipNo = $scope.orderSlipNo;

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
            if (response.data.data.procFg == "00") {
              $scope.btnAddProd      = true;
              $scope.btnDtlSave      = true;
              $scope.flex.isReadOnly = false;

              if (gEnvst1042 === "1" || gEnvst1042 === "2") {
                $scope.btnConfirm = true;
              } else {
                $scope.btnConfirm = false;
              }
            }else{
              $scope.btnAddProd      = false;
              $scope.btnDtlSave      = false;
              $scope.btnConfirm      = false;
              $scope.flex.isReadOnly = true;
            }
            $scope.regHdRemark = response.data.data.remark;
          }

          $scope.searchStoreLoan("N");
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
              //'주문등록_상세_' + getToday() + '.xlsx',
                '주문등록_상세_' + getCurDate('-') + '.xlsx',
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
