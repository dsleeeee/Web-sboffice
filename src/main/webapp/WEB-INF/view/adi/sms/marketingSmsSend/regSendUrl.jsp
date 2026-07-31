<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjRegSendUrlLayer" control="wjRegSendUrlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;height:560px;" fade-in="false" fade-out="false">
    <div ng-controller="regSendUrlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="regSendUrl.info"/>
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 발신번호 --%>
                    <th>
                        <s:message code="regSendUrl.telNo"/>
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="telNoCombo"
                                    ng-model="telNo"
                                    items-source="_getComboData('telNoCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="telNoCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 사용기간 --%>
                    <th>
                        <s:message code="regSendUrl.usePeriod"/>
                    </th>
                    <td>
                        <div class="sb-select fl">
                            <span class="txtIn"><input id="regSendUrlUsePeriod" class="w110px"/></span>
                        </div>
                        <%-- 요청 --%>
                        <button class="btn_skyblue ml5 fl" id="btnRegSendUrlReq" ng-click="reqSendUrl()">
                            <s:message code="regSendUrl.request"/>
                        </button>
                    </td>
                </tr>
                <tr>
                    <%-- 전송URL --%>
                    <th>
                        <s:message code="regSendUrl.sendUrl"/>
                    </th>
                    <td colspan="3">
                        <input type="text" id="sendUrl" ng-model="sendUrl" class="sb-input w100"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <%-- 조회 --%>
                <button class="btn_blue" id="btnRegSendUrlSearch" ng-click="searchRegSendUrl()">
                    <s:message code="cmm.search"/>
                </button>
            </div>

            <%-- 안내문구 --%>
            <div class="mt10 s12">
                <s:message code="regSendUrl.guide"/>
            </div>

            <%-- 요청 목록 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="regSendUrl.userId"/>" binding="userId" width="90" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regSendUrl.telNo"/>" binding="telNo" width="100" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regSendUrl.sendUrl"/>" binding="sendUrl" width="180" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regSendUrl.usePeriod"/>" binding="usePeriod" width="170" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regSendUrl.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regSendUrl.regDt"/>" binding="regDt" width="110" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regSendUrl.regId"/>" binding="regId" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regSendUrl.modDt"/>" binding="modDt" width="110" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regSendUrl.modId"/>" binding="modId" width="80" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/marketingSmsSend/regSendUrl.js?ver=20260723.01" charset="utf-8"></script>
