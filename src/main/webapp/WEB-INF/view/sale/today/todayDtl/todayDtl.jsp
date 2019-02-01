<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/today/todayDtl/todayDtl/"/>

<div class="subCon">
  <div ng-controller="todayDtlCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('todayDtlCtrl')">
          <s:message code="cmm.search" />
        </button>
    </div>
    <table class="searchTbl">
      <colgroup>
        <col class="w25"/>
        <col class="w75"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 조회일자 --%>
        <th><s:message code="cmm.search.date"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
            <%-- 매장코드 --%>
          <th><s:message code="todayDtl.store"/></th>
          <td>
              <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
              <jsp:param name="targetId" value="todayDtlSelectStore"/>
            </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="todayDtlSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      <tr>
        <%-- 포스번호 --%>
        <th><s:message code="todayDtl.dtl.posNo"/></th>
        <td>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="wj-TblWrap mt10" style="height: 400px;">

      <div class="w100 mt10">
        <div class="wj-TblWrapBr">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 200px;">
            <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="todayDtl.saleYn"/>" binding="saleYn" width="60" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.gaAmt"/>" binding="gaAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.vatAmt"/>" binding="vatAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.billCnt"/>" binding="billCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.billUprc"/>" binding="billUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.totPayAmt"/>" binding="totPayAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <%-- 결제수단 컬럼 생성--%>
              <c:forEach var="payCol" items="${payColList}">
                <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>
              <%-- 할인 컬럼 생성--%>
              <c:forEach var="dcCol" items="${dcColList}">
                <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>

              <wj-flex-grid-column header="<s:message code="todayDtl.totGuestCnt"/>" binding="totGuestCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

              <%-- 객수 컬럼 생성--%>
              <c:forEach var="guestCol" items="${guestColList}">
                <wj-flex-grid-column header="${guestCol.guestNm}" binding="guest${guestCol.guestCd}" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>

              <wj-flex-grid-column header="<s:message code="todayDtl.guestUprc"/>" binding="guestUprc" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="todayDtlCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
        </div>
      </div>

      <div style="clear: both"></div>

      <div class="w100 mt10" ng-controller="todayDtlDetailCtrl">
        <div class="wj-TblWrapBr">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 300px;">
            <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.billDt"/>" binding="billDt" width="80" align="center" is-read-only="true" format="time"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.saleYn"/>" binding="saleYn" width="50" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totSaleAmt"/>" binding="totSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.realSaleAmt"/>" binding="realSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.gaAmt"/>" binding="gaAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.vatAmt"/>" binding="vatAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.membrNo"/>" binding="membrNo" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.membrNm"/>" binding="membrNm" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.tblNm"/>" binding="tblNm" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totPayAmt"/>" binding="totPayAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <%-- 결제수단 컬럼 생성--%>
              <c:forEach var="payCol" items="${payColList}">
                <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>
              <%-- 할인 컬럼 생성--%>
              <c:forEach var="dcCol" items="${dcColList}">
                <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>

              <%--<wj-flex-grid-column header="<s:message code="todayDtl.dtl.firstOrderDt"/>" binding="firstOrderDt" width="100" align="center" is-read-only="true" format="time"></wj-flex-grid-column>--%>
              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totGuestCnt"/>" binding="totGuestCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

              <%-- 객수 컬럼 생성--%>
              <c:forEach var="guestCol" items="${guestColList}">
                <wj-flex-grid-column header="${guestCol.guestNm}" binding="guest${guestCol.guestCd}" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>

              <wj-flex-grid-column header="<s:message code="todayDtl.dtl.guestUprc"/>" binding="guestUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="todayDtlDetailCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
  var payCol      = '${payCol}';
  var dcCol       = '${dcCol}';
  var guestCol    = '${guestCol}';
  var arrPayCol   = payCol.split(',');
  var arrDcCol    = dcCol.split(',');
  var arrGuestCol = guestCol.split(',');
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/today/todayDtl/todayDtl.js?ver=20190124.01" charset="utf-8"></script>

<%-- 영수증 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/billInfo/billInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="payColList" value="${payColList}"/>
  <c:param name="guestColList" value="${guestColList}"/>
</c:import>
