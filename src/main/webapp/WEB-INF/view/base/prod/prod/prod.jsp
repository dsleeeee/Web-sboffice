<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="prodEnvstVal" value="${prodEnvstVal}" />
<c:set var="priceEnvstVal" value="${priceEnvstVal}" />

<div class="subCon" ng-controller="prodCtrl">
  <%--searchTbl--%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('prodCtrl')">
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
          <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                 placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
        </td>
        <%-- 바코드 --%>
        <th><s:message code="prod.barCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" />
        </td>
      </tr>
      <tr>
        <%-- 사용여부 --%>
        <th><s:message code="prod.useYn" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchUseYn"
                    ng-model="useYn"
                    control="useYnAllCombo"
                    items-source="_getComboData('useYnAllComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 상품등록주체 (본사/매장) => 단독매장 제외해야함  // todo --%>
          <th><s:message code="prod.brandNm" /></th>
          <td><input type="text" class="sb-input w100" id="srchBrandNm" ng-model="brandNm" /></td>
        <%--
        <th><s:message code="prod.useYn" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchRegOrgnFg"
                    ng-model="regOrgnFg"
                    control="regOrgnFgCombo"
                    items-source="_getComboData('regOrgnFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        --%>
      </tr>
    </tbody>
  </table>
  <%--//searchTbl--%>


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
    <button class="btn_skyblue fr" id="btnAddProd" ng-click="addProd()">
      <s:message code="prod.title.addProd" />
    </button>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10">
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div id="theGrid" style="height: 370px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        control="flex"
        initialized="initGrid(s,e)"
        sticky-headers="true"
        selection-mode="Row"
        items-source="data"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodClass"/>" binding="prodClassNm" width="300" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="150" is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="410" is-read-only="true"></wj-flex-grid-column>

        <c:if test="${orgnFg == 'HQ'}">
          <wj-flex-grid-column header="<s:message code="prod.storeCnt"/>" binding="storeCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        </c:if>

        <wj-flex-grid-column header="<s:message code="prod.costUprc"/>" binding="costUprc" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.splyUprc"/>" binding="splyUprc" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.orgplceCd"/>" binding="orgplceCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column><!--// todo 원산지명 조회 필요-->
        <wj-flex-grid-column header="<s:message code="prod.poUnitFg"/>" binding="poUnitFg" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.useYn"/>" binding="useYn" width="100" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>

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
<script>
  var prodEnvstVal = "${prodEnvstVal}";
  var priceEnvstVal = "${priceEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prod.js?ver=20181228.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 상품별 적용매장 선택 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodStoreRegistView.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
