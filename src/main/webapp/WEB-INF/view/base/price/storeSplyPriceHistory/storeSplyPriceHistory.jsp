<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="storeSplyPriceHistoryCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeSplyPriceHistory.storeSplyPriceHistory"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storeSplyPriceHistoryCtrl', 1)">
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
            <%-- 변경 일자 --%>
            <th><s:message code="storeSplyPriceHistory.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="startDate" ng-model="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" ng-model="endDate" class="w110px"></span>
                </div>
            </td>
            <%-- 분류선택 --%>
            <th><s:message code="storeSplyPriceHistory.prodClass"/></th>
            <td>
                <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;" placeholder="선택" readonly/>
                <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="storeSplyPriceHistoryStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="storeSplyPriceHistoryStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <tr>
            <%-- 상품코드 --%>
            <th>
                <s:message code="storeSplyPriceHistory.prodCd"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th>
                <s:message code="storeSplyPriceHistory.prodNm"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <c:if test="${brandUseFg == '1'}">
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <%-- 상품브랜드 --%>
                    <th><s:message code="storeSplyPriceHistory.prodHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchProdHqBrandCd"
                                        ng-model="prodHqBrandCd"
                                        items-source="_getComboData('srchProdHqBrandCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchProdHqBrandCdCombo">
                                </wj-combo-box>
                            </div>
                        </div>
                    </td>
                    <th></th>
                    <td></td>
                </tr>
            </c:if>
        </c:if>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div id="theGrid" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.prodCd"/>" binding="prodCd" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.saleB"/>" binding="bSplyUprc" is-read-only="true" width="80" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.sale"/>" binding="aSplyUprc" is-read-only="true" width="80" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.procFg"/>" binding="procFg" is-read-only="true" width="90" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.procDt"/>" binding="procDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.modId"/>" binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="storeSplyPriceHistoryCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<%--엑셀 리스트--%>
<div id="wjWrapType2" class="w100 mt10" style="display:none;" ng-controller="storeSplyPriceHistoryExcelCtrl">
    <div class="wj-gridWrap"> <%-- 수정 사항 || 그리드 높이값 스타일 제거 :: style="height: 000px;" --%>
        <wj-flex-grid
                autoGenerateColumns="false"
                control="excelFlex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.prodCd"/>" binding="prodCd" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.saleB"/>" binding="bSplyUprc" is-read-only="true" width="80" align="right"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.sale"/>" binding="aSplyUprc" is-read-only="true" width="80" align="right"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.procFg"/>" binding="procFg" is-read-only="true" width="90" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.procDt"/>" binding="procDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeSplyPriceHistory.modId"/>" binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>
<%--//엑셀 리스트--%>

<script type="text/javascript">
    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/storeSplyPriceHistory/storeSplyPriceHistory.js?ver=20240425.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>