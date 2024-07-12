<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeOrder/storeOrderRegist/"/>

<wj-popup id="wjStoreOrderRegistLayer" control="wjStoreOrderRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="storeOrderRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeOrderRegistCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeOrder.dtl.registTitle"/> -
      [<s:message code="storeOrder.dtl.order"/>]<s:message code="storeOrder.dtl.addProd"/><label id="registSubTitle"></label>
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
          <%-- 상품코드 --%>
          <th><s:message code="storeOrder.dtl.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품코드 --%>
          <th><s:message code="storeOrder.dtl.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="storeOrder.dtl.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 상품분류 --%>
          <th><s:message code="storeOrder.dtl.prodClass"/></th>
          <td>
            <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width: 200px;"
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
          <th><s:message code="storeOrder.dtl.option1"/></th>
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
            <a href="#" class="btn_grayS" ng-click="setSafeToOrder()"><s:message code="storeOrder.dtl.safeStockApply"/></a>
          </td>
          <%-- 거래처 --%>
          <th <c:if test="${envst1242 == '0' or envst1242 == '2'}">style="display: none;"</c:if>><s:message code="storeOrder.dtl.vendr"/></th>
          <td <c:if test="${envst1242 == '0' or envst1242 == '2'}">style="display: none;"</c:if>>
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
          <th><s:message code="storeOrder.dtl.option2"/></th>
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
              <s:message code="storeOrder.dtl.reqDate"/></p>
            <p id="option2OutLayer" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="storeOrder.dtl.outDate"/></p>
            <p id="option2SaleLayer" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="storeOrder.dtl.saleDate"/></p>
            <div id="option2DateLayer" class="sb-select fl ml10" style="display: none;">
              <span class="txtIn"><input id="srchRegStartDate" class="w120px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchRegEndDate" class="w120px"></span>
            </div>
            <p id="option2OrdLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="storeOrder.dtl.txtOption2Ord"/></p>
            <p id="option2OutLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="storeOrder.dtl.txtOption2Out"/></p>
            <p id="option2SaleLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="storeOrder.dtl.txtOption2Sale"/></p>
          </td>
        </tr>
        <tr>
          <th><s:message code="storeOrder.dtl.remark"/></th>
          <td colspan="3">
            <input type="text" id="regHdRemark" name="regHdRemark" ng-model="regHdRemark" class="sb-input w100" maxlength="300"/>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="storeOrder.dtl.excelFormDownload"/></a>
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
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="storeOrder.dtl.excelFormUpload"/></a>
<%--             <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="storeOrder.dtl.textFormUpload"/></a> --%>
            <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
        <%--<a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="storeOrder.dtl.excelFormUploadErrorInfo"/></a> *label변경: 엑셀 업로드 오류내역 -> 업로드 오류내역 --%>
        	<a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="storeOrder.dtl.uploadErrorInfo"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchStoreOrderRegistList();">
          <s:message code="cmm.search"/></button>
        <%-- 확장조회 --%>
        <button class="btn_blue mr5 fr" id="btnSearchAddShow" ng-click="searchAddShowChange()">
            <s:message code="cmm.search.addShow" />
        </button>
      </div>

      <div class="mt20 tr">
        <div class="tr">
          <%-- 저장 --%>
          <button type="button" class="btn_skyblue ml5" id="btnSave" ng-click="storeCloseCheck2()">
            <s:message code="cmm.save"/>
          </button>
          <div class="tooltipBtn fl">설명
            <span class="tooltiptext tooltip-right">
              * <s:message code="storeOrder.dtl.txt1"/><br/>
              * <s:message code="storeOrder.dtl.txt2"/><br/>
              * <s:message code="storeOrder.dtl.txt3"/><br/>
              * <s:message code="storeOrder.dtl.txt4"/><br/>
              * <s:message code="storeOrder.dtl.txt5"/><br/>
            </span>
          </div>
          <p id="registStoreLoanInfo" class="fl s14 bk pdl10 pdt10"></p>
        </div>
      </div>

      <%--<div class="wj-TblWrap ml20 mr20 pdb20">--%>
      <div class="w100 mt10">
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
            ime-enabled="true">

            <!-- define columns -->
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                         binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderSplyUprc"/>" binding="orderSplyUprc" width="60" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderUnitQty"/>" binding="prevOrderUnitQty" width="45" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderEtcQty"/>" binding="prevOrderEtcQty" width="45" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderTotQty"/>" binding="prevOrderTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderUnitQty"/>" binding="orderUnitQty" width="45" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderEtcQty"/>" binding="orderEtcQty" width="45" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTotQty"/>" binding="orderTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderAmt"/>" binding="orderAmt" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderVat"/>" binding="orderVat" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTot"/>" binding="orderTot" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderUnitQty"/><s:message code="storeOrder.dtl.orderAmt"/>" binding="prevOrderTot" width="100" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.saleUprc"/>" binding="saleUprc" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitFg"/>" binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitQty"/>" binding="poUnitQty" width="45" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.safeStock"/>" binding="safeStockUnitQty" width="45" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.safeStock"/>" binding="safeStockEtcQty" width="45" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.storeCurrQty"/>" binding="storeCurUnitQty" width="45" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.storeCurrQty"/>" binding="storeCurEtcQty" width="45" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poMinQty"/>" binding="poMinQty" width="80" align="right" is-read-only="true" visible="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.remark"/>" binding="remark" width="150" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeOrder.dtl.envst0011"/>" binding="envst0011" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="storeOrderRegistCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/storeOrder/storeOrderRegist.js?ver=20240711.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 공통팝업 수불/재고 엑셀업로드 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPS.jsp">
</c:import>