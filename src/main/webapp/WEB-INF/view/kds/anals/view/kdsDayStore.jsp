<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="kdsDayStoreCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('kdsDayStoreList')">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w5"/>
            <col class="w15"/>
            <col class="w5"/>
            <col class="w15"/>
            <col class="w5"/>
            <col class="w15"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 영업일자 --%>
            <th>
                <s:message code="kds.saleDate"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                      <div class="sb-select">
                        <wj-input-date
                                value="kdsDayStartDate"
                                ng-model="kdsDayStartDate"
                                control="kdsDayStartDateCombo"
                                format="yyyy-MM-dd"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                        </wj-input-date>
                      </div>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn">
                    <div class="sb-select">
                      <wj-input-date
                              value="kdsDayEndDate"
                              ng-model="kdsDayEndDate"
                              control="kdsDayEndDateCombo"
                              format="yyyy-MM-dd"
                              min="2000-01-01"
                              max="2099-12-31"
                              initialized="_initDateBox(s)">
                      </wj-input-date>
                    </div>
                    </span>
                </div>
            </td>
            <c:if test="${orgnFg == 'HQ'}">
                <th><s:message code="kds.store.search"/></th>
                <td>
                    <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                        <jsp:param name="targetId" value="regStore"/>
                    </jsp:include>
                </td>
            </c:if>
            <c:if test="${orgnFg == 'HQ'}">
                <th><s:message code="kds.store.contrast"/></th>
                <td>
                    <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                        <jsp:param name="targetId" value="conRegStore"/>
                    </jsp:include>
                </td>
            </c:if>
        </tr>
        <tr>
            <th>
                <s:message code="kds.makeDate.setting"/>
            </th>
            <td>
                <div class="sb-select fl w20 mr10">
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
                </div>
                <div class="sb-select fl w20 mr10">
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
                </div>
            </td>
            <th>
                <s:message code="kds.picDate.setting"/>
            </th>
            <td colspan="3">
                <div class="sb-select fl w20 mr10">
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
                </div>
                <div class="sb-select fl w20 mr10">
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
                </div>
            </td>
        </tr>
        <tr>
            <th><s:message code="kds.prodCd"/></th>
            <td>
                <input type="text" id="prodCd" class="sb-input w50" ng-model="prodCd"
                       maxlength="15"/>
            </td>
            <th><s:message code="kds.prodNm"/></th>
            <td>
                <input type="text" id="prodNm" class="sb-input w50" ng-model="prodNm"
                       maxlength="15"/>
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
        </tbody>
    </table>

    <div class="w50 mt40 mb20 fl ">
        <%--위즈모 차트--%>
        <h2>
            <div class="circle">
                <s:message code="kds.makeDate.standard"/>
            </div>
        </h2>
        <div class="wizWrap" id="chart1" style="width:100%; height:370px;"></div>
    </div>
    <div class="w50 mt40 mb20 fl">
        <%--위즈모 차트--%>
        <h2>
            <div class="circle">
                    <s:message code="kds.picDate.standard"/>
            </div>
        </h2>
        <div class="wizWrap" id="chart2" style="width:100%; height:370px;"></div>
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

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="kds.saleDate"/>" binding="saleDate"
                                     width="*"
                                     is-read-only="true" align="center"></wj-flex-grid-column>

                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.prodCd"/>" binding="prodCd"
                                     width="*"
                                     is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.prodNm"/>" binding="prodNm"
                                     width="*"
                                     is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.amt"/>" binding="saleQty"
                                     width="*"
                                     is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.totalOrderCnt"/>"
                                     binding="orderCnt" width="*"
                                     is-read-only="true" align="right" visible="true"></wj-flex-grid-column>

                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.prodCd"/>" binding="prodCd"
                                     width="*"
                                     is-read-only="true" align="right" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.prodNm"/>" binding="prodNm"
                                     width="*"
                                     is-read-only="true" align="right" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.amt"/>" binding="saleQty"
                                     width="*"
                                     is-read-only="true" align="right" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.totalOrderCnt"/>"
                                     binding="orderCnt" width="*"
                                     is-read-only="true" align="right" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>" binding="avgMake" width="*"
                                     is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>" binding="avgPic" width="*"
                                     is-read-only="true" align="right"></wj-flex-grid-column>

                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.prodCd"/>" binding="conProdCd"
                                     width="*"
                                     is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.prodNm"/>" binding="conProdNm"
                                     width="*"
                                     is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.amt"/>" binding="conSaleQty"
                                     width="*"
                                     is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="false" header="<s:message code="kds.totalOrderCnt"/>"
                                     binding="conOrderCnt" width="*"
                                     is-read-only="true" align="right" visible="true"></wj-flex-grid-column>

                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.prodCd"/>" binding="conProdCd"
                                     width="*"
                                     is-read-only="true" align="right" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.prodNm"/>" binding="conProdNm"
                                     width="*"
                                     is-read-only="true" align="right" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.amt"/>" binding="conSaleQty"
                                     width="*"
                                     is-read-only="true" align="right" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column ng-switch-when="true" header="<s:message code="kds.totalOrderCnt"/>"
                                     binding="conOrderCnt" width="*"
                                     is-read-only="true" align="right" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="kds.avgMakeDate"/>" binding="conAvgMake" width="*"
                                     is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kds.avgPicDate"/>" binding="conAvgPic" width="*"
                                     is-read-only="true" align="right"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/kds/anals/chart/kdsDayStore.js?ver=2020070801.08"
        charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>


