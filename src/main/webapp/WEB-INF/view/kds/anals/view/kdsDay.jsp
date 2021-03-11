<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="kdsDayCtrl">
    <input type="hidden" id="resurceFg" class="sb-input w50" ng-model="resurceFg" value="${orgnFg}"/>
    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('kdsDayList')">
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
                <%-- 영업일자 --%>
                <th>
                    <s:message code="kds.saleDate"/>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="kdsDayStartDate" name="kdsDayStartDate" class="w150px"/></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="kdsDayEndDate" name="kdsDayEndDate" class="w150px"/></span>
                    </div>
                </td>
            </tr>
            <tr>
                <th>
                    <s:message code="kds.makeDate.setting"/>
                </th>
                <td>
                    <div class="sb-select">
                    <span class="txtIn w150px">
                        <wj-combo-box
                                id="makeDate"
                                ng-model="makeDate"
                                control="makeDateCombo"
                                items-source="_getComboData('kdsMakeDate')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn w150px">
                        <wj-combo-box
                                id="makeDateSec"
                                ng-model="makeDateSec"
                                control="makeDateSecCombo"
                                items-source="_getComboData('kdsMakeDateSec')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                    </div>
                </td>
                <th>
                    <s:message code="kds.picDate.setting"/>
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn w150px">
                            <wj-combo-box
                                    id="picDate"
                                    ng-model="picDate"
                                    control="picDateCombo"
                                    items-source="_getComboData('kdsPicDate')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                        <span class="rg">~</span>
                        <span class="txtIn w150px">
                            <wj-combo-box
                                    id="picDateSec"
                                    ng-model="picDateSec"
                                    control="picDateSecCombo"
                                    items-source="_getComboData('kdsPicDateSec')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
            </tr>
            <c:if test="${orgnFg == 'HQ'}">
            <tr>
                <th><s:message code="kds.store"/></th>
                <td>
                    <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                        <jsp:param name="targetId" value="regStore"/>
                    </jsp:include>
                </td>
                <th></th>
                <td></td>
            </tr>
            </c:if>
        </tbody>
    </table>

    <div id="divChart" class="w100 mt40 mb20" style="display: none;">
        <div class="sb-select mt10 fl">
            <%-- 차트 --%>
            <%--<button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="chartKds()">
                <s:message code="kds.chart"/>
            </button>--%>
            <span class="chk ml10">
                    <%-- 제조시간 --%>
                    <input type="checkbox" name="makeChkDt" id="makeChecked" ng-model="makeChecked" ng-change="makeChkDt()">
                    <label for="makeChecked"><s:message code='kds.makeDate'/></label>
                </span>
            <span class="chk ml10">
                    <%-- 픽업시간 --%>
                    <input type="checkbox" name="picChkDt" id="picChecked" ng-model="picChecked" ng-change="picChkDt()">
                    <label for="picChecked"><s:message code='kds.picDate'/></label>
            </span>
            <%-- 건수 --%>
            <%--        <span class="btn_skyblue ml5 mt5 fr">--%>
            <%--			<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()"/>--%>
            <%--			<label for="chkDt">--%>
            <%--				<s:message code="kds.cnt"/>--%>
            <%--			</label>--%>
            <%--		</span>--%>

        </div>
        <h3>
            <%--위즈모 차트--%>
            <div class="circle" style="padding: 10px;">
                    <span class="orange" ng-if="makeChecked === true" style="color:#ff9d39 !important;">
                        <s:message code="kds.makeDate"/>
                    </span>
                <span class="green" ng-if="picChecked === true" style="color: #00ba8b !important;">
                        <s:message code="kds.picDate"/>
                    </span>
            </div>
        </h3>
        <div class="wizWrap" id="chart1" style="width:100%; height:370px; font-size: 10pt;" ></div>
    </div>

    <div class="mt20 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()">
            <s:message code="cmm.excel.down"/>
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
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
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="kds.storeCd"/>" binding="storeCd" width="150"
                                         is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kds.storeNm"/>" binding="storeNm" width="150"
                                         is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="kds.saleDate"/>" binding="saleDate"
                                     width="150"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.totalOrderCnt"/>" binding="orderCnt" width="150"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>" binding="avgMake" width="150"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>" binding="avgPic" width="150"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
</div>
<script>
    var kdsMakeDate = ${ccu.getCommCode("400")};
    var kdsPicDate = ${ccu.getCommCodeExcpAll("401")};
    var kdsMakeDateSec = ${ccu.getCommCode("402")};
    var kdsPicDateSec = ${ccu.getCommCodeExcpAll("403")};
    var regstrStoreList = ${regstrStoreList};
</script>
<style>
    .circle span.orange:before {
        content: '';
        width: 10px;
        height: 10px;
        border-radius: 100%;
        background: #ff9d39;
        border: 1px solid #ff9d39;
        position: absolute;
        left: 0px;
        top: 4px;
    }

    .circle span.green:before {
        content: '';
        width: 10px;
        height: 10px;
        border-radius: 100%;
        background: #00ba8b;
        border: 1px solid #00ba8b;
        position: absolute;
        left: 0px;
        top: 4px;
    }
</style>

<script type="text/javascript" src="/resource/solbipos/js/kds/anals/chart/kdsDay.js?ver=2020070802.12" charset="utf-8"></script>


