<%@ page contentType="text/javascript" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

var menuCd = "${sessionScope.sessionInfo.currentMenu.resrceCd}";
var menuNm = "${sessionScope.sessionInfo.currentMenu.resrceNm}";
var gvListScaleBoxData = ${ccu.getListScale()};
var gvStartDate = "${sessionScope.sessionInfo.startDate}";
var gvEndDate = "${sessionScope.sessionInfo.endDate}";
var gvOrgnFg = "${sessionScope.sessionInfo.orgnFg.getCode()}";

