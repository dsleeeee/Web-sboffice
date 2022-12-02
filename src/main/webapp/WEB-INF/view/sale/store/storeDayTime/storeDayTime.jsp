<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="storeDayTimeCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeDayTimeCtrl')">
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
                    <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                </div>
            </td>
            <%-- 옵션(기간별/일자별) --%>
            <th><s:message code="storeDayTime.optionFg"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="option"
                            ng-model="option"
                            items-source="_getComboData('option')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="optionCombo"
                            selected-index-changed="changeOption(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 옵션(시간대/시간대분류) --%>
            <th><s:message code="storeDayTime.optionFg"/></th>
            <td>
                <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
            </td>
            <%-- 옵션에 따른 조회조건 --%>
            <th><s:message code="storeDayTime.time"/></th>
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
            <%-- 옵션 --%>
            <th><s:message code="storeDayChannel.option"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="option2"
                            ng-model="option2"
                            items-source="_getComboData('option2')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="option2Combo"
                            selected-index-changed="changeOption(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 매장코드 --%>
            <th class="dayTimeStore" style="display: none;"><s:message code="cmm.store"/></th>
            <td class="dayTimeStore" style="display: none;">
                    <%-- 매장선택 모듈 싱글 선택 사용시 include
                         param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                      displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                      modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                      closeFunc - 팝업 닫기시 호출할 함수
                    --%>
                <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                    <jsp:param name="targetId" value="storeDayTimeStore"/>
                </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        <tr>
            <%-- 매장브랜드 --%>
            <th><s:message code="dayProd.storeHqBrand"/></th>
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
                <th><s:message code="dayProd.momsTeam"/></th>
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
                <th><s:message code="dayProd.momsAcShop"/></th>
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
                <th><s:message code="dayProd.momsAreaFg"/></th>
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
                <th><s:message code="dayProd.momsCommercial"/></th>
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
                <th><s:message code="dayProd.momsShopType"/></th>
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
                <th><s:message code="dayProd.momsStoreManageType"/></th>
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
                    <th><s:message code="dayProd.branchCd"/></th>
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
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                     id="wjGridList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeDayTime.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.yoil"/>" binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.branchNm"/>" binding="branchNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.storeCnt"/>" binding="storeCnt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="storeDayTime.saleQty"/>" binding="saleQty" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.saleCnt"/>" binding="saleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.rtnSaleCnt"/>" binding="rtnSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.realSaleCnt"/>" binding="realSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeDayTime.billUprc"/>" binding="billUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>

                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="0" end="23">
                    <wj-flex-grid-column header="<s:message code="storeDayTime.saleQty"/>" binding="saleQty${i}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeDayTime.realSaleAmt"/>" binding="realSaleAmt${i}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeDayTime.rate"/>" binding="rate${i}" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                </c:forEach>

                <%-- 시간대분류류 컬럼 생성--%>
               <c:forEach var="timeSlotCol" items="${timeSlotColList}">
                   <wj-flex-grid-column header="<s:message code="storeDayTime.saleQty"/>" binding="saleQty${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="storeDayTime.realSaleAmt"/>" binding="realSaleAmt${timeSlotCol.value.replace("~","")}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="storeDayTime.rate"/>" binding="rate${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
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

<script type="text/javascript" src="/resource/solbipos/js/sale/store/storeDayTime/storeDayTime.js?ver=20221129.01" charset="utf-8"></script>
