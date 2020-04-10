<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnInstockConfm/rtnInstockConfm/"/>

<div class="subCon" ng-controller="rtnInstockConfmCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
      <%-- 출고일자 --%>
      <th><s:message code="rtnInstockConfm.outDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 진행 --%>
      <th><s:message code="rtnInstockConfm.procFg"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchProcFg"
            ng-model="procFg"
            items-source="_getComboData('srchProcFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
      <%-- 종류 --%>
      <th><s:message code="rtnInstockConfm.slipKind"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchSlipKind"
            ng-model="slipKind"
            items-source="_getComboData('srchSlipKind')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
    </tr>
    <tr>
      <%-- 기사 --%>
      <th><s:message code="rtnInstockConfm.dlvrNm"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchDlvrCd"
            ng-model="dlvrCd"
            items-source="_getComboData('srchDlvrCd')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
      <%--TODO 거래처 로그인시 처리로직 필요 --%>
        <%-- 거래처 --%>
        <th><s:message code="rtnInstockConfm.vendr"/></th>
        <td>
          <%-- 거래처선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrS.jsp" flush="true">
            <jsp:param name="targetId" value="rtnInstockConfmSelectVendr"/>
            <jsp:param name="displayNm" value="전체"/>
            <jsp:param name="displayWidth" value="170px"/>
          </jsp:include>
          <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
        </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="searchRtnInstockConfmList()"><s:message code="cmm.search"/></button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.storeCd"/>" 	binding="storeCd"   width="0" 	align="center" 	is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.slipNo"/>" 	binding="slipNo"    width="100" align="center" 	is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.slipFg"/>" 	binding="slipFg"    width="100" align="center" 	is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.slipKind"/>" 	binding="slipKind"  width="70" 	align="center" 	is-read-only="true" data-map="slipKindMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.procFg"/>" 	binding="procFg" 	width="70" 	align="center" 	is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.storeNm"/>" 	binding="storeNm" 	width="180" align="center" 	is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dlvrNm"/>" 	binding="dlvrCd" 	width="70" 	align="center" 	is-read-only="true" data-map="dlvrMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.reqDate"/>" 	binding="reqDate" 	width="90" 	align="center" 	is-read-only="true" format="date" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.outDate"/>" 	binding="outDate" 	width="90" 	align="center" 	is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.inDate"/>" 	binding="inDate" 	width="90" 	align="center" 	is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.orderTot"/>" 	binding="orderTot" 	width="130" align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.mgrTot"/>" 	binding="mgrTot" 	width="130" align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.outTot"/>" 	binding="outTot" 	width="130" align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.inTot"/>" 	binding="inTot" 	width="130" align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.penaltyAmt"/>"binding="penaltyAmt"width="130" align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnInstockConfm.remark"/>" 	binding="remark" 	width="150" align="left" 	is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="rtnInstockConfmCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript">
  var gEnvst1043 = "${envst1043}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnInstockConfm/rtnInstockConfm.js?ver=20181224.01" charset="utf-8"></script>

<%-- 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 거래명세표 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/transReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
