<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMemberInfoPointLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:700px;" fade-in="false" fade-out="false">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
        <%--<s:message code="regist.memberInfoPoint.info"/>--%>
        <label id="lblMemberInfoPointMembrNo"></label>
        <label id="lblMemberInfoPointMembrNm"></label>
        <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
        <div ng-controller="memberInfoPointCtrl">
            <%-- 그리드 --%>
            <s:message code="regist.memberInfoPoint.infoPoint"/>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:260px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns.="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.chgDate"/>" binding="chgDate" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.pointChgFg"/>" binding="pointChgFg" data-map="pointChgFgDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.chgPoint"/>" binding="chgPoint" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.remark"/>" binding="remark" width="280" is-read-only="true" align="left"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <div ng-controller="memberInfoBuyCtrl">
            <s:message code="regist.memberInfoPoint.infoBuy"/>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:270px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                    autoGenerateColumns.="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.saleDate"/>" binding="saleDate" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.totSaleQty"/>" binding="totSaleQty" width="70" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.totSaleAmt"/>" binding="totSaleAmt" width="80" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.membrSavePoint"/>" binding="membrSavePoint" width="80" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.memberInfoPoint.membrUsePoint"/>" binding="membrUsePoint" width="80" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%-- //body --%>

</wj-popup>

<script type="text/javascript">
    <%-- 포인트변경구분 --%>
    var pointChgFgData = ${ccu.getCommCodeExcpAll("031")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberInfoPoint.js?ver=20201130.03" charset="utf-8"></script>