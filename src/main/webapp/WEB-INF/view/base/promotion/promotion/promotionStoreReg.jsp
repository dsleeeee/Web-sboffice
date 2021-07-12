<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="promotionStoreRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="promotionStoreRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="promotion.storeAdd" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="promotion.storeType" /></th><%--매장타입--%>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="srchStoreType"
                                        items-source="_getComboData('srchStoreType')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchStoreTypeCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 메뉴그룹 --%>
                        <th><s:message code="promotion.menuGroup" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="srchStoreGroup"
                                        items-source="_getComboData('srchStoreGroup')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchStoreGroupCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="promotion.storeCd" /></th>
                        <td>
                            <input type="text" id="srchStoreCd"/>
                        </td>
                        <th><s:message code="promotion.storeNm" /></th>
                        <td>
                            <input type="text" id="srchStoreNm"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 매장상태구분 --%>
                        <th><s:message code="promotion.sysStatFg" /></th>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="srchSysStatFg"
                                        items-source="_getComboData('srchSysStatFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchSysStatFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
                <%-- 버튼영역 --%>
                <div class="mt10 tr">
                    <button class="btn_skyblue" id="btnInsertStoreAll" ng-click="btnInsertStoreAll()"><s:message code="promotion.setAllStore" /></button>
                    <button class="btn_skyblue" id="btnSearchStore" ng-click="btnSearchStore()"><s:message code="cmm.search" /></button>
                    <button class="btn_skyblue ml5 fr" id="btnInsertStore" ng-click="btnInsertStore()"><s:message code="cmm.add"/></button>
                </div>
                <%-- 그리드 영역 --%>
                <div class="w100 mt10 mb20">
                    <%--위즈모 테이블--%>
                    <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
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
                            <wj-flex-grid-column header="<s:message code="promotion.storeType"/>" binding="storeTypeNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="promotion.menuGroup"/>" binding="storeGroupNms" width="250" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="promotion.menuGroup"/>" binding="storeGroupCds" width="250" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="promotion.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="promotion.storeNm"/>" binding="storeNm" width="210" align="left" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="promotion.sysStatFg"/>" binding="sysStatFg" width="90"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                    <%--//위즈모 테이블--%>
                </div>
            </div>
    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotion/promotionStoreReg.js?ver=20210423.03" charset="utf-8"></script>