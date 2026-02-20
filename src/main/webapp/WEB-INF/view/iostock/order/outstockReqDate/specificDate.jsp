<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/specificDate/"/>


<div id="specificView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="specificCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('specificCtrl',1)"><s:message code="cmm.search"/></button>
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
            <jsp:param name="targetId" value="speSelectStore"/>
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
		text-changed="_checkValidation(s)">
      </wj-combo-box>
        <div class="w400px fl" style="display: none;">
          <p class="tl s14 mt5 ml5 lh15">* 시작시간/마감시간은 요청여부 [가능] 일 경우만 동작합니다.</p>
        </div>
      <%--// 페이지 스케일  --%>
      <c:if test="${orgnFg == 'HQ'}">
        <div class="tr">
          <%-- 신규등록 --%>
          <button class="btn_skyblue" ng-click="newSpecificDate()"><s:message code="cmm.new.add"/></button>
          <%-- 저장 --%>
          <button class="btn_skyblue" ng-click="saveSpecificDate()"><s:message code="cmm.save"/></button>
          <%-- 삭제 --%>
          <button class="btn_skyblue" ng-click="deleteSpecificDate()"><s:message code="cmm.del"/></button>
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
        <c:if test="${orgnFg == 'HQ'}">
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sysStatFg"/>" binding="sysStatFg" width="50" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.specificDate"/>" binding="specificDate" width="100" align="center" format="date" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.specificDateRemark"/>" binding="specificDateRemark" width="*" align="left" is-read-only="false" max-length="100"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.outstockReqYn"/>" binding="outstockReqYn" width="70" align="center" data-map="outstockReqYnMap" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>"   binding="startHour" width="40" align="left" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.startTime"/>"   binding="startMs"   width="40" align="left" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"     binding="endHour"   width="40" align="left" visible="false" data-map="timeHourMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.endTime"/>"     binding="endMs"     width="40" align="left" visible="false" data-map="timeMsMap"></wj-flex-grid-column>
      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="specificCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="specificCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockReqDate/specificDate.js?ver=20260213.01" charset="utf-8"></script>

<%-- 특정일 신규등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/outstockReqDate/specificDateRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
