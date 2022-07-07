<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/disuse/disuse/disuseDtl/"/>

<wj-popup id="wjDisuseDtlLayer" control="wjDisuseDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="disuseDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="disuseDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="disuse.reg.registTitle"/>&nbsp;&nbsp;<span id="registSubTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">

      <form name="myForm" novalidate>
        <table class="tblType01" style="position: relative;">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="disuse.dtl.disuseTitle"/><em class="imp">*</em></th>
            <td colspan="3">
              <input type="text" id="dtlDisuseTitle" name="dtlDisuseTitle" ng-model="disuseTitle" class="sb-input w100" maxlength="33"
                     required
                     popover-enable="myForm.dtlDisuseTitle.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="disuse.dtl.disuseTitle"/>은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
			<th><s:message code="disuse.dtl.disuseStorageCd"/><em class="imp">*</em></th>
            <td colspan="3">
            	<span class="txtIn w150px sb-select fl mr5">
	            <wj-combo-box
	                id="disuseDtlAdjStorageCd"
	                ng-model="disuse.dtl.disuseStorageCd"
	                items-source="_getComboData('disuseDtlAdjStorageCd')"
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
          <li class="red fl"><s:message code="disuse.dtl.txt1"/></li>
        </ul>

        <%-- 상품추가 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlAddProd" ng-click="addProd()" ng-if="btnDtlAddProd">
          <s:message code="disuse.dtl.addProd"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlSave" ng-click="saveDisuseDtl('')" ng-if="btnDtlSave">
          <s:message code="cmm.save"/></button>
        <%-- 확정 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlConfirm" ng-click="confirm()" ng-if="btnDtlConfirm">
          <s:message code="disuse.dtl.confirm"/></button>
      </div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px;">
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
            <wj-flex-grid-column header="<s:message code="disuse.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.disuseQty"/>" binding="disuseQty" width="70" align="right" is-read-only="false" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.disuseAmt"/>" binding="disuseAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.disuseProdStatus"/>" binding="disuseProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      `
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="disuseDtlCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">


</script>

<script type="text/javascript" src="/resource/solbipos/js/stock/disuse/disuse/disuseDtl.js?ver=20200904.01" charset="utf-8"></script>
