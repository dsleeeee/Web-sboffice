<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup id="batchStoreLayer" control="batchStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="batchStoreCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="func.batch" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
                <%-- 조회조건 --%>
                <div class="searchBar flddUnfld">
                    <a href="#" class="open fl"><s:message code="func.batch"/></a>
                    <%-- 조회 --%>
                    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                        <button class="btn_blue fr" ng-click="_pageView('batchStoreCtrl',1)">
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
                                            id="bsHqOfficeCd"
                                            ng-model="hqOfficeCd"
                                            control="bsHqOfficeCd"
                                            items-source="_getComboData('bsHqOfficeCd')"
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
                        <td><input type="text" id="bsStoreCd" ng-model="storeCd" class="sb-input w100"/></td>
                        <th><s:message code="func.storeNm" /></th>
                        <td><input type="text" id="bsStoreNm" ng-model="storeNm" class="sb-input w100"/></td>
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
            </div>
    </div>
</wj-popup>
<script>
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/func/batchStore.js?ver=20200708.07" charset="utf-8"></script>
