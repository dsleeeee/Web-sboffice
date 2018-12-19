<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/membr/anals/prepaid/" />

<div class="subCon" ng-controller="prepaidCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_broadcast('prepaidCtrl')">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w20" />
      <col class="w30" />
    </colgroup>
    <tbody>
    <tr ng-show="orgnFg=='H'">
      <%-- 매장 --%>
      <th><s:message code="prepaid.srchStore" /></th>
      <td>
        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
        <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
          <jsp:param name="targetId" value="store"/>
        </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
      <%-- 회원번호 --%>
      <th><s:message code="prepaid.membrNo" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srcgMembrNo" ng-model="memberNo" />
      </td>
    </tr>
    <tr>
      <%-- 회원명 --%>
      <th><s:message code="prepaid.membrNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="memberNm" />
      </td>
      <%-- 정렬 --%>
      <th><s:message code="prepaid.array" /></th>
      <td>
        <div class="sb-select w100">
          <wj-combo-box
                  id="srchArrayCombo"
                  ng-model="array"
                  items-source="_getComboData('srchArrayCombo')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

  <%--위즈모 테이블--%>
  <div class="updownSet oh mb10 mt20">
    <button class="btn_skyblue" id="btnDeposit" style=";" ng-click="charge()">
      <s:message code="prepaid.charge" />
    </button>
  </div>

  <%-- 페이지 스케일  --%>
  <wj-combo-box
          class="w100px fl"
          id="listScaleBox"
          ng-model="listScale"
          items-source="_getComboData('listScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          is-editable="false"
          initialized="initComboBox(s)"
          ng-hide="true">
  </wj-combo-box>

  <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
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
        <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>--%>
        <wj-flex-grid-column header="<s:message code="prepaid.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.saleDate"/>" binding="saleDate" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.membrNo"/>" binding="membrNo" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.membrNm"/>" binding="membrNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidNo"/>" binding="prepaidNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidDt"/>" binding="prepaidDt" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidInFg"/>" binding="prepaidInFg" data-map="prepaidInFgDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidAmt"/>" binding="prepaidAmt" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidPayFg"/>" binding="prepaidPayFg" data-map="prepaidPayFgDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.saleAmt"/>" binding="saleAmt" is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.nonsaleBillNo"/>" binding="nonsaleBillNo" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.orgPrepaidNo"/>" binding="orgPrepaidNo" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.sendYn"/>" binding="sendYn" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.sendDt"/>" binding="sendDt" visible="false"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="prepaidCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  var arrayData = ${ccu.getCommCodeExcpAll("075")};
  var prepaidInFgData = ${ccu.getCommCodeExcpAll("073")};
  var prepaidPayFgData = ${ccu.getCommCodeExcpAll("074")};
  var baseUrl = "${baseUrl}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/prepaid/prepaid.js?ver=2018090301" charset="utf-8"></script>

<%-- 선불금충전 팝업 --%>
<c:import url="/WEB-INF/view/membr/anals/prepaid/charge.jsp">
</c:import>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/application/layer/store.jsp">
</c:import>
