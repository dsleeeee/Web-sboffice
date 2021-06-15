<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<wj-popup control="kioskKeyMapEnvStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="kioskKeyMapEnvStoreCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="popStoreTitle"></label>
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">

            <!-- 적용할 키맵그룹 선택 -->
            <div class="pdb40" style="padding-top:20px;">
                <table class="tblType01">
                    <colgroup>
                        <col width="w20" />
                        <col width="w80" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="kioskKeyMap.posNo" /></th>
                        <td style="padding-left: 15px;">
                            <label id="lblPosNo"></label>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="kioskKeyMap.useTuClsType" /></th>
                        <td>
                            <div class="sb-select" style="width:150px; float:left;">
                                <wj-combo-box
                                        id="envStoreTuClsType"
                                        ng-model="envStoreTuClsType"
                                        items-source="_getComboData('envStoreTuClsType')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="envStoreTuClsTypeCombo">
                                </wj-combo-box>
                            </div>
                            <button class="btn_blue ml10 fl" id="btnInsertEnvStore" ng-click="btnInsertEnvStore()"><s:message code="cmm.apply"/></button>
                            <input type="hidden" id="hdStorePosNo"/>
                            <input type="hidden" id="hdStoreEnvstCd"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapEnvStore.js?ver=20210609.04" charset="utf-8"></script>