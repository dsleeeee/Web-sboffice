<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="rtnStatusProdView" class="subCon3" style="display: none;padding: 10px 20px 40px;" ng-controller="rtnStatusProdCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="rtnStatus.prod"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnRtnStatusProdSearch" ng-click="_pageView('rtnStatusProdCtrl',1)">
        <s:message code="cmm.search"/>
      </button>
    </div>
    <table class="searchTbl">
      <colgroup>
        <col class="w15"/>
        <col class="w35"/>
        <col class="w15"/>
        <col class="w35"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 조회월 --%>
        <th><s:message code="cmm.search.month"/></th>
        <td>
          <div class="sb-select">
              <span class="txtIn"> <input id="startMonth" name="startDate" class="w110px" /></span>
              <span class="rg">~</span>
              <span class="txtIn"> <input id="endMonth" name="endDate" class="w110px" /></span>
          </div>
        </td>
        <th>
            <s:message code="rtnStatus.srchOption"/>
        </th>
        <td>
            <%--상품분류 항목표시--%>
            <div class="mt10">
                <span class="chk ml10">
                    <input type="checkbox" id="chkProdClass" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                    <label for="chkProdClass">
                        <s:message code="periodIostock.prodClassDisplay" />
                    </label>
                </span>
            </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <tr>
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="rtnStatusProdSelectStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
          </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="rtnStatusProdSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <!-- contents start -->
    <div class="">
        <%-- wj grid start --%>
        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                class="w100px fl"
                id="rtnStatusProdListScaleBox"
                ng-model="rtnStatusProdListScale"
                items-source="_getComboData('rtnStatusProdListScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                initialized="initComboBox(s)"
                control="listScaleCombo"
                is-editable="true"
                text-changed="_checkValidation(s)">
            </wj-combo-box>
            <%-- 엑셀 다운로드 //TODO --%>
            <button class="btn_skyblue fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" />
            </button>
        </div>
        <div class="wj-gridWrap2 mt10">
            <wj-flex-grid
                id="rtnStatusProdGrid"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="rtnStatus.prodClassNm"/>"      binding="pathNm"          width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="rtnStatus.prodCd"/>"           binding="prodCd"          width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="rtnStatus.prodNm"/>"           binding="prodNm"          width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="rtnStatus.barcdCd"/>"          binding="barcdCd"         width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="rtnStatus.totSaleQty"/>"       binding="cnt"             width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="rtnStatus.realSaleAmt"/>"      binding="realSaleAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="rtnStatusProdCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%-- //wj grid end --%>
    </div>
    <!-- //contents end -->

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <ul id="rtnStatusProdCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%-- 엑셀 리스트 --%>
    <div class="wj-gridWrap2 mt20" style="display:none;" ng-controller="rtnStatusProdExcelCtrl">
  		<wj-flex-grid
  			id="rtnStatusProdExcelGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="excelFlex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">
            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="rtnStatus.prodClassNm"/>"      binding="pathNm"          width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStatus.prodCd"/>"           binding="prodCd"          width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStatus.prodNm"/>"           binding="prodNm"          width="200" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStatus.barcdCd"/>"          binding="barcdCd"         width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStatus.totSaleQty"/>"       binding="cnt"             width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnStatus.realSaleAmt"/>"      binding="realSaleAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
   </div>
   <%-- //엑셀 리스트 --%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/rtnStatus/prod.js?ver=20250415.01" charset="utf-8"></script>