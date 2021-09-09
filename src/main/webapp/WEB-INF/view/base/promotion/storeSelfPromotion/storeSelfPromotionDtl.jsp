<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup control="storeSelfPromotionDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1200px;">
    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
        <s:message code="storeSelfPromotion.dtlTitle"/>
        <a href="" class="wj-hide btn_close" ng-click="close()"></a>
    </div>

    <div class="wj-dialog-body" ng-controller="storeSelfPromotionDtlCtrl">

        <%-- 마스터 등록 영역 --%>
        <table class="searchTbl">
            <colgroup>
                <col class="w10" />
                <col class="w40" />
                <col class="w10" />
                <col class="w40" />
            </colgroup>
            <tbody>
            <tr style="border-top: 1px solid #CCCCCC;">
                <th colspan="2">
                    <span style="font-size: 13px;"><s:message code="promotion.info" /></span>
                </th>
                <th colspan="2" style="text-align: right;">
                </th>
            </tr>
            <tr>
                <%-- 프로모션명 --%>
                <th><s:message code="promotion.promotionNm" /></th>
                <td>
                    <input type="text" id="promotionNm" ng-model="promotionNm" class="sb-input w100" maxlength="300">
                </td>
                <%-- 프로모션메모 --%>
                <th><s:message code="promotion.memo" /></th>
                <td>
                    <input type="text" id="memo" ng-model="memo" class="sb-input w100" maxlength="300">
                </td>
            </tr>
            <tr>
                <%-- 사용여부 --%>
                <th><s:message code="promotion.useYn" /></th>
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
                <th></th>
                <td></td>
            </tr>
            </tbody>
        </table>

        <%-- 상세 등록 영역 --%>
        <div id="divDetailReg">
            <%--left--%>
            <div class="fl w50 mt10 pdr5" style="margin-bottom: 50px;">
                <%-- 적용조건 등록 영역 --%>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w20" />
                        <col class="w30" />
                        <col class="w20" />
                        <col class="w30" />
                    </colgroup>
                    <tbody>
                    <tr style="border-top: 1px solid #CCCCCC;">
                        <th colspan="4">
                            <span style="font-size: 13px;"><s:message code="promotion.condition" /></span>
                        </th>
                    </tr>
                    <tr>
                        <th><s:message code="promotion.dlvFg" /></th>
                        <td colspan="3" style="height: 35px;">
                            <div class="fl pd5" style="padding-right: 15px;" id="divChkDlvFg">
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgInStore" ng-model="dlvFgInStore"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.inStore" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgDelivery" ng-model="dlvFgDelivery"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.delivery" /> </label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgPacking" ng-model="dlvFgPacking"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.packing" /> </label></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 적용기간 --%>
                        <th>
                            <div style="float: left;"><input type="checkbox" id="chkPeriod" ng-model="isCheckedPeriod" ng-change="isChkPeriod()"/></div>
                            <div style="padding-top: 3px; padding-left: 20px;"><s:message code="promotion.promotionPeriod" /></div>
                        </th>
                        <td colspan="3">
                            <div class="sb-select" id="divChkPeriod" style="display: none;">
                                <span class="txtIn"><input id="promotionStartDate" class="w150px"></span>
                                <span class="rg">~</span>
                                <span class="txtIn"><input id="promotionEndDate" class="w150px"></span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 적용시간 --%>
                        <th>
                            <div style="float: left;"><input type="checkbox" id="chkTime" ng-model="isCheckedTime" ng-change="isChkTime()"/></div>
                            <div style="padding-top: 3px; padding-left: 20px;"><s:message code="promotion.promotionTime" /></div>
                        </th>
                        <td colspan="3">
                            <div id="divChkTime" style="display: none;" >
                                <div class="sb-select fl" style="width:65px;">
                                    <wj-combo-box
                                            id="promotionStartHh"
                                            ng-model="promotionStartHh"
                                            items-source="_getComboData('promotionStartHhCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="promotionStartHhCombo">
                                    </wj-combo-box>
                                </div>
                                <div class="fl pd5" style="padding-right: 15px;">
                                    <label>시 </label>
                                </div>
                                <div class="sb-select fl" style="width:65px;">
                                    <wj-combo-box
                                            id="promotionStartMm"
                                            ng-model="promotionStartMm"
                                            items-source="_getComboData('promotionStartMmCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="promotionStartMmCombo">
                                    </wj-combo-box>
                                </div>
                                <div class="fl pd5" style="padding-right: 15px;">
                                    <label>분 ~ </label>
                                </div>
                                <div class="sb-select fl" style="width:65px;">
                                    <wj-combo-box
                                            id="promotionEndHh"
                                            ng-model="promotionEndHh"
                                            items-source="_getComboData('promotionEndHhCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="promotionEndHhCombo">
                                    </wj-combo-box>
                                </div>
                                <div class="fl pd5" style="padding-right: 15px;">
                                    <label>시 </label>
                                </div>
                                <div class="sb-select fl" style="width:65px;">
                                    <wj-combo-box
                                            id="promotionEndMm"
                                            ng-model="promotionEndMm"
                                            items-source="_getComboData('promotionEndMmCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="promotionEndMmCombo">
                                    </wj-combo-box>
                                </div>
                                <div class="fl pd5" style="padding-right: 15px;">
                                    <label>분 </label>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 적용요일 --%>
                        <th>
                            <div style="float: left;"><input type="checkbox" id="chkDayOfWeek" ng-model="isCheckedDayOfWeek" ng-change="isChkDayOfWeek()"/></div>
                            <div style="padding-top: 3px; padding-left: 20px;"><s:message code="promotion.promotionDayofWeek" /></div>
                        </th>
                        <td colspan="3" style="height: 35px;">
                            <div class="fl pd5" style="padding-right: 15px; display: none;" id="divChkDayOfWeek">
                                <div style="float: left;"><input type="checkbox" id="chkDayOfWeekMon" ng-model="dayOfWeekMon"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.dayOfWeekMon" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDayOfWeekTue" ng-model="dayOfWeekTue"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.dayOfWeekTue" /> </label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDayOfWeekWed" ng-model="dayOfWeekWed"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.dayOfWeekWed" /> </label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDayOfWeekThu" ng-model="dayOfWeekThu"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.dayOfWeekThu" /> </label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDayOfWeekFri" ng-model="dayOfWeekFri"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.dayOfWeekFri" /> </label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDayOfWeekSat" ng-model="dayOfWeekSat"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.dayOfWeekSat" /> </label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDayOfWeekSun" ng-model="dayOfWeekSun"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.dayOfWeekSun" /> </label></div>
                            </div>
                        </td>
                    </tr>
                    <tr <c:if test="${promotionEnvstVal == '1'}">style="display: none;"</c:if>>
                        <%-- 적용조건의 적용대상 --%>
                        <th><s:message code="promotion.memberTargetDs" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="memberTargetDs"
                                        ng-model="memberTargetDs"
                                        items-source="_getComboData('memberTargetDs')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="memberTargetDsCombo"
                                        selected-index-changed="setMemberClassCd(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 적용등급 --%>
                        <th style="display: none;" id="trTitleMemberClassCdY"><s:message code="promotion.memberClassCd" /></th>
                        <td id="trTitleMemberClassCdN"></td>
                        <td>
                            <div class="sb-select w100" id="divMemberClassCd" style="display: none;">
                                <wj-combo-box
                                        id="memberClassCd"
                                        ng-model="memberClassCd"
                                        items-source="_getComboData('memberClassCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="memberClassCdCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 최소구매금액 --%>
                        <th>
                            <div style="float: left;"><input type="checkbox" id="chkMinSaleAmt" ng-model="isCheckedMinSaleAmt" ng-change="isChkMinSaleAmt()"/></div>
                            <div style="padding-top: 3px; padding-left: 20px;"><s:message code="promotion.minSaleAmt" /></div>
                        </th>
                        <td>
                            <div id="divChkMinSaleAmt" style="display: none;">
                                <input type="text" class="sb-input w100" id="minSaleAmt" ng-model="minSaleAmt" maxlength="10" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
                <%-- 적용상품 등록 영역 --%>
                <table class="searchTbl mt10">
                    <colgroup>
                        <col class="w20" />
                        <col class="w30" />
                        <col class="w20" />
                        <col class="w30" />
                    </colgroup>
                    <tbody>
                    <tr id="trProdTop" style="border-top: 1px solid #CCCCCC;">
                        <th colspan="4">
                            <div style="float: left;"><input type="checkbox" id="chkProd" ng-model="isCheckedProd" ng-change="isChkProd()"/></div>
                            <div style="padding-top: 3px; padding-left: 20px; font-size:13px;"><s:message code="promotion.prod" /></div>
                        </th>
                    </tr>
                    <tr id="trSelectProdDs" style="display: none;">
                        <%-- 적용상품의 구매대상 --%>
                        <th><s:message code="promotion.selectProdDs" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="selectProdDs"
                                        ng-model="selectProdDs"
                                        items-source="_getComboData('selectProdDs')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="selectProdDsCombo"
                                        selected-index-changed="setSelectProdCrossFg(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 적용상품의 상품등록구분 --%>
                        <th><s:message code="promotion.prodRegFg" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="prodSelectExceptFg"
                                        ng-model="prodSelectExceptFg"
                                        items-source="_getComboData('prodSelectExceptFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="prodSelectExceptFgCombo"
                                        selected-index-changed="setProdRegBtn(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr id="trSelectProdCrossFg" style="display: none;">
                        <%-- 적용상품의 교차선택구분 --%>
                        <th><s:message code="promotion.selectProdCrossFg" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="selectProdCrossFg"
                                        ng-model="selectProdCrossFg"
                                        items-source="_getComboData('selectProdCrossFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="selectProdCrossFgCombo"
                                        selected-index-changed="setSelectProdCnt(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 적용상품의 수량 --%>
                        <th><label id="lblSelectProdCnt"></label></th>
                        <td>
                            <input type="text" class="sb-input w100" id="selectProdCnt" ng-model="selectProdCnt" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                    </tr>
                    <tr id="trSelectProdGrid" style="display: none;">
                        <td colspan="4" style="border-left: 1px solid #CCCCCC;" ng-controller="storeSelfPromotionSelectProdGridCtrl">
                            <div class="wj-gridWrap" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flexSelectProdGrid"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="" binding="condiProdSeq" width="" is-read-only="true" visible="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="promotion.fg"/>" binding="gubunDs" data-map="gubunDsFgDataMap" width="55" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="promotion.code"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="promotion.name"/>" binding="prodNm" width="250" is-read-only="true" align="left"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="promotion.prodQty"/>" binding="prodQty" width="65" align="center"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%--right--%>
            <div class="fr w50 mt10 pdl5" style="margin-bottom: 10px;">
                <%-- 적용혜택 등록 영역 --%>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w20" />
                        <col class="w30" />
                        <col class="w20" />
                        <col class="w30" />
                    </colgroup>
                    <tbody>
                    <tr style="border-top: 1px solid #CCCCCC;">
                        <th colspan="4">
                            <span style="font-size: 13px;"><s:message code="promotion.bene" /></span>
                        </th>
                    </tr>
                    <tr>
                        <%-- 혜택유형 --%>
                        <th><s:message code="promotion.typeCd" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="typeCd"
                                        ng-model="typeCd"
                                        items-source="_getComboData('typeCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="typeCdCombo"
                                        selected-index-changed="setApplyDcDs(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="trApplyDcDs" style="display: none;">
                        <%-- 할인구분 --%>
                        <th><s:message code="promotion.applyDcDs" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="applyDcDs"
                                        ng-model="applyDcDs"
                                        items-source="_getComboData('applyDcDs')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="applyDcDsCombo"
                                        selected-index-changed="setDcSet(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 할인율 --%>
                        <th><label id="lblDcSet"></label></th>
                        <td>
                            <input type="text" class="sb-input w100" id="dcSet" ng-model="dcSet" maxlength="10" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 출력문구 --%>
                        <th><s:message code="promotion.printMessage" /></th>
                        <td colspan="3" style="font-size: 16px;">
                            <textarea id="printMessage" ng-model="printMessage" class="w100 tArea1" rows="5" maxlength="700" style="height: 94px;"></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <%-- 혜택상품 등록 영역 --%>
                <table class="searchTbl mt10" id="tblBene">
                    <colgroup>
                        <col class="w20" />
                        <col class="w30" />
                        <col class="w20" />
                        <col class="w30" />
                    </colgroup>
                    <tbody>
                    <tr style="border-top: 1px solid #CCCCCC; height: 33px;">
                        <th colspan="4">
                            <span style="font-size: 13px;"><s:message code="promotion.present" /></span>
                        </th>
                    </tr>
                    <tr>
                        <%-- 혜택상품 증정구분 --%>
                        <th><s:message code="promotion.presentDs" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="presentDs"
                                        ng-model="presentDs"
                                        items-source="_getComboData('presentDs')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="presentDsCombo"
                                        selected-index-changed="setSelectCrossFg(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="trSelectCrossFg">
                        <%-- 혜택상품 교차선택구분 --%>
                        <th><s:message code="promotion.selectCrossFg" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="selectCrossFg"
                                        ng-model="selectCrossFg"
                                        items-source="_getComboData('selectCrossFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="selectCrossFgCombo"
                                        selected-index-changed="setSelectGiftCnt(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 혜택상품 수량 --%>
                        <th><label id="lblSelectGiftCnt"></label></th>
                        <td>
                            <input type="text" class="sb-input w100" id="selectGiftCnt" ng-model="selectGiftCnt" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                    </tr>
                    <tr id="trSelectPresentGrid">
                        <td colspan="4" style="border-left: 1px solid #CCCCCC" ng-controller="storeSelfPromotionSelectPresentGridCtrl">
                            <div class="wj-gridWrap" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flexSelectPresentGrid"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="promotion.code"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="promotion.name"/>" binding="prodNm" width="250" is-read-only="true" align="left"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="promotion.prodQty"/>" binding="giftQty" width="65" align="center"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <%-- 상세조회 시, promotionCd 값 hidden에 갖고 있기 --%>
        <input type="hidden" id="hdStoreCd" />
        <input type="hidden" id="hdPromotionCd" />
    </div>

</wj-popup>
<style type="text/css">
    input[type=checkbox]  {
        width: 17px;
        height: 17px;
    }
</style>
<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/promotion/storeSelfPromotion/storeSelfPromotionDtl.js?ver=20210909.02" charset="utf-8"></script>