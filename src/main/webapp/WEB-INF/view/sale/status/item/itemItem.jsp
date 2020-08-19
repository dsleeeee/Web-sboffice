<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div id="itemItemView" class="subCon" ng-controller="itemItemCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl">${menuNm}</a>
		<%-- 조회 --%>
		<div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
			<button class="btn_blue fr" id="btnSearch" ng-click="getItemItemList()">
				<s:message code="cmm.search" />
			</button>
		</div>
	</div>

	<div class="mt20 mb20 oh sb-select dkbr">
		<div class="tr">
			<%-- 사원신규등록 --%>
			<button class="btn_skyblue" id="btnSave" ng-click="save()">
				<s:message code='cmm.save' />
			</button>
		</div>
	</div>

	<%-- 매출항목표시 그리드 --%>
	<div class="w100 mt10 mb20">
		<div class="wj-gridWrap" style="width:450px; height:380px; overflow-x: hidden; overflow-y: hidden;">
			<wj-flex-grid
				id="itemItemGrid"
				autoGenerateColumns="false"
				selection-mode="Row"
				items-source="data"
				control="flex"
				initialized="initGrid(s,e)"
				sticky-headers="true"
				item-formatter="_itemFormatter">
				<!-- define columns -->
				<wj-flex-grid-column header="<s:message code="item.showHdNm"/>" binding="busiFg" align="center" width="1" is-read-only="true" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="item.showHdNm"/>" binding="orgnFg" align="center" width="1" is-read-only="true" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="item.showHdNm"/>" binding="showHdNm" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="item.showYn"/>" binding="showYn" format="checkBox" align="center" width="100"></wj-flex-grid-column>
			</wj-flex-grid>
		</div>
	</div>

	<input type="hidden" id="itemSelectOrgnFg" value="${sessionInfo.orgnFg}"/>
	<input type="hidden" id="itemSelectStoreCd" value="${sessionInfo.storeCd}"/>

	<%-- 페이지 리스트 --%>
	<%-- <div class="pageNum mt20">
		id
		<ul id="itemItemCtrlPager" data-size="10">
		</ul>
	</div> --%>
	<%--//페이지 리스트--%>
</div>

<script type="text/javascript">
	var useYn = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/item/itemItem.js?ver=2018082901.01" charset="utf-8"></script>



