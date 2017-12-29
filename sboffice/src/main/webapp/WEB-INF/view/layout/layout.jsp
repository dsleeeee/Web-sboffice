<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<s:eval var="sessionUrlField" expression="@config['session.url']" scope="application"/>
<s:eval var="sessionUserField" expression="@config['session.user']"/>
<s:eval var="sessionUserMenuField" expression="@config['session.userMenu']" scope="application"/>
<c:set var="sessionUser" value="${sessionScope[sessionUserField]}" scope="session"/>
<c:set var="view" scope="application"><tiles:getAsString name="view" ignore="true"/></c:set>
<c:set var="isPopPage" value="${fn:indexOf(view, 'pop') > -1}" scope="application"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimal-ui">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<tiles:insertAttribute name="resource"/>
<title>solbipos</title>

</head>
<tiles:insertAttribute name="body"/>
</html>