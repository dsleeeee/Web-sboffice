<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="pwdManageStoreCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('pwdManageStoreCtrl',1)">
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
            <%-- 사원구분 --%>
            <th><s:message code="pwdManageStore.empOrgnFg" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchEmpOrgnFg"
                            ng-model="empOrgnFg"
                            control="empOrgnFgCombo"
                            items-source="_getComboData('empOrgnFg')"
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
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="pwdManageStore.storeCd" /></th>
            <td>
                <input type="text" id="srchStoreCd" class="sb-input w100" ng-model="srchStoreCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 매장명 --%>
            <th><s:message code="pwdManageStore.storeNm" /></th>
            <td>
                <input type="text" id="srchStoreNm" class="sb-input w100" ng-model="storeNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 사용자ID --%>
            <th><s:message code="pwdManageStore.userId" /></th>
            <td>
                <input type="text" id="srchUserId" class="sb-input w100" ng-model="srchUserId" maxlength="20" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 사용자명 --%>
            <th><s:message code="pwdManageStore.userNm" /></th>
            <td>
                <input type="text" id="srchUserNm" class="sb-input w100" ng-model="userNm" maxlength="50" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <p class="tl s14 mt5 lh15">- '사용자ID' 클릭시 '비밀번호 변경' 가능합니다.</p>
        <p class="tl s14 mt5 lh15">- '사용자명' 클릭시 '로그인 잠금해제' 가능합니다.</p>
    </div>

    <div class="mt10 oh sb-select dkbr">
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
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="pwdManageStore.empOrgnFg"/>" binding="empOrgnFg" data-map="empOrgnFgDataMap" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.hqOfficeCd"/>" binding="hqOfficeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.hqOfficeNm"/>" binding="hqOfficeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.empNo"/>" binding="empNo" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.userId"/>" binding="userId" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.userNm"/>" binding="userNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.webUseYn"/>" binding="webUseYn" data-map="webUseYnDataMap" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageStore.mpNo"/>" binding="mpNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="pwdManageStoreCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    var serviceFg = ${ccu.getCommCode("007")};
    var webUseYn = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/pwdManageStore/pwdManageStore.js?ver=20230512.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 비밀번호 변경 --%>
<c:import url="/WEB-INF/view/store/manage/pwdManage/pwdChange.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 로그인 잠금해제 --%>
<c:import url="/WEB-INF/view/store/manage/pwdManage/pwdUnlock.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>