<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/today/todayGnrlz/todayGnrlz/"/>

<div id="todayGnrlzView" class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl"><s:message code="todayGnrlz.todaySaleTotal"/></a>
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
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
    <tr ng-controller="storeCtrl">
      <%-- 매장코드 --%>
      <th><s:message code="todayGnrlz.store"/></th>
      <td>
        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
          <jsp:param name="targetId" value="todayGnrlzSelectStore"/>
        </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
    </tr>
    </c:if>
    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
      <input type="hidden" id="todayGnrlzSelectStoreCd" value="${sessionInfo.storeCd}"/>
    </c:if>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('todayGnrlzCtrl')"><s:message code="cmm.search"/></button>
  </div>

  <div class="w40 fl" style="width:44%;">
    <%-- 매출종합 --%>
    <div class="w100 mt10" ng-controller="todayGnrlzCtrl">
      <div class="oh sb-select mb10">
        <span class="fl bk lh30"><s:message code='todayGnrlz.subTitleGnrlz'/></span>
        <%-- 매출총합 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadGnrlz()"><s:message code="cmm.excel.down"/></button>

      </div>

      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 100px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="todayGnrlz.totSaleAmt"/>" binding="totSaleAmt" width="*" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.realSaleAmt"/>" binding="realSaleAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.cancelAmt"/>" binding="cancelAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="todayGnrlzCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <%-- 결제수단별 매출 --%>
    <div class="w100 mt10" ng-controller="todayGnrlzPayCtrl">
      <div class="oh sb-select mb10">
        <span class="fl bk lh30"><s:message code='todayGnrlz.subTitlePay'/></span>

        <%-- 결제수단별 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadPay()"><s:message code="cmm.excel.down"/></button>
      </div>

      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 150px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="todayGnrlz.payNm"/>" binding="payNm" width="*" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.payAmt"/>" binding="payAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.payRate"/>" binding="payRate" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="todayGnrlzCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <%-- 회원 Point 적립/사용 --%>
    <div class="w100 mt10" ng-controller="todayGnrlzMemberCtrl">
      <div class="oh sb-select mb10">
        <span class="fl bk lh30"><s:message code='todayGnrlz.subTitleMember'/></span>
        <%-- 회원 point 적립/사용 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadMember()"><s:message code="cmm.excel.down"/></button>
      </div>

      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 150px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="todayGnrlz.memberNm"/>" binding="membrNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.savePoint"/>" binding="savePoint" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.usePoint"/>" binding="usePoint" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="todayGnrlzCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <div class="w60 fr" style="width:55%;">
    <%-- 상품별 매출현황 --%>
    <div class="w100 mt10" ng-controller="todayGnrlzProdCtrl">
      <div class="oh sb-select mb10">
          <span class="fl bk lh30"><s:message code='todayGnrlz.subTitleProd'/></span>
        <%-- 상품별 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadProd()"><s:message code="cmm.excel.down"/></button>
      </div>

      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 500px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="todayGnrlz.prodCd"/>" binding="prodCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.saleQty"/>" binding="saleQty" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayGnrlz.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="todayGnrlzCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/today/todayGnrlz/todayGnrlz.js?ver=20190122.09" charset="utf-8"></script>

