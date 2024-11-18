<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />

<div id="kioskKeyMapRegistView" name="kioskKeyMapRegistView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="kioskKeyMapRegistCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="kioskKeyMap.kioskKeyMapRegist" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearchCls" ng-click="_pageView('kioskKeyMapRegistCtrl', 1)"><s:message code="cmm.search"/></button>
        </div>

        <table class="searchTbl">
            <colgroup>
                <col class="w10" />
                <col class="w90" />
            </colgroup>
            <tbody>
            <c:if test="${orgnFg == 'STORE'}">
                <tr>
                    <%-- 포스번호 --%>
                <th><s:message code="kioskKeyMap.posNo" /></th>
                <td colspan="3">
                    <div class="sb-select" style="width:200px; float:left;">
                        <wj-combo-box
                                id="posNo"
                                ng-model="posNo"
                                items-source="_getComboData('posNo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="posNoCombo"
                                selected-index-changed="setTuClsType(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </c:if>
            <tr>
                <%-- 키맵그룹 --%>
                <th><s:message code="kioskKeyMap.tuClsType" /></th>
                <td colspan="3">
                    <div class="sb-select mr5" style="width:110px; float:left;">
                        <wj-combo-box
                                id="tuClsType"
                                ng-model="tuClsType"
                                items-source="_getComboData('tuClsType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="tuClsTypeCombo">
                        </wj-combo-box>
                    </div>
                    <c:if test="${orgnFg == 'HQ'}">
                        <%-- KIOSK중분류사용 --%>
                        <div class="sb-select mr5" style="width:110px; float:left;">
                            <wj-combo-box
                                    id="tuMClsFg"
                                    ng-model="tuMClsFg"
                                    items-source="_getComboData('tuMClsFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="tuMClsFgCombo">
                            </wj-combo-box>
                        </div>
                    </c:if>
                    <div <c:choose><c:when test="${orgnFg == 'STORE' && kioskKeyEnvstVal == '0'}">style="visibility: hidden"</c:when><c:otherwise></c:otherwise></c:choose>>
                        <c:if test="${orgnFg == 'HQ' || (orgnFg == 'STORE' && kioskKeyEnvstVal == '1')}">
                            <button class="btn_skyblue" id="btnTuClsTypeAdd" ng-click="tuClsTypeAdd()">
                                <s:message code="kioskKeyMap.tuClsTypeAdd" />
                            </button>
                        </c:if>
                        <c:if test="${kioskKeyMapGrpFg == '1'}">
                            <c:if test="${orgnFg == 'HQ'}">
                                <button class="btn_skyblue" id="btnTuClsTypeCopyHq" ng-click="tuClsTypeCopy()">
                                    <s:message code="kioskKeyMap.tuClsTypeCopy" />
                                </button>
                            </c:if>
                            <c:if test="${orgnFg == 'STORE' && kioskKeyEnvstVal == '1'}">
                                <button class="btn_skyblue" id="btnTuClsTypeCopyStore" ng-click="tuClsTypeCopy()">
                                    <s:message code="kioskKeyMap.tuClsTypeCopy" />
                                </button>
                            </c:if>
                        </c:if>
                        <c:if test="${orgnFg == 'HQ'}">
                            <button class="btn_skyblue" id="btnTuClsTypeStore" ng-click="tuClsTypeStore()">
                                <s:message code="kioskKeyMap.tuClsTypeStore" />
                            </button>
                        </c:if>
                        <%-- 키오스크키맵삭제 --%>
                        <c:if test="${orgnFg == 'HQ' || (orgnFg == 'STORE' && kioskKeyEnvstVal == '1')}">
                        <button class="btn_skyblue" id="btnDelKioskKey" ng-click="kioskKeyDel()">
                            <s:message code="kioskKeyMap.kioskKeyDel" />
                        </button>
                        </c:if>
                        <button class="btn_skyblue" id="btnTuClsTypeStore" ng-click="tuRecmd()">
                            <s:message code="kioskKeyMap.tuRecmd" />
                        </button>
                        <c:if test="${orgnFg == 'HQ' || (orgnFg == 'STORE' && hqOfficeCd != '00000' && kioskKeyEnvstVal == '2')}">
                        <button class="btn_skyblue" id="btnStoreMod" ng-click="storeMod()">
                            <s:message code="kioskKeyMap.storeMod" />
                        </button>
                        </c:if>
                        <c:if test="${orgnFg == 'HQ' || (orgnFg == 'STORE' && kioskKeyEnvstVal == '1')}">
                            <button class="btn_skyblue" id="btnClsTypeNm" ng-click="clsTypeNm()">
                                <s:message code="kioskKeyMap.tuClsTypeNm" />
                            </button>
                        </c:if>
                        <c:if test="${momsEnvstVal == '1'}">
                            <button class="btn_skyblue" id="btnKioskKeyMapView" ng-click="kioskKeyMapView()">
                                <s:message code="kioskKeyMap.kioskKeyMapView" />
                            </button>
                        </c:if>
                        <c:if test="${userId == 'ds021' or userId == 'ds024' or userId == 'h0360'}">
                            <c:if test="${orgnFg == 'HQ'}">
                                <button class="btn_skyblue" id="btnHqSalePrice" ng-click="hqSalePriceView()">
                                    <s:message code="kioskKeyMap.hqSalePrice" />
                                </button>
                                <button class="btn_skyblue" id="btnStoreSalePrice" ng-click="storeSalePriceView()">
                                    <s:message code="kioskKeyMap.storeSalePrice" />
                                </button>
                            </c:if>
                            <c:if test="${orgnFg == 'STORE'}">
                                <button class="btn_skyblue" id="btnSalePrice" ng-click="salePriceView()">
                                    <s:message code="kioskKeyMap.salePrice" />
                                </button>
                            </c:if>
                        </c:if>
                       </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <button class="btn_skyblue fr mr5" id="btnEnv4069" ng-click="envConfg('4069')"><s:message code="kioskKeymap.envConfgPack"/></button>
            <button class="btn_skyblue fr mr5" id="btnEnv4068" ng-click="envConfg('4068')"><s:message code="kioskKeymap.envConfgStore"/></button>
        </div>

        <div class="wj-TblWrap mt10 mb20 w25 fl">
            <div class="wj-TblWrapBr pd10" style="height:600px;">
                <div class="mb5">
                    <label id="lbTuClsType"></label>
                    <input type="hidden" id="tuClsTypeInfo"/>
                </div>
                <div class="updownSet oh mb10 pd5" id="divBtnCls" style="visibility: hidden;">
                    <div <c:choose><c:when test="${orgnFg == 'STORE' && kioskKeyEnvstVal == '0'}">style="visibility: hidden"</c:when><c:otherwise></c:otherwise></c:choose>>
                        <button class="btn_up" id="btnUpCls" ng-click="rowMoveUpCls()" >
                            <s:message code="cmm.up" />
                        </button>
                        <button class="btn_down" id="btnDownCls" ng-click="rowMoveDownCls()">
                            <s:message code="cmm.down" />
                        </button>
                        <button class="btn_skyblue" id="btnBlankCls" ng-click="blankRowCls()" style="display: none;">
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
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true"
                                id="wjGridCategoryCls">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsCd"/>" binding="tuClsCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsNm"/>" binding="tuClsNm" width="140"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.clsMemo"/>" binding="clsMemo" width="90"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuMClsFg"/>" binding="tuMClsFg" data-map="tuMClsFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeModYn"/>" binding="storeModYn" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="" binding="indexNo" width="*"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <input type="hidden" id="hdPosNo" />
                <input type="hidden" id="hdTuClsType" />
                <input type="hidden" id="hdTuMClsFg" />
                <input type="hidden" id="storeMod" />
                <%-- 중분류그리드 --%>
                <div id="divGridCategoryClsM" style="display: none;" ng-controller="categoryClsMCtrl">
                    <div class="w100 mt10 mb20 bt">
                        <div class="mb5 mt10">
                            <label id="lbTuClsCdM"></label>
                        </div>
                        <div class="updownSet oh mb10 pd5" id="divBtnClsM" style="visibility: hidden;">
                            <button class="btn_up" id="btnUpClsM" ng-click="rowMoveUpClsM()" >
                                <s:message code="cmm.up" />
                            </button>
                            <button class="btn_down" id="btnDownClsM" ng-click="rowMoveDownClsM()">
                                <s:message code="cmm.down" />
                            </button>
                            <button class="btn_skyblue" id="btnAddClsM" ng-click="addRowClsM()">
                                <s:message code="cmm.add" />
                            </button>
                            <button class="btn_skyblue" id="btnDelClsM" ng-click="delRowClsM()">
                                <s:message code="cmm.delete" />
                            </button>
                            <button class="btn_skyblue" id="btnSaveClsM" ng-click="saveClsM()">
                                <s:message code="cmm.save" />
                            </button>
                        </div>
                        <div class="wj-gridWrap" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flexM"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true"
                                    id="wjGridCategoryClsM">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuMClsCd"/>" binding="tuMClsCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuMClsNm"/>" binding="tuMClsNm" width="140"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="kioskKeyMap.mmClsMemo"/>" binding="mmClsMemo" width="90"></wj-flex-grid-column>
                                <wj-flex-grid-column header="" binding="indexNo" width="*" visible="false"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="wj-TblWrap mt10 mb20 w35 fl" ng-controller="kioskKeyMapCtrl">
        <div class="wj-TblWrapBr ml10 pd10" style="height:600px; overflow-y: hidden;">
            <span class="fl bk lh30" id="spanTuKeyCls"></span>
            <div class="updownSet oh mb10 pd5" id="divBtnKeyMap" style="visibility: hidden;">
                <button class="btn_up" id="btnUpKeyMap" ng-click="rowMoveUpKeyMap()" >
                    <s:message code="cmm.up" />
                </button>
                <button class="btn_down" id="btnDownKeyMap" ng-click="rowMoveDownKeyMap()">
                    <s:message code="cmm.down" />
                </button>
                <button class="btn_skyblue" id="btnDelKeyMap" ng-click="delRowKeyMap()">
                    <s:message code="cmm.delete" />
                </button>
                <button class="btn_skyblue" id="btnSaveKeyMap" ng-click="saveKeyMap()">
                    <s:message code="cmm.save" />
                </button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:422px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGridKeyMap">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuKeyCd"/>" binding="tuKeyCd" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.prodCd"/>" binding="prodCd" width="105" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.prodNm"/>" binding="prodNm" width="180" is-read-only="true"></wj-flex-grid-column>
                        <%-- [1250 맘스터치] --%>
                        <c:if test="${momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.groupProdNm"/>" binding="groupProdNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <c:if test="${sessionInfo.orgnFg == 'STORE' and momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskDisplayYn"/>" binding="kioskDisplayYn" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.soldOutYn"/>" binding="soldOutYn" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.channelSoldOutYn"/>" binding="channelSoldOutYn" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="" binding="indexNo" width="*" visible="false"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <c:if test="${momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskSaleTimeSetting"/>" binding="saleTime" width="100" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
            </div>
            <input type="hidden" id="hdTuClsCd" />
        </div>
    </div>

    <div class="wj-TblWrap mt10 mb20 w40 fl" ng-controller="kioskProdCtrl">
        <div class="wj-TblWrapBr ml10 pd10" style="height:600px; overflow-y: hidden;">
            <table class="tblType01">
                <colgroup>
                    <col class="w13" />
                    <col class="w35" />
                    <col class="w13" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                    <tr>
                        <th><s:message code="kioskKeyMap.regDate" /></th><%--등록일자--%>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="txtIn"><input id="srchTimeStartDate" ng-model="startDate" class="w110px"></span>
                                <span class="rg">~</span>
                                <span class="txtIn"><input id="srchTimeEndDate" ng-model="endDate" class="w110px"></span>
                                <%--전체기간--%>
                                <span class="chk ml10">
                                  <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                                  <label for="chkDt">
                                    <s:message code="cmm.all.day" />
                                  </label>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="kioskKeyMap.prodCd" /></th><%--상품코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
                        </td>
                        <th><s:message code="kioskKeyMap.prodNm" /></th><%--상품명--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="kioskKeyMap.srchClass" /></th><%--분류조회--%>
                        <td>
                            <input type="text" class="sb-input w" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width:69%;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.cancel"/></button>
                        </td>
                        <th><s:message code="kioskKeyMap.barCd" /></th><%--바코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 사용여부 --%>
                        <th><s:message code="kioskKeyMap.useYn" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchUseYn"
                                        ng-model="useYn"
                                        control="useYnAllCombo"
                                        items-source="_getComboData('useYnAllComboData')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        selected-index="1">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%--상품유형--%>
                        <th><s:message code="kioskKeymap.prodTypeFg" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchProdTypeFg"
                                        ng-model="prodTypeFg"
                                        control="prodTypeFgAllCombo"
                                        items-source="_getComboData('prodTypeFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="kioskKeyMap.regYn" /></th><%--등록여부--%>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchRegYn"
                                        ng-model="regYn"
                                        control="regYnAllCombo"
                                        items-source="_getComboData('regYn')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <c:if test="${brandUseFg != '1' or sessionInfo.orgnFg != 'HQ'}">
                            <th></th>
                            <td></td>
                        </c:if>
                        <c:if test="${brandUseFg == '1'}">
                            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                                <%-- 상품브랜드 --%>
                                <th><s:message code="prod.prodHqBrand"/></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                            id="srchProdHqBrandCd"
                                            items-source="_getComboData('srchProdHqBrandCd')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="srchProdHqBrandCdCombo">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </c:if>
                        </c:if>
                    </tr>
                    <tr>
                        <td colspan="4" align="right">
                            <div id="divBtnProd" style="visibility: hidden;">
                                <button class="btn_skyblue" id="btnSearchProd" ng-click="_pageView('kioskProdCtrl', 1)">
                                    <s:message code="cmm.search" />
                                </button>
                                <button class="btn_skyblue" id="btnRegProd" ng-click="regProd()">
                                    <s:message code="kioskKeyMap.prodReg" />
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:290px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGridProd">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.prodCd"/>" binding="prodCd" width="105" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.prodNm"/>" binding="prodNm" width="175" is-read-only="true"></wj-flex-grid-column>
                        <%-- [1250 맘스터치] --%>
                        <c:if test="${momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.groupProdNm"/>" binding="groupProdNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.saleUprc"/>" binding="saleUprc" width="60" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.regYn"/>" binding="regYn" align="center"data-map="regYnDataMap" width="60" is-read-only="true"></wj-flex-grid-column>
                        <c:if test="${sessionInfo.orgnFg == 'STORE' and momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskDisplayYn"/>" binding="kioskDisplayYn" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.soldOutYn"/>" binding="soldOutYn" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.channelSoldOutYn"/>" binding="channelSoldOutYn" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.selfappYn"/>" binding="selfappYn" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <c:if test="${momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskSaleTimeSetting"/>" binding="saleTime" width="100" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt10">
                <%-- id --%>
                <ul id="kioskProdCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";

    // 키오스크용 포스 목록
    var kioskPosList = ${kioskPosList};

    // 키오스크 키맵그룹 목록
    var kioskTuClsTypeList = ${kioskTuClsTypeList};

    // 키오스크 키맵그룹 목록('전체' 포함)
    var kioskTuClsTypeListAll = ${kioskTuClsTypeList};
    kioskTuClsTypeListAll.unshift({name: "전체", value: ""});

    // 키오스크 키맵그룹 목록('[00]사용중인키맵매장적용' 포함)
    var kioskTuClsTypeList00 = ${kioskTuClsTypeList};
    kioskTuClsTypeList00.unshift({name: "[00]사용중인키맵매장적용", value: "00"});

    // 상품유형구분
    var prodTypeFg = ${ccu.getCommCode("008")};

    // 키오스크 키맵그룹 사용여부 0: 미사용 1: 사용
    var kioskKeyMapGrpFg = "${kioskKeyMapGrpFg}";

    // KIOSK-매장수정여부 0:미사용 1:사용
    var kioskKeyEnvstVal = "${kioskKeyEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapRegist.js?ver=20241118.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 키맵매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapStoreRegist.jsp">
</c:import>

<%-- 키맵적용(매장/포장) 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapEnv.jsp">
</c:import>

<%-- 키맵적용(매장/포장) 팝업(매장용) --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapEnvStore.jsp">
</c:import>

<%-- 키오스크키맵삭제 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyDel.jsp">
</c:import>

<%-- 추천메뉴 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskRecmd.jsp">
</c:import>

<%-- 그룹복제 팝업(매장용) --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapCopyStore.jsp">
</c:import>

<%-- 추천메뉴매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskRecmdStoreRegist.jsp">
</c:import>

<%-- 매장수정허용카테고리 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapStoreMod.jsp">
</c:import>

<%-- 매장수정허용카테고리 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapClsTypeNm.jsp">
</c:import>

<%-- 키맵미리보기 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapView.jsp">
</c:import>

<%-- 본사판매가관리 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/popUpHqSalePrice.jsp">
</c:import>

<%-- 매장판매가관리 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/popUpStoreSalePrice.jsp">
</c:import>

<%-- 판매가관리 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/popUpSalePrice.jsp">
</c:import>