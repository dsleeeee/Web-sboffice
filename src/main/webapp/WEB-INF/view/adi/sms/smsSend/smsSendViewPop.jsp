<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSmsSendViewPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:970px;height:760px;" fade-in="false" fade-out="false">
    <div ng-controller="smsSendViewPopCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsSend.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- SMS전송 --%>
        <c:import url="/WEB-INF/view/adi/sms/smsSend/smsSend.jsp">
        </c:import>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsSendViewPop.js?ver=20210901.01" charset="utf-8"></script>