<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="touchKeyEnvstVal" value="${touchKeyEnvstVal}" />
<c:set var="touchKeyGrp" value="${touchKeyGrp}" />

<%--서브컨텐츠--%>
<div class="subCon" ng-controller="touchKeyCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSrchTouchKey">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
    <tr id="trTouchKeyGrp">
      <%-- 분류조회 --%>
      <th><s:message code="touchKey.grp" /></th>
      <td colspan="3">
        <div class="sb-select" style="width:120px; float:left;">
        <wj-combo-box
                id="touchKeyGrpCombo"
                ng-model="touchKeyGrp"
                control="touchKeyGrpCombo"
                items-source="_getComboData('touchKeyGrpCombo')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
        </wj-combo-box>
        </div>
        <%-- 추가터치키생성 --%>
        <button class="btn_skyblue fl ml20" id="btnNewGrp" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2'}">sty  le="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="touchKey.newGrp"/></button>
        <%-- 터치키복사 --%>
        <button class="btn_skyblue fl ml20" id="btnCopyTouchKey" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose> ng-click="$broadcast('showPopUpCopy')">
          <s:message code="touchKey.copy" />
        </button>
        <%-- 신규생성 취소 --%>
        <%--<button class="btn_skyblue fl ml20" id="btnCancleNewGrp" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="touchKey.cancle"/></button>--%>
        <%-- 터치키 신규 등록인지 수정인지 여부 파악을 위해--%>
        <input type="hidden" id="hdNewGrp"/>
      </td>
    </tr>
    <tr id="trApplyStore" ng-show="userOrgnFg != 'S'">
      <th><s:message code="touchKey.applyStore" /></th>
      <td colspan="3" class="oh">
        <button class="btn_blk fl" id="btnApplyStore" ng-click="$broadcast('showPopUp')">
          <s:message code="touchKey.applyStore" />
        </button>
      </td>
    </tr>
    </tbody>
  </table>

  <%--테이블속성, 테이블관리, 판매터치키 page에만 쓰임--%>
  <div class="TblWrapBr touchKeyWrap oh mt10">
    <%--left--%>
    <div class="w30 fl">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code="touchKey.prodList"/></span>
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
          <%--<span class="fl bk lh30 s14 ml5"><s:message code="touchKey.class"/> :</span>--%>
          <input type="text" id="_prodClassCdNm" name="prodClassCdNm" class="sb-input fl w70 ml5" style="font-size: 13px;"
                 ng-model="prodClassInfo.prodClassCdNm"
                 ng-click="popUpProdClass()"
                 placeholder="상품분류 선택" ng-readonly="true">
        </div>
        <div class="updownSet mt5">
            <%--<span class="fl bk lh30 s14 ml5">&nbsp;&nbsp;&nbsp;<s:message code="touchKey.grid.prodNm"/> :</span>--%>
            <input type="text" id="_prodNm" name="prodNm" class="sb-input fl w70 ml5" style="font-size: 13px;" ng-model="prodClassInfo.prodNm" placeholder="상품명">
            <button class="btn_skyblue fl ml5" id="btnSearch">
                <s:message code="cmm.search" />
            </button>
        </div>
      </div>
      <%--위즈모 테이블--%>
      <div class="cfgWrap2" style="height:461px;">
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
                               width="100" visible="true" align="center"></wj-flex-grid-column>
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
      <%--포스에서 1024*768 사이즈에 보이지 않아 위치변경함(스타일적용, 저장)--%>
      <div class="updownSet oh mb10">
        <div class="fl txtIn">
          <div class="sb-select dkbr fl w120px" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2'}">style="visibility: hidden"</c:when><c:otherwise></c:otherwise></c:choose>>
            <div id="selectStyle" ng-model="selectStyle"></div>
          </div>
          <button class="btn_skyblue fl ml5" id="btnApplyStyle" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="touchKey.applyStyle"/></button>
          <button class="btn_skyblue fl ml20" id="btnSave" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="cmm.save"/></button>
          <%--포스에서 1024*768 사이즈에 보이지 않아 위치변경함(초기화, 삭제)--%>
          <div id="keyStyleAd" class="fl hideNav" style="margin-left : 4px;">
              <button class="btn_skyblue mb5" id="btnReset" ng-click="$broadcast('btnReset')">
                  <s:message code="touchKey.reset"/></button>
              <button class="btn_skyblue" id="btnDelete" ng-click="$broadcast('btnDelete')">
                  <s:message code="touchKey.delete"/></button>
          </div>
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

  <%-- 상품분류 팝업 --%>
  <c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
  </c:import>

  <%-- 터치키 매장적용 팝업 --%>
  <c:import url="/WEB-INF/view/base/prod/touchKey/popUpTouchKeyApplyStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
  </c:import>

  <%-- 터치키복사 팝업 --%>
  <c:import url="/WEB-INF/view/base/prod/touchKey/popUpCopyTouchKey.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
  </c:import>

</div>
<%--//서브컨텐츠--%>

<script>
  var touchKeyGrpData = ${touchKeyGrp};

  // 기존 터치키 그룹이 있을 떄/ 없을 때 버튼, selectBox 설정
  if(touchKeyGrpData.length === 0){
    $("#btnSrchTouchKey").css("display", "none");  //조회버튼
    $("#btnNewGrp").css("display", "none");         //추가터치키생성버튼
    //$("#btnCancleNewGrp").css("display", "none");      //신규생성취소버튼
    $("#touchKeyGrpCombo").attr("disabled", true);  //터치키 그룹코드 콤보박스
    $("#touchKeyGrpCombo").css('background-color', '#F0F0F0'); // 터치키 그룹코드 콤보박스 (회색처리)
    $("#btnApplyStore").css("display", "none");     //터치키매장적용버튼
    $("#trTouchKeyGrp").css("display", "none");     //터치키그룹코드 콤보박스 행
    $("#trApplyStore").css("display", "none");     //터치키매장적용버튼 행

    // 터치키 저장 시 새 그룹으로 생성해 저장하겠다는 Flag
    $("#hdNewGrp").val("Y");

  }else{
    $("#btnSrchTouchKey").css("display", "");
    $("#btnNewGrp").css("display", "");
    //$("#btnCancleNewGrp").css("display", "");
    $("#touchKeyGrpCombo").attr("disabled", false);
    $("#touchKeyGrpCombo").css('background-color', '#FFFFFF');
    $("#btnApplyStore").css("display", "");
    $("#trTouchKeyGrp").css("display", "");
    $("#trApplyStore").css("display", "");

    // 터치키 저장 기본 수정 Flag로 셋팅
    $("#hdNewGrp").val("N");
  }

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
        src="/resource/vendor/wijmo/js/grid/wijmo.grid.filter.min.js?ver=520182500"
        charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/TouchKey.js?ver=20200908.001"
        charset="utf-8"></script>
