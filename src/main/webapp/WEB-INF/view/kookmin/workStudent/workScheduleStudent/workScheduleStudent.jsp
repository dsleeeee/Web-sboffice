<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/kookmin/workStudent/workScheduleStore/workScheduleStore/"/>


<div class="subCon" ng-controller="workScheduleStudentCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('workScheduleStudentCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w25" />
            <col class="w15" />
            <col class="w45" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 년도 --%>
            <th>
                <s:message code="workScheduleStudent.termYear"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchDate" ng-model="srchDate" class="w110px"></span>
                </div>
            </td>
            <%-- 학기구분 --%>
            <th>
                <s:message code="workScheduleStudent.termFg"/>
            </th>
            <td>
                <span class="sb-radio"><input type="radio" id="termFg1" name="termFg" value="1" checked /><label for="termFg1">1학기</label></span>
                <span class="sb-radio"><input type="radio" id="termFg2" name="termFg" value="2"/><label for="termFg2">하계방학</label></span>
                <span class="sb-radio"><input type="radio" id="termFg3" name="termFg" value="3"/><label for="termFg3">2학기</label></span>
                <span class="sb-radio"><input type="radio" id="termFg4" name="termFg" value="4"/><label for="termFg4">동계방학</label></span>
            </td>
        </tr>
        <tr>
            <%-- 근무배치여부 --%>
            <th>
                <s:message code="workScheduleStudent.regFg"/>
            </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="regFg"
                                ng-model="regFg"
                                control="regFgAllCombo"
                                items-source="_getComboData('regFgAllComboData')"
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

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="delete()"><s:message code="cmm.delete" /></button>
        <button class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.save" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridWorkScheduleStudent"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.termYear"/>"      binding="termYear"      width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.termFg"/>"        binding="termFg"        width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.workSchCode"/>"   binding="workSchCode"   width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.storeCd"/>"       binding="storeCd"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.storeNm"/>"       binding="storeNm"       width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.workDay"/>"       binding="workDay"       width="140" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.workTime"/>"      binding="workTime"      width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.hourPay"/>"       binding="hourPay"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.workFg"/>"        binding="workFg"        width="80"  align="center" is-read-only="true"  data-map="workFgComboDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.studentNo"/>"     binding="studentNo"     width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.studentNm"/>"     binding="studentNm"     width="80"  align="center" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.startDay"/>"      binding="startDay"      width="120" align="center" >
                    <wj-flex-grid-cell-template cell-type="CellEdit">
                        <div class="sb-select">
                                    <span class="txtIn w100px">
                                        <wj-input-date
                                                value="$value"
                                                placeholder="미입력"
                                                is-required="false"
                                                is-editable="true"
                                                format="yyyy-MM-dd"
                                                initialized="initInputDate(s)">
                                        </wj-input-date>
                                    </span>
                        </div>
                    </wj-flex-grid-cell-template>
                </wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.endDay"/>"        binding="endDay"        width="120"     align="center" >
                    <wj-flex-grid-cell-template cell-type="CellEdit">
                        <div class="sb-select">
                                    <span class="txtIn w100px">
                                        <wj-input-date
                                                value="$value"
                                                placeholder="미입력"
                                                is-required="false"
                                                is-editable="true"
                                                format="yyyy-MM-dd"
                                                initialized="initInputDate(s)">
                                        </wj-input-date>
                                    </span>
                        </div>
                    </wj-flex-grid-cell-template>
                </wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStudent.remark"/>"        binding="remark"        width="150"    align="center" ></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/workStudent/workScheduleStudent/workScheduleStudent.js?ver=20250911.01" charset="utf-8"></script>

<%-- 프린터연결 팝업 --%>
<c:import url="/WEB-INF/view/kookmin/workStudent/workScheduleStudent/workScheduleStudentReg.jsp">
</c:import>


