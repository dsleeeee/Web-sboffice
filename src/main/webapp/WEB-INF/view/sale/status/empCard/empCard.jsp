<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="empCardCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('empCardCtrl')">
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
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 매장코드 --%>
          <th><s:message code="cmm.store"/></th>
          <td colspan="3">
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                closeFunc - 팝업 닫기시 호출할 함수
              --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
              <jsp:param name="targetId" value="empCardStore"/>
            </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="empCardStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      <tr>
        <%-- 포스번호 --%>
        <th><s:message code="todayDtl.dtl.posNo"/></th>
        <td>
            <span class="txtIn w120px sb-select fl mr5">
              <wj-combo-box
                      id="srchPosNo"
                      ng-model="posNo"
                      items-source="_getComboData('srchPosNo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
        </td>
        <td></td>
        <td></td>
      </tr>
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
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
          <wj-flex-grid-column header="<s:message code="empCard.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empCard.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empCard.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empCard.yoil"/>" binding="yoil" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.billNo"/>" binding="billNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.billDt"/>" binding="billDt" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.saleYn"/>" binding="saleYn" width="50" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totSaleAmt"/>" binding="totSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.realSaleAmt"/>" binding="realSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.gaAmt"/>" binding="gaAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.vatAmt"/>" binding="vatAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.cupAmt"/>" binding="cupAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="empCard.employeeCardNo"/>" binding="employeeCardNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empCard.employeeNo"/>" binding="employeeNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empCard.employeeNm"/>" binding="employeeNm" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empCard.divNm"/>" binding="divNm" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empCard.deptNm"/>" binding="deptNm" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empCard.positionNm"/>" binding="positionNm" width="90" align="center" is-read-only="true"></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="todayDtl.dtl.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <%-- 결제수단 컬럼 생성--%>
          <c:forEach var="payCol" items="${payColList}">
            <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
          <%-- 할인 컬럼 생성--%>
          <c:forEach var="dcCol" items="${dcColList}">
            <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="empCardCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";

  // 결제수단
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

  // 할인
  var dcColList  = [];
  <%--javascript에서 사용할 할인 json 데이터 생성--%>
  <c:forEach var="dcCol" items="${dcColList}">
  var dcParam      = {};
  dcParam.dcCd     = "${dcCol.dcCd}";
  dcParam.dcMethod = "${dcCol.dcMethod}";
  dcColList.push(dcParam);
  </c:forEach>

  var dcCol       = '${dcCol}';
  var arrDcCol    = dcCol.split(',');
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/empCard/empCard.js?ver=20221205.02" charset="utf-8"></script>
<%-- 영수증 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/billInfo/billInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="payColList" value="${payColList}"/>
  <c:param name="guestColList" value="${guestColList}"/>
</c:import>

<%-- 결제수단 팝업 레이어 시작 --%>
<%-- 신용카드 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/card.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 현금 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/cash.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- PAYCO 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/payco.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- VMEM 포인트 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/vpoint.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- VMEM 전자상품권 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/vcharge.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 모바일페이 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/mpay.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 모바일쿠폰 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/mcoupn.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포인트 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/point.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원선불 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/prepaid.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원후불 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/postpaid.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품권 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/gift.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 식권 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/fstmp.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 제휴카드 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/partner.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사원카드 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/empCard.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 가승인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/temporary.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 스마트오더 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/payInfo/vorder.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //결제수단 팝업 레이어 --%>


<%-- 할인 팝업 레이어 시작 --%>
<%-- 일반할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/generalDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 쿠폰할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/coupnDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/membrDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 제휴할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/partnerDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 서비스 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/serviceDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 프로모션할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/promtnDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- VMEM 쿠폰할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/vcoupnDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 스마트 오더 할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/vorderDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //할인 팝업 레이어 시작 --%>