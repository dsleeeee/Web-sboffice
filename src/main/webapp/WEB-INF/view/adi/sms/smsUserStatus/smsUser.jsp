<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="smsUserView">
    <div class="subCon" ng-controller="smsUserCtrl" ng-init="init()">

        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="smsUserStatus.smsUser"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('smsUserCtrl',1)">
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
                    <input type="text" id="orgnCd" ng-model="orgnCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 소속명 --%>
                <th><s:message code="smsUserStatus.orgnNm"/></th>
                <td>
                    <input type="text" id="orgnNm" ng-model="orgnNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 사용자ID --%>
                <th><s:message code="smsUserStatus.userId"/></th>
                <td>
                    <input type="text" id="userId" ng-model="userId" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 사용자명 --%>
                <th><s:message code="smsUserStatus.userNm"/></th>
                <td>
                    <input type="text" id="userNm" ng-model="userNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
        </div>

        <%-- SMS사용자 목록 그리드 --%>
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
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.orgnCd"/>" binding="orgnCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.orgnNm"/>" binding="orgnNm" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.userId"/>" binding="userId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.userNm"/>" binding="userNm" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.certNum"/>" binding="telNo" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.regDt"/>" binding="regDt" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsUserStatus/smsUser.js?ver=20260807.01" charset="utf-8"></script>
