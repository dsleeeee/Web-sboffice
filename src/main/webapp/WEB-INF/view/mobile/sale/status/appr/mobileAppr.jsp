<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileApprCtrl">

    <div class="searchBar">
        <%-- 승인현황 --%>
        <a href="#" class="fl"><s:message code="mobile.appr"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileApprCtrl', 1)">
            <s:message code="mobile.cmm.search"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="mobile.cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 승인현황 --%>
            <th><s:message code="mobile.appr" /></th>
            <td>
                <span class="sb-radio"><input type="radio" id="card" name="apprFg" value="01" checked /><label for="card">카드</label></span>
                <span class="sb-radio"><input type="radio" id="cash" name="apprFg" value="02" /><label for="cash">현금영수</label></span>
            </td>
        </tr>
        <tr>
            <%-- 정렬방식 --%>
            <th><s:message code="mobile.appr.array" /></th>
            <td>
                <span class="sb-radio"><input type="radio" id="arrayS" name="array" value="S" checked /><label for="arrayS">매장별</label></span>
                <span class="sb-radio"><input type="radio" id="arrayD" name="array" value="D" /><label for="arrayD">날짜별</label></span>
            </td>
        </tr>
        <c:if test="${orgnFg == 'HQ'}">
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="mobile.cmm.search.store"/></th>
                <td>
                    <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetId" value="mobileApprStore"/>
                    </jsp:include>
                    <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${orgnFg == 'STORE'}">
            <c:if test="${multiStoreFg ne 0}">
                <tr>
                        <%-- (다중)매장코드 --%>
                    <th><s:message code="mobile.cmm.search.store"/></th>
                    <td>
                            <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectMultiStore.jsp" flush="true">
                            <jsp:param name="targetId" value="mobileApprStore"/>
                        </jsp:include>
                            <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${multiStoreFg eq 0}">
                <input type="hidden" id="mobileApprStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
        </c:if>
        </tbody>
    </table>

    <%-- 요일별 --%>
    <div class="gridBar mt10" id="mobileAppr" onclick="girdFldUnfld('mobileAppr')">
        <a href="#" class="open"><s:message code="mobile.appr.dtl"/></a>
    </div>
    <div class="w100" id="mobileApprGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileAppr"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.appr.storeCd"/>" binding="storeCd" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.appr.storeNm"/>" binding="storeNm" width="1.*" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.appr.saleDate"/>" binding="saleDate" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
<%--                <wj-flex-grid-column header="<s:message code="mobile.appr.apprCnt"/>" binding="apprCnt" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="mobile.appr.apprSaleAmt"/>" binding="totPayAmt" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileApprMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //결제수단 --%>

</div>

<script type="text/javascript">
    var multiStoreFg 	= '${multiStoreFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/appr/mobileAppr.js?ver=20220427.01" charset="utf-8"></script>

<%-- 상세 팝업 --%>
<c:import url="/WEB-INF/view/mobile/sale/status/appr/mobileApprDtl.jsp">
</c:import>