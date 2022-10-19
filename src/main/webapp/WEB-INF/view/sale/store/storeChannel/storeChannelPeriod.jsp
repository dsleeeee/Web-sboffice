<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="storeChannelPeriodView" class="subCon" ng-controller="storeChannelPeriodCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeChannel.period"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeChannelPeriodCtrl')"><s:message code="cmm.search"/></button>
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
                    <span class="txtIn"><input id="periodStartDate" name="periodStartDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="periodEndDate" name="periodEndDate" class="w110px" /></span>
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
                        <jsp:param name="targetId" value="storeChannelPeriodStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="storeChannelPeriodStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <div class="wj-TblWrap mt10">
        <div class="oh sb-select mb10">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadPeriod()"><s:message code="cmm.excel.storeChannel"/></button>
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
                        item-formatter="_itemFormatter"
                        id="wjGridStoreChannelPeriodList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="storeChannel.dlvrInFg"/>" binding="dlvrInFgNm" width="150" align="left" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.gaAmt"/>" binding="gaAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.vatAmt"/>" binding="vatAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.billCnt"/>" binding="billCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.billUprc"/>" binding="billUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeChannel.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 결제수단 컬럼 생성--%>
                    <c:forEach var="payCol" items="${payColList}">
                        <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <%-- 할인 컬럼 생성--%>
                    <c:forEach var="dcCol" items="${dcColList}">
                        <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <wj-flex-grid-column header="<s:message code="storeChannel.totGuestCnt"/>" binding="totGuestCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 객수 컬럼 생성--%>
                    <c:forEach var="guestCol" items="${guestColList}">
                        <wj-flex-grid-column header="${guestCol.guestNm}" binding="guest${guestCol.guestCd}" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <wj-flex-grid-column header="<s:message code="storeChannel.guestUprc"/>" binding="guestUprc" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="storeChannelPeriodCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>
</div>

<script type="text/javascript">
    var payColList = [];
    var dcColList = [];
    var dlvrInFgColList = [];

    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam = {};
    payParam.payCd = "${payCol.payCd}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>

    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="dcCol" items="${dcColList}">
    var dcParam = {};
    dcParam.dcCd = "${dcCol.dcCd}";
    dcParam.dcMethod = "${dcCol.dcMethod}";
    dcColList.push(dcParam);
    </c:forEach>

    <%--javascript에서 사용할 주문채널 구분자 json 데이터 생성--%>
    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
    var param = {};
    param.dlvrInFg = "${dlvrInFgCol.dlvrInFg}";
    param.dlvrInFgNm = "${dlvrInFgCol.dlvrInFgNm}";
    dlvrInFgColList.push(param);
    </c:forEach>

    var payCol = '${payCol}';
    var dcCol = '${dcCol}';
    var guestCol = '${guestCol}';
    var dlvrInFgCol = '${dlvrInFgCol}';
    var arrPayCol = payCol.split(',');
    var arrDcCol = dcCol.split(',');
    var arrGuestCol = guestCol.split(',');
    var arrDlvrInFgCol = dlvrInFgCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/store/storeChannel/storeChannelPeriod.js?ver=20221014.01" charset="utf-8"></script>