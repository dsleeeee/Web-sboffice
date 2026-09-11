<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
    <div ng-controller="storeTimeSaleBensonCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeTimeSaleBenson.storeTimeSaleBenson" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storeTimeSaleBensonCtrl', 1)">
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
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
                <%-- 조회시간 --%>
                <th><s:message code="storeTimeSaleBenson.srchTime"/></th>
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
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="storeTimeSaleBensonStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="storeTimeSaleBensonStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload2()"><s:message code="cmm.excel.downCurrent"/></button>
        </div>

        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
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
                    <%-- 왼쪽 단 (0시~11시) --%>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.saleTime"/>" binding="saleTime1" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.realSaleAmt"/>" binding="realSaleAmt1" width="100" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.billCnt"/>" binding="billCnt1" width="80" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.billUprc"/>" binding="billUprc1" width="90" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.guestCnt"/>" binding="guestCnt1" width="80" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.guestUprc"/>" binding="guestUprc1" width="90" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <%-- 오른쪽 단 (12시~23시) --%>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.saleTime"/>" binding="saleTime2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.realSaleAmt"/>" binding="realSaleAmt2" width="100" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.billCnt"/>" binding="billCnt2" width="80" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.billUprc"/>" binding="billUprc2" width="90" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.guestCnt"/>" binding="guestCnt2" width="80" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeTimeSaleBenson.guestUprc"/>" binding="guestUprc2" width="90" align="right" format="n0" is-read-only="true"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/storeTimeSaleBenson/storeTimeSaleBenson.js?ver=20260910.02" charset="utf-8"></script>
