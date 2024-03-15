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
      <button class="btn_blue fr" id="nxBtnSearch" ng-click="_broadcast('storeInfoCtrl')">
        <s:message code="cmm.search"/>
      </button>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 확장조회 --%>
          <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
            <s:message code="cmm.search.addShow" />
          </button>
      </c:if>
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
      <th><s:message code="cmm.moms.storeHqBrand" /></th>
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
        <th><s:message code="cmm.moms.momsTeam"/></th>
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
        <th><s:message code="cmm.moms.momsAcShop"/></th>
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
        <th><s:message code="cmm.moms.momsAreaFg"/></th>
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
        <th><s:message code="cmm.moms.momsCommercial"/></th>
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
        <th><s:message code="cmm.moms.momsShopType"/></th>
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
        <th><s:message code="cmm.moms.momsStoreManageType"/></th>
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
        <%-- 그룹 --%>
        <th><s:message code="cmm.moms.branch"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchBranchCdCombo"
                    ng-model="branchCd"
                    items-source="_getComboData('branchCdCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    control="srchBranchCdCombo">
            </wj-combo-box>
          </div>
        </td>
        <%-- 매장그룹 --%>
        <th><s:message code="cmm.moms.momsStoreFg01"/></th>
        <td>
            <div class="sb-select">
                <wj-combo-box
                        id="srchMomsStoreFg01Combo"
                        ng-model="momsStoreFg01"
                        items-source="_getComboData('momsStoreFg01Combo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        control="srchMomsStoreFg01Combo">
                </wj-combo-box>
            </div>
        </td>
      </tr>
      <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
          <tr>
              <%-- 매장그룹2 --%>
              <th><s:message code="cmm.moms.momsStoreFg02"/></th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                              id="srchMomsStoreFg02Combo"
                              ng-model="momsStoreFg02"
                              items-source="_getComboData('momsStoreFg02Combo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="srchMomsStoreFg02Combo">
                      </wj-combo-box>
                  </div>
              </td>
              <%-- 매장그룹3 --%>
              <th><s:message code="cmm.moms.momsStoreFg03"/></th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                              id="srchMomsStoreFg03Combo"
                              ng-model="momsStoreFg03"
                              items-source="_getComboData('momsStoreFg03Combo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="srchMomsStoreFg03Combo">
                      </wj-combo-box>
                  </div>
              </td>
          </tr>
          <tr>
              <%-- 매장그룹4 --%>
              <th><s:message code="cmm.moms.momsStoreFg04"/></th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                              id="srchMomsStoreFg04Combo"
                              ng-model="momsStoreFg04"
                              items-source="_getComboData('momsStoreFg04Combo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="srchMomsStoreFg04Combo">
                      </wj-combo-box>
                  </div>
              </td>
              <%-- 매장그룹5 --%>
              <th><s:message code="cmm.moms.momsStoreFg05"/></th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                              id="srchMomsStoreFg05Combo"
                              ng-model="momsStoreFg05"
                              items-source="_getComboData('momsStoreFg05Combo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="srchMomsStoreFg05Combo">
                      </wj-combo-box>
                  </div>
              </td>
          </tr>
      </c:if>
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 480px; overflow-y: hidden; overflow-x: hidden;">
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
            <wj-flex-grid-column header="<s:message code="storeInfo.hqBrandCd"/>" binding="hqBrandCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
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
            <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" width="80" align="left" is-read-only="true" data-map="momsTeamDataMap" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" width="80" align="left" is-read-only="true" data-map="momsAcShopDataMap" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.moms.momsAreaFg"/>" binding="momsAreaFg" width="80" align="left" is-read-only="true" data-map="momsAreaFgDataMap" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.moms.momsCommercial"/>" binding="momsCommercial" width="80" align="left" is-read-only="true" data-map="momsCommercialDataMap" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.moms.momsShopType"/>" binding="momsShopType" width="80" align="left" is-read-only="true" data-map="momsShopTypeDataMap" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreManageType"/>" binding="momsStoreManageType" width="80" align="left" is-read-only="true" data-map="momsStoreManageTypeDataMap" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg01"/>" binding="momsStoreFg01" data-map="momsStoreFg01DataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg02"/>" binding="momsStoreFg02" data-map="momsStoreFg02DataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg03"/>" binding="momsStoreFg03" data-map="momsStoreFg03DataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg04"/>" binding="momsStoreFg04" data-map="momsStoreFg04DataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg05"/>" binding="momsStoreFg05" data-map="momsStoreFg05DataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            </c:if>
            <wj-flex-grid-column header="<s:message code="storeManage.storeLocation"/>" binding="storeLocation" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>

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
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
    var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
    var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
    var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
    var momsStoreFg05ComboList = ${momsStoreFg05ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeInfo/storeInfo.js?ver=20240221.01" charset="utf-8"></script>

<%-- 지도보기 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/mapPop.jsp">
</c:import>