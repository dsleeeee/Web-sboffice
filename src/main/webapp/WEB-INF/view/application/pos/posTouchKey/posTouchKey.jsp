<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body ng-app="rootApp" ng-controller="rootCtrl">
    <div ng-controller="posTouchKeyCtrl" ng-init="init()">
    </div>
</body>
<script>
  var touchKeyEnvstVal = ${touchKeyEnvstVal};
  var touchKeyGrpData = ${touchKeyGrp};
  var maxClassRow = ${maxClassRow};
  var gvOrgnFg = "${orgnFg}";
  var gvHqOfficeCd = "${hqOfficeCd}";
  var gvStoreCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/application/pos/posTouchKey/posTouchKey.js?ver=20210528.02" charset="utf-8"></script>

<%-- 판매터치키등록 --%>
<c:import url="/WEB-INF/view/base/prod/touchKey/touchKey.jsp">
</c:import>