<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="storePosAddLayer" control="storePosAddLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;height:350px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="storePosAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeManage.posAdd" />
            <span id="storePosAddTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
            <%--매장정보--%>
            <h2 class="h2_tit mt5"><%--<input type="hidden" id="spa_storeCd"/>--%>
                [<label id="spa_storeCd"></label>]
                <label id="spa_storeNm"></label>
            </h2>
            <div style="height: 400px; overflow-y: auto;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w15" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 설치포스수 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeManage.installPosCnt" /></div>
                        </th>
                        <td align="center">
                            <label id="spa_posCnt"></label>
                        </td>
                    </tr>
                    <tr>
                        <%-- 추가포스수 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeManage.posAddCnt" /></div>
                        </th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="spa_addPosCombo"
                                        ng-model="spa_addPosCombo"
                                        items-source="_getComboData('spa_addPosCombo')"
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

                <div class="btnSet">
                    <%-- 저장 --%>
                    <span><a href="#" class="btn_blue pd20" id="btnSavePosCnt" ng-click="savePosCnt()"><s:message code="cmm.save" /></a></span>
                </div>

            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storePosAdd.js?ver=20191105.24" charset="utf-8"></script>