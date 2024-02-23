<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="longTermUnusedView" class="subCon" style="display: none;" ng-controller="longTermUnusedCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="accountManage.longTermUnused"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('longTermUnusedCtrl',1)" id="nxBtnSearch1">
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
                <%-- 본사코드 --%>
                <th>
                    <s:message code="accountManage.hqOfficeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 본사명 --%>
                <th>
                    <s:message code="accountManage.hqOfficeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th>
                    <s:message code="accountManage.storeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 매장명 --%>
                <th>
                    <s:message code="accountManage.storeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>

            <tr>
                <%-- 본사상태 --%>
                <th><s:message code="accountManage.hqSysStatFg" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchHqSysStatFg"
                                ng-model="hqSysStatFg"
                                items-source="_getComboData('hqSysStatFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="hqSysStatFgCombo"
                                selected-index="0">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 매장상태 --%>
                <th><s:message code="accountManage.storeSysStatFg" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchStoreSysStatFg"
                                ng-model="storeSysStatFg"
                                items-source="_getComboData('StoreSysStatFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="storeSysStatFgCombo"
                                selected-index="0">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 사용자 ID --%>
                <th><s:message code="accountManage.userId" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 사용자명 --%>
                <th><s:message code="accountManage.userNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchUserNm" ng-model="userNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 접속기간 --%>
                <th><s:message code="accountManage.loginPeriod" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchLoginPeriod"
                                ng-model="loginPeriod"
                                items-source="_getComboData('loginPeriod')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="loginPeriodCombo"
                                selected-index="3">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 계정사용여부 --%>
                <th>
                    <s:message code="accountManage.webUseYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchWebUseYn"
                                ng-model="webUseYn"
                                items-source="_getComboData('webUseYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="webUseYnCombo"
                                selected-index="1">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 사용자구분 --%>
                <th>
                    <s:message code="accountManage.orgnFg2" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchOrgnFg2"
                                ng-model="orgnFg2"
                                items-source="_getComboData('orgnFg2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="orgnFg2Combo"
                                selected-index="6">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
        <%-- 계정복구 --%>
        <button class="btn_skyblue ml5 fr" ng-click="accountStatChg('recovery')" style="display:none;"><s:message code="accountManage.account"/><s:message code="accountManage.recovery"/></button>
        <%-- 계정삭제 --%>
        <button class="btn_skyblue ml5 fr" ng-click="accountStatChg('delete')" style="display:none;"><s:message code="accountManage.account"/><s:message code="accountManage.delete"/></button>
        <%-- 계정사용허용 --%>
        <button class="btn_skyblue ml5 fr" ng-click="accountStatChg('allowedUse')"><s:message code="accountManage.account"/><s:message code="accountManage.allowedUse"/></button>
        <%-- 계정사용중지 --%>
        <button class="btn_skyblue ml5 fr" ng-click="accountStatChg('stopUsing')"><s:message code="accountManage.account"/><s:message code="accountManage.stopUsing"/></button>
        <%-- 계정휴면 --%>
        <button class="btn_skyblue ml5 fr" ng-click="accountStatChg('dormancy')" style="display:none;"><s:message code="accountManage.account"/><s:message code="accountManage.dormancy"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.orgnFg2"/>" binding="orgnFg2" width="80" data-map="orgnFg2DataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.orgnCd"/>" binding="orgnCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.orgnNm"/>" binding="orgnNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.hqOfficeCd"/>" binding="hqOfficeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.hqOfficeNm"/>" binding="hqOfficeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.hqSysStatFg"/>" binding="hqSysStatFg" data-map="sysStatFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.storeSysStatFg"/>" binding="storeSysStatFg" data-map="sysStatFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.userId"/>" binding="userId" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.userNm"/>" binding="userNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.webUseYn"/>" binding="webUseYn" data-map="useYnDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.useYn"/>" binding="useYn" data-map="useYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.webUserUseYn"/>" binding="webUserUseYn" data-map="useYnDataMap" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.lastSaleYm"/>" binding="saleYm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.lastLoginDt"/>" binding="lastLoginDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="orgnFg" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="empNo" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="longTermUnusedCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%-- 엑셀다운로드 그리드 --%>
    <div style="display: none;" ng-controller="longTermUnusedExcelCtrl">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                control="excelflex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="accountManage.orgnFg2"/>" binding="orgnFg2" width="80" data-map="orgnFg2DataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.orgnCd"/>" binding="orgnCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.orgnNm"/>" binding="orgnNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.hqOfficeCd"/>" binding="hqOfficeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.hqOfficeNm"/>" binding="hqOfficeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.hqSysStatFg"/>" binding="hqSysStatFg" data-map="sysStatFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.storeSysStatFg"/>" binding="storeSysStatFg" data-map="sysStatFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.userId"/>" binding="userId" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.userNm"/>" binding="userNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.webUseYn"/>" binding="webUseYn" data-map="useYnDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.useYn"/>" binding="useYn" data-map="useYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.webUserUseYn"/>" binding="webUserUseYn" data-map="useYnDataMap" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.lastSaleYm"/>" binding="saleYm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountManage.lastLoginDt"/>" binding="lastLoginDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="orgnFg" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="empNo" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/accountManage/longTermUnused.js?ver=20240223.02" charset="utf-8"></script>