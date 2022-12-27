<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" id="allergyView" ng-controller="allergyCtrl" style="display: none;">
  <%--searchTbl--%>
  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="prodInfoSearch.allergy"/></a>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('allergyCtrl')">
        <s:message code="cmm.search" />
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
      <%-- 분류조회 --%>
      <th><s:message code="prod.prodClass" /></th>
      <td>
        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
               placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
      </td>
    </tr>
    <tr>
      <%-- 상품코드 --%>
      <th><s:message code="prodInfoSearch.allergy.prodCd" /></th>
      <td>
        <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/>
      </td>
      <%-- 상품명 --%>
      <th><s:message code="prodInfoSearch.allergy.prodNm" /></th>
      <td>
        <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/>
      </td>
    </tr>
    <tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <%-- 상품브랜드 --%>
        <th><s:message code="prodInfoSearch.allergy.prodHqBrand"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchProdHqBrandCombo"
                    ng-model="prodHqBrandCd"
                    items-source="_getComboData('prodHqBrandCdCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="srchProdHqBrandCombo">
            </wj-combo-box>
          </div>
        </td>
      </c:if>
      <%-- 상품 --%>
      <th><s:message code="prodInfoSearch.allergy.prod"/></th>
      <td>
        <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                         targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                         displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                         modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                         closeFunc - 팝업 닫기시 호출할 함수--%>
        <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdMMoms.jsp" flush="true">
          <jsp:param name="targetId" value="allergyProdSelect"/>
        </jsp:include>
        <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
  </div>

  <%--//searchTbl--%>
    <div class="wj-TblWrapBr mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 330px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                sticky-headers="true"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.recipeslCd"/>" binding="recipeslCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.recipeslNm"/>" binding="recipeslNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.allergyNm"/>" binding="allergyNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
      <%-- id --%>
      <ul id="allergyCtrlPager" data-size="10">
      </ul>
    </div>
    <%-- //페이지 리스트 --%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="allergyExcelCtrl">
      <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                id="wjGridExcelList"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="excelFlex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.recipeslCd"/>" binding="recipeslCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.recipeslNm"/>" binding="recipeslNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.allergy.allergyNm"/>" binding="allergyNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트--%>
</div>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodInfoSearch/allergy.js?ver=20221223.01" charset="utf-8"></script>
