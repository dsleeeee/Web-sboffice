<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="empNo" value="${sessionScope.sessionInfo.empNo}" />

<div class="subCon" ng-controller="saleAnalsMonthlyMomsCtrl" ng-init="init()">

	<%-- 조회조건 --%>
	<div class="searchBar">
		<a href="#" class="open fl">${menuNm}</a>
		<div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
			<%-- 조회 --%>
			<button class="btn_blue fr" ng-click="_broadcast('saleAnalsMonthlyMomsCtrl')">
				<s:message code="cmm.search" />
			</button>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<%-- 확장조회 --%>
				<button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
					<s:message code="cmm.search.addShow" />
				</button>
			</c:if>
		</div>
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
			<td>
				<div class="sb-select">
					<span class="txtIn"> <input id="reqYearMonth" name="startDate" class="w110px" /></span>
				</div>
			</td>
			<td></td>
			<td></td>
		</tr>
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<tr>
				<%-- 매장브랜드 --%>
				<th><s:message code="cmm.moms.storeHqBrand"/></th>
				<td>
					<div class="sb-select">
						<wj-combo-box
								id="srchStoreHqBrandCdCombo"
								ng-model="storeHqBrandCd"
								items-source="_getComboData('storeHqBrandCdCombo')"
								display-member-path="name"
								selected-value-path="value"
								is-editable="false"
								control="srchStoreHqBrandCdCombo">
						</wj-combo-box>
					</div>
				</td>
				<%-- 매장선택 --%>
				<th><s:message code="cmm.store.select"/></th>
				<td>
					<%-- 매장선택 모듈 사용시 include --%>
					<jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
						<jsp:param name="targetTypeFg" value="S"/>
						<jsp:param name="targetId" value="saleAnalsMonthlyMomsSelectStore"/>
					</jsp:include>
					<%--// 매장선택 모듈 사용시 include --%>
				</td>
			</tr>
		</c:if>
		<c:if test="${sessionInfo.orgnFg == 'STORE'}">
			<input type="hidden" id="saleAnalsMonthlyMomsSelectStoreCd" value="${sessionInfo.storeCd}"/>
		</c:if>
		</tbody>
	</table>
	<table class="searchTbl" id="tblSearchAddShow" style="display: none;">
		<colgroup>
			<col class="w15"/>
			<col class="w35"/>
			<col class="w15"/>
			<col class="w35"/>
		</colgroup>
		<tbody>
		<tr>
			<%-- 팀별 --%>
			<th><s:message code="cmm.moms.momsTeam"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsTeamCombo"
							ng-model="momsTeam"
							items-source="_getComboData('momsTeamCombo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsTeamCombo">
					</wj-combo-box>
				</div>
			</td>
			<%-- AC점포별 --%>
			<th><s:message code="cmm.moms.momsAcShop"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsAcShopCombo"
							ng-model="momsAcShop"
							items-source="_getComboData('momsAcShopCombo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsAcShopCombo">
					</wj-combo-box>
				</div>
			</td>
		</tr>
		<tr>
			<%-- 지역구분 --%>
			<th><s:message code="cmm.moms.momsAreaFg"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsAreaFgCombo"
							ng-model="momsAreaFg"
							items-source="_getComboData('momsAreaFgCombo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsAreaFgCombo">
					</wj-combo-box>
				</div>
			</td>
			<%-- 상권 --%>
			<th><s:message code="cmm.moms.momsCommercial"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsCommercialCombo"
							ng-model="momsCommercial"
							items-source="_getComboData('momsCommercialCombo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsCommercialCombo">
					</wj-combo-box>
				</div>
			</td>
		</tr>
		<tr>
			<%-- 점포유형 --%>
			<th><s:message code="cmm.moms.momsShopType"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsShopTypeCombo"
							ng-model="momsShopType"
							items-source="_getComboData('momsShopTypeCombo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsShopTypeCombo">
					</wj-combo-box>
				</div>
			</td>
			<%-- 매장관리타입 --%>
			<th><s:message code="cmm.moms.momsStoreManageType"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsStoreManageTypeCombo"
							ng-model="momsStoreManageType"
							items-source="_getComboData('momsStoreManageTypeCombo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsStoreManageTypeCombo">
					</wj-combo-box>
				</div>
			</td>
		</tr>
		<tr>
			<%-- 그룹 --%>
			<th><s:message code="cmm.moms.branch"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchBranchCdCombo"
							ng-model="branchCd"
							items-source="_getComboData('branchCdCombo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchBranchCdCombo">
					</wj-combo-box>
				</div>
			</td>
			<c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
				<%-- 매장그룹 --%>
				<th><s:message code="cmm.moms.momsStoreFg01"/></th>
				<td>
					<div class="sb-select">
						<wj-combo-box
								id="srchMomsStoreFg01Combo"
								ng-model="momsStoreFg01"
								items-source="_getComboData('momsStoreFg01Combo')"
								display-member-path="name"
								selected-value-path="value"
								is-editable="false"
								initialized="_initComboBox(s)"
								control="srchMomsStoreFg01Combo">
						</wj-combo-box>
					</div>
				</td>
			</c:if>
			<c:if test="${sessionScope.sessionInfo.userId != 'ds021' and sessionScope.sessionInfo.userId != 'ds034' and sessionScope.sessionInfo.userId != 'h0393'}">
				<td></td>
				<td></td>
			</c:if>
		</tr>
		<c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
		<tr>
			<%-- 매장그룹2 --%>
			<th><s:message code="cmm.moms.momsStoreFg02"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsStoreFg02Combo"
							ng-model="momsStoreFg02"
							items-source="_getComboData('momsStoreFg02Combo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsStoreFg02Combo">
					</wj-combo-box>
				</div>
			</td>
			<%-- 매장그룹3 --%>
			<th><s:message code="cmm.moms.momsStoreFg03"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsStoreFg03Combo"
							ng-model="momsStoreFg03"
							items-source="_getComboData('momsStoreFg03Combo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsStoreFg03Combo">
					</wj-combo-box>
				</div>
			</td>
		</tr>
		<tr>
			<%-- 매장그룹4 --%>
			<th><s:message code="cmm.moms.momsStoreFg04"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsStoreFg04Combo"
							ng-model="momsStoreFg04"
							items-source="_getComboData('momsStoreFg04Combo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsStoreFg04Combo">
					</wj-combo-box>
				</div>
			</td>
			<%-- 매장그룹5 --%>
			<th><s:message code="cmm.moms.momsStoreFg05"/></th>
			<td>
				<div class="sb-select">
					<wj-combo-box
							id="srchMomsStoreFg05Combo"
							ng-model="momsStoreFg05"
							items-source="_getComboData('momsStoreFg05Combo')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							control="srchMomsStoreFg05Combo">
					</wj-combo-box>
				</div>
			</td>
		</tr>
		</c:if>
	</table>

    <div class="clearfix"></div>

    <!-- 월별판매분석 table start -->
	<div class="tbl-d type2">
		<div class="tbl-tit-btn">
			<div class="txtIn bk lh30">
				<span class="bk" id="year">{{_selectedYear}}</span><s:message code="saleAnalsMonthly.year"/>
				<span class="bk" id="month">{{_selectedMonth}}</span><s:message code="saleAnalsMonthly.month"/>
			</div>
			<%-- 엑셀다운로드 //TODO--%>
			<span class="fr">
				<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>
			</span>
		</div>
		<table id="monthly" name="monthly" class="tbl-cal">
			<colgroup>
				<col class="w11"/>
				<col class="w11"/>
				<col class="w11"/>
				<col class="w11"/>
				<col class="w11"/>
				<col class="w11"/>
				<col class="w11"/>
				<col class="w11"/>
				<col class="w11"/>
			</colgroup>
			<thead>
			<tr>
				<th style="background-color: #d9e5ff;" class="red"><s:message code="saleAnalsMonthly.sun"/></th>
				<th style="background-color: #d9e5ff;"><s:message code="saleAnalsMonthly.mon"/></th>
				<th style="background-color: #d9e5ff;"><s:message code="saleAnalsMonthly.tue"/></th>
				<th style="background-color: #d9e5ff;"><s:message code="saleAnalsMonthly.wed"/></th>
				<th style="background-color: #d9e5ff;"><s:message code="saleAnalsMonthly.thu"/></th>
				<th style="background-color: #d9e5ff;"><s:message code="saleAnalsMonthly.fri"/></th>
				<th style="background-color: #d9e5ff;" class="blue"><s:message code="saleAnalsMonthly.sat"/></th>
				<th style="background-color: #d9e5ff;" colspan="2"><s:message code="saleAnalsMonthly.saleAnal"/></th>
			</tr>
			</thead>
			<tbody ng-bind-html="saleAnalsMonthlyMomsBody">

			</tbody>
		</table>
	</div>
	<!-- // 월별판매분석 table end -->

