<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjProdImgBarrierFreeStoreRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:550px;height:530px;" fade-in="false" fade-out="false">
    <div ng-controller="prodImgBarrierFreeStoreRegistCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prodImgBarrierFreeStoreRegist.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w20"/>
                    <col class="w30"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 매장코드 --%>
                    <th>
                        <s:message code="prodImgBarrierFreeStoreRegist.storeCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="prodImgBarrierFreeStoreRegist.storeNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 매장상태구분 --%>
                    <th>
                        <s:message code="prodImgBarrierFreeStoreRegist.sysStatFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchSysStatFgCombo"
                                    ng-model="sysStatFg"
                                    items-source="_getComboData('sysStatFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchSysStatFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('prodImgBarrierFreeStoreRegistCtrl', 1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:260px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFreeStoreRegist.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFreeStoreRegist.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFreeStoreRegist.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="95" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <!-- 적용할 이미지타입 선택 -->
                <div style="padding-top:20px;">
                    <table class="tblType01">
                        <colgroup>
                            <col width="15%" />
                            <col width="85%" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><s:message code="prodImgBarrierFreeStoreRegist.imgFg" /></th>
                            <td colspan="3">
                                <div style="float: left;"><input type="checkbox" id="chkKioskGreen" value="006"/></div>
                                <div style="float: left; padding: 3px 10px 0 5px;"><label><s:message code="prodImgBarrierFreeStoreRegist.kioskGreenImg" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkKioskYellow" value="007"/></div>
                                <div style="float: left; padding: 3px 10px 0 5px;"><label><s:message code="prodImgBarrierFreeStoreRegist.kioskYellowImg" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkKioskWhite" value="008"/></div>
                                <div style="float: left; padding: 3px 10px 0 5px;"><label><s:message code="prodImgBarrierFreeStoreRegist.kioskWhiteImg" /></label></div>
                                <%-- 적용 --%>
                                <button class="btn_blue ml10 fl" id="btnRegImgStore" ng-click="btnRegImgStore()"><s:message code="cmm.apply"/></button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- // 적용할 이미지타입 선택 -->
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodImgBarrierFree/prodImgBarrierFreeStoreRegist.js?ver=20240517.01" charset="utf-8"></script>