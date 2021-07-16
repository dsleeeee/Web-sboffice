<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileResveInfoCtrl">

    <div class="searchBar">
        <a href="#" class="fl"><s:message code="mobile.resveInfo"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileResveInfoCtrl', 1)">
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
            <%-- 등록일자 --%>
            <th><s:message code="mobile.resveInfo.regDate"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px" /></span>
                    <br><br>
                    <%--전체기간--%>
                    <span class="chk ml10">
                        <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt"><s:message code="cmm.all.day" /></label>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 예약일자 --%>
            <th><s:message code="mobile.resveInfo.resveDate"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="resveStartDate" ng-model="resveStartDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="resveEndDate" ng-model="resveEndDate" class="w110px" /></span>
                    <br><br>
                    <%--전체기간--%>
                    <span class="chk ml10">
                        <input type="checkbox" id="chkResveDt" ng-model="isResveChecked" ng-change="isResveChkDt()" />
                        <label for="chkDt"><s:message code="cmm.all.day" /></label>
                    </span>
                </div>
            </td>
        </tr>
        <c:if test="${multiStoreFg ne 0}">
            <tr>
                <%-- (다중)매장코드 --%>
                <th><s:message code="mobile.cmm.search.store"/></th>
                <td>
                    <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectMultiStore.jsp" flush="true">
                        <jsp:param name="targetId" value="mobileResveInfoStore"/>
                    </jsp:include>
                    <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${multiStoreFg eq 0}">
            <input type="hidden" id="mobileResveInfoStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <tr>
            <%-- 예약번호 --%>
            <th><s:message code="mobile.resveInfo.resveNo"/></th>
            <td><input type="text"  class="sb-input w100" id="resveNo" ng-model="resveNo" /></td>
        </tr>
        <tr>
            <%-- 예약자명 --%>
            <th><s:message code="mobile.resveInfo.resveGuestNm"/></th>
            <td><input type="text"  class="sb-input w100" id="resveGuestNm" ng-model="resveGuestNm" /></td>
        </tr>
        </tbody>
    </table>

    <div class="w100" id="mobileResveInfoGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:100px; height: 100%;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileResveInfo"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
<%--                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.storeCd"/>" binding="storeCd" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.resveDate"/>" binding="resveDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.resveTime"/>" binding="resveTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.resveGuestNm"/>" binding="resveGuestNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.resveGuestCnt"/>" binding="resveGuestCnt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.resveGuestTelNo"/>" binding="resveGuestTelNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.resveMemo"/>" binding="resveMemo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.resveNo"/>" binding="resveNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.regDate"/>" binding="regDt" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.resveInFg"/>" binding="resveInFg" width="80" align="center" is-read-only="true" data-map="inFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.resveInfo.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileResveInfoMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
</div>

<script type="text/javascript">
    var multiStoreFg = '${multiStoreFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/adi/resve/resveInfo/mobileResveInfo.js?ver=20210712.02" charset="utf-8"></script>