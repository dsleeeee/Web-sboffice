<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
 * 본사 권한 : 쿠폰, 쿠폰적용상품, 쿠폰적용매장
 * 매장 권한 : 쿠폰, 쿠폰적용상품
--%>
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/base/pay/coupon/" />

<div class="subCon">

  <%-- 탭  --%>
  <ul class="subTab mb20" style="display: none;">
    <%-- 쿠폰등록 --%>
    <li><a href="javascript:;" id="couponRegTab" class="on" ng-click="_broadcast('couponClassCtrl', true)"><s:message code='coupon.regist.coupon' /></a></li>
    <%-- 쿠폰적용상품 등록 --%>
    <li><a href="javascript:;" id="couponProdTab"><s:message code='coupon.regist.product' /></a></li>
    <%-- 쿠폰적용매장 등록 (본사에서만 보임) --%>
    <c:if test="${orgnFg == 'HQ'}">
      <li><a href="javascript:;" id="couponStoreTab"><s:message code='coupon.regist.store' /></a></li>
    </c:if>
  </ul>

  <%-- 쿠폰분류등록 --%>
  <div class="mb40" ng-controller="couponClassCtrl">
    <div class="wj-TblWrapBr mr10 pd20" style="height:260px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='coupon.regist.class' /></span>
        <button class="btn_skyblue" id="btnClassAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnClassDel" ng-click="del()"><s:message code='cmm.del' /></button>
        <button class="btn_skyblue" id="btnClassSave" ng-click="save()"><s:message code='cmm.save' /></button>
      </div>
      <%-- 쿠폰분류등록 그리드 --%>
      <div id="couponClassGrid" class="wj-gridWrap" style="height:190px">
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
          <wj-flex-grid-column header="<s:message code="coupon.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.payTypeFg"/>" binding="payTypeFg" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.payClassCd"/>" binding="payClassCd" width="200" maxLength="3" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.payClassNm"/>" binding="payClassNm" width="*" maxLength="20"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.serNoYn"/>" binding="serNoYn" width="150" data-map="useYnDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" width="150" data-map="useYnDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cmm.regId"/>" binding="regId" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="representCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
  </div>

  <%-- 쿠폰등록  --%>
  <div class="mb40" ng-controller="couponCtrl">
    <div class="wj-TblWrapBr mr10 pd20" style="height:250px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='coupon.regist.coupon' /> <span id="couponSubTitle"></span> </span>
        <button class="btn_skyblue" id="btnCouponAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnCouponDel" ng-click="del()"><s:message code='cmm.del' /></button>
        <button class="btn_skyblue" id="btnCouponSave" ng-click="save()"><s:message code='cmm.save' /></button>
      </div>
      <%-- 쿠폰등록 그리드 --%>
      <div id="couponGrid" style="height:400px">

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
          <wj-flex-grid-column header="<s:message code="coupon.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnCd"/>" binding="coupnCd" maxLength="4" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnNm"/>" binding="coupnNm" maxLength="15" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.payClassCd"/>" binding="payClassCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnDcFg"/>" binding="coupnDcFg" width="*" data-map="coupnDcFgDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnDcRate"/>" binding="coupnDcRate" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnDcAmt"/>" binding="coupnDcAmt" maxLength="5" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnApplyFg"/>" binding="coupnApplyFg" width="*" data-map="coupnApplyFgDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.prodCnt"/>" binding="prodCnt" isReadOnly=true width="*"></wj-flex-grid-column>

          <c:if test="${orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="coupon.storeCnt"/>" binding="storeCnt" isReadOnly=true width="*"></wj-flex-grid-column>
          </c:if>

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
var coupnDcFg    = ${ccu.getCommCodeExcpAll("013")};
var coupnApplyFg = ${ccu.getCommCodeExcpAll("043")};
var baseUrl      = "${baseUrl}";
var coupnEnvstVal = "${coupnEnvstVal}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/pay/coupon/coupon.js?ver=20180813" charset="utf-8"></script>

<%-- 쿠폰별 상품 등록 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/pay/coupon/couponProdView.jsp">
</c:import>


<%-- 쿠폰별 매장 등록 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/pay/coupon/couponStoreView.jsp">
</c:import>
