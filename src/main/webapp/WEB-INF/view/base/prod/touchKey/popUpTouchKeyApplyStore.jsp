<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="popUpApplyStoreLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="touchKey.layer.title" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="popUpApplyStoreCtrl">
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
                        <th><s:message code="touchKey.layer.srchStoreNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                        </td>
                        <th><s:message code="touchKey.layer.sysStatFg" /></th>
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
                        <th><s:message code="touchKey.layer.clsFg" /></th>
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
                <button class="btn_blue" id="btnSrchApplyStore" ng-click="_broadcast('popUpApplyStoreCtrl')">
                    <s:message code="touchKey.layer.srchBtn" />
                </button>
            </div>
            <div class="wj-dialog-content" style="height:393px;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexLayer"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.storeCd"/>" binding="storeCd" width="150" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.storeNm"/>" binding="storeNm" width="300" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.sysStatFg"/>" binding="sysStatFgNm" width="50" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.clsFg"/>" binding="clsFgNm" width="50" is-read-only="true"></wj-flex-grid-column>

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
<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/popUpTouchKeyApplyStore.js?ver=20181229.01" charset="utf-8"></script>
