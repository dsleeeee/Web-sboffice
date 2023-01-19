<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div id="saleDtlChannelExcelView" class="subCon">
    <div ng-controller="saleDtlChannelExcelCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="saleDtlChannelExcel.saleDtlChannelExcel"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('saleDtlChannelExcelCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue fr mr5" id="btnSearchAddExcelShow" ng-click="searchAddShowChangeExcel()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
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
                <%-- 조회월 --%>
                <th>
                    <s:message code="cmm.search.month" />
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startMonth" name="startMonth" class="w100px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endMonth" name="endMonth" class="w100px" /></span>
                    </div>
                </td>
                <%-- 분류조회 --%>
                <th><s:message code="prodSaleRate.prodClass" /></th>
                <td>
                    <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                           placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
            </tr>
            <tr>
                <%-- 상품표시옵션 --%>
                <th><s:message code="prodSaleRate.prodOption"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchProdOption"
                                ng-model="prodOption"
                                items-source="_getComboData('srchProdOption')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchProdOptionCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 상품코드 --%>
                <th><s:message code="prodSaleRate.prodCd" /></th>
                <td>
                    <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 상품명 --%>
                <th><s:message code="prodSaleRate.prodNm" /></th>
                <td>
                    <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                        <%-- 매장브랜드 --%>
                    <th><s:message code="prodSaleRate.storeHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchStoreHqBrandCd"
                                    items-source="_getComboData('srchStoreHqBrandCd')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchStoreHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 매장코드 --%>
                    <th><s:message code="cmm.store"/></th>
                    <td>
                            <%-- 매장선택 모듈 싱글 선택 사용시 include
                                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                              closeFunc - 팝업 닫기시 호출할 함수
                            --%>
                        <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                            <jsp:param name="targetId" value="saleDtlChannelExcelStore"/>
                        </jsp:include>
                            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="saleDtlChannelExcelStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            <tr>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 상품브랜드 --%>
                    <th><s:message code="prodSaleRate.prodHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdHqBrand"
                                    items-source="_getComboData('srchProdHqBrand')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdHqBrandCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
                <%-- 상품 --%>
                <th><s:message code="prodRankMoms.prod"/></th>
                <td>
                    <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                         targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                         displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                         modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                         closeFunc - 팝업 닫기시 호출할 함수--%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdMMoms.jsp" flush="true">
                        <jsp:param name="targetId" value="saleDtlChannelExcelSelect"/>
                    </jsp:include>
                    <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            </tbody>
        </table>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <table class="searchTbl" id="tblSearchAddShowExcel" style="display: none;">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                        <%-- 팀별 --%>
                    <th><s:message code="prodSaleRate.momsTeam"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsTeam"
                                    items-source="_getComboData('srchMomsTeam')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchMomsTeamCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- AC점포별 --%>
                    <th><s:message code="prodSaleRate.momsAcShop"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsAcShop"
                                    items-source="_getComboData('srchMomsAcShop')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchMomsAcShopCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                        <%-- 지역구분 --%>
                    <th><s:message code="prodSaleRate.momsAreaFg"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsAreaFg"
                                    items-source="_getComboData('srchMomsAreaFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchMomsAreaFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 상권 --%>
                    <th><s:message code="prodSaleRate.momsCommercial"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsCommercial"
                                    items-source="_getComboData('srchMomsCommercial')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchMomsCommercialCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                        <%-- 점포유형 --%>
                    <th><s:message code="prodSaleRate.momsShopType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsShopType"
                                    items-source="_getComboData('srchMomsShopType')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchMomsShopTypeCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 매장관리타입 --%>
                    <th><s:message code="prodSaleRate.momsStoreManageType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsStoreManageType"
                                    items-source="_getComboData('srchMomsStoreManageType')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchMomsStoreManageTypeCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                        <%-- 지사 --%>
                    <th><s:message code="prodSaleRate.branchCd"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchBranchCd"
                                    items-source="_getComboData('srchBranchCd')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchBranchCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </c:if>

        <div class="mt10 oh sb-select dkbr">
            <p class="tl s14 mt5 lh15 red">전체매장 자료생성 요청시 생성까지 최대 90분정도 소요됩니다.</p>
            <%-- 삭제 --%>
            <button class="btn_skyblue ml5 fr" id="btnDel" ng-click="del()">
                <s:message code="cmm.del" />
            </button>
            <%-- 자료생성 --%>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="dataCreate()">
                <s:message code="saleDtlChannelExcel.dataCreate" />
            </button>
            <%-- 자료생성 날짜 --%>
            <div class="sb-select dkbr ml5 fr">
                <span class="txtIn"><input id="dataCreateMonth" name="dataCreateMonth" class="w100px" /></span>
            </div>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.saleMonth"/>" binding="saleMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.procGubun"/>" binding="procGubun" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.procDt"/>" binding="procDt" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.userNm"/>" binding="userNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.procFg"/>" binding="procFg" data-map="procFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.procMsg"/>" binding="procMsg" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.download"/>" binding="download" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--삭제시 필요--%>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.fromSaleDate"/>" binding="fromSaleDate" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.toSaleDate"/>" binding="toSaleDate" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="saleDtlChannelExcel.fileName"/>" binding="fileName" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<form id="saleReport_info" name="saleReport_info" method="post" action="/sale/status/saleDtlChannel/saleDtlChannelExcel/getSaleDtlChannelDownload.sb" target="saleReportFrm">
    <iframe name="saleReportFrm" style="display:none;"></iframe>

    <input type="hidden" name="fileName" value="" /> <%--파일명--%>
</form>

<script type="text/javascript">
    function saleReport_download(fileName)
    {
        document.saleReport_info.fileName.value = fileName;
        document.saleReport_info.submit();
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/prod/saleDtlChannel/saleDtlChannelExcel.js?ver=20230106.01" charset="utf-8"></script>