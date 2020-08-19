<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="virtualLoginAgencyCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('virtualLoginAgencyCtrl',1)">
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
                <%-- 대리점코드 --%>
                <th>
                    <s:message code="virtualLoginAgency.agencyCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchAgencyCd" ng-model="agencyCd" />
                </td>
                <%-- 대리점명 --%>
                <th>
                    <s:message code="virtualLoginAgency.agencyNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchAgencyNm" ng-model="agencyNm" />
                </td>
            </tr>
            <tr>
                <%-- 사용자아이디 --%>
                <th>
                    <s:message code="virtualLoginAgency.userId" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" />
                </td>
                <th>
                </th>
                <td>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt40 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
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
        <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
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
                <wj-flex-grid-column header="<s:message code="virtualLoginAgency.agencyFg"/>" binding="agencyFg" data-map="agencyFgDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualLoginAgency.agencyCd"/>" binding="agencyCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualLoginAgency.agencyNm"/>" binding="agencyNm" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualLoginAgency.ownerNm"/>" binding="ownerNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualLoginAgency.userId"/>" binding="userId" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualLoginAgency.empNm"/>" binding="empNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualLoginAgency.telNo"/>" binding="telNo" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualLoginAgency.mpNo"/>" binding="mpNo" width="150" is-read-only="true" align="center"></wj-flex-grid-column>

            </wj-flex-grid>

            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="virtualLoginAgencyCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>

        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="virtualLoginAgencyCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/virtualLoginAgency/virtualLoginAgency.js?ver=20200231.06" charset="utf-8"></script>