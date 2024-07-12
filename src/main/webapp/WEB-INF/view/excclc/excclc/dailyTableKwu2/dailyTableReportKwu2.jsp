<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup id="wjDailyTableReportKwu2Layer" control="wjDailyTableReportKwu2Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div id="dailyTableReportKwu2Layer" class="wj-dialog wj-dialog-columns" ng-controller="dailyTableReportKwu2Ctrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="spanDtlTitle"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 600px;">
            <%-- 첫째 장 인쇄 --%>
            <div id="divPrintNum1" style="display: none;">
                <div class="mt5 mb10 tr">
                    <%-- 인쇄 --%>
                    <button type="button" class="btn_skyblue ml5" id="btnPrint" ng-click="print()"><s:message code="cmm.print"/></button>
                </div>
                <div class="dailyTableKwu2Report reportPrint w100" id="dailyTableKwu2Report">
                </div>
            </div>
            <%-- 둘째 장 인쇄 --%>
            <div id="divPrintNum2" style="display: none;">
                <div class="mt5 mb10 tr">
                    <%-- 인쇄 --%>
                    <button type="button" class="btn_skyblue ml5" id="btnPrint" ng-click="print2()"><s:message code="cmm.print"/></button>
                </div>
                <div class="dailyTableKwu2Report2 reportPrint w100" id="dailyTableKwu2Report2">
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/dailyTableKwu2/dailyTableReportKwu2.js?ver=20240711.01" charset="utf-8"></script>