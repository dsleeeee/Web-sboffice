<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/payInfo/point/"/>

<wj-popup id="wjPointLayer" control="wjPointLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="pointLayer" class="wj-dialog wj-dialog-columns" ng-controller="pointCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="point.pointPay"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="point.cornrNm"/>" binding="cornrNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="point.membrNo"/>" binding="membrNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="point.membrNm"/>" binding="membrNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="point.membrClassNm"/>" binding="membrClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="point.pointSaveFgNm"/>" binding="pointSaveFgNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="point.dcRate"/>" binding="dcRate" width="80" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="point.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/payInfo/point.js?ver=20190211.01" charset="utf-8"></script>
