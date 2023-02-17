<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
    <div ng-controller="timeProdCtrl">
        <%--<div class="searchBar flddUnfld">--%>
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('timeProdCtrl', 1)">
                    <s:message code="cmm.search"/>
                </button>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
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
                    <%-- 조회일자 --%>
                    <th><s:message code="cmm.search.date"/></th>
                    <td>
                        <div class="sb-select">
                            <%-- 일/월 구분 --%>
                            <span class="txtIn w15">
                                 <div class="sb-select">
                                    <wj-combo-box
                                            id="srchDayGubunCombo"
                                            ng-model="dayGubun"
                                            items-source="_getComboData('dayGubunCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="srchDayGubunCombo"
                                            selected-index-changed="setDayGubunCombo(s)">
                                    </wj-combo-box>
                                </div>
                            </span>
                            <%-- 일 --%>
                            <span id="spanDay">
                                <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                                <span class="rg">~</span>
                                <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                            </span>
                            <%-- 월 --%>
                            <span id="spanMonth" style="display: none;">
                                <span class="txtIn"><input id="startMonth" class="w110px"></span>
                                <span class="rg">~</span>
                                <span class="txtIn"><input id="endMonth" class="w110px"></span>
                            </span>
                        </div>
                    </td>
                    <%-- 분류조회 --%>
                    <th><s:message code="prod.prodClass" /></th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                             placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </tr>
                <tr>
                    <%-- 일자표시옵션 --%>
                    <th><s:message code="timeProd.dayOption"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchDayOptionCombo"
                                    ng-model="dayOption"
                                    items-source="_getComboData('dayOptionCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchDayOptionCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 상품표시옵션 --%>
                    <th><s:message code="timeProd.prodOption"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdOptionCombo"
                                    ng-model="prodOption"
                                    items-source="_getComboData('prodOptionCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdOptionCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>

                <tr>
                    <%-- 옵션(시간대/시간대분류) --%>
                    <th><s:message code="timeProd.optionFg"/></th>
                    <td>
                        <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                        <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
                    </td>
                    <%-- 옵션에 따른 조회조건 --%>
                    <th><s:message code="timeProd.time"/></th>
                    <td>
                        <div id="timeOption">
                            <div class="sb-select fl w200px">
                                <div class="sb-slect fl" style="width:65px;">
                                    <wj-combo-box
                                            id="startTime"
                                            ng-model="startTime"
                                            items-source="_getComboData('startTimeCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="startTimeCombo"
                                            initialized="_initComboBox(s)">
                                            </wj-combo-box>
                                </div>
                                <div class="fl pd5" style="padding-right: 15px;">
                                    <label> ~ </label>
                                </div>
                                <div class="sb-select fl" style="width:65px;">
                                    <wj-combo-box
                                            id="endTime"
                                            ng-model="endTime"
                                            items-source="_getComboData('endTimeCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="endTimeCombo"
                                            initialized="_initComboBox(s)">
                                    </wj-combo-box>
                                </div>
                            </div>
                        </div>
                        <div id="timeSlotOption" style="display: none">
                            <div class="sb-select fl w120px" >
                                <wj-combo-box
                                        id="timeSlotCombo"
                                        ng-model="timeSlot"
                                        control="timeSlotCombo"
                                        items-source="_getComboData('timeSlotCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <%-- 상품코드 --%>
                    <th><s:message code="timeProd.prodCd" /></th>
                    <td>
                        <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                    <%-- 상품명 --%>
                    <th><s:message code="timeProd.prodNm" /></th>
                    <td>
                        <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                </tr>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <tr>
                        <%-- 매장브랜드 --%>
                        <th><s:message code="timeProd.storeHqBrand"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchStoreHqBrandCdCombo"
                                        ng-model="storeHqBrandCd"
                                        items-source="_getComboData('storeHqBrandCdCombo')"
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
                            <%-- 매장선택 모듈 싱글 선택 사용시 include param 정의 :
                                          targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                          closeFunc - 팝업 닫기시 호출할 함수--%>
                            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                                <jsp:param name="targetId" value="timeProdStore"/>
                            </jsp:include>
                            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <input type="hidden" id="timeProdStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
                <tr>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <%-- 상품브랜드 --%>
                        <th><s:message code="timeProd.prodHqBrand"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchProdHqBrandCombo"
                                        ng-model="prodHqBrandCd"
                                        items-source="_getComboData('prodHqBrandCdCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchProdHqBrandCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </c:if>
                    <%-- 상품 --%>
                    <th><s:message code="timeProd.prod"/></th>
                    <td>
                        <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                                       targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                       displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                       modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                       closeFunc - 팝업 닫기시 호출할 함수--%>
                        <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdMMoms.jsp" flush="true">
                        <jsp:param name="targetId" value="timeProdSelect"/>
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
            <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                    <tr>
                        <%-- 팀별 --%>
                        <th><s:message code="timeProd.momsTeam"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsTeamCombo"
                                        ng-model="momsTeam"
                                        items-source="_getComboData('momsTeamCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsTeamCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- AC점포별 --%>
                        <th><s:message code="timeProd.momsAcShop"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsAcShopCombo"
                                        ng-model="momsAcShop"
                                        items-source="_getComboData('momsAcShopCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsAcShopCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 지역구분 --%>
                        <th><s:message code="timeProd.momsAreaFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsAreaFgCombo"
                                        ng-model="momsAreaFg"
                                        items-source="_getComboData('momsAreaFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsAreaFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 상권 --%>
                        <th><s:message code="timeProd.momsCommercial"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsCommercialCombo"
                                        ng-model="momsCommercial"
                                        items-source="_getComboData('momsCommercialCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsCommercialCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 점포유형 --%>
                        <th><s:message code="timeProd.momsShopType"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsShopTypeCombo"
                                        ng-model="momsShopType"
                                        items-source="_getComboData('momsShopTypeCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsShopTypeCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 매장관리타입 --%>
                        <th><s:message code="timeProd.momsStoreManageType"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                    id="srchMomsStoreManageTypeCombo"
                                    ng-model="momsStoreManageType"
                                    items-source="_getComboData('momsStoreManageTypeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsStoreManageTypeCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <tr>
                            <%-- 지사 --%>
                            <th><s:message code="timeProd.branchCd"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchBranchCdComboo"
                                            ng-model="branchCd"
                                            items-source="_getComboData('branchCdCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchBranchCdComboo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </c:if>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
        </div>

        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="wjGridList"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="timeProd.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.saleYm"/>" binding="saleYm" width="60" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.selTypeFg"/>" binding="selTypeFg" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.sideProdNm"/>" binding="sideProdNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.totSaleQty"/>" binding="totSaleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 시간대 컬럼 생성--%>
                    <c:forEach var="i" begin="0" end="23">
                        <wj-flex-grid-column header="<s:message code="timeProd.saleQty1"/>" binding="saleQty1T${i}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.saleQty2"/>" binding="saleQty2T${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.saleQty3"/>" binding="saleQty3T${i}" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt1"/>" binding="realSaleAmt1T${i}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt2"/>" binding="realSaleAmt2T${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt3"/>" binding="realSaleAmt3T${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.rate1"/>" binding="rate1T${i}" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.rate2"/>" binding="rate2T${i}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.rate3"/>" binding="rate3T${i}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                    </c:forEach>

                    <%-- 시간대분류 컬럼 생성--%>
                    <c:forEach var="timeSlotCol" items="${timeSlotColList}">
                        <wj-flex-grid-column header="<s:message code="timeProd.saleQty1"/>" binding="saleQty1T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.saleQty2"/>" binding="saleQty2T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.saleQty3"/>" binding="saleQty3T${timeSlotCol.value.replace("~","")}" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt1"/>" binding="realSaleAmt1T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt2"/>" binding="realSaleAmt2T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt3"/>" binding="realSaleAmt3T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.rate1"/>" binding="rate1T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.rate2"/>" binding="rate2T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="timeProd.rate3"/>" binding="rate3T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                    </c:forEach>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="timeProdCtrlPager" data-size="10">
        </ul>
    </div>
    <%-- //페이지 리스트 --%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="timeProdExcelCtrl">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridExcelList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="timeProd.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.saleYm"/>" binding="saleYm" width="60" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.selTypeFg"/>" binding="selTypeFg" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.sideProdNm"/>" binding="sideProdNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.totSaleQty"/>" binding="totSaleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProd.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="0" end="23">
                    <wj-flex-grid-column header="<s:message code="timeProd.saleQty1"/>" binding="saleQty1T${i}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.saleQty2"/>" binding="saleQty2T${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.saleQty3"/>" binding="saleQty3T${i}" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt1"/>" binding="realSaleAmt1T${i}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt2"/>" binding="realSaleAmt2T${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt3"/>" binding="realSaleAmt3T${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.rate1"/>" binding="rate1T${i}" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.rate2"/>" binding="rate2T${i}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.rate3"/>" binding="rate3T${i}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                </c:forEach>

                <%-- 시간대분류 컬럼 생성--%>
                <c:forEach var="timeSlotCol" items="${timeSlotColList}">
                    <wj-flex-grid-column header="<s:message code="timeProd.saleQty1"/>" binding="saleQty1T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.saleQty2"/>" binding="saleQty2T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.saleQty3"/>" binding="saleQty3T${timeSlotCol.value.replace("~","")}" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt1"/>" binding="realSaleAmt1T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt2"/>" binding="realSaleAmt2T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.realSaleAmt3"/>" binding="realSaleAmt3T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.rate1"/>" binding="rate1T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.rate2"/>" binding="rate2T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProd.rate3"/>" binding="rate3T${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";

  // List 형식("" 안붙임)
  var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
  var branchCdComboList = ${branchCdComboList};
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

  // 시간대분류
  var timeSlotColList = [];
  <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
  <c:forEach var="timeSlotCol" items="${timeSlotColList}">
  var timeSlotParam   = {};
  timeSlotParam.name  = "${timeSlotCol.name}";
  timeSlotParam.value = "${timeSlotCol.value}";
  timeSlotColList.push(timeSlotParam);
  </c:forEach>

  var timeSlotCol    = '${timeSlotCol}';
  var arrTimeSlotCol = timeSlotCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/time/timeProd/timeProd.js?ver=20230217.01" charset="utf-8"></script>

<%-- 20221117 상품분류 팝업 주석처리함 -> 상품 멀티 팝업에 상품분류가 들어감으로 오류 --%>
<%-- 상품분류 팝업 --%>
<%--<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">--%>
<%--</c:import>--%>