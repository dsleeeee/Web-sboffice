<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>
<c:set var="promotionEnvstVal" value="${promotionEnvstVal}" />
<c:set var="modPromotionEnvstVal" value="${modPromotionEnvstVal}" />
<c:set var="storePromoRegYnVal" value="${storePromoRegYnVal}" />

<%-- 리스트 영역 --%>
<div class="subCon" ng-controller="promotionCtrl" style="padding-bottom: 0;">
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="fl"><s:message code="promotion.manage"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue" id="btnRegist" ng-click="openPromotionReg()" <c:if test="${orgnFg == 'STORE' && storePromoRegYnVal == '0'}">style="display: none;"</c:if>>
                <s:message code="cmm.new.add" />
            </button>
            <button class="btn_blue mr3" id="btnSearch" ng-click="_pageView('promotionCtrl',1)">
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
                <th><s:message code="promotion.promotionNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="promotionNm" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="promotion.useYn" /></th>
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
                <th><s:message code="promotion.promotionDate" /></th>
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
                <wj-flex-grid-column header="<s:message code="promotion.promotionCd"/>" binding="promotionCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.promotionNm"/>" binding="promotionNm" width="300" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.memo"/>" binding="memo" width="300" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.startDate"/>" binding="startYmd"  width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.endDate"/>" binding="endYmd"  width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.copy"/>" binding="" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>
</div>

