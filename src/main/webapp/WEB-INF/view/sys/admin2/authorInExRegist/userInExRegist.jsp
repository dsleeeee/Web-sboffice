<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="userInExRegistView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="userInExRegistCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="authorInExRegist.userInExRegist" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue mr3" id="nxBtnSearch1"  ng-click="_pageView('userInExRegistCtrl',1)">
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
            <tr>
                <%-- 사용자ID --%>
                <th>
                    <s:message code="authorInExRegist.userId" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 사용자명 --%>
                <th>
                    <s:message code="authorInExRegist.userNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchUserNm" ng-model="userNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- left --%>
        <div class="wj-TblWrap mt10 mb20 w30 fl">
            <div class="wj-TblWrapBr pd10" style="height:600px;">
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:500px; overflow-y: hidden; overflow-x: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.userId"/>"           binding="userId"            width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.userNm"/>"           binding="userNm"            width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.authGrpCd"/>"        binding="authGrpCd"         width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.orgnFg"/>"           binding="orgnFg"            width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.orgnFg2"/>"          binding="orgnFg2"           width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.orgnGrpCd"/>"        binding="orgnGrpCd"         width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.orgnGrpNm"/>"        binding="orgnGrpNm"         width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.orgnCd"/>"           binding="orgnCd"            width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.orgnNm"/>"           binding="orgnNm"            width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.storeCd"/>"          binding="storeCd"           width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.storeNm"/>"          binding="storeNm"           width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.storeSysStatFg"/>"   binding="storeSysStatFg"    width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.hqOfficeCd"/>"       binding="hqOfficeCd"        width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.hqOfficeNm"/>"       binding="hqOfficeNm"        width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.hqSysStatFg"/>"      binding="hqSysStatFg"       width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.empNo"/>"            binding="empNo"             width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.lastLoginDt"/>"      binding="lastLoginDt"       width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.lastPwdChgDt"/>"     binding="lastPwdChgDt"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.loginFailCnt"/>"     binding="loginFailCnt"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.userStatFg"/>"       binding="userStatFg"        width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.pAgencyCd"/>"        binding="pAgencyCd"         width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.areaFg"/>"           binding="areaFg"            width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.vUserId"/>"          binding="vUserId"           width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.webUseYn"/>"         binding="webUseYn"          width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.serviceFg"/>"        binding="serviceFg"         width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.useYn"/>"            binding="useYn"             width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="authorInExRegist.webUserUseYn"/>"     binding="webUserUseYn"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%-- mid --%>
    <div class="wj-TblWrap mt10 mb20 w35 fl" ng-controller="regUserMenuCtrl">
        <div class="wj-TblWrapBr ml10 pd10" style="height:600px; overflow-y: hidden;">
            <div class="mb10" style="height: 15px;">
                <label id="lblRegUser" style="font-size: 15px;"></label>
            </div>
            <div class="updownSet">
                <%-- 삭제 --%>
                <button class="btn_skyblue" id="btnDeleteUserMenu" ng-click="deleteUserMenu()"><s:message code='cmm.del' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:500px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.resrceCd"/>"         binding="resrceCd"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.resrceNm"/>"         binding="resrceNm"      width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.incldExcldFg"/>"     binding="incldExcldFg"  width="100" is-read-only="true" align="center"  data-map="incldExcldDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.authorProdcFg"/>"    binding="authorProdcFg" width="100" is-read-only="true" align="center"  data-map="authorProdcFgDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.useYn"/>"            binding="useYn"         width="80"  is-read-only="true" align="center"  data-map="useYnDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.authGrpFg"/>"        binding="authGrpFg"     width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.exceptInExFg"/>"     binding="exceptInExFg"  width="80"  is-read-only="true" align="center"  data-map="incldExcldDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.orgnInExFg"/>"       binding="orgnInExFg"    width="80"  is-read-only="true" align="center"  data-map="incldExcldDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.remark"/>"           binding="remark"        width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.loginFg"/>"          binding="loginFg"       width="80"  is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%-- mid --%>

    <%-- right --%>
    <div class="wj-TblWrap mt10 mb20 w35 fr" ng-controller="noRegUserMenuCtrl">
        <div class="wj-TblWrapBr ml10 pd10" style="height:600px; overflow-y: hidden;">

            <table class="tblType01">
                <colgroup>
                    <col class="w13" />
                    <col class="w35" />
                    <col class="w13" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                    <tr>
                        <%-- 메뉴코드 --%>
                        <th><s:message code="authorInExRegist.resrceCd" /></th>
                        <td>
                            <input type="text" class="sb-input w100" ng-model="userResrceCd" />
                        </td>
                        <%-- 메뉴명 --%>
                        <th><s:message code="authorInExRegist.resrceNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" ng-model="userResrceNm" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 권한그룹여부 --%>
                        <th><s:message code="authorInExRegist.authGrpFg" /></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchUserAuthGrpFg"
                                            ng-model="userAuthGrpFg"
                                            items-source="_getComboData('userAuthGrpFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false">
                                    </wj-combo-box>
                                </div>
                            </td>
                    </tr>
                </tbody>
            </table>

            <div class="updownSet oh mb10 mt5">
                <%-- 조회 --%>
                <button class="btn_skyblue fr" ng-click="btnSearch()"><s:message code='cmm.search' /></button>
                <%-- 추가 --%>
                <button class="btn_skyblue fr mr5" id="btnAddUserMenu" ng-click="addUserMenu()"><s:message code='cmm.add' /></button>
                <%-- 권한처리구분 --%>
                <div class="sb-select fr w30 mr5">
                    <wj-combo-box
                            id="authorProdcFg"
                            ng-model="authorProdcFg"
                            items-source="_getComboData('authorProdcFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                </div>
                <%-- 포함제외구분 --%>
                <div class="sb-select fr w30 mr5">
                    <wj-combo-box
                            id="incldExcldFg"
                            ng-model="incldExcldFg"
                            items-source="_getComboData('incldExcldFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                </div>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:350px; overflow-x: hidden; overflow-y: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.resrceCd"/>"     binding="resrceCd"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.resrceNm"/>"     binding="resrceNm"      width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.authGrpFg"/>"    binding="authGrpFg"     width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.exceptInExFg"/>" binding="exceptInExFg"  width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.orgnInExFg"/>"   binding="orgnInExFg"    width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="authorInExRegist.useYn"/>"        binding="useYn"         width="80"  is-read-only="true" align="center"  visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt10">
                <%-- id --%>
                <ul id="noRegUserMenuCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>
    <%-- right --%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin2/authorInExRegist/userInExRegist.js?ver=20260417.01" charset="utf-8"></script>