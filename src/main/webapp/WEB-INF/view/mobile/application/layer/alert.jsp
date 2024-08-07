<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="_alertTent" class="fullDimmed" style="display: none; z-index:5000;"></div>
<div id="_layerOk" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="noTitle w80">
      <p class="bk"></p>
      <div class="btnSet">
        <span>
          <a href="#" class="btn_blue alert">
            <s:message code="cmm.confirm" />
          </a>
        </span>
      </div>
    </div>
  </div>
</div>

<div id="_layerConf" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="noTitle w80">
      <p class="bk"></p>
      <div class="btnSet">
        <span>
          <a href="#" class="btn_blue conf">
            <s:message code="cmm.confirm" />
          </a>
        </span>
        <span>
          <a href="#" class="btn_gray conf">
            <s:message code="cmm.cancel" />
          </a>
        </span>
      </div>
    </div>
  </div>
</div>

<wj-popup control="_alertPopup" class="wj-dialog-index" fade-in="false" fade-out="false" show-trigger="Click" hide-trigger="Click" style="display: none;width: 80%;">
  <div class="wj-dialog-body">
    <div class="wj-popup-alert">
      <p class="bk" ng-bind-html="s_alert_msg">
      </p>
    </div>
  </div>
  <div class="wj-dialog-footer wj-popup-alert-footer">
    <button class="btn wj-hide-apply btn_blue"><s:message code="cmm.confirm" /></button>
  </div>
</wj-popup>

<wj-popup control="_alertConfirm" class="wj-dialog-index" fade-in="false" fade-out="false" show-trigger="Click" hide-trigger="Click" style="display: none;width: 80%;">
  <div class="wj-dialog-body">
    <div class="wj-popup-alert">
      <p class="bk" ng-bind-html="s_confirm_msg">
      </p>
    </div>
  </div>
  <div class="wj-dialog-footer wj-popup-alert-footer">
    <button class="btn wj-hide-apply btn_blue"><s:message code="cmm.confirm" /></button>
    <button class="btn wj-hide-cancel btn_gray"><s:message code="cmm.cancel" /></button>
  </div>
</wj-popup>

<script>
</script>
