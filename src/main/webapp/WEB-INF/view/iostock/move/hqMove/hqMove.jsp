<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/hqMove/hqMove/"/>

<div id="hqMoveView" class="subCon" ng-controller="hqMoveCtrl">
  <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnHqMoveSearch" ng-click="_broadcast('hqMoveCtrl')">
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
          <span class="txtIn"><input id="srchHqMoveStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchHqMoveEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 배송구분 --%>
      <th><s:message code="hqStoreMove.dlvrFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="srchHqMoveDlvrFg"
              ng-model="dlvrFg"
              items-source="_getComboData('srchHqMoveDlvrFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </div>
      </td>
      <%-- 진행 --%>
      <th><s:message code="hqStoreMove.procFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="srchHqMoveProcFg"
              ng-model="procFg"
              items-source="_getComboData('srchHqMoveProcFg')"
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
      <%-- 출고창고 --%>
      <th><s:message code="hqMove.outStorage"/></th>
      <td colspan="3">
        <%-- 창고선택 모듈 싱글 선택 사용시 include
             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수
        --%>
        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStorageS.jsp" flush="true">
          <jsp:param name="targetId" value="hqMoveOutSelectStorage"/>
          <jsp:param name="displayNm" value="${selectStorageDisplayNmAll}"/>
        </jsp:include>
        <%--// 창고선택 모듈 싱글 선택 사용시 include --%>
      </td>
    </tr>
    <tr>
      <%-- 입고창고 --%>
      <th><s:message code="hqMove.inStorage"/></th>
      <td colspan="3">
        <%-- 창고선택 모듈 싱글 선택 사용시 include
             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수
        --%>
        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStorageS.jsp" flush="true">
          <jsp:param name="targetId" value="hqMoveInSelectStorage"/>
          <jsp:param name="displayNm" value="${selectStorageDisplayNmAll}"/>
        </jsp:include>
        <%--// 창고선택 모듈 싱글 선택 사용시 include --%>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="hqMoveListScaleBox"
            ng-model="hqMoveListScale"
            items-source="_getComboData('hqMoveListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
        </wj-combo-box>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadDay()">
            <s:message code="cmm.excel.down" />
        </button>
        <%-- 신규 --%>
        <button type="button" id="btnHqMoveRegist" class="btn_skyblue mr5 fr" ng-click="newHqMoveRegist()">
            <s:message code="hqStoreMove.newRegist"/>
        </button>
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
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="hqStoreMove.moveDate"/>" binding="moveDate" width="*" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.slipNo"/>" binding="slipNo" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.dlvrFg"/>" binding="dlvrFg" width="*" align="center" is-read-only="true" data-map="dlvrFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.procFg"/>" binding="procFg" width="*" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.totQty"/>" binding="dtlCnt" width="*" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqMove.storageCd"/>" binding="outStorageCd" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqMove.storageNm"/>" binding="outStorageNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.totAmt"/>" binding="outTot" width="*" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqMove.storageCd"/>" binding="inStorageCd" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqMove.storageNm"/>" binding="inStorageNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.totAmt"/>" binding="inTot" width="*" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="hqMoveCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/hqMove/hqMove.js" charset="utf-8"></script>

<%-- 본사이동관리 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/hqMove/hqMoveDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 본사이동관리 신규등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/hqMove/hqMoveRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 본사이동관리 상품추가 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/hqMove/hqMoveAddProd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>