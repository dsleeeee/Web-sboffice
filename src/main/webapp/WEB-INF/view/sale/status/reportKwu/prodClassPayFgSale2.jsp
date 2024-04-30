<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" ng-controller="prodClassPayFgSale2Ctrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="prodClassPayFgSale2.prodClassPayFgSale2"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('prodClassPayFgSale2Ctrl')">
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
          <th><s:message code="cmm.search.date" /></th>
          <td >
            <div class="sb-select">
              <span class="txtIn"><input id="srchStartDate" ng-model="srchStartDate" class="w110px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchEndDate" ng-model="srchEndDate" class="w110px"></span>
            </div>
          </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="prodClassPayFgSale2Store"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <td></td>
                <td></td>
                <input type="hidden" id="prodClassPayFgSale2StoreCd" value="${sessionInfo.storeCd}"/>
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
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    id="wjGrid">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.storeCd"/>"       binding="storeCd"       width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.saleDate"/>"      binding="saleDate"      width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.prodClassNm"/>"   binding="prodClassNm"   width="130" align="left"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.prodCd"/>"        binding="prodCd"        width="100"  align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.prodNm"/>"        binding="prodNm"        width="130" align="left"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.saleQty"/>"       binding="saleQty"       width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.saleAmt"/>"       binding="saleAmt"       width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.dcAmt"/>"         binding="dcAmt"         width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.realSaleAmt"/>"   binding="realSaleAmt"   width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.gaAmt"/>"         binding="gaAmt"         width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.vatAmt"/>"        binding="vatAmt"        width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.cashAmt"/>"       binding="cashAmt"       width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.cashBillAmt"/>"   binding="cashBillAmt"   width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale2.cardAmt"/>"       binding="cardAmt"       width="90"  align="right"   is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
</div>

<script type="text/javascript">
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
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/reportKwu/prodClassPayFgSale2.js?ver=20220916.01" charset="utf-8"></script>