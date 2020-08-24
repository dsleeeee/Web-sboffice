<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderRegist/"/>

<wj-popup id="wjRtnStoreOrderRegistLayer" control="wjRtnStoreOrderRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="rtnStoreOrderRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnStoreOrderRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="rtnStoreOrder.dtl.registTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <p class="s14 bk mb5 fl">[<s:message code="rtnStoreOrder.dtl.orderReturn"/>] <s:message code="rtnStoreOrder.dtl.addProd"/></p>
      <p id="registSubTitle" class="s14 bk mb5 fl"></p>

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
          <th><s:message code="rtnStoreOrder.dtl.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품코드 --%>
          <th><s:message code="rtnStoreOrder.dtl.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="rtnStoreOrder.dtl.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
            <%-- 상품분류 --%>
          <th><s:message code="rtnStoreOrder.dtl.prodClass"/></th>
          <td>
            <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                   placeholder="<s:message code="cmm.all" />" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          </td>
        </tr>
        <tr>
          <th><s:message code="rtnStoreOrder.dtl.option1"/></th>
          <td colspan="3">
            <span class="txtIn w150px sb-select fl mr5">
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
            <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnStoreOrder.dtl.safeStockApply"/></a>
          </td>
        </tr>
        <tr>
          <th><s:message code="rtnStoreOrder.dtl.option2"/></th>
          <td colspan="3">
            <span class="txtIn w150px sb-select fl mr5">
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
            <p id="option2OrdLayer" class="s14 bk lh30 fl ml10" style="display: none;"><s:message code="rtnStoreOrder.dtl.reqDate"/></p>
            <p id="option2OutLayer" class="s14 bk lh30 fl ml10" style="display: none;"><s:message code="rtnStoreOrder.dtl.outDate"/></p>
            <p id="option2SaleLayer" class="s14 bk lh30 fl ml10" style="display: none;"><s:message code="rtnStoreOrder.dtl.saleDate"/></p>
            <div id="option2DateLayer" class="sb-select fl ml10" style="display: none;">
              <span class="txtIn"><input id="srchRegStartDate" class="w120px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchRegEndDate" class="w120px"></span>
            </div>
            <p id="option2OrdLayer2" class="s14 bk lh30 fl ml10" style="display: none;"><s:message code="rtnStoreOrder.dtl.txtOption2Ord"/></p>
            <p id="option2OutLayer2" class="s14 bk lh30 fl ml10" style="display: none;"><s:message code="rtnStoreOrder.dtl.txtOption2Out"/></p>
            <p id="option2SaleLayer2" class="s14 bk lh30 fl ml10" style="display: none;"><s:message code="rtnStoreOrder.dtl.txtOption2Sale"/></p>
          </td>
        </tr>
        <tr>
          <th><s:message code="rtnStoreOrder.dtl.remark"/></th>
          <td colspan="3">
            <input type="text" id="regHdRemark" name="regHdRemark" ng-model="regHdRemark" class="sb-input w100" maxlength="300"/>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="rtnStoreOrder.dtl.excelFormDownload"/></a>
            <span class="txtIn w120px" style="border:1px solid #e8e8e8;">
              <wj-combo-box
                id="addQtyFg"
                ng-model="addQtyFg"
                items-source="_getComboData('addQtyFg')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="rtnStoreOrder.dtl.excelFormUpload"/></a>
<%--             <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="rtnStoreOrder.dtl.textFormUpload"/></a> --%>
            <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
            <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="rtnStoreOrder.dtl.excelFormUploadErrorInfo"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchRtnStoreOrderRegistList();"><s:message code="cmm.search"/></button>
      </div>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="rtnStoreOrder.dtl.txt1"/></li>
        <li class="red"><s:message code="rtnStoreOrder.dtl.txt2"/></li>
        <li class="red"><s:message code="rtnStoreOrder.dtl.txt3"/></li>
        <li class="red"><s:message code="rtnStoreOrder.dtl.txt4"/></li>
        <li class="red"><s:message code="rtnStoreOrder.dtl.txt5"/></li>
      </ul>

      <div class="mt40 tr">
			<%--출고창고 --%>
          	<p class="s14 bk fl mr5 lh30"><s:message code="rtnStoreOrder.dtl.outStorage"/></p>
          	<span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="saveDtlRtnRegOutStorageCd"
                ng-model="save.dtl.rtnRegOutStorageCd"
                items-source="_getComboData('saveDtlRtnRegOutStorageCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)"
                selected-index-changed="selectedIndexChanged(s)"
                >
              </wj-combo-box>
            </span>      
        <p id="registStoreLoanInfo" class="fl s14 bk lh30"></p>
        <div class="tr">
          <%-- 저장 --%>
          <button type="button" class="btn_skyblue ml5" id="btnSave" ng-click="saveRtnStoreOrderRegist()"><s:message code="cmm.save"/></button>
        </div>
      </div>

      <%--<div class="wj-TblWrap ml20 mr20 pdb20">--%>
      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 500px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
            frozen-columns		="2">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prodCd"/>" 			binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prodNm"/>" 			binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.orderSplyUprc"/>" 	binding="orderSplyUprc" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.unitQty"/>"			binding="prevOrderUnitQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.etcQty"/>" 			binding="prevOrderEtcQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.prevOrderTotQty"/>" binding="prevOrderTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.unitQty"/>" 		binding="orderUnitQty" width="70" align="right" is-read-only="false"max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtl.etcQty"/>" 			binding="orderEtcQty" width="70" align="right" is-read-only="false"max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
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

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="rtnStoreOrderRegistCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderRegist.js?ver=20200824.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 수불 엑셀업로드 공통 팝업 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">
</c:import>
