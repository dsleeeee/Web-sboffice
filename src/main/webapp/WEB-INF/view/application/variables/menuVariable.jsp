<%@ page contentType="text/javascript" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 선택된 메뉴와 부모 메뉴 --%>
<c:set var="cMenu" value="${sessionScope.sessionInfo.currentMenu}" />
<%-- 접힌 메뉴용 --%>
<c:set var="menuData" value="${sessionScope.sessionInfo.menuData}" />
<c:set var="bkmkData" value="${sessionScope.sessionInfo.bkmkData}" />
<%-- 데이터 파싱 --%>
var menuStr = '${menuData}';
var menus = isEmpty(menuStr) ? '' : JSON.parse(menuStr);
var bkmkStr = '${bkmkData}';
var bkmks = isEmpty(bkmkStr) ? '' : JSON.parse(bkmkStr);
var cMenuResrceCd = '${cMenu.getResrceCd()}';
