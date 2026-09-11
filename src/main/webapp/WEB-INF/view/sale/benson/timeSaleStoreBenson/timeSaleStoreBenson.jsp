<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">
    <div ng-controller="timeSaleStoreBensonCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="timeSaleStoreBenson.timeSaleStoreBenson" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('timeSaleStoreBensonCtrl', 1)">
                    <s:message code="cmm.search"/>
                </button>
            </div>
        </div>

        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
                <%-- 조회옵션 --%>
                <th><s:message code="timeSaleStoreBenson.srchOption"/></th>
                <td>
                    <%-- 내점 --%>
                    <div style="float: left;"><input type="checkbox" id="chkOptStin" ng-model="optStin"/></div>
                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="timeSaleStoreBenson.stin" /></label></div>
                    <%-- 포장 --%>
                    <div style="float: left;"><input type="checkbox" id="chkOptPack" ng-model="optPack"/></div>
                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="timeSaleStoreBenson.pack" /></label></div>
                    <%-- 배달 --%>
                    <div style="float: left;"><input type="checkbox" id="chkOptDlvr" ng-model="optDlvr"/></div>
                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="timeSaleStoreBenson.dlvr" /></label></div>
                </td>
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <%-- 매장브랜드 --%>
                    <th><s:message code="cmm.moms.storeHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchStoreHqBrandCdCombo"
                                    ng-model="storeHqBrandCd"
                                    items-source="_getComboData('storeHqBrandCdCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchStoreHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="timeSaleStoreBensonStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="timeSaleStoreBensonStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 조회조건 엑셀다운로드 --%>
            <%--<button class="btn_skyblue ml5 fr" ng-click="excelDownload('1')"><s:message code="cmm.excel.downCondition"/></button>--%>
            <%-- 분할 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload('2')"><s:message code="cmm.excel.downDivision"/></button>
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload2()"><s:message code="cmm.excel.downCurrent"/></button>
        </div>

        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height: 370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="wjGridList"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <%-- 시간대 컬럼 생성--%>
                    <c:forEach var="i" begin="0" end="9">
                        <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.totSaleAmt"/>" binding="totSaleAmt0${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.realSaleAmt"/>" binding="realSaleAmt0${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.billCnt"/>" binding="billCnt0${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <c:forEach var="i" begin="10" end="23">
                        <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.totSaleAmt"/>" binding="totSaleAmt${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.realSaleAmt"/>" binding="realSaleAmt${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.billCnt"/>" binding="billCnt${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="timeSaleStoreBensonCtrlPager" data-size="10">
            </ul>
        </div>
        <%-- //페이지 리스트 --%>
    </div>

    <%-- 엑셀 리스트 --%>
    <div class="w100 mt10" style="display:none;" ng-controller="timeSaleStoreBensonExcelCtrl">
        <div class="wj-gridWrap" style="height: 370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridExcelList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="0" end="9">
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.totSaleAmt"/>" binding="totSaleAmt0${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.realSaleAmt"/>" binding="realSaleAmt0${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.billCnt"/>" binding="billCnt0${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>
                <c:forEach var="i" begin="10" end="23">
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.totSaleAmt"/>" binding="totSaleAmt${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.realSaleAmt"/>" binding="realSaleAmt${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeSaleStoreBenson.billCnt"/>" binding="billCnt${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
        </div>
    </div>
    <%--// 엑셀 리스트 --%>

</div>

<script type="text/javascript">
    var menuCd = "${menuCd}";
    var menuNm = "${menuNm}";

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/timeSaleStoreBenson/timeSaleStoreBenson.js?ver=20260910.02" charset="utf-8"></script>
