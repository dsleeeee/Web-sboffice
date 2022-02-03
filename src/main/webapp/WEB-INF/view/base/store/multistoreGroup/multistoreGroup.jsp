<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon">
    <div  ng-controller="multistoreGroupCtrl">

        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="multistoreGroup.title" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('multistoreGroupCtrl', 1)">
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
                <th><s:message code="multistoreGroup.groupNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="multistoreNm" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="multistoreGroup.useYn" /></th>
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
        <div class="wj-TblWrap mt20 mb20 w30 fl" style="width: 330px;">
            <div class="wj-TblWrapBr mr10 pd10" style="height:610px;">
                <div class="updownSet oh mb10 pd5">
                    <%--<span class="fl bk lh30"><s:message code='multistoreGroup.group' /></span>--%>
                    <button class="btn_skyblue" id="btnAddGroup" ng-click="addGroup()">
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveGroup" ng-click="saveGroup()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:535px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="multistoreGroup.code"/>" binding="multistoreCd" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="multistoreGroup.groupNm"/>" binding="multistoreNm" width="100"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="multistoreGroup.userId"/>" binding="multistoreUserId" width="120" <%--is-read-only="true"--%>></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="multistoreGroup.useYn"/>" binding="useYn" data-map="useYnDataMap"  width="62"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="multistoreGroup.remark"/>" binding="remark" width="100"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <input type="hidden" id="hdMultistoreCd" />
            </div>
        </div>
    </div>

    <%-- right --%>
    <div class="wj-TblWrap fr" style="width:calc(100% - 330px);">

        <%-- 그룹-매장연결 grid --%>
        <div class="wj-TblWrap mt20 mb5 w45 fl" ng-controller="multistoreMappingCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:610px;">
                <%--<div class="ml5">
                    <span class="bk"><s:message code='multistoreGroup.storeMapping' /></span>
                </div>--%>
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30" id="lblGroup"></span>
                    <button class="btn_skyblue" id="btnDelStoreMapping" ng-click="delStoreMapping()">
                        <s:message code="cmm.del" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:530px; overflow-x: hidden; overflow-y: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="multistoreGroup.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multistoreGroup.storeNm"/>" binding="storeNm" width="150" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 매장선택 grid --%>
        <div class="wj-TblWrap mt20 mb5 w55 fr" ng-controller="multistoreSelectCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:610px;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w20" />
                        <col class="w30" />
                        <col class="w20" />
                        <col class="w30" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="multistoreGroup.storeCd" /></th><%--매장코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="searchStoreCd" />
                        </td>
                        <th><s:message code="multistoreGroup.storeNm" /></th><%--매장명--%>
                        <td>
                            <input type="text" class="sb-input w100" id="searchStoreNm"/>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="multistoreGroup.sysStatFg" /></th><%--매장상태--%>
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
                                <s:message code="multistoreGroup.reg" />
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="wj-gridWrap" style="height:495px; overflow-x: hidden; overflow-y: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="multistoreGroup.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multistoreGroup.storeNm"/>" binding="storeNm" width="150" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multistoreGroup.sysStatFg"/>" binding="sysStatFg" width="70" align="center" data-map="sysStatFgDataMap" is-read-only="true"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/base/store/multistoreGroup/multistoreGroup.js?ver=20210803.05" charset="utf-8"></script>

<%-- 기능사용자 추가 --%>
<c:import url="/WEB-INF/view/base/store/multistoreGroup/multistoreUserPop.jsp">
</c:import>
