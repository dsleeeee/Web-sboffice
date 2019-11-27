<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="agencyListView" class="subCon" style="display: none;" ng-controller="agencyListCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="instl.agency.status" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('agencyListCtrl',1)">
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
                <%-- 조회일자 --%>
                <th>
                    <s:message code="instl.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
                    </div>
                </td>
            </tr>
            <tr <c:if test="${orgnFg == 'AGENCY'}">style="display: none;"</c:if> >
                <%-- 업체코드 --%>
                <th>
                    <s:message code="instl.agency.agencyCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchAgencyCd" ng-model="agencyCd" />
                </td>
                <%-- 업체명 --%>
                <th>
                    <s:message code="instl.agency.agencyNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchAgencyNm" ng-model="agencyNm" />
                </td>
            </tr>
            <tr>
                <%-- 업체구분 --%>
                <th>
                    <s:message code="instl.agencyFg" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                            id="srchAgencyFg"
                            ng-model="agencyFg"
                            items-source="_getComboData('agencyFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 사용여부 --%>
                <th>
                    <s:message code="instl.useYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                            id="srchUseYn"
                            ng-model="useYn"
                            items-source="_getComboData('useYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt40 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScaleAgency"
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
                    <wj-flex-grid-column header="<s:message code="instl.agency.agencyCd"/>" binding="agencyCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.agency.agencyNm"/>" binding="agencyNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.agencyFg"/>" binding="agencyFg" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.ownerNm"/>" binding="ownerNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.buyCnt"/>" binding="buyCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.saleCnt"/>" binding="saleCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.instCnt"/>" binding="instCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.restCnt"/>" binding="restCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.totStoreCnt"/>" binding="totStoreCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.saleStoreCnt"/>" binding="saleStoreCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.closeStoreCnt"/>" binding="closeStoreCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.regDt"/>" binding="regDt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instl.regId"/>" binding="regId" width="115" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="agencyListCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlManage/agencyList.js?ver=2019052801.49" charset="utf-8"></script>

<%-- 업체현황 상세 조회 --%>
<c:import url="/WEB-INF/view/pos/license/instlManage/agencyDtl.jsp">
</c:import>

<%-- 매장별 매출 조회 --%>
<c:import url="/WEB-INF/view/pos/license/instlManage/agencyPurchs.jsp">
</c:import>