<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="storeModLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="storeModCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <h3 class="" style="line-height:50px;"><s:message code="kioskKeyMap.storeModYn"/></h3>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body">
            <span class="s14 lh20 fl mb15"><s:message code="touchKey.storeModGrpMsg"/></span>
            <%-- 기준매장 조회 조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 키맵그룹 --%>
                    <th><s:message code="kioskKeyMap.tuClsType" /></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="tuClsTypeCombo"
                                    ng-model="tuClsType"
                                    control="tuClsTypeCombo"
                                    items-source="_getComboData('tuClsTypeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <c:if test="${orgnFg == 'HQ'}">
                        <%-- 매장수정허용여부 --%>
                        <th><s:message code="kioskKeyMap.storeModYn" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="storeModYnCombo"
                                        ng-model="storeModYn"
                                        control="storeModYnCombo"
                                        items-source="_getComboData('storeModYnCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </c:if>
                </tr>
                </tbody>
            </table>

            <%-- 조회 --%>
            <div class="tr mt10 pdb20 oh bb">
                <button class="btn_skyblue" id="nxBtnSearch" ng-click="getStoreModGrpList()">
                    <s:message code="cmm.search" />
                </button>
                <button class="btn_skyblue" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>

            <div class="wj-gridWrap" style="height:400px;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsType"/>"     binding="tuClsType" width="*" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsTypeNm"/>"   binding="tuClsTypeNm" width="*" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsCd"/>"       binding="tuClsCd" width="*" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsNm"/>"       binding="tuClsNm" width="*" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeModYn"/>"    binding="storeModYn" width="*" align="center" data-map="useYnDataMap" ></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <div class="wj-dialog-footer">
            <c:if test="${orgnFg == 'HQ'}">
                <button class="btn_blue" id="btnSaveStoreModGrp" ng-click="saveStoreModGrp()"><s:message code="cmm.save" /></button>
            </c:if>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapStoreMod.js?ver=20250114.01" charset="utf-8"></script>
