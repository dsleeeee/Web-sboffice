<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<%-- 리스트 영역 --%>
<div class="subCon" ng-controller="eventMessageCtrl" style="padding-bottom: 0;">
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="fl"><s:message code="eventMessage.manage"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue" id="btnRegist" ng-click="openEventMessageReg()">
                <s:message code="cmm.new.add" />
            </button>
            <button class="btn_blue mr3" id="btnSearch" ng-click="_pageView('eventMessageCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w10" />
            <col class="w20" />
            <col class="w10" />
            <col class="w20" />
            <col class="w10" />
            <col class="w20" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 출력문구 --%>
            <th><s:message code="eventMessage.printMessage" /></th>
            <td>
                <input type="text" class="sb-input w100" ng-model="printMessage1"/>
            </td>
            <%-- 사용여부 --%>
            <th><s:message code="eventMessage.useYn" /></th>
            <td>
                <div class="sb-select w100">
                    <wj-combo-box
                        ng-model="useYn"
                        items-source="_getComboData('useYnAll')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 일자 --%>
            <th><s:message code="eventMessage.date" /></th>
            <td>
                <div class="sb-select">
                    <div style="float: left; padding: 5px 10px 5px 5px;">
                        <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()"/>
                    </div>
                    <span class="txtIn w150px">
                        <input id="eventMessageDate">
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 응모권출력여부 --%>
            <th><s:message code="eventMessage.ticketPrintYn" /></th>
            <td>
                <div class="sb-select w100">
                    <wj-combo-box
                            ng-model="ticketPrintYn"
                            items-source="_getComboData('ticketPrintYnAll')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 재출력여부 --%>
            <th><s:message code="eventMessage.rePrintYn" /></th>
            <td>
                <div class="sb-select w100">
                    <wj-combo-box
                            ng-model="rePrintYn"
                            items-source="_getComboData('rePrintYnAll')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 출력조건 --%>
            <th><s:message code="eventMessage.printCondiFg" /></th>
            <td>
                <div class="sb-select w100">
                    <wj-combo-box
                            ng-model="printCondiFg"
                            items-source="_getComboData('printCondiAll')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <%--//searchTbl--%>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div class="wj-gridWrap" style="height: 150px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="" binding="hqOfficeCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="eventMessage.msgCd"/>" binding="msgCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="eventMessage.startYmd"/>" binding="startYmd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="eventMessage.endYmd"/>" binding="endYmd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="eventMessage.ticketPrintYn"/>" binding="ticketPrintYn" data-map="ticketPrintYnFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="eventMessage.rePrintYn"/>" binding="rePrintYn" data-map="rePrintYnFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="eventMessage.printCondiFg"/>" binding="printCondiFg" data-map="printCondiFgDataMap" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="eventMessage.printMessage"/>" binding="printMessage1" width="400" align="left" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="eventMessage.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>
</div>

