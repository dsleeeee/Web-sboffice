<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="instlAgencyView" class="subCon" ng-controller="instlAgencyCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('instlAgencyCtrl',1)">
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
        <tr <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">style="display: none;"</c:if>>
            <%-- 업체코드 --%>
            <th><s:message code="instlAgency.agencyCd" /></th>
            <td><input type="text" id="srchAgencyCd" class="sb-input w100"/></td>
            <%-- 업체명 --%>
            <th><s:message code="instlAgency.agencyNm" /></th>
            <td><input type="text" id="srchAgencyNm" class="sb-input w100"/></td>
        </tr>
        <tr>
            <%-- 사업자번호 --%>
            <th><s:message code="instlAgency.bizNo" /></th>
            <td><input type="text" id="srchBizNo" class="sb-input w100" maxlength="15"/></td>
            <th></th>
            <td></td>
            <%-- 업체구분
            <th><s:message code="instlAgency.agencyFg" /></th>
            <td><input type="text" id="srchAgencyFg" class="sb-input w100"/></td>--%>
        </tr>
        <%--<tr>
            사용여부
            <th><s:message code="instlAgency.useYn" /></th>
            <td><input type="text" id="srchUseYn" class="sb-input w100" maxlength="7"/></td>
            <th></th>
            <td></td>
        </tr>--%>
        </tbody>
    </table>

    <div class="wj-TblWrap mt40">

        <!-- Left List -->
        <div class="w30 fl" style="overflow-x: visible">
            <div class="wj-TblWrapBr mr10 pd20" style="height:700px;">
                <%-- 매장 그리드 --%>
                <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    id="instlAgencyList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="instlAgency.agencyCd"/>" binding="agencyCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="instlAgency.agencyNm"/>" binding="agencyNm" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

        <!-- Right List -->
        <div class="w70 fr">
            <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
                <%-- 업체정보--%>
                <c:import url="/WEB-INF/view/pos/license/instlAgency/agencyInfo.jsp">
                    <c:param name="menuCd" value="${menuCd}"/>
                    <c:param name="menuNm" value="${menuNm}"/>
                </c:import>

                <%-- 사원관리--%>
                <c:import url="/WEB-INF/view/pos/license/instlAgency/empManage.jsp">
                    <c:param name="menuCd" value="${menuCd}"/>
                    <c:param name="menuNm" value="${menuNm}"/>
                </c:import>

                <%-- 인증관리--%>
                <c:import url="/WEB-INF/view/pos/license/instlAgency/authManage.jsp">
                    <c:param name="menuCd" value="${menuCd}"/>
                    <c:param name="menuNm" value="${menuNm}"/>
                </c:import>
            </div>
        </div>
        <%-- 저장타입 지정 --%>
        <input type="hidden" id="rowAgencyCd" name="rowAgencyCd">
    </div>
</div>

<script>

</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlAgency/instlAgency.js?ver=20191015.36" charset="utf-8"></script>

<%-- 설치업체 사원상세 팝업 --%>
<c:import url="/WEB-INF/view/pos/license/instlAgency/empManageDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설치업체 사원 등록/수정 팝업 --%>
<c:import url="/WEB-INF/view/pos/license/instlAgency/empManageRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
