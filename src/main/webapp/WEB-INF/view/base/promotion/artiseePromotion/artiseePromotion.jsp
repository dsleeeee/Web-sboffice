<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="modPromotionEnvstVal" value="${modPromotionEnvstVal}" />

<%-- 리스트 영역 --%>
<div class="subCon" ng-controller="artiseePromotionCtrl" style="padding-bottom: 0;">
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="fl"><s:message code="artiseePromotion.artiseePromotion"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue" id="btnRegist" ng-click="openPromotionReg()" <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if>>
                <s:message code="cmm.new.add" />
            </button>
            <button class="btn_blue mr3" id="btnSearch" ng-click="_pageView('artiseePromotionCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <table class="searchTbl">
            <colgroup>
                <col class="w10" />
                <col class="w20" />
                <col class="w10" />
                <col class="w20" />
                <col class="w10" />
                <col class="w20" />
            </colgroup>
            <tbody>
            <%-- 등록 일자 --%>
            <tr>
                <%-- 프로모션명 --%>
                <th><s:message code="artiseePromotion.promotionNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="promotionNm" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="artiseePromotion.useYn" /></th>
                <td>
                    <div class="sb-select w100">
                        <wj-combo-box
                                ng-model="useYn"
                                items-source="_getComboData('useYnAll')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 행사일 --%>
                <th><s:message code="artiseePromotion.promotionDate" /></th>
                <td>
                    <div class="sb-select">
                        <div style="float: left; padding: 5px 10px 5px 5px;">
                            <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()"/>
                        </div>
                        <span class="txtIn w150px">
                            <input id="promotionDate">
                        </span>
                    </div>
                </td>
            </tr>
            </tr>
        </tbody>
    </table>
    <%--//searchTbl--%>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div class="wj-gridWrap" style="height: 150px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="" binding="hqOfficeCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.promotionCd"/>" binding="promotionCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.promotionNm"/>" binding="promotionNm" width="250" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.memo"/>" binding="memo" width="250" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.startDate"/>" binding="startYmd"  width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.endDate"/>" binding="endYmd"  width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.promoCompFg"/>" binding="promoCompFg" data-map="promoCompFgDataMap" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.promoTypeFg"/>" binding="promoTypeFg" data-map="promoTypeFgDataMap" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="artiseePromotion.prodTypeFg"/>" binding="prodTypeFg" width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>
</div>

