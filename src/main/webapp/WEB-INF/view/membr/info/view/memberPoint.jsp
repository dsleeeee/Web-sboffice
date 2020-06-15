<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="memberPointCtrl">

  <%-- 상단 타이틀 --%>
  <div class="w100 mt10 mb10">
    <h2 class="h2_tit oh lh30">
      <s:message code="memebrPoint.totAjdPoint.title"/>
    </h2>
    <table class="searchTbl">
      <colgroup>
        <col class="w5"/>
        <col class="w10"/>
        <col class="w5"/>
        <col class="w15"/>
      </colgroup>
      <tbody>
      <tr class="brt">
        <%-- 변경 포인트 --%>
        <th><s:message code="memberPoint.totAjdPoint"/></th>
        <td>
          <input type="text" class="sb-input w100" id="totAjdPoint"/>
        </td>
        <%-- 비고  --%>
        <th><s:message code="memberPoint.remark"/></th>
        <td>
          <input type="text" class="sb-input w100" id="remark"/>
        </td>
        <td>
          <input type="button" class="sb-input w5" value="저장" id=""/>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

  <%-- 엑셀 --%>
  <div class="w100 mb10">
    <h2 class="h2_tit oh lh30">
      <s:message code="memberPoint.excelPoing.title"/>
    </h2>
    <table class="searchTbl">
      <colgroup>
        <col class="w10"/>
        <col class="w10"/>
        <col class="w10"/>
        <col class="w50"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 변경 포인트 --%>
        <td>
          <input type="button" class="sb-input w100" value="양식다운로드" id=""/>
          <%--        <button class="btn_blue fr w100">양식다운로드</button>--%>
        </td>
        <td>
          <input type="button" class="sb-input w100" value="양식업로드" id=""/>
        </td>
        <td>
          <input type="button" class="sb-input w100" value="편집화면다운로드" id=""/>
        </td>
        <%-- 비고  --%>
        <td>
          <input type="button" class="sb-input w50" id=""/>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <%-- 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter"
              is-read-only="true">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="incln.group"/>" binding="group" width="130"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.allGroup"/>" binding="allGroup" width="115"
                             is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.gendrF"/>" binding="gendrF" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.gendrG"/>" binding="gendrG" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.ageGroupOne"/>" binding="ageGroupOne" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.ageGroupTwo"/>" binding="ageGroupTwo" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.ageGroupThree"/>" binding="ageGroupThree" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.ageGroupFour"/>" binding="ageGroupFour" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.ageGroupFive"/>" binding="ageGroupFive" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="incln.ageGroupSix"/>" binding="ageGroupSix" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/incln/incln.js?ver=2019052801.11"
        charset="utf-8"></script>