<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" ng-controller="dlvrProdNmMappingCtrl">
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('dlvrProdNmMappingCtrl',1)">
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
        <%-- 등록일자 --%>
        <tr>
            <th><s:message code="dlvrProd.regDate" /></th>
            <td colspan="3" style="text-align: left;">
                <div class="sb-select">
                    <span class="txtIn w100px">
                        <wj-combo-box
                            id="regDtType"
                            ng-model="regDtType"
                            control="regDtTypeCombo"
                            items-source="_getComboData('regDtType')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                    <span class="txtIn w110px">
                      <wj-input-date
                              id="srchTimeStartDate"
                              value="startDate"
                              ng-model="startDate"
                              control="startDateCombo"
                              min="2000-01-01"
                              max="2099-12-31"
                              initialized="_initDateBox(s)">
                      </wj-input-date>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn w110px">
                      <wj-input-date
                              id="srchTimeEndDate"
                              value="endDate"
                              ng-model="endDate"
                              control="endDateCombo"
                              min="2000-01-01"
                              max="2099-12-31"
                              initialized="_initDateBox(s)">
                      </wj-input-date>
                    </span>
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
            <th><s:message code="dlvrProd.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="dlvrProd.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="dlvrProd.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="dlvrProd.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th><s:message code="dlvrProd.barCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 사용여부 --%>
            <th><s:message code="dlvrProd.useYn" /></th>
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
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 채널사상품명 --%>
            <th><s:message code="dlvrProd.channel.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchChannelProdNm" ng-model="channelProdNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>
    <%--//searchTbl--%>

    <%--엑셀업로드 관련 comment --%>
    <table class="searchTbl mt10">
        <colgroup>
            <col class="w100" />
        </colgroup>
        <tbody>
        <tr class="brt">
            <th class="oh gr fl w50" style="height: 75px;">
                <p class="s12 bk lh20">
                    * 상품코드와 정상 매핑된 명칭만 저장됩니다.<br />
                    * 매핑명칭은 최대 30자 입니다.(30자 이상 인 경우, 잘라서 저장됩니다.)<br />
                    * 상품코드에 공백이 들어가지 않도록 주의하세요.<br />
                </p>
            </th>
            <th class="oh gr fr w50" style="height: 75px; font-size:15px">
                <%-- 엑셀업로드 --%>
                <button class="btn_skyblue ml5 fr" id="btnExcelUpload" ng-click="excelUpload()">
                    <s:message code="cmm.excel.excelUpload" />
                </button>
                <%-- 양식다운로드 --%>
                <button class="btn_skyblue ml5 fr" id="btnExcelDownload" ng-click="excelDownload()">
                    <s:message code="cmm.excel.sampleDown" />
                </button>
            </th>
        </tr>
        </tbody>
    </table>

    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <table class="searchTbl mt10">
            <colgroup>
                <col class="w100" />
            </colgroup>
            <tbody>
            <tr class="brt">
                <th class="oh gr fl w50" style="height: 40px;">
                    <p class="s12 bk lh20">
                        * 상품 체크 후, '상품명칭 매장적용'을 클릭해주세요.<br />
                    </p>
                </th>
                <th class="oh gr fr w50" style="height: 40px; font-size:15px">
                    <%-- 상품명칭 매장적용 --%>
                    <button class="btn_skyblue ml5 fr" id="btnStoreApply" ng-click="storeApply()">
                        <s:message code="dlvrProd.dlvrProdNmStoreRegist" />
                    </button>
                </th>
            </tr>
            </tbody>
        </table>
    </c:if>

    <div id="grid" class="w100">
        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="listScale"
                    items-source="_getComboData('listScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="initComboBox(s)">
            </wj-combo-box>
            <%--// 페이지 스케일  --%>

            <%-- 전체 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" id="btnExcelDownloadTotal" ng-click="excelDownloadTotal()">
                <s:message code="cmm.excel.downTotal" />
            </button>
            <%-- 매장환경 복사 - 본사/프랜차이즈 매장만 사용 --%>
            <button class="btn_skyblue ml5 fr"  id="copyBtn" ng-click="copyDlvrProdNm()" <c:if test="${orgnFg eq 'STORE' and hqOfficeCd eq '00000'}">style="display: none;"</c:if>>
                <s:message code="dlvrProd.nmCopy" />
            </button>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()">
                <s:message code="cmm.save" />
            </button>
        </div>
        <div class="wj-gridWrap mt10" style="height:370px; overflow-y: hidden;">
            <div class="row">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                    <!-- define columns -->
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="dlvrProd.prodCd"/>" binding="prodCd" width="100" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrProd.prodNm"/>" binding="prodNm" width="200" is-read-only="true"></wj-flex-grid-column>
                    <%-- 배달앱 구분코드 컬럼 생성--%>
                    <c:forEach var="dlvrCol" items="${dlvrColList}">
                        <wj-flex-grid-column header="${dlvrCol.dlvrNm}[${dlvrCol.dlvrCd}]" binding="dlvrProdNm${dlvrCol.dlvrCd}" width="150" max-length=30></wj-flex-grid-column>
                    </c:forEach>

                </wj-flex-grid>
            </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum2 mt10">
            <%-- id --%>
            <ul id="dlvrProdNmMappingCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>

        <%--엑셀 리스트--%>

        <div class="wj-gridWrap mt10" style="height:370px; overflow-y: hidden; display: none;" ng-controller="dlvrProdNmMappingExcelCtrl">
            <div class="row">
                <wj-flex-grid
                        id="wjGridExcelList"
                        autoGenerateColumns="false"
                        control="excelFlex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dlvrProd.prodCd"/>" binding="prodCd" width="100" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrProd.prodNm"/>" binding="prodNm" width="200" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrProd.useYn"/>" binding="useYn" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <%-- 배달앱 구분코드 컬럼 생성--%>
                    <c:forEach var="dlvrCol" items="${dlvrColList}">
                        <wj-flex-grid-column header="${dlvrCol.dlvrNm}[${dlvrCol.dlvrCd}]" binding="dlvrProdNm${dlvrCol.dlvrCd}" width="150" max-length=30></wj-flex-grid-column>
                    </c:forEach>

                </wj-flex-grid>
            </div>
        </div>

        <%--//엑셀 리스트--%>

    </div>
</div>

<script type="text/javascript">
    var dlvrCol = '${dlvrCol}';
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/dlvrProd/dlvrProdNmMapping.js?ver=20231013.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품명칭복사 --%>
<c:import url="/WEB-INF/view/base/prod/dlvrProd/copyDlvrProdNm.jsp">
</c:import>

<%-- 상품명칭 엑셀업로드 --%>
<c:import url="/WEB-INF/view/base/prod/dlvrProd/excelUploadDlvrProdNm.jsp">
</c:import>

<%-- 상품명칭 매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/dlvrProd/dlvrProdNmStoreRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>