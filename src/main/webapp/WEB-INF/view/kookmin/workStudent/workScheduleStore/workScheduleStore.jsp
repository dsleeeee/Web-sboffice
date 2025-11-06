<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/kookmin/workStudent/workScheduleStore/workScheduleStore/"/>


<div class="subCon" ng-controller="workScheduleStoreCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('workScheduleStoreCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w25" />
            <col class="w15" />
            <col class="w45" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 년도 --%>
            <th>
                <s:message code="workScheduleStore.termYear"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchDate" ng-model="srchDate" class="w110px"></span>
                </div>
            </td>
            <%-- 학기구분 --%>
            <th>
                <s:message code="workScheduleStore.termFg"/>
            </th>
            <td>
                <span class="sb-radio"><input type="radio" id="termFg1" name="termFg" value="1" checked /><label for="termFg1">1학기</label></span>
                <span class="sb-radio"><input type="radio" id="termFg2" name="termFg" value="2"/><label for="termFg2">하계방학</label></span>
                <span class="sb-radio"><input type="radio" id="termFg3" name="termFg" value="3"/><label for="termFg3">2학기</label></span>
                <span class="sb-radio"><input type="radio" id="termFg4" name="termFg" value="4"/><label for="termFg4">동계방학</label></span>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="delete()"><s:message code="cmm.delete" /></button>
        <button class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.save" /></button>
        <button class="btn_skyblue fr mr5" ng-click="add()"><s:message code="cmm.add" /></button>
        <div id="workScheduleStoreStore" class="fr oh bk mr5" style="width: 150px; height:25px; font-size:12px;">
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                <jsp:param name="targetTypeFg" value="M"/>
                <jsp:param name="targetId" value="workScheduleStoreStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
        </div>
    </div>

<%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.termYear"/>"    binding="termYear"      width=""       align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.termYear"/>"    binding="termFg"        width=""       align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.workSchCode"/>" binding="workSchCode"   width="80"     align="center" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.storeCd"/>"     binding="storeCd"       width="80"     align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.storeNm"/>"     binding="storeNm"       width="120"    align="left"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.sun"/>"         binding="sun"           width="50"     align="center" is-read-only="false"  ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.mon"/>"         binding="mon"           width="50"     align="center" is-read-only="false"  ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.tue"/>"         binding="tue"           width="50"     align="center" is-read-only="false"  ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.wed"/>"         binding="wed"           width="50"     align="center" is-read-only="false"  ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.thu"/>"         binding="thu"           width="50"     align="center" is-read-only="false"  ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.fri"/>"         binding="fri"           width="50"     align="center" is-read-only="false"  ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.sat"/>"         binding="sat"           width="50"     align="center" is-read-only="false"  ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.startTime"/>"   binding="startTime"     width="80"     align="center" is-read-only="false"  data-map="timeDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.endTime"/>"     binding="endTime"       width="80"     align="center" is-read-only="false"  data-map="timeDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.hourPay"/>"     binding="hourPay"       width="80"     align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.workFg"/>"      binding="workFg"        width="80"     align="center" data-map="workFgComboDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.workTime"/>"    binding="workTime"      width="80"     align="center" is-read-only="true"   format="n1"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.breakTime"/>"   binding="breakTime"     width="80"     align="center" is-read-only="true"   format="n1"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.totWorkTime"/>" binding="totWorkTime"   width="80"     align="center" is-read-only="true"   format="n1"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workScheduleStore.remark"/>"      binding="remark"        width="150"    align="left" ></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/workStudent/workScheduleStore/workScheduleStore.js?ver=20251106.01" charset="utf-8"></script>


