<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mainVerManageCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('mainVerManageCtrl', 1)" id="nxBtnSearch">
                <s:message code="cmm.search" />
            </button>
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
            <%-- 본사코드 --%>
            <th><s:message code="cmm.hqOfficeCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="srchHqOfficeCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 본사명 --%>
            <th><s:message code="cmm.hqOfficeNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="srchHqOfficeNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 등록여부 --%>
            <th><s:message code="mainVerManage.registYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchRegistYnCombo"
                            ng-model="registYn"
                            items-source="_getComboData('registYnCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchRegistYnCombo">
                    </wj-combo-box>
                </div>
            </td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>

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
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.hqOfficeNm"/>" binding="hqOfficeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mainVerManage.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mainVerManage.mainVerRegist"/>" binding="mainVerRegist" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mainVerManage.mainVerDel"/>" binding="mainVerDel" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mainVerManage.fileDesc"/>" binding="fileDesc" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mainVerManage.progFg"/>" binding="progFg" data-map="progFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 프로그램구분 --%>
    var progFgData = ${ccu.getCommCode("059")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/confg/mainVerManage/mainVerManage.js?ver=20250807.01" charset="utf-8"></script>

<%-- 메인버전 등록 팝업 --%>
<c:import url="/WEB-INF/view/pos/confg/mainVerManage/mainVerRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>