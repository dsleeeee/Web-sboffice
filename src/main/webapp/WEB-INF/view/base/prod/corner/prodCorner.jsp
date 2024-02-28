<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="prodCornerCtrl">
    <%--searchTbl--%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prodCornerCtrl',1)">
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
            <%-- 상품코드 --%>
            <th><s:message code="prodCorner.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="prodCorner.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="prodCorner.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="prodCorner.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 코너 --%>
            <th><s:message code="prodCorner.corner" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchCornrCd"
                            ng-model="srchCornrCd"
                            items-source="_getComboData('srchCornrCd')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="srchCornrCdCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <%--//searchTbl--%>

    <table class="searchTbl mt10">
            <colgroup>
                <col class="w100" />
            </colgroup>
            <tbody>
            <tr class="brt">
                <th class="oh gr">
                    <%-- 조회할 코너 선택 --%>
<%--                    <div class="sb-select fl w5px mr5 mt10">--%>
<%--                        <span>조회할 코너 : </span>--%>
<%--                    </div>--%>
<%--                    <div class="sb-select w10 fl">--%>
<%--                        <wj-combo-box--%>
<%--                                id="srchCornrCd"--%>
<%--                                ng-model="srchCornrCd"--%>
<%--                                items-source="_getComboData('srchCornrCd')"--%>
<%--                                display-member-path="name"--%>
<%--                                selected-value-path="value"--%>
<%--                                is-editable="false"--%>
<%--                                initialized="_initComboBox(s)">--%>
<%--                        </wj-combo-box>--%>
<%--                    </div>--%>
                    <%-- 이동할 코너 선택 --%>
                    <div class="sb-select fl w5px mr5 mt10">
                        <span>이동할 코너 : </span>
                    </div>
                    <div class="sb-select w10 fl">
                        <wj-combo-box
                                id="cornrCd"
                                ng-model="cornrCd"
                                items-source="_getComboData('cornrCd')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                    <a href="#" class="btn_grayS ml10" ng-click="changeProdCorner()">이동</a>
                </th>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadTotal()"><s:message code="cmm.excel.downTotal"/></button>
        </div>

<%--    <div class="mt10 oh sb-select dkbr">--%>
<%--        &lt;%&ndash; 페이지 스케일  &ndash;%&gt;--%>
<%--        <wj-combo-box--%>
<%--                class="w100px fl"--%>
<%--                id="listScaleBox"--%>
<%--                ng-model="listScale"--%>
<%--                control="listScaleCombo"--%>
<%--                items-source="_getComboData('listScaleBox')"--%>
<%--                display-member-path="name"--%>
<%--                selected-value-path="value"--%>
<%--                is-editable="false"--%>
<%--                initialized="_initComboBox(s)">--%>
<%--        </wj-combo-box>--%>
<%--    </div>--%>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
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
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="" binding="storeCd" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="" binding="prodClassCd" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodCorner.prodClassLNm"/>" binding="prodClassNm" width="120" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodCorner.prodClassMNm"/>" binding="prodClassNm" width="120" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodCorner.prodClassSNm"/>" binding="prodClassNm" width="120" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodCorner.cornerCd"/>" binding="cornrCd" width="80" is-read-only="true" align="center" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodCorner.corner"/>" binding="cornrNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodCorner.prodCd"/>" binding="prodCd" width="120" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodCorner.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="prodCornerCtrl"/>
                </jsp:include>
            </div>
        </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="prodCornerCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="wj-TblWrapBr mt10" ng-controller="totalExcelCtrl" style="display: none;">
        <div class="wj-gridWrap" id="qn">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="prodCorner.prodClassLNm"/>" binding="prodClassNm" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodCorner.prodClassMNm"/>" binding="prodClassNm" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodCorner.prodClassSNm"/>" binding="prodClassNm" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodCorner.cornerCd"/>" binding="cornrCd" width="80" is-read-only="true" align="center" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodCorner.corner"/>" binding="cornrNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodCorner.prodCd"/>" binding="prodCd" width="120" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodCorner.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    <%--//엑셀 리스트--%>
    </div>

</div>
<script type="text/javascript">
    var cornerList = ${cornerList};
    var cornerList2 = ${cornerList2};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/corner/prodCorner.js?ver=20240228.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>