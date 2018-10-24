<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 포스기능 적용 매장선택 레이어 팝업 --%>

<div id="storeDim" class="fullDimmed" style="display:none;"></div>
<div id="storeLayer" class="layer" style="display: none;">
  <div class="layer_inner">

    <div class="title w800">
      <p class="tit" id="storeLayerTitle"><span id="storeLayerSubTitle"></span><s:message code="func.regStore" /></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <div>

          <%-- 검색조건--%>
          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
              <th><s:message code="func.hqOfficeCd" /></th>
              <td><input type="text" id="srchHqOfficeCd" maxlength="5"/></td>
              <th><s:message code="func.hqOfficeNm" /></th>
              <td><input type="text" id="srchHqOfficeNm" maxlength="20"/></td>
            </tr>
            <tr>
              <th><s:message code="func.storeCd" /></th>
              <td><input type="text" id="srchStoreCd" maxlength="7"/></td>
              <th><s:message code="func.storeNm" /></th>
              <td><input type="text" id="srchStoreNm" maxlength="20"/></td>
            </tr>
            <tr>
              <th><s:message code="func.clsFg" /></th>
              <td>
                <div class="sb-select">
                  <div id="srchClsFg"></div>
                </div>
              </td>
              <th><s:message code="func.sysStatFg" /></th>
              <td>
                <div class="sb-select">
                  <div id="srchSysStatFg"></div>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
          <%-- 조회버튼 --%>
          <div class="mt10 tr">
            <button class="btn_skyblue" id="btnStoreSearch"><s:message code="cmm.search" /></button>
          </div>
        </div>

        <%-- 등록 매장 그리드 --%>
        <div class="oh mt40">
          <div class="w50 fl">
            <div class="wj-TblWrap mr10" style="height:270px;">
              <div class="oh mb10">
                <span class="fl bk lh20 s14"><s:message code="func.regStore" /></span>

                  <c:if test="${orgnFg == 'MASTER'}">
                    <span class="fr"><a href="#" class="btn_grayS2" id="btnStoreDel"><s:message code="cmm.del" /></a></span>
                  </c:if>

              </div>
              <div id="regStoreGrid"></div>

              <%-- 페이지리스트 --%>
              <%--
              <div class="pageNum mt20">
                <ul id="page1" data-size="10">
                </ul>
              </div>
              --%>
            </div>
          </div>

          <%-- 미등록매장 그리드 --%>
          <div class="w50 fr">
            <div class="wj-TblWrap ml10" style="height:270px;">
              <div class="oh mb10">
                <span class="fl bk lh20 s14"><s:message code="func.noRegStore" /></span>
                  <c:if test="${orgnFg == 'MASTER'}">
                    <span class="fr"><a href="#" class="btn_grayS2" id="btnStoreReg"><s:message code="func.regist" /></a></span>
                  </c:if>
              </div>
              <div id="noRegStoreGrid"></div>

              <%-- 페이지리스트 --%>
              <%--
              <div class="pageNum mt20">
                <ul id="page2" data-size="10">
                </ul>
              </div>
              --%>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>

var baseUrl     = "${baseUrl}";
var clsFg       = ${ccu.getCommCode("001")};
var sysStatFg   = ${ccu.getCommCode("005")};

</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/func/store.js?ver=20180829" charset="utf-8"></script>

