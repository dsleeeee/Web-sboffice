<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="popUpSelLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="template.layer.nm" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="popUpApplyTemplateCtrl">
            <div>
                <table class="tblType01">
                    <colgroup>
                        <col width="10%" />
                        <col width="30%" />
                        <col width="10%" />
                        <col width="20%" />
                        <col width="10%" />
                        <col width="20%" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="template.layer.template" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchTemplateTypeCombo"
                                        ng-model="applyTemplateCd"
                                        items-source="_getComboData('srchTemplateTypeCombo')"
                                        display-member-path="templtNm"
                                        selected-value-path="prtClassCd"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        selected-index-changed="setSelectedCombo(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th><s:message code="template.layer.sysStatFg" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                    id="srchSysStatFgCombo"
                                    ng-model="sysStatFg"
                                    items-source="_getComboData('srchSysStatFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                            </div>
                        </td>
                        <th><s:message code="template.layer.clsFg" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchClsFgCombo"
                                        ng-model="clsFg"
                                        items-source="_getComboData('srchClsFgCombo')"
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
            <%-- 조회 --%>
            <div class="tr mt10 pdb20 oh bb">
                <button class="btn_blue" id="btnSrchApplyStore" ng-click="_broadcast('popUpApplyTemplateCtrl')">
                    <s:message code="template.layer.srchBtn" />
                </button>
            </div>
            <div class="wj-dialog-content" style="height:393px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="template.layer.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="template.layer.storeFg"/>" binding="storeFg" width="50" is-read-only="true" data-map="storeFgDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="template.layer.storeCd"/>" binding="storeCd" width="*" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="template.layer.storeNm"/>" binding="storeNm" width="*" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="template.layer.sysStatFg"/>" binding="sysStatFgNm" width="50" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="template.layer.clsFg"/>" binding="clsFgNm" width="50" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide-apply btn_blue"><s:message code="cmm.apply" /></button>
        </div>
    </div>
</wj-popup>
<script type="text/javascript">
  var sysStatFgComboData = ${ccu.getCommCode("005")};
  var clsFgComboData = ${ccu.getCommCode("001")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/bill/template/popUpTemplate.js?ver=2018091401" charset="utf-8"></script>
