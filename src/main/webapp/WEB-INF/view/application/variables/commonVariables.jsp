<%@ page contentType="text/javascript" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
var coulmnLayout1, coulmnLayout2, coulmnLayout3, coulmnLayout4 = "";
var menuCd = "${menuCd}";
var menuNm = "${menuNm}";
<c:if test="${menuCd ne ''}">
var coulmnLayout1 = ${clo.getColumnLayout(1)};
</c:if>
