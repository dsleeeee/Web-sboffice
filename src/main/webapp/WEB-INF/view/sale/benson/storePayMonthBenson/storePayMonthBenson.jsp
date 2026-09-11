<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
    <div ng-controller="storePayMonthBensonCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storePayMonthBenson.storePayMonthBenson" /></a>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <%-- 조회 --%>
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storePayMonthBensonCtrl', 1)">
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
                            <span class="txtIn"> <input id="startMonthMonthTotal" name="startDate" class="w110px" /></span>
                            <span class="rg">~</span>
                            <span class="txtIn"> <input id="endMonthMonthTotal" name="endDate" class="w110px" /></span>
                        </div>
                    </td>
                    <%-- 옵션 --%>
                    <th><s:message code="storeDayChannel.option"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="option"
                                    ng-model="option"
                                    items-source="_getComboData('option')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="optionCombo"
                                    selected-index-changed="changeOption(s)"
                                    selected-index="1">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 매장브랜드 --%>
                    <th><s:message code="cmm.moms.storeHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchStoreHqBrandCdCombo"
                                    ng-model="storeHqBrandCd"
                                    items-source="_getComboData('storeHqBrandCdCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchStoreHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 매장선택 --%>
                    <th class="storePayMonthBenson" style="display: none;"><s:message code="cmm.store.select"/></th>
                    <td class="storePayMonthBenson" style="display: none;">
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="storePayMonthBensonStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:450px; overflow-y: hidden; overflow-x: hidden;">
                <div class="row">
                    <wj-flex-grid
                        id="wjGridList"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="month.yearMonth"/>" binding="yearMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePayMonthBenson.branchCd"/>" binding="branchCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePayMonthBenson.branchNm"/>" binding="branchNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.storeNm"/>" binding="storeNm" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.saleCnt"/>" binding="saleCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totSaleAmt"/>" binding="totSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totDcAmt"/>" binding="totDcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.billCnt"/>" binding="billCnt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.billUprc"/>" binding="billUprc" width="100" is-read-only="true" align="right" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.gaAmt"/>" binding="gaAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.vatAmt"/>" binding="vatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totTipAmt"/>" binding="totTipAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totEtcAmt"/>" binding="totEtcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <%-- 결제수단 컬럼 생성--%>
                        <c:forEach var="payCol" items="${payColList}">
                            <wj-flex-grid-column header="<s:message code="storePayMonthBenson.payCnt"/>" binding="payCnt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storePayMonthBenson.realSaleAmt"/>" binding="payAmt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storePayMonthBenson.rate"/>" binding="payRate${payCol.payCd}" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        </c:forEach>
                    </wj-flex-grid>
                </div>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="storePayMonthBensonCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

            <%--엑셀 리스트--%>
            <div class="w100 mt10" style="display:none;" ng-controller="storePayMonthBensonExcelCtrl">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            id="wjGridExcelList"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="excelFlex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="month.yearMonth"/>" binding="yearMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePayMonthBenson.branchCd"/>" binding="branchCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePayMonthBenson.branchNm"/>" binding="branchNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.storeNm"/>" binding="storeNm" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.saleCnt"/>" binding="saleCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totSaleAmt"/>" binding="totSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totDcAmt"/>" binding="totDcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.billCnt"/>" binding="billCnt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.billUprc"/>" binding="billUprc" width="100" is-read-only="true" align="right" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.gaAmt"/>" binding="gaAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.vatAmt"/>" binding="vatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totTipAmt"/>" binding="totTipAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totEtcAmt"/>" binding="totEtcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <%-- 결제수단 컬럼 생성--%>
                        <c:forEach var="payCol" items="${payColList}">
                            <wj-flex-grid-column header="<s:message code="storePayMonthBenson.payCnt"/>" binding="payCnt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storePayMonthBenson.realSaleAmt"/>" binding="payAmt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storePayMonthBenson.rate"/>" binding="payRate${payCol.payCd}" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        </c:forEach>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
            <%--//엑셀 리스트--%>
        </div>
    </div>
</div>

<script type="text/javascript">
    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};

    // 결제수단
    var payColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam       = {};
    payParam.payCd     = "${payCol.payCd}";
    payParam.payNm     = "${payCol.payNm}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>

    var payCol    = '${payCol}';
    var arrPayCol = payCol.split(',');

    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/storePayMonthBenson/storePayMonthBenson.js?ver=20260909.01" charset="utf-8"></script>
