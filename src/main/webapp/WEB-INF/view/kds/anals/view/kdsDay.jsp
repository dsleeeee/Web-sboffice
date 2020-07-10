<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="kdsDayCtrl">

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
            <col class="w5"/>
            <col class="w15"/>
            <col class="w5"/>
            <col class="w25"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 영업일자 --%>
            <th>
                <s:message code="kds.saleDate"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                      <div class="sb-select">
                        <wj-input-date
                                value="kdsDayStartDate"
                                ng-model="kdsDayStartDate"
                                control="kdsDayStartDateCombo"
                                format="yyyy/MM/dd"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                        </wj-input-date>
                      </div>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn">
                    <div class="sb-select">
                      <wj-input-date
                              value="kdsDayEndDate"
                              ng-model="kdsDayEndDate"
                              control="kdsDayEndDateCombo"
                              format="yyyy/MM/dd"
                              min="2000-01-01"
                              max="2099-12-31"
                              initialized="_initDateBox(s)">
                      </wj-input-date>
                    </div>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 영업일자 --%>
            <th>
                <s:message code="kds.makeDate.setting"/>
            </th>
            <td>
                <div class="sb-select fl w20 mr10">
                    <wj-combo-box
                            id="membrClass"
                            ng-model="detailData.membrClass"
                            control="membrClassCombo"
                            items-source="_getComboData('kdsMakeDate')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <div class="sb-select fl w20 mr10">
                    <wj-combo-box
                            id="membrClass"
                            ng-model="detailData.membrClass"
                            control="membrClassCombo"
                            items-source="_getComboData('kdsMakeDateSec')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <th>
                <s:message code="kds.picDate.setting"/>
            </th>
            <td>
                <div class="sb-select fl w20 mr10">
                    <wj-combo-box
                            id="membrClass"
                            ng-model="detailData.membrClass"
                            control="membrClassCombo"
                            items-source="_getComboData('picDate')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <div class="sb-select fl w20 mr10">
                    <wj-combo-box
                            id="membrClass"
                            ng-model="detailData.membrClass"
                            control="membrClassCombo"
                            items-source="_getComboData('picDateSec')"
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


    <div class="mt20 oh sb-select dkbr">


        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="memberVendorMapping()">
            <s:message code="cmm.excel.down"/>
        </button>
        <%-- 차트 --%>
        <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="memberVendorMapping()">
            <s:message code="kds.chart"/>
        </button>
        <%-- 픽업시간 --%>
        <span class="btn_skyblue ml5 mt5 fr">
			<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()"/>
			<label for="chkDt">
				<s:message code="kds.picDate"/>
			</label>
		</span>
        <%-- 제조시간 --%>
        <span class="btn_skyblue ml5 mt5 fr">
			<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()"/>
			<label for="chkDt">
				 <s:message code="kds.makeDate"/>
			</label>
		</span>
        <%-- 건수 --%>
<%--        <span class="btn_skyblue ml5 mt5 fr">--%>
<%--			<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()"/>--%>
<%--			<label for="chkDt">--%>
<%--				<s:message code="kds.cnt"/>--%>
<%--			</label>--%>
<%--		</span>--%>
    </div>
    <div class="w100 mt40 mb20 ">
        <%--위즈모 차트--%>
        <h2>
            <div class="circle"><span class="blue"></span><span class="sky"></span></div>
        </h2>
        <div class="wizWrap" id="chart1" style="width:100%; height:370px;"></div>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt40">
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
                <wj-flex-grid-column header="<s:message code="kds.storeCd"/>" binding="membrNo" width="*"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.storeNm"/>" binding="membrNm" width="*"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.saleDate"/>" binding="membrCardNo"
                                     width="*"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.totalOrderCnt"/>" binding="saleCount" width="*"
                                     is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>" binding="saleAmt" width="*"
                                     is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>" binding="dcAmt" width="*"
                                     is-read-only="true" align="right"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
</div>
<script>
    <%--수신, 미수신--%>
    var kdsMakeDate = ${ccu.getCommCode("400")};

    <%--수신, 미수신--%>
    var picDate = ${ccu.getCommCodeExcpAll("401")};

    <%--수신, 미수신--%>
    var kdsMakeDateSec = ${ccu.getCommCode("402")};

    <%--여자, 남자, 사용안함--%>
    var picDateSec = ${ccu.getCommCodeExcpAll("403")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/kds/anals/day/kdsDay.js?ver=2020070801.08"
        charset="utf-8"></script>
