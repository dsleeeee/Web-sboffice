<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="recpOriginInfoView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="recpOriginInfoCtrl">
        <%--searchTbl--%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="recpOriginTab.recpOriginInfo"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue mr3" id="nxBtnSearch3" ng-click="_pageView('recpOriginInfoCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <table class="searchTbl">
            <colgroup>
                <col class="w10" />
                <col class="w40" />
                <col class="w10" />
                <col class="w40" />
            </colgroup>
            <tbody>
            <tr>
                <td style="border-left: 1px solid #ccc;"></td>
                <td></td>
                <td></td>
                <td>
                    <div class="mr10 fr">
                        <button class="btn_skyblue" id="btnRegStore" ng-click="regStore()">
                        <s:message code="prodRecpOriginInfo.regStore" />
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <%--//searchTbl--%>

        <%-- left --%>
        <div class="wj-TblWrap mt10 mb20 w40 fl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:530px;">
                <s:message code="recpOriginInfo.originCdPrt"/>
                <div class="updownSet oh mb10 mt10">
                    <button class="btn_skyblue" id="btnRecpOriginInfoAdd" ng-click="addRowInfo()"><s:message code='cmm.add' /></button>
                    <button class="btn_skyblue" id="btnRecpOriginInfoSave" ng-click="saveInfo()"><s:message code='cmm.save' /></button>
                    <button class="btn_skyblue" id="btnRecpOriginInfoDel" ng-click="delInfo()"><s:message code='cmm.del' /></button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:450px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                    autoGenerateColumns.="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="recpOriginInfo.originCd"/>" binding="originCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="recpOriginInfo.originNm"/>" binding="originNm" width="100" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt10 mb10 w60 fr" ng-controller="recpOriginInfoDetailCtrl">
        <div class="wj-TblWrapBr pd10" style="height:530px; overflow-y: hidden;">
            <s:message code="recpOriginInfo.regInfo"/>
            <div class="updownSet oh mb10">
                <button class="btn_skyblue" id="btnRecpOriginInfoDetailSave" ng-click="saveText()"><s:message code='cmm.save' /></button>
            </div>
            <div class="tc b4" style="background-color:#ebf5ff">
                <table class="tblType01">
                    <colgroup>
                        <col class="w100"/>
                    </colgroup>
                    <tbody>
                    <th class="tc">
                        <label id="originCdNm" readonly>
                        </label>
                    </th>
                    <tr>
                        <td class="lh30">
                            <textarea id="recpOriginInfoDetail" class="w100" cols="42" style="height:400px;resize:none;"></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    var hqOfficeCd = "${hqOfficeCd}";
    var orgnFg = "${orgnFg}";

    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    var subPriceFg = "${subPriceFg}";
    var coercionFg = "${coercionFg}";

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
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/recpOrigin/recpOriginInfo.js?ver=20250203.01" charset="utf-8"></script>

<%-- 매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/recpOrigin/originInfoRegStore.jsp">
</c:import>