<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div id="attrArea" class="wj-TblWrap mt5 ng-cloak" ng-hide="isAttrTab">
  <div class="w50 fl">
    <%--위즈모 테이블--%>
    <div id="gridAttrClass" class="wj-TblWrapBr mr10 pd5" style="height: 550px;" ng-controller="sideMenuAttrClassCtrl">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='sideMenu.tab.attr.class' /></span>
        <button class="btn_skyblue" id="btnAddClass" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelClass" ng-click="deleteRow()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveClass" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div style="height:450px">
        <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter"
            ime-enabled="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenu.attr.sdattrClassCd"/>" binding="sdattrClassCd" width="100" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenu.attr.sdattrClassNm"/>" binding="sdattrClassNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <div class="w50 fl">
    <%--위즈모 테이블--%>
    <div id="gridAttrCd" class="wj-TblWrapBr pd5" style="height: 550px;" ng-controller="sideMenuAttrAttrCtrl">
      <div class="updownSet oh mb10" style="height:60px;">
        <span class="fl bk lh30"><s:message code='sideMenu.tab.attr.attr' /><span id="sideMenuAttrTitle"></span> </span>
          <br>
          <br>
        <button class="btn_up" id="btnUpAttr" ng-click="rowMoveUp()" >
          <s:message code="cmm.up" />
        </button>
        <button class="btn_down" id="btnDownAttr" ng-click="rowMoveDown()" >
          <s:message code="cmm.down" />
        </button>
        <button class="btn_skyblue" id="btnAddAttr" ng-click="addRow()" >
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelAttr" ng-click="deleteRow()" >
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveAttr" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div style="height:420px;">
        <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter"
            ime-enabled="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenu.attr.sdattrCd"/>" binding="sdattrCd" width="100"is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenu.attr.sdattrNm"/>" binding="sdattrNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>

      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>
<script>
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenuAttr.js?ver=20181223.08" charset="utf-8"></script>


