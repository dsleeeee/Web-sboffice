<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="smsTelNoManageView" class="subCon" style="display: none;">
    <div ng-controller="smsTelNoManageCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="smsTelNoManage.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('smsTelNoManageCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="updownSet oh mb10">
                <%-- UP --%>
                <button class="btn_up" id="btnTelNoUp" ng-click="rowMoveUp()"><s:message code="cmm.up" /></button>
                <%-- DOWN --%>
                <button class="btn_down" id="btnTelNoDown" ng-click="rowMoveDown()"><s:message code="cmm.down" /></button>
                <%-- 저장 --%>
                <button class="btn_skyblue" id="btnTelNoSave" ng-click="save()"><s:message code="cmm.save" /></button>
            </div>
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="smsTelNoManage.telNo"/>" binding="telNo" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsTelNoManage.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%-- 저장시 필요 --%>
                    <wj-flex-grid-column header="<s:message code="smsTelNoManage.useSeq"/>" binding="useSeq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnFgData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsTelNoManage/smsTelNoManage.js?ver=20220201.01" charset="utf-8"></script>