<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon">
  <div ng-controller="payDayCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('payDayCtrl')">
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
            <span class="txtIn"><input id="srchPayDayStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchPayDayEndDate" class="w110px"></span>
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
              <jsp:param name="targetId" value="payDaySelectStore"/>
            </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="payDaySelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
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
          <wj-flex-grid-column header="<s:message code="payDay.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.yoil"/>" binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.storeCnt"/>" binding="storeCnt" width="80" align="center" is-read-only="true" ng-if="orgnFg == 'H'"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.billCnt"/>" binding="billCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.billUprc"/>" binding="billUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.gaAmt"/>" binding="gaAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.vatAmt"/>" binding="vatAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.cupAmt"/>" binding="cupAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <%-- 결제수단 컬럼 생성--%>
          <c:forEach var="payCol" items="${payColList}">
            <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
          <wj-flex-grid-column header="<s:message code="payDay.genRealSaleAmt"/>" binding="genRealSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.genRealSaleRate"/>" binding="genRealSaleRate" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.dlvrRealSaleAmt"/>" binding="dlvrRealSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.dlvrRealSaleRate"/>" binding="dlvrRealSaleRate" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.packRealSaleAmt"/>" binding="packRealSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDay.packRealSaleRate"/>" binding="packRealSaleRate" width="100" align="right" is-read-only="true"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="payDayCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
</div>

<script type="text/javascript">
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

  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/pay/payDay/payDay.js?ver=20221013.01" charset="utf-8"></script>