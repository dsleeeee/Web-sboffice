<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="hqDtlDim" class="fullDimmed" style="display:none;"></div>
<div id="hqDtlLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w800">
      <p id="popTitle" class="tit"></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <%-- 사원기초정보 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 사원기초정보 탭 --%>
            <li><a id="hqEmpInfoTab" href="javascript:;" class="on"><s:message code="hqEmp.hqEmpInfo" /></a></li>
            <%-- 메뉴권한 탭 --%>
            <%-- --%>
          </ul>
        </div>
        <%-- 상세 --%>
        <div id="dtlArea" class="" style="display:none;">
          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 사원번호 --%>
                <th>
                  <div class="impWrap"><s:message code="hqEmp.empNo" /></div>
                </th>
                <td  id="vEmpNo"></td>
                <%-- 사원명 --%>
                <th>
                  <div class="impWrap"><s:message code="hqEmp.empNm" /></div>
                </th>
                <td id="vEmpNm"></td>
              </tr>
              <tr>
                <%-- SMS수신여부 --%>
                <th>
                  <div class="impWrap"><s:message code="hqEmp.smsRecvYn" /></div>
                </th>
                <td id="vSmsRecvYn"></td>
                <%-- 웹사용자ID --%>
                <th>
                  <div class="impWrap"><s:message code="hqEmp.userId" /></div>
                </th>
                <td id="vUserId"></td>
              </tr>
              <tr>
                <%-- 비밀번호 --%>
                <th>
                  <div class="impWrap"><s:message code="hqEmp.pwd" /></div>
                </th>
                <td>
                  <span>
                    <a href="javascript:;" class="btn_grayS" id="btnModifyPassword" style="display:none;"><s:message code="hqEmp.modifyBtn" /></a>
                  </span>
                </td>
                <%-- 웹사용여부 --%>
                <th>
                  <div class="impWrap"><s:message code="hqEmp.webUseYn" /></div>
                </th>
                <td id="vWebUseYn"></td>
              </tr>
              <tr>
                <%-- 재직여부 --%>
                <th>
                  <div class="impWrap"><s:message code="hqEmp.serviceFg" /></div>
                </th>
                <td id="vServiceFg"></td>
                <%-- 휴대폰번호 --%>
                <th>
                  <div class="impWrap"><s:message code="hqEmp.mpNo" /></div>
                </th>
                <td id="vMpNo"></td>
              </tr>
            </tbody>
          </table>
        </div>

      </div>
      <%-- 공통 버튼 영역 --%>
      <div class="btnSet">
        <%-- 수정 --%>
        <span><a href="javascript:;" class="btn_blue" id="btnEdit" style="display:none;"><s:message code="cmm.edit" /></a></span>
        <%-- 저장 --%>
        <span><a href="javascript:;" class="btn_blue" id="btnSave" style="display:none;"><s:message code="cmm.save" /></a></span>
        <%-- 닫기 --%>
        <span><a href="javascript:;" class="btn_gray" id="btnClose"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
  </div>
</div>

<%-- 비밀번호 변경 레이어 --%>
<div id="pwdModifyTent" class="fullDimmed" style="display: none;"></div>
<div id="pwdModifyLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w350">
      <%-- 비밀번호 변경 --%>
      <p class="tit"><s:message code="hqEmp.password.modify" /></p>
      <a href="javascript:;" class="btn_close pwdModifyClose"></a>
      <input id="pwdModifyUserId" style="display: none;" />
      <input id="pwdModifyEmpNo" style="display: none;" />
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col class="w40" />
              <col class="w60" />
            </colgroup>
            <tbody>
              <%-- 사용자ID --%>
              <tr>
                <th><s:message code="hqEmp.userId" /></th>
                <td id="layerUserId"></td>
              </tr>
              <%-- 사용자명 --%>
              <tr>
                <th><s:message code="hqEmp.empNm" /></th>
                <td id="layerUserNm"></td>
              </tr>
              <%-- 새비밀번호 --%>
              <tr>
                <th><s:message code="hqEmp.layer.newPassword" /></th>
                <td>
                    <input id="layerNewPassword" type="password" maxlength="16"/>
                </td>
              </tr>
              <%-- 새비밀번호확인 --%>
              <tr>
                <th><s:message code="hqEmp.layer.confirmPassword" /></th>
                <td>
                    <input id="layerConfirmPassword" type="password" maxlength="16" />
                </td>
              </tr>
            </tbody>
          </table>
          <p class="mt20 s12">
            <s:message code="login.layer.pwchg.policy" arguments="6,20" />
          </p>
        </div>
      </div>
      <div id="viewBtnArea" class="mt30 tc">
        <%-- 변경하기 --%>
        <button class="btn_skyblue" id="btnModify"><s:message code="hqEmp.modifyBtn" /></button>
      </div>
    </div>
  </div>
</div>

<script>

