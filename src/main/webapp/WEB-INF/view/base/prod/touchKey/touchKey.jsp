<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<%--서브컨텐츠--%>
<div class="subCon" ng-controller="touchKeyCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>

  <%--테이블속성, 테이블관리, 판매터치키 page에만 쓰임--%>
  <div class="TblWrapBr touchKeyWrap oh mt10">
    <%--left--%>
    <div class="w30 fl">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code="touchKey.prodList"/></span>
        <div class="txtIn">
          <button class="btn_skyblue fl ml20" id="btnSearch"><s:message code="touchKey.reload"/></button>
        </div>
      </div>
      <div class="b4 mb5 pd5">
        <div class="updownSet">
          <span class="fl bk lh30 s14 ml5"><s:message code="touchKey.classUseYn"/> :</span>
          <div class="sb-select dkbr fl w85px ml5">
            <wj-combo-box
              id="touchKeyFilterCombo"
              ng-model="touchKeyFilter"
              items-source="_getComboData('touchKeyFilterCombo')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)"
              selected-index-changed="setTouchKeyFilter(s)">
            </wj-combo-box>
          </div>
        </div>
        <div class="updownSet mt5">
          <span class="fl bk lh30 s14 ml5"><s:message code="touchKey.class"/> :</span>
          <input type="text" id="_prodClassCdNm" name="prodClassCdNm" class="sb-input fl w60 ml5" style="font-size: 13px;"
                 ng-model="prodClassInfo.prodClassCdNm"
                 ng-click="popUpProdClass()"
                 placeholder="상품분류 선택" ng-readonly="true">
        </div>
      </div>
      <%--위즈모 테이블--%>
      <div class="cfgWrap2">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="ListBox"
          items-source="data"
          item-formatter="_itemFormatter"
          allow-dragging="None"
          is-read-only="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="touchKey.grid.touchKeyUsed"/>"
                               binding="touchKeyUsed" width="50"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="touchKey.grid.prodCd"/>" binding="prodCd"
                               width="*" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="touchKey.grid.prodNm"/>" binding="prodNm"
                               width="200"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="touchKey.grid.prodClass"/>"
                               binding="prodClassCd"
                               visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="touchKey.grid.prodClassNm"/>"
                               binding="prodClassNm" width="100"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="touchKey.grid.saleUprc"/>"
                               binding="saleUprc" width="*" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
    <%--//left--%>
    <%--right--%>
    <div class="fl ml10" style="width: 502px;">
      <%--미리보기 영역 시작--%>
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code="touchKey.preview"/></span>
        <div class="txtIn">
          <div class="sb-select dkbr fl w120px">
            <div id="selectStyle"></div>
          </div>
          <button class="btn_skyblue fl ml5" id="btnApplyStyle"><s:message
            code="touchKey.applyStyle"/></button>
          <button class="btn_skyblue fl ml20" id="btnSave"><s:message code="cmm.save"/></button>
        </div>
      </div>
      <div id="touchArea" class="prev2 fl">
        <%--분류버튼 영역 시작--%>
        <div class="touchClassWrap" id="classWrap">
          <%--1줄 "hClassLine1", 2줄 "hClassLine2", 3줄 "hClassLine3" 사용 --%>
          <%--터치키가 들어가는 위치 --%>
          <div class="" id="classArea" tabindex="-1">
          </div>
        </div>
        <%--//분류버튼 영역 끝--%>
        <%--상품버튼 영역 시작--%>
        <div class="touchProdsWrap" id="prodWrap">
          <%--1줄 "hProdLine1", 2줄 "hProdLine2", 3줄 "hProdLine3" 사용 --%>
          <%--터치키가 들어가는 위치 --%>
          <div class="" id="prodArea" tabindex="-1">
          </div>
        </div>
        <%--//상품버튼 영역 끝 --%>
      </div>
      <%--//미리보기 영역 끝 --%>
    </div>
    <%--//right--%>
    <div class="fl ml5" style="width: 125px;">
      <div class="updownSet oh mb10">
      </div>
      <%-- 페이지 버튼 영역 시작 --%>
      <%-- 분류페이지 버튼 --%>
      <div id="divClassNavWrap" class="classNavWrap">
        <div class="classPageNoWrap">
          <span id="classPageNoText" class="s16"></span>
        </div>
        <div class="classNav">
          <div class="classNavPrev fl" id="grpNavPrev"></div>
          <div class="classNavNext fl" id="grpNavNext"></div>
        </div>
      </div>
      <%-- 상품페이지 버튼 --%>
      <div id="divProdNavWrap" class="prodNavWrap">
        <div id="keyStyle" class="oh keyStyleWrap hideNav">
          <div id="fontStyleWrap">
            <span class="s12 fl lh30 bk"><s:message code="touchKey.cellType"/></span>
            <div class="sb-select txtIn fl w100 mb5">
              <div id="cellTypeCombo"></div>
            </div>
            <span class="s12 fl lh30 bk"><s:message code="touchKey.font"/></span>
            <div class="sb-select txtIn fl w100 mb5">
              <%-- 폰트컬러 --%>
              <div id="fontColor"></div>
            </div>
            <div class="sb-select txtIn fl w100 mb5">
              <%-- 폰트사이즈 --%>
              <div id="fontSize"></div>
            </div>
          </div>
          <div id="colorStyleWrap" class="mb5">
            <span class="s12 fl lh30 bk mt5"><s:message code="touchKey.fill"/></span>
            <div class="sb-select txtIn w100">
              <%-- 채우기 --%>
              <div id="fillColor"></div>
            </div>
          </div>
          <div class="fl">
            <button class="btn_skyblue mb5" id="btnReset" ng-click="$broadcast('btnReset')">
              <s:message code="touchKey.reset"/></button>
            <button class="btn_skyblue" id="btnDelete" ng-click="$broadcast('btnDelete')">
              <s:message code="touchKey.delete"/></button>
          </div>
        </div>
        <div class="prodPageNoWrap">
          <span id="prodPageNoText" class="s16"></span>
        </div>
        <div class="prodNav">
          <div class="prodNavPrev fl" id="prodNavPrev"></div>
          <div class="prodNavNext fl" id="prodNavNext"></div>
        </div>
      </div>
      <%--// 페이지 버튼 영역 끝 --%>
    </div>
  </div>

