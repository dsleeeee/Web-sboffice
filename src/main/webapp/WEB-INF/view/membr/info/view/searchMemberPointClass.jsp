<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchMemberClassLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;height:440px;">

    <div class="wj-dialog wj-dialog-columns" ng-controller="searchMemberClassCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="regist.searchMemberClass.info"/>
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
                        <s:message code="regist.searchMemberClass.membrNo"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMembrNo" ng-model="membrNo" />
                    </td>
                    <%-- 회원코드 --%>
                    <th>
                        <s:message code="regist.searchMemberClass.membrNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 단축번호/전화번호 --%>
                    <th colspan="2">
                        <s:message code="regist.searchMemberClass.telNo"/>
                    </th>
                    <td colspan="2">
                        <input type="text" class="sb-input w100" id="srchTelNo" ng-model="telNo" />
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 oh sb-select dkbr">
                <%-- 조회 --%>
                <button class="btn_skyblue ml5 fr" id="btnSearch" ng-click="_broadcast('searchMemberClassCtrl', 1)"><s:message code="cmm.search" /></button>
            </div>

            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:240px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="regist.searchMemberClass.membrNo"/>" binding="membrNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.searchMemberClass.membrNm"/>" binding="membrNm" width="240" is-read-only="true" align="center"></wj-flex-grid-column>

                        <%--팝업 조회시 필요--%>
                        <wj-flex-grid-column header="<s:message code="regist.searchMemberClass.pointSaveFg"/>" binding="pointSaveFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.searchMemberClass.memberCard"/>" binding="memberCard" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.searchMemberClass.memberCash"/>" binding="memberCash" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/searchMemberPointClass.js?ver=20210316.01" charset="utf-8"></script>