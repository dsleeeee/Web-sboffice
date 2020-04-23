<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/frnchs/slip/slip/"/>

<div class="subCon3" ng-controller="slipCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSlipSearch" ng-click="_broadcast('slipMainCtrlSrch')">
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
      <th><s:message code="slipStockInfo.outDate"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn"><input id="srchSlipStartDate" class="w110px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchSlipEndDate" class="w110px"></span>
        </div>
      </td>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <input type="hidden" id="slipSelectStoreCd" value="" />
          <%-- 매장코드 --%>
          <th style="border-left: 1px solid #ccc"><s:message code="slipStockInfo.storeNm" /></th>
          <td>
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                  param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                  closeFunc - 팝업 닫기시 호출할 함수            --%>
              <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                  <jsp:param name="targetId" value="slipSelectStore" />
              </jsp:include> <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
      </c:if>
    </tr>
    <tr>
      <%-- 전표구분 --%>
      <th><s:message code="slipStockInfo.slipFg"/></th>
      <td>
        <div class="sb-select">
        <span class="txtIn w120px">
          <wj-combo-box
            id="srchSlipFg"
            ng-model="slipFgModel"
            items-source="_getComboData('srchSlipFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        </div>
      </td>
      <%-- 전표종류 --%>
      <th><s:message code="slipStockInfo.slipKind"/></th>
      <td>
        <div class="sb-select">
        <span class="txtIn w120px">
          <wj-combo-box
            id="srchSlipKind"
            ng-model="slipKindModel"
            items-source="_getComboData('srchSlipKind')"
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
      <%-- 진행상태 --%>
      <th><s:message code="slipStockInfo.procFg"/></th>
      <td>
        <div class="sb-select">
        <span class="txtIn w120px">
          <wj-combo-box
            id="srchProcFg"
            ng-model="procFgModel"
            items-source="_getComboData('srchProcFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        </div>
      </td>
      <%-- 상품분류 --%>
      <th><s:message code="slipStockInfo.prodClass"/></th>
      <td>
        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
               placeholder="<s:message code="cmm.all" />" readonly/>
        <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
      </td>
    </tr>
    <tr>
      <%-- 상품코드 --%>
      <th><s:message code="slipStockInfo.prodCd"/></th>
      <td>
        <input type="text" id="srchSlipProdCd" name="srchSlipProdCd" ng-model="prodCdModel" class="sb-input w100" maxlength="13"/>
      </td>
      <%-- 상품명 --%>
      <th><s:message code="slipStockInfo.prodNm"/></th>
      <td>
        <input type="text" id="srchSlipProdNm" name="srchSlipProdNm" ng-model="prodNmModel" class="sb-input w100" maxlength="16"/>
      </td>
    </tr>
    </tbody>
  </table>

   <div class="w100 mt20" ng-controller="slipMainCtrl">
     <div class="oh sb-select dkbr mb10">
         <%-- 페이지 스케일  --%>
         <wj-combo-box
                 class="w100px fl"
                 id="slipMainListScaleBox"
                 ng-model="slipMainListScale"
                 items-source="_getComboData('slipMainListScaleBox')"
                 display-member-path="name"
                 selected-value-path="value"
                 initialized="initComboBox(s)"
                 control="conListScale"
				 is-editable="true"
				 text-changed="_checkValidation(s)">
         </wj-combo-box>
         <c:if test="${sessionInfo.orgnFg == 'HQ'}">
             <input type="text" id="slipMainSelectStoreStoreNum" ng-model="storeNum">
         </c:if>
         <%-- 엑셀 다운로드 //TODO --%>
         <button class="btn_skyblue fr" ng-click="excelDownloadSlip()"><s:message code="cmm.excel.down" />
         </button>
     </div>
     <div id="wjWrapType2" class="w100 mt10">
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
           <wj-flex-grid-column header="<s:message code="slipStockInfo.slipNo"/>"        binding="slipNo"        width="80" align="center" is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.storeNm"/>"       binding="storeNm"       width="150" align="center"  is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.slipFg"/>"        binding="slipFgNm"        width="80" align="center"   is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.slipKind"/>"      binding="slipKindNm"      width="80" align="center" is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.procFg"/>"        binding="procFgNm"        width="70" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.outDate"/>"       binding="outDt"       width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.instockDate"/>"   binding="inDt"   width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtlCnt"/>"        binding="dtlCnt"        width="70" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.qty"/>"           binding="mdTotQty"          width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.inTot"/>"         binding="mdTot"        width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.qty"/>"           binding="outTotQty"          width="80" align="center"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.inTot"/>"         binding="outTot"        width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.qty"/>"           binding="inTotQty"          width="70" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.inTot"/>"         binding="inTot"        width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.penaltyAmt"/>"    binding="penaltyAmt"    width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

         </wj-flex-grid>
         <%-- ColumnPicker 사용시 include --%>
         <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
           <jsp:param name="pickerTarget" value="slipMainCtrl"/>
         </jsp:include>
         <%--// ColumnPicker 사용시 include --%>
       </div>
       <%--//위즈모 테이블--%>
     </div>
     <%-- 페이지 리스트 --%>
     <div class="pageNum mt20">
       <ul id="slipMainCtrlPager" data-size="10">
       </ul>
     </div>
     <%--//페이지 리스트--%>
   </div>

   <div style="clear: both"></div>

   <div class="w100 mt20" ng-controller="slipDtlCtrl">
     <div class="oh sb-select dkbr mb10">
       <%-- 페이지 스케일  --%>
       <wj-combo-box
               class="w100px fl"
               id="slipDtlListScaleBox"
               ng-model="slipDtlListScale"
               items-source="_getComboData('slipDtlListScaleBox')"
               display-member-path="name"
               selected-value-path="value"
               initialized="initComboBox(s)"
               control="conListScale"
			   is-editable="true"
			   text-changed="_checkValidation(s)">
       </wj-combo-box>
       <span class="fl bk lh30 ml10" id="dtlSlipNo"></span>

       <%-- 전표별 입출고현황 엑셀다운로드 --%>
       <button class="btn_skyblue fr ml20 mt5" ng-click="excelDownloadSlipDtl()">
           <s:message code="cmm.excel.down" />
       </button>

       <span class="fr bk sb-radio mt10">
           <label><input type="radio" name="displayFg" id="displayAll" value="all" checked="checked" ng-click="displayChg()"><s:message code="cmm.all" /></label>
           <label><input type="radio" name="displayFg" id="displayCntSum" value="cntSum" ng-click="displayChg()"><s:message code="orderStockInfo.dtl.inTotQty+inTot" /></label>
       </span>
     </div>
     <div id="wjWrapType2" class="w100 mt10">
       <%--위즈모 테이블--%>
       <div class="wj-gridWrap">
         <wj-flex-grid
           id="slipDtlGrid"
           autoGenerateColumns="false"
           selection-mode="Row"
           items-source="data"
           control="flex"
           initialized="initGrid(s,e)"
           is-read-only="true"
           item-formatter="_itemFormatter">

           <!-- define columns -->
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodCd"/>"     binding="prodCd"    width="100" align="center" is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodNm"/>"     binding="prodNm"    width="150"  align="center"   is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitFg"/>"   binding="poUnitFgNm"  width="50" align="center" is-read-only="true"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitQty"/>"  binding="poUnitQty" width="50" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.costUprc"/>"   binding="splyUprc"  width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="mdTotQty"  width="80" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="mdAmt"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="mdVat"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="mdTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="outTotQty"  width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="outAmt"     width="80" align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="outVat"     width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="outTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="inTotQty"  width="80" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="inAmt"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="inVat"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="inTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.penaltyAmt"/>" binding="penaltyAmt"width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.remark"/>"     binding="remark"    width="70" align="center"  is-read-only="true"></wj-flex-grid-column>

         </wj-flex-grid>
         <%-- ColumnPicker 사용시 include --%>
         <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
           <jsp:param name="pickerTarget" value="slipDtlCtrl"/>
         </jsp:include>
         <%--// ColumnPicker 사용시 include --%>
       </div>
       <%--//위즈모 테이블--%>
     </div>
     <%-- 페이지 리스트 --%>
     <div class="pageNum mt20">
       <ul id="slipDtlCtrlPager" data-size="10">
       </ul>
     </div>
     <%--//페이지 리스트--%>
   </div>
   
   
   <%--엑셀 리스트1--%>
   <div class="wj-gridWrap" style="display: none;" ng-controller="slipExcelCtrl">
     <wj-flex-grid
       id="slipExcelGrid"
       autoGenerateColumns="false"
       selection-mode="Row"
       items-source="data"
       control="excelFlex"
       initialized="initGrid(s,e)"
       is-read-only="true"
       item-formatter="_itemFormatter">

       <!-- define columns -->
       <wj-flex-grid-column header="<s:message code="slipStockInfo.slipNo"/>"        binding="slipNo"        width="80" align="center" is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.storeNm"/>"       binding="storeNm"       width="150" align="center"  is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.slipFg"/>"        binding="slipFgNm"        width="80" align="center"   is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.slipKind"/>"      binding="slipKindNm"      width="80" align="center" is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.procFg"/>"        binding="procFgNm"        width="70" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.outDate"/>"       binding="outDt"       width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.instockDate"/>"   binding="inDt"   width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtlCnt"/>"        binding="dtlCnt"        width="70" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.qty"/>"           binding="mdTotQty"          width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.inTot"/>"         binding="mdTot"        width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.qty"/>"           binding="outTotQty"          width="80" align="center"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.inTot"/>"         binding="outTot"        width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.qty"/>"           binding="inTotQty"          width="70" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.inTot"/>"         binding="inTot"        width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.penaltyAmt"/>"    binding="penaltyAmt"    width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
     </wj-flex-grid>
   </div>
   
   <%--엑셀 리스트2--%>
   <div class="wj-gridWrap" style="display: none;" ng-controller="slipDtlExcelCtrl">
     <wj-flex-grid
       id="slipDtlExcelGrid"
       autoGenerateColumns="false"
       selection-mode="Row"
       items-source="data"
       control="excelFlex"
       initialized="initGrid(s,e)"
       is-read-only="true"
       item-formatter="_itemFormatter">

       <!-- define columns -->
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodCd"/>"     binding="prodCd"    width="100" align="center" is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodNm"/>"     binding="prodNm"    width="150"  align="center"   is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitFg"/>"   binding="poUnitFgNm"  width="50" align="center" is-read-only="true"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitQty"/>"  binding="poUnitQty" width="50" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.costUprc"/>"   binding="splyUprc"  width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="mdTotQty"  width="80" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="mdAmt"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="mdVat"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="mdTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="outTotQty"  width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="outAmt"     width="80" align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="outVat"     width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="outTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>"   binding="inTotQty"  width="80" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>"      binding="inAmt"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>"      binding="inVat"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>"      binding="inTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.penaltyAmt"/>" binding="penaltyAmt"width="70" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.remark"/>"     binding="remark"    width="70" align="center"  is-read-only="true"></wj-flex-grid-column>
     </wj-flex-grid>
   </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/slip/slip.js" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
