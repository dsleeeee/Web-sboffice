<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="kioskKeyMapRegistView" name="kioskKeyMapRegistView" class="subCon" style="display: none;">
    <div ng-controller="kioskKeyMapRegistCtrl">
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="kioskKeyMap.kioskKeyMapRegist" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearchCls" ng-click="btnSearchCls()"><s:message code="cmm.search"/></button>
        </div>

        <table class="searchTbl">
            <colgroup>
                <col class="w10" />
                <col class="w90" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 포스번호 조회 --%>
                <th><s:message code="kioskKeyMap.posNo" /></th>
                <td colspan="3">
                    <div class="sb-select" style="width:200px; float:left;">
                        <wj-combo-box
                                id="posNoCombo"
                                ng-model="posNo"
                                control="posNoCombo"
                                items-source="_getComboData('posNoCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="wj-TblWrap mt20 mb20 w30 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:480px;">
                <div class="updownSet oh mb10">
                    <button class="btn_up" id="btnUpCls" ng-click="rowMoveUpCls()" >
                        <s:message code="cmm.up" />
                    </button>
                    <button class="btn_down" id="btnDownCls" ng-click="rowMoveDownCls()">
                        <s:message code="cmm.down" />
                    </button>
                    <button class="btn_skyblue" id="btnBlankCls" ng-click="blankRowCls()">
                        <s:message code="kioskKeyMap.blank" />
                    </button>
                    <button class="btn_skyblue" id="btnAddCls" ng-click="addRowCls()">
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnDelCls" ng-click="delRowCls()">
                        <s:message code="cmm.delete" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveCls" ng-click="saveCls()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:388px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsCd"/>" binding="tuClsCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsNm"/>" binding="tuClsNm" width="170"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="wj-TblWrap mt20 mb20 w30 fl" ng-controller="kioskKeyMapKeyCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:480px; overflow-y: hidden;">
            111

        </div>
    </div>

    <div class="wj-TblWrap mt20 mb20 w40 fr" ng-controller="kioskKeyProdCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:480px; overflow-y: hidden;">
            222

        </div>
    </div>


</div>

<script type="text/javascript">
    // 키오스크용 포스 목록
    var kioskPosList = ${kioskPosList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapRegist.js?ver=20200904.27" charset="utf-8"></script>