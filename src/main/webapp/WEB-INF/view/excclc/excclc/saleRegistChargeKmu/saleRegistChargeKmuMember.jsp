<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSaleRegistChargeKmuMemberLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:400px;height:470px;" fade-in="false" fade-out="false">
    <div ng-controller="saleRegistChargeKmuMemberCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="saleRegistChargeKmuMember.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w20"/>
                    <col class="w30"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 회원코드 --%>
                    <th>
                        <s:message code="cmm.membrNo"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMembrNo" ng-model="srchMembrNo" />
                    </td>
                    <%-- 회원명 --%>
                    <th>
                        <s:message code="cmm.membrNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="srchMembrNm" />
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('saleRegistKmuMemberCtrl', 1)"><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.membrNo"/>" binding="membrNo" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.membrNm"/>" binding="membrNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/saleRegistChargeKmu/saleRegistChargeKmuMember.js?ver=20251022.01" charset="utf-8"></script>