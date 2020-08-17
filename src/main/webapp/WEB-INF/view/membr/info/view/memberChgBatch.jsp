<%--
  Date: 2020-06-25
  Time: 오후 4:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>


<div class="subCon" ng-controller="memberChgBatchCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('memberChgBatchCtrl', 1)">
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
            <td colspan="3">
                <div class="sb-select">
            <span class="txtIn">
              <div class="sb-select">
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
              <div class="sb-select">
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
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 회원번호 --%>
            <th><s:message code="regist.membr.no"/></th>
            <td>
                <input type="text" id="memberNo" class="sb-input w100" ng-model="memberNo" maxlength="10"/>
            </td>

            <%-- 회원명 --%>
            <th><s:message code="regist.membr.nm"/></th>
            <td>
                <input type="text" id="memberNm" class="sb-input w100" ng-model="memberNm" maxlength="15"/>
            </td>

        </tr>
        <tr>
            <%-- 등록매장 --%>
            <th><s:message code="regist.reg.store.cd"/></th>
            <td>
                <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
                    <jsp:param name="targetId" value="regStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>

            <%-- 사용매장 --%>
            <th><s:message code="regist.use.store.cd"/></th>
            <td>
                <input type="text" id="storeCd" class="sb-input w100" ng-model="storeCd" maxlength="15"/>
            </td>

        </tr>
        <tr>
            <%-- 회원카드번호 --%>
            <th><s:message code="regist.membr.card.no"/></th>
            <td>
                <input type="text" id="membrCardNo" class="sb-input w100" ng-model="membrCardNo" maxlength="15"/>
            </td>
            <%-- 카드사용구분 --%>
            <th><s:message code="regist.membr.card.yn"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="rMembrcardYn"
                            ng-model="member.cardYn"
                            control="rMembrcardYn"
                            items-source="_getComboData('rMembrcardYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <%--                <input type="text" id="rMembrcardYn" name="cardYn" ng-model="member.cardYn" class="sb-input w100" maxlength="30" />--%>
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
            <%-- E-Mail --%>
            <th>E-Mail</th>
            <td>
                <input type="text" id="emailAddr" class="sb-input w100" ng-model="emailAddr" maxlength="15"/>
            </td>


        </tr>
        <tr>
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
            <%-- 핸드폰번호 --%>
            <th><s:message code="regist.phone.no"/></th>
            <td>
                <input type="text" id="phoneNo" class="sb-input w100" ng-model="phoneNo" maxlength="15"/>
            </td>


        </tr>
        <tr>
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
            <%-- 기념일 --%>
            <th><s:message code="regist.brt.wed.day"/></th>
            <td>
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
            </td>
        </tr>
        <tr>
            <%-- 적립매출횟수 --%>
            <th><s:message code="regist.save.sale"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                      <div class="sb-select">
                        <input type="text" id="startSaveSale" class="sb-input w100" ng-model="startSaveSale"
                               maxlength="15"/>
                      </div>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn">
                      <div class="sb-select">
                        <input type="text" id="endSaveSale" class="sb-input w100" ng-model="endSaveSale"
                               maxlength="15"/>
                      </div>
                    </span>
                </div>
            </td>
            <%-- 가용포인트 --%>
            <th><s:message code="regist.avabl.point"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                      <div class="sb-select">
                        <input type="text" id="startAvablPoint" class="sb-input w100" ng-model="startAvablPoint"
                               maxlength="15"/>
                      </div>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn">
                      <div class="sb-select">
                        <input type="text" id="endAvablPoint" class="sb-input w100" ng-model="endAvablPoint"
                               maxlength="15"/>
                      </div>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 회사단축번호 --%>
            <th><s:message code="regist.membr.stortNo"/></th>
            <td>
                <input type="text" id="stortNo" class="sb-input w100" ng-model="stortNo" maxlength="15"/>
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
            <%-- 회원등급 --%>
            <th><s:message code="regist.memberClass"/><em class="imp">*</em></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="rMemberClass"
                            ng-model="membrClassCd"
                            control="memberClassCombo"
                            items-source="_getComboData('rMemberClass')"
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
                            id="useYn"
                            ng-model="useYn"
                            control="useYnCombo"
                            items-source="_getComboData('useYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
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
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" id="save" ng-click="gridSave()">
            <s:message code="cmm.save"/>
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
                    frozen-columns="2"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk"
                                     width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.no"/>" binding="membrNo" align="center"
                                     is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.nm"/>" binding="membrNm" align="left"
                                     is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.class.cd"/>" binding="membrClassCd" align="center"
                                    data-map="memberClassDataMap" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.class.nm"/>" binding="membrClassNm" align="center"
                                     width="100"  visible="false" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.brthd"/>" binding="birthday" align="center"
                                     width="100" is-read-only="true"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="regist.tel"/>" binding="telNo" width="100" align="center"
                                     is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.phone.no"/>" binding="phoneNo" width="100"
                                     align="center" visible="false"
                                     is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.stortNo"/>" binding="shortNo" width="85"
                                     align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.cnt"/>" binding="saveCnt" width="75"
                                     align="center"
                                     is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.save.amt"/>" binding="saveAmt" width="75"
                                     align="center"
                                     is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.start"/>" binding="firstSaleDate"
                                     width="75" align="center"
                                     is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.visit.end"/>" binding="lastSaleDate"
                                     width="75" align="center"
                                     is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.email.recv"/>" binding="emailRecvYn"
                                     data-map="emailRecvDataMap" width="75" align="center"
                                     ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.sms.recv"/>" binding="smsRecvYn"
                                     data-map="smsRecvDataMap" width="75" align="center"
                                     ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.useYn"/>" binding="useYn" data-map="useYnDataMap"
                                     width="75" align="center" ></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="memberChgBatchCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

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
    var anvrsDataMap = ${ccu.getCommCode("032")};
    <%--카드사용구분--%>
    var rMembrcardList = ${ccu.getCommCode("014")};
    <%--var membrChgBatchList = ${membrChgBatchList};--%>
    var memberClassList = ${memberClassList};
    var memberClassSelect = ${memberClassSelect};

    var regstrStoreList = ${regstrStoreList};


</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberChgBatch.js?ver=20191223.07"
        charset="utf-8"></script>

<%-- 매장 등록/수정--%>
<c:import url="/WEB-INF/view/membr/info/view/memberRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
