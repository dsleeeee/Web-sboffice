<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="mCoupnCalcCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="nxBtnSearch5" ng-click="_pageView('mCoupnCalcCtrl',1)">
            <s:message code="cmm.search"/>
        </button>
        <%-- 확장조회 --%>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <c:if test="${momsEnvstVal == '1'}">
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
            </c:if>
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
          <%-- 조회일자 --%>
          <th><s:message code="cmm.search.date"/></th>
          <td>
              <div class="sb-select">
                  <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                  <span class="rg">~</span>
                  <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
              </div>
          </td>
          <td></td>
          <td></td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <tr>
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
              <%-- 매장선택 --%>
              <th><s:message code="cmm.store.select"/></th>
              <td>
                  <%-- 매장선택 모듈 사용시 include --%>
                  <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                      <jsp:param name="targetTypeFg" value="M"/>
                      <jsp:param name="targetId" value="mCoupnCalcStore"/>
                  </jsp:include>
                  <%--// 매장선택 모듈 사용시 include --%>
              </td>
          </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="mCoupnCalcStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      <tr>
         <%-- 포스 --%>
         <th><s:message code="mCoupnCalc.pos" /></th>
         <td>
           <%-- 포스선택 모듈 멀티 선택 사용시 include --%>
             <jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
                 <jsp:param name="targetId" value="mCoupnCalcSelectPos"/>
                 <jsp:param name="targetStoreId" value="mCoupnCalcStore"/>
                 <jsp:param name="closeFunc" value=""/>
             </jsp:include>
             <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
         </td>
         <%-- 모바일쿠폰 --%>
         <th><s:message code="mCoupnCalc.mCoupn" /></th>
         <td>
             <div class="sb-select w100">
               <wj-combo-box
                 id="srchMCoupn"
                 items-source="_getComboData('srchMCoupn')"
                 display-member-path="name"
                 selected-value-path="value"
                 is-editable="false"
                 initialized="_initComboBox(s)"
                 control="srchMCoupnCombo">
               </wj-combo-box>
             </div>
         </td>
      </tr>
      <tr>
          <%-- 승인구분 --%>
          <th><s:message code="mCoupnCalc.saleFg" /></th>
          <td>
            <div class="sb-select w100">
              <wj-combo-box
                id="srchSaleFg"
                items-source="_getComboData('srchSaleFg')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)"
                control="srchSaleFgCombo">
              </wj-combo-box>
            </div>
          </td>
         <%-- 승인처리 --%>
          <th><s:message code="mCoupnCalc.apprProcFg" /></th>
          <td>
            <div class="sb-select w100">
              <wj-combo-box
                id="srchApprProcFg"
                items-source="_getComboData('srchApprProcFg')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)"
                control="srchApprProcFgCombo">
              </wj-combo-box>
            </div>
          </td>
        <%--<c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <input type="hidden" id="apprAcquireMcouponSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
          <input type="hidden" id="posAcquireMcouponSelectPosCd" value=""/>
          <input type="hidden" id="posAcquireMcouponSelectPosName" value=""/>
          <input type="hidden" id="apprAcquireMcouponSelectCornerCd" value=""/>
          <input type="hidden" id="apprAcquireMcouponSelectCornrName" value=""/>--%>
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
        <%-- 조회조건 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload('1')"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    id="wjGridmCoupnCalc"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.branchCd"/>" binding="branchCd" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.branchNm"/>" binding="branchNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.brandCd"/>" binding="brandCd" data-map="brandCdDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="mCoupnCalc.coupnCd"/>" binding="mcoupnCd" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnCalc.coupnNm"/>" binding="mcoupnNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnCalc.totCnt"/>" binding="cnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnCalc.apprAmt"/>" binding="apprAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnCalc.totCnt"/>" binding="cntOk" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnCalc.apprAmt"/>" binding="apprAmtOk" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnCalc.totCnt"/>" binding="cntCancel" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnCalc.apprAmt"/>" binding="apprAmtCancel" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <ul id="mCoupnCalcCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%-- 엑셀 그리드 --%>
    <div class="w100 mt10 mb20" ng-controller="mCoupnCalcExcelCtrl" style="display: none;">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                        id="wjGridmCoupnCalcExcel"
                        autoGenerateColumns="false"
                        control="excelFlex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                    <!-- define columns -->
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <wj-flex-grid-column header="<s:message code="mCoupnCalc.branchCd"/>" binding="branchCd" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mCoupnCalc.branchNm"/>" binding="branchNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </c:if>
                        <wj-flex-grid-column header="<s:message code="mCoupnCalc.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mCoupnCalc.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <wj-flex-grid-column header="<s:message code="mCoupnCalc.brandCd"/>" binding="brandCd" data-map="brandCdDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.coupnCd"/>" binding="mcoupnCd" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.coupnNm"/>" binding="mcoupnNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.totCnt"/>" binding="cnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.apprAmt"/>" binding="apprAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.totCnt"/>" binding="cntOk" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.apprAmt"/>" binding="apprAmtOk" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.totCnt"/>" binding="cntCancel" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mCoupnCalc.apprAmt"/>" binding="apprAmtCancel" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";

    // 모바일쿠폰
    var mCoupnFg = ${mCoupnComboList};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

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

<script type="text/javascript" src="/resource/solbipos/js/sale/status/mCoupnCalc/mCoupnCalc.js?ver=20240221.01" charset="utf-8"></script>

<%-- 모바일쿠폰 정산 상세화면 팝업 --%>
<c:import url="/WEB-INF/view/sale/status/mCoupnCalc/mCoupnCalcDtl.jsp">
</c:import>