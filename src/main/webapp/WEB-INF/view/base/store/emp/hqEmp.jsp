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
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('hqEmpCtrl')">
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
            <span class="txtIn"><input id="startDate" name="startDate" class="w200px" /></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="endDate" name="endDate" class="w200px" /></span>
            <span class="chk ml10"><input type="checkbox" id="chkDt" checked/>
            <label for="chkDt"><s:message code="cmm.all.day" /></label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%--사원번호 --%>
        <th><s:message code="hqEmp.empNo" /></th>
        <td>
          <input type="text" id="srchEmpNo" name="srchEmpNo" ng-model="hqEmp.empNo" class="sb-input w100" maxlength="4" />
          <%--
          <div class="sb-select">
            <div id="empNo"></div>
          </div>
          --%>
        </td>
        <%-- 사원명 --%>
        <th><s:message code="hqEmp.empNm" /></th>
        <td>
          <input type="text" id="srchEmpNm" name="srchEmpNm" ng-model="hqEmp.empNm" class="sb-input w100" maxlength="15" />
          <%--
          <div class="sb-select">
            <div id="empNm"></div>
          </div>
          --%>
        </td>
      </tr>
      <tr>
        <%--사원ID --%>
        <th><s:message code="hqEmp.userId" /></th>
        <td>
          <input type="text" id="srchUserId" name="srchUserId" ng-model="hqEmp.userId" class="sb-input w100" maxlength="16" />
          <%--
          <div class="sb-select">
            <div id="userId"></div>
          </div>
          --%>
        </td>
        <%-- 재직여부 --%>
        <th><s:message code="hqEmp.serviceFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchHqEmp"
                    ng-model="hqEmp.serviceFg"
                    control="serviceFgCombo"
                    items-source="_getComboData('serviceFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
          <%--
          <div class="sb-select">
            <div id="serviceFg"></div>
          </div>
          --%>
        </td>
      </tr>
      <tr>
        <%--휴대폰번호 --%>
        <th><s:message code="hqEmp.mpNo" /></th>
        <td>
          <input type="text" id="srchMpNo" name="srchMpNo" ng-model="hqEmp.mpNo" class="sb-input w100" maxlength="15" />
          <%--
          <div class="sb-select">
            <div id="mpNo"></div>
          </div>
          --%>
        </td>
        <%-- 웹사용여부 --%>
        <th><s:message code="hqEmp.webUseYn" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchWebUseYn"
                    ng-model="hqEmp.webUseYn"
                    control="webUseYnCombo"
                    items-source="_getComboData('webUseYn')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
          <%--
          <div class="sb-select">
            <div id="webUseYn"></div>
          </div>
          --%>
        </td>
      </tr>
      <tr>
        <%--SMS 수신여부 --%>
        <th><s:message code="hqEmp.smsRecvYn" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchSmsRecvYn"
                    ng-model="hqEmp.smsRecvYn"
                    control="smsRecvYnCombo"
                    items-source="_getComboData('smsRecvYn')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
          <%--
          <div class="sb-select">
            <div id="smsRecvYn"></div>
          </div>
          --%>
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
      <button class="btn_skyblue" id="btnRegist" ng-click="registHqEmp();"><s:message code="hqEmp.hqEmpReg" /></button>
      <%-- sms 전송 //TODO --%>
      <%--<button class="btn_skyblue" id="btnSMS"><s:message code="hqEmp.sendSMS" /></button>--%>
      <%-- 엑셀다운로드 //TODO --%>
      <%--<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>--%>
    </div>
  </div>

  <%-- 본사 사원 그리드 --%>
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="chk" width="40"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.empNo"/>" binding="empNo" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.empNm"/>" binding="empNm" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.userId"/>" binding="userId" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.serviceFg"/>" binding="serviceFg" data-map="areaFgDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.mpNo"/>" binding="mpNo" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqEmp.smsRecvYn"/>" binding="smsRecvYn" data-map="areaFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/hqEmp.js?ver=20181121.01" charset="utf-8"></script>
<%-- 사원신규등록 레이어 팝업 --%>
<%--
<c:import url="/WEB-INF/view/base/store/emp/hqEmpSave.jsp">
</c:import>
--%>

<%-- 사원상세/수정 팝업 --%>
<%--
<c:import url="/WEB-INF/view/base/store/emp/hqEmpDetail.jsp">
</c:import>
--%>

