<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" id="sideMenuProdSoldOutView" ng-controller="sideMenuProdSoldOutCtrl" style="display: none; padding: 10px 20px 40px;">
  <%--searchTbl--%>
  <div class="searchBar">
      <a href="#" class="open fl"><s:message code="soldOut.sideMenuProd" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('sideMenuProdSoldOutCtrl')">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

    <table class="searchTbl">
      <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
      </colgroup>
      <tbody>
        <c:if test="${orgnFg == 'HQ'}">
              <tr>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="sideMenuProdSoldOutStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
              </tr>
        </c:if>
      <tr>
      <c:if test="${brandUseFg == '1'}">
          <%-- 상품브랜드 --%>
          <th><s:message code="salePriceResve.prodHqBrand"/></th>
          <td>
              <div class="sb-select">
                  <wj-combo-box
                          id="srchProdHqBrandCd"
                          items-source="_getComboData('srchProdHqBrandCd')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          control="srchProdHqBrandCdCombo">
                  </wj-combo-box>
              </div>
          </td>
      </c:if>
        <th><s:message code="sideMenuProdSoldOut.prodClass" /></th>
        <td>
            <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                   placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            <div style="display: none;">
                <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                     targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                     displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                     modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                     closeFunc - 팝업 닫기시 호출할 함수--%>
                <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdSMoms.jsp" flush="true">
                    <jsp:param name="targetId" value="prodSoldOutProd"/>
                </jsp:include>
                <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
            </div>
        </td>
      </tr>
      <tr>
        <%-- 선택그룹코드 --%>
        <th>
          <s:message code="sideMenuProdSoldOut.sdselGrpCd" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchSdselGrpCd" ng-model="sdselGrpCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 선택그룹명 --%>
        <th>
          <s:message code="sideMenuProdSoldOut.sdselGrpNm" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchSdselGrpNm" ng-model="sdselGrpNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%-- 선택분류코드 --%>
        <th>
          <s:message code="sideMenuProdSoldOut.sdselClassCd" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchSdselClassCd" ng-model="sdselClassCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 선택분류명 --%>
        <th>
          <s:message code="sideMenuProdSoldOut.sdselClassNm" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchSdselClassNm" ng-model="sdselClassNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th>
          <s:message code="sideMenuProdSoldOut.prodCd" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 상품명 --%>
        <th>
          <s:message code="sideMenuProdSoldOut.prodNm" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
          <%-- 품절여부 --%>
          <th><s:message code="sideMenuProdSoldOut.soldOutYn" /></th>
          <td>
              <div class="sb-select">
                  <wj-combo-box
                          id="srchSideProdSoldOutYn"
                          ng-model="soldOutYn"
                          items-source="_getComboData('sideProdSoldOutYnCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
              </div>
          </td>
          <th></th>
          <td></td>
      </tr>
      </tbody>
    </table>

    <c:if test="${sessionScope.sessionInfo.userId != 'ds021' and sessionScope.sessionInfo.userId != 'ds034' and sessionScope.sessionInfo.userId != 'h0393'}">
      <%-- 일괄적용 --%>
      <table class="searchTbl mt10">
          <colgroup>
              <col class="w15" />
              <col class="w15" />
              <col class="w15" />
              <col class="*" />
          </colgroup>
          <tbody>
          <tr class="brt">
              <%-- 판매상품여부 --%>
              <th>
                  <s:message code="soldOut.soldOutYn" />
              </th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                              id="srchSoldOutYnChg"
                              ng-model="soldOutYnChg"
                              items-source="_getComboData('soldOutYnComboChg')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)">
                      </wj-combo-box>
                  </div>
              </td>
              <%-- 일괄적용 --%>
              <td>
                  <a href="#" class="btn_grayS ml10" ng-click="batchChange()"><s:message code="prodBatchChange.batchChange" /></a>
              </td>
              <%-- 저장 --%>
              <td>
                  <button class="btn_skyblue ml5 fr" id="btnSoldOutYnSave" ng-click="saveSideMenuProd()"><s:message code="cmm.save" /></button>
              </td>
          </tr>
          </tbody>
      </table>
    </c:if>

  <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
  </div>

  <%--//searchTbl--%>
    <div class="wj-TblWrapBr mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 330px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                sticky-headers="true"
                control="flex"
                initialized="initGrid(s,e)"
                item-formatter="_itemFormatter"
                frozen-columns="1">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="soldOut.store"/>" binding="storeCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselGrpCd"/>" binding="sdselGrpCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselGrpNm"/>" binding="sdselGrpNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.fixProdFg"/>" binding="grpFixProdFg" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselTypeFg"/>" binding="sdselTypeFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselClassCd"/>" binding="sdselClassCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselQty"/>" binding="sdselQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.requireYn"/>" binding="requireYn" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.prodClass"/>" binding="prodClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.addProdQty"/>" binding="addProdQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.fixProdFg"/>" binding="fixProdFg" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.soldOutYn"/>" binding="soldOutYn" data-map="soldOutYnDataMap" width="70" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.channelSoldOutYn"/>" binding="channelSoldOutYn" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="soldOut.selfappYn"/>" binding="selfappYn" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
      <%-- id --%>
      <ul id="sideMenuProdSoldOutCtrlPager" data-size="10">
      </ul>
    </div>
    <%-- //페이지 리스트 --%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/soldOut/sideMenuProdSoldOut.js?ver=20240530.01" charset="utf-8"></script>