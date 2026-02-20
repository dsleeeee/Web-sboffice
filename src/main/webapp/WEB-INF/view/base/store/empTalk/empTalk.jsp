<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="empTalkCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="empTalk.empTalk" /></a>
    </div>

    <div class="mt10 updownSet oh">
        <div class="txtIn">
            <button class="btn_skyblue" id="empTalkRegStore" ng-click="empTalkRegStore()">
                <s:message code="empTalk.regStore" />
            </button>
            <button class="btn_up" id="btnUpSelClass" ng-click="rowMoveUp()" >
                <s:message code="cmm.up" />
            </button>
            <button class="btn_down" id="btnDownSelClass" ng-click="rowMoveDown()" >
                <s:message code="cmm.down" />
            </button>
            <button class="btn_skyblue" id="addBtn" ng-click="addRow()">
                <s:message code="cmm.add" />
            </button>
            <button class="btn_skyblue" id="deleteBtn" ng-click="delete()">
                <s:message code="cmm.delete" />
            </button>
            <button class="btn_skyblue" id="saveBtn" ng-click="save()">
                <s:message code="cmm.save" />
            </button>
        </div>
    </div>

    <div id="grid" class="w100">
        <div class="wj-gridWrap mt5" style="height:550px; overflow-y: hidden;">
            <div class="row">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empTalk.empTextNo"/>" binding="empTextNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empTalk.empTextInfo"/>" binding="empTextInfo" width="*"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empTalk.useYn"/>" binding="useYn" data-map="useYnDataMap" width="80"></wj-flex-grid-column>
                    <c:if test="${orgnFg == 'STORE'}">
                        <wj-flex-grid-column header="<s:message code="empTalk.regFg"/>" binding="regFg" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empTalk.regFg"/>" binding="regFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    </c:if>

                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var useYn       = ${ccu.getCommCodeExcpAll("067")};
    var regFg       = ${ccu.getCommCodeExcpAll("071")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/store/empTalk/empTalk.js?ver=20260212.01" charset="utf-8"></script>

