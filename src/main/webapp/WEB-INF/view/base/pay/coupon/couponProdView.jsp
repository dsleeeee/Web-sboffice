<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="couponProdLayer" control="couponProdLayer" show-trigger="Click" hide-trigger="Click" style="width:1200px;">

  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="coupon.regist.product" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body">

      <table class="tblType01">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <label for="payClasssCd"><input id="payClasssCd"></label>
        <tbody>
        <tr>
          <th>적용대상쿠폰</th>
          <td colspan="3" id="couponProdTitle"></td>
        </tr>
        <tr>
          <th><s:message code="coupon.prodCd"/></th>
          <td><input type="text" id="srchApplyProdCd" ng-model="applyProdCd" /></td>
          <th><s:message code="coupon.prodNm"/></th>
          <td><input type="text" id="srchApplyProdNm" ng-model="applyProdNm" /></td>
        </tr>
        </tbody>
      </table>
      <div class="mt10 tr">
        <%-- 양식다운로드 --%>
        <button class="btn_skyblue" id="btnExcelDownload" ><s:message code="cmm.excel.sampleDown" /></button>
        <%-- 엑셀업로드 --%>
        <button class="btn_skyblue" id="btnExcelUpload"><s:message code="cmm.excel.excelUpload" /></button>
        <%-- 조회 --%>
        <button class="btn_skyblue" id="btnSearch" ng-click="_pageView('regProdCtrl', 1)" ><s:message code="cmm.search" /></button>
      </div>
      <div class="oh mt40">

        <%--- 등록상품 그리드 --%>
        <div class="w50 fl">
          <div class="wj-TblWrap mr10" style="height:405px; overflow-y: hidden;" ng-controller="regProdCtrl">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="coupon.regProd"/></span>
              <%--<c:if test="${coupnEnvstVal == 1}">--%>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
              <%--</c:if>--%>
            </div>
            <div id="regProdGrid" style="height: 370px;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.storeCd"/>" binding="storeCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.prodClassCd"/>" binding="prodClassCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.prodClassNm"/>" binding="prodClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.prodCd"/>" binding="prodCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.costUprc"/>" binding="costUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>

        </div>

        <%--- 미등록상품 그리드 --%>
        <div class="w50 fr">
          <div class="wj-TblWrap ml10" style="height:405px; overflow-y: hidden;" ng-controller="noRegProdCtrl">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="coupon.noRegProd" /></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="coupon.regist" /></a></span>
            </div>
            <div id="noRegProdGrid" style="height: 370px;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.storeCd"/>" binding="storeCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.prodClassCd"/>" binding="prodClassCd" width="*" is-read-only="true"  visible="false" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.prodClassNm"/>" binding="prodClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.prodCd"/>" binding="prodCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.costUprc"/>" binding="costUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>

        <div class="wj-TblWrap mt10" style="display:none;" ng-controller="noRegProdExcelCtrl">
          <div id="theGrid" style="height: 350px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="coupon.prodCd"/>" binding="prodCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="coupon.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>

        </div>
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  var coupnEnvstVal = "${coupnEnvstVal}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/pay/coupon/couponProd.js?ver=20240826.01" charset="utf-8"></script>

<%-- 엑셀업로드 --%>
<c:import url="/WEB-INF/view/base/pay/coupon/couponProdExcelUpload.jsp">
</c:import>