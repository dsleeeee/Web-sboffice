<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchOtionGrpLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:600px;" fade-in="false" fade-out="false">
    <div class="wj-dialog wj-dialog-columns" ng-controller="searchOptionGrpCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.optionGrp"/>
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
                    <th><s:message code="prod.optionGrpNm"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="searchOptionGrpNm" ng-model="searchOptionGrpNm" maxlength="10"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="searchOptionGrp()" ><s:message code="cmm.search" /></button>
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
                        <wj-flex-grid-column header="<s:message code="prod.optionGrpCd"/>" binding="optionGrpCd" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prod.optionGrpNm"/>" binding="optionGrpNm" width="280" is-read-only="true" align="left"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/searchOptionGrp.js?ver=20230215.01" charset="utf-8"></script>