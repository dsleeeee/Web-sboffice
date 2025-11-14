<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup control="wjDlvrAgencyRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
    <div class="wj-dialog" ng-controller="dlvrAgencyRegCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dlvrAgencyLink.dlvrAgency.reg"/>
            <a href="#" class="wj-hide btn_close"></a>
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
                    <%-- 배달대행사 --%>
                    <th><s:message code="dlvrAgencyLink.dlvrAgency"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchDlvrAgency"
                                    ng-model="srchDlvrAgency"
                                    items-source="_getComboData('srchDlvrAgency')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchDlvrAgencyCombo"
                                    selected-index-changed="getSubAgency(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 하위 배달대행사 --%>
                    <th><s:message code="dlvrAgencyLink.subAgency"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchSubAgency"
                                    ng-model="srchSubAgency"
                                    items-source="_getComboData('srchSubAgency')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchSubAgencyCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%-- 조회 --%>
                    <button class="btn_skyblue ml5 fr" ng-click="seachStoreList()"><s:message code="cmm.search" /></button>
                </div>
            </div>

            <div class="pdt10" style="height: 200px;">
                <wj-flex-grid
                        id="wjGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.storeCd"/>" binding="storeCode" align="center" width="100" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.storeNm"/>" binding="storeName" align="left" width="150" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.addr"/>" binding="address" width="300" align="left" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.deposit"/>" binding="deposit" align="right" width="100" is-read-only="true" ></wj-flex-grid-column>

                </wj-flex-grid>
            </div>

            <%-- 버튼 영역 --%>
            <div class="wj-dialog-footer">
                <%-- 선택 --%>
                <button class="btn btn_blue" ng-click="btnSelect()"><s:message code="cmm.select"/></button>
                <%-- 닫기 --%>
                <button class="btn btn_blue" ng-click="btnClose()"><s:message code="cmm.close"/></button>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/dlvrAgencyLink/dlvrAgencyReg.js?ver=20251114.01" charset="utf-8"></script>