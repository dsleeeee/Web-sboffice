<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="envRemarkPopLayer" control="envRemarkPopLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:500px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="envRemarkPopCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="lblEnvTitle"></label>
            <span id="storePosAddTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">
            <div id="divEnvRemark"></div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/envRemarkPop.js?ver=20220106.02" charset="utf-8"></script>