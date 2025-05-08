<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="pizzaToppingCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('pizzaToppingCtrl',1)" id="nxBtnSearch">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w10" />
                <col class="w40" />
                <col class="w10" />
                <col class="w40" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 상품코드 --%>
                <th><s:message code="cmm.prodCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdCd" ng-model="srchProdCd" onkeyup="fnNxBtnSearch();" />
                </td>
                <%-- 상품명 --%>
                <th><s:message code="cmm.prodNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdNm" ng-model="srchProdNm" onkeyup="fnNxBtnSearch();" />
                </td>
            </tr>
            <tr>
                <%-- 분류조회 --%>
                <th><s:message code="cmm.prodClass"/></th>
                <td>
                    <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                           placeholder="<s:message code="cmm.prodClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="cmm.useYn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchUseYn"
                                ng-model="useYn"
                                control="useYnCombo"
                                items-source="_getComboData('useYnComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index="2">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- left --%>
        <div class="wj-TblWrap mt10 mb20 w35 fl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:530px;">
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:460px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="pizzaTopping.pathNm"/>" binding="pathNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="pizzaTopping.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="pizzaTopping.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="pizzaTopping.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="pizzaTopping.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                    <%-- 페이지 리스트 --%>
                    <div class="pageNum2 mt10">
                        <%-- id --%>
                        <ul id="pizzaToppingCtrlPager" data-size="10">
                        </ul>
                    </div>
                    <%--//페이지 리스트--%>
                </div>
            </div>
        </div>
        <%--//left--%>
    </div>

    <%--center--%>
    <div class="wj-TblWrap mt10 mb20 w30 fl" ng-controller="pizzaToppingProdCtrl">
        <div class="wj-TblWrapBr mr10 pd10" style="height:530px; overflow-y: hidden;">
            <div id="divBtnProd" style="visibility: hidden;">
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30">
                        <label id="lblProdCd"></label>
                    </span>
                    <%-- 삭제 --%>
                    <button class="btn_skyblue" id="btnPizzaToppingProdDel" ng-click="del()"><s:message code="cmm.del" /></button>
                </div>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:450px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaTopping.toppingProdCd"/>" binding="toppingProdCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaTopping.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaTopping.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaTopping.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--//center--%>

    <%--right--%>
    <div class="wj-TblWrap mt10 mb20 w35 fr" ng-controller="pizzaToppingNoProdCtrl">
        <div class="wj-TblWrapBr pd10" style="height:530px; overflow-y: hidden;">
            <table class="tblType01">
                <colgroup>
                    <col class="w13" />
                    <col class="w35" />
                    <col class="w13" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 상품코드 --%>
                    <th><s:message code="cmm.prodCd"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdCd2" ng-model="srchProdCd" />
                    </td>
                    <%-- 상품명 --%>
                    <th><s:message code="cmm.prodNm"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdNm2" ng-model="srchProdNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 분류조회 --%>
                    <th><s:message code="cmm.prodClass"/></th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchProdClassCd2" ng-model="prodClassNm" ng-click="popUpProdClass2()" style="float: left; width:60%;"
                               placeholder="<s:message code="cmm.prodClass" /> 선택" readonly/>
                        <input type="hidden" id="_prodClassCd2" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd2" style="margin-left: 5px;" ng-click="delProdClass2()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                    <%-- 사용여부 --%>
                    <th><s:message code="cmm.useYn"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchUseYn"
                                    ng-model="useYn"
                                    control="useYnCombo"
                                    items-source="_getComboData('useYnComboData2')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    selected-index="2">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" align="right">
                        <div id="divBtnProd2" style="visibility: hidden;">
                            <%-- 조회 --%>
                            <button class="btn_skyblue" id="btnArtiseeProdSpecNoProd" ng-click="_pageView('pizzaToppingNoProdCtrl', 1)"><s:message code="cmm.search" /></button>
                            <%-- 저장 --%>
                            <button class="btn_skyblue" id="btnPizzaToppingProdSave" ng-click="save()"><s:message code="cmm.save" /></button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:330px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaTopping.toppingProdCd"/>" binding="toppingProdCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaTopping.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaTopping.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaTopping.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%-- 페이지 리스트 --%>
                <div class="pageNum2 mt10">
                    <%-- id --%>
                    <ul id="pizzaToppingNoProdCtrlPager" data-size="10">
                    </ul>
                </div>
                <%--//페이지 리스트--%>
            </div>
        </div>
    </div>
    <%--//right--%>

</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnFgData = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/pizzaTopping/pizzaTopping.js?ver=20250508.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>