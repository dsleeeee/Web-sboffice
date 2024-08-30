<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" id ="lsmStoreView" style="display: none;padding: 10px 20px 40px;">
  <div ng-controller="lsmStoreCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="lsmStore.tukey"/></a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('lsmStoreCtrl',1);">
          <s:message code="cmm.search"/>
        </button>
      </div>
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
        <%-- 매장선택 --%>
        <th><s:message code="cmm.store.select"/></th>
        <td>
          <%-- 매장선택 모듈 사용시 include --%>
          <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
            <jsp:param name="targetTypeFg" value="M"/>
            <jsp:param name="targetId" value="lsmTukey"/>
          </jsp:include>
          <%--// 매장선택 모듈 사용시 include --%>
        </td>
        <%-- 매장브랜드 --%>
        <th><s:message code="cmm.moms.storeHqBrand"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchStoreHqBrandCdCombo"
                    ng-model="storeHqBrandCd"
                    items-source="_getComboData('storeHqBrandCdCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="srchStoreHqBrandCdCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <tr>
        <th><s:message code="lsmStore.tukeyGrpCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchTukeyGrpCd" ng-model="srchTukeyGrpCd" onkeyup="fnNxBtnSearch();" />
        </td>
        <th><s:message code="lsmStore.tukeyGrpNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchTukeyGrpNm" ng-model="srchTukeyGrpNm" onkeyup="fnNxBtnSearch();" />
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="lsmTukeyCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
      <p class="tl s13 mt5 ml5 lh15 fl">엑셀업로드 적용기준 : </p>
      <p class="tl s13 mt5 ml5 lh15 fl">1. 허용분류/카테고리(LSM) 만 수정 <br/>
                                        2. 상품의 단종/사용 여부 체크<br/>
                                        3. 그외에 맞지 않는 정보는 등록되지 않음</p>
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
      <%-- 엑셀업로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelUpload()"><s:message code="cmm.excel.excelUpload"/></button>
      <%-- 양식다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()"><s:message code="cmm.excel.sampleDown"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 500px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
          id="wjGridList"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="lsmStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.sysStatFg"/>" binding="sysStatFg" width="70" data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.tukeyGrpCd"/>" binding="tukeyGrpCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.tukeyGrpNm"/>" binding="tukeyGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.tukeyClassCd"/>" binding="tukeyClassCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.tukeyClassNm"/>" binding="tukeyClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.saleUprc"/>" binding="saleUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
      <%-- id --%>
      <ul id="lsmStoreCtrlPager" data-size="10">
      </ul>
    </div>
    <%--//페이지 리스트--%>
    <%-- 엑셀 리스트 --%>
    <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="lsmStoreExcelCtrl">
      <div class="wj-gridWrap">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="excelFlex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="lsmStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.sysStatFg"/>" binding="sysStatFg" width="70" data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.tukeyGrpCd"/>" binding="tukeyGrpCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.tukeyGrpNm"/>" binding="tukeyGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.tukeyClassCd"/>" binding="tukeyClassCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.tukeyClassNm"/>" binding="tukeyClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="lsmStore.saleUprc"/>" binding="saleUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트 --%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/lsmStore/lsmStore.js?ver=20240829.01" charset="utf-8"></script>

<%-- 엑셀업로드 --%>
<c:import url="/WEB-INF/view/store/storeMoms/lsmStore/excelUploadLsmTukeyStore.jsp">
</c:import>