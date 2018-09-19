<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">
  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
  </div>

  <%-- TABLE1 (프렌차이즈 권한 노출내용) --%>
  <table class="searchTbl">
    <colgroup>
      <col class="w10" />
      <col class="w45" />
      <col class="w10" />
      <col class="w45" />
    </colgroup>
    <tbody>
      <%-- 공통조회조건 --%>
      <tr>
        <th><s:message code="cmm.search.date" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"> <input id="startDt" name="startDt" class="w200" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDt" name="endDt" class="w200" /></span>
          </div>
        </td>
      </tr>

      <%-- 프렌차이즈 본사 조회조건 --%>
      <c:if test="${orgnFg == 'HQ'}">
      <tr>
        <div id="storeCd" style="display: none;"></div>
        <%-- 매장 --%>
        <th><s:message code="cmm.store" /></th>
        <td>
          <div class="sb-select fl w70">
            <div id="storeCdText" class="sb-input w80"></div>
          </div>
          <a href="javascript:;" id="store" class="btn_grayS ml5"><s:message code="cmm.store.select" /></a>
        </td>

        <%-- 입출구분 --%>
        <th><s:message code="cmm.inoutStock.gubn" /></th>
        <td>
            <div class="sb-select">
                <div id="sysStatFg"></div>
            </div>
        </td>
      </tr>
      </c:if>

      <%-- 가맹점 조회조건 --%>
      <c:if test="${orgnFg == 'STORE'}">
      <tr>
        <%-- 입금/출금계정 --%>
        <th>
            <div class="sb-select" class='wj-content' style="width:150px;">
                <div id="sysStatFg"></div>
            </div>
        </th>
        <td colspan="3">
            <div class="sb-select">
                <select id='stAccnt' class='wj-content' style="width:150px; font-size: 0.80em;">
                    <option value="">선택</option>
                </select>
            </div>
        </td>
      </tr>
      </c:if>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="searchBtn"><s:message code="cmm.search" /></button>
  </div>


  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>
    <div id="listScaleBox" class="fr">
        <%-- 엑셀다운로드 //TODO --%>
        <%--<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>--%>
    </div>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <div id="theGrid" style="height:393px;"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="page1" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/application/layer/store.jsp">
</c:import>

