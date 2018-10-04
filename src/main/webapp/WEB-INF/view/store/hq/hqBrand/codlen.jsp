<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%--
* id 규칙
 1. v로 시작하는 id는 단순 조회용 데이터
 2. r로 시작하는 id는 수정, 등록용 데이터
--%>

<%-- 본사 상세정보 레이어 --%>
<div id="codeDim" class="fullDimmed" style="display:none;"></div>
<div id="codeLayer" class="layer" style="display:none;">
    <div class="layer_inner">
    
        <%-- 상품분류 없을 경우 --%>
        <div class="title w600">
            <p id="codeTitle" class="tit"></p>
            <a href="#" class="btn_close"></a>
            <div class="con">
                <div class="tabType1">
                    <ul>
                        <li><a href="#" id="codeSettingTab" class="on"><s:message code="hqBrand.codeSetting" /></a></li>
                        <li><a href="#" id="envSettingTab" ><s:message code="hqBrand.envSetting" /></a></li>
                    </ul>
                </div>
                <div class="bgGray s14 mt20 pd30 lh30 tc">
                    <p class="ic_notice mb20"><span>!</span></p><s:message code="hqBrand.require.prodClass.setting" /><br />
                    <span class="s14 bk"><s:message code="hqBrand.envSetting.info" /></span><s:message code="hqBrand.do.setting.prodClass" />
                </div>
            </div>
            <div class="btnSet">
                <span><a href="#" class="btn_blue" id="settingProdClass"><s:message code="hqBrand.setting.prodClass" /></a></span>
            </div>
        </div>
        <%-- 상품분류 있을 경우 --%>
    </div>
</div>

<script>

function openCodeLayer(){
  $("#codeDim").show();
  $("#codeLayer").show();
  
  var codeTitle = "[" + selectedBrand.hqBrandCd + "] "+ selectedBrand.hqBrandNm;
  
  $("#codeTitle").text(codeTitle);
  console.log(selectedBrand)
}

$("#codeLayer #envSettingTab").click(function(){
  $("#codeDim").hide();
  $("#codeLayer").hide();
  
  openEnvLayer();
});

$("#codeLayer .btn_close").click(function(){
  $("#codeDim").hide();
  $("#codeLayer").hide();
});

</script>

