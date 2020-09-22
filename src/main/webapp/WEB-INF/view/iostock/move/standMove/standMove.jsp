<%@ page import="kr.co.common.utils.security.EncUtil" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/standMove/standMove/"/>

<div id="standMoveView" class="subCon" ng-controller="standMoveCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('standMoveCtrl')"><s:message code="cmm.search"/></button>
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
      <%-- 매장이동일자 --%>
      <th><s:message code="standMove.moveDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 이동구분 --%>
      <th><s:message code="standMove.moveFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="srchIoFg"
              ng-model="ioFg"
              items-source="_getComboData('srchIoFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </div>
      </td>
      <%-- 진행 --%>
      <th><s:message code="storeMove.procFg"/></th>
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
    </tbody>
  </table>

  <div class="tr mt10">
    <%-- 신규 --%>
    <button type="button" id="btnRegist" class="btn_skyblue ml5" ng-click="newRegist()"><s:message code="storeMove.newRegist"/></button>
  </div>
  <div style="clear: both;"></div>

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
        <wj-flex-grid-column header="<s:message code="standMove.moveDate"/>"binding="moveDate" 	width="*" align="center" 	is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="standMove.slipNo"/>" 	binding="slipNo" 	width="*" align="center" 	is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="standMove.slipFg"/>" 	binding="slipFg" 	width="*" align="center" 	is-read-only="true" data-map="ioFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="standMove.dtlCnt"/>" 	binding="dtlCnt" 	width="*" align="center" 	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="standMove.confmYn"/>" binding="confmYn" 	width="*" align="center" 	is-read-only="true" data-map="confmYnDataMap" ></wj-flex-grid-column>
      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="standMoveCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/standMove/standMove.js?ver=20200921.01" charset="utf-8"></script>




<%-- 매장 매장이동관리 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/standMove/standMoveDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장 매장이동관리 신규등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/standMove/standMoveRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장 매장이동관리 상품추가 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/standMove/standMoveAddProd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>