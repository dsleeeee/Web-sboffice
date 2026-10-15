<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="promotionReportBensonCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="promotionReportBenson.promotionReportBenson" /></a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('promotionReportBensonCtrl', 1)">
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
        <th><s:message code="promotionReportBenson.gubun"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchGubunCombo"
                    ng-model="gubunCombo"
                    items-source="_getComboData('gubunCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="changeGubun(s)">
            </wj-combo-box>
          </div>
        </td>
        <td colspan="2">
          <div id="depositYmd">
            <div class="sb-select">
              <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
            </div>
          </div>
          <div id="promotion" style="display: none">
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectPromotionM.jsp" flush="true">
              <jsp:param name="targetId" value="promotionReportBensonPromotion"/>
            </jsp:include>
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
          <td>
              <%-- 매장선택 모듈 사용시 include --%>
              <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                <jsp:param name="targetTypeFg" value="M"/>
                <jsp:param name="targetId" value="promotionReportBensonStore"/>
              </jsp:include>
              <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="promotionReportBensonStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      <tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <th><s:message code="promotionReportBenson.bizNo"/></th>
          <td><input type="text" id="srchBizNo" class="sb-input w100" ng-model="bizNo"/></td>
        </c:if>
      </tr>
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
          <wj-flex-grid-column header="" binding="promoType" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.promotionCd"/>" binding="promotionCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.promotionNm"/>" binding="promotionNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.startYmd"/>" binding="startYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.endYmd"/>" binding="endYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.bizNo"/>" binding="bizNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.hdRealSaleCntPromoCd"/>" binding="hdRealSaleCntPromoCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.dtRealSaleCntPromoCd"/>" binding="dtRealSaleCntPromoCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.realSaleQtyPromoCd"/>" binding="realSaleQtyPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.chargeDs"/>" binding="chargeDs" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.hq"/>" binding="hqChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.ms"/>" binding="msChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.partner"/>" binding="partnerChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.hq"/>" binding="hqChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.ms"/>" binding="msChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.partner"/>" binding="partnerChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReportBenson.depositYmd"/>" binding="depositYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="promotionReportBenson.remark"/>" binding="remark" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          </c:if>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="promotionReportBensonCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--위즈모 테이블--%>
  <div class="wj-gridWrap" style="display: none;" ng-controller="promotionReportBensonExcelCtrl">
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
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.promotionCd"/>" binding="promotionCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.promotionNm"/>" binding="promotionNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.startYmd"/>" binding="startYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.endYmd"/>" binding="endYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.bizNo"/>" binding="bizNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.hdRealSaleCntPromoCd"/>" binding="hdRealSaleCntPromoCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.dtRealSaleCntPromoCd"/>" binding="dtRealSaleCntPromoCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.realSaleQtyPromoCd"/>" binding="realSaleQtyPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.chargeDs"/>" binding="chargeDs" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.hq"/>" binding="hqChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.ms"/>" binding="msChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.partner"/>" binding="partnerChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.hq"/>" binding="hqChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.ms"/>" binding="msChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.partner"/>" binding="partnerChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReportBenson.depositYmd"/>" binding="depositYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <wj-flex-grid-column header="<s:message code="promotionReportBenson.remark"/>" binding="remark" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      </c:if>
    </wj-flex-grid>
  </div>
  <%--//위즈모 테이블--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}";
  var storeCd = "${storeCd}";

  // List 형식("" 안붙임)
  var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotionReportBenson/promotionReportBenson.js?ver=20261013.01" charset="utf-8"></script>

<%-- 프로모션정산 상세 --%>
<c:import url="/WEB-INF/view/base/promotion/promotionReportBenson/promotionReportBensonDtl.jsp">
</c:import>

<%-- 프로모션정산 적용상품 상세 --%>
<c:import url="/WEB-INF/view/base/promotion/promotionReportBenson/promotionSelectProdBenson.jsp">
</c:import>
