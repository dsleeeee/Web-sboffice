<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="saleByChannelCtrl">
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('saleByChannelCtrl', 1)">
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
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                        <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                            <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="saleByChannelSelectStore"/>
                        </jsp:include>
                            <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="saleByChannelSelectStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
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
                    <wj-flex-grid-column header="<s:message code="saleByChannel.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.yoil"/>"    binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.storeNm"/>" binding="storeNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <%-- 채널 구분 컬럼 생성--%>
                    <c:forEach var="dlvrCol" items="${dlvrColList}">
                        <wj-flex-grid-column header="${dlvrCol.dlvrNm}" binding="realSaleCnt${dlvrCol.dlvrFg}${dlvrCol.dlvrCd}" width="100" max-length=30 visible="${dlvrCol.visible}" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.etc"/>" binding="realSaleCntEtc" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.total"/>" binding="realSaleCntSum" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 채널 구분 컬럼 생성--%>
                    <c:forEach var="dlvrCol" items="${dlvrColList}">
                        <wj-flex-grid-column header="${dlvrCol.dlvrNm}" binding="totSaleAmt${dlvrCol.dlvrFg}${dlvrCol.dlvrCd}" width="120" max-length=30 visible="${dlvrCol.visible}" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.etc"/>" binding="totSaleAmtEtc" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.total"/>" binding="totSaleAmtSum" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 채널 구분 컬럼 생성--%>
                    <c:forEach var="dlvrCol" items="${dlvrColList}">
                        <wj-flex-grid-column header="${dlvrCol.dlvrNm}" binding="totDcAmt${dlvrCol.dlvrFg}${dlvrCol.dlvrCd}" width="120" max-length=30 visible="${dlvrCol.visible}" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.etc"/>" binding="totDcAmtEtc" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.total"/>" binding="totDcAmtSum" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 채널 구분 컬럼 생성--%>
                    <c:forEach var="dlvrCol" items="${dlvrColList}">
                        <wj-flex-grid-column header="${dlvrCol.dlvrNm}" binding="realSaleAmt${dlvrCol.dlvrFg}${dlvrCol.dlvrCd}" width="100" max-length=30 visible="${dlvrCol.visible}" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.etc"/>" binding="realSaleAmtEtc" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleByChannel.total"/>" binding="realSaleAmtSum" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var dlvrColList = [];

    <%--javascript에서 사용할 주문채널 구분자 json 데이터 생성--%>
    <c:forEach var="dlvrCol" items="${dlvrColList}">
    var param = {};
    param.dlvrFg = "${dlvrCol.dlvrFg}";
    param.dlvrCd = "${dlvrCol.dlvrCd}";
    param.dlvrNm = "${dlvrCol.dlvrNm}";
    dlvrColList.push(param);
    </c:forEach>
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/marketing/saleByChannel/saleByChannel.js?ver=20250813.01" charset="utf-8"></script>
