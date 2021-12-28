<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="promotionClassRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="promotionClassRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="promotion.classAdd" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
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
                    <th><s:message code="promotion.classCd" /></th>
                    <td>
                        <input type="text" id="srchClassCd" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                    <th><s:message code="promotion.classNm" /></th>
                    <td>
                        <input type="text" id="srchClassNm" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <%-- 버튼영역 --%>
            <div class="mt10 tr">
                <button class="btn_skyblue" id="nxBtnSearch2" ng-click="btnSearchClass()"><s:message code="cmm.search" /></button>
                <button class="btn_skyblue ml5 fr" id="btnInsertClass" ng-click="btnInsertClass()"><s:message code="cmm.add"/></button>
            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGridPromotionClassReg">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="promotion.classCd"/>" binding="prodClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="promotion.classNm"/>" binding="prodClassNm" width="300" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="promotion.prodQty"/>" binding="prodQty" width="70" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>

        <%--구매대상 선택값 --%>
        <input type="hidden" id="hdSelectProdDs2" />
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotion/promotionClassReg.js?ver=20210423.04" charset="utf-8"></script>