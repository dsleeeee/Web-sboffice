<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/iostock/frnchs/storeProd/"/>

<div id="frnchsStoreProdView" class="subCon3" ng-controller="frnchsStoreProdCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="frnchsStoreProd.frnchsStoreProd"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('frnchsStoreProdCtrlSrch')">
            <s:message code="cmm.search"/>
        </button>
    </div>
    <%-- 조회조건 --%>
    <table class="searchTbl">
        <colgroup>
            <col class="w13"/>
            <col class="w37"/>
            <col class="w13"/>
            <col class="w37"/>
        </colgroup>
        <tbody>
        <%-- 출고일자 --%>
        <tr>
            <th><s:message code="frnchsStoreProd.date" /></th>
            <td colspan="3">
            <div class="sb-select">
                <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchEndDate" class="w120px"></span>
            </div>
            </td>
        </tr>
        <tr>
            <%-- 분류--%>
            <th><s:message code="frnchsStoreProd.category" /></th>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg != 'HQ'}">
            <td>
            </c:if>
              <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
              <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
              <button type="button" class="btn_skyblue fl mr5" id="btnCancelFrnchsStoreProdCd" style="margin-left: 5px;" ng-click="delFrnchsStoreProd()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 매장코드 --%>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <th><s:message code="todayBillSaleDtl.store"/></th>
            <td>
                <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                    <jsp:param name="targetId" value="frnchsStoreProdSelectStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
            </c:if>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="frnchsStoreProdSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <tr>
            <%-- 상품명 --%>
            <th><s:message code="frnchsStoreProd.prodNm" /></th>
            <td><input type="text" id="srchProdNm" class="sb-input w100" maxlength="100" ng-model="prodNmModel"/></td>
            <%-- 상품코드 --%>
            <th><s:message code="frnchsStoreProd.prodCd" /></th>
            <td><input type="text" id="srchProdCd" class="sb-input w100" maxlength="13" ng-model="prodCdModel"/></td>
        </tr>
        </tbody>
    </table>

    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
          class="w100px fl"
          id="frnchsStoreProdlistScaleBox"
          ng-model="listScale"
          items-source="_getComboData('frnchsStoreProdlistScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          initialized="_initComboBox(s)"
          control="conListScale"
		  is-editable="true"
		  text-changed="_checkValidation(s)">
        </wj-combo-box>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="text" id="frnchsStoreProdSelectStoreStoreNum" ng-model="storeNum">
        </c:if>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadClass()"><s:message code="cmm.excel.down" />
        </button>
    </div>

    <%--위즈모 테이블--%>
    <div id="wjWrapType2" class="w100 mt10">
      <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="frnchsStore.storeCd"/>"         binding="storeCd"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.storeNm"/>"         binding="storeNm"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.lv1Nm"/>"         binding="lv1Nm"        width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.lv2Nm"/>"         binding="lv2Nm"        width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.lv3Nm"/>"         binding="lv3Nm"        width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.prodCd"/>"         binding="prodCd"        width="120" align="center" format="d" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.prodNm"/>"         binding="prodNm"       width="150" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.poUnitFg"/>"        binding="poUnitFgNm"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.poUnitQty"/>"     binding="poUnitQty"    width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.outCnt"/>"        binding="outCnt"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.cnt"/>"     binding="outTotQty"    width="130" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.totAmt"/>"       binding="outTot"      width="140" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.cnt"/>"    binding="inTotQty"   width="145" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.totAmt"/>"       binding="inTot"   width="145" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStoreProd.penaltyAmt"/>"    binding="penaltyAmt"   width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          <wj-flex-grid-column header=""        binding="poUnitFg"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="frnchsStoreProdCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="frnchsStoreProdCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>


    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="display: none;" ng-controller="frnchsStoreProdExcelCtrl">
      <wj-flex-grid
        id="frnchsStoreProdExcelGrid"
        autoGenerateColumns="false"
        control="excelFlex"
        initialized="initGrid(s,e)"
        sticky-headers="true"
        selection-mode="Row"
        items-source="data"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="frnchsStore.storeCd"/>"         binding="storeCd"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.storeNm"/>"         binding="storeNm"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.lv1Nm"/>"         binding="lv1Nm"        width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.lv2Nm"/>"         binding="lv2Nm"        width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.lv3Nm"/>"         binding="lv3Nm"        width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.prodCd"/>"         binding="prodCd"        width="120" align="center" format="d" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.prodNm"/>"         binding="prodNm"       width="150" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.poUnitFg"/>"        binding="poUnitFgNm"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.poUnitQty"/>"     binding="poUnitQty"    width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.outCnt"/>"        binding="outCnt"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.cnt"/>"     binding="outTotQty"    width="130" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.totAmt"/>"       binding="outTot"      width="140" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.cnt"/>"    binding="inTotQty"   width="145" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.totAmt"/>"       binding="inTot"   width="145" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStoreProd.penaltyAmt"/>"    binding="penaltyAmt"   width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

        <wj-flex-grid-column header=""        binding="poUnitFg"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
</div>
<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/storeProd/storeProd.js?ver=20190125.02" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<c:import url="/WEB-INF/view/iostock/frnchs/storeProd/storeProdDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
