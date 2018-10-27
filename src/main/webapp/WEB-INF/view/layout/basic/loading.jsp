<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="_loadTent" class="fullDimmed" style="display: none;"></div>
<div id="_loading" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="noTitle w500px">
      <p class="bk">
        <s:message code="cmm.loading" />
      </p>
      <p class="mt20">
        <img src="/resource/solbipos/css/img/loading.gif" alt="" />
      </p>
    </div>
  </div>
</div>
<wj-popup control="_loadingPopup" fade-in="false" fade-out="false" show-trigger="Click" hide-trigger="Click" style="width: 500px;">
  <div class="wj-dialog-body">
  </div>
</wj-popup>

