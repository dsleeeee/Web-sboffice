<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/base/store/bizHour/days/"/>

<div id="daysView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="daysCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnDaysSearch" ng-click="_pageView('daysCtrl',1)"><s:message code="cmm.search"/></button>
  </div>
  <c:if test="${orgnFg == 'HQ'}">
    <table class="searchTbl">
      <colgroup>
        <col class="w15"/>
        <col class="w35"/>
        <col class="w15"/>
        <col class="w35"/>
      </colgroup>
      <tbody>
        <tr>
          <%-- 매장선택 --%>
          <th><s:message code="cmm.store.select"/></th>
          <td>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="M"/>
              <jsp:param name="targetId" value="daysSelectStore"/>
            </jsp:include>
          </td>
        </tr>
      </tbody>
    </table>
  </c:if>

  <%-- 안내문 --%>
  <div class="mt10">
    <p class="tl s13 ml5" style="color:#888;">* <s:message code="bizHour.days.gridMsg"/></p>
  </div>

  <div class="mt10 oh sb-select dkbr">
    <div class="tr">
      <%-- 매장영업시간관리 사용여부 (본사) --%>
      <button class="btn_skyblue" ng-click="_broadcast('bizHourUseYnPopCtrl')">매장영업시간관리 사용여부</button>
      <%-- 신규등록 --%>
      <button class="btn_skyblue" ng-click="newDays()"><s:message code="cmm.new.add"/></button>
      <%-- 저장 --%>
      <button class="btn_skyblue" ng-click="saveDays()"><s:message code="cmm.save"/></button>
    </div>
  </div>

  <%-- 위즈모 테이블 --%>
  <div class="w100 mt10">
    <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="itemFormatter"
        ime-enabled="true">

        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.sysStatFg"/>" binding="sysStatFg" width="70" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>

        <%-- 일 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourSun" width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsSun"   width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourSun"   width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsSun"     width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <%-- 월 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourMon" width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsMon"   width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourMon"   width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsMon"     width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <%-- 화 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourTue" width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsTue"   width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourTue"   width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsTue"     width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <%-- 수 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourWed" width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsWed"   width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourWed"   width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsWed"     width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <%-- 목 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourThu" width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsThu"   width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourThu"   width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsThu"     width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <%-- 금 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourFri" width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsFri"   width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourFri"   width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsFri"     width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <%-- 토 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourSat" width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsSat"   width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourSat"   width="55" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsSat"     width="55" align="center" data-map="timeMsMap"></wj-flex-grid-column>
      </wj-flex-grid>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="daysCtrl"/>
      </jsp:include>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/bizHour/days.js?ver=20261008.05" charset="utf-8"></script>

<%-- 요일별 신규등록 레이어 --%>
<c:import url="/WEB-INF/view/base/store/bizHour/daysRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
