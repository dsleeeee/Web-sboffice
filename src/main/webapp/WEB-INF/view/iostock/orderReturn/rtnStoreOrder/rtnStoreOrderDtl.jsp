<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/"/>

<wj-popup id="wjRtnStoreOrderDtlLayer" control="wjRtnStoreOrderDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="rtnStoreOrderDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnStoreOrderDtlCtrl">
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
          <th><s:message code="rtnStoreOrder.remark"/></th>
          <td colspan="3">
            <input type="text" id="dtlHdRemark" name="dtlHdRemark" ng-model="dtlHdRemark" class="sb-input w100"/>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="tr mt10">
        <p id="dtlStoreLoanInfo" class="fl s14 bk lh30"></p>
        <%-- 상품추가/변경 --%>
        <button type="button" id="btnAddProd" class="btn_skyblue ml5" ng-click="addProd()" style="display:none"><s:message code="rtnStoreOrder.addProd"/></button>
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="saveRtnStoreOrderDtl('save')" style="display:none"><s:message code="cmm.save"/></button>
        <%-- 확정 --%>
        <button type="button" id="btnConfirm" class="btn_skyblue ml5" ng-click="saveRtnStoreOrderDtl('confirm')" style="display:none"><s:message code="rtnStoreOrder.dtl.confirm"/></button>
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
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderSplyUprc"/>" binding="orderSplyUprc" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prevOrderTotQty"/>" binding="prevOrderTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderUnitQty"/>" binding="orderUnitQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderEtcQty"/>" binding="orderEtcQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderTotQty"/>" binding="orderTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderAmt"/>" binding="orderAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderVat"/>" binding="orderVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderTot"/>" binding="orderTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.poMinQty"/>" binding="poMinQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 반품등록 상세 그리드 controller */
  app.controller('rtnStoreOrderDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnStoreOrderDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "orderEtcQty") { // 입수에 따라 반품수량 컬럼 readonly 컨트롤
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
          if (col.binding === "orderUnitQty" || col.binding === "orderEtcQty") { // 반품수량 수정시
            var item          = s.rows[e.row].dataItem;
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

            item.orderTotQty = totQty;   // 총반품수량
            item.orderAmt    = orderAmt; // 금액
            item.orderVat    = orderVat; // VAT
            item.orderTot    = orderTot; // 합계
          }
        }

        s.collectionView.commitEdit();
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnStoreOrderDtlCtrl", function (event, data) {
      $scope.reqDate     = data.reqDate;
      $scope.slipFg      = data.slipFg;
      $scope.procFg      = data.procFg;
      $scope.dtlHdRemark = data.hdRemark;
      $scope.storeCd     = data.storeCd;

      $scope.wjRtnStoreOrderDtlLayer.show(true);
      if ($scope.procFg === "00") {
        $("#btnAddProd").show();
        $("#btnDtlSave").show();
        $("#btnConfirm").show();
        $scope.flex.isReadOnly = false;
      }
      else {
        $("#btnAddProd").hide();
        $("#btnDtlSave").hide();
        $("#btnConfirm").hide();
        $scope.flex.isReadOnly = true;
      }

      $("#spanDtlTitle").html('<s:message code="rtnStoreOrder.reqDate"/> : ' + getFormatDate($scope.reqDate, '-'));
      $scope.wjRtnStoreOrderDtlLayer.show(true);
      $scope.searchRtnStoreOrderDtlList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 반품등록 상세내역 리스트 조회
    $scope.searchRtnStoreOrderDtlList = function () {
      // 파라미터
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.slipFg  = $scope.slipFg;
      params.storeCd = $scope.storeCd;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/list.sb", params);
    };

    // 반품 상세 저장
    $scope.saveRtnStoreOrderDtl = function (saveFg) {
      var params   = new Array();
      var orderTot = 0;
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item       = $scope.flex.collectionView.itemsEdited[i];
        item.status    = "U";
        item.reqDate   = $scope.reqDate;
        item.slipFg    = $scope.slipFg;
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리.
        item.hdRemark  = $scope.dtlHdRemark;
        item.storeCd   = $scope.storeCd;
        orderTot += parseInt(item.orderTot);
        params.push(item);
      }

      // 파라미터 길이체크
      if (params.length <= 0) {
        // 수정된 파라미터가 없더라도 확정은 진행되어야함.
        if (saveFg === "confirm") {
          $scope.confirm();
        }
        else {
          $scope._popMsg(messages["cmm.not.modify"]);
        }
        return false;
      } else {
        params = JSON.stringify(params);
      }

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderRegist/save.sb', /* 통신할 URL */
        data   : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope.httpStatusCheck(response)) {
          $scope._popMsg(messages["cmm.saveSucc"]);
          $scope.flex.collectionView.clearChanges();

          // 확정버튼 클릭시에도 변경사항 저장 후에 확정 로직을 진행해야하므로 저장 후에 확정로직 다시 실행.
          if (saveFg === "confirm") {
            $scope.confirm();
          }
          else if (saveFg === "save") {
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

    // 반품확정
    $scope.confirm = function () {
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.slipFg  = $scope.slipFg;
      params.remark  = $scope.dtlHdRemark;
      params.storeCd = $scope.storeCd;

      $scope._save("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/confirm.sb", params, function () {
        $scope.saveOrderDtlCallback()
      });
    };

    // 저장 후 콜백 함수
    $scope.saveOrderDtlCallback = function () {
      $scope.searchRtnStoreOrderDtlList();

      var rtnStoreOrderScope = agrid.getScope('rtnStoreOrderCtrl');
      rtnStoreOrderScope.searchRtnStoreOrderList();
    };

    // 상품추가/변경
    $scope.addProd = function () {
      var params        = {};
      params.callParent = "rtnStoreOrderDtl";
      params.reqDate    = $scope.reqDate;
      params.slipFg     = $scope.slipFg;
      params.hdRemark   = $scope.dtlHdRemark;
      params.storeCd    = $scope.storeCd;
      $scope._broadcast("rtnStoreOrderRegistCtrl", params);
    };

    // http 조회 후 status 체크
    $scope.httpStatusCheck = function (res) {
      if (res.data.status === "OK") {
        return true;
      }
      else if (res.data.status === "FAIL") {
        $scope._popMsg("Ajax Fail By HTTP Request");
        return false;
      }
      else if (res.data.status === "SESSION_EXFIRE") {
        $scope._popMsg(res.data.message, function () {
          location.href = res.data.url;
        });
        return false;
      }
      else if (res.data.status === "SERVER_ERROR") {
        $scope._popMsg(res.data.message);
        return false;
      }
      else {
        var msg = res.data.status + " : " + res.data.message;
        $scope._popMsg(msg);
        return false;
      }
    };

  }]);
</script>
