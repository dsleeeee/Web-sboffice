<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnCd">${sessionScope.sessionInfo.orgnCd}</c:set>

<div class="subCon" ng-controller="memberCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
        <%-- 검색기간 --%>
        <th>
          <div class="sb-select">
            <wj-combo-box
                    id="periodDate"
                    ng-model="member.periodDate"
                    control="periodDateCombo"
                    items-source="_getComboData('periodDate')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    <%--selected-index-changed="setClsFgVal(s,e)"--%>>
            </wj-combo-box>
          </div>
        </th>
        <td>
          <div class="sb-select">
            <span class="txtIn">
              <div class="sb-select" >
                <wj-input-date
                        value="startDate"
                        ng-model="member.startDate"
                        control="startDateCombo"
                        format="yyyy/MM/dd"
                        min="2000-01-01"
                        max="2099-12-31"
                        initialized="_initDateBox(s)">
                </wj-input-date>
              </div>
            </span>
            <span class="rg">~</span>
            <span class="txtIn">
              <div class="sb-select" >
                <wj-input-date
                        value="endDate"
                        ng-model="member.endDate"
                        control="endDateCombo"
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
        <%-- 생일, 결혼기념일 날짜 --%>
        <th>
          <div class="sb-select">
            <wj-combo-box
                    id="anvrsDate"
                    ng-model="member.anvrsDate"
                    control="anvrsDateCombo"
                    items-source="_getComboData('anvrsDate')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
            </wj-combo-box>
          </div>
        </th>
        <td>
          <div class="sb-select">
            <span class="txtIn">
              <div class="sb-select" >
                <wj-input-date
                        value="anvrsStartDate"
                        ng-model="member.anvrsStartDate"
                        control="anvrsStartDateCombo"
                        format="yyyy/MM/dd"
                        min="2000-01-01"
                        max="2099-12-31"
                        initialized="_initDateBox(s)">
                </wj-input-date>
              </div>
            </span>
            <span class="rg">~</span>
            <span class="txtIn">
              <div class="sb-select" >
                <wj-input-date
                        value="anvrsEndDate"
                        ng-model="member.anvrsEndDate"
                        control="anvrsEndDateCombo"
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
          <input type="text" id="memberNo" class="sb-input w100" ng-model="member.memberNo" maxlength="10"/>
        </td>
        <%-- 회원명 --%>
        <th><s:message code="regist.membr.nm"/></th>
        <td>
          <input type="text" id="memberNm" class="sb-input w100" ng-model="member.memberNm" maxlength="15"/>
        </td>
      </tr>
      <tr>
        <%-- 등록매장 --%>
        <th><s:message code="regist.reg.store.cd"/></th>
        <td>
          <input type="text" id="regStore" class="sb-input w100" ng-model="member.regStore" readonly="readonly"/>
        </td>
        <%-- 전화번호 --%>
        <th><s:message code="regist.tel"/></th>
        <td>
          <input type="text" id="telNo" class="sb-input w100" ng-model="member.telNo" maxlength="15"/>
        </td>
      </tr>
      <tr>
        <%-- 회원카드번호 --%>
        <th><s:message code="regist.membr.card.no"/></th>
        <td>
          <input type="text" id="membrCardNo" class="sb-input w100" ng-model="member.membrCardNo" maxlength="15"/>
        </td>
        <%-- E-Mail --%>
        <th>E-Mail</th>
        <td>
          <input type="text" id="membrEmail" class="sb-input w100" ng-model="member.membrEmail" maxlength="15"/>
        </td>
      </tr>
      <tr>
        <%-- 이메일 수신 --%>
        <th><s:message code="regist.email.recv"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="emailRecvYn"
                    ng-model="member.emailRecvYn"
                    control="emailRecvYnCombo"
                    items-source="_getComboData('emailRecvYn')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
            <%--selected-index-changed="setClsFgVal(s,e)"--%>
                    is-read-only="true">
            </wj-combo-box>
          </div>
        </td>
        <%-- SMS 수신 --%>
        <th><s:message code="regist.sms.recv"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="smsRecvYn"
                    ng-model="member.smsRecvYn"
                    control="smsRecvYnCombo"
                    items-source="_getComboData('smsRecvYn')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
            <%--selected-index-changed="setClsFgVal(s,e)"--%>
                    is-read-only="true">
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
                    id="gender"
                    ng-model="member.gender"
                    control="genderCombo"
                    items-source="_getComboData('gender')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    <%--selected-index-changed="setClsFgVal(s,e)"--%>
                    is-read-only="true">
            </wj-combo-box>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>

  <%-- 조회버튼 --%>
  <div class="mt10 pdb10 oh bb">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search"/></button>
  </div>

  <div class="mt10 oh sb-select dkbr">
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

    <%-- 신규 회원 등록 --%>
    <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="registMember()">
      <s:message code="webMenu.new" />
    </button>
  </div>

  <%-- 회원목록 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:315px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              control="flex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="regist.membr.no"/>" binding="membrNo" visible="false" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="regist.membr.nm"/>" binding="membrNm" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="regist.class.cd"/>" binding="membrClassCd" align="center" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="regist.class.nm"/>" binding="membrClassNm" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="regist.card.no"/>" binding="membrCardNo" width="*" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeManageCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>
<script>

var recvData     = ${ccu.getCommCode("072")}; <%--수신, 미수신--%>
var recvDataEx   = ${ccu.getCommCodeExcpAll("072")}; <%--수신, 미수신--%>
var genderData   = ${ccu.getCommCode("055")}; <%--여자, 남자, 사용안함--%>
var genderDataEx = ${ccu.getCommCodeExcpAll("055")}; <%--여자, 남자, 사용안함--%>
var useDataEx    = ${ccu.getCommCodeExcpAll("067")}; <%--사용, 미사용--%>
var periodDate    = ${ccu.getCommCodeExcpAll("077")}; <%--조회기간--%>
var weddingData   = ${ccu.getCommCodeExcpAll("076")}; <%--결혼유무--%>

</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberInfo.js?ver=20181108.01" charset="utf-8"></script>
