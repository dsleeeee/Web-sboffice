<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 포스 환경설정 포스명칭설정 레이어팝업--%>
<div id="storePosNmDim" class="fullDimmed" style="display:none;"></div>
<div id="storePosNmLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w600">
      <p class="tit"><s:message code="storeManage.setting.posName" /></p>
      <a href="javascript:;" class="btn_close"></a>
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
                <th><s:message code="storeManage.posNm" /></th><%-- 포스명칭 --%>
              </tr>
            </thead>
            <tbody id="posNmContent">
            </tbody>
          </table>
        </div>
      </div>
      <div class="btnSet">
        <%-- 저장 --%>
        <span><a id="btnSavePosNm" href="javascript:;" class="btn_blue"><s:message code="cmm.save" /></a></span>
        <%-- 닫기 --%>
        <span><a id="btnClosePosNm" href="javascript:;" class="btn_gray"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

  <%-- 매장환경조회 팝업 오픈 --%>
  function openSetPosNmLayer() {

    var param = {};
    param.storeCd = selectedStore.storeCd;

    $.postJSON("/store/manage/storeManage/storeManage/getPosList.sb", param, function(result) {

      var posList = result.data.list.posList;
      var innerHtml = "";
      for(var i=0; i<posList.length; i++) {

        innerHtml += "<tr>";
        innerHtml += "<td class='tc'>"+posList[i].rownum+"</td>";
        innerHtml += "<td class='tc'>"+posList[i].posNo+"</td>";
        innerHtml += "<td class='tc'>";
        innerHtml += "<input name='pos'id='pos"+posList[i].posNo+"' value='"+posList[i].posNm+"'>";
        innerHtml += "</td>";
        innerHtml += "</tr>";
      }

      $("#posNmContent").html(innerHtml);
      $("#storePosNmDim").show();
      $("#storePosNmLayer").show();
    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 저장 버튼 클릭 --%>
  $("#btnSavePosNm").click(function(){

    var paramArr = new Array();
    var posLength = $("input[name=pos]").length;

    $("#storePosNmLayer input").each(function(index){
      var id = $(this).attr("id");
      var param = {};
      param.storeCd = selectedStore.storeCd;
      param.posNo = id.substring(3,id.length);
      param.posNm = $("#"+ id).val();

      paramArr.push(param);
    });

    $.postJSONArray("/store/manage/storeManage/storeManage/savePosNm.sb", paramArr, function(result) {

      s_alert.pop("<s:message code='cmm.saveSucc' />");

      $("#storePosNmLayer .btn_close").click();
      showPosConfigLayout("03")

    },
    function(result) {
      s_alert.pop(result.message);
    });

  });

  <%-- 레이어 팝업 닫기 --%>
  $("#storePosNmLayer .btn_close, #storePosNmLayer #btnClosePosNm").click(function(){
    $("#storePosNmDim").hide();
    $("#storePosNmLayer").hide();
  });

</script>