<%-- 등록 영역 --%>
<div class="subCon" id="promotionReg" style="display: none;" ng-controller="artiseePromotionRegCtrl">

    <%-- 마스터 등록 영역 --%>
    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr style="border-top: 1px solid #CCCCCC;">
            <th colspan="2">
                <span style="font-size: 13px;"><s:message code="artiseePromotion.regPromotion"/></span>
            </th>
            <th colspan="2" style="text-align: right;">
                <button class="btn_blue" style="font-size: 16px;" id="btnSave" ng-click="savePromotion()">
                    <s:message code="cmm.save"/>
                </button>
            </th>
        </tr>
        <tr>
            <%-- 프로모션명 --%>
            <th><s:message code="artiseePromotion.promotionNm"/></th>
            <td>
                <input type="text" id="promotionNm" ng-model="promotionNm" class="sb-input w100" maxlength="300">
            </td>
            <%-- 프로모션메모 --%>
            <th><s:message code="artiseePromotion.memo"/></th>
            <td>
                <input type="text" id="memo" ng-model="memo" class="sb-input w100" maxlength="300">
            </td>
        </tr>
        <tr>
            <%-- 프로모션적용업체구분 --%>
            <th><s:message code="artiseePromotion.promoCompFg" /></th>
            <td>
                <div class="sb-select w40">
                    <wj-combo-box
                            id="promoCompFg"
                            ng-model="promoCompFg"
                            items-source="_getComboData('promoCompFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="promoCompFgCombo">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 사용여부 --%>
            <th><s:message code="artiseePromotion.useYn" /></th>
            <td>
                <div class="sb-select w40">
                    <wj-combo-box
                           id="useYn"
                            ng-model="useYn"
                            items-source="_getComboData('useYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="useYnCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 프로모션타입 --%>
            <th><s:message code="artiseePromotion.promoTypeFg"/></th>
            <td>
                <div class="sb-select w40">
                    <wj-combo-box
                            id="promoTypeFg"
                            ng-model="promoTypeFg"
                            items-source="_getComboData('promoTypeFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="promoTypeFgCombo"
                            selected-index-changed="setDcSet(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 할인율 --%>
            <th id="thDcSet"><label id="lblDcSet"></label></th>
            <td id="tdDcSet">
                <input type="text" class="sb-input w40" id="dcSet" ng-model="dcSet" maxlength="10" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
            </td>
        </tr>
        <tr>
            <%-- 적용기간 --%>
            <%--<th>
                <div style="float: left;"><input type="checkbox" id="chkPeriod" ng-model="isCheckedPeriod" ng-change="isChkPeriod()"/></div>
                <div style="padding-top: 3px; padding-left: 20px;"><s:message code="artiseePromotion.promotionPeriod" /></div>
            </th>
            <td>
                <div class="sb-select" id="divChkPeriod" style="display: none;">
                    <span class="txtIn"><input id="promotionStartDate" class="w150px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="promotionEndDate" class="w150px"></span>
                </div>
            </td>--%>
            <th> <%--// 일단은 체크박스 히든처리하고 적용기간 무조건 입력(2024.06.20)--%>
                <div style="float: left;"><input type="checkbox" id="chkPeriod" ng-model="isCheckedPeriod" ng-change="isChkPeriod()" style="display: none;"/></div>
                <div style="padding-top: 3px;"><s:message code="artiseePromotion.promotionPeriod" /></div>
            </th>
            <td>
                <div class="sb-select" id="divChkPeriod" style="display: block;">
                    <span class="txtIn"><input id="promotionStartDate" class="w150px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="promotionEndDate" class="w150px"></span>
                </div>
            </td>
            <%-- 적용대상 --%>
            <th><label id="lblProdTypeFg"></label></th>
            <td>
                <div class="sb-select w40" id="divProdTypeFg">
                    <wj-combo-box
                            id="prodTypeFg"
                            ng-model="prodTypeFg"
                            items-source="_getComboData('prodTypeFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="prodTypeFgCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 상세 등록 영역 --%>
    <div id="divDetailReg">
        <%--left--%>
        <span class="fl w50 mt10 pdr5">
            <%-- 적용상품 등록 영역 --%>
            <table class="searchTbl mt10" id="tblProd">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                <tr style="border-top: 1px solid #CCCCCC;">
                    <th colspan="4">
                        <span style="font-size: 13px;"><label id="lblProd"></label></span>
                    </th>
                </tr>
                <tr>
                    <td colspan="4" style="border-left: 1px solid #CCCCCC;" ng-controller="artiseePromotionSelectProdGridCtrl">
                        <div class="updownSet mt5 mb5" style="font-size: 15px;">
                            <%-- 상품추가 --%>
                            <button class="btn_skyblue" id="btnProdAdd" ng-click="prodAdd()"><s:message code='artiseePromotion.prodAdd' /></button>
                            <%-- 분류추가 --%>
                            <button class="btn_skyblue" id="btnClassAdd" ng-click="classAdd()"><s:message code='artiseePromotion.classAdd' /></button>
                            <%-- 저장 --%>
                             <button class="btn_skyblue" id="btnProdSave" ng-click="prodSave()"><s:message code="cmm.save" /></button>
                             <%-- 삭제 --%>
                             <button class="btn_skyblue" id="btnProdDel" ng-click="prodDel()"><s:message code='cmm.del' /></button>
                        </div>
                        <div class="wj-gridWrap" style="height:225px; overflow-x: hidden; overflow-y: hidden;">
                                <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flexSelectProdGrid"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true"
                                    id="wjGridSelectProd">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="artiseePromotion.code"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="artiseePromotion.name"/>" binding="prodNm" width="250" is-read-only="true" align="left"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="artiseePromotion.giftQty"/>" binding="giftQty" width="80" align="center" data-type="Number" format="n0" max-length=3></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%--right--%>
        <div class="fr w50 mt10 pdl5">
            <%-- 적용매장 등록 영역 --%>
            <table class="searchTbl mt10" id="tblPromotionStore">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                <tr style="border-top: 1px solid #CCCCCC;">
                    <th colspan="4">
                        <span style="font-size: 13px;"><s:message code="artiseePromotion.promotionStore" /></span>
                    </th>
                </tr>
                <tr>
                    <td colspan="4" style="border-left: 1px solid #CCCCCC;" ng-controller="artiseePromotionSelectStoreGridCtrl">
                        <div class="updownSet mt5 mb5" style="font-size: 15px;">
                             <%--엑셀다운로드--%>
                            <button class="btn_skyblue" id="btnStoreExcelDown" ng-click="storeExcelDown()"><s:message code='cmm.excel.down' /></button>
                            <%-- 매장추가 --%>
                            <button class="btn_skyblue" id="btnStoreAdd" ng-click="storeAdd()"><s:message code='artiseePromotion.storeAdd' /></button>
                            <%-- 매장삭제 --%>
                            <button class="btn_skyblue" id="btnStoreDel" ng-click="storeDel()"><s:message code='cmm.del' /></button>
                        </div>
                        <div class="wj-gridWrap" style="height:225px; overflow-x: hidden; overflow-y: hidden;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flexSelectStoreGrid"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    id="wjGridSelectStore">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="artiseePromotion.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="artiseePromotion.storeNm"/>" binding="storeNm" width="400" is-read-only="true" align="left"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <%-- 상세조회 시, promotionCd 값 hidden에 갖고 있기 --%>
    <input type="hidden" id="hdPromotionCd" />
    <%-- 상세조회 시, 적용대상 선택값 갖고있기 --%>
    <input type="hidden" id="hdProdTypeFg" />
</div>

<style type="text/css">
    input[type=checkbox]  {
        width: 17px;
        height: 17px;
    }
</style>

<script language="JavaScript">
    var orgnFg = "${orgnFg}";
    var modPromotionEnvstVal = "${modPromotionEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/artiseePromotion/artiseePromotion.js?ver=20240622.01" charset="utf-8"></script>

<%-- 적용상품 상품추가 --%>
<c:import url="/WEB-INF/view/base/promotion/artiseePromotion/artiseePromotionProdReg.jsp">
</c:import>

<%-- 적용상품 분류추가 --%>
<c:import url="/WEB-INF/view/base/promotion/artiseePromotion/artiseePromotionClassReg.jsp">
</c:import>

<%-- 적용매장 매장추가 --%>
<c:import url="/WEB-INF/view/base/promotion/artiseePromotion/artiseePromotionStoreReg.jsp">
</c:import>
