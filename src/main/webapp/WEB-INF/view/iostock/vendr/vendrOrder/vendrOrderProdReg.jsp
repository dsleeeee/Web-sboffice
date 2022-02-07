<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrOrder/vendrOrderProdReg/"/>

<wj-popup id="wjVendrOrderProdRegLayer" control="wjVendrOrderProdRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrOrderProdRegLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrOrderProdRegCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrOrder.reg.title"/>
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
          <th><s:message code="vendrOrder.reg.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="vendrOrder.reg.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="vendrOrder.reg.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 상품분류 --%>
          <th><s:message code="vendrOrder.reg.prodClass"/></th>
          <td>
            <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                   placeholder="<s:message code="cmm.all" />" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          </td>
        </tr>
        <tr>
          <%-- 안전재고 --%>
          <th><s:message code="vendrOrder.reg.safeStock"/></th>
          <td colspan="3">
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="safeStockFg"
                  ng-model="safeStockFg"
                  items-source="_getComboData('safeStockFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="vendrOrder.reg.excelFormDownload"/></a>
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
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="vendrOrder.reg.excelFormUpload"/></a>
<%--             <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="vendrOrder.reg.textFormUpload"/></a> --%>
            <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
            <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="vendrOrder.reg.excelFormUploadErrorInfo"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="fnSearch();">
          <s:message code="cmm.search"/></button>
      </div>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="vendrOrder.reg.txt1"/></li>
      </ul>

      <div class="mt20 tr">
        <%-- 최종원가를 발주원가로 세팅 --%>
        <button type="button" class="btn_skyblue ml5" id="btnAddProd" ng-click="setLastCostToOrderCost()">
          <s:message code="vendrOrder.reg.lastCostToOrderCost"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnProdSave" ng-click="save()">
          <s:message code="cmm.save"/></button>
      </div>

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
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orgplceCd"/>" binding="orgplceCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.lastCostUprc"/>" binding="lastCostUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.safeStock"/>" binding="safeStockUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.safeStock"/>" binding="safeStockEtcQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.currQty"/>" binding="currUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.currQty"/>" binding="currEtcQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prevOrderUnitQty"/>" binding="prevOrderUnitQty" width="50" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prevOrderEtcQty"/>" binding="prevOrderEtcQty" width="50" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderUnitQty"/>" binding="orderUnitQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderEtcQty"/>" binding="orderEtcQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.prevOrderTotQty"/>" binding="prevOrderTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderTotQty"/>" binding="orderTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderAmt"/>" binding="orderAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderVat"/>" binding="orderVat" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.orderTot"/>" binding="orderTot" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrOrder.reg.vendrVatFg01"/>" binding="vendrVatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="vendrOrderProdRegCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrOrder/vendrOrderProdReg.js?ver=20200911.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 공통팝업 수불/재고 엑셀업로드 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPS.jsp">
</c:import>