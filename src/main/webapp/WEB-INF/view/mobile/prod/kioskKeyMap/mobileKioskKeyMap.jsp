<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileKioskKeyMapCtrl">

    <div class="searchBar">
        <a href="#" class="fl"><s:message code="mobile.kioskKeyMap"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileKioskKeyMapGrpCtrl', '4069')">
            <s:message code="mobile.kioskKeyMap.pack"/><s:message code="mobile.cmm.search"/>
        </button>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileKioskKeyMapGrpCtrl', '4068')">
            <s:message code="mobile.kioskKeyMap.store"/><s:message code="mobile.cmm.search"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 포스번호 --%>
            <th><s:message code="mobile.kioskKeyMap.posNo"/></th>
            <td>
                <div class="sb-select">
                    <div class="sb-select fl w100">
                        <wj-combo-box
                                id="posNo"
                                ng-model="posNo"
                                items-source="_getComboData('posNo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="posNoCombo"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <!-- 키맵 그룹 -->
    <div ng-controller="mobileKioskKeyMapGrpCtrl">
        <div class="mt5 mb5 oh sb-select tr">
            <span class="fl bk lh20 s14">포스번호 : <span id="searchPosNo"></span></span>
            <button class="btn_up" id="btnUpKeyMapGrp" ng-click="rowMoveUpKeyMap()" >
                <s:message code="mobile.cmm.up" />
            </button>
            <button class="btn_down" id="btnDownKeyMapGrp" ng-click="rowMoveDownKeyMap()">
                <s:message code="mobile.cmm.down" />
            </button>
            <button class="btn_skyblue ml5 fr" id="btnSaveGrp" ng-click="save()">
                <s:message code="mobile.cmm.save"/>
            </button>
        </div>

        <div class="gridBar mt10" id="mobileKioskKeyMapGrp" onclick="girdFldUnfld('mobileKioskKeyMapGrp')">
            <a href="#" class="open"><s:message code="mobile.kioskKeyMap.kioskKeyMapGrp"/></a>
        </div>
        <div class="w100" id="mobileKioskKeyMapGrpGrid" class="wj-TblWrapBr pd5" style="height: 150px;">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:150px; height: 100%;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.tuClsCd"/>" binding="envstCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.tuClsCd"/>" binding="tuClsType" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.posNo"/>" binding="posNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.tuClsCd"/>" binding="tuClsCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.tuClsNm"/>" binding="tuClsNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

                    <!-- 조회 결과가 없을 때, msg 띄우기 -->
                    <div class="gridMsg" id="mobileKioskKeyMapGrpMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <div ng-controller="mobileKioskKeyMapProdCtrl">
        <div class="mt5 mb5 oh sb-select tr">
            <button class="btn_up" id="btnUpKeyMap" ng-click="rowMoveUpKeyMapProd()" >
                <s:message code="mobile.cmm.up" />
            </button>
            <button class="btn_down" id="btnDownKeyMap" ng-click="rowMoveDownKeyMapProd()">
                <s:message code="mobile.cmm.down" />
            </button>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="saveProd()">
                <s:message code="mobile.cmm.save"/>
            </button>
        </div>

        <div class="gridBar mt10" id="mobileKioskKeyMapProd" onclick="girdFldUnfld('mobileKioskKeyMapProd')">
            <a href="#" class="open" id="kioskKeyMapProdTitle"></a>
        </div>
        <div class="w100" id="mobileKioskKeyMapProdGrid" class="wj-TblWrapBr pd5" style="height: 200px;">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:200px; height: 100%;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.tuClsCd"/>" binding="tuClsType" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.posNo"/>" binding="posNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.tuKeyCd"/>" binding="tuKeyCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.kioskKeyMap.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

                    <!-- 조회 결과가 없을 때, msg 띄우기 -->
                    <div class="gridMsg" id="mobileKioskKeyMapProdMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // 키오스크용 포스 목록
    var kioskPosList = ${kioskPosList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/prod/kioskKeyMap/mobileKioskKeyMap.js?ver=20220822.01" charset="utf-8"></script>
