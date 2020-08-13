<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="wj-dialog-body" id="deliView" name="deliView" class="subCon">
    <%-- body --%>
    <div style="height:400px; overflow-y: auto;">
        <div ng-controller="memberDlvrCtrl">
            <f:form id="regDlvrForm" name="regDlvrForm">
                <div class="searchBar flddUnfld">
                    <a href="#" class="open fl"> <s:message code="regist.delivery.addr"/></a>
                    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                        <button type="button" class="btn_blue fr" ng-click="saveAddr()">
                            <s:message code="cmm.save"/>
                        </button>
                    </div>
                </div>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr style="border-top: 1px solid #ccc;">
                            <%-- 배달구역 --%>
                        <th><s:message code="regist.delivery.zone"/><em class="imp">*</em></th>
                        <td colspan="3">
                            <div class="sb-select fl">
                                <wj-combo-box
                                        id="lZoneListCd"
                                        ng-model="lZoneListCd"
                                        control="lZoneListCdCombo"
                                        items-source="dlvrLzone"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                            <div class="sb-select fl">
                                <wj-combo-box
                                        id="mZoneListCd"
                                        ng-model="mZoneListCd"
                                        control="mZoneListCdCombo"
                                        items-source="dlvrMzone"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                            <%-- 상세주소 --%>
                        <th><s:message code="regist.delivery.addr.dtl"/><em class="imp">*</em></th>
                        <td>
                            <input type="text" id="dlvrAddr" name="dlvrAddr" ng-model="dlvrAddr"
                                   class="sb-input w100" maxlength="30" required>
                        </td>
                            <%-- 사용여부 --%>
                        <th><s:message code="regist.useYn"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="useYn"
                                        ng-model="dlvrUseYn"
                                        control="useYnCombo"
                                        items-source="_getComboData('useYn')"
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
            </f:form>

            <%-- Card Histroy --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            control="flex"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            initialized="initGrid(s,e)"
                            items-source="data"
                    <%--                        frozen-columns="3"--%>
                            item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.no"/>" binding="membrNo"
                                             align="center"
                                             is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.addr"/>" binding="membrNm"
                                             align="left"
                                             is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.delivery.addr.dtl"/>"
                                             binding="membrClassNm"
                                             align="center"
                                             width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.useYn"/>" binding="membrCardNo"
                                             align="center"
                                             width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvr.membr.finalDlvrDate"/>" binding="birthday"
                                             align="center"
                                             width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvr.membr.time"/>" binding="telNo"
                                             width="100"
                                             align="center"
                                             is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <div ng-controller="memberDlvrTelCtrl">
<%--            <f:form id="regDlvrTelForm" name="regDlvrTelForm">--%>
                <div class="searchBar flddUnfld">
                    <a href="#" class="open fl"> <s:message code="regist.delivery.tel"/></a>
                    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                        <button type="button" class="btn_blue fr" ng-click="saveTel()">
                            <s:message code="cmm.save"/>
                        </button>
                    </div>
                </div>
                <%--                                <h3 class="h3_tbl"><s:message code="storeManage.basicInfo"/></h3>--%>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr style="border-top: 1px solid #ccc;">
                            <%-- 배달구역 --%>
                        <th><s:message code="regist.tel"/><em class="imp">*</em></th>
                        <td>
                            <input type="text" id="dlvrTelNo" name="dlvrTelNo" ng-model="dlvrTelNo"
                                   class="sb-input w100" maxlength="30" required>
                        </td>
                        <th><s:message code="regist.useYn"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="dlvrTelUseYn"
                                        ng-model="dlvrTelUseYn"
                                        control="useYnCombo"
                                        items-source="_getComboData('useYn')"
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
<%--            </f:form>--%>

            <%-- Card Histroy --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            control="flex"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            initialized="initGrid(s,e)"
                            items-source="data"
                    <%--                        frozen-columns="3"--%>
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
<%--                        <wj-flex-grid-column header="<s:message code="cmm.no"/>" binding="dlvrTelSeq"--%>
<%--                                             align="center"--%>
<%--                                             is-read-only="true"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="regist.tel"/>" binding="telNo"
                                             align="left"
                                             is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.useYn"/>" binding="useYn"
                                             align="center"
                                             width="*" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.delivery.inputDate"/>"
                                             binding="regDt"
                                             align="center"
                                             width="*" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.delivery.updateDate"/>" binding="modDt"
                                             align="center"
                                             width="*" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    <%--사용, 미사용--%>
    var useDataMap = ${ccu.getCommCodeExcpAll("067")};
    <%--배달구역--%>
    <%--var lZoneList = ${lZoneList};--%>
</script>
<script type="text/javascript"
        src="/resource/solbipos/js/membr/info/view/memberDlvr.js?ver=20191223.17 charset='utf-8'">
</script>
