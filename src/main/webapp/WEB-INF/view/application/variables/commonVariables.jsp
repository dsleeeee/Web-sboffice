<%@ page contentType="text/javascript" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

var menuCd = "${menuCd}";
var menuNm = "${menuNm}";
var coulmnLayout1 = ${clo.getColumnLayout(1)};
var coulmnLayout2 = ${clo.getColumnLayout(2)};
var coulmnLayout3 = ${clo.getColumnLayout(3)};
var coulmnLayout4 = ${clo.getColumnLayout(4)};
