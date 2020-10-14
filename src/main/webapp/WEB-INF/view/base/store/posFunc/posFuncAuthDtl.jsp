<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="posFuncAuthDtlLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:560px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="authDtlTitle"></label>
            <span id="storeTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" ng-controller="posFuncAuthDtlCtrl">
            <div class="updownSet mb10">
                <%-- 저장버튼 --%>
                <button class="btn_skyblue" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
            </div>
            <input type="hidden" id="hdStoreCd" />

            <%-- 그리드 --%>
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:400px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="posFunc.fnkeyNo"/>" binding="fnkeyNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posFunc.fnkeyNm"/>" binding="fnkeyNm" width="160" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posFunc.dispSeq"/>" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posFunc.useYn"/>" binding="useYn" width="*" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posFunc.authYn"/>" binding="authYn" width="70" format="boolean"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="posFunc.setting.auth"/>" binding="buttons" width="130" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="*" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/posFunc/posFuncAuthDtl.js?ver=20201013.01" charset="utf-8"></script>