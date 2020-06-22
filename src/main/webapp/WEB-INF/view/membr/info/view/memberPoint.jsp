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
        <col class="w10"/>
        <col class="w15"/>
        <col class="w5"/>
        <col class="w15"/>
      </colgroup>
      <tbody>
      <tr class="brt">
        <%-- 변경 포인트 --%>
        <th><s:message code="memberPoint.tit.totAdjPoint"/></th>
        <td>
          <input type="text" class="sb-input w100" ng-model="changeAll.totAjdPoint" id="totAjdPoint"/>
        </td>
        <%-- 비고  --%>
        <th><s:message code="memberPoint.remark"/></th>
        <td>
          <input type="text" class="sb-input w100" ng-model="changeAll.adjustPartRemark" id="adjustPartRemark"/>
        </td>
        <td>
<%--          <input type="button" class="sb-input w5" value="저장" onclick="adjustAll()"/>--%>
          <button class="btn_blk sb-input" ng-click="adjustAll()">저장</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

  <%-- 엑셀 --%>
  <div class="w100 mt10 mb10">
    <h2 class="h2_tit oh lh30">
      <s:message code="memberPoint.excelPoing.title"/>
    </h2>
    <table class="searchTbl">
      <colgroup>
        <col class="w1"/>
        <col class="w25"/>
        <col class="w10"/>
        <col class="w5"/>
        <col class="w5"/>
        <col class="w5"/>
        <col class="w25"/>
        <col class="w5"/>
        <col class="w5"/>
        <col class="w15"/>
        <col class="w5"/>
        <col class="w40"/>
        <col class="w40"/>
      </colgroup>
      <tbody>
      <tr class="brt">
        <th></th>
        <td>
          <input type="button" class="sb-input w30" value="양식다운로드" id=""/>
          <input type="button" class="sb-input w30" value="양식업로드" id=""/>
          <input type="button" class="sb-input w30" value="편집화면다운로드" id=""/>
        </td>
        <td>
          <select class="sb-select w100">
            <option value="all">전체</option>
            <option value="success">성공내역</option>
            <option value="error">오류내역</option>
          </select>
        </td>
        <th>
          <s:message code="memberPoint.rowNum"></s:message>
        </th>
        <td>
          <input type="text" class="sb-input w100 id=""/>
        </td>
        <td>
          <input type="button" class="sb-input w100" value="조회" id=""/>
        </td>
        <td></td>
        <td>
          <input type="button" class="sb-input w100" value="양식검증" id=""/>
        </td>
        <th><s:message code="memberPoint.remark"/></th>
        <td>
          <input type="text" class="sb-input w100" id=""/>
        </td>
        <td>
          <input type="button" class="sb-input w100" value="저장" id=""/>
        </td>
        <td></td>
      </tr>
      <tr>
        <th>
        </th>
        <td>
          <h2 class="h2 lh30">
            <s:message code="memberPoint.excelDescriptionOne"/>
          </h2>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>
          <h2 class="h2 lh30">
            <s:message code="memberPoint.excelDescriptionTwo"/>
          </h2>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <th>
        </th>
        <td>
          <h2 class="h2 lh30">
            <s:message code="memberPoint.excelDescriptionThree"/>
          </h2>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>
          <h2 class="h2 lh30">
            <s:message code="memberPoint.excelDescriptionFour"/>
          </h2>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <th>
        </th>
        <td>
          <h2 class="h2 lh30">
            <s:message code="memberPoint.excelDescriptionFive"/>
          </h2>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>
          <h2 class="h2 lh30">
            <s:message code="memberPoint.excelDescriptionSix"/>
          </h2>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
      </tr>
      </tbody>
    </table>
  </div>

  <%-- 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
      <h2 class="h4 oh lh30">
        <s:message code="memberPoint.title.delete"/>
      </h2>
      <h2 class="h4 oh lh30">
        <button>삭제</button>
      </h2>
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"
                             is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="memberPoint.tit.searchResult"/>" binding="searchResult"
                             width="240" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="memberPoint.memberClassNm"/>" binding="memberClassNm" width="115"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="memberPoint.memberNo"/>" binding="memberNo" width="115"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="memberPoint.memberNm"/>" binding="memberNm" width="115"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="memberPoint.memberCardNo"/>" binding="memberCardNo" width="115"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="memberPoint.avablPoint"/>" binding="avablPoint" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="memberPoint.chanPoint"/>" binding="chanPoint" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="memberPoint.totAdjPoint"/>" binding="totAdjPoint" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberPoint.js?ver=2019052801.11"
        charset="utf-8"></script>