</div>

<script>
	var statusDataFg  = ${ccu.getCommCode("100")};
	var empNo = "${empNo}";

	$("#btnExcel").click(function(){
		tabletoExcel(document.getElementById("monthly"), '${menuNm}');
	});

	function tabletoExcel(table, name) {
		var uri = 'data:application/vnd.ms-excel;base64,'
			, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
			, base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))); }
			, format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }); };
		if (!table.nodeType) table = document.getElementById(table);
		var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML };
		let a = document.createElement('a');
		a.href = uri + base64(format(template, ctx));
		a.download = name + '_' + $("#year").text()+$("#month").text() + '_' + getCurDateTime() + ".xls";
		a.click();
  	}
</script>

<script type="text/javascript">
	// List 형식("" 안붙임)
	var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
	var branchCdComboList = ${branchCdComboList};
	var momsTeamComboList = ${momsTeamComboList};
	var momsAcShopComboList = ${momsAcShopComboList};
	var momsAreaFgComboList = ${momsAreaFgComboList};
	var momsCommercialComboList = ${momsCommercialComboList};
	var momsShopTypeComboList = ${momsShopTypeComboList};
	var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
	var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
	var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
	var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
	var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
	var momsStoreFg05ComboList = ${momsStoreFg05ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/monthlyMoms/saleAnalsMonthlyMoms.js?ver=20240221.01" charset="utf-8"></script>

<%-- 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/monthlyMoms/saleAnalsMonthlyMomsStore.jsp">
	<c:param name="menuCd" value="${menuCd}"/>
	<c:param name="menuNm" value="${menuNm}"/>
</c:import>