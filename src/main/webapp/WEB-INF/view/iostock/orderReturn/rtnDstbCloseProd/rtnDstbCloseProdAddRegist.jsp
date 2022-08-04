<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdAddRegist/"/>

<wj-popup id="wjRtnDstbCloseProdAddRegistLayer" control="wjRtnDstbCloseProdAddRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height: 750px;">
  <div id="rtnDstbCloseProdAddRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnDstbCloseProdAddRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanAddRegistTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">

      <div class="tr mt20">
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="save()"><s:message code="cmm.save"/></button>
        <div class="tooltipBtn fl">설명
          <span class="tooltiptext tooltip-right">
            * <s:message code="rtnDstbCloseProd.addRegist.txt1"/><br/>
            * <s:message code="rtnDstbCloseProd.addRegist.txt2"/><br/>
            * <s:message code="rtnDstbCloseProd.addRegist.txt3"/><br/>
            * <s:message code="rtnDstbCloseProd.addRegist.txt4"/><br/>
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
            frozen-columns ="4"
            ime-enabled="true">

            <!-- define columns -->
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                                  binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>--%>
			<wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.prodCd"/>" binding="prodCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.storeNm"/>" binding="storeNm" width="110" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.addRegist.orderFg"/>" binding="orderFg" width="65" align="center" is-read-only="true" data-map="orderFgMap"></wj-flex-grid-column>
			
			<wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg1"/>"     binding="orderFg1"           width="65"	align="center"  is-read-only="true"     data-map="orderFgMap1"                                      ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg2"/>"     binding="orderFg2"           width="80"	align="center"  is-read-only="true"     data-map="orderFgMap2"                                      ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="dstbCloseProd.dtl.orderFg3"/>"     binding="orderFg3"           width="85"	align="center"  is-read-only="true"     data-map="orderFgMap3"                                      ></wj-flex-grid-column>
            
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.mgrSplyUprc"/>" binding="mgrSplyUprc" width="60" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.prevMgrUnitQty"/>" binding="prevMgrUnitQty" width="75" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.prevMgrEtcQty"/>" binding="prevMgrEtcQty" width="75" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.mgrUnitQty"/>" binding="mgrUnitQty" width="75" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.mgrEtcQty"/>" binding="mgrEtcQty" width="75" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.mgrTotQty"/>" binding="mgrTotQty" width="75" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.confirmYn"/>" binding="confirmYn" width="65" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.mgrAmt"/>" binding="mgrAmt" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.mgrVat"/>" binding="mgrVat" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.mgrTot"/>" binding="mgrTot" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.poUnitFg"/>" binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.poUnitQty"/>" binding="poUnitQty" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.splyUprc"/>" binding="splyUprc" width="75" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.hqStock"/>" binding="hdCurUnitQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.hqStock"/>" binding="hdCurEtcQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.storeStock"/>" binding="storeCurUnitQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.storeStock"/>" binding="storeCurEtcQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.procFg"/>" binding="procFg" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.remark"/>" binding="remark" width="150" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.addRegist.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>            
            
            <!-- 
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prodCd"/>" 			binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prodNm"/>" 			binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderSplyUprc"/>" 	binding="orderSplyUprc" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.unitQty"/>"			binding="prevOrderUnitQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.etcQty"/>" 			binding="prevOrderEtcQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prevOrderTotQty"/>" binding="prevOrderTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.unitQty"/>" 		binding="orderUnitQty" width="70" align="right" is-read-only="true"max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.etcQty"/>" 			binding="orderEtcQty" width="70" align="right" is-read-only="true"max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderTotQty"/>" 	binding="orderTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderAmt"/>" 		binding="orderAmt" width="70" align="right" visible="false" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderVat"/>" 		binding="orderVat" width="70" align="right" visible="false"is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderTot"/>" 		binding="orderTot" width="70" align="right" visible="false"is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.saleUprc"/>" 		binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.poUnitFg"/>" 		binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.poUnitQty"/>" 		binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.unitQty"/>" 		binding="safeStockUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.etcQty"/>" 			binding="safeStockEtcQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.unitQty"/>" 		binding="storeCurUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.etcQty"/>" 			binding="storeCurEtcQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.remark"/>" 			binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.poMinQty"/>" 		binding="poMinQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.vatFg"/>" 			binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.envst0011"/>" 		binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            
            <wj-flex-grid-column header=""                                                  	binding="arrStorageCd"  	width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  	binding="arrStorageNm"  	width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			
			<wj-flex-grid-column header=""                                                  	binding="arrcurrQty"  		width="200" align="left"   	is-read-only="true"     visible="false" 												></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  	binding="arrOrderUnitQty"  	width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  	binding="arrOrderEtcQty"   	width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  	binding="arrOrderTotQty"   	width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>

			<wj-flex-grid-column header=""                                                  	binding="arrOrderAmt"      	width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  	binding="arrOrderVat"      	width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  	binding="arrOrderTot"      	width="200" align="left"   	is-read-only="true"     visible="false"         										></wj-flex-grid-column>
			 -->
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

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdAddRegist.js?ver=20220804.01" charset="utf-8"></script>