<%-- 등록 영역 --%>
<div class="subCon" id="eventMessageReg" style="display: none;" ng-controller="eventMessageRegCtrl">

    <%-- 저장버튼 --%>
    <div class="tr">
        <button class="btn_blue" style="font-size: 16px;" id="btnSave" ng-click="saveEventMessage()">
            <s:message code="cmm.save" />
        </button>
    </div>

    <div id="divDetailReg">
        <%-- left --%>
        <div class="fl w50 mt10 pdr5" style="margin-bottom: 100px;">
            <%-- 적용조건 등록 영역 --%>
            <table class="searchTbl">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                <tr style="border-top: 1px solid #CCCCCC;">
                    <th colspan="4">
                        <span style="font-size: 13px;"><s:message code="eventMessage.condition" /></span>
                    </th>
                </tr>
                <tr>
                    <%-- 적용기간 --%>
                    <th>
                        <div style="float: left;"><input type="checkbox" id="chkPeriod" ng-model="isCheckedPeriod" ng-change="isChkPeriod()"/></div>
                        <div style="padding-top: 3px; padding-left: 20px;"><s:message code="eventMessage.period" /></div>
                    </th>
                    <td colspan="3">
                        <div class="sb-select" id="divChkPeriod" style="display: none;">
                            <span class="txtIn"><input id="eventMessageStartDate" class="w150px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="eventMessageEndDate" class="w150px"></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 응모권출력여부 --%>
                    <th><s:message code="eventMessage.ticketPrintYn" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="ticketPrintYn"
                                    ng-model="ticketPrintYn"
                                    items-source="_getComboData('ticketPrintYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="ticketPrintYnCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 재출력여부 --%>
                    <th><s:message code="eventMessage.rePrintYn" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="rePrintYn"
                                    ng-model="rePrintYn"
                                    items-source="_getComboData('rePrintYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="rePrintYnCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 출력조건 --%>
                    <th><s:message code="eventMessage.printCondiFg" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="printCondiFg"
                                    ng-model="printCondiFg"
                                    items-source="_getComboData('printCondiFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="printCondiFgCombo"
                                    selected-index-changed="setProd(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <%-- 사용여부 --%>
                    <th><s:message code="eventMessage.useYn" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="useYn"
                                    ng-model="useYn"
                                    items-source="_getComboData('useYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="useYnCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>

            <%-- 적용상품 등록 영역 --%>
            <table class="searchTbl mt10" id="tblEventMessageProd">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                <tr style="border-top: 1px solid #CCCCCC;">
                    <th colspan="4">
                        <span style="font-size: 13px;"><s:message code="eventMessage.prod" /></span>
                    </th>
                </tr>
                <tr>
                    <td colspan="4" style="border-left: 1px solid #CCCCCC;" ng-controller="selectProdGridCtrl">
                        <div class="updownSet mt5 mb5" style="font-size: 15px;">
                            <%-- 상품추가 --%>
                            <button class="btn_skyblue" id="btnProdAdd" ng-click="prodAdd()"><s:message code='eventMessage.prodAdd' /></button>
                            <%-- 저장 --%>
                            <button class="btn_skyblue" id="btnProdSave" ng-click="prodSave()"><s:message code="cmm.save" /></button>
                            <%-- 삭제 --%>
                            <button class="btn_skyblue" id="btnProdDel" ng-click="prodDel()"><s:message code='cmm.del' /></button>
                        </div>
                        <div class="wj-gridWrap" style="height:225px; overflow-x: hidden; overflow-y: hidden;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flexSelectProdGrid"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="eventMessage.code"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="eventMessage.name"/>" binding="prodNm" width="250" is-read-only="true" align="left"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="eventMessage.saleQty"/>" binding="saleQty" width="65" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <%--right--%>
        <div class="fr w50 mt10 pdl5" style="margin-bottom: 50px;">

            <%-- 출력문구 등록 영역 --%>
            <table class="searchTbl">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                    <tr style="border-top: 1px solid #CCCCCC;">
                        <th colspan="4">
                            <span style="font-size: 13px;"><s:message code="eventMessage.printMessage" /></span>
                        </th>
                    </tr>
                    <tr>
                        <%-- 출력문구 --%>
                        <td colspan="4" style="font-size: 16px; border-left: 1px solid #CCCCCC;">
                            <textarea id="printMessage1" ng-model="printMessage1" class="w100 tArea1" rows="5" maxlength="700" style="height: 121px;"></textarea>
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 적용매장 등록 영역 --%>
            <table class="searchTbl mt10" id="tblEventMessageStore">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                    <tr style="border-top: 1px solid #CCCCCC;">
                        <th colspan="4">
                            <span style="font-size: 13px;"><s:message code="eventMessage.eventMessageStore" /></span>
                        </th>
                    </tr>
                    <tr>
                        <td colspan="4" style="border-left: 1px solid #CCCCCC;" ng-controller="selectStoreGridCtrl">
                            <div class="updownSet mt5 mb5" style="font-size: 15px;">
                                <%-- 매장추가 --%>
                                <button class="btn_skyblue" id="btnStoreAdd" ng-click="storeAdd()"><s:message code='eventMessage.storeAdd' /></button>
                                <%-- 매장삭제 --%>
                                <button class="btn_skyblue" id="btnStoreDel" ng-click="storeDel()"><s:message code='cmm.del' /></button>
                            </div>
                            <div class="wj-gridWrap" style="height:225px; overflow-x: hidden; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flexSelectStoreGrid"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="eventMessage.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="eventMessage.storeNm"/>" binding="storeNm" width="400" is-read-only="true" align="left"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </td>
                    </tr>
                    </tbody>
            </table>
        </div>
    </div>

    <%-- 상세조회 시, msgCd 값 hidden에 갖고 있기 --%>
    <input type="hidden" id="hdMsgCd" />
</div>

<style type="text/css">
    input[type=checkbox]  {
        width: 17px;
        height: 17px;
    }
</style>

<script language="JavaScript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}"
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/eventMessage/eventMessage.js?ver=20210503.04" charset="utf-8"></script>

<%-- 적용상품 상품추가 --%>
<c:import url="/WEB-INF/view/base/promotion/eventMessage/eventMessageProdReg.jsp">
</c:import>

<%-- 적용매장 추가 --%>
<c:import url="/WEB-INF/view/base/promotion/eventMessage/eventMessageStoreReg.jsp">
</c:import>