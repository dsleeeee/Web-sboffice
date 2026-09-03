<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 우편번호 찾기 팝업 --%>
<%-- 선택한 주소를 부모창에 바인딩 하기 위해, 각 화면마다 구분자를 지정하여 element id명을 파악한다. --%>
<%-- jsp:param 방식은 API 호출 시, 파라미터 사용을 불허하기 때문에 호출이 거부됨. --%>
<%--<input type="hidden" id="pageNm" value="naverOrderInfo" />--%>
<%@ include file="/WEB-INF/view/application/layer/searchAddr.jsp" %>

<wj-popup control="wjNaverOrderInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:780px;height:250px;" fade-in="false" fade-out="false">
    <div ng-controller="naverOrderInfoCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <a href="#" class="wj-hide btn_close" ng-click="closeServiceType()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">

            <%-- 연동 정보 섹션 --%>
            <%--<div class="info-section">
                <div class="info-section-title"><s:message code="naverOrderLink.linkInfo"/></div>
                <div class="info-grid">
                    <div class="info-row">
                        &lt;%&ndash; 매장명 &ndash;%&gt;
                        <div class="info-cell">
                            <label class="info-label"><s:message code="naverOrderLink.storeNm"/></label>
                            <input type="text" class="info-input" readonly="readonly" id="storeNm">
                        </div>
                        &lt;%&ndash; 비즈니스 아이디 &ndash;%&gt;
                        <div class="info-cell">
                            <label class="info-label"><s:message code="naverOrderLink.businessId"/></label>
                            <input type="text" class="info-input" readonly="readonly" id="businessId">
                        </div>
                    </div>
                    <div class="info-row">
                        &lt;%&ndash; 서비스 명 &ndash;%&gt;
                        <div class="info-cell">
                            <label class="info-label"><s:message code="naverOrderLink.serviceNm"/><span
                                    class="info-label-sub">(네이버 주문 노출)</span></label>
                            <input type="text" class="info-input" id="serviceNm">
                        </div>
                        &lt;%&ndash; 매장 전화 번호 &ndash;%&gt;
                        <div class="info-cell">
                            <label class="info-label"><s:message code="naverOrderLink.phone"/></label>
                            <input type="text" class="info-input"id="phoneNo">
                        </div>
                    </div>
                    <div class="info-row">
                        &lt;%&ndash; 매장 주소 &ndash;%&gt;
                        <div class="info-cell info-cell-full">
                            <label class="info-label"><s:message code="naverOrderLink.address"/></label>
                            <div style="display:flex; gap:6px; margin-bottom:6px;">
                                <input type="text" class="info-input" readonly="readonly" id="addr" style="flex:1;">
                                <button type="button" class="btn_skyblue" onclick="searchAddr()" style="white-space:nowrap;">주소찾기</button>
                            </div>
                            <input type="text" class="info-input" id="addrDtl">
                            <input type="hidden" class="info-input" id="postNo" >
                        </div>
                    </div>
                </div>
            </div>--%>

            <%-- 주문 유형 정보 섹션 --%>
            <div class="info-section mt10">
                <div class="info-section-title"><s:message code="naverOrderLink.orderTypeInfo"/></div>
                <div class="order-type-grid">
                    <%-- 테이블 주문 --%>
                    <div class="order-type-cell">
                        <span class="order-type-label"><s:message code="naverOrderLink.tableOrder"/></span>
                        <label class="radio-label"><input type="radio" name="tableOrder" id="rdTableOrderY" value="Y"><s:message code="naverOrderLink.exposeY" /></label>
                        <label class="radio-label"><input type="radio" name="tableOrder" id="rdTableOrderN" value="N"><s:message code="naverOrderLink.exposeN" /></label>
                    </div>
                    <%-- 픽업 주문 --%>
                    <div class="order-type-cell">
                        <span class="order-type-label"><s:message code="naverOrderLink.pickupOrder"/></span>
                        <label class="radio-label"><input type="radio" name="pickupOrder" id="rdPickupOrderY" value="Y"><s:message code="naverOrderLink.exposeY" /></label>
                        <label class="radio-label"><input type="radio" name="pickupOrder" id="rdPickupOrderN" value="N"><s:message code="naverOrderLink.exposeN" /></label>
                    </div>
                </div>
            </div>

            <div class="wj-dialog-footer">
                <button class="btn btn_blue" ng-click="saveServiceType()"><s:message code="cmm.save"/></button>
                <button class="btn wj-hide btn_gray" ng-click="closeInfo()"><s:message code="cmm.cancel"/></button>
            </div>

        </div>
    </div>
</wj-popup>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverOrderLink/naverOrderInfo.js?ver=20260831.01" charset="utf-8"></script>
