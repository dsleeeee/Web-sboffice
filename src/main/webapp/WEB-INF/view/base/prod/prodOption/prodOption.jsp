<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('prodOptionCtrl')" >
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

  <div class="wj-TblWrap mt20 mb20 w50 fl" ng-controller="prodOptionCtrl">
    <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
      <s:message code="prodOption.optionGrp"/>
      <div class="updownSet oh mb10">
        <button class="btn_skyblue" id="btnOptionGrpAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnOptionGrpDel" ng-click="del()"><s:message code='cmm.del' /></button>
        <button class="btn_skyblue" id="btnOptionGrpSave" ng-click="save()"><s:message code='cmm.save' /></button>
      </div>
      <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
                  id="wjGridGrp"
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
            <wj-flex-grid-column header="<s:message code="prodOption.optionGrpCd"/>" binding="optionGrpCd" width="65" is-read-only="true"  align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodOption.optionGrpNm"/>" binding="optionGrpNm" width="120"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodOption.useYn"/>" binding="useYn" data-map="useYnDataMap" width="65" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodOption.cnt"/>" binding="cnt" width="65" is-read-only="true" align="center"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <div class="wj-TblWrap mt20 mb20 w50 fl" ng-controller="prodOptionValCtrl">
    <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
      <s:message code="prodOption.optionVal"/><span id="optionGrpTitle"></span>
      <div class="updownSet oh mb10">
        <button class="btn_skyblue" id="btnOptionValAddWithProd" ng-click="addRowWithProd()"><s:message code='prodOption.addWithProd' /></button>
        <button class="btn_skyblue" id="btnOptionValAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnOptionValDel" ng-click="del()"><s:message code='cmm.del' /></button>
        <button class="btn_skyblue" id="btnOptionValSave" ng-click="save()"><s:message code='cmm.save' /></button>
      </div>
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
            ime-enabled="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodOption.optionGrpCd"/>" binding="optionGrpCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodOption.optionValCd"/>" binding="optionValCd" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodOption.optionValNm"/>" binding="optionValNm" width="120" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodOption.prodCd"/>" binding="optProdCd" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodOption.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodOption.printYn"/>" binding="printYn" data-map="printYnDataMap" width="70" align="center"></wj-flex-grid-column>
        </wj-flex-grid>
        </div>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script>
  <%-- 사용여부 --%>
  var useYn = ${ccu.getCommCodeExcpAll("067")};
  var orgnFg = "${orgnFg}";
  var gvListScaleBoxData = ${ccu.getListScale()};
  // 매장상품제한구분 사용여부(매장에서 사용하지만 본사환경설정값으로 여부파악)
  var storeProdUseFg = "${storeProdUseFg}";
  // 브랜드 사용여부
  var brandUseFg = "${brandUseFg}";
  // 사용자 브랜드
  var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodOption/prodOption.js?ver=20230627.01" charset="utf-8"></script>

<%-- 추가(상품포함) 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodOption/prodOptionAddWithProd.jsp">
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>