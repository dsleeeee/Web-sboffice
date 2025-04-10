<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup control="storeTerminalExampleLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="storeTerminalExampleCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body">
            <img src="/resource/solbipos/css/img/store/terminal_manual.png" style="width:100%" alt="" />
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeTerminalExample.js?ver=20250410.01" charset="utf-8"></script>
