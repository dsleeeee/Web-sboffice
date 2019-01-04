<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrExact/vendrExact/"/>

<div class="subCon">
  <div ng-controller="vendrExactCtrl">
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
        <%-- 조회일자 --%>
        <th><s:message code="cmm.search.date"/></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w120px"></span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 거래처 --%>
        <th><s:message code="vendrExact.vendr"/></th>
        <td colspan="3">
          <%-- 거래처선택 모듈 멀티 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="vendrExactSelectVendr"/>
          </jsp:include>
          <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
      <%-- 조회 --%>
      <%--<button class="btn_blue fr" id="btnSearch" ng-click="_pageView('vendrExactCtrl', 1)">--%>
      <button class="btn_blue fr" id="btnSearch" ng-click="fnSearch()">
        <s:message code="cmm.search"/></button>
    </div>

    <div class="w60 fl mt10">
      <div class="wj-TblWrapBr pd20 mr10">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='vendrExact.vendrExact'/></span>
          <%-- 지급액 등록 --%>
          <button type="button" class="btn_skyblue ml5 fr" id="btnRegist" ng-click="newVendrExactRegist()">
            <s:message code="vendrExact.regExcclcAmt"/></button>
        </div>
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="vendrExact.vendrNm"/>" binding="vendrNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrExact.prevUnExcclcTot"/>" binding="prevUnExcclcTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrExact.inExcclcTot"/>" binding="inExcclcTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrExact.payExcclcTot"/>" binding="payExcclcTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrExact.totExcclcTot"/>" binding="totExcclcTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrExact.afterExcclcTot"/>" binding="afterExcclcTot" width="70" align="right" is-read-only="true" aggregate="Sum" word-wrap="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrExact.nowUnExcclcTot"/>" binding="nowUnExcclcTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrExact.vendr"/>" binding="vendrCd" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="vendrExactCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>

  <div class="w40 fr mt10" ng-controller="vendrExactDtlCtrl">
    <div class="wj-TblWrapBr pd20 mr10 ">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vendrExact.vendrExactDtl'/></span>
      </div>
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 350px;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="vendrExact.dtl.excclcDate"/>" binding="excclcDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vendrExact.dtl.inExcclcTot"/>" binding="inExcclcTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vendrExact.dtl.payExcclcTot"/>" binding="payExcclcTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vendrExact.dtl.regNm"/>" binding="regNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vendrExact.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vendrExact.dtl.seqNo"/>" binding="seqNo" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vendrExact.dtl.excclcFg"/>" binding="excclcFg" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="vendrExactDtlCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrExact/vendrExact.js?ver=20181224.01" charset="utf-8"></script>

<%-- 지급액 등록 팝업 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrExact/vendrExactRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
