<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<%--
<link rel="stylesheet" type="text/css" href="/resource/solbipos/css/cmm/dailyReport.css"	/>
<link rel="stylesheet" type="text/css" href="/resource/solbipos/css/sale/dailyReport.css"	/>
<link rel="stylesheet" type="text/css" href="/resource/solbipos/js/sale/anals/dailyReportNew/dailyReport.css"	/>
	searchBar_report
--%>

<%--
<div id="reportView" class="subCon" ng-controller="reportCtrl">

    <div class="searchBar flddUnfld">


<div id="reportView" class="subCon">
    <div class="searchBar flddUnfld" ng-controller="reportCtrl">
--%>
<div id="reportView" class="subCon">
	<div class="div_sort_class" data-sort="0">
	    <div class="searchBar flddUnfld" ng-controller="reportCtrl">
	        <a href="#" class="open fl">${menuNm}</a>
	        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;"><%-- [조회] --%>
	        	<button class="btn_blue fr" ng-click="_broadcast('reportCtrl')"><s:message code="cmm.search" /></button>
	        </div>
	    </div>
	
	    <table class="searchTbl" ng-controller="reportCtrl_store">
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
	                        <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
	                        <span class="rg">~</span>
	                        <span class="txtIn"> <input id="endDate"   name="endDate"   class="w200px" /></span>
	                    </div>
	                </td>
	            </tr>
	
	            <c:if test="${orgnFg == 'HQ'}">
	            <tr>
	                <th><s:message code="dailyReport.store"/></th>	<%-- 매장코드 --%>
	                <td>
	                    <%-- 매장선택 모듈 멀티 선택 사용 시 include
	                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
	                        <jsp:param name="targetId" value="reportSelectStore"/>
	                    </jsp:include>
	                    --%>
	                    <%-- 매장선택 모듈 싱글 선택 사용 시 include
	                            param 정의 : targetId	- angular 콘트롤러 및 input 생성시 사용할 타켓id
	                                        displayNm 	- 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
	                                        modiFg 		- 수정여부(변수 없을 경우 기본값으로 수정가능)
	                                        closeFunc 	- 팝업 닫기시 호출할 함수
	                    --%>
	                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
	                        <jsp:param name="targetId" value="reportSelectStore"/>
	                    </jsp:include>
	                </td>
	            </tr>
	            </c:if>
			    <c:if test="${orgnFg == 'STORE'}">
			      <input type="hidden" id="reportSelectStoreCd" value="${sessionInfo.storeCd}"/>
			    </c:if>
	        </tbody>
	    </table>
	
	
		<%-- '영업일보(0000-00-00 ~ 0000-00-00)' 문구 추가로 변경
	    <div class="mt20 oh sb-select dkbr" ng-controller="reportCtrl_excel">
	    	<button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="dailyReport.reportPrint" /></button>	<s:message code="cmm.excel.down" />
		</div>
		--%>
		<div><br></div>
		<div><br></div>
		<div class="tbl-tit-btn" ng-controller="reportCtrl_excel">
			<div class="txtIn bk lh30">
				<s:message code="dailyReport.report"/> ({{span_startDate}} ~ {{span_endDate}})
			</div>
			<span class="fr">
				<button class="btn_skyblue" ng-click="print()"			><s:message code="dailyReport.reportPrint"	/></button>
				<button class="btn_skyblue" ng-click="excelDownload()"	><s:message code="cmm.excel.down" 			/></button>
			</span>
		</div>
	</div>	<%-- <div class="div_sort_class" data-sort="0"> --%>

