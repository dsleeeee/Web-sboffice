<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon" ng-controller="dlvrInfoCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('dlvrInfoCtrl', 1)" id="nxBtnSearch">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회기간 --%>
            <th>
                <s:message code="dlvrInfo.srchDate"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"> <input id="startDate" name="startDate" class="w115px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endDate" name="endDate" class="w115px"/></span>
                </div>
            </td>
            <th <c:if test="${orgnFg == 'HQ'}">style="display: none"</c:if>>
                <s:message code="dlvrInfo.dlvr.name"/>
            </th>
            <td <c:if test="${orgnFg == 'HQ'}">style="display: none"</c:if>>
                <input type="text" id="dlvrNm" class="sb-input w100" ng-model="dlvrNm" maxlength="15" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr <c:if test="${orgnFg == 'HQ'}">style="display: none"</c:if>>
            <th>
                <s:message code="dlvrInfo.collect.name"/>
            </th>
            <td>
                <input type="text" id="collectNm" class="sb-input w100" ng-model="collectNm" maxlength="15" onkeyup="fnNxBtnSearch();"/>
            </td>
            <th></th>
            <td></td>
        </tr>
        </tbody>
    </table>

    <%-- 그리드 --%>
    <%-- 페이지 스케일  --%>
    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                control="listScaleCombo"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>


        <%-- 조회조건 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload2()" <c:if test="${orgnFg == 'STORE'}">style="display: none"</c:if>><s:message code="cmm.excel.downCondition"/></button>
        <%-- 엑셀 --%>
        <button class="btn_skyblue ml5 fr" id="save" ng-click="excelDownload()" <c:if test="${orgnFg == 'HQ'}">style="display: none"</c:if>> <s:message code="cmm.excel.down"/> </button>

    </div>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
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
                <wj-flex-grid-column header="<s:message code="dlvrInfo.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true" <c:if test="${orgnFg == 'STORE'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true" <c:if test="${orgnFg == 'STORE'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.date"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true" <c:if test="${orgnFg == 'HQ'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true" <c:if test="${orgnFg == 'HQ'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="center" aggregate="Sum" <c:if test="${orgnFg == 'HQ'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.dlvrAddr"/>" binding="dlvrAddr" width="300" is-read-only="true" <c:if test="${orgnFg == 'HQ'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.dlvrTelNo"/>" binding="dlvrTelNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.membrNm"/>" binding="membrNm" width="150" is-read-only="true" align="center" <c:if test="${orgnFg == 'HQ'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.billDt"/>" binding="billDt" width="150" is-read-only="true" align="center" <c:if test="${orgnFg == 'HQ'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.empNm"/>" binding="empNm" width="120" is-read-only="true" align="center" <c:if test="${orgnFg == 'HQ'}">visible="false"</c:if>></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.collect.name"/>" binding="dlvrPayEmpNm" width="120" is-read-only="true" align="center" <c:if test="${orgnFg == 'HQ'}">visible="false"</c:if>></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dlvrInfoCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="dlvrInfoExcelCtrl">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridExcelList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dlvrInfo.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.date"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrInfo.dlvrTelNo"/>" binding="dlvrTelNo" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">

    var hqOfficeCd = "${hqOfficeCd}";

    var payColList = [];
    var dcColList  = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam       = {};
    payParam.payCd     = "${payCol.payCd}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>

    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="dcCol" items="${dcColList}">
    var dcParam      = {};
    dcParam.dcCd     = "${dcCol.dcCd}";
    dcParam.dcMethod = "${dcCol.dcMethod}";
    dcColList.push(dcParam);
    </c:forEach>

    var payCol      = '${payCol}';
    var dcCol       = '${dcCol}';
    var guestCol    = '${guestCol}';
    var arrPayCol   = payCol.split(',');
    var arrDcCol    = dcCol.split(',');
    var arrGuestCol = guestCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/anals/dlvrInfo/dlvrInfo.js?ver=20251106.01"
        charset="utf-8"></script>

<%-- 영수증상세 --%>
<c:import url="/WEB-INF/view/dlvr/anals/dlvrInfo/billPopup.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
