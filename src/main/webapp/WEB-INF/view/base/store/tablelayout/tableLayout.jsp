<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
#content div {
    font-family:inherit;
    font-weight:inherit;
    font-size:inherit;
    color:inherit;
}
.wj-topleft div.wj-row:first-child .wj-cell {background:#e8e8e8 center center no-repeat; background-size:21px 20px;}
</style>
<script>
var urlParams = (function(url) {
  var result = new Object();
  var idx = url.lastIndexOf('?');

  if (idx > 0) {
    var params = url.substring(idx + 1).split('&');

    for (var i = 0; i < params.length; i++) {
      idx = params[i].indexOf('=');

      if (idx > 0) {
        result[params[i].substring(0, idx)] = params[i].substring(idx + 1);
      }
    }
  }

  return result;
})(window.location.href);

// Default resources are included in grapheditor resources
mxLoadResources = false;

// urlParams is null when used for embedding
window.urlParams = window.urlParams || {};

window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE  || 10485760;

window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';
window.STENCIL_PATH = window.STENCIL_PATH || '/resource/graph/stencils';
//window.CONFIG_PATH = window.CONFIG_PATH || '/resource/graph/config';

window.TABLELAYOUT_OPEN_URL = window.TABLELAYOUT_OPEN_URL || '/base/store/tablelayout/tablelayout/view.sb';
window.TABLELAYOUT_SAVE_URL = window.TABLELAYOUT_SAVE_URL || '/base/store/tablelayout/tablelayout/save.sb';

window.mxBasePath = window.mxBasePath || '/resource/vender/mxgraph/src';
window.mxLanguage = window.mxLanguage || urlParams['lang'];
window.mxLanguages = window.mxLanguages || ['ko'];

</script>
<script type="text/javascript" src="/resource/vender/mxgraph/mxClient.js" charset="utf-8"></script>
<!--script type="text/javascript" src="/resource/vender/mxgraph/mxClient.min.js"></script-->
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/TableLayout.js" charset="utf-8"></script>

    <%--서브컨텐츠--%>
    <div class="subCon2">
      <%--테이블속성, 테이블관리, 판매터치키 page에만 쓰임--%>
      <div class="posWrap oh">
        <%--그룹--%>
        <div class="w15 fl">
          <h2 class="h2_tit2 mr20 oh"><s:message code="tableLayout.group"/>
            <a href="javascript:;" class="ftTit" id="btnLayerConfig"><s:message code="tableLayout.tableLayout"/></a>
          </h2>
          <div class="frGroupWrap mr20" id="divLayers">
            <%--<span><a href="javascript:;" class="on">2층</a></span>--%>
          </div>

        </div>
        <%--//그룹--%>
        <%--left--%>
        <div class="w15 fl">
          <%--구성요소--%>
          <h2 class="h2_tit2"><s:message code="tableLayout.component"/></h2>
          <div class="cfgWrap">
            <ul class="tableImg" id="divCompos">
              <%--<li><a href="javascript:;"><img src="img/img_table2.png" alt="" /></a></li>--%>
            </ul>
          </div>
          <%--//구성요소--%>
        </div>
        <%--//left--%>
        <%--right--%>
        <div class="w70 fr">
          <%--미리보기--%>
          <div class="ml20">
            <h2 class="h2_tit2"><s:message code="tableLayout.preview"/></h2>
            <div class="prev1 patternBg" id="content" tabindex="-1">
              <%--점 패턴 background classname : "patternBg"--%>
              <%--510 * 510 pixel 입니다.--%>
            </div>
          </div>
          <%--//미리보기--%>
          <%--설정--%>
          <div class="shopSetting ml20" id="format">
            <div class="btn_int">
              <button class="btn_skyblue" id="btnInit"><s:message code="cmm.init"/></button>
              <button class="btn_skyblue" id="btnSave"><s:message code="cmm.save"/></button>
            </div>
            <div>
              <div class="oh">
                <span class="s12 fl lh30 bk mr10"><s:message code="tableLayout.bgColor"/></span>
                <div class="sb-select fl txtIn w115 mr20">
                  <div id="bgColor"></div>
                </div>
                <span class="s12 fl lh30 bk mr10"><s:message code="tableLayout.bgImg"/></span>
                <div class="txtIn fl bgfile oh"  style="width:250px;">
                  <input type="file" class="fl" id="btnBgFile" accept="image/x-png, image/gif, image/jpeg"/>
                  <a href="javascript:;" class="btn_grayS" id="btnDelBgImg"><s:message code="cmm.delete"/></a>
                </div>
              </div>
              <div class="mt10 oh" id="tableAlign" style="display:none;">
                <span class="s12 fl lh30 bk mr20"><s:message code="tableLayout.align"/></span>
                <span class="align">
                <a href="javascript:;" class="btn_objleft" id="btnLeft"></a>
                <a href="javascript:;" class="btn_objcenter" id="btnCenter"></a>
                <a href="javascript:;" class="btn_objright" id="btnRight"></a>
                </span>
                <span class="align ml10">
                <a href="javascript:;" class="btn_objtop" id="btnTop"></a>
                <a href="javascript:;" class="btn_objmiddle" id="btnMiddle"></a>
                <a href="javascript:;" class="btn_objbottom" id="btnBottom"></a>
                </span>
              </div>
            </div>
          </div>
          <%--//설정--%>
        </div>
        <%--//right--%>
      </div>

    </div>
    <%--//서브컨텐츠--%>


<%--layer:For Center screen--%>
<div class="fullDimmed floorLayer" id="floorMask"  style="display: none;"></div>
<div class="layer floorLayer" id="floorLayer" style="display: none;">
  <div class="layer_inner">
    <%--layerContent--%>
    <div class="title w400">
      <p class="tit"><s:message code="tableLayout.tableLayout"/></p>
      <a href="javascript:;" class="btn_close _btnClose"></a>
      <div class="con sc" style="height:300px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="tableLayout.tableLayout"/></span>
          <button id="btnAdd" class="btn_skyblue"><s:message code="cmm.add"/></button>
        </div>
        <%--위즈모 테이블--%>
        <div>
          <div id="floorGrid" class="mt10"></div>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <div class="btnSet">
        <span><a href="javascript:;" class="btn_blue _btnClose" id="btnApply"><s:message code="cmm.apply"/></a></span>
        <span><a href="javascript:;" class="btn_gray _btnClose"><s:message code="cmm.close"/></a></span>
      </div>
    </div>
    <%--//layerContent--%>
  </div>
</div>
<%--//layer:For Center screen--%>

<script>
$(document).ready(function() {

$("#btnLayerConfig").click(function(e) {
  //$("div.floorLayer").show();
  //wijmo.grid.FlexGrid.refreshAll();
});

$("._btnClose").click(function(e) {
  $("div.floorLayer").hide();
});

});
</script>

<script>
(function() {
  var tableLayoutInit = TableLayout.prototype.init;
  TableLayout.prototype.init = function() {
    tableLayoutInit.apply(this, arguments);
  };
  
  if (!mxClient.isBrowserSupported()) {
    // Displays an error message if the browser is not supported.
    mxUtils.error('Browser is not supported!', 200, false);
  }
  else {
    // Adds required resources (disables loading of fallback properties, this can only
    // be used if we know that all keys are defined in the language specific file)
    mxResources.loadDefaultBundle = false;
    var bundle = mxResources.getDefaultBundle(RESOURCE_BASE, mxLanguage)
        || mxResources.getSpecialBundle(RESOURCE_BASE, mxLanguage);

    // Fixes possible asynchronous requests
    mxUtils.getAll(
      [ bundle, STYLE_PATH + '/tablelayout.xml' ],
      function(xhr) {
        // Adds bundle text to resources
        mxResources.parse(xhr[0].getText());

        // Configures the default graph theme
        var themes = new Object();
        themes[Graph.prototype.defaultThemeName] = xhr[1].getDocumentElement();

        // Main
        var tablelayout = new TableLayout(themes);
      },
      function() {
        document.body.innerHTML = '<center style="margin-top:10%;">Error loading resource files. Please check browser console.</center>';
      });
  }
})();
</script>
