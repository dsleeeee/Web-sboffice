<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="authHqList" value="${authHqList}" />

<div class="subCon" ng-controller="migDataMappingCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('migDataMappingCtrl',1)">
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
            <tr>
                <%-- 이관요청일자 --%>
                <th>
                    <s:message code="migDataMapping.srchMigRegDate" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- OKPOS-KCP 매장코드 --%>
                <th>
                    <s:message code="migDataMapping.srchOkposStoreCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOkposStoreCd" ng-model="okposStoreCd" />
                </td>
                <%-- OKPOS-KCP 매장명 --%>
                <th>
                    <s:message code="migDataMapping.srchOkposStoreNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOkposStoreNm" ng-model="okposStoreNm" />
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <p class="tl s14 mt5 lh15">- 단독매장만 이관 가능합니다. ( 단, 다중 사업자번호 사용매장 이관 불가합니다. )</p>
        <p class="tl s14 mt5 lh15">- 이관 대상 데이터는 매장정보, 포스정보, 상품분류정보, 상품정보, 상품가격정보, 매출일집계 입니다.</p>
        <p class="tl s14 mt5 lh15">- 신규이관요청 클릭 -> OKPOS-KCP 웹정보 로그인 아이디와 비밀번호 입력 -> 조회 -> 대상 매장 체크 -> 솔비포스 매장환경 복사 선택 -> 이관 등록 클릭</p>
        <p class="tl s14 mt5 lh15">- 이관 처리 소요시간은 약 10분 정도이며, 매출 데이터양에 따라 지연 될 수 있습니다.</p>
        <%-- 신규등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="addInfo()">
            <s:message code="migDataMapping.newInfo" />
        </button>
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
                <wj-flex-grid-column header="<s:message code="migDataMapping.okposStoreCd"/>" binding="okposStoreCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.okposStoreNm"/>" binding="okposStoreNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.solbiStoreCd"/>" binding="solbiStoreCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.solbiStoreNm"/>" binding="solbiStoreNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.migYn"/>" binding="migYn" data-map="migYnDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.migPosMstCnt"/>" binding="migPosMstCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.migProdClsCnt"/>" binding="migProdClsCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.migProdCnt"/>" binding="migProdCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.migProdPriceCnt"/>" binding="migProdPriceCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.migSaleCnt"/>" binding="migSaleCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script>
    var authHqList = ${authHqList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/migDataMapping/migDataMapping.js?ver=20200224.01" charset="utf-8"></script>

<%-- OKPOS-KCP 데이터 이관 신규등록 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/migDataMapping/migDataMappingInfo.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>