<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="statusApprListView" class="subCon" style="display: none;">

    <div ng-controller="statusApprListCtrl">
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="storeStatus.apprList"/></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="nxBtnSearch5" ng-click="_pageView('statusApprListCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
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
                            <span class="txtIn"><input id="srchApprStartDate" class="w110px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="srchApprEndDate" class="w110px"></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 사업자번호 --%>
                    <th>
                        <s:message code="storeStatus.bizNo"/>
                    </th>
                    <td>
                        <input type="text" id="srchApprBizNo" class="sb-input w100" maxlength="15" ng-model="bizNo" onkeyup="fnNxBtnSearch('5');"/>
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="storeStatus.storeNm"/>
                    </th>
                    <td>
                        <input type="text" id="srchApprStoreNm" class="sb-input w100" ng-model="storeNm" onkeyup="fnNxBtnSearch('5');"/>
                    </td>
                </tr>
                <c:if test="${orgnFg == 'MASTER'}">
                    <tr>
                        <%-- 관리업체명 --%>
                        <th>
                            <s:message code="storeStatus.agencyNm"/>
                        </th>
                        <td>
                            <input type="text" id="srchApprAgencyNm" class="sb-input w100" maxlength="15" ng-model="agencyNm" onkeyup="fnNxBtnSearch('5');"/>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:if>
                <c:if test="${orgnFg == 'AGENCY'}">
                    <tr>
                        <%-- 업체구분 --%>
                        <th>
                            <s:message code="statusPosInstall.srchAgencyFg" />
                        </th>
                        <td>
                            <div class="sb-select" style="width:300px;">
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
                        <td></td>
                        <td></td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="listScaleAppr"
                    items-source="_getComboData('listScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="initComboBox(s)">
            </wj-combo-box>
            <%--// 페이지 스케일  --%>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10">
            <%--//위즈모 테이블--%>
            <div class="wj-gridWrap mt10" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="storeStatus.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.hqOfficeNm"/>" binding="hqOfficeNm" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.storeNm"/>" binding="storeNm" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.agencyNm"/>" binding="agencyNm" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.bizNo"/>" binding="bizNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.ownerNm"/>" binding="ownerNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.sysOpenDate"/>" binding="sysOpenDate" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.sysClosureDate"/>" binding="sysClosureDate" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <%--<wj-flex-grid-column header="<s:message code="storeStatus.vanContract"/>" binding="venTerminllNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="storeStatus.cardApprCnt"/>" binding="cardApprCnt" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.cardApprAmt"/>" binding="cardReqAmt" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.cashApprCnt"/>" binding="cashApprCnt" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.cashApprAmt"/>" binding="cashReqAmt" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="statusApprListCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="statusApprListCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>

</div>

<script type="text/javascript">
    var payColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam       = {};
    payParam.payCd     = "${payCol.payCd}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>

    var payCol    = '${payCol}';
    var arrPayCol = payCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/status/statusApprList.js?ver=20190924.05" charset="utf-8"></script>

<%-- 매장 정보조회 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusApprDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>