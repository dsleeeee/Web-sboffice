<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="saleStoreListView" class="subCon" style="display: none;" ng-controller="saleStoreListCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="oper.saleStoreList" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('saleStoreListCtrl',1)">
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
            <tr>
                <%-- 시스템등록 일자 --%>
                <th>
                    <s:message code="oper.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn w110px">
                            <wj-input-date
                                value="startDate"
                                ng-model="startDate"
                                control="startDateCombo"
                                min="2018-01-01"
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
                                min="2018-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <span class="chk ml10" style="display:none">
                          <input type="checkbox" id="chkDt" ng-model="isChecked" />
                          <label for="chkDt">
                            <s:message code="oper.noSaleStore" />
                          </label>
                        </span>
                    </div>
                </td>
            </tr>
            <%--<tr <c:if test="${orgnFg == 'AGENCY'}">style="display: none;"</c:if> >--%>
            <tr>
                <%-- 관리밴사 --%>
                <th>
                    <s:message code="oper.manageVan" />
                </th>
                <td>
                    <input type="text" id="manageVanNm" value="선택" class="sb-input w100" ng-readonly="true" ng-click="searchManageVan()">
                    <input type="hidden" id="ssl_srchManageVanCd" ng-hide="true">
                </td>
                <c:if test="${orgnFg == 'MASTER'}">
                    <%-- 관리업체 --%>
                    <th>
                        <s:message code="oper.agency" />
                    </th>
                    <td>
                        <input type="text" id="agencyNm" value="선택" class="sb-input w100" ng-readonly="true" ng-click="searchAgency()">
                        <input type="hidden" id="ssl_srchAgencyCd" ng-hide="true">
                    </td>
                </c:if>
                <c:if test="${orgnFg == 'AGENCY'}">
                    <th></th>
                    <td></td>
                </c:if>
            </tr>
            <tr>
                <%-- 본사코드 --%>
                <th>
                    <s:message code="oper.hqOfficeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
                </td>
                <%-- 본사명 --%>
                <th>
                    <s:message code="oper.hqOfficeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th>
                    <s:message code="oper.storeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                </td>
                <%-- 매장명 --%>
                <th>
                    <s:message code="oper.storeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt40 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScaleSale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="oper.hqOfficeCd"/>" binding="hqOfficeCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.hqOfficeNm"/>" binding="hqOfficeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.storeCd"/>" binding="storeCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.storeNm"/>" binding="storeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.agency"/>" binding="agencyNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.posCnt"/>" binding="posCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.saleFg"/>" binding="saleFg" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.saleFgN"/>" binding="saleFgN" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.saleFgY"/>" binding="saleFgY" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.cardCnt"/>" binding="cardCnt" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.cashCnt"/>" binding="cashCnt" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.cashRecCnt"/>" binding="cashRecCnt" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.maxInstInsDt"/>" binding="maxInstInsDt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.sysOpenDate"/>" binding="sysOpenDate" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.maxSaleDate"/>" binding="maxSaleDate" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="saleStoreListCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    <%-- 상태구분 --%>
    var sysStatFgData = ${ccu.getCommCodeExcpAll("005")};

    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/oper/saleStoreList.js?ver=20201116.02" charset="utf-8"></script>

<%-- 대리점 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>

<%-- 관리밴사 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchVan.jsp">
</c:import>