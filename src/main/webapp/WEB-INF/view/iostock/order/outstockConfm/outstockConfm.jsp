<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockConfm/outstockConfm/"/>

<div class="subCon" ng-controller="outstockConfmCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('outstockConfmCtrl')">
      <s:message code="cmm.search"/></button>
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
      <th><s:message code="outstockConfm.outDate"/></th>
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
      <th><s:message code="outstockConfm.procFg"/></th>
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
      <th><s:message code="outstockConfm.slipKind"/></th>
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
      <th><s:message code="outstockConfm.dlvrNm"/></th>
      <td>
          <span class="txtIn w150px sb-select fl mr5">
            <wj-combo-box
              id="srchDlvrCd"
              ng-model="srch.dlvrCd"
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
      <th><s:message code="outstockConfm.vendrNm"/></th>
      <td>
        <%-- 거래처선택 모듈 싱글 선택 사용시 include
             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수
        --%>
        <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrS.jsp" flush="true">
          <jsp:param name="targetId" value="outstockConfmSelectVendr"/>
          <jsp:param name="displayNm" value="전체"/>
          <jsp:param name="displayWidth" value="170px"/>
        </jsp:include>
        <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
      </td>
    </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="outstockConfm.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="7"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="outstockConfm.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="16"/>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="tr mt10 fr">
    <p class="s14 bk fl mr10 lh30"><s:message code="outstockConfm.reqNoConfirm"/> : </p>
    <p class="s14 bk fl mr10 lh30 red" id="reqNoConfirmCnt"></p>
    <p class="s14 bk fl mr10 lh30"><s:message code="outstockConfm.outstockNoCreate"/> : </p>
    <p class="s14 bk fl mr20 lh30 red" id="outstockNoCreateCnt"></p>
    <%-- 출고일자 --%>
    <p class="s14 bk fl mr5 lh30"><s:message code="outstockConfm.outDate"/></p>
    <div class="sb-select fl mr5">
      <span class="txtIn "><input id="outDate" class="w120px"></span>
    </div>
    <%--출고창고 --%>
          	<p class="s14 bk fl mr5 lh30"><s:message code="outstockConfm.dtl.outStorage"/></p>
          	<span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="srchOutStorageCd"
                ng-model="save.outStorageCd"
                items-source="_getComboData('srchOutStorageCd')"
                display-member-path="name"
                selected-value-path="value"                
                is-editable="false"
                initialized="_initComboBox(s)"
                selected-index-changed="selectedIndexChanged(s)"
                >
              </wj-combo-box>
            </span>
          	<%--배송기사 --%>    
          	<p class="s14 bk fl mr5 lh30"><s:message code="outstockConfm.dtl.dlvrNm"/></p>     
        	<span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="saveDlvrCd"
                ng-model="save.dlvrCd"
                items-source="_getComboData('saveDlvrCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
    
    <%-- 출고자료생성 --%>
    <button type="button" id="btnConfirm" class="btn_skyblue ml5 fl" ng-click="saveOutstockConfirm()">
      <s:message code="outstockConfm.outstockConfirm"/></button>
  </div>
  <div style="clear: both;"></div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.slipNo"/>" binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.slipFg"/>" binding="slipFg" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.vendrNm"/>" binding="vendrNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.slipKind"/>" binding="slipKind" width="70" align="center" is-read-only="true" data-map="slipKindMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.dlvrNm"/>" binding="dlvrNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.reqDate"/>" binding="reqDate" width="90" align="center" is-read-only="true" format="date" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.outDate"/>" binding="outDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.inDate"/>" binding="inDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.orderTot"/>" binding="orderTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.mgrTot"/>" binding="mgrTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.outTot"/>" binding="outTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.inTot"/>" binding="inTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.hqRemark"/>" binding="hqRemark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.outDt"/>" binding="outDt" width="150" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.outNm"/>" binding="outNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="outstockConfmCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockConfm/outstockConfm.js?ver=20200731.01" charset="utf-8"></script>

<%-- 출고확정 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/outstockConfm/outstockConfmDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 거래명세표 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/transReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
