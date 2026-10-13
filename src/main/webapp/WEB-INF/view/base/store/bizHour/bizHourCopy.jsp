<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/store/bizHour/"/>

<div id="bizHourCopyView" class="subCon" style="display: none;padding: 10px 20px 40px;">
  <div ng-controller="bizHourCopyCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnBizHourCopySearch" ng-click="search();"><s:message code="cmm.search"/></button>
    </div>
    <table class="searchTbl">
      <colgroup>
        <col class="w15"/>
        <col class="w85"/>
      </colgroup>
      <tbody>
        <tr>
          <%-- 원본(복사할 영업시간) 매장 선택 : 단일 --%>
          <th><s:message code="cmm.store.select"/></th>
          <td>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="S"/>
              <jsp:param name="targetId" value="copySrcStore"/>
            </jsp:include>
          </td>
        </tr>
        <tr>
          <%-- 복사할 매장(타겟) 선택 : 다중 --%>
          <th><s:message code="bizHour.copyStore"/></th>
          <td>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="M"/>
              <jsp:param name="targetId" value="copyTgtStore"/>
            </jsp:include>
            <%-- 복사 : 요일별 / 특정일 / 전체 --%>
            <div class="fl ml10">
              <a href="#" class="btn_grayS" ng-click="bizHourCopy('D')">요일별복사</a>
              <a href="#" class="btn_grayS" ng-click="bizHourCopy('S')">특정일복사</a>
              <a href="#" class="btn_grayS" ng-click="bizHourCopy('A')">전체복사</a>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

  <%-- 원본 매장의 요일별 영업시간 (읽기전용) --%>
  <div class="w100 mt10" ng-controller="bizHourCopyDaysCtrl">
    <div class="wj-gridWrap" style="height: 200px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="itemFormatter">

        <wj-flex-grid-column header="<s:message code="bizHour.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.sysStatFg"/>" binding="sysStatFg" width="70" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>

        <%-- 일 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourSun" width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsSun"   width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourSun"   width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsSun"     width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <%-- 월 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourMon" width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsMon"   width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourMon"   width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsMon"     width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <%-- 화 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourTue" width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsTue"   width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourTue"   width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsTue"     width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <%-- 수 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourWed" width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsWed"   width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourWed"   width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsWed"     width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <%-- 목 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourThu" width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsThu"   width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourThu"   width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsThu"     width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <%-- 금 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourFri" width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsFri"   width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourFri"   width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsFri"     width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <%-- 토 --%>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startHourSat" width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startTime"/>" binding="startMsSat"   width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endHourSat"   width="55" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endTime"/>"   binding="endMsSat"     width="55" align="center" data-map="timeMsMap"   is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 원본 매장의 특정일 영업시간 (읽기전용) --%>
  <div class="w100 mt10" ng-controller="bizHourCopySpecificCtrl">
    <div class="wj-gridWrap" style="height: 200px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="itemFormatter">

        <wj-flex-grid-column header="<s:message code="bizHour.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.sysStatFg"/>" binding="sysStatFg" width="70" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.bizDate"/>" binding="bizDate" width="100" align="center" format="date" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.bizFg"/>" binding="bizFg" width="70" align="center" data-map="bizFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startHour"/>" binding="startHour" width="70" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startMin"/>" binding="startMs" width="70" align="center" data-map="timeMsMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endHour"/>" binding="endHour" width="70" align="center" data-map="timeHourMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endMin"/>" binding="endMs" width="70" align="center" data-map="timeMsMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.remark"/>" binding="remark" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/bizHour/bizHourCopy.js?ver=20261013.01" charset="utf-8"></script>
