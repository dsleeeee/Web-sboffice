<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="popUpCopyTouchKeyLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="touchKey.copy" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="popUpCopyTouchKeyCtrl">
            <!-- 적용할 터치키 그룹 선택 -->
            <div style="padding-top:20px;">
                <table class="tblType01">
                    <colgroup>
                        <col width="35%" />
                        <col width="65%" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="touchKey.grp" /></th>
                        <td>
                            <div class="sb-select" style="width:120px; float:left;">
                                <wj-combo-box
                                        id="copyTouchKeyGrpCombo"
                                        ng-model="copyTouchKeyGrp"
                                        items-source="_getComboData('copyTouchKeyGrpCombo')"
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
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide-apply btn_blue"><s:message code="cmm.copy" /></button>
        </div>
        </div>
    </div>
</wj-popup>
<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/popUpCopyTouchKey.js?ver=20200428.02" charset="utf-8"></script>