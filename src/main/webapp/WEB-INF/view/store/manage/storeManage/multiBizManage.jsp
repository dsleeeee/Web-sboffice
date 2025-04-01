<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>
<c:set var="baseUrl" value="/store/manage/terminalManage/" />

<wj-popup control="multiBizManageLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="multiBizManageCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span><s:message code="storeManage.multiBizManage"/></span>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>
        <div class="wj-dialog-body">
            <%-- 선택한 매장정보 --%>
            <div class="mb5 oh">
                <label id="lblStoreCd" style="display: none"></label>
                <label id="lblHqOfficeCd" style="display: none"></label>
                <label id="lblVanFixFg" style="display: none"></label>
            </div>
            <div class="oh sb-select dkbr">
                <%-- 코너 선택 --%>
                <div class="sb-select fl w200px mb5">
                    <wj-combo-box
                            ng-model="corner"
                            items-source="cornerArr"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="cornerCombo"
                            selected-index-changed="setCornerFgVal(s,e)">
                    </wj-combo-box>
                </div>
                <span class="fr mb5">
                    <%-- 추가 --%>
                    <button class="btn_skyblue" ng-click="addRow()"><s:message code="cmm.add"/></button>
                    <%-- 저장 --%>
                    <button class="btn_skyblue" ng-click="save()"><s:message code="cmm.save"/></button>
                    <%-- 삭제 --%>
                    <button class="btn_skyblue" ng-click="del()"><s:message code="cmm.del"/></button>
                </span>
            </div>
            <div class="w100">
                <%--위즈모 테이블--%>
                <div class="popGrid mt5" style="height:200px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            id="wjCornerGrid"
                            autoGbeginning-editenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            beginning-edit="changeVendorFg(s,e)"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.cornr"/>" binding="cornrCd" width="100" data-map="cornerFgDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.vendorFg"/>" binding="vendorFg" data-map="vendorFgDataMap" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.vendorCd"/>" binding="vendorNm" data-map="vanCdDataMap" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.vendorCd"/>" binding="vendorCd" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.vendorTermnlNo"/>" binding="vendorTermnlNo" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.vendorSerNo"/>" binding="vendorSerNo" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.cornrNm"/>" binding="cornrNm" width="100" max-length="15"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.ownerNm"/>" binding="ownerNm" width="100" max-length="15"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.bizNo"/>" binding="bizNo" width="100" max-length="10"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.baseYn"/>" binding="baseYn" width="80" data-map="baseYnDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.telNo"/>" binding="telNo" width="100" max-length="14"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="cornrRnum" width="0" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="baseVanYn" width="0" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var baseUrl = "${baseUrl}";
    var vendorFg = ${ccu.getCommCodeExcpAll("078")};
    var vandorList = ${vendorList};
    var terminalFg = ${cnv.getEnvCodeExcpAll("2028")};
    var useYnFg    = ${ccu.getCommCodeExcpAll("067")};
    var hqOfficeCd = "${hqOfficeCd}";
    var clsFg = ${ccu.getCommCodeSelect("001")};
    var sysStatFg = ${ccu.getCommCodeSelect("005")};
    // KOCES 총판 및 하위 대리점 코드
    var agencyCol = '${agencyCol}';

    var arrAgencyCol = agencyCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/multiBizManage.js?ver=20250401.01" charset="utf-8"></script>