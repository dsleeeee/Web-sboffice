<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="storeSaleAreaCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="storeSaleArea.storeSaleArea"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('storeSaleAreaCtrl',1)">
        <s:message code="cmm.search"/>
      </button>
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
        <%-- 지사코드 --%>
        <th><s:message code="storeSaleArea.branchCd" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchBranchCd" onkeyup="fnNxBtnSearch();"/>
          </div>
        </td>
        <%-- 지사명 --%>
        <th><s:message code="storeSaleArea.branchNm" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchBranchNm" onkeyup="fnNxBtnSearch();"/>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="storeSaleArea.storeCd" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchStoreCd" onkeyup="fnNxBtnSearch();"/>
          </div>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="storeSaleArea.storeNm" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchStoreNm" onkeyup="fnNxBtnSearch();"/>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 지역코드 --%>
        <th><s:message code="storeSaleArea.areaCd" /></th>
        <td>
            <div class="sb-select">
              <wj-combo-box
                     id="srchAreaCd"
                     ng-model="areaCd"
                     items-source="_getComboData('areaCd')"
                     display-member-path="name"
                     selected-value-path="value"
                     is-editable="false"
                     initialized="_initComboBox(s)"
                     control="srchAreaCdCombo">
              </wj-combo-box>
            </div>
        </td>
        <%-- 매장상태구분 --%>
        <th><s:message code="storeSaleArea.sysStatFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchSysStatFg"
                    ng-model="sysStatFg"
                    items-source="_getComboData('sysStatFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchSysStatFgCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="mt20 oh sb-select dkbr">
    <%--페이지 스케일 --%>
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
    <div class="tr">
      <%-- 신규등록 --%>
      <button class="btn_skyblue" ng-click="addStoreSaleArea()"><s:message code="cmm.new.add" /></button>
    </div>
  </div>

 <%-- 위즈모 테이블 --%>
  <div class="wj-TblWrap mt10">
    <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          control="flex"
          autoGenerateColumns="false"
          selection-mode="Row"
          initialized="initGrid(s,e)"
          items-source="data"
          item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="storeSaleArea.branchCd"/>" binding="branchCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.branchNm"/>" binding="branchNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.storeNm"/>" binding="storeNm" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.addr"/>" binding="addr" width="400" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeSaleAreaCtrlPager" data-size="10">
    </ul>
  </div>

</div>

<script type="text/javascript">
    var areaCd = ${ccu.getCommCode("061")};
    var sysStatFg = ${ccu.getCommCode("005")};

    // 지사
    var branchCombo = ${branchCombo};

</script>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeSaleArea/storeSaleArea.js?ver=20221125.01" charset="utf-8"></script>

<%-- NAVER Maps API --%>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=67szg2cbo7"></script>

<%-- 점포 영업 지역 관리 상세 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeSaleArea/storeSaleAreaDtl.jsp">
</c:import>

<%-- 점포 영업 지역 관리 등록 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeSaleArea/storeSaleAreaReg.jsp">
</c:import>