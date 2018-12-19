<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/base/pay/mCoupon/" />

<div class="subCon">


  <div class="wj-dialog-body" ng-controller="mCouponClassCtrl">
    <div class="searchBar flddUnfld">
     <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="btnSearch" ng-click="searchMobileCouponClass()">
          <s:message code="cmm.search" />
        </button>
      </div>
    </div>

    <div class="mt40 oh dkbr">
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
                ng-hide="true">
        </wj-combo-box>


      <div class="updownSet oh mb10">
        <button class="btn_skyblue" id="btnClassAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnClassSave" ng-click="save()"><s:message code='cmm.save' /></button>
      </div>
    </div>

    <%-- 상품권분류등록 --%>
      <div class="wj-TblWrapBr mt10" style="height:500px;">
        <%-- 상품권분류등록 그리드 --%>
        <div id="mCouponClassGrid" class="wj-gridWrap" style="height:380px; overflow-y: hidden;">
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
            <wj-flex-grid-column header="<s:message code="mobileCoupon.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobileCoupon.payTypeFg"/>" binding="payTypeFg" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobileCoupon.payClassCd"/>" binding="payClassCd" width="200" maxLength="3" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobileCoupon.payClassNm"/>" binding="payClassNm" width="*" maxLength="20"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobileCoupon.serNoYn"/>" binding="serNoYn" width="150" data-map="useYnDataMap" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" width="150" data-map="useYnDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.regId"/>" binding="regId" visible="false"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="mCouponClassCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>

      </div>
    <%--</div>--%>
  </div>
</div>

<script type="text/javascript">
var orgnFg = "${orgnFg}";
var useYn  = ${ccu.getCommCodeExcpAll("067")};
var mobileCouponDcFg     = ${ccu.getCommCodeExcpAll("013")};
var mobileCouponApplyFg  = ${ccu.getCommCodeExcpAll("043")};
var baseUrl = "${baseUrl}";

</script>
<script type="text/javascript" src="/resource/solbipos/js/base/pay/mCoupon/mobileCoupon.js?ver=20180813.01" charset="utf-8"></script>
