<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="kioskKeyMapEnvLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="kioskKeyMapEnvCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="popTitle"></label>
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
                    <th><s:message code="kioskKeyMap.storeCd" /></th>
                    <td>
                        <input type="text" id="srchEnvStoreCd"/>
                    </td>
                    <th><s:message code="kioskKeyMap.storeNm" /></th>
                    <td>
                        <input type="text" id="srchEnvStoreNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 매장상태구분 --%>
                    <th><s:message code="kioskKeyMap.sysStatFg" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="srchEnvSysStatFg"
                                    items-source="_getComboData('srchEnvSysStatFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchEnvSysStatFgCombo">
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
                <button class="btn_blue" id="btnSearchPos" ng-click="btnSearchPos()"><s:message code="cmm.search" /></button>
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
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeCd"/>" binding="storeCd" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeNm"/>" binding="storeNm" width="190" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.sysStatFg"/>" binding="sysStatFg" width="85"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskPosNo"/>" binding="posNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.envstVal"/>" binding="env4068" width="92" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.envstVal"/>" binding="env4069" width="92" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
                <!-- 적용할 키맵그룹 선택 -->
                <div style="padding-top:20px;">
                    <table class="tblType01">
                        <colgroup>
                            <col width="w20" />
                            <col width="w80" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><s:message code="kioskKeyMap.tuClsType" /></th>
                            <td>
                                <div class="sb-select" style="width:150px; float:left;">
                                    <wj-combo-box
                                            id="envTuClsType"
                                            ng-model="envTuClsType"
                                            items-source="_getComboData('envTuClsType')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="envTuClsTypeCombo">
                                    </wj-combo-box>
                                </div>
                                <div class="fl pd5" style="padding-right: 15px;">
                                    <div style="float: left;"><input type="checkbox" id="chkTuClsTypeStore" checked="checked"/></div>
                                    <div style="padding-top: 3px; padding-left: 20px;"><s:message code="kioskKeyMap.tuClsTypeStore" /></div>
                                </div>
                                <button class="btn_blue ml5 fl" id="btnInsertEnv" ng-click="btnInsertEnv()"><s:message code="cmm.apply"/></button>
                                <input type="hidden" id="hdEnvstCd"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapEnv.js?ver=20220124.02" charset="utf-8"></script>