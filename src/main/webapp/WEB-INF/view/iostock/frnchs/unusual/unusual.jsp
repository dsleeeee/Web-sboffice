<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/fnrchs/unusual/unusual/"/>

<div class="subCon3" ng-controller="unusualCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('unusualMainCtrlSrch')">
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
      <th>
        <div class="sb-select">
           <span class="txtIn">
                <wj-combo-box
                  id="srchUnusualOutDateFgDisplay"
                  ng-model="outDateFgModel"
                  items-source="_getComboData('srchUnusualOutDateFgDisplay')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
            </span>
         </div>
      </th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchUnusualStartDate" class="w120px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchUnusualEndDate" class="w120px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 전표번호 --%>
      <th><s:message code="unusualStockInfo.slipNo"/></th>
      <td>
        <input type="text" id="srchSlipNo" name="srchSlipNo" ng-model="slipNoModel" class="sb-input w100" maxlength="10" onkeyup="fnNxBtnSearch();"/>
      </td>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <input type="hidden" id="unusualSelectStoreCd" value="" />
          <%-- 매장코드 --%>
          <th style="border-left: 1px solid #ccc"><s:message code="slipStockInfo.storeNm" /></th>
          <td>
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                  param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                  closeFunc - 팝업 닫기시 호출할 함수            --%>
              <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                  <jsp:param name="targetId" value="unusualSelectStore" />
              </jsp:include> <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
      	<input type="hidden" id="unusualSelectStoreCd" value="${sessionInfo.storeCd}" />
      </c:if>
    </tr>
    <tr>
      <%-- 전표구분 --%>
      <th><s:message code="unusualStockInfo.slipFg"/></th>
      <td>
        <div class="sb-select">
           <span class="txtIn">
                <wj-combo-box
                  id="srchUnusualSlipFgDisplay"
                  ng-model="slipFgModel"
                  items-source="_getComboData('srchUnusualSlipFgDisplay')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
            </span>
         </div>
      </td>
      <c:if test="${envst1242 == '1'}">
          <th><s:message code="orderStockInfo.vendr" /></th>
          <td>
              <div class="sb-select fl w150px">
                  <wj-combo-box
                          id="vendrCd"
                          ng-model="vendrCd"
                          control="vendrCdCombo"
                          items-source="_getComboData('vendrCd')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
              </div>
          </td>
      </c:if>
    </tr>
    </tbody>
  </table>
  <div class="w100 mt20" ng-controller="unusualMainCtrl">
	<div class="mt20 oh sb-select dkbr">
	 		<wj-combo-box
	      class="w100px fl"
	      id="unusualMainListScaleBox"
	      ng-model="unusualMainListScale"
	      items-source="_getComboData('unusualMainListScaleBox')"
	      display-member-path="name"
	      selected-value-path="value"
	      initialized="_initComboBox(s)"
	      control="conListScale"
		  is-editable="true"
		  text-changed="_checkValidation(s)">
	    </wj-combo-box>
	    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	        <input type="text" id="unusualSelectStoreStoreNum" ng-model="storeNum">
	    </c:if>
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownloadunusual()"><s:message code="cmm.excel.down"/>
	    </button>
	</div>    	

    <div id="wjWrapType1" class="w100 mt10">
     <%--위즈모 테이블--%>
       <div class="wj-gridWrap">
       <wj-flex-grid
         loaded-rows="loadedRows(s,e)"
         autoGenerateColumns="false"
         selection-mode="Row"
         items-source="data"
         control="flex"
         initialized="initGrid(s,e)"
         is-read-only="true"
         item-formatter="_itemFormatter">

         <!-- define columns -->
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.slipNo"/>"     binding="slipNo"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.vendr"/>"      binding="vendr"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.slipFg"/>"     binding="slipFgNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.procFg"/>"     binding="procFgNm"        width="100" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.storeNm"/>"    binding="storeNm"       width="200" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.outDate"/>"    binding="outOt"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.inDate"/>"     binding="inOt"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.prodNm"/>"     binding="prodNm"        width="200" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.outUnitQty"/>" binding="outTotQty"    width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.inUnitQty"/>"  binding="inTotQty"     width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.remark"/>"     binding="remark"        width="100" align="right" is-read-only="true"></wj-flex-grid-column>

         <wj-flex-grid-column header=""     binding="slipFg"        width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
       </wj-flex-grid>
       <%-- ColumnPicker 사용시 include --%>
       <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
         <jsp:param name="pickerTarget" value="unusualCtrl"/>
       </jsp:include>
       <%--// ColumnPicker 사용시 include --%>
     </div>
     <%--//위즈모 테이블--%>
    </div>
    <%-- 페이지 리스트 --%>
	 <div class="pageNum mt20">
	   <ul id="unusualMainCtrlPager" data-size="10">
	   </ul>
	 </div>
    <%--//페이지 리스트--%>
  </div>
 

    <div class="w100 mt20" ng-controller="unusualDtlCtrl">
      <div class="mt20 oh sb-select dkbr">
        <wj-combo-box
       class="w100px fl"
       id="unusualDtlListScaleBox"
       ng-model="unusualDtlListScale"
       items-source="_getComboData('unusualDtlListScaleBox')"
       display-member-path="name"
       selected-value-path="value"
       initialized="_initComboBox(s)"
       control="conListScale"
	   is-editable="true"
	   text-changed="_checkValidation(s)">
     </wj-combo-box>
        <span class="fl bk lh30 pdl20" id="dtlSlipNo"></span>
        
        <%-- 전표별 입출고현황 엑셀다운로드 --%>
        <button class="btn_skyblue fr ml20 mt5" ng-click="excelDownloadUnusualDtl()">
            <s:message code="cmm.excel.down" />
        </button>
          
        <span class="fr bk sb-radio mt10">
            <label><input type="radio" name="displayFg" id="displayAll" value="all"  ng-click="displayChg()"><s:message code="cmm.all" /></label>
            <label><input type="radio" name="displayFg" id="displayCntSum" value="cntSum" checked="checked" ng-click="displayChg()"><s:message code="orderStockInfo.dtl.inTotQty+inTot" /></label>
        </span>
      </div>
      <div id="wjWrapType2" class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap">
          <wj-flex-grid
            id="unusualDtlGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodCd"/>"     binding="prodCd"     width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodNm"/>"     binding="prodNm"     width="150" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitFg"/>"   binding="poUnitFgNm" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitQty"/>"  binding="poUnitQty"  width="50" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.costUprc"/>"   binding="splyUprc"   width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="mdTotQty"   width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="mdAmt"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="mdVat"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="mdTot"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="outTotQty"  width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="outAmt"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="outVat"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="outTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="inTotQty"   width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="inAmt"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="inVat"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="inTot"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.penaltyAmt"/>" binding="penaltyAmt" width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.remark"/>"     binding="remark"     width="70" align="center" is-read-only="true"></wj-flex-grid-column>

            <wj-flex-grid-column header=""   binding="poUnitFg"   width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="unusualDtlCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
       </div>
       <%-- 페이지 리스트 --%>
       <div class="pageNum mt20">
         <ul id="unusualDtlCtrlPager" data-size="10">
         </ul>
       </div>
       <%--//페이지 리스트--%>
    </div>
    
       <%--엑셀 리스트1--%>
       <div class="wj-gridWrap" style="display: none;" ng-controller="unusualExcelCtrl">
       <wj-flex-grid
         id="unusualExcelGrid"
         autoGenerateColumns="false"
         selection-mode="Row"
         items-source="data"
         control="excelFlex"
         initialized="initGrid(s,e)"
         is-read-only="true"
         item-formatter="_itemFormatter">

         <!-- define columns -->
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.slipNo"/>"     binding="slipNo"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.vendr"/>"      binding="vendr"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.slipFg"/>"     binding="slipFgNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.procFg"/>"     binding="procFgNm"        width="100" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.storeNm"/>"    binding="storeNm"       width="200" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.outDate"/>"    binding="outOt"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.inDate"/>"     binding="inOt"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.prodNm"/>"     binding="prodNm"        width="200" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.outUnitQty"/>" binding="outTotQty"    width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.inUnitQty"/>"  binding="inTotQty"     width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="unusualStockInfo.remark"/>"     binding="remark"        width="100" align="right" is-read-only="true"></wj-flex-grid-column>

         <wj-flex-grid-column header=""     binding="slipFg"        width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
       </wj-flex-grid>
     </div>
     
     <%--엑셀 리스트2--%>
     <div class="wj-gridWrap" style="display: none;" ng-controller="unusualDtlExcelCtrl">
       <wj-flex-grid
         id="unusualDtlExcelGrid"
         autoGenerateColumns="false"
         selection-mode="Row"
         items-source="data"
         control="excelFlex"
         initialized="initGrid(s,e)"
         is-read-only="true"
         item-formatter="_itemFormatter">

         <!-- define columns -->
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodCd"/>"     binding="prodCd"     width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodNm"/>"     binding="prodNm"     width="150" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitFg"/>"   binding="poUnitFgNm" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitQty"/>"  binding="poUnitQty"  width="50" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.costUprc"/>"   binding="splyUprc"   width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="mdTotQty"   width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="mdAmt"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="mdVat"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="mdTot"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="outTotQty"  width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="outAmt"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="outVat"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="outTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="inTotQty"   width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="inAmt"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="inVat"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="inTot"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.penaltyAmt"/>" binding="penaltyAmt" width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.remark"/>"     binding="remark"     width="70" align="center" is-read-only="true"></wj-flex-grid-column>

         <wj-flex-grid-column header=""   binding="poUnitFg"   width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
       </wj-flex-grid>
     </div>
</div>

<script type="text/javascript">
    var gEnvst1242  = '${envst1242}';
    var empVendrCd = '${empVendrCd}';
    <%-- 본사 거래처 콤보박스 --%>
    var vendrList = ${vendrList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/unusual/unusual.js?ver=20220727.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
