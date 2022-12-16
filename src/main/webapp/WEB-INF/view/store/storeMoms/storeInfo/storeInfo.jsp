<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="storeInfoCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeInfoCtrl')">
        <s:message code="cmm.search"/>
      </button>
      <%-- 확장조회 --%>
      <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
        <s:message code="cmm.search.addShow" />
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
      <%-- 매장코드 --%>
      <th><s:message code="storeInfo.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="storeInfo.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
      </td>
    </tr>
    <tr>
      <%-- 매장브랜드 --%>
      <th><s:message code="dayProd.storeHqBrand"/></th>
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
      </td>
    </tr>
    </tbody>
  </table>
  <c:if test="${sessionInfo.orgnFg == 'HQ'}">
    <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
      <colgroup>
        <col class="w15"/>
        <col class="w35"/>
        <col class="w15"/>
        <col class="w35"/>
      </colgroup>
      <tbody>
      <tr>
          <%-- 팀별 --%>
        <th><s:message code="dayProd.momsTeam"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchMomsTeamCombo"
                    ng-model="momsTeam"
                    items-source="_getComboData('momsTeamCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchMomsTeamCombo">
            </wj-combo-box>
          </div>
        </td>
          <%-- AC점포별 --%>
        <th><s:message code="dayProd.momsAcShop"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchMomsAcShopCombo"
                    ng-model="momsAcShop"
                    items-source="_getComboData('momsAcShopCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchMomsAcShopCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <tr>
          <%-- 지역구분 --%>
        <th><s:message code="dayProd.momsAreaFg"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchMomsAreaFgCombo"
                    ng-model="momsAreaFg"
                    items-source="_getComboData('momsAreaFgCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchMomsAreaFgCombo">
            </wj-combo-box>
          </div>
        </td>
          <%-- 상권 --%>
        <th><s:message code="dayProd.momsCommercial"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchMomsCommercialCombo"
                    ng-model="momsCommercial"
                    items-source="_getComboData('momsCommercialCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchMomsCommercialCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <tr>
          <%-- 점포유형 --%>
        <th><s:message code="dayProd.momsShopType"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchMomsShopTypeCombo"
                    ng-model="momsShopType"
                    items-source="_getComboData('momsShopTypeCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchMomsShopTypeCombo">
            </wj-combo-box>
          </div>
        </td>
          <%-- 매장관리타입 --%>
        <th><s:message code="dayProd.momsStoreManageType"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchMomsStoreManageTypeCombo"
                    ng-model="momsStoreManageType"
                    items-source="_getComboData('momsStoreManageTypeCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchMomsStoreManageTypeCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <tr>
          <%-- 지사 --%>
        <th><s:message code="dayProd.branchCd"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchBranchCdComboo"
                    ng-model="branchCd"
                    items-source="_getComboData('branchCdCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchBranchCdComboo">
            </wj-combo-box>
          </div>
        </td>
        <td></td>
        <td></td>
      </tr>
      </tbody>
    </table>
  </c:if>

  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
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
        <wj-flex-grid-column header="<s:message code="storeInfo.storeCd"/>" binding="storeCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.storeNm"/>" binding="storeNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.ownerNm"/>" binding="ownerNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
<%--        <wj-flex-grid-column header="<s:message code="storeInfo.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>--%>
        <wj-flex-grid-column header="<s:message code="storeInfo.hqOfficeNm"/>" binding="hqOfficeNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.bizNo"/>" binding="bizNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
<%--        <wj-flex-grid-column header="<s:message code="storeInfo.bizTypeCd"/>" binding="bizTypeCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>--%>
<%--        <wj-flex-grid-column header="<s:message code="storeInfo.bizItemCd"/>" binding="bizItemCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>--%>
        <wj-flex-grid-column header="<s:message code="storeInfo.bizStoreNm"/>" binding="bizStoreNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.telNo"/>" binding="telNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.mpNo"/>" binding="mpNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.faxNo"/>" binding="faxNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.emailAddr"/>" binding="emailAddr" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.hmpgAddr"/>" binding="hmpgAddr" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.postNo"/>" binding="postNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.addr"/>" binding="addr" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.addrDtl"/>" binding="addrDtl" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.areaCd"/>" binding="areaCd" width="80" align="left" is-read-only="true" data-map="areaCdDataMap"></wj-flex-grid-column>
<%--        <wj-flex-grid-column header="<s:message code="storeInfo.storeType"/>" binding="storeType" width="80" align="left" is-read-only="true"></wj-flex-grid-column>--%>
        <wj-flex-grid-column header="<s:message code="storeInfo.clsFg"/>" binding="clsFg" width="80" align="left" is-read-only="true" data-map="clsFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.sysStatFg"/>" binding="sysStatFg" width="80" align="left" is-read-only="true" data-map="sysStatFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.sysOpenDate"/>" binding="sysOpenDate" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.sysClosureDate"/>" binding="sysClosureDate" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.vanCd"/>" binding="vanCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.agencyCd"/>" binding="agencyCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.remark"/>" binding="remark" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.sysRemark"/>" binding="sysRemark" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.hdRemark"/>" binding="hdRemark" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.directManageYn"/>" binding="directManageYn" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.mapStoreCd"/>" binding="mapStoreCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.latitude"/>" binding="latitude" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.longitude"/>" binding="longitude" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.branchCd"/>" binding="branchCd" width="80" align="left" is-read-only="true" data-map="branchCdDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.saleArea"/>" binding="saleArea" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.siteCd"/>" binding="siteCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
<%--        <wj-flex-grid-column header="<s:message code="storeInfo.indexNo"/>" binding="indexNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>--%>
        <wj-flex-grid-column header="<s:message code="storeInfo.momsTeam"/>" binding="momsTeam" width="80" align="left" is-read-only="true" data-map="momsTeamDataMap" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.momsAcShop"/>" binding="momsAcShop" width="80" align="left" is-read-only="true" data-map="momsAcShopDataMap" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.momsAreaFg"/>" binding="momsAreaFg" width="80" align="left" is-read-only="true" data-map="momsAreaFgDataMap" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.momsCommercial"/>" binding="momsCommercial" width="80" align="left" is-read-only="true" data-map="momsCommercialDataMap" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.momsShopType"/>" binding="momsShopType" width="80" align="left" is-read-only="true" data-map="momsShopTypeDataMap" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeInfo.momsStoreManageType"/>" binding="momsStoreManageType" width="80" align="left" is-read-only="true" data-map="momsStoreManageTypeDataMap" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.storeLocation"/>" binding="storeLocation" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeInfo/storeInfo.js?ver=20221214.01" charset="utf-8"></script>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";

  var clsFg = ${ccu.getCommCodeSelect("001")};
  var sysStatFg = ${ccu.getCommCodeSelect("005")};
  var areaCd = ${ccu.getCommCodeSelect("061")};

  // List 형식("" 안붙임)
  var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
  var branchCdComboList = ${branchCdComboList};
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

</script>

<%-- 지도보기 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/mapPop.jsp">
</c:import>