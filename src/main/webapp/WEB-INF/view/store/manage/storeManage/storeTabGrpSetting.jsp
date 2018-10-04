<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 포스 환경설정 테이블 그룹셋팅 레이어팝업--%>
<div id="storeTabGrpDim" class="fullDimmed" style="display:none;"></div>
<div id="storeTabGrpLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w600">
      <p class="tit"><s:message code="storeManage.setting.tableGroup" /></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <div>
          <table class="tblType01 moreDark">
            <colgroup>
              <col class="w15" />
              <col class="w15" />
              <col class="w45" />
            </colgroup>
            <thead>
              <tr>
                <th><s:message code="cmm.no" /></th><%-- No. --%>
                <th><s:message code="storeManage.posNo" /></th><%-- 포스 --%>
                <th><s:message code="storeManage.tableGroup" /></th><%-- 테이블그룹 --%>
              </tr>
            </thead>
            <tbody id="tabGrpContent">
            </tbody>
          </table>
        </div>
      </div>
      <div class="btnSet">
        <%-- 저장 --%>
        <span><a id="btnSaveTabGrp" href="#" class="btn_blue"><s:message code="cmm.save" /></a></span>
        <%-- 닫기 --%>
        <span><a id="btnCloseTabGrp" href="#" class="btn_gray"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

  <%-- 매장환경조회 팝업 오픈 --%>
  function openTabGrpLayer() {

    var param = {};
    param.storeCd = selectedStore.storeCd;

    $.postJSON("/store/manage/storeManage/storeManage/getPosConfigList.sb", param, function(result) {

      var posList = result.data.list.posList;
      var grpList = result.data.list.groupList;

      var innerHtml = "";
      for(var i=0; i<posList.length; i++) {

        innerHtml += "<tr>";
        innerHtml += "<td class='tc'>"+posList[i].rownum+"</td>";
        innerHtml += "<td class='tc'>"+posList[i].posNo+"</td>";
        innerHtml += "<td class='tc'>";
        innerHtml += "<select name='pos'id='pos"+posList[i].posNo+"'>";

        for(var j=0; j<grpList.length; j++){
          innerHtml += "<option value='"+grpList[j].tblGrpCd+"'>"+grpList[j].tblGrpNm+"</option>";
        }

        innerHtml += "</select>";
        innerHtml += "</td>";
        innerHtml += "</tr>";
      }

      $("#tabGrpContent").html(innerHtml);
      $("#storeTabGrpDim").show();
      $("#storeTabGrpLayer").show();
    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 저장 버튼 클릭 --%>
  $("#btnSaveTabGrp").click(function(){

    var paramArr = new Array();
    var posLength = $("select[name=pos]").length;

    $("#storeTabGrpLayer select").each(function(index){
      var id = $(this).attr("id");
      var param = {};
      param.storeCd = selectedStore.storeCd;
      param.posNo       = id.substring(3,id.length);
      param.envstVal  = $("#"+ id).val();
      paramArr.push(param);
    });

    $.postJSONArray("/store/manage/storeManage/storeManage/savePosTabGrp.sb", paramArr, function(result) {

      s_alert.pop("<s:message code='cmm.saveSucc' />");

      $("#storeTabGrpLayer .btn_close").click();
    },
    function(result) {
      s_alert.pop(result.message);
    });

  });

  <%-- 레이어 팝업 닫기 --%>
  $("#storeTabGrpLayer .btn_close, #storeTabGrpLayer #btnCloseTabGrp").click(function(){
    $("#storeTabGrpDim").hide();
    $("#storeTabGrpLayer").hide();
  });

</script>
