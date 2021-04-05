<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
    .cusWrap {width: 100%; min-height: 768px; height: 100%; display: table;}
    /*.cusWrap {width: 760px; min-height: 590px; height: 100%; display: table;}*/
    .content-middle {width: 100%; height: 100%; display: table-cell; vertical-align: middle;}
    /*.cusTitle {display:block; width:100%; height:100%; line-height:45px; color:#337dde;  padding:0 15px; font-size:0.875em; position:relative;}*/
</style>

<body ng-app="rootApp" ng-controller="rootCtrl">
    <div class="cusWrap">
        <div class="content-middle">
            <div class="subCon" ng-controller="posBoardCtrl">
                <%-- 조회조건 --%>
                <div class="searchBar flddUnfld">
                    <a href="#" class="open fl"><s:message code="posBoard.info"/></a>
                    <%-- 조회 --%>
                    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('posBoardListCtrl')">
                            <s:message code="cmm.search" />
                        </button>
                    </div>
                </div>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w15" />
                        <col class="w20" />
                        <col class="w20" />
                        <col class="w15" />
                        <col class="w30" />
                    </colgroup>
                    <tbody>
                    <tr>
                         <%--조회구분--%>
                        <th>
                            <s:message code="posBoard.srchGubun" />
                        </th>
                        <td>
                            <div class="sb-select w100">
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
                             <input type="text" class="sb-input w100" id="srchGubunName" ng-model="gubunName" />
                         </td>
                         <%--열람구분 --%>
                        <th>
                            <s:message code="posBoard.srchGubunRead" />
                        </th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchGubunReadCombo"
                                        ng-model="gubunReadCombo"
                                        items-source="_getComboData('gubunReadCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        <%--열람구분(포스에서 받는 수신여부)--%>
                                        <c:if test="${readYn == 'N'}">
                                            selected-index="1"
                                        </c:if>
                                        <c:if test="${readYn == 'Y'}">
                                            selected-index="2"
                                        </c:if>
                                        >
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%-- 그리드 --%>
                <div class="mb5">
                    <div class="wj-gridWrap" style="height:260px; overflow-y: hidden; overflow-x: hidden;">
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
                            <wj-flex-grid-column header="<s:message code="posBoard.title"/>" binding="title" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.viewCnt"/>" binding="viewCnt" width="55" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.viewYn"/>" binding="viewYn" data-map="viewYnDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                            <%-- 공개대상 현재 웹에선 매장일때 히든 --%>
                            <wj-flex-grid-column header="<s:message code="posBoard.targetFg"/>" binding="targetFg" data-map="targetFgDataMap" width="65" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.agencyNm"/>" binding="agencyNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.userNm"/>" binding="userNm" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.noticeDate"/>" binding="noticeDate" width="160" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.remark"/>" binding="remark" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

                            <%--팝업 조회시 필요--%>
                            <wj-flex-grid-column header="<s:message code="posBoard.boardCd"/>" binding="boardCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.boardSeqNo"/>" binding="boardSeqNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="posBoard.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>

                <%-- 페이지 리스트 --%>
                <div class="pageNum mt10">
                    <%-- id --%>
                    <ul id="posBoardCtrlPager" data-size="10">
                    </ul>
                </div>
                <%--//페이지 리스트--%>
            </div>

            <%-- 그리드 --%>
            <div ng-controller="posBoardPopupCtrl" style="display: none;">
                <div class="wj-gridWrap" style="height:260px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="posBoard.title"/>" binding="title" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.viewCnt"/>" binding="viewCnt" width="55" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.viewYn"/>" binding="viewYn" data-map="viewYnDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                        <%-- 공개대상 현재 웹에선 매장일때 히든 --%>
                        <wj-flex-grid-column header="<s:message code="posBoard.targetFg"/>" binding="targetFg" data-map="targetFgDataMap" width="65" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.agencyNm"/>" binding="agencyNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.userNm"/>" binding="userNm" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.noticeDate"/>" binding="noticeDate" width="160" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.remark"/>" binding="remark" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

                        <%--팝업 조회시 필요--%>
                        <wj-flex-grid-column header="<s:message code="posBoard.boardCd"/>" binding="boardCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.boardSeqNo"/>" binding="boardSeqNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posBoard.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>

        </div>
    </div>
</body>

<script type="text/javascript">
    // 열람구분(포스에서 받는 수신여부)
    var readYn = "${readYn}";
    // 공지팝업 여부(미열람 공지사항 띄움)
    var noticePopupYn = "${noticePopupYn}";

    <%-- 공개대상 --%>
    var targetFgData = ${ccu.getCommCodeExcpAll("106")};
    <%-- 승인구분 --%>
    var apprFgData = ${ccu.getCommCodeExcpAll("107")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/application/pos/posBoard/posBoard.js?ver=20210405.03" charset="utf-8"></script>

<%-- 게시판 상세 팝업(포스용) --%>
<c:import url="/WEB-INF/view/application/pos/posBoard/posBoardDetail.jsp">
</c:import>