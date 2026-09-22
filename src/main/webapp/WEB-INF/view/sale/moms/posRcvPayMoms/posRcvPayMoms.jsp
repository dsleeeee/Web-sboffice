<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon">
    <div ng-controller="posRcvPayMomsCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="posRcvPayMoms.posRcvPayMoms"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('posRcvPayMomsCtrl', 1)">
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
                <%-- 조회일자--%>
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchDate" class="w110px"></span>
                    </div>
                </td>
                <td></td>
                <td></td>
            </tr>
            <%-- 매장선택--%>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                            <%-- 매장선택 모듈 사용시 include (targetTypeFg=S : 싱글 선택) --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="posRcvPayMomsStore"/>
                        </jsp:include>
                            <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="posRcvPayMomsStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
        </div>

        <%-- TODO: 인터페이스정의서 기준 그리드 컬럼 구성 (결제구분, 결제수단, 승인정보, 카드결제 시 할부개월/매입사/발급사/밴사 등) --%>
        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height: 500px; overflow-x: hidden; overflow-y: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.saleYn"/>" binding="saleYn" width="80" align="center"  data-map="saleYnMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.paySeq"/>" binding="paySeq" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.payFg"/>" binding="payFg" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.payCd"/>" binding="payCd" width="90" align="center" data-map="payCdDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.payCdNo"/>" binding="payCdNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.apprDate"/>" binding="apprDate" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.apprTime"/>" binding="apprTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.apprNo"/>" binding="apprNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.apprFg"/>" binding="apprFg" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.totSaleAmt"/>" binding="totSaleAmt" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.realSaleAmt"/>" binding="realSaleAmt" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.totDcAmt"/>" binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.gaAmt"/>" binding="gaAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.vatAmt"/>" binding="vatAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.instCnt"/>" binding="instCnt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.acquireNm"/>" binding="acquireNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.issueNm"/>" binding="issueNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.vanNm"/>" binding="vanNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.couponAmt"/>" binding="couponAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.orgApprNo"/>" binding="orgApprNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posRcvPayMoms.orgApprDate"/>" binding="orgApprDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var payCd = ${ccu.getCommCode("024")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/moms/posRcvPayMoms/posRcvPayMoms.js?ver=20260922.01" charset="utf-8"></script>
