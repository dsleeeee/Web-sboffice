<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 로그인 화면에서 비밀번호 변경 --%>
<c:if test="${param.type != 'user'}">
  <c:set var="id" value="${targetId}"></c:set>
</c:if>
<%-- 메인에서 비밀번호 변경 --%>
<c:if test="${param.type == 'user'}">
  <c:set var="id" value="${sessionScope.sessionInfo.userId}"></c:set>
</c:if>

<div id="fullDimmedPw" class="fullDimmed" style="display: none;"></div>
<div id="layerpw" class="layer" style="display: none;">
  <div class="layer_inner">
    <!--layerContent-->
    <div class="title w500">

      <%-- 타이틀 --%>
      <p class="tit">
        <s:message code="login.layer.pwchg.title" />
      </p>

      <a href="#" class="btn_close"></a>
      <div class="con">

        <%-- 패스워드 변경 정보 --%>
        <c:if test="${param.type == 'pwExpire'}">
          <p class="s14">
            <s:message code="login.layer.pwchg.limit" arguments="70" />
          </p>
          <p class="s14 mt10">
            <s:message code="login.layer.pwchg.info" />
          </p>
        </c:if>

        <c:if test="${param.type == 'pwChg'}">
          <p class="s14">
            <s:message code="login.layer.pwchg.first" />
          </p>
          <p class="s14 mt10">
            <s:message code="login.layer.pwchg.info" />
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
                <th><s:message code="login.userId" /></th>
                <td id="labelUserId">${id}</td>
                <input id="pwdUserId" style="display: none" value="${id}" />
              </tr>

              <tr>
                <th><s:message code="login.userPasswd" /></th>
                <td>
                  <div class="msgWrap">
                    <input type="password" id="currentPw" maxlength="25" /> <span
                      id="currentPwError" class="errorMsg" style="display: none"></span>
                  </div>
                </td>
              </tr>

              <tr>
                <th><s:message code="login.pw.chg.new" /></th>
                <td>
                  <div class="msgWrap">
                    <input type="password" id="newPw" maxlength="25" /> <span id="newPwError"
                      class="errorMsg" style="display: none"></span>
                  </div>
                </td>
              </tr>

              <tr>
                <th><s:message code="login.pw.chg.new" />&nbsp;<s:message
                    code="cmm.confirm" /></th>
                <td>
                  <div class="msgWrap">
                    <input type="password" id="newPwConf" maxlength="25" /> <span
                      id="newPwConfError" class="errorMsg" style="display: none"></span>
                  </div>
                </td>
              </tr>

            </tbody>
          </table>
          <p class="mt20 s12">
            <s:message code="login.layer.pwchg.policy" arguments="6,20" />
          </p>
        </div>
      </div>
      <div class="btnSet">
        <%-- 확인 버튼 --%>
        <span> <a href="#" class="btn_blue" id="confirmBtn">
            <s:message code="cmm.confirm" />
          </a>
        </span>
        <%-- 비밀번호 연장하기 --%>
        <c:if test="${param.type == 'pwExpire'}">
          <span> <a href="#" class="btn_gray" id="extensionBtn">
              <s:message code="login.layer.pwchg.ext" />
            </a>
          </span>
        </c:if>
      </div>
    </div>
    <!--//layerContent-->
  </div>
</div>

<script>
  genEventSingle($("#currentPw"));
  genEventSingle($("#newPw"));
  genEventSingle($("#newPwConf"));

  $("#confirmBtn").click(function() {
    var param = {};
    param.userId = $("#pwdUserId").val();
    param.currentPw = $("#currentPw").val();
    param.newPw = $("#newPw").val();
    param.newPwConf = $("#newPwConf").val();

    $.postJSON("/user/userPwdChg.sb", param, function(result) {
      if (result.status === "OK") {
        if (result.data.msg != undefined) {
          s_alert.popOk(result.data.msg, function() {
            location.href = result.data.url;
          });
        }
      } else if (result.status === "FAIL") {
        processError(result.data);
      }
    }).fail(function() {
      alert("Ajax Fail");
    });
  });

  $("#extensionBtn").click(function() {
    var param = {};
    param.userId = $("#pwdUserId").val();
    param.currentPw = $("#currentPw").val();

    $.postJSON("/user/pwdExtension.sb", param, function(result) {
      if (result.status === "OK") {
        if (result.data.msg != undefined) {
          s_alert.popOk(result.data.msg, function() {
            location.href = result.data.url;
          });
        }
      } else if (result.status === "FAIL") {
        processError(result.data);
      }
    }).fail(function() {
      alert("Ajax Fail");
    });
  });

  $(".btn_close").click(function() {
    $("#fullDimmedPw").hide();
    $("#layerpw").hide();
  });

  function processError(data) {
    if (data.currentPw != undefined) {
      $("#currentPwError").text(
          data.currentPw != undefined ? data.currentPw : "");
      $("#currentPwError").show();
    } else {
      $("#currentPwError").hide();
    }

    if (data.newPw != undefined) {
      $("#newPwError").text(data.newPw != undefined ? data.newPw : "");
      $("#newPwError").show();
    } else {
      $("#newPwError").hide();
    }

    if (data.newPwConf != undefined) {
      $("#newPwConfError").text(
          data.newPwConf != undefined ? data.newPwConf : "");
      $("#newPwConfError").show();
    } else {
      $("#newPwConfError").hide();
    }

    if (data.msg != undefined) {
      s_alert.pop(data.msg);
    }

  }
</script>



















