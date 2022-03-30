<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayProdDtl/"/>

<wj-popup control="wjDayProdDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:780px;height:480px;" fade-in="false" fade-out="false">
    <div ng-controller="dayProdDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
          <s:message code="day.dayProdDtl.dayProd"/>
          <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="oh sb-select dkbr">
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:350px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="day.dayProdDtl.pathNm"/>" binding="pathNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdDtl.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdDtl.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdDtl.saleUprc"/>" binding="saleUprc" width="90" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdDtl.saleQty"/>" binding="saleQty" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdDtl.totSaleAmt"/>" binding="totSaleAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdDtl.totDcAmt"/>" binding="totDcAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdDtl.realSaleAmt"/>" binding="realSaleAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayProdDtl.js?ver=20220329.01" charset="utf-8"></script>