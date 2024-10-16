<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="artiseeProdSpecCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('artiseeProdSpecCtrl',1)">
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
            </tbody>
        </table>

        <%-- left --%>
        <div class="wj-TblWrap mt10 mb20 w30 fl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:590px;">
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:540px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
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
                                <wj-flex-grid-column header="<s:message code="artiseeProdSpec.specCd"/>" binding="specCd" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="artiseeProdSpec.specNm"/>" binding="specNm" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="artiseeProdSpec.prodCnt"/>" binding="prodCnt" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--center--%>
    <div class="wj-TblWrap mt10 mb20 w30 fl" ng-controller="artiseeProdSpecProdCtrl">
        <div class="wj-TblWrapBr mr10 pd10" style="height:590px; overflow-y: hidden;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30">
                    <label id="lblSpecCd"></label>
                    <label id="lblSpecNm"></label>
                </span>
                <%-- 삭제 --%>
                <button class="btn_skyblue" id="btnArtiseeProdSpecDel" ng-click="del()"><s:message code='cmm.del' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:510px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdSpec.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdSpec.prodNm"/>" binding="prodNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--center--%>

    <%--right--%>
    <div class="wj-TblWrap mt10 mb20 w40 fr" ng-controller="artiseeProdSpecNoProdCtrl">
        <div class="wj-TblWrapBr pd10" style="height:590px; overflow-y: hidden;">
            <table class="tblType01 mb10">
                <colgroup>
                    <col class="w13" />
                    <col class="w35" />
                    <col class="w13" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 등록일자 --%>
                    <th><s:message code="artiseeProdSpec.regDate" /></th>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px"></span>
                            <%-- 전체기간 --%>
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
                    <%-- 상품코드 --%>
                    <th><s:message code="cmm.prodCd" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
                    </td>
                    <%-- 상품명 --%>
                    <th><s:message code="cmm.prodNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 분류조회 --%>
                    <th><s:message code="cmm.prodClass" /></th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                               placeholder="<s:message code="cmm.prodClass" /> 선택" readonly/>
                        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                    <%-- 사용여부 --%>
                    <th><s:message code="cmm.useYn" /></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchUseYnAllCombo"
                                    ng-model="useYn"
                                    items-source="_getComboData('useYnAllCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchUseYnAllCombo"
                                    selected-index="1">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" align="right">
                        <%-- 조회 --%>
                        <button class="btn_skyblue" id="btnArtiseeProdSpecNoProd" ng-click="_pageView('artiseeProdSpecNoProdCtrl', 1)"><s:message code="cmm.search" /></button>
                        <%-- 등록 --%>
                        <button class="btn_skyblue" id="btnArtiseeProdSpecAdd" ng-click="add()"><s:message code='artiseeProdSpec.regist' /></button>
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
                        <wj-flex-grid-column header="<s:message code="artiseeProdSpec.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="artiseeProdSpec.prodNm"/>" binding="prodNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt10">
                <%-- id --%>
                <ul id="artiseeProdSpecNoProdCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/artiseeProdSpec/artiseeProdSpec.js?ver=20241015.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>