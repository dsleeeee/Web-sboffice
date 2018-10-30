<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/storeMove/storeMoveAddProd/"/>

<wj-popup id="wjStoreMoveAddProdLayer" control="wjStoreMoveAddProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="storeMoveAddProdLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeMoveAddProdCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeMove.add.dtlTitle"/>
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
          <%-- 상품코드 --%>
            <th><s:message code="storeMove.add.prodCd"/></th>
          <td>
            <input type="text" id="srchAddProdCd" name="srchAddProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
            <th><s:message code="storeMove.add.prodNm"/></th>
          <td>
            <input type="text" id="srchAddProdNm" name="srchAddProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="storeMove.add.txt1"/></li>
        <li class="red"><s:message code="storeMove.add.txt2"/></li>
        <li class="red"><s:message code="storeMove.add.txt3"/></li>
      </ul>

      <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchStoreMoveAddProdList();">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="tr mt20 fr">
        <%-- 저장 --%>
        <button type="button" id="btnRegSave" class="btn_skyblue ml5 fl" ng-click="save()">
          <s:message code="cmm.save"/></button>
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
            <wj-flex-grid-column header="<s:message code="storeMove.add.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.unitQty"/>" binding="outUnitQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.etcQty"/>" binding="outEtcQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.totQty"/>" binding="outTotQty" width="70" align="right" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.splyUprc"/>" binding="outSplyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.amt"/>" binding="outAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.vat"/>" binding="outVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.tot"/>" binding="outTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.splyUprc"/>" binding="inSplyUprc" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.amt"/>" binding="inAmt" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.vat"/>" binding="inVat" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.tot"/>" binding="inTot" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.envst0011"/>" binding="outEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.envst0011"/>" binding="inEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 매장이동관리 신규등록 그리드 controller */
  app.controller('storeMoveAddProdCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeMoveAddProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "outEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
            // console.log(item);
            if (item.poUnitQty === 1) {
              wijmo.addClass(e.cell, 'wj-custom-readonly');
              wijmo.setAttribute(e.cell, 'aria-readonly', true);

              // Attribute 의 변경사항을 적용.
              var html         = e.cell.outerHTML;
              e.cell.outerHTML = html;
            }
          }
        }
      });

      s.cellEditEnded.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          // 주문수량 수정시 금액,VAT,합계 계산하여 보여준다.
          if (col.binding === "outUnitQty" || col.binding === "outEtcQty" || col.binding === "outSplyUprc") {
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

    $scope.calcAmt = function (item) {
      <%-- 수량이 없는 경우 계산하지 않음.
      null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 --%>
      if (nvl(item.outUnitQty, null) === null || (item.poUnitQty !== 1 && nvl(item.outEtcQty, null) === null)) return false;

      var outSplyUprc  = parseInt(nvl(item.outSplyUprc, 0));
      var inSplyUprc   = parseInt(nvl(item.inSplyUprc, 0));
      var poUnitQty    = parseInt(item.poUnitQty);
      var vat01        = parseInt(item.vatFg01);
      var outEnvst0011 = parseInt(item.outEnvst0011);
      var inEnvst0011  = parseInt(item.inEnvst0011);

      var unitQty    = parseInt(nvl(item.outUnitQty, 0)) * parseInt(item.poUnitQty);
      var etcQty     = parseInt(nvl(item.outEtcQty, 0));
      var totQty     = parseInt(unitQty + etcQty);
      var tempOutAmt = Math.round(totQty * outSplyUprc / poUnitQty);
      var tempInAmt  = Math.round(totQty * inSplyUprc / poUnitQty);
      var outAmt     = tempOutAmt - Math.round(tempOutAmt * vat01 * outEnvst0011 / 11);
      var outVat     = Math.round(tempOutAmt * vat01 / (10 + outEnvst0011));
      var outTot     = parseInt(outAmt + outVat);
      var inAmt      = tempInAmt - Math.round(tempInAmt * vat01 * inEnvst0011 / 11);
      var inVat      = Math.round(tempInAmt * vat01 / (10 + inEnvst0011));
      var inTot      = parseInt(inAmt + inVat);

      item.outTotQty = totQty; // 총수량
      item.outAmt = outAmt; // 이출금액
      item.outVat = outVat; // 이출VAT
      item.outTot = outTot; // 이출합계
      item.inAmt  = inAmt; // 이입금액
      item.inVat  = inVat; // 이입VAT
      item.inTot  = inTot; // 이입합계
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeMoveAddProdCtrl", function (event, data) {
      $scope.slipNo     = data.slipNo;
      $scope.outStoreCd = data.outStoreCd;
      $scope.inStoreCd  = data.inStoreCd;

      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.wjStoreMoveAddProdLayer.show(true);

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 매장이동관리 신규등록 상품 리스트 조회
    $scope.searchStoreMoveAddProdList = function () {
      // 파라미터
      var params        = {};
      params.slipNo     = $scope.slipNo;
      params.outStoreCd = $scope.outStoreCd;
      params.inStoreCd  = $scope.inStoreCd;
      params.prodCd     = $scope.prodCd;
      params.prodNm     = $scope.prodNm;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/move/storeMove/storeMoveAddProd/list.sb", params, function () {
      });
    };


    // 저장
    $scope.save = function () {
      var params = [];

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.outEtcQty !== null && (parseInt(item.outEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["storeMove.add.not.etcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.outTot !== null && (parseInt(item.outTot) > 9999999999)) {
          $scope._popMsg(messages["storeMove.add.not.overOutTot"]); // 이출금액이 너무 큽니다.
          return false;
        }
        if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
          $scope._popMsg(messages["storeMove.add.not.overInTot"]); // 이입금액이 너무 큽니다.
          return false;
        }

        item.status     = "U";
        item.slipNo     = $scope.slipNo;
        item.storageCd  = "001";
        item.hqBrandCd  = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

        params.push(item);
      }

      $scope._save("/iostock/move/storeMove/storeMoveAddProd/save.sb", params, function () {
        $scope.saveStoreMoveAddProdCallback()
      });
    };


    $scope.saveStoreMoveAddProdCallback = function () {
      $scope.wjStoreMoveAddProdLayer.hide(true);

      var storeMoveDtlScope = agrid.getScope('storeMoveDtlCtrl');
      storeMoveDtlScope.searchStoreMoveDtlList();

      var storeMoveScope = agrid.getScope('storeMoveCtrl');
      storeMoveScope.searchStoreMoveList();
    };


  }]);
</script>
