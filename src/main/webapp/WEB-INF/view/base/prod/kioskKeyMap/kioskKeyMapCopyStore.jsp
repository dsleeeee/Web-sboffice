<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<wj-popup control="kioskKeyMapCopyStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="kioskKeyMapCopyStoreCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label><s:message code="kioskKeyMap.tuClsTypeCopy" /></label>
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <p class="tl s14 lh15"><s:message code="kioskKeyMap.tuClsTypeCopyComment"/></p>
            <!-- 적용할 키맵그룹 선택 -->
            <div class="pdb40" style="padding-top:20px;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w30"/>
                        <col class="w20"/>
                        <col class="w30"/>
                        <col class="w20"/>
                    </colgroup>
                    <tbody>
                    <tr>        <!-- 복사원본 -->
                        <th><s:message code="kioskKeyMap.copy" />&nbsp;<s:message code="kioskKeyMap.posNo" /></th>
                        <td style="padding-left: 15px;">
                            <div class="sb-select" style="float:left;">
                                <wj-combo-box
                                        id="orgStorePosNo"
                                        ng-model="orgStorePosNo"
                                        items-source="_getComboData('orgStorePosNo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="orgStorePosNoCombo"
                                        selected-index-changed="setOrgTuClsType()">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th><s:message code="kioskKeyMap.tuClsType" /></th>
                        <td style="padding-left: 15px;">
                            <div class="sb-select" style="float:left;">
                                <wj-combo-box
                                        id="orgStoreTuClsType"
                                        ng-model="orgStoreTuClsType"
                                        items-source="_getComboData('orgStoreTuClsType')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="orgStoreTuClsTypeCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>       <!-- 붙여넣어질 -->
                        <th><s:message code="kioskKeyMap.paste" />&nbsp;<s:message code="kioskKeyMap.posNo" /></th>
                        <td style="padding-left: 15px;">
                            <div class="sb-select" style="float:left;">
                                <wj-combo-box
                                        id="storePosNo"
                                        ng-model="storePosNo"
                                        items-source="_getComboData('storePosNo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="storePosNoCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 버튼영역 --%>
            <div class="tr">
                <button class="btn_blue ml10 fr" id="btnInsertEnvStore" ng-click="btnInsertEnvStore()"><s:message code="cmm.copy"/></button>
            </div>
         </div>
    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapCopyStore.js?ver=20210609.05" charset="utf-8"></script>