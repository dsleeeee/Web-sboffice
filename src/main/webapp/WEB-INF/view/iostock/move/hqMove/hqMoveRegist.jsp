<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/hqStoreMove/hqMoveRegist/"/>

<wj-popup id="wjHqMoveRegistLayer" control="wjHqMoveRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="hqMoveRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="hqMoveRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqMove.hqMove"/>
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
          <th><s:message code="hqStoreMove.reg.moveDate"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="regHqMoveDate" class="w150px" ng-model="moveDate"></span>
            </div>
          </td>
          <%-- 배송구분 --%>
          <th><s:message code="hqStoreMove.reg.dlvrFg"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchHqMoveRegDlvrFg"
                  ng-model="regDlvrFg"
                  items-source="_getComboData('srchHqMoveRegDlvrFg')"
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
              <jsp:param name="targetId" value="hqMoveRegistOutSelectStorage"/>
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
              <jsp:param name="targetId" value="hqMoveRegistInSelectStorage"/>
            </jsp:include>
            <%--// 창고선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <th><s:message code="hqStoreMove.reg.remark"/></th>
          <td colspan="3">
            <input type="text" id="hdHqRemark" name="hdHqRemark" ng-model="hdRemark" class="sb-input w100"/>
          </td>
        </tr>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="storeMove.add.prodCd"/></th>
          <td>
            <input type="text" id="srchHqStoreRegProdCd" name="srchHqStoreRegProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="storeMove.add.prodNm"/></th>
          <td>
            <input type="text" id="srchHqStoreRegProdNm" name="srchHqStoreRegProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li><s:message code="hqStoreMove.reg.txt1"/></li>
        <li><s:message code="hqStoreMove.reg.txt2"/></li>
        <li><s:message code="hqStoreMove.reg.txt3"/></li>
      </ul>

      <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnHqSearch" ng-click="searchHqMoveRegistList();">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="tr mt20 fr">
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue ml5 fl" ng-click="excelDownloadDay()">
            <s:message code="cmm.excel.down" />
        </button>
        <%-- 저장 --%>
        <button type="button" id="btnRegHqSave" class="btn_skyblue ml5 fl" ng-click="save()">
          <s:message code="cmm.save"/></button>
        <%-- 저장 및 확정 --%>
        <button type="button" id="btnHqConfirm" class="btn_skyblue ml5 fl" ng-click="confirm()">
          <s:message code="hqStoreMove.reg.confirmBtn"/></button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap oh" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
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
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.prodCd"/>" 	  binding="prodCd" 		 width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.prodNm"/>" 	  binding="prodNm" 		 width="150" align="left"   is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.poUnitFg"/>" 	  binding="poUnitFgNm" 	 width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.poUnitQty"/>"   binding="poUnitQty" 	 width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.unitQty"/>" 	  binding="outUnitQty" 	 width="70"  align="right"  max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.etcQty"/>" 	  binding="outEtcQty" 	 width="70"  align="right"  max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.totQty"/>" 	  binding="outTotQty" 	 width="70"  align="right"  visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.outSplyUprc"/>" binding="outSplyUprc"  width="70"  align="right"  max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.currQty"/>" 	  binding="outCurrQty"   width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.outAmt"/>" 	  binding="outAmt" 		 width="70"  align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.outVat"/>" 	  binding="outVat" 		 width="70"  align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.outTot"/>" 	  binding="outTot" 		 width="70"  align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.inSplyUprc"/>"  binding="inSplyUprc" 	 width="70"  align="right"  max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.currQty"/>" 	  binding="inCurrQty"    width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.inAmt"/>" 	  binding="inAmt" 		 width="70"  align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.inVat"/>" 	  binding="inVat" 		 width="70"  align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.inTot"/>" 	  binding="inTot" 		 width="70"  align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.vatFg"/>" 	  binding="vatFg01" 	 width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.envst0011"/>"   binding="outEnvst0011" width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.envst0011"/>"   binding="inEnvst0011"  width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.poUnitFg"/>" 	  binding="poUnitFg" 	 width="70"  align="center" is-read-only="true" data-map="poUnitFgMap" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/hqMove/hqMoveRegist.js?ver=20181225.06" charset="utf-8"></script>
