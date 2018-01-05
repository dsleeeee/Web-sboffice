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
      <h1>솔비포스 관리자</h1>
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
        <form class="loginF">
          
          <div class="writeInfo">
            <input type="text" id="id" placeholder="아이디" class="id" /><label for="id"></label>
            <input type="password" id="pw" placeholder="비밀번호" class="pw" /><label for="pw"></label>
          </div>
          
          <div class="idsave">
            <span><input type="checkbox" id="chk" /><label for="chk">아이디 저장</label></span>
            <button class="btn_login">로그인</button>
          </div>
          
        </form>
      
      
        <div class="linkArea">
          <span class="find"><a href="#" class="fdId">아이디 찾기</a>
            <a href="#" class="fdPw">비밀번호 찾기</a>
          </span>
          <a href="#" class="distributor">총판 등록</a>
          <a href="#" class="agency">대리점 등록</a>
        </div>
        
      </div>
      
      
    </div>
  </div>
</body>


