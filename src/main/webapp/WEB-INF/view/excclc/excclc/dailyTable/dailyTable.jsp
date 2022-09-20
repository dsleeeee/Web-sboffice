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
				<button class="btn_skyblue" ng-click="print()"			><s:message code="cmm.print"		/></button>
				<button class="btn_skyblue" ng-click="excelDownload()"	><s:message code="cmm.excel.down"	/></button>
			</span>
		</div>
	</div>

	<%-- 1 --%>
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

	<%-- 2 --%>
	<div id="div_sort_id_PAY" class="div_sort_class" data-sort="2" >
	    <div class="w100 mt10 flddUnfld_prodClass" ng-controller="dailyTableCtrl_prodClass">
			<div id="div_PAY">
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

	<%-- 3 --%>
	<div id="div_sort_id_NSL" class="div_sort_class" data-sort="3">
	    <div class="w100 mt10 flddUnfld_pay" ng-controller="dailyTableCtrl_pay">
			<div id="div_NSL">
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

	<%-- 4 --%>
	<div id="div_sort_id_NPAY" class="div_sort_class" data-sort="4">
	    <div class="w100 mt10 flddUnfld_rtn" ng-controller="dailyTableCtrl_rtn">
			<div id="div_NPAY">
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
							<wj-flex-grid-column header="<s:message code="dailyTable.rtnSaleCnt"	/>"		binding="rtnSaleCnt"   	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyTable.rtnSaleAmt"	/>"		binding="rtnSaleAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyTable.totAmt" 		/>"		binding="totAmt"		width="100" is-read-only="true" align="right"></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyTable.outAmt" 	    />"		binding="outAmt"	    width="100" is-read-only="true" align="right"></wj-flex-grid-column>
		                    <wj-flex-grid-column header="<s:message code="dailyTable.inAmt" 	    />"		binding="inAmt"	    	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
			            </wj-flex-grid>
	
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				        	<jsp:param name="pickerTarget" value="dailyTableCtrl_rtn"/>
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