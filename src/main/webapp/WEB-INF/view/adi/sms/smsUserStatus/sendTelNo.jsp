<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="sendTelNoView">
    <div class="subCon" ng-controller="sendTelNoCtrl" ng-init="init()">

        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="smsUserStatus.sendTelNo"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('sendTelNoCtrl',1)">
                    <s:message code="cmm.search"/>
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
            <tr>
                <%-- 소속코드 --%>
                <th><s:message code="smsUserStatus.orgnCd"/></th>
                <td>
                    <input type="text" id="orgnCd2" ng-model="orgnCd" class="sb-input w100" onkeyup="fnNxBtnSearch('2');"/>
                </td>
                <%-- 소속명 --%>
                <th><s:message code="smsUserStatus.orgnNm"/></th>
                <td>
                    <input type="text" id="orgnNm2" ng-model="orgnNm" class="sb-input w100" onkeyup="fnNxBtnSearch('2');"/>
                </td>
            </tr>
            <tr>
                <%-- 사용자ID --%>
                <th><s:message code="smsUserStatus.userId"/></th>
                <td>
                    <input type="text" id="userId2" ng-model="userId" class="sb-input w100" onkeyup="fnNxBtnSearch('2');"/>
                </td>
                <%-- 사용자명 --%>
                <th><s:message code="smsUserStatus.userNm"/></th>
                <td>
                    <input type="text" id="userNm2" ng-model="userNm" class="sb-input w100" onkeyup="fnNxBtnSearch('2');"/>
                </td>
            </tr>
            <tr style="display: none;">
                <%--SMS사용등록 구분--%>
                <th><s:message code="smsUserStatus.fg"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="smsUseRegFg2"
                                ng-model="smsUseRegFg"
                                control="smsUseRegFgCombo"
                                items-source="_getComboData('smsUseRegFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index="1">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
        </div>

        <%-- 발신번호 목록 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="smsUserGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        is-read-only="false"
                        item-formatter="_itemFormatter">
                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.orgnCd"/>" binding="orgnCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.orgnNm"/>" binding="orgnNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.userId"/>" binding="userId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.userNm"/>" binding="userNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.telNo"/>" binding="telNo" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.useYn"/>" binding="useYn" width="100" data-map="useYnFgDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.certId"/>" binding="certId" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsUserStatus/sendTelNo.js?ver=20260807.01" charset="utf-8"></script>
