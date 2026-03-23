<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<div class="subCon" ng-controller="posLogCollectMgmtCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('posLogCollectMgmtCtrl',1)" id="nxBtnSearch">
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
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" ng-model="startDate"class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate"ng-model="endDate" class="w110px"></span>
                    </div>
                </td>
                <%-- 명령타입 --%>
                <th><s:message code="posLogCollectMgmt.commandType"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="commandType"
                                ng-model="commandType"
                                items-source="_getComboData('commandTypeComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="commandTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="posLogCollectMgmt.storeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="storeCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
                </td>
                <%-- 매장명 --%>
                <th><s:message code="posLogCollectMgmt.storeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="storeNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
                </td>
            </tr>
            <tr>
                <%-- 전송구분 --%>
                <th><s:message code="posLogCollectMgmt.sendYn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="sendYn"
                                ng-model="sendYn"
                                items-source="_getComboData('sendYnComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="sendYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 결과 --%>
                <th><s:message code="posLogCollectMgmt.resultCd"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="resultCd"
                                ng-model="resultCd"
                                items-source="_getComboData('resultCdComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="resultCdCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.downCurrent" />
        </button>
        <%-- POS로그수집등록 --%>
        <button class="btn_skyblue ml5 fr" ng-click="regPosLog()">
            <s:message code="posLogCollectMgmt.regPosLog" />
        </button>
    </div>

    <%-- 위즈모 테이블 --%>
    <div class="wj-TblWrap mt5">
        <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.hqOfficeCd"/>"      binding="hqOfficeCd"    align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.hqOfficeNm"/>"      binding="hqOfficeNm"    align="center"  width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.storeCd"/>"         binding="storeCd"       align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.storeNm"/>"         binding="storeNm"       align="center"  width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.saleDate"/>"        binding="saleDate"      align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.posNo"/>"           binding="posNo"         align="center"  width="70"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.seq"/>"             binding="seq"           align="center"  width="60"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.commandId"/>"       binding="commandId"     align="center"  width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.commandType"/>"     binding="commandType"   align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.logType"/>"         binding="logType"       align="center"  width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.startDate"/>"       binding="startDate"     align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.endDate"/>"         binding="endDate"       align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.sql"/>"             binding="sql"           align="left"    width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.dbBackup"/>"        binding="dbBackup"      align="center"  width="90"  is-read-only="true" data-map="dbBackupComboDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.smartOrder"/>"      binding="smartOrder"    align="center"  width="130" is-read-only="true" data-map="smartOrderComboDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.vcatPath"/>"        binding="vcatPath"      align="left"    width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.remark"/>"          binding="remark"        align="left"    width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.sendYn"/>"          binding="sendYn"        align="center"  width="70"  is-read-only="true" data-map="sendYnComboDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.sendDt"/>"          binding="sendDt"        align="center"  width="130" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.regDt"/>"           binding="regDt"         align="center"  width="130" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.regNm"/>"           binding="regNm"         align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.status"/>"          binding="status"        align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.resultCd"/>"        binding="resultCd"      align="center"  width="80"  is-read-only="true" data-map="resultCdComboDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.resultMsg"/>"       binding="resultMsg"     align="left"    width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.data"/>"            binding="data"          align="left"    width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.rowCount"/>"        binding="rowCount"      align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.fileSize"/>"        binding="fileSize"      align="center"  width="80"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.processedAt"/>"     binding="processedAt"   align="center"  width="130" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.errorMessage"/>"    binding="errorMessage"  align="left"    width="120" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
</div>
<script type="text/javascript">
    var userId = "${userId}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/admin/posLogCollectMgmt/posLogCollectMgmt.js?ver=20260320.01" charset="utf-8"></script>
<%-- POS로그수집등록 팝업 --%>
<c:import url="/WEB-INF/view/sys/admin/posLogCollectMgmt/posLogRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>