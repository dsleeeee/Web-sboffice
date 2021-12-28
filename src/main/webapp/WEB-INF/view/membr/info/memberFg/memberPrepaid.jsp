<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<!--   선불 화면    -->
<div class="subCon" id="memberPrepaidArea" ng-controller="memberPrepaidCtrl">
  <%--searchTbl--%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl"><s:message code="memberFg.prepaid" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_pageView('memberPrepaidCtrl', 1)">
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
      <%-- 회원번호 --%>
      <th><s:message code="memberFg.memberNo" /></th>
      <td>
        <input type="text" class="sb-input w100" id="memberNo" ng-model="membrNo" onkeyup="fnNxBtnSearch('1');"/>
      </td>
      <%-- 회원명 --%>
      <th><s:message code="memberFg.memberNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="memberNm" ng-model="membrNm" onkeyup="fnNxBtnSearch('1');"/>
      </td>
    </tr>
    <tr>
      <%-- 회원등급 --%>
      <th><s:message code="memberFg.memberClassCd" /></th>
      <td>
        <div class="sb-select">
          <wj-combo-box
                  id="memberClass"
                  ng-model="membrClassCd"
                  control="memberClassCombo"
                  items-source="_getComboData('memberClass')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
      </td>
      <th><s:message code="memberFg.telNo" /></th>
      <td>
        <input type="text" id="telNo" class="sb-input w100" ng-model="telNo" maxlength="15" onkeyup="fnNxBtnSearch('1');"/>
      </td>
    </tr>
    <c:if test="${orgnFg == 'HQ'}">
      <tr>
        <%-- 매장선택 --%>
        <th><s:message code="cmm.store.select" /></th>
        <td>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
            <jsp:param name="targetId" value="prepaidStore"/>
          </jsp:include>
        </td>
        <th></th>
        <td></td>
      </tr>
    </c:if>
   </tbody>
  </table>
    <%--- 적용 그리드 --%>
    <div class="wj-TblWrap mt20 w50 fl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:460px; overflow-y: hidden;" ng-controller="memberPrepaidRegistCtrl">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="memberFg.regist"/></span>
          <button class="btn_skyblue" ng-click="regPostpaid()"><s:message code="cmm.delete" /></button>
        </div>
        <div class="w100 mt10 mb20">
          <div class="wj-gridWrap" style="height:340px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.memberNo"/>" binding="membrNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.memberNm"/>" binding="membrNm" width="80" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.storeNm"/>" binding="storeNm" width="80" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.memberClassCd"/>" binding="membrClassNm" width="80" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.telNo"/>" binding="telNo" width="80" is-read-only="true" ></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        <div class="pageNum3 mt20">
            <%-- id --%>
            <ul id="memberPrepaidRegistCtrlPager" data-size="10">
            </ul>
        </div>
        </div>
      </div>
    </div>

    <%--- 미적용 그리드 --%>
    <div class="wj-TblWrap mt20 w50 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:460px; overflow-y: hidden;" ng-controller="memberPrepaidNoRegistCtrl">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="memberFg.noRegist"/></span>
          <button class="btn_skyblue" ng-click="regPrepaid()"><s:message code="memberFg.regist"/></button>
        </div>
          <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:340px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.memberNo"/>" binding="membrNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.memberNm"/>" binding="membrNm" width="80" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.storeNm"/>" binding="storeNm" width="80" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.memberClassCd"/>" binding="membrClassNm" width="80" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="memberFg.telNo"/>" binding="telNo" width="80" is-read-only="true" ></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
          <div class="pageNum3 mt20">
              <%-- id --%>
              <ul id="memberPrepaidNoRegistCtrlPager" data-size="10">
              </ul>
          </div>
        </div>
      </div>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/memberFg/memberPrepaid.js?ver=20210514.01" charset="utf-8"></script>
