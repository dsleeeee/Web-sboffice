<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdDtl/"/>

<wj-popup id="wjRtnDstbCloseProdDtlLayer" control="wjRtnDstbCloseProdDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="rtnDstbCloseProdDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnDstbCloseProdDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">

      <div class="tr mt20">
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="save()" ng-if="btnDtlSave"><s:message code="cmm.save"/></button>
        <div class="tooltipBtn fl">설명
            <span class="tooltiptext tooltip-right">
              * <s:message code="rtnDstbCloseProd.dtl.txt1"/><br/>
              * <s:message code="rtnDstbCloseProd.dtl.txt2"/><br/>
              * <s:message code="rtnDstbCloseProd.dtl.txt3"/><br/>
              * <s:message code="rtnDstbCloseProd.dtl.txt4"/><br/>
            </span>
        </div>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
            frozen-columns="3"
            ime-enabled="true">

            <!-- define columns -->
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                            binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.orderSlipNo"/>" binding="orderSlipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.mgrSplyUprc"/>" binding="mgrSplyUprc" width="60" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.mgrUnitQty"/>" binding="mgrUnitQty" width="65" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.mgrEtcQty"/>" binding="mgrEtcQty" width="65" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.mgrTotQty"/>" binding="mgrTotQty" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.mgrAmt"/>" binding="mgrAmt" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.mgrVat"/>" binding="mgrVat" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.mgrTot"/>" binding="mgrTot" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.poUnitFg"/>" binding="poUnitFg" width="65" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.poUnitQty"/>" binding="poUnitQty" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.splyUprc"/>" binding="splyUprc" width="75" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.confirmYn"/>" binding="confirmYn" width="70" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.hqStock"/>" binding="hdCurUnitQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.hqStock"/>" binding="hdCurEtcQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.storeStock"/>" binding="storeCurUnitQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.storeStock"/>" binding="storeCurEtcQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.procFg"/>" binding="procFg" width="65" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.remark"/>" binding="remark" width="150" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.vatFg"/>" binding="vatFg01" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtl.envst0011"/>" binding="envst0011" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdDtl.js?ver=20220804.01" charset="utf-8"></script>
