<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="specificDayMemoCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('specificDayMemoCtrl',1)">
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
                <%-- 조회월 --%>
                <th>
                    <s:message code="specificDayMemo.month" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startMonth" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endMonth" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <%-- 일괄적용 --%>
    <table class="searchTbl mt10">
            <colgroup>
                <col class="w20" />
                <col class="w70" />
                <col class="w10" />
            </colgroup>
            <tbody>
            <tr class="brt">
                <%-- 날짜 --%>
                <th>
                    <div class="sb-select">
                        <span class="txtIn"><input id="specificDay" class="w110px"></span>
                    </div>
                </th>
                <td>
                <%-- 메모 --%>
                    <input type="text" class="sb-input w100" id="specificDayMemo" ng-model="specificDayMemo" ng-keydown="registEvt($event)"/>
                </td>
                <%-- 저장 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="regist()"><s:message code="specificDayMemo.regist" /></a>
                </td>
            </tr>
            </tbody>
        </table>

    <div class="updownSet oh mt10">
        <button class="btn_skyblue" id="btnDel" ng-click="delete()">
            <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSave" ng-click="save()">
            <s:message code="cmm.save" />
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    id="wjGrid">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="specificDayMemo.specificDay"/>" binding="specificDay" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="specificDayMemo.yoil"/>" binding="yoil" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="specificDayMemo.specificNo"/>" binding="specificNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="specificDayMemo.specificDayMemo"/>" binding="specificDayMemo" width="*" align="left"></wj-flex-grid-column>

                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="specificDayMemoCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>

            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/specificDayMemo/specificDayMemo.js?ver=20220720.01" charset="utf-8"></script>
