<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon" id="setProdView">
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="setProd.setProd" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('setProdCtrl',1)" >
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w10" />
            <col class="w20" />
            <col class="w10" />
            <col class="w20" />
            <col class="w10" />
            <col class="w20" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="prod.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="setProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="prod.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="setProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 사용여부 --%>
            <th><s:message code="prod.useYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="useYn"
                            ng-model="useYn"
                            control="useYnAllCombo"
                            items-source="_getComboData('useYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index="2">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 좌측 --%>
    <div class="w50 fl">
        <%-- 좌측 상단 --%>
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr pd5" style="height: 260px;" ng-controller="setProdCtrl">
                <%--위즈모 테이블--%>
                <div class="updownSet oh mb5">
                    <span class="fl bk lh30"><s:message code='setProd.prod' /></span>
<%--                    <button class="btn_skyblue" id="btnSetProdSave" ng-click="setProdSave()" >--%>
<%--                        <s:message code="cmm.save" />--%>
<%--                    </button>--%>
                </div>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:200px">
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
                        <wj-flex-grid-column header="<s:message code="setProd.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.useYn"/>" binding="useYn" width="70" is-read-only="true" align="center" data-map="useYnDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.sideProdYn"/>" binding="sideProdYn" width="100" is-read-only="true" align="center" data-map="useYnDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.sdselGrpCd"/>" binding="sdselGrpCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.sdselGrpNm"/>" binding="sdselGrpNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.regSdselGrp"/>" binding="regSdselGrp" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.nSideProdYn"/>" binding="nSideProdYn" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.regSdselGrpSingle"/>" binding="regSdselGrpSingle" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="setProd.sdselTypeFg"/>" binding="sdselTypeFg" width="70" is-read-only="true" align="center" data-map="sdselTypeFgDataMap"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
        <%-- //좌측 상단 --%>
        <%-- 좌측 하단 --%>
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr pd5" style="height: 260px;" ng-controller="setProeSideMenuSelectGroupCtrl">
                <div class="updownSet oh mb5">
                    <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselGrp' /><span id="setProd"></span></span>
                    <button style="display:none" class="btn_skyblue" id="btnAddSelGroup" ng-click="addRow()" >
                        <s:message code="cmm.add" />
                    </button>
                    <button style="display:none" class="btn_skyblue" id="btnDelSelGroup" ng-click="deleteRow()" >
                        <s:message code="cmm.delete" />
                    </button>
                    <button style="display:none" class="btn_skyblue" id="btnSaveSelGroup" ng-click="save()" >
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:200px">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true"
                            id="wjGridSelGroupList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="70" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="100"></wj-flex-grid-column>
                        <c:if test="${brandUseFg == '1'}">
                            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                                <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.brand"/>" binding="hqBrandCd" data-map="brandDataMap" width="80"></wj-flex-grid-column>
                            </c:if>
                        </c:if>
                        <c:if test="${orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" data-map="fixProdFgDataMap" width="50"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselTypeFg"/>" binding="sdselTypeFg" data-map="sdselTypeFgDataMap" width="70" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
        <%-- //좌측 하단 --%>
    </div>
    <%-- //좌측 --%>
    <%-- 우측 --%>
    <div class="w50 fl">
        <%-- 우측 상단 --%>
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr pd5" style="height: 260px;" ng-controller="setProeSideMenuSelectClassCtrl">
                <div class="updownSet oh mb10" style="height:60px;">
                    <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselClass' /><span id="sideSelectGroupTitle"></span></span>
                    <br>
                    <br>
                    <%-- 선택분류복사 --%>
                    <button style="display:none" class="btn_skyblue" id="btnSdselClassCopy" ng-click="sdselClassCopy()" >
                        <s:message code="sideMenu.selectMenu.sdselClassCopy" />
                    </button>
                    <button style="display:none" class="btn_up" id="btnUpSelClass" ng-click="rowMoveUp()" >
                        <s:message code="cmm.up" />
                    </button>
                    <button style="display:none" class="btn_down" id="btnDownSelClass" ng-click="rowMoveDown()" >
                        <s:message code="cmm.down" />
                    </button>
                    <button style="display:none" class="btn_skyblue" id="btnAddSelClass" ng-click="addRow()" >
                        <s:message code="cmm.add" />
                    </button>
                    <button style="display:none" class="btn_skyblue" id="btnDelSelClass" ng-click="deleteRow()" >
                        <s:message code="cmm.delete" />
                    </button>
                    <button style="display:none" class="btn_skyblue" id="btnSaveSelClass" ng-click="saveClass()" >
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                    <div class="updownSet oh mb10">
                            <%-- 적용매장등록 --%>
                        <button style="display:none" class="btn_skyblue" id="btnSdselClassRegStore" ng-click="sdselClassRegStore()">
                            <s:message code="sideMenu.selectMenu.sdselClassRegStore" />
                        </button>
                    </div>
                </c:if>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:130px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true"
                            id="wjGridSelClassList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassCd"/>" binding="sdselClassCd" width="70" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassNm"/>" binding="sdselClassNm" width="*"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.requireYn"/>" binding="requireYn" data-map="requireYnDataMap" width="85" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="sdselQty" width="50" max-length="3"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="fixProdCnt" width="*" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                        <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.oldRegStoreFg"/>" binding="oldRegStoreFg" data-map="oldRegStoreFgDataMap" width="85" visible="false"></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
        <%-- //우측 상단 --%>
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr pd5" style="height: 260px;" ng-controller="setProeSideMenuSelectProdCtrl">
                <%-- 선택한 선택그룹의 브랜드 코드 --%>
                <input type="hidden" id="hdSdselGrpBrandCd" />
                <div class="updownSet oh mb5">
                    <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselProd' /><span id="sideClassTitle"></span> </span>
                    <button style="display:none" class="btn_up" id="btnUpSelProd" ng-click="rowMoveUp()">
                        <s:message code="cmm.up" />
                    </button>
                    <button style="display:none" class="btn_down" id="btnDownSelProd" ng-click="rowMoveDown()">
                        <s:message code="cmm.down" />
                    </button>
                    <button style="display:none" class="btn_skyblue" id="btnAddSelProd" ng-click="addRow()">
                        <s:message code="cmm.add" />
                    </button>
                    <button style="display:none" class="btn_skyblue" id="btnDelSelProd" ng-click="deleteRow()">
                        <s:message code="cmm.delete" />
                    </button>
                    <button style="display:none" class="btn_skyblue" id="btnSaveSelProd" ng-click="saveProd()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                    <div class="updownSet oh mb10">
                        <%-- 적용매장등록 --%>
                        <button style="display:none" class="btn_skyblue" id="btnSdselProdRegStore" ng-click="sdselProdRegStore()">
                            <s:message code="sideMenu.selectMenu.sdselClassRegStore" />
                        </button>
                    </div>
                </c:if>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:160px;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridSelProdList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                        <c:if test="${brandUseFg == '1'}">
                            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                                <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.brand"/>" binding="hqBrandCd" data-map="brandDataMap" width="80" is-read-only="true"></wj-flex-grid-column>
                            </c:if>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodNm"/>" binding="prodNm" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdUprc"/>" binding="addProdUprc" width="50"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdQty"/>" binding="addProdQty" width="50" max-length="3"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" width="50" data-map="fixProdFgDataMap"></wj-flex-grid-column>
                        <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.oldRegStoreFg"/>" binding="oldRegStoreFg" data-map="oldRegStoreFgDataMap" width="85" visible="false"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.printYn"/>" binding="printYn" data-map="printYnDataMap" width="70" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>
    <%-- //우측 --%>
</div>

<script>
    var srchUseYn = ${ccu.getCommCode("067")};
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";

    <%--  var prodEnvstVal = "${prodEnvstVal}";--%>

    // [1014 포스프로그램구분] 환경설정값
    var posVerEnvstVal = "${posVerEnvstVal}";
    // [1261 필수선택사용여부] 환경설정값
    var requireYnEnvstVal = "${requireYnEnvstVal}";

    var momsEnvstVal = "${momsEnvstVal}"; // [1250 맘스터치] 환경설정값
    var branchCdComboList = ${branchCdComboList};
    var brandList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/setProd/setProd.js?ver=20230904.02" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품선택 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuProdView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택분류복사 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenu/sdselClassCopy.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택분류 적용매장등록 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenu/sdselClassRegStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택상품 적용매장등록 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenu/sdselProdRegStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>