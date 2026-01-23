<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div id="optionMenuDlvrArea" class="wj-TblWrap mt5 ng-cloak" ng-hide="isOptionMenuDlvrTab">

    <table class="searchTbl mb5" style="border-top:1px solid #ddd">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <th>
                <div class="sb-select w100">
                    <wj-combo-box
                            id="srchTypeSelGroup3"
                            items-source="_getComboData('srchTypeSelGroup3')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchTypeSelGroup3Combo">
                    </wj-combo-box>
                </div>
            </th>
            <td>
                <input type="text" id="txtSelGroup3" class="fl sb-input w100"/>
            </td>
            <th>
                <div class="sb-select w100">
                    <wj-combo-box
                            id="srchTypeSelProd3"
                            items-source="_getComboData('srchTypeSelProd3')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchTypeSelProd3Combo">
                    </wj-combo-box>
                </div>
            </th>
            <td>
                <input type="text" id="txtSelProd3" class="fl sb-input w100"/>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 좌측 --%>
    <div class="w40 fl">
        <%-- 좌측 상단 --%>
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr pd5" style="height: 240px;" ng-controller="optionMenuDlvrSelectGroupCtrl">
                <div class="updownSet oh mb5">
                    <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselGrp' /></span>
                    <button class="btn_skyblue" id="orderkitGoto" ng-click="orderkitGoto()" <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if>>
                        <s:message code="sideMenu.selectMenu.orderkitGoto" />
                    </button>
                </div>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:180px">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridSelGroup3List">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="70" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="200" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" data-map="fixProdFgDataMap" width="50" is-read-only="true"></wj-flex-grid-column>
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
            <div class="wj-TblWrapBr pd5" style="height: 260px;" ng-controller="optionMenuDlvrSelectClassCtrl">
                <div class="updownSet oh mb10" style="height:60px;">
                    <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselClass' /><span id="optionMenuDlvrSelectGroupTitle"></span></span>
                </div>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:140px;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridSelClass3List">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassCd"/>" binding="sdselClassCd" width="70" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassNm"/>" binding="sdselClassNm" width="200" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.requireYn"/>" binding="requireYn" data-map="requireYnDataMap" width="85" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="sdselQty" width="50" max-length="3" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
        <%-- //좌측 하단 --%>
    </div>
    <%-- 우측 --%>
    <div class="w60 fl">
        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr ml10 pd5" style="height: 500px;" ng-controller="optionMenuDlvrSelectProdCtrl">
                <div class="updownSet oh mb5">
                    <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselProd' /><span id="optionMenuDlvrSelectClassTitle"></span> </span>
                    <button class="btn_skyblue" id="btnSaveSelProd3" ng-click="saveProd()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:450px;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridSelProd3List">

                        <!-- define columns -->
                        <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodNm"/>" binding="prodNm" width="200" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdUprc"/>" binding="addProdUprc" width="50" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdQty"/>" binding="addProdQty" width="50" max-length="3" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" width="50" data-map="fixProdFgDataMap" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.printYn"/>" binding="printYn" data-map="printYnDataMap" width="70" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>

</div>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var useYnData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/optionMenuDlvr.js?ver=20260122.01" charset="utf-8"></script>