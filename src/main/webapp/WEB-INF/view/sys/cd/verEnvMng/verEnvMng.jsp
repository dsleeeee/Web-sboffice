<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon">
    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="verEnvMng.verEnvMng"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('verCtrl')" id="nxBtnSearch">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>

    <%-- 대 그리드 --%>
    <div class="wj-TblWrap mt20 w25 fl">
        <%-- 버전 --%>
        <div ng-controller="verCtrl">
            <div class="updownSet mt5 mb5" style="font-size: 15px;">
                <span class="fl bk lh30"><s:message code='verEnvMng.ver'/></span>
                <%-- 버전추가 --%>
                <button class="btn_skyblue" id="btnVerAdd" ng-click="addRow()" style="display: none;"><s:message code='cmm.add' /></button>
                <%-- 버전저장 --%>
                <button class="btn_skyblue" id="btnVerSave" ng-click="save()" style="display: none;"><s:message code='cmm.save' /></button>
            </div>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="verEnvMng.vsCd"/>" binding="vsCd" align="left" width="70" max-length="33"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.vsNm"/>" binding="vsNm" align="left" width="100" max-length="33"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.useYn"/>" binding="useYn" align="center" width="70" data-map="useYnDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.envSet"/>" binding="envSet" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.funcSet"/>" binding="funcSet" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 중 그리드 --%>
    <div class="wj-TblWrap mt20 pdl10 w45 fl" id="divMid">

        <%-- 대표명칭 --%>
        <div class="wj-dialog-body" id="divRepresent" ng-controller="representCtrl" style="display:none;">
            <div class="updownSet mt5 mb5" style="font-size: 15px;">
                <span class="fl bk lh30"><s:message code='verEnvMng.grpGridNm'/></span>
                <%-- 대표명칭 사용여부 저장 --%>
                <button class="btn_skyblue" id="btnRepresentSave" ng-click="save()" style="display: none;"><s:message code='cmm.save' /></button>
            </div>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="vsUseYn" width="60" format="checkBoxText"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.envstCd"/>" binding="envstCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.envstNm"/>" binding="envstNm" width="130" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.envstFgNm"/>" binding="envstFg" width="140" data-map="envstFgNmDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.envstGrpCdNm"/>" binding="envstGrpCdNm" width="100" data-map="envstGrpCdNmDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.dirctInYn"/>" binding="dirctInYn" width="70" data-map="dirctInYnDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.targtFgNm"/>" binding="targtFg" data-map="targtFgDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.useYn"/>" binding="useYn" width="80" data-map="useYnDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.remark"/>" binding="remark" width="200" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.envstRemark"/>" binding="envstRemark" width="200" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

        <%-- 기능구분 --%>
        <div class="wj-dialog-body" id="divFuncFg" ng-controller="funcFgCtrl" style="display:none;">
            <div class="updownSet mt5 mb5" style="font-size: 15px;">
                <span class="fl bk lh30"><s:message code='verEnvMng.funcFg'/></span>
            </div>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="verEnvMng.funFgCd"/>" binding="nmcodeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.funcFg"/>" binding="nmcodeNm" width="180" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>

    <%-- 소 그리드 --%>
    <div class="wj-TblWrap mt20 pdl10 w30 fr" id="divSmall">

        <%-- 세부명칭 --%>
        <div class="wj-dialog-body" id="divDetail" ng-controller="detailCtrl" style="display:none;">
            <div class="updownSet mt5 mb5" style="font-size: 15px;">
                <span class="fl bk lh30"><s:message code='verEnvMng.gridNm'/></span>
                <%-- 세부명칭 초기값여부 저장 --%>
                <button class="btn_skyblue" id="btnDetailSave" ng-click="save()" style="display: none;"><s:message code='cmm.save'/></button>
            </div>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="vsDefltYn" width="70" format="checkBoxText"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.envstValCd"/>" binding="envstValCd" width="70" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.envstValNm"/>" binding="envstValNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.defltYn"/>" binding="defltYn" width="80" data-map="defltYnDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.useYn"/>" binding="useYn" width="80" data-map="useYnDataMap" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

        <%-- 기능 --%>
        <div class="wj-dialog-body" id="divFunc" ng-controller="funcCtrl" style="display:none;">
            <div class="updownSet mt5 mb5" style="font-size: 15px;">
                <span class="fl bk lh30"><s:message code='verEnvMng.func'/></span>
                <%-- 기능 사용여부 저장 --%>
                <button class="btn_skyblue" id="btnFuncSave" ng-click="save()" style="display: none;"><s:message code='cmm.save'/></button>
            </div>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="vsUseYn" width="70" format="checkBoxText"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.fnkeyNo"/>" binding="fnkeyNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.fnkeyNm"/>" binding="fnkeyNm" width="120"align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.storeFg"/>" binding="storeFg" width="80" align="center" data-map="storeKindData" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.posFg"/>" binding="posFg" width="90" align="center" data-map="posFgData" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.posiAdjYn"/>" binding="posiAdjYn" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.useYn"/>" binding="fnkeyUseYn0" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.imgFileNm"/>" binding="imgFileNm0" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.useYn"/>" binding="fnkeyUseYn1" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.imgFileNm"/>" binding="imgFileNm1" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="verEnvMng.useYn"/>" binding="fnkeyUseYn" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>--%>
            </div>
        </div>

    </div>

    <%-- 선택한 버전코드 hidden 변수 --%>
    <input type="hidden" id="hdVsCd">
    <input type="hidden" id="hdEnvstCd">
    <input type="hidden" id="hdFnkeyFg">

</div>

<script type="text/javascript">
    // 사용여부
    var useYn = ${ccu.getCommCodeExcpAll("067")};
    // 설정구분
    var envstFgNm = ${ccu.getCommCode("003")};
    // 환경그룹
    var envstGrpCdNm = ${envstGrpList};
    // 대상구분
    var targtFg = ${ccu.getCommCodeExcpAll("038")};
    // 매장종류
    var storeKind = ${ccu.getCommCodeExcpAll("057")};
    // 포스구분
    var posFg = ${ccu.getCommCodeExcpAll("027")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/cd/verEnvMng/verEnvMng.js?ver=20241029.01" charset="utf-8"></script>