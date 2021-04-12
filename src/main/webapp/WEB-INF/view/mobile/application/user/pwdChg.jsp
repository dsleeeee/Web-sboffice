<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2 class="sTit">
    <s:message code="login.find.pw" />
  </h2>
  <p class="h2_txt">
    <s:message code="login.pw.find.new.h2" />
  </p>

  <div class="idInfo">
    <p>
      <span><s:message code="login.userId" /></span> <span>${userId}</span>
    </p>
  </div>

  <f:form class="loginF" method="post" modelAttribute="pwdChg" action="/mobile/user/pwdChgOk.sb" onsubmit="return inputChk();">
    <div class="writeInfo">
      <div>
        <input type="password" id="newPw" name="newPw" placeholder="<s:message code="login.pw.chg.new" />" class="pw" /><label for="newPw"></label>
        <span path="newPw" id="newPwError" class="errorMsg" style="display:none"></span>
      </div>
      <div>
        <input type="password" id="newPwConf" name="newPwConf" placeholder="<s:message code="login.pw.chg.new" />&nbsp;<s:message code="cmm.confirm" />" class="pw" /><label for="newPwConf"></label>
        <span path="newPwConf" id="newPwConfError" class="errorMsg" style="display:none"></span>
      </div>
    </div>

    <input id="uuid" name="uuid" style="display: none" value="${uuid}" />
    <input id="halfId" name="halfId" style="display: none" value="${userId}" />

    <button class="btn_bluew100">
      <s:message code="cmm.confirm" />
    </button>
  </f:form>

</div>


<script>
genEvent($("#newPw"), $("#newPwError"));
genEvent($("#newPwConf"), $("#newPwConfError"));

  // input 값 체크
  function inputChk() {

    if(isEmptyObject($("#newPw").val())){
      $("#newPwError").text(messages["login.pw.chg.new"] + messages["cmm.require.text"]);
      $("#newPwError").show();
      return false;
    }

    if(!isEmptyObject($("#newPw").val()) && (6 > $("#newPw").val().length || $("#newPw").val().length > 20)){
      $("#newPwError").text(messages["login.pw.not.match.length"]);
      $("#newPwError").show();
      return false;
    }

    if(isEmptyObject($("#newPwConf").val())){
      $("#newPwConfError").text(messages["login.pw.chg.new.confm"] + messages["cmm.require.text"]);
      $("#newPwConfError").show();
      return false;
    }

    if(!isEmptyObject($("#newPwConf").val()) && (6 > $("#newPwConf").val().length || $("#newPwConf").val().length > 20)){
      $("#newPwConfError").text(messages["login.pw.not.match.length"]);
      $("#newPwConfError").show();
      return false;
    }

    if($("#newPw").val() !== $("#newPwConf").val()){
      $("#newPwConfError").text(messages["login.pw.chg.new.not.equal"]);
      $("#newPwConfError").show();
      return false;
    }

    return true;

  }
</script>
