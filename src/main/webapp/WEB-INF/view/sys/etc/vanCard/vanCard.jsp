<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/etc/vanCard/vanCard/" />

<div class="subCon">

  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>
  
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('vanCtrl');_broadcast('cardCtrl');"><s:message code="cmm.search" /></button>
  </div>
    
  <div id="gridVan" class="w70 fl" style="width: 60%" ng-controller="vanCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr pd20" style="height: 300px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridVanNm' /></span>
        <button class="btn_skyblue" id="btnAddVan" style="display: none;" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelVan" style="display: none;" ng-click="delete()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveVan" style="display: none;" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div style="height:210px">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="vanCard.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.vanFg"/>" binding="vanFg" width="60" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.vanCd"/>" binding="vanCd" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.vanNm"/>" binding="vanNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.mainIp"/>" binding="mainIp" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.mainPort"/>" binding="mainPort" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.subIp"/>" binding="subIp" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.subPort"/>" binding="subPort" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.telNo"/>" binding="telNo" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vanCard.faxNo"/>" binding="faxNo" width="*"></wj-flex-grid-column>

          </wj-flex-grid>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div id="gridVanCard" class="w30 fr" style="width: 40%" ng-controller="vanCardCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 610px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridVanCardNm' /></span>
        <button class="btn_skyblue" id="btnAddMapng" style="display: none;" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelMapng" style="display: none;" ng-click="delete()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveMapng" style="display: none;" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div style="height:510px;">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="vanCard.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.vanNm"/>" binding="vanNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.vanCardcoCd"/>" binding="vanCardcoCd" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.vanCardcoNm"/>" binding="vanCardcoNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.cardcoCd"/>" binding="cardcoCd" width="*" data-map="cardCmpnyDataMap"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div id="gridCard" class="w70 fl" style="width: 60%" ng-controller="cardCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10 pd20 mb10" style="height: 300px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridCardNm' /></span>
        <button class="btn_skyblue" id="btnAddCard" style="display: none;" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelCard" style="display: none;" ng-click="delete()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveCard" style="display: none;" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div style="height:210px;">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="vanCard.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.cardcoCd"/>" binding="cardcoCd" width="80"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.cardcoNm"/>" binding="cardcoNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.bizNo"/>" binding="bizNo" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.telNo"/>" binding="telNo" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.faxNo"/>" binding="faxNo" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="vanCard.hmpgAddr"/>" binding="hmpgAddr" width="*"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  
</div>

<script>
  var cardCmpnyList = ${cardCmpnyList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/etc/vanCard/vanCard.js?ver=20181018.01" charset="utf-8"></script>
