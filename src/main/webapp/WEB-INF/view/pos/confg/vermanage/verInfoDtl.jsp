<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 버전 상세정보 레이어 --%>
<div id="dim1" class="fullDimmed" style="display:none;"></div>
<div id="verInfoDtlLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w700">
      <%-- 레이어 타이틀 : 버전정보 --%>
      <p class="tit"><s:message code="verManage.verInfo" /></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <div class="tabType1">
          <ul>
            <%-- 버전정보 --%>
            <li><a href="javascript:;" id="verInfoTab" class="on"><s:message code="verManage.verInfo" /></a></li>
            <%-- 적용매장 --%>
            <li><a href="javascript:;" id="storeInfoTab" ><s:message code="verManage.store.registed" /></a></li>
          </ul>
        </div>
        <div id="verInfoArea">

          <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
          <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

          <%-- 버전정보 상세 --%>

          <table id="verInfoViewArea" class="tblType01 mt20">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
                <tr>
                  <%-- 버전일련번호 --%>
                  <th><s:message code="verManage.verSerNo" /></th>
                  <td id="_verSerNo"></td>
                  <%-- 버전적용명 --%>
                  <th><s:message code="verManage.verSerNm" /></th>
                  <td id="_verSerNm"></td>
                </tr>
                <tr>
                  <%-- 파일업로드 --%>
                  <th><s:message code="verManage.file.upload" /></th>
                  <td colspan="3" id="_fileNm"></td>
                </tr>
                <tr>
                  <%-- 파일사이즈 --%>
                  <th><s:message code="verManage.fileSize" /></th>
                  <td id="_fileSize"></td>
                  <%-- 프로그램구분 --%>
                  <th><s:message code="verManage.progFg" /></th>
                  <td id="_progFg"></td>
                </tr>
                <tr>
                  <%-- 포함내역 --%>
                  <th><s:message code="verManage.incldDtls" /></th>
                  <td id="_incldDtls"></td>
                  <%-- 사용여부 --%>
                  <th><s:message code="verManage.useYn" /></th>
                  <td id="_useYn"></td>
                </tr>
                <tr>
                  <%-- 파일설명 --%>
                  <th><s:message code="verManage.fileDesc" /></th>
                  <td colspan="3" id="_fileDesc"></td>
                </tr>
            </tbody>
          </table>
          <div id="viewBtnArea" class="mt10 tc">
            <%-- 삭제 --%>
            <button class="btn_skyblue" id="btnDel"><s:message code="cmm.delete" /></button>
            <%-- 수정 --%>
            <button class="btn_skyblue" id="btnEdit"><s:message code="cmm.edit" /></button>
          </div>


          <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
          <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

          <%-- 버전정보 상세 수정 --%>

          <form id="uploadForm" enctype="multipart/form-data">
          <table id="verInfoEditArea" class="tblType01 mt20" style="display:none;">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
                <tr>
                  <%-- 버전일련번호 --%>
                  <th><s:message code="verManage.verSerNo" /></th>
                  <td>
                    <div class="sb-select fl w70">
                      <div id="verSerNo" class="sb-input w80"></div>
                    </div>
                  </td>
                  <%-- 버전적용명 --%>
                  <th><s:message code="verManage.verSerNm" /></th>
                  <td>
                    <div class="sb-select fl w70">
                      <div id="verSerNm" class="sb-input w80"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 파일업로드 --%>
                  <th><s:message code="verManage.file.upload" /></th>
                  <td colspan="3">
                    <%-- <form action='"${baseUrl}" + "upload.sb' method="post" enctype="multipart/form-data"></form> --%>
                    <!--
                    <div class="sb-select fl w70">
                      <div id="fileNm" class="sb-input w80"></div>
                    </div>
                     -->
                    <input type="file" id="fileNm" name="file" class="sb-input w80">
                    <%-- <a href="javascript:;" id="btnFileUpload" class="btn_grayS"><s:message code="verManage.file.upload" /></a> --%>
                  </td>
                </tr>
                <tr>
                  <%-- 파일사이즈 --%>
                  <th><s:message code="verManage.fileSize" /></th>
                  <td>
                    <div class="sb-select fl w70">
                      <div id="fileSize" class="sb-input w80"></div>
                    </div>
                    <s:message code="verManage.byte" />
                  </td>
                  <%-- 프로그램구분 (selectBox) --%>
                  <th><s:message code="verManage.progFg" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="progFg" class="w130"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 포함내역 (chkbox) --%>
                  <th><s:message code="verManage.incldDtls" /></th>
                  <td class="mt10">
                    <input name="incldDtls" id="pgm" type="checkbox" value="pgm"/><label for="chk"><s:message code='verManage.pgm' /></label>
                    <input name="incldDtls" id="db" type="checkbox" value="db"/><label for="chk"><s:message code='verManage.db' /></label>
                    <input name="incldDtls" id="img" type="checkbox" value="img"/><label for="chk"><s:message code='verManage.img' /></label>
                  </td>
                  <%-- 사용여부 (selectBox) --%>
                  <th><s:message code="verManage.useYn" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="useYn"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 파일설명 --%>
                  <th><s:message code="verManage.fileDesc" /></th>
                  <td colspan="3">
                    <div class="sb-select fl w70">
                      <div id="fileDesc" class="sb-input w80"></div>
                    </div>
                  </td>
                </tr>
            </tbody>
          </table>
          </form>
          <div id="editBtnArea" class="mt10 tc" style="display:none;">
            <%-- 등록 --%>
            <button class="btn_skyblue" id="btnSave"><s:message code="cmm.save" /></button>
            <%-- 수정 --%>
            <%-- <button class="btn_skyblue" id="btnModify" style="display:none;"><s:message code="cmm.edit" /></button> --%>
            <%-- 취소 --%>
            <button class="btn_skyblue" id="btnCancel"><s:message code="cmm.cancel" /></button>
          </div>
        </div>

        <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
        <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

        <%-- 버전 적용 매장 --%>
        <div id="storeInfoArea" class="con sc2" style="height:500px; display:none;"><!--높이는 style로 조정-->
          <div class="tr">
            <%-- 매장추가 --%>
            <a href="javascript:;" id="btnAddStore" class="btn_grayS2"><s:message code="verManage.add.store" /></a>

            <%-- 매장추가 --%>
            <a href="javascript:;" id="btnDelStore" class="btn_grayS2"><s:message code="verManage.delete.store" /></a>

            <%-- 엑셀 다운로드 --%>
            <a href="javascript:;" id="btnExcel2" class="btn_grayS2"><s:message code="cmm.excel.down" /></a>
          </div>
          <!--위즈모 테이블-->
          <div>
            <div id="theGrid2" class="mt10 mb20" style="height:350px;"></div>
          </div>
          <!--//위즈모 테이블-->
        </div>


      </div>
    </div>
  </div>
