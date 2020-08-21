<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">
</c:import>

<div class="subCon" ng-controller="memberExcelUploadCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
<%--            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('memberExcelUploadCtrl', 1)">--%>
<%--                <s:message code="cmm.search"/>--%>
<%--            </button>--%>
        </div>
    </div>

    <%-- 상단 타이틀 --%>
    <div class="w100 mt10 mb10">
        <table class="searchTbl">
            <colgroup>
                <col class="w10"/>
                <col class="w10"/>
                <col class="w10"/>
                <col class="w5"/>
                <col class="w5"/>
                <col class="w5"/>
                <col class="w5"/>
                <col class="w25"/>
                <col class="w10"/>
                <col class="w5"/>
            </colgroup>
            <tbody>

            <%-- First Row--%>
            <tr class="brt">
                <%-- 양식다운로드 --%>
                <td>
                    <button class="btn_skyblue sb-input w100" style="margin-left: 5px"
                            ng-click="excelTextUpload('excelFormDown')">
                        <s:message code="member.excel.download"/></button>
                </td>
                <%-- 양식업로드  --%>
                <td>
                    <button class="btn_skyblue sb-input w100" style="margin-left: 5px"
                            ng-click="excelTextUpload('memberExcel')">
                        <s:message code="member.excel.upload"/>
                    </button>
                </td>
                <%-- 편집화면다운로드  --%>
                <td>
                    <button class="btn_skyblue sb-input w100" style="margin-left: 5px" ng-click="excelDownload()">
                        <s:message
                                code="member.excel.pageDownload"/></button>
                </td>
                <%-- 성공내역, 실페내역  --%>
                <td>
                    <div class="sb-select w100 fl">
                        <wj-combo-box
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
                </td>
                <%-- 검색줄수  --%>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <%-- 양식검증  --%>
                <td>
                    <button class="btn_skyblue sb-input w100" style="margin-left: 5px" ng-click="formChk()"><s:message
                            code="member.excel.check"/></button>
                </td>
                <%-- 저장  --%>
                <td>
                    <button class="btn_skyblue sb-input w100" style="margin-left: 5px" ng-click="save()"><s:message
                            code="cmm.save"/></button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <%-- 그리드 top --%>
    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
    </div>
    <div class="w100" style="margin-bottom: 5px">
        <div class="wj-gridWrap">
            <h2 class="h2 oh lh30">
                <s:message code="member.excel.combo.list"/>
            </h2>
        </div>
        <div class="wj-gridWrap" style="height:100px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex1"
                    initialized="initGrid1(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data1"
                    item-formatter="_itemFormatter"
                    is-read-only="false">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dayMembrDetail.membrClassCd"/>" binding="comboClass"
                                     width="115" is-read-only="false" align="center"
                                     data-map="memberClassList"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.regStore"/>" binding="comboStore" width="115"
                                     align="center"
                                     is-read-only="false" data-map="regstrStoreList"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.gendrFg"/>" binding="comboGendr"
                                     width="220" data-map="genderDataMap"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.wedding"/>" binding="comboWedding" width="230"
                                     is-read-only="false" align="center"
                                     data-map="weddingDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.cardUseType"/>" binding="comboCardUse"
                                     width="230" data-map="rMembrcardList"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayMembrDetail.emailRecvYn"/>" binding="comboEmail"
                                     width="115" data-map="recvDataMap"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayMembrDetail.smsRecvYn"/>" binding="comboSms"
                                     width="120" data-map="recvDataMap"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.dlAddrOne"/>" binding="comboLzone"
                                     width="120" data-map="dlvrLzoneList"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.dlAddrTwo"/>" binding="comboMzone"
                                     width="120" data-map="dlvrMzoneList"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 그리드 bottom --%>
    <div class="w100">
        <div class="wj-gridWrap">
            <h2 class="h2 oh lh30">
                <s:message code="member.excel.memberInfo"/>
                <button class="btn_skyblue sb-input w5" style="margin: 5px 15px" ng-click="deleteUpload()"><s:message
                        code="cmm.delete"/></button>
            </h2>
        </div>
        <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    frozen-columns="2">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" is-read-only="false"
                                     align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.check.result"/>" binding="result" width="115"
                                     is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.nm.kr"/>" binding="membrNm" width="115"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.nm.en"/>" binding="membrEnNm" width="115"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.membrClassCd"/>" binding="membrClassCd"
                                     width="115" align="center" data-map="memberClassList"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.store"/>" binding="membrStore" width="140"
                                     data-map="regstrStoreList" align="center"
                                     is-read-only="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.gendrFg"/>" binding="gendrFg" width="100"
                                     is-read-only="false" align="center" data-map="genderDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.card.no"/>" binding="membrCardNo" width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.birthday"/>" binding="birthday"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.weddingYn"/>" binding="weddingYn"
                                     width="140" data-map="weddingDataMap"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.weddingday"/>" binding="weddingday"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.telNo"/>" binding="memberTelNo" width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.shortNo"/>" binding="memebrShortNo"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.email"/>" binding="memberEmail" width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.postNo"/>" binding="memberPostNo" width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.addr"/>" binding="memberAddr" width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.addrDtl"/>" binding="memberAddrDtl"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.emailRecvYn"/>" binding="emailRecvYn"
                                     width="140" data-map="recvDataMap"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.smsRecvYn"/>" binding="smsRecvYn"
                                     width="140" data-map="recvDataMap"
                                     is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.avablPoint"/>" binding="avablPoint"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.totPoint"/>" binding="totSavePoint"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.dlAddrOne"/>" binding="dlvrLzoneCd"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.dlAddrTwo"/>" binding="dlvrMzoneCd"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.dlAddrDtl"/>" binding="addrDtl"
                                     width="140"
                                     is-read-only="false" align="right"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
</div>

<script>
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
    <%--카드사용구분--%>
    var rMembrcardList = ${ccu.getCommCode("014")};
    var regstrStoreList = ${regstrStoreList};
    var memberClassList = ${memberClassList};
    var dlvrLzoneList = ${dlvrLzoneList};
    <%--카드발급구분--%>
    <%--var rCstCardIssFgList = ${ccu.getCommCodeExcpAll("301")};--%>
    <%--카드상태구분--%>
    <%--var rCstCardStatFgList = ${ccu.getCommCodeExcpAll("300")};--%>


</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberExcelUpload.js?ver=2019052801.11"
        charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberPoint.js?ver=2019052801.11"
        charset="utf-8"></script>