</div>
<%--//서브컨텐츠--%>

<script>
  var urlParams = (function (url) {
    var result = {};
    var idx = url.lastIndexOf('?');

    if (idx > 0) {
      var params = url.substring(idx + 1).split('&');

      for (var i = 0; i < params.length; i++) {
        idx = params[i].indexOf('=');

        if (idx > 0) {
          result[params[i].substring(0, idx)] = params[i].substring(idx + 1);
        }
      }
    }

    return result;
  })(window.location.href);

  // Default resources are included in grapheditor resources
  mxLoadResources = false;

  // urlParams is null when used for embedding
  window.urlParams = window.urlParams || {};

  window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE || 10485760;

  window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
  window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
  window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
  window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
  window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';

  window.TOUCHKEY_OPEN_URL = window.TOUCHKEY_OPEN_URL || '/base/prod/touchKey/touchKey/touchKeyList.sb';
  window.TOUCHKEY_SAVE_URL = window.TOUCHKEY_SAVE_URL || '/base/prod/touchKey/touchKey/save.sb';

  window.mxBasePath = window.mxBasePath || '/resource/vendor/mxgraph/src';
  window.mxLanguage = window.mxLanguage || urlParams['lang'];
  window.mxLanguages = window.mxLanguages || ['ko'];

  window.MAX_CLASS_ROW = '${maxClassRow}' || '2';

</script>
<script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.min.js"
        charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="/resource/vendor/wijmo/js/grid/wijmo.grid.filter.min.js?ver=5.20182.500"
        charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/TouchKey.js?ver=20181210.01"
        charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
