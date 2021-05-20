<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<wj-popup id="billSalePopLayer" control="billSalePopLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:1000px;">
	<div class="wj-dialog wj-dialog-columns" ng-controller="billSalePopCtrl">
    	<div class="wj-dialog-header wj-dialog-header-font">
      		<s:message code="saleComPopup.bill.sale"/>
      		<span id="spanDtlTitle"></span>
      		<a href="#" class="wj-hide btn_close"></a>
    	</div>
		<div class="wj-dialog-body sc2">		
			<div class="w100 mt10" ng-controller="billSale1Ctrl"> 
				<div>
					<%-- 매장정보 --%>
				    <h3>
	         				<s:message code="saleComPopup.bill.storeInfo" />
	       			</h3>       
	      			</div>		
				<div class="wj-TblWrap mt10">					
		        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap">
						<wj-flex-grid
				            autoGenerateColumns="false"
				            selection-mode="Row"
				            items-source="data"
				            control="flex"
				            initialized="initGrid(s,e)"
				            item-formatter="_itemFormatter">
			
				            <!-- define columns -->
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.storeCd"/>" binding="storeCd"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.storeNm"/>" binding="storeNm"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.saleDate"/>"binding="saleDate"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.posNo"/>" 	binding="posNo"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.billNo"/>" 	binding="billNo"	width="*" align="center" ></wj-flex-grid-column>	
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billSale1Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>		
					
			<div class="w100 mt10" ng-controller="billSale2Ctrl"> 
				<%-- 매출종합내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.saleInfo" />
	        		</h3>
	       		</div>	
				<div class="wj-TblWrap mt10">					
		        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap">
						<wj-flex-grid
				            autoGenerateColumns="false"
				            selection-mode="Row"
				            items-source="data"
				            control="flex"
				            initialized="initGrid(s,e)"
				            item-formatter="_itemFormatter">
			
				            <!-- define columns -->
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.totSaleAmt"/>" 	binding="totSaleAmt"	width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.totDcAmt"/>" 	binding="totDcAmt"		width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.realSaleAmt"/>"	binding="realSaleAmt"	width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.netSaleAmt"/>" 	binding="netSaleAmt"	width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.noTexSaleAmt"/>"binding="noTexSaleAmt"	width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.taxSaleAmt"/>"	binding="taxSaleAmt"	width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.vatAmt"/>" 		binding="vatAmt"		width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.totTipAmt"/>" 	binding="totTipAmt"		width="*" align="right" ></wj-flex-grid-column>		
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billSale2Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>		
			
			<div class="w100 mt10" ng-controller="billSale3Ctrl"> 
				<%-- 결제내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.saleAmtInfo" />
	        		</h3>
	       		</div>	
				<div class="wj-TblWrap mt10">					
		        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap">
						<wj-flex-grid
							id="billSale3Grid"
				            autoGenerateColumns="false"
				            selection-mode="Row"
				            items-source="data"
				            control="flex"
				            initialized="initGrid(s,e)"
				            item-formatter="_itemFormatter">		
				            				            				            <!-- define columns -->
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.cornrNm"/>" 	binding="cornrNm"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprProcFg"/>" 	binding="apprProcFg"	width="*" align="center" ></wj-flex-grid-column>	
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billSale3Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
				
			<div class="w100 mt10" ng-controller="billSale4Ctrl"> 
				<%-- 회원정보 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.memberInfo" />
	        		</h3>
	       		</div>	
				<div class="wj-TblWrap mt10">					
		        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap">
						<wj-flex-grid
				            autoGenerateColumns="false"
				            selection-mode="Row"
				            items-source="data"
				            control="flex"
				            initialized="initGrid(s,e)"
				            item-formatter="_itemFormatter">
			
				            <!-- define columns -->
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.memNo"/>" 		binding="membrNo"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.memNm"/>" 		binding="membrNm"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.memCardNo"/>"	binding="membrCardNo"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.memPoint"/>" 	binding="saleSavePoint"	width="*" align="center" ></wj-flex-grid-column>
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billSale4Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
			
			<div class="w100 mt10" id="card" ng-controller="billSale5CardCtrl" style="display: none;"> 
				<%-- 신용카드 결제내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.card" />
	        		</h3>
	       		</div>	
				<div class="wj-TblWrap mt10">					
		        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap">
						<wj-flex-grid
				            autoGenerateColumns="false"
				            selection-mode="Row"
				            items-source="data"
				            control="flex"
				            initialized="initGrid(s,e)"
				            item-formatter="_itemFormatter">
			
				            <!-- define columns -->
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.cornrNm"/>" 	binding="cornrNm"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprProcFg"/>" 	binding="apprProcFg"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.cardNo"/>"		binding="cardNo"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.instCnt"/>" 	binding="instCnt"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprAmt"/>" 	binding="apprAmt"		width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprDt"/>" 		binding="apprDt"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprTime"/>"	binding="apprTime"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprNo"/>" 		binding="apprNo"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.vanCardcoNm"/>" binding="vanCardcoNm"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.issueNm"/>"		binding="issueNm"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.acquireNm"/>" 	binding="acquireNm"		width="*" align="center" ></wj-flex-grid-column>
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billSale5CardCtrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
			
			<div class="w100 mt10" id="cash" ng-controller="billSale5CashCtrl" style="display: none;"> 
				<%-- 현금 결제내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.cash" />
	        		</h3>
	       		</div>	
				<div class="wj-TblWrap mt10">					
		        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap">
						<wj-flex-grid
				            autoGenerateColumns="false"
				            selection-mode="Row"
				            items-source="data"
				            control="flex"
				            initialized="initGrid(s,e)"
				            item-formatter="_itemFormatter">
			
				            <!-- define columns -->
							<wj-flex-grid-column header="<s:message code="saleComPopup.bill.cornrNm"/>" 		binding="cornrNm"			width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprProcFg"/>" 		binding="apprProcFg"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.cashBillCardNo"/>"	binding="cashBillCardNo"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprNo"/>" 			binding="apprNo"			width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprAmt"/>" 		binding="saleAmt"			width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprDt"/>" 			binding="apprDt"			width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprTime"/>" 		binding="apprTime"			width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.vanCardcoNm"/>" 	binding="vanCardcoNm"		width="*" align="center" ></wj-flex-grid-column>
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billSale5CashCtrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
			
			<div class="w100 mt10" ng-controller="billSale6Ctrl"> 
				<%-- 상품내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.prodInfo" />
	        		</h3>
	       		</div>	
				<div class="wj-TblWrap mt10">					
		        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap">
						<wj-flex-grid
				            autoGenerateColumns="false"
				            selection-mode="Row"
				            items-source="data"
				            control="flex"
				            initialized="initGrid(s,e)"
				            item-formatter="_itemFormatter">
			
				            <!-- define columns -->
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.prodCd"/>" 		binding="prodCd"		width="*" align="center" ></wj-flex-grid-column>
							<wj-flex-grid-column header="<s:message code="saleComPopup.bill.prodNm"/>" 		binding="prodNm"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.saleQty"/>" 	binding="saleQty"		width="*" align="right" aggregate="Sum"></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.saleUprc"/>"	binding="saleUprc"		width="*" align="right" aggregate="Sum"></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.totSaleAmt"/>" 	binding="saleAmt"		width="*" align="right" aggregate="Sum"></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.totDcAmt"/>" 	binding="dcAmt"			width="*" align="right" aggregate="Sum"></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.realSaleAmt"/>" binding="realSaleAmt"	width="*" align="right" aggregate="Sum"></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.taxSaleAmt"/>"	binding="taxSaleAmt"	width="*" align="right" aggregate="Sum"></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.vatAmt"/>" 		binding="vatAmt"		width="*" align="right" aggregate="Sum"></wj-flex-grid-column>
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billSale6Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>			
			
			
		</div>							
	</div>
<script type="text/javascript" src="/resource/solbipos/js/sale/com/billSalePop.js?ver=20190207.01" charset="utf-8"></script>
</wj-popup>