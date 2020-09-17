<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/status/prod/class/prodClass/"/>

<div id="prodClassView" class="subCon3" ng-controller="prodClassCtrl"> <%-- 수정 사항 || 클래스 변경 :: class="subCon" >> class="subCon3"--%>
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="prodsale.class"/></a>
    	<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('prodClassCtrlSrch')">
    		<s:message code="cmm.search"/>
    	</button>
	</div>
    <%-- 조회조건 --%>
    <table class="searchTbl">
		<colgroup>
        	<col class="w13"/>
	        <col class="w37"/>
	        <col class="w13"/>
	        <col class="w37"/>
      	</colgroup>
      	<tbody>
       	<%-- 조회일자 --%>
		<tr>
	    	<th><s:message code="cmm.search.date" /></th>
        	<td>
          	<div class="sb-select">
       		    <span class="txtIn"><input id="srchClassStartDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchClassEndDate" class="w120px"></span>
            	<span class="chk ml10" style="display: none;">
					<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
	              	<label for="chkDt">
                		<s:message code="cmm.all.day" />
              		</label>
            	</span>
          	</div>
        	</td>
        	<%-- 조회옵션 --%>
			<th><s:message code="periodIostock.srchOption" /></th>
			<td>
	          	<span class="chk ml10">
					<input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
	              	<label for="chkDt">
                		<s:message code="periodIostock.prodClassDisplay" />
              		</label>
            	</span>
			</td>
		</tr>
		<tr>
        <%-- 상품코드 --%>
        <th><s:message code="prodcalss.prodCd" /></th>
        <td><input type="text" id="srchProdCd" class="sb-input w100" maxlength="13"/></td>
        <%-- 상품명 --%>
        <th><s:message code="prodcalss.prodNm" /></th>
        <td><input type="text" id="srchProdNm" class="sb-input w100" maxlength="100"/></td>
      	</tr>

        <tr>
        	<%-- 분류조회 --%>
	        <th><s:message code="prod.prodClass" /></th>
	        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	        <td>
	        </c:if>
	        <c:if test="${sessionInfo.orgnFg != 'HQ'}">
	        <td colspan="3">
	        </c:if>
	          <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
	                 placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
	          <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCdModel" disabled />
	          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
	        </td>
            <%-- 매장코드 --%>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          	<th><s:message code="todayBillSaleDtl.store"/></th>
          	<td>
            	<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
             		<jsp:param name="targetId" value="pordClassSelectStore"/>
            	</jsp:include>
              	<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          	</td>
        	</c:if>
        </tr>
      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
        	<input type="hidden" id="pordClassSelectStoreCd" value="${sessionInfo.storeCd}"/>
      	</c:if>
		</tbody>
	</table>

	<div class="mt20 oh sb-select dkbr"> <%-- 수정 사항 || 클래스변경 :: class="mt40" >> class="mt20" --%>
	    <%-- 페이지 스케일  --%>
	    <wj-combo-box
	      class="w100px fl"
	      id="prodClasslistScaleBox"
	      ng-model="prodClasslistScale"
	      control="listScaleCombo"
	      items-source="_getComboData('prodClasslistScaleBox')"
	      display-member-path="name"
	      selected-value-path="value"
	      initialized="_initComboBox(s)"
	      is-editable="true"
          text-changed="_checkValidation(s)">
	    </wj-combo-box>
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="text" id="pordClassSelectStoreStoreNum" ng-model="storeNum">
		</c:if>
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownloadClass()"><s:message code="cmm.excel.down" />
	    </button>
	</div>

	<%--위즈모 테이블--%>
    <div id="wjWrapType1" class="w100 mt10"> <%-- 수정 사항 || head line 1 아이디 추가 :: id="wjWrapType1" --%>
      <div class="wj-gridWrap"> <%-- 수정 사항 || 그리드 높이값 스타일 제거 :: style="height: 000px;" --%>
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodCd"/>" 		binding="prodCd" 		width="120" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodNm"/>"			binding="prodNm" 		width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodBar"/>" 		binding="prodBar" 		width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.totSaleQty"/>" 	binding="totSaleQty" 	width="130" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.totSaleAmt"/>" 	binding="totSaleAmt" 	width="130" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.totDcAmt"/>" 		binding="totDcAmt" 		width="140" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.realSaleAmt"/>" 	binding="realSaleAmt" 	width="145" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="prodClassCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="prodClassCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
  
	<%--엑셀 리스트--%>
    <div id="wjWrapType1" class="w100 mt10" style="display:none;" ng-controller="prodClassExcelCtrl"> 
      <div class="wj-gridWrap"> <%-- 수정 사항 || 그리드 높이값 스타일 제거 :: style="height: 000px;" --%>
        <wj-flex-grid
          id="prodClassExcelGrid"	
          autoGenerateColumns="false"
          control="excelFlex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodCd"/>" 		binding="prodCd" 		width="120" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodNm"/>"			binding="prodNm" 		width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodBar"/>" 		binding="prodBar" 		width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.totSaleQty"/>" 	binding="totSaleQty" 	width="130" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.totSaleAmt"/>" 	binding="totSaleAmt" 	width="130" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.totDcAmt"/>" 		binding="totDcAmt" 		width="140" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.realSaleAmt"/>" 	binding="realSaleAmt" 	width="145" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="prodClassCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//엑셀 리스트--%>
    
</div>
<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/class/prodClass.js?ver=20190125.02" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

