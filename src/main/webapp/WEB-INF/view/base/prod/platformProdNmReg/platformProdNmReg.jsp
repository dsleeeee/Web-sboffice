<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon">
    <div ng-controller="platformProdNmRegCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="platformProdNmReg.platformProdNmReg"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('platformProdNmRegCtrl', 1)">
                    <s:message code="cmm.search"/>
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 상품코드 --%>
                <th><s:message code="cmm.prodCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 상품명 --%>
                <th><s:message code="cmm.prodNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 상품분류 --%>
                <th><s:message code="platformProdNmReg.prodClass"/></th>
                <td>
                    <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                           placeholder="<s:message code="cmm.all" />" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="platformProdNmReg.useYn" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="useYn"
                                ng-model="useYn"
                                items-source="_getComboData('useYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchUseYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 플랫폼조회 --%>
                <th><s:message code="platformProdNmReg.platformFg" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="platformFg"
                                ng-model="platformFg"
                                items-source="_getComboData('platformFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchPlatformFgCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 상품브랜드 --%>
                    <th><s:message code="cmm.moms.prodHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdHqBrandCombo"
                                    ng-model="prodHqBrandCd"
                                    items-source="_getComboData('prodHqBrandCdCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdHqBrandCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            </tbody>
        </table>

        <div class="updownSet oh mt10">
            <%-- 초기화 --%>
            <button class="btn_skyblue" ng-click="resetRow()"><s:message code="platformProdNmReg.reset"/></button>
            <%-- 저장 --%>
            <button class="btn_skyblue" ng-click="saveRow()"><s:message code="cmm.save"/></button>
        </div>

        <%-- 위즈모 테이블 --%>
        <div class="wj-TblWrap mt5">
            <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        control="flex"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        initialized="initGrid(s,e)"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.hqBrandNm"/>" binding="hqBrandNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.prodClassCd"/>" binding="prodClassCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.prodClassNm"/>" binding="prodClassNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.prodCd"/>" binding="prodCd" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.saleUprc"/>" binding="saleUprc" align="right" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.prodNm"/>" binding="prodNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.pos"/><s:message code="cmm.prodNm"/>1" binding="posProdNm1" align="left" width="200"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.pos"/><s:message code="cmm.prodNm"/>2" binding="posProdNm2" align="left" width="200"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.kiosk"/><s:message code="cmm.prodNm"/>1" binding="kioskProdNm1" align="left" width="200"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.kiosk"/><s:message code="cmm.prodNm"/>2" binding="kioskProdNm2" align="left" width="200"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="platformProdNmReg.useYn"/>" binding="useYn" align="center" width="80" is-read-only="true" data-map="useYnDataMap"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>


<script type="text/javascript">
    //var orgnFg = "${orgnFg}";
    // 사용여부
    var useYn = ${ccu.getCommCode("067")};

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/platformProdNmReg/platformProdNmReg.js?ver=20260721.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>