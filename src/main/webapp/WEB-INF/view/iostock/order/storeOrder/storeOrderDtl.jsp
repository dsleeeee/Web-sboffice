<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeOrder/storeOrderDtl/"/>

<wj-popup id="wjStoreOrderDtlLayer" control="wjStoreOrderDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="storeOrderDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeOrderDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="storeOrder.remark"/></th>
          <td colspan="3">
            <input type="text" id="dtlHdRemark" name="dtlHdRemark" ng-model="dtlHdRemark" class="sb-input w100"/>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="tr mt10">
        <p id="dtlStoreLoanInfo" class="fl s14 bk lh30"></p>
        <%-- 상품추가/변경 --%>
        <button type="button" id="btnAddProd" class="btn_skyblue ml5" ng-click="addProd()" ng-if="btnAddProd">
          <s:message code="storeOrder.addProd"/></button>
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="saveStoreOrderDtl('save')" ng-if="btnDtlSave">
          <s:message code="cmm.save"/></button>
        <%-- 확정 --%>
        <button type="button" id="btnConfirm" class="btn_skyblue ml5" ng-click="saveStoreOrderDtl('confirm')" ng-if="btnConfirm">
          <s:message code="storeOrder.dtl.confirm"/></button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                         binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderSplyUprc"/>" binding="orderSplyUprc" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderTotQty"/>" binding="prevOrderTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderUnitQty"/>" binding="orderUnitQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderEtcQty"/>" binding="orderEtcQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTotQty"/>" binding="orderTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderAmt"/>" binding="orderAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderVat"/>" binding="orderVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTot"/>" binding="orderTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poMinQty"/>" binding="poMinQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.envst0011"/>" binding="envst0011" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 주문등록 상세 그리드 controller */
  app.controller('storeOrderDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOrderDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "097";
      var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
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
      <%-- 수량이 없는 경우 계산하지 않음. null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 --%>
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

      $scope.wjStoreOrderDtlLayer.show(true);
      if ($scope.procFg === "00") {
        $scope.btnAddProd      = true;
        $scope.btnDtlSave      = true;
        $scope.flex.isReadOnly = false;

        if ("${envst1042}" === "1" || "${envst1042}" === "2") {
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

      $("#spanDtlTitle").html(messages["storeOrder.reqDate"] + ' : ' + getFormatDate($scope.reqDate, '-'));
      $scope.searchStoreLoan("Y");
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 매장여신 조회
    $scope.searchStoreLoan = function (popShowFg) {
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.slipFg  = $scope.slipFg;

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

              // 주문가능금액이 있으면
              if(response.data.data.availableOrderAmt !== null) {
                $scope.prevOrderTot      = response.data.data.prevOrderTot;
                $scope.limitLoanAmt      = response.data.data.limitLoanAmt;
                $scope.currLoanAmt       = response.data.data.currLoanAmt;
                $scope.maxOrderAmt       = response.data.data.maxOrderAmt;
                $scope.noOutstockAmtFg   = response.data.data.noOutstockAmtFg;
                $scope.availableOrderAmt = response.data.data.availableOrderAmt;

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

                $("#dtlStoreLoanInfo").html("1회주문한도액 : " + addComma($scope.maxOrderAmt) + " 여신잔액 : " + addComma($scope.currLoanAmt) + " 미출고액 : " + addComma($scope.prevOrderTot) + " 주문가능액 : " + addComma($scope.availableOrderAmt));
              }
              else {
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
      params.reqDate = $scope.reqDate;
      params.slipFg  = $scope.slipFg;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/order/storeOrder/storeOrderDtl/list.sb", params);
    };

    // 주문 상세 저장
    $scope.saveStoreOrderDtl = function (saveFg) {
      var params   = [];
      var orderTot = 0;
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item       = $scope.flex.collectionView.itemsEdited[i];
        item.status    = "U";
        item.reqDate   = $scope.reqDate;
        item.slipFg    = $scope.slipFg;
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리.
        item.hdRemark  = $scope.dtlHdRemark;
        orderTot += parseInt(item.orderTot);
        params.push(item);
      }

      // 파라미터 길이체크
      if (params.length <= 0) {
        // 수정된 파라미터가 없더라도 확정은 진행되어야함.
        if (saveFg === "confirm") {
          $scope.confirm();
        } else {
          $scope._popMsg(messages["cmm.not.modify"]);
        }
        return false;
      } else {
        params = JSON.stringify(params);
      }

      if ($scope.availableOrderAmt != null) {
        if (parseInt($scope.availableOrderAmt) < parseInt(orderTot)) {
          $scope._popMsg(messages["storeOrder.dtl.orderTotOver"]);
          return false;
        }
      }

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/order/storeOrder/storeOrderRegist/save.sb', /* 통신할 URL */
        data   : params, /* 파라메터로 보낼 데이터 */
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

    // 주문확정
    $scope.confirm = function () {
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.slipFg  = $scope.slipFg;
      params.remark  = $scope.dtlHdRemark;

      $scope._save("/iostock/order/storeOrder/storeOrderDtl/confirm.sb", params, function () {
        $scope.saveOrderDtlCallback()
      });
    };

    // 저장 후 콜백 함수
    $scope.saveOrderDtlCallback = function () {
      $scope.searchStoreOrderDtlList();
      $scope.searchStoreLoan("N");

      var storeOrderScope = agrid.getScope('storeOrderCtrl');
      storeOrderScope.searchStoreOrderList();
    };

    // 상품추가/변경
    $scope.addProd = function () {
      var params        = {};
      params.callParent = "storeOrderDtl";
      params.reqDate    = $scope.reqDate;
      params.slipFg     = $scope.slipFg;
      params.hdRemark   = $scope.dtlHdRemark
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
</script>
