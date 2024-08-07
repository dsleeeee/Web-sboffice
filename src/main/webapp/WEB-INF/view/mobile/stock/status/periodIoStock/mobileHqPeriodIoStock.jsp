<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileHqPeriodIoStockCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="periodIostock.periodIostock"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileHqPeriodIoStockCtrl')"><s:message code="cmm.search"/></button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w30"/>
            <col class="w20"/>
            <col class="w30"/>
        </colgroup>
        <%-- 조회일자 --%>
        <tr>
            <th><s:message code="periodIostock.srchDate" /></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchClassStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchClassEndDate" class="w110px"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="periodIostock.prodCd" /></th>
            <td>
                <input type="text" id="srchProdCd" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="periodIostock.prodNm" /></th>
            <td>
                <input type="text" id="srchProdNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 바코드 --%>
            <th><s:message code="periodIostock.barcdCd" /></th>
            <td>
                <input type="text" id="srchBarcdCd" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 단위구분 --%>
            <th><s:message code="periodIostock.unitFg" /></th>
            <td>
                <div class="sb-select">
				<span class="txtIn w120px">
					<wj-combo-box
                            id="srchUnitFg"
                            ng-model="unitFgModel"
                            items-source="_getComboData('srchUnitFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
					</wj-combo-box>
				</span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 분류 --%>
            <th><s:message code="periodIostock.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w60" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                       placeholder="<s:message code="cmm.all" />" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCdModel" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 조회옵션 --%>
            <th><s:message code="periodIostock.srchOption" /></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w120px">
                        <wj-combo-box
                                id="srchSrchOption"
                                ng-model="srchOption"
                                items-source="_getComboData('srchSrchOption')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                    <span class="chk ml5">
                     <input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                     <label for="chkDt">
                         <s:message code="periodIostock.prodClassDisplay" />
                     </label>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 거래처 --%>
            <th style="display: none;"><s:message code="periodIostock.vendr" /></th>
            <td style="display: none;">
                <%-- 거래처선택 모듈 멀티 선택 사용시 include
                param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                --%>
                <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
                    <jsp:param name="targetId" value="periodIostockSelectVendr"/>
                </jsp:include>
                <input type="hidden" id="periodIostockSelectVendrCd" value=""/>
            </td>
        </tr>
        <tr>
            <%-- 기초/마감재고표시 --%>
            <th><s:message code="periodIostock.stockOption"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w120px">
                        <wj-combo-box
                                id="srchStockOptionCombo"
                                ng-model="srchStockOption"
                                items-source="_getComboData('srchStockOptionCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
            <td></td>
            <td></td>
        </tr>
    </table>

    <input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
    <input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>
    <input type="hidden" id="storeNm" value="${sessionInfo.storeNm}"/>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="periodiostockListScaleBox"
                ng-model="listScale"
                items-source="_getComboData('periodiostockListScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                initialized="_initComboBox(s)"
                control="listScaleCombo"
                is-editable="true"
                text-changed="_checkValidation(s)">
        </wj-combo-box>
        <%-- 엑셀 다운로드 --%>
        <button class="btn_skyblue ml5 fr" id="btnExcelDown" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%--위즈모 테이블--%>
    <div id="wjGridWrap" class="w100 mt10">
        <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="periodIostock.prodClassNm"/>"	binding="prodClassNm"	    width="200"		align="left"	is-read-only="true"	visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.prodCd"/>"		binding="prodCd"		    width="100"		align="center"	is-read-only="true"	format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.prodNm"/>"		binding="prodNm"		    width="150"		align="left"	is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.poUnitQty"/>"		binding="poUnitQty"		    width="50"		align="right"	is-read-only="true"	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.poUnitFg"/>"		binding="poUnitFgNm"	    width="50"		align="center"	is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.barcdCd"/>"		binding="barcdCd"		    width="120"		align="center"	is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.basicStockQty"/>"      binding="baseQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.basicStockAmt"/>"      binding="baseTot" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="vendrInQty"	    width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="vendrInTot"	    width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="vendrOutQty"	    width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="vendrOutTot"	    width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="hqOutQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="hqOutTot"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="hqInQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="hqInTot"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="storeMoveInQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="storeMoveInTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="storeMoveOutQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="storeMoveOutTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.disuse"/>"		binding="disuseQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.adj"/>"			binding="adjQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.setIn"/>"		binding="setInQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="saleVendrOrderQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="saleVendrOrderTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="saleVendrRtnQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="saleVendrRtnTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="periodIostock.endingStockQty"/>"     binding="closeQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.endingStockAmt"/>"     binding="closeTot" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="cmm.search.date"/>"	binding="startDate"		width="60"		align="center"	is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.search.date"/>"	binding="endDate"		width="60"		align="center"	is-read-only="true" visible="false"></wj-flex-grid-column>

                <div class="gridMsg" id="mobileHqPeriodIoStockMsg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="mobileHqPeriodIoStockCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum3 mt20">
        <%-- id --%>
        <ul id="mobileHqPeriodIoStockCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div id="wjGridWrap" class="w100 mt10" style="display:none;" ng-controller="mobileHqPeriodIoStockExcelCtrl">
        <div class="wj-gridWrap" style="height: 350px;">
            <wj-flex-grid
                    id="periodiostockExcelGrid"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    frozen-columns="6">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="periodIostock.prodClassNm"/>"	binding="prodClassNm"	    width="200"		align="left"	is-read-only="true"	visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.prodCd"/>"		binding="prodCd"		    width="100"		align="center"	is-read-only="true"	format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.prodNm"/>"		binding="prodNm"		    width="150"		align="left"	is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.poUnitQty"/>"		binding="poUnitQty"		    width="50"		align="right"	is-read-only="true"	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.poUnitFg"/>"		binding="poUnitFgNm"	    width="50"		align="center"	is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.barcdCd"/>"		binding="barcdCd"		    width="120"		align="center"	is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.basicStockQty"/>"      binding="baseQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.basicStockAmt"/>"      binding="baseTot" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="vendrInQty"	    width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="vendrInTot"	    width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="vendrOutQty"	    width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="vendrOutTot"	    width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="hqOutQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="hqOutTot"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="hqInQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="hqInTot"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="storeMoveInQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="storeMoveInTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="storeMoveOutQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="storeMoveOutTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.disuse"/>"		binding="disuseQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.adj"/>"			binding="adjQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.setIn"/>"		binding="setInQty"	        width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="saleVendrOrderQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="saleVendrOrderTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="saleVendrRtnQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="saleVendrRtnTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="periodIostock.endingStockQty"/>"     binding="closeQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="periodIostock.endingStockAmt"/>"     binding="closeTot" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/mobile/stock/status/periodIoStock/mobileHqPeriodIoStock.js?ver=20240719.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/mobile/sale/com/popup/selectProdClassCd.jsp">
</c:import>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/mobile/stock/com/popup/prodCodeDtl/mobileHqProdCodeDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수량 상세 레이어 --%>
<c:import url="/WEB-INF/view/mobile/stock/com/popup/prodQtyDtl/mobileProdQtyDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>