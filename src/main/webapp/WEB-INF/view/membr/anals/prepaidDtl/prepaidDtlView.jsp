<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="prepaidDtlCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_pageView('prepaidDtlCtrl',1)">
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
    <tr ng-show="orgnFg=='H'">
      <%-- 매장 --%>
      <th><s:message code="prepaidDtl.srchStore" /></th>
      <td>
        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
        <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
          <jsp:param name="targetId" value="store"/>
        </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
      <%-- 회원번호 --%>
      <th><s:message code="prepaidDtl.membrNo" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srcgMembrNo" ng-model="membrNo" />
      </td>
    </tr>
    <tr>
      <%-- 회원명 --%>
      <th><s:message code="prepaidDtl.membrNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" />
      </td>
      <%-- 조회일자 --%>
      <th><s:message code="prepaidDtl.srchDate" /></th>
      <td>
        <div class="sb-select">
          <span class="txtIn"> <input id="startDate" class="w200px" /></span>
          <span class="rg">~</span>
          <span class="txtIn"> <input id="endDate" class="w200px" /></span>
        </div>
      </td>
    </tr>
    </tbody>
  </table>


  <div class="mt40 oh sb-select dkbr">
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
          <wj-flex-grid-column header="<s:message code="prepaidDtl.hqOfficeCd"/>" binding="hqOfficeCd" width="*" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaidDtl.storeCd"/>" binding="storeCd" width="130" is-read-only="true" align="center" visible="{{orgnFg == 'H'}}"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaidDtl.storeNm"/>" binding="storeNm" width="180" is-read-only="true" align="center" visible="{{orgnFg == 'H'}}"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaidDtl.membrNo"/>" binding="membrNo" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaidDtl.membrNm"/>" binding="membrNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaidDtl.saleDate"/>" binding="saleDate" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaidDtl.prepaidFg"/>" binding="prepaidFg" data-map="prepaidFgDataMap" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaidDtl.prepaidAmt"/>" binding="prepaidAmt" width="150" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaidDtl.prepaidPayFg"/>" binding="prepaidPayFgNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="prepaidDtlCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  // 선불구분
  var prepaidFgData = ${ccu.getCommCodeExcpAll("098")};
  //var baseUrl = "${baseUrl}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/prepaidDtl/prepaidDtl.js?ver=2019052506" charset="utf-8"></script>


