<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/manage/view/"/>

<wj-popup id="viewDtlLayer" control="viewDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="viewDtlCtrl">
    <div id="popHeader" class="wj-dialog-header wj-dialog-header-font">
      <span>{{totDate}} {{seqNo}}<s:message code="stockManageView.seq"/> {{hqGbnNm}} [{{title}}] <s:message code="stockManageView.detailInfo"/></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

      <div class="wj-dialog-body sc2">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 200px;">
          <wj-flex-grid
          	id="viewDtlGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="stockManageView.prodCd"/>"        binding="prodCd"	width="100"     align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="stockManageView.prodNm"/>"        binding="prodNm"	width="200"     align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="stockManageView.barcd"/>"         binding="barcd"		width="110"     align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="stockManageView.splyUprc"/>"      binding="splyUprc"	width="80"     align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="stockManageView.costUprc"/>"      binding="costUprc"	width="80"     align="right"		is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="stockManageView.poUnitQty"/>"     binding="poUnitQty"	width="60"     align="right"		is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          	<wj-flex-grid-column header="<s:message code="stockManageView.cmptCurrQty"/>"   binding="cmptCurrQty"	width="60"     align="right"		is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          	<wj-flex-grid-column header="<s:message code="stockManageView.acinsQty"/>"     binding="acinsQty"	width="60"     align="right"		is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          	<wj-flex-grid-column header="<s:message code="stockManageView.adjQty"/>"     binding="adjQty"	width="60"     align="right"		is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          	<wj-flex-grid-column header="<s:message code="stockManageView.disuseQty"/>"     binding="disuseQty"	width="60"     align="right"		is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          	
          	<wj-flex-grid-column header="<s:message code="stockManageView.remark"/>"     binding="remark"	width="80"     align="center"		is-read-only="true"></wj-flex-grid-column>          
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/manage/view/viewDtl.js?ver=20200330.01" charset="utf-8"></script>