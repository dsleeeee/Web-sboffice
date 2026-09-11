<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="payFgBensonCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="payFgBenson.payFgBenson" /></a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('payFgBensonCtrl', 1)">
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
            <span class="txtIn"><input id="srchPayFgBensonStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchPayFgBensonEndDate" class="w110px"></span>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
      <tr>
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
                    selected-index="2">
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
          <th class="payFgBensonStore"><s:message code="cmm.store.select"/></th>
          <td class="payFgBensonStore">
              <%-- 매장선택 모듈 사용시 include --%>
              <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                <jsp:param name="targetTypeFg" value="M"/>
                <jsp:param name="targetId" value="payFgBensonStore"/>
              </jsp:include>
              <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="payFgBensonStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
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
          <wj-flex-grid-column header="<s:message code="payFgBenson.branchCd"/>"    binding="branchCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFgBenson.branchNm"/>"    binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFgBenson.storeCd"/>"     binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFgBenson.storeNm"/>"     binding="storeNm" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFgBenson.payFg"/>"       binding="payCd" width="100" align="center" is-read-only="true" data-map="payCdDataMap" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFgBenson.payCnt"/>"  binding="payCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFgBenson.totSaleAmt"/>"  binding="payAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFgBenson.rate"/>"        binding="rate" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="payFgBensonCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
  <div class="w100 mt10" style="display:none;" ng-controller="payFgBensonExcelCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        id="wjGridExcelList"
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="payFgBenson.branchCd"/>"    binding="branchCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFgBenson.branchNm"/>"    binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFgBenson.storeCd"/>"     binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFgBenson.storeNm"/>"     binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFgBenson.payFg"/>"       binding="payCd" width="100" align="center" is-read-only="true" data-map="payCdDataMap" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFgBenson.payCnt"/>"  binding="payCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFgBenson.totSaleAmt"/>"  binding="payAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFgBenson.rate"/>"        binding="rate" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
  var payCd = ${ccu.getCommCode("024")};

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

  var payCol    = '${payCol}';
  var arrPayCol = payCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/payFgBenson/payFgBenson.js?ver=20260910.01" charset="utf-8"></script>
