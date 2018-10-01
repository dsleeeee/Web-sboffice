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
    <a href="javascript:void(0);" class="open">${menuNm}</a>
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
      <%-- 매장 --%>
      <th><s:message code="prepaid.srchStore" /></th>
      <td colspan="3">
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
      <td colspan="3">
        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="memberNm" />
      </td>
      <%-- 정렬 --%>
      <th><s:message code="prepaid.array" /></th>
      <td>
        <div class="sb-select">
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
  <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
    <div class="updownSet oh mb10">

      <button class="btn_skyblue" id="btnDeposit" style=";" ng-click="deposit()">
        <s:message code="prepaid.deposit" />
      </button>
      <button class="btn_skyblue" id="btnDelRepresent" style="display: none;" ng-click="del()">
        <s:message code="cmm.delete" />
      </button>
      <button class="btn_skyblue" id="btnSaveRepresent" style="display: none;" ng-click="save()">
        <s:message code="cmm.save" />
      </button>
    </div>
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div class="wj-gridWrap" style="height:315px">
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
          <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>--%>
          <wj-flex-grid-column header="<s:message code="prepaid.storeCd"/>" binding="storeCd" width="70"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.storeNm"/>" binding="storeNm" width="100"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.saleDate"/>" binding="saleDate" width="100"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.membrNo"/>" binding="membrNo" width="70"  ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.membrNm"/>" binding="membrNm" width="100" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidNo"/>" binding="prepaidNo" width="70"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidDt"/>" binding="prepaidDt"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidInFg"/>" binding="prepaidInFg" width="*" data-map="prepaidInFgDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.saleAmt"/>" binding="saleAmt" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidAmt"/>" binding="prepaidAmt" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.prepaidPayFg"/>" binding="prepaidPayFg" width="*" data-map="prepaidPayFgDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.nonsaleBillNo"/>" binding="nonsaleBillNo" width="*" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.orgPrepaidNo"/>" binding="orgPrepaidNo" width="*" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.sendYn"/>" binding="sendYn" width="*" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prepaid.sendDt"/>" binding="sendDt" width="*" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="representCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>

    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript">
  var arrayData = ${ccu.getCommCodeExcpAll("075")};
  var prepaidInFgData = ${ccu.getCommCodeExcpAll("073")};
  var prepaidPayFgData = ${ccu.getCommCodeExcpAll("074")};
  var baseUrl = "${baseUrl}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/prepaid/prepaid.js?ver=2018090301" charset="utf-8"></script>
