<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/reqDateCopy/"/>


<div id="reqDateCopyView" class="subCon" style="display: none;padding: 10px 20px 40px;">
  <div ng-controller="reqDateCopyCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="search();"><s:message code="cmm.search"/></button>
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
        <%-- 매장선택 --%>
        <th><s:message code="cmm.store.select"/></th>
        <td colspan="3">
          <%-- 매장선택 모듈 사용시 include --%>
          <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
            <jsp:param name="targetTypeFg" value="S"/>
            <jsp:param name="targetId" value="targetSelectStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 사용시 include --%>
        </td>
      </tr>
      <tr>
        <%-- 요청일 복사매장선택 --%>
        <th><s:message code="outstockReqDate.copyStore"/></th>
        <td colspan="3">
          <%-- 매장선택 모듈 사용시 include --%>
          <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
            <jsp:param name="targetTypeFg" value="M"/>
            <jsp:param name="targetId" value="copySelectStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 사용시 include --%>
          <%-- 복사 --%>
          <a href="#" class="btn_grayS" ng-click="reqDateCopy()"><s:message code="cmm.save"/></a>
          <%--<button class="btn_blue" id="btnSave" ng-click="reqDateCopy();"><s:message code="cmm.save" /></button>--%>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

  <div class="w100" ng-controller="reqDateCopyDaysCtrl">
    <%--위즈모 테이블--%>
  <div class="w100 mt10">
    <div class="wj-gridWrap" style="height: 120px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="20" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>" binding="storeNm" width="80" align="left" format="cDate" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sysStatFg"/>" binding="sysStatFg" width="50" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.orderCloseYn"/>" binding="orderCloseYn" width="80" align="center" data-map="orderCloseYnMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sun"/>" binding="sun" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.mon"/>" binding="mon" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.tue"/>" binding="tue" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.wed"/>" binding="wed" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.thu"/>" binding="thu" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.fri"/>" binding="fri" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sat"/>" binding="sat" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourSun"  width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsSun"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourSun"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsSun"      width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourMon"  width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsMon"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourMon"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsMon"      width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourTue"  width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsTue"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourTue"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsTue"      width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourWed"  width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsWed"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourWed"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsWed"      width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourThu"  width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsThu"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourThu"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsThu"      width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourFri"  width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsFri"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourFri"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsFri"      width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startHourSat"  width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>" binding="startMsSat"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endHourSat"    width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"   binding="endMsSat"      width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.remark"/>" binding="daysRemark" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="reqDateCopyCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <div class="w100" ng-controller="reqDateCopySpecificCtrl">
    <%--위즈모 테이블--%>
   <div class="w100 mt10">
     <div class="wj-gridWrap" style="height: 300px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flexSpec"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sysStatFg"/>" binding="sysStatFg" width="50" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.specificDate"/>" binding="specificDate" width="100" align="center" format="date" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.specificDateRemark"/>" binding="specificDateRemark" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.outstockReqYn"/>" binding="outstockReqYn" width="70" align="center" data-map="outstockReqYnMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>"   binding="startHour" width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>"   binding="startMs"   width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"     binding="endHour"   width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"     binding="endMs"     width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="reqDateCopyCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockReqDate/reqDateCopy.js?ver=20240724.01" charset="utf-8"></script>


