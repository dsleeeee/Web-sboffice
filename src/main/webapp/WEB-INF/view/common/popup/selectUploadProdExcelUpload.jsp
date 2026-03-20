<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" style="display: none" ng-controller="selectUploadProdExcelUploadCtrl">

  <input type="text"
         id="selectUploadProdExcelUploadTargetId"
         class="sb-input fl mr5"
         style="cursor:pointer; width:150px;"
         readonly/>

  <input type="file" class="form-control" id="selectUploadProdExcelUploadFile"
         ng-model="selectUploadProdExcelUploadFile"
         onchange="angular.element(this).scope().excelFileChanged()"
         accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:50px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="selectUploadProdExcelUploadFlex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="selectProdMMoms.prodCd"/>" binding="prodCd" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="selectProdMMoms.prodNm"/>" binding="prodNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/common/popup/selectUploadProdExcelUpload.js?ver=20260318.01" charset="utf-8"></script>