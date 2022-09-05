<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="membrClassManageFg" value="${membrClassManageFg}" />

<%-- 우편번호 찾기 팝업 --%>
<%-- 선택한 주소를 부모창에 바인딩 하기 위해, 각 화면마다 구분자를 지정하여 element id명을 파악한다. --%>
<%-- jsp:param 방식은 API 호출 시, 파라미터 사용을 불허하기 때문에 호출이 거부됨. --%>
<%-- memberBasic.jsp에서 주소검색을 사용하고 있으나, 알 수 없는 parameter값이 넘어가기 때문에 부모페이지에 지정함. --%>
<input type="hidden" id="pageNm" value="memberBasic" />
<%@ include file="/WEB-INF/view/application/layer/searchAddr.jsp" %>

<div class="subCon" ng-controller="memberCtrl">

    <%--<div class="searchBar flddUnfld">--%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('memberCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
            <%-- 확장조회 --%>
            <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                <s:message code="cmm.search.addShow" />
            </button>
        </div>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 검색기간 --%>
            <th>
                <div class="sb-select">
                    <wj-combo-box
                            id="periodType"
                            ng-model="periodType"
                            control="periodTypeCombo"
                            items-source="_getComboData('periodType')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                      <div class="sb-select w110px">
                        <wj-input-date
                                value="periodStartDate"
                                ng-model="periodStartDate"
                                control="periodStartDateCombo"
                                format="yyyy/MM/dd"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                        </wj-input-date>
                      </div>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn">
                      <div class="sb-select w110px">
                        <wj-input-date
                                value="periodEndDate"
                                ng-model="periodEndDate"
                                control="periodEndDateCombo"
                                format="yyyy/MM/dd"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                        </wj-input-date>
                      </div>
                    </span>
                </div>
            </td>
            <%-- 회원등급 --%>
            <th><s:message code="regist.memberClass"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="rMemberClass"
                            ng-model="member.membrClassCd"
                            control="memberClassCombo"
                            items-source="_getComboData('rMemberClass')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 회원번호 --%>
            <th><s:message code="regist.membr.no"/></th>
            <td>
                <input type="text" id="memberNo" class="sb-input w100" ng-model="memberNo" maxlength="10" ng-disabled="newMemberYn === true" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 회원명(한글) --%>
            <th><s:message code="regist.membr.nm"/></th>
            <td>
                <input type="text" id="memberNm" class="sb-input w100" ng-model="memberNm" maxlength="15" ng-disabled="newMemberYn === true" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <%-- 매장 선택 --%>
        <c:if test="${orgnFg == 'HQ'}">
            <tr>
                <th><s:message code="regist.reg.store.cd"/></th>
                <td>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="regStore"/>
                    </jsp:include>
                </td>
                <th><s:message code="regist.use.store.cd"/></th>
                <td>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="regUseStore"/>
                    </jsp:include>
                </td>
            </tr>
        </c:if>
        <%-- 우리매장 --%>
        <c:if test="${orgnFg == 'STORE'}">
            <c:if test="${hqOfficeCd ne '00000'}">
                <tr <c:if test="${membrClassManageFg ne '1'}"> style="display: none"</c:if>> <%-- 자기매장 회원만 보이게 --%>
                    <th><s:message code="regist.use.store.membr"/></th>
                    <td>
                        <input type="checkbox" id="storeMembr" ng-model="storeMembr"/>
                    </td>
                    <th><s:message code="regist.use.visit.store.membr"/></th>
                    <td>
                        <input type="checkbox" id="visitStoreMembr" ng-model="visitStoreMembr"/>
                    </td>
                </tr>
            </c:if>
        </c:if>
        <tr>
            <%-- 회원카드번호 --%>
            <th><s:message code="regist.membr.card.no"/></th>
            <td>
                <input type="text" id="membrCardNo" class="sb-input w60 fl" ng-model="membrCardNo" maxlength="15"/>
                <div class="sb-select w30 ml5 fl">
                    <wj-combo-box
                            id="membrCardFg"
                            ng-model="cstCardUseFg"
                            control="membrCardFgCombo"
                            items-source="_getComboData('membrCardFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <th>E-Mail</th>
            <td>
                <input type="text" id="emailAddr" class="sb-input w100" ng-model="emailAddr" maxlength="15"/>
            </td>
        </tr>
        <tr>
            <%-- 이메일 수신 --%>
            <th><s:message code="regist.email.recv"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="emailRecvYn"
                            ng-model="emailRecvYn"
                            control="emailRecvYnCombo"
                            items-source="_getComboData('emailRecvYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- SMS 수신 --%>
            <th><s:message code="regist.sms.recv"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="smsRecvYn"
                            ng-model="smsRecvYn"
                            control="smsRecvYnCombo"
                            items-source="_getComboData('smsRecvYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 회원등급 --%>
            <%--
            <th><s:message code="regist.class.cd"/></th>
            <td>
              <div class="sb-select">
                <div id="classCd"></div>
              </div>
            </td>
            --%>
            <%-- 성별 --%>
            <th><s:message code="regist.gender"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="gendrFg"
                            ng-model="gendrFg"
                            control="genderCombo"
                            items-source="_getComboData('gendrFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 사용여부 --%>
            <th><s:message code="regist.useYn"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="useYnAll"
                            ng-model="useYn"
                            control="useYnAllCombo"
                            items-source="_getComboData('useYnAll')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 적립매출횟수/금액 --%>
            <th>
                <div class="sb-select">
                    <wj-combo-box
                            id="memberSaleList"
                            ng-model="memberSaleFg"
                            control="memberSaleListCombo"
                            items-source="memberSaleList"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </th>
            <td>
                <input type="text" id="startSaveSale" class="sb-input w100px" ng-model="startSaveSale" maxlength="15"/>
                <span class="rg">~</span>
                <input type="text" id="endSaveSale" class="sb-input w100px" ng-model="endSaveSale" maxlength="15"/>
            </td>
            <%-- 가용포인트 --%>
            <th>
                <div class="sb-select">
                    <wj-combo-box
                            id="memberSaleLmemberPointListist"
                            ng-model="memberPointFg"
                            control="memberPointListCombo"
                            items-source="memberPointList"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </th>
            <td>
                <input type="text" id="startAvablPoint" class="sb-input w100px" ng-model="startAvablPoint" maxlength="15"/>
                <span class="rg">~</span>
                <input type="text" id="endAvablPoint" class="sb-input w100px" ng-model="endAvablPoint" maxlength="15"/>
            </td>
        </tr>
        <tr>
            <%-- 회원단축번호 --%>
            <th><s:message code="regist.membr.stortNo"/></th>
            <td>
                <input type="text" id="shortNo" class="sb-input w100" ng-model="shortNo" maxlength="15"/>
            </td>
            <%-- 결혼여부 --%>
            <th><s:message code="regist.wedding"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="weddingYn"
                            ng-model="weddingYn"
                            control="weddingYnCombo"
                            items-source="_getComboData('weddingYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 생일, 결혼기념일 날짜 --%>
            <th>
                <div class="sb-select">
                    <wj-combo-box
                            id="anvType"
                            ng-model="anvType"
                            control="anvTypeCombo"
                            items-source="_getComboData('anvType')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                      <div class="sb-select w110px">
                        <wj-input-date
                                value="anvStartDate"
                                ng-model="anvStartDate"
                                control="anvStartDateCombo"
                                format="yyyy/MM/dd"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                        </wj-input-date>
                      </div>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn">
                        <div class="sb-select w110px">
                            <wj-input-date
                                    value="anvEndDate"
                                    ng-model="anvEndDate"
                                    control="anvEndDateCombo"
                                    format="yyyy/MM/dd"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </div>
                    </span>
                </div>
            </td>
            <%-- 회원명(영문) --%>
            <th><s:message code="regist.membr.nm.eng"/></th>
            <td>
                <input type="text" id="memberEngNm" class="sb-input w100" ng-model="member.memberEngNm" maxlength="15" ng-disabled="newMemberYn === true" ng/>
            </td>
        </tr>
        <tr>
            <%-- 연락처 --%>
            <th><s:message code="regist.tel"/></th>
            <td>
                <input type="text" id="telNo" class="sb-input w100" ng-model="telNo" maxlength="15"/>
            </td>
            <th><s:message code="regist.membr.new"/></th>
            <td>
                <input type="checkbox" id="newMemberYn" class="mt5" ng-model="newMemberYn"/>
            </td>
        </tr>
        <tr>
            <%-- SMS기간(광고성 SMS전송) --%>
            <th><s:message code="regist.marketingSmsSend"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMarketingSmsGubunCombo"
                            ng-model="marketingSmsGubun"
                            items-source="_getComboData('marketingSmsGubunCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="marketingSmsGubunCombo">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 주소 --%>
            <th><s:message code="regist.addr"/></th>
            <td>
                <input type="text" id="addr" class="sb-input w100" ng-model="addr"/>
            </td>
        </tr>
        </tbody>
    </table>

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
        <%-- 신규 회원 등록
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/>
        </button>--%>
        <%-- 전체 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadTotal()"><s:message code="cmm.excel.downTotal" /></button>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>

        <div <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>>
            <%-- 신규 회원 등록 --%>
            <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="registMember()">
                <s:message code="webMenu.new"/>
            </button>
            <%-- 선택회원 삭제 --%>
            <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="deleteMember()">
                <s:message code="regist.membr.delete"/>
            </button>
            <%-- 본사(A0001, A0007)만 보이게 --%>
            <c:if test="${ (orgnFg eq 'HQ' and hqOfficeCd eq 'A0001') or (orgnFg eq 'HQ' and hqOfficeCd eq 'A0007') }">
                <%-- 회원 거래처 매핑 --%>
                <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="memberVendorMapping()">
                    <s:message code="regist.memberVendorMapping"/>
                </button>
            </c:if>
            <%-- 회원 포인트 이관 --%>
            <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="memberPointMove()">
                <s:message code="regist.memberPointMove"/>
            </button>
            <%-- 회원 포인트 조정 --%>
            <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="memberPointAdjust()">
                <s:message code="regist.memberPointAdjust"/>
            </button>
        </div>

        <%-- SMS전송 --%>
        <button class="btn_skyblue ml5 fr" id="btnSmsSendRepresent" ng-click="smsSendPop()" style="display: none;">
            <s:message code="regist.smsSend"/>
        </button>
    </div>

    <%-- 회원목록 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    frozen-columns="4"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.no"/>" binding="membrNo" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.nm"/>" binding="membrNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.regStore"/>" binding="regStoreNm" align="left" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.class.cd"/>" binding="membrClassCd" align="center"  width="80" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.class.nm"/>" binding="membrClassNm" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.card.no"/>" binding="membrCardNo" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.brthd"/>" binding="birthday" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.tel"/>" binding="telNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.stortNo"/>" binding="shortNo" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.regStore"/>" binding="regStoreCd" visible="false"></wj-flex-grid-column>
<%--                                <wj-flex-grid-column header="<s:message code="regist.membr.regStore"/>" binding="regStoreNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="regist.email.recv"/>" binding="emailRecvYn" data-map="emailRecvDataMap" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.sms.recv"/>" binding="smsRecvYn" data-map="smsRecvDataMap" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.useYn"/>" binding="useYn" data-map="useYnDataMap" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <%--본사--%>
                <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="regist.membr.store"/>" binding="postpaidStore" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <%--단독매장--%>
                <c:if test="${orgnFg == 'STORE'}">
                    <c:if test="${hqOfficeCd eq '00000'}">
                        <wj-flex-grid-column header="<s:message code="regist.membr.store"/>" binding="postpaidStore" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                    </c:if>
                </c:if>
                <%--                <c:if test="visitStoreMembr === true">--%>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.add"/>" binding="storeTotSavePoint" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.use"/>" binding="storeTotUsePoint" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.adj"/>" binding="storeTotAdjPoint" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.cnt"/>" binding="storePointAccCnt" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.amt"/>" binding="storeTotSaleAmt" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.start"/>"  binding="storeFirstSaleDate" width="80" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.end"/>" binding="storeLastSaleDate" width="80" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <%--                </c:if>--%>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.add"/>" binding="totSavePoint" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.use"/>" binding="totUsePoint" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.adj"/>" binding="totAdjPoint" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.ava"/>" binding="avablPoint" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.cnt"/>" binding="pointAccCnt" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.amt"/>" binding="totSaleAmt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.start"/>" binding="firstSaleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.end"/>" binding="lastSaleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.day"/>" binding="regDt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.addr"/>" binding="addr" width="150" align="left" is-read-only="true"></wj-flex-grid-column>

                <%--팝업 조회시 필요--%>
                <wj-flex-grid-column header="<s:message code="regist.membr.pointSaveFg"/>" binding="pointSaveFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.memberCard"/>" binding="memberCard" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.memberCash"/>" binding="memberCash" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="memberCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%-- 엑셀다운로드 그리드 --%>
    <div class="w100 mt10 mb20" style="display:none;" ng-controller="memberExcelCtrl">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="excelFlex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    frozen-columns="4"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.no"/>" binding="membrNo" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.nm"/>" binding="membrNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.regStore"/>" binding="regStoreNm" align="left" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.class.cd"/>" binding="membrClassCd" align="center"  width="80" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.class.nm"/>" binding="membrClassNm" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.card.no"/>" binding="membrCardNo" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.brthd"/>" binding="birthday" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.tel"/>" binding="telNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.stortNo"/>" binding="shortNo" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.regStore"/>" binding="regStoreCd" visible="false"></wj-flex-grid-column>
                <%--                <wj-flex-grid-column header="<s:message code="regist.membr.regStore"/>" binding="regStoreNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="regist.email.recv"/>" binding="emailRecvYn" data-map="emailRecvDataMap" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.sms.recv"/>" binding="smsRecvYn" data-map="smsRecvDataMap" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.useYn"/>" binding="useYn" data-map="useYnDataMap" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <%--본사--%>
                <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="regist.membr.store"/>" binding="postpaidStore" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <%--단독매장--%>
                <c:if test="${orgnFg == 'STORE'}">
                    <c:if test="${hqOfficeCd eq '00000'}">
                        <wj-flex-grid-column header="<s:message code="regist.membr.store"/>" binding="postpaidStore" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                    </c:if>
                </c:if>
                <%--                <c:if test="visitStoreMembr === true">--%>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.add"/>" binding="storeTotSavePoint" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.use"/>" binding="storeTotUsePoint" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.adj"/>" binding="storeTotAdjPoint" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.cnt"/>" binding="storePointAccCnt" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.amt"/>" binding="storeTotSaleAmt" width="75" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.start"/>"  binding="storeFirstSaleDate" width="80" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.end"/>" binding="storeLastSaleDate" width="80" align="center" ng-if="regStoreChk === true" is-read-only="true"></wj-flex-grid-column>
                <%--                </c:if>--%>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.add"/>" binding="totSavePoint" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.use"/>" binding="totUsePoint" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.adj"/>" binding="totAdjPoint" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.point.ava"/>" binding="avablPoint" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.cnt"/>" binding="pointAccCnt" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.amt"/>" binding="totSaleAmt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.start"/>" binding="firstSaleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.end"/>" binding="lastSaleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.day"/>" binding="regDt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.addr"/>" binding="addr" width="150" align="left" is-read-only="true"></wj-flex-grid-column>

                <%--팝업 조회시 필요--%>
                <wj-flex-grid-column header="<s:message code="regist.membr.pointSaveFg"/>" binding="pointSaveFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.memberCard"/>" binding="memberCard" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.memberCash"/>" binding="memberCash" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script>
    var recvDataMap = ${ccu.getCommCode("072")};
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
    var weddingList = ${ccu.getCommCodeSelect("076")};
    <%--결혼유무 Select--%>
    var anvrsDataMap = ${ccu.getCommCode("032")};
    <%--카드사용구분--%>
    var rMembrcardList = ${ccu.getCommCodeExcpAll("301")};
    <%--카드발급구분--%>
    <%--var rCstCardIssFgList = ${ccu.getCommCodeExcpAll("301")};--%>
    <%--카드상태구분--%>
    <%--var rCstCardStatFgList = ${ccu.getCommCodeExcpAll("300")};--%>

    var regstrStoreList = ${regstrStoreList};
    var memberClassList = ${memberClassList};
    var memberClassSelect = ${memberClassSelect};
    var forcedDeleteChkPwd = "${forcedDeleteChkPwd}";

    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var orgnCd = "${orgnCd}";

    // SMS전송 - 메세지그룹
    var msgGrpColList = [];
    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="msgGrpCol" items="${msgGrpColList}">
        var msgGrpParam = {};
        msgGrpParam.msgGrpCd = "${msgGrpCol.msgGrpCd}";
        msgGrpParam.msgGrpNm = "${msgGrpCol.msgGrpNm}";
        msgGrpColList.push(msgGrpParam);
    </c:forEach>

    // 회원등급 관리구분[1237]
    var membrClassManageFg = "${membrClassManageFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberInfo.js?ver=20220722.02" charset="utf-8"></script>

<%-- 후불적용매장등록 --%>
<c:import url="/WEB-INF/view/membr/info/view/postpaidStoreRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원 정보조회 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberInfoDetail.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원 포인트변경내역, 구매내역 조회 팝업 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberInfoPoint.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원 포인트 이관 팝업 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberPointMove.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원 포인트 조정 팝업 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberPointAdjust.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원 포인트 조정 팝업 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberPointAdjustDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원 등급 조회 팝업 --%>
<c:import url="/WEB-INF/view/membr/info/view/searchMemberPointClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- SMS전송 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/smsSend.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원삭제팝업 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberDelete.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장 등록/수정--%>
<c:import url="/WEB-INF/view/membr/info/view/memberRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원 매핑코드 조회 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberMapping.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원 거래처 매핑 조회 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberVendorMapping.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>