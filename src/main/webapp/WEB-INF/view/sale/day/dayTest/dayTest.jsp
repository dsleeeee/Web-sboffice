<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="dayTestCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dayTestCtrl')">
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
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
            <%-- 매장코드 --%>
          <th><s:message code="todayBillSaleDtl.store"/></th>
          <td colspan="3">
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                closeFunc - 팝업 닫기시 호출할 함수
              --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
              <jsp:param name="targetId" value="dayTestStore"/>
            </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="dayTestStoreCd" value="${sessionInfo.storeCd}"/>
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
          <wj-flex-grid-column header="<s:message code="dayTest.saleDate"/>"    binding="saleDate" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.yoil"/>"        binding="yoil" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.branchNm"/>"    binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.storeCd"/>"     binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.storeNm"/>"     binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.area"/>"        binding="area" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.area2"/>"       binding="area2" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.bizArea"/>"     binding="bizArea" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.bizArea2"/>"    binding="bizArea2" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.billCnt"/>"     binding="billCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.billUprc"/>"    binding="billUprc" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.totGuestCnt"/>" binding="totGuestCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.guestUprc"/>"   binding="guestUprc" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.saleQty"/>"     binding="saleQty" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.totSaleAmt"/>"  binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.dcAmt"/>"       binding="dcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.cashAmt"/>"     binding="cashAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.cardAmt"/>"     binding="cardAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dayTest.etcAmt"/>"      binding="etcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="dayTestCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
  <div class="w100 mt10" style="display:none;" ng-controller="dayTestExcelCtrl">
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
        <wj-flex-grid-column header="<s:message code="dayTest.saleDate"/>"    binding="saleDate" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.yoil"/>"        binding="yoil" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.branchNm"/>"    binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.storeCd"/>"     binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.storeNm"/>"     binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.area"/>"        binding="area" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.area2"/>"       binding="area2" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.bizArea"/>"     binding="bizArea" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.bizArea2"/>"    binding="bizArea2" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.billCnt"/>"     binding="billCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.billUprc"/>"    binding="billUprc" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.totGuestCnt"/>" binding="totGuestCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.guestUprc"/>"   binding="guestUprc" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.saleQty"/>"     binding="saleQty" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.totSaleAmt"/>"  binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.dcAmt"/>"       binding="dcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.cashAmt"/>"     binding="cashAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.cardAmt"/>"     binding="cardAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayTest.etcAmt"/>"      binding="etcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayTest/dayTest.js?ver=20221012.01" charset="utf-8"></script>

