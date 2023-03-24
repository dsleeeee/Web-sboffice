<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="promotionReportCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('promotionReportCtrl')">
          <s:message code="cmm.search"/>
        </button>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 확장조회 --%>
          <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
            <s:message code="cmm.search.addShow" />
          </button>
        </c:if>
      </div>
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
        <th><s:message code="promotionReport.gubun"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchGubunCombo"
                    ng-model="gubunCombo"
                    items-source="_getComboData('gubunCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="changeGubun(s)">
            </wj-combo-box>
          </div>
        </td>
        <td colspan="2">
          <div id="depositYmd">
            <div class="sb-select">
              <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
            </div>
          </div>
          <div id="promotion" style="display: none">
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectPromotionM.jsp" flush="true">
              <jsp:param name="targetId" value="promotionReportPromotion"/>
            </jsp:include>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
          <%-- 매장브랜드 --%>
          <th><s:message code="dayProd.storeHqBrand"/></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchStoreHqBrandCdCombo"
                      ng-model="storeHqBrandCd"
                      items-source="_getComboData('storeHqBrandCdCombo')"
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
              <jsp:param name="targetId" value="promotionReportStore"/>
            </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="promotionReportStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      <tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <th><s:message code="promotionReport.bizNo"/></th>
          <td><input type="text" id="srchBizNo" class="sb-input w100" ng-model="bizNo"/></td>
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
          <th><s:message code="dayProd.momsTeam"/></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchMomsTeamCombo"
                      ng-model="momsTeam"
                      items-source="_getComboData('momsTeamCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      control="srchMomsTeamCombo">
              </wj-combo-box>
            </div>
          </td>
            <%-- AC점포별 --%>
          <th><s:message code="dayProd.momsAcShop"/></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchMomsAcShopCombo"
                      ng-model="momsAcShop"
                      items-source="_getComboData('momsAcShopCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      control="srchMomsAcShopCombo">
              </wj-combo-box>
            </div>
          </td>
        </tr>
        <tr>
            <%-- 지역구분 --%>
          <th><s:message code="dayProd.momsAreaFg"/></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchMomsAreaFgCombo"
                      ng-model="momsAreaFg"
                      items-source="_getComboData('momsAreaFgCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      control="srchMomsAreaFgCombo">
              </wj-combo-box>
            </div>
          </td>
            <%-- 상권 --%>
          <th><s:message code="dayProd.momsCommercial"/></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchMomsCommercialCombo"
                      ng-model="momsCommercial"
                      items-source="_getComboData('momsCommercialCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      control="srchMomsCommercialCombo">
              </wj-combo-box>
            </div>
          </td>
        </tr>
        <tr>
            <%-- 점포유형 --%>
          <th><s:message code="dayProd.momsShopType"/></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchMomsShopTypeCombo"
                      ng-model="momsShopType"
                      items-source="_getComboData('momsShopTypeCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      control="srchMomsShopTypeCombo">
              </wj-combo-box>
            </div>
          </td>
            <%-- 매장관리타입 --%>
          <th><s:message code="dayProd.momsStoreManageType"/></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchMomsStoreManageTypeCombo"
                      ng-model="momsStoreManageType"
                      items-source="_getComboData('momsStoreManageTypeCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      control="srchMomsStoreManageTypeCombo">
              </wj-combo-box>
            </div>
          </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <tr>
              <%-- 지사 --%>
            <th><s:message code="dayProd.branchCd"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchBranchCdComboo"
                        ng-model="branchCd"
                        items-source="_getComboData('branchCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        control="srchBranchCdComboo">
                </wj-combo-box>
              </div>
            </td>
            <td></td>
            <td></td>
          </tr>
        </c:if>
        </tbody>
      </table>
    </c:if>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="promotionReport.promotionCd"/>" binding="promotionCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.promotionNm"/>" binding="promotionNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.startYmd"/>" binding="startYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.endYmd"/>" binding="endYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.bizNo"/>" binding="bizNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <%--<wj-flex-grid-column header="<s:message code="promotionReport.hdRealSaleCnt"/>" binding="hdRealSaleCnt" width="100" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.dtRealSaleCnt"/>" binding="dtRealSaleCnt" width="100" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.realSaleQty"/>" binding="realSaleQty" width="100" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>--%>
          <wj-flex-grid-column header="<s:message code="promotionReport.hdRealSaleCntPromoCd"/>" binding="hdRealSaleCntPromoCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.dtRealSaleCntPromoCd"/>" binding="dtRealSaleCntPromoCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.realSaleQtyPromoCd"/>" binding="realSaleQtyPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.chargeDs"/>" binding="chargeDs" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="hqChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="msChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="partnerChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="hqChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="msChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="partnerChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <%--<wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="hqChargeUprcM" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="msChargeUprcM" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="partnerChargeUprcM" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="totHqChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="totMsChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="totPartnerChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="hqChargeUprc" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="msChargeUprc" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="partnerChargeUprc" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="totHqChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="totMsChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="totPartnerChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>--%>
          <wj-flex-grid-column header="<s:message code="promotionReport.depositYmd"/>" binding="depositYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="promotionReport.remark"/>" binding="remark" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          </c:if>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="promotionReportCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--위즈모 테이블--%>
  <div class="wj-gridWrap" style="display: none;" ng-controller="promotionReportExcelCtrl">
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
      <wj-flex-grid-column header="<s:message code="promotionReport.promotionCd"/>" binding="promotionCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.promotionNm"/>" binding="promotionNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.startYmd"/>" binding="startYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.endYmd"/>" binding="endYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.bizNo"/>" binding="bizNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <%--<wj-flex-grid-column header="<s:message code="promotionReport.hdRealSaleCnt"/>" binding="hdRealSaleCnt" width="100" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.dtRealSaleCnt"/>" binding="dtRealSaleCnt" width="100" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.realSaleQty"/>" binding="realSaleQty" width="100" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>--%>
      <wj-flex-grid-column header="<s:message code="promotionReport.hdRealSaleCntPromoCd"/>" binding="hdRealSaleCntPromoCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.dtRealSaleCntPromoCd"/>" binding="dtRealSaleCntPromoCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.realSaleQtyPromoCd"/>" binding="realSaleQtyPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.chargeDs"/>" binding="chargeDs" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="hqChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="msChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="partnerChargeUprcMPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="hqChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="msChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="partnerChargeUprcPromoCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <%--<wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="hqChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="msChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="partnerChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="totHqChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="totMsChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="totPartnerChargeUprcM" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="hqChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="msChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="partnerChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.hq"/>" binding="totHqChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.ms"/>" binding="totMsChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="promotionReport.partner"/>" binding="totPartnerChargeUprc" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>--%>
      <wj-flex-grid-column header="<s:message code="promotionReport.depositYmd"/>" binding="depositYmd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <wj-flex-grid-column header="<s:message code="promotionReport.remark"/>" binding="remark" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      </c:if>
    </wj-flex-grid>
  </div>
  <%--//위즈모 테이블--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}";
  var storeCd = "${storeCd}";

  // List 형식("" 안붙임)
  var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
  var branchCdComboList = ${branchCdComboList};
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

</script>
<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotionReport/promotionReport.js?ver=20230314.01" charset="utf-8"></script>

<%-- 프로모션정산 상세 --%>
<c:import url="/WEB-INF/view/base/promotion/promotionReport/promotionReportDtl.jsp">
</c:import>