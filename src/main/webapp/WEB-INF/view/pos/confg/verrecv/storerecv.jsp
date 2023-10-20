<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<%--<c:set var="baseUrl" value="/pos/confg/verRecv/storeRecv/"/>--%>

<div class="subCon" ng-controller="storeRecvCtrl" id="storeRecvView" style="padding: 10px 20px 40px">
  <div class="searchBar">
    <a href="#" class="open fl">
      <c:if test="${orgnFg != 'HQ'}">${menuNm}</c:if>
      <c:if test="${orgnFg == 'HQ'}"><s:message code="verHq.storeRecv" /></c:if>
  </a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch2" onclick="getStoreList()">
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
      <tr <c:if test="${orgnFg == 'HQ'}"> style="display:none"</c:if>>
        <%-- 본사코드 --%>
        <th><s:message code="verRecv.hqOfficeCd" /></th>
        <td>
          <input type="text" id="hqOfficeCd" name="hqOfficeCd" ng-model="hqOfficeCd" class="sb-input w100" size="50" <c:if test="${orgnFg == 'HQ'}"> value="A0001"</c:if> onkeyup="fnNxBtnSearch('2');">
        </td>
        <%-- 본사명 --%>
        <th><s:message code="verRecv.hqOfficeNm" /></th>
        <td>
          <input type="text" id="hqOfficeNm" name="hqOfficeNm" ng-model="hqOfficeNm" class="sb-input w100" size="50" onkeyup="fnNxBtnSearch('2');">
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="verRecv.storeCd" /></th>
        <td>
          <input type="text" id="storeCd" name="storeCd" ng-model="storeCd" class="sb-input w100" size="50" onkeyup="fnNxBtnSearch('2');">
        </td>
        <%-- 매장명 --%>
        <th><s:message code="verRecv.storeNm" /></th>
        <td>
          <input type="text" id="storeNm" name="storeNm" ng-model="storeNm" class="sb-input w100" size="50" onkeyup="fnNxBtnSearch('2');">
        </td>
      </tr>
      <tr>
        <%-- 프로그램구분 --%>
        <th><s:message code="verRecv.progFg"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="progFg2"
              ng-model="progFg2"
              control="progFg2Combo"
              items-source="_getComboData('progFg2Combo')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false">
            </wj-combo-box>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <%-- 매장별 수신현황 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:600px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              control="flex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="90" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="140" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="180" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.storeCd"/>" binding="storeCd" width="130" align="center" is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.posCnt"/>" binding="posCnt" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.progFg"/>" binding="progFg" data-map="progFg2DataMap" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeRecvCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>
<script>
  var mainYn = ${cnv.getEnvCodeExcpAll("4021")};
  var posFg  = ${cnv.getEnvCodeExcpAll("4020")};
  var verRecvFg = ${ccu.getCommCodeExcpAll("060")};
  var hqOfficeCd = "${hqOfficeCd}";
  var orgnFg = "${orgnFg}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verRecv/storeRecv.js?ver=20220822.01" charset="utf-8"></script>

<%-- 매장추가 레이어 --%>
<c:import url="/WEB-INF/view/pos/confg/verrecv/storeRecvDtl.jsp">
</c:import>
