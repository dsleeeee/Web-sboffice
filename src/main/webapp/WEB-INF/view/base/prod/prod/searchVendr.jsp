<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="wjSearchVendrLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;height:500px;" fade-in="false" fade-out="false">
    <div class="wj-dialog wj-dialog-columns" ng-controller="searchVendrCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.searchProdVendr.info"/>
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w30" />
                    <col class="w70" />
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="prod.vendrStoreRegist.vendrCd"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="searchVendrCd" ng-model="searchVendrCd"/>
                    </td>
                </tr>
                <tr>
                    <th><s:message code="prod.vendrStoreRegist.vendrNm"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="searchVendrNm" ng-model="searchVendrNm"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('searchVendrCtrl', 1)" ><s:message code="cmm.search" /></button>
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
                        <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.vendrCd"/>" binding="vendrCd" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.vendrNm"/>" binding="vendrNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/searchVendr.js?ver=20251114.01" charset="utf-8"></script>
