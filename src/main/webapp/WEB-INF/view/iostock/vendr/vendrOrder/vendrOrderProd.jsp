<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrOrder/vendrOrderProd/"/>

<div id="prodView" style="display: none;" ng-controller="vendrOrderProdCtrl">
  <ul class="txtSty3 mt10">
    <li class="red"><s:message code="vendrOrder.prod.txt1"/></li>
  </ul>

  <div class="mt20 tr">
    <%-- 추가/변경 --%>
    <button type="button" class="btn_skyblue ml5" id="btnAddProd" ng-click="addProd()" ng-if="btnAddProdFg">
      <s:message code="vendrOrder.prod.addProd"/></button>
    <%-- 저장 --%>
    <button type="button" class="btn_skyblue ml5" id="btnProdSave" ng-click="save()" ng-if="btnProdSaveFg">
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
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderUnitQty"/>" binding="orderUnitQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderEtcQty"/>" binding="orderEtcQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderTotQty"/>" binding="orderTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.prevOrderTotQty"/>" binding="prevOrderTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderAmt"/>" binding="orderAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderVat"/>" binding="orderVat" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderTot"/>" binding="orderTot" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.vendrVatFg01"/>" binding="vendrVatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>


<script type="text/javascript">

  /** 발주상품 그리드 controller */
  app.controller('vendrOrderProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrOrderProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "097";
      var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
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
          // 원가 및 수량 수정시 합계 계산하여 보여준다.
          if (col.binding === "costUprc" || col.binding === "orderUnitQty" || col.binding === "orderEtcQty") {
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
      var costUprc     = parseFloat(item.costUprc);
      var poUnitQty    = parseInt(item.poUnitQty);
      var vat01        = parseInt(item.vatFg01);
      var vendrVatFg01 = parseInt(item.vendrVatFg01);

      var unitQty  = parseInt(nvl(item.orderUnitQty, 0)) * parseInt(item.poUnitQty);
      var etcQty   = parseInt(nvl(item.orderEtcQty, 0));
      var totQty   = parseInt(unitQty + etcQty);
      var tempAmt  = Math.round(totQty * costUprc / poUnitQty);
      var orderAmt = tempAmt - Math.round(tempAmt * vat01 * vendrVatFg01 / 11);
      var orderVat = Math.round(tempAmt * vat01 / (10 + vendrVatFg01));
      var orderTot = parseInt(orderAmt + orderVat);

      item.orderTotQty = totQty;   // 총주문수량
      item.orderAmt    = orderAmt; // 금액
      item.orderVat    = orderVat; // VAT
      item.orderTot    = orderTot; // 합계
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrOrderProdCtrl", function (event, data) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.slipNo  = data.slipNo;
      $scope.slipFg  = data.slipFg;
      $scope.vendrCd = data.vendrCd;

      $scope.procFgCheck();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 발주 진행구분 체크
    $scope.procFgCheck = function () {
      var params    = {};
      params.slipNo = $scope.slipNo;
      params.slipFg = $scope.slipFg;

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/vendr/vendrOrder/vendrOrderProd/procFgCheck.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          // 진행구분이 등록이 아니면 상품추가/변경 불가
          if (!$.isEmptyObject(response.data.data)) {
            // 등록 상태이면 버튼 show
            if (response.data.data.procFg != "" && response.data.data.procFg == "0") {
              $scope.btnAddProdFg  = true;
              $scope.btnProdSaveFg = true;
            } else {
              $scope.btnAddProdFg  = false;
              $scope.btnProdSaveFg = false;
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
        $scope.searchVendrOrderProdList();
      });
    };


    // 상품 리스트 조회
    $scope.searchVendrOrderProdList = function () {
      // 파라미터
      var params     = {};
      params.slipNo  = $scope.slipNo;
      params.slipFg  = $scope.slipFg;
      params.vendrCd = $scope.vendrCd;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/vendr/vendrOrder/vendrOrderProd/list.sb", params);
    };


    // 상품 저장
    $scope.save = function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

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
      $scope.searchVendrOrderProdList();
    };


    // 상품추가
    $scope.addProd = function () {
      var params     = {};
      params.slipNo  = $scope.slipNo;
      params.slipFg  = $scope.slipFg;
      params.vendrCd = $scope.vendrCd;
      $scope._broadcast('vendrOrderProdRegCtrl', params);
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

<%-- 상품 등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderProdReg.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
