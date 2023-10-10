<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="monyStatusCtrl" style="padding-bottom: 0;">

    <%--searchTbl--%>
    <div class="searchBar">
      <a href="#" class="fl"><s:message code="status.monyStatus"/></a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue mr3" id="btnSearch" ng-click="_pageView('monyStatusCtrl',1)">
          <s:message code="cmm.search" />
        </button>
      </div>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
          <%-- 조회일자 --%>
          <th><s:message code="cmm.search.date" /></th>
            <td colspan="3">
              <div class="sb-select">
                <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchEndDate" class="w120px"></span>
              </div>
            </td>
        </tr>
        <c:if test="${orgnFg == 'HQ'}">
            <tr>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="monyStatusStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
                <%-- 입출구분 --%>
                <th><s:message code="status.accnt.fg" /></th>
                <td>
                    <div class="sb-select w60">
                        <wj-combo-box
                                ng-model="accntFg"
                                items-source="_getComboData('accntFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="accntFgCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </c:if>
        <c:if test="${orgnFg == 'STORE'}">
            <tr>
                <%-- 입출구분 --%>
                <th><s:message code="status.accnt.fg" /></th>
                <td colspan="3">
                    <%-- 입출구분선택 --%>
                    <div class="sb-select fl mr5 w150px">
                        <wj-combo-box
                                items-source="_getComboData('accntFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="accntFgStoreCombo"
                                selected-index-changed="setAccntFg(s)">
                        </wj-combo-box>
                    </div>
                    <%-- 계정코드 --%>
                    <div class="sb-select fl w250px">
                        <wj-combo-box
                                ng-model="accntCd"
                                items-source="_getComboData('accntCd')"
                                display-member-path="name"
                                selected-value-path="value"
                                control="accntCdCombo"
                                is-editable="false">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
      </table>
    <%--//searchTbl--%>

    <div class="mt20 tr">
        <%-- 엑셀 다운로드 --%>
        <button type="button" class="btn_skyblue ml5" ng-click="excelDownload()">
            <s:message code="cmm.excel.down"/>
        </button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
      <div class="wj-gridWrap" style="height: 370px;">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="status.store.cd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="status.store.nm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="status.sale.date"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="status.pos.no"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="status.accnt.fg"/>" binding="accntFg" data-map="accntFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="status.accnt.nm"/>" binding="accntNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="status.accnt.amt"/>" binding="accntAmt" width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="status.remark"/>" binding="remark" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/mony/status/status.js?ver=20210517.05" charset="utf-8"></script>