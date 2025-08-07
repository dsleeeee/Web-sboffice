<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="pwdManageSaleChkCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('pwdManageSaleChkCtrl',1)">
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
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 사원구분 --%>
                <th><s:message code="pwdManage.empOrgnFg" /></th>
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
                                selected-index="2"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
            <tr style="display: none">
                <%-- 본사코드 --%>
                <th><s:message code="pwdManage.hqOfficeCd" /></th>
                <td>
                    <input type="text" id="srchHqOfficeCd" class="sb-input w100" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 본사명 --%>
                <th><s:message code="pwdManage.hqOfficeNm" /></th>
                <td>
                    <input type="text" id="srchHqOfficeNm" class="sb-input w100" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="pwdManage.storeCd" /></th>
                <td>
                    <input type="text" id="srchStoreCd" class="sb-input w100" ng-model="storeCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 매장명 --%>
                <th><s:message code="pwdManage.storeNm" /></th>
                <td>
                    <input type="text" id="srchStoreNm" class="sb-input w100" ng-model="storeNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <p class="tl s14 mt5 lh15">- 사용여부와 비밀번호 변경을 원하시면 '아이디'를 클릭 해주세요.</p>
        </c:if>
    </div>

    <%-- 회원목록 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.empOrgnFg"/>"   binding="empOrgnFg"     width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.agencyCd"/>"    binding="agencyCd"      width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.agencyNm"/>"    binding="agencyNm"      width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.hqOfficeCd"/>"  binding="hqOfficeCd"    width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.hqOfficeNm"/>"  binding="hqOfficeNm"    width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.storeCd"/>"     binding="storeCd"       width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.storeNm"/>"     binding="storeNm"       width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.userId"/>"      binding="userId"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pwdManageSaleChk.salePwdYn"/>"   binding="salePwdYn"     width="*" align="center" is-read-only="true" data-map="salePwYnDataMap"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="pwdManageSaleChkCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script>
    var useYn     = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/pwdManageSaleChk/pwdManageSaleChk.js?ver=20250805.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 비밀번호 변경 --%>
<c:import url="/WEB-INF/view/store/manage/pwdManageSaleChk/pwdChangeSaleChk.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>