<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjProdImgBarrierFreeDeleteLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:400px;height:250px;" fade-in="false" fade-out="false">
    <div ng-controller="prodImgBarrierFreeDeleteCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prodImgBarrierFreeDelete.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="pdb40" style="padding-top:20px;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w30"/>
                        <col class="w70"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <!-- 이미지구분 -->
                        <th><s:message code="prodImgBarrierFreeDelete.imgFg" /></th>
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
            <div class="tc">
                <button class="btn_blue" id="btnDelete" ng-click="btnDelete()"><s:message code="cmm.delete"/></button>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodImgBarrierFree/prodImgBarrierFreeDelete.js?ver=20240517.01" charset="utf-8"></script>