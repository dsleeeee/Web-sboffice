<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<wj-popup id="goalPopLayer" control="goalPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:1000px;">
<div id="PopLayer" class="wj-dialog wj-dialog-columns" ng-controller="goalPopCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="goal.saleGoal"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">
    	
  		<%-- 조회 --%>
		<div class="searchBar flddUnfld">
		     <a href="#" class="open fl"><s:message code="goal.qry"/></a>
		     <button class="btn_blue fr mt5 mr10" id="btnGoalQrySearch" ng-click="_broadcast('goalPopCtrlSrch')">
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
	        <%-- 조회월 --%>
				<th><s:message code="cmm.search.month"/></th>
					<td colspan="3">
						<div class="sb-select">
		                  	<span class="txtIn w150px">
			              		<wj-input-date
			                      id="srchGoalQryStartDate"
			                      value="srchGoalQryStartDate"
			                      ng-model="startDate"
			                      control="srchGoalQryStartDateCombo"
			                      min="2000-01-01"
			                      max="2099-12-31"
			                      initialized="_initDateBox(s)"
			                      selection-mode="Month" 
			                      format="y">
			              		</wj-input-date>
		            		</span>
		                </div>
					</td>
				</tr>
			</tbody>
		</table>

		<div id="goalRgsrGrid" class="w50 fl" style="width: 27%;">
	    	<%-- 매장조회 --%>
			<div class="w100 mt10" ng-controller="goalQryCtrl">
				<div class="w100 mt10">
			        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap" style="height: 920px;">
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
				            <wj-flex-grid-column header="<s:message code="goalPop.storeCd"/>" 	binding="storeCd"		width="70" 		align="center" is-read-only="true"></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="goalPop.storeNm"/>" 	binding="storeNm"		width="*" 	    align="center" is-read-only="true"></wj-flex-grid-column>		
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="goalQryCtrl"/>
				        </jsp:include>
        				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
			    </div>
			</div>
		</div>
	
		<div id="gridDetail" class="w50 fr" style="width: 72%;">
			<%-- 매출목표조회 --%>
			<div class="sc pd10 mt10">
				<div class="w100 mt10" ng-controller="goalRgsrCtrl">
					<div class="wj-TblWrap mt10">
				        <%-- 매출목표등록 --%>
				        <h3 class="h3_tbl2 pdt5 lh30">
				          <s:message code="goal.rgsr" />
				          <button class="btn_skyblue fr" id="saleGoalRgsr" ng-click="_broadcast('saleGoalRgsr')">등록</button>
				        </h3>
						
						<table class="searchTbl">
							<colgroup>
					            <col class="w20" />
					            <col class="w10" />
					            <col class="w10" />
					            <col class="w10" />
					            <col class="w10" />
					            <col class="w10" />
					            <col class="w10" />
					            <col class="w10" />
				          	</colgroup>
				          	<tbody>
				          		<%-- 매장정보 --%>
					            <tr>
									<th><s:message code="goalPop.storeCd" /></th>
									<td colspan="2">
										<input type="text" id="storeCd" class="sb-input w100" style="text-align: center;" ng-model="goalModifyInfo.storeCd" readOnly="readonly"/>
					                </td>
					                <th colspan="2"><s:message code="goalPop.storeNm" /></th>
					                <td colspan="3">
										<input type="text" id="storeNm" class="sb-input w100" style="text-align: center;" ng-model="goalModifyInfo.storeNm" readOnly="readonly"/>
					                </td>
					            </tr>
					            <%-- 요일별 매출 가중치 --%>
					            <tr>
					              <th rowspan="2"><s:message code="goalPop.yoilSail" /><em class="imp">*</em></th>
					              <td style="text-align:center;"><s:message code="goalPop.yoil1" /></td>
					              <td style="text-align:center;"><s:message code="goalPop.yoil2" /></td>
					              <td style="text-align:center;"><s:message code="goalPop.yoil3" /></td>
					              <td style="text-align:center;"><s:message code="goalPop.yoil4" /></td>
					              <td style="text-align:center;"><s:message code="goalPop.yoil5" /></td>
					              <td style="text-align:center;"><s:message code="goalPop.yoil6" /></td>
					              <td style="text-align:center;"><s:message code="goalPop.yoil7" /></td>	            
					            </tr>
					            <tr>
					              <td><input type="text" id="saleGoalWeight1" class="sb-input w100" maxlength="3" ng-model="goalModifyInfo.saleGoalWeight1" style="text-align: center;"/></td>
					              <td><input type="text" id="saleGoalWeight2" class="sb-input w100" maxlength="3" ng-model="goalModifyInfo.saleGoalWeight2" style="text-align: center;"/></td>
					              <td><input type="text" id="saleGoalWeight3" class="sb-input w100" maxlength="3" ng-model="goalModifyInfo.saleGoalWeight3" style="text-align: center;"/></td>
					              <td><input type="text" id="saleGoalWeight4" class="sb-input w100" maxlength="3" ng-model="goalModifyInfo.saleGoalWeight4" style="text-align: center;"/></td>
					              <td><input type="text" id="saleGoalWeight5" class="sb-input w100" maxlength="3" ng-model="goalModifyInfo.saleGoalWeight5" style="text-align: center;"/></td>
					              <td><input type="text" id="saleGoalWeight6" class="sb-input w100" maxlength="3" ng-model="goalModifyInfo.saleGoalWeight6" style="text-align: center;"/></td>
					              <td><input type="text" id="saleGoalWeight7" class="sb-input w100" maxlength="3" ng-model="goalModifyInfo.saleGoalWeight7" style="text-align: center;"/></td>
					            </tr>
					            <%-- 매출목표금액 --%>
					            <tr>
					              <th><s:message code="goalPop.saleGoalAmt" /><em class="imp">*</em></th>
					              <td colspan="7">
									<input type="text" class="sb-input w200" id="saleGoalAmt" maxlength="13" ng-model="saleGoalAmt" ng-change="change()" style="text-align: right;"/>
					              </td>
					            </tr>
				          	</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div class="sc pd10 mt10">
			    <h3 class="h3_tbl2 pdt5 lh30">
			          <s:message code="goal.qry" />
			          <button class="btn_skyblue fr" id="saleGoalSave" ng-click="_broadcast('saleGoalRgsrDetl')">등록</button>
			    </h3>
				<div id="goalDtl1" class="w50 fl" style="width: 49%;">
				<div class="w100 mt10" ng-controller="goalDtl1Ctrl">
					<div class="w100 mt10">
				        <%--위즈모 테이블--%>
				        <div class="wj-gridWrap" style="height: 620px;">
							<wj-flex-grid
							    id="goalDtl1Grid"
					            autoGenerateColumns="false"
					            selection-mode="Row"
					            items-source="data"
					            control="flex"
					            initialized="initGrid(s,e)"
					            item-formatter="_itemFormatter">
				
					            <!-- define columns -->
					            <wj-flex-grid-column header="<s:message code="goal.goalDay.saleDate"/>" binding="saleGoalDate"	width="80" 		align="center" is-read-only="true"></wj-flex-grid-column>
					            <wj-flex-grid-column header="<s:message code="goalPop.storeCd"/>" 		binding="storeCd"		width="80" 		align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
					            <wj-flex-grid-column header="<s:message code="goalPop.storeCd"/>" 		binding="saleGoalYm"	width="80" 		align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
					            <wj-flex-grid-column header="<s:message code="goal.goalDay.yoil"/>" 	binding="yoil"			width="70" 		align="center" is-read-only="true"></wj-flex-grid-column>
					            <wj-flex-grid-column header="<s:message code="goalPop.saleGoalAmt"/>" 	binding="saleGoalAmt"	width="*" 		align="right"  is-read-only="false" aggregate="Sum"></wj-flex-grid-column>	
				          	</wj-flex-grid>
				          	<%-- ColumnPicker 사용시 include --%>
					        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
					          <jsp:param name="pickerTarget" value="goalDtl1Ctrl"/>
					        </jsp:include>
	        				<%--// ColumnPicker 사용시 include --%>
				        </div>
				        <%--//위즈모 테이블--%>
				    </div>
				</div>
			</div>
			
			<div id="goalDtl2" class="w50 fr" style="width: 49%;">
				<div class="w100 mt10" ng-controller="goalDtl2Ctrl">
					<div class="w100 mt10">
				        <%--위즈모 테이블--%>
				        <div class="wj-gridWrap" style="height: 620px;">
							<wj-flex-grid
							    id="goalDtl2Grid"
					            autoGenerateColumns="false"
					            selection-mode="Row"
					            allowMerging="'All'"
	        					stickyHeaders="true"
					            items-source="data"
					            control="flex"
					            initialized="initGrid(s,e)"
					            item-formatter="_itemFormatter">
				
					            <!-- define columns -->
					            <wj-flex-grid-column header="<s:message code="goal.goalDay.saleDate"/>" binding="saleGoalDate"	width="80" 		align="center" is-read-only="true"></wj-flex-grid-column>
					            <wj-flex-grid-column header="<s:message code="goalPop.storeCd"/>" 		binding="storeCd"		width="80" 		align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
					            <wj-flex-grid-column header="<s:message code="goalPop.storeCd"/>" 		binding="saleGoalYm"	width="80" 		align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
					            <wj-flex-grid-column header="<s:message code="goal.goalDay.yoil"/>" 	binding="yoil"			width="70" 		align="center" is-read-only="true"></wj-flex-grid-column>
					            <wj-flex-grid-column header="<s:message code="goalPop.saleGoalAmt"/>" 	binding="saleGoalAmt"	width="*" 		align="right"  is-read-only="false" aggregate="Sum"></wj-flex-grid-column>		
				          	</wj-flex-grid>
				          	<%-- ColumnPicker 사용시 include --%>
					        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
					          <jsp:param name="pickerTarget" value="goalDtl2Ctrl"/>
					        </jsp:include>
	        				<%--// ColumnPicker 사용시 include --%>
				        </div>
				        <%--//위즈모 테이블--%>
				    </div>
				</div>
			</div>
		</div>
		</div>		
	</div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/sale/anals/goal/goalPop/goalPop.js?ver=201901112.15" charset="utf-8"></script>
</wj-popup>