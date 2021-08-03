<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon">
    <div  ng-controller="multiStoreGroupCtrl">

        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="multiStoreGroup.title" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('multiStoreGroupCtrl', 1)">
                <s:message code="cmm.search" />
            </button>
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
                <%-- 그룹명 --%>
                <th><s:message code="multiStoreGroup.groupNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="multistoreNm"/>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="multiStoreGroup.useYn" /></th>
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

        <%-- left (그룹 grid) --%>
        <div class="wj-TblWrap mt20 mb20 w30 fl" style="width: 350px;">
            <div class="wj-TblWrapBr mr10 pd10" style="height:710px;">
                <div class="updownSet oh mb10 pd5">
                    <%--<span class="fl bk lh30"><s:message code='multiStoreGroup.group' /></span>--%>
                    <button class="btn_skyblue" id="btnAddGroup" ng-click="addGroup()">
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveGroup" ng-click="saveGroup()">
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
                            <wj-flex-grid-column header="<s:message code="multiStoreGroup.code"/>" binding="multistoreCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="multiStoreGroup.groupNm"/>" binding="multistoreNm" width="150"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="multiStoreGroup.useYn"/>" binding="useYn" data-map="useYnDataMap"  width="65"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="multiStoreGroup.remark"/>" binding="remark" width="150"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <input type="hidden" id="hdMultiStoreCd" />
            </div>
        </div>
    </div>

    <%-- right --%>
    <div class="wj-TblWrap fr" style="width:calc(100% - 350px);">

        <%-- 그룹-매장연결 grid --%>
        <div class="wj-TblWrap mt20 mb5 w50 fl" ng-controller="multiStoreMappingCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:710px;">
                <%--<div class="ml5">
                    <span class="bk"><s:message code='multiStoreGroup.storeMapping' /></span>
                </div>--%>
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30" id="lblGroup"></span>
                    <button class="btn_skyblue" id="btnDelStoreMapping" ng-click="delStoreMapping()">
                        <s:message code="cmm.del" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveStoreMapping" ng-click="saveStoreMapping()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:620px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multiStoreGroup.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multiStoreGroup.storeNm"/>" binding="storeNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multiStoreGroup.userId"/>" binding="multistoreUserId" width="100" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 매장선택 grid --%>
        <div class="wj-TblWrap mt20 mb5 w50 fr" ng-controller="multiStoreSelectCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:710px;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w13" />
                        <col class="w35" />
                        <col class="w13" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="multiStoreGroup.storeCd" /></th><%--매장코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="searchStoreCd" />
                        </td>
                        <th><s:message code="multiStoreGroup.storeNm" /></th><%--매장명--%>
                        <td>
                            <input type="text" class="sb-input w100" id="searchStoreNm"/>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="multiStoreGroup.sysStatFg" /></th><%--매장상태--%>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="sysStatFg"
                                        items-source="_getComboData('sysStatFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="sysStatFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td class="fr">
                            <button class="btn_skyblue" id="btnSearchStore" ng-click="searchStore()">
                                <s:message code="cmm.search" />
                            </button>
                            <button class="btn_skyblue" id="btnRegStore" ng-click="regStore()">
                                <s:message code="multiStoreGroup.reg" />
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="wj-gridWrap" style="height:430px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multiStoreGroup.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multiStoreGroup.storeNm"/>" binding="storeNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multiStoreGroup.userId"/>" binding="multistoreUserId" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multiStoreGroup.sysStatFg"/>" binding="sysStatFg" width="80" align="center" data-map="sysStatFgDataMap" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>

</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYn = ${ccu.getCommCodeExcpAll("067")};
    <%-- 상태구분 --%>
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/multiStoreGroup/multiStoreGroup.js?ver=20210730.01" charset="utf-8"></script>