<%--
</div>
<div id="reportView" class="subCon">
--%>

	<%-- 매출종합 --%>
	<div id="div_sort_id_SL" class="div_sort_class" data-sort="1">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_sl" ng-controller="reportCtrl_sl">
			<div class="oh mb10">
	        	<s:message code='dailyReport.sl'/>
			</div>
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
						            	is-read-only		="true"
						            	frozen-columns		="1">
						            <%--sorted-column		="toggleFreeze(false)">--%>
						<%--<wj-flex-grid-column header="<s:message code="dailyReport.slSaleFg"     	/>"     binding="slSaleFg"      	width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>--%>
							<wj-flex-grid-column header="<s:message code="dailyReport.emptySpace"		/>"		binding="slSaleFg"      	width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slTotSaleAmt" 	/>"		binding="slTotSaleAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slTotDcAmt"   	/>"		binding="slTotDcAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slRealSaleAmt"	/>"		binding="slRealSaleAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slGaAmt"      	/>"		binding="slGaAmt"	    	width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slVatAmt"     	/>"		binding="slVatAmt"	    	width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slTotTipAmt"  	/>"		binding="slTotTipAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slTotEtcAmt"  	/>"		binding="slTotEtcAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slTotBillCnt"		/>"		binding="slTotBillCnt"		width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slTotGuestCnt"	/>"		binding="slTotGuestCnt"		width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slBillUnprc"  	/>"		binding="slBillUnprc"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.slGuestUnprc" 	/>"		binding="slGuestUnprc"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_sl"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
	    </div>
    </div>



	<%-- 결제수단 --%>
	<div id="div_sort_id_PAY" class="div_sort_class" data-sort="2">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_pay" ng-controller="reportCtrl_pay">
			<div class="oh mb10">
	        	<span id="span_PAY"><s:message code='dailyReport.pay'/></span>
			</div>
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
						                is-read-only		="true"
						                frozen-columns		="3">
						            <%--sorted-column		="toggleFreeze(false)">--%>
							<wj-flex-grid-column header="<s:message code="dailyReport.payRealSaleAmt"   />"     binding="payRealSaleAmt"    width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payTotTipAmt"     />"     binding="payTotTipAmt"      width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payTotEtcAmt"     />"     binding="payTotEtcAmt"      width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payCardAmt"       />"     binding="payCardAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payCashAmt"       />"     binding="payCashAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payPaycoAmt"      />"     binding="payPaycoAmt"       width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payVpointAmt"     />"     binding="payVpointAmt"      width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payVcoupnAmt"     />"     binding="payVcoupnAmt"      width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payVchargeAmt"    />"     binding="payVchargeAmt"     width="150" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payMpayAmt"       />"     binding="payMpayAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payMcoupnAmt"     />"     binding="payMcoupnAmt"      width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payMembrAmt"      />"     binding="payMembrAmt"       width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payPrepaidAmt"    />"     binding="payPrepaidAmt"     width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payPostpaidAmt"   />"     binding="payPostpaidAmt"    width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payCoupnAmt"      />"     binding="payCoupnAmt"       width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payGiftAmt"       />"     binding="payGiftAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payFstmpAmt"      />"     binding="payFstmpAmt"       width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payPartnerAmt"    />"     binding="payPartnerAmt"     width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payOkcsbAmt"      />"     binding="payOkcsbAmt"       width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payEmpCardAmt"    />"     binding="payEmpCardAmt"     width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.payTemporaryAmt"  />"     binding="payTemporaryAmt"   width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.paySmartOrderAmt"	/>"     binding="paySmartOrderAmt"	width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_pay"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 비매출종합 --%>
	<div id="div_sort_id_NSL" class="div_sort_class" data-sort="3">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_nsl" ng-controller="reportCtrl_nsl">
			<div class="oh mb10">
	        	<span id="span_NSL"><s:message code='dailyReport.nsl'/></span>
			</div>
			<div id="div_NSL">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_nsl
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true">
						            <%--sorted-column		="toggleFreeze(false)">--%>
							<wj-flex-grid-column header="<s:message code="dailyReport.emptySpace"     	/>"     binding="nslSaleFg"      	width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.nslTotSaleAmt"	/>"		binding="nslTotSaleAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.nslTotDcAmt"		/>"		binding="nslTotDcAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.nslRealSaleAmt"	/>"		binding="nslRealSaleAmt"    width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.nslGaAmt"		    />"		binding="nslGaAmt"			width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.nslVatAmt"		/>"		binding="nslVatAmt"			width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
						<%--<wj-flex-grid-column header="<s:message code="dailyReport.nslTotTipAmt"	    />"		binding="nslTotTipAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>--%>
						<%--<wj-flex-grid-column header="<s:message code="dailyReport.nslTotEtcAmt"	    />"		binding="nslTotEtcAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>--%>
							<wj-flex-grid-column header="<s:message code="dailyReport.nslTotBillCnt"	/>"		binding="nslTotBillCnt"    	width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="dailyReport.nslBillUnprc"	    />"		binding="nslBillUnprc"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_nsl"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 비매출 결제수단 --%>
	<div id="div_sort_id_NPAY" class="div_sort_class" data-sort="4">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_npay" ng-controller="reportCtrl_npay">
			<div class="oh mb10">
	        	<span id="span_NPAY"><s:message code='dailyReport.npay'/></span>
			</div>
			<div id="div_NPAY">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_npay
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true">
						            <%--sorted-column		="toggleFreeze(false)">--%>
							<wj-flex-grid-column header="<s:message code="dailyReport.npayRealSaleAmt" 	/>"		binding="npayRealSaleAmt"   width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.npayTotTipAmt" 	/>"		binding="npayTotTipAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.npayTotEtcAmt" 	/>"		binding="npayTotEtcAmt"		width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.npayCardAmt" 	    />"		binding="npayCardAmt"	    width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.npayCashAmt" 	    />"		binding="npayCashAmt"	    width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_npay"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 포스정산 --%>
	<div id="div_sort_id_POS" class="div_sort_class" data-sort="5">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_pos" ng-controller="reportCtrl_pos">
			<div class="oh mb10">
	        	<span id="span_POS"><s:message code='dailyReport.pos'/></span>
			</div>
			<div id="div_POS">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_pos
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.posFundAmt"       />"     binding="posFundAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.posCashExactAmt"  />"     binding="posCashExactAmt"   width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.posIomoneyInAmt"  />"     binding="posIomoneyInAmt"   width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.posIomoneyOutAmt" />"     binding="posIomoneyOutAmt"  width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.posGiftAmt"       />"     binding="posGiftAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.posLostAmt"       />"     binding="posLostAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.posNonsaleAmt" 	/>"     binding="posNonsaleAmt"    	width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_pos"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 판매원별 매출  --%>
	<div id="div_sort_id_EMP" class="div_sort_class" data-sort="6">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_emp" ng-controller="reportCtrl_emp">
			<div class="oh mb10">
	        	<span id="span_EMP"><s:message code='dailyReport.emp'/></span>
			</div>
			<div id="div_EMP">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_emp
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true"
						                frozen-columns		="1">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                <%--<wj-flex-grid-column header="<s:message code="dailyReport.empNo"            />"     binding="empNo"             width="100" is-read-only="true" align="left"	                ></wj-flex-grid-column>--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empNm"            />"     binding="empNm"             width="200" is-read-only="true" align="left"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empSalCnt"        />"     binding="empSalCnt"         width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empSalTotalCard"  />"     binding="empSalTotalCard"   width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empSalTotalCash"  />"     binding="empSalTotalCash"   width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empSalTotalEtc"   />"     binding="empSalTotalEtc"    width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empRtnCnt"        />"     binding="empRtnCnt"         width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empRtnTotalCard"  />"     binding="empRtnTotalCard"   width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empRtnTotalCash"  />"     binding="empRtnTotalCash"   width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empRtnTotalEtc"   />"     binding="empRtnTotalEtc"    width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.empCancelCnt"     />"     binding="empCancelCnt"      width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_emp"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 할인내역 --%>
	<div id="div_sort_id_DC" class="div_sort_class" data-sort="7">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_dc" ng-controller="reportCtrl_dc">
			<div class="oh mb10">
	        	<span id="span_DC"><s:message code='dailyReport.dc'/></span>
			</div>
			<div id="div_DC">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_dc
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true"
						                frozen-columns		="1">
						            <%--sorted-column		="toggleFreeze(false)">--%>
						<%--<wj-flex-grid-column header="<s:message code="dailyReport.dcCd"             />"     binding="dcCd"              width="200" is-read-only="true" align="left"	                ></wj-flex-grid-column>--%>
							<wj-flex-grid-column header="<s:message code="dailyReport.dcNm"             />"     binding="dcNm"              width="200" is-read-only="true" align="left"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcSaleQty"     	/>"     binding="dcSaleQty"			width="150" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcSaleAmt"     	/>"     binding="dcSaleAmt"      	width="200" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                	<wj-flex-grid-column header="<s:message code="dailyReport.dcAmt"            />"     binding="dcAmt"             width="200" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcTotDcAmt"       />"     binding="dcTotDcAmt"        width="200" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcRealSaleAmt"    />"     binding="dcRealSaleAmt"     width="200" is-read-only="true" align="right"	                ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_dc"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 할인상세내역 --%>
	<div id="div_sort_id_DCDTL" class="div_sort_class" data-sort="8">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_dcdtl" ng-controller="reportCtrl_dcdtl">
			<div class="oh mb10">
	        	<span id="span_DCDTL"><s:message code='dailyReport.dcdtl'/></span>
			</div>
			<div id="div_DCDTL">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden; height:300px;">
			            <wj-flex-grid	#grid_dcdtl
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true"
						                frozen-columns		="1">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcdtlDcCd"        />"     binding="dcdtlDcCd"         width="200" is-read-only="true" align="left"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcdtlDcNm"        />"     binding="dcdtlDcNm"         width="200" is-read-only="true" align="left"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcdtlCnt"         />"     binding="dcdtlCnt"          width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcdtlTotSaleAmt"  />"     binding="dcdtlTotSaleAmt"   width="200" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcdtlDcAmt"       />"     binding="dcdtlDcAmt"        width="200" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcdtlTotDcAmt"    />"     binding="dcdtlTotDcAmt"     width="200" is-read-only="true" align="right"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.dcdtlRealSaleAmt" />"     binding="dcdtlRealSaleAmt"  width="200" is-read-only="true" align="right"	                ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_dcdtl"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 상품권 판매 및 회수내역 --%>
	<div id="div_sort_id_GIFT" class="div_sort_class" data-sort="9">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_gift" ng-controller="reportCtrl_gift">
			<div class="oh mb10">
	        	<span id="span_GIFT"><s:message code='dailyReport.gift'/></span>
			</div>
			<div id="div_GIFT">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden; height:300px;">
			            <wj-flex-grid	#grid_gift
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true"
						                frozen-columns		="3">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftCd"           />"     binding="giftCd"            width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftNm"           />"     binding="giftNm"            width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftUprc"         />"     binding="giftUprc"          width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftOutQty"       />"     binding="giftOutQty"        width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftOutAmt"       />"     binding="giftOutAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftSaleQty"      />"     binding="giftSaleQty"       width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftSaleAmt"      />"     binding="giftSaleAmt"       width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftRtnQty"       />"     binding="giftRtnQty"        width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftRtnAmt"       />"     binding="giftRtnAmt"        width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftRtnCarryInQty"/>"     binding="giftRtnCarryInQty" width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.giftRtnCarryInAmt"/>"     binding="giftRtnCarryInAmt" width="100" is-read-only="true" align="right"	                ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_gift"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 수발주내역 --%>
	<div id="div_sort_id_ORDER" class="div_sort_class" data-sort="10">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_order" ng-controller="reportCtrl_order">
			<div class="oh mb10">
	        	<span id="span_ORDER"><s:message code='dailyReport.order'/></span>
			</div>
			<div id="div_ORDER">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_lv1
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.orderOutHqOut"	/>"		binding="orderOutHqOut"     width="150" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.orderOutStoreCfm"	/>"		binding="orderOutStoreCfm"  width="150" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.orderOutError"    />"		binding="orderOutError"     width="150" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.orderRtnHqOut"    />"		binding="orderRtnHqOut"   	width="150" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.orderRtnStoreCfm"	/>"		binding="orderRtnStoreCfm"  width="150" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.orderRtnError"	/>"		binding="orderRtnError"     width="150" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.orderRtnPenalty"	/>"		binding="orderRtnPenalty"   width="150" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_order"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 대분류별 매출 --%>
	<div id="div_sort_id_LV1" class="div_sort_class" data-sort="11">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_lv1" ng-controller="reportCtrl_lv1">
			<div class="oh mb10">
	        	<span id="span_LV1"><s:message code='dailyReport.lv1'/></span>
			</div>
			<div id="div_LV1">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_lv1
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true"
						                frozen-columns		="1">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv1Nm"            />"		binding="lv1Nm"             width="500" is-read-only="true" align="left" 					></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv1TotSaleAmt"    />"		binding="lv1TotSaleAmt"     width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv1TotDcAmt"      />"		binding="lv1TotDcAmt"       width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv1RealSaleAmt"   />"		binding="lv1RealSaleAmt"    width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv1TotSaleQty"    />"		binding="lv1TotSaleQty"     width="200" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_lv1"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 중분류별 매출 --%>
	<div id="div_sort_id_LV2" class="div_sort_class" data-sort="12">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_lv2" ng-controller="reportCtrl_lv2">
			<div class="oh mb10">
	        	<span id="span_LV2"><s:message code='dailyReport.lv2'/></span>
			</div>
			<div id="div_LV2">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden; height:300px;">
			            <wj-flex-grid	#grid_lv2
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true"
						                frozen-columns		="1">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv2Nm"            />"		binding="lv2Nm"             width="500" is-read-only="true" align="left" 					></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv2TotSaleAmt"    />"		binding="lv2TotSaleAmt"     width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv2TotDcAmt"      />"		binding="lv2TotDcAmt"       width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv2RealSaleAmt"   />"		binding="lv2RealSaleAmt"    width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv2TotSaleQty"    />"		binding="lv2TotSaleQty"     width="200" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_lv2"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 소분류별 매출 --%>
	<div id="div_sort_id_LV3" class="div_sort_class" data-sort="13">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_lv3" ng-controller="reportCtrl_lv3">
			<div class="oh mb10">
	        	<span id="span_LV3"><s:message code='dailyReport.lv3'/></span>
			</div>
			<div id="div_LV3">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden; height:300px;">
			            <wj-flex-grid	#grid_lv3
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true"
						                frozen-columns		="1">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv3Nm"            />"		binding="lv3Nm"             width="500" is-read-only="true" align="left" 					></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv3TotSaleAmt"    />"		binding="lv3TotSaleAmt"     width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv3TotDcAmt"      />"		binding="lv3TotDcAmt"       width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv3RealSaleAmt"   />"		binding="lv3RealSaleAmt"    width="200" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.lv3TotSaleQty"    />"		binding="lv3TotSaleQty"     width="200" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_lv3"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 상품별 매출 --%>
	<div id="div_sort_id_PROD" class="div_sort_class" data-sort="14">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_prod" ng-controller="reportCtrl_prod">
			<div class="oh mb10">
	        	<span id="span_PROD"><s:message code='dailyReport.prod'/></span>
			</div>
			<div id="div_PROD">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden; height:300px;">
			            <wj-flex-grid	#grid_prod
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.prodNm"           />"		binding="prodNm"            width="500" is-read-only="true" align="left" 					></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.prodTotSaleQty"   />"		binding="prodTotSaleQty"    width="150" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.prodRealSaleAmt"  />"		binding="prodRealSaleAmt"   width="150" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_prod"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 경쟁사 매출 --%>
	<div id="div_sort_id_COMPT" class="div_sort_class" data-sort="15">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_compt" ng-controller="reportCtrl_compt">
			<div class="oh mb10">
	        	<span id="span_COMPT"><s:message code='dailyReport.compt'/></span>
			</div>
			<div id="div_COMPT">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_prod
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.comptProd"        />"		binding="comptProd"      	width="500" is-read-only="true" align="left" 					></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.comptRealSaleAmt" />"		binding="comptRealSaleAmt"  width="300" is-read-only="true" align="right"					></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_compt"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 승인현황 --%>
	<div id="div_sort_id_APPR" class="div_sort_class" data-sort="16">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_appr" ng-controller="reportCtrl_appr">
			<div class="oh mb10">
	        	<span id="span_APPR"><s:message code='dailyReport.appr'/></span>
			</div>
			<div id="div_APPR">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_appr
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true"
						                frozen-columns		="1">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.emptySpace"      	/>"		binding="apprNm"            width="100" is-read-only="true" align="center" 					></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprCntCard"      />"		binding="apprCntCard"       width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprApCard"       />"		binding="apprApCard"        width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprDcCard"       />"		binding="apprDcCard"        width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprCntCash"      />"		binding="apprCntCash"       width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprApCash"       />"		binding="apprApCash"        width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprDcCash"       />"		binding="apprDcCash"        width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprCntPayco"     />"		binding="apprCntPayco"      width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprApPayco"      />"		binding="apprApPayco"       width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprDcPayco"      />"		binding="apprDcPayco"       width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMpay"      />"		binding="apprCntMpay"       width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprApMpay"       />"		binding="apprApMpay"        width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprDcMpay"       />"		binding="apprDcMpay"        width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMcoupn"    />"		binding="apprCntMcoupn"     width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprApMcoupn"     />"		binding="apprApMcoupn"      width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprDcMcoupn"     />"		binding="apprDcMcoupn"      width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprCntPartner"   />"		binding="apprCntPartner"    width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprApPartner"    />"		binding="apprApPartner"     width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprDcPartner"    />"		binding="apprDcPartner"     width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprCntNcard"     />"		binding="apprCntNcard"      width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprApNcard"      />"		binding="apprApNcard"       width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprDcNcard"      />"		binding="apprDcNcard"       width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
	
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprCntNcash"     />"		binding="apprCntNcash"      width="100" is-read-only="true" align="center" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprApNcash"      />"		binding="apprApNcash"       width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.apprDcNcash"      />"		binding="apprDcNcash"       width="100" is-read-only="true" align="right" 	aggregate="Sum" ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_appr"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 회원 --%>
	<div id="div_sort_id_MEMBR" class="div_sort_class" data-sort="17">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_membr" ng-controller="reportCtrl_membr">
			<div class="oh mb10">
	        	<span id="span_MEMBR"><s:message code='dailyReport.membr'/></span>
			</div>
			<div id="div_MEMBR">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_prod
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                <%--<wj-flex-grid-column header="<s:message code="dailyReport.membr"            />"     binding="membr"             width="150" is-read-only="true" align="left"	                ></wj-flex-grid-column>--%>
				            <wj-flex-grid-column header="<s:message code="dailyReport.membrTotal"       />"     binding="membrTotal"        width="150" is-read-only="true" align="center"	                ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="dailyReport.membrNew"         />"     binding="membrNew"          width="150" is-read-only="true" align="center"	                ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="dailyReport.membrNoInfo"      />"     binding="membrNoInfo"       width="150" is-read-only="true" align="center"	                ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="dailyReport.membrRealSaleAmt" />"     binding="membrRealSaleAmt"  width="150" is-read-only="true" align="right"	                ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="dailyReport.membrSaleAmt"     />"     binding="membrSaleAmt"      width="150" is-read-only="true" align="right"	                ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="dailyReport.membrSalePrctg"   />"     binding="membrSalePrctg"    width="150" is-read-only="true" align="center" format="n5"       ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="dailyReport.membrPointAccum"  />"     binding="membrPointAccum"   width="150" is-read-only="true" align="center"	                ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="dailyReport.membrPointUse"    />"     binding="membrPointUse"     width="150" is-read-only="true" align="center" 	                ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_membr"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



	<%-- 근태관리 --%>
	<div id="div_sort_id_WORK" class="div_sort_class" data-sort="18">
		<div><br></div>
	    <div class="w100 mt10 flddUnfld_work" ng-controller="reportCtrl_work">
			<div class="oh mb10">
	        	<span id="span_WORK"><s:message code='dailyReport.work'/></span>
			</div>
			<div id="div_WORK">
			    <div class="w100 mt10 mb20">
			        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
			            <wj-flex-grid	#grid_work
			            				autoGenerateColumns	="false"
						                control				="flex"
						                initialized			="initGrid_work(s,e)"
						                sticky-headers		="true"
						                selection-mode		="Row"
						                items-source		="data"
						                item-formatter		="_itemFormatter"
						                is-read-only		="true">
						            <%--sorted-column		="toggleFreeze(false)">--%>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.workEmpNm"        />"     binding="workEmpNm"         width="200" is-read-only="true" align="left"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.workCnt"          />"     binding="workCnt"           width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyReport.workTime"         />"     binding="workTime"          width="100" is-read-only="true" align="center"	                ></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="reportCtrl_work"/>
				        </jsp:include>
			        </div>
			    </div>
		    </div>
		</div>
	</div>



