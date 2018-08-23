<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<tiles:insertAttribute name="resource"/>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-route.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="/resource/vendor/wijmo/js/wijmo.angular.min.js?ver=5.20182.500" charset="utf-8"></script>
</head>
<body>
    <tiles:insertAttribute name="body"/>

    <%-- 레이어 팝업 추가 --%>
    <c:import url="/WEB-INF/view/application/layer/alert.jsp">
    </c:import>

    <%-- 로딩 이미지 추가 --%>
    <c:import url="/WEB-INF/view/layout/basic/loading.jsp">
    </c:import>

    <%-- 저장 이미지 추가 --%>
    <c:import url="/WEB-INF/view/layout/basic/saving.jsp">
    </c:import>
</body>
</html>
