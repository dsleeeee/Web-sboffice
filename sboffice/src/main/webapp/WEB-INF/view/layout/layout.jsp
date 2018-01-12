<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="view" scope="application"><tiles:getAsString name="view" ignore="true"/></c:set>
<c:set var="isPopPage" value="${fn:indexOf(view, 'pop') > -1}" scope="application"/>

<c:set var="isLogin" value="${fn:indexOf(view, 'Login') > -1}" scope="application"/>


<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<tiles:insertAttribute name="resource"/>
<title><s:message code="title.web"/></title>

</head>
<tiles:insertAttribute name="body"/>
</html>