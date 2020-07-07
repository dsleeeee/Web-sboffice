<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">
</c:import>

<div class="subCon" ng-controller="memberExcelUploadCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('memberExcelUploadCtrl', 1)">
        <s:message code="cmm.search"/>
      </button>
    </div>
  </div>

  <%-- 상단 타이틀 --%>
  <div class="w100 mt10 mb10">
    <table class="searchTbl">
      <colgroup>
        <col class="w10"/>
        <col class="w10"/>
        <col class="w10"/>
        <col class="w5"/>
        <col class="w5"/>
        <col class="w5"/>
        <col class="w5"/>
        <col class="w25"/>
        <col class="w10"/>
        <col class="w5"/>
      </colgroup>
      <tbody>

      <%-- First Row--%>
      <tr class="brt">
        <%-- 양식다운로드 --%>
        <td>
          <button class="btn_blk sb-input w100" style="margin-left: 5px" ng-click="excelTextUpload('excelFormDown')">
            <s:message code="member.excel.upload"/></button>
        </td>
        <%-- 양식업로드  --%>
        <td>
          <button class="btn_blk sb-input w100" style="margin-left: 5px" ng-click="excelTextUpload()">
            <s:message code="acins.reg.excelFormUpload"/>
          </button>
        </td>
        <%-- 편집화면다운로드  --%>
        <td>
          <button class="btn_blk sb-input w100" style="margin-left: 5px" ng-click="excelDownload()"><s:message
                  code="member.excel.pageDownload"/></button>
        </td>
        <%-- 성공내역, 실페내역  --%>
        <td>
          <select type="text" class="sb-select w100" ng-model="statu" id=""
                  ng-options="item.name for item in statuList">
          </select>
        </td>
        <%-- 검색줄수  --%>
        <td colspan="4"></td>
        <%-- 양식검증  --%>
        <td>
          <button class="btn_blk sb-input w100" style="margin-left: 5px" ng-click=""><s:message
                  code="member.excel.check"/></button>
        </td>
        <%-- 저장  --%>
        <td>
          <button class="btn_blk sb-input w100" style="margin-left: 5px" ng-click="save()"><s:message
                  code="cmm.save"/></button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

  <%-- 그리드 top --%>
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
  <div class="w100" style="margin-bottom: 5px">
    <div class="wj-gridWrap">
      <h2 class="h2 oh lh30">
        <s:message code="member.excel.combo.list"/>
      </h2>
    </div>
    <div class="wj-gridWrap" style="height:100px; overflow-y: hidden; overflow-x: hidden;">
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
        <wj-flex-grid-column header="<s:message code="dayMembrDetail.membrClassCd"/>" binding="searchResult"
                             width="115" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.regStore"/>" binding="" width="115" align="center"
                             is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.gendrFg"/>" binding="memberClassNm" width="220"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="regist.wedding"/>" binding="memberNo" width="230"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.cardUseType"/>" binding="memberNo" width="230"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayMembrDetail.emailRecvYn"/>" binding="memberNm" width="115"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayMembrDetail.smsRecvYn"/>" binding="memberCardNo" width="120"
                             is-read-only="true" align="right"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 그리드 bottom --%>
  <div class="w100">
    <div class="wj-gridWrap">
      <h2 class="h2 oh lh30">
        <s:message code="member.excel.memberInfo"/>
        <button class="btn_skyblue sb-input w5" style="margin: 5px 15px" ng-click=""><s:message
                code="cmm.delete"/></button>
      </h2>
    </div>
    <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" is-read-only="false"
                             align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.nm.kr"/>" binding="membrKrNm" width="115"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.nm.en"/>" binding="membrEnNm" width="115"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.membrClassCd"/>" binding="membrClassCd" width="115"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.store"/>" binding="membrStore" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.gendrFg"/>" binding="gendrFg" width="100"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="regist.membr.card.no"/>" binding="membrCardNo" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.birthday"/>" binding="memberBithday" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.telNo"/>" binding="memberTelNo" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.shortNo"/>" binding="memebrShortNo" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.email"/>" binding="memberEmail" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.postNo"/>" binding="memberPostNo" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.addr"/>" binding="memberAddr" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="member.excel.addrDtl"/>" binding="memberAddrDtl" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberExcelUpload.js?ver=2019052801.11"
        charset="utf-8"></script>