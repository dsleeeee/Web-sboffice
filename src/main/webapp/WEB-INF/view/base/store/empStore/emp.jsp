<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div id="empStoreEmpView" class="subCon" style="display: none;">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="day.dayTotal"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('empStoreEmpCtrl')">
            <s:message code="cmm.search"/>
        </button>
    </div>

    <div ng-controller="empStoreEmpCtrl">
        <%--사원정보--%>
        <div class="wj-TblWrap mt20 mb20 w33 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px; overflow-y: hidden;" >
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30">
                        <s:message code="empStore.emp.emp" />
                    </span>
                </div>
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="empStore.emp.empNo"/>" binding="empNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empStore.emp.empNm"/>" binding="empNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empStore.emp.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empStore.emp.storeCnt"/>" binding="storeCnt" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%--사원정보--%>
    </div>

    <%--관리매장--%>
    <div class="wj-TblWrap mt20 mb20 w33 fl" ng-controller="empManageStoreCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30">
                <s:message code="empStore.emp.manage" /><span id="empTitle"></span>
                </span>
                <button class="btn_skyblue" id="btnDelete" ng-click="del()">
                    <s:message code="cmm.delete" />
                </button>
            </div>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="func.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.storeNm"/>" binding="storeNm" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--상세 조회시 필요--%>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.empNo"/>" binding="empNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
    <%--관리매장--%>

    <%--미관리매장--%>
    <div class="wj-TblWrap mt20 mb20 w33 fr" ng-controller="empNoManageStoreCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30">
                <s:message code="empStore.emp.noManage" />
                </span>
                <button class="btn_skyblue" id="btnAdd" ng-click="add()">
                    <s:message code="cmm.add" />
                </button>
            </div>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="func.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.storeNm"/>" binding="storeNm" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--상세 조회시 필요--%>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empStore.emp.empNo"/>" binding="empNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
    <%--미관리매장--%>

</div>

<script type="text/javascript">
    <%-- 재직구분 --%>
    var serviceFgData = ${ccu.getCommCodeExcpAll("007")};
    <%-- 상태구분 --%>
    var sysStatFgData = ${ccu.getCommCodeExcpAll("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/empStore/emp.js?ver=20200513.05" charset="utf-8"></script>