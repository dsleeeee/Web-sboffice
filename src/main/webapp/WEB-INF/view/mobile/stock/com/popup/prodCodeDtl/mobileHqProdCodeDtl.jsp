<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<wj-popup id="mobileProdCodeDtlLayer" control="mobileProdCodeDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:90%;height:90%">
    <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="mobileProdCodeDtlCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="periodIostock.iostockDetail"/>&nbsp;&nbsp;<span id="spanDtlTitle">{{prodNm}}</span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2">

            <div class="tr fr">
                <%-- 엑셀 다운로드 --%>
                <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
                </button>
            </div>
            <div style="clear: both;"></div>

            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="popGrid mt5" style="height: 500px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="periodIostock.date"/>"      binding="ioOccrDt"          width="80" align="center" is-read-only="true"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"       binding="vendrInQty"		  width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"       binding="vendrInTot"		  width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"       binding="vendrOutQty"		  width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"       binding="vendrOutTot"		  width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"       binding="hqOutQty"          width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"       binding="hqOutTot"          width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"       binding="hqInQty"		      width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"       binding="hqInTot"		      width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"       binding="storeMoveInQty"	  width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"       binding="storeMoveInTot"	  width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"       binding="storeMoveOutQty"	  width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"       binding="storeMoveOutTot"	  width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.ioOccr17"/>"  binding="disuseQty"         width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.ioOccr21"/>"  binding="adjQty"		      width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.ioOccr22"/>"  binding="setInQty"          width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"       binding="saleVendrOrderQty" width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"       binding="saleVendrOrderTot" width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"       binding="saleVendrRtnQty"   width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"       binding="saleVendrRtnTot"   width="60"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="mobileProdCodeDtlCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>
<script type="text/javascript">
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/stock/com/popup/prodCodeDtl/mobileProdCodeDtl.js?ver=20240730.01" charset="utf-8"></script>
