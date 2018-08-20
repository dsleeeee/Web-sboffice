/****************************************************************
 *
 * 파일명 : pwdManage.js
 * 설  명 : 비밀번호 임의변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function () {

  var srchHqOfficeCd = wcombo.genInputText("#srchHqOfficeCd", 5, "");
  var srchHqOfficeNm = wcombo.genInputText("#srchHqOfficeNm", 50, "");
  var srchStoreCd = wcombo.genInputText("#srchStoreCd", 7, "");
  var srchStoreNm = wcombo.genInputText("#srchStoreNm", 50, "");
  var srchUserId = wcombo.genInputText("#srchUserId", 20, "");
  var srchUserNm = wcombo.genInputText("#srchUserNm", 50, "");

  //  dataMap 
  var serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');
  var webUseYnDataMap = new wijmo.grid.DataMap(webUseYn, 'value', 'name');

  var gridData =
    [
      {binding: "hqOfficeCd", header: messages["pwdManage.hqOfficeCd"], width: "*"},
      {binding: "hqOfficeNm", header: messages["pwdManage.hqOfficeNm"], width: "*"},
      {binding: "storeCd", header: messages["pwdManage.storeCd"], width: "*"},
      {binding: "storeNm", header: messages["pwdManage.storeNm"], width: "*"},
      {binding: "empNo", header: messages["pwdManage.empNo"], visible: false, width: "*"},
      {binding: "userId", header: messages["pwdManage.userId"], width: "*"},
      {binding: "userNm", header: messages["pwdManage.userNm"], width: "*"},
      {
        binding: "serviceFg",
        header: messages["pwdManage.serviceFg"],
        dataMap: serviceFgDataMap,
        width: "*"
      },
      {
        binding: "webUseYn",
        header: messages["pwdManage.webUseYn"],
        dataMap: webUseYnDataMap,
        width: "*"
      },
      {binding: "emailAddr", header: messages["pwdManage.emailAddr"], width: "*"},
      {binding: "mpNo", header: messages["pwdManage.mpNo"], width: "*"},
      {binding: "addr", header: messages["pwdManage.addr"], width: "*"}
    ];

  var grid = wgrid.genGrid("#theGrid", gridData, "Y");
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", listScaleBoxData);

  //  그리드 포맷 
  grid.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if (col.binding == "userId") {
        wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
      }
    }
  });

  // 그리드 선택 이벤트
  grid.addEventListener(grid.hostElement, 'click', function(e) {
    var ht = grid.hitTest(e);
    if ( ht.cellType == wijmo.grid.CellType.Cell ) {
      var col = ht.panel.columns[ht.col];
      var selectedRow = grid.rows[ht.row].dataItem;
      if( col.binding == "userId" ) {
        showPwdManageLayer(selectedRow);
      }
    }
  });

  //  조회버튼 클릭
  $("#btnSearch").click(function (e) {
    search(1);
  });

  //  페이징 
  $(document).on("click", ".page", function () {
    search($(this).data("value"));
  });

  //  비밀번호 임의변경 대상 목록 조회
  function search(index) {
    var param = {};
    param.hqOfficeCd = srchHqOfficeCd.text;
    param.hqOfficeNm = srchHqOfficeNm.text;
    param.hqStoreCd = srchStoreCd.text;
    param.hqStoreNm = srchStoreNm.text;
    param.userId = srchUserId.text;
    param.userNm = srchUserNm.text;
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;

    $.postJSON("/store/manage/pwdManage/pwdManage/list.sb", param,
      function (result) {
        if (result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }
        var list = result.data.list;
        grid.itemsSource = new wijmo.collections.CollectionView(list);

        page.make("#page", result.data.page.curr, result.data.page.totalPage);
        if (list.length === undefined || list.length == 0) {
          grid.itemsSource = new wijmo.collections.CollectionView([]);
          s_alert.pop(result.message);
          return;
        }

      }
      , function () {
        s_alert.pop("Ajax Fail");
      }
    );
  };

  var layerPwdChgFgDataMap = [{"name":"웹(WEB) 비밀번호","value":"WEB"},{"name":"포스(POS) 비밀번호","value":"POS"}];
  var layerPwdChgFgCombo = wcombo.genCommonBoxFun("#layerPwdChgFg", layerPwdChgFgDataMap, function(s,e) {
      $("#layerNewPassword, #layerConfirmPassword").val("");
      if ( s.selectedValue === "WEB" ) {
        $("#layerNewPassword, #layerConfirmPassword").attr('maxlength','16');
        $("#layerNewPassword, #layerConfirmPassword").removeAttr("keyup");
      } else {
        $("#layerNewPassword, #layerConfirmPassword").attr('maxlength','4');
        $("#layerNewPassword, #layerConfirmPassword").on("keyup", function() {
          $(this).val($(this).val().replace(/[^0-9]/g,""));
        });
      }
  });

  // 레이어영역 시작
  $(".pwdModifyClose").click(function (e) {
    closePwdManageLayer();
  });

  function showPwdManageLayer(data) {
    $("#pwdModifyTent").show();
    $("#pwdModifyLayer").show();

    $("#pwdModifyUserId").val(data.userId);
    $("#pwdModifyEmpNo").val(data.empNo);
    $("#layerUserId").text(data.userId);
    $("#layerUserNm").text(data.userNm);

  }

  function closePwdManageLayer() {
    // 비밀번호 값 초기화
    $("#layerNewPassword").val("");
    $("#layerConfirmPassword").val("");
    // 창닫기
    $("#pwdModifyTent").hide();
    $("#pwdModifyLayer").hide();
  }

  //  비밀번호 변경 
  $("#btnModify").click(function (e) {

    var param = {};
    param.pwdChgFg = layerPwdChgFgCombo.selectedValue;
    param.empNo = $("#pwdModifyEmpNo").val();
    param.userId = $("#pwdModifyUserId").val();
    param.newPassword = $("#layerNewPassword").val();
    param.confirmPassword = $("#layerConfirmPassword").val();

    $.postJSON("/store/manage/pwdManage/pwdManage/modify.sb", param,
      function (result) {
        s_alert.popOk(messages["pwdManage.modifySucc"], function () {
          closePwdManageLayer();
        });
      },
      function (result) {
        processError(result.data);
      }
    );

  });
  
  // 비밀번호 변경 오류 실패처리
  function processError(data) {

    if (data.newPassword != undefined) {
      $("#newPasswordError").text(data.newPassword != undefined ? data.newPassword : "");
      $("#newPasswordError").show();
    } else {
      $("#newPasswordError").hide();
    }

    if (data.confirmPassword != undefined) {
      $("#confirmPasswordError").text(
        data.confirmPassword != undefined ? data.confirmPassword : "");
      $("#confirmPasswordError").show();
    } else {
      $("#confirmPasswordError").hide();
    }

    if (data.msg != undefined) {
      s_alert.pop(data.msg);
    }
  }

});
