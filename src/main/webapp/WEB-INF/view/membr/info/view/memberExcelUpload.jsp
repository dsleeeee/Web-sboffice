<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>

<div class="subCon" ng-controller="memberExcelUploadCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
    </div>
    <%-- 상단 타이틀 --%>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <%-- First Row--%>
        <tr class="brt">
            <%-- 엑셀 --%>
            <th><s:message code="member.excel"/></th>
            <td>
                <%-- 양식다운로드 --%>
                <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="member.excel.download"/></a>
                <%-- 양식업로드  --%>
                <a href="#" class="btn_grayS" ng-click="excelTextUpload('memberExcel')"><s:message code="member.excel.upload"/></a>
            </td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <ul class="txtSty2 mt10 pdb20">
        <li class="red">
            <s:message code="member.excel.uploadDescription.titInfo"/><br>
            <p>
                <span><s:message code="member.excel.uploadDescription.txt1"/></span><br>
                <span><s:message code="member.excel.uploadDescription.txt2"/></span><br>
                <span><s:message code="member.excel.uploadDescription.txt3"/></span><br>
                <span><s:message code="member.excel.uploadDescription.txt4"/></span><br>
                <span><s:message code="member.excel.uploadDescription.txt5"/></span><br>
                <span><s:message code="member.excel.uploadDescription.txt6"/></span><br>
            </p>
        </li>
    </ul>
    <%-- 콤보 리스트 --%>
    <s:message code="member.excel.combo.list"/>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap wj-control wj-content wj-flexgrid">
            <wj-flex-grid
                autoGenerateColumns="false"
                control="flex1"
                initialized="initGrid1(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data1"
                item-formatter="_itemFormatter"
                is-read-only="false"
                ime-enabled="true">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dayMembrDetail.membrClassCd"/>" binding="comboClass" data-map="memberClassList"
                                     width="145" is-read-only="false" align="center"></wj-flex-grid-column><%-- 콤보리스트 회원등급분류 --%>
                <wj-flex-grid-column header="<s:message code="prod.regStore"/>" binding="comboStore"  data-map="regstrStoreList"
                                     width="170" align="center" is-read-only="false"></wj-flex-grid-column><%-- 콤보리스트 등록매장 --%>
                <wj-flex-grid-column header="<s:message code="member.excel.gendrFg"/>" binding="comboGendr" data-map="genderDataMap"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column><%-- 콤보리스트 성별구분 --%>
                <wj-flex-grid-column header="<s:message code="member.excel.weddingYn"/>" binding="comboWedding" data-map="weddingDataMap"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column><%-- 콤보리스트 결혼여부 --%>
                <wj-flex-grid-column header="<s:message code="member.excel.cardUseType"/>" binding="comboCardUse" data-map="rMembrcardList"
                                     width="110" is-read-only="false" align="center"></wj-flex-grid-column><%-- 콤보리스트 카드사용구분 --%>
                <wj-flex-grid-column header="<s:message code="dayMembrDetail.emailRecvYn"/>" binding="comboEmail" data-map="recvDataMap" 
                                     width="110" is-read-only="false" align="center"></wj-flex-grid-column><%-- 콤보리스트 이메일수신 --%>
                <wj-flex-grid-column header="<s:message code="dayMembrDetail.smsRecvYn"/>" binding="comboSms" data-map="recvDataMap" 
                                     width="110" is-read-only="false" align="center"></wj-flex-grid-column><%-- 콤보리스트 SMS수신 --%>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 회원내역 그리드 --%>
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
        <button class="btn_skyblue sb-input ml5 fl" id="btnSearch" ng-click="_pageView('memberExcelUploadCtrl', 1)"><s:message code="cmm.search"/></button>

        <%-- 편집화면 엑셀 다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="member.excel.pageDownload"/></button>
        <%-- 삭제 --%>
        <button id="btnDelete" class="btn_skyblue ml5 fr" ng-click="deleteUpload()"><s:message code="cmm.delete"/></button>
        <%-- 저장 --%>
        <button id="btnSave" class="btn_skyblue ml5 fr" ng-click="save()"><s:message code="cmm.save"/></button>
        <%-- 양식검증 --%>
        <button class="btn_skyblue ml5 fr" ng-click="formChk()"><s:message code="member.excel.check"/></button>
        <%-- 중복체크(회원명, 전화번호, 카드번호)  --%>
        <span class="s14 bk fr lh25 mr10" >
           <input type="checkbox" id="chkMembr" ng-model="isCheckedMembr" />
                <label for="chkMembr">
                    <s:message code="member.excel.chk.duplicate" />
                </label>
        </span>
    </div>
    <%-- 회원내역 --%>
    <s:message code="member.excel.memberInfo"/>
    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                frozen-columns="2"
                ime-enabled="true">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" 
                                     width="40" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.check.result"/>" binding="result"
                                     width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.nm.kr"/>" binding="membrNm"
                                     width="100" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.nm.en"/>" binding="memberEngNm"
                                     width="100" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.membrClassCd"/>" binding="membrClassCd" data-map="memberClassList"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.store"/>" binding="membrStore" data-map="regstrStoreList" 
                                     width="115" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.store"/>" binding="regStoreCd" data-map="regStoreCd" visible=false
                                     width="160" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.gendrFg"/>" binding="gendrFg" data-map="genderDataMap"
                                     width="70" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.card.no"/>" binding="membrCardNo" 
                                     width="100" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.birthday"/>" binding="birthday"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.weddingYn"/>" binding="weddingYn" data-map="weddingDataMap"
                                     width="70" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.weddingday"/>" binding="weddingday"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.telNo"/>" binding="memberTelNo"
                                     width="90" is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.shortNo"/>" binding="memberShortNo"
                                     width="70" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.email"/>" binding="memberEmail"
                                     width="110" is-read-only="false" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.postNo"/>" binding="memberPostNo"
                                     width="70" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.addr"/>" binding="memberAddr"
                                     width="110" is-read-only="false" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.addrDtl"/>" binding="memberAddrDtl"
                                     width="110" is-read-only="false" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.emailRecvYn"/>" binding="emailRecvYn" data-map="recvDataMap"
                                     width="80" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.smsRecvYn"/>" binding="smsRecvYn" data-map="recvDataMap"
                                     width="80" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.avablPoint"/>" binding="avablPoint"
                                     width="80" is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.totPoint"/>" binding="totSavePoint"
                                     width="80" is-read-only="false" align="right"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
