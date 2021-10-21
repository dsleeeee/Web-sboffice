<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup control="wjSmsChargeRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;height:550px;" fade-in="false" fade-out="false">
    <div ng-controller="smsChargeRegistCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsChargeRegist.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
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
                        <s:message code="smsChargeRegist.orgnCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgnCd" ng-model="srchOrgnCd" />
                    </td>
                    <%-- 소속명 --%>
                    <th>
                        <s:message code="smsChargeRegist.orgnNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgnNm" ng-model="srchOrgnNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 용도 --%>
                    <th>
                        <s:message code="smsChargeRegist.clsFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchClsFgCombo"
                                    ng-model="clsFg"
                                    items-source="_getComboData('clsFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchClsFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 상태 --%>
                    <th>
                        <s:message code="smsChargeRegist.sysStatFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchSysStatFgCombo"
                                    ng-model="sysStatFg"
                                    items-source="_getComboData('sysStatFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchSysStatFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <c:if test="${orgnFg eq 'MASTER'}">
                    <tr>
                        <%-- 소속구분 --%>
                        <th>
                            <s:message code="smsChargeRegist.orgnFg" />
                        </th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchOrgnFgCombo"
                                        ng-model="srchOrgnFg"
                                        items-source="_getComboData('srchOrgnFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchOrgnFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:if>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('smsChargeRegistCtrl', 1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <div class="mt20 oh">
                <table class="tblType01">
                    <colgroup>
                        <col class="w11"/>
                        <col class="w15"/>
                        <col class="w11"/>
                        <col class="w26"/>
                        <col class="w11"/>
                        <col class="w15"/>
                        <col class="w11"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 소속코드 --%>
                        <th>
                            <s:message code="smsChargeRegist.orgnCd"/>
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="selectOrgnCd" ng-model="selectOrgnCd" readonly/>
                        </td>
                        <%-- 소속명 --%>
                        <th>
                            <s:message code="smsChargeRegist.orgnNm"/>
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="selectOrgnNm" ng-model="selectOrgnNm" readonly/>
                        </td>
                        <%-- 충전금액 --%>
                        <th>
                            <s:message code="smsChargeRegist.smsChargeAmt"/>
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="smsChargeAmt" ng-model="smsChargeAmt" style="text-align: right;" />
                            <input type="text" class="sb-input w100" id="smsBaseAmt" ng-model="smsBaseAmt" style="display: none;" readonly />
                        </td>
                        <td>
                            <%-- 저장 --%>
                            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:260px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="smsChargeRegist.orgnCd"/>" binding="orgnCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeRegist.orgnNm"/>" binding="orgnNm" width="190" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeRegist.clsFg"/>" binding="clsFg" data-map="clsFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeRegist.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeRegist.smsAmt"/>" binding="smsAmt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    var orgnFg = '${orgnFg}';

    <%-- 용도 --%>
    var clsFgComboData = ${ccu.getCommCode("001")};
    <%-- 매장상태구분 --%>
    var sysStatFgComboData = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsChargeHist/smsChargeRegist.js?ver=20211020.01" charset="utf-8"></script>