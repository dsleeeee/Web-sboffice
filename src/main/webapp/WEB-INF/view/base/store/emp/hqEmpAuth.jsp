<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="hqEmpAuthLayer" show-trigger="Click" hide-trigger="Click" style="width:600px">
    <div class="wj-dialog wj-dialog-columns title" >

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeManage.storeInfo" />
            <span id="storeAuthTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 탭 --%>
            <div ng-controller="storeAuthCtrl">
                <ul class="subTab">
                    <%-- 매장정보 --%>
                    <li><a id="hqEmpInfo" href="#" ng-click="changeInfoTab();"><s:message code="storeManage.storeInfo" /></a></li>
                    <%-- 메뉴권한 --%>
                    <li><a id="hqEmpAuth" href="#" class="on"><s:message code="storeManage.auth" /></a></li>
                </ul>
            </div>

            <%-- 웹사이트 메뉴 --%>
            <c:import url="/WEB-INF/view/base/store/emp/hqEmpWebMenu.jsp">
            </c:import>
        </div>
    </div>
</wj-popup>
<script>

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/hqEmpAuth.js?ver=20200526.01" charset="utf-8"></script>
