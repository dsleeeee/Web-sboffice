<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script src="/resource/solbipos/js/cmAgency.js"></script>

<div id="_catent" class="fullDimmed" style="display: none;"></div>
<div id="_calayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <!--layerContent-->
    <div class="title w600px">
      <%-- 업체선택 --%>
      <p class="tit">
        <s:message code="cmm.cmagency.select" />
      </p>
      <a href="#" class="btn_close ca_close"></a>
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col width="30%" />
              <col width="70%" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 업체코드/업체명 --%>
                <th><s:message code="cmm.cmagency.cmagencyCond" /></th>
                <td>
                  <div class="sb-select">
                    <div id="_cacode"></div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="mt10 tr">
            <%-- 조회 버튼 --%>
            <button id="_casearch" class="btn_skyblue">
              <s:message code="cmm.search" />
            </button>
          </div>
        </div>
        <div class="mt40">
          <table class="tblType01 moreDark" style="height:100px;">
            <colgroup>
              <col width="10%" />
              <col width="30%" />
              <col width="60%" />
            </colgroup>
            <thead>
              <tr>
                <th></th>
                <th><s:message code="cmm.cmagency.cmagencyCd" /></th>
                <th><s:message code="cmm.cmagency.cmagencyNm" /></th>
              </tr>
            </thead>
            <tbody id="_cabody" >
            </tbody>
          </table>
        </div>
      </div>
      <div class="btnSet">
        <span> 
          <a id="_caok" href="#" class="btn_blue">
            <s:message code="cmm.select" />
          </a>
        </span>
        <span>
          <a href="#" class="btn_gray  ca_close">
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
    var cacode = wcombo.genInput("#_cacode");

    $(".ca_close").click(function(e) {
      c_ca.close();
    });
    $("#_casearch").click(function(e) {
      c_ca.select(cacode.text);
    });
  });
</script>
