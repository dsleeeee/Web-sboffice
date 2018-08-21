<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 매장 상세정보 --%>
<div class="w65 fr" id="storeEnvInfoArea" style="display:none;">
  <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
    <h2 class="h2_tit" id="storeEnvInfoTitle"></h2>

    <%-- 탭 --%>
    <ul class="subTab">
      <%-- 매장정보 --%>
      <li><a id="storeInfo" href="javascript:;" ><s:message code="storeManage.storeInfo" /></a></li>
      <%-- 메뉴권한 (권한 관련 메뉴 : [시스템관리 > 권한관리] 로 분리 --%>
      <%-- <li><a id="storeMenuAuth" href="javascript:;"><s:message code="storeManage.storeMenuAuth" /></a></li> --%>
      <%-- 매장환경 --%>
      <li><a id="storeEnv"  href="javascript:;" class="on"><s:message code="storeManage.storeEnv" /></a></li>
    </ul>

    <%-- 매장환경 분류 탭 --%>
    <div class="subTab2 mt20 mb10">
      <ul id="envGroupTab">
        <%-- 매장환경 --%>
        <li><a href="javascript:;" id="storeEnvFg" envstFg="0" class="on"><s:message code="storeManage.storeEnv" /></a></li>
        <%-- 외식환경 --%>
        <li><a href="javascript:;" id="foodEnvFg" envstFg="1"><s:message code="storeManage.foodEnv" /></a></li>
        <%-- 유통환경  //TODO 추후 추가 -%>
        <%-- <li><a href="javascript:;" id="distributionEnvFg" envstFg="2"><s:message code="storeManage.distributionEnv" /></a></li> --%>
        <%-- 포스환경 --%>
        <li><a href="javascript:;" id="posEnvFg" envstFg="3"><s:message code="storeManage.posEnv" /></a></li>
        <%-- 주방프린터 --%>
        <li><a href="javascript:;" id="printEnvFg" envstFg="4"><s:message code="storeManage.kitchenPrint" /></a></li>
        <%-- 주방프린터 상품연결--%>
        <li><a href="javascript:;" id="printProductEnvFg" envstFg="5"><s:message code="storeManage.kitchenPrintProduct" /></a></li>
        <%-- 터치키복사 --%>
        <%-- <li><a href="javascript:;" id="touchkeyEnvFg" envstFg="6"><s:message code="storeManage.copy.touchKey" /></a></li> --%>
      </ul>
    </div>

    <form id="envForm" name="envForm">
      <%-- 매장환경, 외식환경, 유통환경 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storeConfig.jsp">
      </c:import>
      <%-- 포스환경 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/posConfig.jsp">
      </c:import>
      <%-- 주방프린터 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/kitchenPrint.jsp">
      </c:import>
      <%-- 주방프린터 상품연결 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/kitchenPrintProduct.jsp">
      </c:import>
      <%-- 터치키복사 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/copyTouchKey.jsp">
      </c:import>
    </form>
  </div>
</div>

<script>

var envstGrpCd = ${ccu.getCommCodeExcpAll("004")};
var posList;

<%-- 환경설정 팝업 오픈 --%>
function openEnvLayer(){
  $("#noDataArea").hide();
  $("#storeInfoViewArea").hide();
  $("#storeEnvInfoArea").show();

  var envTitle = "[" + selectedStore.storeCd + "] "+ selectedStore.storeNm;

  $("#storeEnvInfoTitle").text(envTitle);
  $("#storeEnvFg").click();

  // 매장환경에서 사용할 포스목록 조회
  var param = {};

  param.hqOfficeCd  = selectedStore.hqOfficeCd;
  param.storeCd     = selectedStore.storeCd;

  <%-- 포스목록 조회 --%>
  $.postJSON("/store/manage/storeManage/storeManage/getPosList.sb", param, function(result) {
    posList = result.data.list.posList;
  },
    function (result) {
      s_alert.pop(result.message);
      return;
    }
  );
}

<%-- 매장환경 서브 탭 클릭시 --%>
$("#envGroupTab li a").click(function(e){
  var envstFg = $(this).attr("envstFg");

  $("#envGroupTab li a").each(function(index, item){
    if($(this).attr("envstFg") == envstFg) {
      $(this).attr("class", "on");
    } else {
      $(this).removeAttr("class");
    }
  });

  if(envstFg == 0 || envstFg == 1 || envstFg == 2) { <%-- 매장환경, 외식환경, 유통환경 --%>
    hidePosConfigLayout();
    hideKitchenPrintLayout();
    hideKitchenPrintProductLayout();
    hideCopyTouchKeyLayout();
    showStoreConfigLayout(envstFg);
  }
  else if(envstFg == 3) { <%-- 포스환경 --%>
    hideStoreConfigLayout();
    hideKitchenPrintLayout();
    hideKitchenPrintProductLayout();
    hideCopyTouchKeyLayout();
    showPosConfigLayout(envstFg);
  }
  else if(envstFg == 4) { <%-- 주방프린터 --%>
    hideStoreConfigLayout();
    hidePosConfigLayout();
    hideKitchenPrintProductLayout();
    hideCopyTouchKeyLayout();
    showkitchenPrintLayout();
  }
  else if(envstFg == 5) { <%-- 주방프린터 상품연결 --%>
    hideStoreConfigLayout();
    hidePosConfigLayout();
    hideKitchenPrintLayout();
    hideCopyTouchKeyLayout();
    showKitchenPrintProductLayout();
  }
  else if(envstFg == 6) { <%-- 터치키복사 --%>
    hideStoreConfigLayout();
    hidePosConfigLayout();
    hideKitchenPrintLayout();
    hideKitchenPrintProductLayout();
    showCopyTouchKeyLayout();
  }
});

<%-- 매장정보 탭 클릭시 --%>
$("#storeEnvInfoArea #storeInfo").click(function(){
  showDetail();
});

</script>
