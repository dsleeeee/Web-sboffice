<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="storeChannelMonthView" class="subCon" ng-controller="storeChannelMonthCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeChannel.month"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeChannelMonthCtrl')"><s:message code="cmm.search"/></button>
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
                    <span class="txtIn"><input id="monthStartDate" name="monthStartDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="monthEndDate" name="monthEndDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                    <%-- 매장코드 --%>
                <th><s:message code="storeChannel.store"/></th>
                <td>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="storeChannelMonthStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="storeChannelMonthStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <div class="wj-TblWrap mt10">
        <div class="oh sb-select mb10">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadMonth()"><s:message code="cmm.excel.downCondition"/></button>
        </div>
        <div class="wj-TblWrapBr">
            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height: 500px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="storeChannel.saleMonth"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.totBillCnt"/>" binding="totBillCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                    <%-- 주문채널 구분자 컬럼 생성--%>
                    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                        <wj-flex-grid-column header="<s:message code="storeChannel.billCnt"/>" binding="billCnt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeChannel.realSaleAmt"/>" binding="realSaleAmt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>

                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="storeChannelDayCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/store/storeChannel/storeChannelMonth.js?ver=20221014.01" charset="utf-8"></script>