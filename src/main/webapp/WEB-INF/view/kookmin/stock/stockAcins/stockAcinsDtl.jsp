<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<wj-popup id="wjStockAcinsDtlLayer" control="wjStockAcinsDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
    <div id="acinsDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="stockAcinsDtlCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="stockAcins.reg.registTitle"/>&nbsp;&nbsp;<span id="registSubTitle"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 700px;">

            <form name="myForm" novalidate>
                <table class="tblType01" style="position: relative;">
                    <colgroup>
                        <col class="w10"/>
                        <col class="w20"/>
                        <col class="w10"/>
                        <col class="w20"/>
                        <col class="w10"/>
                        <col class="w20"/>
                    </colgroup>
                    <tbody>
                        <tr>
                            <%-- 거래처선택 --%>
                            <th><s:message code="cmm.vendr.select"/></th>
                            <td colspan="2">
                                <%-- 거래처선택 모듈 싱글 선택 사용시 include
                                     param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                                  closeFunc - 팝업 닫기시 호출할 함수
                                --%>
                                <jsp:include page="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/acquireSelectVendr.jsp" flush="true">
                                    <jsp:param name="targetId" value="stockAcinsDtlVendr"/>
                                    <jsp:param name="vendrFg" value="1"/>
                                </jsp:include>
                                <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
                            </td>
                            <%-- 매장선택 --%>
                            <th><s:message code="cmm.store.select"/></th>
                            <td colspan="2">
                                <%-- 매장선택 모듈 사용시 include --%>
                                <jsp:include page="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/acquireSelectStore.jsp" flush="true">
                                    <jsp:param name="targetTypeFg" value="S"/>
                                    <jsp:param name="targetId" value="stockAcinsDtlStore"/>
                                </jsp:include>
                                <%--// 매장선택 모듈 사용시 include --%>
                            </td>
                        </tr>
                        <tr>
                            <th><s:message code="stockAcins.dtl.acinsTitle"/><em class="imp">*</em></th>
                            <td colspan="3">
                                <input type="text" id="dtlAcinsTitle" name="dtlAcinsTitle" ng-model="acinsTitle" class="sb-input w100" maxlength="33"
                                       required
                                       popover-enable="myForm.dtlAcinsTitle.$invalid"
                                       popover-placement="bottom-left"
                                       popover-trigger="'mouseenter'"
                                       uib-popover="<s:message code="acins.dtl.acinsTitle"/>은(는) 필수 입력항목 입니다."/>
                            </td>
                            <th><s:message code="stockAcins.dtl.acinsReason"/></th>
                            <td>
                              <span class="txtIn w150px sb-select fl mr5">
                                 <wj-combo-box
                                         id="acinsDtlReason"
                                         ng-model="acinsReason"
                                         items-source="_getComboData('acinsDtlReason')"
                                         display-member-path="name"
                                         selected-value-path="value"
                                         is-editable="false"
                                         initialized="_initComboBox(s)">
                                 </wj-combo-box>
                              </span>
                            </td>
                        </tr>
                        <tr <c:if test="${storageEnvstVal == '0'}">style="display: none;"</c:if> >
                            <th><s:message code="stockAcins.dtl.adjStorageCd"/><em class="imp">*</em></th>
                            <td>
                                <span class="txtIn w150px sb-select fl mr5">
                                    <wj-combo-box
                                            id="acinsDtlAdjStorageCd"
                                            ng-model="acins.dtl.adjStorageCd"
                                            items-source="_getComboData('acinsDtlAdjStorageCd')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            selected-index-changed="selectedIndexChanged(s)">
                                    </wj-combo-box>
                                </span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>

            <div class="mt10 tr">
                <ul ng-if="btnDtlSave" class="txtSty3">
                    <li class="red fl"><s:message code="stockAcins.dtl.txt1"/></li>
                </ul>

                <%-- 상품추가 --%>
                <button type="button" class="btn_skyblue ml5" id="btnDtlAddProd" ng-click="addProd()" ng-if="btnDtlAddProd">
                    <s:message code="stockAcins.dtl.addProd"/></button>
                <%-- 저장 --%>
                <button type="button" class="btn_skyblue ml5" id="btnDtlSave" ng-click="saveAcinsDtl('')" ng-if="btnDtlSave">
                    <s:message code="cmm.save"/></button>
                <%-- 확정 --%>
                <button type="button" class="btn_skyblue ml5" id="btnDtlConfirm" ng-click="confirm()" ng-if="btnDtlConfirm">
                    <s:message code="stockAcins.dtl.confirm"/></button>
            </div>

            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.lastCostUprc"/>" binding="lastCostUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.cmptCurrQty"/>" binding="cmptCurrQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.acinsQty"/>" binding="acinsQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.adjAmt"/>" binding="adjAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.adjQty"/>" binding="adjQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.acinsAmt"/>" binding="acinsAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="stockAcins.reg.acinsProdStatus"/>" binding="acinsProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
            `
            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="stockAcinsDtlCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    // [1241 창고사용여부] 환경설정값
    var storageEnvstVal = "${storageEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/stock/stockAcins/stockAcinsDtl.js?ver=20251211.01" charset="utf-8"></script>
