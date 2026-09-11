<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
    파일명 : dayTimeSaleBenson.jsp
    설  명 : 벤슨 > 매출분석 > 일별시간대별매출조회
    ======================================================
    No  ||  수정자 ||  수정일자    ||  수정내역
    ======================================================
    1       김유승     2026.09.10     최초작성
--%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
    <div ng-controller="dayTimeSaleBensonCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="dayTimeSaleBenson.dayTimeSaleBenson" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('dayTimeSaleBensonCtrl', 1)">
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
                <%-- 조회기간 --%>
                <th><s:message code="dayTimeSaleBenson.srchDate"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
                <%-- 조회시간 --%>
                <th><s:message code="dayTimeSaleBenson.srchTime"/></th>
                <td>
                    <div class="sb-select fl w200px">
                        <div class="sb-select fl" style="width:65px;">
                            <wj-combo-box
                                    id="startTime"
                                    ng-model="startTime"
                                    items-source="_getComboData('startTimeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="startTimeCombo"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <div class="fl pd5" style="padding-right: 15px;">
                            <label> ~ </label>
                        </div>
                        <div class="sb-select fl" style="width:65px;">
                            <wj-combo-box
                                    id="endTime"
                                    ng-model="endTime"
                                    items-source="_getComboData('endTimeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="endTimeCombo"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </div>
                </td>
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td colspan="3">
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="dayTimeSaleBensonStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="dayTimeSaleBensonStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
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
                    <wj-flex-grid-column header="<s:message code="dayTimeSaleBenson.saleDate"/>" binding="saleDate" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayTimeSaleBenson.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayTimeSaleBenson.realSaleAmtTot"/>" binding="realSaleAmtTot" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 시간대 컬럼 생성--%>
                    <c:forEach var="i" begin="0" end="9">
                        <wj-flex-grid-column header="0${i}" binding="realSaleAmt0${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <c:forEach var="i" begin="10" end="23">
                        <wj-flex-grid-column header="${i}" binding="realSaleAmt${i}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var menuCd = "${menuCd}";
    var menuNm = "${menuNm}";
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/dayTimeSaleBenson/dayTimeSaleBenson.js?ver=20260910.02" charset="utf-8"></script>
