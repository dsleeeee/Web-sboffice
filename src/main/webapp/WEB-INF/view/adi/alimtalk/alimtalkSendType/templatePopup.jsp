<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjTemplatePopupLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;height:500px;" fade-in="false" fade-out="false">
    <div ng-controller="templatePopupCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="templatePopup.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="subCon">
            <div class="w100">
                <div class="oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15 blue">템플릿을 클릭하면 적용됩니다.</p>
                </div>
            </div>
            <%-- 템플릿 양식 --%>
            <div class="w100 fl" style="height:410px;">
                <div style="height:360px; overflow-x: hidden; overflow-y: auto;">
                    <div id="divTemplateCommentList"></div>
                </div>
            </div>
            <%-- //템플릿 양식 --%>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendType/templatePopup.js?ver=20220321.01" charset="utf-8"></script>