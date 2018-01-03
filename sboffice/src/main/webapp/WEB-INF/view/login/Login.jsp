<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body class="login">
  <div class="login_wrap">
  
    <div class="login_box">
      <div class="login_tit">
        <h1>solbipos 2</h1>
      </div>
      <div class="login_cont">
      
        <f:form modelAttribute="auth" method="post" action="/auth/login.sb">
        
          <label for="userId"><span class="hidden">${userIdLabel}</span></label>
          
          <input type="text" id="userId" name="userId" class="login_input m_b_10" placeholder="${userIdLabel}" value="${userIdVal}" required="required"/>
          
          
          <f:errors path="userId"/>
          
          <label for="userPasswd"><span class="hidden">${userPasswdLabel}</span></label>
          <input type="password" id="userPwd" name="userPwd" class="login_input m_b_10"
                placeholder="${userPasswdLabel}" value="${param.userPasswd}" required="required"/>
          <f:errors path="userPwd"/>
          
          <div class="checkbox m_b_10">
            <input type="checkbox" id="rememberId" name="rememberId"/>
            <label for="rememberId"><s:message code="label.auth.login.rememberId"/></label>
          </div>
          
          <div class="clear m_t_30">
            <button type="submit" class="login-bt btn-gr"><s:message code="label.auth.login.submit"/></button>
          </div>
        </f:form>
      </div>
    </div>
    <div class="copylight m_t_30">2015 ⓒ KCP TREEPAY MANAGEMENTS.</div>
  </div>
  
</body>


