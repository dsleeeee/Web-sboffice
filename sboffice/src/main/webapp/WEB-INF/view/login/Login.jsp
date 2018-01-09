<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<body>
  <div class="login">
    <header>
      <a href="#">
        <img src="/resource/solbipos/img/login/logo_login.png" alt="솔비포스" />
      </a>
      <h1><s:message code="title.web"/></h1>
    </header>
    <div class="content">
      
      
      
      <!--광고배너영역-->
      <div class="bannerArea">
        <div class="slider">
          <input type="radio" name="slide_switch" id="id1" checked="checked" /> 
          <label for="id1"></label> <img src="/resource/solbipos/img/login/banner01.png" alt="" /> 
          <input type="radio" name="slide_switch" id="id2" /> 
          <label for="id2"></label> <img src="/resource/solbipos/img/login/banner02.png" alt="" />
        </div>
      </div>
      <!--//광고배너영역-->
      
      
      
      <div class="loginArea">
        <h2>Welcome Login</h2>
        
        <f:form class="loginF" modelAttribute="sessionInfo" method="post" action="/auth/login.sb">
          
          <div class="writeInfo">
            <input class="id" type="text" id="userId" name="userId" placeholder="<s:message code="label.login.userId"/>"/><label for="userId"></label>
<%--             <f:errors path="userId"/> --%>
            <input class="pw" type="password" id="userPwd" name="userPwd" placeholder="<s:message code="label.login.userPasswd"/>"/><label for="userPwd"></label>
<%--             <f:errors path="userPwd"/> --%>
          </div>
          
          <div class="idsave">
            <span>
              <input type="checkbox" id="chk" checked="true" />
              <label for="chk">
                <s:message code="label.login.rememberId"/>
              </label>
            </span>
            <button class="btn_login">
              <s:message code="label.login.submit"/>
            </button>
          </div>
          
        </f:form>
      
      
        <div class="linkArea">
          <span class="find">
            <a href="#" class="fdId"><s:message code="label.login.find.id"/></a>
            <a href="#" class="fdPw"><s:message code="label.login.find.pw"/></a>
          </span>
          <a href="#" class="distributor"><s:message code="label.login.add.dist"/></a>
          <a href="#" class="agency"><s:message code="label.login.add.agency"/></a>
        </div>
        
      </div>
      
      
    </div>
  </div>
</body>


