<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrInstock/vendrInstockOrderInfoReg/"/>

<wj-popup id="wjVendrInstockOrderInfoRegLayer" control="wjVendrInstockOrderInfoRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrInstockOrderInfoRegLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrInstockOrderInfoRegCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrInstock.ord.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="vendrInstock.ord.txt1"/></li>
      </ul>

      <div class="mt20 tr">
        <%-- 초기화 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDefault" ng-click="setDefault()">
          <s:message code="vendrInstock.ord.default"/></button>
        <%-- 발주내역으로 세팅 --%>
        <button type="button" class="btn_skyblue ml5" id="btnOrderToInstock" ng-click="setOrderToInstock()">
          <s:message code="vendrInstock.ord.setOrderToInstock"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnOrderInfoRegSave" ng-click="save()">
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
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.lastCostUprc"/>" binding="lastCostUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.orderCostUprc"/>" binding="orderCostUprc" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.orderUnitQty"/>" binding="orderUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.orderEtcQty"/>" binding="orderEtcQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inCostUprc"/>" binding="costUprc" width="80" align="right" is-read-only="false" max-length=8></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inUnitQty"/>" binding="inUnitQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inEtcQty"/>" binding="inEtcQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inTotQty"/>" binding="inTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.prevInTotQty"/>" binding="prevInTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inAmt"/>" binding="inAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inVat"/>" binding="inVat" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inTot"/>" binding="inTot" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.vendrVatFg01"/>" binding="vendrVatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">

  /** 입고/반출 상품 추가/변경 그리드 controller */
  app.controller('vendrInstockOrderInfoRegCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrInstockOrderInfoRegCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "orderEtcQty" || col.binding === "inEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
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
      <%-- 수량이 없는 경우 계산하지 않음. null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 --%>
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
      $scope._inquiryMain("/iostock/vendr/vendrInstock/vendrInstockOrderInfoReg/list.sb", params);
    };


    // 상품 저장
    $scope.save = function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        // 이전 주문수량이 없으면서 주문수량 0인 경우 저장하지 않는다.
        if (item.inTotQty === 0) {
          continue;
        }
        if (item.inEtcQty !== null && (parseInt(item.inEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["vendrInstock.ord.not.inEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
          $scope._popMsg(messages["vendrInstock.ord.not.overInTot"]); // 주문금액이 너무 큽니다.
          return false;
        }

        item.status    = "U";
        item.slipNo    = $scope.slipNo;
        item.slipFg    = $scope.slipFg;
        item.storageCd = "001";
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

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


  }])
  ;

</script>

