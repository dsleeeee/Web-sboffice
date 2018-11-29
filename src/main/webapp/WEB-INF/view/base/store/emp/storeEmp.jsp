<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="storeEmpCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="getStoreEmpList()">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15">
      <col class="w35">
      <col class="w15">
      <col class="w35">
    </colgroup>
    <tbody>
      <tr>
        <%-- 등록일자 --%>
        <th><s:message code="storeEmp.regDt" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchStartDate"
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
                      id="srchEndDate"
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
        <%-- 사원번호 --%>
        <th><s:message code="storeEmp.empNo" /></th>
        <td>
          <input type="text" id="srchEmpNo" name="srchEmpNo" ng-model="empNo" class="sb-input w100" maxlength="4" />
        </td>
        <%-- 사원명 --%>
        <th><s:message code="storeEmp.empNm" /></th>
        <td>
          <input type="text" id="srchEmpNm" name="srchEmpNm" ng-model="empNm" class="sb-input w100" maxlength="15" />
        </td>
      </tr>
      <tr>
        <%-- 사원ID --%>
        <th><s:message code="storeEmp.userId" /></th>
        <td>
          <input type="text" id="srchUserId" name="srchUserId" ng-model="userId" class="sb-input w100" maxlength="16" />
        </td>
        <th></th>
        <td></td>
      </tr>
      <tr>
        <%-- 재직여부 --%>
        <th><s:message code="storeEmp.serviceFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchServiceFg"
                    ng-model="serviceFg"
                    items-source="_getComboData('serviceFgComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 휴대폰번호 --%>
        <th><s:message code="storeEmp.mpNo" /></th>
        <td>
          <input type="text" id="srchMpNo" name="srchMpNo" ng-model="mpNo" class="sb-input w100" maxlength="15" />
        </td>
      </tr>
      <tr>
        <%-- 웹사용여부 --%>
        <th><s:message code="storeEmp.webUseYn" /></th>
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
        <%-- SMS수신여부 --%>
        <th><s:message code="storeEmp.smsRecvYn" /></th>
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
      </tr>
    </tbody>
  </table>
  <!--//searchTbl-->

  <div class="mt20 mb20 oh sb-select dkbr">
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
      <%-- 사원신규등록 --%>
      <button class="btn_skyblue" ng-click="registStoreEmp()"><s:message code="storeEmp.empRegist" /></button>
      <%-- 엑셀다운로드 //todo --%>
      <%--<button class="btn_skyblue"><s:message code="cmm.excel.down" /></button>--%>
    </div>
  </div>

  <%-- 매장 사원 그리드 --%>
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeEmp.empNo"/>" binding="empNo" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeEmp.empNm"/>" binding="empNm" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeEmp.userId"/>" binding="userId" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeEmp.webUseYn"/>" binding="webUseYn" data-map="webUseYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeEmp.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeEmp.mpNo"/>" binding="mpNo" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeEmp.smsRecvYn"/>" binding="smsRecvYn" data-map="smsRecvYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeEmpCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>
<script>
  var serviceFg  = ${ccu.getCommCode("007")};
  var webUseYn   = ${ccu.getCommCode("067")};
  var smsRecvYn  = ${ccu.getCommCode("072")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/storeEmp.js?ver=20180829.01" charset="utf-8"></script>

<%-- 매장사원 상세정보 레이어 --%>
<c:import url="/WEB-INF/view/base/store/emp/storeEmpDetail.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장사원 등록/수정 레이어 --%>
<c:import url="/WEB-INF/view/base/store/emp/storeEmpSave.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 패스워드 변경 레이어 --%>
<%--
<c:import url="/WEB-INF/view/base/store/emp/storeEmpModifyPwd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
--%>



