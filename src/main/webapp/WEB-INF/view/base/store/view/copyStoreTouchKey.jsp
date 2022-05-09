<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="copyStoreTouchKeyLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:600px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font" ng-controller="copyStoreTouchKeyCtrl">
            <s:message code="storeView.copy.touchKey" />
            <a href="" class="wj-hide btn_close" ng-click="closePop()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" style="height:500px; overflow-y: auto;">

            <div ng-controller="copyStoreTouchKeyCtrl">
                <p class="mb5" style="font-size: small">-<s:message code="storeView.original.storeCd" />-</p>

                <%-- 기준매장 조회 조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w30"/>
                        <col class="w15"/>
                        <col class="w30"/>
                        <col class="w10"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장코드 --%>
                        <th><s:message code="storeView.storeCd" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreCd1" onkeyup="fnNxBtnSearch();"/>
                        </td>
                        <%-- 매장명 --%>
                        <th><s:message code="storeView.storeNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreNm1" onkeyup="fnNxBtnSearch();"/>
                        </td>
                        <td>
                            <%--조회--%>
                            <button class="btn_skyblue fr" id="btnSearch" ng-click="searchOrgStoreList()" ><s:message code="cmm.search" /></button>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%-- 기준매장 그리드 --%>
                <div class="w100 mt10 mb10">
                    <div class="wj-gridWrap" style="height:120px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="storeView.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeView.storeNm"/>" binding="storeNm" width="300" is-read-only="true" align="left"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>
            </div>

            <div ng-controller="copyStoreTouchKey2Ctrl">

                <%-- 선택한 기준매장의 터치키그룹 --%>
                <div class="mb20">
                    <table class="tblType01">
                        <colgroup>
                            <col class="w20"/>
                            <col class="w80"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><s:message code="storeView.original.storeCd"/></th>
                            <td>
                                <label id="selStore"></label>
                                <input type="hidden" id="hdOrgStorecd"/>
                            </td>
                        </tr>
                        <tr>
                            <th><s:message code="storeView.touchKey.grp"/></th>
                            <td>
                                <div class="sb-select w150px" id="selTouchKeyGrp" style="display: none;">
                                    <wj-combo-box
                                            id="tuKeyGrpCombo"
                                            control="tuKeyGrpCombo"
                                            items-source="_getComboData('tuKeyGrpCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <p class="mb5" style="font-size: small">- <s:message code="storeView.target.storeCd" /> -</p>

                <%-- 적용대상매장 조회 조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w30"/>
                        <col class="w15"/>
                        <col class="w30"/>
                        <col class="w10"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장코드 --%>
                        <th><s:message code="storeView.storeCd" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreCd2" onkeyup="fnNxBtnSearch();"/>
                        </td>
                        <%-- 매장명 --%>
                        <th><s:message code="storeView.storeNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreNm2" onkeyup="fnNxBtnSearch();"/>
                        </td>
                        <td>
                            <%--조회--%>
                            <button class="btn_skyblue fr" id="btnSearch" ng-click="searchTargetStoreList()" ><s:message code="cmm.search" /></button>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%-- 적용대상매장 그리드 --%>
                <div class="w100 mt10 mb10">
                    <div class="wj-gridWrap" style="height:120px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeView.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeView.storeNm"/>" binding="storeNm" width="300" is-read-only="true" align="left"></wj-flex-grid-column>

                         </wj-flex-grid>
                    </div>
                </div>

                <%-- 복사 버튼 --%>
                <div class="tc mt20">
                    <button class="btn_blue" id="btnSave" ng-click="saveCopyStoreTouchKey()">
                        <s:message code="cmm.copy" />
                    </button>
                </div>
            </div>

        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/view/copyStoreTouchKey.js?ver=20220504.01" charset="utf-8"></script>