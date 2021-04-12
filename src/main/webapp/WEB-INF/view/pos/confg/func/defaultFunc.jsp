<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="defaultFuncLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:730px">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="defaultFuncCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="func.defaultFunc" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"><s:message code="func.defaultFunc"/></a>
                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_pageView('defaultFuncCtrl',1)">
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
                <c:if test="${orgnFg != 'HQ'}">
                    <tr>
                        <th><s:message code="func.hqOffice" /></th>
                        <td colspan="3">
                            <div class="sb-select w50">
                                <wj-combo-box
                                        id="dfHqOfficeCd"
                                        ng-model="hqOfficeCd"
                                        control="dfHqOfficeCd"
                                        items-source="_getComboData('dfHqOfficeCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <th><s:message code="func.storeCd" /></th>
                    <td><input type="text" id="dfStoreCd" ng-model="storeCd" class="sb-input w100"/></td>
                    <th><s:message code="func.storeNm" /></th>
                    <td><input type="text" id="dfStoreNm" ng-model="storeNm" class="sb-input w100"/></td>
                </tr>
                </tbody>
            </table>
            <br/>
            <%--위즈모 테이블--%>
            <div class="theGrid" style="height: 250px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="func.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="func.hqOfficeNm"/>" binding="hqOfficeNm" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="func.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="func.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>

            <%-- 적용대상매장 정보 --%>
            <div id="divInfo" style="display: none;">
                <table class="searchTbl mt10">
                    <colgroup>
                        <col class="w80" />
                        <col class="w20" />
                </colgroup>
                    <tbody>
                        <tr class="brt">
                            <th class="oh gr" style="vertical-align: top;">
                                <span style="font-size: 13px;">※ <s:message code="func.require.select.store"/></span>
                                <p class="s12 bk mt10 lh20">
                                    <label id="lblStoreInfo"></label>
                                    <input type="hidden" id="hdStoreCd"/>
                                    <input type="hidden" id="hdStoreNm"/>
                                </p>
                            </th>
                            <th class="oh gr">
                                <button class="btn_blue fr" id="btnReset"  ng-click="defaultFuncReset()"><s:message code="cmm.apply" /></button>
                            </th>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</wj-popup>
<script>
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/func/defaultFunc.js?ver=20200703" charset="utf-8"></script>