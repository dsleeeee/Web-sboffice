<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="multistoreUserPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="multistoreUserPopCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="multistoreGroup.userSelect" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 버튼영역 --%>
            <div class="oh sb-select dkbr">
                <span class="fl bk lh30"><label id="lblInfo"></label></span>
                <button class="btn_skyblue fr" id="btnSelectUser" ng-click="btnSelectUser()"><s:message code="cmm.chk"/></button>
            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="multistoreGroup.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multistoreGroup.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="multistoreGroup.id"/>" binding="userId" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/multistoreGroup/multistoreUserPop.js?ver=20210803.02" charset="utf-8"></script>