<%-- 등록 영역 --%>
<div class="subCon" id="promotionReg" style="display: none;" ng-controller="promotionRegCtrl">

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
                    <button class="btn_blue" style="font-size: 16px;" id="btnSave" ng-click="savePromotion()">
                        <s:message code="cmm.save" />
                    </button>
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
                <%-- 프로모션 종류 --%>
                <th><s:message code="promotion.promotionType" /></th>
                <td>
                    <div class="sb-select w40" style="float: left; padding-right: 10px;">
                        <wj-combo-box
                                id="promotionType"
                                items-source="_getComboData('promotionType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="promotionTypeCombo"
                                selected-index-changed="setPromotionRegDetail(s)">
                        </wj-combo-box>
                    </div>
                    <div>
                        <button class="btn_skyblue" ng-click="openPromotionExample()">
                            <s:message code="promotion.example" />
                        </button>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <%-- 상세 등록 영역 --%>
    <div id="divDetailReg">
        <%--left--%>
        <div class="fl w50 mt10 pdr5" style="margin-bottom: 100px;">
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
                    <tr <c:if test="${momsEnvstVal == '0'}">style="display: none;"</c:if>>
                        <th><s:message code="promotion.kioskBanner" /></th>
                        <td colspan="3">
                            <div class="pd5">
                                <label id="lblFileNm"></label>
                                <input type="hidden" id="hdFileNo"/>
                            </div>
                            <f:form id="regForm" name="regForm" method="post" enctype="multipart/form-data">
                                <input type="file" id="fileKioskBanner" name="fileKioskBanner" class="form-control" ng-model="fileKioskBanner"
                                       onchange="angular.element(this).scope().uploadChange()" accept="image/x-png, image/gif, image/jpeg"/>
                            </f:form>
                            <div class="pd5" style="height: 35px;">
                                <button type="button" id="btnImgCancel" class="btn_skyblue ml5" ng-click="imgCancel('003','F')"><s:message code="cmm.selectCancel" /></button>
                                <button type="button" id="btnDelImg" class="btn_skyblue" ng-click="delImg('003')"><s:message code="cmm.del" /></button>
                            </div>
                            <div class="pd5">
                                <label style="color: red;"><s:message code="promotion.fileSize.max"/></label>
                                <label id="lblFileSize"></label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="promotion.dlvFg" /></th>
                        <td colspan="3" style="height: 35px;">
                            <div class="fl pd5" style="padding-right: 15px;">
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgInStore" ng-model="dlvFgInStore"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.inStore" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgDelivery" ng-model="dlvFgDelivery"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.delivery" /> </label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgPacking" ng-model="dlvFgPacking"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.packing" /> </label></div>

                                <span <c:if test="${momsEnvstVal == '0'}">style="display: none"</c:if>>
                                    <div style="float: left;"><input type="checkbox" id="chkDlvFgYogiyo" ng-model="dlvFgYogiyo"/></div>
                                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.yogiyo" /></label></div>
                                    <div style="float: left;"><input type="checkbox" id="chkDlvFgCoupangeats" ng-model="dlvFgCoupangeats"/></div>
                                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.coupangeats" /></label></div>
                                    <div style="float: left;"><input type="checkbox" id="chkDlvFgWmpo" ng-model="dlvFgWmpo"/></div>
                                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.wmpo" /></label></div>
                                    <div style="float: left;"><input type="checkbox" id="chkDlvFgPayco" ng-model="dlvFgPayco"/></div>
                                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.payco" /></label></div>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr <c:if test="${momsEnvstVal == '0'}">style="display: none"</c:if>>
                        <th></th>
                        <td colspan="3" style="height: 35px;">
                            <div class="fl pd5" style="padding-right: 15px;">
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgSpecialdelivery" ng-model="dlvFgSpecialdelivery"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.specialdelivery" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgKakao" ng-model="dlvFgKakao"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.kakao" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgBaemin" ng-model="dlvFgBaemin"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.baemin" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgDdangyo" ng-model="dlvFgDdangyo"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.ddangyo" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDlvFgNpaysmartorder" ng-model="dlvFgNpaysmartorder"/></div>
                                <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.npaysmartorder" /></label></div>
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
                        <%-- 구매금액 --%>
                        <th>
                            <div style="float: left;"><input type="checkbox" id="chkMinSaleAmt" ng-model="isCheckedMinSaleAmt" ng-change="isChkMinSaleAmt()"/></div>
                            <div style="padding-top: 3px; padding-left: 20px;"><s:message code="promotion.minSaleAmt" /></div>
                        </th>
                        <td colspan="3">
                            <div id="divChkMinSaleAmt" style="display: none;">
                                <div style="float: left;"><input type="text" class="sb-input w150px" id="minSaleAmt" ng-model="minSaleAmt" maxlength="10" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/></div>
                                <div style="float: left; padding-top: 5px; padding-left:5px; padding-right:5px;"><label>~</label></div>
                                <div style="float: left;"><input type="text" class="sb-input w150px" id="maxSaleAmt" ng-model="maxSaleAmt" maxlength="10" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/></div>
                            </div>
                        </td>
                    </tr>
                    <tr <c:if test="${momsEnvstVal == '0'}">style="display: none"</c:if>>
                        <%-- 구매횟수제한 --%>
                        <th><s:message code="promotion.momsPurchsCntLimit" /></th>
                        <td>
                            <input type="text" class="sb-input w150px" id="momsPurchsCntLimit" ng-model="momsPurchsCntLimit" maxlength="2" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                        <%-- 구매갯수제한 --%>
                        <th><s:message code="promotion.momsPurchsQtyLimit" /></th>
                        <td>
                            <input type="text" class="sb-input w150px" id="momsPurchsQtyLimit" ng-model="momsPurchsQtyLimit" maxlength="2" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                    </tr>
                    <tr <c:if test="${momsEnvstVal == '0'}">style="display: none"</c:if>>
                        <%--본사분담금--%>
                        <th><s:message code="promotion.hqChargeUprc" /></th>
                        <td>
                            <input type="text" class="sb-input w150px" id="hqChargeUprc" ng-model="hqChargeUprc" maxlength="10" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                        <%--매장분담금--%>
                        <th><s:message code="promotion.msChargeUprc" /></th>
                        <td>
                            <input type="text" class="sb-input w150px" id="msChargeUprc" ng-model="msChargeUprc" maxlength="10" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                    </tr>
                    <tr <c:if test="${momsEnvstVal == '0'}">style="display: none"</c:if>>
                        <%-- 제휴분담금 --%>
                        <th><s:message code="promotion.partnerChargeUprc" /></th>
                        <td>
                            <input type="text" class="sb-input w150px" id="partnerChargeUprc" ng-model="partnerChargeUprc" maxlength="10" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                        <%-- 입금일 --%>
                        <th>
                            <div style="float: left;"><input type="checkbox" id="chkDepositYmd" ng-model="isCheckedDepositYmd" ng-change="isChkDepositYmd()"/></div>
                            <div style="padding-top: 3px; padding-left: 20px;"><s:message code="promotion.depositYmd" /></div>
                        </th>
                        <td>
                            <div class="sb-select" id="divChkDepositYmd" style="display: none;">
                                <span class="txtIn"><input id="depositYmd" class="w150px"></span>
                            </div>
                        </td>
                    </tr>
                    <tr <c:if test="${momsEnvstVal == '0'}">style="display: none"</c:if>>
                        <%-- 제한된 결제 --%>
                        <th><s:message code="promotion.blockPay" /></th>
                        <td colspan="3" style="height: 35px;">
                            <div style="float: left;"><input type="checkbox" id="chkPayFgMomsGiftcard" ng-model="payFgMomsGiftcard"/></div>
                            <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="promotion.momsGiftcard" /></label></div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <%-- 적용상품 등록 영역 --%>
            <table class="searchTbl mt10" id="tblProd">
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
                                    initialized="initComboBox(s)"
                                    control="selectProdDsCombo"
                                    selected-index-changed="setSelectProdCnt(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 적용상품의 수량 --%>
                    <th id="thSelectProdCnt" style="display: none;"><label id="lblSelectProdCnt"></label></th>
                    <td id="tdSelectProdCnt" style="display: none;">
                        <input type="text" class="sb-input w100" id="selectProdCnt" ng-model="selectProdCnt" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                    </td>
                </tr>
                <tr id="trSelectProdGrid" style="display: none;">
                    <td colspan="4" style="border-left: 1px solid #CCCCCC;" ng-controller="promotionSelectProdGridCtrl">
                        <div class="updownSet mt5 mb5" style="font-size: 15px;">
                            <%-- 상품추가 --%>
                            <button class="btn_skyblue" id="btnProdAdd" ng-click="prodAdd()"><s:message code='promotion.prodAdd' /></button>
                            <%-- 분류추가 --%>
                            <button class="btn_skyblue" id="btnClassAdd" ng-click="classAdd()"><s:message code='promotion.classAdd' /></button>
                            <%-- 저장 --%>
                             <button class="btn_skyblue" id="btnProdSave" ng-click="prodSave()"><s:message code="cmm.save" /></button>
                             <%-- 삭제 --%>
                             <button class="btn_skyblue" id="btnProdDel" ng-click="prodDel()"><s:message code='cmm.del' /></button>
                        </div>
                        <div class="updownSet mt5 mb5" id="divSelectProdBatch">
                            <%-- 할인값 일괄적용 --%>
                            <div style="float: right; padding:0 0 0 5px;"><button class="btn_skyblue" ng-click="batchDcSet()"><s:message code='promotion.batch' /></button></div>
                            <div style="float: right;"><input type="text" class="sb-input w60px" id="dcSetBatch" maxlength="5" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/></div>
                            <div style="float: right; padding: 6px;"><label><s:message code='promotion.dcSetVal' /></label></div>
                            <%-- 할인구분 일괄적용 --%>
                            <div style="float: right; padding:0 15px 0 5px;"><button class="btn_skyblue" ng-click="batchApplyDcDs()"><s:message code='promotion.batch' /></button></div>
                            <div class="sb-select w100px" style="float: right;">
                                <wj-combo-box
                                    id="applyDcDsBatch"
                                    ng-model="applyDcDsBatch"
                                    items-source="_getComboData('applyDcDs')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="applyDcDsBatchCombo">
                                </wj-combo-box>
                            </div>
                            <div style="float: right; padding: 6px;"><label><s:message code='promotion.applyDcDs' /></label></div>
                        </div>
                        <div class="wj-gridWrap" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
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
                                <wj-flex-grid-column header="" binding="condiProdSeq" width="" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="promotion.fg"/>" binding="gubunDs" data-map="gubunDsFgDataMap" width="55" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="promotion.code"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="promotion.name"/>" binding="prodNm" width="250" is-read-only="true" align="left"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="promotion.prodQty"/>" binding="prodQty" width="65" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="promotion.applyDcDs"/>" binding="applyDcDs" data-map="applyDcDsDataMap" width="70" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="promotion.dcSetVal"/>" binding="dcSet" width="70" align="right"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
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
                        <span style="font-size: 13px;"><s:message code="promotion.promotionStore" /></span>
                    </th>
                </tr>
                <tr>
                    <%-- 적용매장의 매장등록구분 --%>
                    <th><s:message code="promotion.storeRegFg" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="storeSelectExceptFg"
                                    ng-model="storeSelectExceptFg"
                                    items-source="_getComboData('storeSelectExceptFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="storeSelectExceptFgCombo"
                                    selected-index-changed="setStoreRegBtn(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="4" style="border-left: 1px solid #CCCCCC;" ng-controller="promotionSelectStoreGridCtrl">
                        <div class="updownSet mt5 mb5" style="font-size: 15px;">
                            <%--양식다운로드--%>
                            <button class="btn_skyblue" id="btnStoreSampleDown" ng-click="storeSampleDown()"><s:message code='cmm.excel.sampleDown' /></button>
                            <%--엑셀업로드--%>
                            <button class="btn_skyblue" id="btnStoreExcelUpload" ng-click="storeExcelUpload()"><s:message code='cmm.excel.excelUpload' /></button>
                            <%--엑셀다운로드--%>
                            <button class="btn_skyblue" id="btnStoreExcelDown" ng-click="storeExcelDown()"><s:message code='cmm.excel.down' /></button>
                            <%-- 매장추가 --%>
                            <button class="btn_skyblue" id="btnStoreAdd" ng-click="storeAdd()"><s:message code='promotion.storeAdd' /></button>
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
                                <wj-flex-grid-column header="<s:message code="promotion.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="promotion.storeNm"/>" binding="storeNm" width="400" is-read-only="true" align="left"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%--right--%>
        <div class="fr w50 mt10 pdl5" style="margin-bottom: 50px;">
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
                                        initialized="initComboBox(s)"
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
                        <th><label id="lblApplyDcDs"><s:message code="promotion.applyDcDs" /></label></th>
                        <td id="tdApplyDcDs">
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
                        <th id="thDcSet"><label id="lblDcSet"></label></th>
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
                        <%-- 혜택상품 구분 --%>
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
                                        selected-index-changed="setSelectGiftCnt(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 혜택상품 상품수 --%>
                        <th id="thSelectGiftCnt"><s:message code="promotion.selectGiftNCnt" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="selectGiftCnt" ng-model="selectGiftCnt" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" style="border-left: 1px solid #CCCCCC" ng-controller="promotionSelectPresentGridCtrl">
                            <div class="updownSet mt5 mb5" style="font-size: 15px;">
                                <%-- 혜택상품 상품추가 --%>
                                <button class="btn_skyblue" id="btnPresentAdd" ng-click="presentAdd()"><s:message code='promotion.prodAdd' /></button>
                                <%-- 혜택상품 저장 --%>
                                <button class="btn_skyblue" id="btnPresentSave" ng-click="presentSave()"><s:message code='cmm.save' /></button>
                                <%-- 혜택상품 삭제 --%>
                                <button class="btn_skyblue" id="btnPresentDel" ng-click="presentDel()"><s:message code='cmm.del' /></button>
                            </div>
                            <div class="wj-gridWrap" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flexSelectPresentGrid"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter"
                                        ime-enabled="true"
                                        id="wjGridSelectPresent">

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
    <input type="hidden" id="hdPromotionCd" />
