<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="saleCancelStatusAllView" class="subCon" style="padding: 10px 20px 40px;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="saleCancelStatus.allStore"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearchAll" ng-click="_broadcast('saleCancelStatusAllCtrl')">
            <s:message code="cmm.search"/>
        </button>
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
                    <span class="txtIn"><input id="srchStartDateAll" ng-model="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDateAll" ng-model="endDate" class="w110px"></span>
            </span>
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
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="saleCancelStatusAllSelctStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="saleCancelStatusAllSelctStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <!-- contents start -->
    <div class="wj-gridWrap2 mt10" ng-controller="saleCancelStatusAllCtrl">
        <%-- wj grid start --%>
        <div class="oh sb-select dkbr mb10 mr10" >
            <%-- 엑셀 다운로드 //TODO --%>
            <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
            </button>
        </div>
        <div class="wj-gridWrap">
            <wj-flex-grid
                    id="saleCancelStatusAllMainGrid"
                    loaded-rows="loadedRows(s,e)"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.storeCd"/>"      binding="storeCd"       width="80"   align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.storeNm"/>"      binding="storeNm"       width="*"  align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.saleCnt"/>"      binding="saleCnt"       width="80"   align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.realSaleAmt"/>"  binding="realSaleAmt"   width="80"   align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.rtnCnt"/>"       binding="rtnCnt"        width="80"   align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.rtnAmt"/>"       binding="rtnAmt"        width="80"   align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.cancelCnt"/>"    binding="cancelCnt"     width="80"   align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.cancelAmt"/>"    binding="cancelAmt"     width="80"   align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="saleCancelStatusAllCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%-- //wj grid end --%>
    </div>
    <!-- //contents end -->

    <!-- 하단 그리드 -->
    <!-- contents start -->
    <%-- wj grid start --%>
    <div class="wj-gridWrap2 mt10" ng-controller="saleCancelStatusAllDtlCtrl">
        <%-- left --%>
        <div class="oh sb-select dkbr mb10 mr10" >
            <%-- 엑셀 다운로드 --%>
            <button class="btn_skyblue fr" ng-click="excelDownloadDtl()"><s:message code="cmm.excel.down" />
            </button>
        </div>
        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        id="saleCancelStatusAllDtlGrid"
                        loaded-rows="loadedRows2(s,e)"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">
                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.storeCd"/>"      binding="storeCd"       width="80"  align="center"    is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.storeNm"/>"      binding="storeNm"       width="100" align="center"    is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.saleDate"/>"     binding="saleDate"      width="90"  align="center"    is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.posNo"/>"        binding="posNo"         width="80"  align="center"    is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.billNo"/>"       binding="billNo"        width="80"  align="center"    is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.cancelDt"/>"     binding="cancelDt"      width="130" align="center"    is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.cancelFg"/>"     binding="cancelFg"      width="80"  align="center"    is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.totSaleAmt"/>"   binding="totSaleAmt"    width="80"  align="right"     is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.dcAmt"/>"        binding="totDcAmt"      width="80"  align="right"     is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.cancelAmt"/>"    binding="cancelAmt"     width="80"  align="right"     is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleCancelStatus.orderFg"/>"      binding="orderFg"       width="80"  align="center"    is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="saleCancelStatusAllDtlCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
    </div>
    <%-- //wj grid end --%>
    <!-- //contents end -->
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/marketing/saleCancelStatus/saleCancelStatusAll.js?ver=20250731.01" charset="utf-8"></script>