<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="saleStoreListView" class="subCon" style="display: none;" ng-controller="saleStoreListCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="oper.saleStoreList" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('saleStoreListCtrl',1)"  id="nxBtnSearch1">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
            <tr>
                <%-- 시스템등록 일자 --%>
                <th>
                    <s:message code="oper.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn w110px">
                            <wj-input-date
                                value="startDate"
                                ng-model="startDate"
                                control="startDateCombo"
                                min="2018-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <span class="rg">~</span>
                        <span class="txtIn w110px">
                            <wj-input-date
                                value="endDate"
                                ng-model="endDate"
                                control="endDateCombo"
                                min="2018-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <span class="chk ml10" style="display:none">
                          <input type="checkbox" id="chkDt" ng-model="isChecked" />
                          <label for="chkDt">
                            <s:message code="oper.noSaleStore" />
                          </label>
                        </span>
                    </div>
                </td>
            </tr>
            <%--<tr <c:if test="${orgnFg == 'AGENCY'}">style="display: none;"</c:if> >--%>
            <tr>
                <%-- 관리밴사 --%>
                <th>
                    <s:message code="oper.manageVan" />
                </th>
                <td>
                    <input type="text" id="manageVanNm" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchManageVan()" style="float: left;">
                    <input type="hidden" id="ssl_srchManageVanCd" ng-hide="true">
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelManageVanCd" style="margin-left: 5px;" ng-click="delManageVanCd()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <c:if test="${orgnFg == 'MASTER'}">
                    <%-- 관리업체 --%>
                    <th>
                        <s:message code="oper.agency" />
                    </th>
                    <td>
                        <input type="text" id="agencyNm" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchAgency()" style="float: left;">
                        <input type="hidden" id="ssl_srchAgencyCd" ng-hide="true">
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelAgencyCd" style="margin-left: 5px;" ng-click="delAgencyCd()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </c:if>
                <c:if test="${orgnFg == 'AGENCY'}">
                    <th></th>
                    <td></td>
                </c:if>
            </tr>
            <tr>
                <%-- 본사코드 --%>
                <th>
                    <s:message code="oper.hqOfficeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 본사명 --%>
                <th>
                    <s:message code="oper.hqOfficeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th>
                    <s:message code="oper.storeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 매장명 --%>
                <th>
                    <s:message code="oper.storeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 사업자번호 --%>
                <th>
                    <s:message code="oper.bizNo" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchBizNo" ng-model="bizNo" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 신규/전환 --%>
                <th><s:message code="oper.shopMigFg" /></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                      id="srchShopMigFg"
                      ng-model="shopMigFg"
                      control="shopMigFgCombo"
                      items-source="_getComboData('srchShopMigFg')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                  </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>


    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                    id="saleStoreListGrid"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    frozen-columns="4"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="oper.hqOfficeCd"/>    " binding="hqOfficeCd"      width=" 60" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.hqOfficeNm"/>    " binding="hqOfficeNm"      width="120" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.storeCd"/>       " binding="storeCd"         width=" 80" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.storeNm"/>       " binding="storeNm"         width="150" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.bizNo"/>         " binding="bizNo"           width="100" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.vendorTermnlNo"/>" binding="vendorTermnlNo"  width="100" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.sysStatNm"/>     " binding="sysStatNm"       width=" 50" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.vanNm"/>         " binding="vanNm"           width=" 80" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.shopMigFg"/>     " binding="shopMigFg"       width=" 80" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.regDate"/>       " binding="regDate"         width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.sysOpenDate"/>   " binding="sysOpenDate"     width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.agencyCd"/>      " binding="agencyCd"        width=" 80" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.agency"/>        " binding="agencyNm"        width="120" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.posCnt"/>        " binding="posCnt"          width=" 60" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.billCnt"/>       " binding="billCnt"         width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.saleCnt"/>       " binding="saleCnt"         width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.rtnSaleCnt"/>    " binding="rtnSaleCnt"      width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.realSaleCnt"/>   " binding="realSaleCnt"     width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.pBillCnt"/>      " binding="pBillCnt"        width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.pSaleCnt"/>      " binding="pSaleCnt"        width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.pRtnSaleCnt"/>   " binding="pRtnSaleCnt"     width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.pRealSaleCnt"/>  " binding="pRealSaleCnt"    width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.pCardCnt"/>      " binding="pCardCnt"        width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.pCashCnt"/>      " binding="pCashCnt"        width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.pCashApprCnt"/>  " binding="pCashApprCnt"    width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.totGuestCnt"/>   " binding="totGuestCnt"     width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.totDlvrCnt"/>    " binding="totDlvrCnt"      width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.totResveCnt"/>   " binding="totResveCnt"     width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.totRefundCnt"/>  " binding="totRefundCnt"    width="120" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.minSaleDate"/>   " binding="minSaleDate"     width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.maxSaleDate"/>   " binding="maxSaleDate"     width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.ownerNm"/>       " binding="ownerNm"         width=" 80" is-read-only="true" align="center"> </wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 상태구분 --%>
    var sysStatFgData = ${ccu.getCommCodeExcpAll("005")};

    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/oper/saleStoreList.js?ver=20201210.01" charset="utf-8"></script>

<%-- 대리점 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>

<%-- 관리밴사 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchVan.jsp">
</c:import>