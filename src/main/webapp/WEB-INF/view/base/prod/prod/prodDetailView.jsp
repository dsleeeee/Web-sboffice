<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="prodDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="prodDetailCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.layer.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 600px;">
            <h3 class="h3_tbl brt"><s:message code="prod.title.basicInfo"/></h3>
            <div class="tblBr">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 상품이미지 //TODO --%>
                        <th rowspan="4"><s:message code="prod"/><br/><s:message code="image"/>
                        </th>
                        <td rowspan="4">
                            <%--등록한 상품이 없는 경우--%>
                            <span class="goodsNo" id="goodsNoProdDetail"><s:message code="image"/> 등록 준비중 입니다</span>
                            <%--등록한 상품이 있는 경우--%>
                            <%--<span class="goodsYes"><img src="img/sample.jpg" alt="" /></span>--%>
                            <span class="goodsYes" id="goodsYesProdDetail"><img id="imgProdImageProdDetail" /></span>
                        </td>
                        <%-- 브랜드명 --%>
                        <th <c:if test="${brandUseFg == '0'}"> style="display: none;" </c:if> ><s:message code="prod.brandNm"/></th>
                        <td id="_hqBrandNm" <c:if test="${brandUseFg == '0'}"> style="display: none;" </c:if> >
                            {{prodDetail.hqBrandNm}}
                        </td>
                    </tr>
                    <tr>
                        <%--상품유형 //TODO --%>
                        <th>
                            <s:message code="prod.prodTypeFg"/>
                        </th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.prodTypeFg"
                                    ng-hide="true"
                                    text="_prodTypeFg"
                                    items-source="_getComboData('prodTypeFgComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_prodTypeFg}}
                        </td>
                    </tr>
                    <tr>
                        <%--상품코드--%>
                        <th>
                            <s:message code="prod.prodCd"/>
                        </th>
                        <td id="_prodCd">
                            {{prodDetail.prodCd}}
                        </td>
                    </tr>
                    <tr>
                        <%--상품명--%>
                        <th>
                            <s:message code="prod.prodNm"/>
                        </th>
                        <td id="_prodNm">
                            {{prodDetail.prodNm}}
                        </td>
                    </tr>
                    <tr>
                        <%--상품분류--%>
                        <th><s:message code="prod.prodClass"/></th>
                        <td id="_prodClassCdNm">
                            {{prodDetail.prodClassCdNm}}
                        </td>
                        <%--거래처 --%>
                        <th>
                            <s:message code="prod.vendr"/>
                        </th>
                        <td id="_vendr">
                            {{prodDetail.vendrNm}}
                            <div style="display: none;">
                                {{prodDetail.vendrCd}}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%--판매상품여부--%>
                        <th><s:message code="prod.saleProdYn"/></th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.saleProdYn"
                                    ng-hide="true"
                                    text="_saleProdYn"
                                    items-source="_getComboData('saleProdYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_saleProdYn}}
                        </td>
                        <%--원산지--%>
                        <th>
                            <s:message code="prod.orgplceCd"/>
                        </th>
                        <td id="_orgplceCd">
                            <s:message code="prod.orgplceCd"/> 등록은 준비중 입니다.
                        </td>
                    </tr>
                    <tr>
                        <%--판매단가--%>
                        <th>
                            <s:message code="prod.saleUprc"/>
                        </th>
                        <td id="_saleUprc">
                            {{prodDetail.saleUprc}}
                        </td>
                        <%-- 봉사료 포함 여부 --%>
                        <th><s:message code="prod.prodTipYn"/></th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.prodTipYn"
                                    ng-hide="true"
                                    text="_prodTipYn"
                                    items-source="_getComboData('prodTipYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_prodTipYn}}
                        </td>
                    </tr>
                    <tr>
                        <%--과세여부--%>
                        <th>
                            <s:message code="prod.vatFg"/>
                        </th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.vatFg"
                                    ng-hide="true"
                                    text="_vatFg"
                                    items-source="_getComboData('vatFgComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_vatFg}}
                        </td>
                        <%--부가세포함여부--%>
                        <th>
                            <s:message code="prod.vatIncldYn"/>
                        </th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.vatIncldYn"
                                    ng-hide="true"
                                    text="_vatIncldYn"
                                    items-source="_getComboData('vatIncldYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_vatIncldYn}}
                        </td>
                    </tr>
                    <tr <c:if test="${momsEnvstVal == '1'}">style="display: none"</c:if>>
                        <%--사용여부--%>
                        <th <c:if test="${momsEnvstVal == '1'}">style="display: none"</c:if>>
                            <s:message code="useYn"/>
                        </th>
                        <td <c:if test="${momsEnvstVal == '1'}">style="display: none"</c:if>>
                            <wj-combo-box
                                    ng-model="prodDetail.useYn"
                                    ng-hide="true"
                                    text="_useYn"
                                    items-source="_getComboData('useYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_useYn}}
                        </td>
                    </tr>
                    <tr>
                        <%--바코드--%>
                        <th>
                            <s:message code="prod.barCd"/>
                        </th>
                        <td>
                            {{prodDetail.barCd}}
                        </td>
                        <%-- 가격관리구분 --%>
                        <th>
                            <s:message code="prod.prcCtrlFg"/>
                        </th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.prcCtrlFg"
                                    ng-hide="true"
                                    text="_prcCtrlFg"
                                    items-source="_getComboData('prcCtrlFgComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_prcCtrlFg}}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 내점/배달/포장 가격 --%>
            <c:if test="${subPriceFg == '1'}">
                <h3 class="h3_tbl"><s:message code="prod.title.saleUprc"/>&nbsp;&nbsp;<input type="checkbox" id="saleUprcApplyProdDetail" ng-model="saleUprcApply" />&nbsp;<s:message code="prod.saleUprcApply"/></h3>
                <div class="tblBr">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%--내점가--%>
                        <th><s:message code="prod.stinSaleUprc"/></th>
                        <td>
                            {{prodDetail.stinSaleUprc}}
                        </td>
                        <%--포장가--%>
                        <th><s:message code="prod.packSaleUprc"/></th>
                        <td>
                            {{prodDetail.packSaleUprc}}
                        </td>
                    </tr>
                    <tr>
                        <%--배달가--%>
                        <th><s:message code="prod.dlvrSaleUprc"/></th>
                        <td>
                            {{prodDetail.dlvrSaleUprc}}
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            </c:if>
            <%-- 상품부가정보 --%>
            <h3 class="h3_tbl"><s:message code="prod.title.addInfo"/></h3>
            <div class="tblBr">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%--재고관리여부--%>
                        <th>
                            <s:message code="prod.stockProdYn"/>
                        </th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.stockProdYn"
                                    ng-hide="true"
                                    text="_stockProdYn"
                                    items-source="_getComboData('useYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_stockProdYn}}
                        </td>
                        <%--저장품코드 //TODO --%>
                        <th>
                            <s:message code="prod.saveProdCd"/>
                        </th>
                        <td>
                            <%--<a href="#" class="link" id="_saveProdCd"></a>--%>
                            <s:message code="prod.saveProdCd"/> 준비중 입니다.
                        </td>
                    </tr>
                    <tr>
                        <%--세트상품구분//TODO --%>
                        <th>
                            <s:message code="prod.setProdFg"/>
                        </th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.setProdFg"
                                    ng-hide="true"
                                    text="_setProdFg"
                                    items-source="_getComboData('setProdFgComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_setProdFg}}
                            &nbsp;&nbsp;
                            <a href="#" id="btnDtlSetConfigProd" class="btn_grayS" ng-click="dtlSetConfigProd()" style="display: none;"><s:message code="prod.configProd"/></a>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <%--포인트적립여부--%>
                        <th>
                            <s:message code="prod.pointSaveYn"/>
                        </th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.pointSaveYn"
                                    ng-hide="true"
                                    text="_pointSaveYn"
                                    items-source="_getComboData('useYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_pointSaveYn}}
                        </td>
                        <%--사이드상품여부--%>
                        <th><s:message code="prod.sideProdYn"/></th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.sideProdYn"
                                    ng-hide="true"
                                    text="_sideProdYn"
                                    items-source="_getComboData('useYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_sideProdYn}}
                        </td>
                        <%--품절여부 //TODO --%>
                        <th style="display: none">
                            <s:message code="prod.soldOutYn"/>
                        </th>
                        <td style="display: none">
                            <%--{{prodDetail.soldOutYnNm}}--%>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr ng-if="prodDetail.sideProdYn === 'Y'">
                        <%--사이드속성분류코드(속성) --%>
                        <th><s:message code="prod.sdattrClassCd"/></th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.sdattrClassCd"
                                    ng-hide="true"
                                    text="_sdattrClassCd"
                                    items-source="_getComboData('sdattrClassCdComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_sdattrClassCd}}
                        </td>
                        <%--사이드선택그룹코드(선택메뉴) --%>
                        <th><s:message code="prod.sdselGrpCd"/></th>
                        <td>
                            <wj-combo-box ng-model="prodDetail.sdselGrpCd"
                                          ng-hide="true"
                                          text="_sdselGrpCd"
                                          items-source="_getComboData('sdselGrpCdComboData')"
                                          display-member-path="name"
                                          selected-value-path="value"
                                          is-editable="false">
                            </wj-combo-box>
                            {{_sdselGrpCd}}
                        </td>
                    </tr>
                    <%-- [1250 맘스터치] --%>
                    <c:if test="${momsEnvstVal == '1'}">
                        <tr>
                            <%-- 단품/세트선택설정 --%>
                            <th><s:message code="prod.groupProdCd"/></th>
                            <td>
                                {{prodDetail.groupProdNm}}
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <!-- 보증금상품코드 -->
                        <th><s:message code="prod.depositProdCd"/></th>
                        <td>
                            {{prodDetail.depositProdNm}}
                        </td>
                    </tr>
                    <tr>
                        <!-- 보증금상품유형 -->
                        <th><s:message code="prod.depositCupFg"/></th>
                        <td>
                            <wj-combo-box ng-model="prodDetail.depositCupFg"
                                          ng-hide="true"
                                          text="_depositCupFg"
                                          items-source="_getComboData('depositCupFgComboData2')"
                                          display-member-path="name"
                                          selected-value-path="value"
                                          is-editable="false">
                            </wj-combo-box>
                            {{_depositCupFg}}
                        </td>
                        <!-- 포인트사용여부 -->
                        <th><s:message code="prod.pointUseYn"/></th>
                        <td>
                            <wj-combo-box ng-model="prodDetail.pointUseYn"
                                          ng-hide="true"
                                          text="_pointUseYn"
                                          items-source="_getComboData('useYnComboData')"
                                          display-member-path="name"
                                          selected-value-path="value"
                                          is-editable="false">
                            </wj-combo-box>
                            {{_pointUseYn}}
                        </td>
                    </tr>
                    <tr>
                        <!-- 할인여부 -->
                        <th><s:message code="prod.dcYn"/></th>
                        <td>
                            <wj-combo-box ng-model="prodDetail.dcYn"
                                          ng-hide="true"
                                          text="_dcYn"
                                          items-source="_getComboData('useYnComboData')"
                                          display-member-path="name"
                                          selected-value-path="value"
                                          is-editable="false">
                            </wj-combo-box>
                            {{_dcYn}}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 발주정보 --%>
            <h3 class="h3_tbl"><s:message code="prod.title.orderInfo"/></h3>
            <div class="tblBr">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr <c:if test="${momsEnvstVal == '1' && orgnFg == 'STORE'}">style="display: none"</c:if>> <%-- 맘스터치 매장은 공금단가와 원가단가를 hidden 처리 --%>
                        <%--공급단가--%>
                        <th>
                            <s:message code="prod.splyUprc"/>
                        </th>
                        <td id="_splyUprc">
                            {{prodDetail.splyUprc}}
                        </td>
                        <%--공급단가사용여부--%>
                        <th style="display: none">
                            <s:message code="prod.splyUprc"/><br><s:message code="cmm.useYn"/>
                        </th>
                        <td style="display: none">
                            <wj-combo-box
                                    ng-model="prodDetail.splyUprcUseYn"
                                    ng-hide="true"
                                    text="_splyUprcUseYn"
                                    items-source="_getComboData('splyUprcUseYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_splyUprcUseYn}}
                        </td>
                        <%--원가단가--%>
                        <th>
                            <s:message code="prod.costUprc"/>
                        </th>
                        <td id="_costUprc">
                            {{prodDetail.costUprc}}
                        </td>
                        <%--최종원가단가--%>
                        <th style="display: none">
                            <s:message code="prod.lastCostUprc"/>
                        </th>
                        <td style="display: none">
                            {{prodDetail.lastCostUprc}}
                        </td>
                    </tr>
                    <tr>
                        <%--발주상품구분--%>
                        <th><s:message code="prod.poProdFg"/></th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.poProdFg"
                                    ng-hide="true"
                                    text="_poProdFg"
                                    items-source="_getComboData('poProdFgComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_poProdFg}}
                        </td>
                        <%--발주단위--%>
                        <th><s:message code="prod.poUnitFg"/></th>
                        <td>
                            <wj-combo-box
                                    ng-model="prodDetail.poUnitFg"
                                    ng-hide="true"
                                    text="_poUnitFg"
                                    items-source="_getComboData('poUnitFgComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                            </wj-combo-box>
                            {{_poUnitFg}}
                        </td>
                    </tr>
                    <tr>
                        <%--발주단위수량--%>
                        <th><s:message code="prod.poUnitQty"/></th>
                        <td>
                            {{prodDetail.poUnitQty}}
                        </td>
                        <%--최소발주수량--%>
                        <th><s:message code="prod.poMinQty"/></th>
                        <td>
                            {{prodDetail.poMinQty}}
                        </td>
                    </tr>
                    <tr>
                        <%--안전재고--%>
                        <th><s:message code="prod.safeStockQty"/></th>
                        <td>
                            {{prodDetail.safeStockQty}}
                        </td>
                        <%--초기재고--%>
                        <th style="display: none">
                            <s:message code="prod.startStockQty"/>
                        </th>
                        <td style="display: none">
                            {{prodDetail.defaultStock}}
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- [1250 맘스터치] --%>
            <c:if test="${momsEnvstVal == '1'}">
                <%-- 영양정보 --%>
                <h3 class="h3_tbl"><s:message code="prod.title.nuInfo"/></h3>
                <div class="tblBr">
                    <table class="tblType01">
                        <colgroup>
                            <col class="w15" />
                            <col class="w35" />
                            <col class="w15" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <%-- 총중량 --%>
                            <th><s:message code="prod.nuTotWt"/></th>
                            <td>
                                <div class="sb-select w80 fl">
                                    {{prodDetail.nuTotWt}}
                                </div>
                                <div class="sb-select w20 fr">
                                    (g)
                                </div>
                            </td>
                            <%-- 총열량 --%>
                            <th><s:message code="prod.nuKcal"/></th>
                            <td>
                                <div class="sb-select w80 fl">
                                    {{prodDetail.nuKcal}}
                                </div>
                                <div class="sb-select w20 fr">
                                    (kcal)
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 단백질 --%>
                            <th><s:message code="prod.nuProtein"/></th>
                            <td>
                                <div class="sb-select w80 fl">
                                    {{prodDetail.nuProtein}}
                                </div>
                                <div class="sb-select w20 fr">
                                    (g)
                                </div>
                            </td>
                            <%-- 나트륨 --%>
                            <th><s:message code="prod.nuSodium"/></th>
                            <td>
                                <div class="sb-select w80 fl">
                                    {{prodDetail.nuSodium}}
                                </div>
                                <div class="sb-select w20 fr">
                                    (mg)
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 당류 --%>
                            <th><s:message code="prod.nuSugars"/></th>
                            <td>
                                <div class="sb-select w80 fl">
                                    {{prodDetail.nuSugars}}
                                </div>
                                <div class="sb-select w20 fr">
                                    (g)
                                </div>
                            </td>
                            <%-- 포화지방 --%>
                            <th><s:message code="prod.nuSatFat"/></th>
                            <td>
                                <div class="sb-select w80 fl">
                                    {{prodDetail.nuSatFat}}
                                </div>
                                <div class="sb-select w20 fr">
                                    (g)
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 카페인 --%>
                            <th><s:message code="prod.nuCaffeine"/></th>
                            <td>
                                <div class="sb-select w80 fl">
                                    {{prodDetail.nuCaffeine}}
                                </div>
                                <div class="sb-select w20 fr">
                                    (mg)
                                </div>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <%-- 기타정보 --%>
            <h3 class="h3_tbl"><s:message code="prod.title.etcInfo"/></h3>
            <div class="tblBr">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매핑상품코드 --%>
                        <th><s:message code="prod.mapProdCd"/></th>
                        <td colspan="3">
                            {{prodDetail.mapProdCd}}
                        </td>
                    </tr>
                    <tr style="display: none">
                        <%-- 코너 --%>
                        <th><s:message code="prod.corner"/></th>
                        <td colspan="3">
                            <div class="sb-select">
                                <wj-combo-box ng-model="prodDetail.cornrCd"
                                      ng-hide="true"
                                      text="_cornrCd"
                                      items-source="_getComboData('cornrCdComboData')"
                                      display-member-path="name"
                                      selected-value-path="value"
                                      is-editable="false">
                                </wj-combo-box>

                            </div>
                            {{_cornrCd}}
                        </td>
                    </tr>
                    <%-- [1250 맘스터치] --%>
                    <c:if test="${momsEnvstVal == '1'}">
                        <tr>
                            <%-- KIOSK 판매시간 --%>
                            <th><s:message code="prod.kioskSaleTime"/></th>
                            <td>
                                <wj-combo-box
                                    ng-model="prodDetail.saleTimeFg"
                                    ng-hide="true"
                                    text="_saleTimeFg"
                                    items-source="_getComboData('useYnComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false">
                                </wj-combo-box>
                                {{_saleTimeFg}}
                            </td>
                            <%-- KIOSK 시간설정 --%>
                            <th><s:message code="prod.kioskSaleTimeSetting"/></th>
                            <td>
                                <div id="divSaleTime"></div>
                            </td>
                        </tr>
                    </c:if>
                        <tr>
                            <%-- KIOSK 엣지 --%>
                            <th><s:message code="prod.momsKioskEdge"/></th>
                            <td>
                                <wj-combo-box
                                        ng-model="prodDetail.momsKioskEdge"
                                        ng-hide="true"
                                        text="_momsKioskEdge"
                                        items-source="_getComboData('momsKioskEdgeComboData')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false">
                                </wj-combo-box>
                                {{_momsKioskEdge}}
                            </td>
                            <c:if test="${momsEnvstVal == '1'}">
                                <%-- 상품옵션그룹 --%>
                                <th><s:message code="prod.optionGrp"/></th>
                                <td>
                                    {{prodDetail.optionGrpNm}}
                                </td>
                            </c:if>
                        </tr>
                    <c:if test="${momsEnvstVal == '1'}">
                        <tr>
                            <%-- 출시일 --%>
                            <th><s:message code="prod.releaseDate"/></th>
                            <td>
                                <label id="lblReleaseDate" />
                            </td>
                            <%-- 단종일 --%>
                            <th><s:message code="prod.disconDate"/></th>
                            <td>
                                <label id="lblDisconDate" />
                            </td>
                        </tr>
                        <tr>
                            <%-- 판매방식 --%>
                            <th><s:message code="prod.saleType"/></th>
                            <td>
                                <label id="lblSaleType" />
                            </td>
                            <th></th>
                            <td></td>
                        </tr>
                        <tr>
                            <%-- 판매채널 --%>
                            <th><s:message code="prod.saleChannel"/></th>
                            <td colspan="3" style="height: 35px;">
                                <label id="lblSaleChannel" />
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
            <%-- 비고 --%>
            <h3 class="h3_tbl" ng-if="prodDetail.remark !== ''"><s:message code="prod.title.remark"/></h3>
            <div class="tblBr" ng-if="prodDetail.remark !== ''">
                <table class="tblType01">
                    <colgroup>
                        <col class="w100"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th class="gr lh20" id="_remark" style="height:25px;">
                            {{prodDetail.remark}}
                        </th>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 상품상세설명 --%>
            <h3 class="h3_tbl" ng-if="prodDetail.prodInfo !== ''"><s:message code="prod.title.info"/></h3>
            <div class="tblBr" ng-if="prodDetail.prodInfo !== ''">
                <table class="tblType01">
                    <colgroup>
                        <col class="w100"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th class="gr lh20" id="_prodInfo">
                            <textarea id="_info"  class="w100" cols="42" style="height:30px;resize: none;" readonly>{{prodDetail.prodInfo}}</textarea>
                        </th>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 할인/적립정보 --%>
            <h3 class="h3_tbl" ng-if="prodDetail.pointSaveYn === 'Y'"><s:message code="prod.title.dcAndSaveInfo"/></h3>
            <div class="tblBr" ng-if="prodDetail.pointSaveYn === 'Y'">
                <table class="tblType01">
                    <colgroup>
                        <col width="15%"/>
                        <col width="85%"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%--할인 //TODO --%>
                        <th><s:message code="prod.dc"/></th>
                        <td id="_dc">
                            할인정보 준비중 입니다.
                        </td>
                    </tr>
                    <tr>
                        <%--적립 //TODO --%>
                        <th><s:message code="prod.save"/></th>
                        <td id="_save">
                            적립정보 준비중 입니다.
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 연결상품정보 --%>
            <h3 class="h3_tbl"><s:message code="prod.title.unitstInfo"/></h3>
            <div class="tblBr">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w85"/>
                    </colgroup>
                    <tbody id="_linkedProdInfoProdDetail">
                    <tr>
                        <th class="gr lh20" colspan="2">
                            연결상품정보 준비중 입니다.
                        </th>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide-apply btn_blue" ng-show="btnShowFg"><s:message code="cmm.edit"/></button>
            <button class="btn wj-hide-cancel btn_blue"><s:message code="cmm.close"/></button>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prodDetailView.js?ver=20250224.01" charset="utf-8"></script>