</div>

<script>
    var recvDataMap = ${ccu.getCommCodeExcpAll("072")}; <%-- 수신, 미수신 --%>
    var genderDataMap = ${ccu.getCommCodeExcpAll("055")}; <%-- 성별구분 : 여자, 남자, 사용안함 --%>
    var useDataMap = ${ccu.getCommCodeExcpAll("067")}; <%--사용, 미사용--%>
    <%-- var periodDataMap = ${ccu.getCommCodeExcpAll("077")}; 조회기간 --%>
    var weddingDataMap = ${ccu.getCommCodeExcpAll("076")}; <%-- 결혼유무 --%>
    var anvrsDataMap = ${ccu.getCommCodeExcpAll("032")};
    var rMembrcardList = ${ccu.getCommCodeExcpAll("014")}; <%-- 카드사용구분 --%>
    var regstrStoreList = ${regstrStoreList}; <%-- 매장목록 --%>
    var memberClassList = ${memberClassList}; <%-- 회원등급목록 --%>
    var hqOfficeCd = "${hqOfficeCd}"; <%-- 본사코드 --%>
    var storeCd = "${storeCd}"; <%-- 매장코드 --%>
    var orgnFg = "${orgnFg}"; <%-- 본사매장구분 --%>
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberExcelUpload.js?ver=2019052801.13" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberPoint.js?ver=2019052801.12" charset="utf-8"></script>

<%-- 수불 엑셀업로드 공통 팝업 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">
</c:import>