<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div id="selectMenuArea" class="subCon" ng-hide="isManageTab" ng-controller="sideMenuManageCtrl">

        <table class="searchTbl" style="border-top:1px solid #ddd">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 분류조회 --%>
                <th><s:message code="sideMenu.manage.srchProdClass" /></th>
                <td>
                    <input type="text" class="sb-input w70" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;" placeholder="<s:message code="sideMenu.manage.srchProdClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <%-- 브랜드 --%>
                <c:if test="${orgnFg == 'HQ' and brandUseFg == '1'}"><%-- 브랜드를 사용하는 본사/매장인 경우만 변경 가능 --%>
                    <th><s:message code="sideMenu.manage.brand" /></th>
                    <td>
                        <div class="sb-select w70">
                            <wj-combo-box
                                    id="srchHqBrand"
                                    ng-model="hqBrandCd"
                                    items-source="_getComboData('srchHqBrand')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchHqBrandCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
            </tr>
            <tr>
                <%-- 상품코드 --%>
                <th>
                    <s:message code="sideMenu.manage.prodCd"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="prodCd"/>
                </td>
                <%-- 상품명 --%>
                <th>
                    <s:message code="sideMenu.manage.prodNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="prodNm"/>
                </td>
            </tr>
            <tr>
                <%-- 사이드메뉴여부 --%>
                <th>
                    <s:message code="sideMenu.manage.sideProdYn"/>
                </th>
                <td>
                    <div class="sb-select w70">
                        <wj-combo-box id="srchSideProdYn"
                            ng-model="sideProdYn"
                            items-source="_getComboData('sideProdYnComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchSideProdYnCombo"
                            selected-index-changed="setSdattrClassCd(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <td colspan="2">
                    <div style="float: left;"><input type="checkbox" id="chkSdattrClassNone"/></div>
                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="sideMenu.manage.attrNo"/></label></div>
                    <div style="float: left;"><input type="checkbox" id="chkSdSelGrpNone"/></div>
                    <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="sideMenu.manage.selectMenuNo" /></label></div>
                </td>
            </tr>
            <tr id="trAttrSelectMenu">
                <%-- 속성 --%>
                <th>
                    <s:message code="sideMenu.manage.attr"/>
                </th>
                <td>
                    <div class="sb-select w70">
                        <wj-combo-box id="srchSdattrClassCd"
                                ng-model="sdattrClassCd"
                                items-source="_getComboData('sdattrClassCdComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchSdattrClassCdCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 선택메뉴 --%>
                <th>
                    <s:message code="sideMenu.manage.selectMenu"/>
                </th>
                <td>
                    <%-- 선택메뉴 선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/application/layer/searchSdSelGrp.jsp" flush="true">
                        <jsp:param name="targetId" value="sdselGrp"/>
                    </jsp:include>
                    <%--// 선택메뉴 선택 모듈 사용시 include --%>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일 --%>
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
            <%-- 저장 --%>
            <button class="btn_blue ml5 fr" ng-click="prodBatchSave()"><s:message code="cmm.save" /></button>
            <%-- 프랜차이즈 매장일때만 --%>
            <c:if test="${orgnFg eq 'STORE' and hqOfficeCd ne '00000'}">
                <%-- 상품등록구분이 '본사'인 상품은 수정할 수 없습니다. --%>
                <div class="s14 bk lh25 ml5 mr15 fr">
                    <s:message code="prodBatchChange.regFgHqBlank" />
                </div>
            </c:if>
        </div>

        <%-- 일괄적용 --%>
        <table class="searchTbl mt10" style="border-top:1px solid #ddd">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
                <col/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 사이드메뉴여부 --%>
               <th>
                    <s:message code="sideMenu.manage.sideProdYn"/>
               </th>
                <td>
                    <div class="sb-select w70" style="float:left;">
                        <wj-combo-box
                                ng-model="chgSideProdYn"
                                items-source="_getComboData('chgSideProdYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="chgSideProdYnCombo">
                        </wj-combo-box>
                    </div>
                    <a href="#" class="btn_grayS ml5" style="padding:0 8px;" ng-click="prodBatchChange('chgSideProdYn')"><s:message code="sideMenu.manage.batchChange"/></a>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <%-- 속성 --%>
                <th>
                    <s:message code="sideMenu.manage.attr"/>
                </th>
                <td>
                    <div class="sb-select w70" style="float:left;">
                        <wj-combo-box
                                ng-model="chgSdattrClassCd"
                                items-source="_getComboData('chgSdattrClassCd')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="chgSdattrClassCdCombo">
                        </wj-combo-box>
                    </div>
                    <a href="#" class="btn_grayS ml5" style="padding:0 8px;" ng-click="prodBatchChange('chgSdattrClass')"><s:message code="sideMenu.manage.batchChange"/></a>
                </td>
                <%-- 선택메뉴 --%>
                <th>
                    <s:message code="sideMenu.manage.selectMenu"/>
                </th>
                <td>
                    <%-- 선택메뉴 선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/application/layer/searchSdSelGrp.jsp" flush="true">
                        <jsp:param name="targetId" value="chgSdselGrp"/>
                    </jsp:include>
                    <%--// 선택메뉴 선택 모듈 사용시 include --%>
                     <a href="#" class="btn_grayS" style="padding:0 8px;" ng-click="prodBatchChange('chgSdselGrp')"><s:message code="sideMenu.manage.batchChange"/></a>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:280px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridSelectMenuArea">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>

                    <%-- 프랜차이즈 매장일때만 --%>
                    <c:if test="${orgnFg eq 'STORE' and hqOfficeCd ne '00000'}">
                        <wj-flex-grid-column header="<s:message code="prodBatchChange.regFg"/>" binding="regFg" data-map="regFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    </c:if>

                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.prodClassNm"/>" binding="pathNm" width="300" is-read-only="true" align="left"></wj-flex-grid-column>

                    <%-- 브랜드를 사용하는 본사/매장인 경우만 변경 가능 --%>
                    <c:if test="${orgnFg == 'HQ' and brandUseFg == '1'}">
                        <wj-flex-grid-column header="<s:message code="sideMenu.manage.brand"/>" binding="hqBrandNm" is-read-only="true" width="100" align="center"></wj-flex-grid-column>
                    </c:if>

                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.saleProdYn"/>" binding="saleProdYn" data-map="saleProdYnDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.orgplceCd"/>" binding="orgplceCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.stockProdYn"/>" binding="stockProdYn" data-map="stockProdYnDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.vatFg"/>" binding="vatFg" data-map="vatFgDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.sideProdYn"/>" binding="sideProdYn" data-map="sideProdYnDataMap"  width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.attr"/>" binding="sdattrClassCd" data-map="sdattrClassDataMap" width="150" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.selectMenu"/>" binding="sdselGrpCd" data-map="sdselGrpDataMap" width="150" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.useYn"/>" binding="useYn" width="100" data-map="useYnDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.poProdFg"/>" binding="poProdFg" data-map="poProdFgDataMap" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.manage.prodTipYn"/>" binding="prodTipYn" data-map="prodTipYnDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>


                </wj-flex-grid>
            </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="sideMenuManageCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
</div>

<style type="text/css">
    input[type=checkbox]  {
        width: 17px;
        height: 17px;
    }
</style>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";

    // 브랜드
    var brandList = ${brandList};
    // 속성
    var sdattrClassList = ${sdattrClassList};
    // 선택메뉴
    var sdselGrpList = ${sdselGrpList};

    <%-- 사이드메뉴여부 (검색조건용)--%>
    var sideProdYnData = ${ccu.getCommCode("067")};
    <%-- 과세여부 구분 --%>
    var vatFgData = ${ccu.getCommCodeExcpAll("039")};
    <%-- 판매상품여부, 재고상품여부 구분, 사이드메뉴여부, 사용여부, 상품봉사료여부 --%>
    var useYnData = ${ccu.getCommCodeExcpAll("067")};
    <%-- 발주상품 구분 --%>
    var poProdFgData = ${ccu.getCommCodeExcpAll("092")};
    if(orgnFg === "STORE"){
        poProdFgData.shift();
        poProdFgData.shift();
        poProdFgData.shift();
    }

</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenuManage.js?ver=20220614.01" charset="utf-8"></script>