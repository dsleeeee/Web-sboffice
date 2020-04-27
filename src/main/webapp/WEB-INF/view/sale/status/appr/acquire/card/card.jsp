<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="apprAcquireCardView" class="subCon3"  ng-controller="apprAcquireCardCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="store.pay1"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnApprCardSearch" ng-click="_broadcast('apprAcquireCardCtrlSrch')">
        <s:message code="cmm.search"/>
      </button>
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
        <td <c:if test="${sessionInfo.orgnFg == 'STORE'}">colspan="3"</c:if> >
        <div class="sb-select">
            <span class="txtIn"><input id="srchApprAcquireCardStartDate" class="w120px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchApprAcquireCardEndDate" class="w120px"></span>
            <span class="chk ml10">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>
        </div>
        </td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="hidden" id="apprAcquireCardSelectStoreCd" value="${sessionInfo.storeCd}"/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="apprAcquireCardSelectStore"/>
                <jsp:param name="targetPosId" value="apprAcquireCardSelectPos"/>
                <jsp:param name="targetCornerId" value="apprAcquireCardSelectCorner"/>
                <jsp:param name="closeFunc" value="getCornerNmList,getPosNmList"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      </c:if>
      </tr>

      <tr>
        <%-- 포스선택 --%>
        <th><s:message code="pos.pos" /></th>
        <td>
            <%-- 포스선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
                <jsp:param name="targetId" value="apprAcquireCardSelectPos"/>
                <jsp:param name="targetStoreId" value="apprAcquireCardSelectStore"/>
                <jsp:param name="closeFunc" value="getPosNmList"/>
            </jsp:include>
            <%--// 포스선택 모듈 멀티 선택 사용시 include --%>
        </td>

        <%-- 코너표시 --%>
        <th><s:message code="corner.cornrNm" /></th>
        <td>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectCornerM.jsp" flush="true">
                <jsp:param name="targetId" value="apprAcquireCardSelectCorner"/>
                <jsp:param name="targetStoreId" value="apprAcquireCardSelectStore"/>
                <jsp:param name="closeFunc" value="getCornerNmList"/>
            </jsp:include>
        </td>
      </tr>

      <tr>
        <%-- 승인구분 --%>
        <th><s:message code="dayMcoupn.apprProcFg" /></th>
        <td>
          <div class="sb-select">
              <span class="txtIn">
                    <wj-combo-box
                      id="srchAcquireCardSaleFgDisplay"
                      ng-model="saleFgModel"
                      items-source="_getComboData('srchAcquireCardSaleFgDisplay')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                </span>
          </div>
        </td>
       <%-- 승인처리 --%>
        <th><s:message code="storeStatus.apprProcFg" /></th>
        <td>
          <div class="sb-select">
              <span class="txtIn">
                    <wj-combo-box
                      id="srchAcquireCardApprProcFgDisplay"
                      ng-model="apprProcFgModel"
                      items-source="_getComboData('srchAcquireCardApprProcFgDisplay')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                </span>
          </div>
        </td>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="apprAcquireCardSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
        <input type="hidden" id="apprAcquireCardSelectPosCd" value=""/>
        <input type="hidden" id="apprAcquireCardSelectPosName" value=""/>
        <input type="hidden" id="apprAcquireCardSelectCornerCd" value=""/>
        <input type="hidden" id="apprAcquireCardSelectCornrName" value=""/>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="apprAcquireCardListScaleBox"
            ng-model="apprAcquireCardListScale"
            items-source="_getComboData('apprAcquireCardListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="initComboBox(s)"
            control="conListScale"
			is-editable="true"
			text-changed="_checkValidation(s)">
    </wj-combo-box>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="hidden" id="apprAcquireCardSelectStoreStoreNum" ng-model="storeNum">
        </c:if>
    <%-- 엑셀 다운로드 //TODO --%>
    <button class="btn_skyblue fr" ng-click="excelDownloadAcquireCard()"><s:message code="cmm.excel.down" />
    </button>
  </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap2">
        <wj-flex-grid
          id="apprAcquireCardGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="rtnStatus.storeCd"            />"     binding="storeCd"         width="100" is-read-only="true" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="rtnStatus.storeNm"            />"     binding="storeNm"         width="200" is-read-only="true" align="center" ></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="appr.acquire.cardCd"            />"   binding="acquireCd"       width="100" is-read-only="true" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="appr.acquire.cardNm"            />"   binding="acquireNm"       width="200" is-read-only="true" align="center" ></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntCard"      />"     binding="cnt"             width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.requestAmt"           />"     binding="saleAmt"         width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApCard"       />"     binding="apprAmt"         width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntCard"      />"     binding="cntA"            width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.requestAmt"           />"     binding="saleAmtA"        width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApCard"       />"     binding="apprAmtA"        width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntCard"      />"     binding="cntB"            width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="taxBill.requestAmt"           />"     binding="saleAmtB"        width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApCard"       />"     binding="apprAmtB"        width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="apprAcquireCardCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
   <%-- id --%>
    <ul id="apprAcquireCardCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%-- 엑셀 리스트 --%>
  <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="apprAcquireCardExcelCtrl">
    <div class="wj-gridWrap">
          <wj-flex-grid
              id="apprAcquireCardExcelGrid"
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="excelFlex"
              initialized="initGrid(s,e)"
              loaded-rows="loadedRows(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">
              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="rtnStatus.storeCd"            />"     binding="storeCd"         width="100" is-read-only="true" align="center" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="rtnStatus.storeNm"            />"     binding="storeNm"         width="200" is-read-only="true" align="center" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="appr.acquire.cardCd"            />"   binding="acquireCd"       width="100" is-read-only="true" align="center" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="appr.acquire.cardNm"            />"   binding="acquireNm"       width="200" is-read-only="true" align="center" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntCard"      />"     binding="cnt"             width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="taxBill.requestAmt"           />"     binding="saleAmt"         width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyReport.apprApCard"       />"     binding="apprAmt"         width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntCard"      />"     binding="cntA"            width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="taxBill.requestAmt"           />"     binding="saleAmtA"        width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyReport.apprApCard"       />"     binding="apprAmtA"        width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntCard"      />"     binding="cntB"            width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="taxBill.requestAmt"           />"     binding="saleAmtB"        width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyReport.apprApCard"       />"     binding="apprAmtB"        width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          </wj-flex-grid>
      </div>
  </div>
  <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/appr/acquire/card/card.js" charset="utf-8"></script>
