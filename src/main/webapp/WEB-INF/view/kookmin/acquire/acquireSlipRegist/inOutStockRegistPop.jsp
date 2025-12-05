<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<wj-popup id="wjInOutStockRegistPopLayer" control="wjInOutStockRegistPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
    <div id="vendrInstockPopLayer" class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="popTitle" class="s16 txtIn"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 700px;">
            <div ng-controller="inOutStockRegistCtrl">
                <%-- 입고/반출정보, 입고/반출상품, 반출서 탭 --%>
                <div class="tabType1">
                    <ul>
                        <%-- 입고/반출정보 탭 --%>
                        <li>
                            <a id="inOutStockDtlTab" href="#" class="on" ng-click="inOutStockDtlShow()" ng-if="inOutStockDtlShowFg" ng-bind-html="inOutStockDtlTab"></a>
                        </li>
                        <%-- 입고/반출상품 탭 --%>
                        <li>
                            <a id="inOutStockProdTab" href="#" ng-click="inOutStockProdShow()" ng-if="inOutStockProdShowFg" ng-bind-html="inOutStockProdTab"></a>
                        </li>
                        <%-- 반출서 탭 --%>
                        <li>
                            <a id="inOutStockReportTab" href="#" ng-click="inOutStockReportShow()" ng-if="inOutStockReportShowFg" ng-bind-html="inOutStockReportTab"></a>
                        </li>
                    </ul>
                </div>
            </div>


            <script type="text/javascript" src="/resource/solbipos/js/kookmin/acquire/acquireSlipRegist/inOutStockRegistPop.js?ver=20251121.01" charset="utf-8"></script>

            <%-- 입고/반출정보 레이어 --%>
            <c:import url="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/inOutStockDtl.jsp">
                <c:param name="menuCd" value="${menuCd}"/>
                <c:param name="menuNm" value="${menuNm}"/>
            </c:import>


            <%-- 상품 레이어 --%>
            <c:import url="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/inOutStockProd.jsp">
                <c:param name="menuCd" value="${menuCd}"/>
                <c:param name="menuNm" value="${menuNm}"/>
            </c:import>

            <%-- 반출서 레이어 --%>
            <c:import url="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/inOutStockReport.jsp">
                <c:param name="menuCd" value="${menuCd}"/>
                <c:param name="menuNm" value="${menuNm}"/>
            </c:import>

        </div>
    </div>
</wj-popup>
