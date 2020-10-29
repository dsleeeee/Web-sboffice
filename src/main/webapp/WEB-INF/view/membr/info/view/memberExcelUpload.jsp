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
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('memberExcelUploadCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
        </div>
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
            <%-- 회원내역 --%>
            <th><s:message code="member.excel.memberInfo"/></th>
            <%-- 성공내역, 실페내역  --%>
            <td>
                <div class="sb-select w40 fl">
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
                <%-- 편집화면다운로드  --%>
                <a href="#" class="btn_grayS ml5" ng-click="excelDownload()"><s:message code="member.excel.pageDownload"/></a>
                
            </td>
        </tr>
        </tbody>
    </table>
    <ul class="txtSty2 mt10 pdb20 bb">
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

    <%-- 콤보 리스트 그리드 --%>
    <div class="w100">
        <div class="h2_tit mt30">
            <%-- 콤보 리스트 --%>
            <s:message code="member.excel.combo.list"/>
        </div>
        <div class="wj-gridWrap wj-control wj-content wj-flexgrid">
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
    <div class="h2_tit w100 mt20">
        <%-- 회원내역 --%>
        <s:message code="member.excel.memberInfo"/>
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
        <%-- 저장 --%>
        <button id="btnSave" class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.save"/></button>
        <%-- 삭제 --%>
        <button id="btnDelete" class="btn_skyblue fr mr5" ng-click="deleteUpload()"><s:message code="cmm.delete"/></button>
        <%-- 양식검증 --%>
        <button class="btn_skyblue fr mr15" ng-click="formChk()"><s:message code="member.excel.check"/></button>
    </div>
    <%-- 그리드 bottom --%>
    <div class="w100 mt10">
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
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" 
                                     width="40" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.check.result"/>" binding="result"
                                     width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.nm.kr"/>" binding="membrNm"
                                     width="115" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.nm.en"/>" binding="memberEngNm"
                                     width="115" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.membrClassCd"/>" binding="membrClassCd" data-map="memberClassList"
                                     width="115" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.store"/>" binding="membrStore" data-map="regstrStoreList" 
                                     width="160" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.store"/>" binding="regStoreCd" data-map="regStoreCd" visible=false
                                     width="160" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.gendrFg"/>" binding="gendrFg" data-map="genderDataMap"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.card.no"/>" binding="membrCardNo" 
                                     width="140" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.birthday"/>" binding="birthday"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.weddingYn"/>" binding="weddingYn" data-map="weddingDataMap"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.weddingday"/>" binding="weddingday"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.telNo"/>" binding="memberTelNo"
                                     width="100" is-read-only="false" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.shortNo"/>" binding="memberShortNo"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.email"/>" binding="memberEmail"
                                     width="140" is-read-only="false" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.postNo"/>" binding="memberPostNo"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.addr"/>" binding="memberAddr"
                                     width="120" is-read-only="false" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.addrDtl"/>" binding="memberAddrDtl"
                                     width="140" is-read-only="false" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.emailRecvYn"/>" binding="emailRecvYn" data-map="recvDataMap"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="member.excel.smsRecvYn"/>" binding="smsRecvYn" data-map="recvDataMap"
                                     width="90" is-read-only="false" align="center"></wj-flex-grid-column>
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
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberExcelUpload.js?ver=2019052801.11" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberPoint.js?ver=2019052801.11" charset="utf-8"></script>