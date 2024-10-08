<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="storeTypeApplyStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="storeTypeApplyStoreCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeType.storeTypeApplyStore" />
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
                    <th><s:message code="storeType.storeType" /></th><%--매장타입--%>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="srchPopStoreType"
                                    items-source="_getComboData('srchPopStoreType')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchPopStoreTypeCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 메뉴그룹 --%>
                    <th><s:message code="storeType.menuGroup" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="srchPopStoreGroup"
                                    items-source="_getComboData('srchPopStoreGroup')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchPopStoreGroupCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th><s:message code="storeType.storeCd" /></th>
                    <td>
                        <input type="text" id="srchPopStoreCd" onkeyup="fnNxBtnSearch();"/>
                    </td>
                    <th><s:message code="storeType.storeNm" /></th>
                    <td>
                        <input type="text" id="srchPopStoreNm" onkeyup="fnNxBtnSearch();"/>
                    </td>
                </tr>
                <tr>
                    <%-- 매장상태구분 --%>
                    <th><s:message code="storeType.sysStatFg" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="srchPopSysStatFg"
                                    items-source="_getComboData('srchPopSysStatFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchPopSysStatFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <c:if test="${brandUseFg != '1' or sessionInfo.orgnFg != 'HQ'}">
                        <th></th>
                        <td></td>
                    </c:if>
                    <c:if test="${brandUseFg == '1'}">
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <%-- 매장브랜드 --%>
                            <th><s:message code="storeType.storeHqBrand" /></th>
                            <td>
                              <div class="sb-select">
                                <wj-combo-box
                                  id="srchPopStoreHqBrandCd"
                                  ng-model="storeHqBrandCd"
                                  items-source="_getComboData('srchPopStoreHqBrandCd')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  control="srchPopStoreHqBrandCdCombo">
                                </wj-combo-box>
                              </div>
                            </td>
                        </c:if>
                    </c:if>
                </tr>
                </tbody>
            </table>
            <%-- 버튼영역 --%>
            <div class="mt10 tr">
                <button class="btn_blue" id="nxBtnSearch" ng-click="searchPopStore()"><s:message code="cmm.search" /></button>
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
                            item-formatter="_itemFormatter"
                            id="wjGridList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.storeType"/>" binding="storeTypeNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.menuGroup"/>" binding="storeGroupNms" width="250" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.menuGroup"/>" binding="storeGroupCds" width="250" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.storeNm"/>" binding="storeNm" width="190" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.sysStatFg"/>" binding="sysStatFg" width="80"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.applyDt"/>" binding="applyDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
            <table class="tblType01">
                <colgroup>
                    <col class="w30"/>
                    <col class="w70"/>
                </colgroup>
                <tbody>
                <%-- 적용일시 --%>
                <tr>
                    <th>
                        <div style="float: left;"><input type="checkbox" id="chkApplyDay" ng-model="isCheckedApplyDay" ng-change="isChkApplyDay()" /></div>
                        <div style="padding-top: 3px; padding-left: 25px;"><s:message code="storeType.applyDay"/></div>
                    </th>
                    <td>
                        <div class="sb-select fl" style="padding-right: 10px;">
                            <span class="txtIn"><input id="applyDay" class="w120px"></span>
                        </div>
                        <div class="sb-select fl" style="width:65px;">
                            <wj-combo-box
                                    id="applyDayHh"
                                    ng-model="applyDayHh"
                                    items-source="_getComboData('applyDayHhCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="applyDayHhCombo"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <div class="fl pd5" style="padding-right: 15px;">
                            <label>시 </label>
                        </div>
                    </td>
                </tr>
                <%--<c:if test="${storeTypeApplyEnvstVal == '1'}">
                    <tr>
                        <th><s:message code="storeType.saleUprcApply"/></th>
                        <td>
                            <div style="float: left;"><input type="checkbox" id="chkSaleUprcApply"/></div>
                            <div style="padding-top: 3px; padding-left: 25px;"><s:message code="storeType.saleUprcApply" /></div>
                        </td>
                    </tr>
                </c:if>--%>
                </tbody>
            </table>
            <div class="btnSet2">
                <button class="btn_blue" id="btnStoreTypeApply" ng-click="storeTypeApply()"><s:message code="cmm.apply"/></button>
            </div>
        </div>
    </div>
</wj-popup>

<style type="text/css">
    input[type=checkbox]  {
        width: 17px;
        height: 17px;
    }
</style>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storeType/storeTypeApplyStore.js?ver=20230802.01" charset="utf-8"></script>