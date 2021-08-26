<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="setConfigProdLayer" control="setConfigProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:850px;">
    <div class="wj-dialog wj-dialog-columns title">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.title.setConfigProd"/>
            <a href="#" class="wj-hide btn_close" onclick="closePop()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="oh">
                <%-- 구성내역 --%>
                <div class="w55 fl" ng-controller="setConfigProdCtrl">
                    <div class="wj-TblWrap mr10" style="height:420px; overflow-y:hidden;">
                        <span class="fl bk lh20 s14"><s:message code="prod.configList"/></span>
                        <div class="updownSet oh pdt5">
                            <div id="divSetConfigProdBtn">
                                <%-- UP --%>
                                <button class="btn_up" ng-click="rowMoveUp()"><s:message code="cmm.up" /></button>
                                <%-- DOWN --%>
                                <button class="btn_down" ng-click="rowMoveDown()"><s:message code="cmm.down" /></button>
                                <%-- 삭제 --%>
                                <button class="btn_skyblue" ng-click="delProd()"><s:message code='cmm.del' /></button>
                                <%-- 저장 --%>
                                <button class="btn_skyblue" ng-click="saveProd()"><s:message code="cmm.save" /></button>
                            </div>
                        </div>
                        <div id="regStoreGrid" class="mt10" style="height: 360px; overflow-y: hidden;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                                <%--<wj-flex-grid-column header="<s:message code="prod.dispSeq"/>" binding="dispSeq" width="40" is-read-only="true" align="center"></wj-flex-grid-column>--%>
                                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="unitProdCd" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.qty"/>" binding="unitProdQty" width="40" align="right"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.unit"/>" binding="poUnitFg" width="40" is-read-only="true" align="center" data-map="poUnitFgDataMap"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.costUprc"/>" binding="costUprc" width="60" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
                <%-- 상품검색 --%>
                <div class="w45 fr" ng-controller="srchSetConfigProdCtrl">
                    <div class="wj-TblWrap ml10" style="height:420px; overflow-y: hidden;" >
                        <table class="tblType01" id="tblSrchProd">
                            <colgroup>
                                <col class="w20" />
                                <col class="w30" />
                                <col class="w20" />
                                <col class="w30" />
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th><s:message code="prod.prodNm" /></th><%--상품명--%>
                                    <td colspan="2" >
                                        <input type="text" class="sb-input w100" ng-model="prodNm" />
                                    </td>
                                    <td align="right">
                                        <button class="btn_skyblue" ng-click="_pageView('srchSetConfigProdCtrl', 1)">
                                            <s:message code="cmm.search" />
                                        </button>
                                        <button class="btn_skyblue" ng-click="regProd()">
                                            <s:message code="prod.regist" />
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div id="noRegStoreGrid" class="mt10" style="height: 360px; overflow-y: hidden;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="unitProdCd" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.unit"/>" binding="poUnitFg" width="40" is-read-only="true" align="center" data-map="poUnitFgDataMap"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%-- 닫기 버튼 --%>
        <div class="tc">
            <button class="btn wj-hide btn_blue" onclick="closePop()"></a>
                <s:message code="cmm.close" />
            </button>
        </div>
        <input type="hidden" id="hdSetConfigProdCd" />
        <input type="hidden" id="hdSetConfigSetProdFg" />
    </div>
</wj-popup>

<script type="text/javascript">
    var poUnitFgData = ${ccu.getCommCodeExcpAll("093")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/setConfigProdRegist.js?ver=20210826.03" charset="utf-8"></script>