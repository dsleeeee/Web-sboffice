<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayStoreDc/"/>

<wj-popup id="wjDayStoreDcLayer" control="wjDayStoreDcLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dayStoreDcLayer" class="wj-dialog wj-dialog-columns" ng-controller="dayStoreDcCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="day.dayStoreDc.dayStoreDc"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="day.dayStoreDc.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="day.dayStoreDc.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="day.dayStoreDc.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <%-- 할인 컬럼 생성--%>
            <c:forEach var="dcCol" items="${dcColList}">
              <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </c:forEach>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayStoreDc.js?ver=20190220.01" charset="utf-8"></script>
