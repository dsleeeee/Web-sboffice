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
            <wj-flex-grid-column header="<s:message code="storeMove.add.poUnitFg"/>" binding="poUnitFgNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.unitQty"/>" binding="outUnitQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.etcQty"/>" binding="outEtcQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.totQty"/>" binding="outTotQty" width="70" align="right" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.splyUprc"/>" binding="outSplyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.amt"/>" binding="outAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.vat"/>" binding="outVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.tot"/>" binding="outTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.inSplyUprc"/>" binding="inSplyUprc" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.envst0011"/>" binding="outEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.envst0011"/>" binding="inEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.add.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/storeMove/storeMoveAddProd.js?ver=20181224.01" charset="utf-8"></script>
