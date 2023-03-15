<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 웹사이트 메뉴 권한  --%>
<div id="webMenuArea">
    <%-- 탭 --%>
    <div class="subTab2 mt20 mb10">
        <ul>
            <%-- 웹사이트 메뉴 --%>
            <li><a href="#" id="webMenu" class="on"><s:message code="hqEmp.webMenu" /></a></li>
            <%-- 모바일 메뉴 --%>
<%--            <li><a href="#" id="mobileMenu" onclick="changeMobileTab();"><s:message code="hqEmp.mobMenu" /></a></li>--%>
        </ul>
    </div>

    <%-- 웹사이트 메뉴 --%>
    <div id="webArea" ng-controller="hqEmpWebMenuCtrl">
        <%-- 권한복사 영역 --%>
        <table class="tblType01 moreDark mb10 mt10">
            <colgroup>
                <col class="w20" />
                <col class="w55" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 메뉴권한복사 --%>
                <th><s:message code="hqEmp.copy.authorExcept" /></th>
                <td colspan="3">
                    <div class="sb-select fl w60 mr10">
                        <wj-combo-box
                                id="empNoCombo"
                                ng-model="empNo"
                                control="empNoCombo"
                                items-source="_getComboData('empNoCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="setStoreCd(s)">
                        </wj-combo-box>
                    </div>
                    <%-- 메뉴권한복사 버튼  --%>
                    <div class="fl">
                        <a href="#" class="btn_grayS" id="btnCopyAuth" ng-click="copyAuth()"><s:message code="hqEmp.copy.auth" /></a>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <%-- 사용 메뉴 --%>
        <div class="oh mt10" style="padding-bottom: 10px;">
            <div class="wj-TblWrap mr10" style="height:220px;">
                <div class="oh mb10">
                    <span class="fl bk lh20 s14"><s:message code="hqEmp.use.menu" /> </span>
                    <%-- 미사용등록 버튼 --%>
                    <span class="fr"><a id="btnRemoveMenu" href="#" class="btn_grayS2" ng-click="notUseReg()"><s:message code="hqEmp.exceptReg" /></a></span>
                </div>
                <%-- 위즈모 --%>
                <wj-flex-grid
                        style="height:180px"
                        id="avlblMenuGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        allow-merging="Cells">
                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="hqEmp.lMenuCd"/>" binding="resrceCdLarge" width="*" visible="false" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.lMenuNm"/>" binding="resrceNmLarge" width="90" is-read-only="true"  allow-merging="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.mMenuCd"/>" binding="resrceCdMid" width="*" visible="false" allow></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.mMenuNm"/>" binding="resrceNmMid" width="90" is-read-only="true" allow-merging="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.chk.menu"/>" binding="gChk" width="60"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.sMenuCd"/>" binding="resrceCdSmall" width="*" visible="false" allow></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.sMenuNm"/>" binding="resrceNmSmall" width="230" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%-- //사용 메뉴 --%>
    </div>

    <%-- 미사용 메뉴 --%>
    <div id="webArea2" ng-controller="notUseHqEmpWebMenuCtrl">
        <div class="oh mt10">
            <div class="wj-TblWrap mr10" style="height:220px;">
                <div class="oh mb10">
                    <span class="fl bk lh20 s14"><s:message code="hqEmp.except.menu" /> </span>
                    <%-- 사용등록 버튼 --%>
                    <span class="fr"><a id="btnAddMenu" href="#" class="btn_grayS2"  ng-click="useReg()"><s:message code="hqEmp.useReg" /></a></span>
                </div>
                <%-- 위즈모 --%>
                <wj-flex-grid
                        style="height:180px"
                        id="beUseMenuGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        allow-merging="Cells">
                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="hqEmp.lMenuCd"/>" binding="resrceCdLarge" width="*" visible="false" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.lMenuNm"/>" binding="resrceNmLarge" width="90" is-read-only="true"  allow-merging="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.mMenuCd"/>" binding="resrceCdMid" width="*" visible="false" allow></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.mMenuNm"/>" binding="resrceNmMid" width="90" is-read-only="true" allow-merging="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.chk.menu"/>" binding="gChk" width="60"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.sMenuCd"/>" binding="resrceCdSmall" width="*" visible="false" allow></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqEmp.sMenuNm"/>" binding="resrceNmSmall" width="230" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
    <%-- //미사용 메뉴 --%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/hqEmpWebMenu.js?ver=20200529.18" charset="utf-8"></script>