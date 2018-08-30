<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="_alertTent" class="fullDimmed" style="display: none;"></div>
<div id="_layerOk" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="noTitle w500">
      <p class="bk"></p>
      <div class="btnSet">
        <span>
          <a href="javascript:;" class="btn_blue alert">
            <s:message code="cmm.confirm" />
          </a>
        </span>
      </div>
    </div>
  </div>
</div>

<div id="_layerConf" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="noTitle w500">
      <p class="bk"></p>
      <div class="btnSet">
        <span>
          <a href="javascript:;" class="btn_blue conf">
            <s:message code="cmm.confirm" />
          </a>
        </span>
        <span>
          <a href="javascript:;" class="btn_gray conf">
            <s:message code="cmm.cancel" />
          </a>
        </span>
      </div>
    </div>
  </div>
</div>

<wj-popup control="_alertTent" fade-in="false" fade-out="false" show-trigger="Click" hide-trigger="Click" style="display: none;width: 500px;">
  <div class="wj-dialog-body">
    <div class="wj-popup-alert">
      <p class="bk">
        {{ s_alert_msg }}
      </p>
    </div>
  </div>
  <div class="wj-dialog-footer wj-popup-alert-footer">
    <button class="btn wj-hide-apply btn_blue"><s:message code="cmm.confirm" /></button>
  </div>
</wj-popup>

<script>
</script>
