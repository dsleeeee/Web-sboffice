<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="kioskOptionProdStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="kioskOptionProdStoreCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="kioskOption.optionProdStore" />
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
                    <th><s:message code="kioskOption.storeCd" /></th>
                    <td>
                        <input type="text" id="srchStoreCd"/>
                    </td>
                    <th><s:message code="kioskOption.storeNm" /></th>
                    <td>
                        <input type="text" id="srchStoreNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 매장상태구분 --%>
                    <th><s:message code="kioskOption.sysStatFg" /></th>
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
                    <c:if test="${brandUseFg != '1' or sessionInfo.orgnFg != 'HQ'}">
                        <th></th>
                        <td></td>
                    </c:if>
                    <c:if test="${brandUseFg == '1'}">
                      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                          <%-- 매장브랜드 --%>
                          <th><s:message code="kioskOption.storeHqBrand" /></th>
                          <td>
                            <div class="sb-select">
                              <wj-combo-box
                                id="srchStoreHqBrandCd"
                                ng-model="storeHqBrandCd"
                                items-source="_getComboData('srchStoreHqBrandCd')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchStoreHqBrandCdCombo">
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
                <button class="btn_blue" id="btnSearchStore" ng-click="btnSearchStore()"><s:message code="cmm.search" /></button>
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
                            id="wjGridKioskOptionProdStore">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOption.storeCd"/>" binding="storeCd" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOption.storeNm"/>" binding="storeNm" width="210" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOption.sysStatFg"/>" binding="sysStatFg" width="85"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOption.kioskPosCnt"/>" binding="kioskPosCnt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
            <%-- 버튼 영역 --%>
            <div class="btnSet2">
                <%-- 적용 --%>
                <button class="btn_blue ml10 fl" id="btnApplyStore" ng-click="btnApplyStore()"><s:message code="cmm.apply"/></button>
            </div>
        </div>
    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskOption/kioskOptionProdStore.js?ver=20220111.01" charset="utf-8"></script>