<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="alimtalkCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="nxBtnSearch" ng-click="_broadcast('alimtalkCtrl')">
        <s:message code="cmm.search"/>
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15"/>
      <col class="w35"/>
      <col class="w15"/>
      <col class="w35"/>
    </colgroup>
    <tbody>
    </tbody>
  </table>

    <div class="updownSet oh mb10 mt10">
        <button class="btn_skyblue" id="btnAddRepresent" ng-click="addRow()">
            <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelRepresent" ng-click="deleteRow()">
            <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveRepresent" ng-click="save()">
            <s:message code="cmm.save" />
        </button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            id="wjGridList"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            control="flex"
            initialized="initGrid(s,e)"
            item-formatter="_itemFormatter">

            <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="alimtalk.alimtalkFg"/>" binding="alimtalkFg" width="80" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="alimtalk.mpNo"/>" binding="mpNo" width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="alimtalk.mpInfo"/>" binding="mpInfo" width="200" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="alimtalk.remark"/>" binding="remark" width="*" align="left"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/etc/alimtalk/alimtalk.js?ver=20260615.01" charset="utf-8"></script>