<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<%--
<div id="configView" class="subCon" ng-controller="configCtrl">
--%>
<div id="configView" class="subCon">

	<%--
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
    </div>
	--%>



    <div id="gridRepresent" class="w50 fl" style="width:40%" ng-controller="configCtrl_1">
        <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
	        <div class="updownSet oh mb10">
		        <span class="fl bk lh30"><s:message code='dailyReport.cfgPayLine' /></span>	<%-- 결재라인 --%>
		        <button class="btn_skyblue" style="display: none;" id="btnAdd" 	ng-click="addRow()"		><s:message code="cmm.add" 		/></button>	<%-- style="display: none;"	none block inline --%>
		        <button class="btn_skyblue" style="display: none;" id="btnDel" 	ng-click="deleteRow()"	><s:message code="cmm.delete" 	/></button>
		        <button class="btn_skyblue" style="display: none;" id="btnSave"	ng-click="save()"		><s:message code="cmm.save" 	/></button>
			</div>

		    <div class="wj-gridWrap" style="height:315px">	<%-- 개발시 높이 조절해서 사용   &   tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어서 사용 --%>
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
						<wj-flex-grid-column header="<s:message code="dailyReport.cfgPayLineNm"  	/>"		binding="cfgPayLineNm"			width="173"	is-read-only="false" 	align="left"	max-length=10	></wj-flex-grid-column>

						<wj-flex-grid-column header=""														binding="cfgHqOfficeCd"			width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgHqBrandCd"      	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgStoreCd"        	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgPayLineNo"			width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgUseYn"          	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
						<wj-flex-grid-column header=""														binding="cfgStatus"          	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
			        </wj-flex-grid>

			        <%-- ColumnPicker 사용시 include --%>
			        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
			        	<jsp:param name="pickerTarget" value="configCtrl_1"/>
			        </jsp:include>
		        </div>
	        </div>
        </div>
    </div>



    <div id="gridDetail" class="w50 fr" style="width:60%" ng-controller="configCtrl_2">
        <div class="wj-TblWrapBr ml10 pd20" style="height: 700px;">
	        <div class="updownSet oh mb10">
	        <span class="fl bk lh30"><s:message code='dailyReport.cfgNm' /></span>	<%-- 영업일보 구성내역 --%>
		        <button class="btn_skyblue" style="display: none;" id="btnSave2"	ng-click="save()"		><s:message code="cmm.save" 	/></button>	<%-- style="display: none;"	none block inline --%>
        	</div>

        	<div class="wj-gridWrap" style="height:680px">	<%-- 개발시 높이 조절해서 사용   &   tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어서 사용 --%>
		        <wj-flex-grid	autoGenerateColumns	="false"
						        control				="flex"
						        initialized			="initGrid(s,e)"
						        sticky-headers		="true"
						        selection-mode		="Row"
						        items-source		="data"
						        item-formatter		="_itemFormatter"
						        sorted-column		="toggleFreeze(false)">
						        <%--
						        is-read-only		="true"
						        frozen-columns		="2"
						        --%>
					<wj-flex-grid-column header="<s:message code="dailyReport.cfgSaleDailyReportNm"	/>"	binding="cfgSaleDailyReportNm"	width="350"	is-read-only="true"		align="left"	                ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dailyReport.cfgChk"				/>"	binding="gChk" 					width="40"	is-read-only="false"	align="center"	                ></wj-flex-grid-column>

					<wj-flex-grid-column header=""														binding="cfgHqOfficeCd"			width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
					<wj-flex-grid-column header=""														binding="cfgHqBrandCd"      	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
					<wj-flex-grid-column header=""														binding="cfgStoreCd"        	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
					<wj-flex-grid-column header=""														binding="cfgSaleDailyReportCd" 	width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dailyReport.cfgSelYn"				/>"	binding="cfgSelYn"				width="50"	is-read-only="true" 	align="center"	visible="false"	></wj-flex-grid-column>
		        </wj-flex-grid>

		        <%-- ColumnPicker 사용시 include --%>
		        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
		        	<jsp:param name="pickerTarget" value="configCtrl_2"/>
		        </jsp:include>
	        </div>
        </div>
    </div>





</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/dailyReport/config/config.js?ver=20200206.00" charset="utf-8"></script>
