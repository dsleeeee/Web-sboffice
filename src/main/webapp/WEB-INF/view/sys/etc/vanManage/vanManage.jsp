<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('vanManageCtrl',1)" id="nxBtnSearch">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <div id="gridRepresent" class="w50 fl mt10" style="width: 20%" ng-controller="vanManageCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr mr10 pd20" style="height: 480px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='vanManage.vanManage' /></span>
            </div>
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
                <div class="row" >
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="vanManage.vanFg"/>" binding="nmcodeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vanManage.vanNm"/>" binding="nmcodeNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <div id="gridDetail" class="w50 fr mt10 mb20" style="width: 80%" ng-controller="vanManageDtlCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr pd20" style="height: 480px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='vanManage.vanDtl'/></span>
                <button class="btn_skyblue" id="btnAddDetail" ng-click="addRow()">
                    <s:message code="cmm.add" />
                </button>
                <button class="btn_skyblue" id="btnSaveDetail" ng-click="save()">
                    <s:message code="cmm.save" />
                </button>
                <button class="btn_skyblue" id="btnDelDetail" ng-click="delete()">
                    <s:message code="cmm.del" />
                </button>
            </div>
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.vanFg"/>" binding="vanFg" width="60" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.vanCd"/>" binding="vanCd" width="80" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.vanNm"/>" binding="vanNm" width="120"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.mainIp"/>" binding="mainIp" width="120"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.mainPort"/>" binding="mainPort" width="100" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.subIp"/>" binding="subIp" width="120"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.subPort"/>" binding="subPort" width="100" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.telNo"/>" binding="telNo" width="100"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanManage.faxNo"/>" binding="faxNo" width="100"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>
<script type="text/javascript" src="/resource/solbipos/js/sys/etc/vanManage/vanManage.js?ver=20240912.01" charset="utf-8"></script>
