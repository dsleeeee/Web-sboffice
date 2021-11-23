<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div id="storeTypeChgHistView" name="storeTypeChgHistView" class="subCon" style="display: none;">

    <div ng-controller="storeTypeChgHistCtrl">

        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeType.storeTypeChgHist" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storeTypeChgHistCtrl', 1)">
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
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"><input id="stchStartDate" name="stchStartDate" class="w110px"/></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="stchEndDate" name="stchEndDate" class="w110px"/></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 매장타입코드 --%>
                <th>
                    <s:message code="storeType.code" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="stchStoreTypeCd" ng-model="stchStoreTypeCd" />
                </td>
                <%-- 매장타입명 --%>
                <th>
                    <s:message code="storeType.typeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="stchStoreTypeNm" ng-model="stchStoreTypeNm" />
                </td>
            </tr>
            <tr>
                <th><s:message code="storeType.procFg" /></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn w150px">
                            <wj-combo-box
                                    id="stchProcFg"
                                    ng-model="procFgModel"
                                    items-source="_getComboData('stchProcFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="stchProcFgCombo"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
                <th></th>
                <td>
                </td>
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
            <%--// 페이지 스케일  --%>
            <%-- 엑셀다운로드 --%>
            <%--<button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="empCardInfo.excelDownload" /></button>--%>
        </div>

        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr mt10">
            <div id="theGridStoreType" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="storeType.code"/>" binding="storeTypeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.typeNm"/>" binding="storeTypeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="useYn" width="80" is-read-only="true" align="center" data-map="useYnDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.remark"/>" binding="remark" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.typeNm"/>" binding="bStoreTypeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="bUseYn" width="80" is-read-only="true" align="center" data-map="useYnDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.remark"/>" binding="bRemark" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.procFg"/>" binding="procFg" is-read-only="true" width="80" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.procDt"/>" binding="procDt" is-read-only="true" width="150" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.modId"/>" binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>


                </wj-flex-grid>
            </div>
        </div>
        <%--//위즈모 테이블--%>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="storeTypeChgHistCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>

</div>

<script type="text/javascript">
    var useYnData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storeType/storeTypeChgHist.js?ver=20211118.02" charset="utf-8"></script>