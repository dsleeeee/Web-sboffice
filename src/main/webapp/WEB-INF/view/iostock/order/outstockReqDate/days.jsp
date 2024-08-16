<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/days/"/>


<div id="daysView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="daysCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('daysCtrl',1)"><s:message code="cmm.search"/></button>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15"/>
      <col class="w35"/>
      <col class="w15"/>
      <col class="w35"/>
    </colgroup>
    <tbody>
    <c:if test="${orgnFg == 'HQ'}">
      <tr>
        <%-- 매장선택 --%>
        <th><s:message code="cmm.store.select"/></th>
        <td>
          <%-- 매장선택 모듈 사용시 include --%>
          <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
            <jsp:param name="targetTypeFg" value="M"/>
            <jsp:param name="targetId" value="daysSelectStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 사용시 include --%>
        </td>
      </tr>
    </c:if>
    </tbody>
  </table>

  <div class="mt10 oh sb-select dkbr">
      <%-- 페이지 스케일  --%>
      <wj-combo-box
        class="w100px fl"
        id="listScaleBox"
        ng-model="listScale"
        items-source="_getComboData('listScaleBox')"
        display-member-path="name"
        selected-value-path="value"
        initialized="_initComboBox(s)"
        control="conListScale"
		is-editable="true"
		text-changed="_checkValidation(s)"
        <c:if test="${orgnFg != 'HQ'}">
        style="visibility:hidden"
        </c:if>
      >
      </wj-combo-box>
      <%--// 페이지 스케일  --%>

      <c:if test="${orgnFg == 'HQ'}">
        <div class="tr">
          <%-- 신규등록 --%>
          <button class="btn_skyblue" ng-click="saveDays()"><s:message code="cmm.save"/></button>
        </div>
      </c:if>
    </div>

    <%--위즈모 테이블--%>
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

        <!-- define columns -->
        <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>--%>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>" binding="storeNm" width="*" align="left" format="cDate" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sysStatFg"/>" binding="sysStatFg" width="50" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.orderCloseYn"/>" binding="orderCloseYn" width="80" align="center" data-map="orderCloseYnMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sun"/>" binding="sun" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.mon"/>" binding="mon" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.tue"/>" binding="tue" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.wed"/>" binding="wed" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.thu"/>" binding="thu" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.fri"/>" binding="fri" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sat"/>" binding="sat" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourSun"  width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsSun"    width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourSun"    width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsSun"      width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourMon"  width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsMon"    width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourMon"    width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsMon"      width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourTue"  width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsTue"    width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourTue"    width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsTue"      width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourWed"  width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsWed"    width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourWed"    width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsWed"      width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourThu"  width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsThu"    width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourThu"    width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsThu"      width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourFri"  width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsFri"    width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourFri"    width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsFri"      width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourSat"  width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsSat"    width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourSat"    width="40" align="left" is-read-only="false" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsSat"      width="40" align="left" is-read-only="false" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.remark"/>" binding="daysRemark" width="80" align="left" is-read-only="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="daysCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="daysCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockReqDate/days.js?ver=20240805.01" charset="utf-8"></script>


