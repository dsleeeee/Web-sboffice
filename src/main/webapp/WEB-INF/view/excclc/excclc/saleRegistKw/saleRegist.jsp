<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeNm" value="${sessionScope.sessionInfo.storeNm}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="saleRegistCtrl">
  <div class="searchBar ">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('saleRegistCtrl')">
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
          <span class="txtIn"><input id="srchSaleDate" class="w110px"></span>
        </div>
      </td>
      <c:if test="${orgnFg == 'HQ'}">
        <th><s:message code="saleRegist.store"/></th>
        <td>
          <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
          <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
            <jsp:param name="targetId" value="saleRegistStore"/>
          </jsp:include>
        </td>
      </c:if>
    </tr>
    </tbody>
  </table>

  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="newRegist()"><s:message code="saleRegist.newRegist"/></button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="saleRegist.posNo"/>"        binding="posNo" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.billNo"/>"       binding="billNo" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.saleFg"/>"       binding="saleFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.billDtlCnt"/>"   binding="billDtlCnt" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.totSaleAmt"/>"   binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.dcAmt"/>"        binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.realSaleAmt"/>"  binding="realSaleAmt" width="80" align="right" is-read-only="true"  aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.gaAmt"/>"        binding="gaAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.vatAmt"/>"       binding="vatAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.payAmt"/>"       binding="payAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.cashSaleAmt"/>"  binding="cashSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.cardSaleAmt"/>"  binding="cardSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.etcSaleAmt"/>"   binding="etcSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="saleRegist.modFg"/>"        binding="modFg" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="saleRegistCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>
<script>
  var orgnFg = "${orgnFg}";
  var storeNm = "${storeNm}";
  var storeCd = "${storeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/saleRegistKw/saleRegist.js?ver=20220831.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 신규등록 --%>
<c:import url="/WEB-INF/view/excclc/excclc/saleRegistKw/newRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>