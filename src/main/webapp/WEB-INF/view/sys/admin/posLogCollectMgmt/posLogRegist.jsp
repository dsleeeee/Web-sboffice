<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="posLogRegistLayer" control="posLogRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;overflow-y:auto;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="posLogCollectMgmt.regPosLog"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body" >
            <%-- body_위 --%>
            <div ng-controller="searchStoreCtrl">
                <table class="tblType01" >
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <%-- 본사코드 --%>
                            <th>
                                <s:message code="posLogCollectMgmt.hqOfficeCd"/>
                            </th>
                            <td>
                                <input type="text" id="regHqOfficeCd" ng-model="hqOfficeCd" />
                            </td>
                            <%-- 본사명 --%>
                            <th>
                                <s:message code="posLogCollectMgmt.hqOfficeNm"/>
                            </th>
                            <td>
                                <input type="text" id="regHqOfficeNm" ng-model="hqOfficeNm" />
                            </td>
                        </tr>
                        <tr>
                            <%-- 매장코드 --%>
                            <th>
                                <s:message code="posLogCollectMgmt.storeCd"/>
                            </th>
                            <td>
                                <input type="text" id="regStoreCd" ng-model="storeCd" />
                            </td>
                            <%-- 매장명 --%>
                            <th>
                                <s:message code="posLogCollectMgmt.storeNm"/>
                            </th>
                            <td>
                                <input type="text" id="regStoreNm" ng-model="storeNm" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="mt5 oh">
                    <button class="btn_blue fr mr10" id="btnSearchStoreList" ng-click="_pageView('searchStoreCtrl',1)">
                        <s:message code="cmm.search" />
                    </button>
                </div>
                <%--- 매장 그리드 --%>
                <div class="oh mt5">
                    <div class="w100 fl">
                        <div id="regProdGrid" class="wj-gridWrap" style="height: 200px; overflow-y: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.hqOfficeCd"/>"  binding="hqOfficeCd"    width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.hqOfficeNm"/>"  binding="hqOfficeNm"    width="120" align="left"    is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.storeCd"/>"     binding="storeCd"       width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.storeNm"/>"     binding="storeNm"       width="150" align="left"    is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="posLogCollectMgmt.posNo"/>"       binding="posNo"         width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="searchStoreCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

            <%-- body_아래 --%>
            <div class="mt10" ng-controller="regPosLogCtrl">
                <div class="oh tblBr" style="border-top:1px solid #e8e8e8">
                    <table class="tblType01" >
                        <colgroup>
                            <col class="w15" />
                            <col class="w35" />
                            <col class="w15" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                            <tr>
                                <%-- 명령타입 --%>
                                <th>
                                    <s:message code="posLogCollectMgmt.commandType"/>
                                </th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="regCommandType"
                                                ng-model="regCommandType"
                                                items-source="_getComboData('regCommandTypeComboData')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                control="regCommandTypeCombo"
                                                text-changed="changeCommandType(s)">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <%-- 로그타입 --%>
                                <th>
                                    <s:message code="posLogCollectMgmt.logType"/>
                                </th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="regLogType"
                                                ng-model="regLogType"
                                                items-source="_getComboData('regLogTypeComboData')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                control="regLogTypeCombo"
                                                text-changed="changeLogType(s)">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                <td colspan="3" ng-show="showTableLogTypeFg">
                                    <input type="checkbox" id="od" value="OD" name="tableLogTypeFg"><label for="od" class="ml5 mr5"><s:message code='posLogCollectMgmt.od' /></label>
                                    <input type="checkbox" id="sl" value="SL" name="tableLogTypeFg"><label for="sl" class="ml5 mr5"><s:message code='posLogCollectMgmt.sl' /></label>
                                    <input type="checkbox" id="rv" value="RV" name="tableLogTypeFg"><label for="rv" class="ml5 mr5"><s:message code='posLogCollectMgmt.rv' /></label>
                                </td>
                                <td colspan="3" ng-show="showLogFileTypeFg">
                                    <input type="checkbox" id="pos"         value="POS"         name="logFileTypeFg"><label for="pos"         class="ml5 mr5"><s:message code='posLogCollectMgmt.pos' /></label>
                                    <input type="checkbox" id="vcat"        value="VCAT"        name="logFileTypeFg"><label for="vcat"        class="ml5 mr5"><s:message code='posLogCollectMgmt.vcat' /></label>
                                    <input type="checkbox" id="paycoVcat"   value="PaycoVCAT"   name="logFileTypeFg"><label for="paycoVcat"   class="ml5 mr5"><s:message code='posLogCollectMgmt.paycoVcat' /></label>
                                    <input type="checkbox" id="paycoVmem"   value="PaycoVMEM"   name="logFileTypeFg"><label for="paycoVmem"   class="ml5 mr5"><s:message code='posLogCollectMgmt.paycoVmem' /></label>
                                    <input type="checkbox" id="paycoVorder" value="PaycoVORDER" name="logFileTypeFg"><label for="paycoVorder" class="ml5 mr5"><s:message code='posLogCollectMgmt.paycoVorder' /></label>
                                </td>
                            </tr>
                            <tr>
                                <%-- 기간 --%>
                                <th>
                                    <s:message code="posLogCollectMgmt.periodDay"/>
                                </th>
                                <td colspan="3">
                                    <div class="sb-select">
                                        <span class="txtIn w110px">
                                            <wj-input-date
                                                    id="srchDateFrom"
                                                    value="dateFrom"
                                                    ng-model="dateFrom"
                                                    control="dateFromCombo"
                                                    min="2000-01-01"
                                                    max="2099-12-31"
                                                    initialized="_initDateBox(s)">
                                            </wj-input-date>
                                        </span>
                                        <span class="rg">~</span>
                                        <span class="txtIn w110px">
                                            <wj-input-date
                                                    id="srchDateTo"
                                                    value="dateTo"
                                                    ng-model="dateTo"
                                                    control="dateToCombo"
                                                    min="2000-01-01"
                                                    max="2099-12-31"
                                                    initialized="_initDateBox(s)">
                                            </wj-input-date>
                                        </span>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div id="sqlDiv" ng-show="showSqlDiv">
                    <h3 class="h3_tbl"><s:message code="posLogCollectMgmt.sql"/></h3>
                    <div class="tblBr">
                        <table class="tblType01">
                            <colgroup>
                                <col class="w100" />
                            </colgroup>
                            <tbody>
                                <td>
                                    <textarea class="sb-input w100" style="height:70px;resize: none;" id="sql" ng-model="sql"></textarea>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="oh tblBr">
                    <table class="tblType01" >
                        <colgroup>
                            <col class="w15" />
                            <col class="w35" />
                            <col class="w15" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                            <tr>
                                <%-- DB백업 포함여부 --%>
                                <th>
                                    <s:message code="posLogCollectMgmt.dbBackupYn"/>
                                </th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="regDbBackup"
                                                ng-model="regDbBackup"
                                                items-source="_getComboData('regDbBackupComboData')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                control="regDbBackupCombo">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                <%-- 스마트오더 수집여부 --%>
                                <th>
                                    <s:message code="posLogCollectMgmt.smartOrder"/>
                                </th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="regSmartOrder"
                                                ng-model="regSmartOrder"
                                                items-source="_getComboData('regSmartOrderComboData')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                control="regSmartOrderCombo">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <%-- VCAT 경로 --%>
                                <th>
                                    <s:message code="posLogCollectMgmt.vcatPath"/>
                                </th>
                                <td colspan="3">
                                    <input type="text" id="vcatPath" ng-model="vcatPath" />
                                </td>
                            </tr>
                            <tr>
                                <%-- 사유 --%>
                                <th>
                                    <s:message code="posLogCollectMgmt.remark"/>
                                </th>
                                <td colspan="3">
                                    <input type="text" id="remark" ng-model="remark" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="tc mt10">
                    <div>
                        <button class="btn_blue" ng-click="savePosLog()"><s:message code="cmm.save"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/sys/admin/posLogCollectMgmt/posLogRegist.js?ver=20260311.02" charset="utf-8"></script>