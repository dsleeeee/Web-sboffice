<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<%--<c:set var="prodEnvstVal" value="${prodEnvstVal}" />--%>
<%--<c:set var="priceEnvstVal" value="${priceEnvstVal}" />--%>
<c:set var="prodNoEnvFg" value="${prodNoEnvFg}" />
<c:set var="kitchenprintLink" value="${kitchenprintLink}" />
<c:set var="brandUseFg" value="${brandUseFg}" />

<div class="subCon" ng-controller="prodCtrl" id="prodView">
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeSideMenu.prod" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('prodCtrl',1)">
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
        <%-- 등록 일자 --%>
        <tr>
            <th><s:message code="prod.regDate" /></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchTimeStartDate" ng-model="startDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchTimeEndDate" ng-model="endDate" class="w120px"></span>
                    <%--전체기간--%>
                    <span class="chk ml10">
                      <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                      <label for="chkDt">
                        <s:message code="cmm.all.day" />
                      </label>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="prod.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="prod.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="prod.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <input type="hidden" id="_selectCancelFg" name="selectCancelFg" ng-model="selectCancelFg" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th><s:message code="prod.barCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <tr>
            <%-- 사용여부 --%>
            <th><s:message code="prod.useYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchUseYn"
                            ng-model="useYn"
                            control="useYnAllCombo"
                            items-source="_getComboData('useYnAllComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index="1">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 브랜드 --%>
            <%--<c:if test="${brandUseFg == '1'}">
                <th><s:message code="prod.brandNm" /></th>
                <td><input type="text" class="sb-input w100" id="srchBrandNm" ng-model="hqBrandNm" onkeyup="fnNxBtnSearch('2');"/></td>
            </c:if>
            <c:if test="${brandUseFg == '0'}">
                <th></th>
                <td></td>
            </c:if>--%>
            <c:if test="${brandUseFg != '1' or sessionInfo.orgnFg != 'HQ'}">
                <th></th>
                <td></td>
            </c:if>
            <c:if test="${brandUseFg == '1'}">
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 상품브랜드 --%>
                    <th><s:message code="prod.prodHqBrand"/></th>
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
            </c:if>
        </tr>
        </tbody>
    </table>
    <%--//searchTbl--%>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                control="listScaleCombo"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
        </wj-combo-box>
        <%-- 전체상품엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadTotal()" ><s:message code="cmm.excel.downTotal"/></button>
        <%-- 조회조건내역엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadCondition()" ><s:message code="cmm.excel.downCondition"/></button>
        <%-- 상품엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()" ><s:message code="cmm.excel.down"/></button>
        <%-- 신규상품등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnAddProd" ng-click="addProd()" ng-show="btnShowFg" style="display: none;">
            <s:message code="prod.title.addProd" />
        </button>
        <%-- 매장상품일괄등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnStoreProdBatchList" ng-click="storeProdBatchList()" style="display: none;">
            <s:message code="prod.title.storeProdBatch" />
        </button>
        <%-- 상품삭제 --%>
        <button class="btn_skyblue ml5 fr" id="btnDelProd" ng-click="delProd()" ng-show="btnShowFg" style="display: none;">
            <s:message code="prod.title.delProd" />
        </button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div id="theGrid" style="height: 400px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    id="wjGridProd">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="true"></wj-flex-grid-column>
                <c:if test="${brandUseFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.prodClassCd"/>" binding="prodClassCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodClassNm"/>" binding="prodClassNm" width="300" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.barCd"/>" binding="barCd" width="100" is-read-only="true"></wj-flex-grid-column>
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="prod.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.costUprc"/>" binding="costUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.splyUprc"/>" binding="splyUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.orgplceCd"/>" binding="orgplceCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column><!--// todo 원산지명 조회 필요-->
                <wj-flex-grid-column header="<s:message code="prod.poUnitFg"/>" binding="poUnitFg" width="80" data-map="poUnitFgComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.useYn"/>" binding="useYn" width="80" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <c:if test="${orgnFg == 'STORE'}">
                    <wj-flex-grid-column header="<s:message code="prod.regFg"/>" binding="regFg" width="100" is-read-only="true" data-map="regFgDataMap" align="center"></wj-flex-grid-column>
                </c:if>

                <wj-flex-grid-column header="<s:message code="prod.brandCd"/>" binding="hqBrandCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandN" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sideProdYn"/>" binding="sideProdYn" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sdattrClassCd"/>" binding="sdattrClassCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sdselGrpCd"/>" binding="sdselGrpCd" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="prodCtrl"/>
            </jsp:include>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="prodCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="wj-TblWrapBr mt10" ng-controller="totalExcelCtrl" style="display: none;">
        <div class="wj-gridWrap" id="qn">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    loaded-rows="loadedRows(s,e)"
                    is-read-only="true"
                    frozen-columns="6"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <c:if test="${brandUseFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.prodClassCd"/>" binding="prodClassCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodClassNm"/>" binding="prodClassNm" width="300" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.barCd"/>" binding="barCd" width="100" is-read-only="true"></wj-flex-grid-column>
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="prod.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.costUprc"/>" binding="costUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.splyUprc"/>" binding="splyUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.orgplceCd"/>" binding="orgplceCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column><!--// todo 원산지명 조회 필요-->
                <wj-flex-grid-column header="<s:message code="prod.poUnitFg"/>" binding="poUnitFg" width="80" data-map="poUnitFgComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.useYn"/>" binding="useYn" width="80" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <c:if test="${orgnFg == 'STORE'}">
                    <wj-flex-grid-column header="<s:message code="prod.regFg"/>" binding="regFg" width="100" is-read-only="true" data-map="regFgDataMap" align="center"></wj-flex-grid-column>
                </c:if>

                <wj-flex-grid-column header="<s:message code="prod.brandCd"/>" binding="hqBrandCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandN" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sideProdYn"/>" binding="sideProdYn" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sdattrClassCd"/>" binding="sdattrClassCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sdselGrpCd"/>" binding="sdselGrpCd" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%--//엑셀 리스트--%>
    </div>
