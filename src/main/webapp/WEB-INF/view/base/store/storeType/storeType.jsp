<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div id="storeTypeView" name="storeTypeView" class="subCon" style="display: none;">

    <div ng-controller="storeTypeCtrl">
        <%-- 제목 및 조회버튼  --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeType.storeTypeManage" /></a>
            <c:if test="${storeTypeAutoEnvstVal == '0'}">
                <button class="btn_blue fr mt5 mr10" id="btnApplyStore" ng-click="applyStore()"><s:message code="storeType.applyStore"/></button>
            </c:if>
            <button class="btn_blue fr mt5 mr5" id="btnSearchStoreType" ng-click="_pageView('storeTypeCtrl', 1)"><s:message code="cmm.search"/></button>
        </div>
        <%-- 조회조건 --%>
        <table class="searchTbl">
            <colgroup>
                <col class="w13" />
                <col class="w35" />
                <col class="w13" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 매장타입명 --%>
                <th><s:message code="storeType.storeTypeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="storeTypeNm"/>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="storeType.useYn" /></th>
                <td>
                    <div class="sb-select" style="width:200px;">
                        <wj-combo-box
                                ng-model="useYn"
                                items-source="_getComboData('useYnAll')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- left (매장타입관리-매장타입등록 grid) --%>
        <div class="wj-TblWrap mt20 mb20 w30 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:710px;">
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30"><s:message code='storeType.storeTypeManage' /></span>
                    <button class="btn_skyblue" id="btnAddStoreType" ng-click="addStoreType()">
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveStoreType" ng-click="saveStoreType()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:635px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="storeType.code"/>" binding="storeTypeCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeType.typeNm"/>" binding="storeTypeNm" width="150"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="useYn" data-map="useYnDataMap"  width="65"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeType.remark"/>" binding="remark" width="150"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <input type="hidden" id="hdStoreTypeCd" />
            </div>
        </div>
    </div>

    <%-- right --%>
    <div class="wj-TblWrap w70 fr">

        <%-- 매장타입관리-매장연결 grid --%>
        <div class="wj-TblWrap mt20 mb5 w50 fl" ng-controller="storeMappingCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:350px;">
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30"><s:message code='storeType.storeMapping' /></span>
                    <button class="btn_skyblue" id="btnDelStoreMapping" ng-click="delStoreMapping()">
                        <s:message code="cmm.del" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:285px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.storeCd"/>" binding="storeCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.storeNm"/>" binding="storeNm" width="180" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 매장타입관리-매장선택 grid --%>
        <div class="wj-TblWrap mt20 mb5 w50 fr" ng-controller="storeSelectCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:350px;">
                <table class="tblType01 mb10">
                    <colgroup>
                        <col class="w20" />
                        <col class="w30" />
                        <col class="w20" />
                        <col class="w30" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th><s:message code="storeType.storeCd" /></th><%--매장코드--%>
                            <td>
                                <input type="text" class="sb-input w100" id="searchStoreCd" />
                            </td>
                            <th><s:message code="storeType.storeNm" /></th><%--매장명--%>
                            <td>
                                <input type="text" class="sb-input w100" id="searchStoreNm" />
                            </td>
                        </tr>
                        <tr>
                            <%-- 매장상태 --%>
                            <th><s:message code="storeType.sysStatFg" /></th>
                            <td>
                                <div class="sb-select w100">
                                    <wj-combo-box
                                            id="searchSysStatFg"
                                            items-source="_getComboData('searchSysStatFg')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="searchSysStatFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <td></td>
                            <td class="fr">
                                <button class="btn_skyblue" id="btnSearchStore" ng-click="searchStore()">
                                    <s:message code="cmm.search" />
                                </button>
                                <button class="btn_skyblue" id="btnRegStore" ng-click="regStore()">
                                    <s:message code="storeType.reg" />
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                <div class="wj-gridWrap" style="height:250px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.storeCd"/>" binding="storeCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.storeNm"/>" binding="storeNm" width="180" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 매장타입관리-메뉴그룹연결 grid --%>
        <div class="wj-TblWrap mt5 mb20 w50 fl" ng-controller="menuGroupMappingCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:350px;">
                <div class="updownSet oh mb10 pd5">
                <span class="fl bk lh30"><s:message code='storeType.menuGroupMapping' /></span>
                <button class="btn_skyblue" id="btnDelMenuGroupMapping" ng-click="delMenuGroupMapping()">
                    <s:message code="cmm.del" />
                </button>
                </div>
                <div class="wj-gridWrap" style="height:285px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.grpCd"/>" binding="storeGroupCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.grpNm"/>" binding="storeGroupNm" width="180" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="useYn" width="65" data-map="useYnDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 매장타입관리-메뉴그룹선택 grid --%>
        <div class="wj-TblWrap mt5 mb20 w50 fr" ng-controller="menuGroupSelectCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:350px;">
                <table class="tblType01 mb10">
                    <colgroup>
                        <col class="w20" />
                        <col class="w30" />
                        <col class="w20" />
                        <col class="w30" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="storeType.grpCd" /></th><%--그룹코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="searchStoreGroupCd" />
                        </td>
                        <th><s:message code="storeType.grpNm" /></th><%--그룹명--%>
                        <td>
                            <input type="text" class="sb-input w100" id="searchStoreGroupNm" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 메뉴 사용여부 --%>
                        <th><s:message code="storeType.useYn" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                    id="searchUseYn"
                                    items-source="_getComboData('searchUseYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="searchUseYnCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td class="fr">
                            <button class="btn_skyblue" id="btnSearchMenuGroup" ng-click="searchMenuGroup()">
                                <s:message code="cmm.search" />
                            </button>
                            <button class="btn_skyblue" id="btnRegMenuGroup" ng-click="regMenuGroup()">
                                <s:message code="storeType.reg" />
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="wj-gridWrap" style="height:250px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.grpCd"/>" binding="storeGroupCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.grpNm"/>" binding="storeGroupNm" width="180" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="useYn" width="65" data-map="useYnDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    var useYn = ${ccu.getCommCodeExcpAll("067")};
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storeType/storeType.js?ver=20210628.06" charset="utf-8"></script>

<%-- 매장타입 매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/store/storeType/storeTypeApplyStore.jsp">
</c:import>