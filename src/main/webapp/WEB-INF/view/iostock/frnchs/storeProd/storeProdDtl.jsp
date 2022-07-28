<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/frnchs/store/"/>

<wj-popup id="frnchsStoreProdDtlLayer" control="frnchsStoreProdDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px; height:520px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="frnchsStoreProdDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="frnchsStoreProd.dtl"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2">
    <div>
      <div class="fl"><s:message code="frnchsStoreProd.dtl"/> (<s:message code="frnchsStoreProd.prodCd"/> : {{prodCd}} <s:message code="frnchsStoreProd.prodNm"/> : {{prodNm}})</div>
      <div class="fr">
          <span class="rdo fl mr20 pst7">
              <label class="r-box"><input type="radio"  name="isCheckedSort"  value="1" ng-click="totalOrNot()" id="totRadio" checked="checked" />전체</label>
              <label class="r-box"><input type="radio"  name="isCheckedSort"  value="2" ng-click="totalOrNot()" />수량+합계</label>
          </span>
          <%-- 엑셀 다운로드 //TODO --%>
          <button class="btn_skyblue fr" ng-click="excelDownloadClass()"><s:message code="cmm.excel.down" /></button>
      </div>
    </div>

    <div class="clearfix"></div>

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height:400px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.outDate"/>"       binding="outDtFm"         width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.inDate"/>"        binding="inDtFm"        width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.storeNm"/>"       binding="storeNm"        width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.fg"/>"            binding="slipFgNm"        width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.slipKind"/>"      binding="slipKindNm"    width="100"     align="center"  is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.proc"/>"          binding="procFgNm"   width="100"     align="center"  is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.splyUprc"/>"      binding="splyUprc"    width="100"     align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.cnt"/>"           binding="outTotQty"    width="100"     align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.price"/>"         binding="outAmt"        width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.vat"/>"           binding="outVat"         width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.totAmt"/>"        binding="outTot"         width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.cnt"/>"           binding="inTotQty"         width="100"     align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.price"/>"         binding="inAmt"      width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.vat"/>"           binding="inVat"     width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.totAmt"/>"        binding="inTot"     width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.penaltyAmt"/>"    binding="penaltyAmt"     width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStoreProd.remark"/>"        binding="remark"     width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            
            <wj-flex-grid-column header=""            binding="outDt"           width="*"     align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header=""            binding="inDt"            width="*"     align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/storeProd/storeProdDtl.js?ver=20190207.02" charset="utf-8"></script>
