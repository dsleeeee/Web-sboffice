<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="giftCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('giftCtrl')">
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
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
        <%-- 매장브랜드 --%>
        <th><s:message code="mcoupn.hqBrand"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="hqBrandCd"
                    ng-model="hqBrandCd"
                    items-source="_getComboData('hqBrandCd')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="hqBrandCdCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
          <%-- 매장선택 --%>
          <th><s:message code="cmm.store.select"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="S"/>
              <jsp:param name="targetId" value="giftStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="giftStoreCd" value="${sessionInfo.storeCd}"/>
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
          <wj-flex-grid-column header="<s:message code="gift.saleDate"/>" binding="saleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.branchNm"/>" binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.storeCd"/>"  binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.storeNm"/>"  binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.posNo"/>"    binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.billNo"/>"   binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.saleFg"/>"   binding="saleFg" width="80" align="center" is-read-only="true" data-map="saleFgMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftNm"/>"   binding="giftNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftCd"/>"   binding="giftCd" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftAmt"/>"  binding="giftAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.apprAmt"/>"  binding="apprAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.apprNo"/>"   binding="apprNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.apprDt"/>"   binding="apprDt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="giftCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="giftCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
  <div class="w100 mt10" style="display:none;" ng-controller="giftExcelCtrl">
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
        <wj-flex-grid-column header="<s:message code="gift.saleDate"/>" binding="saleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.branchNm"/>" binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.storeCd"/>"  binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.storeNm"/>"  binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.posNo"/>"    binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.billNo"/>"   binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.saleFg"/>"   binding="saleFg" width="80" align="center" is-read-only="true" data-map="saleFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.giftNm"/>"   binding="giftNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.giftCd"/>"   binding="giftCd" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.giftAmt"/>"  binding="giftAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.apprAmt"/>"  binding="apprAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.apprNo"/>"   binding="apprNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="gift.apprDt"/>"   binding="apprDt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
  var hqBrandList = ${hqBrandList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/appr/gift/gift.js?ver=20221018.01" charset="utf-8"></script>

