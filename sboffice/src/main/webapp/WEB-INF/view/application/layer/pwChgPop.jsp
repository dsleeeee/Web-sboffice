<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="layerpw" class="layer" style="display: none;">
  <div class="layer_inner">
    <!--layerContent-->
    <div class="title w500">

      <%-- 타이틀 --%>
      <p class="tit">
        <s:message code="label.layer.pwchg.title" />
      </p>

      <a href="#" class="btn_close"></a>
      <div class="con">

        <%-- 패스워드 변경 정보 --%>
        <c:if test="${param.type != 'user'}">
          <p class="s14">
            <s:message code="label.layer.pwchg.limit" arguments="70" />
          </p>
          <p class="s14 mt10">
            <s:message code="label.layer.pwchg.info" />
          </p>
        </c:if>

        <div class="mt20">
          <!--높이는 style로 조정, 스크롤 들어가는경우 sc추가-->
          <table class="tblType01">
            <colgroup>
              <col width="30%" />
              <col width="70%" />
            </colgroup>
            <tbody>
              <tr>
                <th><s:message code="label.login.userId" /></th>
                <td>${sessionScope.sessionInfo.userId}</td>
                <input id="userId" style="display: none" value="${sessionScope.sessionInfo.userId}" />
              </tr>
              <tr>
                <th><s:message code="label.login.userPasswd" /></th>
                <td>
                  <div class="msgWrap">
                    <input type="password" id="currentPw" />
                    <span id="currentPwError" class="errorMsg" style="display:none"></span>
                  </div>
                </td>
              </tr>
              <tr>
                <th><s:message code="label.pw.chg.new" /></th>
                <td>
                  <div class="msgWrap">
                    <input type="password" id="newPw" />
                    <span id="newPwError" class="errorMsg" style="display:none"></span>
                  </div>
                </td>
              </tr>
              <tr>
                <th><s:message code="label.pw.chg.new" />&nbsp;<s:message code="label.cmm.confirm" /></th>
                <td>
                  <div class="msgWrap">
                    <input type="password" id="newPwConf" />
                    <span id="newPwConfError" class="errorMsg" style="display:none"></span>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <p class="mt20 s12">
            <s:message code="label.layer.pwchg.policy" arguments="6,20" />
          </p>
        </div>
      </div>
      <div class="btnSet">
        <%-- 확인 버튼 --%>
        <span> <a href="#" class="btn_blue" id="confirmBtn">
            <s:message code="label.cmm.confirm" />
          </a>
        </span>
        <%-- 비밀번호 연장하기 --%>
        <c:if test="${param.type != 'user'}">
          <span> <a href="#" class="btn_gray" id="extensionBtn">
              <s:message code="label.layer.pwchg.ext" />
            </a>
          </span>
        </c:if>
      </div>
    </div>
    <!--//layerContent-->
  </div>
</div>

<script>
  $("#confirmBtn").click(function() {
    var param = {};
    param.userId = $("#userId").val();
    param.currentPw = $("#currentPw").val();
    param.newPw = $("#newPw").val();
    param.newPwConf = $("#newPwConf").val();

    $.postJSON("/user/userPwdChg.sb", param, function(result) {

      console.log(result.status);
      console.log(result.data);

      if (result.status === "OK") {
        if(result.data.msg != undefined) {
      		alert(result.data.msg);
          }
      } else if (result.status === "FAIL") {
        processError(result.data);
      }
      
//       $(".fullDimmed").show();
//       $("#layerpop").toggle();
      
    }).fail(function() {
      alert("Ajax Fail");
    });
  });

  $("#extensionBtn").click(function() {
    alert("연장하기");
  });

  $(".btn_close").click(function() {
    $(".fullDimmed").toggle();
    $("#layerpw").toggle();
  });
  
  function processError(data) {
    if(data.currentPw != undefined) {
      $("#currentPwError").text(data.currentPw != undefined ? data.currentPw : "");
      $("#currentPwError").show();
    }
    else {
      $("#currentPwError").hide();
    }
    
    if(data.newPw != undefined) {
      $("#newPwError").text(data.newPw != undefined ? data.newPw : "");
      $("#newPwError").show();
    }
    else {
      $("#newPwError").hide();
    }
    
    if(data.newPwConf != undefined) {
      $("#newPwConfError").text(data.newPwConf != undefined ? data.newPwConf : "");
      $("#newPwConfError").show();
    }
    else {
      $("#newPwConfError").hide();
    }
    
    if(data.msg != undefined) {
		alert(data.msg);
    }
    
  }
</script>



















