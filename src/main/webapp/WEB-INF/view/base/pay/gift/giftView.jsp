<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/base/pay/gift/" />

<div class="subCon">

  <div ng-controller="giftClassCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('giftClassCtrl', 1)">
          <s:message code="cmm.search" />
        </button>
      </div>
    </div>

    <%-- 상품권분류등록 --%>
    <div class="mt10" >
      <div class="wj-TblWrapBr pd10" style="height:400px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='gift.regist.class' /></span>
          <%-- 페이지 스케일  --%>
          <wj-combo-box
                  class="w100px fl"
                  id="listScaleBox"
                  ng-model="listScale"
                  items-source="_getComboData('listScaleBox')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="initComboBox(s)"
                  visible="false">
          </wj-combo-box>
          <%--// 페이지 스케일  --%>
          <button class="btn_skyblue" id="btnClassAdd" ng-click="addRow()" ng-if="userUseYn"><s:message code='cmm.add' /></button>
          <button class="btn_skyblue" id="btnClassDel" ng-click="del()" ng-if="userUseYn"><s:message code='cmm.del' /></button>
          <button class="btn_skyblue" id="btnClassSave" ng-click="save()" ng-if="userUseYn"><s:message code='cmm.save' /></button>
        </div>
        <%-- 상품권분류등록 그리드 --%>
        <div id="giftClassGrid" class="wj-gridWrap" style="height:280px; overflow-y: hidden;">
          <wj-flex-grid
                  id="wjGridGiftClass"
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
            <wj-flex-grid-column header="<s:message code="gift.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="gift.payTypeFg"/>" binding="payTypeFg" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="gift.payClassCd"/>" binding="payClassCd" width="100" maxLength="3" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="gift.payClassNm"/>" binding="payClassNm" width="*" maxLength="20"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="gift.serNoYn"/>" binding="serNoYn" data-map="useYnDataMap" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" width="100" data-map="useYnDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.regId"/>" binding="regId" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="gift.giftCnt"/>" binding="giftCnt" visible="false"></wj-flex-grid-column>
              <c:if test="${mappingFg == '1'}">
                  <wj-flex-grid-column header="<s:message code="gift.mappingCd"/>" binding="mappingCode" data-map="mappingCdDataMap" ></wj-flex-grid-column>
              </c:if>
          </wj-flex-grid>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt10">
          <%-- id --%>
          <ul id="giftClassCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>
    </div>
  </div>
  <%-- 상품권등록  --%>
  <div class="mt10" ng-controller="giftCtrl">
    <div class="wj-TblWrapBr pd10" style="height:400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='gift.regist.gift' /> <span id="giftSubTitle"></span> </span>
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)"
                visible="false">
        </wj-combo-box>
        <button class="btn_skyblue" id="btnGiftAdd" ng-click="addRow()" ng-if="userUseYn"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnGiftDel" ng-click="del()" ng-if="userUseYn"><s:message code='cmm.del' /></button>
        <button class="btn_skyblue" id="btnGiftSave" ng-click="save()" ng-if="userUseYn"><s:message code='cmm.save' /></button>
      </div>
      <input type="hidden" id="hdPayClassCd"/>
      <%-- 상품권등록 그리드 --%>
      <div id="giftGrid" class="wj-gridWrap" style="height:280px;overflow-y: hidden;">
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
          <wj-flex-grid-column header="<s:message code="gift.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftCd"/>" binding="giftCd" width="*" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftNm"/>" binding="giftNm" maxLength="15" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.payClassCd"/>" binding="payClassCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftUprc"/>" binding="giftUprc" width="*" max-length="9"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" width="*" data-map="useYnDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cmm.regId"/>" binding="regId" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>

      <%-- 페이지 리스트 --%>
      <div class="pageNum mt10">
        <%-- id --%>
        <ul id="giftCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>

    </div>
  </div>
</div>

<script type="text/javascript">
var orgnFg       = "${orgnFg}";
var useYn        = ${ccu.getCommCodeExcpAll("067")};
var giftDcFg     = ${ccu.getCommCodeExcpAll("013")};
var giftApplyFg  = ${ccu.getCommCodeExcpAll("043")};
var baseUrl      = "${baseUrl}";
var mappingFg = "${mappingFg}";
var mappingCd   = ${ccu.getCommCodeExcpAll("801")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/pay/gift/gift.js?ver=20230309.01" charset="utf-8"></script>
