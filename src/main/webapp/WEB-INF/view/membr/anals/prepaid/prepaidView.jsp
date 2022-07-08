<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="membrClassManageFg" value="${membrClassManageFg}" />
<c:set var="baseUrl" value="/membr/anals/prepaid/" />

<div class="subCon" ng-controller="prepaidCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_pageView('prepaidCtrl', 1)" id="nxBtnSearch">
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
        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
          <jsp:param name="targetId" value="prepaidChargeStore"/>
        </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <%-- 회원번호 --%>
      <th><s:message code="prepaid.membrNo" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srcgMembrNo" ng-model="membrNo" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 회원명 --%>
      <th><s:message code="prepaid.membrNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" onkeyup="fnNxBtnSearch();"/>
      </td>
    </tr>
    <tr>
      <th><s:message code="prepaid.prepaidYn"/></th>
      <td>
        <div class="sb-select">
          <wj-combo-box
                  id="srchUseYn"
                  ng-model="useYn"
                  items-source="_getComboData('useYn')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
      </td>
      <th <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>><s:message code="prepaid.prepaidAmt2"/></th>
      <td <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>>
        <input type="text" class="sb-input w50" id="prepaidAmt" ng-model="prepaidAmt" numberOnly>
        <a href="#" class="btn_grayS ml10" ng-click="batchChange()"><s:message code="prepaid.batchChange" /></a>
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

    <%-- 선불금 충전 --%>
    <span <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>>
      <button class="btn_skyblue ml5 fr"  ng-click="charge()">
        <s:message code="prepaid.charge" />
      </button>
    </span>
    <%-- 엑셀 다운로드 //TODO --%>
    <button class="btn_skyblue fr" ng-click="excelDownload()">
      <s:message code="cmm.excel.down" />
    </button>
  </div>

  <%-- 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
      <div class="row">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <%--
          <wj-flex-grid-column header="<s:message code="prepaid.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.storeNm"/>" binding="storeNm" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.saleDate"/>" binding="saleDate" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidNo"/>" binding="prepaidNo" width="70" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.membrNo"/>" binding="membrNo" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.membrNm"/>" binding="membrNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidInFg"/>" binding="prepaidInFg" data-map="prepaidInFgDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidPayFg"/>" binding="prepaidPayFg" data-map="prepaidPayFgDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidAmt"/>" binding="prepaidAmt" is-read-only="true" width="140"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.saleAmt"/>" binding="saleAmt" is-read-only="true" width="140" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidDt"/>" binding="prepaidDt" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.nonsaleBillNo"/>" binding="nonsaleBillNo" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.orgPrepaidNo"/>" binding="orgPrepaidNo" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.sendYn"/>" binding="sendYn" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.sendDt"/>" binding="sendDt" visible="false"></wj-flex-grid-column>
          --%>
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.hqOfficeCd"/>" binding="hqOfficeCd" width="70" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.storeNm"/>" binding="storeNm" width="140" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.membrNo"/>" binding="membrNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.membrNm"/>" binding="membrNm" width="90" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidAmt"/>" binding="prepaidAmt" width="90" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidUseAmt"/>" binding="prepaidUseAmt" width="90" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidBalAmt"/>" binding="prepaidBalAmt" width="90" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidAmt2"/>" binding="prepaidAmt2" width="90" data-type="Number" align="right"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
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

<%-- 엑셀 리스트 --%>
<div class="w100 mt10 mb20">
  <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; display: none;" ng-controller="prepaidExcelCtrl">
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

        <wj-flex-grid-column header="<s:message code="prepaid.hqOfficeCd"/>" binding="hqOfficeCd" width="70" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.storeNm"/>" binding="storeNm" width="140" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.membrNo"/>" binding="membrNo" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.membrNm"/>" binding="membrNm" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidAmt"/>" binding="prepaidAmt" width="150" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidUseAmt"/>" binding="prepaidUseAmt" width="150" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prepaid.prepaidBalAmt"/>" binding="prepaidBalAmt" width="150" is-read-only="true" ></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
</div>

<script type="text/javascript">
  var arrayData = ${ccu.getCommCodeExcpAll("075")};
  var prepaidInFgData = ${ccu.getCommCodeExcpAll("073")};
  var prepaidPayFgData = ${ccu.getCommCodeExcpAll("074")};
  var orgnFg = "${orgnFg}";
  var baseUrl = "${baseUrl}";

  // 회원등급 관리구분[1237]
  var membrClassManageFg = ${membrClassManageFg};

  $(function(){
    $("input:text[numberOnly]").on("keyup", function() {
      $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
    });
  });
</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/prepaid/prepaid.js?ver=20181226.04" charset="utf-8"></script>

<%--&lt;%&ndash; 선불금충전 팝업 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/membr/anals/prepaid/charge.jsp">--%>
<%--</c:import>--%>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/application/layer/store.jsp">
</c:import>
