<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeClose/storeClose/"/>

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
          <span class="txtIn"><input id="srchMonth" class="w120px"></span>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

  <div ng-controller="storeCloseCtrl">
    <div class="mt10 pdb20 oh">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="btnSearch" ng-click="searchParam();">
        <s:message code="cmm.search"/></button>
    </div>

    <div class="w40 fl" style="width: 40%">
      <div class="wj-TblWrapBr mr10 pd10" style="height: 400px;">
        <div class="oh sb-select mb10">
          <span class="fl bk lh30"><s:message code='storeClose.closeMonthSubTitle'/></span>
        </div>

        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 335px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storeClose.closeMonth"/>" binding="closeMonth" width="0" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeClose.closeMonthNm"/>" binding="closeMonthNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeClose.dateCnt"/>" binding="dateCnt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeClose.closeDateCnt"/>" binding="closeDateCnt" width="*" align="right" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="storeCloseCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>

  <div class="w60 fr" style="width: 60%" ng-controller="storeCloseDtlCtrl">
    <div class="wj-TblWrapBr pd10" style="height: 400px;">

      <div class="oh sb-select mb10">
        <span class="fl bk lh30"><s:message code='storeClose.closeDateSubTitle'/></span>
        <div class="tr">
          <%-- 저장 --%>
          <button class="btn_skyblue" ng-click="saveStoreClose()">
            <s:message code="cmm.save"/></button>
        </div>
      </div>

      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 335px;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                  binding="gChk"         width="40" align="center"></wj-flex-grid-column>--%>
          <wj-flex-grid-column header="<s:message code="storeClose.closeDate"/>" binding="closeDate" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.closeDateNm"/>" binding="closeDateNm" width="60" align="center" is-read-only="true" format="userFormat"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.orderCloseFg"/>" binding="prevOrderCloseFg" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.orderCloseFg"/>" binding="orderCloseFg" width="80" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.orderCloseDt"/>" binding="orderCloseDt" width="*" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.orderCloseNm"/>" binding="orderCloseNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="storeCloseDtlCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/storeClose/storeClose.js?ver=20181224.01" charset="utf-8"></script>
