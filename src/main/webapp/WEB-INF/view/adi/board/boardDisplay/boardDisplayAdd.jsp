<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjBoardDisplayAddLayer" control="wjBoardDisplayAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:940px;height:550px;" fade-in="false" fade-out="false">
    <div ng-controller="boardDisplayAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="boardDisplayAdd.info"/>
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="tr" style="display: none;">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('smsGeneralNoManageCtrl', 1)"><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.title"/>" binding="title" width="300" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.viewCnt"/>" binding="viewCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.viewYn"/>" binding="viewYn" data-map="viewYnDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                        <c:if test="${orgnFg != 'STORE'}">
                            <wj-flex-grid-column header="<s:message code="boardDisplayAdd.targetFg"/>" binding="targetFg" data-map="targetFgDataMap" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.agencyNm"/>" binding="agencyNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.userNm"/>" binding="userNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.noticeDate"/>" binding="noticeDate" width="160" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.remark"/>" binding="remark" width="150" is-read-only="true" align="left"></wj-flex-grid-column>

                        <%--팝업 조회시 필요--%>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.boardCd"/>" binding="boardCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDisplayAdd.boardSeqNo"/>" binding="boardSeqNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 등록 버튼 --%>
            <div class="tc mt10">
                <button id="funcSaveBoardDisplayAdd" class="btn_blue">
                    <s:message code="boardDisplayAdd.add" />
                </button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/boardDisplay/boardDisplayAdd.js?ver=20250604.012" charset="utf-8"></script>