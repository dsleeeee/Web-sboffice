<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div class="subCon" ng-controller="systemEmpCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('systemEmpCtrl',1)">
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
        <th><s:message code="systemEmp.regDt" /></th>
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
        <th><s:message code="systemEmp.empNo" /></th>
        <td>
          <input type="text" id="srchEmpNo" name="srchEmpNo" ng-model="empNo" class="sb-input w100" maxlength="4" />
        </td>
        <%-- 사원명 --%>
        <th><s:message code="systemEmp.empNm" /></th>
        <td>
          <input type="text" id="srchEmpNm" name="srchEmpNm" ng-model="empNm" class="sb-input w100" maxlength="15" />
        </td>
      </tr>
      <tr>
        <%--사원ID --%>
        <th><s:message code="systemEmp.userId" /></th>
        <td>
          <input type="text" id="srchUserId" name="srchUserId" ng-model="userId" class="sb-input w100" maxlength="16" />
        </td>
        <%-- 재직여부 --%>
        <th><s:message code="systemEmp.serviceFg" /></th>
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
        <th><s:message code="systemEmp.mpNo" /></th>
        <td>
          <input type="text" id="srchMpNo" name="srchMpNo" ng-model="mpNo" class="sb-input w100" maxlength="15" />
        </td>
        <%-- 웹사용여부 --%>
        <th><s:message code="systemEmp.webUseYn" /></th>
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
        <th><s:message code="systemEmp.smsRecvYn" /></th>
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
        <th></th>
        <td></td>
      </tr>
      <tr <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">style="display: none;"</c:if>>
        <%-- 관리업체 코드 --%>
        <th><s:message code="systemEmp.agencyCd" /></th>
        <td>
          <input type="text" id="srchAgencyCd" name="srchAgencyCd" ng-model="agencyCd" class="sb-input w100" maxlength="5" />
        </td>
        <%-- 관리업체 명 --%>
        <th><s:message code="systemEmp.agencyNm" /></th>
        <td>
          <input type="text" id="srchAgencyNm" name="srchAgencyNm" ng-model="agencyNm" class="sb-input w100" maxlength="15" />
        </td>
      </tr>
      <tr <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">style="display: none;"</c:if>>
        <%-- 관리자 구분 --%>
        <th><s:message code="systemEmp.adminFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchAdminFg"
                    ng-model="adminFg"
                    items-source="_getComboData('adminFgComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <th></th>
        <td></td>
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
      <button class="btn_skyblue" id="btnRegist" ng-click="registSystemEmp()"><s:message code="systemEmp.empRegist" /></button>
      <%-- sms 전송 //TODO --%>
      <%--<button class="btn_skyblue" id="btnSMS"><s:message code="systemEmp.sendSMS" /></button>--%>
      <%-- 엑셀다운로드 //TODO --%>
      <%--<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>--%>
    </div>
  </div>

  <%-- 사원 그리드 --%>
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
        <wj-flex-grid-column header="<s:message code="systemEmp.empNo"/>" binding="empNo" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.empNm"/>" binding="empNm" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.userId"/>" binding="userId" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.useYn"/>" binding="useYn" data-map="webUseYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.webUseYn"/>" binding="webUseYn" data-map="webUseYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.mpNo"/>" binding="mpNo" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.smsRecvYn"/>" binding="smsRecvYn" data-map="smsRecvYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.adminFg"/>" binding="adminFg" data-map="adminFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.agencyCd"/>" binding="agencyCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="systemEmp.agencyNm"/>" binding="agencyNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="systemEmpCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script>
  var serviceFg    = ${ccu.getCommCode("007")};
  var webUseYn     = ${ccu.getCommCode("067")};
  var smsRecvYn    = ${ccu.getCommCode("072")};
  var adminFgData  = ${ccu.getCommCode("097")};
  var orgnFg = "${orgnFg}";
  var orgnCd = "${orgnCd}";
  var orgnNm = "${orgnNm}";
  var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/systemEmp.js?ver=20181121.08" charset="utf-8"></script>

<%-- 사원상세 팝업 --%>
<c:import url="/WEB-INF/view/base/store/emp/systemEmpDetail.jsp">
</c:import>

<%-- 사원신규등록/수정 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/store/emp/systemEmpSave.jsp">
</c:import>

<%-- 대리점 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>

<%-- 비밀번호 변경 팝업 --%>
<%--
<c:import url="/WEB-INF/view/base/store/emp/changePassword.jsp">
</c:import>
--%>



