<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script src="/resource/solbipos/js/store.js"></script>

<div id="_storetent" class="fullDimmed" style="display: none;"></div>
<div id="_storelayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <!--layerContent-->
    <div class="title w600">
      <%-- 매장선택 --%>
      <p class="tit">
        <s:message code="cmm.store.select" />
      </p>
      <a href="javascript:;" class="btn_close store_close"></a>
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col width="15%" />
              <col width="35%" />
              <col width="15%" />
              <col width="35%" />
            </colgroup>
            <tbody>
              <tr>
                <th><s:message code="cmm.search.word" /></th>
                <td colspan="3">
                  <div class="sb-select">
                    <div id="_storecode"></div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="mt10 tr">
            <button id="_storesearch" class="btn_skyblue">
              <s:message code="cmm.search" />
            </button>
          </div>
        </div>
        <div class="mt40">
          <table class="tblType01 moreDark">
            <colgroup>
              <col width="10%" />
              <col width="15%" />
              <col width="45%" />
              <col width="30%" />
            </colgroup>
            <thead>
              <tr>
                <th><input id="_storeall" type="checkbox" /></th>
                <th><s:message code="cmm.mrhst.cd" /></th>
                <th><s:message code="cmm.mrhst.nm" /></th>
                <th><s:message code="cmm.owner.nm" /></th>
              </tr>
            </thead>
            <tbody id="_storebody">
            </tbody>
          </table>
        </div>
      </div>
      <div class="btnSet">
        <span> 
          <a id="_storeok" href="javascript:;" class="btn_blue">
            <s:message code="cmm.select" />
          </a>
        </span> 
        <span>
          <a href="javascript:;" class="btn_gray  store_close">
            <s:message code="cmm.close" />
          </a>
        </span>
      </div>
    </div>
    <!--//layerContent-->
  </div>
</div>
<script>
  $(document).ready(function() {
    var storecode = wcombo.genInput("#_storecode");
    $("#_storeall").click(function() {
      if ($("#_storeall").prop("checked")) {
        $("input[name=_storelist]:checkbox").prop("checked", true);
      } 
      else {
        $("input[name=_storelist]:checkbox").prop("checked", false);
      }
    });
    $(".store_close").click(function(e) {
      c_store.close();
    });
    $("#_storesearch").click(function(e) {
      c_store.select(storecode.text);
    });
  });
</script>