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
    <a href="#" class="open">${menuNm}</a>
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
        <div id="storeCd" style="display: none;"></div>
        <input type="text" class="sb-input w100" id="storeCdText" ng-model="storeCds" readonly="readonly" ng-click="searchStore()" />
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

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('prepaidCtrl')"><s:message code="cmm.search" /></button>
  </div>

  <%--위즈모 테이블--%>
  <div class="updownSet oh mb10 mt40">
    <button class="btn_skyblue" id="btnDeposit" style=";" ng-click="charge()">
      <s:message code="prepaid.charge" />
    </button>
  </div>
  <%-- 개발시 높이 조절해서 사용--%>
  <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
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
        <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>--%>
        <wj-flex-grid-column header="<s:message code="prepaid.storeCd"/>" binding="storeCd" width="70"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.storeNm"/>" binding="storeNm" width="100"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.saleDate"/>" binding="saleDate" width="100"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.membrNo"/>" binding="membrNo" width="70" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.membrNm"/>" binding="membrNm" width="100"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidNo"/>" binding="prepaidNo" width="70" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidDt"/>" binding="prepaidDt"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidInFg"/>" binding="prepaidInFg"  data-map="prepaidInFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidAmt"/>" binding="prepaidAmt" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidPayFg"/>" binding="prepaidPayFg"  data-map="prepaidPayFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.saleAmt"/>" binding="saleAmt" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.nonsaleBillNo"/>" binding="nonsaleBillNo" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.orgPrepaidNo"/>" binding="orgPrepaidNo" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.sendYn"/>" binding="sendYn" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.sendDt"/>" binding="sendDt" visible="false"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
</div>

<script type="text/javascript">
  var arrayData = ${ccu.getCommCodeExcpAll("075")};
  var prepaidInFgData = ${ccu.getCommCodeExcpAll("073")};
  var prepaidPayFgData = ${ccu.getCommCodeExcpAll("074")};
  var baseUrl = "${baseUrl}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/prepaid/prepaid.js?ver=20180903.01" charset="utf-8"></script>

<%-- 선불금충전 팝업 --%>
<c:import url="/WEB-INF/view/membr/anals/prepaid/charge.jsp">
</c:import>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/application/layer/store.jsp">
</c:import>
