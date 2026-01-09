<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="dlvrAgencyLinkCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="dlvrAgencyLink.dlvrAgencyLink"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('dlvrAgencyLinkCtrl',1)">
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
            <%-- 주문 연동 활성화  --%>
            <th><s:message code="dlvrAgencyLink.link.active"/></th>
            <td colspan="3">
                <div class="sb-select w30">
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
        </tr>
        </tbody>
    </table>

    <table class="searchTbl mt10">
        <colgroup>
            <col class="w75"/>
            <col class="w25"/>
        </colgroup>
        <tbody>
        <tr class="brt">
            <th class="oh gr">
                <%-- 유저 구독여부 안내 --%>
                <p class="s13 bk pdt5 pdb5" style="height: 25px;">
                    <label id="lblText"></label>
                </p>
            </th>
            <th>
                <div class="updownSet oh mb10">
                    <button class="btn_skyblue" id="btnSave" ng-click="btnSave()">
                        <s:message code="cmm.save"/>
                    </button>
                    <button class="btn_skyblue" id="orderkitGoto" ng-click="orderkitGoto()">
                        <s:message code="dlvrAgencyLink.orderkitGoto"/>
                    </button>
                </div>
            </th>
        </tr>
        </tbody>
    </table>

    <%-- 안내 문구 --%>
    <div class="mt10 oh" id="divInstructions" style="display: none;">
        <p class="tl s14 mt5 lh15">▶ '부릉'은 고객센터를 통해 연동하실 수 있습니다.</p>
        <p class="tl s14 mt5 lh15">▶ 링크포스 고객센터 1644-5195 문의해 주세요.</p>
    </div>

    <%-- left --%>
    <div class="wj-TblWrap mt10 mb20 w50 fl" id="divLeft" style="display: none;">
        <div class="wj-TblWrapBr mr10 pd10" style="height:550px;">
            <span class="bk lh30"><s:message code='dlvrAgencyLink.dlvrAgencyLink.status'/></span>
            <div class="updownSet oh mb10 pd5">
                <button class="btn_skyblue" id="btnAdd" ng-click="btnAdd()">
                    <s:message code="dlvrAgencyLink.dlvrAgency.reg"/>
                </button>
                <button class="btn_skyblue" id="btnClear" ng-click="btnClear()">
                    <s:message code="dlvrAgencyLink.clear"/>
                </button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:450px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            id="wjGridMain"
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.dlvrAgency"/>" binding="riderName" width="200" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.deposit"/>" binding="deposit" width="100" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.linkDate"/>" binding="mappingDateTime" width="200" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%-- left --%>

    <%-- right --%>
    <div class="wj-TblWrap mt10 mb20 w50 fl" id="divRight" style="display: none;">
        <div class="wj-TblWrapBr pd10" style="height:550px;">

        </div>
    </div>
    <%-- right --%>

</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/dlvrAgencyLink/dlvrAgencyLink.js?ver=20260107.01" charset="utf-8"></script>

<%-- 배달대행사 추가 팝업 --%>
<c:import url="/WEB-INF/view/dlvr/info/dlvrAgencyLink/dlvrAgencyReg.jsp">
</c:import>