</div>

<style type="text/css">
    input[type=checkbox]  {
        width: 17px;
        height: 17px;
    }
</style>

<script language="JavaScript">
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";
    var userId = "${userId}";
    var promotionEnvstVal = "${promotionEnvstVal}";
    var modPromotionEnvstVal = "${modPromotionEnvstVal}";

    // 브랜드
    var brandList = ${brandList};
    // 매장타입
    var storeTypeList = ${storeTypeList};
    // 메뉴그룹
    var storeGroupList = ${storeGroupList};
    // 프로모션 종류
    var promotionTypeList  = ${promotionTypeList};
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";
    // [1253 매장프로모션생성] 환경설정값 조회
    var storePromoRegYnVal = "${storePromoRegYnVal}";

    // POS에서 해당 WEB 화면 최초 접속한 경우(접속하면서 session 생성), 왼쪽 메뉴영역은 접어두기.
    // 최초 접속시에는 이전 URL 인자값으로 판별가능
    var referrer = document.referrer;
    if(referrer.indexOf("userId") > 0 && referrer.indexOf("resrceCd") > 0 && referrer.indexOf("accessCd") > 30 ){
        $(".menuControl").trigger("click");
    }

    // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값 판단하여 왼쪽 메뉴영역은 접어두기.
    // 재접속시에는 이전 URL 인자값이 없어, 로그인 여부 판별시에 특정 parameter 값을 보내 처리.
    if("${posLoginReconnect}" === "Y"){ // 직접입력한경우
        $(".menuControl").trigger("click");
    }
</script>

<style type="text/css">
    input[type=file]  {
        font-size: 12px;
        width:70%;
        float:left;
    }
</style>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotion/promotion.js?ver=20221129.03" charset="utf-8"></script>

<%-- 적용상품 상품추가 --%>
<c:import url="/WEB-INF/view/base/promotion/promotion/promotionProdReg.jsp">
</c:import>

<%-- 적용상품 분류추가 --%>
<c:import url="/WEB-INF/view/base/promotion/promotion/promotionClassReg.jsp">
</c:import>

<%-- 적용매장 추가 --%>
<c:import url="/WEB-INF/view/base/promotion/promotion/promotionStoreReg.jsp">
</c:import>

<%-- 혜택상품 추가 --%>
<c:import url="/WEB-INF/view/base/promotion/promotion/promotionPresentReg.jsp">
</c:import>

<%-- 프로모션 예시사례 안내 팝업 --%>
<c:import url="/WEB-INF/view/base/promotion/promotion/promotionExample.jsp">
</c:import>

<%-- 적용매장 엑셀업로드 --%>
<c:import url="/WEB-INF/view/base/promotion/promotion/excelUploadPromotion.jsp">
</c:import>