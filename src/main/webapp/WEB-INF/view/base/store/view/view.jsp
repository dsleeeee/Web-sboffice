<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="storeListCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="getStoreList()">
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
        <%-- 매장코드 --%>
        <th><s:message code="storeView.storeCd" /></th>
        <td>
          <input type="text" id="srchStoreCd" class="sb-input w100" ng-model="storeCd" maxlength="10"/>
          <%--
          <div class="sb-select">
            <div id="sStoreCd"></div>
          </div>
          --%>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="storeView.storeNm" /></th>
        <td>
          <input type="text" id="srchStoreNm" class="sb-input w100" ng-model="storeNm" maxlength="10"/>
          <%--
          <div class="sb-select">
            <div id="sStoreNm"></div>
          </div>
          --%>
        </td>
      </tr>
      <tr>
        <%-- 매장형태 (직영/가맹) //todo  --%>
<%--
        <th><s:message code="storeView.storeTypeNm" /></th>
        <td>
        </td>
--%>
        <%-- 용도 --%>
        <th><s:message code="storeView.clsFgNmG" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchClsFg"
                    ng-model="clsFg"
                    items-source="_getComboData('clsFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 매장상태 --%>
        <th><s:message code="storeView.sysStatFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchSysStatFg"
                    ng-model="sysStatFg"
                    items-source="_getComboData('sysStatFg')"
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

  <div class="mt20 oh sb-select dkbr">
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

    <%-- 매장환경 복사 --%>
    <button class="btn_skyblue fr" id="copyBtn" ng-click="copyStoreEnv()" style="display:none;">
      <s:message code="storeView.copy.store" />
    </button>

    <%-- 엑셀 다운로드 //todo --%>
    <%--
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
    --%>
  </div>

  <%-- 회원목록 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              control="flex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="storeView.storeCd"/>" binding="storeCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.storeNm"/>" binding="storeNm" width="120" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.storeTypeNm"/>" binding="storeTypeNm" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.clsFgNmG"/>" binding="clsFg" width="80" data-map="clsFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.bizNo"/>" binding="bizNo" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.ownerNm"/>" binding="ownerNm" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.telNo"/>" binding="telNo" width="5"  is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.address"/>" binding="address" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.posVerNo"/>" binding="posVerNo" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.sysStatFgNm"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.sysOpenDate"/>" binding="sysOpenDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.sysClosureDate"/>" binding="sysClosureDate"  width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeView.sms"/>" binding="gChk" visible="false" width="40" align="center"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeListCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>
<script>
var clsFg = ${ccu.getCommCodeSelect("001")};
var sysStatFg = ${ccu.getCommCodeSelect("005")};
var areaCd = ${ccu.getCommCodeSelect("061")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/store/view/view.js?ver=20181228.01" charset="utf-8"></script>


<%-- 매장 상세정보 --%>
<c:import url="/WEB-INF/view/base/store/view/dtl.jsp">
</c:import>

<%-- 밴사 설정정보 --%>
<c:import url="/WEB-INF/view/base/store/view/vanConfg.jsp">
</c:import>

<%-- 매장환경복사 --%>
<c:import url="/WEB-INF/view/base/store/view/copyStoreEnv.jsp">
</c:import>
