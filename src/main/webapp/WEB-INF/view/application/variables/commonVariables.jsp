<%@ page contentType="text/javascript" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

var coulmnLayout1, coulmnLayout2, coulmnLayout3, coulmnLayout4 = "";
var menuCd = "${sessionScope.sessionInfo.currentMenu.resrceCd}";
var menuNm = "${sessionScope.sessionInfo.currentMenu.resrceNm}";
var listScaleBoxData = ${ccu.getListScale()};

