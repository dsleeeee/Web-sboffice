<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="dlvrEmpCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="dlvrEmp.dlvrEmp"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('dlvrEmpCtrl', 1)">
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
            <%-- 등록일자 --%>
            <th>
                <s:message code="dlvrEmp.regDate"/>
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"> <input id="srchStartDate" name="startDate" class="w115px" readonly /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="srchEndDate" name="endDate" class="w115px" readonly /></span>
                    <span class="chk ml10">
                        <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt">
                            <s:message code="cmm.all.day" />
                        </label>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 배달사원번호 --%>
            <th>
                <s:message code="dlvrEmp.empNo"/>
            </th>
            <td>
                <input type="text" id="srchDlvrEmpNo" class="sb-input w100"/>
            </td>
            <%-- 배달사원명 --%>
            <th>
                <s:message code="dlvrEmp.empNm"/>
            </th>
            <td>
                <input type="text" id="srchDlvrEmpNm" class="sb-input w100"/>
            </td>
        </tr>
        <tr>
            <%-- 휴대폰번호 --%>
            <th>
                <s:message code="dlvrEmp.hpNo"/>
            </th>
            <td>
                <input type="text" id="srchHpNo" class="sb-input w100"/>
            </td>
            <%-- SMS 수신여부 --%>
            <th>
                <s:message code="dlvrEmp.smsRecvYn"/>
            </th>
            <td>
                <div class="sb-select w50">
                    <wj-combo-box
                            id="srchSmsRecvYn"
                            items-source="_getComboData('srchSmsRecvYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="initComboBox(s)"
                            control="srchSmsRecvYnCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 사용여부 --%>
            <th>
                <s:message code="dlvrEmp.useYn"/>
            </th>
            <td>
                <div class="sb-select w50">
                    <wj-combo-box
                            id="srchUseYn"
                            items-source="_getComboData('srchUseYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="initComboBox(s)"
                            control="srchUseYnCombo">
                    </wj-combo-box>
                </div>
            </td>
            <th></th>
            <td></td>
        </tr>
        </tbody>
    </table>

    <%-- 그리드 --%>
    <%-- 페이지 스케일  --%>
    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                control="listScaleCombo"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>

        <%-- 신규사원등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnDlvrEmpReg" ng-click="btnDlvrEmpReg()">
            <s:message code="dlvrEmp.dlvrEmpReg"/>
        </button>
    </div>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dlvrEmp.empNo"/>" binding="dlvrEmpNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrEmp.empNm"/>" binding="dlvrEmpNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrEmp.hpNo"/>" binding="hpNo" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrEmp.smsRecvYn"/>" binding="smsRecvYn"  width="150" align="center" is-read-only="true" data-map="smsRecvDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrEmp.useYn"/>" binding="useYn" width="150" align="center" is-read-only="true" data-map="useYnDataMap"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dlvrEmpCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    var smsRecvYn  = ${ccu.getCommCodeExcpAll("072")};
    var useYn = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/dlvrEmp/dlvrEmp.js?ver=20211020.03" charset="utf-8"></script>

<%-- 상세 팝업 --%>
<c:import url="/WEB-INF/view/dlvr/info/dlvrEmp/dlvrEmpDtl.jsp">
</c:import>

<%-- 신규사원등록 팝업 --%>
<c:import url="/WEB-INF/view/dlvr/info/dlvrEmp/dlvrEmpReg.jsp">
</c:import>