<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<wj-popup id="billRtnPopLayer" control="billRtnPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:1000px;">
	<div class="wj-dialog wj-dialog-columns" ng-controller="billRtnPopCtrl">
    	<div class="wj-dialog-header wj-dialog-header-font">
      		<s:message code="saleComPopup.bill.rtn"/>
      		<span id="spanDtlTitle"></span>
      		<a href="#" class="wj-hide btn_close"></a>
    	</div>
		<div class="wj-dialog-body sc2">		
			<div class="w100 mt10" ng-controller="billRtn1Ctrl"> 
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
				          <jsp:param name="pickerTarget" value="billRtn1Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>		
					
			<div class="w100 mt10" ng-controller="billRtn2Ctrl"> 
				<%-- 매출취소내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.rtnInfo" />
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
				          <jsp:param name="pickerTarget" value="billRtn2Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>		
			
			<div class="w100 mt10" ng-controller="billRtn3Ctrl"> 
				<%-- 결제취소내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.saleRtnInfo" />
	        		</h3>
	       		</div>	
				<div class="wj-TblWrap mt10">					
		        <%--위즈모 테이블--%>
			        <div class="wj-gridWrap">
						<wj-flex-grid
							id="billRtn3Grid"
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
				          <jsp:param name="pickerTarget" value="billRtn3Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
						
			<div class="w100 mt10" id="rtnCard" ng-controller="billRtn5CardCtrl" style="display: none;"> 
				<%-- 신용카드 결제내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.rtnCard" />
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
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprAmt"/>" 	binding="saleAmt"		width="*" align="right" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprDt"/>" 		binding="apprDt"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprTime"/>"	binding="apprTime"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.apprNo"/>" 		binding="apprNo"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.vanCardcoNm"/>" binding="vanCardcoNm"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.issueNm"/>"		binding="issueNm"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.acquireNm"/>" 	binding="acquireNm"		width="*" align="center" ></wj-flex-grid-column>
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billRtn5CardCtrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
			
			<div class="w100 mt10" id="rtnCcash" ng-controller="billRtn5CashCtrl" style="display: none;"> 
				<%-- 현금 결제내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.rtnCash" />0
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
				          <jsp:param name="pickerTarget" value="billRtn5CashCtrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
			
			<div class="w100 mt10" ng-controller="billRtn6Ctrl"> 
				<%-- 상품취소내역 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.rtnProdInfo" />
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
				          <jsp:param name="pickerTarget" value="billRtn6Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
					
			<div class="w100 mt10" ng-controller="billRtn7Ctrl"> 
				<%-- 원거래매출정보 --%>
				<div>
	          		<h3>
	          			<s:message code="saleComPopup.bill.realSaleAmtInfo" />
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
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.realSaleDate"/>"binding="saleDate"	width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.realPosNo"/>" 	binding="posNo"		width="*" align="center" ></wj-flex-grid-column>
				            <wj-flex-grid-column header="<s:message code="saleComPopup.bill.realBillNo"/>" 	binding="billNo"	width="*" align="center" ></wj-flex-grid-column>	
			          	</wj-flex-grid>
			          	<%-- ColumnPicker 사용시 include --%>
				        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				          <jsp:param name="pickerTarget" value="billRtn7Ctrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>			
			
			<div class="w100 mt10" id="realCard" ng-controller="billRealCardCtrl" style="display: none;"> 
				<%-- 신용카드 결제내역 --%>
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
				          <jsp:param name="pickerTarget" value="billRealCardCtrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
			
			<div class="w100 mt10" id="realCash" ng-controller="billRealCashCtrl" style="display: none;"> 
				<%-- 현금 결제내역 --%>
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
				          <jsp:param name="pickerTarget" value="billRealCashCtrl"/>
				        </jsp:include>
	       				<%--// ColumnPicker 사용시 include --%>
			        </div>
			        <%--//위즈모 테이블--%>
				</div>
			</div>	
			
		</div>							
	</div>
<script type="text/javascript" src="/resource/solbipos/js/sale/com/billRtnPop.js?ver=20190207.01" charset="utf-8"></script>
</wj-popup>