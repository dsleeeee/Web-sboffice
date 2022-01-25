<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="kioskOptionCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('kioskOptionCtrl',1)" id="nxBtnSearch">
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
                    <%-- 최초설치일자 --%>
                    <th>
                        <s:message code="kioskOption.date" />
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
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 상품코드 --%>
                    <th>
                        <s:message code="kioskOption.prodCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"
                    </td>
                    <%-- 상품명 --%>
                    <th>
                        <s:message code="kioskOption.prodNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
                    </td>
                </tr>
                <tr>
                    <%-- 분류조회 --%>
                    <th>
                        <s:message code="kioskOption.srchClass" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                               placeholder="<s:message code="kioskOption.srchClass" /> 선택" readonly/>
                        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                    <%-- 바코드 --%>
                    <th>
                        <s:message code="kioskOption.barCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch();"/>
                    </td>
                </tr>
                <tr>
                    <%-- 사용여부 --%>
                    <th>
                        <s:message code="kioskOption.useYn" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchUseYn"
                                ng-model="useYn"
                                items-source="_getComboData('useYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <th></th>
                    <td></td>
                </tr>
            </tbody>
        </table>

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w40 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
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
                                is-read-only="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="kioskOption.prodCd"/>" binding="prodCd" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="kioskOption.prodNm"/>" binding="prodNm" width="120" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="kioskOption.saleUprc"/>" binding="saleUprc" width="80" align="right"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="kioskOptionDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <label id="lblProdCd"></label>
            <label id="lblProdNm"></label>
            <div class="updownSet oh mb10">
                <%-- UP --%>
                <button class="btn_up" id="btnProdUp" ng-click="rowMoveUp()"><s:message code="cmm.up" /></button>
                <%-- DOWN --%>
                <button class="btn_down" id="btnProdDown" ng-click="rowMoveDown()"><s:message code="cmm.down" /></button>
                <%-- 추가 --%>
                <button class="btn_skyblue" id="btnProdAdd" ng-click="add()"><s:message code='cmm.add' /></button>
                <%-- 저장 --%>
                <button class="btn_skyblue" id="btnProdSave" ng-click="save()"><s:message code="cmm.save" /></button>
                <%-- 삭제 --%>
                <button class="btn_skyblue" id="btnProdDel" ng-click="del()"><s:message code='cmm.del' /></button>

                <c:if test="${orgnFg == 'HQ'}">
                    <%-- 옵션상품 매장적용 --%>
                    <button class="btn_skyblue" id="btnOptionProdStore" ng-click="optionProdStore()"><s:message code='kioskOption.optionProdStore' /></button>
                </c:if>

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
                        <wj-flex-grid-column header="<s:message code="kioskOption.optionProdCd"/>" binding="optionProdCd" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOption.optionProdNm"/>" binding="optionProdNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOption.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOption.optionFg"/>" binding="optionFg" data-map="optionFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskOption/kioskOption.js?ver=20220120.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 키오스크옵션 상품등록 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskOption/kioskOptionProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 키오스크옵션 옵션상품 매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskOption/kioskOptionProdStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>