<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="tableOrderKeyMapRegistView" class="subCon" style="display: none;padding: 10px 20px 40px">

    <div ng-controller="tableOrderKeyMapRegistCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="tableOrderKeyMapTab.tableOrderKeyMapRegist" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearchCls" ng-click="_pageView('tableOrderKeyMapRegistCtrl', 1)"><s:message code="cmm.search"/></button>
        </div>

        <div class="mt10 oh">
            <div class="fr">
                <div <c:choose><c:when test="${orgnFg == 'STORE' && kioskKeyEnvstVal == '0'}">style="visibility: hidden"</c:when><c:otherwise></c:otherwise></c:choose> >
                    <c:if test="${orgnFg == 'HQ'}">
                        <button class="btn_skyblue" id="btnTuClsTypeStore" ng-click="tuClsTypeStore()">
                            <s:message code="tableOrderKeyMap.tuClsTypeStore" />
                        </button>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="wj-TblWrap w25 fl">
            <div class="wj-TblWrapBr pd10" style="height:600px;">
                <div class="updownSet oh mb10 pd5" id="divBtnCls" style="visibility: hidden; height:60px;">
                    <div <c:choose><c:when test="${orgnFg == 'STORE' && kioskKeyEnvstVal == '0'}">style="visibility: hidden"</c:when><c:otherwise></c:otherwise></c:choose>>
                        <button class="btn_up" id="btnUpCls" ng-click="rowMoveUpCls()" >
                            <s:message code="cmm.up" />
                        </button>
                        <button class="btn_down" id="btnDownCls" ng-click="rowMoveDownCls()">
                            <s:message code="cmm.down" />
                        </button>
                        <br/>
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
                    <div class="wj-gridWrap" style="height:400px; overflow-x: hidden; overflow-y: hidden;">
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
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.tuClsCd"/>" binding="tuClsCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.tuClsNm"/>" binding="tuClsNm" width="140"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.clsMemo"/>" binding="clsMemo" width="90"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.storeModYn"/>" binding="storeModYn" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <input type="hidden" id="storeMod" />
            </div>
        </div>
    </div>

    <div class="wj-TblWrap w35 fl" ng-controller="tableOrderKeyMapCtrl">
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
                <button class="btn_skyblue" ng-click="excelDownload()">
                    <s:message code="cmm.excel.downCurrent"/>
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
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.tuKeyCd"/>" binding="tuKeyCd" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.prodCd"/>" binding="prodCd" width="105" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.prodNm"/>" binding="prodNm" width="180" is-read-only="true"></wj-flex-grid-column>
                        <%-- [1250 맘스터치] --%>
                        <c:if test="${momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.groupProdNm"/>" binding="groupProdNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <c:if test="${sessionInfo.orgnFg == 'STORE' and momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.kioskDisplayYn"/>" binding="kioskDisplayYn" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.soldOutYn"/>" binding="soldOutYn" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <input type="hidden" id="hdTuClsCd" />
        </div>
    </div>

    <div class="wj-TblWrap w40 fl" ng-controller="tableOrderProdCtrl">
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
                    <th><s:message code="tableOrderKeyMap.regDate" /></th><%--등록일자--%>
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
                    <th><s:message code="tableOrderKeyMap.prodCd" /></th><%--상품코드--%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
                    </td>
                    <th><s:message code="tableOrderKeyMap.prodNm" /></th><%--상품명--%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
                    </td>
                </tr>
                <tr>
                    <th><s:message code="tableOrderKeyMap.srchClass" /></th><%--분류조회--%>
                    <td>
                        <input type="text" class="sb-input w" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width:69%;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.cancel"/></button>
                    </td>
                    <th><s:message code="tableOrderKeyMap.barCd" /></th><%--바코드--%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" />
                    </td>
                </tr>
                <tr>
                    <th><s:message code="tableOrderKeyMap.useYn" /></th><%--사용여부--%>
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
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <th><s:message code="tableOrderKeyMap.prodTypeFg" /></th><%--상품유형--%>
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
                    <th><s:message code="tableOrderKeyMap.regYn" /></th><%--등록여부--%>
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
                            <button class="btn_skyblue" id="btnSearchProd" ng-click="_pageView('tableOrderProdCtrl', 1)">
                                <s:message code="cmm.search" />
                            </button>
                            <button class="btn_skyblue" id="btnRegProd" ng-click="regProd()">
                                <s:message code="tableOrderKeyMap.prodReg" />
                            </button>
                            <button class="btn_skyblue" ng-click="excelDownload()">
                                <s:message code="cmm.excel.downCurrent"/>
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
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.prodCd"/>" binding="prodCd" width="105" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.prodNm"/>" binding="prodNm" width="175" is-read-only="true"></wj-flex-grid-column>
                        <%-- [1250 맘스터치] --%>
                        <c:if test="${momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.groupProdNm"/>" binding="groupProdNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.saleUprc"/>" binding="saleUprc" width="60" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.regYn"/>" binding="regYn" align="center" data-map="regYnDataMap" width="60" is-read-only="true"></wj-flex-grid-column>
                        <c:if test="${sessionInfo.orgnFg == 'STORE' and momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.kioskDisplayYn"/>" binding="kioskDisplayYn" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.soldOutYn"/>" binding="soldOutYn" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="tableOrderKeyMap.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt10">
                <%-- id --%>
                <ul id="tableOrderProdCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/tableOrderKeyMap/tableOrderKeyMapRegist.js?ver=20250219.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 테이블오더키맵 매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/tableOrderKeyMap/tableOrderKeyMapStoreRegist.jsp">
</c:import>