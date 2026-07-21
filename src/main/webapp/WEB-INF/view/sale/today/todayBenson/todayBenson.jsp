<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>--%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
    <div ng-controller="todayBensonCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="todayBenson.todayBenson"/></a>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <%-- 조회 --%>
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('todayBensonCtrl', 1)">
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
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                    </div>
                </td>
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
                    <th><s:message code="cmm.store.select"/></th>
                    <td colspan="3">
                            <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="todayBensonStore"/>
                        </jsp:include>
                            <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="todayBensonStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
        </div>

        <div class="w100 mt10">
            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="todayBenson.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayBenson.storeNm"/>" binding="storeNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.billNo"/>" binding="billNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.billDt"/>" binding="billDt" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.saleYn"/>" binding="saleYn" width="50" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totSaleAmt"/>" binding="totSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.realSaleAmt"/>" binding="realSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.gaAmt"/>" binding="gaAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.vatAmt"/>" binding="vatAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.cupAmt"/>" binding="cupAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 결제수단 컬럼 생성--%>
                    <c:forEach var="payCol" items="${payColList}">
                        <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <%-- 모바일페이상세 컬럼 생성--%>
                    <c:forEach var="mpayCol" items="${mpayColList}">
                        <wj-flex-grid-column header="${mpayCol.mpayNm}" binding="mpay${mpayCol.mpayCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <%-- 할인 컬럼 생성--%>
                    <c:forEach var="dcCol" items="${dcColList}">
                        <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="todayBensonCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="todayBensonCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="todayBensonExcelCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="todayBenson.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayBenson.storeNm"/>" binding="storeNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.billNo"/>" binding="billNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.billDt"/>" binding="billDt" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.saleYn"/>" binding="saleYn" width="50" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totSaleAmt"/>" binding="totSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.realSaleAmt"/>" binding="realSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.gaAmt"/>" binding="gaAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.vatAmt"/>" binding="vatAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.cupAmt"/>" binding="cupAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 결제수단 컬럼 생성--%>
                <c:forEach var="payCol" items="${payColList}">
                    <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>
                <%-- 모바일페이상세 컬럼 생성--%>
                <c:forEach var="mpayCol" items="${mpayColList}">
                    <wj-flex-grid-column header="${mpayCol.mpayNm}" binding="mpay${mpayCol.mpayCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>
                <%-- 할인 컬럼 생성--%>
                <c:forEach var="dcCol" items="${dcColList}">
                    <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};

    // 결제수단
    var payColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam       = {};
    payParam.payCd     = "${payCol.payCd}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>

    var payCol      = '${payCol}';
    var arrPayCol   = payCol.split(',');

    // 모바일페이상세
    var mpayColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="mpayCol" items="${mpayColList}">
    var mpayParam       = {};
    mpayParam.payCd     = "${mpayCol.mpayCd}";
    mpayParam.payMethod = "${mpayCol.mpayMethod}";
    mpayColList.push(mpayParam);
    </c:forEach>

    var mpayCol    = '${mpayCol}';
    var arrMpayCol = mpayCol.split(',');

    // 할인
    var dcColList  = [];
    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="dcCol" items="${dcColList}">
    var dcParam      = {};
    dcParam.dcCd     = "${dcCol.dcCd}";
    dcParam.dcMethod = "${dcCol.dcMethod}";
    dcColList.push(dcParam);
    </c:forEach>

    var dcCol       = '${dcCol}';
    var arrDcCol    = dcCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/today/todayBenson/todayBenson.js?ver=20260716.01" charset="utf-8"></script>

<%-- 영수증 팝업 --%>
<c:import url="/WEB-INF/view/sale/today/todayBenson/billInfoBenson.jsp">
</c:import>
