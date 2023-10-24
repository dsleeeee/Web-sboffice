<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="storeNm" value="${sessionScope.sessionInfo.storeNm}" />

<div id="daySaleReportListView" class="subCon" style="display: none;">
    <div ng-controller="daySaleReportListCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="daySaleReportList.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('daySaleReportListCtrl',1)">
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
                <td>
                    <div class="sb-select">
                        <span class="txtIn"> <input id="daySaleReportListStartMonth" name="startMonth" class="w100px" /></span>
                    </div>
                </td>
                <c:if test="${orgnFg == 'HQ'}">
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="daySaleReportListStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </c:if>
                <c:if test="${orgnFg == 'STORE'}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                <s:message code="cmm.excel.down" />
            </button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        id="wjGridDaySaleReportListList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="daySaleReportList.prodNm"/>" binding="prodNm" width="130" is-read-only="true" align="right"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    var storeNm = "${storeNm}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/daySaleReport/daySaleReportList.js?ver=20220531.02" charset="utf-8"></script>