<script>
$(document).ready(function(){

  var accntFgData = new wijmo.grid.DataMap([
    {id:"1", name:"<s:message code='status.inmoney'/>"},
    {id:"2", name:"<s:message code='status.outmoney'/>"},
    {id:"3", name:"<s:message code='status.readymoney'/>"}], 'id', 'name');

  var rdata =
    [
      {"binding": "storeNm", "header": "<s:message code='status.store.nm' />", width: "*"},                      // 매장명
      {"binding": "saleDate", "header": "<s:message code='status.sale.date' />", width: "*"},                    // 영업일자
      {"binding": "posNo", "header": "<s:message code='status.pos.no' />", width:"*"},                          // 포스번호
      {"binding": "accntFg", "header": "<s:message code='status.accnt.fg' />", dataMap: accntFgData, width: "*"},                      // 입출구분
      {"binding": "accntNm", "header": "<s:message code='status.accnt.nm' />", width: "*"},                      // 계정
      {"binding": "accntAmt", "header": "<s:message code='status.accnt.amt' />", width: "*"},                    // 금액
      {"binding": "remark", "header": "<s:message code='status.remark' />", width: "*"}                          // 비고
   ];

  var grid         = wgrid.genGrid("#theGrid", rdata);
  var startDt      = wcombo.genDateVal("#startDt", "${sessionScope.sessionInfo.startDt}");
  var endDt        = wcombo.genDateVal("#endDt", "${sessionScope.sessionInfo.endDt}");
  var ldata        = ${ccu.getListScale()};
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);
  var cdata        = ${ccu.getCommCode("040")};
  var sysStatFg    = wcombo.genCommonBox("#sysStatFg", cdata);

  <c:if test="${orgnFg != 'STORE'}">
      var storeCd      = wcombo.genInput("#storeCd");

      <c:if test="${orgnFg == 'HQ'}">
          var storeCdText  = wcombo.genInput("#storeCdText");
      </c:if>

      <c:if test="${orgnFg != 'HQ'}">
          storeCd.text = "${sessionScope.sessionInfo.orgnCd}";
          var storeCdText  = "";
      </c:if>

      storeCdText.isDisabled = true;
  </c:if>

  // 본사(HQ) 조회
  function search(index) {
      if(storeCd.text == "") {
        <%-- 조회 매장을 선택해주세요. --%>
        var msg = "<s:message code='dclzManage.select.store'/>";
        s_alert.pop(msg);
        return;
      }
    var param = {};

    var stCd = storeCd.text;

    param.startDt = getDate(startDt);
    param.endDt = getDate(endDt);
    param.storeCd = stCd.replace("ALL,","");
    param.chkDt = $('#chkDt').is(":checked");
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;
    param.arrStoreCd = stCd;


    if(sysStatFg.text == "입금"){
        param.accntFg = "1";
    }else if(sysStatFg.text == "출금"){
        param.accntFg = "2";
    }

    $.postJSON("/adi/mony/status/status/list.sb", param, function(result) {
      var list = result.data.list;

      if(list.length == 0) {
        s_alert.pop(result.message);
      }

      grid.itemsSource = list;
      page.make("#page1", result.data.page.curr, result.data.page.totalPage);
      },
      function(result){
        s_alert.pop(result.data.msg);
      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
    });
  }

  // 가맹점(STORE) 조회
  function searchStore(index) {
    var param = {};

    param.startDt = getDate(startDt);
    param.endDt = getDate(endDt);
    param.accntCd = $("#stAccnt").val();
    param.chkDt = $('#chkDt').is(":checked");
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;


    if(sysStatFg.text == "입금"){
        param.accntFg = "1";
    }else if(sysStatFg.text == "출금"){
        param.accntFg = "2";
    }

    $.postJSON("/adi/mony/status/status/list.sb", param, function(result) {
      var list = result.data.list;

      if(list.length == 0) {
        s_alert.pop(result.message);
      }

      grid.itemsSource = list;
      page.make("#page1", result.data.page.curr, result.data.page.totalPage);
      },
      function(result){
        s_alert.pop(result.data.msg);
      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
    });
  }


    <%-- 매장선택 --%>
    $("#store").click(function(e){
      alert('매장선택')

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
  $("#searchBtn").click(function( e ){
    <c:if test="${orgnFg != 'STORE'}">
        search(1);
    </c:if>

    <c:if test="${orgnFg == 'STORE'}">
        searchStore(1);
    </c:if>
  });

  <c:if test="${orgnFg == 'STORE'}">
  $("#sysStatFg input").change(function( e ){
        var param = {};

        param.storeCd = "${orgnCd}";
        param.chkDt = $('#chkDt').is(":checked");

        if(sysStatFg.text == "전체"){
            $("#stAccnt").empty();
            $("#stAccnt").append("<option value='' selected>선택</option>");
        }else{
            if(sysStatFg.text == "입금"){
                param.accntFg = "1";
            }else if(sysStatFg.text == "출금"){
                param.accntFg = "2";
            }

            // 계정조회
            // TODO 계정 등록 후 테스트 필요
            $.postJSON("/adi/mony/status/status/accnt.sb", param, function(result) {
                  var list = result.data.list;
                  var strHtml = "";

                  if(list.length == 0) {
                    s_alert.pop("<s:message code='status.no.account'/>");
                    return false;
                  }

                  $("#stAccnt").empty();

                  for(var i=0; i<list.length; i++) {
                    if(i==0){
                      $("#stAccnt").append("<option value='"+ list[i].accntCd +"' selected> " + list[i].accntNm +  "</option>");
                    } else {
                      $("#stAccnt").append("<option value='"+ list[i].accntCd +"' > " + list[i].accntNm +  "</option>");
                    }
                  }
              },
              function(result){
                s_alert.pop(result.data.msg);
              })
              .fail(function(){
                s_alert.pop("Ajax Fail");
            });
        }
  });
  </c:if>

  <%-- 페이징 --%>
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });

  <%-- 엑셀 다운로드 버튼 클릭 --%>
  $("#btnExcel").click(function(){
    var name = "${menuNm}";
    wexcel.down(grid, name, name + ".xlsx");
  });
});
</script>
