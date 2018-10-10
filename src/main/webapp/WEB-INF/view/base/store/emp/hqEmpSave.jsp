<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="hqRegDim" class="fullDimmed" style="display:none;"></div>
<div id="hqRegLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w800">
      <p id="popTitle" class="tit"></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <%-- 사원기초정보 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 사원기초정보 탭 --%>
            <li><a id="hqEmpInfoTab" href="#" class="on"><s:message code="hqEmp.hqEmpInfo" /></a></li>
            <%-- 메뉴권한 탭 --%>
            <%-- --%>
          </ul>
        </div>
        <%-- 등록/수정 --%>
        <div id="regArea" class="mt20 sc" style="display:none;">
          <form id="regForm">
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
                    <div class="impWrap"><s:message code="hqEmp.empNo" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <span class="w50 txtIn">
                      <div class="sb-select">
                        <div id="rEmpNo"></div>
                      </div>
                    </span>
                    <%-- 중복체크 --%>
                    <span>
                      <a href="#" class="btn_grayS" id="btnChkHqEmpNo"><s:message code="hqEmp.chk.duplicate" /></a>
                    </span>
                  </td>
                  <%-- 사원명 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqEmp.empNm" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rEmpNm" class="sb-input"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- SMS수신여부 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqEmp.smsRecvYn" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rSmsRecvYn"></div>
                    </div>
                  </td>
                  <%-- 웹사용자ID --%>
                  <th>
                    <div class="impWrap"><s:message code="hqEmp.userId" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <span class="w50 txtIn">
                      <div class="sb-select">
                        <div id="rUserId"></div>
                      </div>
                    </span>
                    <%-- 중복체크 --%>
                    <span class="txtIn">
                      <a href="#" class="btn_grayS" id="btnChkHqUserId"><s:message code="hqEmp.chk.duplicate" /></a>
                    </span>
                  </td>
                </tr>
                <tr>
                  <%-- 비밀번호 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqEmp.pwd" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <span class="w45 txtIn" >
                      <div class="sb-select">
                        <input class="pw" type="password" id="rUserPwd" name="rUserPwd" maxlength="25">
                      </div>
                    </span>
                    <span class="w45 txtIn">
                      <div class="sb-select">
                        <input class="pw" type="password" id="rUserPwdCfm" name="rUserPwdCfm"
                        placeholder="<s:message code='hqEmp.pwdChk'/>" maxlength="25">
                      </div>
                    </span>
                  </td>
                  <%-- 웹사용여부 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqEmp.webUseYn" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rWebUseYn"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 재직여부 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqEmp.serviceFg" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rServiceFg"></div>
                    </div>
                  </td>
                  <%-- 휴대폰번호 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqEmp.mpNo" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rMpNo"></div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </form>
        </div>
      </div>

      <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
      <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

      <%-- 공통 버튼 영역 --%>

      <div class="btnSet">
        <%-- 등록 --%>
        <span><a href="#" class="btn_blue" id="btnReg" style="display:none;"><s:message code="cmm.new.add" /></a></span>
        <%-- 수정 --%>
        <span><a href="#" class="btn_blue" id="btnSave" style="display:none;"><s:message code="cmm.save" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_gray" id="btnClose"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

  var smsRecvYnData  = ${ccu.getCommCodeExcpAll("072")};
  <%--var webUseYnData   = ${ccu.getCommCodeExcpAll("067")};--%>
  var fireFgData     = ${ccu.getCommCodeExcpAll("007")};

  var rEmpNo     = wcombo.genInputText("#rEmpNo",4,"",null);
  var rEmpNm     = wcombo.genInput("#rEmpNm");
  var rSmsRecvYn = wcombo.genCommonBox("#rSmsRecvYn",smsRecvYnData);
  var rUserId    = wcombo.genInput("#rUserId");
  var rWebUseYn  = wcombo.genCommonBox("#rWebUseYn",webUseYnData);
  var rServiceFg = wcombo.genCommonBox("#rServiceFg",fireFgData);
  var rMpNo      = wcombo.genInput("#rMpNo");



