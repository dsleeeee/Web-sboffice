<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfodelete.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" ng-controller="barcdCtrl">
    <%--searchTbl--%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="barcd.prodBarcd" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('barcdCtrl',1)">
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
            <th><s:message code="barcd.regDate" /></th>
            <td colspan="3">
                <div class="sb-select">
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
            <th><s:message code="barcd.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
            </td>
            <%-- 상품명 --%>
            <th><s:message code="barcd.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="barcd.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="barcd.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th><s:message code="barcd.barCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" />
            </td>
        </tr>
        <tr>
            <%-- 사용여부 --%>
            <th><s:message code="barcd.prodUseYn" /></th>
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
                <%-- 바코드유무 --%>
            <th><s:message code="barcd.barcdYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchUseYn"
                            ng-model="barcdYn"
                            control="useYnAllCombo"
                            items-source="_getComboData('useYnAllComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <%-- 브랜드 --%>
        <c:if test="${orgnFg == 'HQ'}">
            <tr>
                <th><s:message code="barcd.brandNm" /></th>
                <td><input type="text" class="sb-input w100" id="srchBrandNm" ng-model="hqBrandNm" /></td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <%--//searchTbl--%>

    <div class="mt20 oh sb-select dkbr">
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
            <%-- 상품엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()" ><s:message code="cmm.excel.down"/></button>
            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()" ><s:message code="barcd.excelUpload"/></button>
            <%-- 입력양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()" ><s:message code="barcd.sampleDownload"/></button>
    </div>
    <div class="mt5 oh sb-select dkbr">
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" ng-click="save()" ><s:message code="cmm.save"/></button>
        <%-- 삭제 --%>
        <button class="btn_skyblue ml5 fr" ng-click="delete()" ><s:message code="cmm.delete"/></button>
        <%-- 바코드 자동 생성 --%>
<%--        <button class="btn_skyblue ml5 fr" ng-click="autoBarcd()" ><s:message code="barcd.autoBarcd"/></button>--%>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10" id="barcdGrid">
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div id="theGrid" style="height: 370px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.result"/>" binding="result" width="80" is-read-only="true" align="center" visible = "false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.prodCd"/>" binding="prodCd" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.barcdOld"/>" binding="barcdOld" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.barcdNew"/>" binding="barCd" width="*" format="d"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="barcdCtrlPager" data-size="10">
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
                <wj-flex-grid-column header="<s:message code="barcd.prodCd"/>" binding="prodCd" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.barcdOld"/>" binding="barcdOld" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.barcdNew"/>" binding="barcd" width="*" format="d"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%--//엑셀 리스트--%>
    </div>
</div>

<script>
    var useYn = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodBarcd/barcd.js?ver=20201224.05" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prodBarcd/prodDetailView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 레이어 팝업 : 엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodBarcd/excelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>