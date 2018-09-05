<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="javascript:void(0);" class="open">${menuNm}</a>
  </div>

  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
      <tr>
        <th>
          <s:message code="cmm.search.date" />
        </th>
        <td colspan="3">
          <%-- 조회 일자 --%>
          <div class="sb-select">
            <span class="txtIn"> <input id="startDt" name="startDt" class="w200" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDt" name="endDt" class="w200" /></span>
          </div>
        </td>
      </tr>
      <tr>

        <div id="storeCd" style="display: none;"></div>
        <%-- 매장 --%>
        <c:if test="${orgnFg == 'HQ'}">
          <th><s:message code="cmm.store" /></th>
          <td>
            <div class="sb-select fl w70">
              <div id="storeCdText" class="sb-input w80"></div>
            </div>
            <a href="javascript:;" id="store" class="btn_grayS ml5"><s:message code="cmm.store.select" /></a>
          </td>
        </c:if>

        <%-- 입력구분 --%>
        <th><s:message code="cmm.input.type" /></th>
        <c:if test="${orgnFg == 'HQ'}">
          <td>
        </c:if>
        <c:if test="${orgnFg != 'HQ'}">
          <td colspan="3">
        </c:if>
          <div class="sb-select">
            <div id="inFg"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh">
    <button id="searchBtn" class="btn_blue fr" >
      <s:message code="cmm.search" />
    </button>
    <button id="regBtn" class="btn_blue fl" >
    <s:message code="cmm.new.add" />
    </button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>
    <%-- 엑셀 다운로드 --%>
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <div id="theGrid"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

<%-- 신규 등록 레이어 --%>
<div id="dclzRegTent" class="fullDimmed" style="display: none;"></div>
<div id="dclzRegLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w600">
      <%-- 근태등록 --%>
      <p class="tit"><s:message code="dclzManage.reg.nm" /></p>
      <a href="javascript:;" class="btn_close dclzRegClose"></a>
      <input id="dclzStoreCd" style="display: none;" />
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col width="15%" />
              <col width="70%" />
            </colgroup>
            <tbody>
              <%-- 영업일자 --%>
              <tr>
                <th><s:message code="dclzManage.sale.date" /></th>
                <td>
                  <div class="sb-select">
                    <input id="saleDate" class="w200" />
                  </div>
                </td>
              </tr>
              <%-- 사원 --%>
              <tr>
                <th><s:message code="cmm.emp" /></th>
                <td>
                  <div class="sb-select">
                    <div id="empNo"></div>
                  </div>
                </td>
              </tr>
              <%-- 출근일시 --%>
              <tr>
                <th><s:message code="dclzManage.empin" /></th>
                <td>
                  <div class="sb-select">
                    <input id="empInDtDate" class="w200" />
                    <input id="empInDtTime" class="w200" />
                  </div>
                </td>
              </tr>
              <%-- 퇴근일시 --%>
              <tr>
                <th><s:message code="dclzManage.empout" /></th>
                <td>
                  <div class="sb-select">
                    <input id="empOutDtDate" class="w200" />
                    <input id="empOutDtTime" class="w200" />
                  </div>
                </td>
              </tr>
              <%-- 비고 --%>
              <tr>
                <th><s:message code="cmm.remark" /></th>
                <td>
                  <div class="sb-select">
                    <div id="remark"></div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="btnSet">
        <%-- 수정 --%>
        <span id="dclzLayerEdit" style="display: none;">
          <a href="javascript:;" class="btn_blue"><s:message code="cmm.edit" /></a>
        </span>
        <%-- 삭제 --%>
        <span id="dclzLayerDel" style="display: none;">
          <a href="javascript:;" class="btn_blue"><s:message code="cmm.del" /></a>
        </span>
        <%-- 등록 --%>
        <span id="dclzLayerReg" style="display: none;">
          <a id="dclzReg" href="javascript:;" class="btn_blue"><s:message code="cmm.add" /></a>
        </span>
      </div>
    </div>
  </div>
</div>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/application/layer/store.jsp">
</c:import>

