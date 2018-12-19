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
  <div ng-controller="couponClassCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('couponClassCtrl')">
          <s:message code="cmm.search" />
        </button>
      </div>
    </div>

    <%-- 쿠폰분류등록 --%>
    <div class="mb20 mt20" >
      <div class="wj-TblWrapBr mr10 pd20" style="height:300px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='coupon.regist.class' /></span>
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
       <c:if test="${orgnFg == 'HQ'}">
          <button class="btn_skyblue" id="btnClassAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
          <button class="btn_skyblue" id="btnClassSave" ng-click="save()"><s:message code='cmm.save' /></button>
       </c:if>
        </div>
        <%-- 쿠폰분류등록 그리드 --%>
        <div id="couponClassGrid" class="wj-gridWrap" style="height:180px;overflow-y: hidden;">
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
            <wj-flex-grid-column header="<s:message code="coupon.payClassCd"/>" binding="payClassCd" maxLength="3" is-read-only="true"  width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="coupon.payClassNm"/>" binding="payClassNm"  maxLength="20"  width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="coupon.serNoYn"/>" binding="serNoYn" data-map="useYnDataMap"  width="*" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" data-map="useYnDataMap"  width="*" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.regId"/>" binding="regId" visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="coupon.couponCnt"/>" binding="couponCnt" visible="false" ></wj-flex-grid-column>
          </wj-flex-grid>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="couponClassCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>
    </div>

  </div>

  <%-- 쿠폰등록  --%>
  <div class="mb40" ng-controller="couponCtrl">
    <div class="wj-TblWrapBr mr10 pd20" style="height:305px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='coupon.regist.coupon' /> <span id="couponSubTitle"></span> </span>
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
      <c:if test="${orgnFg == 'HQ'}">
        <button class="btn_skyblue" id="btnCouponAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
        <button class="btn_skyblue" id="btnCouponDel" ng-click="del()"><s:message code='cmm.del' /></button>
        <button class="btn_skyblue" id="btnCouponSave" ng-click="save()"><s:message code='cmm.save' /></button>
      </c:if>
      </div>
      <%-- 쿠폰등록 그리드 --%>
      <div id="couponGrid" class="wj-gridWrap" style="height:190px;overflow-y: hidden;">
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
          <wj-flex-grid-column header="<s:message code="coupon.coupnCd"/>" binding="coupnCd" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnNm"/>" binding="coupnNm" maxLength="15" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.payClassCd"/>" binding="payClassCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnDcFg"/>" binding="coupnDcFg" data-map="coupnDcFgDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnDcRate"/>" binding="coupnDcRate" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnDcAmt"/>" binding="coupnDcAmt" maxLength="5" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.coupnApplyFg"/>" binding="coupnApplyFg"  data-map="coupnApplyFgDataMap" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="coupon.prodCnt"/>" binding="prodCnt" is-read-only="true" align="center"></wj-flex-grid-column>

          <c:if test="${orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="coupon.storeCnt"/>" binding="storeCnt" is-read-only="true" width="*" align="center"></wj-flex-grid-column>
          </c:if>

          <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" width="*" data-map="useYnDataMap" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cmm.regId"/>" binding="regId" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>

      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="couponCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>

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
<script type="text/javascript" src="/resource/solbipos/js/base/pay/coupon/coupon.js?ver=20180813.01" charset="utf-8"></script>

<%-- 쿠폰별 상품 등록 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/pay/coupon/couponProdView.jsp">
  <c:param name="coupnEnvstVal" value="${coupnEnvstVal}"/>
</c:import>


<%-- 쿠폰별 매장 등록 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/pay/coupon/couponStoreView.jsp">
</c:import>
