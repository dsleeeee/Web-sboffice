<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/> <%-- 20241115 테스트 임시--%>

<div id="smsTelNoStopView" class="subCon" style="display: none;padding: 10px 20px 40px;">
    <div ng-controller="smsTelNoStopCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="smsTelNoStop.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('smsTelNoStopCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="updownSet oh mb10">
                <%-- 저장 --%>
                <button class="btn_skyblue ml5 fr" id="btnTelNoStopSave" ng-click="save()"><s:message code="cmm.save" /></button>
                <%-- 일반번호 인증요청 처리 --%>
                <button id="request" class="btn_skyblue ml5 fr" ng-click="smsGeneralNoManage()">
                    <s:message code="smsTelNoStop.smsGeneralNoManage" />
                </button>
                <%-- 20241115 테스트 임시--%>
                <c:if test="${userId == 'sdffd2605' or userId == 'kjsun1117'}">
                    <%-- 서류인증신청 --%>
                    <button id="request2" class="btn_skyblue ml5 fr" ng-click="smsGeneralNoManage2()" style="display: block;">
                        <s:message code="smsTelNoStop.smsGeneralNoManage2" />
                    </button>
                </c:if>
            </div>
            <div class="wj-gridWrap" style="height:450px; overflow-y: hidden; overflow-x: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="smsTelNoStop.orgnCd"/>" binding="orgnCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsTelNoStop.orgnNm"/>" binding="orgnNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsTelNoStop.telNo"/>" binding="telNo" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsTelNoStop.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="100" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnFgData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsTelNoManage/smsTelNoStop.js?ver=20241120.01" charset="utf-8"></script>

<%-- 일반번호 인증요청 처리 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsTelNoManage/smsGeneralNoManage.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 일반번호 인증요청 처리2 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsTelNoManage/smsGeneralNoManage2.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>