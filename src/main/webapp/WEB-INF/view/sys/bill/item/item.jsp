<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/bill/item/item/" />

<div class="subCon" ng-controller="printCodeCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
        <%-- 코드 --%>
        <%--
        <th><s:message code="item.prtCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchPrtCd" ng-model="prtCd" />
        </td>
        --%>
        <%-- 코드명 --%>
        <th><s:message code="item.prtNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchPrtNm" ng-model="prtNm" />
        </td>
        <th></th>
        <td></td>
      </tr>
      <tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 oh">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('printCodeCtrl')"><s:message code="cmm.search" /></button>
  </div>

  <div class="w50 fl mt40" style="width: 100%">
    <%--위즈모 테이블--%>
    <div id="gridPrintCode" class="wj-TblWrapBr mr10 pd20" style="height: 480px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='item.gridNm' /></span>
        <button class="btn_skyblue" id="btnAdd" style="display: none;" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDel" style="display: none;" ng-click="delete()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSave" style="display: none;" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div style="height:400px">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="item.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="item.prtCd"/>" binding="prtCd" width="200"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="item.prtNm"/>" binding="prtNm" width="200"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="item.samplYn"/>" binding="samplYn" width="60"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="item.content"/>" binding="content" width="*"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

</div>
<script type="text/javascript" src="/resource/solbipos/js/sys/bill/item/item.js?ver=20181018.01" charset="utf-8"></script>
