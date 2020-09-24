<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/storeMove/storeMoveDtl/"/>

<wj-popup id="wjStoreMoveDtlLayer" control="wjStoreMoveDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="storeMoveDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeMoveDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeMove.dtl.dtlTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="storeMove.dtl.moveDate"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="dtlMoveDate" class="w150px" ng-model="moveDate"></span>
            </div>
          </td>
          <%-- 배송구분 --%>
          <th><s:message code="storeMove.dlvrFg"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchDtlDlvrFg"
                  ng-model="dlvrFg"
                  items-source="_getComboData('srchDtlDlvrFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 출고매장 --%>
          <th><s:message code="storeMove.dtl.outStoreCd"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 멀티 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
              <jsp:param name="targetId" value="storeMoveDtlOutSelectStore"/>
              <jsp:param name="modiFg" value="N"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <%-- 입고매장 --%>
          <th><s:message code="storeMove.dtl.inStoreCd"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 싱글 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
              <jsp:param name="targetId" value="storeMoveDtlInSelectStore"/>
              <jsp:param name="modiFg" value="N"/>
            </jsp:include>
            <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <th><s:message code="storeMove.dtl.remark"/></th>
          <td colspan="3">
            <input type="text" id="dtlHdRemark" name="dtlHdRemark" ng-model="dtlHdRemark" class="sb-input w100"/>
          </td>
        </tr>
        </tbody>
      </table>

      <table class="tblType01 mt10">
        <colgroup>
          <col style="width: 13%;"/>
          <col style="width: 20%;"/>
          <col style="width: 13%;"/>
          <col style="width: 20%;"/>
          <col style="width: 13%;"/>
          <col style="width: 20%;"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="storeMove.dtl.regDt"/></th>
          <td><p id="regDt" class="s12"></p></td>
          <th><s:message code="storeMove.dtl.outConfmDt"/></th>
          <td><p id="outConfmDt" class="s12"></p></td>
          <th><s:message code="storeMove.dtl.inConfmDt"/></th>
          <td><p id="inConfmDt" class="s12"></p></td>
        </tr>
        <tr>
          <th><s:message code="storeMove.dtl.hqConfmDt"/></th>
          <td><p id="hqConfmDt" class="s12"></p></td>
          <th id="thOutSlipNo"></th>
          <td><p id="outSlipNo" class="s12"></p></td>
          <th id="thInSlipNo"></th>
          <td><p id="inSlipNo" class="s12"></p></td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="storeMove.dtl.txt2"/></li>
        <li class="red"><s:message code="storeMove.dtl.txt3"/></li>
      </ul>

      <div class="tr mt20 fr">
        <%-- 상품추가 --%>
        <button type="button" id="btnDtlAddProd" class="btn_skyblue ml5 fl" ng-click="addProd()" ng-if="btnDtlAddProd">
          <s:message code="storeMove.dtl.addProdBtn"/></button>
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()" ng-if="btnDtlSave">
          <s:message code="cmm.save"/></button>
        <%-- 저장 및 출고,이입확정 --%>
        <button type="button" id="btnDtlConfirm" class="btn_skyblue ml5 fl" ng-click="confirm()" ng-if="btnDtlConfirm"></button>
        <%-- 삭제 --%>
        <button type="button" id="btnDtlDel" class="btn_skyblue ml5 fl" ng-click="delete()" ng-if="btnDtlDel">
          <s:message code="cmm.delete"/></button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.prodNm"/>" binding="prodNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.poUnitFg"/>" binding="poUnitFgNm" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.unitQty"/>" binding="outUnitQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.etcQty"/>" binding="outEtcQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.totQty"/>" binding="outTotQty" width="70" align="right" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.totQty"/>" binding="prevOutTotQty" width="70" align="right" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.splyUprc"/>" binding="splyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.outSplyUprc"/>" binding="outSplyUprc" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.inSplyUprc"/>" binding="inSplyUprc" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.amt"/>" binding="amt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.vat"/>" binding="vat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.tot"/>" binding="tot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.envst0011"/>" binding="outEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.envst0011"/>" binding="inEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"  visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/storeMove/storeMoveDtl.js?ver=20181224.01" charset="utf-8"></script>
