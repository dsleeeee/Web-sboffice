<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="saleAnalysisReportCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('saleAnalysisReportCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회월 --%>
            <th>
                <s:message code="cmm.search.month" />
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"> <input id="startMonth" name="startMonth" class="w100px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endMonth" name="endMonth" class="w100px" /></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <p class="tl s14 mt5 lh15 red">점유율(%)은 상품의 매장 점유율입니다.</p>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    frozen-columns="8"
                    id="wjGridSaleAnalysisReportList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="saleAnalysisReport.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisReport.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisReport.totSaleQty"/>" binding="totSaleQty" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisReport.totServiceQty"/>" binding="totServiceQty" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisReport.totSaleAmt"/>" binding="totSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisReport.totDcAmt"/>" binding="totDcAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisReport.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisReport.totQtyRate"/>" binding="totQtyRate" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <%-- 매장 컬럼 생성--%>
                <c:forEach var="storeCol" items="${storeColList}">
                    <wj-flex-grid-column header="<s:message code="saleAnalysisReport.saleQty"/>" binding="store${storeCol.storeCd}SaleQty" width="70" is-read-only="true" align="right" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleAnalysisReport.serviceQty"/>" binding="store${storeCol.storeCd}ServiceQty" width="70" is-read-only="true" align="right" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleAnalysisReport.saleAmt"/>" binding="store${storeCol.storeCd}SaleAmt" width="70" is-read-only="true" align="right" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleAnalysisReport.dcAmt"/>" binding="store${storeCol.storeCd}DcAmt" width="70" is-read-only="true" align="right" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleAnalysisReport.realSaleAmt"/>" binding="store${storeCol.storeCd}RealSaleAmt" width="70" is-read-only="true" align="right" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleAnalysisReport.qtyRate"/>" binding="store${storeCol.storeCd}QtyRate" width="85" is-read-only="true" align="right" aggregate="Sum" visible="false"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    // 매장
    var storeColList = [];
    <%--javascript에서 사용할 매장 json 데이터 생성--%>
    <c:forEach var="storeCol" items="${storeColList}">
        var storeParam = {};
        storeParam.storeCd = "${storeCol.storeCd}";
        storeParam.storeNm = "${storeCol.storeNm}";
        storeColList.push(storeParam);
    </c:forEach>

    var storeCol = '${storeCol}';
    var arrStoreCol = storeCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/saleAnalysisReport/saleAnalysisReport.js?ver=20211216.03" charset="utf-8"></script>