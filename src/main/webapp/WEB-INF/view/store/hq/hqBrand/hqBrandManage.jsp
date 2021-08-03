<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" ng-controller="hqBrandManageCtrl">

  <div>
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('hqBrandManageCtrl', 1)">
          <s:message code="cmm.search" />
        </button>
      </div>
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
        <%-- 브랜드코드 --%>
        <th><s:message code="hqBrand.hqBrandCd" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchHqBrandCd" ng-model="hqBrandCd" />
          </div>
        </td>
        <%-- 브랜드명 --%>
        <th><s:message code="hqBrand.hqBrandNm" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchHqBrandNm" ng-model="hqBrandNm" />
          </div>
        </td>
      </tr>
      <tr>
        <%-- 사용여부 --%>
        <th><s:message code="hqBrand.useYn" /></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchUseYnFg"
                      ng-model="useYnFg"
                      items-source="_getComboData('srchUseYnFg')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
              </wj-combo-box>
            </div>
          </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>


  <div class="mt20 oh sb-select dkbr">
    <%--페이지 스케일 --%>
    <div id="listScaleBox" class="w100px fl"></div>
    <div class="tr">
      <%-- 브랜드신규등록 --%>
      <button class="btn_skyblue" id="btnAdd" ng-click="addRow()"><s:message code="hqBrand.newBrand" /></button>
      <%-- 저장 --%>
      <button class="btn_skyblue" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
    </div>
  </div>

  <%-- 위즈모 테이블 --%>
  <div class="wj-TblWrap mt10">
    <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="hqBrand.hqOfficeCd"/>" binding="hqOfficeCd" width="0" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqBrand.storeCd"/>" binding="storeCd" width="0" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqBrand.hqOfficeNm"/>" binding="hqOfficeNm" width="0"visible="false"></wj-flex-grid-column>
        <c:if test="${orgnFg == 'HQ'}">
          <wj-flex-grid-column header="<s:message code="hqBrand.hqBrandCd"/>" binding="hqBrandCd" width="200" align="center" max-length="7"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqBrand.hqBrandNm"/>" binding="hqBrandNm" width="500" align="center" max-length="10"></wj-flex-grid-column>
        </c:if>
        <c:if test="${orgnFg == 'STORE'}">
          <wj-flex-grid-column header="<s:message code="hqBrand.hqBrandCd"/>" binding="msBrandCd" width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqBrand.hqBrandNm"/>" binding="msBrandNm" width="500" align="center" max-length="10"></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" width="200" data-map="useYnDataMap" align="center"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <%--<div class="pageNum mt20">
    &lt;%&ndash; id &ndash;%&gt;
    <ul id="hqBrandManageCtrlPager" data-size="10">
    </ul>
  </div>--%>
</div>

<script>
  var useYn = ${ccu.getCommCodeExcpAll("067")};
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
  var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/hq/hqBrand/hqBrandManage.js?ver=20210727.02" charset="utf-8"></script>
