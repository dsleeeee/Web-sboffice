<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="memberPointCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
    </div>

    <%-- 상단 타이틀 --%>
    <div class="w100 mt10 mb10">
        <h2 class="h2_tit oh lh30">
            <%-- 전체회원 포인트 세팅 --%>
            <s:message code="membrPoint.totAjdPoint.title"/>
        </h2>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <tr class="brt">
                <%-- 변경 포인트 --%>
                <th><s:message code="membrPoint.tit.totAdjPoint"/></th>
                <td>
                    <input type="text" class="sb-input " ng-model="changeAll.totAjdPoint" id="totAjdPoint"/>
                </td>
                <%-- 비고  --%>
                <th><s:message code="memberPoint.remark"/></th>
                <td>
                    <input type="text" class="sb-input fl w70" ng-model="changeAll.adjustPartRemark" id="adjustPartRemark"/>
                    <%--저장--%>
                    <button class="btn_skyblue sb-input ml5 fl w25" ng-click="adjustAll()"><s:message code="cmm.save"/></button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <%-- 엑셀 --%>
    <div class="w100 mt10 mb10">
        <h2 class="h2_tit oh lh30">
            <s:message code="memberPoint.excelPoing.title"/>
        </h2>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w15"/>
                <col class="w15"/>
                <col class="w5"/>
                <col class="w5"/>
                <col class="w15"/>
                <col class="w10"/>
                <col class="w10"/>
                <col class="w15"/>
            </colgroup>
            <tbody>
            <tr class="brt">
                <%-- 양식다운로드 --%>
                <td>
                    <button class="btn_skyblue sb-input w100" style="margin-left: 5px" ng-click="excelTextUpload('excelFormDown')"><s:message code="member.excel.download"/></button>
                </td>
                <%-- 양식업로드  --%>
                <td>
                    <button class="btn_skyblue sb-input w100" style="margin-left: 5px"  ng-click="excelTextUpload('memberPoint')"><s:message code="member.excel.upload"/></button>
                </td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
        <ul class="txtSty2 mt10 pdb20 bb">
            <li class="red">
                <p>
                    <s:message code="memberPoint.excelDescription"/><br>
                    <span><s:message code="memberPoint.excelDescriptionOne"/></span><br>
                    <span><s:message code="memberPoint.excelDescriptionTwo"/></span><br>
                    <span><s:message code="memberPoint.excelDescriptionThree"/></span><br>
                    <span><s:message code="memberPoint.excelDescriptionFour"/></span><br>
                    <span><s:message code="memberPoint.excelDescriptionFive"/></span><br>
                    <span><s:message code="memberPoint.excelDescriptionSix"/></span><br>
                </p>
            </li>
        </ul>
    </div>

    <div class="oh mb10">
        <div class="sb-select dkbr ml5 fl">
            <wj-combo-box
                    class="w100px fl"
                    id="status"
                    ng-model="status"
                    control="statusCombo"
                    items-source="statusList"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
        </div>
        <%--조회--%>
        <button class="btn_skyblue sb-input ml5 fl" id="btnSearch" ng-click="_pageView('memberPointCtrl', 1)"><s:message code="cmm.search"/></button>

        <%-- 편집화면 엑셀 다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="member.excel.pageDownload"/></button>
        <%--삭제--%>
        <button class="btn_skyblue ml5 fr" ng-click="deleteUpload()"><s:message code="cmm.delete"/></button>
        <%--저장--%>
        <button class="btn_skyblue ml5 fr" ng-click="save()"><s:message code="cmm.save"/></button>
        <%--비고--%>
        <div class="s14 bk lh25 ml5 fr">
            <s:message code="memberPoint.remark"/>
            <input type="text" class="sb-input w100px ml5 fr" id="" ng-model="remark"/>
        </div>
        <%-- 양식검증 --%>
        <button class="btn_skyblue ml5 fr mr10" ng-click="formChk()"><s:message code="member.excel.check"/></button>
    </div>
    <%-- 회원 포인트 조정 내역 --%>
    <s:message code="memberPoint.title.delete"/>
    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    id="memberPointGrid"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    frozen-columns="2">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberPoint.tit.searchResult"/>" binding="memberResult" width="250" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.membrClassCd"/>" binding="membrClassCd" width="110" data-map="memberClassList" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberPoint.memberNo"/>" binding="membrNo" width="110" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberPoint.memberNm"/>" binding="membrNm" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberPoint.memberCardNo"/>" binding="membrCardNo" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberPoint.avablPoint"/>" binding="avablPoint" width="110" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberPoint.chanPoint"/>" binding="totAdjPoint" width="110" is-read-only="false" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberPoint.totAdjPointAfter"/>" binding="totAdjPointAfter" width="110" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%-- 페이지 리스트 --%>
    <%--        <div class="pageNum mt20">--%>
    <%--            <ul class="memberPointCtrlPager" data-size="10">--%>
    <%--                <li class="btn_previous first"><a href="javascript:void(0);" onclick="return false;" ng-click="_pagingGridFirst('memberPointCtrl', 0);"></a></li>--%>
    <%--                <li class="btn_previous"><a href="javascript:void(0);" onclick="return false;" ng-click="_pagingGridPrev($event, 'memberPointCtrl', '{prev}');"></a></li>--%>

    <%--                <li ng-repeat="num in paging.pageSize ">{{num}}</li>--%>
    <%--                <li class="btn_next"><a href="javascript:void(0);" onclick="return false;" ng-click="_pagingGridNext($event, 'memberPointCtrl', '{next}');"></a></li>--%>
    <%--                <li class="btn_next last"><a href="javascript:void(0);" onclick="return false;" ng-click="_pagingGridLast('memberPointCtrl', '{totalPage}');"></a></li>--%>
    <%--            </ul>--%>
    <%--        </div>--%>
    <%--//페이지 리스트--%>
</div>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";

    var recvDataMap = ${ccu.getCommCodeSelect("072")};
    <%--수신, 미수신--%>
    var recvDataMapEx = ${ccu.getCommCodeExcpAll("072")};
    <%--수신, 미수신--%>
    var genderDataMap = ${ccu.getCommCode("055")};
    <%--여자, 남자, 사용안함--%>
    var genderDataMapEx = ${ccu.getCommCodeExcpAll("055")};
    <%--여자, 남자, 사용안함--%>
    var useDataMap = ${ccu.getCommCodeExcpAll("067")};
    <%--사용, 미사용--%>
    var periodDataMap = ${ccu.getCommCodeExcpAll("077")};
    <%--조회기간--%>
    var weddingDataMap = ${ccu.getCommCodeExcpAll("076")};
    <%--결혼유무--%>
    var anvrsDataMap = ${ccu.getCommCode("032")};
    var memberClassList = ${memberClassList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberPoint.js?ver=20201118.03" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberExcelUpload.js?ver=20201118.01" charset="utf-8"></script>

<%-- 수불 엑셀업로드 공통 팝업 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">
</c:import>