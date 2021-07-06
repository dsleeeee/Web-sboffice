<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="guestManageCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('guestManageCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <div class="mt10 oh sb-select dkbr">
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()">
            <s:message code="cmm.save" />
        </button>
    </div>

    <%-- body --%>
    <div class="w100 mt10 mb20">
        <div class="wj-dialog-body sc2">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w15"/>
                    <col class="w15"/>
                    <col class="w15"/>
                    <col class="w15"/>
                    <col class="w15"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 객층1 --%>
                    <th class="tc">
                        <s:message code="guestManage.nmcodeNm1"/>
                    </th>
                    <%-- 객층2 --%>
                    <th class="tc">
                        <s:message code="guestManage.nmcodeNm2"/>
                    </th>
                    <%-- 객층3 --%>
                    <th class="tc">
                        <s:message code="guestManage.nmcodeNm3"/>
                    </th>
                    <%-- 객층4 --%>
                    <th class="tc">
                        <s:message code="guestManage.nmcodeNm4"/>
                    </th>
                    <%-- 객층5 --%>
                    <th class="tc">
                        <s:message code="guestManage.nmcodeNm5"/>
                    </th>
                    <%-- 객층6 --%>
                    <th class="tc">
                        <s:message code="guestManage.nmcodeNm6"/>
                    </th>
                </tr>
                <tr>
                    <%-- 객층1 --%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNmcodeNm1" ng-model="nmcodeNm1" />
                    </td>
                    <%-- 객층2 --%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNmcodeNm2" ng-model="nmcodeNm2" />
                    </td>
                    <%-- 객층3 --%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNmcodeNm3" ng-model="nmcodeNm3" />
                    </td>
                    <%-- 객층4 --%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNmcodeNm4" ng-model="nmcodeNm4" />
                    </td>
                    <%-- 객층5 --%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNmcodeNm5" ng-model="nmcodeNm5" />
                    </td>
                    <%-- 객층6 --%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNmcodeNm6" ng-model="nmcodeNm6" />
                    </td>
                </tr>
            </table>
        </div>
        <%-- 그리드 --%>
        <div class="w100 mt10 mb20" style="display: none;">
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="guestManage.nmcodeCd"/>" binding="nmcodeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="guestManage.nmcodeNm"/>" binding="nmcodeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
    <%-- //body --%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/guestManage/guestManage.js?ver=20210706.02" charset="utf-8"></script>