<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/daySumAvg/"/>

<wj-popup control="wjDaySumAvgLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:780px;height:480px;" fade-in="false" fade-out="false">
    <div ng-controller="daySumAvgCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
          <s:message code="day.daySumAvg.daySumAvg"/>
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
                        <wj-flex-grid-column header="<s:message code="day.daySumAvg.saleDate"/>" binding="saleDate" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.daySumAvg.totSaleAmt"/>" binding="totSaleAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.daySumAvg.totDcAmt"/>" binding="totDcAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.daySumAvg.realSaleAmt"/>" binding="realSaleAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.daySumAvg.totSaleAmt"/>" binding="avgSaleAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.daySumAvg.totDcAmt"/>" binding="avgDcAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.daySumAvg.realSaleAmt"/>" binding="avgRealSaleAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/daySumAvg.js?ver=20220330.01" charset="utf-8"></script>