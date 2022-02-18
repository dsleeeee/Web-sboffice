<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div class="subCon" ng-controller="boardListCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('boardListCtrl',1)" id="nxBtnSearch">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w10" />
            <col class="w10" />
            <col class="w30" />
            <col class="w10" />
            <col class="w40" />
        </colgroup>
        <tbody>
            <tr>
                <%-- 조회구분 --%>
                <th>
                    <s:message code="board.srchGubun" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                            id="srchGubunCombo"
                            ng-model="gubunCombo"
                            items-source="_getComboData('gubunCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <td>
                    <input type="text" class="sb-input w100" id="srchGubunName" ng-model="gubunName" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%--열람구분 --%>
                <th>
                    <s:message code="board.srchGubunRead" />
                </th>
                <td>
                    <div class="sb-select w200px">
                        <wj-combo-box
                                id="srchGubunReadCombo"
                                ng-model="gubunReadCombo"
                                items-source="_getComboData('gubunReadCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt40 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScaleBoard"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>

        <%-- 신규등록 --%>
        <div>
            <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="addInfo()">
                <s:message code="board.newInfo" />
            </button>
        </div>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="board.title"/>" binding="title" width="300" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.viewCnt"/>" binding="viewCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.viewYn"/>" binding="viewYn" data-map="viewYnDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                <c:if test="${orgnFg != 'STORE'}">
                    <wj-flex-grid-column header="<s:message code="board.targetFg"/>" binding="targetFg" data-map="targetFgDataMap" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="board.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.agencyNm"/>" binding="agencyNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.userNm"/>" binding="userNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.noticeDate"/>" binding="noticeDate" width="160" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.remark"/>" binding="remark" width="150" is-read-only="true" align="left"></wj-flex-grid-column>

                <%--팝업 조회시 필요--%>
                <wj-flex-grid-column header="<s:message code="board.boardCd"/>" binding="boardCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.boardSeqNo"/>" binding="boardSeqNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.fullSizeYn"/>" binding="fullSizeYn" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="boardListCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    <%-- 공개대상 --%>
    <%--var targetFgData = ${ccu.getCommCodeExcpAll("106")};--%>
   /* var targetFgData = [
        {"name":"전체","value":"1"},
        {"name":"특정매장","value":"2"}
    ];*/
    var targetFgData = [
            {"name": "관리자/총판/대리점", "value": "123"},
            {"name": "전체", "value": "12345"},
            {"name": "총판/대리점", "value": "23"},
            {"name": "전체", "value": "2345"},
            {"name": "대리점", "value": "3"},
            {"name": "전체", "value": "345"},
            {"name": "본사", "value": "4"},
            {"name": "본사/매장", "value": "45"},
            {"name": "매장", "value": "5"},
            {"name": "특정대상(본사/매장)", "value": "6"}
        ];
    <%-- 승인구분 --%>
    var apprFgData = ${ccu.getCommCodeExcpAll("107")};
    /*var apprFgData = [
        {"name":"기안","value":"1"},
        {"name":"승인","value":"2"},
        {"name":"반려","value":"3"}
    ];*/

    var boardCd = "${boardCd}";
    var rootUrl = "${rootUrl}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/board/boardList.js?ver=20210405.07" charset="utf-8"></script>

<%-- 게시판 상세 팝업 --%>
<c:import url="/WEB-INF/view/adi/board/board/boardDetail.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 게시판 상세 Full Size팝업 --%>
<c:import url="/WEB-INF/view/adi/board/board/boardDetailFullSize.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 게시판 신규등록,수정 팝업 --%>
<c:import url="/WEB-INF/view/adi/board/board/boardInfo.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 열람자목록 팝업 --%>
<c:import url="/WEB-INF/view/adi/board/board/boardReadingHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>