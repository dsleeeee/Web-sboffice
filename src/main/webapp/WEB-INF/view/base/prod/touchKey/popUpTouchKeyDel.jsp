<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="popUpTouchKeyDelLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:540px;height:540px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="popUpTouchKeyDelCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="touchKey.touchKeyDel" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 터치키그룹코드 --%>
                    <th><s:message code="touchKey.grpCd" /></th>
                    <td>
                        <input type="text" id="srchTukeyGrpCd" ng-model="tukeyGrpCd"/>
                    </td>
                    <%-- 터치키그룹명 --%>
                    <th><s:message code="touchKey.grpNm" /></th>
                    <td>
                        <input type="text" id="srchTukeyGrpNm" ng-model="tukeyGrpNm"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="mt10 tr">
                <button class="btn_blue" id="btnSearchPopUpTouchKeyDel" ng-click="searchPopUpTouchKeyDel()"><s:message code="cmm.search" /></button>
            </div>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.grpCd"/>" binding="tukeyGrpCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.grpNm"/>" binding="tukeyGrpNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <div class="wj-dialog-footer">
                <%-- 삭제 --%>
                <button class="btn_blue" ng-click="saveDel()"><s:message code="cmm.del"/></button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/popUpTouchKeyDel.js?ver=20230718.01" charset="utf-8"></script>