<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="workStudentKookminCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('workStudentKookminCtrl',1)" id="nxBtnSearch">
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
                <%-- 소속코드 --%>
                <th>
                    <s:message code="workStudentKookmin.searchCondition"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchCondition" ng-model="srchCondition" onkeyup="fnNxBtnSearch();" />
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 updownSet oh">
        <div class="txtIn">
            <button class="btn_skyblue" id="addBtn" ng-click="addRow()">
                <s:message code="cmm.add" />
            </button>
            <button class="btn_skyblue" id="deleteBtn" ng-click="delete()">
                <s:message code="cmm.delete" />
            </button>
            <button class="btn_skyblue" id="saveBtn" ng-click="save()">
                <s:message code="cmm.save" />
            </button>
            <button class="btn_skyblue" id="btnSampleDown" ng-click="sampleDownload()">
                <s:message code="cmm.excel.sampleDown" />
            </button>
            <button class="btn_skyblue" id="btnExcelUpload" ng-click="excelUpload()">
                <s:message code="cmm.excel.excelUpload" />
            </button>
        </div>
    </div>

    <div id="grid" class="w100">
        <div class="wj-gridWrap mt5" style="height:550px; overflow-y: hidden;">
            <div class="row">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.studentNo"/>"  binding="studentNo"     width="100" align="center"  ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.studentNm"/>"  binding="studentNm"     width="80"  align="center"  ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.department"/>" binding="department"    width="120" align="left"    ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.mpNo"/>"       binding="mpNo"          width="120" align="center"  ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.bankNm"/>"     binding="bankNm"        width="80"  align="center"  ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.accountNo"/>"  binding="accountNo"     width="150" align="left"    ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.coopYn"/>"     binding="coopYn"        width="60"  align="center"  ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.remark"/>"     binding="remark"        width="150" align="left"    ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.regDt"/>"      binding="regDt"         width="130" align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.regNm"/>"      binding="regNm"         width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.modDt"/>"      binding="modDt"         width="130" align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="workStudentKookmin.modNm"/>"      binding="modNm"         width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<%-- 양식다운로드/엑셀업로드 관련 --%>
<div style="display: none;" ng-controller="workStudentKookminExcelCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="workStudentKookmin.studentNo"/>"  binding="studentNo"     width="100" align="center"  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="workStudentKookmin.studentNm"/>"  binding="studentNm"     width="80"  align="center"  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="workStudentKookmin.department"/>" binding="department"    width="120" align="left"    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="workStudentKookmin.mpNo"/>"       binding="mpNo"          width="120" align="center"  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="workStudentKookmin.bankNm"/>"     binding="bankNm"        width="80"  align="center"  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="workStudentKookmin.accountNo"/>"  binding="accountNo"     width="150" align="left"    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="workStudentKookmin.coopYn"/>"     binding="coopYn"        width="60"  align="center"  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="workStudentKookmin.remark"/>"     binding="remark"        width="150" align="left"    ></wj-flex-grid-column>

        </wj-flex-grid>
    </div>

    <input type="file" class="form-control" id="excelUpFile"
           ng-model="excelUpFile"
           onchange="angular.element(this).scope().excelFileChanged()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/workStudent/workStudent/workStudentKookmin.js?ver=20250904.01" charset="utf-8"></script>

<%-- excelfile read js --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>

