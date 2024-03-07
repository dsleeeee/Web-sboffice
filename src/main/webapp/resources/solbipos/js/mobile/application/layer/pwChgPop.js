/****************************************************************
 *
 * 파일명 : pwChgPop.js
 * 설  명 : 모바일 비밀번호 변경팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.05     이다솜      1.0
 *
 * **************************************************************/
genEventSingle($("#currentPw"));
genEventSingle($("#newPw"));
genEventSingle($("#newPwConf"));

$("#confirmBtn").click(function() {
  var param = {};
  param.userId = $("#pwdUserId").val();
  param.currentPw = $("#currentPw").val();
  param.newPw = $("#newPw").val();
  param.newPwConf = $("#newPwConf").val();

    if(param.currentPw === null || param.currentPw === ''
        || param.newPw === null || param.newPw === ''
        || param.newPwConf === null || param.newPwConf === ''){
        s_alert.pop(messages["systemEmp.result.fail.pwd"]); // 비밀번호 변경에 실패하였습니다.
        return false;
    }

  $.postJSON("/mobile/user/userPwdChg.sb", param, function (result) {
      if (result.status === "OK") {
        if (result.data.msg != undefined) {
          s_alert.popOk(result.data.msg, function () {
            location.href = result.data.url;
          });
        }
      } else if (result.status === "FAIL") {
        processError(result.data);
      }
    },
    function (result) {
      s_alert.pop(result.message);
      return;
    });
});

$("#extensionBtn").click(function() {
    var param = {};
    param.userId = $("#pwdUserId").val();
    param.currentPw = $("#currentPw").val();

    $.postJSON("/mobile/user/pwdExtension.sb", param, function(result) {
        if (result.status === "OK") {
            if (result.data.msg != undefined) {
                s_alert.popOk(result.data.msg, function() {
                    location.href = result.data.url;
                });
            }
        } else if (result.status === "FAIL") {
            processError(result.data);
        }
    },
    function (result) {
      s_alert.pop(result.message);
      return;
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
