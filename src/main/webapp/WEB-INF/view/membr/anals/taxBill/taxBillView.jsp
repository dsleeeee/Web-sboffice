<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="taxBillCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_broadcast('taxBillCtrl')">
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
      <%-- 요청일자 --%>
      <th><s:message code="taxBill.requestDt" /></th>
      <td>
        <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      value="startDate"
                      ng-model="startDate"
                      control="startDateCombo"
                      min="2018-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
          <span class="rg">~</span>
          <span class="txtIn w110px">
              <wj-input-date
                      value="endDate"
                      ng-model="endDate"
                      control="endDateCombo"
                      min="2018-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
          <span class="chk ml10">
              <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
              <label for="chkDt">
                <s:message code="cmm.all.day" />
              </label>
            </span>
        </div>
      </td>
      <%-- 발행상태 --%>
      <th><s:message code="taxBill.statusFg" /></th>
      <td>
        <div class="sb-select">
          <wj-combo-box
                  id="srchStatusFgCombo"
                  ng-model="statusFg"
                  items-source="_getComboData('srchStatusFgCombo')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 회원번호 --%>
      <th><s:message code="taxBill.membrNo" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srcgMembrNo" ng-model="membrNo" />
      </td>
      <%-- 회원명 --%>
      <th><s:message code="taxBill.membrNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" />
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
          <wj-flex-grid-column header="<s:message code="taxBill.billDate"/>" binding="billDate" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.billSeq"/>" binding="billSeq" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.hqOfficeCd"/>" binding="membrOrgnCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.membrNo"/>" binding="membrNo" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.membrNm"/>" binding="membrNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.requestDt"/>" binding="requestDt" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.requestAmt"/>" binding="requestAmt" width="*" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.statusFg"/>" binding="statusFg" data-map="statusFgDataMap" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="taxBillCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>
<script>
  var statusFg = ${ccu.getCommCode("100")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/taxBill/taxBill.js?ver=20181213.01" charset="utf-8"></script>
