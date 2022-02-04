<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="prodRecpOriginView" class="subCon" style="display: none;">

    <div ng-controller="prodRecpOriginCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="recpOriginTab.prodRecpOrigin" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue" id="btnRegist" ng-click="openOriginReg()">
                    <s:message code="prodRecpOrigin.originReg" />
                </button>
                <button class="btn_blue mr3" id="nxBtnSearch1"  ng-click="_pageView('prodRecpOriginCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 등록일자 --%>
                <th>
                    <s:message code="prodRecpOrigin.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                    <span class="txtIn w110px">
                        <wj-input-date
                                value="startDate"
                                ng-model="startDate"
                                control="startDateCombo"
                                min="2018-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                        </wj-input-date>
                    </span>
                        <span class="rg">~</span>
                        <span class="txtIn w110px">
                        <wj-input-date
                                value="endDate"
                                ng-model="endDate"
                                control="endDateCombo"
                                min="2018-01-01"
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
                </td>
            </tr>
            <tr>
                <%-- 상품코드 --%>
                <th>
                    <s:message code="prodRecpOrigin.prodCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 상품명 --%>
                <th>
                    <s:message code="prodRecpOrigin.prodNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 분류조회 --%>
                <th>
                    <s:message code="prodRecpOrigin.srchClass" />
                </th>
                <td>
                    <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                           placeholder="<s:message code="prodRecpOrigin.srchClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <%-- 바코드 --%>
                <th>
                    <s:message code="prodRecpOrigin.barCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 사용여부 --%>
                <th>
                    <s:message code="prodRecpOrigin.useYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchUseYnCombo"
                                ng-model="useYn"
                                items-source="_getComboData('useYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 원산지등록 사용여부 --%>
                <th>
                    <s:message code="prodRecpOrigin.recpOriginUseYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchRecpOriginUseYnCombo"
                                ng-model="recpOriginUseYn"
                                items-source="_getComboData('recpOriginUseYnCombo')"
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

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w30 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                    <div class="updownSet oh mb10">
                            <%-- 저장 --%>
                        <button class="btn_skyblue" id="btnProdRecpOriginSave" ng-click="prodRecpOriginSave()"><s:message code='cmm.save' /></button>
                    </div>
                </c:if>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="prodRecpOrigin.pathNm"/>" binding="pathNm" width="120" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prodRecpOrigin.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prodRecpOrigin.prodNm"/>" binding="prodNm" width="120" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prodRecpOrigin.recpOriginCnt"/>" binding="recpOriginCnt" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                                <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                                    <wj-flex-grid-column header="<s:message code="prodRecpOrigin.hqBrandNm"/>" binding="hqBrandCdCombo" data-map="hqBrandFgMap" width="80" align="center"></wj-flex-grid-column>
                                </c:if>

                                <%--팝업 조회시 필요--%>
                                <wj-flex-grid-column header="<s:message code="prodRecpOriginAdd.hqBrandCd"/>" binding="hqBrandCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%-- mid --%>
    <div class="wj-TblWrap mt20 mb20 w35 fl" ng-controller="prodRecpOriginDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <div class="mb10" style="height: 15px;">
                <label id="lblProd" style="font-size: 15px;"></label>
            </div>
            <div class="updownSet">
                <%-- UP --%>
                <button class="btn_up" id="btnProdUp" ng-click="rowMoveUp()"><s:message code="cmm.up" /></button>
                <%-- DOWN --%>
                <button class="btn_down" id="btnProdDown" ng-click="rowMoveDown()"><s:message code="cmm.down" /></button>
                <%-- 저장 --%>
                <button class="btn_skyblue" id="btnProdRecpOriginDetailSave" ng-click="save()"><s:message code='cmm.save' /></button>
                <%-- 삭제 --%>
                <button class="btn_skyblue" id="btnProdRecpOriginDetailDel" ng-click="del()"><s:message code='cmm.del' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="prodRecpOrigin.hqBrandNm"/>" binding="hqBrandNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="prodRecpOrigin.recipesCd"/>" binding="recipesCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodRecpOrigin.recipesNm"/>" binding="recipesNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodRecpOrigin.orgplceNm"/>" binding="orgplceNm" width="200" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodRecpOrigin.recpSeq"/>" binding="recpSeq" width="70" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%-- mid --%>

    <%-- right --%>
    <div class="wj-TblWrap mt20 mb20 w35 fr" ng-controller="prodRecpOriginRegCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">

            <table class="tblType01">
                <colgroup>
                    <col class="w13" />
                    <col class="w35" />
                    <col class="w13" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%--재료명--%>
                    <th><s:message code="prodRecpOrigin.recipesNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="recipesNm" />
                    </td>
                    <%--원산지명--%>
                    <th><s:message code="prodRecpOrigin.orgplceNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="orgplceNm" />
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="updownSet oh mb10">
                <%-- 조회 --%>
                <button class="btn_skyblue" ng-click="btnSearch()"><s:message code='cmm.search' /></button>
                <%-- 추가 --%>
                <button class="btn_skyblue" id="btnProdRecpOriginDetailAdd" ng-click="add()"><s:message code='cmm.add' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
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
                        <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="prodRecpOrigin.hqBrandNm"/>" binding="hqBrandNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="prodRecpOrigin.recipesCd"/>" binding="recipesCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodRecpOrigin.recipesNm"/>" binding="recipesNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodRecpOrigin.orgplceNm"/>" binding="orgplceNm" width="200" is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%-- right --%>

</div>

<script type="text/javascript">
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/recpOrigin/prodRecpOrigin.js?ver=20210327.05" charset="utf-8"></script>

<%-- 원산지신규등록 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/recpOrigin/recpOriginReg.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>