</div>


<script type="text/javascript" src="/resource/solbipos/js/sale/anals/dailyReportNew/report/report.js" charset="utf-8"></script>

<script type="text/javascript" charset="utf-8">
	/*
	var myArray = ['0', '5', '4', '3', '2', '1'];	//For Test
	var elArray = [];

	$('div[id^="div_"]').each(function() {
	    elArray[ $(this).data('data-order') ] = $(this);
	    
	    console.log('$(this):' + $(this));
	});

	$.each(myArray, function(index, value){
		console.log('index:' + index + ' & value:' + value);
		$('#reportView').append( elArray[value] );
	});	
	*/
	
	/*
	function sortByDataItem_OLD(containerID) {
		$('div[id^="div_"]').each(function() {
		    //elArray[ $(this).data('data-order') ] = $(this);
		    //console.log('$(this):' + $(this));
		    console.log("###");
		});		
		console.log('### sortByDataItem > containerID: ' + containerID);

	    var values = [];
	  //$("#" + containerID + " .item").each(function(index) {
	  //$('div[id^="div_"]').each(function(index) {
	  //$("#" + containerID + " .div_").each(function(index) {
	  //$('#' + containerID + ' .div[id^="div_"]').each(function(index) {
	//eval( '$(".div_'  + item.cfgCd + '").show();' );
	
		$('div[id^="div_"]').each( function(index, value) {
	    	console.log('### > index: ' + index + ' & value:' + value + ' & data-order:' + $(this).data("data-order"));
	        var item        = {};
	            item.index  = index;
	            item.obj    = this;
	            item.value  = $(this).data("data-order");
	        values.push(item);
	        //console.log('### > item: ' + item);
	    });

		for (var i=0; i<values.length; i++) {
			console.log('Before values[]: ' + values[i].index);
		}
	    values.sort( function(a, b){return(b.value - a.value);} );
		
	    for (var i=0; i<values.length; i++) {
	    	console.log('After  values[]: ' + values[i].index);
		}

	    //var container = $("#" + containerID);
	    //for (var i=0; i<values.length; i++) {
	    //    var self = $(values[i].obj);
	    //        self.detach();
	    //    container.prepend(self);
	    //}
	    return;
	}
	*/
	

	function sortByDataItem(containerID){
		//console.log("### sortByDataItem");

	    var values = [];
	    $("#" + containerID + " .div_sort_class").each(function(index) {
//	    	console.log(' > :' + $(this).data("sort"));
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
	

	//	sortByDataItem("reportView");
	//	console.log('div_sort_id_NPAY:' + $('#div_sort_id_NPAY').data('sort') );
		/*
		$('#div_sort_id_COMPT').data('sort') = '10';
		$('#div_sort_id_COMPT').attr("data-sort", '10' )
		$('div_sort_id_COMPT').attr('data-sort', '10' );
		$('#div_sort_id_COMPT').data('sort');
		*/
	//	$('#div_sort_id_NPAY').data('sort', '2');
	//	console.log('div_sort_id_NPAY:' + $('#div_sort_id_NPAY').data('sort') );
	//	sortByDataItem("reportView");
</script>


