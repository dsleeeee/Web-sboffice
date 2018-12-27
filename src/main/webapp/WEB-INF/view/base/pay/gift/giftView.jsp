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
        <button class="btn_blue fr" id="btnSearch" ng-click="searchGiftClass()">
          <s:message code="cmm.search" />
        </button>
      </div>
    </div>

    <%-- 상품권분류등록 --%>
    <div class="mb20 mt20" >
      <div class="wj-TblWrapBr mr10 pd20" style="height:450px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='gift.regist.class' /></span>
          <button class="btn_skyblue" id="btnClassAdd"    ng-click="addRow()" ng-if="userUseYn"><s:message code='cmm.add' /></button>
          <%--<button class="btn_skyblue" id="btnClassDel" ng-click="del()"><s:message code='cmm.del' /></button>--%>
          <button class="btn_skyblue" id="btnClassSave"   ng-click="save()" ng-if="userUseYn"><s:message code='cmm.save' /></button>
        </div>
        <%-- 상품권분류등록 그리드 --%>
        <div id="giftClassGrid" class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter">

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
          </wj-flex-grid>
        </div>
      </div>
    </div>
  </div>
  <%-- 상품권등록  --%>
  <div class="mb40" ng-controller="giftCtrl">
    <div class="wj-TblWrapBr mr10 pd20" style="height:450px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='gift.regist.gift' /> <span id="giftSubTitle"></span> </span>
        <button class="btn_skyblue" id="btnGiftAdd" ng-click="addRow()" ng-if="userUseYn"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnGiftDel" ng-click="del()" ng-if="userUseYn"><s:message code='cmm.del' /></button>
        <button class="btn_skyblue" id="btnGiftSave" ng-click="save()" ng-if="userUseYn"><s:message code='cmm.save' /></button>
      </div>
      <%-- 상품권등록 그리드 --%>
      <div id="giftGrid" class="wj-gridWrap" style="height:370px;overflow-y: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftCd"/>" binding="giftCd" width="*" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftNm"/>" binding="giftNm" maxLength="15" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.payClassCd"/>" binding="payClassCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="gift.giftUprc"/>" binding="giftUprc" width="*" maxLength="8"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" width="*" data-map="useYnDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cmm.regId"/>" binding="regId" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>

      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
var orgnFg       = "${orgnFg}";
var useYn        = ${ccu.getCommCodeExcpAll("067")};
var giftDcFg     = ${ccu.getCommCodeExcpAll("013")};
var giftApplyFg  = ${ccu.getCommCodeExcpAll("043")};
var baseUrl      = "${baseUrl}";

</script>
<script type="text/javascript" src="/resource/solbipos/js/base/pay/gift/gift.js?ver=20180813.01" charset="utf-8"></script>
