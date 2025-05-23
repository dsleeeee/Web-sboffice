<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayDc/"/>

<div id="dayDcView" name="dayView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="dayDcCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl"><s:message code="day.dc"/></a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dayDcCtrl')">
      <s:message code="cmm.search"/>
    </button>
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
      <%-- 조회일자 --%>
      <th><s:message code="cmm.search.date"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchDcStartDate" class="w110px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchDcEndDate" class="w110px"></span>
        </div>
      </td>
    </tr>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
      <tr>
        <%-- 매장선택 --%>
        <th><s:message code="cmm.store.select"/></th>
        <td colspan="3">
          <%-- 매장선택 모듈 사용시 include --%>
          <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
            <jsp:param name="targetTypeFg" value="M"/>
            <jsp:param name="targetId" value="dayDcSelectStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 사용시 include --%>
        </td>
      </tr>
    </c:if>
    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
      <input type="hidden" id="dayDcSelectStoreCd" value="${sessionInfo.storeCd}"/>
    </c:if>
    </tbody>
  </table>
  <div style="clear: both;"></div>

  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="day.dayDc.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="day.dayDc.yoil"/>" binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="day.dayDc.storeCnt"/>" binding="storeCnt" width="80" align="center" is-read-only="true" ng-if="orgnFg == 'H'"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="day.dayDc.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="day.dayDc.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <%-- 할인 컬럼 생성--%>
        <c:forEach var="dcCol" items="${dcColList}">
          <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </c:forEach>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="dayDcCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<%--<script type="text/javascript">
  var dcColList = [];
  &lt;%&ndash;javascript에서 사용할 할인 json 데이터 생성&ndash;%&gt;
  <c:forEach var="dcCol" items="${dcColList}">
  var dcParam      = {};
  dcParam.dcCd     = "${dcCol.dcCd}";
  dcParam.dcMethod = "${dcCol.dcMethod}";
  dcColList.push(dcParam);
  </c:forEach>

  var dcCol    = '${dcCol}';
  var arrDcCol = dcCol.split(',');
</script>--%>
<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayDc.js?ver=20250415.01" charset="utf-8"></script>

