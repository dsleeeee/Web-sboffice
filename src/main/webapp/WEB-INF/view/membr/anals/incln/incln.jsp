<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="inclnCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_broadcast('inclnCtrl')">
        <s:message code="cmm.search"/>
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w5"/>
      <col class="w15"/>
      <col class="w5"/>
      <col class="w25"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회기간 --%>
      <th>
        <s:message code="dlvrInfo.srchDate"/>
      </th>
      <td>
        <div class="sb-select">
          <span class="txtIn"> <input id="startDate" name="startDate" class="w200px"/></span>
          <span class="rg">~</span>
          <span class="txtIn"> <input id="endDate" name="endDate" class="w200px"/></span>
        </div>
      </td>
      <td>
        <div class="sb-input">
          <input type="radio" name="searchOption" ng-model="searchOption" value="AMT" ng-checked="true"
                 checked="checked">
          <label class="mr5"><s:message code="incln.sum.amt"/></label>
          <input type="radio" name="searchOption" ng-model="searchOption" value="QTY">
          <label><s:message code="incln.sum.qty"/></label>
        </div>
      </td>
      <td></td>
    </tr>
    </tbody>
  </table>

  <%-- 그리드 --%>
  <div class="w50 fl mt40 mb20" style="width: 100%">
    <div class="wj-TblWrapBr ml10 pd20" style="height: 600px;">
      <div class="oh sb-select dkbr">
        <button class="btn_skyblue ml5 fr" id="save" ng-click="excelDownload()">
          <s:message code="member.excel"/>
        </button>
      </div>
      <div class="wj-gridWrap" style="height:480px; overflow-y: hidden;">
        <div class="row">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter">
            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="incln.lv1Nm"/>" binding="lv1Nm" width="130"
                                 is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.lv2Nm"/>" binding="lv2Nm" width="150"
                                 is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.lv3Nm"/>" binding="lv3Nm" width="150"
                                 is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.sumSale"/>" binding="sumSale" width="130"
                                 is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.gendrF"/>" binding="sumGendrF" width="115"
                                 is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.gendrG"/>" binding="sumGendrG" width="115"
                                 is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.ageTeens"/>" binding="sumAgeTeens" width="115"
                                 is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.ageTwenties"/>" binding="sumAgeTwenties" width="115"
                                 is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.ageThirties"/>" binding="sumAgeThirties" width="115"
                                 is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.ageForties"/>" binding="sumAgeForties" width="115"
                                 is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.ageFifties"/>" binding="sumAgeFifties" width="115"
                                 is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="incln.ageSixties"/>" binding="sumAgeSixties" width="115"
                                 is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>
    </div>
  </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/incln/incln.js?ver=2019052801.11"
        charset="utf-8"></script>