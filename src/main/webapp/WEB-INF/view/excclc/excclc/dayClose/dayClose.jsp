<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeNm" value="${sessionScope.sessionInfo.storeNm}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="dayCloseCtrl">
  <div class="searchBar ">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dayCloseCtrl')">
      <s:message code="cmm.search" />
    </button>
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
          <div class="sb-select">
            <span class="txtIn"> <input id="closeMonth" name="startDate" class="w110px" /></span>
          </div>
        </div>
      </td>
      <th><s:message code="dayClose.closeFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
              <wj-combo-box
                      id="closeFg"
                      ng-model="closeFg"
                      control="picDateCombo"
                      items-source="_getComboData('closeFg')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
              </wj-combo-box>
          </span>
        </div>
      </td>
    </tr>
    <c:if test="${orgnFg != 'STORE'}">
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="month.store"/></th>
        <td>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
          <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
            <jsp:param name="targetId" value="dayCloseStore"/>
          </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      </tr>
    </c:if>
    </tbody>
  </table>

  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter"
        frozen-columns="5">

        <!-- define columns -->

        <wj-flex-grid-column header="<s:message code="dayClose.storeCd"/>"      binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.storeNm"/>"      binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.closeDate"/>"    binding="closeDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.openDate"/>"     binding="openDate" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.closeFg"/>"      binding="closeFg" width="80" align="center" data-map="closeFgDataMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.interestAmt"/>"  binding="interestAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.inAmt"/>"        binding="inAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.outAmt"/>"       binding="outAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.groupAmt"/>"     binding="groupAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.hockeyAmt"/>"    binding="hockeyAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.etcAmt"/>"       binding="etcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.inDayAmt"/>"     binding="inDayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.inCardAmt"/>"    binding="inCardAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.inSum"/>"        binding="inSum" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.inMonthSum"/>"   binding="inMonthSum" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.inBMonthSum"/>"  binding="inBMonthSum" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.inTotalSum"/>"   binding="inTotalSum" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.outSum"/>"       binding="outSum" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.outMonthSum"/>"  binding="outMonthSum" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.outBMonthSum"/>" binding="outBMonthSum" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.outTotalSum"/>"  binding="outTotalSum" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.remark1"/>"      binding="remark1" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.remark2"/>"      binding="remark2" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.remark3"/>"      binding="remark3" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.remark4"/>"      binding="remark4" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.remark5"/>"      binding="remark5" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayClose.remark6"/>"      binding="remark6" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script>
  var orgnFg = "${orgnFg}";
  var storeNm = "${storeNm}";
  var storeCd = "${storeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/dayClose/dayClose.js?ver=20220907.01" charset="utf-8"></script>

<c:import url="/WEB-INF/view/excclc/excclc/dayClose/dayCloseDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>