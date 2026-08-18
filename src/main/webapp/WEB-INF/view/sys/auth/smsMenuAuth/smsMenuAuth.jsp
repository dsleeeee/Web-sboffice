<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon">

    <%-- SMS 관리자 화면 --%>
    <div ng-controller="smsMenuAuthCtrl">
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <div class="mr15 fr" style="display:block; position:relative; margin-top:6px;">
                <button class="btn_blue mr3" id="nxBtnSearch" ng-click="_pageView('smsMenuAuthCtrl', 1)">
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
                <%-- 메뉴코드 --%>
                <th><s:message code="smsMenuAuth.resrceCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSmsMenuAuthResrceCd" ng-model="resrceCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 메뉴명 --%>
                <th><s:message code="smsMenuAuth.resrceNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSmsMenuAuthResrceNm" ng-model="resrceNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="wj-TblWrap mt20 mb20 w30 fl">
            <div class="wj-TblWrapBr pd10" style="height:570px; overflow-y:hidden;">
                <div class="updownSet oh mb10">
                    <span class="fl bk s14 lh30"><s:message code="smsMenuAuth.smsAdminMenu"/></span>
                </div>
                <div class="wj-gridWrap" style="height:500px; overflow:hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <wj-flex-grid-column header="<s:message code="smsMenuAuth.resrceCd"/>" binding="resrceCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsMenuAuth.resrceNm"/>" binding="resrceNm" width="180" is-read-only="true" align="left"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <%-- 등록된 사용자 정보 --%>
    <div class="wj-TblWrap mt20 mb20 w35 fl" ng-controller="smsMenuAuthRegUserCtrl">
        <div class="wj-TblWrapBr ml10 pd10" style="height:570px; overflow-y:hidden;">
            <div class="updownSet mb10" style="overflow:hidden;">
                <span id="lblSmsMenuAuthRegUser" class="bk s14 lh30"
                      style="display:block; margin-right:52px; overflow:hidden; text-align:left; text-overflow:ellipsis; white-space:nowrap;"><s:message code="smsMenuAuth.regUserInfo"/></span>
                <button class="btn_skyblue" id="btnDeleteSmsMenuAuth"
                        style="position:absolute; top:0; right:0; z-index:1;"
                        ng-click="deleteSmsMenuAuth()">
                    <s:message code="cmm.del"/>
                </button>
            </div>
            <div class="wj-gridWrap" style="height:500px; overflow:hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.empNo"/>" binding="empNo" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.empNm"/>" binding="empNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.userId"/>" binding="userId" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.webUseYn"/>" binding="webUseYn" width="90" is-read-only="true" align="center" data-map="useYnComboDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.serviceFg"/>" binding="serviceFg" width="90" is-read-only="true" align="center" data-map="serviceFgComboDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.useYn"/>" binding="useYn" width="80" is-read-only="true" align="center" data-map="useYnComboDataMap"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 미등록된 관리자 사용자 정보 --%>
    <div class="wj-TblWrap mt20 mb20 w35 fr" ng-controller="smsMenuAuthNoRegUserCtrl">
        <div class="wj-TblWrapBr ml10 pd10" style="height:570px; overflow-y:hidden;">
            <div class="updownSet mb10" style="overflow:hidden;">
                <span id="lblSmsMenuAuthNoRegUser" class="bk s14 lh30"
                      style="display:block; margin-right:52px; overflow:hidden; text-align:left; text-overflow:ellipsis; white-space:nowrap;"><s:message code="smsMenuAuth.noRegUserInfo"/></span>
                <button class="btn_skyblue" id="btnAddSmsMenuAuth"
                        style="position:absolute; top:0; right:0; z-index:1;"
                        ng-click="addSmsMenuAuth()">
                    <s:message code="cmm.add"/>
                </button>
            </div>
            <div class="wj-gridWrap" style="height:500px; overflow:hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.empNo"/>" binding="empNo" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.empNm"/>" binding="empNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.userId"/>" binding="userId" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.webUseYn"/>" binding="webUseYn" width="90" is-read-only="true" align="center" data-map="useYnComboDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.serviceFg"/>" binding="serviceFg" width="90" is-read-only="true" align="center" data-map="serviceFgComboDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsMenuAuth.useYn"/>" binding="useYn" width="80" is-read-only="true" align="center" data-map="useYnComboDataMap"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/auth/smsMenuAuth/smsMenuAuth.js?ver=20260810.01" charset="utf-8"></script>
