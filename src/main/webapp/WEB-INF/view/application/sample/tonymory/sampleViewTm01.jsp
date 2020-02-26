<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
html, body {height:100%}
*html .wrap {height:100%}
.wrap {height:100% !important}
#loginTony {position:absolute; top:0; left:0; right:0; bottom:0; width:100%; height:100%; margin:0 0 0 -230px; z-index:9000; background-color:#fff}
.log-bg {position:absolute; top:0; left:0; right:0; bottom:0; width:100%; height:100%; margin-left:230px; z-index:9001; background-color:#fff}
#logTo {position:absolute; top:0; left:0; right:0; bottom:0; width:1050px; height:auto; margin:120px auto 0; z-index:9002}
.login-left {float:left; width:420px}
.loginArea {float:left; margin-top:51px}
.writeInfo {width:80%; margin:0 auto}
.idsave {width:80%; margin:10px auto 30px}
a.btn_tony_login {display:inline-block; position:absolute; right:0; right:0; text-align:center; background:#1e88e5; font-size:1.125em; border:0; width:170px; padding:15px 5px; border-radius:4px; cursor:pointer; color:#fff !important}
a.btn_tony_login:hover {background:#174291; transition:all 0.3s;}
</style>


		<div id="loginTony" ng-controller="spTonyCtrl">

			<div class="log-bg">
			<div id="logTo">

				<div class="login-left">
					<a href="/">
						<img src="/resource/solbipos/css/img/test/logo_login_tony.jpg" alt="tonymory">
					</a>		
					
					<div class="bannerArea">
						<img src="/resource/solbipos/css/img/test/tony_banner01.jpg" alt=""> 
					</div>
				</div>

				<div class="loginArea">

					<h2>Welcome Login</h2>

					<div class="writeInfo">
						<div>
							<input class="id" type="text" id="userId" name="userId" placeholder="아이디" value="" maxlength="20" /><label for="userId"></label>
						</div>
						<div>
							<input class="pw" type="password" id="userPwd" name="userPwd" placeholder="비밀번호" maxlength="25" /><label for="userPwd"></label>
						</div>
					</div>
				
					<div class="idsave">
						<span>
							<input type="checkbox" id="chk" name="chk"><label for="chk">아이디 저장</span></label>
						</span>
						<a class="btn_tony_login" href="/sample/tonymory/sample41.sb">로그인</a>	
					</div>
				
				
					<div class="linkArea">
						<span class="find">
							<a href="/user/idFind.sb" class="fdId">아이디찾기</a>
							<a href="/user/pwdFind.sb" class="fdPw">비밀번호찾기</a>
						</span>
						<a href="http://www.solbipos.com" target="_blank" class="distributor">총판등록</a>
						<a href="http://www.solbipos.com" target="_blank" class="agency">대리점등록</a>
					</div>
				
				</div>
			</div>
        
      
<script>
  // js 추가
  var app = agrid.getApp();

  /**********************************************************************
   *  토니모리 테스트 페이지
   **********************************************************************/
  app.controller('spTonyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('spTonyCtrl', $scope, $http, true));


  }

  ]);
</script>        