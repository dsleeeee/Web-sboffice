<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="storeProdBatchRegistLayer" control="storeProdBatchRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="regProdCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.title.storeProdBatch"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <th>적용대상매장</th>
                    <td colspan="3" id="storeTitle"></td>
                    <input type="hidden" id="hdHqOfficeCd"/>
                    <input type="hidden" id="hdStoreCd"/>
                    <input type="hidden" id="hdStoreNm"/>
                </tr>
                <tr>
                    <th><s:message code="prod.prodCd"/></th>
                    <td><input type="text" id="srchBatProdCd" ng-model="prodCd" /></td>
                    <th><s:message code="prod.prodNm"/></th>
                    <td><input type="text" id="srchBatProdNm" ng-model="prodNm" /></td>
                </tr>
                <c:if test="${subPriceFg == '1'}">
                    <tr>
                        <th><input type="checkbox" id="storeSaleUprcApply" ng-model="storeSaleUprcApply"/> <s:message code="salePrice.batchChange"/></th>
                        <td><s:message code="salePrice.saleUprcApply"/></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
            <%-- 조회 --%>
            <div class="mt10 tr">
                <button class="btn_skyblue" id="btnBatchSearch" ng-click="_pageView('regProdCtrl', 1)" ><s:message code="cmm.search" /></button>
            </div>
            <%--- 적용상품 그리드 --%>
            <div class="oh mt20">
                <div class="w50 fl">
                    <div class="wj-TblWrap mr10" style="height:395px; overflow-y:hidden;">
                        <div class="oh">
                            <span class="fl bk lh20 s14"><s:message code="prod.regProd"/></span>
                            <span class="fr">
                                <a href="#" class="btn_grayS2" ng-click="changeSaleUprc()"><s:message code="prod.change.saleUprc" /></a>
                                <a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a>
                            </span>
                        </div>
                        <div id="regProdGrid" class="mt10" style="height: 370px; overflow-y: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true"  align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="*" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="70" align="right" ></wj-flex-grid-column>
                                <c:if test="${subPriceFg == '1'}">
                                    <wj-flex-grid-column header="<s:message code="prod.stinSaleUprc"/>" binding="stinSaleUprc" width="70" align="right" ></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="70" align="right" ></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.packSaleUprc"/>" binding="packSaleUprc" width="70" align="right" ></wj-flex-grid-column>
                                </c:if>
                                <wj-flex-grid-column header="<s:message code="prod.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>

                                <wj-flex-grid-column header="<s:message code="prod.saleUprcB"/>" binding="saleUprcB" visible="false" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.stinSaleUprc"/>" binding="stinSaleUprcB" visible="false" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.dlvrSaleUprc"/>" binding="dlvrSaleUprcB" visible="false" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.packSaleUprc"/>" binding="packSaleUprcB" visible="false" ></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
                <%--- 미적용상품 그리드 --%>
                <div class="w50 fr" ng-controller="noRegProdCtrl">
                    <div class="wj-TblWrap ml10" style="height:395px; overflow-y: hidden;" >
                        <div class="oh">
                            <span class="fl bk lh20 s14"><s:message code="prod.noRegProd"/></span>
                            <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="prod.regist"/></a></span>
                        </div>
                        <div id="noRegProdGrid" class="mt10" style="height: 370px; overflow-y: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="*" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                                <c:if test="${subPriceFg == '1'}">
                                    <wj-flex-grid-column header="<s:message code="prod.stinSaleUprc"/>" binding="stinSaleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.packSaleUprc"/>" binding="packSaleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                                </c:if>
                                <wj-flex-grid-column header="<s:message code="prod.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.saleUprcB"/>" binding="saleUprcB" visible="false" ></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script>
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
    // 내점/배달/포장 가격관리 사용여부 (0: 미사용 1: 사용)
    var subPriceFg = "${subPriceFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/storeProdBatchRegist.js?ver=20210526.03" charset="utf-8"></script>