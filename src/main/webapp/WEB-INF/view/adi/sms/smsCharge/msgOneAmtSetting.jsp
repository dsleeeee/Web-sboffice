<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="msgOneAmtSettingView" class="subCon" style="display: none;">
    <div ng-controller="msgOneAmtSettingCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="msgOneAmtSetting.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('msgOneAmtSettingCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w20"/>
                <col class="w30"/>
                <col class="w20"/>
                <col class="w30"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 소속코드 --%>
                <th>
                    <s:message code="msgOneAmtSetting.orgnCd"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOrgnCd" ng-model="srchOrgnCd" />
                </td>
                <%-- 소속명 --%>
                <th>
                    <s:message code="msgOneAmtSetting.orgnNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOrgnNm" ng-model="srchOrgnNm" />
                </td>
            </tr>
            <tr>
                <%-- 소속구분 --%>
                <th>
                    <s:message code="msgOneAmtSetting.orgnFg" />
                </th>
                <td>
                    <div class="sb-select w40">
                        <wj-combo-box
                                id="srchMsgOneAmtSettingOrgnFgCombo"
                                ng-model="srchOrgnFg"
                                items-source="_getComboData('msgOneAmtSettingOrgnFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="msgOneAmtSettingOrgnFgCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
            </tbody>
        </table>

        <table class="searchTbl mt10">
            <colgroup>
                <col class="w5"/>
                <col class="w10"/>
                <col class="w5"/>
                <col class="w10"/>
                <col class="w5"/>
                <col class="w10"/>
            </colgroup>
            <tbody>
            <tr class="brt">
                <%-- 건당금액(기본값) --%>
                <th colspan="6">
                    <s:message code="msgOneAmtSetting.msgOneAmtBase" />
                </th>
            </tr>
            <tr>
                <%-- SMS --%>
                <th>
                    <s:message code="msgOneAmtSetting.sms" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="msgOneAmtSettingSmsOneAmt" ng-model="smsOneAmt" readonly />
                </td>
                <%-- LMS --%>
                <th>
                    <s:message code="msgOneAmtSetting.lms" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="msgOneAmtSettingLmsOneAmt" ng-model="lmsOneAmt" readonly />
                </td>
                <%-- MMS --%>
                <th>
                    <s:message code="msgOneAmtSetting.mms" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="msgOneAmtSettingMmsOneAmt" ng-model="mmsOneAmt" readonly />
                </td>
            </tr>
            <tr>
                <%-- 알림톡 --%>
                <th>
                    <s:message code="msgOneAmtSetting.alk" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="msgOneAmtSettingAlkOneAmt" ng-model="alkOneAmt" readonly />
                </td>
                <%-- 알림톡LMS --%>
                <th>
                    <s:message code="msgOneAmtSetting.alkLms" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="msgOneAmtSettingAlkLmsOneAmt" ng-model="alkLmsOneAmt" readonly />
                </td>
                <th></th>
                <td></td>
            </tr>
            </tbody>
        </table>

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w50 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:420px;">
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="msgOneAmtSetting.orgnFg"/>" binding="orgnFg" data-map="orgnFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="msgOneAmtSetting.orgnCd"/>" binding="orgnCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="msgOneAmtSetting.orgnNm"/>" binding="orgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="msgOneAmtSettingDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:420px; overflow-y: hidden;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30">
                    <label id="lblOrgnCd"></label>
                    <label id="lblOrgnNm"></label>
                 </span>
                <button class="btn_skyblue" id="btnMsgOneAmtSettingSave" ng-click="save()"><s:message code='cmm.save' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:340px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="msgOneAmtSetting.msgType"/>" binding="msgType" data-map="msgTypeDataMap" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="msgOneAmtSetting.msgOneAmt"/>" binding="msgOneAmt" width="80" align="center"></wj-flex-grid-column>

                        <%--저장시 필요--%>
                        <wj-flex-grid-column header="<s:message code="msgOneAmtSetting.orgnCd"/>" binding="orgnCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/msgOneAmtSetting.js?ver=20220519.01" charset="utf-8"></script>