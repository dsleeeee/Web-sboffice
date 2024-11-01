<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

  <div ng-controller="postpaidDtlCtrl">
    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" ng-click="_pageView('postpaidDtlCtrl',1)" id="nxBtnSearch">
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
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="startDate" class="w110px" /></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="endDate" class="w110px" /></span>
          </div>
        </td>
        <td></td>
        <td></td>
      </tr>
      <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
          <%-- 매장 --%>
          <th><s:message code="postpaidDtl.srchStore" /></th>
          <td>
              <%-- [NXPOS-1648,1699] 매장선택 모듈 통합 / 추후작업예정 --%>
              <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
              <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
                  <jsp:param name="targetId" value="store"/>
              </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
          <td></td>
          <td></td>
      </tr>
      <tr>
        <%-- 회원번호 --%>
        <th><s:message code="postpaidDtl.membrNo" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srcgMembrNo" ng-model="membrNo" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 회원명 --%>
        <th><s:message code="postpaidDtl.membrNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" onkeyup="fnNxBtnSearch();"/>
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
          is-editable="false"
          initialized="initComboBox(s)">
      </wj-combo-box>
      <%--// 페이지 스케일  --%>
      <%-- 조회조건 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
      <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
        <div class="row">
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
            <wj-flex-grid-column header="<s:message code="postpaidDtl.storeCd"/>" binding="storeCd" width="80" is-read-only="true" visible="{{orgnFg == 'H'}}" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.storeNm"/>" binding="storeNm" width="100" is-read-only="true" visible="{{orgnFg == 'H'}}" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.membrNo"/>" binding="membrNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.membrNm"/>" binding="membrNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.saleDate"/>" binding="saleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidFg"/>" binding="postpaidFg" data-map="postpaidFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidAmt"/>" binding="postpaidAmt" width="80" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.depositAmt"/>" binding="depositAmt" width="80" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidPayFg"/>" binding="postpaidPayFgNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidDt"/>" binding="postpaidDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>

            <wj-flex-grid-column header="<s:message code="postpaidDtl.hqOfficeCd"/>" binding="hqOfficeCd" width="100" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidPayFg"/>" binding="postpaidPayFg" width="100" visible="false"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
      <%-- id --%>
      <ul id="postpaidDtlCtrlPager" data-size="10">
      </ul>
    </div>
    <%--//페이지 리스트--%>
  </div>

  <div ng-controller="postpaidDtlExcelCtrl">
    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden; display: none;">
      <div class="row">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="excelFlex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                is-read-only="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="postpaidDtl.storeCd"/>" binding="storeCd" width="80" is-read-only="true" visible="{{orgnFg == 'H'}}" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.storeNm"/>" binding="storeNm" width="100" is-read-only="true" visible="{{orgnFg == 'H'}}" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.membrNo"/>" binding="membrNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.membrNm"/>" binding="membrNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.saleDate"/>" binding="saleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidFg"/>" binding="postpaidFg" data-map="postpaidFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidAmt"/>" binding="postpaidAmt" width="80" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.depositAmt"/>" binding="depositAmt" width="80" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidPayFg"/>" binding="postpaidPayFgNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidDt"/>" binding="postpaidDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="postpaidDtl.hqOfficeCd"/>" binding="hqOfficeCd" width="100" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaidDtl.postpaidPayFg"/>" binding="postpaidPayFg" width="100" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
  </div>

</div>

<script type="text/javascript">
  //후불구분
  var postpaidFgData = ${ccu.getCommCodeExcpAll("073")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/postpaidDtl/postpaidDtl.js?ver=20231121.01" charset="utf-8"></script>


