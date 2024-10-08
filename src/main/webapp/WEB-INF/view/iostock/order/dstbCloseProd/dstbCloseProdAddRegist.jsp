<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbCloseProd/dstbCloseProdAddRegist/"/>

<wj-popup id="wjDstbCloseProdAddRegistLayer" control="wjDstbCloseProdAddRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="dstbCloseProdAddRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbCloseProdAddRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanAddRegistTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">

      <div class="tr mt20">
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="save()">
          <s:message code="cmm.save"/></button>
          <div class="tooltipBtn fl">설명
            <span class="tooltiptext tooltip-right">
              * <s:message code="dstbCloseProd.addRegist.txt1"/><br/>
              * <s:message code="dstbCloseProd.addRegist.txt2"/><br/>
              * <s:message code="dstbCloseProd.addRegist.txt3"/><br/>
              * <s:message code="dstbCloseProd.addRegist.txt4"/><br/>
            </span>
          </div>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
            frozen-columns="4"
            ime-enabled="true">

            <!-- define columns -->
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                                  binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.prodCd"/>" binding="prodCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.orderFg"/>" binding="orderFg" width="65" align="center" is-read-only="true" data-map="orderFgMap"></wj-flex-grid-column>
            
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg1"/>"     binding="orderFg1"           width="65"	align="center"  is-read-only="true"     data-map="orderFgMap1"                                      ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg2"/>"     binding="orderFg2"           width="75"	align="center"  is-read-only="true"     data-map="orderFgMap2"                                      ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg3"/>"     binding="orderFg3"           width="90"	align="center"  is-read-only="true"     data-map="orderFgMap3"                                      ></wj-flex-grid-column>
            
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.mgrSplyUprc"/>" binding="mgrSplyUprc" width="60" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.prevMgrUnitQty"/>" binding="prevMgrUnitQty" width="75" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.prevMgrEtcQty"/>" binding="prevMgrEtcQty" width="75" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.mgrUnitQty"/>" binding="mgrUnitQty" width="65" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.mgrEtcQty"/>" binding="mgrEtcQty" width="65" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.mgrTotQty"/>" binding="mgrTotQty" width="65" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.confirmYn"/>" binding="confirmYn" width="70" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.mgrAmt"/>" binding="mgrAmt" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.mgrVat"/>" binding="mgrVat" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.mgrTot"/>" binding="mgrTot" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.poUnitFg"/>" binding="poUnitFg" width="65" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.poUnitQty"/>" binding="poUnitQty" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.splyUprc"/>" binding="splyUprc" width="75" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.hqStock"/>" binding="hdCurUnitQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.hqStock"/>" binding="hdCurEtcQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.storeStock"/>" binding="storeCurUnitQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.storeStock"/>" binding="storeCurEtcQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.procFg"/>" binding="procFg" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.remark"/>" binding="remark" width="150" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dstbCloseStoreAddCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstbCloseProd/dstbCloseProdAddRegist.js?ver=20220722.02" charset="utf-8"></script>
