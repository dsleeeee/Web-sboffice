<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/fnrchs/prod/prod/"/>

<div class="subCon3" ng-controller="prodCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open  fl">${menuNm}</a>
        <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('prodCtrlSrch')">
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
      <th><s:message code="prodClass.outDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchProdStartDate" class="w120px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchProdEndDate" class="w120px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 상품분류 --%>
      <th><s:message code="slipStockInfo.prodClass"/></th>
      <td>
        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
               placeholder="<s:message code="cmm.all" />" readonly/>
        <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
      	<button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
      </td>
      <%-- 상품코드 --%>
      <th><s:message code="prodStockInfo.prodCd"/></th>
      <td>
        <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
      </td>
    </tr>
    <tr>
      <%-- 상품명 --%>
      <th><s:message code="prodStockInfo.prodNm"/></th>
      <td>
        <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="16"/>
      </td>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <input type="hidden" id="prodSelectStoreCd" value="" />
          <%-- 매장코드 --%>
          <th style="border-left: 1px solid #ccc"><s:message code="slipStockInfo.storeNm" /></th>
          <td>
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                  param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                  closeFunc - 팝업 닫기시 호출할 함수            --%>
              <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                  <jsp:param name="targetId" value="prodSelectStore" />
              </jsp:include> <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
      </c:if>
    </tr>
    </tbody>
  </table>

  <div class="mt20 oh sb-select dkbr">
  	<%-- 페이지 스케일  --%>
	<wj-combo-box
	  class="w100px fl"
	  id="prodlistScaleBox"
	  ng-model="prodlistScale"
	  control="listScaleCombo"
	  items-source="_getComboData('prodlistScaleBox')"
	  display-member-path="name"
	  selected-value-path="value"
	  is-editable="false"
	  initialized="_initComboBox(s)">
	</wj-combo-box>
	<c:if test="${sessionInfo.orgnFg == 'HQ'}">
	  <input type="text" id="prodSelectStoreStoreNum" ng-model="storeNum">
	</c:if>
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue fr" ng-click="excelDownloadProd()">
       <s:message code="cmm.excel.down" />
    </button>
  </div>

  <div id="wjWrapType2" class="w100 mt10">
    <div class="wj-TblWrapBr">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodStockInfo.prodCd"/>"    binding="prodCd"        width="140" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.prodNm"/>"    binding="prodNm"        width="180" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.poUnitFg"/>"  binding="poUnitFgNm"    width="80"  align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.poUnitQty"/>" binding="poUnitQty"     width="80"  align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.outCnt"/>"    binding="outCnt"        width="80"  align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.inTotQty"/>"  binding="outTotQty"     width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.inTot"/>"     binding="outTot"        width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.inTotQty"/>"  binding="inTotQty"      width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.inTot"/>"     binding="inTot"         width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodStockInfo.penaltyAmt"/>"binding="penaltyAmt"    width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="prodCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
    <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
   <%-- id --%>
    <ul id="prodCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/prod/prod.js" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 입고 상세 팝업 레이어 --%>
<c:import url="/WEB-INF/view/iostock/frnchs/prod/prodInOutstockInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
