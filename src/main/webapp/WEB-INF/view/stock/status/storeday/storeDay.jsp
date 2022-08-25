<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/status/storeday/storeDay/"/>

<!-- contents start -->
<div class="subCon3" ng-controller="storeDayCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>

      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeDayMainCtrlSrch')">
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
            <span class="txtIn"><input id="srchStoreDayStartDate" class="w120px"></span>
          </div>
        </td>

        <input type="hidden" id="storeDaySelectStoreCd" valaue=""/>
        <%-- 매장코드 --%>
        <th><s:message code="storeDay.storeNm"/></th>
        <td>
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="storeDaySelectStore"/>
            </jsp:include>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="storeDay.prodCd"/></th>
        <td><input type="text" class="sb-input w100" id="srchProdCd" ng-model="srchProdCd" onkeyup="fnNxBtnSearch();"/></td>

        <%-- 상품명 --%>
        <th><s:message code="storeDay.prodNm"/></th>
        <td><input type="text" class="sb-input w100" id="srchProdNm" ng-model="srchProdNm" onkeyup="fnNxBtnSearch();"/></td>
      </tr>
      <tr>
        <%-- 바코드 --%>
        <th><s:message code="storeDay.barcdCd"/></th>
        <td><input type="text" class="sb-input w100" id="srchBarcdCd" ng-model="srchBarcdCd" onkeyup="fnNxBtnSearch();"/></td>

        <%-- 단위구분 --%>
        <th><s:message code="storeDay.unitFg"/></th>
        <td>
            <div class="sb-select">
        <span class="txtIn w120px">
            <wj-combo-box
                    id="srchUnitFgDisplay"
                    ng-model="unitFgModel"
                    items-source="_getComboData('srchUnitFgDisplay')"
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
        <%-- 상품분류 --%>
        <th><s:message code="storeDay.prodClass"/></th>
        <td>
          <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                 placeholder="<s:message code="cmm.all" />" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCdModel" disabled/>
          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
        </td>

        <%-- 조회옵션 --%>
        <th><s:message code="storeDay.srchOption"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn w120px">
                <wj-combo-box
                  id="srchOptionDisplay"
                  ng-model="srchOption"
                  items-source="_getComboData('srchOptionDisplay')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
            </span>
            <span class="chk ml5">
                <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()"/>
                <label for="chkDt">
                    <s:message code="storeDay.prodClassDisplay" />
                </label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 거래처 --%>
        <th style="display: none;"><s:message code="storeDay.vendr"/></th>
        <td style="display: none;">
          <%-- 거래처선택 모듈 멀티 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="storeDaySelectVendr"/>
          </jsp:include>
          <input type="hidden" id="storeDaySelectVendrCd" value=""/>
        </td>
      </tr>
      </tbody>
    </table>

    <input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>

    <div ng-controller="storeDayMainCtrl">
	    <div class="mt20 oh sb-select dkbr">
	      <%-- 페이지 스케일  --%>
          <wj-combo-box
            class="w100px fl"
            id="storeDayMainlistScaleBox"
            ng-model="listScale"
            items-source="_getComboData('storeDayMainlistScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
          </wj-combo-box>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <input type="text" id="storeDayMainSelectStoreStoreNum" ng-model="storeNum">
          </c:if>
	      <%-- 엑셀 다운로드 //TODO --%>
	      <button class="btn_skyblue fr" ng-click="excelDownloadStoreDay()"><s:message code="cmm.excel.down" />
	      </button>
	    </div>

	    <%-- gird 1 --%>
	    <div class="w100 mt10" id="wjWrapType2">
	        <div class="wj-gridWrap">
	            <wj-flex-grid
	              id="storeDayMainGrid"
	              loaded-rows="loadedRows(s,e)"
	              autoGenerateColumns="false"
	              selection-mode="Row"
	              items-source="data"
	              control="flex"
	              initialized="initGrid(s,e)"
	              is-read-only="true"
	              item-formatter="_itemFormatter"
	              frozen-columns="5">

	              <!-- define columns -->
	              <wj-flex-grid-column header="<s:message code="storeDay.prodClassNm"/>"		binding="prodClassNm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="storeDay.prodCd"/>"   			binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.prodNm"/>"   			binding="prodNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.storeCd"/>"  			binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.storeNm"/>"  			binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.poUnitQty"/>"			binding="poUnitQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.poUnitFg"/>" 			binding="poUnitFgNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.barcdCd"/>"  			binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreInQty"/>"  	binding="storeInQty" width="60" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreInAmt"/>"  	binding="storeInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreOutQty"/>" 	binding="storeOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreOutAmt"/>" 	binding="storeOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accPurchsInQty"/>" 	binding="purchsInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accPurchsInAmt"/>" 	binding="purchsInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accPurchsOutQty"/>"	binding="purchsOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accPurchsOutAmt"/>"	binding="purchsOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreSaleQty"/>"	binding="storeSaleQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreSaleAmt"/>"	binding="storeSaleTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreMoveInQty"/>" 	binding="moveInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreMoveInAmt"/>" 	binding="moveInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreMoveOutQty"/>"	binding="moveOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accStoreMoveOutAmt"/>"	binding="moveOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accDisuse"/>"      	binding="disuseQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accAdj"/>"         	binding="adjQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.accSetIn"/>"       	binding="setInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="cmm.search.date"/>"       	    binding="startDate" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
	            </wj-flex-grid>
	            <%-- ColumnPicker 사용시 include --%>
	            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	              <jsp:param name="pickerTarget" value="storeDayMainCtrl"/>
	            </jsp:include>
	            <%--// ColumnPicker 사용시 include --%>
	        </div>
	        <%-- 페이지 리스트 --%>
		    <div class="pageNum mt20">
		      <%-- id --%>
		      <ul id="storeDayMainCtrlPager" data-size="10">
		      </ul>
		    </div>
		    <%--//페이지 리스트--%>
		    
		    <%-- 엑셀 리스트 --%>
		    <div class="wj-gridWrap" style="display:none;" ng-controller="storeDayExcelCtrl">
	            <wj-flex-grid
	              id="storeDayMainExcelGrid"
	              loaded-rows="loadedRows(s,e)"
	              autoGenerateColumns="false"
	              selection-mode="Row"
	              items-source="data"
	              control="excelFlex"
	              initialized="initGrid(s,e)"
	              is-read-only="true"
	              item-formatter="_itemFormatter"
	              frozen-columns="5">

	              <!-- define columns -->
				  <wj-flex-grid-column header="<s:message code="storeDay.prodClassNm"/>"			binding="prodClassNm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

				  <wj-flex-grid-column header="<s:message code="storeDay.prodCd"/>"   				binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.prodNm"/>"   				binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.storeCd"/>"  				binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.storeNm"/>"  				binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.poUnitQty"/>"				binding="poUnitQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.poUnitFg"/>" 				binding="poUnitFgNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeDay.barcdCd"/>"  				binding="barcdCd" width="100" align="center" format="d" is-read-only="true"></wj-flex-grid-column>

					<wj-flex-grid-column header="<s:message code="storeDay.accStoreInQty"/>"  		binding="storeInQty" width="60" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accStoreInAmt"/>"  		binding="storeInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accStoreOutQty"/>" 		binding="storeOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accStoreOutAmt"/>" 		binding="storeOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accPurchsInQty"/>" 		binding="purchsInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accPurchsInAmt"/>" 		binding="purchsInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accPurchsOutQty"/>"		binding="purchsOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accPurchsOutAmt"/>"		binding="purchsOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accStoreSaleQty"/>"		binding="storeSaleQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accStoreSaleAmt"/>"		binding="storeSaleTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

					<wj-flex-grid-column header="<s:message code="storeDay.accStoreMoveInQty"/>" 	binding="moveInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accStoreMoveInAmt"/>" 	binding="moveInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accStoreMoveOutQty"/>"	binding="moveOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accStoreMoveOutAmt"/>"	binding="moveOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accDisuse"/>"      		binding="disuseQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accAdj"/>"         		binding="adjQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="storeDay.accSetIn"/>"       		binding="setInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	            </wj-flex-grid>
	        </div>
	    </div>
    </div>
</div>
<!-- //contents end -->

<script type="text/javascript" src="/resource/solbipos/js/stock/status/storeday/storeDay.js?ver=20220803.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/hqProdCodeDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수량 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodQtyDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
