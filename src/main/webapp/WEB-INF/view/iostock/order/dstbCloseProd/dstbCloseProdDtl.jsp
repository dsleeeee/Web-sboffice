<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbCloseProd/dstbCloseProdDtl/"/>

<wj-popup id="wjDstbCloseProdDtlLayer" control="wjDstbCloseProdDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dstbCloseProdDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbCloseProdDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">

      <ul class="txtSty3">
        <li class="red"><s:message code="dstbCloseProd.dtl.txt1"/></li>
        <li class="red"><s:message code="dstbCloseProd.dtl.txt2"/></li>
        <li class="red"><s:message code="dstbCloseProd.dtl.txt3"/></li>
        <li class="red"><s:message code="dstbCloseProd.dtl.txt4"/></li>
      </ul>

      <div class="tr mt20">
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="save()"><s:message code="cmm.save"/></button>
      </div>
      <div style="clear: both;"></div>

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
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                            binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg"/>" binding="orderFg" width="80" align="center" is-read-only="true" data-map="orderFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrSplyUprc"/>" binding="mgrSplyUprc" width="70" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrUnitQty"/>" binding="mgrUnitQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrEtcQty"/>" binding="mgrEtcQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrTotQty"/>" binding="mgrTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrAmt"/>" binding="mgrAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrVat"/>" binding="mgrVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrTot"/>" binding="mgrTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.splyUprc"/>" binding="splyUprc" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.hqStock"/>" binding="hqUnitStock" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.hqStock"/>" binding="hqEtcStock" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.storeStock"/>" binding="storeUnitStock" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.storeStock"/>" binding="storeEtcStock" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.confirmYn"/>" binding="confirmYn" width="60" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 분배마감 상세 그리드 controller */
  app.controller('dstbCloseProdDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dstbCloseProdDtlCtrl', $scope, $http, true));

    $scope.orderFgMap = new wijmo.grid.DataMap([
      {id: "Y", name: "<s:message code='dstbCloseProd.dtl.orderFgY'/>"},
      {id: "N", name: "<s:message code='dstbCloseProd.dtl.orderFgN'/>"},
    ], 'id', 'name');

    $scope.procFgMap = new wijmo.grid.DataMap([
      {id: "00", name: "<s:message code='dstbCloseProd.dtl.procFgReg'/>"},
      {id: "10", name: "<s:message code='dstbCloseProd.dtl.procFgMd'/>"},
      {id: "20", name: "<s:message code='dstbCloseProd.dtl.procFgDstbClose'/>"},
      {id: "30", name: "<s:message code='dstbCloseProd.dtl.procFgSlip'/>"}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "mgrEtcQty") { // 입수에 따라 분배수량 컬럼 readonly 컨트롤
            // console.log(item);
            if (item.poUnitQty === 1) {
              wijmo.addClass(e.cell, 'wj-custom-readonly');
              wijmo.setAttribute(e.cell, 'aria-readonly', true);

              // Attribute 의 변경사항을 적용.
              e.cell.outerHTML = e.cell.outerHTML;
            }
          }
        }
      });

      s.cellEditEnded.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "mgrSplyUprc" || col.binding === "mgrUnitQty" || col.binding === "mgrEtcQty") { // 분배수량 수정시
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
    };

    // 금액 계산
    $scope.calcAmt = function (item) {
      var mgrSplyUprc = parseInt(item.mgrSplyUprc);
      var poUnitQty   = parseInt(item.poUnitQty);
      var vat01       = parseInt(item.vatFg01);
      var envst0011   = parseInt(item.envst0011);

      var unitQty = parseInt(nvl(item.mgrUnitQty, 0)) * parseInt(item.poUnitQty);
      var etcQty  = parseInt(nvl(item.mgrEtcQty, 0));
      var totQty  = parseInt(unitQty + etcQty);
      var tempAmt = Math.round(totQty * mgrSplyUprc / poUnitQty);
      var mgrAmt  = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
      var mgrVat  = Math.round(tempAmt * vat01 / (10 + envst0011));
      var mgrTot  = parseInt(mgrAmt + mgrVat);

      item.mgrTotQty = totQty; // 총분배수량
      item.mgrAmt    = mgrAmt; // 금액
      item.mgrVat    = mgrVat; // VAT
      item.mgrTot    = mgrTot; // 합계
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dstbCloseProdDtlCtrl", function (event, data) {
      $scope.reqDate = data.reqDate;
      $scope.prodCd  = data.prodCd;
      $scope.prodNm  = data.prodNm;
      $scope.slipFg  = data.slipFg;
      $scope.procFg  = data.procFg;

      $scope.wjDstbCloseProdDtlLayer.show(true);
      $("#spanDtlTitle").html('[<s:message code="dstbCloseProd.dtl.order"/>] ' + '[' + $scope.prodCd + '] ' + $scope.prodNm);

      if (parseInt($scope.procFg) < 20) {
        $("#btnDtlSave").show();
      }
      else {
        $("#btnDtlSave").hide();
      }
      $scope.searchDstbCloseProdDtlList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 분배마감 상세내역 리스트 조회
    $scope.searchDstbCloseProdDtlList = function () {
      // 파라미터
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.prodCd  = $scope.prodCd;
      params.slipFg  = $scope.slipFg;
      params.procFg  = $scope.procFg;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/order/dstbCloseProd/dstbCloseProdDtl/list.sb", params, function () {
      });
    };

    // 저장 전 값 체크
    $scope.save = function () {
      var params = new Array();

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.mgrUnitQty === null && item.mgrEtcQty === null) {
          $scope._popMsg(messages["dstbCloseProd.dtl.require.mgrQty"]); // 분배수량을 입력해주세요.
          return false;
        }
        if (item.mgrEtcQty !== null && (parseInt(item.mgrEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["dstbCloseProd.dtl.not.mgrEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.mgrTot !== null && (parseInt(item.mgrTot) > 9999999999)) {
          $scope._popMsg(messages["dstbCloseProd.dtl.not.overMgrTot"]); // 분배금액이 너무 큽니다.
          return false;
        }

        item.status  = "U";
        item.reqDate = $scope.reqDate;
        item.slipFg  = $scope.slipFg;
        params.push(item);
      }

      $scope._save("/iostock/order/dstbCloseProd/dstbCloseProdDtl/save.sb", params, function () {
        $scope.saveDstbCloseProdDtlCallback()
      });
    };

    // 저장 후 콜백 함수
    $scope.saveDstbCloseProdDtlCallback = function () {
      var dstbCloseProdScope = agrid.getScope('dstbCloseProdCtrl');
      dstbCloseProdScope.searchDstbCloseProdList();

      $scope.wjDstbCloseProdDtlLayer.hide(true);
    };
  }]);
</script>
