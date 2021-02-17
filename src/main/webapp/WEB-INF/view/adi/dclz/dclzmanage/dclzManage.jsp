<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="dclzManageCtrl">
  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_pageView('dclzManageCtrl',1)">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

  <table class="searchTbl">
      <colgroup>
        <col class="w15"/>
        <col class="w85"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 조회일자 --%>
        <th><s:message code="cmm.search.date"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 입력 구분 --%>
        <th><s:message code="cmm.input.type" /></th>
        <td>
            <div class="sb-select w150px">
                <wj-combo-box
                        id="inFg"
                        ng-model="inFg"
                        items-source="_getComboData('inFg')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
            </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
            <%-- 매장 --%>
          <th><s:message code="dclzManage.storeNm"/></th>
          <td>
              <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
              <jsp:param name="targetId" value="dclzManageStore"/>
            </jsp:include>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="dclzManageStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>

    <div class="mt20 oh sb-select dkbr">
      <%-- 엑셀 다운로드 --%>
      <button class="btn_skyblue ml5 fr" id="btnExcel" ng-click="excelDownload()">
        <s:message code="cmm.excel.down" />
      </button>

      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <%-- 신규등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnRegist" ng-click="dclzRegist()">
          <s:message code="dclzManage.reg.nm" />
        </button>
      </c:if>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
      <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                is-read-only="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="dclzManage.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.storeNm"/>" binding="storeNm" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.sale.date"/>" binding="saleDate" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.empNo"/>" binding="empNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.empNm"/>" binding="empNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.empInDt"/>" binding="empInDate" width="150" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.empInDt"/>" binding="empInDt" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.empOutDt"/>" binding="empOutDt" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.workTime"/>" binding="workTime" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.inFg"/>" binding="inFg" data-map="inFgDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dclzManage.remark"/>" binding="remark" width="300" is-read-only="true" align="left"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="dclzManageCtrl"/>
        </jsp:include>
      </div>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/dclz/dclzmanage/dclzManage.js?ver=20210209.06" charset="utf-8"></script>

<%-- 근태 상세 팝업 --%>
<c:import url="/WEB-INF/view/adi/dclz/dclzmanage/dclzDetail.jsp">
</c:import>

<c:if test="${sessionInfo.orgnFg == 'STORE'}">
  <%-- 근태 신규등록 팝업 --%>
  <c:import url="/WEB-INF/view/adi/dclz/dclzmanage/dclzRegist.jsp">
  </c:import>
</c:if>
