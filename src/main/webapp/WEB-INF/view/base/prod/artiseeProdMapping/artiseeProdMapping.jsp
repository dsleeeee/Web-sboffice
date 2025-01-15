<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />

<div class="subCon" style="padding: 10px 20px 10px;">

    <div ng-controller="artiseeProdMappingCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="artiseeProdMapping.artiseeProdMapping" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('artiseeProdMappingCtrl', 1)"><s:message code="cmm.search"/></button>
        </div>

        <table class="searchTbl">
            <colgroup>
                <col class="w13" />
                <col class="w35" />
                <col class="w13" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <th><s:message code="artiseeProdMapping.regDate" /></th><%--등록일자--%>
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
                <th><s:message code="artiseeProdMapping.prodCd" /></th><%--상품코드--%>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <th><s:message code="artiseeProdMapping.prodNm" /></th><%--상품명--%>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <th><s:message code="artiseeProdMapping.srchClass" /></th><%--분류조회--%>
                <td>
                    <input type="text" class="sb-input w" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width:69%;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.cancel"/></button>
                </td>
                <th><s:message code="artiseeProdMapping.barCd" /></th><%--바코드--%>
                <td>
                    <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 사용여부 --%>
                <th><s:message code="artiseeProdMapping.useYn" /></th>
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
                <th><s:message code="artiseeProdMapping.prodTypeFg" /></th>
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
                <th><s:message code="artiseeProdMapping.regYn" /></th><%--등록여부--%>
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
            </tbody>
        </table>

        <div class="wj-TblWrap mt10 mb20 w50 fl">
            <div class="wj-TblWrapBr pd10">
                <s:message code="artiseeProdMapping.mappInfo"/>
                <div class="updownSet oh mb10">
                    <button class="btn_skyblue" id="btnDel" ng-click="delete()">
                        <s:message code="cmm.delete" />
                    </button>
                    <button class="btn_skyblue" id="btnExcelDownload" ng-click="excelDownload()">
                        <s:message code="cmm.excel.down" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:550px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGrid">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.mappingCd"/>" binding="mappingCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.prodCd"/>" binding="prodCd" width="105" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.prodNm"/>" binding="prodNm" width="80" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.grpCd"/>" binding="sdselGrpCd" width="70" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.grpNm"/>" binding="sdselGrpNm" width="60" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.mappingInfo"/>" binding="mappingInfo" width="300" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.mappingString"/>" binding="mappingString" width="200" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.mappingStringNm"/>" binding="mappingStringNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.erpProdCd"/>" binding="erpProdCd" align="center" width="105" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.erpProdNm"/>" binding="erpProdNm" width="105" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.modDt"/>"  binding="modDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.userNm"/>" binding="userNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>


    <div class="wj-TblWrap mt10 mb20 w50 fl" ng-controller="artiseeProdCtrl">
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
                    <th><s:message code="artiseeProdMapping.regDate" /></th><%--등록일자--%>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="txtIn"><input id="prodSrchTimeStartDate" ng-model="startDate" class="w110px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="prodSrchTimeEndDate" ng-model="endDate" class="w110px"></span>
                            <%--전체기간--%>
                            <span class="chk ml10">
                                  <input type="checkbox" id="prodChkDt" ng-model="isChecked" ng-change="isChkDt()" />
                                  <label for="chkDt">
                                    <s:message code="cmm.all.day" />
                                  </label>
                                </span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th><s:message code="artiseeProdMapping.prodCd" /></th><%--상품코드--%>
                    <td>
                        <input type="text" class="sb-input w100" id="prodSrchProdCd" ng-model="prodCd" />
                    </td>
                    <th><s:message code="artiseeProdMapping.prodNm" /></th><%--상품명--%>
                    <td>
                        <input type="text" class="sb-input w100" id="prodSrchProdNm" ng-model="prodNm" />
                    </td>
                </tr>
                <tr>
                    <th><s:message code="artiseeProdMapping.srchClass" /></th><%--분류조회--%>
                    <td>
                        <input type="text" class="sb-input w" id="prodSrchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width:69%;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                        <input type="hidden" id="prod_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="prodBtnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.cancel"/></button>
                    </td>
                    <th><s:message code="artiseeProdMapping.barCd" /></th><%--바코드--%>
                    <td>
                        <input type="text" class="sb-input w100" id="prodSrchBarCd" ng-model="barCd" />
                    </td>
                </tr>
                <tr>
                    <%-- 사용여부 --%>
                    <th style='display:none'><s:message code="artiseeProdMapping.useYn" /></th>
                    <td style='display:none'>
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
                    <th><s:message code="artiseeProdMapping.prodTypeFg" /></th>
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
                    <th><s:message code="artiseeProdMapping.regYn" /></th><%--등록여부--%>
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
                </tr>
                <tr>
                    <td colspan="4" align="right" style="visibility: hidden;">
                        <div id="divBtnProd">
                            <button class="btn_skyblue" id="btnSearchProd" ng-click="_pageView('artiseeProdCtrl', 1)">
                                <s:message code="cmm.search" />
                            </button>
                            <button class="btn_skyblue" id="btnRegProd" ng-click="excelDownload2()">
                                <s:message code="cmm.excel.down" />
                            </button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="mt10 mb10">
                <label id="mapStrNm"></label>
            </div>
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
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.prodCd"/>" binding="prodCd" width="105" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt10">
                <%-- id --%>
                <ul id="artiseeProdCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

            <%-- 엑셀 리스트--%>
            <div class="w100 mt10 mb20" style="display:none;" ng-controller="artiseeProdExcelCtrl">
                <div class="wj-gridWrap" style="height:290px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="excelFlex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGridProdExcel">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.prodCd"/>" binding="prodCd" width="105" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdMapping.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<script type="text/javascript">

    // 상품유형구분
    var prodTypeFg = ${ccu.getCommCode("008")};

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/artiseeProdMapping/artiseeProdMapping.js?ver=20250114.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>