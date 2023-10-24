<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreAdd/"/>

<wj-popup id="wjRtnDstbCloseStoreAddLayer" control="wjRtnDstbCloseStoreAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="rtnDstbCloseStoreAddLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnDstbCloseStoreAddCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="rtnDstbCloseStore.add.title"/> -
      <s:message code="rtnDstbCloseStore.add.addProdSubTitle"/>
      <label id="addProdSubTitle"></label>
      <label id="orderFgSubTitle"></label>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 매장선택 --%>
          <th><s:message code="cmm.store.select"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="S"/>
              <jsp:param name="targetId" value="rtnDstbCloseStoreAddSelectStore"/>
              <jsp:param name="closeFunc" value="fnGridClear"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="rtnDstbCloseStore.add.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품코드 --%>
          <th><s:message code="rtnDstbCloseStore.add.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="rtnDstbCloseStore.add.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 분류 --%>
          <th><s:message code="rtnDstbCloseStore.add.prodClassNm"/></th>
          <td>
            <input type="text" class="sb-input" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width: 200px;"
                   placeholder="<s:message code="cmm.all" />" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled/>
            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
          </td>
        </tr>
        </tbody>
      </table>
      <table class="tblType01" id="tblSearchAddShow" style="display: none;">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 옵션1 --%>
          <th><s:message code="rtnDstbCloseStore.add.option1"/></th>
          <td>
          <span class="txtIn w200px sb-select fl mr5">
            <wj-combo-box
              id="option1"
              ng-model="option1"
              items-source="_getComboData('option1')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
          </td>
          <%-- 거래처 --%>
            <th <c:if test="${envst1242 == '0'}">style="display: none;"</c:if>><s:message code="dstbCloseProd.dtl.vender"/></th>
            <td <c:if test="${envst1242 == '0'}">style="display: none;"</c:if>>
              <div class="sb-select fl w150px">
                <wj-combo-box
                  id="dtlVendrCd"
                  ng-model="vendrCd"
                  control="dtlVendrCdCombo"
                  items-source="_getComboData('dtlVendrCd')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
            </td>
        </tr>
        <tr>
          <%-- 옵션2 --%>
          <th><s:message code="rtnDstbCloseStore.add.option2"/></th>
          <td colspan="3">
          <span class="txtIn w120px sb-select fl mr5">
            <wj-combo-box
              id="option2"
              ng-model="option2"
              items-source="_getComboData('option2')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)"
              selected-index-changed="selectedIndexChanged(s, e)"
            >
            </wj-combo-box>
          </span>
            <p id="option2OrdLayer" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="rtnDstbCloseStore.add.reqDate"/></p>
            <p id="option2OutLayer" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="rtnDstbCloseStore.add.outDate"/></p>
            <p id="option2SaleLayer" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="rtnDstbCloseStore.add.saleDate"/></p>
            <div id="option2DateLayer" class="sb-select fl ml10" style="display: none;">
              <span class="txtIn"><input id="srchRegStartDate" class="w120px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchRegEndDate" class="w120px"></span>
            </div>
            <p id="option2OrdLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="rtnDstbCloseStore.add.txtOption2Ord"/></p>
            <p id="option2OutLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="rtnDstbCloseStore.add.txtOption2Out"/></p>
            <p id="option2SaleLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="rtnDstbCloseStore.add.txtOption2Sale"/></p>
          </td>
        </tr>
<!--         <tr> -->
<!--           <td colspan="4"> -->
<%--             <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="rtnDstbCloseStore.add.excelFormDownload"/></a> --%>
<%--             <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="rtnDstbCloseStore.add.excelFormUpload"/></a> --%>
<%--             <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="rtnDstbCloseStore.add.textFormUpload"/></a> --%>
<%--             <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a> --%>
<%--             <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="rtnDstbCloseStore.add.excelFormUploadErrorInfo"/></a> --%>
<!--           </td> -->
<!--         </tr> -->
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="search();">
          <s:message code="cmm.search"/>
        </button>
        <%-- 확장조회 --%>
        <button class="btn_blue mr5 fr" id="btnSearchAddShow" ng-click="searchAddShowChange()">
            <s:message code="cmm.search.addShow" />
        </button>
      </div>
      <div class="mt10 tr">
        <div class="tr">
          <%-- 저장 --%>
          <button type="button" class="btn_skyblue ml5" id="btnSave" ng-click="saveRtnDstbCloseStoreAdd()"><s:message code="cmm.save"/></button>
          <div class="tooltipBtn fl">설명
             <span class="tooltiptext tooltip-right">
               * <s:message code="rtnDstbCloseStore.add.txt1"/><br/>
             </span>
          </div>
        </div>
      </div>

      <%--<div class="wj-TblWrap ml20 mr20 pdb20">--%>
      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
            frozen-columns="2"
            ime-enabled="true">

            <!-- define columns -->
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                             binding="gChk"                width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.prodClassNm"/>" binding="prodClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.mgrSplyUprc"/>" binding="mgrSplyUprc" width="65" align="right" max-length=10 is-read-only="false" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.prevMgrUnitQty"/>" binding="prevMgrUnitQty" width="75" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.prevMgrEtcQty"/>" binding="prevMgrEtcQty" width="75" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.prevMgrTotQty"/>" binding="prevMgrTotQty" width="65" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.mgrUnitQty"/>" binding="mgrUnitQty" width="65" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.mgrEtcQty"/>" binding="mgrEtcQty" width="65" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.mgrTotQty"/>" binding="mgrTotQty" width="65" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.mgrAmt"/>" binding="mgrAmt" width="65" align="right" is-read-only="true" visible="false" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.mgrVat"/>" binding="mgrVat" width="65" align="right" is-read-only="true" visible="false" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.mgrTot"/>" binding="mgrTot" width="65" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.saleUprc"/>" binding="saleUprc" width="65" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.poUnitFg"/>" binding="poUnitFg" width="65" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.poUnitQty"/>" binding="poUnitQty" width="65" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.splyUprc"/>" binding="splyUprc" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.hqSafeStock"/>" binding="hqSafeStockUnitQty" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.hqSafeStock"/>" binding="hqSafeStockEtcQty" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.hqCurrQty"/>" binding="hqCurrUnitQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.hqCurrQty"/>" binding="hqCurrEtcQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.storeCurrQty"/>" binding="storeCurrUnitQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.storeCurrQty"/>" binding="storeCurrEtcQty" width="65" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.remark"/>" binding="remark" width="150" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.poMinQty"/>" binding="poMinQty" width="65" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.vatFg"/>" binding="vatFg01" width="65" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.add.envst0011"/>" binding="envst0011" width="65" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
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
        <ul id="rtnDstbCloseStoreAddCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreAdd.js?ver=20220805.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 수불 엑셀업로드 공통 팝업 --%>
<%--<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">--%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPS.jsp">
</c:import>
