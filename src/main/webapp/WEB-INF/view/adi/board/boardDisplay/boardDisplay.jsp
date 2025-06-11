<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="boardDisplayCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('boardDisplayCtrl', 1)" id="nxBtnSearch">
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
                <s:message code="boardDisplay.srchGubun" />
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
            <%-- 열람구분 --%>
            <th>
                <s:message code="boardDisplay.srchGubunRead" />
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

    <div class="mt10 oh sb-select dkbr">
        <div class="updownSet oh">
            <%-- UP --%>
            <button class="btn_up" id="btnUp" ng-click="rowMoveUp()"><s:message code="cmm.up" /></button>
            <%-- DOWN --%>
            <button class="btn_down" id="btnDown" ng-click="rowMoveDown()"><s:message code="cmm.down" /></button>
            <%-- 저장 --%>
            <button class="btn_skyblue" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
            <%-- 노출제외 --%>
            <button class="btn_skyblue" id="btnDelete" ng-click="displayRemove()"><s:message code="boardDisplay.displayRemove" /></button>
            <%-- 상위노출게시물선택 --%>
            <button class="btn_skyblue" id="btnAdd" ng-click="displayAdd()"><s:message code="boardDisplay.displayAdd" /></button>
        </div>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:500px; overflow-y: hidden; overflow-x: hidden;">
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
                <wj-flex-grid-column header="<s:message code="boardDisplay.dispSeq"/>" binding="dispSeq" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.backColorYn"/>" binding="backColorYn" width="60" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.title"/>" binding="title" width="300" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.viewCnt"/>" binding="viewCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.viewYn"/>" binding="viewYn" data-map="viewYnDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                <c:if test="${orgnFg != 'STORE'}">
                    <wj-flex-grid-column header="<s:message code="boardDisplay.targetFg"/>" binding="targetFg" data-map="targetFgDataMap" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="boardDisplay.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.agencyNm"/>" binding="agencyNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.userNm"/>" binding="userNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.noticeDate"/>" binding="noticeDate" width="160" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.remark"/>" binding="remark" width="150" is-read-only="true" align="left"></wj-flex-grid-column>

                <%--팝업 조회시 필요--%>
                <wj-flex-grid-column header="<s:message code="boardDisplay.boardCd"/>" binding="boardCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="boardDisplay.boardSeqNo"/>" binding="boardSeqNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

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

<script type="text/javascript" src="/resource/solbipos/js/adi/board/boardDisplay/boardDisplay.js?ver=20250604.012" charset="utf-8"></script>

<%-- 상위노출게시물선택 팝업 --%>
<c:import url="/WEB-INF/view/adi/board/boardDisplay/boardDisplayAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>