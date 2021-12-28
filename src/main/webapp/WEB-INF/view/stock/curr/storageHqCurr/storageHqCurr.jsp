<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/curr/storageHqCurr/storageHqCurr/"/>

<div class="subCon3" ng-controller="storageHqCurrCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="nxBtnSearch" ng-click="_broadcast('storageHqCurrCtrlSrch')">
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
	      <%-- 상품코드 --%>
	      <th><s:message code="storeCurr.prodCd"/></th>
	      <td>
	        <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCdModel" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch();"/>
	      </td>
	      <%-- 상품명 --%>
	      <th><s:message code="storeCurr.prodNm"/></th>
	      <td>
	        <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNmModel" class="sb-input w100" maxlength="50" onkeyup="fnNxBtnSearch();"/>
	      </td>
      </tr>
      <tr>
	      <%-- 바코드 --%>
	      <th><s:message code="storeCurr.barcdNm"/></th>
	      <td>
	        <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCdModel" class="sb-input w100" maxlength="40" onkeyup="fnNxBtnSearch();"/>
	      </td>
	      <%-- 단위구분 --%>
	      <th><s:message code="storeCurr.unitFg"/></th>
	      <td>
	        <div class="sb-select">
	          <span class="txtIn w150px">
	            <wj-combo-box
	              id="srchUnitFg"
	              ng-model="unitFgModel"
	              items-source="_getComboData('srchUnitFg')"
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
		<%-- 분류 --%>
		<th><s:message code="storeCurr.prodClass"/></th>
		<td>
		  <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
		         placeholder="<s:message code="cmm.all" />" readonly/>
		  <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCdModel" disabled/>
		  <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
		</td>

	    <%-- 안전재고 --%>
        <th><s:message code="hqCurr.safeStockFg"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn w150px">
              <wj-combo-box
                id="srchSafeStockFg"
                ng-model="safeStockFgModel"
                items-source="_getComboData('srchSafeStockFg')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
            <span class="chk ml20">
                 <input type="checkbox" id="prodClassDisplay" ng-model="isDisplay" ng-change="fnDisplay()"/>
                 <label for="prodClassDisplay">
                     <s:message code="storageHqCurr.prodClassDisplay" />
                 </label>
             </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 거래처 --%>
        <th style="display: none;"><s:message code="storeCurr.vendrNm"/></th>
        <td style="display: none;">
          <%-- 거래처선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="storageHqCurrSelectVendr"/>
            <jsp:param name="displayNm" value="전체"/>
          </jsp:include>
          <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
        </td>
      </tr>
      </tbody>
    </table>

    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	    <input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
	</c:if>
	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
	    <input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>
	</c:if>

    <input type="hidden" id="storageHqCurrSelectStorageCd" value="">
    <input type="hidden" id="storageHqCurrSelectStorageName" value="">

    <div style="clear: both;"></div>

    <div class="mt20 oh sb-select dkbr">
	    <%-- 페이지 스케일  --%>
	    <wj-combo-box
	            class="w100px fl"
	            id="storageHqCurrListScaleBox"
	            ng-model="storageHqCurrListScale"
	            items-source="_getComboData('storageHqCurrListScaleBox')"
	            display-member-path="name"
	            selected-value-path="value"
	            initialized="initComboBox(s)"
	            control="listScaleCombo"
				is-editable="true"
				text-changed="_checkValidation(s)">
	    </wj-combo-box>
	    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	        <input type="text" id="storageHqCurrSelectStoreStoreNum" ng-model="storeNum">
	    </c:if>
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownloadStorageHqCurr()"><s:message code="cmm.excel.down" />
	    </button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType1">
      <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          id="storageHqCurrGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter"
          frozen-columns="5"
          allow-dragging="None">
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="storageHqCurr.lv1Nm"/>"             binding="lv1Nm"             width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.lv2Nm"/>"             binding="lv2Nm"             width="140" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.lv3Nm"/>"             binding="lv3Nm"             width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.prodCd"/>"            binding="prodCd"            width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.prodNm"/>"            binding="prodNm"            width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.poUnitQty"/>"         binding="poUnitQty"         width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.poUnitFg"/>"          binding="poUnitFgNm"          width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.vendrNm"/>"           binding="vendrNm"           width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.barcdNm"/>"           binding="barcdCd"           width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.costUprc"/>"          binding="costUprc"          width="80" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.safeStockQty"/>"      binding="safeStockQty"      width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.totCurrQty"/>"        binding="currQty"           width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <wj-flex-grid-column header="<s:message code="storageHqCurr.currQty000"/>"        binding="currQty000"        width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:if>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.firstVendrInDate"/>"  binding="firstVendrInDate"  width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.lastVendrInDate"/>"   binding="lastVendrInDate"   width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.firstHqOutDate"/>"    binding="firstHqOutDate"    width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.lastHqOutDate"/>"     binding="lastHqOutDate"     width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.firstSaleDate"/>"     binding="firstSaleDate"     width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.lastSaleDate"/>"      binding="lastSaleDate"      width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
          </c:if>
          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <wj-flex-grid-column header="<s:message code="storageHqCurr.firstVendrInDate"/>"       binding="firstInDate"  width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storageHqCurr.lastVendrInDate"/>"        binding="lastInDate"   width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storageHqCurr.firstSaleDate"/>"     binding="firstSaleDate"     width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storageHqCurr.lastSaleDate"/>"      binding="lastSaleDate"      width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
          </c:if>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="storageHqCurrCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="storageHqCurrCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%-- 엑셀 리스트 --%>
  <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="storageHqCurrExcelCtrl">
     <div class="wj-gridWrap">
        <wj-flex-grid
          id="storageHqCurrExcelGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="excelFlex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter"
          frozen-columns="5"
          allow-dragging="None">
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="storageHqCurr.lv1Nm"/>"             binding="lv1Nm"             width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.lv2Nm"/>"             binding="lv2Nm"             width="140" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.lv3Nm"/>"             binding="lv3Nm"             width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.prodCd"/>"            binding="prodCd"            width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.prodNm"/>"            binding="prodNm"            width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.poUnitQty"/>"         binding="poUnitQty"         width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.poUnitFg"/>"          binding="poUnitFgNm"          width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.vendrNm"/>"           binding="vendrNm"           width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.barcdNm"/>"           binding="barcdCd"           width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.costUprc"/>"          binding="costUprc"          width="80" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.safeStockQty"/>"      binding="safeStockQty"      width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storageHqCurr.totCurrQty"/>"        binding="currQty"           width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <wj-flex-grid-column header="<s:message code="storageHqCurr.currQty000"/>"        binding="currQty000"        width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:if>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.firstVendrInDate"/>"  binding="firstVendrInDate"  width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.lastVendrInDate"/>"   binding="lastVendrInDate"   width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.firstHqOutDate"/>"    binding="firstHqOutDate"    width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.lastHqOutDate"/>"     binding="lastHqOutDate"     width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.firstSaleDate"/>"     binding="firstSaleDate"     width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="storageHqCurr.lastSaleDate"/>"      binding="lastSaleDate"      width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
          </c:if>
          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <wj-flex-grid-column header="<s:message code="storageHqCurr.firstVendrInDate"/>"       binding="firstInDate"  width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storageHqCurr.lastVendrInDate"/>"        binding="lastInDate"   width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storageHqCurr.firstSaleDate"/>"     binding="firstSaleDate"     width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storageHqCurr.lastSaleDate"/>"      binding="lastSaleDate"      width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
          </c:if>
        </wj-flex-grid>
     </div>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/stock/curr/storageHqCurr/storageHqCurr.js" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
<%-- 재고현황 팝업 --%>
<c:import url="/WEB-INF/view/stock/curr/hqCurr/hqCurrDtl.jsp"></c:import>