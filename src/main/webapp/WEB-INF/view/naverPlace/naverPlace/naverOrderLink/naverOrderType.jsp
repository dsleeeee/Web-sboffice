<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<wj-popup control="wjNaverOrderTypeLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:350px;" fade-in="false" fade-out="false">
    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
        <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

        <%-- 타이틀 영역 --%>
        <div class="link-complete-title-wrap">
            <h3 class="link-complete-title">
                매장 연동이 완료되었습니다.
            </h3>
            <h3 class="store-list-title">네이버 주문에서 이용할 주문 유형을 설정 해주세요.</h3>
        </div>

        <%-- 주문 유형 정보 섹션 --%>
        <div class="info-section mt10">
            <div class="info-section-title"><s:message code="naverOrderLink.orderTypeInfo"/></div>
            <div class="order-type-grid">
                <%-- 테이블 주문 --%>
                <div class="order-type-cell">
                    <span class="order-type-label"><s:message code="naverOrderLink.tableOrder"/></span>
                    <label class="radio-label"><input type="radio" name="tableOrder2" id="rdTableOrderY2" value="Y">사용</label>
                    <label class="radio-label"><input type="radio" name="tableOrder2" id="rdTableOrderN2" value="N"> 미사용</label>
                </div>
                <%-- 픽업 주문 --%>
                <div class="order-type-cell">
                    <span class="order-type-label"><s:message code="naverOrderLink.pickupOrder"/></span>
                    <label class="radio-label"><input type="radio" name="pickupOrder2" id="rdPickupOrderY2" value="Y"> 사용</label>
                    <label class="radio-label"><input type="radio" name="pickupOrder2" id="rdPickupOrderN2" value="N"> 미사용</label>
                </div>
            </div>
        </div>

        <div class="wj-dialog-footer">
            <button class="btn btn_blue" ng-click="saveSetting()" id="btnSaveSetting"><s:message code="naverOrderLink.saveSetting"/></button>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">

</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverOrderLink/naverOrderType.js?ver=20260423.01" charset="utf-8"></script>