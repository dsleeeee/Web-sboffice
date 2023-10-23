<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/days/"/>


<div id="daysView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="daysCtrl">
  <div class="searchBar flddUnfld">
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
      <%--// 페이지 스케일  --%>
      <div class="tr">
        <%-- 신규등록 --%>
        <button class="btn_skyblue" ng-click="saveDays()"><s:message code="cmm.save"/></button>
      </div>
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
        <wj-flex-grid-column header="<s:message code="outstockReqDate.remark"/>" binding="daysRemark" width="*" align="left" is-read-only="false"></wj-flex-grid-column>

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

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockReqDate/days.js?ver=20181224.01" charset="utf-8"></script>


