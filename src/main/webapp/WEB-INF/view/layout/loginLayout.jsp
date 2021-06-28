<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="view" scope="application"><tiles:getAsString name="view" ignore="true"/></c:set>
<c:set var="isLogin" value="${fn:indexOf(view, 'login') > -1}" scope="application"/>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<tiles:insertAttribute name="resource"/>
<title><s:message code="title.web"/></title>
</head>

<body>
  <div class="login">
    <header>
      <a href="/">
        <img src="/resource/solbipos/css/img/Basic_logo.png?v=20210624" alt="솔비포스" style="width:180px;"/>
      </a>
      <%--<h1><s:message code="title.web"/></h1>--%>
    </header>
    <div class="content">
      <!--광고배너영역-->
      <div class="bannerArea">
        <div class="slider">
          <input type="radio" name="slide_switch" id="id1" checked="checked" /> 
          <label for="id1"></label> <img src="/resource/solbipos/css/img/banner03.png" alt="" />
          <input type="radio" name="slide_switch" id="id2" /> 
          <label for="id2"></label> <img src="/resource/solbipos/css/img/banner04.png" alt="" />
        </div>
      </div>
      <!--//광고배너영역-->
      
      <tiles:insertAttribute name="body"/>
      
    </div>
  </div>
</body>
</html>