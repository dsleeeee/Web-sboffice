<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="naverMenuLinkCtrl">
    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="naverMenuLink.naverMenuLink" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('naverMenuLinkCtrl',1)">
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
            <%-- 네이버 메뉴 연동 여부 --%>
            <th><s:message code="naverMenuLink.naverMenuLinkYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="naverMenuLinkYn"
                            ng-model="naverMenuLinkYn"
                            items-source="_getComboData('naverMenuLinkYn')"
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
        </tbody>
    </table>

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

    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="naverMenuLink.posShopId"/>" binding="posShopId" width="100" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverMenuLink.optionId"/>" binding="optionId" width="100" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverMenuLink.agencyKey"/>" binding="agencyKey" width="100" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverMenuLink.optionId"/>" binding="optionIdClick" width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverMenuLink.optionNm"/>" binding="name" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverMenuLink.prodCd"/>" binding="prodCd" width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverMenuLink.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverMenuLink.sideYn"/>" binding="sideYn" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="naverMenuLinkCtrlPager" data-size="10">
        </ul>
    </div>
    <%-- //페이지 리스트 --%>

</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverMenuLink/naverMenuLink.js?ver=20260831.01" charset="utf-8"></script>

<%-- 네이버 메뉴 연동 팝업--%>
<c:import url="/WEB-INF/view/naverPlace/naverPlace/naverMenuLink/naverMenuSetting.jsp">
</c:import>
