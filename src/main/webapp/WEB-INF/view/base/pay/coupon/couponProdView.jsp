<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
 * 본사 권한 : 쿠폰, 쿠폰적용상품, 쿠폰적용매장
 * 매장 권한 : 쿠폰, 쿠폰적용상품
 //TODO 환경변수값 이용해서 쿠폰등록을 본사에서 컨트롤하는지, 매장에서 컨트록하는지, 둘다 하는지 받아야 함
--%>
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/base/pay/coupon/" />

<div class="subCon">

  <%-- 탭 (본사에서만 보임) --%>
  <c:if test="${orgnFg == 'HQ'}">
    <ul class="subTab mb20">
      <%-- 쿠폰등록 --%>
      <li><a href="javascript:;" id="couponRegTab"><s:message code='coupon.regist.coupon' /></a></li>
      <%-- 쿠폰적용상품 등록 --%>
      <li><a href="javascript:;" id="couponProdTab" class="on"><s:message code='coupon.regist.product' /></a></li>
      <%-- 쿠폰적용매장 등록 --%>
      <li><a href="javascript:;" id="couponStoreTab"><s:message code='coupon.regist.store' /></a></li>
    </ul>
  </c:if>

  <%-- 쿠폰 //TODO 페이징 --%>
  <div class="wj-TblWrap mb40">
    <div class="wj-TblWrapBr mr10 pd20" style="height:230px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='coupon.regist.coupon' /></span>
        <button class="btn_skyblue" id="btnCouponAdd"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnCouponDel"><s:message code='cmm.del' /></button>
        <button class="btn_skyblue" id="btnCouponSave"><s:message code='cmm.save' /></button>
      </div>
      <%-- 쿠폰등록 그리드 --%>
      <div id="couponGrid" style="height:190px"></div>
    </div>
  </div>

  <%-- 상품 --%>
  <div class="wj-TblWrap mt20 oh">
    <%-- 등록 상품--%>
    <div class="w50 fl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:500px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30">등록 상품</span>
          <button class="btn_skyblue">등록해제</button>
        </div>
        <%-- 등록 상품 그리드 --%>
        <div id="gridRegistProd"></div>
      </div>
    </div>

    <%-- 미등록 상품 --%>
    <div class="w50 fr">
      <div class="wj-TblWrapBr ml10 pd20" style="height:500px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30">미등록 상품</span>
          <button class="btn_skyblue">등록</button>
        </div>
        <%-- 미등록 상품 그리드 --%>
        <div id="gridNoRegistProd"></div>
      </div>
    </div>
  </div>

</div>

<script type="text/javascript">
var useYn        = ${ccu.getCommCodeExcpAll("067")};
var coupnDcFg    = ${ccu.getCommCodeExcpAll("013")};
var coupnApplyFg = ${ccu.getCommCodeExcpAll("043")};
var baseUrl      = "${baseUrl}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/pay/coupon/couponProd.js?ver=20180817" charset="utf-8"></script>
