<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<wj-popup control="moneyFgRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:400px; height:500px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="moneyFgRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="depositDdc.moneyFgReg"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30">
                    <input type="hidden" id="hdNmcodeGrpCd" value="133">
                    [<label id="lblNmcodeGrpCd"></label>]
                    <s:message code='depositDdc.depositDdcFg' />
                </span>
                <button class="btn_skyblue" ng-click="addMoneyFg()">
                    <s:message code="depositDdc.new"/>
                </button>
                <button class="btn_skyblue" ng-click="delMoneyFg()">
                    <s:message code="cmm.delete"/>
                </button>
                <button class="btn_skyblue" ng-click="saveMoneyFg()">
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
                            item-formatter="_itemFormatter"
                            ime-enabled="true"
                            id="wjGridMoneyFgReg">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.code2"/>" binding="nmcodeCd" width="80" is-read-only="false" align="center" max-length="2"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.name"/>" binding="nmcodeNm" width="120" is-read-only="false" align="left" max-length="15"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.useYn"/>" binding="useYn" width="70" data-map="useYnDataMap" align="center" is-read-only="false"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/depositDdc/popup/moneyFgReg.js?ver=20220421.01" charset="utf-8"></script>
