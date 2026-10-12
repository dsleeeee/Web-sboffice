<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/store/bizHour/days/"/>

<%-- 매장영업시간관리 - 요일별 (매장 전용 : 요일별 한 줄씩, 일~토 기본 표시) --%>
<div id="daysView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="daysCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnDaysSearch" ng-click="_pageView('daysCtrl',1)"><s:message code="cmm.search"/></button>
  </div>

  <%-- 안내문(좌) + 매장영업시간관리 사용여부(우) : 같은 줄 --%>
  <div class="mt10 oh">
    <%-- 매장영업시간관리 사용여부 (매장) : 우측 --%>
    <span class="fr" ng-controller="bizHourUseYnCtrl" ng-init="loadUseYn()">
      <span class="fl s13 ml5 mr5" style="line-height:28px;">매장영업시간관리 사용여부</span>
      <div class="sb-select fl w100px">
        <span class="txtIn">
          <wj-combo-box
                  ng-model="useYn"
                  items-source="useYnList"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false">
          </wj-combo-box>
        </span>
      </div>
      <button class="btn_skyblue fl ml5" ng-click="saveUseYn()"><s:message code="cmm.save"/></button>
    </span>
    <p class="tl s13 ml5 fl" style="color:#888;">* <s:message code="bizHour.days.gridMsg"/></p>
  </div>

  <%-- 그리드 버튼줄 (좌: 매장영업시간관리 사용여부 / 우: 신규등록·저장·삭제) --%>
  <div class="mt10 oh sb-select dkbr">
    <div class="tr">
      <%-- 신규등록 --%>
      <button class="btn_skyblue" ng-click="newDays()"><s:message code="cmm.new.add"/></button>
      <%-- 저장 --%>
      <button class="btn_skyblue" ng-click="saveDays()"><s:message code="cmm.save"/></button>
      <%-- 삭제 --%>
      <button class="btn_skyblue" ng-click="deleteDays()"><s:message code="cmm.del"/></button>
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

        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.sysStatFg"/>" binding="sysStatFg" width="80" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.dayFg"/>" binding="dayFg" width="60" align="center" data-map="dayFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startHour"/>" binding="startHour" width="80" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.startMin"/>" binding="startMs" width="80" align="center" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endHour"/>" binding="endHour" width="80" align="center" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="bizHour.endMin"/>" binding="endMs" width="80" align="center" data-map="timeMsMap"></wj-flex-grid-column>
      </wj-flex-grid>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="daysCtrl"/>
      </jsp:include>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/bizHour/daysStore.js?ver=20261008.06" charset="utf-8"></script>

<%-- 요일별 신규등록 레이어 --%>
<c:import url="/WEB-INF/view/base/store/bizHour/daysRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