<%-- ============================================= 신규등록폼 관련 =========================================== --%>

  <%-- 등록 팝업 열기 --%>
  function openRegistLayer() {
    $("#hqRegLayer #popTitle").text("<s:message code='hqEmp.hqEmpReg' />");
    $("#hqRegLayer").show();
    $("#hqRegDim").show();
    $("#regArea").show();
    $("#btnReg").show();
    $("#btnSave").hide();

    rUserId.isReadOnly = false;
    rEmpNo.isReadOnly = false;
    $("#btnChkHqEmpNo").show();
    $("#btnChkHqUserId").show();

  }

  function openModLayer(items) {
    $("#hqRegLayer #popTitle").text("<s:message code='hqEmp.hqEmpMod' />");
    $("#hqRegLayer").show();
    $("#hqRegDim").show();
    $("#regArea").show();

    $("#btnReg").hide();
    $("#btnSave").show();

  }

  function getModData(items) {
    var param = items;

    $.postJSON("/base/store/emp/hq/detail.sb", param, function(result) {
      var data = result.data;

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


    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 폼 리셋 --%>
  function resetForm() {
    $("#regForm")[0].reset();
    rSmsRecvYn.selectedIndex  = 0;
    rWebUseYn.selectedIndex    = 0;
    rServiceFg.selectedIndex   = 0;
  }

<%-- ============================================= 데이터 저장 관련 =========================================== --%>

  <%-- validation --%>
  function chkVal(sendUrl, chkType) {

    if(chkType == "btnReg") {
      <%-- 사원번호 입력해주세요. --%>
      var msg = "<s:message code='hqEmp.empNo'/> <s:message code='cmm.require.text'/>";
      if(rEmpNo.value == "") {
        s_alert.pop(msg);
        return;
      }

      <%-- 사원번호를 중복체크 해주세요. --%>
      var msg = "<s:message code='hqEmp.empNo'/> <s:message code='cmm.require.duplicate'/>";
      if(!isHqEmpNoChk || ( rEmpNo.value != tmpHqEmpNo )) {
        s_alert.pop(msg);
        return;
      }

      <%-- 웹사용자아이디를 입력해주세요. --%>
      var msg = "<s:message code='hqEmp.userId'/> <s:message code='cmm.require.text'/>";
      if(rWebUseYn.selectedValue == "Y" && rUserId.text == "") {
        s_alert.pop(msg);
        return;
      }

      <%-- 웹사용자아이디를 중복체크 해주세요. --%>
      var msg = "<s:message code='hqEmp.userId'/> <s:message code='cmm.require.duplicate'/>";
      if(rWebUseYn.selectedValue == "Y" && (!isHqUserIdChk || ( rUserId.text != tmpHqUserId ))) {
        s_alert.pop(msg);
        return;
      }

     <%-- 비밀번호를 입력해주세요. --%>
      var msg = "<s:message code='hqEmp.pwd'/> <s:message code='cmm.require.text'/>";
      if(rWebUseYn.selectedValue == "Y" && $("#rUserPwd").val() == "") {
        s_alert.pop(msg);
        return;
      }

      <%-- 비밀번호를 확인해주세요. --%>
      var msg = "<s:message code='hqEmp.pwd'/> <s:message code='cmm.require.check'/>";
      if( $("#rUserPwd").val() != $("#rUserPwdCfm").val() ) {
        s_alert.pop(msg);
        return;
      }
    }

    if(chkType == "btnSave") {
      <%-- 비밀번호를 확인해주세요. --%>
      var msg = "<s:message code='hqEmp.pwd'/> <s:message code='cmm.require.check'/>";
      if(rWebUseYn.selectedValue == "Y" && $("#rUserPwd").val() != $("#rUserPwdCfm").val()) {
        s_alert.pop(msg);
        return;
      }

      if(rUserId.isReadOnly == false)
      {
        <%-- 웹사용자아이디를 입력해주세요. --%>
        var msg = "<s:message code='hqEmp.userId'/> <s:message code='cmm.require.text'/>";
        if(rWebUseYn.selectedValue == "Y" && rUserId.text == "") {
          s_alert.pop(msg);
          return;
        }

        <%-- 웹사용자아이디를 중복체크 해주세요. --%>
        var msg = "<s:message code='hqEmp.userId'/> <s:message code='cmm.require.duplicate'/>";
        if(rWebUseYn.selectedValue == "Y" && (!isHqUserIdChk || ( rUserId.text != tmpHqUserId ))) {
          s_alert.pop(msg);
          return;
        }

        <%-- 비밀번호를 입력해주세요. --%>
        var msg = "<s:message code='hqEmp.pwd'/> <s:message code='cmm.require.text'/>";
        if(rWebUseYn.selectedValue == "Y" && $("#rUserPwd").val() == "") {
          s_alert.pop(msg);
          return;
        }

        <%-- 비밀번호를 확인해주세요. --%>
        var msg = "<s:message code='hqEmp.pwd'/> <s:message code='cmm.require.check'/>";
        if( $("#rUserPwd").val() != $("#rUserPwdCfm").val() ) {
          s_alert.pop(msg);
          return;
        }
      }

    }

    <%-- 사원명 입력해주세요. --%>
    var msg = "<s:message code='hqEmp.empNm'/> <s:message code='cmm.require.text'/>";
    if(rEmpNm.text == "") {
      s_alert.pop(msg);
      return;
    }

    <%-- SMS수신여부를 선택해주세요. --%>
    var msg = "<s:message code='hqEmp.smsRecvYn'/> <s:message code='cmm.require.select'/>";
    if(rSmsRecvYn.selectedValue == "" || rSmsRecvYn.selectedValue == null) {
      s_alert.pop(msg);
      return;
    }

    <%-- 웹사용여부를 선택해주세요. --%>
    var msg = "<s:message code='hqEmp.webUseYn'/> <s:message code='cmm.require.select'/>";
    if(rWebUseYn.selectedValue == "" || rWebUseYn.selectedValue == null) {
      s_alert.pop(msg);
      return;
    }

    <%-- 재직여부를 선택해주세요. --%>
    var msg = "<s:message code='hqEmp.serviceFg'/> <s:message code='cmm.require.select'/>";
    if(rServiceFg.selectedValue == "" || rServiceFg.selectedValue == null) {
      s_alert.pop(msg);
      return;
    }

    <%-- 휴대폰번호를 입력해주세요. --%>
    var msg = "<s:message code='hqEmp.mpNo'/> <s:message code='cmm.require.text'/>";
    if(rMpNo.text == "") {
      s_alert.pop(msg);
      return;
    }

    registHqEmp(sendUrl);
  }


  <%-- 저장 --%>
  function registHqEmp(sendUrl) {

    var param = {};
    param.empNo      = rEmpNo.value;
    param.empNm      = rEmpNm.text;
    param.smsRecvYn  = rSmsRecvYn.selectedValue;
    param.userPwd    = $("#rUserPwd").val();
    param.userPwdCfm = $("#rUserPwdCfm").val();
    param.newUserPwd = $("#rUserPwd").val();
    param.userId     = rUserId.text;
    param.webUseYn   = rWebUseYn.selectedValue;
    param.serviceFg  = rServiceFg.selectedValue;
    param.mpNo       = rMpNo.text;

    $.postJSONSave(sendUrl, param, function(result) {

      if(result.status == "FAIL") {
        s_alert.pop(result);
        return;
      }else{
        if(result.data == "SUCCESS") {
            s_alert.pop("<s:message code='cmm.registSucc'/>");
            $(".btn_close").click();
            search(1);
        }
        else if(result.data == "EMP_NO_DUPLICATE") {
            s_alert.pop("<s:message code='hqEmp.empNo.duplicate.msg'/>");
        }
        else if(result.data == "USER_ID_DUPLICATE") {
            s_alert.pop("<s:message code='hqEmp.userId.duplicate.msg'/>");
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
        else if(result.data == "USER_ID_REGEXP") {
            s_alert.pop("<s:message code='hqEmp.userIdRegexp.msg'/>");
        }
        else {
            s_alert.pop("<s:message code='cmm.registFail'/>");
        }
      }
    }
    ,function(result) {
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

  <%-- 사원번호 중복체크 버튼 클릭 --%>
  var isHqEmpNoChk = false;
  var tmpHqEmpNo = "";
  $("#btnChkHqEmpNo").click(function(e){

    var param = {};
    param.empNo = rEmpNo.value;

    var msg = "<s:message code='hqEmp.empNo'/> <s:message code='cmm.require.text'/>";
    if( param.empNo == "" ) {
        s_alert.pop(msg);
        return;
    }

    var msg = "<s:message code='hqEmp.empNoRegexp.msg'/>";
    if(param.empNo.length != 4) {
      s_alert.pop(msg);
      return;
    }

    $.postJSON("/base/store/emp/hq/chkHqEmpNo.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }

      isHqEmpNoChk = false;
      tmpHqEmpNo = "";
      if(result.data == "SUCCESS") {
        isHqEmpNoChk = true;
        tmpHqEmpNo = param.empNo;
        s_alert.pop("<s:message code='hqEmp.notDuplicate.msg'/>");
      }
      else if(result.data == "EMP_NO_REGEXP") {
        s_alert.pop("<s:message code='hqEmp.empNoRegexp.msg'/>");
      }
      else if(result.data == "FAIL") {
        s_alert.pop("<s:message code='cmm.error'/>");
      }
      else {
        s_alert.pop("<s:message code='hqEmp.empNo.duplicate.msg'/>");
      }
    }
    ,function(result) {
      var resultKey;
      for(var k in result.data) {
        resultKey = k;
      }
      s_alert.pop(result.data[resultKey]);
    }
    ,function(){
      s_alert.pop("Ajax Fail");
    });
  });

  <%-- 사용자아이디 중복체크 버튼 클릭 --%>
  var isHqUserIdChk = false;
  var tmpHqUserId = "";
  $("#btnChkHqUserId").click(function(e){

    var param = {};
    param.userId = rUserId.text;
    param.webUseYn  = rWebUseYn.selectedValue;

    var msg = "<s:message code='hqEmp.webUseYn'/> <s:message code='cmm.require.check'/>";
    if( param.webUseYn != "Y"){
        s_alert.pop(msg);
        return;
    }

    msg = "<s:message code='hqEmp.userId'/> <s:message code='cmm.require.text'/>";
    if( param.userId == ""){
        s_alert.pop(msg);
        return;
        }

    $.postJSON("/base/store/emp/hq/chkHqUserId.sb", param, function(result) {

      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }

      isHqUserIdChk = false;
      tmpHqUserId = "";
      if(result.data == "SUCCESS"){
        isHqUserIdChk = true;
        tmpHqUserId = param.userId;
        s_alert.pop("<s:message code='hqEmp.notDuplicate.msg'/>");
      } else if(result.data == "USER_ID_REGEXP"){
        s_alert.pop("<s:message code='hqEmp.userIdRegexp.msg'/>");
      } else {
        s_alert.pop("<s:message code='hqEmp.userId.notDuplicate.msg'/>");
      }
    }
    ,function(result) {
      var resultKey;
      for(var k in result.data) {
        resultKey = k;
      }
      s_alert.pop(result.data[resultKey]);
    }
    ,function(){
      s_alert.pop("Ajax Fail");
    });
  });

  <%-- 신규등록 버튼 클릭 --%>
   $("#btnReg").click(function(e){
     chkVal("/base/store/emp/hq/regist.sb",$("#btnReg").attr('id'));
   });

  <%-- 저장 버튼 클릭 (수정) --%>
  $("#btnSave").click(function(e){
    chkVal("/base/store/emp/hq/save.sb",$("#btnSave").attr('id'));
  });

  <%-- 닫기 버튼 클릭 --%>
  $("#hqRegLayer .btn_close, #hqRegLayer #btnClose").click(function(e){
    resetForm();
    $("#regArea").hide();
    $("#hqRegLayer").hide();
    $("#hqRegDim").hide();

    $("#btnChkHqEmpNo").show();
    $("#btnChkHqUserId").show();

    rUserId.isReadOnly = false;
    rEmpNo.isReadOnly = false;

  });

</script>