<%-- ============================================= 상세 폼 관련 =========================================== --%>

  <%-- 상세정보 팝업 열기 --%>
  function openDtlLayer(items) {
    $("#hqDtlLayer #popTitle").text("<s:message code='hqEmp.hqEmpDtl' />");
    $("#hqDtlLayer").show();
    $("#hqDtlDim").show();
    getDtlData(items);
    $("#dtlArea").show();
    $("#btnEdit").show();
  }

  function getDtlData(items) {
    var param = items;
    $.postJSON("/base/store/emp/hq/detail.sb", param, function(result) {
      var data = result.data;

      <%-- 상세정보 --%>
      $("#vEmpNo").text(data.empNo);
      $("#vEmpNm").text(data.empNm);
      $("#vSmsRecvYn").text(data.smsRecvYnNm);
      $("#vWebUseYn").text(data.webUseYnNm);
      $("#vServiceFg").text(data.serviceFgNm);
      $("#vMpNo").text(data.mpNo);

      if(data.webUseYn == "Y" || data.userId != null) {
        $("#vUserId").text(data.userId);
      } else {
        $("#vUserId").text("");
      }

      if(data.webUseYn == "Y") {
        $("#btnModifyPassword").show();
      } else {
        $("#btnModifyPassword").hide();
      }

      <%-- 수정정보 --%>
      rEmpNo.value              = data.empNo;
      rEmpNm.text              = data.empNm;
      rSmsRecvYn.selectedValue = data.smsRecvYn;
      rUserId.text             = data.userId;
      rWebUseYn.selectedValue  = data.webUseYn;
      rServiceFg.selectedValue = data.serviceFg;
      rMpNo.text               = data.mpNo;

      if(data.webUseYn == "Y" || data.userId != null) {
        $("#btnChkHqUserId").hide();
        rUserId.isReadOnly = true;
      } else {
        $("#btnChkHqUserId").show();
        rUserId.isReadOnly = false;
      }
      rEmpNo.isReadOnly = true;
      $("#btnChkHqEmpNo").hide();

      <%-- 비밀번호 변경정보 --%>
      $("#pwdModifyEmpNo").val(data.empNo);
      $("#pwdModifyUserId").val(data.userId);
      $("#layerUserId").text(data.empNo);
      $("#layerUserNm").text(data.empNm);

    }
    ,function(result){
      var resultKey;
      for(var k in result.data) {
        resultKey = k;
      }

      s_alert.pop(result.data[resultKey]);
    }
    ,function (result) {
      s_alert.pop("Ajax Fail");
      return;
    });
  }

  <%-- ============================================= 데이터 저장 관련 =========================================== --%>

  <%-- validation --%>
  function chkPassWordVal(sendUrl, chkType) {
    <%-- 비밀번호를 입력해주세요. --%>
    var msg = "<s:message code='hqEmp.pwd'/> <s:message code='cmm.require.text'/>";
    if($("#layerConfirmPassword").val() == "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 비밀번호를 확인해주세요. --%>
    var msg = "<s:message code='hqEmp.pwd'/> <s:message code='cmm.require.check'/>";
    if($("#layerConfirmPassword").val()!= $("#layerConfirmPassword").val()) {
      s_alert.pop(msg);
      return;
    }

    modifyPassword(sendUrl);
  }

  <%-- 패스워드 변경 --%>
  function modifyPassword(sendUrl) {
    var param = {};
    param.empNo = $("#pwdModifyEmpNo").val();
    param.userId = $("#pwdModifyUserId").val();
    param.userPwd = $("#layerNewPassword").val();
    param.userPwdCfm = $("#layerConfirmPassword").val();
    param.newUserPwd = $("#layerNewPassword").val();

    $.postJSON(sendUrl, param, function(result) {
     if(result.status == "FAIL") {
         s_alert.pop(result);
         return;
      }
      else {
        if(result.data == "SUCCESS") {
            s_alert.pop("<s:message code='cmm.modifySucc'/>");
            $(".btn_close").click();
            search(1);
        }
        else if(result.data == "PASSWORD_NOT_MATCH") {
            s_alert.pop("<s:message code='hqEmp.passwordNotMatch.msg'/>");
        }
        else if(result.data == "PASSWORD_NOT_CHANGED") {
            s_alert.pop("<s:message code='hqEmp.passwordNotChanged.msg'/>");
        }
        else if(result.data == "PASSWORD_REGEXP") {
            s_alert.pop("<s:message code='hqEmp.passwordRegexp.msg'/>");
        }
        else {
            s_alert.pop("<s:message code='cmm.modifyFail'/>");
        }
      }
    }
    ,function(result){
      var resultKey;
      for(var k in result.data) {
        resultKey = k;
      }

      s_alert.pop(result.data[resultKey]);
    }
    ,function(){
      s_alert.pop("Ajax Fail");
    });
  }

<%-- ============================================= 버튼 이벤트 관련 =========================================== --%>
  $("#btnModifyPassword").click(function(e){

    resetForm();
    $("#dtlArea").hide();
    $("#hqDtlLayer").hide();
    $("#hqDtlDim").hide();
    $("#btnSave").hide();
    $("#btnEdit").hide();

    $("#pwdModifyTent").show();
    $("#pwdModifyLayer").show();

    $("#pwdModifyUserId").val();
    $("#pwdModifyEmpNo").val();
    $("#layerUserId").text();
    $("#layerUserNm").text();

  });

  <%-- 변경하기 버튼 클릭 --%>
   $("#btnModify").click(function(e){
     chkPassWordVal("/base/store/emp/hq/modifyPassword.sb");
   });

  <%-- 수정 버튼 클릭 --%>
  $("#hqDtlLayer #btnEdit").click(function(e){
    $("#hqDtlLayer #popTitle").text("<s:message code='hqEmp.hqEmpMod' />");
    $("#hqDtlLayer").hide();
    $("#hqDtlDim").hide();
    openModLayer();
    $("#dtlArea").hide();

  });

  <%-- 닫기 버튼 클릭 --%>
  $("#hqDtlLayer .btn_close, #hqDtlLayer #btnClose, #pwdModifyLayer .btn_close" ).click(function(e){
    resetForm();
    $("#dtlArea").show();
    $("#hqDtlLayer").hide();
    $("#hqDtlDim").hide();
    $("#btnSave").hide();
    $("#btnEdit").hide();

    <%--패스워드 초기화--%>
    $("#pwdModifyTent").hide();
    $("#pwdModifyLayer").hide();
    $("#layerNewPassword").val("");
    $("#layerConfirmPassword").val("");
  });

</script>

