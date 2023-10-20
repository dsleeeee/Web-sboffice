<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="kdsDayProdTimeCtrl">
    <input type="hidden" id="resurceFg" class="sb-input w50" ng-model="resurceFg" value="${orgnFg}"/>
    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('kdsDayProdTimeList')" id="nxBtnSearch">
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
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="kdsDayStartDate" name="kdsDayStartDate" class="w120px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="kdsDayEndDate" name="kdsDayEndDate" class="w120px"/></span>
                </div>
            </td>
            <%-- 시간대 --%>
            <th>
                <s:message code="kds.time"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w120px">
                        <wj-combo-box
                                id="timeZone"
                                ng-model="timeZone"
                                control="timeZoneCombo"
                                items-source="kdsTimeZone"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn w120px">
                        <wj-combo-box
                                id="timeZoneSec"
                                ng-model="timeZoneSec"
                                control="timeZoneSecCombo"
                                items-source="kdsTimeZone"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <th>
                <s:message code="kds.makeDate.setting"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w120px">
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
                    <span class="txtIn w120px">
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
                    <span class="txtIn w120px">
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
                    <span class="txtIn w120px">
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
        <tr>
            <th><s:message code="kds.prodCd"/></th>
            <td>
                <input type="text" id="prodCd" class="sb-input w100" ng-model="prodCd" maxlength="15" onkeyup="fnNxBtnSearch();"/>
            </td>
            <th><s:message code="kds.prodNm"/></th>
            <td>
                <input type="text" id="prodNm" class="sb-input w100" ng-model="prodNm" maxlength="15" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <c:if test="${orgnFg == 'HQ'}">
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="regStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </c:if>
            <%-- 분류조회 --%>
            <th><s:message code="kds.prodClass"/></th>
            <td>
                <input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="prodCorner.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <c:if test="${orgnFg != 'HQ'}">
                <td></td>
                <td></td>
            </c:if>
        </tr>
        </tbody>
    </table>

    <div id="divChart" class="w100 mt10 mb20" style="display: none;">
        <div class="sb-select mt10 fl">
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
        </div>
        <%--위즈모 차트--%>
        <h3>
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

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="excelDownloadInfo()">
            <s:message code="cmm.excel.down"/>
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    id="wjGridList"
            >
                <%-- ng-repeat="item in kdsTimeZone"--%>
                <!-- define columns -->
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="kds.storeCd"/>" binding="storeCd" width="80"
                                         is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kds.storeNm"/>" binding="storeNm" width="120"
                                         is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="kds.saleDate"/>" binding="saleDate"
                                     width="80"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
                <%--                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/> {{item.name}}"--%>
                <%--                                     binding="cntHh{{item.value}}"--%>
                <%--                                     width="75" is-read-only="true" align="center"></wj-flex-grid-column>--%>
                <%--                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/> {{item.name}}"--%>
                <%--                                     binding="makeHh{{item.value}}"--%>
                <%--                                     width="75" is-read-only="true" align="center"></wj-flex-grid-column>--%>
                <%--                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/> {{item.name}}"--%>
                <%--                                     binding="picHh{{item.value}}"--%>
                <%--                                     width="75" is-read-only="true" align="center"></wj-flex-grid-column>--%>

                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh00" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh00" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh00" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh01" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh01" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh01" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh02" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh02" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh02" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh03" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh03" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh03" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh04" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh04" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh04" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh05" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh05" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh05" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh06" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh06" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh06" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh07" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh07" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh07" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh08" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh08" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh08" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh09" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh09" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh09" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh10" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh10" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh10" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh11" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh11" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh11" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh12" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh12" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh12" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh13" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh13" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh13" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh14" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh14" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh14" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh15" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh15" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh15" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh16" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh16" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh16" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh17" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh17" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh17" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh18" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh18" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh18" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh19" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh19" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh19" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh20" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh20" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh20" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh21" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh21" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh21" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh22" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh22" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh22" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.sale.amt"/>"
                                     binding="qtyHh23" width="70" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>"
                                     binding="makeHh23" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>"
                                     binding="picHh23" width="80" is-read-only="true"
                                     align="center" visible="false"></wj-flex-grid-column>
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

    var orgnFg = "${orgnFg}";

    <%--var kdsTimeZone = ${kdsTimeZone};--%>
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

<script type="text/javascript" src="/resource/solbipos/js/kds/anals/chart/kdsDayProdTime.js?ver=2020070801.19"
        charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
