<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<wj-popup id="wjVendrOrderTypeRegPopLayer" control="wjVendrOrderTypeRegPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;">
    <div id="wjVendrOrderTypeRegPopLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrOrderTypeRegPopCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="vendrOrder.dtl.vendrOrderType" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30">
                    <input type="hidden" id="hdNmcodeGrpCd" value="024">
                    [<label id="lblNmcodeGrpCd"></label>]
                    <s:message code='vendrOrder.pop.vendrOrder/ExportType' />
                </span>
                <button class="btn_skyblue" ng-click="addVendrType()">
                    <s:message code="vendrOrder.pop.new"/>
                </button>
                <button class="btn_skyblue" ng-click="delVendrType()">
                    <s:message code="cmm.delete"/>
                </button>
                <button class="btn_skyblue" ng-click="saveVendrType()">
                    <s:message code="cmm.save"/>
                </button>
            </div>
            <div class="wj-gridWrap" style="height:480px; overflow-y: hidden;">
                <div class="row">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vendrOrder.pop.code1"/>" binding="nmcodeCd" width="90" is-read-only="false" align="center" max-length="1"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vendrOrder.pop.areaNm"/>" binding="nmcodeNm" width="120" is-read-only="false" align="left" max-length="15"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vendrOrder.pop.useYn"/>" binding="useYn" width="80" data-map="useYnDataMap" align="center" is-read-only="false" align="right"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    <%-- 사용 여부 --%>
    var useYn = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrOrder/vendrOrderTypeRegPop.js?ver=20201210.08" charset="utf-8"></script>
