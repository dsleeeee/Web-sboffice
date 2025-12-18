<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="stockAdjustCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>

        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('stockAdjustCtrl', 1)">
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
            <%-- 조정일자 --%>
            <th><s:message code="stockAdjust.adjDate"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                </div>
            </td>
            <%-- 진행 --%>
            <th><s:message code="stockAdjust.procFg"/></th>
            <td>
                <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
                    id="srchProcFg"
                    ng-model="procFg"
                    items-source="_getComboData('srchProcFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 조정등록 --%>
            <th><s:message code="stockAdjust.adjRegist"/></th>
            <td>
                <div class="sb-select fl mr10">
                    <span class="txtIn"><input id="adjDate" class="w110px" ng-model="adjDate"></span>
                </div>
                <a href="#" class="btn_grayS" ng-click="newAdj()"><s:message code="stockAdjust.adjRegist"/></a>
            </td>
            <%-- 사유 --%>
            <th><s:message code="stockAdjust.adjReason"/></th>
            <td>
      <span class="txtIn w150px sb-select fl mr5">
         <wj-combo-box
                 id="adjReason"
                 ng-model="adjReason"
                 items-source="_getComboData('adjReason')"
                 display-member-path="name"
                 selected-value-path="value"
                 is-editable="false"
                 initialized="_initComboBox(s)">
         </wj-combo-box>
      </span>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10">
        <div class="updownSet oh">
            <span class="tl s14 mt5 lh15 red">* 확정된 데이터는 삭제 하실 수 없습니다.</span>
            <%-- 삭제 --%>
            <button type="button" class="btn_skyblue ml5 fr" id="btnDelete" ng-click="deleteAdj()">
                <s:message code="cmm.delete"/>
            </button>
        </div>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-x: hidden; overflow-y: hidden;">
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
                <wj-flex-grid-column header="<s:message code="stockAdjust.adjDate"/>" binding="adjDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.seqNo"/>" binding="seqNo" width="40" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.procFg"/>" binding="procFg" width="40" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.adjTitle"/>" binding="adjTitle" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.adjReason"/>" binding="adjReason" width="80" align="left" is-read-only="true" data-map="adjReasonDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.dtlCnt"/>" binding="dtlCnt" width="50" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.regDate"/>" binding="regDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.regId"/>" binding="regId" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.modDate"/>" binding="modDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.modId"/>" binding="modId" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.confmDate"/>" binding="confmDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.confmId"/>" binding="confmId" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockAdjust.adjStorageCd"/>" binding="adjStorageCd" width="0" align="center" is-read-only="true" format="date"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="stockAdjustCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="stockAdjustCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script>
    var reasonData =  ${reasonData};
</script>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/stock/stockAdjust/stockAdjust.js?ver=20251216.01" charset="utf-8"></script>

<%-- 조정 상세 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/stock/stockAdjust/stockAdjustDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 조정 등록 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/stock/stockAdjust/stockAdjustRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
