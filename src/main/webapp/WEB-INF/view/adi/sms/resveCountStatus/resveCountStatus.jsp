<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="resveCountStatusCtrl">

    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button id="nxBtnSearch" class="btn_blue fr"  ng-click="_pageView('resveCountStatusCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회월 --%>
            <th>
                <s:message code="cmm.search.month" />
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"> <input id="startMonth" ng-model="startMonth" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endMonth" name="endMonth" class="w110px" /></span>
                </div>
            </td>
                <%-- 옵션 --%>
                <th><s:message code="resveCountStatus.option"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchOption"
                                ng-model="option"
                                items-source="_getComboData('srchOption')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchOptionCombo">
                        </wj-combo-box>
                    </div>
                </td>
        </tr>
        <tr>
            <%-- sms금액 --%>
            <th><s:message code="resveCountStatus.smsAmt" /></th>
            <td>
                <input type="text" class="sb-input w100" id="smsAmt" ng-model="smsAmt" onkeyup="fnNxBtnSearch();" />
            </td>
            <%-- lms금액 --%>
            <th><s:message code="resveCountStatus.lmsAmt" /></th>
            <td>
                <input type="text" class="sb-input w100" id="lmsAmt" ng-model="lmsAmt" onkeyup="fnNxBtnSearch();" />
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <div id="grid" class="w100">
        <div class="wj-gridWrap mt10" style="height:450px; overflow-y: hidden;">
            <div class="row">
                <wj-flex-grid
                        id="resveConutStatusGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="resveCountStatus.storeCd"/>" binding="storeCd" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="resveCountStatus.storeNm"/>" binding="storeNm" width="120" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="resveCountStatus.msgFg"/>" binding="msgFg" align="center" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="resveCountStatus.resveFg"/>" binding="resveFg" width="100" align="center" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="resveCountStatus.yearMonth"/>" binding="yearMonth" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="resveCountStatus.cnt"/>" binding="cnt" width="110" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="resveCountStatus.amtTotal"/>" binding="amtTotal" width="120" align="center" aggregate="Sum"></wj-flex-grid-column>

                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="resveCountStatusCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/resveCountStatus/resveCountStatus.js?ver=20250509.01" charset="utf-8"></script>