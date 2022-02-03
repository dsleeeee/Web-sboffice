<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<wj-popup id="wjVendrTrtmntLayer" control="wjVendrTrtmntLayer"  show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;height:650px;">
  <div id="vendrTrtmntLayer" class="wj-dialog wj-dialog-columns">
    <div class="layer_inner">
      <div class="title w800px">
        <%-- 타이틀 --%>
        <p id="popTitle" class="tit"><s:message code='vendr.layer.trtmnt.title' /></p>
        <a href="#" class="btn_close" onclick="closePop();"></a>
        <div class="con">
          <%-- 거래처등록, 취급상품 탭 --%>
          <div class="tabType1">
            <ul>
              <%-- 거래처등록 탭 --%>
              <li><a href="#" id="vendrTab" onclick="changeTab('1')"><s:message code="vendr.regst" /></a></li>
              <%-- 취급상품 탭 --%>
              <li><a href="#" id="trtMntTab" class="on" onclick="changeTab('2')"><s:message code="vendr.trtMnt" /></a></li>
            </ul>
          </div>

          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 상품코드 --%>
                <th><s:message code="vendr.prodCd" /></th>
                <td><input type="text" class="sb-input w100" id="sProdCd" ng-model="sProdCd" /></td>
                <%-- 상품명 --%>
                <th><s:message code="vendr.prodNm" /></th>
                <td><input type="text" class="sb-input w100" id="sProdNm" ng-model="sProdNm" /></td>
              </tr>
              <tr>
                <%-- 분류조회 --%>
                <th><s:message code="vendr.productClass" /></th>
                <%--<td>
                  <div class="sb-select">
                    <div id="sProductClass">
                      <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" onClick="openProdClassPop()" placeholder="<s:message code="cmm.all" />" readonly/>
                    </div>
                  </div>
                </td>--%>
                  <td colspan="3">
                      <input type="text" class="sb-inpu" id="sProdClassNm" onClick="popUpProdClass()" style="float: left; width:250px;"
                             placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                      <input type="hidden" id="sProdClassCd" name="sProdClassCd" ng-model="sProdClassCd" disabled />
                      <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" onClick="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                  </td>
              </tr>
            </tbody>
          </table>
          <%-- 조회버튼 --%>
          <div class="mt10 pdb20 oh bb">
            <button class="btn_blue fr" id="searchTrt" ng-click="_pageView('vendrTrtmntCtrl', 1)">
              <s:message code="cmm.search" />
            </button>
          </div>

          <div class="wj-TblWrap mt20" style="height:500px;">
            <input type="hidden" id="hdVendrCd" />
            <!--left 취급상품-->
            <div class="w50 fl" style="height:400px;" ng-controller="vendrTrtmntCtrl">
              <div class="wj-TblWrapBr mr10 pd10" style="height:310px; border: 1px solid white;">
                <h3 class="lh30" style="font-size:0.75em; border:1px solid #ccc; background:#e8e8e8; padding:5px 15px; color:#222; min-width:300px; position:relative;">
                  <s:message code="vendr.trtmnt.yes" />
                  <span class="fr" style="font-size:1em;" id="btnDelete">
                    <a href="#" class="btn_grayS" ng-click="del()"><s:message code="cmm.delete" /></a>
                  </span>
                </h3>
                <%--<div style="height:240px;" id="theGrid1"></div>--%>
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                  <!-- define columns -->
                  <wj-flex-grid-column header="<s:message code="func.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="vendr.prodCd"/>" binding="prodCd" width="*" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="vendr.prodNm"/>" binding="prodNm" width="*" is-read-only="true" ></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="vendr.splyUprc"/>" binding="splyUprc" width="*" align="right" ></wj-flex-grid-column>
                </wj-flex-grid>
              </div>
            </div>
            <!--//left-->

            <!--right 미취급상품-->
            <div class="w50 fr" style="height:400px;" ng-controller="noVendrTrtmntCtrl">
              <div class="wj-TblWrapBr ml10 pd10" style="height:310px; border: 1px solid white;">
                <h3 class="lh30" style="font-size:0.75em; border:1px solid #ccc; background:#e8e8e8; padding:5px 15px; color:#222; min-width:300px; position:relative;">
                  <s:message code="vendr.trtmnt.no" />
                  <span class="fr" style="font-size:1em;" id="btnRegist" >
                    <a href="#" class="btn_grayS" ng-click="reg()"><s:message code="vendr.regist" /></a>
                  </span>
                </h3>
                <%-- <div style="height:240px;" id="theGrid2"></div>--%>
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                  <!-- define columns -->
                  <wj-flex-grid-column header="<s:message code="func.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="vendr.prodCd"/>" binding="prodCd" width="*" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="vendr.prodNm"/>" binding="prodNm" width="*" is-read-only="true" ></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="vendr.splyUprc"/>" binding="splyUprc" width="*" align="right" ></wj-flex-grid-column>
                </wj-flex-grid>
              </div>
            </div>
            <!--//right-->
          </div>
          <!--//2단-->
        </div>
      </div>
    </div>
  </div>
</wj-popup>

<script>
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/vendr/trtmnt.js?ver=20200423.41" charset="utf-8"></script>