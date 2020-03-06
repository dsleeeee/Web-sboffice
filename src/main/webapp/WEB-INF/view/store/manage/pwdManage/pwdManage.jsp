<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="pwdManageCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('pwdManageCtrl',1)">
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
      <tr  ng-show="userOrgnFg != 'S'">
        <%-- 사원구분 --%>
        <th><s:message code="pwdManage.empOrgnFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchEmpOrgnFg"
                    ng-model="empOrgnFg"
                    control="empOrgnFgCombo"
                    items-source="_getComboData('empOrgnFg')"
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
      <tr ng-show="userOrgnFg == 'M'">
        <%-- 총판/대리점 코드 --%>
        <th><s:message code="pwdManage.agencyCd" /></th>
        <td>
          <input type="text" id="srchAgencyCd" class="sb-input w100" ng-model="srchAgencyCd" maxlength="5"/>
        </td>
        <%-- 총판/대리점명 --%>
        <th><s:message code="pwdManage.agencyNm" /></th>
        <td>
          <input type="text" id="srchAgencyNm" class="sb-input w100" ng-model="agencyNm" maxlength="50"/>
        </td>
      </tr>
      <tr ng-show="userOrgnFg != 'H' && userOrgnFg != 'S'">
        <%-- 본사코드 --%>
        <th><s:message code="pwdManage.hqOfficeCd" /></th>
        <td>
          <input type="text" id="srchHqOfficeCd" class="sb-input w100" ng-model="srchHqOfficeCd" maxlength="5"/>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="pwdManage.hqOfficeNm" /></th>
        <td>
          <input type="text" id="srchHqOfficeNm" class="sb-input w100" ng-model="hqOfficeNm" maxlength="50"/>
        </td>
      </tr>
      <tr ng-show="userOrgnFg != 'S'">
        <%-- 매장코드 --%>
        <th><s:message code="pwdManage.storeCd" /></th>
        <td>
          <input type="text" id="srchStoreCd" class="sb-input w100" ng-model="srchStoreCd" maxlength="7"/>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="pwdManage.storeNm" /></th>
        <td>
          <input type="text" id="srchStoreNm" class="sb-input w100" ng-model="storeNm" maxlength="50"/>
        </td>
      </tr>
      <tr>
        <%-- 사용자ID --%>
        <th><s:message code="pwdManage.userId" /></th>
        <td>
          <input type="text" id="srchUserId" class="sb-input w100" ng-model="userId" maxlength="20"/>
        </td>
        <%-- 사용자명 --%>
        <th><s:message code="pwdManage.userNm" /></th>
        <td>
          <input type="text" id="srchUserNm" class="sb-input w100" ng-model="userNm" maxlength="50"/>
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
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.empOrgnFg"/>" binding="empOrgnFg" data-map="empOrgnFgDataMap" width="*" align="center" is-read-only="true"></wj-flex-grid-column>

      <c:if test="${orgnFg != 'HQ' and orgnFg != 'STORE'}">
        <wj-flex-grid-column header="<s:message code="pwdManage.agencyCd"/>" binding="agencyCd" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.agencyNm"/>" binding="agencyNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
      </c:if>

      <c:if test="${orgnFg != 'STORE'}">
        <wj-flex-grid-column header="<s:message code="pwdManage.hqOfficeCd"/>" binding="hqOfficeCd" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.hqOfficeNm"/>" binding="hqOfficeNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
      </c:if>
        <wj-flex-grid-column header="<s:message code="pwdManage.storeCd"/>" binding="storeCd" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.storeNm"/>" binding="storeNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.empNo"/>" binding="empNo" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.userId"/>" binding="userId" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.userNm"/>" binding="userNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap"  width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.webUseYn"/>" binding="webUseYn" data-map="webUseYnDataMap"  width="*" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="pwdManage.mpNo"/>" binding="mpNo" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="pwdManageCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>
<script>
  var serviceFg    = ${ccu.getCommCode("007")};
  var webUseYn     = ${ccu.getCommCode("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/pwdManage/pwdManage.js?ver=2018121102.08" charset="utf-8"></script>

<%-- 레이어 팝업 : 비밀번호 변경 --%>
<c:import url="/WEB-INF/view/store/manage/pwdManage/pwdChange.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 로그인 잠금해제 --%>
<c:import url="/WEB-INF/view/store/manage/pwdManage/pwdUnlock.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
