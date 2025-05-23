<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="rtnStatusDayView" class="subCon"  ng-controller="rtnStatusDayCtrl" style="padding: 10px 20px 40px;">
    <div class="searchBar">
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
            <span class="txtIn"><input id="srchRtnStatusDayStartDate" class="w110px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchRtnStatusDayEndDate" class="w110px"></span>
            <span class="chk ml10" style="display: none;">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>
        </div>
        </td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="hidden" id="rtnStatusDaySelectStoreCd" value=""/>
			<%-- 매장선택 --%>
			<th><s:message code="cmm.store.select"/></th>
			<td>
				<%-- 매장선택 모듈 사용시 include --%>
				<jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
					<jsp:param name="targetTypeFg" value="M"/>
					<jsp:param name="targetId" value="rtnStatusDaySelectStore"/>
				</jsp:include>
				<%--// 매장선택 모듈 사용시 include --%>
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
	<div class="wj-gridWrap2 mt10" ng-controller="rtnStatusDayMainCtrl">
	    <%-- wj grid start --%>
	    <div class="mt10 oh sb-select dkbr">
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
	    <div class="wj-gridWrap2">
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
        <div class="pageNum">
          <ul id="rtnStatusDayMainCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
        
        <div class="wj-gridWrap2 mt10" style="display:none;" ng-controller="rtnStatusDayExcelCtrl">
			<wj-flex-grid
			  id="rtnStatusDayExcelGrid"
			  autoGenerateColumns="false"
			  selection-mode="Row"
			  items-source="data"
			  control="dayExcelflex"
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
	    </div>
	</div>
	<!-- //contents end -->

	<!-- 하단 그리드 2개 -->
    <!-- contents start -->
    <%-- wj grid start --%>
    <div class="wj-TblWrap" style="height:550px; overflow-y: hidden; overflow-x: hidden;">
		<%-- left --%>
		<div class="w50 fl" ng-controller="rtnStatusDayDtlCtrl" style="width:48%">
	        <div class="oh sb-select dkbr mb10 mr10" >
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
				<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
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
			</div>
		   <%-- 페이지 리스트 --%>
		   <div class="pageNum3 mt20">
			 <ul id="rtnStatusDayDtlCtrlPager" data-size="10">
			 </ul>
		   </div>
		   <%--//페이지 리스트--%>

		   <%-- 엑셀 리스트2 --%>
		   <div class="wj-TblWrapBr2 mr10" style="display:none;" ng-controller="rtnStatusDayDtlExcelCtrl">
				<div class="wj-gridWrap">
				   <wj-flex-grid
					  id="rtnStatusDayDtlExcelGrid"
					  autoGenerateColumns="false"
					  selection-mode="Row"
					  items-source="data"
					  control="dayDtlExcelflex"
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
		   </div>
        </div>

        <%-- right --%>
        <div class="w50 fr" ng-controller="rtnStatusPosDtlCtrl" style="width:48%">
			<div class="oh sb-select dkbr mb10">
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
			<div class="wj-TblWrapBr1">
				<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
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
	           </div>
	          <%-- 페이지 리스트 --%>
			  <div class="pageNum3 mt20">
			    <ul id="rtnStatusPosDtlCtrlPager" data-size="10">
			    </ul>
			  </div>
			  <%--//페이지 리스트--%>
			  
			  <!-- 엑셀 리스트3 -->
			  <div class="wj-TblWrapBr1" style="display:none;" ng-controller="rtnStatusPosDtlExcelCtrl">
	            	<div class="wj-gridWrap">
                   <wj-flex-grid
                      id="rtnStatusPosDtlExcelGrid"
	                  autoGenerateColumns="false"
	                  selection-mode="Row"
	                  items-source="data"
	                  control="posDtlExcelflex"
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
	            </div>
	           </div>
        </div>
    </div>
    <%-- //wj grid end --%>
<!-- //contents end -->
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/rtnStatus/rtnStatus.js?ver=20250415.01" charset="utf-8"></script>