<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon">
    <div ng-controller="orderTimeTrackingCtrl">
        <%--searchTbl--%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="orderTimeTracking.orderTimeTracking" /></a>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <%-- 조회 --%>
                <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('orderTimeTrackingCtrl',1)">
                    <s:message code="cmm.search" />
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
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date"/></th>
                <td colspan="3">
                  <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                  </div>
                </td>
              </tr>
              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                  <%-- 매장브랜드 --%>
                  <th><s:message code="orderTimeTracking.storeHqBrand"/></th>
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
                  <%-- 매장코드 --%>
                  <th><s:message code="cmm.store"/></th>
                  <td colspan="3">
                      <%-- 매장선택 모듈 사용시 include --%>
                      <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                          <jsp:param name="targetTypeFg" value="S"/>
                          <jsp:param name="targetId" value="orderTimeTrackingStore"/>
                      </jsp:include>
                      <%--// 매장선택 모듈 사용시 include --%>
                  </td>
                </tr>
              </c:if>
              <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="orderTimeTrackingStoreCd" value="${sessionInfo.storeCd}"/>
              </c:if>
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
              <th><s:message code="orderTimeTracking.momsTeam"/></th>
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
              <th><s:message code="orderTimeTracking.momsAcShop"/></th>
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
              <th><s:message code="orderTimeTracking.momsAreaFg"/></th>
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
              <th><s:message code="orderTimeTracking.momsCommercial"/></th>
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
              <th><s:message code="orderTimeTracking.momsShopType"/></th>
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
              <th><s:message code="orderTimeTracking.momsStoreManageType"/></th>
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
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <tr>
                  <%-- 그룹 --%>
                <th><s:message code="orderTimeTracking.branchCd"/></th>
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
                <td></td>
                <td></td>
              </tr>
            </c:if>
            </tbody>
          </table>
        </c:if>

        <div class="mt10 oh sb-select dkbr">
          <%-- 엑셀다운로드 --%>
          <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
        </div>

        <div class="w100 mt10">
            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.hqBrandCd"/>" binding="hqBrandCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.saleDate"/>" binding="saleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.serviceType"/>" binding="serviceType" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.channelOrderNo"/>" binding="channelOrderNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.channelType"/>" binding="channelType" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.channelService"/>" binding="channelService" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.channelRegDt"/>" binding="channelRegDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.vOrderNo"/>" binding="vorderNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.vOrderRegDt"/>" binding="vorderRegDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.orderNo"/>" binding="orderNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.posRegDt"/>" binding="posRegDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.acceptedFg"/>" binding="acceptedFg" data-map="acceptedFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.acceptedDt"/>" binding="acceptedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.cookedDt"/>" binding="cookedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.finishedDt"/>" binding="finishedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.calledDt"/>" binding="calledDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.assignedDt"/>" binding="assignedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.pickedUpDt"/>" binding="pickedUpDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.completedFg"/>" binding="completedFg" data-map="completedFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.completedDt"/>" binding="completedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.canceledFg"/>" binding="canceledFg" data-map="canceledFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.canceledDt"/>" binding="canceledDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="orderTimeTracking.canceledMemo"/>" binding="canceledMemo" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="orderTimeTrackingCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>

            </div>
            <%--//위즈모 테이블--%>
        </div>

    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="orderTimeTrackingCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="orderTimeTrackingExcelCtrl">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="excelFlex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.hqBrandCd"/>" binding="hqBrandCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.saleDate"/>" binding="saleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.serviceType"/>" binding="serviceType" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.channelOrderNo"/>" binding="channelOrderNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.channelType"/>" binding="channelType" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.channelService"/>" binding="channelService" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.channelRegDt"/>" binding="channelRegDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.vOrderNo"/>" binding="vOrderNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.vOrderRegDt"/>" binding="vOrderRegDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.orderNo"/>" binding="orderNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.posRegDt"/>" binding="posRegDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.acceptedFg"/>" binding="acceptedFg" data-map="acceptedFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.acceptedDt"/>" binding="acceptedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.cookedDt"/>" binding="cookedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.finishedDt"/>" binding="finishedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.calledDt"/>" binding="calledDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.assignedDt"/>" binding="assignedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.pickedUpDt"/>" binding="pickedUpDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.completedFg"/>" binding="completedFg" data-map="completedFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.completedDt"/>" binding="completedDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.canceledFg"/>" binding="canceledFg" data-map="canceledFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.canceledDt"/>" binding="canceledDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderTimeTracking.canceledMemo"/>" binding="canceledMemo" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
    </div>
    <%--엑셀 리스트--%>

</div>

<script type="text/javascript">

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

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/orderTimeTracking/orderTimeTracking.js?ver=20230828.03" charset="utf-8"></script>