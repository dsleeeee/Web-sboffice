<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('pizzaEdgeCtrl', 1)" id="nxBtnSearch">
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
        </tbody>
    </table>

    <div id="gridPizzaEdge" class="w50 fl mt10" style="width: 33%" ng-controller="pizzaEdgeCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr mr10 pd10" style="height: 480px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='pizzaEdge.pizza' /></span>
            </div>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
                <div class="row" >
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
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.prod"/>"            binding="prodNm"        width="80"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.sideInfo"/>"        binding="sideInfo"      width="120" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselProdCd"/>"     binding="sdselProdCd"   width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselProdNm"/>"     binding="sdselProdNm"   width="120" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.prodCd"/>"          binding="prodCd"        width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.prodNm"/>"          binding="prodNm"        width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselGrpCd"/>"      binding="sdselGrpCd"    width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselGrpNm"/>"      binding="sdselGrpNm"    width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselClassCd"/>"    binding="sdselClassCd"  width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselClassNm"/>"    binding="sdselClassNm"  width="100" is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <div id="gridPizzaEdgeMapp" class="w50 fl mt10" style="width: 33%" ng-controller="pizzaEdgeMappCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr pd10 mr10" style="height: 480px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='pizzaEdge.regProd' /></span>
                <button class="btn_skyblue" id="btnDeleteMapp" style="display: none;" ng-click="delete()">
                    <s:message code="cmm.delete" />
                </button>
            </div>
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>"                   binding="gChk"                  width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sideInfo"/>"        binding="sideInfo"              width="120" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselProdCd"/>"     binding="mappingProdCd"         width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselProdNm"/>"     binding="mappingProdNm"         width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselGrpCd"/>"      binding="sdselGrpCd"            width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselGrpNm"/>"      binding="sdselGrpNm"            width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselClassCd"/>"    binding="mappingSdselClassCd"   width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselClassNm"/>"    binding="mappingSdselClassNm"   width="100" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <div id="gridPizzaEdgeNoReg" class="w50 fl mt10" style="width: 33%" ng-controller="pizzaEdgeNoRegCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr pd10" style="height: 480px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='pizzaEdge.noRegProd' /></span>
                <button class="btn_skyblue" id="btnSaveNoReg" style="display: none;" ng-click="save()">
                    <s:message code="pizzaEdge.reg" />
                </button>
            </div>
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.prodCd"/>"          binding="sdselProdCd"   width="100" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.prodNm"/>"          binding="sdselProdNm"   width="100" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sideInfo"/>"        binding="sideInfo"      width="120" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselProdCd"/>"     binding="sdselProdCd"   width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselProdNm"/>"     binding="sdselProdNm"   width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselGrpCd"/>"      binding="sdselGrpCd"    width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselGrpNm"/>"      binding="sdselGrpNm"    width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselClassCd"/>"    binding="sdselClassCd"  width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="pizzaEdge.sdselClassNm"/>"    binding="sdselClassNm"  width="100" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/pizzaEdge/pizzaEdge.js?ver=20250424.01" charset="utf-8"></script>
