<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbCloseStore/dstbCloseStore/"/>

<wj-popup id="wjDstbCloseStoreDtlLayer" control="wjDstbCloseStoreDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dstbCloseStoreDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbCloseStoreDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">

      <ul class="txtSty3">
        <li class="red"><s:message code="dstbCloseStore.dtl.txt1"/></li>
        <li class="red"><s:message code="dstbCloseStore.dtl.txt2"/></li>
        <li class="red"><s:message code="dstbCloseStore.dtl.txt3"/></li>
        <li class="red"><s:message code="dstbCloseStore.dtl.txt4"/></li>
      </ul>

      <div class="tr mt20">
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="save()" ng-if="btnDtlSave"><s:message code="cmm.save"/></button>

        <%-- 엑셀 다운로드 --%>
        <button                               class="btn_skyblue ml5" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
            frozen-columns		="4">

            <!-- define columns -->
        <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                           binding="gChk"             	width="40"  align="center"></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.seq"/>" 		    binding="seq"               width="0"   align="center"  is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.storeCd"/>" 	    binding="storeCd"           width="0"   align="center"  is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.prodCd"/>" 	    binding="prodCd"            width="100" align="center"  is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.prodNm"/>" 	    binding="prodNm"            width="150" align="left"    is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.poUnitFg"/>" 	    binding="poUnitFg"          width="70"  align="center"  is-read-only="true"     data-map="poUnitFgMap"                                          ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.poUnitQty"/>" 	    binding="poUnitQty"         width="70"  align="right"   is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.splyUprc"/>" 	    binding="splyUprc"          width="80"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.mgrSplyUprc"/>"    binding="mgrSplyUprc"       width="70"  align="right"   is-read-only="false"    data-type="Number"  format="n0"                 max-length=10   ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.hqStock"/>"        binding="hdCurUnitQty"       width="70"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.hqStock"/>"        binding="hdCurEtcQty"        width="70"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.storeStock"/>"     binding="storeCurUnitQty"    width="70"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.storeStock"/>"     binding="storeCurEtcQty"     width="70"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.mgrUnitQty"/>"     binding="mgrUnitQty"        width="70"  align="right"   is-read-only="false"    data-type="Number"  format="n0" aggregate="Sum" max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.mgrEtcQty"/>"      binding="mgrEtcQty"         width="70"  align="right"   is-read-only="false"    data-type="Number"  format="n0" aggregate="Sum" max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.mgrTotQty"/>"      binding="mgrTotQty"         width="70"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.mgrAmt"/>"         binding="mgrAmt"            width="70"  align="right"   is-read-only="true"     data-type="Number"  format="n0" aggregate="Sum"                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.mgrVat"/>"         binding="mgrVat"            width="70"  align="right"   is-read-only="true"     data-type="Number"  format="n0" aggregate="Sum"                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.mgrTot"/>"         binding="mgrTot"            width="70"  align="right"   is-read-only="true"     data-type="Number"  format="n0" aggregate="Sum"                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.procFg"/>"         binding="procFg"            width="70"  align="center"  is-read-only="true"     data-map="procFgMap"                                            ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.confirmYn"/>"      binding="confirmYn"         width="80"  align="center"  is-read-only="false"                        format="checkBoxText"                       ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.remark"/>"         binding="remark"            width="200" align="left"    is-read-only="false"                                                    max-length=300  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.vatFg"/>"          binding="vatFg01"           width="70"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.dtl.envst0011"/>"      binding="envst0011"          width="70" align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstbCloseStore/dstbCloseStoreDtl.js?ver=20181224.01" charset="utf-8"></script>