</div>

<script>
    <%--var prodEnvstVal = "${prodEnvstVal}";--%>
    <%--var priceEnvstVal = "${priceEnvstVal}";--%>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var sUserId = "${userId}";
    var prodNoEnvFg = "${prodNoEnvFg}";
    var prodAuthEnvstVal = "${prodAuthEnvstVal}";
    // 프린터연결팝업창 여부(0:안띄움 1:띄움)
    var kitchenprintLink = "${kitchenprintLink}";
    // 상품코드 PREFIX 값 사용여부
    var prodCdPreFg     = "${prodCdPreFg}";

    /* 상품상세 필수 START */
    // 내점/배달/포장 가격관리 사용여부 (0: 미사용 1: 사용)
    var subPriceFg = "${subPriceFg}";
    // (상품관리)브랜드사용여부
    var brandUseFg = "${brandUseFg}";
    // 매장상품제한구분 사용여부(매장 세트구성상품 등록시 사용, 매장에서 사용하지만 본사환경설정값으로 여부파악)
    var storeProdUseFg = "${storeProdUseFg}";
    // 브랜드
    var brandList = ${brandList};
    // 매장별 브랜드 콤보박스 조회(사용자 상관없이 전체 브랜드 표시)
    var userHqStoreBrandCdComboList = ${userHqStoreBrandCdComboList};
    // 사용자 매장브랜드(조회용)
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
    // 코너 콤보박스
    var cornerList = ${cornerList};
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
    var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
    var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
    var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
    var momsStoreFg05ComboList = ${momsStoreFg05ComboList};
    /* 상품상세 필수 END */


    // KIOSK 엣지
    var momsKioskEdgeComboData = ${ccu.getCommCodeExcpAll("180")};

    // POS에서 해당 WEB 화면 최초 접속한 경우(접속하면서 session 생성), 왼쪽 메뉴영역은 접어두기.
    // 최초 접속시에는 이전 URL 인자값으로 판별가능
    var referrer = document.referrer;
    if(referrer.indexOf("userId") > 0 && referrer.indexOf("resrceCd") > 0 && referrer.indexOf("accessCd") > 30 ){
        $(".menuControl").trigger("click");
    }

    // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값 판단하여 왼쪽 메뉴영역은 접어두기.
    // 재접속시에는 이전 URL 인자값이 없어, 로그인 여부 판별시에 특정 parameter 값을 보내 처리.
    if("${posLoginReconnect}" === "Y"){ // 직접입력한경우
        $(".menuControl").trigger("click");
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prod.js?ver=20250725.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
    <c:param name="prodNoEnvFg" value="${prodNoEnvFg}"/>
</c:import>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
    <c:param name="kitchenprintLink" value="${kitchenprintLink}"/>
</c:import>

<%-- 레이어 팝업 : 상품별 적용매장 선택 팝업 --%>
<%-- 그리드에 등록매장수 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodStoreRegistView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 매장 리스트 팝업 --%>
<%-- 매장상품일괄등록 --%>
<c:import url="/WEB-INF/view/base/prod/prod/storeProdBatchList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 매장별 상품 일괄적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/storeProdBatchRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 상품삭제 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDeleteView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 상품등록 및 삭제 비밀번호 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodAddDelPw.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCdCheck.jsp">
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCdCheck2.jsp">
</c:import>