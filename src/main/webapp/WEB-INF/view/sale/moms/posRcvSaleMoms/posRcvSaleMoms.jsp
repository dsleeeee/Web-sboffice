<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon">
    <div ng-controller="posRcvSaleMomsCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="posRcvSaleMoms.posRcvSaleMoms"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('posRcvSaleMomsCtrl', 1)">
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
                            <jsp:param name="targetId" value="posRcvSaleMomsStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="posRcvSaleMomsStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
        </div>

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
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.posNo"/>" binding="posNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleYn"/>" binding="saleYn" width="60" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.channelOrderNo"/>" binding="channelOrderNo" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.channelOrderNo2"/>" binding="channelOrderNo2" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.channelType"/>" binding="channelType" width="90" align="center" data-map="dlvrInFgDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.serviceType"/>" binding="dlvrOrderFg2" width="90" align="center" data-map="dlvrOrderFgDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.channelServiceType"/>" binding="channelServiceType" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.orgSaleDate"/>" binding="orgSaleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.orgPosNo"/>" binding="orgPosNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.orgBillNo"/>" binding="orgBillNo" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.tblNm"/>" binding="tblNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billDt"/>" binding="billDt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.selTypeFg"/>" binding="selTypeFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt1"/>" binding="totSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt2"/>" binding="totSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt3"/>" binding="totSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt1"/>" binding="dcAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt2"/>" binding="dcAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt3"/>" binding="dcAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt1"/>" binding="gaAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt2"/>" binding="gaAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt3"/>" binding="gaAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt1"/>" binding="vatAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt2"/>" binding="vatAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt3"/>" binding="vatAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="stinSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="stinSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="stinSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="stinRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="stinRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="stinRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="dlvrSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="dlvrSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="dlvrSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="dlvrRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="dlvrRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="dlvrRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="packSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="packSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="packSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="packRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="packRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="packRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>

                    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>

                    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>

                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    var dlvrInFg = ${ccu.getCommCodeExcpAll("112")};

    // 채널
    var dlvrInFgColList = [];

    <%--javascript에서 사용할 주문채널 구분자 json 데이터 생성--%>
    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
    var param = {};
    param.dlvrInFg = "${dlvrInFgCol.dlvrInFg}";
    param.dlvrInFgNm = "${dlvrInFgCol.dlvrInFgNm}";
    dlvrInFgColList.push(param);
    </c:forEach>

    var dlvrInFgCol = '${dlvrInFgCol}';
    var dlvrInFgColNm = '${dlvrInFgColNm}';
    var arrDlvrInFgCol = dlvrInFgCol.split(',');
    var arrDlvrInFgColNm = dlvrInFgColNm.split(',');

    // header값 셋팅을 위한 변수
    var vDlvrOrderFg = ["stin", "dlvr", "pack"];

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/moms/posRcvSaleMoms/posRcvSaleMoms.js?ver=20260923.01" charset="utf-8"></script>