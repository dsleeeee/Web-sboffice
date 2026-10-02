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

  <div class="mt10 oh sb-select dkbr">
    <%-- 페이지 스케일 --%>
    <wj-combo-box
      class="w100px fl"
      id="daysListScaleBox"
      ng-model="listScale"
      items-source="_getComboData('listScaleBox')"
      display-member-path="name"
      selected-value-path="value"
      initialized="_initComboBox(s)"
      control="conListScale"
      is-editable="true"
      text-changed="_checkValidation(s)">
    </wj-combo-box>
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

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="daysCtrlPager" data-size="10"></ul>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/bizHour/days.js?ver=20261001.06" charset="utf-8"></script>

<%-- 요일별 신규등록 레이어 --%>
<c:import url="/WEB-INF/view/base/store/bizHour/daysRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
