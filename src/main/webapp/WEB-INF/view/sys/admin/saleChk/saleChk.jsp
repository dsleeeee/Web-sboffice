<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div class="subCon" ng-controller="saleChkCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button id="nxBtnSearch" class="btn_blue fr"  ng-click="_pageView('saleChkCtrl',1)">
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
        <%-- 매출 일자 --%>
        <tr>
            <th><s:message code="saleChk.saleDate" /></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w120px"></span>
                </div>
            </td>
        </tr>
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="saleChk.hqOfficeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="saleChk.hqOfficeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();" />
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="saleChk.storeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
        </td>
        <%-- 매장명 --%>
        <th><s:message code="saleChk.storeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
        </td>
      </tr>
      <tr>
        <%-- 매출구분 --%>
        <th><s:message code="saleChk.saleYn" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchSaleYn"
              ng-model="saleYn"
              control="saleYnCombo"
              items-source="_getComboData('srchSaleYn')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)"
              selected-index="2">
            </wj-combo-box>
          </div>
        </td>
        <%-- 체크항목 --%>
        <th><s:message code="saleChk.checkNm" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchCheckNm"
              ng-model="checkNm"
              control="checkNmCombo"
              items-source="_getComboData('srchCheckNm')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)"
              selected-index="1">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <tr>
          <%-- 오류타입 --%>
          <th><s:message code="saleChk.remark" /></th>
          <td>
              <div class="sb-select">
                  <wj-combo-box
                          id="srchRemark"
                          ng-model="remark"
                          control="remarkCombo"
                          items-source="_getComboData('srchRemark')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index="1">
                  </wj-combo-box>
              </div>
          </td>
          <%-- 처리메시지 --%>
          <th><s:message code="saleChk.resultMsg" /></th>
          <td>
              <div class="sb-select">
                  <input type="text" class="sb-input w100" id="srchResultMsg" ng-model="resultMsg" onkeyup="fnNxBtnSearch();" />
              </div>
          </td>
      </tr>
        <tr>
            <%-- 처리구분 --%>
            <th><s:message code="saleChk.procYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchProcYn"
                            ng-model="procYn"
                            control="saleYnCombo"
                            items-source="_getComboData('srchProcYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 체크항목코드 --%>
            <th><s:message code="saleChk.checkCd" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchCheckCd"
                            ng-model="checkCd"
                            control="checkCdCombo"
                            items-source="_getComboData('srchCheckCd')"
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

  <div id="grid" class="w100">

    <div class="mt20 oh sb-select dkbr">
      <%-- 페이지 스케일  --%>
      <wj-combo-box
        class="w100px fl"
        id="listScaleBox"
        ng-model="listScale"
        control="listScaleCombo"
        items-source="_getComboData('listScaleBox')"
        display-member-path="name"
        selected-value-path="value"
        is-editable="false"
        initialized="_initComboBox(s)">
      </wj-combo-box>

      <%-- 저장 --%>
      <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>

    </div>

    <div class="wj-gridWrap mt10" style="height:370px; overflow-y: hidden;">
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
          <wj-flex-grid-column header="<s:message code="saleChk.checkNm"/>" binding="checkNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.storeNm"/>" binding="storeNm" width="200" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.billDt"/>" binding="billDt" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.billNo"/>" binding="billNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.saleYn"/>" binding="saleYn" align="center" is-read-only="true" data-map="saleYnDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.resultMsg"/>" binding="resultMsg" width="110" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.remark"/>" binding="remark" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.procDate"/>" binding="procDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.procTime"/>" binding="procTime" width="110" align="center" is-read-only="true"></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="saleChk.procDt2"/>" binding="procDt2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.procYn2"/>" binding="procYn2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.checkCd2"/>" binding="checkCd2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.checkNm2"/>" binding="checkNm2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.storeCd2"/>" binding="storeCd2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.storeNm2"/>" binding="storeNm2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.hqOfficeCd2"/>" binding="hqOfficeCd2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.saleDate2"/>" binding="saleDate2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.posNo2"/>" binding="posNo2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.billNo2"/>" binding="billNo2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.saleYn2"/>" binding="saleYn2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.remark2"/>" binding="remark2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.hqBrandCd2"/>" binding="hqBrandCd2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.procDate2"/>" binding="procDate2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.procTime2"/>" binding="procTime2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleChk.resultMsg2"/>" binding="resultMsg2" width="110" align="center" is-read-only="true"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="saleChkCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin/saleChk/saleChk.js?ver=20220307.01" charset="utf-8"></script>
