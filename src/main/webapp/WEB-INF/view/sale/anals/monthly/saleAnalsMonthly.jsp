<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="saleAnalsMonthlyCtrl" ng-init="init()">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <%-- <a href="#" class="open fl">${menuNm}</a> --%>
    <a href="#" class="open fl"><s:message code="saleAnalsMonthly.title"/></a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" ng-click="_broadcast('saleAnalsMonthlyCtrl')"><s:message code="cmm.search" /></button>
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
              <span class="txtIn"> <input id="reqYearMonth" name="startDate" class="w120px" /></span>
            </span>
        </div>
      </td>
    </tr>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
    	<tr>
    		<%-- 매장코드 --%>
			<th><s:message code="todayBillSaleDtl.store"/></th>
			<td>
				<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
					<jsp:param name="targetId" value="saleAnalsMonthlySelectStore"/>
				</jsp:include>
			</td>
		</tr>
	</c:if>
	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
		<input type="hidden" id="saleAnalsMonthlySelectStoreCd" value="${sessionInfo.storeCd}"/>
	</c:if>
    <%-- <tr>
      조회조건
      <th><s:message code="saleAnalsMonthly.reqCondition"/></th>
      <td>
        <div class="sb-select txtIn w110px">
          <wj-combo-box
                  id="srchStatusFgCombo"
                  ng-model="statusFg"
                  items-source="_getComboData('srchStatusFgCombo')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
        <span class="txtIn chk ml10">
		  <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
		  <label for="chkDt">
		    <s:message code="cmm.all.day" />
		  </label>
		</span>
      </td>
    </tr> --%>
    </tbody>
  </table>

    <div class="clearfix"></div>

    <!-- 월별판매분석 table start -->
	<div class="tbl-d type2">
	  <div class="tbl-tit-btn">
		  <div class="txtIn bk lh30">
		  	<span class="bk">{{_selectedYear}}</span><s:message code="saleAnalsMonthly.year"/>
		  	<span class="bk">{{_selectedMonth}}</span><s:message code="saleAnalsMonthly.month"/>
		  </div>
		  <%-- 엑셀다운로드 //TODO--%>
		  <span class="fr">
		  	<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>
		  </span>
	  </div>

	  <table class="tbl-cal">
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
	      <th class="red"><s:message code="saleAnalsMonthly.sun"/></th>
	      <th><s:message code="saleAnalsMonthly.mon"/></th>
	      <th><s:message code="saleAnalsMonthly.tue"/></th>
	      <th><s:message code="saleAnalsMonthly.wed"/></th>
	      <th><s:message code="saleAnalsMonthly.thu"/></th>
	      <th><s:message code="saleAnalsMonthly.fri"/></th>
	      <th class="blue"><s:message code="saleAnalsMonthly.sat"/></th>
	      <th colspan="2"><s:message code="saleAnalsMonthly.saleAnal"/></th>
	    </tr>
	    </thead>
	    <tbody ng-bind-html="saleAnalsMonthlyBody">

	    </tbody>
	  </table>
	</div>
	<!-- // 월별판매분석 table end -->

</div>

<script>
  var statusDataFg  = ${ccu.getCommCode("100")};
  
  
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/monthly/saleAnalsMonthly.js?ver=20200116.01" charset="utf-8"></script>


<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/monthly/monthlyPop/saleAnalsMonthlyPayFg.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>