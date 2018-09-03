/****************************************************************
 *
 * 파일명 : storeEmp.js
 * 설  명 : 매장사원정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.29     이한빈      1.0
 *
 * **************************************************************/
$(document).ready(function () {
  //<%-- ============================================= 조회 및 레이어 관련 =========================================== --%>
  //<%-- 조회관련 변수 --%>
  var srchStartDt = wcombo.genDate("#srchStartDt"),
      srchEndDt = wcombo.genDate("#srchEndDt"),
      srchEmpNo = wcombo.genInputText("#srchEmpNo", "4", ""),
      srchEmpNm = wcombo.genInput("#srchEmpNm"),
      srchUserId = wcombo.genInput("#srchUserId"),
      srchServiceFg = wcombo.genCommonBox("#srchServiceFg", serviceFg),
      srchMpNo = wcombo.genInput("#srchMpNo"),
      srchWebUseYn = wcombo.genCommonBox("#srchWebUseYn", useYn),
      srchSmsRecvYn = wcombo.genCommonBox("#srchSmsRecvYn", RecvYn);

  //<%-- 등록관련 변수 --%>
  var rEmpNo = wcombo.genInputText("#rEmpNo", "4", ""),
      rEmpNm = wcombo.genInput("#rEmpNm"),
      rUserId = wcombo.genInput("#rUserId"),
      rServiceFg = wcombo.genCommonBox("#rServiceFg", serviceFgExcpAll),
      rMpNo = wcombo.genInput("#rMpNo"),
      rWebUseYn = wcombo.genCommonBox("#rWebUseYn", useYnExcpAll),
      rSmsRecvYn = wcombo.genCommonBox("#rSmsRecvYn", RecvYnExcpAll),
      rNewPwd = $("#rNewPwd"),
      rNewPwdConfirm = $("#rNewPwdConfirm"),
      rStoreCd = "";

  //<%-- 그리드 Header --%>
  var hData =
    [
      {binding:"empNo", header:messages["storeEmp.empNo"], width: "*"},//사원번호
      {binding:"empNm", header:messages["storeEmp.empNm"], width: "*"},//사원명
      {binding:"userId", header:messages["storeEmp.userId"], width: "*"},//사원ID
      {binding:"serviceFgNm", header:messages["storeEmp.serviceFg"], width: "*"},//재직여부
      {binding:"mpNo", header:messages["storeEmp.mpNo"], width: "*"},//휴대폰번호
      {binding:"smsRecvYn", header:messages["storeEmp.smsRecvYn"], width: "*", dataType:wijmo.DataType.Boolean},//SMS수신여부
    ];

  var grid          = wgrid.genGrid("#storeEmpGrid", hData, false);
  var listScaleBox  = wcombo.genCommonBox("#listScaleBox", listScaleBoxData);

  //<%-- 그리드 포맷 --%>
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "empNm" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  //<%-- 그리드 선택 이벤트 --%>
  grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
    var ht = grid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];

      if( col.binding == "empNm" ) {
        selectedEmp = grid.rows[ht.row].dataItem;
        openDtlLayer(selectedEmp);
      }
    }
  });

  //<%-- 그리드 조회 --%>
  function srchGrid(index) {
    var param = {};
    param.startDt = getDate(srchStartDt);
    param.endDt = getDate(srchEndDt);
    param.empNo = srchEmpNo.value;
    param.empNm = srchEmpNm.text;
    param.userId = srchUserId.text;
    param.serviceFg = srchServiceFg.selectedValue;
    param.mpNo = srchMpNo.text;
    param.webUseYn = srchWebUseYn.selectedValue;
    param.smsRecvYn = srchSmsRecvYn.selectedValue;
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;
    param.chkDt = $("#chkDt").is(":checked");

    $.postJSON(baseUrl + "list.sb", param,
      function (result) {
        var list = result.data.list;

        if (list.length === undefined || list.length == 0) {
          s_alert.pop(messages["cmm.empty.data"]);
        }

        grid.itemsSource = list;

        page.make("#page", result.data.page.curr, result.data.page.totalPage);
      },
      function () {
        s_alert.pop("Ajax Fail");
      }
    );
  };

  //<%-- 등록/수정 팝업 열기 --%>
  function openSaveLayer(flag) {
    if( flag == "regist" )
      setRegistView();
    else if( flag == "modify" )
      setModifyView();

    showSave();
  }

  //<%-- 등록수정 레이아웃 보여주기 --%>
  function showSave(){
    $("#storeSaveLayer").show();
    $("#storeSaveDim").show();
  }

  //<%-- 등록수정 레이아웃 숨김 --%>
  function hideSave(){
    $("#storeSaveLayer").hide();
    $("#storeSaveDim").hide();
    resetForm();
  }

  //<%-- 등록화면 초기화 --%>
  function resetForm() {
    //<%-- INPUT 초기화 --%>
    $("#regForm")[0].reset();
    rServiceFg.selectedIndex = 0;
    rSmsRecvYn.selectedIndex = 0;
    rWebUseYn.selectedIndex = 1;
    rUserId.text = "";
    rNewPwd.val("");
    rNewPwdConfirm.val("");
    rStoreCd = "";

    $(".errorMsg").text("").hide();

    rWebUseYn.onSelectedIndexChanged();

    //<%-- span 초기화 --%>
    $("#empNo").text("");
    $("#userId").text("");
  }

  //<%-- 등록 뷰 세팅 --%>
  function setRegistView() {
    $(".regist").show();
    resetForm();
  }

  //<%-- 수정 뷰 세팅 --%>
  function setModifyView() {
    $(".regist").hide();
  }

  //<%-- 상세정보 팝업 열기 --%>
  function openDtlLayer(items) {
    getDtlData(items);
    showDetail();
  }

  function getDtlData(items) {
    $("#btnChangePwd").hide();

    var param = items;

    $.postJSON(baseUrl + "detail.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      var data = result.data;

      //<%-- 상세정보 --%>
      $("#vEmpNo").text(data.empNo);
      $("#vEmpNm").text(data.empNm);
      $("#vSmsRecvYn").text(data.smsRecvYnNm);
      $("#vUserId").text(data.userId == null ? "": data.userId);
      $("#vWebUseYn").text(data.webUseYnNm);
      $("#vServiceFg").text(data.serviceFgNm);
      $("#vMpNo").text(data.mpNo);

      //<%-- 상세정보 수정 뷰에 값 세팅 --%>
      $("#empNo").text(data.empNo);
      $("#userId").text(data.userId == null ? "": data.userId);
      rStoreCd = data.storeCd;
      rEmpNm.text = data.empNm;
      rSmsRecvYn.selectedValue = data.smsRecvYn;
      rServiceFg.selectedValue = data.serviceFg;
      rMpNo.text = data.mpNo;
      rWebUseYn.selectedValue = data.webUseYn;
      rWebUseYn.onSelectedIndexChanged();

      //<%-- 비밀번호 변경 버튼 노출 --%>
      if( data.webUseYn == "Y" ) {
        $("#btnChangePwd").show();
      }
    }
    ,function(result){
      s_alert.pop(result.message);
    });
  }

  //<%-- 상세정보 레이아웃 보여주기 --%>
  function showDetail(){
    $("#storeDetailLayer").show();
    $("#storeDetailDim").show();
  }

  //<%-- 상세정보 레이아웃 숨김 --%>
  function hideDetail(){
    $("#storeDetailLayer").hide();
    $("#storeDetailDim").hide();
  }

  //<%-- 비밀번호 변경 레이아웃 보여주기 --%>
  function showPwdManageLayer(data) {
    $("#pwdModifyTent").show();
    $("#pwdModifyLayer").show();

    $("#pwdModifyUserId").val(data.userId);
    $("#pwdModifyEmpNo").val(data.empNo);
    $("#layerUserId").text(data.userId);
    $("#layerUserNm").text(data.userNm);

    $(".errorMsg").text("").hide();
  }

  //<%-- 비밀번호 변경 레이아웃 숨김 --%>
  function closePwdManageLayer() {
    // 비밀번호 값 초기화
    $("#newPwdLayer").val("");
    $("#newPwdConfirmLayer").val("");
    // 창닫기
    $("#pwdModifyTent").hide();
    $("#pwdModifyLayer").hide();
  }

  //<%-- ============================================= 데이터 저장 관련 =========================================== --%>

  //<%-- validation --%>
  function chkVal() {
    //<%-- 사원번호를 입력해주세요. --%>
    var msg = messages["storeEmp.empNo"] + messages["cmm.require.text"];
    if(rEmpNo.value === "" && !$("#empNo").text()) {
      $("#empNoErrorMsg").text(msg).show();
      $("#rEmpNo").find("input").focus();
      return;
    }

    //<%-- 사원명을 입력해주세요. --%>
    var msg = messages["storeEmp.empNm"] + messages["cmm.require.text"];
    if(rEmpNm.text === "") {
      $("#empNmErrorMsg").text(msg).show();
      $("#rEmpNm").find("input").focus();
      return;
    }

    if(rWebUseYn.selectedValue == "Y" && webUserRegist){
      //<%-- 웹사용자 ID 중복체크를 해주세요. --%>
      var msg = messages["storeEmp.require.chk.id"];
      if(isDuplicated) {
        $("#userIdErrorMsg").text(msg).show();
        $("#rUserId").find("input").focus();
        return;
      }

      //<%-- 비밀번호를 입력해주세요. --%>
      var msg = messages["storeEmp.pwd"] + messages["cmm.require.text"];
      if(!rNewPwd.val()) {
        $("#newPwdErrorMsg").text(msg).show();
        $("#rNewPwd").focus();
        return;
      }

      //<%-- 비밀번호 확인을 입력해주세요. --%>
      var msg = messages["storeEmp.pwdConfirm"] + messages["cmm.require.text"];
      if(!rNewPwdConfirm.val()) {
        $("#newPwdConfirmErrorMsg").text(msg).show();
        $("#rNewPwdConfirm").focus();
        return;
      }

      //<%-- 입력하신 비밀번호가 비밀번호확인과 일치하지 않습니다. --%>
      var msg = messages["storeEmp.result.fail.pwdconfirm"];
      if(rNewPwd.val() != rNewPwdConfirm.val()){
        $("#newPwdConfirmErrorMsg").text(msg).show();
        $("#rNewPwdConfirm").focus();
        return;
      }
    }

    //<%-- 휴대폰번호를 입력해주세요. --%>
    var msg = messages["storeEmp.mpNo"] + messages["cmm.require.text"];
    if(rMpNo.text === "") {
      $("#mpNoErrorMsg").text(msg).show();
      $("#rMpNo").find("input").focus();
      return;
    }

    saveStoreEmp();
  }

  //<%-- 저장 --%>
  function saveStoreEmp() {
    var param = {};
    param.storeCd = rStoreCd;
    param.empNo = rEmpNo.value ? rEmpNo.value : $("#empNo").text();
    param.empNm = rEmpNm.text;
    param.serviceFg = rServiceFg.selectedValue;
    param.mpNo = rMpNo.text;
    param.webUseYn = rWebUseYn.selectedValue;
    param.smsRecvYn = rSmsRecvYn.selectedValue;
    param.userId = rUserId.text ? rUserId.text : $("#userId").text();
    param.webUserRegist = webUserRegist;

    if( rWebUseYn.selectedValue == "Y" ) {
      param.newPwd = rNewPwd.val();
      param.newPwdConfirm = rNewPwdConfirm.val();
    }

    //console.log(param)

    $.postJSONSave(baseUrl + "save.sb", param, function(result) {
      s_alert.popOk(messages["cmm.saveSucc"], function () {
        $(".btn_close").click();
        srchGrid(1);
      });
    },
      function (result) {
        if( result.message ) {
          s_alert.pop(result.message);
          return;
        }

        if( result.data ) {
          var keys = Object.keys(result.data);
          keys.forEach(function(key){
            $("#"+key+"ErrorMsg").text(result.data[key]).show();
          });
          return;
        }

        s_alert.pop(messages["cmm.error"]);
      }
    );
  }

  //<%-- ============================================= 버튼 이벤트 관련 =========================================== --%>

  //<%-- 페이징 --%>
  $(".page").click(function() {
    srchGrid($(this).data("value"));
  });

  //<%-- 사원신규등록 버튼 클릭--%>
  $("#btnRegist").click(function(){
    openSaveLayer("regist");
  });

  //<%-- 조회 버튼 클릭--%>
  $("#btnSearch").click(function(){
    srchGrid(1);
  });

  //<%-- 엑셀 다운로드 버튼 클릭 --%>
  $("#btnExcel").click(function(){
    var name = menuNm;
    wexcel.down(grid, name, name + ".xlsx");
  });

  //<%-- 전체기간 체크박스 --%>
  $("#chkDt").click(function() {
    var chkDt = $('#chkDt').is(":checked");
    srchStartDt.isDisabled = chkDt;
    srchEndDt.isDisabled = chkDt;
  });

  //<%-- 등록/수정 레이어 저장 버튼 클릭 --%>
  $("#storeSaveLayer #btnSave").click(function(e){
    chkVal();
  });

  //<%-- 닫기 버튼 클릭 --%>
  $("#storeSaveLayer .btn_close, #storeSaveLayer .btnClose").click(function(e){
    hideSave();
  });

  var isDuplicated = true;
  //<%-- 중복체크 버튼 클릭 --%>
  $("#storeSaveLayer #btnCheckDup").click(function(e){
    var userId = rUserId.text;

    var msg = messages["storeEmp.layer.userId"] + messages["cmm.require.text"];
    if( !userId ) {
      s_alert.pop(msg);
      return;
    }

    $.postJSON(baseUrl + "checkUserId.sb", {"userId" : userId}, function(result) {
        if( result.data ) {
          s_alert.pop(messages["storeEmp.result.fail.id"]);
        } else {
          s_alert.pop(messages["storeEmp.result.succ.id"]);
          isDuplicated = false;
        }
      },
        function (result) {
          s_alert.pop(result.message);
          return;
        }
      );
  });

  //<%-- 웹사용자ID 변경 시 중복체크 관련 값 세팅 --%>
  rUserId.textChanged.addHandler(function(s, e){
    isDuplicated = true;
  });

  var webUserRegist = false;
  //<%-- 웹사용여부 변경 이벤트 --%>
  rWebUseYn.selectedIndexChanged.addHandler(function(s, e){
    var webUseYn = s["selectedItem"]["value"];
    refreshWebUserInfoView(webUseYn);
  });

  //<%-- 웹사용여부 변경에 따른 뷰 설정 --%>
  function refreshWebUserInfoView(webUseYn){
    $("#webUserModifyView").hide();
    $("#webUserRegistView").hide();
    $("#detailPwdArea").find("input").removeAttr("disabled");
    webUserRegist = false;

    var isRegist = !rStoreCd; // 등록 or 수정
    var userId = $("#userId").text();

    // 등록 뷰
    if(webUseYn == "Y" && (isRegist || !userId)) {
      $(".regist").show();
      $(".webUseY").attr("style","display:inline-block");
      $("#webUserRegistView").show();
      webUserRegist = true;
    }
    // 수정 뷰
    else {
      $(".webUseY").attr("style","display:none");
      $("#webUserModifyView").show();

      if(webUseYn == "N") {
        rUserId.text = "";
        $("#detailPwdArea").find("input").attr("disabled","disabled");
      }
    }
  }

  //<%-- 상세레이어 수정 버튼 클릭 --%>
  $("#storeDetailLayer #btnEdit").click(function(e){
    hideDetail();
    openSaveLayer("modify");
  });

  //<%-- 상세레이어 닫기 버튼 클릭 --%>
  $("#storeDetailLayer .btn_close, #storeDetailLayer .btnClose").click(function(e){
    hideDetail();
  });

  //<%-- 상세 레이어 변경하기 버튼 클릭 --%>
  $("#storeDetailLayer #btnChangePwd").click(function(e){
    var param = {};
    param.userId = $("#vUserId").text();
    param.empNo = $("#vEmpNo").text();
    param.userId = $("#vUserId").text();
    param.userNm = $("#vEmpNm").text();

    showPwdManageLayer(param);
  });

  // 비밀번호 변경 레이어 닫기
  $(".pwdModifyClose").click(function (e) {
    closePwdManageLayer();
  });

  // 비밀번호 변경레이어 변경하기 버튼클릭
  $("#btnPwdModify").click(function (e) {
    var param = {};
    param.webUseYn = "Y";
    param.empNo = $("#pwdModifyEmpNo").val();
    param.userId = $("#pwdModifyUserId").val();
    param.newPwd = $("#newPwdLayer").val();
    param.newPwdConfirm = $("#newPwdConfirmLayer").val();

    $.postJSONSave(baseUrl + "modifyPwd.sb", param,
      function (result) {
        s_alert.popOk(messages["pwdManage.modifySucc"], function () {
          closePwdManageLayer();
        });
      },
      function (result) {
        if( result.data ) {
          var keys = Object.keys(result.data);
          keys.forEach(function(key){
            $("#"+key+"LayerErrorMsg").text(result.data[key]).show();
          });
          return;
        }

        s_alert.pop(messages["cmm.error"]);
      }
    );
  });

  $("input.wj-form-control, input[type=password]").bind("change keydown",function(){
    var errorMsg = $(this).parents().filter(".msgWrap").find(".errorMsg");
    if( errorMsg ) errorMsg.text("").hide();
  });
});