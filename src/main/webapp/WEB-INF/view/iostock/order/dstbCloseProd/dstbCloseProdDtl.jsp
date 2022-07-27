<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbCloseProd/dstbCloseProdDtl/"/>

<wj-popup id="wjDstbCloseProdDtlLayer" control="wjDstbCloseProdDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="dstbCloseProdDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbCloseProdDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">
      <div class="tr mt20">
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="save()" ng-if="btnDtlSave"><s:message code="cmm.save"/></button>
        <%-- 엑셀 다운로드 --%>
        <button class="btn_skyblue ml5" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
        <div class="tooltipBtn fl">설명
            <span class="tooltiptext tooltip-right">
              * <s:message code="dstbCloseProd.dtl.txt1"/><br/>
              * <s:message code="dstbCloseProd.dtl.txt2"/><br/>
              * <s:message code="dstbCloseProd.dtl.txt3"/><br/>
              * <s:message code="dstbCloseProd.dtl.txt4"/><br/>
            </span>
        </div>
      </div>

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
            frozen-columns="3"
            ime-enabled="true">

            <!-- define columns -->
        <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                      	binding="gChk"             	width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.seq"/>"         binding="seq"               width="0"   align="center"  is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.storeCd"/>"     binding="storeCd"           width="70"  align="center"  is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.storeNm"/>"     binding="storeNm"           width="150" align="left"    is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg"/>"     binding="orderFg"           width="65"	align="center"  is-read-only="true"     data-map="orderFgMap"                                           ></wj-flex-grid-column>
            
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg1"/>"     binding="orderFg1"           width="65"	align="center"  is-read-only="true"     data-map="orderFgMap1"                                      ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg2"/>"     binding="orderFg2"           width="75"	align="center"  is-read-only="true"     data-map="orderFgMap2"                                      ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg3"/>"     binding="orderFg3"           width="90"	align="center"  is-read-only="true"     data-map="orderFgMap3"                                      ></wj-flex-grid-column>
			                                    
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrSplyUprc"/>" binding="mgrSplyUprc"       width="60"  align="right"   is-read-only="false"    data-type="Number"  format="n0" max-length=10                   ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrUnitQty"/>"  binding="mgrUnitQty"        width="60"  align="right"   is-read-only="false"    data-type="Number"  format="n0" max-length=8    aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrEtcQty"/>"   binding="mgrEtcQty"         width="60"  align="right"   is-read-only="false"    data-type="Number"  format="n0" max-length=8    aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrTotQty"/>"   binding="mgrTotQty"         width="60"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.confirmYn"/>"   binding="confirmYn"         width="70"  align="center"  is-read-only="false"                        format="checkBoxText"                       ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrAmt"/>"      binding="mgrAmt"            width="60"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                 aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrVat"/>"      binding="mgrVat"            width="60"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                 aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.mgrTot"/>"      binding="mgrTot"            width="60"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                 aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.poUnitFg"/>"    binding="poUnitFg"          width="70"  align="center"  is-read-only="true"     data-map="poUnitFgMap"                                          ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.poUnitQty"/>"   binding="poUnitQty"         width="60"  align="right"   is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.splyUprc"/>"    binding="splyUprc"          width="80"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.hqStock"/>"     binding="hdCurUnitQty"       width="60"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.hqStock"/>"     binding="hdCurEtcQty"        width="60"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.storeStock"/>"  binding="storeCurUnitQty"    width="60"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.storeStock"/>"  binding="storeCurEtcQty"     width="60"  align="right"   is-read-only="true"     data-type="Number"  format="n0"                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.procFg"/>"      binding="procFg"            width="65"  align="center"  is-read-only="true"     data-map="procFgMap"                                            ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.remark"/>"      binding="remark"            width="150" align="left"    is-read-only="false"                                    max-length=300                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.vatFg"/>"       binding="vatFg01"           width="70"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.envst0011"/>"   binding="envst0011"         width="70"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstbCloseProd/dstbCloseProdDtl.js?ver=20220722.01" charset="utf-8"></script>
