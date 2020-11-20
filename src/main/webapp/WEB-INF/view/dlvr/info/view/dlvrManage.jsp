<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
    </div>
    <%-- 그리드 left --%>
    <div class="w50 fl" ng-controller="dlvrManageCtrl">
        <div class="wj-TblWrapBr ml10 pd20 mt10" style="height: 600px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='dlvrManage.first' /></span>
                <button class="btn_skyblue" ng-click="dlvrAreaAdd()">
                    <s:message code="dlvrManage.new"/>
                </button>
                <button class="btn_skyblue" ng-click="dlvrAreaSave()">
                    <s:message code="cmm.save"/>
                </button>
                <button class="btn_skyblue" ng-click="dlvrAreaDel()">
                    <s:message code="cmm.delete"/>
                </button>
                <button class="btn_up" ng-click="dlvrAreaUp()">
                    <s:message code="dlvrManage.up"/>
                </button>
                <button class="btn_down" ng-click="dlvrAreaDn()">
                    <s:message code="dlvrManage.dn"/>
                </button>
            </div>
            <div class="wj-gridWrap" style="height:480px; overflow-y: hidden;">
                <div class="row">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
<%--                            sticky-headers="true"--%>
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"
                                             align="center"
                                             is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.cd"/>" binding="dlvrLzoneCd"
                                             width="80" is-read-only="true" align="center"
                        ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.areaNm"/>" binding="dlvrLzoneNm"
                                             width="150"
                                             is-read-only="false" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.areaNm"/>" binding="pageNo"
                                             width="230"
                                             is-read-only="false" align="right"  visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.useYn"/>" binding="useYn"
                                             width="80" data-map="useYnDataMap" align="center"
                                             is-read-only="false" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.input"/>" binding="inFg"
                                             width="120"
                                             is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.input"/>" binding="inNm"
                                             width="80"
                                             is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <%-- 그리드 right --%>
    <div class="w50 fl" ng-controller="dlvrManageDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20 mt10" style="height: 600px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='dlvrManage.secend' /><label id="lblLzone" style="padding-left: 10px;"></label></span>
                <button class="btn_skyblue" ng-click="dlvrDetailAreaAdd()">
                    <s:message code="dlvrManage.new"/>
                </button>
                <button class="btn_skyblue" ng-click="dlvrDetailAreaSave()">
                    <s:message code="cmm.save"/>
                </button>
                <button class="btn_skyblue" ng-click="dlvrDetailAreaDel()">
                    <s:message code="cmm.delete"/>
                </button>
                <button class="btn_up" ng-click="dlvrDetailAreaUp()">
                    <s:message code="dlvrManage.up"/>
                </button>
                <button class="btn_down" ng-click="dlvrDetailAreaDn()">
                    <s:message code="dlvrManage.dn"/>
                </button>
            </div>
            <div class="wj-gridWrap" style="height:480px; overflow-y: hidden;">
                <div class="row">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
<%--                            is-read-only="true"--%>
                    >
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"
                                             align="center"
                                             is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.areaNm"/>" binding="pageNo"
                                             width="230"
                                             is-read-only="false" align="right"  visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.cd"/>" binding="dlvrMzoneCd"
                                             width="80"
                                             is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.areaNm"/>" binding="dlvrMzoneNm"
                                             width="150"
                                             is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.areaNm"/>" binding="dlvrAddr"
                                             width="230"  visible="false"
                                             is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.useYn"/>" binding="useYn"
                                             width="80" data-map="useYnDataMap"
                                             is-read-only="false" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.input"/>" binding="inNm"
                                             width="80"
                                             is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrManage.input"/>" binding="inFg"
                                             width="120" visible="false"
                                             is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var useYn = ${ccu.getCommCodeExcpAll("067")};
    <%--var dlvrFirstList = ${dlvrFirstList};--%>
</script>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/view/dlvr.js?ver=2020062901.31" charset="utf-8"></script>