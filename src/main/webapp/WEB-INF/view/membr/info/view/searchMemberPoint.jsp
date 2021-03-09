<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchMemberPointLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;height:430px;" fade-in="false" fade-out="false">

    <div class="wj-dialog wj-dialog-columns" ng-controller="searchMemberPointCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="lblTitle"></span>
            <s:message code="regist.searchMemberPoint.info"/>
            <label id="lblGubun" style="display: none"></label>
            <label id="lblGubunMemberNo" style="display: none"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 회원명 --%>
                    <th>
                        <s:message code="regist.searchMemberPoint.membrNo"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMembrNo" ng-model="membrNo" />
                    </td>
                    <%-- 회원코드 --%>
                    <th>
                        <s:message code="regist.searchMemberPoint.membrNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 단축번호/전화번호 --%>
                    <th colspan="2">
                        <s:message code="regist.searchMemberPoint.telNo"/>
                    </th>
                    <td colspan="2">
                        <input type="text" class="sb-input w100" id="srchTelNo" ng-model="telNo" />
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 oh sb-select dkbr">
                <%-- 조회 --%>
                <button class="btn_skyblue ml5 fr" id="btnSearch" ng-click="_broadcast('searchMemberPointCtrl', 1)"><s:message code="cmm.search" /></button>
            </div>

            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:230px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns.="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="regist.searchMemberPoint.membrNo"/>" binding="membrNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.searchMemberPoint.membrNm"/>" binding="membrNm" width="160" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.searchMemberPoint.avablPoint"/>" binding="avablPoint" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/searchMemberPoint.js?ver=20210310.01" charset="utf-8"></script>