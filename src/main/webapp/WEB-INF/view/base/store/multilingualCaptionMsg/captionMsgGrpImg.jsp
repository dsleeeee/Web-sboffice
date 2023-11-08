<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="captionMsgGrpImgLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:600px;">

    <div class="wj-dialog wj-dialog-columns" ng-controller="captionMsgGrpImgCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="multilingualCaptionMsg.preview"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 이미지 --%>
            <img id="imgPreview" style="width:100%;"/>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/multilingualCaptionMsg/captionMsgGrpImg.js?ver=20231109.01" charset="utf-8"></script>