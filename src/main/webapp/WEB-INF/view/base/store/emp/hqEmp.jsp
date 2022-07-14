<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="hqEmpCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('hqEmpCtrl',1)">
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
        <%--등록일자 --%>
        <th><s:message code="hqEmp.regDt" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchTimeStartDate"
                      value="startDate"
                      ng-model="startDate"
                      control="startDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
            <span class="rg">~</span>
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchTimeEndDate"
                      value="endDate"
                      ng-model="endDate"
                      control="endDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
            <span class="chk ml10">
              <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
              <label for="chkDt">
                <s:message code="cmm.all.day" />
              </label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%--사원번호 --%>
        <th><s:message code="hqEmp.empNo" /></th>
        <td>
          <input type="text" id="srchEmpNo" name="srchEmpNo" ng-model="empNo" class="sb-input w100" maxlength="4" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 사원명 --%>
        <th><s:message code="hqEmp.empNm" /></th>
        <td>
          <input type="text" id="srchEmpNm" name="srchEmpNm" ng-model="empNm" class="sb-input w100" maxlength="15" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%--사원ID --%>
        <th><s:message code="hqEmp.userId" /></th>
        <td>
          <input type="text" id="srchUserId" name="srchUserId" ng-model="userId" class="sb-input w100" maxlength="16" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 재직여부 --%>
        <th><s:message code="hqEmp.serviceFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchHqEmp"
                    ng-model="serviceFg"
                    items-source="_getComboData('serviceFgComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <tr>
        <%--휴대폰번호 --%>
        <th><s:message code="hqEmp.mpNo" /></th>
        <td>
          <input type="text" id="srchMpNo" name="srchMpNo" ng-model="mpNo" class="sb-input w100" maxlength="15" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 웹사용여부 --%>
        <th><s:message code="hqEmp.webUseYn" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchWebUseYn"
                    ng-model="webUseYn"
                    items-source="_getComboData('webUseYnComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <tr>
        <%--SMS 수신여부 --%>
        <th><s:message code="hqEmp.smsRecvYn" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchSmsRecvYn"
                    ng-model="smsRecvYn"
                    items-source="_getComboData('smsRecvYnComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
          <%-- 메인화면매출표시 --%>
          <th><s:message code="hqEmp.mainSaleFg" /></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchMainSaleFg"
                      ng-model="mainSaleFg"
                      items-source="_getComboData('srchMainSaleFg')"
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

  <div class="mt40 oh sb-select dkbr">
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
    <%--// 페이지 스케일  --%>


    <div class="tr">
      <%-- 신규등록 --%>
      <button class="btn_skyblue" id="btnRegist" ng-click="registHqEmp()"><s:message code="hqEmp.hqEmpReg" /></button>
      <%-- sms 전송 //TODO --%>
      <%--<button class="btn_skyblue" id="btnSMS"><s:message code="hqEmp.sendSMS" /></button>--%>
      <%-- 엑셀다운로드 //TODO --%>
      <%--<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>--%>
    </div>
  </div>

  <%-- 본사 사원 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              control="flex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.empNo"/>" binding="empNo" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.empNm"/>" binding="empNm" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.userId"/>" binding="userId" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.webUseYn"/>" binding="webUseYn" data-map="webUseYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.mpNo"/>" binding="mpNo" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.smsRecvYn"/>" binding="smsRecvYn" data-map="smsRecvYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.useYn"/>" binding="useYn" data-map="useYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.mainSaleFg"/>" binding="mainSaleFg" data-map="mainSaleFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>

        <%--조회시 필요--%>
        <wj-flex-grid-column header="<s:message code="hqEmp.hqBrandCd"/>" binding="hqBrandCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.hqBrandNm"/>" binding="hqBrandNm" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="hqEmpCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script>
  var serviceFg  = ${ccu.getCommCode("007")};
  var webUseYn   = ${ccu.getCommCode("067")};
  var smsRecvYn  = ${ccu.getCommCode("072")};

  <%-- 브랜드사용여부 --%>
  var userHqBrandYn = '${userHqBrandYn}';

  <%-- 본사 거래처 콤보박스 --%>
  var vendrList = ${vendrList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/hqEmp.js?ver=2018112103" charset="utf-8"></script>

<%-- 사원기초정보 상세 Tab 팝업 --%>
<c:import url="/WEB-INF/view/base/store/emp/hqEmpDetail.jsp">
</c:import>

<%-- 사원기초정보 등록/수정 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/store/emp/hqEmpSave.jsp">
</c:import>

<%-- 패스워드 변경 레이어 --%>
<c:import url="/WEB-INF/view/base/store/emp/changePassword.jsp">
</c:import>

<%-- 메뉴권한 Tab 팝업--%>
<c:import url="/WEB-INF/view/base/store/emp/hqEmpAuth.jsp">
</c:import>

