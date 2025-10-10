<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<style>
    .small-text-table * {
        font-size: 10px !important;
    }
</style>

<div class="subCon" ng-controller="saleAnalysisByTimeCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('saleAnalysisByTimeCtrl',1)">
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
            <%-- 조회일자 --%>
            <th>
                <s:message code="cmm.search.date"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="S"/>
                    <jsp:param name="targetId" value="saleAnalysisByTimeStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
        </tr>
        <tr>
            <%-- 지점코드 --%>
            <th>
                <s:message code="saleAnalysisByTime.branchCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchBranchCd" ng-model="branchCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 지점명 --%>
            <th>
                <s:message code="saleAnalysisByTime.branchNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchBranchNm" ng-model="branchNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 요일 --%>
            <th>
                <s:message code="saleAnalysisByTime.yoil"/>
            </th>
            <td colspan="3">
                <div class="fl pd5" style="padding-right: 15px;">
                    <div style="float: left;"><input type="checkbox" id="chkMon" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkMon"><s:message code="saleAnalysisByTime.mon" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkTue" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkTue"><s:message code="saleAnalysisByTime.tue" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkWed" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkWed"><s:message code="saleAnalysisByTime.wed" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkThu" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkThu"><s:message code="saleAnalysisByTime.thu" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkFri"  checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkFri"><s:message code="saleAnalysisByTime.fri" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkSat" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkSat"><s:message code="saleAnalysisByTime.sat" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkSun" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkSun"><s:message code="saleAnalysisByTime.sun" /></label></div>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 구분 --%>
            <th>
                <s:message code="saleAnalysisByTime.timeFg"/>
            </th>
            <td colspan="3">
                <div class="fl pd5" style="padding-right: 15px;">
                    <div style="float: left;"><input type="checkbox" id="chkGuestCnt" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkGuestCnt"><s:message code="saleAnalysisByTime.guestCnt" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkGuestAmt" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkGuestAmt"><s:message code="saleAnalysisByTime.guestAmt" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkSaleQty" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkSaleQty"><s:message code="saleAnalysisByTime.saleQty" /></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkTotSaleAmt" checked/></div>
                    <div style="float: left; padding-left:5px; padding-right:10px;"><label for="chkTotSaleAmt"><s:message code="saleAnalysisByTime.totSaleAmt" /></label></div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="report()"><s:message code="saleAnalysisByTime.print" /></button>
        <button class="btn_skyblue fr mr5" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <%--                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByTime.storeCd"/>"    binding="storeCd"   width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByTime.storeNm"/>"    binding="storeNm"   width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByTime.saleDate"/>"   binding="saleDate"  width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByTime.yoil"/>"       binding="yoil"      width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByTime.timeFg"/>"     binding="rowFg"     width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="06" end="23">
                    <wj-flex-grid-column header="${i}" binding="saleAnalsH${i}" width="80" align="right" is-read-only="true" format="n0"></wj-flex-grid-column>
                </c:forEach>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByTime.totSaleAnals"/>" binding="totSaleAnals" width="90" align="right" is-read-only="true" format="n0"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/saleAnalysis/saleAnalysisByTime/saleAnalysisByTime.js?ver=20250929.01" charset="utf-8"></script>

<%-- 시간대별 매출분석 출력 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/saleAnalysis/saleAnalysisByTime/saleAnalysisByTimeReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>