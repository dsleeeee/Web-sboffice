<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/verdrOrder/vendrOrderProdReg/"/>

<wj-popup id="wjVendrOrderProdRegLayer" control="wjVendrOrderProdRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrOrderProdRegLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrOrderProdRegCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrOrder.reg.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <table class="tblType01" style="position: relative;">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="vendrOrder.reg.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="vendrOrder.reg.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="vendrOrder.reg.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 상품분류 --%>
          <th><s:message code="vendrOrder.reg.prodClass"/></th>
          <td>
            <input type="text" id="srchProdClass" name="prodClass" ng-model="prodClass" class="sb-input w100" maxlength="40"/>
          </td>
        </tr>
        <tr>
          <%-- 안전재고 --%>
          <th><s:message code="vendrOrder.reg.safeStock"/></th>
          <td colspan="3">
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="safeStockFg"
                  ng-model="safeStockFg"
                  items-source="_getComboData('safeStockFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a href="#" class="btn_grayS" ng-click=""><s:message code="vendrOrder.reg.excelFormDownload"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="vendrOrder.reg.excelFormUpload"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="vendrOrder.reg.textFormUpload"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="cmm.excel.down"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="vendrOrder.reg.excelFormUploadErrorInfo"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchVendrOrderRegList();">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="mt20 tr">
        <%-- 최종원가를 발주원가로 세팅 --%>
        <button type="button" class="btn_skyblue ml5" id="btnAddProd" ng-click="setLastCostToOrderCost()">
          <s:message code="vendrOrder.reg.lastCostToOrderCost"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnProdSave" ng-click="save()">
          <s:message code="cmm.save"/></button>
      </div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orgplceCd"/>" binding="orgplceCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.lastCostUprc"/>" binding="lastCostUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.poUnitFg"/>" binding="poUnitFg" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.safeStock"/>" binding="safeStockUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.safeStock"/>" binding="safeStockEtcQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.currQty"/>" binding="currUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.currQty"/>" binding="currEtcQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prevOrderUnitQty"/>" binding="prevOrderUnitQty" width="50" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prevOrderEtcQty"/>" binding="prevOrderEtcQty" width="50" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderUnitQty"/>" binding="orderUnitQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderEtcQty"/>" binding="orderEtcQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prevOrderTotQty"/>" binding="prevOrderTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderTotQty"/>" binding="orderTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderTot"/>" binding="orderTot" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.envst0011"/>" binding="envst0011" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="vendrOrderProdRegCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">

  /** 발주상품 추가/변경 그리드 controller */
  app.controller('vendrOrderProdRegCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrOrderProdRegCtrl', $scope, $http, true));

    $scope._setComboData("safeStockFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["vendrOrder.reg.safeStockY"], "value": "Y"}
    ]);


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "orderEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
            // console.log(item);
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
          // 폐기수량 수정시 금액,VAT,합계 계산하여 보여준다.
          if (col.binding === "orderUnitQty" || col.binding === "orderEtcQty") {
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
      if (nvl(item.orderUnitQty, null) === null || (item.poUnitQty !== 1 && nvl(item.orderEtcQty, null) === null)) return false;

      var costUprc  = parseFloat(item.costUprc);
      var poUnitQty = parseInt(item.poUnitQty);
      var vat01     = parseInt(item.vatFg01);
      var envst0011 = parseInt(item.envst0011);

      var unitQty  = (parseInt(nvl(item.prevOrderUnitQty, 0)) + parseInt(nvl(item.orderUnitQty, 0))) * parseInt(item.poUnitQty);
      var etcQty   = parseInt(nvl(item.prevOrderEtcQty, 0)) + parseInt(nvl(item.orderEtcQty, 0));
      var totQty   = parseInt(unitQty + etcQty);
      var tempAmt  = Math.round(totQty * costUprc / poUnitQty);
      var orderAmt = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
      var orderVat = Math.round(tempAmt * vat01 / (10 + envst0011));
      var orderTot = parseInt(orderAmt + orderVat);

      item.orderTotQty = totQty;   // 총주문수량
      item.orderTot    = orderTot; // 합계
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrOrderProdRegCtrl", function (event, data) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.slipNo = data.slipNo;
      $scope.slipFg = data.slipFg;

      $scope.wjVendrOrderProdRegLayer.show(true);

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 상품 리스트 조회
    $scope.searchVendrOrderRegList = function () {
      // 파라미터
      var params       = {};
      params.slipNo    = $scope.slipNo;
      params.slipFg    = $scope.slipFg;
      params.listScale = 50;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/vendr/vendrOrder/vendrOrderProdReg/list.sb", params);
    };


    // 상품 저장
    $scope.save = function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        // 이전 주문수량이 없으면서 주문수량 0인 경우 저장하지 않는다.
        if (item.prevOrderTotQty === null && item.orderTotQty === 0) {
          continue;
        }
        if (item.orderEtcQty !== null && (parseInt(item.orderEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["vendrOrder.reg.not.orderEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.orderTot !== null && (parseInt(item.orderTot) > 9999999999)) {
          $scope._popMsg(messages["vendrOrder.reg.not.overOrderTot"]); // 주문금액이 너무 큽니다.
          return false;
        }

        item.status    = "U";
        item.slipNo    = $scope.slipNo;
        item.slipFg    = $scope.slipFg;
        item.storageCd = "001";
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

        params.push(item);
      }

      $scope._save("/iostock/vendr/vendrOrder/vendrOrderProdReg/save.sb", params, function () {
        $scope.saveRegistCallback()
      });
    };


    // 저장 후 콜백 서치 함수
    $scope.saveRegistCallback = function () {
      $scope.searchVendrOrderRegList();

      var vendrOrderProdScope = agrid.getScope('vendrOrderProdCtrl');
      vendrOrderProdScope.procFgCheck();
    };


    $scope.setLastCostToOrderCost = function () {
      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
      // 데이터 처리중 팝업 띄우기위해 setTimeout 사용.
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


  }]);

</script>

