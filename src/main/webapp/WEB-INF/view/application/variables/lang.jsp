<%@ page contentType="text/javascript" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
var messages = {};
<c:forEach var="key" items="${keys}">
<c:set var="textSplit" value="${fn:split(key, '=')}" />
messages["<s:message text='${textSplit[0]}' javaScriptEscape='true'/>"] = "<s:message text='${textSplit[1]}' javaScriptEscape='true'/>";
</c:forEach>
