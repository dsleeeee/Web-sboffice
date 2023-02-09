<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="timeProdChannelCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="timeProdChannel.timeProdChannel"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('timeProdChannelCtrl')">
        <s:message code="cmm.search"/>
      </button>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <%-- 확장조회 --%>
        <button class="btn_blue fr mt5 mr5" id="btnSearchAddShow" ng-click="searchAddShowChange()">
          <s:message code="cmm.search.addShow" />
        </button>
      </c:if>
    </div>

    <table class="searchTbl">
          <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
          </colgroup>
          <tbody>
              <tr>
                  <%-- 조회일자 --%>
                  <th><s:message code="cmm.search.date"/></th>
                  <td>
                      <div class="sb-select">
                          <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                          <span class="rg">~</span>
                          <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                      </div>
                  </td>
                  <%-- 분류조회 --%>
                  <th><s:message code="timeProdChannel.prodClass" /></th>
                  <td>
                      <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                              placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                      <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                      <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                  </td>
              </tr>
              <tr>
                  <%-- 일자표시옵션 --%>
                  <th><s:message code="timeProdChannel.dayOption"/></th>
                  <td>
                      <div class="sb-select">
                          <wj-combo-box
                                  id="srchDayOption"
                                  ng-model="dayOption"
                                  items-source="_getComboData('srchDayOption')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  control="srchDayOptionCombo">
                          </wj-combo-box>
                      </div>
                  </td>
                  <%-- 상품표시옵션 --%>
                  <th><s:message code="timeProdChannel.prodOption"/></th>
                  <td>
                      <div class="sb-select">
                          <wj-combo-box
                                  id="srchProdOption"
                                  ng-model="prodOption"
                                  items-source="_getComboData('srchProdOption')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  control="srchProdOptionCombo">
                          </wj-combo-box>
                      </div>
                  </td>
              </tr>
              <tr>
                  <%-- 옵션(시간대/시간대분류) --%>
                  <th><s:message code="timeProdChannel.optionFg"/></th>
                  <td>
                      <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                      <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
                  </td>
                  <%-- 옵션에 따른 조회조건 --%>
                  <th><s:message code="timeProdChannel.time"/></th>
                  <td>
                      <div id="timeOption">
                          <div class="sb-select fl w200px">
                              <div class="sb-slect fl" style="width:65px;">
                                  <wj-combo-box
                                          id="startTime"
                                          ng-model="startTime"
                                          items-source="_getComboData('startTimeCombo')"
                                          display-member-path="name"
                                          selected-value-path="value"
                                          is-editable="false"
                                          control="startTimeCombo"
                                          initialized="_initComboBox(s)">
                                          </wj-combo-box>
                              </div>
                              <div class="fl pd5" style="padding-right: 15px;">
                                  <label> ~ </label>
                              </div>
                              <div class="sb-select fl" style="width:65px;">
                                  <wj-combo-box
                                          id="endTime"
                                          ng-model="endTime"
                                          items-source="_getComboData('endTimeCombo')"
                                          display-member-path="name"
                                          selected-value-path="value"
                                          is-editable="false"
                                          control="endTimeCombo"
                                          initialized="_initComboBox(s)">
                                  </wj-combo-box>
                              </div>
                          </div>
                      </div>
                      <div id="timeSlotOption" style="display: none">
                          <div class="sb-select fl w120px" >
                              <wj-combo-box
                                      id="timeSlotCombo"
                                      ng-model="timeSlot"
                                      control="timeSlotCombo"
                                      items-source="_getComboData('timeSlotCombo')"
                                      display-member-path="name"
                                      selected-value-path="value"
                                      is-editable="false"
                                      initialized="_initComboBox(s)">
                              </wj-combo-box>
                          </div>
                      </div>
                  </td>
              </tr>
              <tr>
                  <%-- 상품코드 --%>
                  <th><s:message code="timeProdChannel.prodCd" /></th>
                  <td>
                      <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/>
                  </td>
                  <%-- 상품명 --%>
                  <th><s:message code="timeProdChannel.prodNm" /></th>
                  <td>
                      <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/>
                  </td>
              </tr>
              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <tr>
                  <%-- 매장브랜드 --%>
                  <th><s:message code="timeProdChannel.storeHqBrand"/></th>
                  <td>
                      <div class="sb-select">
                          <wj-combo-box
                            id="srchStoreHqBrandCd"
                            items-source="_getComboData('srchStoreHqBrandCd')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchStoreHqBrandCdCombo">
                          </wj-combo-box>
                      </div>
                  </td>
                  <%-- 매장코드 --%>
                  <th><s:message code="cmm.store"/></th>
                  <td>
                    <%-- 매장선택 모듈 싱글 선택 사용시 include
                         param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                      displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                      modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                      closeFunc - 팝업 닫기시 호출할 함수
                    --%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                        <jsp:param name="targetId" value="timeProdChannelStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                  </td>
              </tr>
              </c:if>
              <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                  <input type="hidden" id="timeProdChannelStoreCd" value="${sessionInfo.storeCd}"/>
              </c:if>
              <tr>
                  <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                     <%-- 상품브랜드 --%>
                      <th><s:message code="timeProdChannel.prodHqBrand"/></th>
                      <td>
                          <div class="sb-select">
                              <wj-combo-box
                                  id="srchProdHqBrand"
                                  items-source="_getComboData('srchProdHqBrand')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  control="srchProdHqBrandCombo">
                              </wj-combo-box>
                          </div>
                      </td>
                  </c:if>
                  <%-- 상품 --%>
                  <th><s:message code="timeProdChannel.prod"/></th>
                  <td>
                      <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                           targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                           displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                           modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                           closeFunc - 팝업 닫기시 호출할 함수--%>
                      <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdMMoms.jsp" flush="true">
                          <jsp:param name="targetId" value="timeProdChannelSelect"/>
                      </jsp:include>
                      <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
                  </td>
                  <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                      <td></td>
                      <td></td>
                  </c:if>
              </tr>
          </tbody>
      </table>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
              <%-- 팀별 --%>
            <th><s:message code="timeProdChannel.momsTeam"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsTeam"
                  items-source="_getComboData('srchMomsTeam')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsTeamCombo">
                </wj-combo-box>
              </div>
            </td>
              <%-- AC점포별 --%>
            <th><s:message code="timeProdChannel.momsAcShop"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsAcShop"
                  items-source="_getComboData('srchMomsAcShop')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsAcShopCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <tr>
              <%-- 지역구분 --%>
            <th><s:message code="timeProdChannel.momsAreaFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsAreaFg"
                  items-source="_getComboData('srchMomsAreaFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsAreaFgCombo">
                </wj-combo-box>
              </div>
            </td>
              <%-- 상권 --%>
            <th><s:message code="timeProdChannel.momsCommercial"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsCommercial"
                  items-source="_getComboData('srchMomsCommercial')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsCommercialCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <tr>
              <%-- 점포유형 --%>
            <th><s:message code="timeProdChannel.momsShopType"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsShopType"
                  items-source="_getComboData('srchMomsShopType')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsShopTypeCombo">
                </wj-combo-box>
              </div>
            </td>
              <%-- 매장관리타입 --%>
            <th><s:message code="timeProdChannel.momsStoreManageType"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsStoreManageType"
                  items-source="_getComboData('srchMomsStoreManageType')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsStoreManageTypeCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <tr>
            <%-- 지사 --%>
            <th><s:message code="timeProdChannel.branchCd"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchBranchCd"
                  items-source="_getComboData('srchBranchCd')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchBranchCdCombo">
                </wj-combo-box>
              </div>
            </td>
            <td></td>
            <td></td>
          </tr>
          </tbody>
        </table>
      </c:if>

      <div class="mt10 oh sb-select dkbr">
          <%-- 조회조건 엑셀다운로드 --%>
          <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
      </div>

      <div class="w100 mt10">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
              id="wjGridList"
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.lClassNm"/>" binding="lClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.mClassNm"/>" binding="mClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.sClassNm"/>" binding="sClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.selTypeFg"/>" binding="selTypeFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.time"/>" binding="saleHour" data-map="timeSlotDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="stinSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="stinSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="stinSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="stinRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="stinRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="stinRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="dlvrSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="dlvrSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="dlvrSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="dlvrRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="dlvrRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="dlvrRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="packSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="packSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="packSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="packRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="packRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="packRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>

                <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>

                <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>

            </wj-flex-grid>

          </div>
          <%--//위즈모 테이블--%>
      </div>

      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
          <ul id="timeProdChannelCtrlPager" data-size="10">
          </ul>
      </div>
      <%--//페이지 리스트--%>

      <%--엑셀 리스트--%>
      <div class="wj-gridWrap" style="display: none;"  ng-controller="timeProdChannelExcelCtrl">
        <wj-flex-grid
          id="wjGridExcelList"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="excelFlex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.lClassNm"/>" binding="lClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.mClassNm"/>" binding="mClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.sClassNm"/>" binding="sClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.selTypeFg"/>" binding="selTypeFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.time"/>" binding="saleHour" data-map="timeSlotDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="stinSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="stinSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="stinSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="stinRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="stinRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="stinRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="dlvrSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="dlvrSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="dlvrSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="dlvrRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="dlvrRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="dlvrRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="packSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="packSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="packSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="packRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="packRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="packRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </c:forEach>

            <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </c:forEach>

            <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.saleQty3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeProdChannel.realSaleAmt3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </c:forEach>
        </wj-flex-grid>
      </div>
      <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">

    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";

    // 콤보박스 데이터
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

    // 채널
    var dlvrInFgColList = [];

    <%--javascript에서 사용할 주문채널 구분자 json 데이터 생성--%>
    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
    var param = {};
    param.dlvrInFg = "${dlvrInFgCol.dlvrInFg}";
    param.dlvrInFgNm = "${dlvrInFgCol.dlvrInFgNm}";
    dlvrInFgColList.push(param);
    </c:forEach>

    var dlvrInFgCol = '${dlvrInFgCol}';
    var dlvrInFgColNm = '${dlvrInFgColNm}';
    var arrDlvrInFgCol = dlvrInFgCol.split(',');
    var arrDlvrInFgColNm = dlvrInFgColNm.split(',');

    // header값 셋팅을 위한 변수
    var vDlvrOrderFg = ["stin", "dlvr", "pack"];

    // 시간대분류(시간대 콤보박스용)
    var timeSlotColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="timeSlotCol" items="${timeSlotColList}">
    var timeSlotParam   = {};
    timeSlotParam.name  = "${timeSlotCol.name}";
    timeSlotParam.value = "${timeSlotCol.value}";
    timeSlotColList.push(timeSlotParam);
    </c:forEach>

    // 시간대분류(리스트 시간대 data-map용)
    var timeSlotColList2 = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="timeSlotCol2" items="${timeSlotColList}">
    var timeSlotParam2   = {};
    timeSlotParam2.name  = "${timeSlotCol2.name}" + "(" + "${timeSlotCol2.value}" + ")";
    timeSlotParam2.value = "${timeSlotCol2.value}".replace('~', '');
    timeSlotColList2.push(timeSlotParam2);
    </c:forEach>

    var timeSlotCol    = '${timeSlotCol}';
    var arrTimeSlotCol = timeSlotCol.split(',');

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/time/timeProdChannel/timeProdChannel.js?ver=20230126.01" charset="utf-8"></script>