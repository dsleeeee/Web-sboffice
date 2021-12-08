<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="branchAreaView" class="subCon" style="display: none;">
    <div ng-controller="branchAreaCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeProdSaleReportTab.branchArea"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('branchAreaCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w50 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:450px;">
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30">
                        <s:message code="branchArea.branch"/>
                    </span>
                    <button class="btn_skyblue" id="btnBranchAreaAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
                    <button class="btn_skyblue" id="btnBranchAreaSave" ng-click="save()"><s:message code='cmm.save' /></button>
                    <button class="btn_skyblue" id="btnBranchAreaDel" ng-click="del()"><s:message code='cmm.del' /></button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                    autoGenerateColumns.="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="branchArea.branchCd"/>" binding="branchCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="branchArea.branchNm"/>" binding="branchNm" width="120" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="branchAreaDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:450px; overflow-y: hidden;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30">
                    <s:message code="branchArea.area"/>
                    <label id="lblBranchCd"></label>
                    <label id="lblBranchNm"></label>
                </span>
                <button class="btn_skyblue" id="btnBranchAreaDetailAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
                <button class="btn_skyblue" id="btnBranchAreaDetailSave" ng-click="save()"><s:message code='cmm.save' /></button>
                <button class="btn_skyblue" id="btnBranchAreaDetailDel" ng-click="del()"><s:message code='cmm.del' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="branchArea.areaCd"/>" binding="areaCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="branchArea.areaNm"/>" binding="areaNm" width="120" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/storeProdSaleReport/branchArea.js?ver=20211207.01" charset="utf-8"></script>