</div>
<script>


  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

  <%-- 적용매장 --%>

    <%-- dataMap 조회 --%>
    var clsFg     = ${ccu.getCommCodeExcpAll("003")};
    var sysStatFg = ${ccu.getCommCode("009")};
    var verRecvFg = ${ccu.getCommCodeExcpAll("091")};

    var clsFgDataMap     = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    var sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    var verRecvFgDataMap = new wijmo.grid.DataMap(verRecvFg, 'value', 'name');

    <%-- 적용매장 Header --%>
    var hData2 =
      [
        {binding:"chk", header:"<s:message code='cmm.chk' />", width:40},
        {binding:"storeCd", header:"<s:message code='verManage.store.storeCd' />"},
        {binding:"storeNm", header:"<s:message code='verManage.store.storeNm' />"},
        {binding:"posNo", header:"<s:message code='verManage.store.posNo' />"},
        {binding:"posNm", header:"<s:message code='verManage.store.posNm' />"},
        {binding:"verRecvFg", header:"<s:message code='verManage.store.verRecvFg' />"},
        {binding:"verRecvDt", header:"<s:message code='verManage.store.verRecvDt' />"},
        {binding:"posIp", header:"<s:message code='verManage.store.posIp' />"},
        {binding:"clsFg", header:"<s:message code='verManage.store.clsFg' />", dataMap:clsFgDataMap},
        {binding:"sysStatFg", header:"<s:message code='verManage.store.sysStatFg' />",dataMap:sysStatFgDataMap},
        {binding:"regDt", header:"<s:message code='verManage.regDt' />"},
        {binding:"regId", header:"<s:message code='verManage.regId' />"}
      ];

    <%-- 적용매장 그리드 생성 --%>
    var grid2 = wgrid.genGrid("#theGrid2", hData2, "${menuCd}", 2, ${clo.getColumnLayout(2)});
    grid2.isReadOnly = true;

    <%-- 체크박스 초기화 --%>
    grid2.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "chk") {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chk == true || item.chk == "Y" ? 'checked' : '') + '>';
        }
      }
    });

    <%-- 체크박스 핸들러 --%>
    grid2.addEventListener(grid2.hostElement, 'mousedown', function(e) {
      var ht = grid2.hitTest(e);
      if( ht.cellType == wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if(col.binding == "chk") {
          grid2.beginUpdate();
          if(grid2.cells.getCellData(ht.row, ht.col, true)){
            grid2.cells.setCellData(ht.row, ht.col, false);
          } else {
            grid2.cells.setCellData(ht.row, ht.col, true);
          }
          grid2.endUpdate();
        }
      }
    });


    <%-- 적용매장 탭 클릭 --%>
    $("#storeInfoTab").click(function(e){
      <%-- 버전정보 등록시, 버전정보 없을 경우 --%>
      if(selectVerSerNo == "") {
        s_alert.pop("<s:message code='verManage.require.verSerNo'/>");
        return false;
      }

      var param = {};
      param.verSerNo = selectVerSerNo;

      searchVerStore(param);

      $(this).addClass("on");
      $("#verInfoTab").removeClass("on");
      $("#verInfoArea").hide();
      $("#storeInfoArea").show();
    });

    <%-- 매장추가 버튼 클릭 --%>
    $("#btnAddStore").click(function(e){
      showAddStorelLayer();
    });

    <%-- 매장삭제 버튼 클릭 --%>
    $("#btnDelStore").click(function(e){
      for(var i = grid2.itemsSource.itemCount-1; i >= 0; i-- ){
        var item = grid2.itemsSource.items[i];
        if(item.chk){
          grid2.itemsSource.removeAt(i);
        }
      }

      if(grid2.itemsSource.itemsRemoved.length == 0) {
        s_alert("<s:message code='verManage.no.delete.item' />");
        return;
      }
      else {
        var paramArr = new Array();
        for(var i=0; i<grid2.collectionView.itemsRemoved.length; i++){
          grid2.collectionView.itemsRemoved[i].status = "D";
          paramArr.push(grid2.collectionView.itemsRemoved[i]);
        }
        $.postJSONArray("/pos/confg/verManage/applcStore/removeStore.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.delSucc' />");
          grid2.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        });
      }
    });

    <%-- 엑셀 다운로드 버튼 클릭 --%>
    $("#btnExcel2").click(function(e){
      var name = "<s:message code='verManage.store.registed'/>";
      wexcel.down(grid2, name, name + ".xlsx");
    });

  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

    <%-- 버전정보 --%>

    <%-- 버전정보 탭 클릭 --%>
    $("#verInfoTab").click(function(e){
      $(this).addClass("on");
      $("#storeInfoTab").removeClass("on");
      $("#verInfoArea").show();
      $("#storeInfoArea").hide();
    });

    <%-- 버전정보  등록 및 수정 form 생성  --%>
    var verSerNoInput = wcombo.genInput("#verSerNo");
    var verSerNmInput = wcombo.genInput("#verSerNm");
    var fileSizeInput = wcombo.genInput("#fileSize");
    var fileDescInput = wcombo.genInput("#fileDesc");
    var pData         = ${ccu.getCommCodeExcpAll("090")};
    var progFgCombo   = wcombo.genCommonBox("#progFg", pData);
    var useYnData     = ${ccu.getCommCodeExcpAll("904")};
    var useYnCombo    = wcombo.genCommonBox("#useYn", useYn);

    fileSizeInput.isReadOnly  = true;

    <%-- 버전정보 - 수정버튼 클릭 --%>
    $("#btnEdit").click(function(e){
      $("#verInfoViewArea").hide();
      $("#viewBtnArea").hide();

      $("#verInfoEditArea").show();
      $("#editBtnArea").show();
    });

    <%-- 버전정보 - 삭제 버튼 클릭 --%>
    $("#btnDel").click(function(e){

      if(selectVerSerCnt > 0 ){
        s_alert.pop("<s:message code='verManage.store.exist'/>");
        return;
      }

      var param = {};
      param.verSerNo = selectVerSerNo;

      var msg = "<s:message code='cmm.choo.delete'/>";

      s_alert.popConf(msg, function(){
        $.postJSON("/pos/confg/verManage/verInfo/remove.sb", param, function(result) {
          if(result.status === "FAIL") {
            s_alert.pop(result.message);
            return;
          }
          var msg2 = "<s:message code='cmm.deleteSucc'/>";
          s_alert.popOk(msg2, function() {
            closeVerInfoLayer();
            search(1);
          });
        })
        .fail(function(){
          s_alert.pop("Ajax Fail");
        });
      });
    });

    <%-- 취소버튼 클릭 --%>
    $("#btnCancel").click(function(e){
      if(selectVerSerNo == "") {
        closeVerInfoLayer();
      } else {
        $("#verInfoViewArea").show();
        $("#viewBtnArea").show();
        $("#verInfoEditArea").hide();
        $("#editBtnArea").hide();
      }
    });

  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

    <%-- 공통 --%>

    <%-- 상세정보 조회 --%>
    function searchVerInfoDtl(obj) {

      var param = obj;
      $.postJSON("/pos/confg/verManage/verInfo/dtlInfo.sb", param, function(result) {
        if(result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }
        showVerInfoDtlEditLayer(result.data);
      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
      });
    }

    <%-- 적용매장  조회 --%>
    function searchVerStore(obj){
      $.postJSON("/pos/confg/verManage/applcStore/list.sb", obj, function(result) {
        if(result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }

        var list = result.data.list;

        grid2.itemsSource = new wijmo.collections.CollectionView(list);
        grid2.itemsSource.trackChanges = true;

        selectVerSerCnt = list.length;

        /*
        if(list.length === undefined || list.length == 0) {
          s_alert.pop(result.message);
          return;
        }
        */
      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
      });
    }

    <%-- 파일 업로드시, 파일 사이즈 --%>
    $("#fileNm").bind("change", function(){
      fileSizeInput.text = document.getElementById("fileNm").files[0].size;
    });

    <%-- 등록버튼 클릭 --%>
    $("#btnSave").click(function(e){
      // validation

      var sendUrl = "";

      if(selectVerSerNo == "") {
        sendUrl = "/pos/confg/verManage/verInfo/regist.sb";
        //chkSerNo(sendUrl);
      } else {
        sendUrl = "/pos/confg/verManage/verInfo/modify.sb";
      }
      sendFormData(sendUrl);
    });

    <%-- 버전일련번호 중복체크 --%>
    /*
    function chkSerNo(sendUrl){
      if(selectVerSerNo == "") {
        var param = {};
        param.verSerNo = verSerNoInput.text;

        $.postJSON("/pos/confg/vermMnage/verInfo/chkVerSerNo.sb", param, function(result) {
          if(result.data > 0) {
            s_alert.pop("<s:message code='verManage.duplicate.verSerNo' />");
            return;
          }
          sendFormData(sendUrl);
        })
        .fail(function(){
          s_alert.pop("Ajax Fail");
        });
      }
    }
     */
    <%-- 저장 또는 수정 --%>
    function sendFormData(sendUrl) {

      var pgmYn = $('input:checkbox[id="pgm"]').is(":checked") == true ? "Y" : "N";
      var dbYn  = $('input:checkbox[id="db"]').is(":checked") == true ? "Y" : "N";
      var imgYn = $('input:checkbox[id="img"]').is(":checked") == true ? "Y" : "N";

      var formData = new FormData($("#uploadForm")[0]);

      formData.append("verSerNo", verSerNoInput.text);
      formData.append("verSerNm", verSerNmInput.text);
      formData.append("fileSize", fileSizeInput.text);
      formData.append("fileDesc", fileDescInput.text);
      formData.append("progFg", progFgCombo.selectedValue);
      formData.append("pgmYn", pgmYn);
      formData.append("dbYn", dbYn);
      formData.append("imgYn", imgYn);
      formData.append("useYn", useYnCombo.selectedValue);

      $.postJSONFile(sendUrl, formData, function(result) {
        if(result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }

        $("#verInfoViewArea").hide();
        $("#viewBtnArea").hide();
        $("#verInfoEditArea").show();
        $("#editBtnArea").show();

      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
      });

      return false;
    }

    <%-- 레이어 닫기 버튼 클릭--%>
    $(".btn_close").click(function(e){
      closeVerInfoLayer();
    });

    <%--- 레이어 닫기 --%>
    function closeVerInfoLayer(){
      $("#uploadForm")[0].reset();
      $("#verInfoDtl").hide();
      $("#verInfoDtlLayer").hide();
      $("#dim1").hide();
    }

  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

    <%-- 등록 --%>

    function showVerInfoDtlRegistLayer(){

      <%-- 레이어 보이기 --%>
      $("#verInfoViewArea").hide();
      $("#viewBtnArea").hide();

      $("#verInfoEditArea").show();
      $("#editBtnArea").show();

      $("#verInfoDtl").hide();
      $("#verInfoDtlLayer").show();

      $("#dim1").show();
      $("#verInfoTab").click();

      //verSerNoInput.text = "자동입력";
    }

  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

    <%-- 레이어 열기  --%>
    function showVerInfoDtlEditLayer(obj) {

      var incldDtls = "";
      if(obj.pgmYn == "Y") incldDtls += "<s:message code='verManage.pgm' />";
      if(obj.dbYn  == "Y") incldDtls += ((incldDtls == ""? "" : " / ") + "<s:message code='verManage.db' />");
      if(obj.imgYn == "Y") incldDtls += ((incldDtls == ""? "" : " / ") + "<s:message code='verManage.img' />");

      var progFgTxt;
      var useYnTxt;

      $.each(pData, function(i, item){
        if(obj.progFg == item.value) {
          progFgTxt = item.name;
        }
      });

      $.each(useYn, function(i, item){
        if(obj.useYn == item.value) {
          useYnTxt = item.name;
        }
      });

      <%-- 버전정보 조회 --%>
      $("#_verSerNo").text(obj.verSerNo);
      $("#_verSerNm").text(obj.verSerNm);
      $("#_fileNm").text(obj.fileNm);
      $("#_fileSize").text(obj.fileSize);
      $("#_progFg").text(progFgTxt);
      $("#_incldDtls").text(incldDtls);
      $("#_useYn").text(useYnTxt);
      $("#_fileDesc").text(obj.fileDesc);

      <%-- 버전정보 수정 --%>
      verSerNoInput.text  = obj.verSerNo;
      verSerNmInput.text  = obj.verSerNm;
      //fileNmInput.text    = obj.fileNm;
      fileSizeInput.text  = obj.fileSize;
      fileDescInput.text  = obj.fileDesc;

      <%-- 레이어 보이기 --%>
      $("#verInfoViewArea").show();
      $("#viewBtnArea").show();

      $("#verInfoEditArea").hide();
      $("#editBtnArea").hide();

      $("#verInfoDtl").show();
      $("#verInfoDtlLayer").show();
      $("#dim1").show();
      $("#verInfoTab").click();
    }

</script>
