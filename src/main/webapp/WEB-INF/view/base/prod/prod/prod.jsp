<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="prodCtrl">
  <%--searchTbl--%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
      <%-- 등록 일자 --%>
      <tr>
        <th><s:message code="prod.regDate" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                value="startDate"
                ng-model="startDate"
                control="startDateCombo"
                min="2000-01-01"
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
                min="2000-01-01"
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
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="prod.prodCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
        </td>
        <%-- 상품명 --%>
        <th><s:message code="prod.prodNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
        </td>
      </tr>
      <tr>
        <%-- 분류조회 --%>
        <th><s:message code="prod.prodClass" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCd" />
        </td>
        <%-- 바코드 --%>
        <th><s:message code="prod.barCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" />
        </td>
      </tr>
    </tbody>
  </table>
  <%--//searchTbl--%>

  <div class="mt10 oh">
    <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prodCtrl', 1)" >
      <s:message code="cmm.search" />
    </button>
  </div>

  <div class="mt40 oh sb-select dkbr">
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

    <%-- 엑셀 다운로드 //TODO --%>
    <%--
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
    --%>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10">
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div id="theGrid" style="height: 365px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        control="flex"
        initialized="initGrid(s,e)"
        sticky-headers="true"
        selection-mode="Row"
        items-source="data"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodClass"/>" binding="prodClassNm" width="150" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.costUprc"/>" binding="costUprc" width="80" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.splyUprc"/>" binding="splyUprc" width="80" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.orgplceCd"/>" binding="orgplceCd" width="80" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.poUnitFg"/>" binding="poUnitFg" width="80" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="prodCtrl"/>
      </jsp:include>
    </div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="prodCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prod.js?ver=20181027.01" charset="utf-8"></script>

<%-- 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/popUpProdDetail.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/popUpProdModify.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>



