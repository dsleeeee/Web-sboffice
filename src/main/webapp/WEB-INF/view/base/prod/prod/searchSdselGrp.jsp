<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchSdselGrpLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:600px;" fade-in="false" fade-out="false">
    <div class="wj-dialog wj-dialog-columns" ng-controller="searchSdselGrpCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.sdselGrpCd"/>
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close"></a>
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
                    <th><s:message code="prod.sdselGrpNm"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="searchSdselGrpNm" ng-model="searchSdselGrpNm" maxlength="10"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('searchSdselGrpCtrl', 1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="prod.sdselGrpCode"/>" binding="sdselGrpCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prod.sdselGrpNm"/>" binding="sdselGrpNm" width="280" is-read-only="true" align="left"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/searchSdselGrp.js?ver=20210913.03" charset="utf-8"></script>