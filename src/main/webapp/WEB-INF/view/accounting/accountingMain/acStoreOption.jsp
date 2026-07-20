<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="acStoreOptionView" class="subCon" ng-controller="acStoreOptionCtrl" style="display: none;padding: 10px 20px 40px;">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="acStoreOption.title"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('acStoreOptionCtrl', 1)" id="acStoreOptionBtnSearch">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <%-- 매장선택 --%>
        <tr>
            <th><s:message code="cmm.store.select"/></th>
            <td colspan="3">
                <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="acStoreOptionStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        <tr>
            <%-- 항목1 --%>
            <th><s:message code="acStoreOption.option01"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreOption01" ng-model="option01"/>
            </td>
            <%-- 항목2 --%>
            <th><s:message code="acStoreOption.option02"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchStoreOption02Combo"
                            ng-model="option02"
                            items-source="_getComboData('option02Combo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 항목3 --%>
            <th><s:message code="acStoreOption.option03"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchStoreOption03Combo"
                            ng-model="option03"
                            items-source="_getComboData('option03Combo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 항목4 --%>
            <th><s:message code="acStoreOption.option04"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreOption04" ng-model="option04" numberOnly/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" ng-click="saveAcStoreOption()"><s:message code="cmm.save" /></button>
        <%-- 삭제 --%>
        <button class="btn_skyblue ml5 fr" ng-click="delAcStoreOption()"><s:message code="cmm.del" /></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:420px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGridAcStoreOptionList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acStoreOption.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acStoreOption.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                <%-- 항목1 : 입력 --%>
                <wj-flex-grid-column header="<s:message code="acStoreOption.option01"/>" binding="option01" width="100" align="left"></wj-flex-grid-column>
                <%-- 항목2 : 콤보박스(방식1/방식2/방식3) --%>
                <wj-flex-grid-column header="<s:message code="acStoreOption.option02"/>" binding="option02" width="90" align="center" data-map="option02DataMap" ></wj-flex-grid-column>
                <%-- 항목3 : 체크박스 (서버가 boolean(true/false)으로 내려줘야 자동으로 체크박스로 렌더링됨) --%>
                <wj-flex-grid-column header="<s:message code="acStoreOption.option03"/>" binding="option03" width="60" align="center"></wj-flex-grid-column>
                <%-- 항목4 : 숫자입력 (0~100, 저장 시 JS에서 범위 체크) --%>
                <wj-flex-grid-column header="<s:message code="acStoreOption.option04"/>" binding="option04" width="80" align="right"></wj-flex-grid-column>
                <%-- 항목5~항목20 : 세부 입력방식 정의 전이라 우선 텍스트 입력 컬럼으로 처리 (추후 타입 확정되면 교체 필요) --%>
                <c:forEach var="i" begin="5" end="20">
                    <fmt:formatNumber var="optNo" value="${i}" pattern="00"/>
                    <wj-flex-grid-column header="항목${i}" binding="option${optNo}" width="90" align="left"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    $(function(){
        $("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^0-9]/g,""));
        });
    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/accounting/accountingMain/acStoreOption.js?ver=20260713.01" charset="utf-8"></script>
