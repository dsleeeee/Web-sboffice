<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon">


    <div ng-controller="dlvrCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('dlvrCtrl', 1)">
                    <s:message code="cmm.search"/>
                </button>
            </div>
        </div>

        <%-- 상단 타이틀 --%>
        <div class="w100 mt10 mb10">
            <table class="searchTbl">
                <colgroup>
                    <col class="w5"/>
                    <col class="w15"/>
                    <col class="w7"/>
                    <col class="w5"/>
                    <col class="w5"/>
                    <col class="w7"/>
                    <col class="w10"/>
                    <col class="w5"/>
                    <col class="w15"/>
                    <col class="w7"/>
                    <col class="w5"/>
                    <col class="w10"/>
                </colgroup>
                <tbody>

                <%-- First Row--%>
                <tr class="brt">
                    <%-- 회원번호 --%>
                    <th><s:message code="dlvr.membr.no"/></th>
                    <td colspan="2">
                        <input type="text" class="sb-input w100" ng-model="membrNo" id="membrNo"/>
                    </td>
                    <%-- 회원명  --%>
                    <th><s:message code="dlvr.membr.name"/></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="membrNm" id="membrNm"/>
                    </td>
                    <%-- 회원등급  --%>
                    <th><s:message code="dlvr.membr.grade"/></th>
                    <td>
                        <div class="sb-select fl">
                            <wj-combo-box
                                    id="rMemberClass"
                                    ng-model="membrClassCd"
                                    control="memberClassCombo"
                                    items-source="_getComboData('rMemberClass')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>

                <%-- Second Row--%>
                <tr class="brt">
                    <%-- 배달구역 --%>
                    <th><s:message code="dlvr.membr.area"/></th>
                    <td colspan="2">
                        <div class="sb-select mr5 w45 fl">
                            <wj-combo-box
                                    id="dlvrLzoneCd"
                                    ng-model="dlvrLzoneCd"
                                    control="dlvrLzoneCdCombo"
                                    items-source="_getComboData('rMemberDlvrLzone')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <div class="sb-select w45 fl">
                            <wj-combo-box
                                    id="dlvrMzoneCd"
                                    ng-model="dlvrMzoneCd"
                                    control="dlvrMzoneCdCombo"
                                    items-source="dlvrMzone"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 상세주소  --%>
                    <th><s:message code="dlvr.membr.areaDetail"/></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="addrDtl" id="areaDetail"/>
                    </td>
                    <%-- 배달지사용  --%>
                    <th><s:message code="dlvr.membr.areaUseYn"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="useYn"
                                    ng-model="useYn"
                                    control="useYnCombo"
                                    items-source="_getComboData('useYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 전화  --%>
                    <th><s:message code="dlvr.membr.phone"/></th>
                    <td colspan="2">
                        <input type="text" class="sb-input w100" ng-model="telNo" id="telNo"/>
                    </td>
                    <%-- 전화사용  --%>
                    <th><s:message code="dlvr.membr.phoneUseYn"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="dlvrTelUseYn"
                                    ng-model="dlvrTelUseYn"
                                    control="dlvrTelUseYnCombo"
                                    items-source="_getComboData('telUseYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td colspan="2">
                    </td>
                </tr>

                <%-- Thrid Row--%>
                <tr class="brt">
                    <%-- 배달구역 --%>
                    <th><s:message code="dlvr.membr.area"/></th>
                    <td colspan="2">
                        <div class="sb-select mr5 w30 fl">
                            <wj-combo-box
                                    id="totDlvrLzoneCd"
                                    ng-model="totDlvrLzoneCd"
                                    control="totDlvrLzoneCdCombo"
                                    items-source="_getComboData('rMemberDlvrLzone')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <div class="sb-select w30 fl ">
                            <wj-combo-box
                                    id="totDlvrMzoneCd"
                                    ng-model="totDlvrMzoneCd"
                                    control="totDlvrMzoneCdCombo"
                                    items-source="totDlvrMzone"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <button class="btn_skyblue sb-input fl" style=" margin-left: 1%" ng-click="dlvrZoneSetting()">
                            <s:message code="cmm.apply"/>
                        </button>
                    </td>
                    <%-- 배달지사용  --%>
                    <th><s:message code="dlvr.membr.areaUseYn"/></th>
                    <td colspan="3">
                        <div class="sb-select w60 fl">
                            <wj-combo-box
                                    id="totUseYn"
                                    ng-model="totUseYn"
                                    control="totUseYnCombo"
                                    items-source="_getComboData('rUseYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <button class="btn_skyblue sb-input fl" style=" margin-left: 1%" ng-click="dlvrZoneUseSetting()">
                            <s:message code="cmm.apply"/>
                        </button>
                    </td>
                    <%-- 전화  --%>
                    <th><s:message code="dlvr.membr.phoneNumber"/></th>
                    <td colspan="2">
                        <input type="text" class="sb-input w60 fl" ng-model="totTelNo" id="phoneNumber"/>
                        <button class="btn_skyblue sb-input fl" style=" margin-left: 1%" ng-click="telNoSetting()">
                            <s:message code="cmm.apply"/>
                        </button>
                    </td>
                    <%-- 전화사용  --%>
                    <th><s:message code="dlvr.membr.phoneUseYn"/></th>
                    <td colspan="3">
                        <div class="sb-select w60 fl">
                            <wj-combo-box
                                    id="totTelUseYn"
                                    ng-model="totTelUseYn"
                                    control="totTelUseYnCombo"
                                    items-source="_getComboData('useYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <button class="btn_skyblue sb-input fl" style=" margin-left: 1%" ng-click="telNoUseSetting()">
                            <s:message code="cmm.apply"/>
                        </button>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <%-- 그리드 left --%>
        <%-- 페이지 스케일  --%>
        <div class="mt20 oh sb-select dkbr">
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
        </div>
        <div class="w50 fl" style="width:55%;">
            <div class="wj-gridWrap" style="height:600px; overflow-y: hidden; overflow-x: hidden;">
                <h2 class="h2_tit oh lh30">
                    <s:message code="dlvr.membr.adressList"/>
                    <button class="btn_skyblue sb-input" style="float: right; margin-top: 1%; margin-right: 1%"
                            ng-click="infoDelete()">
                        <s:message code="cmm.delete"/>
                    </button>
                    <button class="btn_skyblue sb-input" style="float: right; margin-top: 1%; margin-right: 1%" ng-click="infoSave()">
                        <s:message code="cmm.save"/>
                    </button>
                    <button class="btn_skyblue sb-input" ng-click="excelDownload()"
                            style="float: right; margin-top: 1%; margin-right: 1%">
                        <s:message code="cmm.excel"/>
                    </button>
                </h2>
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">
                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"
                                         is-read-only="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvr.membr.name"/>" binding="membrNm"
                                         width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvr.membr.addr"/>" binding="addr"
                                         width="220" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvr.membr.areaDetail"/>" binding="addrDtl"
                                         width="200" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvr.membr.useYn"/>" binding="useYn" width="90"
                                         align="left" data-map="useYnDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvr.membr.finalDlvrDate"/>" binding="lastDlvrDate"
                                         width="120"
                                         is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvr.membr.time"/>" binding="totDlvrCnt" width="110"
                                         is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>


    <%-- 그리드 right --%>
    <div class="w50 fr" ng-controller="dlvrTelCtrl" style="width:44%;">
        <div class="wj-gridWrap" style="height:600px; overflow-y: hidden; overflow-x: hidden;">
            <h2 class="h2_tit oh lh30">
                <s:message code="dlvr.membr.phoneNumberList"/>
                <button class="btn_skyblue sb-input" style="float: right; margin-top: 1%; margin-right: 1%"
                        ng-click="infoDelete()">
                    <s:message code="cmm.delete"/>
                </button>
                <button class="btn_skyblue sb-input" style="float: right; margin-top: 1%; margin-right: 1%" ng-click="infoSave()">
                    <s:message code="cmm.save"/>
                </button>
                <button class="btn_skyblue sb-input" ng-click="telExcelDownload()"
                        style="float: right; margin-top: 1%; margin-right: 1%">
                    <s:message code="cmm.excel"/>
                </button>
            </h2>
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"
                                     is-read-only="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvr.membr.name"/>" binding="membrNm"
                                     width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.tel"/>" binding="telNo" width="180"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvr.membr.useYn"/>" binding="useYn" width="90"
                                     data-map="useYnDataMap"
                                     is-read-only="false" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvr.membr.finalInputDate"/>" binding="regDt" width="140"
                                     is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvr.membr.finalUpdateDate"/>" binding="modDt" width="140"
                                     is-read-only="true" align="right"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script>
    var useDataMap = ${ccu.getCommCodeExcpAll("067")};
    var useData = ${ccu.getCommCode("067")};
    <%--사용, 미사용--%>
    var memberClassList = ${memberClassList};
    var memberDlvrLzone = ${memberDlvrLzone};
    <%--var memberDlvrMzone = ${memberDlvrMzone};--%>
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/dlvr.js?ver=2019052801.14" charset="utf-8"></script>