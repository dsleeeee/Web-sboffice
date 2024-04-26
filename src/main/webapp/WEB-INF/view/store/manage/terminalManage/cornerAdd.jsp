<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="cornerAddLayer" control="cornerAddLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;height:400px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="cornerAddCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="terminalManage.cornrAdd" />
            <span id="storePosAddTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">

            <div class="oh sb-select dkbr">
                <%--매장정보--%>
                [<label id="cnr_storeCd"></label>]
                <label id="cnr_storeNm"></label>
                <label id="cnr_vanFixFg" style="display: none"></label>

                <div class="updownSet oh">
                    <%-- 삭제 --%>
                    <button class="btn_skyblue fr ml5" ng-click="cornerDel()"><s:message code="cmm.del"/></button>
                    <%-- 저장 --%>
                    <button class="btn_skyblue fr ml5" ng-click="cornerSave()"><s:message code="cmm.save"/></button>
                    <%-- 추가 --%>
                    <button class="btn_skyblue fr" ng-click="cornerAdd()"><s:message code="cmm.add"/></button>
                </div>
            </div>

            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height:250px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    beginning-edit="changeVendorFg(s,e)"
                    ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="terminalManage.cornrCd"/>" binding="cornrCd" width="100"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="terminalManage.cornrNm"/>" binding="cornrNm" width="100" max-length="15"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="terminalManage.owner"/>" binding="ownerNm" width="100" max-length="15"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="terminalManage.bizNo"/>" binding="bizNo" width="150" max-length="10"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="terminalManage.telNo"/>" binding="telNo" width="150" max-length="14"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="terminalManage.vanCd"/>" binding="vanCd" width="150" max-length="3"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="terminalManage.vanTermnlNo"/>" binding="vanTermnlNo" width="150" max-length="20"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="terminalManage.vanSerNo"/>" binding="vanSerNo" width="150" max-length="20"></wj-flex-grid-column>
                    <wj-flex-grid-column header="" binding="termnlCnt" width="100" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/terminalManage/cornerAdd.js?ver=20240404.02" charset="utf-8"></script>