<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<wj-popup control="statusStoreCornerViewLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:500px;" fade-in="false" fade-out="false">
    <div ng-controller="statusStoreCornerCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="statusStoreCorner.info" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- 그리드 --%>
        <div class="wj-dialog-body">
            <div style="height:470px; overflow-y: auto;">
            <%-- 코너사용현황 --%>
                <div class="w100 mt10 mb20">
                    <span id="hqOfficeNmTitle" class="fl bk lh20 s14"></span>
                    <h3 class="h3_tbl"><s:message code="statusStoreCorner.useInfo" /></h3>
                    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                is-read-only="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.cornrNm"/>" binding="cornrNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.vendorTermnlNo"/>" binding="vendorTermnlNo" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.cashPer"/>" binding="cashPer" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.cardPer"/>" binding="cardPer" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.extraPer"/>" binding="extraPer" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.ownerNm"/>" binding="ownerNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.bizNo"/>" binding="bizNo" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.condition"/>" binding="condition" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.event"/>" binding="event" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.telNo"/>" binding="telNo" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusStoreCorner.useYn"/>" binding="useYn" data-map="useYnDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/status/statusStoreCorner.js?ver=2019052801.19" charset="utf-8"></script>