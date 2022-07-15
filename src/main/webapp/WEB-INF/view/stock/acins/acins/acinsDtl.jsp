<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/acins/acins/acinsDtl/"/>

<wj-popup id="wjAcinsDtlLayer" control="wjAcinsDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="acinsDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="acinsDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="acins.reg.registTitle"/>&nbsp;&nbsp;<span id="registSubTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">

      <form name="myForm" novalidate>
        <table class="tblType01" style="position: relative;">
          <colgroup>
            <col class="w10"/>
            <col class="w20"/>
            <col class="w10"/>
            <col class="w20"/>
            <col class="w10"/>
            <col class="w20"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="acins.dtl.acinsTitle"/><em class="imp">*</em></th>
            <td colspan="3">
              <input type="text" id="dtlAcinsTitle" name="dtlAcinsTitle" ng-model="acinsTitle" class="sb-input w100" maxlength="33"
                     required
                     popover-enable="myForm.dtlAcinsTitle.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="acins.dtl.acinsTitle"/>은(는) 필수 입력항목 입니다."/>
            </td>
            <th><s:message code="acins.dtl.acinsReason"/></th>
            <td>
              <span class="txtIn w150px sb-select fl mr5">
                 <wj-combo-box
                         id="acinsDtlReason"
                         ng-model="acinsReason"
                         items-source="_getComboData('acinsDtlReason')"
                         display-member-path="name"
                         selected-value-path="value"
                         is-editable="false"
                         initialized="_initComboBox(s)">
                 </wj-combo-box>
              </span>
            </td>
          </tr>
          <tr>
			<th><s:message code="acins.dtl.adjStorageCd"/><em class="imp">*</em></th>
            <td>
            	<span class="txtIn w150px sb-select fl mr5">
	            <wj-combo-box
	                id="acinsDtlAdjStorageCd"
	                ng-model="acins.dtl.adjStorageCd"
	                items-source="_getComboData('acinsDtlAdjStorageCd')"
	                display-member-path="name"
	                selected-value-path="value"
	                is-editable="false"
	                initialized="_initComboBox(s)"
	                selected-index-changed="selectedIndexChanged(s)"
	                >
	              </wj-combo-box>    
	            </span>        	
            </td>
          </tr>
          </tbody>
        </table>
      </form>

      <div class="mt10 tr">
        <ul ng-if="btnDtlSave" class="txtSty3">
          <li class="red fl"><s:message code="acins.dtl.txt1"/></li>
        </ul>

      <%-- 상품추가 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlAddProd" ng-click="addProd()" ng-if="btnDtlAddProd">
          <s:message code="acins.dtl.addProd"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlSave" ng-click="saveAcinsDtl('')" ng-if="btnDtlSave">
          <s:message code="cmm.save"/></button>
        <%-- 확정 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlConfirm" ng-click="confirm()" ng-if="btnDtlConfirm">
          <s:message code="acins.dtl.confirm"/></button>
      </div>

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
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.cmptCurrQty"/>" binding="cmptCurrQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.acinsQty"/>" binding="acinsQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.adjAmt"/>" binding="adjAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.adjQty"/>" binding="adjQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.acinsAmt"/>" binding="acinsAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="acins.reg.acinsProdStatus"/>" binding="acinsProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      `
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="acinsDtlCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/acins/acins/acinsDtl.js?ver=20200904.02" charset="utf-8"></script>
