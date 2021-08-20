<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="rtnStatusBillView" class="subCon"  ng-controller="rtnStatusBillCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="rtnStatus.rtnBill"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnrtnStatusBillSearch" ng-click="_broadcast('rtnStatusBillCtrl')">
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
            <span class="txtIn"><input id="srchrtnStatusBillStartDate" class="w110px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchrtnStatusBillEndDate" class="w110px"></span>
        </div>
        </td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="rtnStatusBillSelectStoreCd" value=""/>
        <%-- 매장코드 --%>
        <th><s:message code="rtnStatus.storeCd"/></th>
        <td>
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="rtnStatusBillSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="rtnStatusBillSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      <tr>
          <%-- 조회옵션1 --%>
          <th><s:message code="rtnStatus.option1"/></th>
          <td>
              <div class="sb-select w100">
                  <wj-combo-box
                          id="srchOption1"
                          items-source="_getComboData('srchOption1')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          control="srchOption1Combo"
                          selected-index="1">
                  </wj-combo-box>
              </div>
          </td>
              <%-- 조회옵션2 --%>
              <th><s:message code="rtnStatus.option2"/></th>
              <td>
                  <div class="sb-select w100">
                      <wj-combo-box
                              id="srchOption2"
                              items-source="_getComboData('srchOption2')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              control="srchOption2Combo"
                              selected-index="2">
                      </wj-combo-box>
                  </div>
              </td>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <!-- contents start -->
	<div class="wj-gridWrap2 mt20">
	    <%-- wj grid start --%>
	    <div class="mt20 oh sb-select dkbr">
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="text" id="rtnStatusBillSelectStoreStoreNum" ng-model="storeNum">
        </c:if>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
        </button>
        </div>
	    <div class="wj-gridWrap2 mt10">
			<wj-flex-grid
			  id="rtnStatusBillGrid"
			  autoGenerateColumns="false"
			  selection-mode="Row"
			  items-source="data"
			  control="flex"
			  initialized="initGrid(s,e)"
			  is-read-only="true"
			  item-formatter="_itemFormatter">

			  <!-- define columns -->
              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <wj-flex-grid-column header="<s:message code="rtnStatus.storeCd"/>"     binding="storeCd"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="rtnStatus.storeNm"/>"     binding="storeNm"       width="150" align="left" is-read-only="true"></wj-flex-grid-column>
              </c:if>
			  <wj-flex-grid-column header="<s:message code="rtnStatus.saleDate"/>"      binding="saleDate"      width="80" align="center" is-read-only="true"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="rtnStatus.posNo"/>"         binding="posNo"         width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="rtnStatus.bill.no"/>"       binding="billNo"        width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="rtnStatus.saleDate"/>"      binding="orgSaleDate"   width="80" align="center" is-read-only="true"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="rtnStatus.posNo"/>"         binding="orgPosNo"      width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="rtnStatus.bill.no"/>"       binding="orgBillNo"     width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="rtnStatus.totSaleAmt"/>"    binding="totSaleAmt"    width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="rtnStatus.totDcAmt"/>"          binding="totDcAmt"      width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="rtnStatus.realSaleAmt"/>"   binding="realSaleAmt"   width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="rtnStatus.gaAmt"/>"         binding="gaAmt"         width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="rtnStatus.vatAmt"/>"        binding="vatAmt"        width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="rtnStatus.totTipAmt"/>"     binding="totTipAmt"     width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="rtnStatus.totEtcAmt"/>"     binding="totEtcAmt"     width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="rtnStatus.dtl.totPayAmt"/>" binding="totPayAmt"     width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <%-- 결제수단 컬럼 생성--%>
                <c:forEach var="payCol" items="${payColList}">
                    <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
			  <jsp:param name="pickerTarget" value="rtnStatusBillCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
	    </div>
	    <%-- //wj grid end --%>

	</div>
</div>
<script type="text/javascript">
    var srchOption2 = ${ccu.getCommCode("024")};

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

    // 할인
    var dcColList = [];
    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="dcCol" items="${dcColList}">
    var dcParam      = {};
    dcParam.dcCd     = "${dcCol.dcCd}";
    dcParam.dcMethod = "${dcCol.dcMethod}";
    dcColList.push(dcParam);
    </c:forEach>

    var dcCol    = '${dcCol}';
    var arrDcCol = dcCol.split(',');

    var guestCol    = '${guestCol}';
    var arrGuestCol = guestCol.split(',');
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/rtnStatus/rtnStatusBill.js?ver=20210721.01" charset="utf-8"></script>
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