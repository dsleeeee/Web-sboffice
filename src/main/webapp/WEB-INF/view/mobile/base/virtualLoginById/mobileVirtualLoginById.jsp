<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="mobileVirtualLoginByIdCtrl">

    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="mobile.virtualLoginById"/></a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button id="nxBtnSearch" class="btn_blue fr" ng-click="_pageView('mobileVirtualLoginByIdCtrl',1)">
                <s:message code="cmm.search"/>
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
            <%-- 본사코드 --%>
            <th><s:message code="mobile.virtualLoginById.hqOfficeCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();" />
            </td>
            <%-- 본사명 --%>
            <th><s:message code="mobile.virtualLoginById.hqOfficeNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();" />
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="mobile.virtualLoginById.storeCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
            </td>
            <%-- 매장명 --%>
            <th><s:message code="mobile.virtualLoginById.storeNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
            </td>
        </tr>
        <tr>
            <%-- 사용자아이디 --%>
            <th>
                <s:message code="mobile.virtualLoginById.userId"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 사용자아이디 --%>
            <th>
                <s:message code="mobile.virtualLoginById.userNm"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchUserNm" ng-model="userNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 용도 --%>
            <th><s:message code="mobile.virtualLoginById.clsFgNm" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchClsFg"
                            ng-model="clsFg"
                            control="clsFgCombo"
                            items-source="_getComboData('srchClsFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 매장상태 --%>
            <th><s:message code="mobile.virtualLoginById.sysStatFgNm" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchStatFg"
                            ng-model="sysStatFg"
                            control="statFgCombo"
                            items-source="_getComboData('srchStatFg')"
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

    <div id="grid" class="w100">

        <div class="mt10 oh sb-select dkbr">
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
        </div>

        <div class="wj-gridWrap mt10" style="height:450px; overflow-y: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.hqOfficeNm"/>" binding="hqOfficeNm"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.hqUserId"/>" binding="hqUserId" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.storeCd"/>" binding="storeCd" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.storeNm"/>" binding="storeNm" width="200"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.userId"/>" binding="userId" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.userNm"/>" binding="userNm" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.clsFgNm"/>" binding="clsFgNm" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.sysStatFgNm"/>" binding="sysStatFgNm" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.ownerNm"/>" binding="ownerNm" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.telNo"/>" binding="telNo" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.mpNo"/>" binding="mpNo" align="center" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.agencyNm"/>" binding="agencyNm" width="100" align="center" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.sysOpenDate"/>" binding="sysOpenDate" width="110" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.sysClosureDate"/>" binding="sysClosureDate" width="110" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.virtualLoginById.storeModYn"/>" binding="storeModYn" width="110" align="center"></wj-flex-grid-column>
                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="mobileVirtualLoginByIdCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>

            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="mobileVirtualLoginByIdCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    var clsFg = ${ccu.getCommCode("001")};
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/base/virtualLoginById/mobileVirtualLoginById.js?ver=20250712.01" charset="utf-8"></script>