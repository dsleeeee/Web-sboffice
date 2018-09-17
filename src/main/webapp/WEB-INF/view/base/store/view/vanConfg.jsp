<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="layerName" value="_vanConfig" />

<div id="${layerName}Mask" class="fullDimmed" style="display: none;"></div>
<div id="${layerName}Layer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w700">
      <p class="tit" id="vstoreTitle"></p>
      <a href="javascript:;" class="btn_close ${layerName}Close"></a>
      <div class="con" style="height:300px;">
          <p class="s14 bk mb5"><s:message code="storeView.vanConfig.title" /></p>
        <div id="vcGrid" class="mt10" style="height:270px;"></div>
      </div>
      <div class="btnSet">
        <span>
          <a href="javascript:;" class="btn_gray  ${layerName}Close">
            <s:message code="cmm.close" />
          </a>
        </span>
      </div>
    </div>
  </div>
</div>
<script>
    <%-- 밴사 설정정보 조회 결과 --%>
    var vcData =
      [
        {binding:"storeNm", header:"<s:message code='storeView.vanConfig.storeNm' />"},
        {binding:"cornrNm", header:"<s:message code='storeView.vanConfig.cornrNm' />"},
        {binding:"vanTermnlNo", header:"<s:message code='storeView.vanConfig.vanTermnlNo' />"},
        {binding:"ownerNm", header:"<s:message code='storeView.vanConfig.ownerNm' />"},
        {binding:"bizNo", header:"<s:message code='storeView.vanConfig.bizNo' />"}
      ];

    <%-- 그리드 생성 --%>
    var vcGrid = wgrid.genGrid("#vcGrid", vcData, false);
    
    <%--  밴사 설정정보 조회 --%>
    function getVanConfigDetail(obj) {
      var param = obj;
      wgrid.getGridData("/base/store/view/vanconfg/list.sb",    param, vcGrid, 
            function(result){
                $("#${layerName}Mask, #${layerName}Layer").show();
              var storeTitle = param.storeCd+ " " + param.storeNm;
              $("#vstoreTitle").text(storeTitle);
            },
            function(){
              s_alert.pop("Ajax Fail");
          }
      );
    }

    <%-- 레이어 닫기 버튼 클릭--%>
    $(".${layerName}Close").click(function(e) {
      $("#${layerName}Mask, #${layerName}Layer").hide();
    });
</script>