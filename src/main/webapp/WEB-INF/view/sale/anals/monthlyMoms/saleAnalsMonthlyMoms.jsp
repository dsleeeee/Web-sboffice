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
  <div class="searchBar flddUnfld">
     <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" ng-click="_broadcast('saleAnalsMonthlyMomsCtrl')"><s:message code="cmm.search" /></button>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w30" />
      <col class="w70" />
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회월 --%>
      <th><s:message code="cmm.search.month"/></th>
      <td>
        <div class="sb-select">
              <span class="txtIn"> <input id="reqYearMonth" name="startDate" class="w110px" /></span>
            </span>
        </div>
      </td>
    </tr>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
    	<tr>
    		<%-- 매장코드 --%>
			<th><s:message code="todayBillSaleDtl.store"/></th>
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
  var empNo = "${empNo}"

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

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/monthlyMoms/saleAnalsMonthlyMoms.js?ver=20230413.01" charset="utf-8"></script>

<%-- 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/monthlyMoms/saleAnalsMonthlyMomsStore.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>