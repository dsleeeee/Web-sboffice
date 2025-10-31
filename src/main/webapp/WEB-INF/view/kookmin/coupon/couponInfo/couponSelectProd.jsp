<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="couponSelectProdLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:550px;">
    <div class="wj-dialog wj-dialog-columns title">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="cmm.prod.select" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" ng-controller="couponSelectProdCtrl">
            <div class="w100">
                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장코드 --%>
                        <th><s:message code="couponInfo.prodCd" /></th>
                        <td>
                            <input type="text" id="srchSelectProdCd" ng-model="srchSelectProdCd"/>
                        </td>
                        <%-- 매장명 --%>
                        <th><s:message code="couponInfo.prodCd" /></th>
                        <td>
                            <input type="text" id="srchSelectProdNm" ng-model="srchSelectProdNm"/>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <td colspan="4">
                            <%-- 버튼영역 --%>
                            <div class="tr">
                                <div>
                                    <%-- 조회 --%>
                                    <button class="btn_skyblue ml5 fr" ng-click="searchProd()"><s:message code="cmm.search" /></button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%--위즈모 테이블--%>
                <div class="theGrid mt10" style="height: 400px;">
                    <wj-flex-grid
                            id="wjGrid"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="couponInfo.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/coupon/couponInfo/couponSelectProd.js?ver=20251022.01" charset="utf-8"></script>