<script>

  $(document).ready(function() {

    <%-- 근태관리 부분 --%>
    var rdata =
      [
        {binding:"storeNm", header:"<s:message code='loginStatus.storeNm' />",width:"*"},
        {binding:"saleDate", header:"<s:message code='dclzManage.sale.date' />",width:"*"},
        {binding:"empNo", header:"<s:message code='dclzManage.empNo' />",width:"*"},
        {binding:"empNm", header:"<s:message code='dclzManage.empNm' />",width:"*"},
        {binding:"empInDt", header:"<s:message code='dclzManage.empInDt' />",width:"*"},
        {binding:"empOutDt", header:"<s:message code='dclzManage.empOutDt' />",width:"*"},
        {binding:"workTime", header:"<s:message code='dclzManage.workTime' />",width:"*"},
        {binding:"inFgNm", header:"<s:message code='dclzManage.inFg' />",width:"*"},
        {binding:"remark", header:"<s:message code='dclzManage.remark' />",width:"*"}
      ];

    var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    var startDt      = wcombo.genDateVal("#startDt", "${sessionScope.sessionInfo.startDt}");
    var endDt        = wcombo.genDateVal("#endDt", "${sessionScope.sessionInfo.endDt}");
    var ldata        = ${ccu.getListScale()};
    var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);
    var cdata        = ${ccu.getCommCode("087")};
    var inFg         = wcombo.genCommonBox("#inFg", cdata);
    var storeCd      = wcombo.genInput("#storeCd");

    <c:if test="${orgnFg == 'HQ'}">
        var storeCdText  = wcombo.genInput("#storeCdText");
    </c:if>

    <c:if test="${orgnFg != 'HQ'}">
        storeCd.text = "${sessionScope.sessionInfo.orgnCd}";
        var storeCdText  = "";
    </c:if>

    storeCdText.isDisabled = true;

    <%-- 그리드 링크 --%>
    grid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        if( col.binding == "empNm" ) {
          var item = s.rows[e.row].dataItem;
          item.row = e.row;
          item.cl = "dclz_row";
          var html = wijmo.format("<a href=\"javascript:;\" class=\"{cl}\" data-value=\"{row}\">{empNm}</a>", item);
          e.cell.innerHTML = html;
        }
      }
    });

    <%-- 그리드 선택 이벤트 --%>
    $(document).on("click",".dclz_row",function() {
      var row = $(this).data("value");
      var rowData = grid.rows[row].dataItem;
      showDclzLayer("view", rowData);
    });

    <%-- 매장선택 --%>
    $("#store").click(function(e){
      c_store.init(function(arr){
        storeCdText.text = "";
        storeCd.text = "";

        if(arr[0].cd === "") {
          storeCdText.text = "전체";
          arr.splice(0, 1);
        }

        if(arr.length > 1) {
          var a = arr.length -1;
          storeCdText.text = arr[0].nm + "외 " + a.toString() + " 선택";
        }
        else if(arr.length == 1){
          storeCdText.text = arr[0].nm;
        }

        for(var i=0; i<arr.length; i++) {
          if(i == arr.length - 1) {
            storeCd.text += arr[i].cd.toString();
          }
          else {
            storeCd.text += arr[i].cd.toString() + ",";
          }
        }
      });
    });

    <%-- 리스트 조회 --%>
    $("#searchBtn").click(function(e){
      search(1);
    });

    <%-- 페이징 --%>
    $(document).on("click", ".page", function() {
      search($(this).data("value"));
    });

    <%-- 엑셀 다운로드 --%>
    $("#excelBtn").click(function( e ){
      var name = "${menuNm}";
      wexcel.down(grid, name, name + ".xlsx");
    });

    <%-- 근태관리 리스트 조회 --%>
    function search(index) {
      if(storeCd.text == "") {
        <%-- 조회 매장을 선택해주세요. --%>
        var msg = "<s:message code='dclzManage.select.store'/>";
        s_alert.pop(msg);
        return;
      }
      var param = {};
      param.startDt = getDate(startDt);
      param.endDt = getDate(endDt);
      param.storeCd = storeCd.text;
      param.inFg = inFg.selectedValue;
      param.listScale = listScaleBox.selectedValue;
      param.curr = index;

      $.postJSON("/adi/dclz/dclzmanage/dclzmanage/list.sb", param, function(result) {
        var list = result.data.list;
        if(list.length === undefined || list.length == 0) {
          s_alert.pop(result.message);
          return;
        }
        grid.itemsSource = list;
        page.make("#page", result.data.page.curr, result.data.page.totalPage);
        },
        function(result) {
          s_alert.pop(result.message);
      });
    }

