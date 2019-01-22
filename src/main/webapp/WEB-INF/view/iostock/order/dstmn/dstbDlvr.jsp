<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstmn/dstbDlvr/"/>

<wj-popup id="wjDstbDlvrLayer" control="wjDstbDlvrLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;">
  <div id="dstbDlvrLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbDlvrCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanTitle">분배지시서(기사별) 프린트</span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">
      <div class="tr fr">
        <%-- 인쇄 --%>
        <button type="button" id="btnDlvrReport" class="btn_skyblue" ng-click="dlvrReport('prodStore')">
          상품-매장 인쇄</button>
          <button type="button" id="btnDlvrReport" class="btn_skyblue" ng-click="dlvrReport('storeProd')">
            매장-상품 인쇄</button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dstbDlvr.dlvrCd"/>" binding="dlvrCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dstbDlvr.dlvrNm"/>" binding="dlvrNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstmn/dstbDlvr.js?ver=20190116.01" charset="utf-8"></script>
