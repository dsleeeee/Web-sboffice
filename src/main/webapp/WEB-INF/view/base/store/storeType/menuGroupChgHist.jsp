<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div id="menuGroupChgHistView" name="menuGroupChgHistView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="menuGroupChgHistCtrl">

        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeType.menuGroupChgHist" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch4" ng-click="_pageView('menuGroupChgHistCtrl', 1)">
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
            <%-- 변경 일자 --%>
            <tr>
                <th><s:message code="storeType.chgDate" /></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="mgchStartDate" name="mgchStartDate" class="w110px"/></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="mgchEndDate" name="mgchEndDate" class="w110px"/></span>
                    </div>
                </td>
                <th><s:message code="storeType.procFg" /></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn w150px">
                            <wj-combo-box
                                    id="mgchProcFg"
                                    ng-model="procFgModel"
                                    items-source="_getComboData('mgchProcFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="mgchProcFgCombo"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 메뉴그룹코드 --%>
                <th>
                    <s:message code="storeType.code" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="mgchMenuGroupCd" ng-model="mgchMenuGroupCd" onkeyup="fnNxBtnSearch('4');"/>
                </td>
                <%-- 메뉴그룹명 --%>
                <th>
                    <s:message code="storeType.menuGroupNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="mgchMenuGroupNm" ng-model="mgchMenuGroupNm" onkeyup="fnNxBtnSearch('4');"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
        </div>

        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr mt10">
            <div id="theGridMenuGroup" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="storeType.storeGroupCd"/>" binding="storeGroupCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.menuGroupNm"/>" binding="storeGroupNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="useYn" width="80" is-read-only="true" align="center" data-map="useYnDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.remark"/>" binding="remark" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="bUseYn" width="80" is-read-only="true" align="center" data-map="useYnDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.remark"/>" binding="bRemark" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.procFg"/>" binding="procFg" is-read-only="true" width="100" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.procDt"/>" binding="procDt" is-read-only="true" width="150" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.modId"/>" binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%--//위즈모 테이블--%>

    </div>

</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storeType/menuGroupChgHist.js?ver=20230807.01" charset="utf-8"></script>