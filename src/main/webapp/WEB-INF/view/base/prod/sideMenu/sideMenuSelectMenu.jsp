<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div id="selectMenuArea" class="wj-TblWrap mt5 ng-cloak" ng-hide="isSelectMenuTab">

    <table class="searchTbl mb5" style="border-top:1px solid #ddd">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
            <tr>
                <th>
                    <div class="sb-select w100">
                        <wj-combo-box
                            id="srchTypeSelGroup"
                            items-source="_getComboData('srchTypeSelGroup')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchTypeSelGroupCombo">
                        </wj-combo-box>
                    </div>
                </th>
                <td>
                    <input type="text" id="txtSelGroup" class="fl sb-input w100" onkeyup="fnNxBtnSearch(1);"/>
                </td>
                <th>
                    <div class="sb-select w100">
                        <wj-combo-box
                            id="srchTypeSelProd"
                            items-source="_getComboData('srchTypeSelProd')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchTypeSelProdCombo">
                        </wj-combo-box>
                    </div>
                </th>
                <td>
                    <input type="text" id="txtSelProd" class="fl sb-input w100" onkeyup="fnNxBtnSearch(1);"/>
                </td>
            </tr>
        </tbody>
    </table>

    <%-- 좌측 --%>
    <div class="w40 fl">
        <%-- 좌측 상단 --%>
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr pd5" style="height: 240px;" ng-controller="sideMenuSelectGroupCtrl">
                <div class="updownSet oh mb5">
                    <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselGrp' /></span>
                    <button class="btn_skyblue" id="btnAddSelGroup" ng-click="addRow()" >
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnDelSelGroup" ng-click="deleteRow()" >
                        <s:message code="cmm.delete" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveSelGroup" ng-click="save()" >
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:170px">
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
                        <c:if test="${orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" data-map="fixProdFgDataMap" width="50"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselTypeFg"/>" binding="sdselTypeFg" data-map="sdselTypeFgDataMap" width="70" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
        <%-- //좌측 상단 --%>
        <%-- 좌측 하단 --%>
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr pd5" style="height: 260px;" ng-controller="sideMenuSelectClassCtrl">
                <div class="updownSet oh mb10" style="height:60px;">
                    <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselClass' /><span id="sideSelectGroupTitle"></span></span>
                    <br>
                    <br>
                    <%-- 선택분류복사 --%>
                    <button class="btn_skyblue" id="btnSdselClassCopy" ng-click="sdselClassCopy()" >
                        <s:message code="sideMenu.selectMenu.sdselClassCopy" />
                    </button>
                    <button class="btn_up" id="btnUpSelClass" ng-click="rowMoveUp()" >
                        <s:message code="cmm.up" />
                    </button>
                    <button class="btn_down" id="btnDownSelClass" ng-click="rowMoveDown()" >
                        <s:message code="cmm.down" />
                    </button>
                    <button class="btn_skyblue" id="btnAddSelClass" ng-click="addRow()" >
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnDelSelClass" ng-click="deleteRow()" >
                        <s:message code="cmm.delete" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveSelClass" ng-click="saveClass()" >
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                    <div class="updownSet oh mb10">
                        <%-- 적용매장등록 --%>
                        <button class="btn_skyblue" id="btnSdselClassRegStore" ng-click="sdselClassRegStore()">
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
                        <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.oldRegStoreFg"/>" binding="oldRegStoreFg" data-map="oldRegStoreFgDataMap" width="85" visible="false"></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
        <%-- //좌측 하단 --%>
    </div>
    <%-- //좌측 --%>
    <%-- 우측 --%>
    <div class="w60 fl">
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr ml10 pd5" style="height: 500px;" ng-controller="sideMenuSelectProdCtrl">
                <div class="updownSet oh mb5">
                    <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselProd' /><span id="sideClassTitle"></span> </span>
                    <button class="btn_up" id="btnUpSelProd" ng-click="rowMoveUp()">
                        <s:message code="cmm.up" />
                    </button>
                    <button class="btn_down" id="btnDownSelProd" ng-click="rowMoveDown()">
                        <s:message code="cmm.down" />
                    </button>
                    <button class="btn_skyblue" id="btnAddSelProd" ng-click="addRow()">
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnDelSelProd" ng-click="deleteRow()">
                        <s:message code="cmm.delete" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveSelProd" ng-click="saveProd()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                    <div class="updownSet oh mb10">
                        <%-- 적용매장등록 --%>
                        <button class="btn_skyblue" id="btnSdselProdRegStore" ng-click="sdselProdRegStore()">
                            <s:message code="sideMenu.selectMenu.sdselClassRegStore" />
                        </button>
                    </div>
                </c:if>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:370px;">
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
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>
    <%-- //우측 --%>
</div>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenuSelectMenu.js?ver=20230706.02" charset="utf-8"></script>

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