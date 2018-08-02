<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script src="/resource/solbipos/js/hqOffice.js"></script>

<div id="_hqtent" class="fullDimmed" style="display: none;"></div>
<div id="_hqlayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <!--layerContent-->
    <div class="title w600">
      <%-- 매장선택 --%>
      <p class="tit">
        <s:message code="cmm.hq.select" />
      </p>
      <a href="javascript:;" class="btn_close hq_close"></a>
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
                <%-- 본사코드 --%>
                <th><s:message code="cmm.hedofc.cd" /></th>
                <td>
                  <div class="sb-select">
                    <div id="_hqcode"></div>
                  </div>
                </td>
                <%-- 본사명 --%>
                <th><s:message code="cmm.hedofc.nm" /></th>
                <td>
                  <div class="sb-select">
                    <div id="_hqname"></div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="mt10 tr">
            <%-- 조회 버튼 --%>
            <button id="_hqsearch" class="btn_skyblue">
              <s:message code="cmm.search" />
            </button>
          </div>
        </div>
        <div class="mt40">
          <table class="tblType01 moreDark" style="height:100px;">
            <colgroup>
              <col width="10%" />
              <col width="15%" />
              <col width="45%" />
              <col width="30%" />
            </colgroup>
            <thead>
              <tr>
                <th id="_hqall"></th>
                <th><s:message code="cmm.hedofc.cd" /></th>
                <th><s:message code="cmm.hedofc.nm" /></th>
                <th><s:message code="cmm.owner.nm" /></th>
              </tr>
            </thead>
            <tbody id="_hqbody" >
            </tbody>
          </table>
        </div>
      </div>
      <div class="btnSet">
        <span> 
          <a id="_hqok" href="javascript:;" class="btn_blue">
            <s:message code="cmm.select" />
          </a>
        </span>
        <span>
          <a href="javascript:;" class="btn_gray  hq_close">
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
    var hqcode = wcombo.genInput("#_hqcode");
    var hqname = wcombo.genInput("#_hqname");
    $("#_hqall").click(function() {
      if ($("#_hqall").prop("checked")) {
        $("input[name=_hqlist]:checkbox").prop("checked", true);
      } 
      else {
        $("input[name=_hqlist]:checkbox").prop("checked", false);
      }
    });
    $(".hq_close").click(function(e) {
      c_hq.close();
    });
    $("#_hqsearch").click(function(e) {
      c_hq.select(hqcode.text, hqname.text);
    });
  });
</script>