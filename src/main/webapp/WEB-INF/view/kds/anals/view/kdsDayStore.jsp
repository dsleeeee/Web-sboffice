<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="kdsDayStoreCtrl">
    <input type="hidden" id="resurceFg" class="sb-input w50" ng-model="resurceFg" value="${orgnFg}"/>
    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('kdsDayStoreList')" id="nxBtnSearch">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 영업일자 --%>
            <th>
                <s:message code="kds.saleDate"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"> <input id="kdsDayStartDate" name="kdsDayStartDate" class="w150px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="kdsDayEndDate" name="kdsDayEndDate" class="w150px"/></span>
                </div>
            </td>
            <%-- 분류조회 --%>
            <th><s:message code="kds.prodClass"/></th>
            <td>
                <input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm"
                       ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="prodCorner.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;"
                        ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
        </tr>
        <c:if test="${orgnFg == 'HQ'}">
        <tr>
            <th><s:message code="kds.store.search"/></th>
            <td>
                <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                    <jsp:param name="targetId" value="regStore"/>
                </jsp:include>
            </td>
            <th><s:message code="kds.store.contrast"/></th>
            <td>
                <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                    <jsp:param name="targetId" value="conRegStore"/>
                </jsp:include>
            </td>
        </tr>
        </c:if>
        <tr>
            <th>
                <s:message code="kds.makeDate.setting"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w150px">
                        <wj-combo-box
                                id="makeDate"
                                ng-model="makeDate"
                                control="makeDateCombo"
                                items-source="_getComboData('kdsMakeDate')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn w150px">
                        <wj-combo-box
                                id="makeDateSec"
                                ng-model="makeDateSec"
                                control="makeDateSecCombo"
                                items-source="_getComboData('kdsMakeDateSec')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
            <th>
                <s:message code="kds.picDate.setting"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w150px">
                        <wj-combo-box
                                id="picDate"
                                ng-model="picDate"
                                control="picDateCombo"
                                items-source="_getComboData('kdsPicDate')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn w150px">
                        <wj-combo-box
                                id="picDateSec"
                                ng-model="picDateSec"
                                control="picDateSecCombo"
                                items-source="_getComboData('kdsPicDateSec')"
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
            <th><s:message code="kds.prodCd"/></th>
            <td>
                <input type="text" id="prodCd" class="sb-input w100" ng-model="prodCd"
                       maxlength="15" onkeyup="fnNxBtnSearch();"/>
            </td>
            <th><s:message code="kds.prodNm"/></th>
            <td>
                <input type="text" id="prodNm" class="sb-input w100" ng-model="prodNm"
                       maxlength="15" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div id="divChart" class="w100 mt40 mb20" style="display: none;">
        <div class="w50 fl">
            <%--위즈모 차트--%>
            <div class="sb-select fl" style="padding:10px;">
                <h4>
                    <div class="circle">
                        - <s:message code="kds.makeDate.standard"/> -
                    </div>
                </h4>
            </div>
            <h3>
                <div class="circle" style="padding: 10px;">
                        <span class="orange" style="color:#ff9d39 !important;">
                            <s:message code="kds.search.store"/>
                        </span>
                    <span class="green"style="color: #00ba8b !important;">
                             <s:message code="kds.con.store"/>
                        </span>
                </div>
            </h3>
            <div class="wizWrap" id="chart1" style="width:100%; height:370px; font-size: 10pt;"></div>
        </div>
        <div class="w50 fr">
            <%--위즈모 차트--%>
            <div class="sb-select fl" style="padding:10px;">
                <h4>
                    <div class="circle">
                        - <s:message code="kds.picDate.standard"/> -
                    </div>
                </h4>
            </div>
            <h3>
                <div class="circle" style="padding: 10px;">
                    <span class="orange" style="color:#ff9d39 !important;">
                        <s:message code="kds.search.store"/>
                    </span>
                    <span class="green"style="color: #00ba8b !important;">
                         <s:message code="kds.con.store"/>
                    </span>
                </div>
            </h3>
            <div class="wizWrap" id="chart2" style="width:100%; height:370px; font-size: 10pt;"></div>
        </div>
    </div>

    <div class="mt20 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="excelDownloadInfo()">
            <s:message code="cmm.excel.down"/>
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt40">
        <div class="wj-gridWrap" ng-switch="!!prodChk" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns START-->
                <%-- 영업일자(조회매장/대비매장 공통) --%>
                <wj-flex-grid-column header="<s:message code="kds.saleDate"/>" binding="saleDate" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                <%-- 조회매장 START --%>
                <%-- HDR --%>
                <%--<wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.prodCd"/>" binding="prodCd" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.prodNm"/>" binding="prodNm" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.amt"/>" binding="saleQty" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>--%>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.totalOrderCnt"/>" binding="orderCnt" width="100" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                <%-- DTL --%>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.prodCd"/>" binding="prodCd" width="*" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.prodNm"/>" binding="prodNm" width="*" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.amt"/>" binding="saleQty" width="*" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                <%--<wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.totalOrderCnt"/>" binding="orderCnt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>--%>
                <%-- 평균제조시간/평균픽업시간 --%>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>" binding="avgMake" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>" binding="avgPic" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <%-- 조회매장 END --%>

                <%-- 대비매장 START --%>
                <%-- HDR --%>
                <%--<wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.prodCd"/>" binding="conProdCd" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.prodNm"/>" binding="conProdNm" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.amt"/>" binding="conSaleQty" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>--%>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.totalOrderCnt"/>" binding="conOrderCnt" width="100" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                <%-- DTL --%>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.prodCd"/>" binding="conProdCd" width="*" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.prodNm"/>" binding="conProdNm" width="*" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.amt"/>" binding="conSaleQty" width="*" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                <%--<wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.totalOrderCnt"/>" binding="conOrderCnt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>--%>
                <%-- 평균제조시간/평균픽업시간 --%>
                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>" binding="conAvgMake" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>" binding="conAvgPic" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <%-- 대비매장 END --%>
                <!-- define columns END-->

            </wj-flex-grid>
        </div>
    </div>
</div>
<script>
    var kdsMakeDate = ${ccu.getCommCode("400")};
    var kdsPicDate = ${ccu.getCommCodeExcpAll("401")};
    var kdsMakeDateSec = ${ccu.getCommCode("402")};
    var kdsPicDateSec = ${ccu.getCommCodeExcpAll("403")};
    var regstrStoreList = ${regstrStoreList};
</script>

<style>
    .circle span.orange:before {
        content: '';
        width: 10px;
        height: 10px;
        border-radius: 100%;
        background: #ff9d39;
        border: 1px solid #ff9d39;
        position: absolute;
        left: 0px;
        top: 4px;
    }

    .circle span.green:before {
        content: '';
        width: 10px;
        height: 10px;
        border-radius: 100%;
        background: #00ba8b;
        border: 1px solid #00ba8b;
        position: absolute;
        left: 0px;
        top: 4px;
    }
</style>

<script type="text/javascript" src="/resource/solbipos/js/kds/anals/chart/kdsDayStore.js?ver=2020070801.15" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>


