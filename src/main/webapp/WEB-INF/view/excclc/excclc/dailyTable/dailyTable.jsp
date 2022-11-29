<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="dailyTableView" class="subCon">
	<div class="div_sort_class" data-sort="0">
		<div class="searchBar flddUnfld" ng-controller="dailyTableCtrl" ng-init="init()">
			<a href="#" class="open fl">${menuNm}</a>
			<div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;"><%-- [조회] --%>
				<button class="btn_blue fr" ng-click="_broadcast('dailyTableCtrl')"><s:message code="cmm.search" /></button>
			</div>
		</div>

		<table class="searchTbl" ng-controller="dailyTableCtrl_store">
			<colgroup>
				<col class="w15" />
				<col class="w35" />
				<col class="w15" />
				<col class="w35" />
			</colgroup>
			<tbody>
			<tr>
				<th>
					<s:message code="cmm.search.date" />
				</th>
				<td colspan="3">
					<div class="sb-select">
						<span class="txtIn"> <input id="startDate" name="startDate" class="w110px" /></span>
					</div>
				</td>
			</tr>

			<c:if test="${orgnFg == 'HQ'}">
				<tr>
					<th><s:message code="cmm.store"/></th>	<%-- 매장코드 --%>
					<td>
							<%-- 매장선택 모듈 싱글 선택 사용 시 include
                                    param 정의 : targetId	- angular 콘트롤러 및 input 생성시 사용할 타켓id
                                                displayNm 	- 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                                modiFg 		- 수정여부(변수 없을 경우 기본값으로 수정가능)
                                                closeFunc 	- 팝업 닫기시 호출할 함수
                            --%>
						<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
							<jsp:param name="targetId" value="dailyTableSelectStore"/>
						</jsp:include>

					</td>
				</tr>
			</c:if>
			<c:if test="${orgnFg == 'STORE'}">
				<input type="hidden" id="dailyTableSelectStoreCd" value="${sessionInfo.storeCd}"/>
			</c:if>
			</tbody>
		</table>

		<div class="mt10 oh sb-select dkbr" ng-controller="dailyTableCtrl_excel">
			<span class="fr">
				<button class="btn_skyblue" ng-click="print()"			>첫째 장 <s:message code="cmm.print"		/></button>
				<button class="btn_skyblue" ng-click="print2()"			>둘째 장 <s:message code="cmm.print"		/></button>
				<button class="btn_skyblue" ng-click="excelDownload()"	><s:message code="cmm.excel.down"	/></button>
			</span>
		</div>
	</div>

	<%-- 매출종합 --%>
	<div id="div_sort_id_SL" class="div_sort_class" data-sort="1">
		<div class="w100 mt10 flddUnfld_sl" ng-controller="dailyTableCtrl_sl">
			<div id="div_SL">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_sl
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers		="true"
										 selection-mode		="Row"
										 items-source		="data"
										 item-formatter		="_itemFormatter"
										 is-read-only		="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.saleAmt"			/>"		binding="saleAmt"      		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.dcAmt" 			/>"		binding="dcAmt"				width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.netSaleAmt"   		/>"		binding="netSaleAmt"		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.membrSaleAmt"		/>"		binding="membrSaleAmt"		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthSaleAmt"		/>"		binding="monthSaleAmt"	   	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthDcAmt"     	/>"		binding="monthDcAmt"	   	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthNetSaleAmt"  	/>"		binding="monthNetSaleAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthMembrSaleAmt"	/>"		binding="monthMembrSaleAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.guestCnt"			/>"		binding="guestCnt"			width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.guestUprc"			/>"		binding="guestUprc"			width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthGuestCnt"  	/>"		binding="monthGuestCnt"		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthGuestUprc" 	/>"		binding="monthGuestUprc"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_sl"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 매출종합 --%>
	<div id="div_sort_id_PRODCLASS" class="div_sort_class" data-sort="2" >
		<div class="w100 mt10 flddUnfld_prodClass" ng-controller="dailyTableCtrl_prodClass">
			<div id="div_PRODCLASS">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_prodClass
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers		="true"
										 selection-mode		="Row"
										 items-source		="data"
										 item-formatter		="_itemFormatter"
										 is-read-only		="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.prodClassCd"   	/>"     binding="prodClassCd"       width="100" is-read-only="true" align="center"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.prodClassNm"     	/>"     binding="prodClassNm"		width="100" is-read-only="true" align="left"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.saleQty"     		/>"     binding="saleQty"			width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cashAmt"       	/>"     binding="cashAmt"			width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cardAmt"       	/>"     binding="cardAmt"			width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.etcSaleAmt"      	/>"     binding="etcSaleAmt" 		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.realSaleAmt"      	/>"     binding="realSaleAmt"		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthSaleQty"     	/>"     binding="monthSaleQty"		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthCashAmt"     	/>"     binding="monthCashAmt"		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthCardAmt"    	/>"     binding="monthCardAmt"		width="150" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthEtcSaleAmt"	/>"     binding="monthEtcSaleAmt"	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthRealSaleAmt"	/>"     binding="monthRealSaleAmt"	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_prodClass"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 결제수단 --%>
	<div id="div_sort_id_PAY" class="div_sort_class" data-sort="3">
		<div class="w100 mt10 flddUnfld_pay" ng-controller="dailyTableCtrl_pay">
			<div id="div_PAY">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_pay
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers		="true"
										 selection-mode		="Row"
										 items-source		="data"
										 item-formatter		="_itemFormatter"
										 is-read-only		="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.cashAmt"     		/>"     binding="cashAmt"      		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cardAmt"			/>"		binding="cardAmt"			width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.postpaidAmt"		/>"		binding="postpaidAmt"		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.gitfAmt"			/>"		binding="gitfAmt"    		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.bMonthUnpaidAmt"	/>"		binding="bMonthUnpaidAmt"	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthUnpaidAmt"	/>"		binding="monthUnpaidAmt"	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.commissionAmt"		/>"		binding="commissionAmt"    	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.interestAmt"	    />"		binding="interestAmt"		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_pay"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 반품출납 --%>
	<div id="div_sort_id_RTN" class="div_sort_class" data-sort="4">
		<div class="w100 mt10 flddUnfld_rtn" ng-controller="dailyTableCtrl_rtn">
			<div id="div_RTN">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_rtn
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers		="true"
										 selection-mode		="Row"
										 items-source		="data"
										 item-formatter		="_itemFormatter"
										 is-read-only		="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.rtnSaleCnt"	/>"		binding="rtnSaleCnt"   	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.rtnSaleAmt"	/>"		binding="rtnSaleAmt"	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.totAmt" 		/>"		binding="totAmt"		width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.outAmt" 	    />"		binding="outAmt"	    width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.inAmt" 	    />"		binding="inAmt"	    	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_rtn"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 결재라인 --%>
	<div id="gridRepresent" class="w50 fl" style="display: none" ng-controller="configCtrl_1">
		<div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
			<div class="updownSet oh mb10">
				<span class="fl bk lh30"><s:message code='dailyReport.cfgPayLine' /></span>	<%-- 결재라인 --%>
				<button class="btn_skyblue" style="display: none;" id="btnAdd" 	ng-click="addRow()"		><s:message code="cmm.add" 		/></button>	<%-- style="display: none;"	none block inline --%>
				<button class="btn_skyblue" style="display: none;" id="btnDel" 	ng-click="deleteRow()"	><s:message code="cmm.delete" 	/></button>
				<button class="btn_skyblue" style="display: none;" id="btnSave"	ng-click="save()"		><s:message code="cmm.save" 	/></button>
			</div>

			<div class="wj-gridWrap" style="height:315px; overflow-x: hidden; overflow-y: hidden;">	<%-- 개발시 높이 조절해서 사용   &   tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어서 사용 --%>
				<div class="row">
					<wj-flex-grid	id					="grid_payline"
									 autoGenerateColumns	="false"
									 control				="flex"
									 initialized			="initGrid(s,e)"
									 sticky-headers		="true"
									 selection-mode		="Row"
									 items-source		="data"
									 item-formatter		="_itemFormatter">
						<%--
                        is-read-only		="true"
                        frozen-columns		="3"
                        --%>
						<wj-flex-grid-column header="<s:message code="dailyReport.cfgChk"			/>"		binding="gChk" 					width="30"	is-read-only="false" 	align="center"	                ></wj-flex-grid-column>
						<wj-flex-grid-column header="<s:message code="dailyReport.cfgPayLineSeq"	/>"		binding="cfgPayLineSeq"			width="40"	is-read-only="true" 	align="center"	                ></wj-flex-grid-column>
						<wj-flex-grid-column header="<s:message code="dailyReport.cfgPayLineNm"  	/>"		binding="cfgPayLineNm"			width="123"	is-read-only="false" 	align="left"	max-length=10	></wj-flex-grid-column>

						<wj-flex-grid-column header=""														binding="cfgHqOfficeCd"			width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgHqBrandCd"      	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgStoreCd"        	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgPayLineNo"			width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgUseYn"          	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgStatus"          	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
					</wj-flex-grid>

					<%-- ColumnPicker 사용시 include --%>
					<%--
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="configCtrl_1"/>
                    </jsp:include>
                    --%>
				</div>
			</div>
		</div>
	</div>

	<%-- 첫째장_수강현황 --%>
	<div id="div_sort_id_courseStatus" style="display: none" class="div_sort_class" data-sort="5">
		<div class="w100 mt10 flddUnfld_courseStatus" ng-controller="dailyTableCtrl_courseStatus">
			<div id="div_COURSESTATUS">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_courseStatus
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers			="true"
										 selection-mode			="Row"
										 items-source			="data"
										 item-formatter			="_itemFormatter"
										 is-read-only			="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.fg"		/>"	binding="fg"      	 width="100" is-read-only="true" align="center"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.totTuition"/>"	binding="totTuition" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.studentCnt"/>"	binding="studentCnt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.dcTuition"	/>"	binding="dcTuition"	 width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.netTuition"/>"	binding="netTuition" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.tuition"	/>"	binding="tuition"	 width="100" is-read-only="true" align="right"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_courseStatus"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 첫째장_수강유형 --%>
	<div id="div_sort_id_courseType" style="display: none" class="div_sort_class" data-sort="6">
		<div class="w100 mt10 flddUnfld_courseType" ng-controller="dailyTableCtrl_courseType">
			<div id="div_COURSETYPE">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_courseType
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers			="true"
										 selection-mode			="Row"
										 items-source			="data"
										 item-formatter			="_itemFormatter"
										 is-read-only			="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.fg"/>"				binding="fg"      	 	width="100" is-read-only="true" align="center"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cashAmt"/>"		binding="cashAmt" 		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cardAmt"/>"		binding="cardAmt" 		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.daySaleQty"/>"		binding="daySaleQty"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.daily"/>"			binding="daily" 		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthSaleQty"/>"	binding="monthSaleQty"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthly"/>"		binding="monthly"	 	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.bMonthly"/>"		binding="bMonthly"	 	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.totSum"/>"			binding="totSum"	 	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_courseType"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 첫째장_수강료현황--%>
	<div id="div_sort_id_tuition1" style="display: none" class="div_sort_class" data-sort="7">
		<div class="w100 mt10 flddUnfld_courseType" ng-controller="dailyTableCtrl_tuition1">
			<div id="div_TUITION1">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_courseType
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers			="true"
										 selection-mode			="Row"
										 items-source			="data"
										 item-formatter			="_itemFormatter"
										 is-read-only			="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.fg"/>"				binding="fg"     	width="100" is-read-only="true" align="center"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cashAmt"/>"		binding="cashAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cardAmt"/>"		binding="cardAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.catAmt"/>"			binding="catAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.totInAmt"/>"		binding="totInAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cancelCourse"/>"	binding="cancelCnt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cancelCourse"/>"	binding="cancelAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_tuition1"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 첫째장_수강료현황--%>
	<div id="div_sort_id_tuition2" style="display: none" class="div_sort_class" data-sort="8">
		<div class="w100 mt10 flddUnfld_courseType" ng-controller="dailyTableCtrl_tuition2">
			<div id="div_TUITION2">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_courseType
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers			="true"
										 selection-mode			="Row"
										 items-source			="data"
										 item-formatter			="_itemFormatter"
										 is-read-only			="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.fg"/>"					binding="fg"      	 		width="100" is-read-only="true" align="center"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.bMonthUnpaidAmt"/>"	binding="bMonthUnpaidAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.monthUnpaidAmt"/>"		binding="monthUnpaidAmt" 	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.commissionAmt"/>"		binding="commissionAmt"		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.interestAmt"/>"		binding="interestAmt" 		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.cardInAmt"/>"			binding="cardInAmt"			width="100" is-read-only="true" align="right"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_tuition2"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 첫째장_단체수강내역--%>
	<div id="div_sort_id_groupCourse" style="display: none" class="div_sort_class" data-sort="9">
		<div class="w100 mt10 flddUnfld_courseType" ng-controller="dailyTableCtrl_groupCourse">
			<div id="div_GROUPCOURSE">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_courseType
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers			="true"
										 selection-mode			="Row"
										 items-source			="data"
										 item-formatter			="_itemFormatter"
										 is-read-only			="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.fg"/>"			binding="fg"      	width="100" is-read-only="true" align="center"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.groupNm"/>"	binding="groupNm"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.saleDate"/>"	binding="saleDate" 	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.summary"/>"	binding="summary"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.amt"/>"		binding="amt" 		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_groupCourse"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 둘째장_출납현황--%>
	<div id="div_sort_id_paymentStatus1" style="display: none" class="div_sort_class" data-sort="10">
		<div class="w100 mt10 flddUnfld_courseType" ng-controller="dailyTableCtrl_paymentStatus1">
			<div id="div_PAYMENTSTATUS1">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_courseType
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers			="true"
										 selection-mode			="Row"
										 items-source			="data"
										 item-formatter			="_itemFormatter"
										 is-read-only			="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.monthInAmtSum"/>"	binding="monthInAmtSum"	width="100" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.inAmt"/>"			binding="inAmt"			width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.outAmt"/>"			binding="outAmt" 		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.remainAmt"/>"		binding="remainAmt"		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_paymentStatus1"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 둘째장_출납현황--%>
	<div id="div_sort_id_paymentStatus2" style="display: none" class="div_sort_class" data-sort="11">
		<div class="w100 mt10 flddUnfld_courseType" ng-controller="dailyTableCtrl_paymentStatus2">
			<div id="div_PAYMENTSTATUS2">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_courseType
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers			="true"
										 selection-mode			="Row"
										 items-source			="data"
										 item-formatter			="_itemFormatter"
										 is-read-only			="true">
							<wj-flex-grid-column header="<s:message code="dailyTable.info"/>"		binding="inInfo"	width="100" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.amt"/>"		binding="inAmt"		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.info"/>"		binding="outInfo" 	width="100" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.amt"/>"		binding="outAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.remainAmt"/>"	binding="remainAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_paymentStatus2"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 둘째장_출납현황--%>
	<div id="div_sort_id_paymentStatus3" style="display: none" class="div_sort_class" data-sort="12">
		<div class="w100 mt10 flddUnfld_courseType" ng-controller="dailyTableCtrl_paymentStatus3">
			<div id="div_PAYMENTSTATUS3">
				<div class="w100 mt10 mb20">
					<div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
						<wj-flex-grid	#grid_courseType
										 autoGenerateColumns	="false"
										 control				="flex"
										 initialized			="initGrid(s,e)"
										 sticky-headers			="true"
										 selection-mode			="Row"
										 items-source			="data"
										 item-formatter			="_itemFormatter"
										 is-read-only			="true"
										 headers-visibility		="None">
							<wj-flex-grid-column header="<s:message code="dailyTable.remark"/>"		binding="remark"	width="100" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyTable.content"/>"	binding="content"	width="400" is-read-only="true" align="left"></wj-flex-grid-column>
						</wj-flex-grid>

						<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
							<jsp:param name="pickerTarget" value="dailyTableCtrl_paymentStatus3"/>
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/dailyTable/dailyTable.js?ver=20220915.01" charset="utf-8"></script>

<script type="text/javascript" charset="utf-8">
	function sortByDataItem(containerID){
		var values = [];
		$("#" + containerID + " .div_sort_class").each(function(index) {
			var item        = {};
			item.index  = index;
			item.obj    = this;
			item.value  = $(this).data("sort");
			values.push(item);
		});

		values.sort( function(a, b){return(b.value - a.value);} );
		var container = $("#" + containerID);
		for (var i=0; i<values.length; i++) {
			var self = $( values[i].obj );
			self.detach();
			container.prepend(self);
		}
		return;
	}

</script>

<%--allow-merging="true"--%>