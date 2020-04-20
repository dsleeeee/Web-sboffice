<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="rtnStatusDayView" class="subCon"  ng-controller="rtnStatusDayCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="rtnStatus.rtnStatus"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnRtnStatusDaySearch" ng-click="_broadcast('rtnStatusDayMainCtrlSrch')">
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
            <span class="txtIn"><input id="srchRtnStatusDayStartDate" class="w120px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchRtnStatusDayEndDate" class="w120px"></span>
            <span class="chk ml10">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>
        </div>
        </td>

        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="rtnStatusDaySelectStoreCd" value=""/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="rtnStatusDaySelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="rtnStatusDaySelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <!-- contents start -->
	<div class="wj-gridWrap2 mt20" ng-controller="rtnStatusDayMainCtrl">
	    <%-- wj grid start --%>
	    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="rtnStatusDayListScaleBox"
                ng-model="rtnStatusDayListScale"
                items-source="_getComboData('rtnStatusDayListScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                initialized="initComboBox(s)"
                control="listScaleCombo"
                is-editable="true"
                text-changed="_checkValidation(s)">
        </wj-combo-box>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="text" id="rtnStatusDaySelectStoreStoreNum" ng-model="storeNum">
        </c:if>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" />
        </button>
        </div>
	    <div class="wj-gridWrap2 mt10">
		        <wj-flex-grid
		          id="rtnStatusDayGrid"
		          loaded-rows="loadedRows(s,e)"
		          autoGenerateColumns="false"
		          selection-mode="Row"
		          items-source="data"
		          control="flex"
		          initialized="initGrid(s,e)"
		          is-read-only="true"
		          item-formatter="_itemFormatter">
		          <!-- define columns -->
		          <wj-flex-grid-column header="<s:message code="rtnStatus.storeNm"/>"    binding="storeNm"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
		          <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cntY"           width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		          <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmtY"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		          <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cntN"           width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmtN"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cnt"            width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmt"    width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="rtnStatus.storeCd"/>"    binding="storeCd"        width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
		        </wj-flex-grid>
		        <%-- ColumnPicker 사용시 include --%>
		        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
		          <jsp:param name="pickerTarget" value="rtnStatusDayMainCtrl"/>
		        </jsp:include>
		        <%--// ColumnPicker 사용시 include --%>
	    </div>
	    <%-- //wj grid end --%>
	    <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <ul id="rtnStatusDayMainCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
	</div>
	<!-- //contents end -->


    <!-- contents start -->
    <%-- wj grid start --%>
    <div class="wj-TblWrap">
	        <%-- left --%>
	        <div class="w50 fl" ng-controller="rtnStatusDayDtlCtrl">
	        <div class="mt20 oh sb-select dkbr mb10 mr10" >
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="rtnStatusDayDtlListScaleBox"
                    ng-model="rtnStatusDayDtlListScale"
                    items-source="_getComboData('rtnStatusDayDtlListScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    initialized="initComboBox(s)"
                    control="listScaleCombo"
	                is-editable="true"
	                text-changed="_checkValidation(s)">
            </wj-combo-box>
            <span class="fl bk lh30 pdl20" id="strNm"></span>
            <%-- 엑셀 다운로드 //TODO --%>
            <button class="btn_skyblue fr" ng-click="excelDownloadDayDtlCtrl()"><s:message code="cmm.excel.down" />
            </button>
            </div>
	            <div class="wj-TblWrapBr2 mr10">
                   <wj-flex-grid
	                  id="rtnStatusDayDtlGrid"
	                  loaded-rows="loadedRows2(s,e)"
	                  autoGenerateColumns="false"
	                  selection-mode="Row"
	                  items-source="data"
	                  control="flex"
	                  initialized="initGrid(s,e)"
	                  is-read-only="true"
	                  item-formatter="_itemFormatter">
	                  <!-- define columns -->
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.saleDate"/>"   binding="saleDate"       width="*" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cntY"           width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmtY"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cntN"           width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                         <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmtN"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                         <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cnt"            width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                         <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmt"    width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	                </wj-flex-grid>
	                <%-- ColumnPicker 사용시 include --%>
	                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	                  <jsp:param name="pickerTarget" value="rtnStatusDayDtlCtrl"/>
	                </jsp:include>
	                <%--// ColumnPicker 사용시 include --%>
	           </div>
		       <%-- 페이지 리스트 --%>
	           <div class="pageNum mt20">
	             <ul id="rtnStatusDayDtlCtrlPager" data-size="10">
	             </ul>
	           </div>
	           <%--//페이지 리스트--%>
        </div>

        <%-- right --%>
        <div class="w50 fr" ng-controller="rtnStatusPosDtlCtrl">
        <div class="mt20 oh sb-select dkbr mb10 ml10">
           <%-- 페이지 스케일  --%>
           <wj-combo-box
                   class="w100px fl"
                   id="rtnStatusPosDtlListScaleBox"
                   ng-model="rtnStatusPosDtlListScale"
                   items-source="_getComboData('rtnStatusPosDtlListScaleBox')"
                   display-member-path="name"
                   selected-value-path="value"
                   initialized="initComboBox(s)"
                   control="listScaleCombo"
	               is-editable="true"
	               text-changed="_checkValidation(s)">
           </wj-combo-box>
           <span class="fl bk lh30 pdl20" id="dateYMD"></span>
           <%-- 엑셀 다운로드 //TODO --%>
           <button class="btn_skyblue fr" ng-click="excelDownloadPosDtlCtrl()"><s:message code="cmm.excel.down" />
           </button>
           </div>
	            <div class="wj-TblWrapBr1 ml10">
                   <wj-flex-grid
                      id="rtnStatusPosDtlGrid"
	                  autoGenerateColumns="false"
	                  selection-mode="Row"
	                  items-source="data"
	                  control="flex"
	                  initialized="initGrid(s,e)"
	                  is-read-only="true"
	                  item-formatter="_itemFormatter">
	                  <!-- define columns -->
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.posNo"/>"       binding="posNo"         width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.bill.no"/>"     binding="billNo"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.saleFg"/>"      binding="saleYn"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.totSaleAmt"/>"  binding="totSaleAmt"    width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.dcAmt"/>"       binding="totDcAmt"      width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	                  <wj-flex-grid-column header="<s:message code="rtnStatus.realSaleAmt"/>" binding="realSaleAmt"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	                </wj-flex-grid>
	                <%-- ColumnPicker 사용시 include --%>
	                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	                  <jsp:param name="pickerTarget" value="rtnStatusPosDtlCtrl"/>
	                </jsp:include>
	                <%--// ColumnPicker 사용시 include --%>
	            </div>
	          <%-- 페이지 리스트 --%>
			  <div class="pageNum mt20">
			    <ul id="rtnStatusPosDtlCtrlPager" data-size="10">
			    </ul>
			  </div>
			  <%--//페이지 리스트--%>
        </div>
    </div>
    <%-- //wj grid end --%>
<!-- //contents end -->
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/rtnStatus/rtnStatus.js" charset="utf-8"></script>

