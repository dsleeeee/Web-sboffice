<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/setProdAdj/setProdAdj/setProdAdjRegist/"/>

<wj-popup id="wjSetProdAdjRegistLayer" control="wjSetProdAdjRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="setProdAdjRegistLayer" class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="setProdAdj.reg.registTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <div ng-controller="setProdAdjRegistCtrl">
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <%-- 상품코드 --%>
            <th><s:message code="setProdAdj.reg.prodCd"/></th>
            <td>
              <input type="text" id="srchRegProdCd" name="srchRegProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="setProdAdj.reg.prodNm"/></th>
            <td>
              <input type="text" id="srchRegProdNm" name="srchRegProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
            </td>
          </tr>
          </tbody>
        </table>

        <div class="mt10 oh">
          <%-- 조회 --%>
          <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchSetProdAdjRegistList();">
            <s:message code="cmm.search"/></button>
        </div>

        <ul class="txtSty3 mt10">
          <li class="red"><s:message code="setProdAdj.reg.txt1"/></li>
          <li class="red"><s:message code="setProdAdj.reg.txt2"/></li>
        </ul>

        <%-- 세트상품 --%>
        <div class="mt10">
          <div class="wj-TblWrapBr pd20">
            <div class="updownSet oh mb10">
              <span class="fl bk lh30"><s:message code='setProdAdj.reg.setProdTitle'/></span>
              <div class="fr">
                <p class="s12 lh30 fl mr5"><s:message code='setProdAdj.reg.setDate'/> : </p>
                <div class="sb-select mr5 fl">
                  <span class="txtIn"><input id="setDate" class="w120px"></span>
                </div>
                <%-- 구성등록 --%>
                <button type="button" class="btn_skyblue ml5 fl" id="btnCompstSave" ng-click="saveSetProdAdjRegist('1')">
                  <s:message code="setProdAdj.reg.compstReg"/></button>
                <%-- 해체등록 --%>
                <button type="button" class="btn_skyblue ml5 fl" id="btnDsmntSave" ng-click="saveSetProdAdjRegist('2')">
                  <s:message code="setProdAdj.reg.dsmntReg"/></button>
              </div>
            </div>

            <div class="w100 mt10">
              <%--위즈모 테이블--%>
              <div class="wj-gridWrap" style="height: 150px;">
                <wj-flex-grid
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  items-source="data"
                  control="flex"
                  initialized="initGrid(s,e)"
                  is-read-only="false"
                  item-formatter="_itemFormatter">

                  <!-- define columns -->
                  <wj-flex-grid-column header="<s:message code="setProdAdj.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="setProdAdj.reg.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="setProdAdj.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="setProdAdj.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="setProdAdj.reg.setProdQty"/>" binding="setProdQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="setProdAdj.reg.setProdAmt"/>" binding="setProdAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
              </div>
              <%--//위즈모 테이블--%>
            </div>
          </div>
        </div>
      </div>

      <%-- 세트구성상품  --%>
      <div class="mt20" ng-controller="setProdAdjRegistCompstCtrl">
        <div class="wj-TblWrapBr pd20">
          <div class="updownSet oh mb10">
            <span class="fl bk lh30"><s:message code='setProdAdj.reg.compstTitle'/></span>
          </div>

          <div class="w100 mt10">
            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height:150px;">
              <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="false"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="setProdAdj.reg.prodCd"/>" binding="unitProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="setProdAdj.reg.prodNm"/>" binding="unitProdNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="setProdAdj.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="setProdAdj.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="setProdAdj.reg.unitProdQty"/>" binding="unitProdQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="setProdAdj.reg.totCostUprc"/>" binding="totCostUprc" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

              </wj-flex-grid>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/setProdAdj/setProdAdj/setProdAdjRegist.js?ver=20181224.01" charset="utf-8"></script>
