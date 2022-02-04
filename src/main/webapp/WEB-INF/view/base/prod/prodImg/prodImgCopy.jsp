<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<wj-popup control="prodImgCopyLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:400px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="prodImgCopyCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label><s:message code="prodImg.prodImgCopy" /></label>
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <!-- 적용할 키맵그룹 선택 -->
            <div class="pdb40" style="padding-top:20px;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w30"/>
                        <col class="w70"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <!-- 복사 -->
                        <th><s:message code="prodImg.orgImgFg" /></th>
                        <td style="padding-left: 15px;">
                            <div class="sb-select" style="float:left;">
                                <wj-combo-box
                                        id="orgImgFg"
                                        ng-model="orgImgFg"
                                        items-source="_getComboData('orgImgFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="orgImgFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <!-- 붙여넣기 -->
                        <th><s:message code="prodImg.pasteImgFg" /></th>
                        <td style="padding-left: 15px;">
                            <div class="sb-select" style="float:left;">
                                <wj-combo-box
                                        id="imgFg"
                                        ng-model="imgFg"
                                        items-source="_getComboData('imgFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="imgFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 버튼영역 --%>
            <div class="tr">
                <button class="btn_blue ml10 fr" id="btnCopy" ng-click="btnCopy()"><s:message code="cmm.copy"/></button>
            </div>
         </div>
    </div>
</wj-popup>

<script>
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodImg/prodImgCopy.js?ver=20220113.01" charset="utf-8"></script>