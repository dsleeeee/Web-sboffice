<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayStoreDtl/"/>

<wj-popup id="wjDayStoreDtlLayer" control="wjDayStoreDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dayStoreDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="dayStoreDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="day.dayStoreDtl.dayStore"/>
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
            <wj-flex-grid-column header="<s:message code="day.dayStoreDtl.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="day.dayStoreDtl.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="day.dayStoreDtl.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="day.dayStoreDtl.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="day.dayStoreDtl.billCnt"/>" binding="billCnt" width="100" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="day.dayStoreDtl.billUprc"/>" binding="billUprc" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayStoreDtl.js?ver=20190218.01" charset="utf-8"></script>
