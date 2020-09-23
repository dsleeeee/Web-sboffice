<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrOrder/vendrOrder/"/>

<div class="subCon" ng-controller="vendrOrderCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('vendrOrderCtrl', 1)"><s:message code="cmm.search"/></button>
    <%--<a href="#" class="open"></a>--%>
    <%-- 조회 --%>
    <%--<button class="btn_blue fr" style="position: absolute; top:5px; right:5px;" id="btnSearch" ng-click="_pageView('vendrOrderCtrl', 1)">--%>
    <%--<s:message code="cmm.search"/></button>--%>
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
      <%-- 발주일자 --%>
      <th><s:message code="vendrOrder.orderDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w120px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 전표번호 --%>
      <th><s:message code="vendrOrder.slipNo"/></th>
      <td>
        <input type="text" id="srchSlipNo" name="srchSlipNo" ng-model="slipNo" class="sb-input w100" maxlength="8"/>
      </td>
      <%-- 진행 --%>
      <th><s:message code="vendrOrder.procFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
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
        </div>
      </td>
    </tr>
    <tr>
      <%-- 거래처 --%>
      <th><s:message code="vendrOrder.vendr"/></th>
      <td colspan="3">
        <%-- 거래처선택 모듈 싱글 선택 사용시 include
             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수
        --%>
        <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrS.jsp" flush="true">
          <jsp:param name="targetId" value="vendrOrderSelectVendr"/>
          <jsp:param name="displayNm" value="전체"/>
        </jsp:include>
        <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt20 tr">
    <%-- 발주신규등록 --%>
    <button type="button" class="btn_skyblue ml5" id="btnRegist" ng-click="newVendrOrder()">
      <s:message code="vendrOrder.orderRegist"/></button>
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
        is-read-only="false"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="vendrOrder.slipNo"/>" binding="slipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.vendr"/>" binding="vendrCd" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.vendr"/>" binding="vendrNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.procFg"/>" binding="procFg" width="60" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.orderType"/>" binding="orderType" width="80" align="center" is-read-only="true" data-map="orderTypeMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.orderDate"/>" binding="orderDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.orderReqDate"/>" binding="orderReqDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="vendrOrderCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrOrder/vendrOrder.js?ver=20181224.01" charset="utf-8"></script>

<%-- 발주 상세 팝업전용 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderPop.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
