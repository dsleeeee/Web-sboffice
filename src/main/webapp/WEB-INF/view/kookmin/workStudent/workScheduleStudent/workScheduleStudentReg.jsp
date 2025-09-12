<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjWorkScheduleStudentRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:510px;" fade-in="false" fade-out="false">
    <div ng-controller="workScheduleStudentRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="workScheduleStudent.pop.workStudentReg"/>
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
                    <%-- 학번 --%>
                    <th>
                        <s:message code="workScheduleStudent.studentNo"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStudentNo" ng-model="studentNo" />
                    </td>
                    <%-- 이름 --%>
                    <th>
                        <s:message code="workScheduleStudent.studentName"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStudentNm" ng-model="studentNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 학과 --%>
                    <th>
                        <s:message code="workScheduleStudent.department"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchDepartment" ng-model="department" />
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('workScheduleStudentRegCtrl', 1)"><s:message code="cmm.search" /></button>
                </div>
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
<%--                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="workScheduleStudent.studentNo"/>"     binding="studentNo"     width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="workScheduleStudent.studentName"/>"   binding="studentNm"     width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="workScheduleStudent.department"/>"    binding="department"    width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/workStudent/workScheduleStudent/workScheduleStudentReg.js?ver=20250911.01" charset="utf-8"></script>