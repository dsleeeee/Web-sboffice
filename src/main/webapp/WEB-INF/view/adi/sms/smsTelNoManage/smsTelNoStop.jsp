<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="smsTelNoStopView" class="subCon" style="display: none;padding: 10px 20px 40px;">
    <div ng-controller="smsTelNoStopCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="smsTelNoStop.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('smsTelNoStopCtrl',1)" id="nxBtnSearch3">
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
                    <%-- 소속코드 --%>
                    <th>
                        <s:message code="smsTelNoStop.orgnCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgnCd" ng-model="srchOrgnCd" onkeyup="fnNxBtnSearch('3');" />
                    </td>
                    <%-- 소속명 --%>
                    <th>
                        <s:message code="smsTelNoStop.orgnNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgnNm" ng-model="srchOrgnNm" onkeyup="fnNxBtnSearch('3');" />
                    </td>
                </tr>
            </tbody>
        </table>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="updownSet oh mb10">
                <%-- 저장 --%>
                <button class="btn_skyblue ml5 fr" id="btnTelNoStopSave" ng-click="save()"><s:message code="cmm.save" /></button>
                <%-- 일반번호 인증요청 처리 --%>
                <button id="request" class="btn_skyblue ml5 fr" ng-click="smsGeneralNoManage()" style="display: none;">
                    <s:message code="smsTelNoStop.smsGeneralNoManage" />
                </button>
                <%-- 서류인증신청 --%>
                <button id="request2" class="btn_skyblue ml5 fr" ng-click="smsGeneralNoManage2()">
                    <s:message code="smsTelNoStop.smsGeneralNoManage2" />
                </button>
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
                    <wj-flex-grid-column header="<s:message code="smsTelNoStop.certId"/>" binding="certId" width="160" is-read-only="true" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnFgData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsTelNoManage/smsTelNoStop.js?ver=20250612.01" charset="utf-8"></script>

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