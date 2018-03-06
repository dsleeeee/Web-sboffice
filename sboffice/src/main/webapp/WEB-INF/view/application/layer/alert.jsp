<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="fullDimmedId" class="fullDimmed" style="display: none;"></div>
<div id="layerOk" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="noTitle w500">
      <p class="bk"></p>
      <div class="btnSet">
        <span><a href="#" class="btn_blue">
            <s:message code="label.cmm.confirm" />
          </a></span></span>
      </div>
    </div>
  </div>
</div>

<div id="layerConf" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="noTitle w500">
      <p class="bk"></p>
      <div class="btnSet">
        <span><a href="#" class="btn_blue">
            <s:message code="label.cmm.confirm" />
          </a></span></span> <span><a href="#" class="btn_gray">
            <s:message code="label.cmm.cancel" />
          </a></span>
      </div>
    </div>
  </div>
</div>

<script>
</script>