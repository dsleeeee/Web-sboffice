<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/hqStoreMove/hqMoveDtl/"/>

<wj-popup id="wjHqMoveDtlLayer" control="wjHqMoveDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="hqMoveDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="hqMoveDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqMove.hqMoveDtl"/>
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
          <th><s:message code="hqStoreMove.dtl.moveDate"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="dtlHqMoveMoveDate" class="w150px" ng-model="moveDate"></span>
            </div>
          </td>
          <%-- 배송구분 --%>
          <th><s:message code="hqStoreMove.dtl.dlvrFg"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchHqMoveDtlDlvrFg"
                  ng-model="dlvrFg"
                  items-source="_getComboData('srchHqMoveDtlDlvrFg')"
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
          <%-- 출고창고 --%>
          <th><s:message code="hqMove.outStorage"/></th>
          <td colspan="3">
            <%-- 창고선택 모듈 멀티 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStorageS.jsp" flush="true">
              <jsp:param name="targetId" value="hqMoveDtlOutSelectStorage"/>
              <jsp:param name="modiFg" value="N"/>
            </jsp:include>
            <%--// 창고선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <%-- 입고창고 --%>
          <th><s:message code="hqMove.inStorage"/></th>
          <td colspan="3">
            <%-- 창고선택 모듈 싱글 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStorageS.jsp" flush="true">
              <jsp:param name="targetId" value="hqMoveDtlInSelectStorage"/>
              <jsp:param name="modiFg" value="N"/>
            </jsp:include>
            <%--// 창고선택 모듈 싱글 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <th><s:message code="hqStoreMove.dtl.remark"/></th>
          <td colspan="3">
            <input type="text" id="dtlHqMoveHdRemark" name="dtlHqMoveHdRemark" ng-model="dtlHdRemark" class="sb-input w100"/>
          </td>
        </tr>
        </tbody>
      </table>

      <table class="tblType01 mt10">
        <colgroup>
          <col style="width: 15%;"/>
          <col style="width: 35%;"/>
          <col style="width: 15%;"/>
          <col style="width: 35%;"/>
        </colgroup>
        <tbody>
        <tr>
          <th class="tc"><s:message code="hqStoreMove.dtl.regDt"/></th>
          <td class="tc"><p id="regHqDt" class="s12"></p></td>
          <th class="tc"><s:message code="hqStoreMove.dtl.hqConfmDt"/></th>
          <td class="tc"><p id="hqConfmHqDt" class="s12"></p></td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="hqStoreMove.dtl.txt2"/></li>
        <li class="red"><s:message code="hqStoreMove.dtl.txt3"/></li>
      </ul>

      <div class="tr mt20 fr">
        <div id="hqMoveDtlBtnLayer" ng-if="hqMoveDtlBtnLayer">
          <%-- 상품추가 --%>
          <button type="button" id="btnHqAddProd" class="btn_skyblue ml5 fl" ng-click="addProd()">
            <s:message code="hqStoreMove.dtl.addProdBtn"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnHqSave" class="btn_skyblue ml5 fl" ng-click="save()">
            <s:message code="cmm.save"/></button>
          <%-- 저장 및 본사확정 --%>
          <button type="button" id="btnHqConfirm" class="btn_skyblue ml5 fl" ng-click="confirm()">
            <s:message code="hqStoreMove.dtl.confirmBtn"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnHqDel" class="btn_skyblue ml5 fl" ng-click="delete()">
            <s:message code="cmm.delete"/></button>
        </div>
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
            item-formatter="_itemFormatter"
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.poUnitFg"/>" binding="poUnitFgNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.unitQty"/>" binding="outUnitQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.etcQty"/>" binding="outEtcQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.totQty"/>" binding="outTotQty" width="70" align="right" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.outSplyUprc"/>" binding="outSplyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.currQty"/>" 	  binding="outCurrQty"   width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.outAmt"/>" binding="outAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.outVat"/>" binding="outVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.outTot"/>" binding="outTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.inSplyUprc"/>" binding="inSplyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.currQty"/>" 	  binding="inCurrQty"    width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.envst0011"/>" binding="outEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.envst0011"/>" binding="inEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"  visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/hqMove/hqMoveDtl.js?ver=20181224.02" charset="utf-8"></script>
