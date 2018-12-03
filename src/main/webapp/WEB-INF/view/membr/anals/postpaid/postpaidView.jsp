<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/membr/anals/postpaid/" />

<div class="subCon" ng-controller="postpaidCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_broadcast('postpaidCtrl')">
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
      <div id="storeCd" style="display: none;"></div>
      <th><s:message code="postpaid.srchStore" /></th>
      <td>
        <input type="text" class="sb-input w100" id="storeCdText" ng-model="storeCds" readonly="readonly" ng-click="searchStore()" />
      </td>
      <%-- 회원번호 --%>
      <th><s:message code="postpaid.membrNo" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srcgMembrNo" ng-model="membrNo" />
      </td>
    </tr>
    <tr>
      <%-- 회원명 --%>
      <th><s:message code="postpaid.membrNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" />
      </td>
      <th></th>
      <td></td>
      <%-- 정렬 --%>
      <%--
      <th><s:message code="postpaid.array" /></th>
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
      --%>
    </tr>
    </tbody>
  </table>

  <%--위즈모 테이블--%>
  <div class="updownSet oh mb10 mt20">
    <button class="btn_skyblue" id="btnDeposit" style=";" ng-click="deposit()">
      <s:message code="postpaid.deposit" />
    </button>
    <button class="btn_skyblue" id="btnDelRepresent" style="display: none;" ng-click="del()">
      <s:message code="cmm.delete" />
    </button>
    <button class="btn_skyblue" id="btnSaveRepresent" style="display: none;" ng-click="save()">
      <s:message code="cmm.save" />
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

  <%-- 그리드 --%>
  <div class="wj-gridWrap" style="height:315px; overflow-y: hidden;">
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
        <wj-flex-grid-column header="<s:message code="postpaid.hqOfficeCd"/>" binding="hqOfficeCd" width="70" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.storeNm"/>" binding="storeNm" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.membrNo"/>" binding="membrNo" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.membrNm"/>" binding="membrNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.postpaidAmt"/>" binding="postpaidAmt" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.postpaidInAmt"/>" binding="postpaidInAmt" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.postpaidBalAmt"/>" binding="postpaidBalAmt" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.regDt"/>" binding="regDt" is-read-only="true" width="170" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.regId"/>" binding="regId" is-read-only="true" align="center"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="postpaidCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  var postpaidFgData = ${ccu.getCommCodeExcpAll("073")};
  var postpaidPayFgData = ${ccu.getCommCodeExcpAll("074")};
  var baseUrl = "${baseUrl}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/postpaid/postpaid.js?ver=20180903.01" charset="utf-8"></script>

<%-- 외상입금 팝업 --%>
<c:import url="/WEB-INF/view/membr/anals/postpaid/deposit.jsp">
</c:import>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/application/layer/store.jsp">
</c:import>