<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

    <%-- 신규등록 레이어 팝업 --%>
    var saleDate     = wcombo.genDate("#saleDate");
    var empInDtDate  = wcombo.genDate("#empInDtDate");
    var empOutDtDate = wcombo.genDate("#empOutDtDate");
    var remark       = wcombo.genInput("#remark");
    var empInDtTime  = wcombo.genTime("#empInDtTime", 15);
    var empOutDtTime = wcombo.genTime("#empOutDtTime", 15);
    var empNo        = wcombo.genCommonBoxSimple("#empNo");

    <%-- 근태 등록 --%>
    $("#dclzReg").click(function(e) {
      var param = {};

      param.storeCd = storeCd.text;
      param.empNo = empNo.selectedValue;
      param.saleDate = getDate(saleDate);
      param.empInDt = getDate(empInDtDate) + getTime(empInDtTime);
      param.empOutDt = getDate(empOutDtDate) + getTime(empOutDtTime);
      param.remark = remark.text;

      $.postJSON("/adi/dclz/dclzmanage/dclzmanage/regist.sb", param, function(result) {
        closeDclzLayer();
        },
        function(result){
          s_alert.pop(result.message);
      });
    });

    <%-- 신규등록 버튼 --%>
    $("#regBtn").click(function(e){
      var arr = storeCd.text.split(",");
      if(arr.length > 1 || arr.length == 0 || arr[0] === "") {
        <%-- 하나의 매장만 선택해 주세요. --%>
        var msg = "<s:message code='dclzManage.store.one'/>";
        s_alert.pop(msg);
        return;
      }

      var param = {};
      param.storeCd = arr[0];

      $.postJSON("/adi/dclz/dclzmanage/dclzmanage/employee.sb", param, function(result) {
        var list = result.data;
        var comboData = [];
        for(var i=0; i<list.length; i++) {
          var data = {};
          data.name = list[i].empNm;
          data.value = list[i].empNo;
          comboData.push(data);
        }
        empNo.itemsSource = comboData;
        showDclzLayer("reg", null);
      },
      function(result){
        s_alert.pop(result.message);
    });

    $(".dclzRegClose").click(function(e){
      closeDclzLayer();
    });

    function showDclzLayer(type, data) {
      $("#dclzLayerReg").hide();
      $("#dclzLayerDel").hide();
      $("#dclzLayerEdit").hide();

      if(type === "reg") {
        remark.text = "";
        $("#dclzLayerReg").show();
      }
      else if(type === "view") {
        var empIn = new Date(data.empInDt);
        var empOut = new Date(data.empOutDt);
        saleDate.value = new Date(data.saleDate);
        empInDtDate.value = empIn;
        empInDtTime.value = empIn;
        empOutDtDate.value = empOut;
        empOutDtTime.value = empOut;
        remark.text = data.remark;
        $("#dclzStoreCd").val(data.storeCd);

        var comboData = [];
        var d = {};
        d.name = data.empNm;
        d.value = data.empNo;
        comboData.push(d);
        empNo.itemsSource = comboData;

        $("#dclzLayerDel").show();
        $("#dclzLayerEdit").show();
      }
      else {
        return;
      }

      $("#dclzRegTent").show();
      $("#dclzRegLayer").show();
    }

    function closeDclzLayer() {
      $("#dclzRegLayer").hide();
      $("#dclzRegTent").hide();
    }

    <%-- 근태 삭제 --%>
    $("#dclzLayerDel").click(function( e ){
      var param = {};
      param.storeCd = $("#dclzStoreCd").val();
      param.empNo = empNo.selectedValue;
      param.empInDate = getDate(empInDtDate);

      $.postJSON("/adi/dclz/dclzmanage/dclzmanage/remove.sb", param, function(result) {
        var msg = "<s:message code='cmm.del'/>";
        s_alert.popOk(msg, function() {
          closeDclzLayer();
        });
        },
        function(result){
          s_alert.pop(result.message);
      });
    });

    <%-- 근태 수정 --%>
    $("#dclzLayerEdit").click(function( e ){
      var param = {};
      param.storeCd = $("#dclzStoreCd").val();
      param.empNo = empNo.selectedValue;
      param.saleDate = getDate(saleDate);
      param.empInDt = getDate(empInDtDate) + getTime(empInDtTime);
      param.empOutDt = getDate(empOutDtDate) + getTime(empOutDtTime);
      param.remark = remark.text;

      var msg = "<s:message code='cmm.choo.modify'/>";

      s_alert.popConf(msg, function(){
        $.postJSON("/adi/dclz/dclzmanage/dclzmanage/modify.sb", param, function(result) {
          var msg2 = "<s:message code='cmm.modify'/>";
            s_alert.popOk(msg2, function() {
            closeDclzLayer();
          },
          function(result){
            s_alert.pop(result.message);
        });
      });
    });

  });
</script>
















