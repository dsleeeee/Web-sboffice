<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="boardMasterCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('boardMasterCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <%-- 그리드 --%>
    <div class="mt20 mb40">
        <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
            <div class="updownSet oh mb10">
                <button class="btn_skyblue" id="btnCouponAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
                <button class="btn_skyblue" id="btnCouponDel" ng-click="del()"><s:message code='cmm.del' /></button>
                <button class="btn_skyblue" id="btnClassSave" ng-click="save()"><s:message code='cmm.save' /></button>
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
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="boardMaster.boardCd"/>" binding="boardCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="boardMaster.boardNm"/>" binding="boardNm" width="200" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="boardMaster.boardFg"/>" binding="boardFg" data-map="boardFgDataMap" width="100" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="boardMaster.answerFg"/>" binding="answerFg" data-map="answerFgDataMap" width="100" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="boardMaster.autoApprFg"/>" binding="autoApprFg" data-map="autoApprFgDataMap" width="100" align="center"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 게시판 구분 --%>
    //var boardFgData = ${ccu.getCommCodeExcpAll("105")};
    var boardFgData = [
        {"name":"본사전용","value":"1"},
        {"name":"매장전용","value":"2"},
        {"name":"본사/매장공용","value":"3"},
    ];
</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/board/boardMaster/boardMaster.js?ver=20200210.06" charset="utf-8"></script>