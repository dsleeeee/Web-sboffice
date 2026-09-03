<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<wj-popup control="wjNaverOrderTypeLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:550px;height:450px;" fade-in="false" fade-out="false">
    <div ng-controller="naverOrderTypeCtrl">

        <%-- body --%>
        <div class="wj-dialog-body">

            <%-- 타이틀 영역 --%>
            <div class="link-complete-title-wrap pdt20">
                <h3 class="link-complete-title">
                    매장 연동이 완료되었습니다.
                </h3>
                <h3 class="store-list-title">네이버 주문의 노출여부를 설정 해주세요.</h3>
            </div>

            <%-- 주문 유형 정보 섹션 --%>
            <div class="order-type-popup">
                <div class="order-type-popup-box">
                    <div class="order-type-popup-title"><s:message code="naverOrderLink.orderTypeInfo"/></div>
                    <%-- 테이블 주문 --%>
                    <div class="order-type-popup-row">
                        <span class="order-type-popup-label"><s:message code="naverOrderLink.tableOrder"/></span>
                        <span class="order-type-popup-radios">
                            <label class="radio-label"><input type="radio" name="tableOrder2" id="rdTableOrderY2" value="Y"><s:message code="naverOrderLink.exposeY" /></label>
                            <label class="radio-label"><input type="radio" name="tableOrder2" id="rdTableOrderN2" value="N"><s:message code="naverOrderLink.exposeN" /></label>
                        </span>
                    </div>
                    <%-- 픽업 주문 --%>
                    <div class="order-type-popup-row">
                        <span class="order-type-popup-label"><s:message code="naverOrderLink.pickupOrder"/></span>
                        <span class="order-type-popup-radios">
                            <label class="radio-label"><input type="radio" name="pickupOrder2" id="rdPickupOrderY2" value="Y"><s:message code="naverOrderLink.exposeY" /></label>
                            <label class="radio-label"><input type="radio" name="pickupOrder2" id="rdPickupOrderN2" value="N"><s:message code="naverOrderLink.exposeN" /></label>
                        </span>
                    </div>
                </div>
            </div>

            <div class="wj-dialog-footer">
                <button class="action-btn1 mt30" ng-click="saveSetting()" id="btnSaveSetting"><s:message code="naverOrderLink.saveSetting"/></button>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">

</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverOrderLink/naverOrderType.js?ver=20260831.01" charset="utf-8"></script>

<style>
    .order-type-popup {
        margin-top: 10px;
    }

    .order-type-popup-title {
        font-size: 0.95rem;
        font-weight: 700;
        color: #212529;
        text-align: center;
        padding: 14px 0;
        border-bottom: 1px solid #e9ecef;
    }

    .order-type-popup-box {
        border: 1px solid #d0d0d0;
        border-radius: 6px;
        background: #ffffff;
        padding: 0 20px;
    }

    .order-type-popup-row {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 14px 0;
    }

    .order-type-popup-row + .order-type-popup-row {
        border-top: 1px solid #e9ecef;
    }

    .order-type-popup-label {
        font-size: 0.9rem;
        font-weight: 700;
        color: #212529;
    }

    .order-type-popup-radios {
        display: flex;
        align-items: center;
        gap: 20px;
    }
</style>