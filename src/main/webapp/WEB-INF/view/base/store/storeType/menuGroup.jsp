<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div id="menuGroupView" name="menuGroupView" class="subCon" style="display: none;">

    <div ng-controller="menuGroupCtrl">
        <%-- 제목 및 조회버튼  --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeType.menuGroupManage" /></a>
            <button class="btn_blue fr mt5 mr10" id="nxBtnSearch2" ng-click="_pageView('menuGroupCtrl', 1)"><s:message code="cmm.search"/></button>
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
                <%-- 메뉴그룹명 --%>
                <th><s:message code="storeType.menuGroupNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="storeGroupNm" onkeyup="fnNxBtnSearch('2');"/>
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
            <c:if test="${subPriceFg == '1' and storeTypeApplyEnvstVal == '1'}">
                <tr>
                    <th><input type="checkbox" id="saleUprcApply" ng-model="saleUprcApply"/> <s:message code="salePrice.batchChange"/></th>
                    <td><s:message code="salePrice.saleUprcApply"/></td>
                </tr>
            </c:if>
            </tbody>
        </table>

        <%-- left (메뉴그룹관리-메뉴그룹등록 grid) --%>
        <div class="wj-TblWrap mt20 mb20 w30 fl" style="width: 275px;">
            <div class="wj-TblWrapBr mr10 pd10" style="height:710px;">
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30"><s:message code='storeType.menuGroupManage' /></span>
                    <button class="btn_skyblue" id="btnAddMenuGroup" ng-click="addMenuGroup()">
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveMenuGroup" ng-click="saveMenuGroup()">
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
                            <wj-flex-grid-column header="<s:message code="storeType.code"/>" binding="storeGroupCd" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeType.menuGroupNm"/>" binding="storeGroupNm" width="100"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="useYn" data-map="useYnDataMap"  width="62" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeType.remark"/>" binding="remark" width="100"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <input type="hidden" id="hdStoreGroupCd" />
            </div>
        </div>

    </div>

    <%-- right --%>
    <div class="wj-TblWrap fr" style="width:calc(100% - 275px);">

        <%-- 메뉴그룹관리-상품연결 grid --%>
        <div class="wj-TblWrap mt20 mb5 w50 fl" ng-controller="prodMappingCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:710px;">
                <div class="ml5">
                    <span class="bk"><s:message code='storeType.prodMapping' /></span>
                </div>
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30" id="lblMenuGroup"></span>
                    <button class="btn_skyblue" id="btnDelProdMapping" ng-click="delProdMapping()">
                        <s:message code="cmm.del" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveProdMapping" ng-click="saveProdMapping()">
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
                        <wj-flex-grid-column header="<s:message code="storeType.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.saleUprc"/>" binding="saleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.stinSaleUprc"/>" binding="stinSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.packSaleUprc"/>" binding="packSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 메뉴그룹관리-상품선택 grid --%>
        <div class="wj-TblWrap mt20 mb5 w50 fr" ng-controller="prodSelectCtrl">
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
                        <th><s:message code="storeType.regDate" /></th><%--등록일자--%>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="txtIn w110px">
                                  <wj-input-date
                                          id="srchTimeStartDate"
                                          value="startDate"
                                          ng-model="startDate"
                                          control="startDateCombo"
                                          min="2000-01-01"
                                          max="2099-12-31"
                                          initialized="_initDateBox(s)">
                                  </wj-input-date>
                                </span>
                                <span class="rg">~</span>
                                <span class="txtIn w110px">
                                  <wj-input-date
                                          id="srchTimeEndDate"
                                          value="endDate"
                                          ng-model="endDate"
                                          control="endDateCombo"
                                          min="2000-01-01"
                                          max="2099-12-31"
                                          initialized="_initDateBox(s)">
                                  </wj-input-date>
                                </span>
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
                        <th><s:message code="storeType.brand" /></th><%--브랜드--%>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                    id="srchBrand"
                                    items-source="_getComboData('srchBrand')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchBrandCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <th><s:message code="storeType.prodCd" /></th><%--상품코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchProdCd" />
                        </td>
                        <th><s:message code="storeType.prodNm" /></th><%--상품명--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchProdNm"/>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="storeType.srchClass" /></th><%--분류조회--%>
                        <td>
                            <input type="text" class="sb-input w" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width:69%;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.cancel"/></button>
                        </td>
                        <th><s:message code="storeType.barCd" /></th><%--바코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchBarCd"/>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="storeType.useYn" /></th><%--사용여부--%>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="srchUseYn"
                                        items-source="_getComboData('srchUseYn')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchUseYnCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th><s:message code="storeType.prodTypeFg" /></th><%--상품유형--%>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchProdTypeFg"
                                        items-source="_getComboData('srchProdTypeFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchProdTypeFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%--<th><s:message code="storeType.regYn" /></th>--%><%--등록여부--%>
                        <td></td>
                        <td>
                            <div class="sb-select" style="display: none;">
                                <wj-combo-box
                                        id="srchRegYn"
                                        items-source="_getComboData('srchRegYn')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchRegYnCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td class="fr">
                            <button class="btn_skyblue" id="btnSrchProd" ng-click="searchProd()">
                                <s:message code="cmm.search" />
                            </button>
                            <button class="btn_skyblue" id="btnRegProd" ng-click="regProd()">
                                <s:message code="storeType.reg" />
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
                        <wj-flex-grid-column header="<s:message code="storeType.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.saleUprc"/>" binding="saleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.stinSaleUprc"/>" binding="stinSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.packSaleUprc"/>" binding="packSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.useYn"/>" binding="useYn" width="60" data-map="useYnDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.regYn"/>" binding="regYn" width="60" data-map="regYnDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>

</div>

<script type="text/javascript">
    var useYn = ${ccu.getCommCodeExcpAll("067")};
    // 상품유형구분
    var prodTypeFg = ${ccu.getCommCode("008")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storeType/menuGroup.js?ver=20210701.11" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>