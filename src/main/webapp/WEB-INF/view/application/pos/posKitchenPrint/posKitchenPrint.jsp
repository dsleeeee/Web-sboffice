<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body ng-app="rootApp" ng-controller="rootCtrl">
    <div ng-controller="posKitchenPrintCtrl" ng-init="init()">
    </div>
</body>
<script>
    var gvOrgnFg = "${orgnFg}";
    var gvHqOfficeCd = "${hqOfficeCd}";
    var gvStoreCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/application/pos/posKitchenPrint/posKitchenPrint.js?ver=20210607.01" charset="utf-8"></script>

<%-- 주방프린터상품연결 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/storeKitchenPrintProductView.jsp">
</c:import>