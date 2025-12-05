<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="inStockReportDtlCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('inStockReportDtlCtrl', 1)"><s:message code="cmm.search"/></button>
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
                    <span class="txtIn"> <input id="srchStartDate" name="srchStartDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="srchEndDate" name="srchEndDate" class="w110px" /></span>
                    </span>
                </div>
            </td>
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="S"/>
                    <jsp:param name="targetId" value="inStockReportDtlStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
        </tr>
        <tr>
            <th><s:message code="cmm.vendr.select"/></th>
            <td>
                <%-- 거래처선택 모듈 싱글 선택 사용시 include
                     param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                  closeFunc - 팝업 닫기시 호출할 함수
                --%>
                <jsp:include page="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/acquireSelectVendr.jsp" flush="true">
                    <jsp:param name="targetId" value="inStockReportDtlVendr"/>
                    <jsp:param name="vendrFg" value="1"/>
                </jsp:include>
                <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
            </td>
            <%--분류 선택--%>
            <th><s:message code="hqSplyPrice.select.prodClass"/></th>
            <td>
                <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="선택" readonly />
                <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 tr">
        <%-- 출력 --%>
        <button type="button" class="btn_skyblue ml5" ng-click="print()">
            <s:message code="inStockReportDtl.print"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.vendrCd"/>"      binding="vendrCd"       width="90"  align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.vendrNm"/>"      binding="vendrNm"       width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.storeCd"/>"      binding="storeCd"       width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.storeNm"/>"      binding="storeNm"       width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.prodCd"/>"       binding="prodCd"        width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.prodNm"/>"       binding="prodNm"        width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.tradeFg"/>"      binding="tradeFg"       width="60"  align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.saleUprc"/>"     binding="saleUprc"      width="70"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.costUprc"/>"     binding="costUprc"      width="70"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.acquireRate"/>"  binding="acquireRate"   width="70"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.acquireQty"/>"   binding="acquireQty"    width="70"  align="right"   is-read-only="true"> </wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.acquireAmt"/>"   binding="acquireAmt"    width="70"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.rtnQty"/>"       binding="rtnQty"        width="70"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.rtnAmt"/>"       binding="rtnAmt"        width="70"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportDtl.totAmt"/>"       binding="totAmt"        width="70"  align="right"   is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="inStockReportDtlCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/acquire/inStockReportDtl/inStockReportDtl.js?ver=20251202.01" charset="utf-8"></script>

<%-- 매입처별 상세매입내역(상품별) 팝업 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/acquire/inStockReportDtl/inStockReportDtlReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>