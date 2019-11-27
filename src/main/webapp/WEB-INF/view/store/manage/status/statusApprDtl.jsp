<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="statusApprDtlLayer" control="statusApprDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="statusApprDtlCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="spanDtlTitle"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body" style="height: 500px;">
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 460px; overflow-y: hidden; overflow-x: hidden;" >
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter"
                            id="wjApprGridList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="storeStatus.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.hqBrandCd"/>" binding="hqBrandCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.salesDate"/>" binding="saleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.apprProcFg"/>" binding="apprProcFg" data-map="apprProcFgDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.acquireNm"/>" binding="acquireNm" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.cardNo"/>" binding="cardNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.reqAmt"/>" binding="reqAmt" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.tipAmt"/>" binding="tipAmt" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.vatAmt"/>" binding="vatAmt" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.instCnt"/>" binding="instCnt" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <%--<wj-flex-grid-column header="<s:message code="storeStatus.validDt"/>" binding="validDt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="storeStatus.apprDt"/>" binding="apprDt" width="80" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.apprNo"/>" binding="apprNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeStatus.apprAmt"/>" binding="apprAmt" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>

                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>

        <div class="btnSet2">
            <%-- 닫기 --%>
            <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">

</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/status/statusApprDtl.js?ver=20190926.01" charset="utf-8"></script>

<%-- 매출 상세내역 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusApprInfo.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>