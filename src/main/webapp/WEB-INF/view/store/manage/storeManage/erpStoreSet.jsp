<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="erpStoreSetLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="erpStoreSetCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeManage.storeSelect" />
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
                    <th><s:message code="storeManage.storeCd" /></th>
                    <td>
                        <input type="text" id="erpStoreCd"/>
                    </td>
                    <th><s:message code="storeManage.storeNm" /></th>
                    <td>
                        <input type="text" id="erpStoreNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 사업자번호 --%>
                    <th><s:message code="storeManage.bizNo" /></th>
                    <td>
                        <input type="text" id="erpBizNo"/>
                    </td>
                    <%-- 등록여부 --%>
                    <th><s:message code="storeManage.regYn" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                id="erpRegYn"
                                items-source="_getComboData('erpRegYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="erpRegYnCombo"
                                selected-index="2">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <%-- 버튼영역 --%>
            <div class="mt10 oh sb-select dkbr">
                <%-- 페이지 스케일  --%>
                <wj-combo-box
                    class="w100px fl"
                    id="listScaleErpBox"
                    ng-model="listScaleErp"
                    items-source="_getComboData('listScaleErpBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="initComboBox(s)">
                </wj-combo-box>
                <button class="btn_skyblue fr" id="btnSearchErpStore"  ng-click="_pageView('erpStoreSetCtrl',1)"><s:message code="cmm.search" /></button>
            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 250px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">
                            <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="storeManage.storeCd"/>" binding="bbqStoreCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.storeNm"/>" binding="storeNm" width="180" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.onwerNm"/>" binding="ownerNm" width="100"  align="center"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeManage.regYn"/>" binding="regYn" width="100" align="center" data-map="regYnDataMap"is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="erpStoreSetCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/erpStoreSet.js?ver=20211015.01" charset="utf-8"></script>